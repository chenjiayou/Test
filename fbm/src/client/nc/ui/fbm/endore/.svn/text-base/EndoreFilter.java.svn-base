package nc.ui.fbm.endore;

import nc.ui.fbm.pub.FBMClientInfo;
import nc.ui.pub.bill.BillItem;
import nc.ui.tm.framework.ref.filter.BillItemRefModelFilter;

/*
 *  过滤类：实现对票据为已收票状态进行过滤
 *  日期：2008-03-27
 *  Add By Wangyg
 */
public class EndoreFilter extends BillItemRefModelFilter {

	public EndoreFilter(BillItem billitem) {
		super(billitem);
	}
	@Override
	protected String getSelfFilterString() {
		String filterSql = " sfflag='Y' and disable = 'N' and pk_billtypecode='36GA'";
		if (FBMClientInfo.isSettleCenter()) {
			filterSql = filterSql + " and registerstatus in('register','has_relief_keep')";
		}else{
			filterSql =  filterSql + " and registerstatus in('register')";
		}
		
		return filterSql;
	}

}
