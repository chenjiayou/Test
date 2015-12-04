package nc.bs.fbm.accept;

import java.util.ArrayList;
import java.util.List;

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
import nc.vo.fbm.acceptbill.AcceptVO;
import nc.vo.fbm.pub.BaseinfoVO;
import nc.vo.fbm.pub.FBMProxy;
import nc.vo.fbm.pub.constant.FbmBusConstant;
import nc.vo.fbm.register.RegisterVO;
import nc.vo.fi.pub.ProductInfo;
import nc.vo.pub.BusinessException;
import nc.vo.pub.lang.UFDate;
import nc.vo.pub.lang.UFDouble;
import nc.vo.trade.pub.HYBillVO;

public class AcceptUtil extends FBM2CMPAccSuper {

	public void addCMPBill(HYBillVO billvo, String tally_corp, String tallyman,
			UFDate tallydate) throws BusinessException {
		if (!isTogatherWithCMP(tally_corp)) {
			return;
		}

		AcceptVO headvo = (AcceptVO) billvo.getParentVO();
		SettlementBodyVO[] rvo = new SettlementBodyVO[1];
		initSettlementBodyVO(rvo, headvo);
		CommonDAO commonDAO = new CommonDAO();
		BaseinfoVO baseVO = commonDAO.queryBaseinfoByPK(headvo.getPk_baseinfo());
		rvo[0].setPk_corp(tally_corp);
		rvo[0].setPk_currtype(baseVO.getPk_curr());
		rvo[0].setTallydate(headvo.getDacceptdate());
		rvo[0].setSigndate(headvo.getDacceptdate());
		rvo[0].setFundformcode(CmpConst.BILL_DEPOSIT);
		rvo[0].setBilldate(headvo.getDacceptdate());
		rvo[0].setReceive(baseVO.getMoneyy());
		rvo[0].setFundsflag(CmpConst.CASH_FLOW_IN);
		rvo[0].setDirection(CmpConst.Direction_Receive);
		rvo[0].setPk_notetype(baseVO.getFbmbilltype());
		rvo[0].setTradertype(CmpConst.TradeObjType_Bank);
		rvo[0].setPk_trader(baseVO.getPaybank());
		buildOppInfo(rvo[0]);

		rvo[0].setMemo(headvo.getNote());
		rvo[0].setFracrate(headvo.getFrate());// 辅币汇率
		rvo[0].setLocalrate(headvo.getBrate());// 本币汇率
		fillCurrKeyValue(rvo[0]);

		// getBankTallyService().writeBankacc(rvo);
		writeBankAcc(rvo, headvo.getWritebankacc());

	}

