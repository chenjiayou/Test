package nc.ui.fbm.discount.listener;

import nc.ui.fbm.pub.AbstractItemEditListener;
import nc.ui.fbm.pub.FBMManageUI;
import nc.ui.pub.beans.UIRefPane;
import nc.ui.pub.bill.BillEditEvent;
import nc.vo.fbm.discount.DiscountVO;
import nc.vo.fbm.register.RegisterVO;
import nc.vo.pub.lang.UFDate;
import nc.vo.pub.lang.UFDouble;

/**
 * 
 * <p>
 *	贴现办理界面贴现汇票字段编辑监听类
 * <p>创建人：bsrl
 * <b>日期：2007-09
 *
 */
public class GatherEditListener extends AbstractItemEditListener {

	/**
	 * 
	 */
	public GatherEditListener() {
	}

	/**
	 * @param ui
	 * @param itemKey
	 */
	public GatherEditListener(FBMManageUI ui, String itemKey) {
		super(ui, itemKey);
	}

	/**
	 * @param ui
	 * @param itemKey
	 * @param pos
	 */
	public GatherEditListener(FBMManageUI ui, String itemKey, int pos) {
		super(ui, itemKey, pos);
	}

	public UFDouble getUfDouble(Object vadouble){
		UFDouble ret = null;
		if(vadouble != null){
			if(vadouble instanceof UFDouble){
				ret = (UFDouble)vadouble;
			}else if(vadouble instanceof String){
				ret = new UFDouble((String)vadouble);
			}
		}
		
		return ret;
	}
	
	/* (non-Javadoc)
	 * @see nc.ui.fbm.pub.AbstractItemEditListener#responseEditEvent(nc.ui.pub.bill.BillEditEvent)
	 */
	@Override
	public void responseEditEvent(BillEditEvent editEvent) {
		String filterKey = DiscountVO.PK_DISCOUNT_APP;
		UIRefPane discountappref=(UIRefPane) getUI().getBillCardPanel().getHeadItem(filterKey).getComponent();
		discountappref.setPK(null);
		
		UIRefPane fbmbillref=(UIRefPane) getUI().getBillCardPanel().getHeadItem(editEvent.getKey()).getComponent();
//		getUI().getBillCardPanel().getHeadItem(DiscountVO.HOLDERACC).setValue(null);
//    	getUI().getBillCardPanel().getHeadItem(DiscountVO.HOLDERBANK).setValue(null);
    	UFDouble hpje = getUfDouble(fbmbillref.getRefModel().getValue(RegisterVO.MONEYY));
    	String currtype = (String)fbmbillref.getRefModel().getValue(RegisterVO.PK_CURR);

    	UFDate dqrq = null;
    	if(fbmbillref.getRefModel().getValue(RegisterVO.ENDDATE) != null && 
    			fbmbillref.getRefModel().getValue(RegisterVO.ENDDATE).toString().trim().length() > 0) {
    		dqrq = new UFDate((String) fbmbillref.getRefModel().getValue(RegisterVO.ENDDATE));
    	}
		

	    Integer discountdelayday = null;
	    Object discdeldatVO = getUI().getBillCardPanel().getHeadItem(DiscountVO.DISCOUNTDELAYDAYNUM).getValueObject();
		if(discdeldatVO != null && discdeldatVO.toString().trim().length() > 0) {
		    discountdelayday = new Integer((String)discdeldatVO);
	    } else {
	    	discountdelayday = new Integer(0);
	    }
		UFDouble ratevalue= new UFDouble((String)(getUI().getBillCardPanel().getHeadItem(DiscountVO.DISCOUNTYRATE).getValueObject()));
		
		String strtxrq = (String)getUI().getBillCardPanel().getHeadItem(DiscountVO.DDISCOUNTDATE).getValueObject();
		UFDate txrq = null;
		if(strtxrq != null && strtxrq.trim().length() != 0) {
		    txrq = new UFDate ((String)getUI().getBillCardPanel().getHeadItem(DiscountVO.DDISCOUNTDATE).getValueObject());
		}
		if(txrq != null && dqrq != null) {
			if(dqrq.after(txrq)) {
				Integer ratedaynum = (Integer) getUI().getBillCardPanel().getHeadItem(DiscountVO.RATEDAYNUM).getValueObject();
		    	String pk_corp = (String)getUI().getBillCardPanel().getHeadItem(DiscountVO.PK_CORP).getValueObject();
				UFDouble discountcharge = null; 
				Object discharvalueObject = getUI().getBillCardPanel().getHeadItem(DiscountVO.DISCOUNTCHARGE).getValueObject();
				if(discharvalueObject != null && discharvalueObject.toString().length() > 0) {
					discountcharge = new UFDouble((String)discharvalueObject);
				}
				hpje = new UFDouble((String)(getUI().getBillCardPanel().getHeadItem(DiscountVO.HPJE).getValueObject()));

				UFDouble discountinterest = null;
	            if(txrq != null && ratedaynum != null) {
	            	discountinterest = DiscountCalculate.calculateDiscountInterest(hpje, txrq, dqrq, currtype, discountdelayday, ratevalue, ratedaynum, pk_corp);
	            		//hpje.multiply(intdate + discountdelayday).multiply(ratevalue).div(100).div(ratedaynum);
	            }
				getUI().getBillCardPanel().getHeadItem(DiscountVO.DISCOUNTINTEREST).setValue(discountinterest);
				if(discountcharge != null && discountinterest != null) {
					getUI().getBillCardPanel().getHeadItem(DiscountVO.MONEYY).setValue(hpje.sub(discountinterest).sub(discountcharge));
				} else if(discountinterest != null){
					getUI().getBillCardPanel().getHeadItem(DiscountVO.MONEYY).setValue(hpje.sub(discountinterest));
				}

			}else {
				getUI().getBillCardPanel().getHeadItem(DiscountVO.MONEYY).setValue(null);
				getUI().getBillCardPanel().getHeadItem(DiscountVO.DISCOUNTINTEREST).setValue(null);
			}
		}
		getUI().fireCardAfterEdit(DiscountVO.YBBZ);
	
	}

}
