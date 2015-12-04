package nc.ui.fbm.pub.refmodel;

import nc.ui.pub.ClientEnvironment;
import nc.ui.tm.framework.ref.AbstractTMRefModel;
import nc.ui.tm.framework.ref.filter.RefDataFilter;

/**
 * 
 * <p>
 * 贴现申请单参照，用于贴现办理
 * </p>
 * 
 * @author bsrl
 * @date 2007-10-10
 * @version V5.02
 */
public class DiscountApplyRefModel extends AbstractTMRefModel {

	private String[] FIELD_CODE = new String[] { "discountappno", "fbmbillno",
			"currtypename", "moneyy" };
	private String[] FIELD_NAME = new String[] {
			nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm",
					"UPPFBMComm-000053")/* @res"贴现申请单号" */,
			nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm",
					"UPPFBMComm-000036")/* @res"票据编号" */,
			nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm",
					"UPPFBMComm-000037")/* @res"币种" */,
			nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm",
					"UPPFBMComm-000038") /* @res"金额" */};
	private String TITLE = nc.ui.ml.NCLangRes.getInstance().getStrByID(
			"fbmcomm", "UPPFBMComm-000054")/* @res"贴现申请单参照" */;
	// 本公司收票记录。实际使用时，编写增加状态过滤条件的子类实现
	private String TABLE_NAME = "(select fbm_discount.moneyf, fbm_discount.moneyb, fbm_discount.interestmoneyf, fbm_discount.interestmoneyb, fbm_discount.chargemoneyf,fbm_discount.chargemoneyb, fbm_discount.frate,fbm_discount.brate,   fbm_discount.vbillstatus vbillstatus, fbm_discount.ddiscountdate ddiscountdate, fbm_discount.discountinterest discountinterest, fbm_discount.ratedaynum ratedaynum, fbm_discount.moneyy dismoneyy, fbm_discount.discountyrate discountyrate, fbm_discount.discountdelaydaynum discountdelaydaynum, fbm_discount.discountcharge discountcharge, fbm_discount.discount_account discount_account, fbm_register.holdunit holdunit, pk_discount,fbm_discount.vbillno discountappno, pk_register,fbm_baseinfo.pk_baseinfo,fbm_baseinfo.enddate,"
			+ "fbm_baseinfo.invoicedate,fbm_baseinfo.payunit,fbm_baseinfo.paybankacc,fbm_baseinfo.receivebankacc,"
			+ "fbm_baseinfo.receiveunit,fbm_baseinfo.fbmbilltype,fbm_register.gatherdate,registerstatus,fbmbillno,pk_curr,fbm_baseinfo.moneyy,currtypename,fbm_discount.opbilltype opbilltype"
			+ " from fbm_discount left join fbm_register on fbm_discount.pk_source = fbm_register.pk_register  join (select fbm_baseinfo.*,bd_currtype.currtypename from fbm_baseinfo join bd_currtype on fbm_baseinfo.pk_curr =  bd_currtype.pk_currtype ) fbm_baseinfo on (fbm_register.pk_baseinfo = fbm_baseinfo.pk_baseinfo) "
			+ "where isnull(fbm_discount.dr,0)=0  and isnull(fbm_baseinfo.dr,0)=0  and fbm_discount.pk_corp = '"
			+ ClientEnvironment.getInstance().getCorporation().getPk_corp()
			+ "' and fbm_discount.pk_billtypecode = '36GF' " 
			+" ) register";
	private String PK_FIELD_NAME = "pk_discount";
	private String[] HIDDEN_FIELD = new String[] { "pk_curr" };

	public DiscountApplyRefModel() {
		super();
		setFieldCode(FIELD_CODE);
		setFieldName(FIELD_NAME);
		setPkFieldCode(PK_FIELD_NAME);
		setRefCodeField(FIELD_CODE[0]);
		setRefNameField(FIELD_CODE[0]);
		setHiddenFieldCode(HIDDEN_FIELD);
		setDefaultFieldCount(4);
		addDispConvertor();
	}

	@Override
	public String[] getHiddenFieldCode() {
		return new String[] { "moneyf", "moneyb", "interestmoneyb",
				"interestmoneyf", "chargemoneyb", "chargemoneyf", "frate",
				"brate", "discountcharge", "discountdelaydaynum", "dismoneyy",
				"discountyrate", "ratedaynum", "discountinterest",
				"ddiscountdate", "pk_discount", "pk_register", "pk_baseinfo",
				"enddate", "invoicedate", "payunit", "paybankacc",
				"receivebankacc", "receiveunit", "fbmbilltype", "gatherdate",
				"registerstatus", "holdunit", "discount_account", "vbillstatus",
				"pk_curr", "opbilltype" };
	}

	@Override
	public String getRefNodeName() {
		return PK_FIELD_NAME;
	}

	@Override
	public String getRefTitle() {
		return TITLE;
	}

	@Override
	public String getTableName() {
		return TABLE_NAME;
	}

	protected RefDataFilter createRefFilter() {
		RefDataFilter m_RefFilter = null;
		m_RefFilter = new RefDataFilter();
		// 设置过滤的列
		// m_RefFilter.setFilterIndex(new
		// int[]{CommonUtil.getArrayLength(getFieldCode())+CommonUtil.getArrayLength(getHiddenFieldCode())
		// - 2});
		m_RefFilter.setFilterIndex(new int[] { getFieldIndex("vbillstatus") });
		// 设置过滤的值
		m_RefFilter.setFilterValue(new String[][] { { "1" } });
		m_RefFilter.setReverse(true);

		return m_RefFilter;
	}

	@Override
	protected String getCurrencyName() {
		// TODO Auto-generated method stub
		return "pk_curr";
	}

	@Override
	protected String[] getMoneyKeys() {
		// TODO Auto-generated method stub
		return new String[] { "moneyy" };
	}

	
	public boolean isCacheEnabled() {
		return false;
	}
}