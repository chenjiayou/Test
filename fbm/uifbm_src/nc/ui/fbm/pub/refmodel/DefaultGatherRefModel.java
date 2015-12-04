package nc.ui.fbm.pub.refmodel;

import java.awt.Container;

import nc.ui.pub.ClientEnvironment;
import nc.ui.tm.framework.ref.AbstractTMRefModel;
import nc.ui.tm.framework.ref.filter.SQLRefModelFilter;

/**
 *
 * <p>
 *   收票登记参照
 *   用于票据相关业务单据的票据参照
 *
 *   注意：业务单据参照票据不要用基本信息参照，要用此参照
 * </p>
 * @author xwq
 * @date 2007-8-10
 * @version V5.0
 */
public class DefaultGatherRefModel extends AbstractTMRefModel{

	private String[] FIELD_CODE = new String[]{"fbmbillno","currtypename","moneyy","enddate"};
	private String[] FIELD_NAME = new String[]{nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm","UPPFBMComm-000036")/* @res"票据编号"*/,nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm","UPPFBMComm-000037")/* @res"币种"*/,nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm","UPPFBMComm-000038")/* @res"金额"*/,nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("common","UC000-0000630")/*@res "到期日期"*/};
	private String TITLE = nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm","UPPFBMComm-000039")/* @res"票据参照"*/;
	//本公司收票记录。实际使用时，编写增加状态过滤条件的子类实现
	private String TABLE_NAME = "(select fbm_register.pk_billtypecode pk_billtypecode,fbm_register.sfflag sfflag,fbm_register.frate frate,fbm_register.brate brate , fbm_register.moneyb moneyb, fbm_register.moneyf moneyf,  fbm_register.holdunit holdunit,fbm_register.keepunit keepunit, fbm_register.pk_loanbank,pk_register,fbm_baseinfo.pk_baseinfo,fbm_baseinfo.enddate,"
			+ "isnull(fbm_baseinfo.disable,'N') disable,fbm_baseinfo.invoicedate,fbm_baseinfo.payunit,fbm_baseinfo.paybankacc,fbm_baseinfo.receivebankacc,fbm_baseinfo.contractno contractno, fbm_baseinfo.acceptanceno acceptanceno,fbm_baseinfo.paybank paybank,fbm_baseinfo.receivebank receivebank, "
			+ "fbm_baseinfo.receiveunit,fbm_register.dept, fbm_baseinfo.invoiceunit invoiceunit, fbm_baseinfo.fbmbilltype,fbm_register.gatherdate,registerstatus,fbmbillno,pk_curr,fbm_baseinfo.moneyy,currtypename "
			+ " from fbm_register  join ( select fbm_baseinfo.*,bd_currtype.currtypename from fbm_baseinfo join bd_currtype on fbm_baseinfo.pk_curr =  bd_currtype.pk_currtype ) fbm_baseinfo on (fbm_register.pk_baseinfo = fbm_baseinfo.pk_baseinfo) where isnull(fbm_register.dr,0)=0  and isnull(fbm_baseinfo.dr,0)=0  and fbm_register.pk_corp = '"
			+ ClientEnvironment.getInstance().getCorporation().getPk_corp()
			+ "' ) gathertable ";
	private String PK_FIELD_NAME = "pk_register";
	private String[] HIDDEN_FIELD = new String[]{"pk_curr"};

	public DefaultGatherRefModel() {
		super(null);
		setFieldCode(FIELD_CODE);
		setFieldName(FIELD_NAME);
		setPkFieldCode(PK_FIELD_NAME);
		setRefCodeField(FIELD_CODE[0]);
		setRefNameField(FIELD_CODE[0]);
		setHiddenFieldCode(HIDDEN_FIELD);
		setDefaultFieldCount(4);
		addSqlFilter(new SQLRefModelFilter(){
			protected String getSelfFilterString() {
				return " sfflag='Y' ";
			}
		});
		addDispConvertor();
	}

	public DefaultGatherRefModel(Container con) {
		super(con);
		setFieldCode(FIELD_CODE);
		setFieldName(FIELD_NAME);
		setPkFieldCode(PK_FIELD_NAME);
		setRefCodeField(FIELD_CODE[0]);
		setRefNameField(FIELD_CODE[0]);
		setHiddenFieldCode(HIDDEN_FIELD);
		setDefaultFieldCount(4);
		addSqlFilter(new SQLRefModelFilter(){
			protected String getSelfFilterString() {
				return " sfflag='Y' ";
			}
		});
		addDispConvertor();
	}

	@Override
	public String[] getHiddenFieldCode() {
		return new String[] {  "frate", "brate","moneyb","moneyf", "disable", "pk_register", "pk_baseinfo", "acceptanceno","contractno",
				"invoicedate", "payunit", "paybankacc", "receivebankacc","pk_loanbank","keepunit",
				"receiveunit", "fbmbilltype", "gatherdate","registerstatus", "holdunit","sfflag","pk_billtypecode","invoiceunit","pk_curr",
				"receivebank","paybank"
				};
	}

	@Override
	public String getRefNodeName() {
		// TODO Auto-generated method stub
		return PK_FIELD_NAME;
	}

	@Override
	public String getRefTitle() {
		// TODO Auto-generated method stub
		return TITLE;
	}

	@Override
	public String getTableName() {
		// TODO Auto-generated method stub
		return TABLE_NAME;
	}

	@Override
	protected String getCurrencyName() {
		// TODO Auto-generated method stub
		return "pk_curr";
	}

	@Override
	protected String[] getMoneyKeys() {
		// TODO Auto-generated method stub
		return new String[]{"moneyy"};
	}
	
	public boolean isCacheEnabled() {
		return false;
	}

}