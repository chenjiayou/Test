package nc.ui.fbm.storage.status;

import nc.ui.pub.ClientEnvironment;
import nc.ui.trade.base.AbstractBillUI;
import nc.vo.engine.status.AbstractBillUIStatus;
import nc.vo.fbm.pub.constant.IFBMStatus;
import nc.vo.pub.SuperVO;
import nc.vo.trade.pub.IBillStatus;

/**
 * 返回单据录入公司与单据状态合并后的状态
 * @author xwq
 *
 * 2008-8-29
 */
public class BillStatusWithPKCorp extends AbstractBillUIStatus<Integer>{

	public static int BTN_DISABLE = 0;
	public static int CENTER_CHECKPASS = 1;//中心替下级单位录入,并审核通过
	public static int UNIT_SUBMIT = 2;//单位录入，并提交
	
	
	private String billcorpKey;
	
	public BillStatusWithPKCorp(AbstractBillUI ui,String billcorpKey) {
		super(ui);
		this.billcorpKey = billcorpKey;
	}

	public Integer getStatus() throws Exception {
		if(getUI().getBufferData().getCurrentVO() == null){
			return BTN_DISABLE;
		}
		SuperVO superVO = (SuperVO) getUI().getBufferData().getCurrentVO().getParentVO();
		String billcorpPK = (String)superVO.getAttributeValue(billcorpKey);
		String pk_corp = (String)superVO.getAttributeValue("pk_corp");
		
		//检查单据状态
		int vbillstatus = (Integer)superVO.getAttributeValue("vbillstatus");
		
		
		if(pk_corp.equals(billcorpPK) && vbillstatus == IFBMStatus.SUBMIT){
			return UNIT_SUBMIT;
		}
		
		if(!pk_corp.equals(billcorpPK)&& vbillstatus == IBillStatus.CHECKPASS){
			return CENTER_CHECKPASS;
		}

		return BTN_DISABLE;
	}

	public String getStatusKindName() {
		return BillStatusWithPKCorp.class.getName();
	}
}
