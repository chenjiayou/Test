package nc.ui.fbm.discount.status;

import java.util.ArrayList;
import java.util.List;

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
 * 
 ***********************************************************
 * 日期：2008-3-28							   
 * 程序员:吴二山 							   
 * 功能：贴现办理制证按钮只有在办理成功时可用						   
 ***********************************************************
 */
public class DiscountStatusBuildFactory extends UAPButtonStatusBuildFactory {

	/**
	 * 
	 */
	public DiscountStatusBuildFactory() {
	}

	protected List<List> initSelfButtonRule(List<List> rule, ButtonVO btnVO) {
		rule.clear();
		ArrayList<IStatusCheck<Integer>> elementStatus = new ArrayList<IStatusCheck<Integer>>();
		switch (btnVO.getBtnNo()) {
		
		case IBillButton.CancelAudit:
			elementStatus.add(new UAPBillOperateElement(IBillStatus.class.getName(), new int[]{IBillStatus.CHECKPASS}));
			elementStatus.add(new StatusElementForInt(FBMVoucherBtnStatus.class.getName(),new int[]{FBMVoucherBtnStatus.CAN_VOUCHER}));
			break;
		case IFBMButton.FBM_VOUCHER:// 制证
			elementStatus.add(new UAPBillStatusElement(IBillStatus.class
					.getName(), new int[] { IBillStatus.CHECKPASS }));// 审批通过
			elementStatus.add(new StatusElementForInt(IDiscountStatus.class
					.getName(), new int[] { IDiscountStatus.SUCCESS }));// 同时办理成功
			elementStatus.add(new StatusElementForInt(FBMVoucherBtnStatus.class.getName(),new int[]{FBMVoucherBtnStatus.CAN_VOUCHER}));
			break;
		case IFBMButton.FBM_CANCELVOUCHER:
			elementStatus.add(new UAPBillStatusElement(IBillStatus.class
					.getName(), new int[] { IBillStatus.CHECKPASS }));// 审批通过
			elementStatus.add(new StatusElementForInt(IDiscountStatus.class
					.getName(), new int[] { IDiscountStatus.SUCCESS }));// 同时办理成功
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