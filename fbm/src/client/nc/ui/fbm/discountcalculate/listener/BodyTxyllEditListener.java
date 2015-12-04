package nc.ui.fbm.discountcalculate.listener;

import nc.ui.fbm.discountcalculate.DiscountCalculationUI;
import nc.ui.pub.bill.BillEditEvent;
import nc.ui.pub.bill.BillModel;
import nc.vo.fbm.register.RegisterVO;
import nc.vo.pub.lang.UFDouble;

/**
 * 
 * <p>
 *	贴现试算表体贴现月利率编辑监听类
 * <p>创建人：bsrl
 * <b>日期：2007-12-03
 *
 */
public class BodyTxyllEditListener  extends
DiscountCalculationAbstractItemEditListener{
	/**
	 * 
	 */
	public BodyTxyllEditListener() {
	}

	/**
	 * @param ui
	 * @param itemKey
	 */
	public BodyTxyllEditListener(DiscountCalculationUI ui, String itemKey) {
		super(ui, itemKey);
	}

	/**
	 * @param ui
	 * @param itemKey
	 * @param pos
	 */
	public BodyTxyllEditListener(DiscountCalculationUI ui, String itemKey, int pos) {
		super(ui, itemKey, pos);
	}
	
	@Override
	public void responseEditEvent(BillEditEvent editEvent) {
		BillModel billmodel = getUI().getBillCardPanel().getBillModel(RegisterVO.TABLENAME);
		if(billmodel.getValueAt(editEvent.getRow(),RegisterVO.LLTS ) == null){
			billmodel.setValueAt(360, editEvent.getRow(), RegisterVO.LLTS);
		}
		Object txyll = billmodel.getValueAt(editEvent.getRow(), RegisterVO.TXYLL);
		if(txyll != null && txyll.toString().trim().length() > 0) {
			Object llts = billmodel.getValueAt(editEvent.getRow(), RegisterVO.LLTS);
	        Integer i_llts = new Integer((String)llts);
	        billmodel.setValueAt(new UFDouble((((UFDouble)txyll).toDouble()*new Integer(12)/new Integer(10))), editEvent.getRow(), RegisterVO.TXNLL);
			Object txnll = billmodel.getValueAt(editEvent.getRow(), RegisterVO.TXNLL);
	        billmodel.setValueAt(new UFDouble((((UFDouble)txnll).toDouble()*new Integer(10)/i_llts)), editEvent.getRow(), RegisterVO.TXRLL);
		}else {
			billmodel.setValueAt(null, editEvent.getRow(), RegisterVO.TXNLL);
			billmodel.setValueAt(null, editEvent.getRow(), RegisterVO.TXRLL);
		}

		/**
		 * 贴现年利率的设置
		 * */
		//清空该行贴现年利率方案设置
		billmodel.setValueAt("", editEvent.getRow(),"txnllfa_c");
		billmodel.setValueAt("", editEvent.getRow(), RegisterVO.TXNLLFA);
		billmodel.setValueAt(null, editEvent.getRow(), RegisterVO.QXRQ);
		billmodel.setValueAt(null, editEvent.getRow(), RegisterVO.TXJZ);
		billmodel.setValueAt(null, editEvent.getRow(), RegisterVO.TXLX);
		

	}
}
