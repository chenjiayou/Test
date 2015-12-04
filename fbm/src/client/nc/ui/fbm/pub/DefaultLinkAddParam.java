/**
 *
 */
package nc.ui.fbm.pub;

import nc.ui.pub.linkoperate.ILinkAddData;

/**
 * <p>
 * 关联新增的参数VO
 * <p>创建人：lpf
 * <b>日期：2007-9-18
 *
 */
public class DefaultLinkAddParam implements ILinkAddData {
	
	private Object userObject = null;

	private String billPK = null;

	private String billType = null;
	
	private FBMManageUI ui = null;
	/**
	 * 
	 */
	public DefaultLinkAddParam() {
		// TODO Auto-generated constructor stub
	}
	
	public DefaultLinkAddParam(Object userObject) {
		super();
		this.userObject = userObject;
	}
	
	/* (non-Javadoc)
	 * @see nc.ui.pub.linkoperate.ILinkAddData#getSourceBillID()
	 */
	public String getSourceBillID() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see nc.ui.pub.linkoperate.ILinkAddData#getSourceBillType()
	 */
	public String getSourceBillType() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see nc.ui.pub.linkoperate.ILinkAddData#getSourcePkOrg()
	 */
	public String getSourcePkOrg() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see nc.ui.pub.linkoperate.ILinkAddData#getUserObject()
	 */
	public Object getUserObject() {
		// TODO Auto-generated method stub
		return userObject;
	}

	public String getBillPK() {
		return billPK;
	}

	public void setBillPK(String billPK) {
		this.billPK = billPK;
	}

	public String getBillType() {
		return billType;
	}

	public void setBillType(String billType) {
		this.billType = billType;
	}

	public void setUserObject(Object userObject) {
		this.userObject = userObject;
	}

	public FBMManageUI getUi() {
		return ui;
	}

	public void setUi(FBMManageUI ui) {
		this.ui = ui;
	}
	
	
}
