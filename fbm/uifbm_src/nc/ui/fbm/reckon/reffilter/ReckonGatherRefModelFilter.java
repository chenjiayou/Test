package nc.ui.fbm.reckon.reffilter;

import nc.ui.pub.beans.UIRefPane;
import nc.ui.pub.bill.BillItem;
import nc.ui.tm.framework.ref.filter.BillItemRefModelFilter;

/**
 * 
 * <p>
 *	调剂清算单界面清算票据过滤器类
 * <p>创建人：bsrl
 * <b>日期：2007-10-30
 *
 */
public class ReckonGatherRefModelFilter extends BillItemRefModelFilter {

	public ReckonGatherRefModelFilter(BillItem billitem) {
		super(billitem);
	}

	@Override
	protected String getSelfFilterString() {
		UIRefPane refHoldunit= (UIRefPane)getBillItem().getComponent();
		String condition = null;
		condition = (String) refHoldunit.getRefModel().getValue("bd_cubasdoc.pk_cubasdoc");
		if(condition != null && condition.trim().length() > 0) {
			return " cubasedoc =  '" + condition + "'";
		}else{
			return null;
		}
	}
}
