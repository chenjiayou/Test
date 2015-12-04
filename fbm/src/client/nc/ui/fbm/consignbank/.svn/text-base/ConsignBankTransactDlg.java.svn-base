package nc.ui.fbm.consignbank;

import java.awt.BorderLayout;
import java.awt.Container;

import nc.ui.fac.account.pub.AbstractOKCancleDlg;
import nc.ui.pub.bill.BillCardPanel;
import nc.ui.pub.bill.BillData;
import nc.ui.pub.bill.BillItem;
import nc.ui.pub.bill.IBillItem;
import nc.ui.pub.beans.MessageDialog;
import nc.ui.pub.beans.UILabel;

/**
 *
 * <p>
 *	银行托收办理对话框
 * <p>创建人：bsrl
 * <b>日期：2007-09
 *
 */
public class ConsignBankTransactDlg extends AbstractOKCancleDlg {
	/**
	 *
	 */
	private static final long serialVersionUID = -7065571911558193327L;
	private UILabel txtNoteLable = null;
	private BillItem collectionDate = null;


	public ConsignBankTransactDlg(Container parent) {
		super(parent);
		initialize();
	}

	protected boolean onBoOK() {
		if(getCollectionDate().getValueObject() == null || getCollectionDate().getValueObject().toString().trim().length() == 0){
			MessageDialog.showWarningDlg(this, nc.ui.ml.NCLangRes.getInstance().getStrByID("36201020","UPP36201020-000000")/* @res"警告"*/, nc.ui.ml.NCLangRes.getInstance().getStrByID("36201020","UPP36201020-000006")/* @res"请输入款项收妥日期！"*/);
			return false;
		}
		return true;
	}

	public BillItem getCollectionDate() {
		if(collectionDate == null){
			collectionDate = new BillItem();
			collectionDate.setDataType(IBillItem.DATE);
			collectionDate.setName(nc.ui.ml.NCLangRes.getInstance().getStrByID("36201020","UPT36201020-000017")/* @res"款项收妥日期"*/);
		}

		return collectionDate;
	}

	protected void initialize() {
		setTitle(nc.ui.ml.NCLangRes.getInstance().getStrByID("36201020","UPP36201020-000007")/* @res"银行托收办理"*/);
		getNorthPanel().add(getTxtNoteLable());
		BillData billdata = new BillData();
		billdata.setHeadItems(new BillItem[]{getCollectionDate()});//getrefDepositDate(),
		BillCardPanel panel = new BillCardPanel();
		panel.setBillData(billdata);
		panel.setEnabled(true);
		getCollectionDate().setEnabled(true);
		getNorthPanel().setLayout(new java.awt.BorderLayout());
		getNorthPanel().add(panel,BorderLayout.CENTER);
	}

	protected int initDlgHigh() {
		return 120;
	}

	public UILabel getTxtNoteLable(){
		if(txtNoteLable == null){
			txtNoteLable = new UILabel();
			txtNoteLable.setText(nc.ui.ml.NCLangRes.getInstance().getStrByID("36201020","UPP36201020-000008")/* @res"请输入款项收妥日期:"*/);
		}

		return txtNoteLable;
	}

}