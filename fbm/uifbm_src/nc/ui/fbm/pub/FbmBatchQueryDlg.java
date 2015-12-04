package nc.ui.fbm.pub;

import java.awt.Container;

import javax.swing.JScrollPane;

import nc.ui.fac.account.pub.AbstractOKCancleDlg;
import nc.ui.pub.beans.UILabel;
import nc.ui.pub.beans.UITextArea;

public class FbmBatchQueryDlg extends AbstractOKCancleDlg {


	private static final long serialVersionUID = -2457383360934095721L;

	private UITextArea txtBillNo = null;
	private UILabel lblHolderAcc = null;
	private JScrollPane scrollPane = null;
	
	public FbmBatchQueryDlg(Container parent) {
		super(parent);
		initialize();
	}

	private void initialize() {
		setTitle("������ѯ");
		getNorthPanel().setLayout(null);
		getNorthPanel().add(getLalHolderAcc(),getLalHolderAcc().getName());
//		getNorthPanel().add(getTxtBillNo(),getTxtBillNo().getName());
		getNorthPanel().add(getScrollPane(),getScrollPane().getName());
	}
	

	public UILabel getLalHolderAcc(){
		if(lblHolderAcc == null){
			lblHolderAcc = new UILabel();
			lblHolderAcc.setText("Ʊ�ݱ��(���Ʊ�ݱ��ʱһ��Ʊ�ݱ��ռһ��)");
			lblHolderAcc.setBounds(40, 15, 250, 20);
			
		}

		return lblHolderAcc;

	}
	
	
	public UITextArea getTxtBillNo() {
		if(txtBillNo == null){
			txtBillNo = new UITextArea();
			txtBillNo.setName("fbmbillno");
	        txtBillNo.setBounds(40, 35, 240, 200);
		}

		return txtBillNo;
	}
	
	public JScrollPane getScrollPane(){
		if(scrollPane == null){
			scrollPane = new JScrollPane(getTxtBillNo());
			scrollPane.setBounds(40, 35, 240, 200);
		}
		
		return scrollPane;
	}
	
}
