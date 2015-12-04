package nc.ui.fbm.reckon.reffilter;

import nc.ui.pub.bill.BillItem;
import nc.ui.tm.framework.ref.filter.BillItemRefModelFilter;

/**
 * <p>
 * ������������������ڹ�������
 * <p>�����ˣ�bsrl
 * <b>���ڣ�2007-10-31
 *
 */
public class ReckonDateRefFilter  extends BillItemRefModelFilter{
	public ReckonDateRefFilter(BillItem billitem) {
		super(billitem);
	}

	@Override
	protected String getSelfFilterString() {
		String refReckonDate= (String)getBillItem().getValueObject();
		if(refReckonDate != null && refReckonDate.trim().length() > 0) {
		    return " enddate <= '" + refReckonDate + "'";
		}else{
			return null;
		}
	}
}