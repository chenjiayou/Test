package nc.ui.fbm.consignbank.listener;

import nc.ui.fbm.pub.AbstractItemEditListener;
import nc.ui.fbm.pub.FBMClientInfo;
import nc.ui.fbm.pub.FBMManageUI;
import nc.ui.pub.beans.UIComboBox;
import nc.ui.pub.beans.UIRefPane;
import nc.ui.pub.bill.BillEditEvent;
import nc.ui.pub.bill.BillItem;
import nc.vo.fbm.consignbank.CollectionVO;
import nc.vo.fbm.discount.DiscountVO;
import nc.vo.fbm.pub.constant.FbmBusConstant;

public class ConsignBankPKSourceListener extends AbstractItemEditListener {

	
	public ConsignBankPKSourceListener() {
	}

	public ConsignBankPKSourceListener(FBMManageUI ui, String itemKey) {
		super(ui, itemKey);
	}
	
	
	@Override
	public void responseEditEvent(BillEditEvent editEvent) {
		//点击票据编号时，票据类别的判断。私有与统管。	
		if ((FBMClientInfo.isSettleCenter())) {
			String pk_corp = (String) ((UIRefPane) editEvent.getSource())
					.getRefModel().getValue("pk_corp");
			String cur_pk_corp = FBMClientInfo.getCorpPK();
			if(pk_corp!=null){
				if (cur_pk_corp.equals(pk_corp)) {// 设置为私有
					((UIComboBox) getUI().getBillCardPanel().getHeadItem(
							CollectionVO.OPBILLTYPE).getComponent())
							.setSelectedItem(FbmBusConstant.BILL_PRIVACY);
					BillItem planitem = getUI().getBillCardPanel().getHeadItem(CollectionVO.FBMPLANITEM);
					planitem.setEnabled(true);
				} else {
					((UIComboBox) getUI().getBillCardPanel().getHeadItem(
							CollectionVO.OPBILLTYPE).getComponent())
							.setSelectedItem(FbmBusConstant.BILL_UNISTORAGE);
					BillItem planitem = getUI().getBillCardPanel().getHeadItem(CollectionVO.FBMPLANITEM);
					planitem.setEnabled(false);
					planitem.setValue(null);
				}
			}
		}
	}
}
			
	


