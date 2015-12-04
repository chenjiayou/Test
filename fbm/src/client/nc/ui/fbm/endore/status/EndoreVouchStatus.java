package nc.ui.fbm.endore.status;

import nc.ui.fbm.pub.FBMManageUI;
import nc.ui.trade.base.AbstractBillUI;
import nc.vo.engine.status.AbstractBillUIStatus;
import nc.vo.fbm.endore.EndoreVO;
import nc.vo.fbm.pub.constant.FbmBusConstant;
import nc.vo.pub.AggregatedValueObject;
import nc.vo.pub.SuperVO;

public class EndoreVouchStatus extends AbstractBillUIStatus<Integer> {

	public EndoreVouchStatus(AbstractBillUI ui) {
		super(ui);
	}

	public Integer getStatus() throws Exception {
		int unistorage = IEndoreBillTypeStatus.UNISTORAGE;// Ĭ��ͳ��
		AggregatedValueObject currVo = getUI().getBufferData().getCurrentVO();
		if (null != currVo && null != currVo.getParentVO()) {
			String opBillType = (String)((SuperVO) currVo.getParentVO())
					.getAttributeValue(EndoreVO.OPBILLTYPE);// �õ�Ʊ�����
			if (FbmBusConstant.BILL_PRIVACY.equals(opBillType)) {
				return new Integer(IEndoreBillTypeStatus.PRIVATEBILL);//����
			}
		}
		return new Integer(unistorage);
	}

	public String getStatusKindName() {
		return IEndoreBillTypeStatus.class.getName();
	}
	
	@Override
	public FBMManageUI getUI() {
		// TODO Auto-generated method stub
		return (FBMManageUI) super.getUI();
	}

}
