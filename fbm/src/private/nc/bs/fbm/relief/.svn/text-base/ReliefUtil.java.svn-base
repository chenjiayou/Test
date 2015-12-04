package nc.bs.fbm.relief;

import java.util.ArrayList;
import java.util.List;

import nc.bs.dao.BaseDAO;
import nc.bs.fbm.pub.CommonDAO;
import nc.bs.fbm.pub.FBM2CMPAccSuper;
import nc.bs.fbm.pub.FbmBDQueryDAO;
import nc.bs.framework.common.InvocationInfoProxy;
import nc.bs.framework.common.NCLocator;
import nc.itf.cm.prv.CmpConst;
import nc.itf.cmp.bankacc.AccBusiVO;
import nc.itf.cmp.bankacc.CMPaccStatus;
import nc.itf.fbm.pub.IFbm2CmpAccSettleOrg;
import nc.itf.uap.bd.cust.ICustManDocQuery;
import nc.vo.bd.b08.CustBasVO;
import nc.vo.cmp.settlement.SettlementBodyVO;
import nc.vo.fbm.pub.BaseinfoVO;
import nc.vo.fbm.pub.BusiActionParamVO;
import nc.vo.fbm.pub.FBMProxy;
import nc.vo.fbm.pub.FBMPublicQry;
import nc.vo.fbm.register.RegisterVO;
import nc.vo.fbm.relief.ReliefBVO;
import nc.vo.fbm.relief.ReliefVO;
import nc.vo.fi.pub.ProductInfo;
import nc.vo.pub.BusinessException;
import nc.vo.pub.lang.UFDate;
import nc.vo.tam.account.IAccConst;
import nc.vo.tam.account.accid.AccidVO;
import nc.vo.trade.pub.HYBillVO;

/**
 * 
 * 类功能说明： 调剂出库用到的临时常量类 日期：2007-10-31 程序员： wues
 * 
 */
public class ReliefUtil extends FBM2CMPAccSuper implements IFbm2CmpAccSettleOrg {
	/**
	 * 将ActionParamVO进行拆分 分成自己的票据和其他的票据
	 * 
	 * @param params
	 * @return
	 * @throws BusinessException
	 */
	public static List<BusiActionParamVO>[] splitParams(
			BusiActionParamVO<ReliefVO>[] params) throws BusinessException {
		if (null == params || params.length == 0) {
			return null;
		}
		// 第0个list存放自己的票，第一个元素存放他人调剂过来的票
		List<BusiActionParamVO>[] list = new ArrayList[2];

		for (int i = 0; i < params.length; i++) {
			if (isSelfBill(params[i])) {
				// 是自己的票
				if (null == list[0]) {
					list[0] = new ArrayList<BusiActionParamVO>();
				}
				list[0].add(params[i]);
			} else {
				if (null == list[1]) {
					list[1] = new ArrayList<BusiActionParamVO>();
				}
				list[1].add(params[i]);
			}
		}
		return list;
	}

	/**
	 * 根据票据PK和持票单位PK判断是否已经存在
	 * 
	 * @param pk_baseInfo
	 * @param holdUnit
	 * @return
	 */
	public static boolean isSelfBill(BusiActionParamVO<ReliefVO> param)
			throws BusinessException {

		String pk_register = param.getPk_source();
		String holdUnit = ((ReliefVO) param.getSuperVO()).getReliefunit();

		RegisterVO vo = null;

		BaseDAO dao = new BaseDAO();
		if (null == param.getRegisterVO()) {
			vo = (RegisterVO) dao.retrieveByPK(RegisterVO.class, pk_register);
		} else {
			vo = param.getRegisterVO();
		}
		// 根据pk_register取得相应的收票登记单与holdunit对比，不能pk_baseinfo，因为有可能收到回头票
		if (null == vo || "".equals(vo.getPrimaryKey())) {
			throw new BusinessException(
					nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("36201045", "UPP36201045-000009")/*
																											 * @res
																											 * "根据收票登记单号无法查到对应的收票信息，数据错误。"
																											 */);
		}

		// 由于调剂的票持票单位已经改变，所以原有的用vo.getHoldunit()判断是不是自己的票的方法不起作用了
		// 现在改用客商进行判断。
		String pk_corp1 = vo.getPk_corp();
		String pk_corp = InvocationInfoProxy.getInstance().getCorpCode();
		FbmBDQueryDAO queryDao = new FbmBDQueryDAO();
		String cubasdoc = queryDao.retriveCubasdocByPk_corp(pk_corp1, pk_corp);
		// if (!holdUnit.equals(vo.getHoldunit())) {
		if (!holdUnit.equals(cubasdoc)) {
			// 不是自己的票据
			return false;
		} else {
			return true;
		}
	}

