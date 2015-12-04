package nc.ui.fbm.pub.outer;

import nc.bs.logging.Logger;
import nc.ui.fbm.pub.FBMClientInfo;
import nc.ui.pub.ClientEnvironment;
import nc.ui.pub.para.SysInitBO_Client;
import nc.ui.tm.framework.ref.AbstractTMRefModel;
import nc.vo.fbm.pub.constant.FBMParamConstant;
import nc.vo.fbm.pub.constant.FbmBusConstant;
import nc.vo.fbm.pub.constant.FbmStatusConstant;
import nc.vo.pub.BusinessException;

public class Bill4PayableEditRefModel extends AbstractTMRefModel {

	private String[] FIELD_CODE = new String[] { "fbmbillno", "currtypename",
			"moneyy", "bankdocname", "enddate" };
	private String[] FIELD_NAME = new String[] {
			nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm", "UPPFBMComm-000036")/*
																						 * @res
																						 * "票据编号"
																						 */,
			nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm", "UPPFBMComm-000037")/*
																						 * @res
																						 * "币种"
																						 */,
			nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm", "UPPFBMComm-000038") /*
																						 * @res
																						 * "金额"
																						 */,
			"付款银行", "到期日期" };
	private String[] HIDDEN_FIELD = new String[] { "pk_curr", "pk_cubasdoc",
			"frate", "brate", "moneyf", "moneyb", "paytype" };// paytype指托管或者私有
	private String PK_FIELD_NAME = "fbmbillno";

	public Bill4PayableEditRefModel() {
		super();
		setFieldCode(FIELD_CODE);
		setFieldName(FIELD_NAME);
		setPkFieldCode(PK_FIELD_NAME);
		setRefCodeField(FIELD_CODE[0]);
		setRefNameField(FIELD_CODE[0]);
		setHiddenFieldCode(HIDDEN_FIELD);
		setDefaultFieldCount(5);
		addDispConvertor();
		setWherePart(getFilterString());
	}

	@Override
	public String getRefNodeName() {
		// TODO Auto-generated method stub
		return PK_FIELD_NAME;
	}

	@Override
	public String getRefTitle() {
		return nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm", "UPPFBMComm-000039")/*
																						 * @res
																						 * "票据参照"
																						 */;
	}

	@Override
	// NCdp200704925付款单参照有问题，参照出其它公司的开票、已调剂到其它公司的票据
	public String getTableName() {

		return "(select case fbm_register.pk_billtypecode when '36GA' then null when '36GL' then fbm_baseinfo.receiveunit end as pk_cubasdoc,fbm_register.pk_billtypecode pk_billtypecode,fbm_register.sfflag sfflag,fbm_register.holdunit holdunit,fbm_register.keepunit keepunit, fbm_register.pk_loanbank,pk_register,"
				+ "fbm_register.frate,fbm_register.brate,fbm_register.moneyf,fbm_register.moneyb,"
				+ "fbm_baseinfo.pk_baseinfo,fbm_baseinfo.enddate, isnull(fbm_baseinfo.disable,'N') disable,fbm_baseinfo.invoicedate,fbm_baseinfo.paybankacc,fbm_baseinfo.paybank,fbm_baseinfo.receivebankacc,"
				+ "fbm_baseinfo.receiveunit,fbm_baseinfo.invoiceunit invoiceunit, fbm_baseinfo.fbmbilltype,fbm_register.gatherdate,registerstatus,fbmbillno,pk_curr,fbm_baseinfo.moneyy,currtypename,bankdocname, "
				+ " case registerstatus when 'has_relief_keep' then '"
				+ FbmBusConstant.BILL_UNISTORAGE
				+ "' else '"
				+ FbmBusConstant.BILL_PRIVACY
				+ "' end as paytype"
				+ " from fbm_register join ( select fbm_baseinfo.*,bd_currtype.currtypename from fbm_baseinfo join bd_currtype on fbm_baseinfo.pk_curr =  bd_currtype.pk_currtype ) fbm_baseinfo on (fbm_register.pk_baseinfo = fbm_baseinfo.pk_baseinfo) "
				+ "left join ( select fbm_baseinfo.pk_baseinfo,bd_bankdoc.pk_bankdoc,bd_bankdoc.bankdocname from fbm_baseinfo join bd_bankdoc on "
				+ " fbm_baseinfo.paybank =  bd_bankdoc.pk_bankdoc ) fbm_baseinfo2 on (fbm_register.pk_baseinfo = fbm_baseinfo2.pk_baseinfo) "
				+ " where isnull(fbm_register.dr,0)=0 and isnull(fbm_baseinfo.dr,0)=0  and "
				+ "    (( (fbm_register.sfflag = 'Y' and fbm_register.pk_corp = '"
				+ ClientEnvironment.getInstance().getCorporation().getPk_corp()
				+ "'"
				+ "and fbm_register.keepunit= '"
				+ FBMClientInfo.getCorpCubasdoc(ClientEnvironment.getInstance().getCorporation().getPk_corp())
				+ "' and registerstatus = 'register') or  fbm_register.keepunit = '"
				+ FBMClientInfo.getCorpCubasdoc(ClientEnvironment.getInstance().getCorporation().getPk_corp())
				+ "' and registerstatus = 'has_relief_keep') "
				+ " or ((fbm_register.sfflag = 'N' and registerstatus = '"
				+ FbmStatusConstant.HAS_INVOICE
				+ "' and fbm_register.pk_corp = '"
				+ ClientEnvironment.getInstance().getCorporation().getPk_corp()
				+ "')))) gathertable ";
	}

	/**
	 * 返回过滤条件,如果收付报和票据不集成应用则参照为空
	 * 
	 * @return
	 */
	private String getFilterString() {
		String pk_corp = ClientEnvironment.getInstance().getCorporation().getPk_corp();
		try {
			String paramValue = SysInitBO_Client.queryByParaCode(pk_corp, FBMParamConstant.FBM005).getValue();
			if ("N".equals(paramValue)) {
				return "1=0";
			}
		} catch (BusinessException e) {
			Logger.error(e.getMessage(), e);
		}
		return "";
	}

	@Override
	protected String[] getMoneyKeys() {
		// TODO Auto-generated method stub
		return new String[] { "moneyy" };
	}

	@Override
	protected String getCurrencyName() {
		// TODO Auto-generated method stub
		return "pk_curr";
	}

	public boolean isCacheEnabled() {
		return false;
	}
}