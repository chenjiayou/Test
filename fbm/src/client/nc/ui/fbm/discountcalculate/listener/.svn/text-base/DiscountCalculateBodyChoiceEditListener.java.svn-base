package nc.ui.fbm.discountcalculate.listener;

import nc.ui.fbm.discountcalculate.DiscountCalculationUI;
import nc.ui.pub.beans.UICheckBox;
import nc.ui.pub.beans.UITable;
import nc.ui.pub.bill.BillCardPanel;
import nc.ui.pub.bill.BillEditEvent;
import nc.ui.pub.bill.IBillItem;
import nc.vo.fbm.register.RegisterVO;

/**
 * 
 * <p>
 * 表体选择事件发生后的处理监听
 * <p>创建人：hzguo
 * <b>日期：2008-07-10
 *
 */
public class DiscountCalculateBodyChoiceEditListener extends
DiscountCalculationAbstractItemEditListener {

	/**
	 * 
	 */
	public DiscountCalculateBodyChoiceEditListener() {
	}

	/**
	 * @param ui
	 * @param itemKey
	 */
	public DiscountCalculateBodyChoiceEditListener(DiscountCalculationUI ui) {
		super(ui, RegisterVO.CHOICE,IBillItem.BODY);
	}

	/**
	 * @param ui
	 * @param itemKey
	 * @param pos
	 */
	public DiscountCalculateBodyChoiceEditListener(DiscountCalculationUI ui, String itemKey, int pos) {
		super(ui, itemKey, pos);
	}
	
	@Override
	public void responseEditEvent(BillEditEvent editEvent) {
		BillCardPanel billCardPanel = getUI().getBillCardPanel();
		UITable ut = billCardPanel.getBillTable();
		Boolean choices = (Boolean)ut.getModel().getValueAt(editEvent.getRow(), billCardPanel.getBodyColByKey(RegisterVO.CHOICE));
		if (!choices.booleanValue()){
//			UITable ut = getBillCardPanel().getBillTable();
			ut.getModel().setValueAt(null, editEvent.getRow(), billCardPanel.getBodyColByKey(RegisterVO.TXLX));
			ut.getModel().setValueAt(null, editEvent.getRow(), billCardPanel.getBodyColByKey(RegisterVO.TXJZ));
//			ut.getModel().setValueAt(null, e.getRow(), getBillCardPanel().getBodyColByKey("txrlv"));
//			ut.getModel().setValueAt(null, e.getRow(), getBillCardPanel().getBodyColByKey("txylv"));
			((UICheckBox)billCardPanel.getHeadItem(RegisterVO.ALL_CHOICE).getComponent()).setSelected(false);
		}

	}

}
