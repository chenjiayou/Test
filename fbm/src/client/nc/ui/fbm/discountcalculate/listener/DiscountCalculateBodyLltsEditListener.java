package nc.ui.fbm.discountcalculate.listener;

import nc.ui.pub.bill.BillEditEvent;
import nc.ui.pub.bill.BillModel;
import nc.vo.fbm.register.RegisterVO;

/**
 * 
 * <p>
 *	贴现试算界面表体利率天数编辑监听类
 * <p>创建人：bsrl
 * <b>日期：2007-10-13
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
		 * 利率天数的设置
		 * */
		BillModel billmodel = getUI().getBillCardPanel().getBillModel(RegisterVO.TABLENAME);

		//清空该行贴现年利率方案设置
		billmodel.setValueAt("", editEvent.getRow(), "txnllfa_c");
		billmodel.setValueAt(null, editEvent.getRow(), RegisterVO.TXJZ);
		billmodel.setValueAt(null, editEvent.getRow(), RegisterVO.TXLX);
	

	}

}
