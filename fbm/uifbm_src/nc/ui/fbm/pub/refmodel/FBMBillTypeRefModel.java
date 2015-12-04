package nc.ui.fbm.pub.refmodel;

import java.awt.Container;

import nc.ui.tm.framework.ref.AbstractTMRefModel;


/**
 * 票据类型参照
 * @author bsrl
 *  2007-12-18
 */
public class FBMBillTypeRefModel extends AbstractTMRefModel{
	// 显示字段编码
	private String[] fieldNames = new String[]{nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("common","UC000-0003279")/*@res "编码"*/, nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("common","UC000-0003135")/*@res "类型"*/};
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
		return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add","UPP3620ADD-000063")/*@res "票据类型参照"*/;
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
	 * 参照标题
	 * 创建日期：(01-4-4 0:57:23)
	 * @return java.lang.String
	 */
	public String getRefTitle() {
		return nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm","UPPFBMComm-000055")/* @res"票据类型"*/;
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