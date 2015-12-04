package nc.ui.fbm.pub.reffilter;

import nc.ui.fbm.pub.FBMManageUI;
import nc.ui.pub.bill.BillItem;
import nc.ui.tm.framework.ref.filter.BillItemRefModelFilter;

/**
 * 结算中心对应的银行档案
 * @author xwq
 *
 */
public class BankOnlySettleCenterFilter extends BillItemRefModelFilter {
	private FBMManageUI ui;
	private String itemkey;
	/**
	 * @param billitem
	 * @param ui 
	 */
	public BankOnlySettleCenterFilter(BillItem billitem, FBMManageUI ui) {
		super(billitem);
		this.ui=ui;
	}
	

	public BankOnlySettleCenterFilter(BillItem billitem, FBMManageUI ui,
			String itemkey) {
		super(billitem);
		this.ui = ui;
		this.itemkey = itemkey;
	}
	@Override
	protected String getSelfFilterString() {
		// TODO Auto-generated method stub
		return " pk_settlecenter is not null";
	}

}
