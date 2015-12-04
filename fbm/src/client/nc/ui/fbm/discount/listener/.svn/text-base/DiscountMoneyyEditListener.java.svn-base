package nc.ui.fbm.discount.listener;

import nc.ui.fbm.pub.AbstractItemEditListener;
import nc.ui.fbm.pub.FBMManageUI;
import nc.ui.pub.bill.BillEditEvent;
import nc.vo.fbm.discount.DiscountVO;
import nc.vo.pub.lang.UFDouble;

/**
 * 
 * <p>
 *	实付贴现金额编辑监听器
 * <p>创建人：bsrl
 * <b>日期：2007-09
 *
 */
public class DiscountMoneyyEditListener extends AbstractItemEditListener{
	/**
	 * 
	 */
	public DiscountMoneyyEditListener() {
	}

	/**
	 * @param ui
	 * @param itemKey
	 */
	public DiscountMoneyyEditListener(FBMManageUI ui, String itemKey) {
		super(ui, itemKey);
	}

	/**
	 * @param ui
	 * @param itemKey
	 * @param pos
	 */
	public DiscountMoneyyEditListener(FBMManageUI ui, String itemKey, int pos) {
		super(ui, itemKey, pos);
	}

	/* (non-Javadoc)
	 * @see nc.ui.fbm.pub.AbstractItemEditListener#responseEditEvent(nc.ui.pub.bill.BillEditEvent)
	 */
	@Override
	public void responseEditEvent(BillEditEvent editEvent) {
		UFDouble hpje = null;
		UFDouble discountmoney = new UFDouble((String) getUI().getBillCardPanel().getHeadItem(editEvent.getKey()).getValueObject());
		hpje = new UFDouble((String)(getUI().getBillCardPanel().getHeadItem(DiscountVO.HPJE).getValueObject()));
		getUI().getBillCardPanel().getHeadItem(DiscountVO.DISCOUNTYRATE).setValue(null);
		UFDouble discountcharge = null;
		if(getUI().getBillCardPanel().getHeadItem(DiscountVO.DISCOUNTCHARGE).getValueObject() != null && getUI().getBillCardPanel().getHeadItem(DiscountVO.DISCOUNTCHARGE).getValueObject().toString().length() > 0) {
			discountcharge = new UFDouble((String)getUI().getBillCardPanel().getHeadItem(DiscountVO.DISCOUNTCHARGE).getValueObject());
		}
		if(discountcharge != null) {
			getUI().getBillCardPanel().getHeadItem(DiscountVO.DISCOUNTINTEREST).setValue(hpje.sub(discountmoney).sub(discountcharge));
		} else {
			getUI().getBillCardPanel().getHeadItem(DiscountVO.DISCOUNTINTEREST).setValue(hpje.sub(discountmoney));
		}
	}
}
