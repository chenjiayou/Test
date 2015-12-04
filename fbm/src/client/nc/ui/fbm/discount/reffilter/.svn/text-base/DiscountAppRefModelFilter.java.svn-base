package nc.ui.fbm.discount.reffilter;

import nc.ui.tm.framework.ref.filter.BillItemRefModelFilter;
import nc.ui.trade.base.AbstractBillUI;

public class DiscountAppRefModelFilter extends BillItemRefModelFilter {

	public DiscountAppRefModelFilter(AbstractBillUI ui) {
		super(ui);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected String getSelfFilterString() {
		// TODO Auto-generated method stub
		return "  pk_discount not in(select b.pk_discount_app from fbm_discount b where b.pk_billtypecode = '36GG' " 
			+"   and isnull(b.dr,0)=0 and b.pk_discount_app is not null)";
	}

}
