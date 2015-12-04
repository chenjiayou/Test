package nc.bs.fbm.reckon;

import nc.bs.fbm.pub.CommonDAO;
import nc.bs.fbm.pub.FBM2CMPAccSuper;
import nc.bs.framework.common.NCLocator;
import nc.itf.cm.prv.CmpConst;
import nc.itf.cmp.bankacc.AccBusiVO;
import nc.itf.cmp.bankacc.CMPaccStatus;
import nc.itf.uap.bd.cust.ICustManDocQuery;
import nc.vo.bd.b08.CustBasVO;
import nc.vo.cmp.settlement.SettlementBodyVO;
import nc.vo.fbm.pub.BaseinfoVO;
import nc.vo.fbm.pub.FBMPublicQry;
import nc.vo.fbm.reckon.ReckonBVO;
import nc.vo.fbm.reckon.ReckonVO;
import nc.vo.fi.pub.ProductInfo;
import nc.vo.pub.BusinessException;
import nc.vo.pub.lang.UFDate;
import nc.vo.trade.pub.HYBillVO;

public class ReckonUtil extends FBM2CMPAccSuper {

	public void addCMPBill(HYBillVO billvo, String tally_corp, String tallyman,
			UFDate tallydate) throws BusinessException {
		if (!isTogatherWithCMP(tally_corp)) {
			return;
		}
		ReckonVO headvo = (ReckonVO) billvo.getParentVO();
		ReckonBVO[] bodyvo = (ReckonBVO[]) billvo.getChildrenVO();

		SettlementBodyVO[] rvos = new SettlementBodyVO[bodyvo.length];
		CommonDAO commonDAO = new CommonDAO();
		BaseinfoVO baseVO = null;
		ICustManDocQuery cumanQry = (ICustManDocQuery) NCLocator.getInstance().lookup(ICustManDocQuery.class);
		String cent_corp = FBMPublicQry.retrivePk_centcorpByPkCorp(tally_corp);
		String strWhere = " bd_cubasdoc.pk_corp1 = '" + cent_corp + "' ";
		CustBasVO[] custbasVos = cumanQry.queryCustBasicInfo(strWhere, null);

		for (int i = 0; i < bodyvo.length; i++) {
			baseVO = commonDAO.queryBaseinfoByPK(bodyvo[i].getPk_baseinfo());
			rvos[i] = new SettlementBodyVO();
			rvos[i].setPk_corp(tally_corp);
			rvos[i].setSystemcode(ProductInfo.pro_FBM);
			rvos[i].setPk_bill(headvo.getPrimaryKey());
			rvos[i].setPk_currtype(baseVO.getPk_curr());
			rvos[i].setBillcode(headvo.getVbillno());
			rvos[i].setTallystatus(new Integer(
					CMPaccStatus.SUCCESSACCOUNT.getStatus()));
			rvos[i].setTallydate(headvo.getDapprovedate());
			rvos[i].setSigndate(headvo.getDapprovedate());
			rvos[i].setFundformcode(CmpConst.BILL_DEPOSIT);
			rvos[i].setTradertype(CmpConst.TradeObjType_KeShang);
			if (custbasVos != null && custbasVos.length > 0) {
				rvos[i].setPk_trader(custbasVos[0].getPk_cubasdoc());
			}

			buildOppInfo(rvos[i]);
			rvos[i].setPk_billtype(headvo.getPk_billtypecode());
			rvos[i].setBilldate(headvo.getDapprovedate());
			rvos[i].setPk_notetype(baseVO.getFbmbilltype());

			// 中心欠单位钱
			if (bodyvo[i].getMoneyy().doubleValue() > 0) {
				rvos[i].setReceive(baseVO.getMoneyy().multiply(-1));
				rvos[i].setFundsflag(CmpConst.CASH_FLOW_OUT);
				rvos[i].setDirection(CmpConst.Direction_Pay);
			} else {
				rvos[i].setReceive(baseVO.getMoneyy().multiply(-1));
				rvos[i].setFundsflag(CmpConst.CASH_FLOW_IN);
				rvos[i].setDirection(CmpConst.Direction_Receive);
			}
			rvos[i].setMemo(headvo.getNote());

			fillCurrKeyValue(rvos[i]);
		}
		// getBankTallyService().writeBankacc(rvos);
		writeBankAcc(rvos, headvo.getWritebankacc());
	}

