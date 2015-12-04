package nc.bs.fbm.consignbank;

import nc.bs.dao.BaseDAO;
import nc.bs.fbm.pub.CommonDAO;
import nc.bs.fbm.pub.FBM2CMPAccSuper;
import nc.itf.cdm.util.CommonUtil;
import nc.itf.cm.prv.CmpConst;
import nc.itf.cmp.bankacc.AccBusiVO;
import nc.itf.cmp.bankacc.CMPaccStatus;
import nc.vo.bd.b08.CustBasMapping;
import nc.vo.bd.b08.CustBasVO;
import nc.vo.cmp.settlement.SettlementBodyVO;
import nc.vo.fbm.consignbank.CollectionVO;
import nc.vo.fbm.pub.BaseinfoVO;
import nc.vo.fi.pub.ProductInfo;
import nc.vo.pub.BusinessException;
import nc.vo.pub.lang.UFDate;
import nc.vo.trade.pub.HYBillVO;

public class ConsignBankUtil extends FBM2CMPAccSuper {

	public void addCMPBill(HYBillVO billvo, String tally_corp, String tallyman,
			UFDate tallydate) throws BusinessException {
		if (!isTogatherWithCMP(tally_corp)) {
			return;
		}
		CollectionVO headvo = (CollectionVO) billvo.getParentVO();

		SettlementBodyVO rvo = new SettlementBodyVO();
		CommonDAO commonDAO = new CommonDAO();
		BaseinfoVO baseVO = commonDAO.queryBaseinfoByPK(headvo.getPk_baseinfo());
		rvo.setPk_corp(tally_corp);
		rvo.setSystemcode(ProductInfo.pro_FBM);
		rvo.setPk_bill(headvo.getPrimaryKey());
		rvo.setPk_currtype(baseVO.getPk_curr());
		rvo.setBillcode(headvo.getVbillno());
		rvo.setTallystatus(new Integer(CMPaccStatus.SUCCESSACCOUNT.getStatus()));
		rvo.setTallydate(headvo.getDcollectiondate());
		rvo.setSigndate(headvo.getDcollectiondate());
		rvo.setFundformcode(CmpConst.BILL_DEPOSIT);

		rvo.setPk_billtype(headvo.getPk_billtypecode());
		rvo.setBilldate(headvo.getDcollectiondate());
		rvo.setPay(baseVO.getMoneyy());
		rvo.setFundsflag(CmpConst.CASH_FLOW_OUT);
		rvo.setDirection(CmpConst.Direction_Pay);
		rvo.setTradertype(CmpConst.TradeObjType_KeShang);
		rvo.setPk_trader(baseVO.getPayunit());
		rvo.setPk_oppaccount(baseVO.getPaybankacc());
		buildOppInfo(rvo);

		rvo.setMemo(headvo.getNote());
		rvo.setPk_notetype(baseVO.getFbmbilltype());
		rvo.setFracrate(headvo.getFrate());// 辅币汇率
		rvo.setLocalrate(headvo.getBrate());// 本币汇率
		fillCurrKeyValue(rvo);

		// getBankTallyService().writeBankacc(new SettlementBodyVO[]{rvo});
		writeBankAcc(new SettlementBodyVO[] { rvo }, headvo.getWritebankacc());
	}

	public void addCMPBank(HYBillVO billvo, String tally_corp, String tallyman,
			UFDate tallydate) throws BusinessException {
		if (!isTogatherWithCMP(tally_corp)) {
			return;
		}
		CollectionVO headvo = (CollectionVO) billvo.getParentVO();

		SettlementBodyVO rvo = new SettlementBodyVO();
		CommonDAO commonDAO = new CommonDAO();
		BaseinfoVO baseVO = commonDAO.queryBaseinfoByPK(headvo.getPk_baseinfo());
		rvo.setPk_corp(tally_corp);
		rvo.setSystemcode(ProductInfo.pro_FBM);
		rvo.setPk_bill(headvo.getPrimaryKey());
		rvo.setPk_currtype(baseVO.getPk_curr());
		rvo.setBillcode(headvo.getVbillno());
		rvo.setTallystatus(new Integer(CMPaccStatus.SUCCESSACCOUNT.getStatus()));
		rvo.setTallydate(headvo.getDcollectiondate());
		rvo.setSigndate(headvo.getDcollectiondate());
		rvo.setFundformcode(CmpConst.BANK_DEPOSIT);
		rvo.setPk_billtype(headvo.getPk_billtypecode());
		rvo.setBilldate(headvo.getDcollectiondate());
		rvo.setReceive(baseVO.getMoneyy());
		rvo.setFundsflag(CmpConst.CASH_FLOW_IN);
		rvo.setDirection(CmpConst.Direction_Receive);
		rvo.setPk_account(headvo.getHolderacc());
		rvo.setTradertype(CmpConst.TradeObjType_KeShang);

		rvo.setPk_trader(baseVO.getPayunit());

		CustBasVO custBasVo = null;
		custBasVo = (CustBasVO) new BaseDAO().retrieveByPK(CustBasVO.class, new CustBasMapping(), baseVO.getPayunit());
		if (custBasVo != null) {
			Integer custProp = custBasVo.getCustprop();
			if (custProp.intValue() == 0) {
				rvo.setTranstype(1); // 根据 交易对方客户类型来判断交易类型，0为内部交易，1为外部交易。
			} else {
				rvo.setTranstype(0);
			}
		} else {
			rvo.setTradertype(1);
		}

		rvo.setPk_oppaccount(baseVO.getPaybankacc());
		buildOppInfo(rvo);

		String paybankaccPk = baseVO.getPaybankacc();
		paybankaccPk = queryBankaccbyPk(paybankaccPk);
		if (!CommonUtil.isNull(paybankaccPk)) {
			rvo.setPk_oppaccount(paybankaccPk);
		}
		rvo.setMemo(headvo.getNote());
		rvo.setFracrate(headvo.getFrate());// 辅币汇率
		rvo.setLocalrate(headvo.getBrate());// 本币汇率
		fillCurrKeyValue(rvo);

		// getBankTallyService().writeBankacc(new SettlementBodyVO[] { rvo });
		writeBankAcc(new SettlementBodyVO[] { rvo }, headvo.getWritebankacc());
	}

	/**
	 * 删除银行帐户账 内部托管VO to 银行账VO
	 * 
	 * @param vo
	 * @param pk_corp
	 * @return
	 * @throws BusinessException
	 */
	public void delCMPacc(HYBillVO billvo, String pk_corp, String operator,
			UFDate operatedate) throws BusinessException {

		CollectionVO vo = (CollectionVO) billvo.getParentVO();
		AccBusiVO rvo = new AccBusiVO();
		rvo.setPk_corp(pk_corp);
		rvo.setPk_sourcebill(vo.getPrimaryKey());
		rvo.setOperator(operator);// 当前登陆人
		rvo.setOperateDate(operatedate);// 当前日期

		getBankTallyService().deleteWhenHaveBill(rvo);
	}

}
