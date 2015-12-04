/**
 *
 */
package nc.bs.fbm.pub;

import nc.bs.trade.checkflow.AbstractPFCheckFlow;
import nc.vo.trade.field.BillField;
import nc.vo.trade.field.IBillField;

/**
 * 
 * <p>
 *	公用的审批流校验类
 * <p>创建人：lpf
 * <b>日期：2007-8-23
 *
 */
public class PublicPFCheckFlow extends AbstractPFCheckFlow {

	/**
	 * 
	 */
	public PublicPFCheckFlow() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see nc.bs.trade.checkflow.AbstractPFCheckFlow#createBillField()
	 */
	@Override
	protected IBillField createBillField() throws Exception {
		// TODO Auto-generated method stub
		return BillField.getInstance();
	}

}
