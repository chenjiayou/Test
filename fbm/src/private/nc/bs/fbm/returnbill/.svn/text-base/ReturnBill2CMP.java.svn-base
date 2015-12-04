package nc.bs.fbm.returnbill;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import nc.bs.dao.BaseDAO;
import nc.bs.fbm.pub.CommonDAO;
import nc.bs.fbm.pub.FBM2CMPAccSuper;
import nc.bs.framework.common.NCLocator;
import nc.itf.cm.prv.CmpConst;
import nc.itf.cmp.bankacc.AccBusiVO;
import nc.itf.cmp.bankacc.CMPaccStatus;
import nc.itf.fbm.pub.IFbm2CmpAccSettleOrg;
import nc.itf.uap.bd.cust.ICustManDocQuery;
import nc.itf.uif.pub.IUifService;
import nc.vo.bd.b08.CustBasVO;
import nc.vo.cmp.settlement.SettlementBodyVO;
import nc.vo.fbm.outer.OuterVO;
import nc.vo.fbm.pub.BaseinfoVO;
import nc.vo.fbm.pub.FBMActionQuery;
import nc.vo.fbm.pub.FBMProxy;
import nc.vo.fbm.pub.FBMPublicQry;
import nc.vo.fbm.pub.constant.FbmBusConstant;
import nc.vo.fbm.register.RegisterVO;
import nc.vo.fbm.returnbill.ReturnBVO;
import nc.vo.fbm.returnbill.ReturnVO;
import nc.vo.fbm.storage.StorageVO;
import nc.vo.fi.pub.ProductInfo;
import nc.vo.pub.BusinessException;
import nc.vo.pub.lang.UFDate;
import nc.vo.tam.account.IAccConst;
import nc.vo.tam.account.accid.AccidVO;
import nc.vo.trade.pub.HYBillVO;

/**
 * 退票写银行帐户帐
 * 
 * @author xwq
 * 
 *         2008-11-10
 */
