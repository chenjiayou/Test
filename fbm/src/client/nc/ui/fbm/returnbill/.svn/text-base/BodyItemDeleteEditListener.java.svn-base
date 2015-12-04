/**
 *
 */
package nc.ui.fbm.returnbill;

import nc.ui.fbm.pub.AbstractItemEditListener;
import nc.ui.fbm.pub.FBMManageUI;
import nc.ui.pub.bill.BillEditEvent;

/**
 * <p>
 * 清空表体行
 * <p>创建人：lpf
 * <b>日期：2007-11-6
 *
 */
public class BodyItemDeleteEditListener extends AbstractItemEditListener {

	/**
	 * 
	 */
	public BodyItemDeleteEditListener() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ui
	 * @param itemKey
	 */
	public BodyItemDeleteEditListener(FBMManageUI ui, String itemKey) {
		super(ui, itemKey);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ui
	 * @param itemKey
	 * @param pos
	 */
	public BodyItemDeleteEditListener(FBMManageUI ui, String itemKey, int pos) {
		super(ui, itemKey, pos);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see nc.ui.fbm.pub.AbstractItemEditListener#responseEditEvent(nc.ui.pub.bill.BillEditEvent)
	 */
	@Override
	public void responseEditEvent(BillEditEvent editEvent) {
		getUI().getBillCardPanel().getBodyPanel().getTable().selectAll();
		int row = getUI().getBillCardPanel().getRowCount();
		if(row==-1)
			return;
		for (int i = 0; i < row; i++) {
			getUI().getBillCardPanel().delLine();
		}
	}

}
