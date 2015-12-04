package nc.ui.fbm.endore;

import nc.ui.pub.beans.UIComboBox;
import nc.ui.pub.bill.BillItem;
import nc.ui.tm.framework.ref.filter.BillItemRefModelFilter;
import nc.vo.fbm.pub.constant.FbmBusConstant;

public class EndoreMemberFilter extends BillItemRefModelFilter {

	public EndoreMemberFilter(BillItem billitem) {
		super(billitem);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected String getSelfFilterString() {
		String ret = null;
		if (getBillItem() != null) {
			UIComboBox opBillType = (UIComboBox) getBillItem().getComponent();
			String opType = (String) opBillType.getSelectdItemValue();
			
//			if (FbmBusConstant.BILL_UNISTORAGE.equals(opType)) {//如果选择的是统管，则只显示外部客商，否则显示全部客商
//				ret = " bd_cubasdoc.custprop = 0 and (custflag='1' or custflag='2')";
//			}else
//			{
				ret = " (custflag='1' or custflag='2')"; //custflag:客户(0)，客商、供应商(1,2)
//			}
		}
		return ret;
	}

}
