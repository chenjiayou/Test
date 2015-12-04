package nc.ui.fbm.storage.status;

import nc.bs.framework.common.NCLocator;
import nc.itf.uap.bd.cust.ICuBasDocQry;
import nc.ui.pub.ClientEnvironment;
import nc.ui.trade.base.AbstractBillUI;
import nc.vo.bd.b08.CubasdocVO;
import nc.vo.bd.b08.CustBasVO;
import nc.vo.engine.status.AbstractBillUIStatus;
import nc.vo.pub.SuperVO;
import nc.vo.pub.lang.UFBoolean;

/**
 * ���˰�ť״̬����
 * @author xwq
 *
 * 2008-7-30
 */
public class TallyStatus extends AbstractBillUIStatus<Integer> {
	
	public static int BTN_DISABLE = 0;//��������˻�ȡ������
	public static int UNIT_CAN_TALLY = 1;//�������
	public static int UNIT_CAN_CANCELTALLY = 2;//����ȡ������
	
	private String tallyflagKey;
	private String do_corpKey;
	
	public TallyStatus(AbstractBillUI ui,String do_corpKey,String tallyflagKey) {
		super(ui);
		this.tallyflagKey = tallyflagKey;
		this.do_corpKey = do_corpKey;
	}

	public Integer getStatus() throws Exception {
		if(getUI().getBufferData().getCurrentVO() == null){
			return BTN_DISABLE;
		}
		
		SuperVO parentVO = (SuperVO)getUI().getBufferData().getCurrentVO().getParentVO();
		UFBoolean tallyflag = (UFBoolean)parentVO.getAttributeValue(tallyflagKey);
		String docorp = (String)parentVO.getAttributeValue(do_corpKey);
		String currcorp = ClientEnvironment.getInstance().getCorporation().getPrimaryKey();
		
		if(currcorp.equals(docorp)){
			if(tallyflag.booleanValue()){//��λ�Ѽ���
				return UNIT_CAN_CANCELTALLY;
			}else{//��λδ����
				return UNIT_CAN_TALLY;
			}
		}
		
		return BTN_DISABLE;
	}

	public String getStatusKindName() {
		// TODO Auto-generated method stub
		return TallyStatus.class.getName();
	}

}
