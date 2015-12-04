package nc.ui.fbm.pub.refmodel;

import java.awt.Container;
import java.util.Vector;

import nc.ui.fbm.pub.FBMClientInfo;
import nc.ui.tm.framework.ref.AbstractTMRefModel;

/**
 * Ʊ�������գ�������ͳ��ҵ��ģ����Ӧ��Ʊ�ݵ�������Ԥ��ʹ�ã�
 * @author bsrl
 *  2008-03-21
 */
public class FBMBillStyleRefModel extends AbstractTMRefModel{
	// ��ʾ�ֶα���
	private String[] fieldNames = new String[]{nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000394")/*@res "Ʊ�����"*/};

	/**
	 *
	 */
	public FBMBillStyleRefModel() {
		super();
	}

	/**
	 * @param con
	 */
	public FBMBillStyleRefModel(Container con) {
		super(con);
	}

	/* (non-Javadoc)
	 * @see nc.ui.tm.framework.ref.AbstractTMRefModel#getRefNodeName()
	 */
	@Override
	public String getRefNodeName() {
		return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add","UPP3620ADD-000059")/*@res "Ʊ��������"*/;
	}

	@Override
	public Vector getData() {
		Vector vec = new Vector();
		if(FBMClientInfo.isSettleCenter()) {
			Vector v1 = new Vector();
			v1.add(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add","UPP3620ADD-000060")/*@res "����"*/);
			Vector v2 = new Vector();
			v2.add(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add","UPP3620ADD-000061")/*@res "ͳ��"*/);
			Vector v3 = new Vector();
			v3.add(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add","UPP3620ADD-000062")/*@res "ȫ��"*/);
			vec.add(v1);
			vec.add(v2);
			vec.add(v3);
		} else {
			Vector v1 = new Vector();
			v1.add(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add","UPP3620ADD-000060")/*@res "����"*/);
			vec.add(v1);
		}
		return vec;
	}

	@Override
	public String[] getFieldName() {
		return fieldNames;
	}

	@Override
	public int getDefaultFieldCount() {
		return 1;
	}

	@Override
	public String getPkFieldCode() {
		return "fbmbillstyle";
	}

	/**
	 * ���ձ���
	 * �������ڣ�(01-4-4 0:57:23)
	 * @return java.lang.String
	 */
	public String getRefTitle() {
		return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000394")/*@res "Ʊ�����"*/;
	}

	@Override
	public String getRefNameField() {
		return getFieldCode()[0];
	}

	@Override
	public String getTableName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] getFieldCode() {
		// TODO Auto-generated method stub
		return new String[]{"fbmbillstyle"};
	}
}