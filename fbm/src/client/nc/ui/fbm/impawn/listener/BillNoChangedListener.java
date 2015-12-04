package nc.ui.fbm.impawn.listener;

import nc.ui.fbm.pub.AbstractItemEditListener;
import nc.ui.fbm.pub.FBMManageUI;
import nc.ui.pub.bill.BillEditEvent;
import nc.vo.fbm.impawn.ImpawnVO;

/**
 * 功能： 评估价值监听器 日期：2007-9-20 程序员：wues
 */
public class BillNoChangedListener extends AbstractItemEditListener {

	public BillNoChangedListener() {
		super();

	}

	public BillNoChangedListener(FBMManageUI ui, String itemKey, int pos) {
		super(ui, itemKey, pos);

	}

	public BillNoChangedListener(FBMManageUI ui, String itemKey) {
		super(ui, itemKey);

	}

	@Override
	public void responseEditEvent(BillEditEvent editEvent){
		getUI().fireCardAfterEdit(ImpawnVO.EVALUATEVALUE);
	}

}
