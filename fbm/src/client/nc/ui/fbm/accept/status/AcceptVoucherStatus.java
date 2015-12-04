package nc.ui.fbm.accept.status;

import nc.ui.trade.base.AbstractBillUI;
import nc.vo.engine.status.AbstractBillUIStatus;
import nc.vo.fbm.acceptbill.AcceptVO;
import nc.vo.pub.AggregatedValueObject;
import nc.vo.pub.lang.UFBoolean;

public class AcceptVoucherStatus extends AbstractBillUIStatus<Integer>{

	public static int BTN_DISABLE = 0;
	public static int CAN_VOUCHER =1;
	public static int CAN_CANCELVOUCHER = 2;
	
	public AcceptVoucherStatus(AbstractBillUI ui) {
		super(ui);
	}
	
	public Integer getStatus() throws Exception {
		// TODO Auto-generated method stub
		AggregatedValueObject currVo = getUI().getBufferData().getCurrentVO();
		if (null != currVo && null != currVo.getParentVO()) {
			UFBoolean unitvoucher = (UFBoolean)currVo.getParentVO().getAttributeValue(AcceptVO.UNITVOUCHER);
			if(unitvoucher!=null){
				if(unitvoucher.booleanValue()){
					return CAN_CANCELVOUCHER;
				}
				return CAN_VOUCHER;
			}
		}
		return BTN_DISABLE;
	}

	public String getStatusKindName() {
		// TODO Auto-generated method stub
		return AcceptVoucherStatus.class.getName();
	}

}
