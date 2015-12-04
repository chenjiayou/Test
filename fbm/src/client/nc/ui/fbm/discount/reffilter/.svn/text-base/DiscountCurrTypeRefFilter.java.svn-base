package nc.ui.fbm.discount.reffilter;

import nc.itf.cdm.util.CommonUtil;
import nc.ui.pub.beans.UIRefPane;
import nc.ui.pub.bill.BillItem;
import nc.ui.tm.framework.ref.filter.BillItemRefModelFilter;

/**
 * 
 * <p>
 *	票据贴现办理单根据币种过滤贴现账号
 * <p>创建人：bsrl
 * <b>日期：2007-12-06
 *
 */
public class DiscountCurrTypeRefFilter extends BillItemRefModelFilter{
	public DiscountCurrTypeRefFilter(BillItem billitem) {
		super(billitem);
	}

	@Override 
	protected String getSelfFilterString() {
		UIRefPane currtype = (UIRefPane)getBillItem().getComponent();
		String condition = null;
		condition = (String)currtype.getRefModel().getValue("pk_currtype");
		
		if(CommonUtil.isNull(condition)){
			return null;
		}else{
			return " pk_currtype = '" + condition +"' " ;
		}
	}
}
