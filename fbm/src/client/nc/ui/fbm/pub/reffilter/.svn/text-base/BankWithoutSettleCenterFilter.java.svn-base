package nc.ui.fbm.pub.reffilter;

import nc.ui.tm.framework.ref.filter.BillItemRefModelFilter;
import nc.ui.trade.base.AbstractBillUI;

/**
 * 不包括结算中心的银行档案
 * @author xwq
 *
 */
public class BankWithoutSettleCenterFilter extends BillItemRefModelFilter {

	public BankWithoutSettleCenterFilter(AbstractBillUI ui) {
		super(ui);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected String getSelfFilterString() {
		// TODO Auto-generated method stub
		return " (banktypecode <> '9999' or banktypecode is null)";
	}

}
