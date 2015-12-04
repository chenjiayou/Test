package nc.ui.fbm.invoice.reffilter;

import nc.ui.pub.ClientEnvironment;
import nc.ui.tm.framework.ref.filter.BillItemRefModelFilter;

/**
 * 当前公司权限 银行账户
 * @author xwq
 *
 * 2008-10-28
 */
public class CurrCorpBankaccFilter extends BillItemRefModelFilter {

	@Override
	protected String getSelfFilterString() {
		return "authcorp='"+ClientEnvironment.getInstance().getCorporation().getPrimaryKey()+"'";
	}
	
	

}
