package nc.ui.fbm.storage.listener;

import nc.ui.fbm.pub.AbstractItemEditListener;
import nc.ui.fbm.pub.FBMManageUI;
import nc.ui.pub.bill.BillEditEvent;
import nc.ui.pub.bill.BillItem;
import nc.vo.fbm.pub.constant.FbmBusConstant;
import nc.vo.fbm.storage.StorageVO;

/**
 * ѡ���йܷ�ʽ���������ѡ�񱣹��йܣ����ڲ��˻�����Ϊ���Ҳ��ɱ༭
 * @author xwq
 *
 * 2008-12-20
 */
public class InputTypeListener extends AbstractItemEditListener {

	/**
	 * @param ui
	 * @param itemKey
	 */
	public InputTypeListener(FBMManageUI ui, String itemKey) {
		super(ui, itemKey);
	}

	@Override
	public void responseEditEvent(BillEditEvent editEvent) {
		BillItem keepaccItem = getUI().getBillCardPanel().getHeadItem(StorageVO.KEEPACCOUNT);
		BillItem inputtypeItem = getUI().getBillCardPanel().getHeadItem(StorageVO.INPUTTYPE);
		BillItem unitplanItem = getUI().getBillCardPanel().getHeadItem(StorageVO.UNITPLANITEM);
		
		if(inputtypeItem.getValue()!=null){
			if(inputtypeItem.getValue().equals(FbmBusConstant.KEEP_TYPE_STORE)){//���ܴ��
				keepaccItem.setValue(null);
				keepaccItem.setEnabled(false);
				unitplanItem.setValue(null);
				unitplanItem.setEnabled(false);
			}else{
				keepaccItem.setEnabled(true);
				unitplanItem.setEnabled(true);
			}
		}
		int row = getUI().getBillCardPanel().getRowCount();
		if(row==-1)
			return;
		for (int i = 0; i < row; i++) {
			getUI().getBillCardPanel().delLine();
		}
	}
}
