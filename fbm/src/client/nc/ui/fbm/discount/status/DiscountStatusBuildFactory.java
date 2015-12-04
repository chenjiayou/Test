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
 * ���ڣ�2008-3-28							   
 * ����Ա:���ɽ 							   
 * ���ܣ����ְ�����֤��ťֻ���ڰ���ɹ�ʱ����						   
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
		case IFBMButton.FBM_VOUCHER:// ��֤
			elementStatus.add(new UAPBillStatusElement(IBillStatus.class
					.getName(), new int[] { IBillStatus.CHECKPASS }));// ����ͨ��
			elementStatus.add(new StatusElementForInt(IDiscountStatus.class
					.getName(), new int[] { IDiscountStatus.SUCCESS }));// ͬʱ����ɹ�
			elementStatus.add(new StatusElementForInt(FBMVoucherBtnStatus.class.getName(),new int[]{FBMVoucherBtnStatus.CAN_VOUCHER}));
			break;
		case IFBMButton.FBM_CANCELVOUCHER:
			elementStatus.add(new UAPBillStatusElement(IBillStatus.class
					.getName(), new int[] { IBillStatus.CHECKPASS }));// ����ͨ��
			elementStatus.add(new StatusElementForInt(IDiscountStatus.class
					.getName(), new int[] { IDiscountStatus.SUCCESS }));// ͬʱ����ɹ�
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