/**
 *
 */
package nc.ui.fbm.invoice.status;

import java.util.ArrayList;
import java.util.List;

import nc.ui.fbm.gather.status.IFBMBillTypeStatus;
import nc.ui.fbm.pub.IFBMButton;
import nc.ui.fbm.pub.btnstatus.FBMVoucherBtnStatus;
import nc.ui.trade.button.IBillButton;
import nc.vo.engine.status.element.IStatusCheck;
import nc.vo.engine.status.element.StatusElementForInt;
import nc.vo.engine.status.element.UAPBillOperateElement;
import nc.vo.engine.status.element.UAPBillStatusElement;
import nc.vo.engine.status.factory.UAPButtonStatusBuildFactory;
import nc.vo.fbm.pub.constant.IFBMStatus;
import nc.vo.trade.button.ButtonVO;
import nc.vo.trade.pub.IBillStatus;

/**
 * <p>
 *
 * <p>创建人：lpf
 * <b>日期：2007-10-29
 *
 */
public class InvoiceStatusBuildFactory extends UAPButtonStatusBuildFactory {

	/**
	 * 
	 */
	public InvoiceStatusBuildFactory() {
		// TODO Auto-generated constructor stub
	}

	@Override
	protected List<List> initSelfButtonRule(List<List> rule, ButtonVO btnVO) {
		rule.clear();
		ArrayList<IStatusCheck<Integer>> elementStatus = new ArrayList<IStatusCheck<Integer>>();
		switch(btnVO.getBtnNo()){
		case IBillButton.CancelAudit:
			elementStatus.add(new UAPBillOperateElement(IBillStatus.class.getName(), new int[]{IFBMStatus.Create,IBillStatus.CHECKPASS}));
			elementStatus.add(new StatusElementForInt(FBMVoucherBtnStatus.class.getName(),new int[]{FBMVoucherBtnStatus.CAN_VOUCHER}));
			elementStatus.add(new StatusElementForInt(IFBMBillTypeStatus.class.getName(),new int[]{IFBMBillTypeStatus.STATUS_INVOICED,IFBMBillTypeStatus.STATUS_PAYBILL}));
			break;
		case IFBMButton.Invoice_Return:
		case IFBMButton.Invoice_BillPay:
			elementStatus.add(new StatusElementForInt(IFBMBillTypeStatus.class.getName(), new int[]{IFBMBillTypeStatus.STATUS_INVOICED,IFBMBillTypeStatus.STATUS_PAYBILL}));
			break;
		case IFBMButton.Invoice_CancelBill:
			elementStatus.add(new StatusElementForInt(IsBillDestroyed.class.getName(), new int[]{IsBillDestroyed.STATUS_NOT_DESTROYED}));
			elementStatus.add(new UAPBillOperateElement(IBillStatus.class.getName(), new int[]{IFBMStatus.Create,IBillStatus.CHECKPASS}));
			elementStatus.add(new StatusElementForInt(IFBMBillTypeStatus.class.getName(), new int[]{IFBMBillTypeStatus.STATUS_RETURNED,IFBMBillTypeStatus.STATUS_PAYBILL}));
			break;
		case IFBMButton.Invoice_DelCancelBill:
			elementStatus.add(new StatusElementForInt(IFBMBillTypeStatus.class.getName(), new int[]{IFBMBillTypeStatus.STATUS_DESTROYED}));
			break;
		case IFBMButton.QUERYRATION:
			elementStatus.add(new StatusElementForInt(IsUseCCcontrol.class.getName(), new int[]{IsUseCCcontrol.IS_USECC}));
			break;
		case IFBMButton.FBM_VOUCHER:// 制证
			elementStatus.add(new UAPBillStatusElement(IBillStatus.class
					.getName(), new int[] { IBillStatus.CHECKPASS }));// 审批通过
			elementStatus.add(new StatusElementForInt(FBMVoucherBtnStatus.class.getName(),new int[]{FBMVoucherBtnStatus.CAN_VOUCHER}));
			break;
		case IFBMButton.FBM_CANCELVOUCHER:
			elementStatus.add(new UAPBillStatusElement(IBillStatus.class
					.getName(), new int[] { IBillStatus.CHECKPASS }));// 审批通过
			elementStatus.add(new StatusElementForInt(FBMVoucherBtnStatus.class.getName(),new int[]{FBMVoucherBtnStatus.CAN_CANCELVOUCHER}));
			break;
		default:
		}
		
		if(elementStatus!=null && elementStatus.size() > 0){
			rule.add(elementStatus);
		}
		return rule;
	}
}
