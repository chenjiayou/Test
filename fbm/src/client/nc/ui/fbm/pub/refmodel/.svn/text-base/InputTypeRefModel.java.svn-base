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
 * @author wangjing5
 *
 */
public class InputTypeRefModel extends AbstractRefModel {

	private static String INPUTTYPE_NAME = nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add","UPP3620ADD-000240")/*@res "托管方式"*/;
	private static final String NAME = "INPUTTYPE";
	private static final String pklFieldCode = StorageVO.INPUTTYPE;
	private String[] fieldcode = {NAME};
	private String[] fieldname = {INPUTTYPE_NAME};
	/**
	 * 
	 */
	public InputTypeRefModel() {
		super();
	}
	@Override
	public Vector getData() {
		// TODO 自动生成方法存根
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