	public void addCMPBank(HYBillVO billvo, String tally_corp, String tallyman,
			UFDate tallydate) throws BusinessException {
		if (!isTogatherWithCMP(tally_corp)) {
			return;
		}

		ReckonVO headvo = (ReckonVO) billvo.getParentVO();
		ReckonBVO[] bodyvo = (ReckonBVO[]) billvo.getChildrenVO();

		SettlementBodyVO[] rvos = new SettlementBodyVO[bodyvo.length];
		CommonDAO commonDAO = new CommonDAO();
		BaseinfoVO baseVO = null;
		ICustManDocQuery cumanQry = (ICustManDocQuery) NCLocator.getInstance().lookup(ICustManDocQuery.class);
		String cent_corp = FBMPublicQry.retrivePk_centcorpByPkCorp(tally_corp);
		String strWhere = " bd_cubasdoc.pk_corp1 = '" + cent_corp + "' ";
		CustBasVO[] custbasVos = cumanQry.queryCustBasicInfo(strWhere, null);

		for (int i = 0; i < bodyvo.length; i++) {
			baseVO = commonDAO.queryBaseinfoByPK(bodyvo[i].getPk_baseinfo());
			rvos[i] = new SettlementBodyVO();
			rvos[i].setPk_corp(tally_corp);
			rvos[i].setSystemcode(ProductInfo.pro_FBM);
			rvos[i].setPk_bill(headvo.getPrimaryKey());
			rvos[i].setPk_currtype(baseVO.getPk_curr());
			rvos[i].setBillcode(headvo.getVbillno());
			rvos[i].setTallystatus(new Integer(
					CMPaccStatus.SUCCESSACCOUNT.getStatus()));
			rvos[i].setTallydate(headvo.getDapprovedate());
			rvos[i].setSigndate(headvo.getDapprovedate());
			rvos[i].setFundformcode(CmpConst.BANK_DEPOSIT);
			rvos[i].setPk_billtype(headvo.getPk_billtypecode());
			rvos[i].setBilldate(headvo.getDapprovedate());

			// 中心欠单位钱
			if (bodyvo[i].getMoneyy().doubleValue() > 0) {
				rvos[i].setPk_account(headvo.getInacc());
				rvos[i].setReceive(baseVO.getMoneyy());
				rvos[i].setFundsflag(CmpConst.CASH_FLOW_IN);
				rvos[i].setDirection(CmpConst.Direction_Receive);
			} else {
				rvos[i].setPk_account(headvo.getInacc());
				rvos[i].setPay(baseVO.getMoneyy());
				rvos[i].setFundsflag(CmpConst.CASH_FLOW_OUT);
				rvos[i].setDirection(CmpConst.Direction_Pay);
			}
			rvos[i].setTradertype(CmpConst.TradeObjType_KeShang);
			if (custbasVos != null && custbasVos.length > 0) {
				rvos[i].setPk_trader(custbasVos[0].getPk_cubasdoc());
			}
			buildOppInfo(rvos[i]);
			rvos[i].setMemo(headvo.getNote());
			fillCurrKeyValue(rvos[i]);
		}

		// getBankTallyService().writeBankacc(rvos);
		writeBankAcc(rvos, headvo.getWritebankacc());
	}

	public void delCMPacc(HYBillVO billvo, String pk_corp, String operator,
			UFDate operatedate) throws BusinessException {
		ReckonVO vo = (ReckonVO) billvo.getParentVO();
		AccBusiVO rvo = new AccBusiVO();
		rvo.setPk_corp(pk_corp);
		rvo.setPk_sourcebill(vo.getPrimaryKey());
		rvo.setOperator(operator);// 当前登陆人
		rvo.setOperateDate(operatedate);// 当前日期

		getBankTallyService().deleteWhenHaveBill(rvo);
	}
}
