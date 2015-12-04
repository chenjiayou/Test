package nc.ui.fbm.impawn;

import java.awt.Container;
import java.awt.Dimension;

import nc.itf.cdm.util.CommonUtil;
import nc.ui.fac.account.pub.AbstractOKCancleDlg;
import nc.ui.pub.beans.MessageDialog;
import nc.ui.pub.beans.UILabel;
import nc.ui.pub.beans.UIRefPane;
/**
 * 功能：
       质押回收时弹出的对话框要求用户输入回收信息{回收人，日期等}
 * 日期：2007-9-25
 * 程序员：wues
 */
public class ImpawnBackInfoDialog extends AbstractOKCancleDlg{
	//质押回收日期
	private UIRefPane paneDate = new UIRefPane();;
	//质押回收单位
	private UIRefPane paneUnit = new UIRefPane();;

	public ImpawnBackInfoDialog(Container parent) {
		super(parent);
		//初始化对话框上面的元素
		init();
	}

	private static final long serialVersionUID = 1L;

	private void init() {
		this.setTitle(nc.ui.ml.NCLangRes.getInstance().getStrByID("36201040","UPP36201040-000005")/* @res"质押回收信息登记"*/);
		this.setSize(new Dimension(300,150));
		this.getNorthPanel().setLayout(new java.awt.FlowLayout());
		addComponent();
	}

	private void addComponent(){
		this.getNorthPanel().add(new UILabel(nc.ui.ml.NCLangRes.getInstance().getStrByID("36201040","UPT36201040-000014")/* @res"质押回收日期"*/));

		paneDate.setRefNodeName("日历");
		paneDate.setPreferredSize(new Dimension(200,22));
		this.getNorthPanel().add(paneDate);
		this.getNorthPanel().add(new UILabel(nc.ui.ml.NCLangRes.getInstance().getStrByID("36201040","UPP36201040-000006")/* @res"质押回收人    "*/));
		paneUnit.setPreferredSize(new Dimension(200,22));
		//paneUnit.setRefModel(new nc.ui.fbm.pub.refmodel.DefaultCustbasdocRefModel());
		paneUnit.setRefNodeName("操作员");

		this.getNorthPanel().add(paneUnit);
	}


	protected boolean onBoOk(){
		if(CommonUtil.isNull(paneDate.getRefPK())){
			MessageDialog.showWarningDlg(this, nc.ui.ml.NCLangRes.getInstance().getStrByID("36201040","UPP36201040-000007")/* @res"提示"*/, nc.ui.ml.NCLangRes.getInstance().getStrByID("36201040","UPP36201040-000008")/* @res"请输入质押回收日期！"*/);
			return false;
		}
		if (CommonUtil.isNull(paneUnit.getRefPK())) {
			MessageDialog.showWarningDlg(this, nc.ui.ml.NCLangRes.getInstance().getStrByID("36201040","UPP36201040-000007")/* @res"提示"*/, nc.ui.ml.NCLangRes.getInstance().getStrByID("36201040","UPP36201040-000009")/* @res"请输入质押回收人！"*/);
			return false;
		}
		return true;
	}

	public UIRefPane getPaneDate(){
		return this.paneDate;
	}
	public UIRefPane getPaneUnit(){
		return this.paneUnit;
	}

}