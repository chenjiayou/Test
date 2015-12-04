package nc.ui.fbm.consignbank.listener;

import nc.ui.fbm.pub.AbstractItemEditListener;
import nc.ui.fbm.pub.FBMManageUI;
import nc.ui.pub.beans.UIRefPane;
import nc.ui.pub.bill.BillEditEvent;
import nc.ui.pub.bill.BillItem;
import nc.vo.fbm.consignbank.CollectionVO;
import nc.vo.fbm.pub.constant.FbmStatusConstant;

/**
 * <p>
 * 托收界面票据编辑监听器类
 * <p>
 * 创建人：bsrl <b>日期：2007-09
 * 
 */
public class ConsignBankSourceEditListener extends AbstractItemEditListener {
	/**
	 * 
	 */
	public ConsignBankSourceEditListener() {
	}

	/**
	 * @param ui
	 * @param itemKey
	 */
	public ConsignBankSourceEditListener(FBMManageUI ui, String itemKey) {
		super(ui, itemKey);
	}

	/**
	 * @param ui
	 * @param itemKey
	 * @param pos
	 */
	public ConsignBankSourceEditListener(FBMManageUI ui, String itemKey, int pos) {
		super(ui, itemKey, pos);
	}

	@Override
	public void responseEditEvent(BillEditEvent editEvent) {
//		getUI().getBillCardPanel().getHeadItem(CollectionVO.HOLDERACC)
//				.setValue(null);
		BillItem sourceItem = getUI().getBillCardPanel().getHeadItem(
				CollectionVO.PK_SOURCE);
		UIRefPane pane = (UIRefPane) sourceItem.getComponent();
		String registerstatus = (String) pane.getRefValue("registerstatus");
		if (FbmStatusConstant.HAS_BANK_KEEP.equals(registerstatus)) {// 已银行托管
			String keepunit = (String) pane.getRefValue("keepunit");
			
			//getUI().getBillCardPanel().getHeadItem(CollectionVO.HOLDERBANK).setEnabled(false);
			
			getUI().getBillCardPanel().getHeadItem(CollectionVO.HOLDERBANK)
					.setValue(keepunit);
		} else {
			//getUI().getBillCardPanel().getHeadItem(CollectionVO.HOLDERBANK).setEnabled(true);
//			getUI().getBillCardPanel().getHeadItem(CollectionVO.HOLDERBANK)
//					.setValue(null);

		}
		getUI().fireCardAfterEdit(CollectionVO.YBBZ);
	}
}
