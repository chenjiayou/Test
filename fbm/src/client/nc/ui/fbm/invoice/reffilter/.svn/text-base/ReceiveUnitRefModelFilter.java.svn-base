/**
 *
 */
package nc.ui.fbm.invoice.reffilter;

import nc.itf.cdm.util.CommonUtil;
import nc.ui.fbm.pub.FBMClientInfo;
import nc.ui.fbm.pub.FBMManageUI;
import nc.ui.pub.bill.BillItem;
import nc.ui.tm.framework.ref.filter.BillItemRefModelFilter;

/**
 * <p>
 * 开票收款单位过滤掉当前客商
 * <p>创建人：lpf
 * <b>日期：2007-11-13
 *
 */
public class ReceiveUnitRefModelFilter extends BillItemRefModelFilter {
	private FBMManageUI ui; 
	/**
	 * @param billitem
	 */
	public ReceiveUnitRefModelFilter(BillItem billitem) {
		super(billitem);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see nc.ui.tm.framework.ref.filter.AbstractRefModelFilter#getSelfFilterString()
	 */
	public ReceiveUnitRefModelFilter(BillItem billitem, FBMManageUI ui) {
		super(billitem);
		this.ui = ui;
	}

	@Override
	protected String getSelfFilterString() {
		String pk_cubasdoc = FBMClientInfo.getCorpCubasdoc(ui._getCorp().getPk_corp());
		if(!CommonUtil.isNull(pk_cubasdoc)){
			return " bd_cubasdoc.pk_cubasdoc<>'" + pk_cubasdoc + "' ";
		}
		return null;
	}

}
