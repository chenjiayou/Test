package nc.ui.fbm.discountcalculate.listener;

import nc.ui.fbm.discountcalculate.DiscountCalculationUI;
import nc.ui.pub.bill.BillEditEvent;
import nc.ui.pub.bill.BillModel;
import nc.vo.fbm.register.RegisterVO;
import nc.vo.pub.lang.UFDate;

/**
 * 
 * <p>
 *	贴现试算界面表头贴现延迟天数编辑监听器类
 * <p>创建人：bsrl
 * <b>日期：2007-10-13
 *
 */
public class DiscountCalculateHeadDiscountDelayDayNumEditListener extends
DiscountCalculationAbstractItemEditListener{
	/**
	 * 
	 */
	public DiscountCalculateHeadDiscountDelayDayNumEditListener() {
	}

	/**
	 * @param ui
	 * @param itemKey
	 */
	public DiscountCalculateHeadDiscountDelayDayNumEditListener(DiscountCalculationUI ui, String itemKey) {
		super(ui, itemKey);
	}

	/**
	 * @param ui
	 * @param itemKey
	 * @param pos
	 */
	public DiscountCalculateHeadDiscountDelayDayNumEditListener(DiscountCalculationUI ui, String itemKey, int pos) {
		super(ui, itemKey, pos);
	}
	
	@Override
	public void responseEditEvent(BillEditEvent editEvent) {
		BillModel billmodel = getUI().getBillCardPanel().getBillModel(RegisterVO.TABLENAME);
		
		int discountday = 0;
		Object delayday = getUI().getBillCardPanel().getHeadItem(RegisterVO.DISCOUNTDELAYDAYNUM).getValueObject();
		if(delayday != null && delayday.toString().trim().length() > 0) {
			discountday = getInteger(delayday).intValue();
		} 

		/**
		 * 1.刷新列表中的对应项 
		 */
		//if(billmodel.getRowCount() > 1){
			for(int i=0; i < billmodel.getRowCount(); i++){
				if(getUI().isTotalRow(i)) continue;
				UFDate txrq = getUfDate(getUI().getRefTakenProccessor().getValueByTakenInList(RegisterVO.TABLENAME, RegisterVO.TXRQ, i));//(RegisterVO.INVOICEDATE));
				UFDate dqrq = getUfDate(getUI().getRefTakenProccessor().getValueByTakenInList(RegisterVO.TABLENAME, RegisterVO.ENDDATE, i));			

				billmodel.setValueAt(discountday, i, RegisterVO.DISCOUNTDELAYDAYNUM);

				//计算贴现天数
			    if(txrq != null && dqrq != null ) {
			    	billmodel.setValueAt(UFDate.getDaysBetween(txrq, dqrq) + discountday, i, RegisterVO.TXTS);
			    }

			    billmodel.setValueAt(null,i,RegisterVO.TXJZ);
			    billmodel.setValueAt(null,i,RegisterVO.TXLX);
			}
		//}
	}

}
