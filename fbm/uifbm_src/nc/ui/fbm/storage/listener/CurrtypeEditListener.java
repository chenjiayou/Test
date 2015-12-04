package nc.ui.fbm.storage.listener;

import nc.ui.fbm.pub.AbstractItemEditListener;
import nc.ui.fbm.pub.FBMManageUI;
import nc.ui.pub.bill.BillEditEvent;
import nc.vo.fbm.storage.StorageBVO;
import nc.vo.fbm.storage.StorageVO;


/**
 * @author bsrl
 * @since 5.3
 *
 *  2008-03-18
 *  内部存放和内部领用界面表头币种字段编辑后处理类
 */
public class CurrtypeEditListener extends AbstractItemEditListener {
	private String clearItemKey;
	/**
	 * 
	 */
	public CurrtypeEditListener() {
	}

	public CurrtypeEditListener(FBMManageUI ui, String itemKey,
			String clearItemKey) {
		super(ui, itemKey);
		this.clearItemKey = clearItemKey;
	}

	/**
	 * @param ui
	 * @param itemKey
	 */
	public CurrtypeEditListener(FBMManageUI ui, String itemKey) {
		super(ui, itemKey);
	}

	/* (non-Javadoc)
	 * @see nc.ui.fbm.pub.AbstractItemEditListener#responseEditEvent(nc.ui.pub.bill.BillEditEvent)
	 */
	@Override
	public void responseEditEvent(BillEditEvent editEvent) {
		getUI().getBillCardPanel().getHeadItem(StorageVO.KEEPACCOUNT).setValue(null);
		getUI().getBillCardPanel().getBillModel(StorageBVO.tablecode).setBodyDataVO(null);
	}
}
