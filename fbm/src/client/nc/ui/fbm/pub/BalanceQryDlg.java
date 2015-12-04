package nc.ui.fbm.pub;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


/**
 * �˴���������˵����
 * �������ڣ�(2004-12-14 9:18:42)
 * @author��Administrator
 */
public class BalanceQryDlg extends nc.ui.pub.beans.UIDialog implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private nc.ui.pub.beans.UIButton ivjUIButtonOK = null;
	private javax.swing.JPanel ivjUIDialogContentPane = null;
	private nc.ui.pub.beans.UILabel ivjUILabelBalance = null;
	private nc.ui.pub.beans.UILabel ivjUILabelBookBalance = null;
	private nc.ui.pub.beans.UITextField ivjUITextFieldBalance = null;
	private nc.ui.pub.beans.UITextField ivjUITextFieldBookBalance = null;

/**
 * BalanceQryDlg ������ע�⡣
 * @param parent java.awt.Container
 */
public BalanceQryDlg(java.awt.Container parent) {
	super(parent);
	initialize();
}
/**
 * BalanceQryDlg ������ע�⡣
 * @param parent java.awt.Container
 * @param title java.lang.String
 */
public BalanceQryDlg(java.awt.Container parent, String title) {
	super(parent, title);
	initialize();
}
/**
 * BalanceQryDlg ������ע�⡣
 * @param owner java.awt.Frame
 */
public BalanceQryDlg(java.awt.Frame owner) {
	super(owner);
	initialize();
}
/**
 * BalanceQryDlg ������ע�⡣
 * @param owner java.awt.Frame
 * @param title java.lang.String
 */
public BalanceQryDlg(java.awt.Frame owner, String title) {
	super(owner, title);
	initialize();
}
/**
 * ���� UIButtonOK ����ֵ��
 * @return nc.ui.pub.beans.UIButton
 */
