package nc.ui.fbm.reckon.reffilter;

import nc.ui.pub.beans.UIRefPane;
import nc.ui.pub.bill.BillItem;
import nc.ui.tm.framework.ref.filter.BillItemRefModelFilter;

/**
 * <p>
 * 调剂清算界面清算单位参照过滤器类
 * <p>创建人：bsrl
 * <b>日期：2007-10-30
 *
 */
public class ReckonUnitRefFilter  extends BillItemRefModelFilter{
	public ReckonUnitRefFilter(BillItem billitem) {
		super(billitem);
	}

	@Override
	protected String getSelfFilterString() {
		UIRefPane refReckonUnit= (UIRefPane)getBillItem().getComponent();
		String condition = null;
		condition = (String) refReckonUnit.getRefModel().getValue("bd_cubasdoc.pk_cubasdoc");
		if(condition != null && condition.trim().length() > 0) {
			String ret = " exists (select 1 from  bd_cubasdoc a,bd_settleunit b where a.pk_corp1 = b.pk_corp1 and b.pk_corp1 = bd_accid.ownercorp and a.pk_cubasdoc='"
				+ condition + "')";
				
			return ret;
		}else{
			return null;
		}
	}
}
