package nc.ui.fbm.pub.refmodel;

import java.awt.Container;

import nc.ui.pub.beans.UIRefPane;
import nc.ui.tm.framework.ref.AbstractTMRefModel;
import nc.ui.tm.framework.ref.filter.SQLRefModelFilter;

/**
 *
 * �๦��˵����
 	�ڲ��˻�����
 * ���ڣ�2007-10-23
 * ����Ա�� wues
 */
public class InnerAccountRefModel extends AbstractTMRefModel {

	public InnerAccountRefModel() {
		this(null);
	}

	public InnerAccountRefModel(Container con) {
		super(con);
		setMatchPkWithWherePart(false);
		setSqlFilter(getSQLFilter());
		setFieldCode(getFieldCode());
		setFieldName(getFieldName());
		setPkFieldCode(getPkFieldCode());
		setRefCodeField(getFieldCode()[0]);
		setRefNameField(getFieldCode()[1]);
		setDefaultFieldCount(4);
	}

	private SQLRefModelFilter getSQLFilter() {
		SQLRefModelFilter filter = new SQLRefModelFilter();
		filter.setSql(" frozenflag not in (2,1) ");
		filter.setRefPane((UIRefPane) getParent());
		return filter;
	}

	public int getDefaultFieldCount() {
		return 7;
	}

	public java.lang.String[] getFieldCode() {
		return new String[] { "accidcode", "accidname", "unitname",
				"currtypename", "content", "remcode" };
	}

	public java.lang.String[] getFieldName() {
		return new String[] {
				nc.ui.ml.NCLangRes.getInstance().getStrByID("common",
						"UC000-0003822")/* @res"�˻�����" */,
				nc.ui.ml.NCLangRes.getInstance().getStrByID("common",
						"UC000-0003820")/* @res"�˻�����" */,
				nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm","UPPFBMComm-000058")/* @res"������˾"*/,
				nc.ui.ml.NCLangRes.getInstance().getStrByID("cfcode",
						"UPPcfcode-000360") /* @res"����" */, nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm","UPPFBMComm-000059")/* @res"��ע"*/, nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm","UPPFBMComm-000060")/* @res"������"*/ };
	}

	public String[] getHiddenFieldCode() {
		return new String[] { "pk_accid", "pk_fim", "bd_accid.pk_currtype",
				"bd_accid.pk_accbank", "frozenflag", "accflag",
				"pk_accidstlcent" };
	}

	public java.lang.String getPkFieldCode() {
		return "pk_accid";
	}

	public java.lang.String getRefNodeName() {
		return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add","UPP3620ADD-000065")/*@res "�˻�����ģ��"*/;
	}

	public String getRefTitle() {
		return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add","UPP3620ADD-000066")/*@res "�˻�����"*/;
	}

	@Override
	public String getWherePart() {
		return "  bd_accid.acccl = 0 and bd_accid.accflag in(2,3) ";
	}

	public String getTableName() {
		return " bd_accid INNER JOIN bd_currtype on bd_currtype.pk_currtype=bd_accid.pk_currtype inner join bd_corp on bd_corp.pk_corp=bd_accid.pk_corp";
	}

	@Override
	public String[][] getFormulas() {
		return new String[][] { { "pk_curr",
				"pk_curr->getColValue(bd_currtype,currtypename,pk_currtype,pk_curr)" } };
	}
}