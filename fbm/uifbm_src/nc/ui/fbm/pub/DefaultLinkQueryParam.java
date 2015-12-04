/**
 *
 */
package nc.ui.fbm.pub;

import nc.ui.pub.linkoperate.ILinkQueryData;

/**
 * <p>
 * 关联查询的参数VO
 * <p>创建人：lpf
 * <b>日期：2007-9-24
 *
 */
public class DefaultLinkQueryParam implements ILinkQueryData {
	
	private Object userObject = null;

	private String billPK = null;

	private String billType = null;
	/**
	 * 
	 */
	public DefaultLinkQueryParam() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see nc.ui.pub.linkoperate.ILinkQueryData#getBillID()
	 */
	public String getBillID() {
		// TODO Auto-generated method stub
		return billPK;
	}

	/* (non-Javadoc)
	 * @see nc.ui.pub.linkoperate.ILinkQueryData#getBillType()
	 */
	public String getBillType() {
		// TODO Auto-generated method stub
		return billType;
	}

	/* (non-Javadoc)
	 * @see nc.ui.pub.linkoperate.ILinkQueryData#getPkOrg()
	 */
	public String getPkOrg() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see nc.ui.pub.linkoperate.ILinkQueryData#getUserObject()
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

	public void setUserObject(Object userObject) {
		this.userObject = userObject;
	}

	public void setBillType(String billType) {
		this.billType = billType;
	}
}
