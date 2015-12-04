/**
 *
 */
package nc.ui.fbm.storage.innerkeep;

import java.awt.Container;
import java.awt.Dimension;

import nc.ui.fac.account.pub.AbstractOKCancleDlg;
import nc.ui.pub.beans.UILabel;
import nc.ui.pub.beans.UITextField;

/**
 * <p>
 * �ڲ��й�-�˻ضԻ���
 * <p>�����ˣ�lpf
 * <b>���ڣ�2007-10-15
 *
 */
public class ReturnReatonInputDlg extends AbstractOKCancleDlg {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private UILabel lbReason = null;
	private UITextField txtReason = null;

	/**
	 * @param parent
	 */
	public ReturnReatonInputDlg(Container parent) {
		super(parent);
		initialize();
	}

	private void initialize() {
		setTitle(nc.ui.ml.NCLangRes.getInstance().getStrByID("36201017","UPP36201017-000003")/* @res"�ڲ��й��˻�"*/);
		getNorthPanel().add(getTxtLabel(),getTxtLabel().getName());
		getNorthPanel().add(getTxtInput(),getTxtInput().getName());
	}

	public UITextField getTxtInput() {
		if(txtReason==null){
			txtReason = new UITextField();
			txtReason.setPreferredSize(new Dimension(300,20));
			txtReason.setName("txtReason");
		}
		return txtReason;
	}

	private UILabel getTxtLabel() {
		if(lbReason   == null){
			lbReason = new UILabel();
			lbReason.setName("lbReason");
			lbReason.setText(nc.ui.ml.NCLangRes.getInstance().getStrByID("36201017","UPP36201017-000004")/* @res"�������˻����:"*/);

		}
		return lbReason;
	}

	protected int initDlgHigh() {
		return 125;
	}

}