	/**
	 * 根据fieldValue将SQL查询包上单引号 例：fieldValue = "555"; 返回： "'555'",增强正常拼接时的正确性和易读性
	 * 
	 * @param fieldCode
	 * @param fieldValue
	 * @return
	 */
	public static String enclose(String fieldValue) {
		if (null == fieldValue || "".equals(fieldValue.trim())) {
			return "''";
		} else {
			return "'" + fieldValue + "'";
		}
	}

	public void delCMPacc(HYBillVO billvo, String pk_corp, String operator,
			UFDate operatedate) throws BusinessException {

		ReliefVO vo = (ReliefVO) billvo.getParentVO();
		AccBusiVO rvo = new AccBusiVO();
		rvo.setPk_corp(pk_corp);
		rvo.setPk_sourcebill(vo.getPrimaryKey());
		rvo.setOperator(operator);// 当前登陆人
		rvo.setOperateDate(operatedate);// 当前日期

		getBankTallyService().deleteWhenHaveBill(rvo);
	}

	public void insertBankAcc4Center(HYBillVO billvo, String tallycorp,
			String tallyman, UFDate tallydate) throws BusinessException {
		// TODO Auto-generated method stub

	}

	public void insertBankAcc4Unit(HYBillVO billvo, String tallycorp,
			String tallyman, UFDate tallydate) throws BusinessException {

		if (!isTogatherWithCMP(tallycorp)) {
			return;
		}
		ReliefVO headvo = (ReliefVO) billvo.getParentVO();

		AccidVO accidvo = (AccidVO) FBMProxy.getUAPQuery().retrieveByPK(AccidVO.class, headvo.getInneracc());
		// 如果是票据户，则直接返回
		if (accidvo.getAcctype() == IAccConst.ACCL_BILL) {
			return;
		}

		ReliefBVO[] bodyvo = (ReliefBVO[]) billvo.getChildrenVO();

		SettlementBodyVO[] rvos = new SettlementBodyVO[bodyvo.length];
		CommonDAO commonDAO = new CommonDAO();
		BaseinfoVO baseVO = null;
		ICustManDocQuery cumanQry = (ICustManDocQuery) NCLocator.getInstance().lookup(ICustManDocQuery.class);
		String cent_corp = FBMPublicQry.retrivePk_centcorpByPkCorp(tallycorp);
		String strWhere = " bd_cubasdoc.pk_corp1 = '" + cent_corp + "' ";
		CustBasVO[] custbasVos = cumanQry.queryCustBasicInfo(strWhere, null);

		for (int i = 0; i < bodyvo.length; i++) {
			baseVO = commonDAO.queryBaseinfoByPK(bodyvo[i].getPk_baseinfo());
			rvos[i] = new SettlementBodyVO();
			rvos[i].setPk_corp(tallycorp);
			rvos[i].setSystemcode(ProductInfo.pro_FBM);
			rvos[i].setPk_bill(headvo.getPrimaryKey());
			rvos[i].setPk_currtype(headvo.getPk_currtype());
			rvos[i].setBillcode(headvo.getVbillno());
			rvos[i].setTallystatus(new Integer(
					CMPaccStatus.SUCCESSACCOUNT.getStatus()));
			rvos[i].setTallydate(headvo.getDapprovedate());
			rvos[i].setSigndate(headvo.getDapprovedate());
			rvos[i].setFundformcode(CmpConst.BANK_DEPOSIT);
			rvos[i].setPk_billtype(headvo.getPk_billtypecode());
			rvos[i].setBilldate(headvo.getDapprovedate());
			rvos[i].setReceive(baseVO.getMoneyy());
			rvos[i].setFundsflag(CmpConst.CASH_FLOW_IN);
			rvos[i].setDirection(CmpConst.Direction_Receive);
			rvos[i].setPk_account(headvo.getInneracc());
			rvos[i].setTradertype(CmpConst.TradeObjType_KeShang);
			if (custbasVos != null && custbasVos.length > 0) {
				rvos[i].setPk_trader(custbasVos[0].getPk_cubasdoc());
			}
			buildOppInfo(rvos[i]);

			rvos[i].setMemo(headvo.getMemo());
			fillCurrKeyValue(rvos[i]);
		}

		// getBankTallyService().writeBankacc(rvos);
		writeBankAcc(rvos, headvo.getWritebankacc());

	}

}