package nc.ui.fbm.endore;

import nc.ui.pub.ClientEnvironment;
import nc.ui.pub.beans.UIComboBox;
import nc.ui.pub.bill.BillItem;
import nc.ui.tm.framework.ref.filter.BillItemRefModelFilter;
import nc.vo.fbm.pub.constant.FbmBusConstant;

/**
 * 
 ***********************************************************
 * 日期：2008-3-11							   
 * 程序员:吴二山 							   
 * 功能：此类为根据票据类型过滤选票						   
 ***********************************************************
 */
public class BillSelectOpTypeFilterForEndore  extends BillItemRefModelFilter {

	public BillSelectOpTypeFilterForEndore(BillItem billitem) {
		super(billitem);
	}
	/* (non-Javadoc)
	 * @see nc.ui.tm.framework.ref.filter.AbstractRefModelFilter#getSelfFilterString()
	 */
	@Override
	protected String getSelfFilterString() {
		String ret = null;
		if (getBillItem() != null) {
			UIComboBox opBillType = (UIComboBox) getBillItem().getComponent();
			String opType = (String) opBillType.getSelectdItemValue();
			
			if (opType == null) {
				return null;
			}
			
			if (FbmBusConstant.BILL_PRIVACY.equals(opType)) {//如果选择的是私有票据，则加入pk_corp的条件,只选本公司的
				ret = " pk_corp = '" + ClientEnvironment.getInstance().getCorporation().getPk_corp() + "' and registerstatus='register' ";
			} else {
				ret = " pk_corp != '" + ClientEnvironment.getInstance().getCorporation().getPk_corp() + "'";
			}
		}
		return ret;
	}

}
