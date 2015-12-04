package nc.ui.fbm.pub.outer;

import nc.ui.pub.linkoperate.ILinkQueryData;

/**
 * 联查收付报接口参数
 * @author xwq
 *
 */
public class LinkSFQueryData implements ILinkQueryData{

	private String[] pk_bill_h;
	
	public LinkSFQueryData(String[] pks){
		pk_bill_h = pks;
	}
	
	public String getBillID() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getBillType() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getPkOrg() {
		// TODO Auto-generated method stub
		return null;
	}

	public Object getUserObject() {
		return new Object[]{pk_bill_h};
	}
	
}
