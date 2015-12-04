package nc.ui.fbm.discountcalculate.listener;

import nc.ui.fbm.discountcalculate.DiscountCalculationUI;
import nc.ui.pub.beans.UIComboBox;
import nc.ui.pub.beans.UIRefPane;
import nc.ui.pub.bill.BillEditEvent;
import nc.ui.pub.bill.BillModel;
import nc.vo.fbm.register.RegisterVO;
import nc.vo.pub.lang.UFDouble;

/**
 * 
 * <p>
 *	贴现试算界面表头利率天数编辑监听类
 * <p>创建人：bsrl
 * <b>日期：2007-10-13
 *
 */
public class DiscountCalculateHeadLltsEditListener extends
DiscountCalculationAbstractItemEditListener {

	/**
	 * 
	 */
	public DiscountCalculateHeadLltsEditListener() {
	}

	/**
	 * @param ui
	 * @param itemKey
	 */
	public DiscountCalculateHeadLltsEditListener(DiscountCalculationUI ui, String itemKey) {
		super(ui, itemKey);
	}

	/**
	 * @param ui
	 * @param itemKey
	 * @param pos
	 */
	public DiscountCalculateHeadLltsEditListener(DiscountCalculationUI ui, String itemKey, int pos) {
		super(ui, itemKey, pos);
	}
	
	@Override
	public void responseEditEvent(BillEditEvent editEvent) {

		/**
		 * 利率天数的设置
		 * */
		Object o_content = null;
		
		UIComboBox content = (UIComboBox)getUI().getBillCardPanel().getHeadItem(editEvent.getKey()).getComponent();
		UIRefPane content3 = (UIRefPane)getUI().getBillCardPanel().getHeadItem(RegisterVO.TXNLL).getComponent();
		
		
		BillModel billmodel = getUI().getBillCardPanel().getBillModel(RegisterVO.TABLENAME);
		o_content = (Integer)content.getSelectdItemValue();

		
		/**
		 * 1.刷新列表中的对应项 
		 */
		//if(billmodel.getRowCount() > 1){
			for(int i=0; i < billmodel.getRowCount(); i++){
				
				if(getUI().isTotalRow(i)) continue;
				
				Object o = billmodel.getValueAt(i, RegisterVO.TXNLLFA);
				if(o != null && ((String)o).trim().length() != 0){
					continue;
				}
				
				billmodel.setValueAt(o_content, i, editEvent.getKey());
				
				
				if(o_content != null){
				//计算利率月利率和日利率
					UFDouble txnlv = (UFDouble)billmodel.getValueAt(i, RegisterVO.TXNLL);
					if(txnlv == null){
						if(content3.getText() == null || content3.getText().trim().length() == 0){
							//清空年月日利率
							billmodel.setValueAt(null,i, RegisterVO.TXNLL);
							billmodel.setValueAt(null,i, RegisterVO.TXYLL);
							billmodel.setValueAt(null,i, RegisterVO.TXRLL);
							
							continue;
						}else{
							txnlv = new UFDouble(content3.getText());
						}
					}
					
					billmodel.setValueAt(new UFDouble(txnlv.toDouble()*new Integer(10)/new Integer(12)),i, RegisterVO.TXYLL);
					billmodel.setValueAt(new UFDouble(txnlv.toDouble()*new Integer(10)/(Integer)o_content),i, RegisterVO.TXRLL);
				}else{
					billmodel.setValueAt(null,i, RegisterVO.TXYLL);
					billmodel.setValueAt(null,i, RegisterVO.TXRLL);
				}
				
				billmodel.setValueAt(null,i,RegisterVO.TXJZ);
				billmodel.setValueAt(null,i,RegisterVO.TXLX);
			}
		//}
	

	}

}
