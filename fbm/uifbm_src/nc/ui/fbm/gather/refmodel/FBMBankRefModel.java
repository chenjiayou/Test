package nc.ui.fbm.gather.refmodel;

import java.awt.Container;

import nc.ui.tm.framework.ref.AbstractTMRefModel;

/**
 * 手录银行和银行档案合集
 * 
 * @author xwq
 * 
 *         2008-11-10
 */
public class FBMBankRefModel extends AbstractTMRefModel {

	public static String BANKDOC_NAME = "bankdocname";

	public static String BANKDOC_CODE = "bankdoccode";

	public static String BANKDOC_REMCODE = "amcode";

	public static String PK_BANKDOC = "pk_bankdoc";

	public static String[] fieldcode = new String[] { BANKDOC_CODE,
			BANKDOC_NAME, BANKDOC_REMCODE };
	public static String[] hidefield = new String[] { PK_BANKDOC };
	public static String[] filedname = new String[] {
			nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add", "UPP3620ADD-000033")/*
																								 * @res
																								 * "银行编码"
																								 */,
			nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("common", "UC000-0004115")/*
																							 * @res
																							 * "银行名称"
																							 */,
			nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm", "UPPFBMComm-000060") /*
																								 * @res
																								 * "助记码"
																								 */, };

	public FBMBankRefModel() {
		super();
		// TODO Auto-generated constructor stub
		setMatchPkWithWherePart(false);
	}

	public FBMBankRefModel(Container con) {
		super(con);
		// TODO Auto-generated constructor stub
		setMatchPkWithWherePart(false);
	}
	
	@Override
	public String getRefNodeName() {
		// TODO Auto-generated method stub
		return "tm_fbm_bankdoc";
	}

	@Override
	public String getRefTitle() {
		// TODO Auto-generated method stub
		return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add", "UPP3620ADD-000034")/*
																									 * @res
																									 * "银行档案"
																									 */;
	}

	@Override
	public String getTableName() {
		// TODO Auto-generated method stub
		return "v_fbm_bankdoc";
	}

	@Override
	public String[] getFieldCode() {
		// TODO Auto-generated method stub
		return fieldcode;
	}

	@Override
	public String[] getFieldName() {
		// TODO Auto-generated method stub
		return filedname;
	}

	@Override
	public String getPkFieldCode() {
		// TODO Auto-generated method stub
		return PK_BANKDOC;
	}

	@Override
	public String[] getHiddenFieldCode() {
		// TODO Auto-generated method stub
		return hidefield;
	}

}