package nc.ui.fbm.relief.listener;

import nc.ui.fbm.pub.AbstractItemEditListener;
import nc.ui.fbm.pub.FBMManageUI;
import nc.ui.pub.bill.BillEditEvent;
import nc.vo.fbm.relief.ReliefBVO;
import nc.vo.fbm.relief.ReliefVO;

public class ReliefCurrTypeEditListener extends AbstractItemEditListener{

	
	public ReliefCurrTypeEditListener() {
		super();
	}

	public ReliefCurrTypeEditListener(FBMManageUI ui, String itemKey, int pos) {
		super(ui, itemKey, pos);
	}

	public ReliefCurrTypeEditListener(FBMManageUI ui, String itemKey) {
		super(ui, itemKey);
	}

	@Override
	public void responseEditEvent(BillEditEvent editEvent) {
		
		this.getUI().getBillCardPanel().getHeadItem(ReliefVO.INNERACC).setValue(null);
		this.getUI().getBillCardPanel().getBillModel(ReliefBVO.TABLECODE).setBodyDataVO(null);
	}
}
