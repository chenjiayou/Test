package nc.ui.fbm.endore.status;

import java.util.ArrayList;
import java.util.List;

import nc.ui.fbm.pub.IFBMButton;
import nc.ui.fbm.pub.btnstatus.FBMVoucherBtnStatus;
import nc.vo.engine.status.element.IStatusCheck;
import nc.vo.engine.status.element.StatusElementForInt;
import nc.vo.engine.status.element.UAPBillStatusElement;
import nc.vo.engine.status.factory.UAPButtonStatusBuildFactory;
import nc.vo.trade.button.ButtonVO;
import nc.vo.trade.pub.IBillStatus;

public class EndoreStatusBuildFactory extends UAPButtonStatusBuildFactory {

	public EndoreStatusBuildFactory() {
	}

	protected List<List> initSelfButtonRule(List<List> rule, ButtonVO btnVO) {
		rule.clear();
		ArrayList<IStatusCheck<Integer>> elementStatus = new ArrayList<IStatusCheck<Integer>>();
		switch (btnVO.getBtnNo()) {
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
