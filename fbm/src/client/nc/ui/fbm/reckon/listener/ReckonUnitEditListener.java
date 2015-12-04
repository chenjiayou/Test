package nc.ui.fbm.reckon.listener;

import nc.ui.fbm.pub.AbstractItemEditListener;
import nc.ui.fbm.pub.FBMManageUI;
import nc.ui.pub.bill.BillEditEvent;
import nc.ui.pub.bill.BillModel;
import nc.vo.fbm.reckon.ReckonBVO;
import nc.vo.fbm.reckon.ReckonVO;

/**
 * <p>
 * 调剂清算-清算单位编辑监听器类
 * <p>创建人：bsrl
 * <b>日期：2007-10-31
 *
 */
public class ReckonUnitEditListener extends AbstractItemEditListener{
	/**
	 * 
	 */
	public ReckonUnitEditListener() {
	}

	/**
	 * @param ui
	 * @param itemKey
	 */
	public ReckonUnitEditListener(FBMManageUI ui, String itemKey) {
		super(ui, itemKey);
	}

	/**
	 * @param ui
	 * @param itemKey
	 * @param pos
	 */
	public ReckonUnitEditListener(FBMManageUI ui, String itemKey, int pos) {
		super(ui, itemKey, pos);
	}

	/* (non-Javadoc)
	 * @see nc.ui.fbm.pub.AbstractItemEditListener#responseEditEvent(nc.ui.pub.bill.BillEditEvent)
	 */
	@Override
	public void responseEditEvent(BillEditEvent editEvent) {
		
		if(editEvent.getValue() != null && editEvent.getValue().toString().trim().length() > 0) {
			BillModel billmodel = getUI().getBillCardPanel().getBillModel(ReckonBVO.TABLENAME);
			int rowCount = billmodel.getRowCount();
			if(rowCount>0){//删除所有行
				int[] array = new int[rowCount];
				for (int i = 0; i <rowCount; i++) {
					array[i] = i;
				}
				billmodel.delLine(array);
			}
			getUI().getBillCardPanel().getHeadItem(ReckonVO.RECKONMONEYSUM).setValue(null);
	    }
		
		getUI().getBillCardPanel().getHeadItem(ReckonVO.INACC).setValue(null);
		getUI().getBillCardPanel().getHeadItem(ReckonVO.OUTACC).setValue(null);
	}
}
