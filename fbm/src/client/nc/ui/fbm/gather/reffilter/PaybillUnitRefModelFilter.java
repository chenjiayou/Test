/**
 *
 */
package nc.ui.fbm.gather.reffilter;

import nc.ui.pub.bill.BillItem;
import nc.ui.tm.framework.ref.filter.BillItemRefModelFilter;

/**
 * <p>
 * 付票单位参照过滤掉供应商
 * <p>创建人：lpf
 * <b>日期：2007-12-13
 *
 */
public class PaybillUnitRefModelFilter extends BillItemRefModelFilter {

	/**
	 * @param billitem
	 */
	public PaybillUnitRefModelFilter(BillItem billitem) {
		super(billitem);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see nc.ui.tm.framework.ref.filter.AbstractRefModelFilter#getSelfFilterString()
	 */
	@Override
	protected String getSelfFilterString() {
		return " custflag<>'1' ";
	}

}
