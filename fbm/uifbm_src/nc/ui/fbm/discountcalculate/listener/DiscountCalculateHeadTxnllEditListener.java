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
 *	贴现试算界面表头贴现年利率编辑监听类
 * <p>创建人：bsrl
 * <b>日期：2007-10-13
 *
 */
public class DiscountCalculateHeadTxnllEditListener extends
DiscountCalculationAbstractItemEditListener {

	/**
	 * 
	 */
	public DiscountCalculateHeadTxnllEditListener() {
	}

	/**
	 * @param ui
	 * @param itemKey
	 */
	public DiscountCalculateHeadTxnllEditListener(DiscountCalculationUI ui, String itemKey) {
		super(ui, itemKey);
	}

	/**
	 * @param ui
	 * @param itemKey
	 * @param pos
	 */
	public DiscountCalculateHeadTxnllEditListener(DiscountCalculationUI ui, String itemKey, int pos) {
		super(ui, itemKey, pos);
	}
	
	
	@Override
	public void responseEditEvent(BillEditEvent editEvent) {

		/**
		 * 贴现年利率的设置
		 * */
		Object o_content = null;
		UIRefPane content = (UIRefPane)getUI().getBillCardPanel().getHeadItem(editEvent.getKey()).getComponent();
		UIRefPane txllfa = (UIRefPane)getUI().getBillCardPanel().getHeadItem(RegisterVO.TXNLLFA).getComponent();
		o_content = content.getText();	
		
		BillModel billmodel = getUI().getBillCardPanel().getBillModel(RegisterVO.TABLENAME);
		
		/**
		 * 1.表头贴现利率方案置空
		 */
		txllfa.setPK(null);
		
		/**
		 * 2.设置利率天数可编辑,并操作默认值为360
		 * */
		UIComboBox content3 = (UIComboBox)getUI().getBillCardPanel().getHeadItem(RegisterVO.LLTS).getComponent();
		boolean flagfirst = false;
		if(!content3.isEnabled() ||(content3.isEnabled() && content3.getSelectedIndex() == -1)){
			if(content3.getSelectedIndex() == -1){
				content3.setSelectedIndex(0);
			}
			content3.setEnabled(true);
			flagfirst = true;
		}
		
		/**
		 * 3.刷新列表中的对应项,并清空贴现年利率方案
		 */
		//if(billmodel.getRowCount() > 1){
			for(int i=0; i < billmodel.getRowCount(); i++){
				if(getUI().isTotalRow(i)) continue;
				
				billmodel.setValueAt(o_content, i, editEvent.getKey());
				if(flagfirst){
					billmodel.setValueAt(content3.getSelectdItemValue(), i, RegisterVO.LLTS);
				}
				billmodel.setValueAt(null, i, "txnllfa_c");
				billmodel.setValueAt(null, i, RegisterVO.TXNLLFA);
				billmodel.setValueAt(null, i, RegisterVO.QXRQ);
				billmodel.setValueAt(null, i, RegisterVO.TXJZ);
				billmodel.setValueAt(null, i, RegisterVO.TXLX);
				
				if(o_content!= null){
					if(((String)o_content).trim().length() != 0){
						UFDouble od =new UFDouble((String)o_content);
						
						//重新计算贴现月利率，日利率
						billmodel.setValueAt(new UFDouble((od.toDouble()*new Integer(10)/new Integer(12))), i, RegisterVO.TXYLL);
						billmodel.setValueAt(new UFDouble((od.toDouble()*new Integer(10)/(Integer)content3.getSelectdItemValue())), i, RegisterVO.TXRLL);
					}else{
//						重新计算贴现月利率，日利率
						billmodel.setValueAt(null, i, RegisterVO.TXYLL);
						billmodel.setValueAt(null, i, RegisterVO.TXRLL);
					}
				}
				
		//	}
		}	
	

	}

}
