package nc.ui.fbm.pub.refmodel;

import java.util.Vector;

import nc.ui.bd.ref.AbstractRefModel;
import nc.ui.fbm.pub.ComBoxUtil;
import nc.ui.pub.beans.constenum.DefaultConstEnum;
import nc.vo.fbm.discount.DiscountVO;
/**
 *
 ***********************************************************
 * ���ڣ�2008-3-26
 * ����Ա:���ɽ
 * ���ܣ�Ʊ��������
 ***********************************************************
 */
public class OpBillTypeRefModel extends AbstractRefModel {
	private static final String NAME = "TYPENAME";
	private static final String pklFieldCode = DiscountVO.OPBILLTYPE;//Ʊ�����
	private String[] fieldcode = {NAME};
	private String[] fieldname = {nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("36201020","UPT36201020-000074")/*@res "Ʊ�����"*/};
	/**
	 *
	 */
	public OpBillTypeRefModel() {
		super();
		setDefaultFieldCount(1);

	}

	@Override
	public Vector getData() {
		Vector<Vector<Object>> v = new Vector<Vector<Object>>();
		DefaultConstEnum[] consts = ComBoxUtil.getOpBillType();
		for (int i = 0; i < consts.length; i++) {
			Vector<Object> vect = new Vector<Object>();
			vect.add(consts[i].getName());
			vect.add(consts[i].getValue());
			v.add(vect);
		}
		setData(v);
		return v;
	}

	@Override
	public String[] getFieldCode() {
		return fieldcode;
	}

	@Override
	public String[] getFieldName() {
		return fieldname;
	}

	@Override
	public String getRefCodeField() {
		return pklFieldCode;
	}

	@Override
	public String getRefNameField() {
		return NAME;
	}

	@Override
	public String getRefShowNameField() {
		return NAME;
	}

	@Override
	public String getRefTitle() {
		return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("36201020","UPT36201020-000074")/*@res "Ʊ�����"*/;
	}

	@Override
	public String getPkFieldCode() {
		return pklFieldCode;
	}

	@Override
	public String[] getHiddenFieldCode() {
		return new String[]{pklFieldCode};
	}

}