/**
 *
 */
package nc.ui.fbm.invoice.listener;

import nc.ui.fbm.pub.AbstractItemEditListener;
import nc.ui.fbm.pub.FBMManageUI;
import nc.ui.pub.bill.BillEditEvent;
import nc.vo.fbm.register.RegisterVO;

/**
 * <p>
 * 
 * <p>�����ˣ�lpf
 * <b>���ڣ�2007-9-4
 *
 */
public class InvoicePayunitListener extends AbstractItemEditListener{

	/**
	 * 
	 */
	public InvoicePayunitListener() {
		// TODO Auto-generated constructor stub
	}

	
	public InvoicePayunitListener(FBMManageUI ui, String itemKey, int pos) {
		super(ui, itemKey, pos);
		// TODO Auto-generated constructor stub
	}


	public InvoicePayunitListener(FBMManageUI ui, String itemKey) {
		super(ui, itemKey);
		// TODO Auto-generated constructor stub
	}


	@Override
	public void responseEditEvent(BillEditEvent editEvent) {
		//��Ҫ���˱�֤���˺�
		getUI().getBillCardPanel().getHeadItem(RegisterVO.RECEIVEBANKACC).setValue(null);
		//getUI().getBillCardPanel().getHeadItem(RegisterVO.RECEIVEACCNAME).setValue(null);
	}
}
