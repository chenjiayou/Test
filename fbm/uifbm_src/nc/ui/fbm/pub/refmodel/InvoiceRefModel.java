/**
 *
 */
package nc.ui.fbm.pub.refmodel;

import java.awt.Container;
import nc.ui.tm.framework.ref.AbstractTMRefModel;

/**
 * <p>
 * 开票基本信息参照
 * <p>创建人：lpf
 * <b>日期：2007-9-4
 *
 */
public class InvoiceRefModel extends AbstractTMRefModel {
	/**
	 *
	 */
	public InvoiceRefModel() {
		// TODO Auto-generated constructor stub
		addDispConvertor();
	}

	/**
	 * @param con
	 */
	public InvoiceRefModel(Container con) {
		super(con);
		// TODO Auto-generated constructor stub
		addDispConvertor();
	}

	/* (non-Javadoc)
	 * @see nc.ui.tm.framework.ref.AbstractTMRefModel#getRefNodeName()
	 */
	@Override
	public String getRefNodeName() {
		return "fbm_register";
	}

	/* (non-Javadoc)
	 * @see nc.ui.tm.framework.ref.AbstractTMRefModel#getRefTitle()
	 */
	@Override
	public String getRefTitle() {
		return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add","UPT36201510-000041")/*@res "票据基本信息"*/;
	}

	/* (non-Javadoc)
	 * @see nc.ui.tm.framework.ref.AbstractTMRefModel#getTableName()
	 */
	@Override
	public String getTableName() {
		return " (select bd_currtype.currtypename,fbm_register.pk_corp,fbm_register.isverify,fbm_register.holdunit,fbm_register.pk_billtypecode,fbm_baseinfo.acceptanceno, " +
				" fbm_register.pk_loanbank,fbm_baseinfo.orientation,fbm_register.pk_register,fbm_baseinfo.contractno," +
				" fbm_baseinfo.pk_baseinfo, fbm_baseinfo.enddate,fbm_baseinfo.invoicedate, fbm_baseinfo.invoiceunit," +
				" fbm_baseinfo.payunit,fbm_baseinfo.paybankacc,fbm_baseinfo.receivebankacc, fbm_baseinfo.receiveunit, " +
				" fbm_baseinfo.fbmbilltype, registerstatus, fbm_baseinfo.fbmbillno,pk_curr, fbm_baseinfo.moneyy,fbm_baseinfo.paybank,fbm_baseinfo.receivebank," +
				" fbm_register.securityaccount,fbm_register.securitymoney,fbm_register.impawnmode,fbm_register.cctype "+
				" from fbm_register left join fbm_baseinfo on (fbm_baseinfo.pk_baseinfo =  fbm_register.pk_baseinfo)"+
				" join bd_currtype on(fbm_baseinfo.pk_curr =  bd_currtype.pk_currtype)" +
				" where isnull(fbm_register.dr,0)=0 and isnull(fbm_baseinfo.dr,0)=0  " +
//				" and fbm_register.pk_corp = '"+ClientEnvironment.getInstance().getCorporation().getPk_corp()+"' " +
				"  ) newtable ";
	}

	@Override
	public String[] getFieldCode() {
		return new String[]{"fbmbillno","currtypename","moneyy","enddate"};
	}

	@Override
	public String[] getFieldName() {
		return new String[] {
				nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm",
						"UPPFBMComm-000036")/* @res"票据编号" */,
				nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm",
						"UPPFBMComm-000037")/* @res"币种" */,
				nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm",
						"UPPFBMComm-000038")/* @res"金额" */,
				nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm",
						"UPPFBMComm-000395") /* @res"到期日期"*/};
	}

	@Override
	public String getPkFieldCode() {
		return "pk_register";
	}

	@Override
	public String[] getHiddenFieldCode() {
		return new String[] { "pk_curr","pk_register","pk_baseinfo", "acceptanceno", "contractno", "invoicedate", "invoiceunit", "holdunit","payunit",	"paybankacc", "isverify",
				"receivebankacc", "receiveunit", "fbmbilltype","pk_loanbank","pk_billtypecode",
				"securityaccount","securitymoney","impawnmode","registerstatus","orientation","cctype","pk_corp","paybank","receivebank" };
	}

	@Override
	public int getDefaultFieldCount() {
		return 4;
	}

	@Override
	public String getRefNameField() {
		return getFieldCode()[0];
	}

//	@Override
//	protected String[][] getFormulas() {
//		return new String[][]{
//				{"pk_curr","pk_curr->getColValue(bd_currtype,currtypename,pk_currtype,pk_curr)"}};
//	}

	@Override
	protected String getCurrencyName() {
		return "pk_curr";
	}

	@Override
	protected String[] getMoneyKeys() {
		return new String[]{"moneyy"};
	}
	
	public boolean isCacheEnabled() {
		return false;
	}
}