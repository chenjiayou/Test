package nc.ui.fbm.discount.listener;

import nc.ui.fbm.pub.AbstractItemEditListener;
import nc.ui.fbm.pub.FBMManageUI;
import nc.ui.pub.bill.BillEditEvent;
import nc.vo.fbm.discount.DiscountVO;

/**
 * 
 * <p>
 *	贴现办理界面申请单编号编辑监听器类
 * <p>创建人：bsrl
 * <b>日期：2007-09
 *
 */
public class DiscountApplyEditListener extends AbstractItemEditListener{
	/**
	 * 
	 */
	public DiscountApplyEditListener() {
	}

	/**
	 * @param ui
	 * @param itemKey
	 */
	public DiscountApplyEditListener(FBMManageUI ui, String itemKey) {
		super(ui, itemKey);
	}

	/**
	 * @param ui
	 * @param itemKey
	 * @param pos
	 */
	public DiscountApplyEditListener(FBMManageUI ui, String itemKey, int pos) {
		super(ui, itemKey, pos);
	}

	/* (non-Javadoc)
	 * @see nc.ui.fbm.pub.AbstractItemEditListener#responseEditEvent(nc.ui.pub.bill.BillEditEvent)
	 */
	@Override
	public void responseEditEvent(BillEditEvent editEvent) {
		getUI().getBillCardPanel().getHeadItem(DiscountVO.HOLDERACC).setValue(null);
    	getUI().getBillCardPanel().getHeadItem(DiscountVO.HOLDERBANK).setValue(null);
    	getUI().fireCardAfterEdit(DiscountVO.YBBZ);
	}
}
