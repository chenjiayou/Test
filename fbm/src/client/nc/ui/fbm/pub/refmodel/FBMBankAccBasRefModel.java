package nc.ui.fbm.pub.refmodel;

import java.awt.Container;
import java.util.Vector;

import nc.ui.tm.framework.ref.AbstractTMRefModel;

public class FBMBankAccBasRefModel extends AbstractTMRefModel {

	public static String BANKDOC_FIELD = "pk_bankdoc";
	public static String ACCOUNT_FIELD = "account";
	public static String AUTHCORP = "authcorp";
	public static String PK_CUBASDOC = "pk_cubasdoc";

	public FBMBankAccBasRefModel() {
		super();
		// TODO Auto-generated constructor stub
		setWherePart(" accstate<>3 and acctype in(0,1) and isinneracc = 'N' and accclass<>3");
		setDefaultFieldCount(4);
	}

	public FBMBankAccBasRefModel(Container con) {
		super(con);
		// TODO Auto-generated constructor stub
		setWherePart(" accstate<>3  and acctype in(0,1) and isinneracc = 'N' and accclass<>3");
		setDefaultFieldCount(4);
	}

	@Override
	public String getRefTitle() {
		// TODO Auto-generated method stub
		return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("common", "UC000-0004117")/*
																								 * @res
																								 * "银行账户"
																								 */;
	}

	@Override
	public String getTableName() {
		StringBuffer tableSQL = new StringBuffer();
		// 内部交易账号或者自建帐号
		tableSQL.append(" select bd_bankaccbas.*,bd_cubasdoc.pk_cubasdoc as pk_cubasdoc,bd_bankaccauth.pk_corp as authcorp,bd_cubasdoc.custname  ");
		// dev.efficiency.start
		tableSQL.append(" , bd_cubasdoc.bp2 as yy   ");
		// dev.efficiency.end
		tableSQL.append(" from bd_bankaccbas INNER JOIN bd_bankaccauth on bd_bankaccbas.pk_bankaccbas=bd_bankaccauth.pk_bankaccbas ");
		tableSQL.append(" inner join bd_bankaccmng on bd_bankaccmng.pk_bankaccbas = bd_bankaccbas.pk_bankaccbas ");
		tableSQL.append(" left join bd_cubasdoc on bd_cubasdoc.pk_corp1=bd_bankaccauth.pk_corp");
		tableSQL.append(" where  bd_bankaccauth.pk_corp in(select pk_corp1 from bd_cubasdoc bas join bd_cumandoc man on bas.pk_cubasdoc = man.pk_cubasdoc where man.pk_corp='"
				+ getPk_corp()
				+ "' ) and bd_bankaccauth.usepowerflag='Y' ");

		// 外部客商账号
		StringBuffer tableSQL2 = new StringBuffer();
		tableSQL2.append(" select bd_bankaccbas.*,bd_custbank.pk_cubasdoc,'' as authcorp ,bd_cubasdoc.custname");
		tableSQL2.append(",  bd_cubasdoc.bp2 as yy ");
		tableSQL2.append(" from bd_bankaccbas inner join bd_custbank on bd_bankaccbas.pk_bankaccbas = bd_custbank.pk_accbank inner join  bd_cubasdoc on bd_cubasdoc.pk_cubasdoc = bd_custbank.pk_cubasdoc where bd_custbank.pk_cubasdoc ");
		tableSQL2.append("in(select pk_cubasdoc from bd_cumandoc where bd_cumandoc.pk_corp='"
				+ getPk_corp()
				+ "' ) ");

		return "((" + tableSQL + ") union (" + tableSQL2 + ")) as fbmbankacc";
	}

	@Override
	public String[] getFieldCode() {
		return new String[] { "account", "accountname", "remcode","custname" };
	}

	@Override
	public String[] getHiddenFieldCode() {
		return new String[] { "pk_bankdoc", "pk_bankaccbas", PK_CUBASDOC,
				"pk_currtype" };
	}

	@Override
	public String[] getFieldName() {
		return new String[] {
				nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add", "UPP3620ADD-000057")/*
																									 * @res
																									 * "银行帐户编码"
																									 */,
				nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add", "UPP3620ADD-000058") /*
																									 * @res
																									 * "银行帐户名称"
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
	public String getPkFieldCode() {
		return "pk_bankaccbas";
	}

	@Override
	public String getRefNodeName() {
		return "tm_fbm_bankaccbas";
	}
	
	public void setCacheEnabled(boolean cacheEnabled) {
		// TODO Auto-generated method stub
		super.setCacheEnabled(cacheEnabled);
	}
	public void matchNameData(String field, String blurstr) {
		Vector result = matchData(field, blurstr);
		setSelectedData(result);
	}

	@Override
	public String getRefShowNameField() {
		return ACCOUNT_FIELD;
	}
}