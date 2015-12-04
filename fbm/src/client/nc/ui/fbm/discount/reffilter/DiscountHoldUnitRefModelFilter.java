package nc.ui.fbm.discount.reffilter;

import nc.ui.fbm.pub.FBMClientInfo;
import nc.ui.pub.bill.BillItem;
import nc.ui.tm.framework.ref.filter.BillItemRefModelFilter;

/**
 * 
 * <p>
 *	票据贴现办理单根据持票单位过滤其他参照
 * <p>创建人：bsrl
 * <b>日期：2007-09
 *
 */
public class DiscountHoldUnitRefModelFilter extends BillItemRefModelFilter{
	public DiscountHoldUnitRefModelFilter(BillItem billitem) {
		super(billitem);
	}

	@Override 
	protected String getSelfFilterString() {
		String pk_corp = FBMClientInfo.getCorpPK();
		return "  (pk_corp ='"+pk_corp+"' or (pk_corp = '0001' and pk_bankaccbas in (" +
		"select pk_bankaccbas from bd_bankaccauth where pk_bankaccbas = bd_bankaccauth.pk_bankaccbas " +
		"and bd_bankaccauth.pk_corp = '"+pk_corp+"' and isnull(bd_bankaccauth.dr,0) = 0)) ) " ;

	}
}
