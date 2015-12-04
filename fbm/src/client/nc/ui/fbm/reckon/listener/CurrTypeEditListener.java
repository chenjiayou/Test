package nc.ui.fbm.reckon.listener;

import nc.ui.fbm.pub.AbstractItemEditListener;
import nc.ui.fbm.pub.FBMManageUI;
import nc.ui.pub.bill.BillEditEvent;
import nc.ui.pub.bill.BillModel;
import nc.vo.fbm.reckon.ReckonBVO;
import nc.vo.fbm.reckon.ReckonVO;

/**
 * <p>
 * 调剂清算-币种编辑监听器类
 * <p>创建人：bsrl
 * <b>日期：2007-11-02
 *
 */
public class CurrTypeEditListener extends AbstractItemEditListener{
		/**
		 * 
		 */
		public CurrTypeEditListener() {
		}

		/**
		 * @param ui
		 * @param itemKey
		 */
		public CurrTypeEditListener(FBMManageUI ui, String itemKey) {
			super(ui, itemKey);
		}

		/**
		 * @param ui
		 * @param itemKey
		 * @param pos
		 */
		public CurrTypeEditListener(FBMManageUI ui, String itemKey, int pos) {
			super(ui, itemKey, pos);
		}

		/* (non-Javadoc)
		 * @see nc.ui.fbm.pub.AbstractItemEditListener#responseEditEvent(nc.ui.pub.bill.BillEditEvent)
		 */
		@Override
		public void responseEditEvent(BillEditEvent editEvent) {
			if(editEvent.getValue() != null && editEvent.getValue().toString().trim().length() > 0) {
				BillModel billmodel = getUI().getBillCardPanel().getBillModel(ReckonBVO.TABLENAME);
				billmodel.setBodyDataVO(null);
				getUI().getBillCardPanel().getHeadItem(ReckonVO.RECKONMONEYSUM).setValue(null);
		    }
		}
}
