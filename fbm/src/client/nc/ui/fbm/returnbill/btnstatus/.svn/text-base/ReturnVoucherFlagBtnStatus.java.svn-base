package nc.ui.fbm.returnbill.btnstatus;

import nc.ui.trade.base.AbstractBillUI;
import nc.vo.engine.status.AbstractBillUIStatus;
import nc.vo.pub.CircularlyAccessibleValueObject;
import nc.vo.pub.lang.UFBoolean;

public class ReturnVoucherFlagBtnStatus extends AbstractBillUIStatus<Integer>{
	public static int BTN_DISABLE = 0;
	public static int BTN_NOT_VOUCHER = 1;//未制证
	public static int BTN_HAS_VOUCHER =2 ;//已制证
	private String voucherKey;//制证字段编码
	
	
	private ReturnVoucherFlagBtnStatus(AbstractBillUI ui) {
		super(ui);
	}
	
	public ReturnVoucherFlagBtnStatus(AbstractBillUI ui,String voucherKey){
		super(ui);
		this.voucherKey = voucherKey;
	}
	
	public Integer getStatus() throws Exception {
		if(getUI().getBufferData().getCurrentVO()==null || getUI().getBufferData().getCurrentVO().getParentVO() == null){
			return BTN_DISABLE;
		}
		CircularlyAccessibleValueObject returnVo = getUI().getBufferData().getCurrentVO().getParentVO();
		if(getUI().getBufferData().getCurrentVO()==null || returnVo == null){
			return BTN_DISABLE;
		}
		
		Object o = getUI().getBufferData().getCurrentVO().getParentVO().getAttributeValue(voucherKey);
	
		UFBoolean b = (UFBoolean)o;
		
		if(b.booleanValue()){
			return BTN_HAS_VOUCHER;
		}
		return BTN_NOT_VOUCHER;
	}

	public String getStatusKindName() {
		// TODO Auto-generated method stub
		return ReturnVoucherFlagBtnStatus.class.getName();
	}

}
