package nc.ui.fbm.pub.refmodel;

import java.awt.Container;
import java.util.HashMap;

import nc.ui.fbm.pub.FBMClientInfo;
import nc.ui.tm.framework.ref.AbstractTMRefModel;
import nc.ui.tm.framework.ref.filter.RefDataFilter;
import nc.ui.tm.framework.util.ClientInfo;
import nc.vo.fts.pub.CenterUnitUtil;

/**
 * <p>
 * ��������ʹ�ã������˻����ڲ����̵��ڲ�����˻�
 * <p>
 * �����ˣ�bsrl <b>���ڣ�2007-10-31
 * 
 */
public class ReckonAccRefModel extends AbstractTMRefModel {

	private static HashMap<String, String> settleMap = null;

	public ReckonAccRefModel(Container con) {
		super(con);
	}

	private static final String frozen_name = "(case "
			+ "bd_accid.frozenflag when '0' then '����' "
			+ "when '1' then '����' "
			+ "when '2' then '����' "
			+ "when '3' then '���ֶ���' "
			+ "when '4' then '����' "
			+ "else '����' end)"; // �����־
	private static final String acccl_name = "(case "
			+ "acccl when 0 then '����' "
			+ "when 1 then '����' "
			+ "when 2 then '����' "
			+ "when 3 then '֪ͨ' "
			+ "when 4 then 'Э��' "
			+ "when 5 then '��֤��' "
			+ "else '����' end)"; // �˻�����

	public ReckonAccRefModel() {
		this(null);
	}

	public String getRefNodeName() {
		return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add", "UPP3620ADD-000069")/*
																									 * @res
																									 * "�ڲ��˻�����"
																									 */;
	}

	public String getRefTitle() {
		return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add", "UPP3620ADD-000069")/*
																									 * @res
																									 * "�ڲ��˻�����"
																									 */;
	}

	public java.lang.String[] getFieldCode() {
		return new String[] { "accidcode",// �˻�����
				"accidname",// �˻�����
				acccl_name,// �˻���������
				"currtypename",// ����
				"currmny",// ��ǰ���
				"custname",// ������λ
				frozen_name };
	}

	/**
	 * getDefaultFieldCount ����ע�⡣
	 */
	public int getDefaultFieldCount() {
		return 7;
	}

	public java.lang.String[] getFieldName() {
		return new String[] {
				nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm", "UPPFBMComm-000061")/*
																							 * @res
																							 * "�˻�����"
																							 */,
				nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm", "UPPFBMComm-000062")/*
																							 * @res
																							 * "�˻�����"
																							 */,
				nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm", "UPPFBMComm-000063")/*
																							 * @res
																							 * "�˻�����"
																							 */,
				nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm", "UPPFBMComm-000037")/*
																							 * @res
																							 * "����"
																							 */,
				nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm", "UPPFBMComm-000064")/*
																							 * @res
																							 * "��ǰ���"
																							 */,
				nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm", "UPPFBMComm-000065")/*
																							 * @res
																							 * "������λ"
																							 */,
				nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm", "UPPFBMComm-000066") /*
																							 * @res
																							 * "�����־"
																							 */
		};
	}

	public String[] getHiddenFieldCode() {
		return new String[] { "pk_currtype", "pk_cubasdoc", "pk_accid",
				"pk_createcenter",
				"pk_corp1",// ������λ��Ӧ�Ĺ�˾pk
				"pk_corp",// ������λ���ڽ������ĵĹ�˾pk
				"accopendate",
				"acccl",// �˻�����
				"settlecentername", "pk_cubasdoccenter", "custnamecenter",
				"bd_accid.frozenflag" };
	}

	/**
	 * ���ߣ�hzguo <br>
	 * ���ڣ�2006-4-5
	 * 
	 * @see nc.ui.tm.framework.ref.AbstractTMRefModel#getCurrencyName()
	 */
	protected String getCurrencyName() {
		return "pk_currtype";
	}

	/**
	 * ���ߣ�hzguo <br>
	 * ���ڣ�2006-4-5
	 * 
	 * @see nc.ui.tm.framework.ref.AbstractTMRefModel#getMoneyKeys()
	 */
	protected String[] getMoneyKeys() {
		return new String[] { "currmny" };
	}

	public String getTableName() {
		return getAllAccConditionTableName();
	}

