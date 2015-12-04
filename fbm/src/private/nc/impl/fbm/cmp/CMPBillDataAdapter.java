package nc.impl.fbm.cmp;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import nc.bs.fbm.pub.ActionQueryDAO;
import nc.bs.fbm.pub.BaseInfoBO;
import nc.bs.fbm.pub.CommonDAO;
import nc.bs.fbm.pub.OuterRelationDAO;
import nc.vo.cmp.settlement.SettlementAggVO;
import nc.vo.cmp.settlement.SettlementBodyVO;
import nc.vo.cmp.settlement.SettlementHeadVO;
import nc.vo.ep.dj.DJZBVO;
import nc.vo.fbm.endore.EndoreVO;
import nc.vo.fbm.outer.ArapBillParamVO;
import nc.vo.pub.BusinessException;
import nc.vo.pub.lang.UFDouble;
import nc.vo.trade.pub.HYBillVO;

/**
 * 将收付款单转换为需要的参数对象
 * 
 * @author wes
 * 
 */
public class CMPBillDataAdapter {

	/**
	 * 收付报动作-新增保存
	 */
	public static String ACTION_ADD = "add";

	/**
	 * 收付报动作-编辑保存
	 */
	public static String ACTION_EDIT = "edit";

	/**
	 * 收付报动作 - 删除
	 */
	public static String ACTION_DEL = "del";

	/**
	 * 收付报动作 - 生效
	 */
	public static String ACTION_EFFECT = "effect";

	/**
	 * 收付报动作 - 取消生效
	 */
	public static String ACTION_UNEFFECT = "uneffect";

	/**
	 * 应收应付动作
	 */
	public static String ACTION_YS_YF = "ys_yf";

	/**
	 * 原币
	 */
	private static final int YB = 0;

	/**
	 * 辅币
	 */
	private static final int FB = 1;

	/**
	 * 本币
	 */
	private static final int BB = 2;

	/**
	 * 将收付款单据VO转换为票据处理的参数对象
	 * 
	 * @param param
	 * @return
	 */
	public static ArapBillParamVO buildBillParam(SettlementAggVO param,
			SettlementBodyVO item, int row) throws BusinessException {

		SettlementHeadVO vo = (SettlementHeadVO) param.getParentVO();
		ArapBillParamVO arapParam = new ArapBillParamVO();
		String djdl = vo.getDef11();// 取得前面getClass中放置的单据大类，临时存储
		String djlxbm = vo.getPk_tradetype();// 具体的单据小类

		arapParam = new ArapBillParamVO();
		if (item.getNotenumber() != null) {
			arapParam.setFbmbillno(item.getNotenumber().trim());
		}
		arapParam.setPk_curr(item.getPk_currtype());
		arapParam.setDjrq(vo.getBusi_billdate());// 业务单据日期

		arapParam.setMemo(item.getMemo());//摘要
		
		// 金额合并处理

		Map<String, UFDouble> mapMoneyy = null;
		Map<String, UFDouble> mapMoneyf = null;
		Map<String, UFDouble> mapMoneyb = null;
		mapMoneyy = sumMoney((SettlementBodyVO[]) param.getChildrenVO(), djdl,
				YB);
		mapMoneyf = sumMoney((SettlementBodyVO[]) param.getChildrenVO(), djdl,
				FB);
		mapMoneyb = sumMoney((SettlementBodyVO[]) param.getChildrenVO(), djdl,
				BB);
		arapParam.setMoneyy(mapMoneyy.get(arapParam.getFbmbillno()));
		arapParam.setMoneyf(mapMoneyf.get(arapParam.getFbmbillno()));
		arapParam.setMoneyb(mapMoneyb.get(arapParam.getFbmbillno()));

		arapParam.setFrate(item.getFracrate());// 辅币汇率
		arapParam.setBrate(item.getLocalrate());// 本币汇率

		arapParam.setPk_bill_h(vo.getPrimaryKey());
		arapParam.setPk_bill_b(item.getPrimaryKey());
		arapParam.setOuterbilltype(djlxbm);// 单据类型编码
		arapParam.setOuterdjdl(djdl);// 设置单据大类
		arapParam.setRow(row + 1);
		// arapParam.setSkbankacc(item.getSkyhzh());// 收款银行账号
		// arapParam.setFkbankacc(item.getFkyhzh());// 付款银行账号
		arapParam.setVoperator(vo.getPk_billoperator());// 录入人
		arapParam.setDoperatdate(vo.getBusi_billdate());// 录入日期，取单据日期
		arapParam.setVeffector(vo.getPk_signer());// 生效人，取确认人(签字人)
		arapParam.setDeffectdate(vo.getSigndate());// 生效日期
		arapParam.setPk_plansubj(item.getPk_plansubj());

		String pk_corp = vo.getPk_corp();

		CommonDAO dao = new CommonDAO();
		String pk_cust = dao.queryCustByCorp(pk_corp);

		if (pk_cust == null) {
			throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl
					.getNCLangRes().getStrByID("fbmcomm", "UPPFBMComm-000224")/* @res"公司未对应客商" */);
		}
		arapParam.setCurrentunit(pk_cust);// 本方单位，取公司对应客商

