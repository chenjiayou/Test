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
 * ���ո��ת��Ϊ��Ҫ�Ĳ�������
 * 
 * @author wes
 * 
 */
public class CMPBillDataAdapter {

	/**
	 * �ո�������-��������
	 */
	public static String ACTION_ADD = "add";

	/**
	 * �ո�������-�༭����
	 */
	public static String ACTION_EDIT = "edit";

	/**
	 * �ո������� - ɾ��
	 */
	public static String ACTION_DEL = "del";

	/**
	 * �ո������� - ��Ч
	 */
	public static String ACTION_EFFECT = "effect";

	/**
	 * �ո������� - ȡ����Ч
	 */
	public static String ACTION_UNEFFECT = "uneffect";

	/**
	 * Ӧ��Ӧ������
	 */
	public static String ACTION_YS_YF = "ys_yf";

	/**
	 * ԭ��
	 */
	private static final int YB = 0;

	/**
	 * ����
	 */
	private static final int FB = 1;

	/**
	 * ����
	 */
	private static final int BB = 2;

	/**
	 * ���ո����VOת��ΪƱ�ݴ���Ĳ�������
	 * 
	 * @param param
	 * @return
	 */
	public static ArapBillParamVO buildBillParam(SettlementAggVO param,
			SettlementBodyVO item, int row) throws BusinessException {

		SettlementHeadVO vo = (SettlementHeadVO) param.getParentVO();
		ArapBillParamVO arapParam = new ArapBillParamVO();
		String djdl = vo.getDef11();// ȡ��ǰ��getClass�з��õĵ��ݴ��࣬��ʱ�洢
		String djlxbm = vo.getPk_tradetype();// ����ĵ���С��

		arapParam = new ArapBillParamVO();
		if (item.getNotenumber() != null) {
			arapParam.setFbmbillno(item.getNotenumber().trim());
		}
		arapParam.setPk_curr(item.getPk_currtype());
		arapParam.setDjrq(vo.getBusi_billdate());// ҵ�񵥾�����

		arapParam.setMemo(item.getMemo());//ժҪ
		
		// ���ϲ�����

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

		arapParam.setFrate(item.getFracrate());// ���һ���
		arapParam.setBrate(item.getLocalrate());// ���һ���

		arapParam.setPk_bill_h(vo.getPrimaryKey());
		arapParam.setPk_bill_b(item.getPrimaryKey());
		arapParam.setOuterbilltype(djlxbm);// �������ͱ���
		arapParam.setOuterdjdl(djdl);// ���õ��ݴ���
		arapParam.setRow(row + 1);
		// arapParam.setSkbankacc(item.getSkyhzh());// �տ������˺�
		// arapParam.setFkbankacc(item.getFkyhzh());// ���������˺�
		arapParam.setVoperator(vo.getPk_billoperator());// ¼����
		arapParam.setDoperatdate(vo.getBusi_billdate());// ¼�����ڣ�ȡ��������
		arapParam.setVeffector(vo.getPk_signer());// ��Ч�ˣ�ȡȷ����(ǩ����)
		arapParam.setDeffectdate(vo.getSigndate());// ��Ч����
		arapParam.setPk_plansubj(item.getPk_plansubj());

		String pk_corp = vo.getPk_corp();

		CommonDAO dao = new CommonDAO();
		String pk_cust = dao.queryCustByCorp(pk_corp);

		if (pk_cust == null) {
			throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl
					.getNCLangRes().getStrByID("fbmcomm", "UPPFBMComm-000224")/* @res"��˾δ��Ӧ����" */);
		}
		arapParam.setCurrentunit(pk_cust);// ������λ��ȡ��˾��Ӧ����

		arapParam.setTradertype(item.getTradertype());// �Է�����
		
		if(item.getTradertype() != 0){
			throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcode112","UPPFBMCODE112-000037")/*@res "ʹ��Ʊ�ݽ��н���������������ǿ���"*/);
		}
		arapParam.setOtherunit(item.getPk_trader());// �Է���λ��ȡ������
		

		arapParam.setPk_corp(pk_corp);

		// ��ѯƱ�ݻ�����ϢVO
		if (arapParam.getFbmbillno() != null
				&& arapParam.getFbmbillno().length() > 0) {
			BaseInfoBO baseBO = new BaseInfoBO();
			arapParam.setBaseinfoVO(baseBO.queryByFbmbillno(arapParam
					.getFbmbillno()));
			// ��ѯƱ�����¶���VO
			ActionQueryDAO actionDao = new ActionQueryDAO();
			arapParam.setNewActionVO(actionDao.queryNewestByFbmBillno(arapParam
					.getFbmbillno(), pk_corp));
			if (arapParam.getPk_busbill() == null) {
				arapParam.setPk_busbill(arapParam.getNewActionVO()
						.getPk_source());
			}
			// ��ѯ��ǰ���ݹ���
			OuterRelationDAO relDao = new OuterRelationDAO();
			arapParam.setOuterVO(relDao.queryBypk_b(item.getPrimaryKey()));
		}
		return arapParam;
	}

	/**
	 * �ո�������ת��Ϊ�����������
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
	 * �ϲ�Ʊ�ݽ�� moneytype 0 ԭ�� 1 ���� 2 ����
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
					money = items[i].getReceive();// ����ԭ�ҽ��
					break;
				case FB:
					money = items[i].getReceivefrac();// ���ҽ��
					break;
				case BB:
					money = items[i].getReceivelocal();// ����
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