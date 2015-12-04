package nc.ui.fbm.endore;

import nc.ui.pub.ClientEnvironment;
import nc.ui.pub.beans.UIComboBox;
import nc.ui.pub.bill.BillItem;
import nc.ui.tm.framework.ref.filter.BillItemRefModelFilter;
import nc.vo.fbm.pub.constant.FbmBusConstant;

/**
 * 
 ***********************************************************
 * ���ڣ�2008-3-11							   
 * ����Ա:���ɽ 							   
 * ���ܣ�����Ϊ����Ʊ�����͹���ѡƱ						   
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
			
			if (FbmBusConstant.BILL_PRIVACY.equals(opType)) {//���ѡ�����˽��Ʊ�ݣ������pk_corp������,ֻѡ����˾��
				ret = " pk_corp = '" + ClientEnvironment.getInstance().getCorporation().getPk_corp() + "' and registerstatus='register' ";
			} else {
				ret = " pk_corp != '" + ClientEnvironment.getInstance().getCorporation().getPk_corp() + "'";
			}
		}
		return ret;
	}

}
