package nc.ui.fbm.endore.listener;

import nc.ui.fbm.pub.AbstractItemEditListener;
import nc.ui.fbm.pub.FBMManageUI;
import nc.ui.pub.bill.BillEditEvent;
import nc.vo.fbm.endore.EndoreVO;

public class ConfigBZEditListener extends AbstractItemEditListener {

	
	public ConfigBZEditListener() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ConfigBZEditListener(FBMManageUI ui, String itemKey, int pos) {
		super(ui, itemKey, pos);
		// TODO Auto-generated constructor stub
	}

	public ConfigBZEditListener(FBMManageUI ui, String itemKey) {
		super(ui, itemKey);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void responseEditEvent(BillEditEvent editEvent) {
		
		if (editEvent.getValue() == null || "".equals(editEvent.getValue().toString().trim())) {
			getUI().getBillCardPanel().getHeadItem(EndoreVO.BRATE).setValue(null);
			getUI().getBillCardPanel().getHeadItem(EndoreVO.FRATE).setValue(null);
			getUI().getBillCardPanel().getHeadItem(EndoreVO.MONEYB).setValue(null);
			getUI().getBillCardPanel().getHeadItem(EndoreVO.MONEYF).setValue(null);
		}
		
		getUI().fireCardAfterEdit(EndoreVO.PK_CURR);

	}

}
