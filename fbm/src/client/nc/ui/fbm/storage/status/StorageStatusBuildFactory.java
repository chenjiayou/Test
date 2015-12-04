/**
 *
 */
package nc.ui.fbm.storage.status;

import java.util.ArrayList;
import java.util.List;

import nc.ui.cdm.loandeal.pub.IApproveStatus;
import nc.ui.fbm.pub.IFBMButton;
import nc.ui.trade.button.IBillButton;
import nc.vo.engine.status.element.IStatusCheck;
import nc.vo.engine.status.element.StatusElementForInt;
import nc.vo.engine.status.element.UAPBillStatusElement;
import nc.vo.engine.status.factory.UAPButtonStatusBuildFactory;
import nc.vo.fbm.pub.constant.IFBMStatus;
import nc.vo.trade.button.ButtonVO;
import nc.vo.trade.pub.IBillStatus;

/**
 * <p>
 * 内部托管状态机
 * <p>创建人：lpf
 * <b>日期：2007-10-11
 *
 */
public class StorageStatusBuildFactory extends UAPButtonStatusBuildFactory {

	/**
	 * 
	 */
	public StorageStatusBuildFactory() {
		// TODO Auto-generated constructor stub
	}

	protected List<List> initSelfButtonRule(List<List> rule, ButtonVO btnVO) {
		rule.clear();
		ArrayList<IStatusCheck<Integer>> elementStatus = new ArrayList<IStatusCheck<Integer>>();;
		switch(btnVO.getBtnNo()){
		case IBillButton.Audit:
			elementStatus.add(new StatusElementForInt(IApproveStatus.class.getName(), new int[]{IApproveStatus.IS_APPROVE_MAN}));
			elementStatus.add(new UAPBillStatusElement(IBillStatus.class.getName(), new int[]{IBillStatus.FREE,IBillStatus.CHECKGOING}));
			elementStatus.add(new StatusElementForInt(IOwnerCorpStatus.class.getName(),new int[]{IOwnerCorpStatus.IS_OWNER_CORP}));
			break;
		case IBillButton.CancelAudit:
//			elementStatus.add(new StatusElementForInt(IUnApproveStatus.class.getName(), new int[]{IUnApproveStatus.IS_APPROVE_MAN}));
//			elementStatus.add(new StatusElementForInt(IOwnerCorpStatus.class.getName(),new int[]{IOwnerCorpStatus.IS_OWNER_CORP}));
			elementStatus.add(new UAPBillStatusElement(IBillStatus.class.getName(), new int[]{IBillStatus.CHECKPASS,IFBMStatus.RETURNED}));
			break;	
		case IBillButton.Edit:
		case IBillButton.Delete:
			elementStatus.add(new StatusElementForInt(IOwnerCorpStatus.class.getName(), new int[]{IOwnerCorpStatus.IS_OWNER_CORP}));
			elementStatus.add(new UAPBillStatusElement(IBillStatus.class.getName(), new int[]{IBillStatus.FREE}));
			break;	

		case IFBMButton.CenterKeep_Return:
			elementStatus.add(new StatusElementForInt(UnitBillStatus.class.getName(), new int[]{UnitBillStatus.UNIT_BILL}));
			elementStatus.add(new UAPBillStatusElement(IBillStatus.class.getName(), new int[]{IFBMStatus.SUBMIT}));
			elementStatus.add(new StatusElementForInt(IOwnerCorpStatus.class.getName(),new int[]{IOwnerCorpStatus.ISNOT_OWNER_CORP}));
			break;
		case IFBMButton.CenterKeep_INPUT:
			elementStatus.add(new StatusElementForInt(UnitBillStatus.class.getName(), new int[]{UnitBillStatus.UNIT_BILL}));
			elementStatus.add(new UAPBillStatusElement(IBillStatus.class.getName(), new int[]{IBillStatus.CHECKPASS,IFBMStatus.SUBMIT}));
			elementStatus.add(new StatusElementForInt(BillStatusWithPKCorp.class.getName(),new int[]{BillStatusWithPKCorp.CENTER_CHECKPASS,BillStatusWithPKCorp.UNIT_SUBMIT}));
			break;
		case IFBMButton.CenterBack_Output:
			elementStatus.add(new StatusElementForInt(UnitBillStatus.class.getName(), new int[]{UnitBillStatus.UNIT_BILL}));
			elementStatus.add(new UAPBillStatusElement(IBillStatus.class.getName(), new int[]{IBillStatus.CHECKPASS,IFBMStatus.SUBMIT}));
			elementStatus.add(new StatusElementForInt(BillStatusWithPKCorp.class.getName(),new int[]{BillStatusWithPKCorp.CENTER_CHECKPASS,BillStatusWithPKCorp.UNIT_SUBMIT}));
			break;
		case IFBMButton.CenterKeep_CANCELINPUT:
			elementStatus.add(new StatusElementForInt(UnitBillStatus.class.getName(), new int[]{UnitBillStatus.UNIT_BILL}));
			elementStatus.add(new UAPBillStatusElement(IBillStatus.class.getName(), new int[]{IFBMStatus.INPUT_SUCCESS}));
			break;
		case IFBMButton.CenterBack_CancelOut:
			elementStatus.add(new StatusElementForInt(UnitBillStatus.class.getName(), new int[]{UnitBillStatus.UNIT_BILL}));
			elementStatus.add(new UAPBillStatusElement(IBillStatus.class.getName(), new int[]{IFBMStatus.OUTPUT_SUCCESS}));
			break;
		case IFBMButton.FBM_TALLY:
			elementStatus.add(new StatusElementForInt(TallyStatus.class.getName(),new int[]{TallyStatus.UNIT_CAN_TALLY}));
			elementStatus.add(new UAPBillStatusElement(IBillStatus.class.getName(), new int[]{IFBMStatus.INPUT_SUCCESS,IFBMStatus.OUTPUT_SUCCESS}));
			break;
		case IFBMButton.FBM_CANCELTALLY:
			elementStatus.add(new StatusElementForInt(TallyStatus.class.getName(),new int[]{TallyStatus.UNIT_CAN_CANCELTALLY}));
			elementStatus.add(new UAPBillStatusElement(IBillStatus.class.getName(), new int[]{IFBMStatus.INPUT_SUCCESS,IFBMStatus.OUTPUT_SUCCESS}));
			break;
		case IFBMButton.FBM_SUBMIT:
			elementStatus.add(new StatusElementForInt(DealSelfBillStatus.class.getName(),new int[]{DealSelfBillStatus.DEAL_SELF_BILL}));
			elementStatus.add(new UAPBillStatusElement(IBillStatus.class.getName(), new int[]{IFBMStatus.CHECKPASS}));
			elementStatus.add(new StatusElementForInt(IOwnerCorpStatus.class.getName(),new int[]{IOwnerCorpStatus.IS_OWNER_CORP}));
			break;
		case IFBMButton.FBM_CANCELSUBMIT:
			elementStatus.add(new StatusElementForInt(DealSelfBillStatus.class.getName(),new int[]{DealSelfBillStatus.DEAL_SELF_BILL}));
			elementStatus.add(new UAPBillStatusElement(IBillStatus.class.getName(), new int[]{IFBMStatus.SUBMIT}));
			break;
		case IFBMButton.FBM_VOUCHER:
			elementStatus.add(new StatusElementForInt(VoucherStatus.class.getName(),new int[]{VoucherStatus.CENTER_CAN_VOUCHER,VoucherStatus.UNIT_CAN_VOUCHER}));
			elementStatus.add(new UAPBillStatusElement(IBillStatus.class.getName(), new int[]{IFBMStatus.INPUT_SUCCESS,IFBMStatus.OUTPUT_SUCCESS}));
			break;
		case IFBMButton.FBM_CANCELVOUCHER:
			elementStatus.add(new StatusElementForInt(VoucherStatus.class.getName(),new int[]{VoucherStatus.CENTER_CAN_CANCELVOUCHER,VoucherStatus.UNIT_CAN_CANCELVOUCHER}));
			elementStatus.add(new UAPBillStatusElement(IBillStatus.class.getName(), new int[]{IFBMStatus.INPUT_SUCCESS,IFBMStatus.OUTPUT_SUCCESS}));
			break;
		default:
	}
		if(elementStatus!=null && elementStatus.size() > 0){
			rule.add(elementStatus);
		}
	return rule;
	}
}
