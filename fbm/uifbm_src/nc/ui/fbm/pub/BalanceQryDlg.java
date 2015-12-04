package nc.ui.fbm.pub;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


/**
 * 此处插入类型说明。
 * 创建日期：(2004-12-14 9:18:42)
 * @author：Administrator
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
 * BalanceQryDlg 构造子注解。
 * @param parent java.awt.Container
 */
public BalanceQryDlg(java.awt.Container parent) {
	super(parent);
	initialize();
}
/**
 * BalanceQryDlg 构造子注解。
 * @param parent java.awt.Container
 * @param title java.lang.String
 */
public BalanceQryDlg(java.awt.Container parent, String title) {
	super(parent, title);
	initialize();
}
/**
 * BalanceQryDlg 构造子注解。
 * @param owner java.awt.Frame
 */
public BalanceQryDlg(java.awt.Frame owner) {
	super(owner);
	initialize();
}
/**
 * BalanceQryDlg 构造子注解。
 * @param owner java.awt.Frame
 * @param title java.lang.String
 */
public BalanceQryDlg(java.awt.Frame owner, String title) {
	super(owner, title);
	initialize();
}
/**
 * 返回 UIButtonOK 特性值。
 * @return nc.ui.pub.beans.UIButton
 */
/* 警告：此方法将重新生成。 */
private nc.ui.pub.beans.UIButton getUIButtonOK() {
	if (ivjUIButtonOK == null) {
		try {
			ivjUIButtonOK = new nc.ui.pub.beans.UIButton();
			ivjUIButtonOK.setName("UIButtonOK");
			ivjUIButtonOK.setText(nc.ui.ml.NCLangRes.getInstance().getStrByID("common","UC001-0000044")/*@res "确定"*/);
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
 * 返回 UIDialogContentPane 特性值。
 * @return javax.swing.JPanel
 */
/* 警告：此方法将重新生成。 */
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
 * 返回 UILabelBalance 特性值。
 * @return nc.ui.pub.beans.UILabel
 */
/* 警告：此方法将重新生成。 */
private nc.ui.pub.beans.UILabel getUILabelBalance() {
	if (ivjUILabelBalance == null) {
		try {
			ivjUILabelBalance = new nc.ui.pub.beans.UILabel();
			ivjUILabelBalance.setName("UILabelBalance");
			ivjUILabelBalance.setText(nc.ui.ml.NCLangRes.getInstance().getStrByID("36101010","UPP36101010-000019")/*@res "实时余额"*/);
			ivjUILabelBalance.setForeground(java.awt.Color.black);
			ivjUILabelBalance.setILabelType(0/** Java默认(自定义)*/);
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
 * 返回 UILabelBookBalance 特性值。
 * @return nc.ui.pub.beans.UILabel
 */
/* 警告：此方法将重新生成。 */
private nc.ui.pub.beans.UILabel getUILabelBookBalance() {
	if (ivjUILabelBookBalance == null) {
		try {
			ivjUILabelBookBalance = new nc.ui.pub.beans.UILabel();
			ivjUILabelBookBalance.setName("UILabelBookBalance");
			ivjUILabelBookBalance.setText(nc.ui.ml.NCLangRes.getInstance().getStrByID("36101010","UPP36101010-000020")/*@res "账面余额"*/);
			ivjUILabelBookBalance.setForeground(java.awt.Color.black);
			ivjUILabelBookBalance.setILabelType(0/** Java默认(自定义)*/);
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
 * 返回 UITextFieldBalance 特性值。
 * @return nc.ui.pub.beans.UITextField
 */
/* 警告：此方法将重新生成。 */
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
 * 返回 UITextFieldBookBalance 特性值。
 * @return nc.ui.pub.beans.UITextField
 */
/* 警告：此方法将重新生成。 */
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
 * 每当部件抛出异常时被调用
 * @param exception java.lang.Throwable
 */
private void handleException(java.lang.Throwable exception) {


}
/**
 * 初始化类。
 */
/* 警告：此方法将重新生成。 */
private void initialize() {
	try {
		setName("BalanceQryDlg");
		setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		setSize(400, 221);
		setTitle(nc.ui.ml.NCLangRes.getInstance().getStrByID("36101010","UPP36101010-000021")/*@res "联查余额"*/);
		setContentPane(getUIDialogContentPane());
	} catch (java.lang.Throwable ivjExc) {
		handleException(ivjExc);
	}
	getUIButtonOK().addActionListener(this);
}
/* （非 Javadoc）
 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
 */
public void actionPerformed(ActionEvent e) {
    // TODO 自动生成方法存根
    closeOK();
}
}