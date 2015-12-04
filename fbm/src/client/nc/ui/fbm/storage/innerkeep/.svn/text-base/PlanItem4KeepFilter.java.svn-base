package nc.ui.fbm.storage.innerkeep;

import nc.ui.fbm.pub.FBMManageUI;
import nc.ui.pub.bill.BillItem;


/**
 * 只过滤出制定公司的计划项目
 * @author xwq
 *
 * 2009-1-6
 */
public class PlanItem4KeepFilter extends nc.ui.tm.framework.ref.filter.BillItemRefModelFilter {

	public PlanItem4KeepFilter(BillItem billitem, FBMManageUI ui) {
		super(billitem);
		this.ui = ui;
	}
	
	@Override
	protected String getSelfFilterString() {
		return " pk_corp='" + billitem.getValue() + "'  and ioflag =0";
	}

}
