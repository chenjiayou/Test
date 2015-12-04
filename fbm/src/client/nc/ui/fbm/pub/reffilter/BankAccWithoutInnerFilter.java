package nc.ui.fbm.pub.reffilter;

import nc.ui.tm.framework.ref.filter.BillItemRefModelFilter;

/**
 * 银行账号参照，不包括内部帐户生成的
 * @author xwq
 *
 * 2008-10-17
 */
public class BankAccWithoutInnerFilter extends BillItemRefModelFilter {

	
	@Override
	protected String getSelfFilterString() {
		// TODO Auto-generated method stub
		return " isinneracc = 'N' ";
	}
	
}
