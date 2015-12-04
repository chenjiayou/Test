package nc.ui.fbm.gather.reffilter;

import nc.ui.pub.bill.BillItem;
import nc.ui.tm.framework.ref.filter.BillItemRefModelFilter;

public class BankdocRefModelFilter extends BillItemRefModelFilter {

	public BankdocRefModelFilter(BillItem billitem) {
		super(billitem);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected String getSelfFilterString() {
		return " (pk_banktype is null or pk_banktype  not in(select pk_banktype from bd_banktype where banktypecode = '9999'))";
	}

}
