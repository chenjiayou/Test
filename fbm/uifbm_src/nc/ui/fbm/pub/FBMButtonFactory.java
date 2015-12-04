/**
 *
 */
package nc.ui.fbm.pub;

import nc.ui.fbm.accept.btnvo.AcceptLinkQryBtnVO;
import nc.ui.fbm.discount.btnvo.DiscountLinkGroupButtonVO;
import nc.ui.fbm.discount.btnvo.QryBalancePlanBtnVO;
import nc.ui.fbm.discount.btnvo.QryInterestPlanBtnVO;
import nc.ui.fbm.invoice.btnvo.InvoiceCancelBillBtnVO;
import nc.ui.fbm.invoice.btnvo.InvoiceLinkQueryGroupBtnVO;
import nc.ui.fbm.invoice.btnvo.InvoiceQryPayPlanBtnVO;
import nc.ui.fbm.invoice.btnvo.QryChargePlanBtnVO;
import nc.ui.fbm.pub.buttonvo.AttachButtonVO;
import nc.ui.fbm.pub.buttonvo.CenterBackCancelOutputBtnVO;
import nc.ui.fbm.pub.buttonvo.CenterBackOutputBtnVO;
import nc.ui.fbm.pub.buttonvo.CenterKeepCancelInputBtnVO;
import nc.ui.fbm.pub.buttonvo.CenterKeepInputBtnVO;
import nc.ui.fbm.pub.buttonvo.ConsignBankCancelTransactButtonVO;
import nc.ui.fbm.pub.buttonvo.ConsignBankCancelVoucherVO;
import nc.ui.fbm.pub.buttonvo.ConsignBankDsiableButtonVO;
import nc.ui.fbm.pub.buttonvo.ConsignBankLQGatherPlan;
import nc.ui.fbm.pub.buttonvo.ConsignBankLQPlan;
import nc.ui.fbm.pub.buttonvo.ConsignBankLQVoucherVO;
import nc.ui.fbm.pub.buttonvo.ConsignBankLinkGatherButtonVO;
import nc.ui.fbm.pub.buttonvo.ConsignBankLinkGroupButtonVO;
import nc.ui.fbm.pub.buttonvo.ConsignBankTransactButtonVO;
import nc.ui.fbm.pub.buttonvo.ConsignBankVoucherVO;
import nc.ui.fbm.pub.buttonvo.DiscountApplyTransactButtonVO;
import nc.ui.fbm.pub.buttonvo.DiscountCalculateButtonVO;
import nc.ui.fbm.pub.buttonvo.DiscountCancelVoucherVO;
import nc.ui.fbm.pub.buttonvo.DiscountLQGatherPlan;
import nc.ui.fbm.pub.buttonvo.DiscountLQVoucherVO;
import nc.ui.fbm.pub.buttonvo.DiscountLinkGatherButtonVO;
import nc.ui.fbm.pub.buttonvo.DiscountVoucherVO;
import nc.ui.fbm.pub.buttonvo.EndoreCancelVoucherVO;
import nc.ui.fbm.pub.buttonvo.EndoreDestoryVO;
import nc.ui.fbm.pub.buttonvo.EndoreLQClearVoucherVO;
import nc.ui.fbm.pub.buttonvo.EndoreLQVoucherVO;
import nc.ui.fbm.pub.buttonvo.EndoreLinkBillButtonVO;
import nc.ui.fbm.pub.buttonvo.EndoreLinkGroupButtonVO;
import nc.ui.fbm.pub.buttonvo.EndoreVoucherVO;
import nc.ui.fbm.pub.buttonvo.FBMCancelSubmitBtnVO;
import nc.ui.fbm.pub.buttonvo.FBMCancelTallyBtnVO;
import nc.ui.fbm.pub.buttonvo.FBMCancelVoucherBtnVO;
import nc.ui.fbm.pub.buttonvo.FBMQueryVoucherBtnVO;
import nc.ui.fbm.pub.buttonvo.FBMSubmitBtnVO;
import nc.ui.fbm.pub.buttonvo.FBMTallyBtnVO;
import nc.ui.fbm.pub.buttonvo.FBMVoucherBtnVO;
import nc.ui.fbm.pub.buttonvo.FbmCancelSelectBtnVO;
import nc.ui.fbm.pub.buttonvo.FbmInvertSelectBtnVO;
import nc.ui.fbm.pub.buttonvo.FbmSelectAllBtnVO;
import nc.ui.fbm.pub.buttonvo.GatherBatchConsignBtnVO;
import nc.ui.fbm.pub.buttonvo.GatherBatchDiscountBtnVO;
import nc.ui.fbm.pub.buttonvo.GatherBatchImpawnBtnVO;
import nc.ui.fbm.pub.buttonvo.GatherBatchOPGroupBtnVO;
import nc.ui.fbm.pub.buttonvo.GatherBusGroupBtnVO;
import nc.ui.fbm.pub.buttonvo.GatherLQueryGroupBtnVO;
import nc.ui.fbm.pub.buttonvo.GatherLQueryPJBookBtnVO;
import nc.ui.fbm.pub.buttonvo.GatherLQuerySFBillBtnVO;
import nc.ui.fbm.pub.buttonvo.GatherToBankKeepBtnVO;
import nc.ui.fbm.pub.buttonvo.GatherToConsignBtnVO;
import nc.ui.fbm.pub.buttonvo.GatherToDisAccountBtnVO;
import nc.ui.fbm.pub.buttonvo.GatherToImpawnBtnVO;
import nc.ui.fbm.pub.buttonvo.GatherToReturnBtnVO;
import nc.ui.fbm.pub.buttonvo.GathertoBankBackBtnVO;
import nc.ui.fbm.pub.buttonvo.ImpawnBackBtn;
import nc.ui.fbm.pub.buttonvo.ImpawnCancelBackBtnVO;
import nc.ui.fbm.pub.buttonvo.IneerBackLinkGroup;
import nc.ui.fbm.pub.buttonvo.IneerKeepLinkGroup;
import nc.ui.fbm.pub.buttonvo.InvoiceBusGroupBtnVO;
import nc.ui.fbm.pub.buttonvo.InvoiceDelCancelBillBtnVO;
import nc.ui.fbm.pub.buttonvo.InvoiceQueryRationBtnVO;
import nc.ui.fbm.pub.buttonvo.InvoiceToBillPayBtnVO;
import nc.ui.fbm.pub.buttonvo.InvoiceToReturnBtnVO;
import nc.ui.fbm.pub.buttonvo.LinkInAccountBalanceBtnVO;
import nc.ui.fbm.pub.buttonvo.Print4NoteButtonVO;
import nc.ui.fbm.pub.buttonvo.Query2PlanBtnVO;
import nc.ui.fbm.pub.buttonvo.QxspBtnVO;
import nc.ui.fbm.pub.buttonvo.RecieptCancelVoucherVO;
import nc.ui.fbm.pub.buttonvo.RecieptVoucherVO;
import nc.ui.fbm.pub.buttonvo.ReckonCancelVoucherButtonVO;
import nc.ui.fbm.pub.buttonvo.ReckonLinkAccountBalance;
import nc.ui.fbm.pub.buttonvo.ReckonLinkGroupButtonVO;
import nc.ui.fbm.pub.buttonvo.ReckonLinkInAcc;
import nc.ui.fbm.pub.buttonvo.ReckonLinkOutAcc;
import nc.ui.fbm.pub.buttonvo.ReckonLinkVoucherButtonVO;
import nc.ui.fbm.pub.buttonvo.ReckonVoucherButtonVO;
import nc.ui.fbm.pub.buttonvo.ReliefCancelOutputVO;
import nc.ui.fbm.pub.buttonvo.ReliefCancelVoucherVO;
import nc.ui.fbm.pub.buttonvo.ReliefLQAccountBalanceVO;
import nc.ui.fbm.pub.buttonvo.ReliefLQGroupVO;
import nc.ui.fbm.pub.buttonvo.ReliefLQVoucherVO;
import nc.ui.fbm.pub.buttonvo.ReliefOutputVO;
import nc.ui.fbm.pub.buttonvo.ReliefVoucherVO;
import nc.ui.fbm.pub.buttonvo.ReturnBillTransBtnVO;
import nc.ui.fbm.pub.buttonvo.ReturnBusGroupBtnVO;
import nc.ui.fbm.pub.buttonvo.ReturnCancelTransBtnVO;
import nc.ui.fbm.pub.buttonvo.SpwcBtnVO;
import nc.ui.fbm.pub.buttonvo.StorageQueryGLBtnVO;
import nc.ui.fbm.pub.buttonvo.StorageReturnBtnVO;
import nc.ui.fbm.returnbill.ReturnLQueryGroupBtnVO;
import nc.ui.trade.button.IBillButtonVO;
import nc.vo.trade.button.ButtonVO;