		arapParam.setTradertype(item.getTradertype());// 对方类型
		
		if(item.getTradertype() != 0){
			throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcode112","UPPFBMCODE112-000037")/*@res "使用票据进行结算往来对象必须是客商"*/);
		}
		arapParam.setOtherunit(item.getPk_trader());// 对方单位，取伙伴编码
		

		arapParam.setPk_corp(pk_corp);

		// 查询票据基本信息VO
		if (arapParam.getFbmbillno() != null
				&& arapParam.getFbmbillno().length() > 0) {
			BaseInfoBO baseBO = new BaseInfoBO();
			arapParam.setBaseinfoVO(baseBO.queryByFbmbillno(arapParam
					.getFbmbillno()));
			// 查询票据最新动作VO
			ActionQueryDAO actionDao = new ActionQueryDAO();
			arapParam.setNewActionVO(actionDao.queryNewestByFbmBillno(arapParam
					.getFbmbillno(), pk_corp));
			if (arapParam.getPk_busbill() == null) {
				arapParam.setPk_busbill(arapParam.getNewActionVO()
						.getPk_source());
			}
			// 查询当前单据关联
			OuterRelationDAO relDao = new OuterRelationDAO();
			arapParam.setOuterVO(relDao.queryBypk_b(item.getPrimaryKey()));
		}
		return arapParam;
	}

	/**
	 * 收付报单据转换为背书办理单数据
	 * 
	 * @param param
	 * @param actionCode
	 * @return
	 * @throws BusinessException
	 */
	public HYBillVO buildEndoreData(DJZBVO param) throws BusinessException {
		EndoreVO endoreVO = new EndoreVO();
		HYBillVO billVO = new HYBillVO();

		billVO.setParentVO(endoreVO);
		return billVO;
	}

	/**
	 * 合并票据金额 moneytype 0 原币 1 辅币 2 本币
	 * 
	 * @param items
	 * @return
	 */
	private static Map<String, UFDouble> sumMoney(SettlementBodyVO[] items,
			String djdl, int moneytype) throws BusinessException {
		Map<String, UFDouble> map = new HashMap<String, UFDouble>();
		Set<String> billnoSet = new HashSet<String>();
		String billno = null;
		UFDouble money = null;

		for (int i = 0; i < items.length; i++) {

			if (items[i].getNotenumber() != null) {
				billno = items[i].getNotenumber().trim();
			} else {
				continue;
			}

			if (CMPConstant.BILLTYPE_SK.equals(djdl)
					|| CMPConstant.BILLTYPE_SJ.equals(djdl)) {
				switch (moneytype) {
				case YB:
					money = items[i].getReceive();// 付款原币金额
					break;
				case FB:
					money = items[i].getReceivefrac();// 辅币金额
					break;
				case BB:
					money = items[i].getReceivelocal();// 本币
					break;
				}
			}
			if (CMPConstant.BILLTYPE_FK.equals(djdl)
					|| CMPConstant.BILLTYPE_FJ.equals(djdl)) {
				switch (moneytype) {
				case YB:
					money = items[i].getPay();
					break;
				case FB:
					money = items[i].getPayfrac();
					break;
				case BB:
					money = items[i].getPaylocal();
					break;
				}
			}
			if (money == null) {
				money = new UFDouble(0);
			}

			if (billnoSet.contains(billno)) {
				UFDouble tmpMoney = map.get(billno);
				map.put(billno, tmpMoney.add(money));
			} else {
				map.put(billno, money);
			}
			billnoSet.add(billno);
		}
		return map;
	}

}