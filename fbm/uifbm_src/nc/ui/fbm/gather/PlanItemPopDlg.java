package nc.ui.fbm.gather;

import java.awt.Container;

import nc.ui.fac.account.pub.AbstractOKCancleDlg;
import nc.ui.fbm.planref.PlanItemInRefModel;
import nc.ui.pub.beans.UILabel;
import nc.ui.pub.beans.UIRefPane;

/**
 * �ƻ���Ŀ�Ի���
 * @author xwq
 *
 * 2009-1-7
 */
public class PlanItemPopDlg extends AbstractOKCancleDlg {

	UILabel txtNoteLable = null;
	UIRefPane planitemPane = null;

	public PlanItemPopDlg(Container parent) {
		super(parent);
		// TODO Auto-generated constructor stub
		initialize();
	}

	protected void initialize() {
		setTitle(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcode112","UPPFBMCODE112-000015")/*@res "¼��ƻ���Ŀ"*/);
		getNorthPanel().add(getTxtNoteLabel());
		getNorthPanel().add(getPlanitemPane());
	}

	public String getPlanItemPK(){
		return planitemPane.getRefModel().getPkValue();
	}

	protected int initDlgHigh() {
		return 150;
	}

	public UILabel getTxtNoteLabel(){
		if(txtNoteLable == null){
			txtNoteLable = new UILabel();
			txtNoteLable.setText(nc.ui.ml.NCLangRes.getInstance().getStrByID("36201005","UPP36201005-000016")/* @res"��Ʊ����ƻ���Ŀ:"*/);
		}

		return txtNoteLable;
	}

	public UIRefPane getPlanitemPane(){
		if(planitemPane == null){
			planitemPane = new UIRefPane();
			PlanItemInRefModel refmodel = new PlanItemInRefModel();
			planitemPane.setRefModel(refmodel);
		}
		return planitemPane;
	}
}