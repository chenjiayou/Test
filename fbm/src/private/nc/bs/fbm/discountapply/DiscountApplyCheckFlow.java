package nc.bs.fbm.discountapply;

import nc.bs.trade.checkflow.AbstractPFCheckFlow;
import nc.vo.trade.field.BillField;
import nc.vo.trade.field.IBillField;

public class DiscountApplyCheckFlow extends AbstractPFCheckFlow {

	public DiscountApplyCheckFlow() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	protected IBillField createBillField() throws Exception {
		// TODO Auto-generated method stub
		return BillField.getInstance();
	}

}
