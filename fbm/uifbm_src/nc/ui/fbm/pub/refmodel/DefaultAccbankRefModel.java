/**
 *
 */
package nc.ui.fbm.pub.refmodel;

import java.awt.Container;

import nc.ui.tm.framework.ref.AbstractTMRefModel;

/**
 * <p>
 *
 * <p>�����ˣ�lpf
 * <b>���ڣ�2007-8-29
 *
 *
 *xwq 2009.6.11 �����Ѿ��������������ǵ����ܱ�����ģ�������ֶ����ã��ݲ�ɾ��
 */
public class DefaultAccbankRefModel extends AbstractTMRefModel {
	// ��ʾ�ֶα���
	private String[] fieldCodes = new String[] { "bankacc", "pk_currtype","bankname",  "address" };
	private String[] fieldNames = new String[]{nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm","UPPFBMComm-000045")/* @res"�����ʺ�"*/,nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm","UPPFBMComm-000037")/* @res"����"*/,nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm","UPPFBMComm-000046")/* @res"������������"*/,nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm","UPPFBMComm-000047")/* @res"���е�ַ"*/};
	private String custPK;

	/**
	 *
	 */
	public DefaultAccbankRefModel() {
		super();
	}

	/**
	 * @param con
	 */
	public DefaultAccbankRefModel(Container con) {
		super(con);
		setMatchPkWithWherePart(true);
	}

	/* (non-Javadoc)
	 * @see nc.ui.tm.framework.ref.AbstractTMRefModel#getRefNodeName()
	 */
	@Override
	public String getRefNodeName() {
		return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add","UPP3620ADD-000055")/*@res "�������в���"*/;
	}

	/* (non-Javadoc)
	 * @see nc.ui.tm.framework.ref.AbstractTMRefModel#getTableName()
	 */
	@Override
	public String getTableName() {
		return "(select bd_accbank.pk_currtype,bd_accbank.bankacc,bd_accbank.pk_accbank,bd_accbank.bankname,bd_accbank.address from bd_accbank left outer join "
				+" bd_accbank_fts ON bd_accbank.pk_accbank = bd_accbank_fts.pk_accbank AND bd_accbank_fts.pk_corp ='"+getPk_corp()+"'"
				+" left join bd_currtype on(bd_currtype.pk_currtype=bd_accbank.pk_currtype) where (bd_accbank.pk_corp='" + getPk_corp()+ "' "
				+" or (bd_accbank.pk_corp= '" + getGroupCode()+ "' and ("
				+" (bd_accbank.pk_accbank in(select pk_accbank from bd_accbank_auth where pk_corp='"+ getPk_corp()+"' and isnull(dr,0)=0 and frozenflag='N')) )))) accbank ";
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
				"pk_accbank"};
	}

	public String getCustPK() {
		return custPK;
	}

	@Override
	public String getRefTitle() {
		// TODO Auto-generated method stub
		return null;
	}

//	@Override
//	public String getWherePart() {
//		// TODO Auto-generated method stub
//		String strWhere = "";
//
//		if (!CommonUtil.isNull(getCustPK())) {
//			strWhere = " pk_accbank in(select distinct pk_accbank from bd_custbank where pk_cubasdoc ='"+getCustPK()+"')";;
//		}
//
//		return strWhere;
//	}










}