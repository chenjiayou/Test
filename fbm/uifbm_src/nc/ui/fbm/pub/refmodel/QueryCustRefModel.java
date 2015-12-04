package nc.ui.fbm.pub.refmodel;

import nc.ui.tm.framework.ref.AbstractTMRefModel;

/**
 *  ���ڲ�ѯ�����ڵĸ��λ���տλ
 * @author xwq
 *
 */
public class QueryCustRefModel extends AbstractTMRefModel {
	private String[] FIELD_CODE = new String[] { "custcode", "custname" };
	private String[] FIELD_NAME = new String[] { nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm","UPPFBMComm-000043")/* @res"���̱��"*/, nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm","UPPFBMComm-000044")/* @res"��������"*/ };
	private String[] HIDDEN_FIELD = new String[]{ "pk_cubasdoc"};

	@Override
	public String[] getFieldCode() {
		return FIELD_CODE;
	}

	@Override
	public String[] getFieldName() {
		return FIELD_NAME;
	}

	@Override
	public String[] getHiddenFieldCode() {
		return HIDDEN_FIELD;
	}

	@Override
	public String getPkFieldCode() {
		return "pk_cubasdoc";
	}

	@Override
	public String getRefNodeName() {
		// TODO Auto-generated method stub
		return "v_fbm_cubasdoc";
	}

	@Override
	public String getRefTitle() {
		// TODO Auto-generated method stub
		return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add","UPP3620ADD-000068")/*@res "Ʊ�ݿ��̵���"*/;
	}

	@Override
	public String getTableName() {
		// TODO Auto-generated method stub
		return "v_fbm_cubasdoc";
	}

}