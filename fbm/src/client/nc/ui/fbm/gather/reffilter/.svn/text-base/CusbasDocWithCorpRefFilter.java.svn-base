/**
 *
 */
package nc.ui.fbm.gather.reffilter;

import nc.ui.fbm.pub.FBMManageUI;
import nc.ui.tm.framework.ref.filter.BillItemRefModelFilter;
import nc.ui.tm.framework.util.ClientInfo;
import nc.ui.trade.base.AbstractBillUI;

/**
 * <p>
 * 客商管理档案过滤
 * <p>创建人：lpf
 * <b>日期：2007-12-26
 *
 */
public class CusbasDocWithCorpRefFilter extends BillItemRefModelFilter {

	private String acckey;//账号字段
	/**
	 * @param billitem
	 */
	public CusbasDocWithCorpRefFilter(AbstractBillUI ui,String acckey) {
		super(ui);
		this.acckey = acckey;
	}

	/* (non-Javadoc)
	 * @see nc.ui.tm.framework.ref.filter.AbstractRefModelFilter#getSelfFilterString()
	 */
	@Override
	protected String getSelfFilterString() {
		String filter = " mancorp='"+ClientInfo.getCorpPK()+"' ";
		Object obj = ((FBMManageUI)ui).getBillCardPanel().getHeadItem(acckey).getValueObject();
		
//		if(obj != null){
//			String pk_acc = String.valueOf(obj);
//			filter = filter + " and pk_cubasdoc in(select pk_cubasdoc from bd_cubasdoc join bd_bankaccbas on bd_cubasdoc.pk_corp1 = bd_bankaccbas.ownercorp where bd_bankaccbas.pk_bankaccbas = '"+pk_acc+"')";
//		}
		
		return filter;
	}

}
