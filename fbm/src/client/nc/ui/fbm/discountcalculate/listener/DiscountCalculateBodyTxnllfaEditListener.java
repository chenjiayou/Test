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
 *	�����������������������ʷ����༭������
 * <p>�����ˣ�bsrl
 * <b>���ڣ�2007-10-13
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
		 * �������ʷ���
		 * */
		//��ո�������������,������������
		StringBuffer err_str = new StringBuffer();
		BillModel billmodel = getUI().getBillCardPanel().getBillModel(RegisterVO.TABLENAME);
		UFDate qxrq = null; //��ǰ��Ч����
		UFDate txrq = null; //��ǰ��������
		
		//���������������Ч����
		UIRefPane content = (UIRefPane)((BillCellEditor)editEvent.getSource()).getComponent();
		String days = null;
		if(content.getSelectedData() != null && content.getSelectedData().size() > 0){
			days = (String)((Vector)content.getSelectedData().get(0)).get(5);
			qxrq = getUfDate(((Vector)content.getSelectedData().get(0)).get(4));
			billmodel.setValueAt(days, editEvent.getRow(), RegisterVO.LLTS);
		}else{
			billmodel.setValueAt(null, editEvent.getRow(), RegisterVO.LLTS);
		}
		
		//��ʼ����ǰ��������
		Object o_txrq = billmodel.getValueAt(editEvent.getRow(),RegisterVO.TXRQ);
		txrq = getUfDate(o_txrq);
		
		if(qxrq!=null){
			if(txrq == null){
				//���������ʷ���pkͨ����ʽ���
				//��������������ͨ����ʽ��д
				billmodel.setValueAt(qxrq, editEvent.getRow(), RegisterVO.TXRQ);//��Ч����
			}else if(qxrq.after(txrq)){
				//error ��������Ч������Ʊ����������֮��
				BuildErrorMessage(err_str, DiscountErrorFlag.ERROR_TXRQ_LT_QXRQ, editEvent.getRow()+1);
				billmodel.setValueAt(null, editEvent.getRow(), editEvent.getKey());
				billmodel.setValueAt(null, editEvent.getRow(), RegisterVO.TXNLLFA);
				billmodel.setValueAt(null, editEvent.getRow(), RegisterVO.TXRQ);//��Ч����
			}
		}else{
			//error ��Ч���ڲ�����
			BuildErrorMessage(err_str, DiscountErrorFlag.ERROR_QXRQ_NOT_EXIST, editEvent.getRow()+1);
			billmodel.setValueAt(null, editEvent.getRow(), editEvent.getKey());
			billmodel.setValueAt(null, editEvent.getRow(), RegisterVO.TXNLLFA);//code
			billmodel.setValueAt(null, editEvent.getRow(), RegisterVO.TXRQ);//��Ч����
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
