package nc.ui.fbm.impawn.reffilter;

import nc.ui.pub.bill.BillItem;
import nc.ui.tm.framework.ref.filter.BillItemRefModelFilter;
import nc.vo.fbm.pub.constant.FbmStatusConstant;

/**
 * 
 * 类功能说明： 
 * 	票据质押，选择票据编号时的参照过滤器，选择状态为已登记的票据
 * 日期：2007-11-6 
 * 程序员： wues
 */
public class ImpawnFilter extends BillItemRefModelFilter {

	public ImpawnFilter(BillItem billitem) {
		super(billitem);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected String getSelfFilterString() {
		return " registerstatus='" + FbmStatusConstant.REGISTER + "' and disable='N' ";
	}

}
