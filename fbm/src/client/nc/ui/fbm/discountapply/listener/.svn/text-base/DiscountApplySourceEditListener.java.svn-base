package nc.ui.fbm.discountapply.listener;

import nc.ui.fbm.pub.AbstractItemEditListener;
import nc.ui.fbm.pub.FBMManageUI;
import nc.ui.pub.bill.BillEditEvent;
import nc.vo.fbm.discount.DiscountVO;

/**
 * 
 * <p>
 *	贴现申请界面贴现汇票编辑监听类
 * <p>创建人：bsrl
 * <b>日期：2007-09
 *
 */
public class DiscountApplySourceEditListener extends AbstractItemEditListener{
	/**
	 * 
	 */
	public DiscountApplySourceEditListener() {
	}

	/**
	 * @param ui
	 * @param itemKey
	 */
	public DiscountApplySourceEditListener(FBMManageUI ui, String itemKey) {
		super(ui, itemKey);
	}

	/**
	 * @param ui
	 * @param itemKey
	 * @param pos
	 */
	public DiscountApplySourceEditListener(FBMManageUI ui, String itemKey, int pos) {
		super(ui, itemKey, pos);
	}

	/* (non-Javadoc)
	 * @see nc.ui.fbm.pub.AbstractItemEditListener#responseEditEvent(nc.ui.pub.bill.BillEditEvent)
	 */
	@Override
	public void responseEditEvent(BillEditEvent editEvent) {
	
//		getUI().getBillCardPanel().getHeadItem(DiscountVO.HOLDERACC).setValue(null);
//    	getUI().getBillCardPanel().getHeadItem(DiscountVO.HOLDERBANK).setValue(null);
    	getUI().fireCardAfterEdit(DiscountVO.YBBZ);
    	
    	
    	
//    	new YFBEditListerner(getUI(), DiscountVO.YBBZ,DiscountVO.YBBZ,DiscountVO.MONEYY, DiscountVO.MONEYF,DiscountVO.MONEYB, DiscountVO.FRATE, DiscountVO.BRATE).responseEditEvent(null);
	}
}
