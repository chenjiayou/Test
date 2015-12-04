package nc.ui.fbm.gather.listener;

import nc.itf.cdm.util.CommonUtil;
import nc.ui.fbm.gather.refmodel.FBMBankRefModel;
import nc.ui.fbm.pub.AbstractItemEditListener;
import nc.ui.fbm.pub.FBMManageUI;
import nc.ui.pub.beans.UIRefPane;
import nc.ui.pub.bill.BillEditEvent;

public class BankEditListener extends AbstractItemEditListener{

	private String banknameKey;
	
	public BankEditListener(FBMManageUI ui,String itemKey,String banknameKey){
		super(ui,itemKey);
		this.banknameKey = banknameKey;
	}
	
	@Override
	public void responseEditEvent(BillEditEvent editEvent) {
		// TODO Auto-generated method stub
		UIRefPane bankRefPane = (UIRefPane)getUI().getBillCardPanel().getHeadItem(getItemKey()).getComponent();
		
		if(bankRefPane.getRefModel() instanceof FBMBankRefModel){
			((FBMBankRefModel)bankRefPane.getRefModel()).matchData(FBMBankRefModel.BANKDOC_NAME, bankRefPane.getText());
		}
		String pk_bankdoc = bankRefPane.getRefPK();
		if(CommonUtil.isNull(pk_bankdoc)){
			getUI().getBillCardPanel().getHeadItem(banknameKey).setValue( bankRefPane.getText());
		}
	}

}
