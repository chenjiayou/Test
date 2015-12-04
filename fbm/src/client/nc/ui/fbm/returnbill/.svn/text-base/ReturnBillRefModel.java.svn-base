package nc.ui.fbm.returnbill;

import java.awt.Container;

import nc.ui.fbm.pub.refmodel.DefaultGatherRefModel;
import nc.ui.pub.ClientEnvironment;
import nc.ui.tm.framework.ref.filter.SQLRefModelFilter;

public class ReturnBillRefModel extends DefaultGatherRefModel {

	private String[] FIELD_CODE = new String[] { "fbmbillno", "currtypename",
			"moneyy", "enddate" };
	private String[] FIELD_NAME = new String[] {
			nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm", "UPPFBMComm-000036")/*
																						 * @res
																						 * "票据编号"
																						 */,
			nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm", "UPPFBMComm-000037")/*
																						 * @res
																						 * "币种"
																						 */,
			nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm", "UPPFBMComm-000038")/*
																						 * @res
																						 * "金额"
																						 */,
			nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("common", "UC000-0000630") /*
																							 * @res
																							 * "到期日期"
																							 */};
	private String TITLE = nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm", "UPPFBMComm-000039")/*
																									 * @res
																									 * "票据参照"
																									 */;

	// 集中退票　票据编号参照，过滤到统管的票据。
	private String TABLE_NAME = "(select fbm_register.pk_billtypecode pk_billtypecode,"
			+ "fbm_register.sfflag sfflag,fbm_register.frate frate,fbm_register.brate brate ,"
			+ "fbm_register.moneyb moneyb, fbm_register.moneyf moneyf,fbm_register.holdunit holdunit,"
			+ "fbm_register.keepunit keepunit, fbm_register.pk_loanbank,pk_register,fbm_baseinfo.pk_baseinfo,"
			+ "fbm_baseinfo.enddate,isnull(fbm_baseinfo.disable,'N') disable,fbm_baseinfo.invoicedate,"
			+ "fbm_baseinfo.payunit,fbm_baseinfo.paybankacc,fbm_baseinfo.receivebankacc,"
			+ "fbm_baseinfo.contractno contractno, fbm_baseinfo.acceptanceno acceptanceno,"
			+ "fbm_baseinfo.receiveunit,fbm_register.dept, fbm_baseinfo.invoiceunit invoiceunit,"
			+ "fbm_baseinfo.fbmbilltype,fbm_register.gatherdate,registerstatus,fbmbillno,pk_curr,fbm_baseinfo.moneyy,currtypename, "
			+ "fbm_register.gathertype"
			+ " from fbm_register  "
			+ "join ( select fbm_baseinfo.*,bd_currtype.currtypename from fbm_baseinfo "
			+ "join bd_currtype on fbm_baseinfo.pk_curr =  bd_currtype.pk_currtype ) fbm_baseinfo "
			+ "on (fbm_register.pk_baseinfo = fbm_baseinfo.pk_baseinfo) "
			+ "where isnull(fbm_register.dr,0)=0  and isnull(fbm_baseinfo.dr,0)=0 "
			+ " and fbm_register.pk_corp = '"
			+ ClientEnvironment.getInstance().getCorporation().getPk_corp()
			+ "'  ) gathertable";

	private String PK_FIELD_NAME = "pk_register";
	private String[] HIDDEN_FIELD = new String[] { "pk_curr" };

	public ReturnBillRefModel() {
		super(null);
		setFieldCode(FIELD_CODE);
		setFieldName(FIELD_NAME);
		setPkFieldCode(PK_FIELD_NAME);
		setRefCodeField(FIELD_CODE[0]);
		setRefNameField(FIELD_CODE[0]);
		setHiddenFieldCode(HIDDEN_FIELD);
		setDefaultFieldCount(4);
		addSqlFilter(new SQLRefModelFilter() {
			protected String getSelfFilterString() {
				return " sfflag='Y' ";
			}
		});
		addDispConvertor();
	}

	public ReturnBillRefModel(Container con) {
		super(con);
		setFieldCode(FIELD_CODE);
		setFieldName(FIELD_NAME);
		setPkFieldCode(PK_FIELD_NAME);
		setRefCodeField(FIELD_CODE[0]);
		setRefNameField(FIELD_CODE[0]);
		setHiddenFieldCode(HIDDEN_FIELD);
		setDefaultFieldCount(4);
		addSqlFilter(new SQLRefModelFilter() {
			protected String getSelfFilterString() {
				return " sfflag='Y' ";
			}
		});
		addDispConvertor();
	}

	@Override
	public String[] getHiddenFieldCode() {
		return new String[] { "frate", "brate", "moneyb", "moneyf", "disable",
				"pk_register", "pk_baseinfo", "dept",
				"acceptanceno", "contractno", "invoicedate", "payunit",
				"paybankacc", "receivebankacc", "pk_loanbank", "receiveunit",
				"fbmbilltype", "gatherdate", "registerstatus", "holdunit", "sfflag",
				"pk_billtypecode", "invoiceunit", "pk_curr", "gathertype" };
	}

	@Override
	public String getTableName() {
		// TODO Auto-generated method stub
		return TABLE_NAME;
	}

	public boolean isCacheEnabled() {
		return false;
	}
}