	public void addCMPBank(HYBillVO billvo, String tally_corp, String tallyman,
			UFDate tallydate) throws BusinessException {
		if (!isTogatherWithCMP(tally_corp)) {
			return;
		}

		List<SettlementBodyVO> list = new ArrayList<SettlementBodyVO>();

		AcceptVO headvo = (AcceptVO) billvo.getParentVO();
		SettlementBodyVO rvo = new SettlementBodyVO();
		CommonDAO commonDAO = new CommonDAO();
		BaseinfoVO baseVO = commonDAO.queryBaseinfoByPK(headvo.getPk_baseinfo());
		rvo.setPk_corp(tally_corp);
		rvo.setSystemcode(ProductInfo.pro_FBM);
		rvo.setPk_bill(headvo.getPrimaryKey());
		rvo.setPk_currtype(baseVO.getPk_curr());
		rvo.setBillcode(headvo.getVbillno());
		rvo.setTallystatus(new Integer(CMPaccStatus.SUCCESSACCOUNT.getStatus()));
		rvo.setTallydate(headvo.getDacceptdate());
		rvo.setSigndate(headvo.getDacceptdate());
		rvo.setFundformcode(CmpConst.BANK_DEPOSIT);
		// rvo.setTradertype(CmpConst.INNER_TRADE);
		rvo.setPk_billtype(headvo.getPk_billtypecode());
		rvo.setBilldate(headvo.getDacceptdate());
		// rvo.setPay(baseVO.getMoneyy());
		// rvo.setFundsflag(CmpConst.CASH_FLOW_OUT);
		// rvo.setDirection(CmpConst.Direction_Pay);
		String paybankacc = baseVO.getPaybankacc();
		rvo.setPk_account(paybankacc);
		rvo.setTradertype(CmpConst.TradeObjType_KeShang);
		rvo.setPk_trader(baseVO.getReceiveunit());

		CustBasVO custBasVo = null;
		custBasVo = (CustBasVO) new BaseDAO().retrieveByPK(CustBasVO.class, new CustBasMapping(), baseVO.getReceiveunit());
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
		rvo.setPk_oppaccount(baseVO.getReceivebankacc());
		buildOppInfo(rvo);

		rvo.setFracrate(headvo.getFrate());// 辅币汇率
		rvo.setLocalrate(headvo.getBrate());// 本币汇率
		rvo.setMemo(headvo.getNote());

		// 付款银行账户流出票面金额.
		SettlementBodyVO rvo0 = (SettlementBodyVO) rvo.clone();
		rvo0.setPk_account(paybankacc);
		rvo0.setPay(baseVO.getMoneyy());
		rvo0.setReceive(null);
		rvo0.setFundsflag(CmpConst.CASH_FLOW_OUT);
		rvo0.setDirection(CmpConst.Direction_Pay);
		rvo0.setFracrate(headvo.getFrate());// 辅币汇率
		rvo0.setLocalrate(headvo.getBrate());// 本币汇率
		fillCurrKeyValue(rvo0);
		list.add(rvo0);

		RegisterVO invoiceVO = (RegisterVO) FBMProxy.getUifService().queryByPrimaryKey(RegisterVO.class, headvo.getPk_source());
		if (invoiceVO.getImpawnmode().equals(FbmBusConstant.ASSURETYPE_BAIL)) {
			UFDouble secmoney = headvo.getSecuritymoney();
			if (secmoney == null) {
				throw new BusinessException(
						nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcode112", "UPPFBMCODE112-000024")/*
																													 * @res
																													 * "保证金金额不能为空！"
																													 */);
			}
			UFDouble backsecmoney = headvo.getBacksecmoney();
			if (backsecmoney == null) {
				backsecmoney = new UFDouble(0);
			}

			// 付款写账：
			// 如果返回保证金小于保证金有如下四笔账，
			// 1：保证金账户，流出，金额＝返回保证金
			// 2：返回保证金账户：流入，金额＝返回保证金。
			// 3：保证金账户：流出，金额＝保证金－返回保证金
			// 4：付款银行账户：流入，金额＝保证金－返回保证金
			// 否则写二笔账户，
			// 1：保证金账户，流出，金额＝返回保证金
			// 2：返回保证金账户，流入，金额＝返回保证金
			// 如果返回保证金金额为空：
			// 1. 保证金账户：流出，金额＝保证金
			// 2. 付款银行账户：流入，金额＝保证金
			if (backsecmoney == null || backsecmoney.doubleValue() <= 0) {
				// 保证金账户 流出，金额＝保证金
				SettlementBodyVO rvo3 = (SettlementBodyVO) rvo.clone();
				rvo3.setPk_account(headvo.getSecurityaccount());
				rvo3.setPay(secmoney);
				rvo3.setReceive(null);
				rvo3.setFundsflag(CmpConst.CASH_FLOW_OUT);
				rvo3.setDirection(CmpConst.Direction_Pay);
				rvo3.setFracrate(headvo.getFrate());// 辅币汇率
				rvo3.setLocalrate(headvo.getBrate());// 本币汇率
				fillCurrKeyValue(rvo3);
				list.add(rvo3);

				// 付款银行账户 流入，金额＝保证金
				SettlementBodyVO rvo4 = (SettlementBodyVO) rvo.clone();
				rvo4.setPk_account(paybankacc);
				rvo4.setReceive(secmoney);
				rvo4.setPay(null);
				rvo4.setFundsflag(CmpConst.CASH_FLOW_IN);
				rvo4.setDirection(CmpConst.Direction_Receive);
				rvo4.setFracrate(headvo.getFrate());// 辅币汇率
				rvo4.setLocalrate(headvo.getBrate());// 本币汇率
				fillCurrKeyValue(rvo4);
				list.add(rvo4);
			} else if (backsecmoney.doubleValue() != 0
					&& backsecmoney.sub(secmoney).doubleValue() < 0) {
				// 保证金账户 流出，金额＝返回保证金
				SettlementBodyVO rvo1 = (SettlementBodyVO) rvo.clone();
				rvo1.setPk_account(headvo.getSecurityaccount());
				// rvo1.setPk_bill(headvo.getSecurityaccount());
				rvo1.setPay(backsecmoney);
				rvo1.setReceive(null);
				rvo1.setFundsflag(CmpConst.CASH_FLOW_OUT);
				rvo1.setDirection(CmpConst.Direction_Pay);
				rvo1.setFracrate(headvo.getFrate());// 辅币汇率
				rvo1.setLocalrate(headvo.getBrate());// 本币汇率
				fillCurrKeyValue(rvo1);
				list.add(rvo1);
				// 返回保证金账户 流入，金额＝返回保证金。
				SettlementBodyVO rvo2 = (SettlementBodyVO) rvo.clone();
				rvo2.setPk_account(headvo.getBacksecaccount());
				// rvo2.setPk_bill(headvo.getBacksecaccount());
				rvo2.setReceive(backsecmoney);
				rvo2.setPay(null);
				rvo2.setFundsflag(CmpConst.CASH_FLOW_IN);
				rvo2.setDirection(CmpConst.Direction_Receive);
				rvo2.setFracrate(headvo.getFrate());// 辅币汇率
				rvo2.setLocalrate(headvo.getBrate());// 本币汇率
				fillCurrKeyValue(rvo2);
				list.add(rvo2);
				// 保证金账户 流出，金额＝保证金－返回保证金
				SettlementBodyVO rvo3 = (SettlementBodyVO) rvo.clone();
				rvo3.setPk_account(headvo.getSecurityaccount());
				// rvo3.setPk_bill(headvo.getSecurityaccount());
				rvo3.setPay(secmoney.sub(backsecmoney));
				rvo3.setReceive(null);
				rvo3.setFundsflag(CmpConst.CASH_FLOW_OUT);
				rvo3.setDirection(CmpConst.Direction_Pay);
				rvo3.setFracrate(headvo.getFrate());// 辅币汇率
				rvo3.setLocalrate(headvo.getBrate());// 本币汇率
				fillCurrKeyValue(rvo3);
				list.add(rvo3);
				// 付款银行账户 流入，金额＝保证金－返回保证金
				SettlementBodyVO rvo4 = (SettlementBodyVO) rvo.clone();
				rvo4.setPk_account(paybankacc);
				rvo4.setReceive(secmoney.sub(backsecmoney));
				rvo4.setPay(null);
				rvo4.setFundsflag(CmpConst.CASH_FLOW_IN);
				rvo4.setDirection(CmpConst.Direction_Receive);
				rvo4.setFracrate(headvo.getFrate());// 辅币汇率
				rvo4.setLocalrate(headvo.getBrate());// 本币汇率
				fillCurrKeyValue(rvo4);
				list.add(rvo4);
			} else {
				// 保证金账户 流出，金额＝返回保证金
				SettlementBodyVO rvo1 = (SettlementBodyVO) rvo.clone();
				rvo1.setPk_account(headvo.getSecurityaccount());
				// rvo1.setPk_bill(headvo.getSecurityaccount());
				rvo1.setPay(backsecmoney);
				rvo1.setReceive(null);
				rvo1.setFundsflag(CmpConst.CASH_FLOW_OUT);
				rvo1.setDirection(CmpConst.Direction_Pay);
				rvo1.setFracrate(headvo.getFrate());// 辅币汇率
				rvo1.setLocalrate(headvo.getBrate());// 本币汇率
				fillCurrKeyValue(rvo1);
				list.add(rvo1);
				// 返回保证金账户 流入，金额＝返回保证金
				if (!CommonUtil.isNull(headvo.getBacksecaccount())
						&& headvo.getBacksecmoney() != null
						&& headvo.getBacksecmoney().doubleValue() > 0) {
					SettlementBodyVO rvo2 = (SettlementBodyVO) rvo.clone();
					rvo2.setPk_account(headvo.getBacksecaccount());
					// rvo2.setPk_bill(headvo.getBacksecaccount());
					rvo2.setReceive(backsecmoney);
					rvo2.setPay(null);
					rvo2.setFundsflag(CmpConst.CASH_FLOW_IN);
					rvo2.setDirection(CmpConst.Direction_Receive);
					rvo2.setFracrate(headvo.getFrate());// 辅币汇率
					rvo2.setLocalrate(headvo.getBrate());// 本币汇率
					fillCurrKeyValue(rvo2);
					list.add(rvo2);
				}
			}
		}
		SettlementBodyVO[] rvos = new SettlementBodyVO[list.size()];
		rvos = list.toArray(new SettlementBodyVO[] {});

		// getBankTallyService().writeBankacc(rvos);
		writeBankAcc(rvos, headvo.getWritebankacc());
	}

	public void delCMPacc(HYBillVO billvo, String pk_corp, String operator,
			UFDate operatedate) throws BusinessException {

		AcceptVO vo = (AcceptVO) billvo.getParentVO();
		AccBusiVO rvo = new AccBusiVO();
		rvo.setPk_corp(pk_corp);
		rvo.setOperator(operator);// 当前登陆人
		rvo.setOperateDate(operatedate);// 当前日期

		rvo.setPk_sourcebill(vo.getPrimaryKey());
		getBankTallyService().deleteWhenHaveBill(rvo);

	}

}