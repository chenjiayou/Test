package nc.ui.fbm.pub.refmodel;

import java.util.Vector;

import nc.ui.bd.ref.AbstractRefModel;
import nc.ui.pub.beans.constenum.DefaultConstEnum;
import nc.vo.fbm.register.RegisterVO;

public class QEArApRefModel extends AbstractRefModel {
	private static final String NAME = "KEY";
	private static final String pklFieldCode = RegisterVO.ORIENTATION;//票据应收应付
	private String[] fieldcode = {NAME};
	private String[] fieldname = {"票据应收应付类别"};
	/**
	 *
	 */
	public QEArApRefModel() {
		super();
		setDefaultFieldCount(1);

	}

	@Override
	public Vector getData() {
		Vector<Vector<Object>> v = new Vector<Vector<Object>>();
		DefaultConstEnum[] consts =  new DefaultConstEnum[] {
				new DefaultConstEnum("ar","应收票据"),new DefaultConstEnum("ap","应付票据")};
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
		return "票据应收应付类别";
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
