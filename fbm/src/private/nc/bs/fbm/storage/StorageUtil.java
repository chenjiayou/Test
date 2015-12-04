package nc.bs.fbm.storage;

import nc.bs.fbm.pub.CommonDAO;
import nc.bs.fbm.pub.FBM2CMPAccSuper;
import nc.bs.framework.common.NCLocator;
import nc.itf.cm.prv.CmpConst;
import nc.itf.cmp.bankacc.AccBusiVO;
import nc.itf.cmp.bankacc.CMPaccStatus;
import nc.itf.fbm.pub.IFbm2CmpAccSettleOrg;
import nc.itf.uap.bd.cust.ICustManDocQuery;
import nc.vo.bd.b08.CustBasVO;
import nc.vo.cmp.settlement.SettlementBodyVO;
import nc.vo.fbm.pub.BaseinfoVO;
import nc.vo.fbm.pub.FBMProxy;
import nc.vo.fbm.pub.FBMPublicQry;
import nc.vo.fbm.pub.constant.FbmBusConstant;
import nc.vo.fbm.storage.StorageBVO;
import nc.vo.fbm.storage.StorageVO;
import nc.vo.fi.pub.ProductInfo;
import nc.vo.pub.BusinessException;
import nc.vo.pub.lang.UFDate;
import nc.vo.tam.account.IAccConst;
import nc.vo.tam.account.accid.AccidVO;
import nc.vo.trade.pub.HYBillVO;

/**
 * �йܵ������õ�д������
 * 
 * @author xwq
 * 
 *         2008-12-20
 */
