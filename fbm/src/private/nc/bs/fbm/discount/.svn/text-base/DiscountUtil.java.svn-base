package nc.bs.fbm.discount;

import nc.bs.fbm.pub.CommonDAO;
import nc.bs.fbm.pub.FBM2CMPAccSuper;
import nc.itf.cm.prv.CmpConst;
import nc.itf.cmp.bankacc.AccBusiVO;
import nc.itf.cmp.bankacc.CMPaccStatus;
import nc.vo.cmp.settlement.SettlementBodyVO;
import nc.vo.fbm.discount.DiscountVO;
import nc.vo.fbm.pub.BaseinfoVO;
import nc.vo.fi.pub.ProductInfo;
import nc.vo.pub.BusinessException;
import nc.vo.pub.lang.UFDate;
import nc.vo.trade.pub.HYBillVO;

public class DiscountUtil extends FBM2CMPAccSuper {
	public void addCMPBill(HYBillVO billvo, String tally_corp, String tallyman,
			UFDate tallydate) throws BusinessException {
		if (!isTogatherWithCMP(tally_corp)) {
			return;
		}
		DiscountVO headvo = (DiscountVO) billvo.getParentVO();

		SettlementBodyVO rvo = new SettlementBodyVO();
		CommonDAO commonDAO = new CommonDAO();
		BaseinfoVO baseVO = commonDAO.queryBaseinfoByPK(headvo.getPk_baseinfo());
		rvo.setPk_corp(tally_corp);
		rvo.setSystemcode(ProductInfo.pro_FBM);
		rvo.setPk_bill(headvo.getPrimaryKey());
		rvo.setPk_currtype(baseVO.getPk_curr());
		rvo.setBillcode(headvo.getVbillno());
		rvo.setTallystatus(new Integer(CMPaccStatus.SUCCESSACCOUNT.getStatus()));
		rvo.setTallydate(headvo.getDdiscountdate());
		rvo.setSigndate(headvo.getDdiscountdate());
		rvo.setFundformcode(CmpConst.BILL_DEPOSIT);
		rvo.setPk_billtype(headvo.getPk_billtypecode());
		rvo.setBilldate(headvo.getDdiscountdate());
		rvo.setPay(baseVO.getMoneyy());
		rvo.setFundsflag(CmpConst.CASH_FLOW_OUT);
		rvo.setDirection(CmpConst.Direction_Pay);
		rvo.setTradertype(CmpConst.TradeObjType_Bank);
		String accountPk = headvo.getDiscount_account();
		String bankPk = queryBankDocPkByBankaccPk(accountPk);
		rvo.setPk_trader(bankPk);
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
		DiscountVO headvo = (DiscountVO) billvo.getParentVO();

		SettlementBodyVO rvo = new SettlementBodyVO();
		CommonDAO commonDAO = new CommonDAO();
		BaseinfoVO baseVO = commonDAO.queryBaseinfoByPK(headvo.getPk_baseinfo());
		rvo.setPk_corp(tally_corp);
		rvo.setSystemcode(ProductInfo.pro_FBM);
		rvo.setPk_bill(headvo.getPrimaryKey());
		rvo.setPk_currtype(baseVO.getPk_curr());
		rvo.setBillcode(headvo.getVbillno());
		rvo.setTallystatus(new Integer(CMPaccStatus.SUCCESSACCOUNT.getStatus()));
		rvo.setTallydate(headvo.getDdiscountdate());
		rvo.setSigndate(headvo.getDdiscountdate());
		rvo.setFundformcode(CmpConst.BANK_DEPOSIT);
		rvo.setPk_billtype(headvo.getPk_billtypecode());
		rvo.setBilldate(headvo.getDdiscountdate());
		rvo.setReceive(headvo.getMoneyy());
		rvo.setFundsflag(CmpConst.CASH_FLOW_IN);
		rvo.setDirection(CmpConst.Direction_Receive);
		rvo.setPk_account(headvo.getDiscount_account());
		rvo.setTradertype(CmpConst.TradeObjType_Bank);
		String accountPk = headvo.getDiscount_account();
		String bankPk = queryBankDocPkByBankaccPk(accountPk);
		rvo.setPk_trader(bankPk);
		rvo.setMemo(headvo.getNote());
		rvo.setFracrate(headvo.getFrate());// 辅币汇率
		rvo.setLocalrate(headvo.getBrate());// 本币汇率
		fillCurrKeyValue(rvo);

		// getBankTallyService().writeBankacc(new SettlementBodyVO[] { rvo });
		writeBankAcc(new SettlementBodyVO[] { rvo }, headvo.getWritebankacc());
	}

	public void delCMPacc(HYBillVO billvo, String pk_corp, String operator,
			UFDate operatedate) throws BusinessException {

		DiscountVO vo = (DiscountVO) billvo.getParentVO();
		AccBusiVO rvo = new AccBusiVO();
		rvo.setPk_corp(pk_corp);
		rvo.setPk_sourcebill(vo.getPrimaryKey());
		rvo.setOperator(operator);// 当前登陆人
		rvo.setOperateDate(operatedate);// 当前日期

		getBankTallyService().deleteWhenHaveBill(rvo);
	}
}
