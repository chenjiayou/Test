package nc.ui.fbm.discountcalculate.listener;

import nc.ui.pub.bill.BillEditEvent;
import nc.ui.pub.bill.BillModel;
import nc.vo.fbm.register.RegisterVO;

/**
 * 
 * <p>
 *	�����������������������༭������
 * <p>�����ˣ�bsrl
 * <b>���ڣ�2007-10-13
 *
 */
public class DiscountCalculateBodyLltsEditListener extends
DiscountCalculationAbstractItemEditListener {
	
	/**
	 * 
	 */
	public DiscountCalculateBodyLltsEditListener() {
	}

	/**
	 * @param ui
	 * @param itemKey
	 */
	public DiscountCalculateBodyLltsEditListener(nc.ui.fbm.discountcalculate.DiscountCalculationUI ui, String itemKey) {
		super(ui, itemKey);
	}

	/**
	 * @param ui
	 * @param itemKey
	 * @param pos
	 */
	public DiscountCalculateBodyLltsEditListener(nc.ui.fbm.discountcalculate.DiscountCalculationUI ui, String itemKey, int pos) {
		super(ui, itemKey, pos);
	}
	
	@Override
	public void responseEditEvent(BillEditEvent editEvent) {

		/**
		 * ��������������
		 * */
		BillModel billmodel = getUI().getBillCardPanel().getBillModel(RegisterVO.TABLENAME);

		//��ո������������ʷ�������
		billmodel.setValueAt("", editEvent.getRow(), "txnllfa_c");
		billmodel.setValueAt(null, editEvent.getRow(), RegisterVO.TXJZ);
		billmodel.setValueAt(null, editEvent.getRow(), RegisterVO.TXLX);
	

	}

}
