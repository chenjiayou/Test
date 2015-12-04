package nc.ui.fbm.discountcalculate.listener;

import nc.ui.fbm.discountcalculate.DiscountCalculationUI;
import nc.ui.pub.bill.BillEditEvent;
import nc.ui.pub.bill.BillModel;
import nc.vo.fbm.register.RegisterVO;
import nc.vo.pub.lang.UFDouble;

/**
 * 
 * <p>
 *	�����������������������ʱ༭������
 * <p>�����ˣ�bsrl
 * <b>���ڣ�2007-10-13
 *
 */
public class DiscountCalculateBodyTxnllEditListener extends
DiscountCalculationAbstractItemEditListener {

	/**
	 * 
	 */
	public DiscountCalculateBodyTxnllEditListener() {
	}

	/**
	 * @param ui
	 * @param itemKey
	 */
	public DiscountCalculateBodyTxnllEditListener(DiscountCalculationUI ui, String itemKey) {
		super(ui, itemKey);
	}

	/**
	 * @param ui
	 * @param itemKey
	 * @param pos
	 */
	public DiscountCalculateBodyTxnllEditListener(DiscountCalculationUI ui, String itemKey, int pos) {
		super(ui, itemKey, pos);
	}
	
	@Override
	public void responseEditEvent(BillEditEvent editEvent) {
		BillModel billmodel = getUI().getBillCardPanel().getBillModel(RegisterVO.TABLENAME);

		if(billmodel.getValueAt(editEvent.getRow(), RegisterVO.LLTS) == null){
			billmodel.setValueAt(360, editEvent.getRow(), RegisterVO.LLTS);
		}
		Object txnll = billmodel.getValueAt(editEvent.getRow(), RegisterVO.TXNLL);
		if(txnll != null && txnll.toString().trim().length() > 0) {
			Object llts = billmodel.getValueAt(editEvent.getRow(), RegisterVO.LLTS);
	        Integer i_llts = new Integer((String)llts);
	//		UFDouble od =new UFDouble((String)txnll);
	        billmodel.setValueAt(new UFDouble((((UFDouble)txnll).toDouble()*new Integer(10)/new Integer(12))), editEvent.getRow(), RegisterVO.TXYLL);
	        billmodel.setValueAt(new UFDouble((((UFDouble)txnll).toDouble()*new Integer(10)/i_llts)), editEvent.getRow(), RegisterVO.TXRLL);
		} else {
			billmodel.setValueAt(null, editEvent.getRow(), RegisterVO.TXYLL);
			billmodel.setValueAt(null, editEvent.getRow(), RegisterVO.TXRLL);
		}

		/**
		 * ���������ʵ�����
		 * */
		//��ո������������ʷ�������
		billmodel.setValueAt("", editEvent.getRow(), "txnllfa_c");
		billmodel.setValueAt("", editEvent.getRow(), RegisterVO.TXNLLFA);
		billmodel.setValueAt(null, editEvent.getRow(), RegisterVO.QXRQ);
		billmodel.setValueAt(null, editEvent.getRow(), RegisterVO.TXJZ);
		billmodel.setValueAt(null, editEvent.getRow(), RegisterVO.TXLX);
		

	}

}
