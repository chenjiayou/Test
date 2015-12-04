package nc.ui.fbm.discountcalculate.listener;

import java.util.ArrayList;

import nc.ui.fbm.discountcalculate.DiscountCalculationUI;
import nc.ui.fbm.discountcalculate.DiscountErrorFlag;
import nc.ui.pub.beans.UIRefPane;
import nc.ui.pub.bill.BillEditEvent;
import nc.ui.pub.bill.BillModel;
import nc.vo.fbm.register.RegisterVO;
import nc.vo.pub.lang.UFDate;

/**
 * 
 * <p>
 *	贴现试算界面表头贴现日期编辑监听类
 * <p>创建人：bsrl
 * <b>日期：2007-10-13
 *
 */
public class DiscountCalculateHeadTxrqEditListener extends
DiscountCalculationAbstractItemEditListener {

	/**
	 * 
	 */
	public DiscountCalculateHeadTxrqEditListener() {
	}

	/**
	 * @param ui
	 * @param itemKey
	 */
	public DiscountCalculateHeadTxrqEditListener(DiscountCalculationUI ui, String itemKey) {
		super(ui, itemKey);
	}

	/**
	 * @param ui
	 * @param itemKey
	 * @param pos
	 */
	public DiscountCalculateHeadTxrqEditListener(DiscountCalculationUI ui, String itemKey, int pos) {
		super(ui, itemKey, pos);
	}
	
	@Override
	public void responseEditEvent(BillEditEvent editEvent) {

		/**
		 * 设置贴现日期
		 * */
		StringBuffer err_str = new StringBuffer();
		Object o_content = null;
		UIRefPane content = (UIRefPane)getUI().getBillCardPanel().getHeadItem(editEvent.getKey()).getComponent();
		BillModel billmodel = getUI().getBillCardPanel().getBillModel(RegisterVO.TABLENAME);
		o_content = content.getText();
		ArrayList<Integer> erRow = new ArrayList<Integer>();
		ArrayList<Integer> erRow2 = new ArrayList<Integer>();
		
		int discountday = 0;
		Object delayday = getUI().getBillCardPanel().getHeadItem(RegisterVO.DISCOUNTDELAYDAYNUM).getValueObject();
		if(delayday != null && delayday.toString().trim().length() > 0) {
			discountday = getInteger((String)delayday).intValue();
		}
		UFDate txrq = getUfDate(o_content);
		
		/**
		 * 1.刷新列表中的对应项
		 */
		//if(billmodel.getRowCount() > 1){
			for(int i=0; i < billmodel.getRowCount(); i++){
				if(getUI().isTotalRow(i)) continue;
				
				if(o_content != null && !((String)o_content).trim().equals("")){
					
					//UFDate cprq = getUfDate(getUI().getRefTakenProccessor().getValueByTakenInList(RegisterVO.TABLENAME, RegisterVO.INVOICEDATE, i));//(RegisterVO.INVOICEDATE));
					//UFDate dqrq = getUfDate(getUI().getRefTakenProccessor().getValueByTakenInList(RegisterVO.TABLENAME, RegisterVO.ENDDATE, i));
					UFDate cprq = getUfDate(billmodel.getValueAt(i, RegisterVO.INVOICEDATE));
					UFDate dqrq = getUfDate(billmodel.getValueAt(i, RegisterVO.ENDDATE));
					
					UFDate qxrq = null;
					if(getUI().getRefTakenProccessor().getValueByTakenInList(RegisterVO.TABLENAME, RegisterVO.QXRQ, i) != null) {
						qxrq = getUfDate(getUI().getRefTakenProccessor().getValueByTakenInList(RegisterVO.TABLENAME, RegisterVO.QXRQ, i));
					}
					
					
					if(cprq.after(txrq)){
						erRow.add(new Integer(i+1));
						billmodel.setValueAt(null, i, RegisterVO.TXRQ);
						billmodel.setValueAt(null, i, RegisterVO.TXTS);
						continue;
					}
					
					if(qxrq != null && qxrq.after(txrq)){
						erRow2.add(new Integer(i+1));
						billmodel.setValueAt(null, i, RegisterVO.TXRQ);
						billmodel.setValueAt(null, i, RegisterVO.TXTS);
						continue;
					}
					
					//计算贴现天数
					if(txrq.after(dqrq)){
						billmodel.setValueAt(0, i, RegisterVO.TXTS);
					}else{
						billmodel.setValueAt(UFDate.getDaysBetween(txrq, dqrq) + discountday, i, RegisterVO.TXTS);
					}
					
				}
				
				billmodel.setValueAt(o_content, i, editEvent.getKey());
				billmodel.setValueAt(null,i,RegisterVO.TXJZ);
				billmodel.setValueAt(null,i,RegisterVO.TXLX);
			}
		//}
		
		int[] row = new int[erRow.size()];
		for(int i = 0; i < erRow.size(); i ++) {
			row[i] = erRow.get(i).intValue();
		}
		
		int[] row2 = new int[erRow2.size()];
		for(int i = 0; i < erRow2.size(); i ++) {
			row2[i] = erRow2.get(i).intValue();
		}
		/**
		 * 2.如果需要显示错误信息
		 * */
		if(erRow.size() != 0){
			err_str.append(BuildErrorMessage(row, DiscountErrorFlag.ERROR_TXRQ_LT_CPRQ));
		}
		
		if(erRow2.size() != 0){
			err_str.append(BuildErrorMessage(row2, DiscountErrorFlag.ERROR_TXRQ_LT_QXRQ));
		}
		
		if(err_str.length() != 0){
			getUI().showErrorMessage(err_str.toString());
		}
		
	

	}

}
