package nc.ui.fbm.discountcalculate.listener;

import java.util.Vector;

import nc.ui.fbm.discountcalculate.DiscountCalculationUI;
import nc.ui.fbm.discountcalculate.DiscountErrorFlag;
import nc.ui.pub.beans.UIRefPane;
import nc.ui.pub.bill.BillCellEditor;
import nc.ui.pub.bill.BillEditEvent;
import nc.ui.pub.bill.BillModel;
import nc.vo.fbm.register.RegisterVO;
import nc.vo.pub.lang.UFDate;

/**
 * 
 * <p>
 *	贴现试算界面表体贴现年利率方案编辑监听类
 * <p>创建人：bsrl
 * <b>日期：2007-10-13
 *
 */
public class DiscountCalculateBodyTxnllfaEditListener extends
DiscountCalculationAbstractItemEditListener {

	/**
	 * 
	 */
	public DiscountCalculateBodyTxnllfaEditListener() {
	}

	/**
	 * @param ui
	 * @param itemKey
	 */
	public DiscountCalculateBodyTxnllfaEditListener(DiscountCalculationUI ui, String itemKey) {
		super(ui, itemKey);
	}

	/**
	 * @param ui
	 * @param itemKey
	 * @param pos
	 */
	public DiscountCalculateBodyTxnllfaEditListener(DiscountCalculationUI ui, String itemKey, int pos) {
		super(ui, itemKey, pos);
	}
	
	@Override
	public void responseEditEvent(BillEditEvent editEvent) {

		/**
		 * 贴现利率方案
		 * */
		//清空该行贴现年利率,利率天数设置
		StringBuffer err_str = new StringBuffer();
		BillModel billmodel = getUI().getBillCardPanel().getBillModel(RegisterVO.TABLENAME);
		UFDate qxrq = null; //当前起效日期
		UFDate txrq = null; //当前贴现日期
		
		//获得利率天数和起效日期
		UIRefPane content = (UIRefPane)((BillCellEditor)editEvent.getSource()).getComponent();
		String days = null;
		if(content.getSelectedData() != null && content.getSelectedData().size() > 0){
			days = (String)((Vector)content.getSelectedData().get(0)).get(5);
			qxrq = getUfDate(((Vector)content.getSelectedData().get(0)).get(4));
			billmodel.setValueAt(days, editEvent.getRow(), RegisterVO.LLTS);
		}else{
			billmodel.setValueAt(null, editEvent.getRow(), RegisterVO.LLTS);
		}
		
		//初始化当前贴现日期
		Object o_txrq = billmodel.getValueAt(editEvent.getRow(),RegisterVO.TXRQ);
		txrq = getUfDate(o_txrq);
		
		if(qxrq!=null){
			if(txrq == null){
				//贴现年利率方案pk通过公式添加
				//贴现年利率名称通过公式回写
				billmodel.setValueAt(qxrq, editEvent.getRow(), RegisterVO.TXRQ);//起效日期
			}else if(qxrq.after(txrq)){
				//error 年利率起效日期在票据贴现日期之后
				BuildErrorMessage(err_str, DiscountErrorFlag.ERROR_TXRQ_LT_QXRQ, editEvent.getRow()+1);
				billmodel.setValueAt(null, editEvent.getRow(), editEvent.getKey());
				billmodel.setValueAt(null, editEvent.getRow(), RegisterVO.TXNLLFA);
				billmodel.setValueAt(null, editEvent.getRow(), RegisterVO.TXRQ);//起效日期
			}
		}else{
			//error 起效日期不存在
			BuildErrorMessage(err_str, DiscountErrorFlag.ERROR_QXRQ_NOT_EXIST, editEvent.getRow()+1);
			billmodel.setValueAt(null, editEvent.getRow(), editEvent.getKey());
			billmodel.setValueAt(null, editEvent.getRow(), RegisterVO.TXNLLFA);//code
			billmodel.setValueAt(null, editEvent.getRow(), RegisterVO.TXRQ);//起效期日
		}
		
		billmodel.setValueAt(null, editEvent.getRow(), RegisterVO.TXNLL);
		billmodel.setValueAt(null, editEvent.getRow(), RegisterVO.TXYLL);
		billmodel.setValueAt(null, editEvent.getRow(), RegisterVO.TXRLL);
		billmodel.setValueAt(null, editEvent.getRow(), RegisterVO.TXJZ);
		billmodel.setValueAt(null, editEvent.getRow(), RegisterVO.TXLX);
		
		if(err_str.length() > 0){
			getUI().showErrorMessage(err_str.toString());
		}
		
	

	}

}
