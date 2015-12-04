/**
 *
 */
package nc.ui.fbm.storage.innerback;

import nc.ui.fbm.pub.AbstractUIChecker;
import nc.ui.fbm.pub.FBMManageUI;

/**
 * <p>
 *  内部存放、领用日期校验
 * <p>创建人：lpf
 * <b>日期：2007-11-29
 *
 */
public class InnerKeepDateChecker extends AbstractUIChecker {
	String itemkey;
	/**
	 * 
	 */
	public InnerKeepDateChecker() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ui
	 */
	public InnerKeepDateChecker(FBMManageUI ui) {
		super(ui);
		// TODO Auto-generated constructor stub
	}

	
	public InnerKeepDateChecker(FBMManageUI ui, String itemkey) {
		super(ui);
		this.itemkey = itemkey;
	}

	/* (non-Javadoc)
	 * @see nc.ui.fbm.pub.AbstractUIChecker#check()
	 */
	@Override
	public String check() {
		
		return null;
	}

}
