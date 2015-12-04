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
			
//			if (FbmBusConstant.BILL_UNISTORAGE.equals(opType)) {//���ѡ�����ͳ�ܣ���ֻ��ʾ�ⲿ���̣�������ʾȫ������
//				ret = " bd_cubasdoc.custprop = 0 and (custflag='1' or custflag='2')";
//			}else
//			{
				ret = " (custflag='1' or custflag='2')"; //custflag:�ͻ�(0)�����̡���Ӧ��(1,2)
//			}
		}
		return ret;
	}

}
