package nc.bs.fbm.invoice;

import java.util.ArrayList;
import java.util.List;

import nc.bs.fbm.pub.ActionQueryDAO;
import nc.bs.fbm.pub.CommonDAO;
import nc.bs.fbm.pub.FBM2CMPAccSuper;
import nc.itf.cm.prv.CmpConst;
import nc.itf.cmp.bankacc.AccBusiVO;
import nc.itf.cmp.bankacc.CMPaccStatus;
import nc.vo.cmp.settlement.SettlementBodyVO;
import nc.vo.fbm.outer.OuterVO;
import nc.vo.fbm.pub.ActionVO;
import nc.vo.fbm.pub.BaseinfoVO;
import nc.vo.fbm.pub.FBMProxy;
import nc.vo.fbm.pub.constant.FbmActionConstant;
import nc.vo.fbm.pub.constant.FbmBusConstant;
import nc.vo.fbm.register.RegisterVO;
import nc.vo.fi.pub.ProductInfo;
import nc.vo.pub.BusinessException;
import nc.vo.pub.SuperVO;
import nc.vo.pub.lang.UFDate;
import nc.vo.pub.lang.UFDouble;
import nc.vo.trade.pub.HYBillVO;
import nc.vo.uapbd.bankaccount.BankaccbasVO;

public class InvoiceUtil extends FBM2CMPAccSuper {

	/**
	 * ��������Ʊ����
	 * 
	 * @param billvo
	 * @param tally_corp
	 * @param tallyman
	 * @param tallydate
	 * @throws BusinessException
	 */
	public void addCMPBill4Destory(HYBillVO billvo, String tally_corp,
			String tallyman, UFDate tallydate) throws BusinessException {
		if (!isTogatherWithCMP(tally_corp)) {
			return;
		}

		RegisterVO headvo = (RegisterVO) billvo.getParentVO();

		// ��Ʊ��Ʊ����
		ActionQueryDAO actionDAO = new ActionQueryDAO();
		ActionVO newActionVO = actionDAO.queryNewestByPk_baseinfo(headvo.getPk_baseinfo(), headvo.getPk_corp());
		if (newActionVO.getActioncode().equals(FbmActionConstant.AUDIT)
				&& newActionVO.getBilltype().equals(FbmBusConstant.BILLTYPE_RETURN)) {
			// ���û����fbm_outer���˵����Ʊû�����ո���������ֱ���Ѹ�Ʊ�򹴣�����
			SuperVO[] outers = FBMProxy.getUifService().queryByCondition(OuterVO.class, "isnull(dr,0)=0 and pk_billtypecode='36GL' and pk_busibill='"
					+ headvo.getPrimaryKey()
					+ "'");
			if (outers != null && outers.length > 0) {// ���ո������ݣ���дƱ����
				return;
			}
		} else if (newActionVO.getActioncode().equals(FbmActionConstant.AUDIT)
				&& newActionVO.getBilltype().equals(FbmBusConstant.BILLTYPE_GATHER)) {
			SuperVO[] outers = FBMProxy.getUifService().queryByCondition(OuterVO.class, "isnull(dr,0)=0 and pk_billtypecode='36GA' and pk_busibill='"
					+ newActionVO.getPk_source()
					+ "'");
			// if(outers == null || outers.length == 0){
			// return ;
			// }\
			return;// ��Ʊ������Ӧ��дƱ���ʣ�
		}

		SettlementBodyVO[] rvo = new SettlementBodyVO[1];
		initSettlementBodyVO(rvo, headvo);
		CommonDAO commonDAO = new CommonDAO();
		BaseinfoVO baseVO = commonDAO.queryBaseinfoByPK(headvo.getPk_baseinfo());
		rvo[0].setPk_corp(tally_corp);
		rvo[0].setPk_currtype(baseVO.getPk_curr());
		rvo[0].setTallydate(tallydate);
		rvo[0].setSigndate(tallydate);
		rvo[0].setFundformcode(CmpConst.BILL_DEPOSIT);
		rvo[0].setBilldate(tallydate);
		rvo[0].setPay(baseVO.getMoneyy().multiply(-1));
		rvo[0].setFundsflag(CmpConst.CASH_FLOW_OUT);
		rvo[0].setDirection(CmpConst.Direction_Pay);
		rvo[0].setPk_notetype(baseVO.getFbmbilltype());
		rvo[0].setTradertype(CmpConst.TradeObjType_KeShang);// �Է�Ϊ����
		rvo[0].setPk_trader(baseVO.getReceiveunit());
		rvo[0].setPk_oppaccount(baseVO.getReceivebankacc());
		buildOppInfo(rvo[0]);

		rvo[0].setMemo(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("36201505", "UPP36201505-000006")/*
																											 * @res
																											 * "������Ʊ�Ǽǵ���Ʊҵ��"
																											 */);
		rvo[0].setFracrate(headvo.getFrate());// ���һ���
		rvo[0].setLocalrate(headvo.getBrate());// ���һ���
		fillCurrKeyValue(rvo[0]);

		// getBankTallyService().writeBankacc(rvo);
		writeBankAcc(rvo, headvo.getWritebankacc());
	}

