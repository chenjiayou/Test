/**
 *
 */
package nc.ui.fbm.pub.refmodel;

import java.awt.Container;

import nc.ui.tm.framework.ref.AbstractTMRefModel;
import nc.ui.tm.framework.ref.filter.SQLRefModelFilter;

/**
 * <p>
 * ���й�˾��Ʊ����Ϣ����˾�����������ڸ��Ե�filter��,�ṩ������Ʊ�ݴ���ڵ�ʹ��
 * <p>�����ˣ�lpf
 * <b>���ڣ�2007-10-24
 *
 */
public class AllCorpBillGatherRefModel extends AbstractTMRefModel {

	private static final String FIELD_ORDER = " moneyy desc ";
	private String[] FIELD_CODE = new String[]{"fbmbillno","currtypename","moneyy","enddate"};
	private String[] FIELD_NAME = new String[]{nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm","UPPFBMComm-000036")/* @res"Ʊ�ݱ��"*/,nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm","UPPFBMComm-000037")/* @res"����"*/,nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm","UPPFBMComm-000038")/* @res"���"*/,nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("common","UC000-0000630")/*@res "��������"*/};
	private String TITLE = nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm","UPPFBMComm-000039")/* @res"Ʊ�ݲ���"*/;
	private String TABLE_NAME = "(select currtypename,fbm_register.moneyb,fbm_register.brate,fbm_register.paybillunit,fbm_baseinfo.invoiceunit,fbm_register.holdunit holdunit,fbm_register.pk_corp,fbm_register.keepunit keepunit, fbm_register.pk_loanbank,pk_register,fbm_baseinfo.pk_baseinfo as pk_baseinfo,fbm_baseinfo.enddate,"
		+ " fbm_baseinfo.invoicedate,fbm_baseinfo.payunit,fbm_baseinfo.paybankacc,fbm_baseinfo.receivebankacc,fbm_register.gathertype,"
		+ " fbm_baseinfo.receiveunit,fbm_register.sfflag,fbm_baseinfo.fbmbilltype,fbm_register.gatherdate,registerstatus,fbmbillno,pk_curr,fbm_baseinfo.moneyy,fbm_baseinfo.disable  "
		+ " from fbm_register  join fbm_baseinfo on (fbm_register.pk_baseinfo = fbm_baseinfo.pk_baseinfo) "
		+ " join bd_currtype on(fbm_baseinfo.pk_curr=bd_currtype.pk_currtype) "
		+ " where isnull(bd_currtype.dr,0)=0 and isnull(fbm_register.dr,0)=0 and  isnull(fbm_baseinfo.dr,0)=0 ) gathertable ";
	private String PK_FIELD_NAME = "pk_register";

	/**
	 *
	 */
	public AllCorpBillGatherRefModel() {
		super(null);
		setFieldCode(FIELD_CODE);
		setFieldName(FIELD_NAME);
		setPkFieldCode(PK_FIELD_NAME);
		setRefCodeField(FIELD_CODE[0]);
		setRefNameField(FIELD_CODE[0]);
		setDefaultFieldCount(4);
		setOrderPart(FIELD_ORDER);
		addSqlFilter(new SQLRefModelFilter(){
			protected String getSelfFilterString() {
				return " sfflag='Y' ";
			}
		});
		addDispConvertor();
	}

	/**
	 * @param con
	 */
	public AllCorpBillGatherRefModel(Container con) {
		super(con);
		setFieldCode(FIELD_CODE);
		setFieldName(FIELD_NAME);
		setPkFieldCode(PK_FIELD_NAME);
		setRefCodeField(FIELD_CODE[0]);
		setRefNameField(FIELD_CODE[0]);
		setDefaultFieldCount(4);
		setOrderPart(FIELD_ORDER);
		addDispConvertor();
	}

	@Override
	public String[] getHiddenFieldCode() {
		return new String[] { "pk_curr","pk_register", "pk_baseinfo",
				"invoicedate", "payunit", "paybankacc", "receivebankacc","pk_loanbank",
				"receiveunit", "fbmbilltype", "gatherdate","registerstatus", "holdunit","keepunit",
				"pk_corp","sfflag","paybillunit","keepunit","invoiceunit","moneyb","brate","disable","gathertype"};
	}

	/* (non-Javadoc)
	 * @see nc.ui.tm.framework.ref.AbstractTMRefModel#getRefNodeName()
	 */
	@Override
	public String getRefNodeName() {
		return PK_FIELD_NAME;
	}

	/* (non-Javadoc)
	 * @see nc.ui.tm.framework.ref.AbstractTMRefModel#getRefTitle()
	 */
	@Override
	public String getRefTitle() {
		return TITLE;
	}

	@Override
	protected String getCurrencyName() {
		return "pk_curr";
	}

	@Override
	protected String[] getMoneyKeys() {
		return new String[]{"moneyy"};
	}

	/* (non-Javadoc)
	 * @see nc.ui.tm.framework.ref.AbstractTMRefModel#getTableName()
	 */
	@Override
	public String getTableName() {
		// TODO Auto-generated method stub
		return TABLE_NAME;
	}

	/**
	 * ����ֵ���飭�������ֶ� �������ڣ�(2001-8-13 16:19:24)
	 *
	 * @return java.lang.String[]
	 */
	public java.lang.String[] getRefCodeValues() {
		String[] sDatas = super.getRefCodeValues();
		if(sDatas!=null&&sDatas.length>1){
			String[] returnStr = new String[1];
			returnStr[0] = sDatas[0];
			return returnStr;
		}
		return sDatas;
	}
	
	public boolean isCacheEnabled() {
		return false;
	}
}