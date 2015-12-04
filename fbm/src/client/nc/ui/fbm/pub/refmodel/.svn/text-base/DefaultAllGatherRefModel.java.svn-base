package nc.ui.fbm.pub.refmodel;

import java.awt.Container;
import java.math.BigDecimal;
import java.util.Vector;

import nc.ui.pub.ClientEnvironment;
import nc.ui.tm.framework.ref.AbstractTMRefModel;
import nc.ui.tm.framework.ref.filter.RefDataFilter;
import nc.vo.pub.lang.UFNumberFormat;

public class DefaultAllGatherRefModel extends AbstractTMRefModel {

	protected RefDataFilter createRefFilter() {
		return null;
	}

	private String[] FIELD_CODE = new String[]{"fbmbillno","pk_curr","moneyy", "holdunitname"};
	private String[] FIELD_NAME = new String[]{nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm","UPPFBMComm-000036")/* @res"票据编号"*/,nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm","UPPFBMComm-000037")/* @res"币种"*/,nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm","UPPFBMComm-000038")/* @res"金额"*/, nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm","UPPFBMComm-000048")/* @res"持票单位"*/};
	private String TITLE = nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm","UPPFBMComm-000039")/* @res"票据参照"*/;

	private String TABLE_NAME = "(select b.custname holdunitname,fbm_register.pk_billtypecode pk_billtypecode," +
			          "fbm_register.sfflag sfflag,fbm_register.frate frate,fbm_register.brate brate , " +
			          "fbm_register.moneyb moneyb, fbm_register.moneyf moneyf,  fbm_register.holdunit holdunit," +
			          "fbm_register.keepunit keepunit, fbm_register.pk_loanbank,pk_register," +
			          "fbm_baseinfo.pk_baseinfo,fbm_baseinfo.enddate,"
					+ "isnull(fbm_baseinfo.disable,'N') disable,fbm_baseinfo.invoicedate,fbm_baseinfo.payunit," +
					"fbm_baseinfo.paybankacc,fbm_baseinfo.receivebankacc,fbm_baseinfo.contractno contractno, " +
					"fbm_baseinfo.acceptanceno acceptanceno, "
					+ "fbm_baseinfo.receiveunit,fbm_register.dept, fbm_baseinfo.invoiceunit invoiceunit, " +
					"fbm_baseinfo.fbmbilltype,fbm_register.gatherdate,fbmbillno,pk_curr," +
					"fbm_baseinfo.moneyy,registerstatus,currtypename "
					+ " from fbm_register " +
					" left join bd_cubasdoc b on (b.pk_cubasdoc = fbm_register.holdunit ) " +
					" join ( select fbm_baseinfo.*,bd_currtype.currtypename from fbm_baseinfo " +
					"join bd_currtype on fbm_baseinfo.pk_curr =  bd_currtype.pk_currtype ) fbm_baseinfo on" +
					" (fbm_register.pk_baseinfo = fbm_baseinfo.pk_baseinfo) where isnull(fbm_register.dr,0)=0" +
					" and isnull(fbm_baseinfo.dr,0)=0 and fbm_register.pk_corp " +
					"in (select pk_corp1" +
					" from bd_settleunit unit" +
					" where pk_settlecent in (select bd_settlecenter.pk_settlecenter" +
					" from bd_settlecenter" +
					" where bd_settlecenter.pk_corp = '"+ ClientEnvironment.getInstance().getCorporation().getPk_corp()+"')" +
					" union" +
					" select pk_corp1" +
					" from bd_settleunit unit" +
					" where unit.pk_corp1 = '"+ ClientEnvironment.getInstance().getCorporation().getPk_corp()+"'" +
					" union" +
					" select pk_corp from bd_corp where bd_corp.pk_corp = '"+ ClientEnvironment.getInstance().getCorporation().getPk_corp()+"'"
        			+ "union (select distinct sm_user_role.pk_corp as pk_corp from sm_user_role " +
        					"inner join sm_power_func on sm_user_role.pk_role = sm_power_func.pk_role " +
        					"where (sm_user_role.cuserid = '"+ ClientEnvironment.getInstance().getUser().getPrimaryKey()+"')" +
        							" and sm_power_func.resource_data_id = '0001NC100000000068JK' )  )" +
        			" ) gathertable ";
		 
	private String PK_FIELD_NAME = "pk_register";

	public DefaultAllGatherRefModel() {
		super(null);
		setFieldCode(FIELD_CODE);
		setFieldName(FIELD_NAME);
		setPkFieldCode(PK_FIELD_NAME);
		setRefCodeField(FIELD_CODE[0]);
		setRefNameField(FIELD_CODE[0]);
		setDefaultFieldCount(4);
	}

	public DefaultAllGatherRefModel(Container con) {
		super(con);
		setFieldCode(FIELD_CODE);
		setFieldName(FIELD_NAME);
		setPkFieldCode(PK_FIELD_NAME);
		setRefCodeField(FIELD_CODE[0]);
		setRefNameField(FIELD_CODE[0]);
		setDefaultFieldCount(4);
	}

	@Override
	public String[] getHiddenFieldCode() {
		return new String[] { "frate", "brate","moneyb","moneyf", "disable", "pk_register", "pk_baseinfo", 
				"enddate","dept","acceptanceno","contractno",
				"invoicedate", "payunit", "paybankacc", "receivebankacc","pk_loanbank",
				"receiveunit", "fbmbilltype", "gatherdate", "holdunit","sfflag","pk_billtypecode",
				"invoiceunit","currtypename","registerstatus"
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



	@Override
	public String[][] getFormulas() {
		// TODO Auto-generated method stub
		return new String[][]{
				{"pk_curr","pk_curr->getColValue(bd_currtype,currtypename,pk_currtype,pk_curr)"}};
	}

	@Override
	public Vector getData() {
		// TODO Auto-generated method stub
		Vector v = super.getData();
		if(v!=null&& v.size() > 0){
			int len = v.size();
			for(int i = 0 ; i < len ; i++){
				Vector vj = (Vector)v.get(i);
				for(int j = 0 ; j < vj.size(); j++){
					Object obj = vj.get(j);
					if(obj instanceof BigDecimal){
						double newObj = ((BigDecimal)obj).doubleValue();
						vj.set(j, UFNumberFormat.format(newObj));
					}
				}
			}
		}
		return v;

	}
	
	public boolean isCacheEnabled() {
		return false;
	}
}