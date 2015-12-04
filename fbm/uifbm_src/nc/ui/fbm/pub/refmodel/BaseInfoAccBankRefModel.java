/**
 *
 */
package nc.ui.fbm.pub.refmodel;

import java.awt.Container;
import java.util.Vector;

/**
 * <p>
 * 支持手工录入的开户银行参照（pk+name）
 * <p>
 * 创建人：lpf <b>日期：2007-9-13
 * 
 */
public class BaseInfoAccBankRefModel extends DefaultAccbankRefModel {

	public static String BANKDOC_FIELD = "pk_bankdoc";
	public static String ACCOUNT_FIELD = "account";

	public static String CUBASDOC_FIELD = "bd_cubasdoc.pk_cubasdoc";
	/**
	 *
	 */
	public BaseInfoAccBankRefModel() {
		super();
		setMatchPkWithWherePart(false);
		setWherePart(" (isinneracc is null or isinneracc = 'N') and (accstate is null or accstate<>3) and (acctype is null or acctype in(0,1)) and accclass<>3 ");

	}

	/**
	 * @param con
	 */
	public BaseInfoAccBankRefModel(Container con) {
		super(con);
		// TODO Auto-generated constructor stub
		setMatchPkWithWherePart(false);
		setWherePart(" (isinneracc is null or isinneracc = 'N') and (accstate is null or accstate<>3) and (acctype is null or acctype in(0,1)) and accclass<>3 ");

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nc.ui.tm.framework.ref.AbstractTMRefModel#getTableName()
	 */
	@Override
	public String getTableName() {
		return " v_fbm_bankaccbas left JOIN bd_bankaccauth on v_fbm_bankaccbas.pk_bankaccbas=bd_bankaccauth.pk_bankaccbas left join bd_cubasdoc on bd_bankaccauth.pk_corp=bd_cubasdoc.pk_corp1";
	}

	@Override
	public String[] getFieldCode() {
		// TODO Auto-generated method stub
		return new String[] { "account", "accountname", "remcode", "custname" };
	}

	@Override
	public String[] getFieldName() {
		return new String[] {
				nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm", "UPPFBMComm-000040")/*
																							 * @res
																							 * "银行账号"
																							 */,
				nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm", "UPPFBMComm-000041") /*
																							 * @res
																							 * "银行名称"
																							 */,
				nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm", "UPPFBMComm-000060") /*
																								 * @res
																								 * "助记码"
																								 */,
				nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm", "UPPFBMComm-000044") /*
																									 * @res
																									 * "客商名称"
																									 */};
	}

	@Override
	public String[] getHiddenFieldCode() {
		return new String[] { "v_fbm_bankaccbas.pk_bankaccbas",
				"v_fbm_bankaccbas.pk_corp",CUBASDOC_FIELD ,"pk_bankdoc"};
	}

	public void MatchNameData(String field, String blurstr) {
		Vector result = matchData(field, blurstr);
		setSelectedData(result);
	}

	public String[] getRefShowNameValues() {
		String[] refNames = super.getRefShowNameValues();
		if (refNames != null && refNames.length > 0) {
			String[] returnStr = new String[1];
			returnStr[0] = refNames[0];
			return returnStr;
		}
		return refNames;
	}

	/**
	 * 返回值数组－－编码字段 创建日期：(2001-8-13 16:19:24)
	 * 
	 * @return java.lang.String[]
	 */
	public java.lang.String[] getRefCodeValues() {
		String[] sDatas = super.getRefCodeValues();
		if (sDatas != null && sDatas.length > 1) {
			String[] returnStr = new String[1];
			returnStr[0] = sDatas[0];
			return returnStr;
		}
		return sDatas;
	}

	@Override
	public String[][] getFormulas() {
		// TODO Auto-generated method stub
		return new String[][] { { "pk_currtype",
				"pk_currtype->getColValue(bd_currtype,currtypename,pk_currtype,pk_currtype)" } };
	}

	@Override
	public String getRefNameField() {
		// TODO Auto-generated method stub
		return getFieldCode()[0];
	}

	@Override
	public String getPkFieldCode() {
		// TODO Auto-generated method stub
		return "v_fbm_bankaccbas.pk_bankaccbas";
	}

	@Override
	public int getDefaultFieldCount() {
		// TODO Auto-generated method stub
		return 4;
	}

	/**
	 * 参照标题 创建日期：(01-4-4 0:57:23)
	 * 
	 * @return java.lang.String
	 */
	public String getRefTitle() {
		return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("common", "UC000-0004117")/*
																								 * @res
																								 * "银行账户"
																								 */;
	}

	public void matchNameData(String field, String blurstr) {
		Vector result = matchData(field, blurstr);
		setSelectedData(result);
	}
}