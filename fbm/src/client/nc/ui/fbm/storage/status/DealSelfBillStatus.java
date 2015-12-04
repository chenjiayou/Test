package nc.ui.fbm.storage.status;

import nc.ui.pub.ClientEnvironment;
import nc.ui.trade.base.AbstractBillUI;
import nc.vo.engine.status.AbstractBillUIStatus;
import nc.vo.pub.SuperVO;

/**
 * 判断是否处理登录公司的业务单据
 * 
 * @author xwq
 *
 * 2008-8-2
 */
public class DealSelfBillStatus extends AbstractBillUIStatus<Integer> {

	public static int BTN_DISABLE = 0;
	public static int DEAL_SELF_BILL = 1;
	
	private String billcorpKey;//标识业务单据公司的字段
	
	public DealSelfBillStatus(AbstractBillUI ui,String billcorpKey) {
		super(ui);
		this.billcorpKey = billcorpKey;
		// TODO Auto-generated constructor stub
	}

	public Integer getStatus() throws Exception {
		// TODO Auto-generated method stub
		if(getUI().getBufferData().getCurrentVO() == null){
			return BTN_DISABLE;
		}
		String loginCorp = ClientEnvironment.getInstance().getCorporation().getPrimaryKey();
		SuperVO vo = (SuperVO)getUI().getBufferData().getCurrentVO().getParentVO();
		if(loginCorp.equals(vo.getAttributeValue(billcorpKey))){
			return DEAL_SELF_BILL;
		}
		
		return BTN_DISABLE;
	}

	public String getStatusKindName() {
		// TODO Auto-generated method stub
		return DealSelfBillStatus.class.getName();
	}

}
