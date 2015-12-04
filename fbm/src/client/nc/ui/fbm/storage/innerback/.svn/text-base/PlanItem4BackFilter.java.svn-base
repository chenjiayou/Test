package nc.ui.fbm.storage.innerback;

import nc.ui.fbm.pub.FBMManageUI;
import nc.ui.pub.bill.BillItem;

public class PlanItem4BackFilter extends nc.ui.tm.framework.ref.filter.BillItemRefModelFilter {

	public PlanItem4BackFilter(BillItem billitem, FBMManageUI ui) {
		super(billitem);
		this.ui = ui;
	}
	
	@Override
	protected String getSelfFilterString() {
		return " pk_corp='" + billitem.getValue() + "'  and ioflag =1";
	}
}