public class ReturnBill2CMP extends FBM2CMPAccSuper implements
		IFbm2CmpAccSettleOrg {

	public void insertBankAcc4Center(HYBillVO billvo, String tallycorp,
			String tallyman, UFDate tallydate) throws BusinessException {
		if (!isTogatherWithCMP(tallycorp)) {
			return;
		}
		// 维护中心票据账
		addCMPBill(billvo, tallycorp, tallydate, tallyman);
	}

	public void insertBankAcc4Unit(HYBillVO billvo, String tallycorp,
			String tallyman, UFDate tallydate) throws BusinessException {
		// TODO Auto-generated method stub
		addCMPBill(billvo, tallycorp, tallydate, tallyman);
		addCMPBank(billvo, tallycorp, tallydate, tallyman);
	}

	public void addCMPBill(HYBillVO billvo, String pk_corp, UFDate operatedate,
			String operator) throws BusinessException {
		if (!isTogatherWithCMP(pk_corp)) {
			return;
		}
		ReturnVO headvo = (ReturnVO) billvo.getParentVO();
		ReturnBVO[] childvos = (ReturnBVO[]) billvo.getChildrenVO();

		SettlementBodyVO[] rvo = new SettlementBodyVO[childvos.length];

		CommonDAO commonDAO = new CommonDAO();

		String returntype = headvo.getReturntype();
		ICustManDocQuery cumanQry = (ICustManDocQuery) NCLocator.getInstance().lookup(ICustManDocQuery.class);
		for (int i = 0; i < childvos.length; i++) {
			rvo[i] = new SettlementBodyVO();
			rvo[i].setPk_corp(pk_corp);
			rvo[i].setSystemcode(ProductInfo.pro_FBM);// 来源系统
			rvo[i].setPk_bill(headvo.getPrimaryKey());// 业务单据主键
			String pk_baseinfo = childvos[i].getPk_baseinfo();
			BaseinfoVO basevo = commonDAO.queryBaseinfoByPK(pk_baseinfo);
			rvo[i].setPk_currtype(basevo.getPk_curr());
			rvo[i].setBillcode(headvo.getVbillno());// 单据编号
			rvo[i].setBilldate(headvo.getDreturndate());// 业务日期取实际放款日期
			rvo[i].setTallystatus(new Integer(
					CMPaccStatus.SUCCESSACCOUNT.getStatus()));// 记账状态,设置为结算成功日期
			rvo[i].setTallydate(headvo.getDreturndate());
			rvo[i].setSigndate(headvo.getDreturndate());

			rvo[i].setFundformcode(CmpConst.BILL_DEPOSIT);

			rvo[i].setTradertype(CmpConst.TradeObjType_KeShang);// 对方类型为客商

			if (returntype.equals(FbmBusConstant.BACK_TYPE_UNISTORAGE_UNIT)) {
				rvo[i].setFundsflag(CmpConst.CASH_FLOW_IN);// 资金流向(0 资金流入1 资金流出2
				// 转账出)
				rvo[i].setDirection(CmpConst.Direction_Receive);
				rvo[i].setReceive(basevo.getMoneyy());
				// 对方是中心客商,则找当前公司对应的中心
				String cent_corp = FBMPublicQry.retrivePk_centcorpByPkCorp(pk_corp);
				String strWhere = " bd_cubasdoc.pk_corp1 = '"
						+ cent_corp
						+ "' ";
				CustBasVO[] custbasVos = cumanQry.queryCustBasicInfo(strWhere, pk_corp);
				if (custbasVos != null && custbasVos.length > 0) {
					rvo[i].setPk_trader(custbasVos[0].getPk_cubasdoc());
				}
			} else {
				rvo[i].setFundsflag(CmpConst.CASH_FLOW_OUT);// 资金流向(0 资金流入1
				// 资金流出2 转账出)
				rvo[i].setDirection(CmpConst.Direction_Pay);
				rvo[i].setPay(basevo.getMoneyy());

				// 对方是单位客商
				RegisterVO tmpVO = (RegisterVO) commonDAO.getBaseDAO().retrieveByPK(RegisterVO.class, childvos[i].getPk_source());
				if (tmpVO != null) {
					rvo[i].setPk_trader(tmpVO.getPaybillunit());// 取收票登记上的付票单位即可
				}
			}

			buildOppInfo(rvo[i]);

			rvo[i].setPk_billtype(headvo.getPk_billtypecode());
			rvo[i].setPk_notetype(basevo.getFbmbilltype());
			fillCurrKeyValue(rvo[i]);
		}
		// getBankTallyService().writeBankacc(rvo);
		writeBankAcc(rvo, headvo.getWritebankacc());

	}

	public void addCMPBank(HYBillVO billvo, String pk_corp, UFDate operatedate,
			String operator) throws BusinessException {
		if (!isTogatherWithCMP(pk_corp)) {
			return;
		}
		ReturnVO headvo = (ReturnVO) billvo.getParentVO();
		ReturnBVO[] childvos = (ReturnBVO[]) billvo.getChildrenVO();
		ArrayList<SettlementBodyVO> list = new ArrayList<SettlementBodyVO>();

		CommonDAO commonDAO = new CommonDAO();
		IUifService srv = FBMProxy.getUifService();

		String returntype = headvo.getReturntype();

		for (int i = 0; i < childvos.length; i++) {
			SettlementBodyVO rvo = new SettlementBodyVO();
			rvo.setPk_corp(pk_corp);
			rvo.setSystemcode(ProductInfo.pro_FBM);// 来源系统
			rvo.setPk_bill(headvo.getPrimaryKey());// 业务单据主键
			String pk_baseinfo = childvos[i].getPk_baseinfo();
			BaseinfoVO basevo = commonDAO.queryBaseinfoByPK(pk_baseinfo);
			rvo.setPk_currtype(basevo.getPk_curr());
			rvo.setBillcode(headvo.getVbillno());// 单据编号
			rvo.setBilldate(headvo.getDreturndate());// 业务日期取实际放款日期
			rvo.setTallystatus(new Integer(
					CMPaccStatus.SUCCESSACCOUNT.getStatus()));// 记账状态,设置为结算成功日期
			rvo.setTallydate(headvo.getDreturndate());
			rvo.setSigndate(headvo.getDreturndate());
			rvo.setFundformcode(CmpConst.BANK_DEPOSIT);// 银行存款

			if (returntype.equals(FbmBusConstant.BACK_TYPE_UNISTORAGE_UNIT)) {
				rvo.setFundsflag(CmpConst.CASH_FLOW_OUT);// 资金流向(0 资金流入1 资金流出2
				// 转账出)
				rvo.setDirection(CmpConst.Direction_Pay);
				rvo.setPay(basevo.getMoneyy());
			} else {
				rvo.setFundsflag(CmpConst.CASH_FLOW_IN);// 资金流向(0 资金流入1 资金流出2
				// 转账出)
				rvo.setDirection(CmpConst.Direction_Receive);
				rvo.setReceive(basevo.getMoneyy());
			}
			HYBillVO tempBillVO = FBMActionQuery.getInstance().queryLastBillTypeBillVO(pk_baseinfo, FbmBusConstant.BILLTYPE_INNERKEEP);
			String selfAcc = ((StorageVO) tempBillVO.getParentVO()).getKeepaccount();

			// 如果是票据户，则不记账
			AccidVO accidvo = (AccidVO) srv.queryByPrimaryKey(AccidVO.class, selfAcc);
			if (accidvo.getAcctype() == IAccConst.ACCL_BILL) {
				continue;
			}

			// rvo.setReceive(basevo.getMoneyy());
			rvo.setPk_account(selfAcc); // 本方帐

			rvo.setTradertype(CmpConst.TradeObjType_Bank);// 对方类型为银行

			rvo.setTradertype(1);// 0 内部交易 1 外部交易
			rvo.setPk_billtype(headvo.getPk_billtypecode());
			fillCurrKeyValue(rvo);

			list.add(rvo);
		}
		if (list.size() > 0) {
			// getBankTallyService().writeBankacc(list.toArray(new
			// SettlementBodyVO[0]));
			writeBankAcc(list.toArray(new SettlementBodyVO[0]), headvo.getWritebankacc());
		}
	}

	public void delCMPacc(HYBillVO billvo, String pk_corp, String operator,
			UFDate operatedate) throws BusinessException {

		ReturnVO vo = (ReturnVO) billvo.getParentVO();
		AccBusiVO rvo = new AccBusiVO();
		rvo.setPk_corp(pk_corp);
		rvo.setPk_sourcebill(vo.getPrimaryKey());
		rvo.setOperator(operator);// 当前登陆人
		rvo.setOperateDate(operatedate);// 当前日期

		getBankTallyService().deleteWhenHaveBill(rvo);
	}

	public void addNegativeBillCMP(HYBillVO billvo, String tally_corp,
			String tallyman, UFDate tallydate) throws BusinessException {

		if (!isTogatherWithCMP(tally_corp)) {
			return;
		}

		List<SettlementBodyVO> rvolist = new ArrayList<SettlementBodyVO>();

		SettlementBodyVO[] rvo = null;
		ReturnVO headvo = (ReturnVO) billvo.getParentVO();
		ReturnBVO[] bvos = (ReturnBVO[]) billvo.getChildrenVO();
		rvo = new SettlementBodyVO[bvos.length];
		initSettlementBodyVO(rvo, headvo);

		for (int i = 0; i < bvos.length; i++) {
			CommonDAO commonDAO = new CommonDAO();
			BaseDAO dao = new BaseDAO();
			RegisterVO regvo = (RegisterVO) dao.retrieveByPK(RegisterVO.class, bvos[i].getPk_source());
			BaseinfoVO baseVO = commonDAO.queryBaseinfoByPK(regvo.getPk_baseinfo());
			// 判断是否有接口单据引用，如果有接口单据引用就不再冲票据日记账
			Collection col = dao.retrieveByClause(OuterVO.class, "pk_register='"
					+ bvos[i].getPk_source()
					+ "'");
			if (col != null && col.size() > 0) {
				continue;
			}
			if (regvo.getSfflag().booleanValue()) {

				rvo[i].setPk_corp(tally_corp);
				rvo[i].setPk_currtype(baseVO.getPk_curr());
				rvo[i].setTallydate(tallydate);
				rvo[i].setSigndate(tallydate);

				rvo[i].setFundformcode(CmpConst.BILL_DEPOSIT);
				rvo[i].setBilldate(tallydate);
				rvo[i].setPay(baseVO.getMoneyy().multiply(-1));
				rvo[i].setFundsflag(CmpConst.CASH_FLOW_OUT);
				rvo[i].setDirection(CmpConst.Direction_Pay);
				rvo[i].setPk_notetype(baseVO.getFbmbilltype());
				rvo[i].setTradertype(CmpConst.TradeObjType_KeShang);// 对方为客商

				rvo[i].setPk_trader(regvo.getPaybillunit());
				buildOppInfo(rvo[i]);

				rvo[i].setMemo(regvo.getNote());
				rvo[i].setFracrate(regvo.getFrate());// 辅币汇率
				rvo[i].setLocalrate(regvo.getBrate());// 本币汇率
				fillCurrKeyValue(rvo[0]);

				rvolist.add(rvo[i]);
			}
		}
		// getBankTallyService().writeBankacc(rvolist.toArray(new
		// SettlementBodyVO[0]));
		writeBankAcc(rvolist.toArray(new SettlementBodyVO[0]), headvo.getWritebankacc());
	}

	public void addGatherNegativeBillCMP(HYBillVO billvo, String tally_corp,
			String tallyman, UFDate tallydate) throws BusinessException {
		if (!isTogatherWithCMP(tally_corp)) {
			return;
		}
		List<SettlementBodyVO> rvolist = new ArrayList<SettlementBodyVO>();
		String sqlbuffer = "";
		SettlementBodyVO[] rvo = null;
		SettlementBodyVO[] returnvo = null;
		ReturnVO headvo = (ReturnVO) billvo.getParentVO();
		ReturnBVO[] bvos = (ReturnBVO[]) billvo.getChildrenVO();
		rvo = new SettlementBodyVO[bvos.length];
		initSettlementBodyVO(rvo, headvo);
		if (headvo.getReturntype().equals(FbmBusConstant.BACK_TYPE_GATHER)
				|| headvo.getReturntype().equals(FbmBusConstant.BACK_TYPE_INVOICE)) {
			sqlbuffer = " and pk_billtypecode = '36GA' ";
		} else if (headvo.getReturntype().equals(FbmBusConstant.BACK_TYPE_ENDORE)) {
			sqlbuffer = " and pk_billtypecode = '36GQ'";
		} else if (headvo.getReturntype().equals(FbmBusConstant.BACK_TYPE_DISABLE)) {
			sqlbuffer = " and pk_billtypecode = '36GA'"; // 与LX沟通 废票退票时，单据类型为
															// :36GA
		}
		for (int i = 0; i < bvos.length; i++) {
			CommonDAO commonDAO = new CommonDAO();
			BaseDAO dao = new BaseDAO();
			RegisterVO regvo = (RegisterVO) dao.retrieveByPK(RegisterVO.class, bvos[i].getPk_source());
			BaseinfoVO baseVO = commonDAO.queryBaseinfoByPK(regvo.getPk_baseinfo());
			// 判断是否有接口单据引用，如果有接口单据引用就不再冲票据日记账
			Collection col = dao.retrieveByClause(OuterVO.class, "pk_register='"
					+ bvos[i].getPk_source()
					+ "'"
					+ sqlbuffer);
			if (col != null && col.size() > 0) {
				continue;
			}
			if (regvo.getSfflag().booleanValue()) {

				rvo[i].setPk_corp(tally_corp);
				rvo[i].setPk_currtype(baseVO.getPk_curr());
				rvo[i].setTallydate(tallydate);
				rvo[i].setSigndate(tallydate);
				rvo[i].setFundformcode(CmpConst.BILL_DEPOSIT);
				rvo[i].setBilldate(tallydate);
				rvo[i].setReceive(baseVO.getMoneyy().multiply(-1));
				rvo[i].setFundsflag(CmpConst.CASH_FLOW_IN);
				rvo[i].setDirection(CmpConst.Direction_Receive);
				rvo[i].setPk_notetype(baseVO.getFbmbilltype());
				rvo[i].setTradertype(CmpConst.TradeObjType_KeShang);
				rvo[i].setPk_trader(regvo.getPaybillunit());
				buildOppInfo(rvo[i]);

				rvo[i].setMemo(regvo.getNote());
				rvo[i].setFracrate(regvo.getFrate());// 辅币汇率
				rvo[i].setLocalrate(regvo.getBrate());// 本币汇率
				fillCurrKeyValue(rvo[i]);
				rvolist.add(rvo[i]);
			}
		}

		// getBankTallyService().writeBankacc(rvolist.toArray(new
		// SettlementBodyVO[0]));
		writeBankAcc(rvolist.toArray(new SettlementBodyVO[0]), headvo.getWritebankacc());
	}

	public void delNegativeBillCMP(HYBillVO billvo, String pk_corp,
			String operator, UFDate operatedate) throws BusinessException {

		ReturnVO vo = (ReturnVO) billvo.getParentVO();
		AccBusiVO rvo = new AccBusiVO();
		rvo.setPk_corp(pk_corp);
		rvo.setPk_sourcebill(vo.getPrimaryKey());
		rvo.setOperator(operator);// 当前登陆人
		rvo.setOperateDate(operatedate);// 当前日期

		getBankTallyService().deleteWhenHaveBill(rvo);
	}

}
