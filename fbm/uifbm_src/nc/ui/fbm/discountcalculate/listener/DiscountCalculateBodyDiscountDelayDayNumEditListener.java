package nc.ui.fbm.discountcalculate.listener;

import nc.ui.fbm.discountcalculate.DiscountCalculationUI;
import nc.ui.pub.bill.BillEditEvent;
import nc.ui.pub.bill.BillModel;
import nc.vo.fbm.register.RegisterVO;
import nc.vo.pub.lang.UFDate;

/**
 * 
 * <p>
 *	贴现试算界面表体贴现延迟天数编辑监听类
 * <p>创建人：bsrl
 * <b>日期：2007-10-13
 *
 */
public class DiscountCalculateBodyDiscountDelayDayNumEditListener extends
DiscountCalculationAbstractItemEditListener{
	/**
	 * 
	 */
	public DiscountCalculateBodyDiscountDelayDayNumEditListener() {
	}

	/**
	 * @param ui
	 * @param itemKey
	 */
	public DiscountCalculateBodyDiscountDelayDayNumEditListener(DiscountCalculationUI ui, String itemKey) {
		super(ui, itemKey);
	}

	/**
	 * @param ui
	 * @param itemKey
	 * @param pos
	 */
	public DiscountCalculateBodyDiscountDelayDayNumEditListener(DiscountCalculationUI ui, String itemKey, int pos) {
		super(ui, itemKey, pos);
	}
	
	@Override
	public void responseEditEvent(BillEditEvent editEvent) {
		BillModel billmodel = getUI().getBillCardPanel().getBillModel(RegisterVO.TABLENAME);
	
		Object delayday = billmodel.getValueAt(editEvent.getRow(), RegisterVO.DISCOUNTDELAYDAYNUM);
		int delaydaynum = 0;
        if(delayday != null && delayday.toString().trim().length() > 0) {
        	delaydaynum = getInteger(delayday.toString()).intValue();
        }

		UFDate dqrq = getUfDate(getUI().getRefTakenProccessor().getValueByTakenInList(RegisterVO.TABLENAME, RegisterVO.ENDDATE, editEvent.getRow()));
		Object o = billmodel.getValueAt(editEvent.getRow(), RegisterVO.TXRQ);
		UFDate txrq = null;
		if(o != null && o.toString().trim().length() > 0){
		    txrq = getUfDate(o);
		}
        int txts = 0;
        if(txrq != null && dqrq != null) {
        	txts = UFDate.getDaysBetween(txrq, dqrq) + delaydaynum;
        } 
        billmodel.setValueAt( txts, editEvent.getRow(), RegisterVO.TXTS);
		
        billmodel.setValueAt(null, editEvent.getRow(), RegisterVO.TXJZ);
        billmodel.setValueAt(null, editEvent.getRow(), RegisterVO.TXLX);
		
	

	}
}
