package nc.ui.fbm.pub.refmodel;

import java.awt.Container;

import nc.ui.tm.framework.ref.AbstractTMRefModel;
import nc.ui.tm.framework.ref.filter.RefDataFilter;
import nc.vo.fbm.pub.constant.FbmBusConstant;
import nc.vo.fbm.reckon.ReckonBVO;
import nc.vo.pub.ValueObject;
import nc.vo.pub.lang.UFDate;
import nc.vo.pub.lang.UFDouble;

/**
 *
 * <p>
 *   已调剂票据参照
 *   用于票据调剂清算
 * </p>
 * @author bsrl
 * @date 2007-10-22
 * @version V5.02
 */
public class ReckonSourceRefModel extends AbstractTMRefModel{
	private String[] FIELD_CODE = new String[]{"fbmbillno","currtypename","holdunitname", "fbmmoneyy", "enddate","reckonunitname" };
	private String[] FIELD_NAME = new String[]{nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm","UPPFBMComm-000036")/* @res"票据编号"*/,nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm","UPPFBMComm-000037")/* @res"币种"*/,nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm","UPPFBMComm-000048")/* @res"持票单位"*/, nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm","UPPFBMComm-000038")/* @res"金额"*/,nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm","UPPFBMComm-000068")/* @res"到期日期"*/, nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm","UPPFBMComm-000069")/* @res"清算单位"*/};
	private String TITLE = nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm","UPPFBMComm-000039")/* @res"票据参照"*/;
	private String TABLE_NAME = "(select distinct fbm_accountdetail.pk_detail pk_detail,fbm_baseinfo.fbmbilltype" +
			", fbm_register.pk_register pk_register, fbm_accountdetail.pk_org cubasedoc, a.custname reckonunitname,  fbm_register.holdunit " +
			"holdunit, bd_cubasdoc.custname holdunitname, fbm_baseinfo.pk_baseinfo," +
			"fbm_accountdetail.liquidationdate enddate, fbm_accountdetail.isliquid isliquid, fbmbillno," +
			"fbm_baseinfo.pk_curr pk_currtype,fbm_register.brate brate, fbm_register.frate frate, " +
			"fbm_register.moneyf moneyf, fbm_register.moneyb moneyb, fbm_baseinfo.moneyy fbmmoneyy," +
			"fbm_accountdetail.moneyy moneyy , fbm_baseinfo.payunit payunit ,currtypename"
			+ " from fbm_accountdetail left join fbm_register on (fbm_accountdetail.pk_register = " +
					"fbm_register.pk_register) join ( select fbm_baseinfo.*,bd_currtype.currtypename from " +
					"fbm_baseinfo join bd_currtype on fbm_baseinfo.pk_curr =  bd_currtype.pk_currtype) " +
					"fbm_baseinfo on (fbm_register.pk_baseinfo = fbm_baseinfo.pk_baseinfo) " +
					" join bd_cubasdoc on bd_cubasdoc.pk_cubasdoc = fbm_register.holdunit " +
					" left join bd_cubasdoc a " +
					"on a.pk_cubasdoc =  fbm_accountdetail.pk_org  where " +
			"isnull(fbm_baseinfo.dr,0)=0 and isnull(fbm_register.dr,0)=0 and fbm_register.pk_billtypecode =" +
			" '"+FbmBusConstant.BILLTYPE_GATHER + "' and isnull(fbm_accountdetail.dr,0)=0 and " +
					"(fbm_accountdetail.reason = '" + FbmBusConstant.ACCOUNT_REASON_RELIEF_USE +"' or " +
							"fbm_accountdetail.reason = '" + FbmBusConstant.ACCOUNT_REASON_CENTER_USE +"')" +
							" and fbm_accountdetail.pk_type = '"+ FbmBusConstant.ACCOUNT_TYPE_LIQUID + "' " +
			" ) gathertable ";
	private String PK_FIELD_NAME = "pk_detail";
	private String[] HIDDEN_CODE = new String[]{"pk_currtype"};

	public ReckonSourceRefModel() {
		super(null);
		setFieldCode(FIELD_CODE);
		setFieldName(FIELD_NAME);
		setPkFieldCode(PK_FIELD_NAME);
		setRefCodeField(FIELD_CODE[0]);
		setRefNameField(FIELD_CODE[0]);
		setDefaultFieldCount(6);
		setHiddenFieldCode(HIDDEN_CODE);
		addDispConvertor();
	}

	public ReckonSourceRefModel(Container con) {
		super(con);
		setFieldCode(FIELD_CODE);
		setFieldName(FIELD_NAME);
		setPkFieldCode(PK_FIELD_NAME);
		setRefCodeField(FIELD_CODE[0]);
		setRefNameField(FIELD_CODE[0]);
		setDefaultFieldCount(6);
		setHiddenFieldCode(HIDDEN_CODE);
		addDispConvertor();
	}

	@Override
	public String[] getHiddenFieldCode() {
		return new String[] { "pk_detail", "pk_register", "pk_baseinfo","holdunit", "payunit","isliquid",
				"cubasedoc","moneyy","pk_currtype", "moneyb", "moneyf", "frate", "brate", "fbmbilltype"};
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

	@Override
	protected String getCurrencyName() {
		return "pk_currtype";
	}

	@Override
	protected String[] getMoneyKeys() {
		return new String[]{"fbmmoneyy"};
	}

	public ValueObject convertToVO(java.util.Vector vData) {
		ReckonBVO vo = new ReckonBVO();
		vo.setPk_detail((String) vData.get(getFieldIndex("pk_detail")));
		vo.setPk_source((String) vData.get(getFieldIndex("pk_register")));
		vo.setPk_baseinfo((String) vData.get(getFieldIndex("pk_baseinfo")));
		vo.setFbmbillmoney(new UFDouble(vData.get(getFieldIndex("fbmmoneyy")).toString()));
		vo.setHoldunit((String) vData.get(getFieldIndex("holdunit")));
		vo.setEnddate(new UFDate((String) vData.get(getFieldIndex("enddate"))));
		vo.setPayunit(((String) vData.get(getFieldIndex("payunit"))));
		vo.setMoneyy(new UFDouble(vData.get(getFieldIndex("moneyy")).toString()));
		vo.setMoneyf(new UFDouble(vData.get(getFieldIndex("moneyf")).toString()));
		vo.setMoneyb(new UFDouble(vData.get(getFieldIndex("moneyb")).toString()));
		vo.setBrate(new UFDouble(vData.get(getFieldIndex("brate")).toString()));
		vo.setFrate(new UFDouble(vData.get(getFieldIndex("frate")).toString()));
		vo.setPk_curr((String) vData.get(getFieldIndex("pk_currtype")));
		return vo;
	}

	protected RefDataFilter createRefFilter() {

		RefDataFilter m_RefFilter = null;
		m_RefFilter = new RefDataFilter();
		// 设置过滤的列
		m_RefFilter.setFilterIndex(new int[] { getFieldIndex("isliquid") });
		// 设置过滤的值
		m_RefFilter.setFilterValue(new String[][] { { "Y" } });
		m_RefFilter.setRefPane(this.getParent());

		return m_RefFilter;
	}
	
	public boolean isCacheEnabled() {
		return false;
	}

}