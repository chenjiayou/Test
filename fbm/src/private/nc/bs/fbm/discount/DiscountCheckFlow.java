package nc.bs.fbm.discount;

import nc.bs.trade.checkflow.AbstractPFCheckFlow;
import nc.vo.trade.field.BillField;
import nc.vo.trade.field.IBillField;

public class DiscountCheckFlow extends AbstractPFCheckFlow {

	public DiscountCheckFlow() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	protected IBillField createBillField() throws Exception {
		// TODO Auto-generated method stub
		return BillField.getInstance();
	}

}
