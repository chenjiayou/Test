package nc.ui.fbm.reciept.status;

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
import nc.ui.fbm.pub.btnstatus.FBMTallyBtnStatus;

public class RecieptStatusBuildFactory  extends UAPButtonStatusBuildFactory {

	protected List<List> initSelfButtonRule(List<List> rule, ButtonVO btnVO) {
		rule.clear();
		ArrayList<IStatusCheck<Integer>> elementStatus = new ArrayList<IStatusCheck<Integer>>();;
		switch (btnVO.getBtnNo()) {
			case IFBMButton.FBM_VOUCHER:
				elementStatus.add(new UAPBillStatusElement(IBillStatus.class.getName(), new int[]{IBillStatus.CHECKPASS}));
				elementStatus.add(new StatusElementForInt(FBMVoucherBtnStatus.class.getName(),new int[]{FBMVoucherBtnStatus.CAN_VOUCHER}));
				break;
			case IFBMButton.FBM_CANCELVOUCHER:
				elementStatus.add(new UAPBillStatusElement(IBillStatus.class.getName(), new int[]{IBillStatus.CHECKPASS}));
				elementStatus.add(new StatusElementForInt(FBMVoucherBtnStatus.class.getName(),new int[]{FBMVoucherBtnStatus.CAN_CANCELVOUCHER}));
			break;
			case IFBMButton.FBM_TALLY:
				elementStatus.add(new UAPBillStatusElement(IBillStatus.class.getName(), new int[]{IBillStatus.CHECKPASS}));
				elementStatus.add(new StatusElementForInt(FBMTallyBtnStatus.class.getName(),new int[]{FBMTallyBtnStatus.CAN_TALLY}));
				break;
			case IFBMButton.FBM_CANCELTALLY:
				elementStatus.add(new UAPBillStatusElement(IBillStatus.class.getName(), new int[]{IBillStatus.CHECKPASS}));
				elementStatus.add(new StatusElementForInt(FBMTallyBtnStatus.class.getName(),new int[]{FBMTallyBtnStatus.CAN_CANCELTALLY}));
				break;	
		}
		
		if(elementStatus!=null && elementStatus.size() > 0){
			rule.add(elementStatus);
		}
		return rule;
	}
}
