package nc.ui.fbm.discount.listener;

import nc.ui.fbm.pub.AbstractItemEditListener;
import nc.ui.fbm.pub.FBMManageUI;
import nc.ui.pub.bill.BillEditEvent;
import nc.vo.fbm.discount.DiscountVO;
import nc.vo.pub.lang.UFDouble;

/**
 * 
 * <p>
 *	贴现办理和申请界面贴现手续费编辑监听类
 * <p>创建人：bsrl
 * <b>日期：2007-09
 *
 */
public class DiscountChargeEditListener extends AbstractItemEditListener{
	/**
	 * 
	 */
	public DiscountChargeEditListener() {
	}

	/**
	 * @param ui
	 * @param itemKey
	 */
	public DiscountChargeEditListener(FBMManageUI ui, String itemKey) {
		super(ui, itemKey);
	}

	/**
	 * @param ui
	 * @param itemKey
	 * @param pos
	 */
	public DiscountChargeEditListener(FBMManageUI ui, String itemKey, int pos) {
		super(ui, itemKey, pos);
	}
	
	@Override
	public void responseEditEvent(BillEditEvent editEvent) {
	    
		UFDouble discountcharge = null;
		Object objdischarge = getUI().getBillCardPanel().getHeadItem(DiscountVO.DISCOUNTCHARGE).getValueObject();
		if(objdischarge != null && objdischarge.toString().length() > 0 ) {
		    discountcharge = new UFDouble((String)(objdischarge));
		}
		UFDouble discountinterest = null;
		Object objinterest = getUI().getBillCardPanel().getHeadItem(DiscountVO.DISCOUNTINTEREST).getValueObject();
		if(objinterest != null && objinterest.toString().length() > 0 ) {
			discountinterest = new UFDouble((String)(objinterest));
		}
	    UFDouble pmje = new UFDouble((String)(getUI().getBillCardPanel().getHeadItem(DiscountVO.HPJE).getValueObject()));
		UFDouble moneyy = null;
		if(discountcharge == null) {
			getUI().getBillCardPanel().getHeadItem(DiscountVO.MONEYY).setValue(pmje.sub(discountinterest));
			return;
		}
		Object objmoneyy = getUI().getBillCardPanel().getHeadItem(DiscountVO.MONEYY).getValueObject();
		if(objmoneyy != null && objmoneyy.toString().length() > 0) {
			moneyy = new UFDouble((String)(objmoneyy));
		}
		if(moneyy != null && discountcharge != null && discountinterest != null) {
			getUI().getBillCardPanel().getHeadItem(DiscountVO.MONEYY).setValue(pmje.sub(discountinterest).sub(discountcharge));
		} 
	}
	
}