	/**
	 * NC5.6�޸�Ϊ����Ѹ�Ʊ�򹴣����֧�����������ո�������ά��
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

		if (!headvo.getSfflag().booleanValue()) {
			return;
		}
		SettlementBodyVO[] rvo = new SettlementBodyVO[1];
		initSettlementBodyVO(rvo, headvo);
		CommonDAO commonDAO = new CommonDAO();
		BaseinfoVO baseVO = commonDAO.queryBaseinfoByPK(headvo.getPk_baseinfo());
		rvo[0].setPk_corp(tally_corp);
		rvo[0].setPk_currtype(baseVO.getPk_curr());
		rvo[0].setTallydate(headvo.getInvoicedate());
		rvo[0].setSigndate(headvo.getInvoicedate());
		rvo[0].setFundformcode(CmpConst.BILL_DEPOSIT);
		rvo[0].setBilldate(headvo.getInvoicedate());
		rvo[0].setPay(baseVO.getMoneyy());
		rvo[0].setFundsflag(CmpConst.CASH_FLOW_OUT);
		rvo[0].setDirection(CmpConst.Direction_Pay);
		rvo[0].setPk_notetype(baseVO.getFbmbilltype());
		rvo[0].setTradertype(CmpConst.TradeObjType_Bank);
		rvo[0].setPk_trader(baseVO.getPaybank());
		buildOppInfo(rvo[0]);

		rvo[0].setMemo(headvo.getNote());
		rvo[0].setFracrate(headvo.getFrate());// ���һ���
		rvo[0].setLocalrate(headvo.getBrate());// ���һ���
		fillCurrKeyValue(rvo[0]);

		// getBankTallyService().writeBankacc(rvo);
		writeBankAcc(rvo, headvo.getWritebankacc());
	}

	public void addCMPBank(HYBillVO billvo, String tally_corp, String tallyman,
			UFDate tallydate) throws BusinessException {
		if (!isTogatherWithCMP(tally_corp)) {
			return;
		}

		RegisterVO vo = (RegisterVO) billvo.getParentVO();
		if (vo.getPoundagemoney() == null) {
			vo.setPoundagemoney(new UFDouble(0));
		}
		if (vo.getPoundagemoneyb() == null) {
			vo.setPoundagemoneyb(new UFDouble(0));
		}
		if (vo.getPoundagemoneyf() == null) {
			vo.setPoundagemoneyf(new UFDouble(0));
		}
		List<SettlementBodyVO> retList = new ArrayList<SettlementBodyVO>();

		CommonDAO commonDAO = new CommonDAO();
		BaseinfoVO baseVO = commonDAO.queryBaseinfoByPK(vo.getPk_baseinfo());
		SettlementBodyVO rvo = new SettlementBodyVO();

		rvo.setPk_corp(tally_corp);// ��˾
		rvo.setSystemcode(ProductInfo.pro_FBM);// ��Դϵͳ
		rvo.setPk_bill(vo.getPrimaryKey());// ҵ�񵥾�����

		rvo.setBillcode(vo.getVbillno());// ���ݱ��
		rvo.setBilldate(vo.getInvoicedate());// ҵ������ȡʵ�ʷſ�����
		rvo.setTallystatus(new Integer(CMPaccStatus.SUCCESSACCOUNT.getStatus()));// ����״̬,����Ϊ����ɹ�����
		rvo.setTallydate(vo.getInvoicedate());
		rvo.setSigndate(vo.getInvoicedate());
		rvo.setFundformcode(CmpConst.BANK_DEPOSIT);// ���д��

		String selfAcc = baseVO.getPaybankacc();

		UFDouble securitymoney = vo.getSecuritymoney() == null ? new UFDouble(0)
				: vo.getSecuritymoney();
		rvo.setPay(vo.getPoundagemoney().add(securitymoney));

		UFDouble securitymoneyf = vo.getSecuritymoneyf() == null ? new UFDouble(
				0)
				: vo.getSecuritymoneyf();
		rvo.setPayfrac(vo.getPoundagemoneyf().add(securitymoneyf));

		UFDouble securitymoneyb = vo.getSecuritymoneyb() == null ? new UFDouble(
				0)
				: vo.getSecuritymoneyb();
		rvo.setPaylocal(vo.getPoundagemoneyb().add(securitymoneyb));//
		rvo.setFundsflag(CmpConst.CASH_FLOW_OUT);// �ʽ�����(0 �ʽ�����1 �ʽ�����2 ת�˳�)
		rvo.setDirection(CmpConst.Direction_Pay);// CmpConst.Direction_Receive=0�տCmpConst.Direction_Receive=1���

		rvo.setPk_account(selfAcc); // ������
		rvo.setPk_currtype(baseVO.getPk_curr());// ����
		rvo.setFracrate(vo.getFrate());// ���һ���
		rvo.setLocalrate(vo.getBrate());// ���һ���
		rvo.setTradertype(CmpConst.TradeObjType_Bank);// �Է�����Ϊ����
		rvo.setPk_trader(baseVO.getPaybank());
		buildOppInfo(rvo);

		rvo.setPk_billtype(vo.getPk_billtypecode());
		rvo.setMemo(vo.getNote());
		retList.add(rvo);

		// ����֤��
		if (FbmBusConstant.ASSURETYPE_BAIL.equals(vo.getImpawnmode())) {

			BankaccbasVO bankaccvo = (BankaccbasVO) FBMProxy.getUAPQuery().retrieveByPK(BankaccbasVO.class, vo.getSecurityaccount());

			SettlementBodyVO rvo1 = new SettlementBodyVO();
			rvo1.setPk_corp(tally_corp);// ��˾
			rvo1.setSystemcode(ProductInfo.pro_FBM);// ��Դϵͳ
			rvo1.setPk_bill(vo.getPrimaryKey());// ҵ�񵥾�����
			rvo1.setPk_account(bankaccvo.getPrimaryKey());
			rvo1.setBillcode(vo.getVbillno());// ���ݱ��
			rvo1.setBilldate(vo.getInvoicedate());// ҵ������ȡʵ�ʷſ�����
			rvo1.setTallydate(vo.getInvoicedate());
			rvo1.setSigndate(vo.getInvoicedate());
			rvo1.setTallystatus(new Integer(
					CMPaccStatus.SUCCESSACCOUNT.getStatus()));// ����״̬,����Ϊ����ɹ�����
			rvo1.setFundformcode(1);// ���д��

			selfAcc = null;

			selfAcc = vo.getSecurityaccount();
			rvo1.setReceive(vo.getSecuritymoney());
			rvo1.setReceivefrac(vo.getSecuritymoneyf());
			rvo1.setReceivelocal(vo.getSecuritymoneyb());//
			rvo1.setFundsflag(CmpConst.CASH_FLOW_IN);// �ʽ�����(0 �ʽ�����1 �ʽ�����2 ת�˳�)
			rvo1.setDirection(CmpConst.Direction_Receive);// CmpConst.Direction_Receive=0�տCmpConst.Direction_Receive=1���

			rvo1.setPk_account(selfAcc); // ������
			rvo1.setPk_currtype(baseVO.getPk_curr());// ����
			rvo1.setFracrate(vo.getFrate());// ���һ���
			rvo1.setLocalrate(vo.getBrate());// ���һ���
			rvo1.setTradertype(CmpConst.TradeObjType_Bank);// �Է�����Ϊ����
			rvo1.setPk_trader(baseVO.getPaybank());
			buildOppInfo(rvo1);

			rvo1.setPk_billtype(vo.getPk_billtypecode());
			rvo1.setMemo(vo.getNote());
			retList.add(rvo1);
		}

		// UFBoolean writebankacc = vo.getWritebankacc();
		// if (writebankacc == null || writebankacc.booleanValue() == false) {
		// getBankTallyService().writeBankaccWithCheck((SettlementBodyVO[])
		// retList.toArray(new SettlementBodyVO[0]), false);
		// } else {
		// getBankTallyService().writeBankacc((SettlementBodyVO[])
		// retList.toArray(new SettlementBodyVO[0]));
		// }
		//		
		writeBankAcc((SettlementBodyVO[]) retList.toArray(new SettlementBodyVO[0]), vo.getWritebankacc());
	}

	public void delCMPacc(HYBillVO billvo, String pk_corp, String operator,
			UFDate operatedate) throws BusinessException {

		RegisterVO vo = (RegisterVO) billvo.getParentVO();
		AccBusiVO rvo = new AccBusiVO();
		rvo.setPk_corp(pk_corp);
		rvo.setPk_sourcebill(vo.getPrimaryKey());
		rvo.setOperator(operator);// ��ǰ��½��
		rvo.setOperateDate(operatedate);// ��ǰ����

		getBankTallyService().deleteWhenHaveBill(rvo);

		// ����֤��
		// if (FbmBusConstant.ASSURETYPE_BAIL.equals(vo.getImpawnmode())) {
		// BankaccbasVO bankaccvo =
		// (BankaccbasVO)FBMProxy.getUAPQuery().retrieveByPK(BankaccbasVO.class,
		// vo.getSecurityaccount());
		// rvo.setPk_sourcebill(bankaccvo.getPrimaryKey());
		// getBankTallyService().deleteWithCheckWhenHaveBill(rvo, false);
		// }

	}

}
