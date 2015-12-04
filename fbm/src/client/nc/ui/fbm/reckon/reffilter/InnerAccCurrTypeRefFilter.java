package nc.ui.fbm.reckon.reffilter;

import nc.ui.pub.bill.BillItem;
import nc.ui.tm.framework.ref.filter.BillItemRefModelFilter;

public class InnerAccCurrTypeRefFilter extends BillItemRefModelFilter{
	public InnerAccCurrTypeRefFilter(BillItem billitem) {
		super(billitem);
	}
	
	@Override
	protected String getSelfFilterString() {
		String currtype = (String)getBillItem().getValueObject();
		if(currtype != null && currtype.trim().length() > 0) {
		    return " bd_accid.pk_currtype =  '" + currtype + "'";
		}else{
			return null;
		}
	}
}
