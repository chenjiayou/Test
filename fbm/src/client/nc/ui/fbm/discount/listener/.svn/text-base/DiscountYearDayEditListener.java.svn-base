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
 *	贴现界面利率天数编辑listener
 * <p>创建人：bsrl
 * <b>日期：2007-11-19
 *
 */
public class DiscountYearDayEditListener  extends AbstractItemEditListener {

	/**
	 * 
	 */
	public DiscountYearDayEditListener() {
	}

	/**
	 * @param ui
	 * @param itemKey
	 */
	public DiscountYearDayEditListener(FBMManageUI ui, String itemKey) {
		super(ui, itemKey);
	}

	/**
	 * @param ui
	 * @param itemKey
	 * @param pos
	 */
	public DiscountYearDayEditListener(FBMManageUI ui, String itemKey, int pos) {
		super(ui, itemKey, pos);
	}
	
	@Override
	public void responseEditEvent(BillEditEvent editEvent) {
		Integer ratedaynum = null;
		if(getUI().getBillCardPanel().getHeadItem(editEvent.getKey()).getValueObject() != null &&
				getUI().getBillCardPanel().getHeadItem(editEvent.getKey()).getValueObject().toString().trim().length() > 0) {
			ratedaynum = (Integer) getUI().getBillCardPanel().getHeadItem(editEvent.getKey()).getValueObject();
		}

	    Integer discountdelayday = null;
	    if(getUI().getBillCardPanel().getHeadItem(DiscountVO.DISCOUNTDELAYDAYNUM).getValueObject() != null && getUI().getBillCardPanel().getHeadItem(DiscountVO.DISCOUNTDELAYDAYNUM).getValueObject().toString().trim().length() > 0) {
		    discountdelayday = new Integer((String)getUI().getBillCardPanel().getHeadItem(DiscountVO.DISCOUNTDELAYDAYNUM).getValueObject());
	    } else {
	    	discountdelayday = new Integer(0);
	    }
	    
	    String discountDate = (String)getUI().getBillCardPanel().getHeadItem(DiscountVO.DDISCOUNTDATE).getValueObject();
	    if (null == discountDate || "".equals(discountDate.trim())) {
	    	return ;
	    }
	    
		UFDate txrq = new UFDate (discountDate);
		
		String strrate = (String)getUI().getBillCardPanel().getHeadItem(DiscountVO.DISCOUNTYRATE).getValueObject();
		UFDouble ratevalue = null;
		if(strrate != null && strrate.trim().length() != 0) {
			ratevalue= new UFDouble((String)(getUI().getBillCardPanel().getHeadItem(DiscountVO.DISCOUNTYRATE).getValueObject()));
		}
		if(ratevalue != null) {
			UFDate dqrq = new UFDate((String)getUI().getBillCardPanel().getHeadItem(DiscountVO.DQRQ).getValueObject());
			if(dqrq.after(txrq)) {
				UFDouble hpje = null;
				String currtype = (String)(getUI().getBillCardPanel().getHeadItem(DiscountVO.YBBZ).getValueObject());
				String pk_corp = (String)(getUI().getBillCardPanel().getHeadItem(DiscountVO.PK_CORP).getValueObject());

				String strhpje = null;
				if(getUI().getBillCardPanel().getHeadItem(DiscountVO.HPJE).getValueObject() != null) {
				    strhpje = (String)(getUI().getBillCardPanel().getHeadItem(DiscountVO.HPJE).getValueObject());
				}
				UFDouble discountcharge = null;
				if(getUI().getBillCardPanel().getHeadItem(DiscountVO.DISCOUNTCHARGE).getValueObject() != null && getUI().getBillCardPanel().getHeadItem(DiscountVO.DISCOUNTCHARGE).getValueObject().toString().trim().length() > 0) {
					discountcharge = new UFDouble((String) getUI().getBillCardPanel().getHeadItem(DiscountVO.DISCOUNTCHARGE).getValueObject());
				}
	
				if(strhpje != null && strhpje.trim().length() != 0) {
					hpje = new UFDouble((String)(getUI().getBillCardPanel().getHeadItem(DiscountVO.HPJE).getValueObject()));
					UFDouble discountinterest = null;
		            if(txrq != null && ratedaynum != null) {
		            	discountinterest = DiscountCalculate.calculateDiscountInterest(hpje, txrq, dqrq,currtype, discountdelayday, ratevalue, ratedaynum, pk_corp);
		            		//hpje.multiply(intdate + discountdelayday).multiply(ratevalue).div(100).div(ratedaynum);
						getUI().getBillCardPanel().getHeadItem(DiscountVO.DISCOUNTINTEREST).setValue(discountinterest);
						if(discountcharge != null) {
							getUI().getBillCardPanel().getHeadItem(DiscountVO.MONEYY).setValue(hpje.sub(discountinterest).sub(discountcharge));
						} else {
							getUI().getBillCardPanel().getHeadItem(DiscountVO.MONEYY).setValue(hpje.sub(discountinterest));
						}
		            }
				}
		    }else {
				getUI().getBillCardPanel().getHeadItem(DiscountVO.MONEYY).setValue(null);
				getUI().getBillCardPanel().getHeadItem(DiscountVO.DISCOUNTINTEREST).setValue(null);
			}
		}
	}
}
