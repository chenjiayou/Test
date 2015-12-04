package nc.ui.fbm.pub.refmodel;

import java.awt.Container;

import nc.ui.tm.framework.ref.AbstractTMRefModel;


/**
 * Ʊ�����Ͳ���
 * @author bsrl
 *  2007-12-18
 */
public class FBMBillTypeRefModel extends AbstractTMRefModel{
	// ��ʾ�ֶα���
	private String[] fieldNames = new String[]{nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("common","UC000-0003279")/*@res "����"*/, nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("common","UC000-0003135")/*@res "����"*/};
	private String[] fieldCodes = new String[]{"notetypecode", "notetypename"};
	/**
	 *
	 */
	public FBMBillTypeRefModel() {
		super();
	}

	/**
	 * @param con
	 */
	public FBMBillTypeRefModel(Container con) {
		super(con);
	}

	/* (non-Javadoc)
	 * @see nc.ui.tm.framework.ref.AbstractTMRefModel#getRefNodeName()
	 */
	@Override
	public String getRefNodeName() {
		return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add","UPP3620ADD-000063")/*@res "Ʊ�����Ͳ���"*/;
	}


	@Override
	public String[] getFieldName() {
		return fieldNames;
	}

	@Override
	public int getDefaultFieldCount() {
		return 2;
	}

	@Override
	public String getPkFieldCode() {
		return "pk_notetype";
	}
	public String[] getHiddenFieldCode() {
		return new String[] { "pk_notetype"};
	}
	/**
	 * ���ձ���
	 * �������ڣ�(01-4-4 0:57:23)
	 * @return java.lang.String
	 */
	public String getRefTitle() {
		return nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm","UPPFBMComm-000055")/* @res"Ʊ������"*/;
	}


	@Override
	public String getTableName() {
		return "(select pk_notetype,notetypecode, notetypename from bd_notetype) notetype";
	}

	@Override
	public String[] getFieldCode() {
		return fieldCodes;
	}
}