public class StorageUtil extends FBM2CMPAccSuper implements
		IFbm2CmpAccSettleOrg {

	public void insertBankAcc4Center(HYBillVO billvo, String tallycorp,
			String tallyman, UFDate tallydate) throws BusinessException {
		// TODO Auto-generated method stub
		if (!isTogatherWithCMP(tallycorp)) {
			return;
		}
		StorageVO vo = (StorageVO) billvo.getParentVO();
		// ��ŷ�ʽΪ�����й�ʱдƱ���ռ���
		if (vo.getKeepaccount() != null
				&& FbmBusConstant.KEEP_TYPE_RELIEF.equals(vo.getInputtype())) {
			addCMPBill(billvo, tallycorp, tallyman, tallydate);
		}
	}

	public void insertBankAcc4Unit(HYBillVO billvo, String tallycorp,
			String tallyman, UFDate tallydate) throws BusinessException {
		if (!isTogatherWithCMP(tallycorp)) {
			return;
		}
		StorageVO vo = (StorageVO) billvo.getParentVO();
		// ֻ�е����й�ʱ��д�����˺�Ʊ����
		if (vo.getKeepaccount() != null
				&& FbmBusConstant.KEEP_TYPE_RELIEF.equals(vo.getInputtype())) {
			addCMPBill(billvo, tallycorp, tallyman, tallydate);

			AccidVO accidvo = (AccidVO) FBMProxy.getUAPQuery().retrieveByPK(AccidVO.class, vo.getKeepaccount());
			// �����Ʊ�ݻ�����ֱ�ӷ���
			if (accidvo.getAcctype() == IAccConst.ACCL_BILL) {
				return;
			}
			addCMPBank(billvo, tallycorp, tallyman, tallydate);
		}
	}

	/**
	 * д�����ռ���
	 * 
	 * @param billvo
	 * @param pk_corp
	 * @param tallyman
	 * @param tallydate
	 * @throws BusinessException
	 */
	private void addCMPBank(HYBillVO billvo, String pk_corp, String tallyman,
			UFDate tallydate) throws BusinessException {

		if (!isTogatherWithCMP(pk_corp)) {
			return;
		}
		StorageVO headvo = (StorageVO) billvo.getParentVO();
		StorageBVO[] bodyvo = (StorageBVO[]) billvo.getChildrenVO();
		AccidVO accidvo = (AccidVO) FBMProxy.getUAPQuery().retrieveByPK(AccidVO.class, headvo.getKeepaccount());

		String keepcorp = headvo.getKeepcorp();
		// ���ʱΪ��λ���ˣ�����Ϊ���ļ���
		boolean isSettleUnit = pk_corp.equals(keepcorp) ? true : false;

		if (!isSettleUnit) {
			return;// ���Ĳ��ü�������
		}
		String billtype = headvo.getPk_billtypecode();

		SettlementBodyVO[] rvos = new SettlementBodyVO[bodyvo.length];
		CommonDAO commonDAO = new CommonDAO();
		BaseinfoVO baseVO = null;
		ICustManDocQuery cumanQry = (ICustManDocQuery) NCLocator.getInstance().lookup(ICustManDocQuery.class);
		String cent_corp = FBMPublicQry.retrivePk_centcorpByPkCorp(pk_corp);
		String strWhere = " bd_cubasdoc.pk_corp1 = '" + cent_corp + "' ";
		CustBasVO[] custbasVos = cumanQry.queryCustBasicInfo(strWhere, null);

		for (int i = 0; i < bodyvo.length; i++) {
			baseVO = commonDAO.queryBaseinfoByPK(bodyvo[i].getPk_baseinfo());
			rvos[i] = new SettlementBodyVO();
			rvos[i].setPk_corp(pk_corp);
			rvos[i].setSystemcode(ProductInfo.pro_FBM);
			rvos[i].setPk_bill(headvo.getPrimaryKey());
			rvos[i].setPk_currtype(headvo.getPk_currtype());
			rvos[i].setBillcode(headvo.getVbillno());
			rvos[i].setPk_account(accidvo.getPrimaryKey());
			rvos[i].setTallystatus(new Integer(
					CMPaccStatus.SUCCESSACCOUNT.getStatus()));
			rvos[i].setTallydate(tallydate);
			rvos[i].setSigndate(tallydate);
			rvos[i].setBilldate(tallydate);
			rvos[i].setFundformcode(CmpConst.BANK_DEPOSIT);
			rvos[i].setTradertype(CmpConst.TradeObjType_KeShang);
			rvos[i].setPk_billtype(headvo.getPk_billtypecode());
			rvos[i].setMemo(headvo.getMemo());
			rvos[i].setTranstype(CmpConst.INNER_TRADE);// �ڲ�����
			fillCurrKeyValue(rvos[i]);

			if (billtype.equals(FbmBusConstant.BILLTYPE_INNERKEEP)) {
				rvos[i].setBilldate(headvo.getInputdate());
				rvos[i].setReceive(baseVO.getMoneyy());

				rvos[i].setFundsflag(CmpConst.CASH_FLOW_IN);
				rvos[i].setDirection(CmpConst.Direction_Receive);

			} else if (billtype.equals(FbmBusConstant.BILLTYPE_INNERBACK)) {
				rvos[i].setBilldate(headvo.getOutputdate());
				rvos[i].setPay(baseVO.getMoneyy());
				rvos[i].setFundsflag(CmpConst.CASH_FLOW_OUT);
				rvos[i].setDirection(CmpConst.Direction_Pay);
			}

			if (custbasVos != null && custbasVos.length > 0) {
				rvos[i].setPk_trader(custbasVos[0].getPk_cubasdoc());
			}

			buildOppInfo(rvos[i]);
			fillCurrKeyValue(rvos[i]);
		}

		// getBankTallyService().writeBankacc(rvos);
		writeBankAcc(rvos, headvo.getWritebankacc());
	}

	/**
	 * дƱ���ռ���
	 * 
	 * @param billvo
	 * @param pk_corp
	 * @param tallyman
	 * @param tallydate
	 * @param isCurrentAcc
	 *            �ڲ��ʻ��Ƿ�����˻�
	 * @return
	 * @throws BusinessException
	 */
	private void addCMPBill(HYBillVO billvo, String pk_corp, String tallyman,
			UFDate tallydate) throws BusinessException {
		if (!isTogatherWithCMP(pk_corp)) {
			return;
		}
		StorageVO headvo = (StorageVO) billvo.getParentVO();
		StorageBVO[] bodyvo = (StorageBVO[]) billvo.getChildrenVO();

		String billtype = headvo.getPk_billtypecode();
		String keepcorp = headvo.getKeepcorp();
		// ���ʱΪ��λ���ˣ�����Ϊ���ļ���
		boolean isSettleUnit = pk_corp.equals(keepcorp) ? true : false;

		SettlementBodyVO[] rvos = new SettlementBodyVO[bodyvo.length];
		initSettlementBodyVO(rvos, headvo);

		CommonDAO commonDAO = new CommonDAO();
		BaseinfoVO baseVO = null;
		ICustManDocQuery cumanQry = (ICustManDocQuery) NCLocator.getInstance().lookup(ICustManDocQuery.class);
		String cent_corp = FBMPublicQry.retrivePk_centcorpByPkCorp(pk_corp);
		String strWhere = " bd_cubasdoc.pk_corp1 = '" + cent_corp + "' ";
		CustBasVO[] custbasVos = cumanQry.queryCustBasicInfo(strWhere, null);
		for (int i = 0; i < bodyvo.length; i++) {
			baseVO = commonDAO.queryBaseinfoByPK(bodyvo[i].getPk_baseinfo());
			rvos[i].setPk_corp(pk_corp);
			rvos[i].setPk_currtype(headvo.getPk_currtype());

			rvos[i].setFundformcode(CmpConst.BILL_DEPOSIT);
			rvos[i].setTradertype(CmpConst.TradeObjType_KeShang);

			rvos[i].setPk_notetype(baseVO.getFbmbilltype());
			rvos[i].setMemo(headvo.getMemo());

			if (billtype.equals(FbmBusConstant.BILLTYPE_INNERKEEP)) {
				rvos[i].setBilldate(headvo.getInputdate());
				rvos[i].setTallydate(headvo.getInputdate());
				rvos[i].setSigndate(headvo.getInputdate());
				if (isSettleUnit) {
					rvos[i].setPay(baseVO.getMoneyy());
					rvos[i].setFundsflag(CmpConst.CASH_FLOW_OUT);
					rvos[i].setDirection(CmpConst.Direction_Pay);
					if (custbasVos != null && custbasVos.length > 0) {
						rvos[i].setPk_trader(custbasVos[0].getPk_cubasdoc());
					}

				} else {
					rvos[i].setReceive(baseVO.getMoneyy());
					rvos[i].setFundsflag(CmpConst.CASH_FLOW_IN);
					rvos[i].setDirection(CmpConst.Direction_Receive);

					rvos[i].setPk_trader(headvo.getKeepunit());
				}
			} else if (billtype.equals(FbmBusConstant.BILLTYPE_INNERBACK)) {
				rvos[i].setBilldate(headvo.getOutputdate());
				rvos[i].setTallydate(headvo.getOutputdate());
				rvos[i].setSigndate(headvo.getOutputdate());
				if (isSettleUnit) {
					rvos[i].setReceive(baseVO.getMoneyy());
					rvos[i].setFundsflag(CmpConst.CASH_FLOW_IN);
					rvos[i].setDirection(CmpConst.Direction_Receive);
					if (custbasVos != null && custbasVos.length > 0) {
						rvos[i].setPk_trader(custbasVos[0].getPk_cubasdoc());
					}
				} else {
					rvos[i].setPay(baseVO.getMoneyy());
					rvos[i].setFundsflag(CmpConst.CASH_FLOW_OUT);
					rvos[i].setDirection(CmpConst.Direction_Pay);
					rvos[i].setPk_trader(headvo.getOutputunit());
				}
			}
			buildOppInfo(rvos[i]);
			fillCurrKeyValue(rvos[i]);
		}

		// getBankTallyService().writeBankacc(rvos);
		writeBankAcc(rvos, headvo.getWritebankacc());
	}

	/**
	 * ɾ�������ʻ��� �ڲ��й�VO to ������VO
	 * 
	 * @param vo
	 * @param pk_corp
	 * @return
	 * @throws BusinessException
	 */
	public void delCMPacc(HYBillVO billvo, String pk_corp, String operator,
			UFDate operatdate) throws BusinessException {

		StorageVO vo = (StorageVO) billvo.getParentVO();
		AccBusiVO rvo = new AccBusiVO();
		rvo.setPk_corp(pk_corp);
		rvo.setPk_sourcebill(vo.getPrimaryKey());
		rvo.setOperator(operator);// ��ǰ��½��
		rvo.setOperateDate(operatdate);// ��ǰ����
		getBankTallyService().deleteWhenHaveBill(rvo);

	}

}
