package nc.ui.fbm.pub.refmodel;

import java.awt.Container;

import nc.ui.tm.framework.ref.AbstractTMRefModel;
import nc.itf.fbm.cust.CombineCust;

public class CombineCustRefModel extends AbstractTMRefModel {

	private String FIELD_ORDER = "custcode desc";

	private String[] FIELD_CODE = new String[]{"custcode","custname"};
	private String[] FIELD_NAME = new String[]{nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("common","UC000-0001587")/*@res "客商编码"*/,nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("common","UC000-0001578")/*@res "客商名称"*/};
	private String TITLE = nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add","UPP3620ADD-000054")/*@res "客商基本档案及银行档案"*/;
	private String TABLE_NAME = CombineCust.getCombineCustSQL();
	private String PK_FIELD_NAME = "pk_cubasdoc";

	public CombineCustRefModel(){
		super();
		init();
	}

	public CombineCustRefModel(Container con) {
		super(con);
		init();
	}

	private void  init(){
		setFieldCode(FIELD_CODE);
		setFieldName(FIELD_NAME);
		setPkFieldCode(PK_FIELD_NAME);
		setRefCodeField(FIELD_CODE[0]);
		setRefNameField(FIELD_CODE[1]);
		setDefaultFieldCount(2);
		setOrderPart(FIELD_ORDER);
	}



	@Override
	public String[] getHiddenFieldCode() {
		// TODO Auto-generated method stub
		return new String[]{PK_FIELD_NAME};
	}

	@Override
	public String getRefNodeName() {
		// TODO Auto-generated method stub
		return PK_FIELD_NAME;
	}

	@Override
	public String getRefTitle() {
		// TODO Auto-generated method stub
		return TITLE;
	}

	@Override
	public String getTableName() {
		// TODO Auto-generated method stub
		return TABLE_NAME;
	}

}