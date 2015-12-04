package nc.ui.fbm.discountapply.reffilter;

import nc.ui.fbm.pub.FBMManageUI;
import nc.ui.tm.framework.ref.filter.BillItemRefModelFilter;
import nc.ui.trade.base.AbstractBillUI;

public class BankAccRefModelFilter extends BillItemRefModelFilter {

	private String bankdocKey = null;//银行档案字段名称
	
	public BankAccRefModelFilter(AbstractBillUI ui) {
		super(ui);
	}

	public BankAccRefModelFilter() {
		super();
	}

	public String getBankdocKey() {
		return bankdocKey;
	}

	public void setBankdocKey(String bankdocKey) {
		this.bankdocKey = bankdocKey;
	}



	@Override
	protected String getSelfFilterString() {
		// TODO Auto-generated method stub
		Object pk_bankdoc = ((FBMManageUI)ui).getBillCardPanel().getHeadItem(bankdocKey).getValueObject();
		if(pk_bankdoc!=null){
			return " pk_bankdoc ='"+pk_bankdoc+"'";
		}
		return null;
	}

}
