package nc.ui.fbm.consignbank;

import java.awt.Container;
import java.awt.Dimension;

import nc.itf.cdm.util.CommonUtil;
import nc.ui.fac.account.pub.AbstractOKCancleDlg;
import nc.ui.pub.beans.MessageDialog;
import nc.ui.pub.beans.UILabel;
import nc.ui.pub.beans.UITextField;

/**
 * <p>
 *	银行托收作废对话框
 * <p>创建人：bsrl
 * <b>日期：2007-09
 *
 */
public class ConsignBankDisableDlg extends AbstractOKCancleDlg {
	/**
	 *
	 */
	private static final long serialVersionUID = -4031381918463642105L;
	UILabel txtNoteLable = null;
	UITextField txtNote = null;

	public ConsignBankDisableDlg(Container parent) {
		super(parent);
		initialize();
	}
	protected boolean onBoOK() {
		if(CommonUtil.isNull(getTxtNote().getText() )){
			MessageDialog.showWarningDlg(this, nc.ui.ml.NCLangRes.getInstance().getStrByID("36201020","UPP36201020-000000")/* @res"警告"*/, nc.ui.ml.NCLangRes.getInstance().getStrByID("36201020","UPP36201020-000001")/* @res"请输入处理意见！"*/);
			return false;
		}
		return true;
	}

	protected void initialize() {
		setTitle(nc.ui.ml.NCLangRes.getInstance().getStrByID("36201020","UPP36201020-000002")/* @res"银行托收作废"*/);
		getNorthPanel().add(getTxtNoteLable(),getTxtNoteLable().getName());
		getNorthPanel().add(getTxtNote(),getTxtNote().getName());
	}

	protected int initDlgHigh() {
		return 150;
	}

	public UILabel getTxtNoteLable(){
		if(txtNoteLable == null){
			txtNoteLable = new UILabel();
			txtNoteLable.setText(nc.ui.ml.NCLangRes.getInstance().getStrByID("36201020","UPP36201020-000003")/* @res"请输入票据银行托收作废原因:"*/);
		}

		return txtNoteLable;
	}

	public UITextField getTxtNote(){
		if(txtNote == null){
			txtNote = new UITextField();
			txtNote.setPreferredSize(new Dimension(120,23));
			txtNote.setLocation(1, 1);
		}

		return txtNote;
	}
}