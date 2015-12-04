package nc.ui.fbm.discountcalculate.listener;

import java.util.Vector;

import nc.ui.fbm.discountcalculate.DiscountCalculationUI;
import nc.ui.fbm.discountcalculate.DiscountErrorFlag;
import nc.ui.pub.beans.UICheckBox;
import nc.ui.pub.beans.UIComboBox;
import nc.ui.pub.beans.UIRefPane;
import nc.ui.pub.bill.BillEditEvent;
import nc.ui.pub.bill.BillModel;
import nc.vo.fbm.register.RegisterVO;
import nc.vo.pub.lang.UFDate;

/**
 * 
 * <p>
 *	������������ͷ���������ʷ����༭������
 * <p>�����ˣ�bsrl
 * <b>���ڣ�2007-10-13
 *
 */
public class DiscountCalculateHeadTxnllfaEditListener extends
DiscountCalculationAbstractItemEditListener {
	/**
	 * 
	 */
	public DiscountCalculateHeadTxnllfaEditListener() {
	}

	/**
	 * @param ui
	 * @param itemKey
	 */
	public DiscountCalculateHeadTxnllfaEditListener(DiscountCalculationUI ui, String itemKey) {
		super(ui, itemKey);
	}

	/**
	 * @param ui
	 * @param itemKey
	 * @param pos
	 */
	public DiscountCalculateHeadTxnllfaEditListener(DiscountCalculationUI ui, String itemKey, int pos) {
		super(ui, itemKey, pos);
	}
	
	@Override
	public void responseEditEvent(BillEditEvent editEvent) {
		/**
		 * �������ʷ���
		 * */
		UICheckBox m_check = (UICheckBox)getUI().getBillCardPanel().getHeadItem("autocal").getComponent();
		boolean b_auto = m_check.isSelected();
		
		StringBuffer err_str = new StringBuffer();

		Object o_content = null;
		
		UIRefPane content = (UIRefPane)getUI().getBillCardPanel().getHeadItem(editEvent.getKey()).getComponent();
		o_content = content.getRefName();
		Object o_content1 = null;
		Object o_content2 = null;
		if(o_content != null){
			o_content1 = content.getRefPK();
		}
		/**
		 * 1.��ձ�ͷ����,���̶�������Ϊ��
		 * */
		UIRefPane content2 = (UIRefPane)getUI().getBillCardPanel().getHeadItem(RegisterVO.TXNLL).getComponent();
		content2.setPK(null);//�������¼�,���ֵ
		UIComboBox content3 = (UIComboBox)getUI().getBillCardPanel().getHeadItem(RegisterVO.LLTS).getComponent();
		String days = null;
		if(content.getSelectedData() != null && content.getSelectedData().size() > 0){
			days = (String)((Vector)content.getSelectedData().get(0)).get(5);
			o_content2 = getUfDate(((Vector)content.getSelectedData().get(0)).get(4));
			content3.setSelectedIndex(content3.getItemIndexByName(days.trim()));
		}else{
			content3.setSelectedIndex(-1);
		}
		content3.setEnabled(false);
		
		/**
		 * 2.������б�������
		 * */
		BillModel billmodel = getUI().getBillCardPanel().getBillModel(RegisterVO.TABLENAME);

		UFDate qxrq = null;
		UFDate txrq = null;
		if(content.getSelectedData() != null && content.getSelectedData().size() > 0){
			qxrq = getUfDate(((Vector)content.getSelectedData().get(0)).get(4));
		}
		
		//if(billmodel.getRowCount() > 1){
			for(int i=0; i < billmodel.getRowCount(); i++){
				if(getUI().isTotalRow(i)) continue;
				Object o_txrq = billmodel.getValueAt(i,RegisterVO.TXRQ);
				txrq = getUfDate(o_txrq);
				if(qxrq != null){
					if (o_txrq == null){
						billmodel.setValueAt(o_content, i, editEvent.getKey()+"_c");
						billmodel.setValueAt(o_content1, i, editEvent.getKey());
						billmodel.setValueAt(o_content2, i, RegisterVO.QXRQ);
					}else if(qxrq.after(txrq)){
						//error ��������Ч������Ʊ����������֮��
						BuildErrorMessage(err_str, DiscountErrorFlag.ERROR_TXRQ_LT_QXRQ, i+1);
						billmodel.setValueAt(null, i, editEvent.getKey()+"_c");
						billmodel.setValueAt(null, i, editEvent.getKey());
						billmodel.setValueAt(null, i, RegisterVO.QXRQ);
					}else {
						billmodel.setValueAt(o_content, i, editEvent.getKey()+"_c");//name
						billmodel.setValueAt(o_content1, i, editEvent.getKey());//code
						billmodel.setValueAt(o_content2, i, RegisterVO.QXRQ);//��Ч����
					}
				}else{
					//error ��Ч���ڲ�����
					BuildErrorMessage(err_str, DiscountErrorFlag.ERROR_QXRQ_NOT_EXIST, i+1);
					billmodel.setValueAt(null, i, editEvent.getKey()+"_c");
					billmodel.setValueAt(null, i, editEvent.getKey());//code
					billmodel.setValueAt(null, i, RegisterVO.QXRQ);//��Ч����
				}
				billmodel.setValueAt(null,i,RegisterVO.TXNLL);
				billmodel.setValueAt(null,i,RegisterVO.TXYLL);
				billmodel.setValueAt(null,i,RegisterVO.TXRLL);
				billmodel.setValueAt(null,i,RegisterVO.TXJZ);
				billmodel.setValueAt(null,i,RegisterVO.TXLX);
				if(days!=null){
					billmodel.setValueAt(days,i,RegisterVO.LLTS);
				}else{
					billmodel.setValueAt(null,i,RegisterVO.LLTS);
				}
			}
	//	}
		if(err_str.length() > 0){
			getUI().showErrorMessage(err_str.toString());
		}
	}
}
