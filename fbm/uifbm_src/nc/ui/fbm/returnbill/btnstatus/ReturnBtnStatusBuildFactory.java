package nc.ui.fbm.returnbill.btnstatus;

import java.util.ArrayList;
import java.util.List;

import nc.ui.fbm.pub.IFBMButton;
import nc.ui.trade.button.IBillButton;
import nc.vo.engine.status.element.IStatusCheck;
import nc.vo.engine.status.element.StatusElementForInt;
import nc.vo.engine.status.element.UAPBillStatusElement;
import nc.vo.engine.status.factory.UAPButtonStatusBuildFactory;
import nc.vo.trade.button.ButtonVO;
import nc.vo.trade.pub.IBillStatus;

public class ReturnBtnStatusBuildFactory extends UAPButtonStatusBuildFactory {

	protected List<List> initSelfButtonRule(List<List> rule, ButtonVO btnVO) {
		rule.clear();
		ArrayList<IStatusCheck<Integer>> elementStatus = new ArrayList<IStatusCheck<Integer>>();
		switch (btnVO.getBtnNo()) {
		case IBillButton.CancelAudit:
			elementStatus.add(new UAPBillStatusElement(IBillStatus.class
					.getName(), new int[] { IBillStatus.CHECKPASS }));// 审批通过
			elementStatus.add(new StatusElementForInt(ReturnVoucherFlagBtnStatus.class.getName(),new int[]{ReturnVoucherFlagBtnStatus.BTN_NOT_VOUCHER}));
			break;
		case IFBMButton.FBM_VOUCHER:// 制证
			elementStatus.add(new UAPBillStatusElement(IBillStatus.class
					.getName(), new int[] { IBillStatus.CHECKPASS }));// 审批通过
			elementStatus.add(new StatusElementForInt(ReturnVoucherBtnStatus.class.getName(),new int[]{ReturnVoucherBtnStatus.CAN_VOUCHER}));
			
			break;
		case IFBMButton.FBM_CANCELVOUCHER://取消制证
			elementStatus.add(new UAPBillStatusElement(IBillStatus.class
					.getName(), new int[] { IBillStatus.CHECKPASS }));// 审批通过
			elementStatus.add(new StatusElementForInt(ReturnVoucherBtnStatus.class.getName(),new int[]{ReturnVoucherBtnStatus.CAN_CANCELVOUCHER}));
			break;
		case IFBMButton.ReturnBill_TransToSFBill://转出
			elementStatus.add(new StatusElementForInt(TransformBtnStatus.class.getName(),new int[]{TransformBtnStatus.BTN_CAN_TRANSFORM}));
			break;
		case IFBMButton.ReturnBill_CancelTrans://取消转出
			elementStatus.add(new StatusElementForInt(TransformBtnStatus.class.getName(),new int[]{TransformBtnStatus.BTN_CAN_UNTRANSFORM}));
			break;
		}
		
		if(elementStatus!=null && elementStatus.size() > 0){
			rule.add(elementStatus);
		}
		return rule;
	}
}
