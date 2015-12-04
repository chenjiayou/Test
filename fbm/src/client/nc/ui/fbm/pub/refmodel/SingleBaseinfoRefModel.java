/**
 *
 */
package nc.ui.fbm.pub.refmodel;

import java.awt.Container;

import nc.vo.fbm.register.RegisterVO;

/**
 * <p>
 * Ʊ�ݻ�����Ϣ���գ���������Ʊ/��Ʊ��Ϣ���ṩ����Ʊ����ڵ�ʹ��
 * <p>�����ˣ�lpf
 * <b>���ڣ�2007-9-4
 *
 */
public class SingleBaseinfoRefModel extends InvoiceRefModel {

	/**
	 *
	 */
	public SingleBaseinfoRefModel() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param con
	 */
	public SingleBaseinfoRefModel(Container con) {
		super(con);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see nc.ui.tm.framework.ref.AbstractTMRefModel#getRefTitle()
	 */
	@Override
	public String getRefTitle() {
		// TODO Auto-generated method stub
		return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add","UPT36201510-000041")/*@res "Ʊ�ݻ�����Ϣ"*/;
	}

	@Override
	public String getPkFieldCode() {
		// TODO Auto-generated method stub
		return "pk_baseinfo";
	}

	@Override
	public String[] getFieldCode() {
		// TODO Auto-generated method stub
		return new String[]{"fbmbillno","pk_curr"};
	}

	@Override
	public String[] getFieldName() {
		// TODO Auto-generated method stub
		return new String[]{nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm","UPPFBMComm-000036")/* @res"Ʊ�ݱ��"*/,nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm","UPPFBMComm-000037")/* @res"����"*/};
	}

	@Override
	public String[] getHiddenFieldCode() {
		// TODO Auto-generated method stub
		return new String[] { RegisterVO.PK_BASEINFO,RegisterVO.ACCEPTANCENO,RegisterVO.CONTRACTNO, RegisterVO.ENDDATE,
				RegisterVO.INVOICEDATE,RegisterVO.INVOICEUNIT, RegisterVO.MONEYY,RegisterVO.PAYBANKACC,
				RegisterVO.FBMBILLTYPE,RegisterVO.PAYUNIT,RegisterVO.RECEIVEBANKACC,RegisterVO.RECEIVEUNIT,RegisterVO.ORIENTATION,RegisterVO.REGISTERSTATUS,RegisterVO.PAYBANK,RegisterVO.RECEIVEBANK};
	}
	
	@Override
	public int getDefaultFieldCount() {
		return 2;
	}
	
	public boolean isCacheEnabled() {
		return false;
	}
}