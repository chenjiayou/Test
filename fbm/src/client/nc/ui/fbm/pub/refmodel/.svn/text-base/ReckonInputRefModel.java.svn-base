package nc.ui.fbm.pub.refmodel;

import java.util.Vector;

import nc.ui.bd.ref.AbstractRefModel;
import nc.ui.fbm.pub.ComBoxUtil;
import nc.ui.pub.beans.constenum.DefaultConstEnum;
import nc.vo.fbm.reckon.ReckonBVO;

/**
 *
 * 类功能说明：
     调剂清算的调剂方向
 * 日期：2007-12-14
 * 程序员： wues
 *
 */
public class ReckonInputRefModel extends AbstractRefModel {
	private static final String INPUTTYPE_NAME = nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm","UPPFBMComm-000067")/* @res"调剂方向"*/;
	private static final String NAME = "TYPENAME";
	private static final String pklFieldCode = ReckonBVO.DIRECTION;//清算方向
	private String[] fieldcode = {NAME};
	private String[] fieldname = {INPUTTYPE_NAME};
	/**
	 *
	 */
	public ReckonInputRefModel() {
		super();

	}

	@Override
	public Vector getData() {
		Vector<Vector<Object>> v = new Vector<Vector<Object>>();
		DefaultConstEnum[] consts = ComBoxUtil.getReckonDirection();
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
		return INPUTTYPE_NAME;
	}

	@Override
	public String getPkFieldCode() {
		return pklFieldCode;
	}
//
//	@Override
//	public int[] getShownColumns() {
//		return new int[]{0};
//	}

	@Override
	public String[] getHiddenFieldCode() {
		return new String[]{pklFieldCode};
	}

}