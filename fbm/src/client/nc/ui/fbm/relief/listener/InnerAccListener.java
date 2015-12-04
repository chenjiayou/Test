package nc.ui.fbm.relief.listener;

import nc.ui.fbm.pub.AbstractItemEditListener;
import nc.ui.fbm.pub.FBMManageUI;
import nc.ui.pub.bill.BillEditEvent;
import nc.vo.fbm.relief.ReliefVO;

/**
 * 类功能说明：
     根据结算单位过滤账户
 * 日期：2007-11-5
 * 程序员： wues
 *
 */
public class InnerAccListener extends AbstractItemEditListener{

	
	public InnerAccListener() {
		super();
	}

	public InnerAccListener(FBMManageUI ui, String itemKey, int pos) {
		super(ui, itemKey, pos);
	}

	public InnerAccListener(FBMManageUI ui, String itemKey) {
		super(ui, itemKey);
	}

	@Override
	public void responseEditEvent(BillEditEvent editEvent) {
		
		this.getUI().getBillCardPanel().getHeadItem(ReliefVO.INNERACC).setValue(null);
		
	}

}
