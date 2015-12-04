package nc.ui.fbm.pub.btnstatus;

import nc.ui.trade.base.AbstractBillUI;
import nc.vo.engine.status.AbstractBillUIStatus;
import nc.vo.pub.lang.UFBoolean;


/**
 * 票据公用制证和取消制证按钮的判断类
 * @author xwq
 *
 * 2008-8-21
 */
public class FBMVoucherBtnStatus extends AbstractBillUIStatus<Integer>{
	public static int BTN_DISABLE = 0;
	public static int CAN_VOUCHER = 1;
	public static int CAN_CANCELVOUCHER = 2;
	
	private String voucherKey;//制证字段编码
	
	private FBMVoucherBtnStatus(AbstractBillUI ui) {
		super(ui);
	}
	
	public FBMVoucherBtnStatus(AbstractBillUI ui,String voucherKey){
		super(ui);
		this.voucherKey = voucherKey;
	}
	
	public Integer getStatus() throws Exception {
		if(getUI().getBufferData().getCurrentVO()==null || getUI().getBufferData().getCurrentVO().getParentVO() == null){
			return BTN_DISABLE;
		}
		Object o = getUI().getBufferData().getCurrentVO().getParentVO().getAttributeValue(voucherKey);
		if(o == null){
			return CAN_VOUCHER;
		}
		UFBoolean b = (UFBoolean)o;
		
		if(b.booleanValue()){
			return CAN_CANCELVOUCHER;
		}
		return CAN_VOUCHER;
	}

	public String getStatusKindName() {
		return FBMVoucherBtnStatus.class.getName();
	}

}
