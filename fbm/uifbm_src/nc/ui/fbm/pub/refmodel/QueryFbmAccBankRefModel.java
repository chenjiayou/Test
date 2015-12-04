package nc.ui.fbm.pub.refmodel;

import java.awt.Container;

import nc.ui.tm.framework.ref.AbstractTMRefModel;

/**
 * 查询对话框银行帐号(手输+参照)
 * @author xwq
 *
 */
public class QueryFbmAccBankRefModel extends AbstractTMRefModel {
	// 显示字段编码
	private String[] fieldCodes = new String[] { "bankacc", "pk_currtype","bankname",  "address" };
	private String[] fieldNames = new String[]{nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm","UPPFBMComm-000045")/* @res"银行帐号"*/,nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm","UPPFBMComm-000037")/* @res"币种"*/,nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm","UPPFBMComm-000046")/* @res"开户银行名称"*/,nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm","UPPFBMComm-000047")/* @res"银行地址"*/};
	private String custPK;

	/**
	 *
	 */
	public QueryFbmAccBankRefModel() {
		super();
	}

	/**
	 * @param con
	 */
	public QueryFbmAccBankRefModel(Container con) {
		super(con);
		setMatchPkWithWherePart(true);
	}

	/* (non-Javadoc)
	 * @see nc.ui.tm.framework.ref.AbstractTMRefModel#getRefNodeName()
	 */
	@Override
	public String getRefNodeName() {
		return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add","UPP3620ADD-000055")/*@res "开户银行参照"*/;
	}

	/* (non-Javadoc)
	 * @see nc.ui.tm.framework.ref.AbstractTMRefModel#getTableName()
	 */
	@Override
	public String getTableName() {
		return "v_fbm_accbank";
	}

	@Override
	public String[] getFieldCode() {
		// TODO Auto-generated method stub
		return fieldCodes;
	}

	@Override
	public String[] getFieldName() {
		return fieldNames;
	}

	public void setCustPK(String pk) {
		this.custPK=pk;
		resetSelectedData_WhenDataChanged();
	}

	@Override
	public String[] getHiddenFieldCode() {
		// TODO Auto-generated method stub
		return new String[]{
//				"pk_cubasdoc",
				"pk_accbank",};
	}

	public String getCustPK() {
		return custPK;
	}


	@Override
	public int getDefaultFieldCount() {
		// TODO Auto-generated method stub
		return 4;
	}

	@Override
	public String getPkFieldCode() {
		// TODO Auto-generated method stub
		return "pk_accbank";
	}

	/**
	 * 参照标题
	 * 创建日期：(01-4-4 0:57:23)
	 * @return java.lang.String
	 */
	public String getRefTitle() {
		return nc.ui.ml.NCLangRes.getInstance().getStrByID("common","UC000-0001896")/*@res "开户银行"*/;
	}

	@Override
	public String getRefNameField() {
		// TODO Auto-generated method stub
		return getFieldCode()[0];
	}

	@Override
	public String[][] getFormulas() {
		// TODO Auto-generated method stub
		return new String[][]{
				{"pk_currtype","pk_currtype->getColValue(bd_currtype,currtypename,pk_currtype,pk_currtype)"}};
	}
}