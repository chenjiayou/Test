/**
 *
 */
package nc.ui.fbm.discountapply.reffilter;

import nc.ui.fbm.pub.FBMClientInfo;
import nc.ui.pub.bill.BillItem;
import nc.ui.tm.framework.ref.filter.BillItemRefModelFilter;

/**
 * <p>
 * 持票单位账号根据公司和银行属性过滤
 * <p>创建人：lpf
 * <b>日期：2007-12-25
 *
 */
public class HolderaccRefModelFilter extends BillItemRefModelFilter {

	/**
	 * @param billitem
	 */
	public HolderaccRefModelFilter(BillItem billitem) {
		super(billitem);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see nc.ui.tm.framework.ref.filter.AbstractRefModelFilter#getSelfFilterString()
	 */
	@Override
	protected String getSelfFilterString() {
		String corpPK = FBMClientInfo.getCorpPK();
//		return " isCust='Y' and (pk_corp ='"+corpPK+"' or (pk_corp = '0001' and pk_bankaccbas in (" +
//				"select pk_bankaccbas from bd_bankaccauth where pk_bankaccbas = bd_bankaccauth.pk_bankaccbas " +
//				"and bd_bankaccauth.pk_corp = '"+corpPK+"' and isnull(bd_bankaccauth.dr,0) = 0 and frozenflag = 'N' )) ) " ;
		return " ( bd_bankaccbas.pk_corp ='"+corpPK+"' or ( bd_bankaccbas.pk_corp = '0001' and bd_bankaccbas.pk_bankaccbas in (" +
		"select bd_bankaccbas.pk_bankaccbas from bd_bankaccauth where  bd_bankaccbas.pk_bankaccbas = bd_bankaccauth.pk_bankaccbas " +
		"and bd_bankaccauth.pk_corp = '"+corpPK+"' and isnull(bd_bankaccauth.dr,0) = 0  )) ) " ;

	}

}
