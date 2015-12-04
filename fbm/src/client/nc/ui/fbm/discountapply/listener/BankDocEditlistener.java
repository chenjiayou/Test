package nc.ui.fbm.discountapply.listener;

import nc.ui.fbm.pub.AbstractItemEditListener;
import nc.ui.fbm.pub.FBMManageUI;
import nc.ui.pub.beans.UIRefPane;
import nc.ui.pub.bill.BillEditEvent;
import nc.ui.pub.bill.BillItem;

public class BankDocEditlistener extends AbstractItemEditListener {

	private String accKey = null;
	
	public BankDocEditlistener(FBMManageUI ui,String bankKey,String accKey){
		super(ui,bankKey);
		this.accKey = accKey;
	}
	@Override
	public void responseEditEvent(BillEditEvent editEvent) {
		 
		BillItem accItem = getUI().getBillCardPanel().getHeadItem(accKey);
		if(accItem != null){
			((UIRefPane)accItem.getComponent()).setPK(accItem.getValueObject());
		}
	}

}