/**
 * <p>
 * Ʊ�ݰ�ť����
 * <p>
 * �����ˣ�lpf <b>���ڣ�2007-8-24
 * 
 */
public class FBMButtonFactory implements IFBMButton {

	/**
	 * 
	 */
	public FBMButtonFactory() {
		// TODO Auto-generated constructor stub
	}

	public static ButtonVO getButtonVO(int btnid) {
		// TODO Auto-generated method stub
		IBillButtonVO btnVo = null;
		switch (btnid) {
		case IFBMButton.Gather_BusGroup:
			btnVo = new GatherBusGroupBtnVO();
			break;
		case IFBMButton.Gather_BankKeep:
			btnVo = new GatherToBankKeepBtnVO();
			break;
		case IFBMButton.Gather_BankBack:
			btnVo = new GathertoBankBackBtnVO();
			break;
		case IFBMButton.ConsignBank_Disable:
			btnVo = new ConsignBankDsiableButtonVO();
			break;
		case IFBMButton.ConsignBank_Transact:
			btnVo = new ConsignBankTransactButtonVO();
			break;
		case IFBMButton.DiscountApply_Transact:
			btnVo = new DiscountApplyTransactButtonVO();
			break;
		case IFBMButton.Gather_ReturnBill:
			btnVo = new GatherToReturnBtnVO();
			break;
		case IFBMButton.Gather_BankDiscount:
			btnVo = new GatherToDisAccountBtnVO();
			break;
		case IFBMButton.Gather_Consign:
			btnVo = new GatherToConsignBtnVO();
			break;
		case IFBMButton.Discount_Calculate:
			btnVo = new DiscountCalculateButtonVO();
			break;
		case IFBMButton.Invoice_BillPay:
			btnVo = new InvoiceToBillPayBtnVO();
			break;
		case IFBMButton.Invoice_Return:
			btnVo = new InvoiceToReturnBtnVO();
			break;
		case IFBMButton.Invoice_BusGroup:
			btnVo = new InvoiceBusGroupBtnVO();
			break;
		case IFBMButton.QUERYRATION:
			btnVo = new InvoiceQueryRationBtnVO();
			break;
		case IFBMButton.Invoice_LinkQueryGroup:
			btnVo = new InvoiceLinkQueryGroupBtnVO();
			break;
		case IFBMButton.Invoice_CancelBill:
			btnVo = new InvoiceCancelBillBtnVO();
			break;
		case IFBMButton.Invoice_DelCancelBill:
			btnVo = new InvoiceDelCancelBillBtnVO();
			break;
		case IFBMButton.Gather_Impawn:
			btnVo = new GatherToImpawnBtnVO();
			break;
		case IFBMButton.ConsignBank_LinkGroup:
			btnVo = new ConsignBankLinkGroupButtonVO();
			break;
		case IFBMButton.Discount_LinkGroup:
			btnVo = new DiscountLinkGroupButtonVO();
			break;

		case IFBMButton.ConsignBank_LinkGather:
			btnVo = new ConsignBankLinkGatherButtonVO();
			break;

		case IFBMButton.Discount_LinkGather:
			btnVo = new DiscountLinkGatherButtonVO();
			break;
		case IFBMButton.Gather_LQueryGroup:
			btnVo = new GatherLQueryGroupBtnVO();
			break;
		case IFBMButton.Gather_LQueryPJBook:
			btnVo = new GatherLQueryPJBookBtnVO();
			break;
		case IFBMButton.Gather_LQuerySFBill:
			btnVo = new GatherLQuerySFBillBtnVO();
			break;

		case IFBMButton.ReturnBill_BusGroup:
			btnVo = new ReturnBusGroupBtnVO();
			break;
		case IFBMButton.ReturnBill_CancelTrans:
			btnVo = new ReturnCancelTransBtnVO();
			break;
		case IFBMButton.ReturnBill_TransToSFBill:
			btnVo = new ReturnBillTransBtnVO();
			break;
		case IFBMButton.BTN_IMPAWN_BACK:
			btnVo = new ImpawnBackBtn();
			break;
		case IFBMButton.CenterKeep_INPUT:
			btnVo = new CenterKeepInputBtnVO();
			break;
		case IFBMButton.CenterKeep_CANCELINPUT:
			btnVo = new CenterKeepCancelInputBtnVO();
			break;
		case IFBMButton.CenterBack_Output:
			btnVo = new CenterBackOutputBtnVO();
			break;
		case IFBMButton.CenterBack_CancelOut:
			btnVo = new CenterBackCancelOutputBtnVO();
			break;
		case IFBMButton.Center_Storage_QueryGL:
			btnVo = new StorageQueryGLBtnVO();
			break;

		case IFBMButton.CenterKeep_Return:
			btnVo = new StorageReturnBtnVO();
			break;
		case IFBMButton.Relief_Output:
			btnVo = new ReliefOutputVO();// ��������-����
			break;
		case IFBMButton.Relief_CancelOutput:
			btnVo = new ReliefCancelOutputVO();// ��������-ȡ������
			break;
		case IFBMButton.Relief_Voucher:
			btnVo = new ReliefVoucherVO();// ��������-��֤
			break;
		case IFBMButton.Relief_CancelVoucher:
			btnVo = new ReliefCancelVoucherVO();// ��������-ȡ����֤
			break;
		case IFBMButton.Relief_LinkQueryGroup:
			btnVo = new ReliefLQGroupVO();// ��������-������
			break;
		case IFBMButton.Relief_LQAccoutBalance:
			btnVo = new ReliefLQAccountBalanceVO();// ��������-�����˻����
			break;
		case IFBMButton.Relief_LQVoucher:
			btnVo = new ReliefLQVoucherVO();// ��������-����ƾ֤
			break;
		case IFBMButton.Reciept_Voucher:
			btnVo = new RecieptVoucherVO();// ��������ص�-��֤
			break;
		case IFBMButton.Reciept_CancelVoucher:
			btnVo = new RecieptCancelVoucherVO();// ��������ص�-ȡ����֤
			break;
		case IFBMButton.Reckon_CancelVoucher:
			btnVo = new ReckonCancelVoucherButtonVO();// ��������-ȡ����֤
			break;
		case IFBMButton.Reckon_Voucher:
			btnVo = new ReckonVoucherButtonVO();// ��������-��֤
			break;
		case IFBMButton.Reckon_LinkAccoutBalance:
			btnVo = new ReckonLinkAccountBalance();// ��������-�����˻�
			break;
		case IFBMButton.Reckon_LinkGroup:
			btnVo = new ReckonLinkGroupButtonVO();// ��������-�����鰴ť
			break;
		case IFBMButton.Reckon_LinkVoucher:
			btnVo = new ReckonLinkVoucherButtonVO();// ��������-����ƾ֤
			break;
		case IFBMButton.ConsignBank_CancelTransact:
			btnVo = new ConsignBankCancelTransactButtonVO();// ��������-ȡ������
			break;
		case IFBMButton.Endore_Voucher:
			btnVo = new EndoreVoucherVO();// �����������֤
			break;
		case IFBMButton.Endore_CancelVoucher:
			btnVo = new EndoreCancelVoucherVO();// ���������ȡ����֤
			break;
		case IFBMButton.Discount_Voucher:
			btnVo = new DiscountVoucherVO();// ���ְ�����֤
			break;
		case IFBMButton.Discount_CancelVoucher:
			btnVo = new DiscountCancelVoucherVO();// ���ְ���ȡ����֤
			break;
		case IFBMButton.ConsignBank_Voucher:
			btnVo = new ConsignBankVoucherVO();// �������գ���֤
			break;
		case IFBMButton.ConsignBank_CancelVoucher:
			btnVo = new ConsignBankCancelVoucherVO();// �������գ�ȡ����֤
			break;
		case IFBMButton.Endore_LinkVoucher:
			btnVo = new EndoreLQVoucherVO();// �������������ƾ֤
			break;
		case IFBMButton.Endore_Destroy:
			btnVo = new EndoreDestoryVO();// �����������
			break;
		case IFBMButton.Discount_LinkVoucher:// ���ְ�������ƾ֤
			btnVo = new DiscountLQVoucherVO();
			break;
		case IFBMButton.ConsignBank_LinkVoucher:// �������գ�����ƾ֤��
			btnVo = new ConsignBankLQVoucherVO();
			break;
		case IFBMButton.Attach:
			btnVo = new AttachButtonVO();
			break;
		case IFBMButton.Endore_LinkGroup:
			btnVo = new EndoreLinkGroupButtonVO();// �������������
			break;
		case IFBMButton.Endore_LinkBill:
			btnVo = new EndoreLinkBillButtonVO();// ������������ո�������
			break;
		case IFBMButton.Endore_LinkClearVoucher:
			btnVo = new EndoreLQClearVoucherVO();// ��������������ƾ֤��
			break;
		case IFBMButton.FBM_VOUCHER:
			btnVo = new FBMVoucherBtnVO();
			break;
		case IFBMButton.FBM_CANCELVOUCHER:
			btnVo = new FBMCancelVoucherBtnVO();
			break;
		case IFBMButton.FBM_QUERYVOUCHER:
			btnVo = new FBMQueryVoucherBtnVO();
			break;
		case IFBMButton.FBM_SUBMIT:
			btnVo = new FBMSubmitBtnVO();
			break;
		case IFBMButton.FBM_CANCELSUBMIT:
			btnVo = new FBMCancelSubmitBtnVO();
			break;
		case IFBMButton.FBM_TALLY:
			btnVo = new FBMTallyBtnVO();
			break;
		case IFBMButton.FBM_CANCELTALLY:
			btnVo = new FBMCancelTallyBtnVO();
			break;
		case IFBMButton.Reckon_LinkInAccountBalance:
			btnVo = new ReckonLinkInAcc();
			break;
		case IFBMButton.Reckon_LinkOutAccountBalance:
			btnVo = new ReckonLinkOutAcc();
			break;
		case IFBMButton.LINK_ACCOUNTBANLANCE:
			btnVo = new LinkInAccountBalanceBtnVO();
			break;
		case IFBMButton.BTN_CANCEL_IMPAWNBACK:
			btnVo = new ImpawnCancelBackBtnVO();
			break;
		case IFBMButton.BTN_RETURN_QUERYGROUP:
			btnVo = new ReturnLQueryGroupBtnVO();
			break;
		case IFBMButton.BTN_QUERY_PLAN:
			btnVo = new Query2PlanBtnVO();
			break;
		case IFBMButton.BTN_QUERY_CHARGE_PLAN:
			btnVo = new QryChargePlanBtnVO();
			break;
		case IFBMButton.BTN_QUERY_INVOICE_PAY_PLAN:
			btnVo = new InvoiceQryPayPlanBtnVO();
			break;
		case IFBMButton.BTN_ACCEPT_QUERY_GROUP:
			btnVo = new AcceptLinkQryBtnVO();
			break;
		case IFBMButton.BTN_QUERY_INTEREST_PLAN:
			btnVo = new QryInterestPlanBtnVO();
			break;
		case IFBMButton.BTN_QUERY_BALANCE_PLAN:
			btnVo = new QryBalancePlanBtnVO();
			break;
		case IFBMButton.BTNVO_SPWC:
			btnVo = new SpwcBtnVO(); // ��Ʊ�Ǽǣ���Ʊ��� zhouwb 2008-9-17
			break;
		case IFBMButton.BTNVO_QXSP:
			btnVo = new QxspBtnVO(); // ��Ʊ�Ǽǣ�ȡ����Ʊ zhouwb 2008-9-17
			break;
		case IFBMButton.ConsignBank_LinkPlan:
			btnVo = new ConsignBankLQPlan();
			break;
		case IFBMButton.ConsignBank_LinkGatherPlan:
			btnVo = new ConsignBankLQGatherPlan();
			break;
		case IFBMButton.Discount_LinkGatherPlan:
			btnVo = new DiscountLQGatherPlan();
			break;
		case IFBMButton.InnerBack_LinkGroup:
			btnVo = new IneerBackLinkGroup();
			break;
		case IFBMButton.InnerKeep_LinkGroup:
			btnVo = new IneerKeepLinkGroup();
			break;
		case IFBMButton.PRINT4NOTE:
			btnVo = new Print4NoteButtonVO();
			break;
		case IFBMButton.FBM_SELECTALL:  //ȫѡ
			btnVo = new FbmSelectAllBtnVO();
			break;
		case IFBMButton.FBM_CANCELSELECT: //ȫ��
			btnVo = new FbmCancelSelectBtnVO();
			break;
		case IFBMButton.FBM_INVERTSELECT:  //��ѡ
			btnVo = new FbmInvertSelectBtnVO();
			break;
		case IFBMButton.Gather_Batch:    //��Ʊ�Ǽ�-��������
			btnVo = new GatherBatchOPGroupBtnVO();
			break;
		case IFBMButton.Gather_BatchConsign:  //��Ʊ�Ǽ�--��������.
			btnVo = new GatherBatchConsignBtnVO();
			break;
		case IFBMButton.Gather_BatchDiscount: //��Ʊ�Ǽ�--��������
			btnVo = new GatherBatchDiscountBtnVO();
			break;
		case IFBMButton.Gather_BatchImpawn:  //��Ʊ�Ǽ�--������Ѻ
			btnVo = new GatherBatchImpawnBtnVO();
			break;
		default:
			break;
		}

		return btnVo == null ? null : btnVo.getButtonVO();
	}

}
