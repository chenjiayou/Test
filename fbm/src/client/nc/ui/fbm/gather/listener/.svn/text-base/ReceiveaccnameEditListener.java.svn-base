/**
 *
 */
package nc.ui.fbm.gather.listener;

import nc.ui.fbm.pub.AbstractItemEditListener;
import nc.ui.fbm.pub.FBMManageUI;
import nc.ui.pub.bill.BillEditEvent;
import nc.vo.pub.lang.UFBoolean;

/**
 * <p>
 * 
 * <p>
 * 创建人：lpf <b>日期：2007-12-11
 * 
 */
public class ReceiveaccnameEditListener extends AbstractItemEditListener {
	String filterKey = null;

	/**
	 * 
	 */
	public ReceiveaccnameEditListener() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ui
	 * @param itemKey
	 */
	public ReceiveaccnameEditListener(FBMManageUI ui, String itemKey) {
		super(ui, itemKey);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ui
	 * @param itemKey
	 * @param pos
	 */
	public ReceiveaccnameEditListener(FBMManageUI ui, String itemKey, int pos) {
		super(ui, itemKey, pos);
		// TODO Auto-generated constructor stub
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nc.ui.fbm.pub.AbstractItemEditListener#responseEditEvent(nc.ui.pub.bill
	 * .BillEditEvent)
	 */
	public ReceiveaccnameEditListener(FBMManageUI ui, String itemKey,
			String filterKey) {
		super(ui, itemKey);
		this.filterKey = filterKey;
	}

	@Override
	public void responseEditEvent(BillEditEvent editEvent) {
		if (getUI().getBillCardPanel().getHeadItem(filterKey) != null) {
			getUI().getBillCardPanel().getHeadItem(filterKey).setValue(UFBoolean.TRUE);
		}
	}

}
