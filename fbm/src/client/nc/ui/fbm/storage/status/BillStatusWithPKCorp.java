package nc.ui.fbm.storage.status;

import nc.ui.pub.ClientEnvironment;
import nc.ui.trade.base.AbstractBillUI;
import nc.vo.engine.status.AbstractBillUIStatus;
import nc.vo.fbm.pub.constant.IFBMStatus;
import nc.vo.pub.SuperVO;
import nc.vo.trade.pub.IBillStatus;

/**
 * ���ص���¼�빫˾�뵥��״̬�ϲ����״̬
 * @author xwq
 *
 * 2008-8-29
 */
public class BillStatusWithPKCorp extends AbstractBillUIStatus<Integer>{

	public static int BTN_DISABLE = 0;
	public static int CENTER_CHECKPASS = 1;//�������¼���λ¼��,�����ͨ��
	public static int UNIT_SUBMIT = 2;//��λ¼�룬���ύ
	
	
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
		
		//��鵥��״̬
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
