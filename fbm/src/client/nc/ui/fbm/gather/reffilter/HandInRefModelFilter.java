/**
 *
 */
package nc.ui.fbm.gather.reffilter;

import nc.ui.pub.bill.BillItem;
import nc.ui.tm.framework.ref.filter.BillItemRefModelFilter;

/**
 * <p>
 * 过滤掉参照中手工录入的数据
 * <p>创建人：lpf
 * <b>日期：2007-12-11
 *
 */
public class HandInRefModelFilter extends BillItemRefModelFilter {

	/**
	 * @param billitem
	 */
	public HandInRefModelFilter(BillItem billitem) {
		super(billitem);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see nc.ui.tm.framework.ref.filter.AbstractRefModelFilter#getSelfFilterString()
	 */
	@Override
	protected String getSelfFilterString() {
		//return " isCust='Y' ";
		return null;
	}

}
