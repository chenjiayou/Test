package nc.ui.fbm.pub.btnstatus;

import nc.ui.trade.base.AbstractBillUI;
import nc.vo.engine.status.AbstractBillUIStatus;
import nc.vo.pub.lang.UFBoolean;

/**
 * 票据公用记账和取消记账按钮的判断类
 * @author xwq
 *
 * 2008-8-21
 */
public class FBMTallyBtnStatus extends AbstractBillUIStatus<Integer>{

	public static int BTN_DISABLE = 0;
	public static int CAN_TALLY = 1;
	public static int CAN_CANCELTALLY = 2;
	
	private String tallyKey;
	
	private FBMTallyBtnStatus(AbstractBillUI ui) {
		super(ui);
	}
	
	public FBMTallyBtnStatus(AbstractBillUI ui,String tallyKey) {
		super(ui);
		this.tallyKey = tallyKey;
	}

	public Integer getStatus() throws Exception {
		if(getUI().getBufferData().getCurrentVO()==null || getUI().getBufferData().getCurrentVO().getParentVO() == null){
			return BTN_DISABLE;
		}
		Object o = getUI().getBufferData().getCurrentVO().getParentVO().getAttributeValue(tallyKey);
		if(o == null){
			return BTN_DISABLE;
		}
		UFBoolean b = (UFBoolean)o;
		
		if(b.booleanValue()){
			return CAN_CANCELTALLY;
		}
		return CAN_TALLY;
	}

	public String getStatusKindName() {
		// TODO Auto-generated method stub
		return FBMTallyBtnStatus.class.getName();
	}

}
