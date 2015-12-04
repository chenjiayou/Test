package nc.ui.fbm.discountapply.reffilter;

import nc.ui.pub.bill.BillItem;
import nc.ui.tm.framework.ref.filter.BillItemRefModelFilter;

/**
 * 
 * <p>
 *	票据贴现申请单根据持票单位过滤其他参照
 * <p>创建人：bsrl
 * <b>日期：2007-09
 *
 */
public class DiscountApplyHoldUnitRefModelFilter extends BillItemRefModelFilter{
	public DiscountApplyHoldUnitRefModelFilter(BillItem billitem) {
		super(billitem);
	}

	@Override 
	protected String getSelfFilterString() {
		return null;
	}
}
