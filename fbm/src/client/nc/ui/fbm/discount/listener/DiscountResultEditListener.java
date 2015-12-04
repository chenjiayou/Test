package nc.ui.fbm.discount.listener;

import nc.ui.fbm.pub.AbstractItemEditListener;
import nc.ui.fbm.pub.FBMManageUI;
import nc.ui.pub.bill.BillEditEvent;
import nc.vo.fbm.discount.DiscountVO;
import nc.vo.fbm.pub.constant.FbmBusConstant;

/**
 * 
 * <p>
 *	贴现办理界面贴现办理结果编辑监听类
 * <p>创建人：bsrl
 * <b>日期：2007-09
 *
 */
public class DiscountResultEditListener extends AbstractItemEditListener{
	/**
	 * 
	 */
	public DiscountResultEditListener() {
	}

	/**
	 * @param ui
	 * @param itemKey
	 */
	public DiscountResultEditListener(FBMManageUI ui, String itemKey) {
		super(ui, itemKey);
	}

	/**
	 * @param ui
	 * @param itemKey
	 * @param pos
	 */
	public DiscountResultEditListener(FBMManageUI ui, String itemKey, int pos) {
		super(ui, itemKey, pos);
	}
	
	@Override
	public void responseEditEvent(BillEditEvent editEvent) {
			String discountresult = (String)(getUI().getBillCardPanel().getHeadItem(editEvent.getKey()).getValueObject());
			
			if(discountresult.equalsIgnoreCase(FbmBusConstant.DISCOUNT_RESULT_DISABLE)) {
				getUI().getBillCardPanel().getHeadItem(DiscountVO.MONEYY).setValue(null);
				getUI().getBillCardPanel().getHeadItem(DiscountVO.DISCOUNTCHARGE).setValue(null);
				getUI().getBillCardPanel().getHeadItem(DiscountVO.DISCOUNTINTEREST).setValue(null);
				getUI().getBillCardPanel().getHeadItem(DiscountVO.DISCOUNTYRATE).setValue(null);
				getUI().getBillCardPanel().getHeadItem(DiscountVO.RATEDAYNUM).setValue(null);
				getUI().getBillCardPanel().getHeadItem(DiscountVO.DISCOUNTDELAYDAYNUM).setValue(null);
				
				getUI().getBillCardPanel().getHeadItem(DiscountVO.MONEYY).setEnabled(false);
				getUI().getBillCardPanel().getHeadItem(DiscountVO.DISCOUNTCHARGE).setEnabled(false);
				getUI().getBillCardPanel().getHeadItem(DiscountVO.DISCOUNTINTEREST).setEnabled(false);
				getUI().getBillCardPanel().getHeadItem(DiscountVO.DISCOUNTYRATE).setEnabled(false);
				getUI().getBillCardPanel().getHeadItem(DiscountVO.RATEDAYNUM).setEnabled(false);
				getUI().getBillCardPanel().getHeadItem(DiscountVO.DISCOUNTDELAYDAYNUM).setEnabled(false);
				getUI().getBillCardPanel().getHeadItem(DiscountVO.FAILREASON).setEnabled(true);
				
				getUI().getBillCardPanel().getHeadItem(DiscountVO.CHARGEPLANITEM).setEnabled(false);
				getUI().getBillCardPanel().getHeadItem(DiscountVO.INTERESTPLANITEM).setEnabled(false);
				getUI().getBillCardPanel().getHeadItem(DiscountVO.BALANCEPLANITEM).setEnabled(false);
				getUI().getBillCardPanel().getHeadItem(DiscountVO.FBMPLANITEM).setEnabled(false);
				
				getUI().getBillCardPanel().getHeadItem(DiscountVO.CHARGEPLANITEM).setValue(null);
				getUI().getBillCardPanel().getHeadItem(DiscountVO.INTERESTPLANITEM).setValue(null);
				getUI().getBillCardPanel().getHeadItem(DiscountVO.BALANCEPLANITEM).setValue(null);
				getUI().getBillCardPanel().getHeadItem(DiscountVO.FBMPLANITEM).setValue(null);
			} else {	
				getUI().getBillCardPanel().getHeadItem(DiscountVO.FAILREASON).setValue(null);
				getUI().getBillCardPanel().getHeadItem(DiscountVO.MONEYY).setEnabled(true);
				getUI().getBillCardPanel().getHeadItem(DiscountVO.DISCOUNTCHARGE).setEnabled(true);
				getUI().getBillCardPanel().getHeadItem(DiscountVO.DISCOUNTINTEREST).setEnabled(true);
				getUI().getBillCardPanel().getHeadItem(DiscountVO.DISCOUNTYRATE).setEnabled(true);
				getUI().getBillCardPanel().getHeadItem(DiscountVO.RATEDAYNUM).setEnabled(true);
				getUI().getBillCardPanel().getHeadItem(DiscountVO.DISCOUNTDELAYDAYNUM).setEnabled(true);
				getUI().getBillCardPanel().getHeadItem(DiscountVO.FAILREASON).setEnabled(false);
				
				getUI().getBillCardPanel().getHeadItem(DiscountVO.CHARGEPLANITEM).setEnabled(true);
				getUI().getBillCardPanel().getHeadItem(DiscountVO.INTERESTPLANITEM).setEnabled(true);
				getUI().getBillCardPanel().getHeadItem(DiscountVO.BALANCEPLANITEM).setEnabled(true);
				getUI().getBillCardPanel().getHeadItem(DiscountVO.FBMPLANITEM).setEnabled(true);
			}
	}
}
