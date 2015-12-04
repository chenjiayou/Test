package nc.ui.fbm.discount.reffilter;

import nc.ui.fbm.pub.FBMClientInfo;
import nc.ui.pub.ClientEnvironment;
import nc.ui.pub.beans.UIComboBox;
import nc.ui.pub.beans.UIRefPane;
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
public class BillSelectByOpTypeFilter  extends BillItemRefModelFilter {

	public BillSelectByOpTypeFilter(BillItem billitem) {
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
				ret = " pk_corp = '" + ClientEnvironment.getInstance().getCorporation().getPk_corp() + "' and (registerstatus='register' or registerstatus='has_bank_keep') ";
			} else {
				ret = " pk_corp != '" + ClientEnvironment.getInstance().getCorporation().getPk_corp() + "'";
			}
		}
		return ret;
	}

}
