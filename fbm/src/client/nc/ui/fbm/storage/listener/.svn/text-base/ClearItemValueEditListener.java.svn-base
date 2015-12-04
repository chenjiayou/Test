/**
 *
 */
package nc.ui.fbm.storage.listener;

import nc.ui.fbm.pub.AbstractItemEditListener;
import nc.ui.fbm.pub.FBMManageUI;
import nc.ui.pub.bill.BillEditEvent;

/**
 * <p>
 * 清空某一项值
 * <p>创建人：lpf
 * <b>日期：2007-11-29
 *
 */
public class ClearItemValueEditListener extends AbstractItemEditListener {
	private String clearItemKey;
	/**
	 * 
	 */
	public ClearItemValueEditListener() {
		// TODO Auto-generated constructor stub
	}

	public ClearItemValueEditListener(FBMManageUI ui, String itemKey,
			String clearItemKey) {
		super(ui, itemKey);
		this.clearItemKey = clearItemKey;
	}

	/**
	 * @param ui
	 * @param itemKey
	 */
	public ClearItemValueEditListener(FBMManageUI ui, String itemKey) {
		super(ui, itemKey);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see nc.ui.fbm.pub.AbstractItemEditListener#responseEditEvent(nc.ui.pub.bill.BillEditEvent)
	 */
	@Override
	public void responseEditEvent(BillEditEvent editEvent) {
		getUI().getBillCardPanel().getHeadItem(clearItemKey).setValue(null);
	}

}
