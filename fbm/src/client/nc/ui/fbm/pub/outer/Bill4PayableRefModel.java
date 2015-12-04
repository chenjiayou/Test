package nc.ui.fbm.pub.outer;

import nc.ui.tm.framework.ref.AbstractTMRefModel;

/**
 *
 * <p>
 *   �ṩ�ո���ϵͳ
 *   ���Ʊ�ݱ�Ų���
 * </p>
 *  ���ڳ�ʼ��UIʱ���÷���
		AbstractRefModel refmdl=((UIRefPane) getBillCardPanel().getHeadItem("�ֶ���").getComponent()).getRefModel();
		if (refmdl instanceof AbstractTMRefModel) {
			Bill4PayableFilter filter=new Bill4PayableFilter(getBillCardPanel().getHeadItem("�ֶ���"));
			((AbstractTMRefModel)refmdl).addSqlFilter(filter);
		}
 * @author xwq
 * @date 2007-9-11
 * @version V5.0
 */
public class Bill4PayableRefModel extends AbstractTMRefModel {

	private String[] FIELD_CODE = new String[]{"fbmbillno","pk_curr","moneyy"};
	private String[] FIELD_NAME = new String[]{nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm","UPPFBMComm-000036")/* @res"Ʊ�ݱ��"*/,nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm","UPPFBMComm-000037")/* @res"����"*/,nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm","UPPFBMComm-000038")/* @res"���"*/};

	private String PK_FIELD_NAME = "fbmbillno";


	public Bill4PayableRefModel() {
		super();
		setFieldCode(FIELD_CODE);
		setFieldName(FIELD_NAME);
		setPkFieldCode(PK_FIELD_NAME);
		setRefCodeField(FIELD_CODE[0]);
		setRefNameField(FIELD_CODE[0]);
		setDefaultFieldCount(3);
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
		// TODO Auto-generated method stub
		return "fbm_baseinfo";
	}

	@Override
	public String[][] getFormulas() {
		// TODO Auto-generated method stub
		return new String[][]{
				{"pk_curr","pk_curr->getColValue(bd_currtype,currtypename,pk_currtype,pk_curr)"}};
	}

}