package nc.ui.fbm.pub.reffilter;

import nc.ui.bd.ref.IRefConst;
import nc.ui.pub.ClientEnvironment;
import nc.ui.tm.framework.ref.filter.BillItemRefModelFilter;
import nc.ui.trade.base.AbstractBillUI;

/**
 * 查询权的银行账户
 * @author xwq
 *
 * 2008-10-20
 */
public class BankAccQueryPowerFilter  extends BillItemRefModelFilter {
	
	public BankAccQueryPowerFilter(AbstractBillUI ui) {
		super(ui);
	}

	@Override
	protected String getSelfFilterString() {
		String pk_corp = ClientEnvironment.getInstance().getCorporation().getPrimaryKey();
		if (pk_corp.equals(IRefConst.GROUPCORP)) {
			return "querypowerflag='Y' and mainaccount is null";
		} else {
			return " bd_bankaccauth.pk_corp ='" + pk_corp
					+ "' and querypowerflag='Y' and mainaccount is null";
		}
	}

	
}
