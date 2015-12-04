/**
 *
 */
package nc.ui.fbm.storage;

import nc.itf.cdm.util.CommonUtil;
import nc.ui.fbm.pub.FBMManageUI;
import nc.ui.pub.beans.UIRefPane;
import nc.ui.pub.bill.BillItem;
import nc.ui.tm.framework.ref.filter.BillItemRefModelFilter;
import nc.vo.fbm.storage.StorageVO;

/**
 * <p>
 * 
 * <p>创建人：lpf
 * <b>日期：2007-10-23
 *
 */
public class InnerAccRefModelFilter extends BillItemRefModelFilter {
	private FBMManageUI ui;
	private String itemkey;
	/**
	 * @param billitem
	 * @param ui 
	 */
	public InnerAccRefModelFilter(BillItem billitem, FBMManageUI ui) {
		super(billitem);
		this.ui=ui;
	}
	

	public InnerAccRefModelFilter(BillItem billitem, FBMManageUI ui,
			String itemkey) {
		super(billitem);
		this.ui = ui;
		this.itemkey = itemkey;
	}


	/* (non-Javadoc)
	 * @see nc.ui.tm.framework.ref.filter.AbstractRefModelFilter#getSelfFilterString()
	 */
	@Override
	protected String getSelfFilterString() {
		String ret = "";
		String pk_cubasdoc = (String) ui.getBillCardPanel().getHeadItem(itemkey).getValueObject();
		if (!CommonUtil.isNull(pk_cubasdoc)) {
			ret = " exists (select 1 from  bd_cubasdoc a,bd_settleunit b where a.pk_corp1 = b.pk_corp1 and b.pk_corp1 = bd_accid.ownercorp and a.pk_cubasdoc='"
				+ pk_cubasdoc + "') and ";
		}
		
		String pk_curr = (String) ui.getBillCardPanel().getHeadItem(StorageVO.PK_CURRTYPE).getValueObject();
		if(!CommonUtil.isNull(pk_curr)){
			UIRefPane refpane = (UIRefPane) ui.getBillCardPanel().getHeadItem(StorageVO.PK_CURRTYPE).getComponent();
			String pk_currtype = (String) refpane.getRefValue("pk_currtype");
			ret = ret+"  bd_accid.pk_currtype='"+pk_currtype+"'";
		}
		return ret;
	}

}
