package nc.ui.fbm.pub.refmodel;

import java.awt.Container;
import java.util.Hashtable;

import nc.ui.fbm.pub.FBMClientInfo;
import nc.ui.fbm.relief.ReliefUtil;
import nc.ui.tm.framework.ref.AbstractTMRefModel;
import nc.ui.tm.framework.ref.filter.RefDataFilter;
import nc.ui.tm.framework.util.ClientInfo;
import nc.vo.fbm.relief.ReliefBVO;
import nc.vo.pub.ValueObject;
import nc.vo.pub.lang.UFDate;
import nc.vo.pub.lang.UFDouble;

/**
 *
 * �๦��˵���� ��������ѡƱ���� ���ڣ�2007-10-22 ����Ա�� wues
 *
 */
public class ReliefRefModel extends AbstractTMRefModel {

	private String[] FIELD_CODE = new String[] { "fbmbillno", "currtypename",
			"moneyy" ,"enddate"};
	private String[] HIDDEN_CODE = new String[] { "pk_curr" };
	private String[] FIELD_NAME = new String[] { nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm","UPPFBMComm-000036")/* @res"Ʊ�ݱ��"*/, nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm","UPPFBMComm-000037")/* @res"����"*/, nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm","UPPFBMComm-000038")/* @res"���"*/,nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm","UPPFBMComm-000395") /* @res"��������"*/ };
	/**
	 * ȡĳ��״̬����Ʊ�Ǽ���Ϣ��Ʊ�ݻ�����Ϣ �ڴ�Ϊ�ѵ������
	 */
	// private String endStatus = "has_relief_keep";
	private String TITLE = nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm","UPPFBMComm-000070")/* @res"Ʊ��ѡ��"*/;

	private String PK_FIELD_NAME = "pk_register";

	public ReliefRefModel() {
		super(null);
		setFieldCode(FIELD_CODE);
		setFieldName(FIELD_NAME);
		setPkFieldCode(PK_FIELD_NAME);
		setRefCodeField(FIELD_CODE[0]);
		setRefNameField(FIELD_CODE[0]);
		setHiddenFieldCode(HIDDEN_CODE);
		setDefaultFieldCount(3);
		addDispConvertor();

	}

	public ReliefRefModel(Container con) {
		super(con);
		setFieldCode(FIELD_CODE);
		setFieldName(FIELD_NAME);
		setPkFieldCode(PK_FIELD_NAME);
		setRefCodeField(FIELD_CODE[0]);
		setRefNameField(FIELD_CODE[0]);
		setHiddenFieldCode(HIDDEN_CODE);
		setDefaultFieldCount(4);
		addDispConvertor();
	}

	@Override
	public String[] getHiddenFieldCode() {
		return new String[] { "pk_register", "pk_baseinfo", "enddate",
				"invoiceunit", "holdunit", "receiveunit", "payunit",
				"paybillunit", "registerstatus", "pk_curr", "moneyb", "moneyf",
				"frate", "brate", "fbmbilltype" };
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
		StringBuffer strBuf = new StringBuffer();
		strBuf
				.append(" (select distinct pk_register, fbm_baseinfo.pk_baseinfo, fbmbillno, fbm_baseinfo.moneyy,fbmbilltype, ");
		strBuf
				.append(" fbm_baseinfo.enddate, fbm_baseinfo.invoiceunit invoiceunit, fbm_register.brate, fbm_register.frate, fbm_register.moneyf, fbm_register.moneyb, ");
		strBuf
				.append("fbm_baseinfo.receiveunit, fbm_baseinfo.payunit, holdunit, paybillunit, pk_curr , registerstatus ,currtypename");
		strBuf
				.append(" from fbm_register  ");
		strBuf
				.append(" join (select fbm_baseinfo.*,bd_currtype.currtypename from fbm_baseinfo join bd_currtype on fbm_baseinfo.pk_curr =  bd_currtype.pk_currtype ) fbm_baseinfo on (fbm_register.pk_baseinfo = fbm_baseinfo.pk_baseinfo) ");
		strBuf
				.append(" where isnull(fbm_register.dr,0)=0  and isnull(fbm_baseinfo.dr,0)=0 ");


		// �����ǰ��¼��Ϊ�������ģ���ȡ���ļ��������������ӵ�λ�����Ϊ���㵥λ�򲻼ӹ�˾����
		String pk_corp = ClientInfo.getCorpPK();
		if (FBMClientInfo.isSettleCenter(pk_corp))
			// ����λ���������ӵ�λ
			strBuf.append(" and fbm_register.pk_corp in (").append(
					ReliefUtil.getAllSubCorp(pk_corp)).append(")");

		strBuf.append(" ) gatherTable");
		return strBuf.toString();
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

	protected RefDataFilter createRefFilter() {

		RefDataFilter m_RefFilter = null;
		m_RefFilter = new RefDataFilter();
		// ���ù��˵���
		m_RefFilter.setFilterIndex(new int[] { getFieldIndex("registerstatus") });
		// ���ù��˵�ֵ
		m_RefFilter.setFilterValue(new String[][] { { "has_relief_keep" } });
		m_RefFilter.setReverse(true);
		m_RefFilter.setRefPane(this.getParent());

		return m_RefFilter;
	}

	public ValueObject convertToVO(java.util.Vector vData) {
		ReliefBVO vo = new ReliefBVO();

		// ��������field��vector�е�index
		Hashtable hashFiledIndex = getHtCodeIndex();

		vo.setFbmbillno((String) vData.get((Integer) hashFiledIndex
				.get("fbmbillno")));

		vo.setPk_curr((String) (vData.get((Integer) hashFiledIndex
				.get("pk_curr"))));
		vo.setMoneyy(new UFDouble(vData.get(
				(Integer) hashFiledIndex.get("moneyy")).toString()));
		vo.setPk_source((String) vData.get((Integer) hashFiledIndex
				.get("pk_register")));
		vo.setPk_baseinfo((String) vData.get((Integer) hashFiledIndex
				.get("pk_baseinfo")));
		vo.setEnddate(new UFDate((String) vData.get((Integer) hashFiledIndex
				.get("enddate"))));
		vo.setInvoiceunit((String) vData.get((Integer) hashFiledIndex
				.get("invoiceunit")));
		vo.setHoldunit((String) vData.get((Integer) hashFiledIndex
				.get("holdunit")));
		vo.setReceiveunit((String) vData.get((Integer) hashFiledIndex
				.get("receiveunit")));
		vo.setPayunit((String) vData.get((Integer) hashFiledIndex
				.get("payunit")));
		vo.setPaybillunit((String) vData.get((Integer) hashFiledIndex
				.get("paybillunit")));
		return vo;
	}
	
	public boolean isCacheEnabled() {
		return false;
	}
}