/* ���棺�˷������������ɡ� */
private nc.ui.pub.beans.UIButton getUIButtonOK() {
	if (ivjUIButtonOK == null) {
		try {
			ivjUIButtonOK = new nc.ui.pub.beans.UIButton();
			ivjUIButtonOK.setName("UIButtonOK");
			ivjUIButtonOK.setText(nc.ui.ml.NCLangRes.getInstance().getStrByID("common","UC001-0000044")/*@res "ȷ��"*/);
			ivjUIButtonOK.setBounds(155, 129, 76, 22);
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	}
	return ivjUIButtonOK;
}
/**
 * ���� UIDialogContentPane ����ֵ��
 * @return javax.swing.JPanel
 */
/* ���棺�˷������������ɡ� */
private javax.swing.JPanel getUIDialogContentPane() {
	if (ivjUIDialogContentPane == null) {
		try {
			ivjUIDialogContentPane = new javax.swing.JPanel();
			ivjUIDialogContentPane.setName("UIDialogContentPane");
			ivjUIDialogContentPane.setLayout(null);
			//getUIDialogContentPane().add(getUILabelBalance(), getUILabelBalance().getName());
			getUIDialogContentPane().add(getUILabelBookBalance(), getUILabelBookBalance().getName());
			//getUIDialogContentPane().add(getUITextFieldBalance(), getUITextFieldBalance().getName());
			getUIDialogContentPane().add(getUITextFieldBookBalance(), getUITextFieldBookBalance().getName());
			getUIDialogContentPane().add(getUIButtonOK(), getUIButtonOK().getName());
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	}
	return ivjUIDialogContentPane;
}
/**
 * ���� UILabelBalance ����ֵ��
 * @return nc.ui.pub.beans.UILabel
 */
/* ���棺�˷������������ɡ� */
private nc.ui.pub.beans.UILabel getUILabelBalance() {
	if (ivjUILabelBalance == null) {
		try {
			ivjUILabelBalance = new nc.ui.pub.beans.UILabel();
			ivjUILabelBalance.setName("UILabelBalance");
			ivjUILabelBalance.setText(nc.ui.ml.NCLangRes.getInstance().getStrByID("36101010","UPP36101010-000019")/*@res "ʵʱ���"*/);
			ivjUILabelBalance.setForeground(java.awt.Color.black);
			ivjUILabelBalance.setILabelType(0/** JavaĬ��(�Զ���)*/);
			ivjUILabelBalance.setFont(new java.awt.Font("dialog", 0, 12));
			ivjUILabelBalance.setBounds(34, 21, 118, 22);
			ivjUILabelBalance.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	}
	return ivjUILabelBalance;
}
/**
 * ���� UILabelBookBalance ����ֵ��
 * @return nc.ui.pub.beans.UILabel
 */
/* ���棺�˷������������ɡ� */
private nc.ui.pub.beans.UILabel getUILabelBookBalance() {
	if (ivjUILabelBookBalance == null) {
		try {
			ivjUILabelBookBalance = new nc.ui.pub.beans.UILabel();
			ivjUILabelBookBalance.setName("UILabelBookBalance");
			ivjUILabelBookBalance.setText(nc.ui.ml.NCLangRes.getInstance().getStrByID("36101010","UPP36101010-000020")/*@res "�������"*/);
			ivjUILabelBookBalance.setForeground(java.awt.Color.black);
			ivjUILabelBookBalance.setILabelType(0/** JavaĬ��(�Զ���)*/);
			ivjUILabelBookBalance.setFont(new java.awt.Font("dialog", 0, 12));
			ivjUILabelBookBalance.setBounds(34, 63, 118, 22);
			ivjUILabelBookBalance.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	}
	return ivjUILabelBookBalance;
}
/**
 * ���� UITextFieldBalance ����ֵ��
 * @return nc.ui.pub.beans.UITextField
 */
/* ���棺�˷������������ɡ� */
private nc.ui.pub.beans.UITextField getUITextFieldBalance() {
	if (ivjUITextFieldBalance == null) {
		try {
			ivjUITextFieldBalance = new nc.ui.pub.beans.UITextField();
			ivjUITextFieldBalance.setName("UITextFieldBalance");
			ivjUITextFieldBalance.setLocation(159, 23);
			// user code begin {1}
			ivjUITextFieldBalance.setSize(128, 20);
			ivjUITextFieldBalance.setEnabled(false);
			ivjUITextFieldBalance.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	}
	return ivjUITextFieldBalance;
}
public nc.ui.pub.beans.UITextField getUITextFieldBalance_pub() {
	return getUITextFieldBalance();
}
/**
 * ���� UITextFieldBookBalance ����ֵ��
 * @return nc.ui.pub.beans.UITextField
 */
/* ���棺�˷������������ɡ� */
private nc.ui.pub.beans.UITextField getUITextFieldBookBalance() {
	if (ivjUITextFieldBookBalance == null) {
		try {
			ivjUITextFieldBookBalance = new nc.ui.pub.beans.UITextField();
			ivjUITextFieldBookBalance.setName("UITextFieldBookBalance");
			ivjUITextFieldBookBalance.setLocation(159, 65);
			// user code begin {1}
			ivjUITextFieldBookBalance.setSize(128, 20);
			ivjUITextFieldBookBalance.setEnabled(false);
			ivjUITextFieldBookBalance.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	}
	return ivjUITextFieldBookBalance;
}
public nc.ui.pub.beans.UITextField getUITextFieldBookBalance_pub() {
	return getUITextFieldBookBalance();
}
/**
 * ÿ�������׳��쳣ʱ������
 * @param exception java.lang.Throwable
 */
private void handleException(java.lang.Throwable exception) {


}
/**
 * ��ʼ���ࡣ
 */
/* ���棺�˷������������ɡ� */
private void initialize() {
	try {
		setName("BalanceQryDlg");
		setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		setSize(400, 221);
		setTitle(nc.ui.ml.NCLangRes.getInstance().getStrByID("36101010","UPP36101010-000021")/*@res "�������"*/);
		setContentPane(getUIDialogContentPane());
	} catch (java.lang.Throwable ivjExc) {
		handleException(ivjExc);
	}
	getUIButtonOK().addActionListener(this);
}
/* ���� Javadoc��
 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
 */
public void actionPerformed(ActionEvent e) {
    // TODO �Զ����ɷ������
    closeOK();
}
}