	/**
	 * <p>
	 * �˻���������
	 * <p>
	 * ���ߣ�bsrl <br>
	 * ���ڣ�2006-11-30
	 * 
	 * @return
	 */
	public static String getAccConditionTableName() {
		String table = " select distinct bd_accid.accidcode,bd_accid.accidname,bd_accid.acccl,bd_accid.currmny,bd_accid.accopendate, bd_cubasdoc.pk_cubasdoc pk_cubasdoc,bd_cubasdoc.custname,bd_accid.pk_currtype pk_currtype,bd_currtype.currtypename,bd_accid.pk_accid,bd_accid.frozenflag,bd_accid.accflag,bd_accid.pk_createcenter "
				+ " ,bd_cubasdoc.pk_corp1,bd_settlecenter.pk_corp,bd_settlecenter.settlecentername, a.pk_cubasdoc as pk_cubasdoccenter, a.custname as custnamecenter "
				+ " from bd_accid "
				+ " left outer join bd_currtype on bd_accid.pk_currtype=bd_currtype.pk_currtype "
				+ " left outer join bd_settleunit on bd_accid.pk_settleunit=bd_settleunit.pk_settleunit "
				+ " left outer join bd_cubasdoc on bd_cubasdoc.pk_corp1 = bd_settleunit.pk_corp1 "
				+ " left outer join bd_settlecenter on bd_settlecenter.pk_settlecenter = bd_settleunit.pk_settlecent "
				+ " left outer join bd_cubasdoc a on bd_settlecenter.pk_corp = a.pk_corp1"
				+ " left outer join bd_cumandoc on bd_cubasdoc.pk_cubasdoc= bd_cumandoc.pk_cubasdoc"
				+ " where "
				+ " bd_cubasdoc.custprop <> 0 "
				+ " and bd_cubasdoc.pk_corp1 is not null "
				+ "and bd_cumandoc.pk_corp = '"
				+ ClientInfo.getCorpPK()
				+ "' ";

		if (FBMClientInfo.isSettleCenter()) {
			String centerpk = getSettleCenterPK(ClientInfo.getCorpPK());
			table += " and bd_accid.pk_createcenter = '" + centerpk + "' ";
		} else {
			table += " and bd_cubasdoc.pk_corp1 = '"
					+ ClientInfo.getCorpPK()
					+ "'";
		}

		return "(" + table + " ) bd_accid ";
	}

	public static String getAllAccConditionTableName() {
		return getAccConditionTableName();
	}

	/**
	 * ���ұ���˾���ڲ�����
	 * 
	 * @return
	 */
	public static String getCusDocByCorp() {
		return " select bd_cubasdoc.pk_cubasdoc from bd_cubasdoc where bd_cubasdoc.pk_corp1= '"
				+ ClientInfo.getCorpPK()
				+ "' ";
	}

	private static HashMap<String, String> getSettleMap() {
		if (settleMap == null) {
			settleMap = new HashMap<String, String>();
		}
		return settleMap;
	}

	private static String getSettleCenterPK(String pk_corp) {
		if (!getSettleMap().containsKey(pk_corp)) {
			String centerpk = CenterUnitUtil.instance.getSettleCenterVOByCorpPK(pk_corp).getPrimaryKey();
			getSettleMap().put(pk_corp, centerpk);
		}

		return getSettleMap().get(pk_corp);
	}

	public int[] getTransColumns() {
		return new int[] { 3, 5 };
	}

	public java.lang.String getPkFieldCode() {
		return "pk_accid";
	}

	protected RefDataFilter createRefFilter() {

		RefDataFilter m_RefFilter_child_1 = null;
		m_RefFilter_child_1 = new RefDataFilter();
		// ���ù��˵��� ���˷�Ʊ
		m_RefFilter_child_1.setFilterIndex(new int[] { getFieldIndex("acccl") });
		// ���ù��˵�ֵ
		m_RefFilter_child_1.setFilterValue(new String[][] { { "0" } });
		m_RefFilter_child_1.setReverse(true);
		m_RefFilter_child_1.setRefPane(this.getParent());

		RefDataFilter m_RefFilter_child = null;
		m_RefFilter_child = new RefDataFilter(m_RefFilter_child_1);
		// ���ù��˵��� Ʊ��״̬����
		m_RefFilter_child.setFilterIndex(new int[] { getFieldIndex("bd_accid.frozenflag") });
		// ���ù��˵�ֵ
		m_RefFilter_child.setFilterValue(new String[][] { { "0" } });
		m_RefFilter_child.setReverse(true);
		m_RefFilter_child.setRefPane(this.getParent());

		return m_RefFilter_child;
	}

}