/**
 *
 */
package nc.ui.fbm.pub.refmodel;

import java.util.Vector;

import nc.ui.bd.ref.AbstractRefModel;
import nc.ui.fbm.pub.ComBoxUtil;
import nc.ui.pub.beans.constenum.DefaultConstEnum;
import nc.vo.fbm.storage.StorageVO;

/**
 * @author LPF
 * 内部托管入库方式参照，作为影响因素提供给科目分类定义使用
 */
public class StorageInputRefModel extends AbstractRefModel {
//	private static final String INPUTTYPECODE_NAME = "存放方式编码";
	private static final String INPUTTYPE_NAME = nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm","UPPFBMComm-000071")/* @res"存放方式"*/;
	private static final String NAME = "TYPENAME";
	private static final String pklFieldCode = StorageVO.INPUTTYPE;
	private String[] fieldcode = {NAME};
	private String[] fieldname = {INPUTTYPE_NAME};
	/**
	 *
	 */
	public StorageInputRefModel() {
		super();

	}

	@Override
	public Vector getData() {
		Vector<Vector<Object>> v = new Vector<Vector<Object>>();
		DefaultConstEnum[] consts = ComBoxUtil.getStorageKeepType();
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