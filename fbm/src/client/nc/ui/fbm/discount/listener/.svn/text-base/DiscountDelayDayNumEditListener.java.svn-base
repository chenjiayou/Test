package nc.ui.fbm.discount.listener;

import nc.ui.fbm.pub.AbstractItemEditListener;
import nc.ui.fbm.pub.FBMManageUI;
import nc.ui.pub.bill.BillEditEvent;
import nc.vo.fbm.discount.DiscountVO;
import nc.vo.pub.lang.UFDate;
import nc.vo.pub.lang.UFDouble;

/**
 * 
 * <p>
 *	贴现延迟日期编辑监听器
 * <p>创建人：bsrl
 * <b>日期：2007-09
 *
 */
public class DiscountDelayDayNumEditListener extends AbstractItemEditListener{
	/**
	 * 
	 */
	public DiscountDelayDayNumEditListener() {
	}

	/**
	 * @param ui
	 * @param itemKey
	 */
	public DiscountDelayDayNumEditListener(FBMManageUI ui, String itemKey) {
		super(ui, itemKey);
	}

	/**
	 * @param ui
	 * @param itemKey
	 * @param pos
	 */
	public DiscountDelayDayNumEditListener(FBMManageUI ui, String itemKey, int pos) {
		super(ui, itemKey, pos);
	}
	
	@Override
	public void responseEditEvent(BillEditEvent editEvent) {
		    Integer discountdelayday = null;
		    if(getUI().getBillCardPanel().getHeadItem(DiscountVO.DISCOUNTDELAYDAYNUM).getValueObject() != null && getUI().getBillCardPanel().getHeadItem(DiscountVO.DISCOUNTDELAYDAYNUM).getValueObject().toString().trim().length() > 0) {
			    discountdelayday = new Integer((String)getUI().getBillCardPanel().getHeadItem(DiscountVO.DISCOUNTDELAYDAYNUM).getValueObject());
		    } else {
		    	discountdelayday = new Integer(0);
		    }
		    
		    UFDouble ratevalue = null;
		    if(getUI().getBillCardPanel().getHeadItem(DiscountVO.DISCOUNTYRATE).getValueObject() != null && getUI().getBillCardPanel().getHeadItem(DiscountVO.DISCOUNTYRATE).getValueObject().toString().trim().length() > 0) {
				ratevalue = new UFDouble((String)(getUI().getBillCardPanel().getHeadItem(DiscountVO.DISCOUNTYRATE).getValueObject()));
		    }
	
		    String strtxrq = null;
		    if(getUI().getBillCardPanel().getHeadItem(DiscountVO.DDISCOUNTDATE).getValueObject() != null && getUI().getBillCardPanel().getHeadItem(DiscountVO.DDISCOUNTDATE).getValueObject().toString().trim().length() > 0) {
				strtxrq = (String)getUI().getBillCardPanel().getHeadItem(DiscountVO.DDISCOUNTDATE).getValueObject();
		    }
		    
			UFDate txrq = null;
			if(strtxrq != null && strtxrq.trim().length() != 0) {
			    txrq = new UFDate ((String)getUI().getBillCardPanel().getHeadItem(DiscountVO.DDISCOUNTDATE).getValueObject());
			}
			if(txrq != null && ratevalue != null) {
				UFDate dqrq = new UFDate((String)getUI().getBillCardPanel().getHeadItem(DiscountVO.DQRQ).getValueObject());
				if(dqrq.after(txrq)) {
					Integer ratedaynum = (Integer) getUI().getBillCardPanel().getHeadItem(DiscountVO.RATEDAYNUM).getValueObject();
					UFDouble discountcharge = null;
					if(getUI().getBillCardPanel().getHeadItem(DiscountVO.DISCOUNTCHARGE).getValueObject() != null && getUI().getBillCardPanel().getHeadItem(DiscountVO.DISCOUNTCHARGE).getValueObject().toString().length() > 0) {
						discountcharge = new UFDouble((String)getUI().getBillCardPanel().getHeadItem(DiscountVO.DISCOUNTCHARGE).getValueObject());
					}
					UFDouble hpje = null;
					String currtype = (String)(getUI().getBillCardPanel().getHeadItem(DiscountVO.YBBZ).getValueObject());
			    	String pk_corp = (String)(getUI().getBillCardPanel().getHeadItem(DiscountVO.PK_CORP).getValueObject());

					String strhpje = null;
					if(getUI().getBillCardPanel().getHeadItem(DiscountVO.HPJE).getValueObject() != null) {
					    strhpje = (String)(getUI().getBillCardPanel().getHeadItem(DiscountVO.HPJE).getValueObject());
					}
					if(strhpje != null && strhpje.trim().length() != 0) {
						hpje = new UFDouble((String)(getUI().getBillCardPanel().getHeadItem(DiscountVO.HPJE).getValueObject()));
						UFDouble discountinterest = null;
			            if(txrq != null && ratedaynum != null) {
			            	discountinterest = DiscountCalculate.calculateDiscountInterest(hpje, txrq, dqrq,currtype, discountdelayday, ratevalue, ratedaynum, pk_corp);
			            	//discountinterest = hpje.multiply(intdate + discountdelayday).multiply(ratevalue).div(100).div(ratedaynum);
			            }
						getUI().getBillCardPanel().getHeadItem(DiscountVO.DISCOUNTINTEREST).setValue(discountinterest);
						if(discountcharge != null) {
							getUI().getBillCardPanel().getHeadItem(DiscountVO.MONEYY).setValue(hpje.sub(discountinterest).sub(discountcharge));
						} else {
							getUI().getBillCardPanel().getHeadItem(DiscountVO.MONEYY).setValue(hpje.sub(discountinterest));
						}
					}
			    }else {
					getUI().getBillCardPanel().getHeadItem(DiscountVO.MONEYY).setValue(null);
					getUI().getBillCardPanel().getHeadItem(DiscountVO.DISCOUNTINTEREST).setValue(null);
				}
			}
	}
}
