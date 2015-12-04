package nc.ui.fbm.pub.refmodel;

import nc.ui.fbm.pub.FBMManageUI;
import nc.ui.pub.beans.UIRefPane;
import nc.ui.pub.bill.BillItem;
import nc.ui.tm.framework.ref.filter.BillItemRefModelFilter;
import nc.vo.fbm.relief.ReliefVO;

/**
 * 
 * 类功能说明： 内部账户过滤器，根据选择的调剂单位和币种选择相应的账户 日期：2007-10-23 程序员： wues
 * 
 */
public class InnerAccountRefModelFilter extends BillItemRefModelFilter {
	private FBMManageUI ui;
	public InnerAccountRefModelFilter(BillItem billitem) {
		super(billitem);
	}

	public InnerAccountRefModelFilter(BillItem billitem,FBMManageUI ui) {
		super(billitem);
		this.ui=ui;
	}
	
	protected String getSelfFilterString() {
		String ret = null;
		if (getBillItem() != null) {
			UIRefPane reliefUnitPane = (UIRefPane) getBillItem().getComponent();
			String reliefUnit = (String) reliefUnitPane.getRefPK();
			UIRefPane currPane = (UIRefPane) ui.getBillCardPanel().getHeadItem(ReliefVO.PK_CURRTYPE).getComponent();
			String curr = (String) currPane.getRefPK();
			if (reliefUnit != null) {
				ret = " exists (select 1 from  bd_cubasdoc a,bd_settleunit b where a.pk_corp1 = b.pk_corp1 and b.pk_corp1 = bd_accid.ownercorp and a.pk_cubasdoc='"
					+ reliefUnit + "')";
				
			}
			if(curr != null) {
				if(ret  == null || ret.trim().length() == 0)
					ret = " bd_accid.pk_currtype = '" + curr + "' ";
				else 
					ret += " and bd_accid.pk_currtype = '" + curr + "' ";
			}
		}
		return ret;
	}
}
