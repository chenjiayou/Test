package nc.ui.fbm.endore.listener;

import nc.ui.fbm.pub.AbstractItemEditListener;
import nc.ui.fbm.pub.FBMClientInfo;
import nc.ui.fbm.pub.FBMManageUI;
import nc.ui.pub.beans.UIComboBox;
import nc.ui.pub.beans.UIRefPane;
import nc.ui.pub.bill.BillEditEvent;
import nc.vo.fbm.discount.DiscountVO;
import nc.vo.fbm.endore.EndoreVO;
import nc.vo.fbm.pub.constant.FbmBusConstant;

public class EndorePKSourceListener extends AbstractItemEditListener {

	
	public EndorePKSourceListener() {
	}

	public EndorePKSourceListener(FBMManageUI ui, String itemKey) {
		super(ui, itemKey);
	}
	
	
	@Override
	public void responseEditEvent(BillEditEvent editEvent) {
		//点击票据编号时，票据类别的判断。私有与统管。	
		if ((FBMClientInfo.isSettleCenter())) {
			String pk_corp = (String) ((UIRefPane) editEvent.getSource())
					.getRefModel().getValue("pk_corp");
			String cur_pk_corp = FBMClientInfo.getCorpPK();
			if (cur_pk_corp.equals(pk_corp)) {// 设置为私有
				((UIComboBox) getUI().getBillCardPanel().getHeadItem(
						DiscountVO.OPBILLTYPE).getComponent())
						.setSelectedItem(FbmBusConstant.BILL_PRIVACY);
			} else {
				((UIComboBox) getUI().getBillCardPanel().getHeadItem(
						DiscountVO.OPBILLTYPE).getComponent())
						.setSelectedItem(FbmBusConstant.BILL_UNISTORAGE);
			}
		}
		
		//点击票据编号时，对被背书单进行判断。
		Object pk_endorsee  = getUI().getBillCardPanel().getHeadItem(EndoreVO.ENDORSEE).getValueObject();
		((UIRefPane)getUI().getBillCardPanel().getHeadItem(EndoreVO.ENDORSEE).getComponent()).setPK(pk_endorsee);
	}
}
			
	


