package nc.bs.fbm.gather;

import nc.bs.fbm.pub.CommonDAO;
import nc.bs.fbm.pub.FBM2CMPAccSuper;
import nc.itf.cm.prv.CmpConst;
import nc.itf.cmp.bankacc.AccBusiVO;
import nc.vo.cmp.settlement.SettlementBodyVO;
import nc.vo.fbm.pub.BaseinfoVO;
import nc.vo.fbm.register.RegisterVO;
import nc.vo.pub.BusinessException;
import nc.vo.pub.lang.UFDate;
import nc.vo.trade.pub.HYBillVO;

/**
 * 收票登记现金平台账
 * 
 * @author xwq
 * 
 */
public class GatherCmpAcc extends FBM2CMPAccSuper {

	public void delCMPacc(HYBillVO billvo, String pk_corp, String operator,
			UFDate operatedate) throws BusinessException {

		RegisterVO vo = (RegisterVO) billvo.getParentVO();
		AccBusiVO rvo = new AccBusiVO();
		rvo.setPk_corp(pk_corp);
		rvo.setPk_sourcebill(vo.getPrimaryKey());
		rvo.setOperator(operator);// 当前登陆人
		rvo.setOperateDate(operatedate);// 当前日期

		getBankTallyService().deleteWhenHaveBill(rvo);
	}

	/**
	 * 如果
	 * 
	 * @param billvo
	 * @param tally_corp
	 * @param tallyman
	 * @param tallydate
	 * @throws BusinessException
	 */
	public void addCMPBill(HYBillVO billvo, String tally_corp, String tallyman,
			UFDate tallydate) throws BusinessException {
		if (!isTogatherWithCMP(tally_corp)) {
			return;
		}

		RegisterVO headvo = (RegisterVO) billvo.getParentVO();
		SettlementBodyVO[] rvo = new SettlementBodyVO[1];
		initSettlementBodyVO(rvo, headvo);
		CommonDAO commonDAO = new CommonDAO();
		BaseinfoVO baseVO = commonDAO.queryBaseinfoByPK(headvo.getPk_baseinfo());
		rvo[0].setPk_corp(tally_corp);
		rvo[0].setPk_currtype(baseVO.getPk_curr());
		rvo[0].setTallydate(headvo.getGatherdate());
		rvo[0].setSigndate(headvo.getGatherdate());
		rvo[0].setFundformcode(CmpConst.BILL_DEPOSIT);
		rvo[0].setBilldate(headvo.getGatherdate());
		rvo[0].setReceive(baseVO.getMoneyy());
		rvo[0].setFundsflag(CmpConst.CASH_FLOW_IN);
		rvo[0].setDirection(CmpConst.Direction_Receive);
		rvo[0].setPk_notetype(baseVO.getFbmbilltype());
		rvo[0].setTradertype(CmpConst.TradeObjType_KeShang);
		rvo[0].setPk_trader(headvo.getPaybillunit());
		buildOppInfo(rvo[0]);

		rvo[0].setMemo(headvo.getNote());
		rvo[0].setFracrate(headvo.getFrate());// 辅币汇率
		rvo[0].setLocalrate(headvo.getBrate());// 本币汇率
		fillCurrKeyValue(rvo[0]);

		// getBankTallyService().writeBankacc(rvo);
		writeBankAcc(rvo, headvo.getWritebankacc());
	}
}
