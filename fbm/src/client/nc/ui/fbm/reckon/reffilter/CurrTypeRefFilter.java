package nc.ui.fbm.reckon.reffilter;

import nc.ui.pub.bill.BillItem;
import nc.ui.tm.framework.ref.filter.BillItemRefModelFilter;

/**
 * <p>
 * ��������-���ձ��ֹ���
 * <p>�����ˣ�bsrl
 * <b>���ڣ�2007-11-02
 *
 */
public class CurrTypeRefFilter extends BillItemRefModelFilter{
	public CurrTypeRefFilter(BillItem billitem) {
		super(billitem);
	}

	@Override
	protected String getSelfFilterString() {
		String currtype = (String)getBillItem().getValueObject();
		if(currtype != null && currtype.trim().length() > 0) {
		    return " pk_currtype =  '" + currtype + "'";
		}else{
			return null;
		}
	}
}
