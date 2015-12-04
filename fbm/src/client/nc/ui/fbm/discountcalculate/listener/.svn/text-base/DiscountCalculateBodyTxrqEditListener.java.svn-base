package nc.ui.fbm.discountcalculate.listener;

import nc.ui.fbm.discountcalculate.DiscountCalculationUI;
import nc.ui.fbm.discountcalculate.DiscountErrorFlag;
import nc.ui.pub.bill.BillEditEvent;
import nc.ui.pub.bill.BillModel;
import nc.vo.fbm.register.RegisterVO;
import nc.vo.pub.lang.UFDate;

/**
 * 
 * <p>
 *	贴现试算界面表体贴现日期编辑监听类
 * <p>创建人：bsrl
 * <b>日期：2007-10-13
 *
 */
public class DiscountCalculateBodyTxrqEditListener extends
DiscountCalculationAbstractItemEditListener {

	/**
	 * 
	 */
	public DiscountCalculateBodyTxrqEditListener() {
	}

	/**
	 * @param ui
	 * @param itemKey
	 */
	public DiscountCalculateBodyTxrqEditListener(DiscountCalculationUI ui, String itemKey) {
		super(ui, itemKey);
	}

	/**
	 * @param ui
	 * @param itemKey
	 * @param pos
	 */
	public DiscountCalculateBodyTxrqEditListener(DiscountCalculationUI ui, String itemKey, int pos) {
		super(ui, itemKey, pos);
	}

	@Override
	public void responseEditEvent(BillEditEvent editEvent) {

		/**
		 * 贴现日期的设置
		 * */
		
		//清空该行贴现年利率方案设置
		BillModel billmodel = getUI().getBillCardPanel().getBillModel(RegisterVO.TABLENAME);
		UFDate cprq = getUfDate(getUI().getRefTakenProccessor().getValueByTakenInList(RegisterVO.TABLENAME, RegisterVO.INVOICEDATE, editEvent.getRow()));
		UFDate dqrq = getUfDate(getUI().getRefTakenProccessor().getValueByTakenInList(RegisterVO.TABLENAME, RegisterVO.ENDDATE, editEvent.getRow()));
		UFDate qxrq = null;
		if((String)getUI().getRefTakenProccessor().getValueByTakenInList(RegisterVO.TABLENAME, RegisterVO.QXRQ, editEvent.getRow()) != null) {
			qxrq = getUfDate(getUI().getRefTakenProccessor().getValueByTakenInList(RegisterVO.TABLENAME, RegisterVO.QXRQ, editEvent.getRow()));
		}
		
		Object o = billmodel.getValueAt(editEvent.getRow(), RegisterVO.TXRQ);
		Object delayday = billmodel.getValueAt(editEvent.getRow(), RegisterVO.DISCOUNTDELAYDAYNUM);
		int delaydaynum = 0;
        if(delayday != null && delayday.toString().trim().length() > 0) {
        	delaydaynum = getInteger(delayday).intValue();
        }
		if(o != null){
			UFDate txrq = new UFDate(o.toString());
			
			if(txrq != null && cprq != null && cprq.after(txrq)){
				ShowMessageBox(new int[]{editEvent.getRow()+1}, DiscountErrorFlag.ERROR_TXRQ_LT_CPRQ);
				billmodel.setValueAt(null,editEvent.getRow(), RegisterVO.TXRQ);
				billmodel.setValueAt(null, editEvent.getRow(), RegisterVO.TXTS);
			}else if(txrq != null && qxrq != null && qxrq.after(txrq)){
				ShowMessageBox(new int[]{editEvent.getRow()+1}, DiscountErrorFlag.ERROR_TXRQ_LT_QXRQ);
				billmodel.setValueAt(null,editEvent.getRow(), RegisterVO.TXRQ);
				billmodel.setValueAt(null, editEvent.getRow(), RegisterVO.TXTS);
			}else{
				//计算贴现天数
				if(txrq.after(dqrq)){
					billmodel.setValueAt(0, editEvent.getRow(), RegisterVO.TXTS);
				}else{
					billmodel.setValueAt(UFDate.getDaysBetween(txrq, dqrq) + delaydaynum, editEvent.getRow(),RegisterVO.TXTS);
				}
			}
			
		}
		
		billmodel.setValueAt(null, editEvent.getRow(), RegisterVO.TXJZ);
		billmodel.setValueAt(null, editEvent.getRow(), RegisterVO.TXLX);
		
	

	}

}
