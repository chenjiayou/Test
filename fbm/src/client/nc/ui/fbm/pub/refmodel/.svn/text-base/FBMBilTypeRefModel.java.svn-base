/**
 * 
 */
package nc.ui.fbm.pub.refmodel;

import java.awt.Container;

import nc.ui.tm.framework.ref.AbstractTMRefModel;

/**
 * @author wangjing5
 *
 */
public class FBMBilTypeRefModel extends AbstractTMRefModel {
    //显示字段编码
	private String[] fieldNames = new String[]{"编码","名称"};
	private String[] fieldCodes = new String[]{"notetypecode","notetypename"};
	/**
	 * 
	 */
	public FBMBilTypeRefModel() {
	  super();
	  setWherePart(" noteclass = 1 ");
	  setDefaultFieldCount(2);
	}

	/**
	 * @param con
	 */
	public FBMBilTypeRefModel(Container con) {
		super(con);
		setWherePart(" noteclass = 1 ");
		setDefaultFieldCount(2);
	}

	@Override
	public String getRefNodeName() {
		return "票据类型参照";
	}

	@Override
	public String getRefTitle() {
		return "票据类型";
	}

	@Override
	public String getTableName() {
		return "bd_notetype";
	}

	@Override
	public String[] getFieldCode() {
		return fieldCodes;
	}

	@Override
	public String[] getFieldName() {
		return fieldNames;
	}

	@Override
	public String[] getHiddenFieldCode() {
		return new String[]{"pk_notetype"};
	}

	@Override
	public String getPkFieldCode() {
		return "pk_notetype";
	}

	@Override
	public int getDefaultFieldCount() {
		return 2;
	}

}
