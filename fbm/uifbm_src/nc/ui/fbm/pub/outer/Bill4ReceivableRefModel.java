package nc.ui.fbm.pub.outer;

import nc.ui.tm.framework.ref.AbstractTMRefModel;

/**
 *
 * <p>
 *   提供收付报系统
 *   收款单票据编号参照
 * </p>
 * 请在初始化UI时调用方法
		AbstractRefModel refmdl=((UIRefPane) getBillCardPanel().getHeadItem("字段名").getComponent()).getRefModel();
		if (refmdl instanceof AbstractTMRefModel) {
			Bill4ReceiveableFilter filter=new Bill4ReceiveableFilter(getBillCardPanel().getHeadItem("字段名"));
			((AbstractTMRefModel)refmdl).addSqlFilter(filter);
		}
 *
 * @author xwq
 * @date 2007-9-11
 * @version V5.0
 */
public class Bill4ReceivableRefModel extends AbstractTMRefModel {

	private String[] FIELD_CODE = new String[]{"fbmbillno","pk_curr","moneyy"};
	private String[] FIELD_NAME = new String[]{nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm","UPPFBMComm-000036")/* @res"票据编号"*/,nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm","UPPFBMComm-000037")/* @res"币种"*/,nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm","UPPFBMComm-000038")/* @res"金额"*/};

	private String PK_FIELD_NAME = "fbmbillno";


	public Bill4ReceivableRefModel() {
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
		return nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm","UPPFBMComm-000039")/* @res"票据参照"*/;
	}

	@Override
	public String getTableName() {
		return "fbm_baseinfo";
	}


	@Override
	public String[][] getFormulas() {
		// TODO Auto-generated method stub
		return new String[][]{
				{"pk_curr","pk_curr->getColValue(bd_currtype,currtypename,pk_currtype,pk_curr)"}};
	}

}