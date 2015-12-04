package nc.ui.fbm.pub.outer;

import nc.ui.pub.ClientEnvironment;
import nc.ui.tm.framework.ref.AbstractTMRefModel;
import nc.vo.fbm.pub.constant.FbmBusConstant;
import nc.vo.fbm.pub.constant.FbmStatusConstant;

/**
 *
 * ��Ʊ��ʽ���ɵ��ո�������
 * Ʊ�ݺŲ���
 * DJZBheadVO.jsfsbmΪ36GL��ϵͳ��ԴΪ�ʽ�Ʊ��
 * @author xwq
 *
 */
public class Bill4InvoiceEditRefModel extends AbstractTMRefModel {
	private String[] FIELD_CODE = new String[]{"fbmbillno","currtypename","moneyy"};
	private String[] FIELD_NAME = new String[]{nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm","UPPFBMComm-000036")/* @res"Ʊ�ݱ��"*/,nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm","UPPFBMComm-000037")/* @res"����"*/,nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm","UPPFBMComm-000038")/* @res"���"*/};
	private String[] HIDDEN_FIELD = new String[]{"pk_curr"};
	private String PK_FIELD_NAME = "fbmbillno";


	public Bill4InvoiceEditRefModel() {
		super();
		setFieldCode(FIELD_CODE);
		setFieldName(FIELD_NAME);
		setPkFieldCode(PK_FIELD_NAME);
		setRefCodeField(FIELD_CODE[0]);
		setRefNameField(FIELD_CODE[0]);
		setHiddenFieldCode(HIDDEN_FIELD);
		setDefaultFieldCount(3);
		addDispConvertor();
	}

	@Override
	public String getRefNodeName() {
		// TODO Auto-generated method stub
		return PK_FIELD_NAME;
	}

	@Override
	public String getRefTitle() {
		// TODO Auto-generated method stub
		return nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm","UPPFBMComm-000039")/* @res"Ʊ�ݲ���"*/;
	}

	@Override
	public String getTableName() {
		return "(select fbm_baseinfo.*,bd_currtype.currtypename from fbm_baseinfo join bd_currtype on fbm_baseinfo.pk_curr =  bd_currtype.pk_currtype where " + getFilterString() + ") fbm_baseinfo";
	}

	/**
	 * ���ع�������
	 * @return
	 */
	private String getFilterString(){
		String pk_corp = ClientEnvironment.getInstance().getCorporation().getPk_corp();
		StringBuffer sb = new StringBuffer();
		sb.append(" exists(select pk_register from fbm_register ");
		sb.append(" where fbm_register.pk_baseinfo = fbm_baseinfo.pk_baseinfo ");
		sb.append(" and isnull(fbm_register.dr,0)=0  ");
		sb.append(" and fbm_register.pk_billtypecode = '" + FbmBusConstant.BILLTYPE_INVOICE +"'");
		sb.append(" and fbm_register.pk_corp = '" + pk_corp + "'");
		sb.append(" and fbm_register.registerstatus in( '" + FbmStatusConstant.HAS_INVOICE +"','"+FbmStatusConstant.HAS_PAYBILL+"')" );
		sb.append(" )");
		return sb.toString();
	}
	@Override
	protected String[] getMoneyKeys() {
		// TODO Auto-generated method stub
		return new String[]{"moneyy"};
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