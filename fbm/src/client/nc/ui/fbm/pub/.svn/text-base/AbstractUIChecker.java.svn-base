/**
 *
 */
package nc.ui.fbm.pub;


/**
 * <p>
 *
 * <p>创建人：lpf
 * <b>日期：2007-8-16
 *
 */
public abstract class AbstractUIChecker implements IUIChecker {
	private FBMManageUI ui;
	/**
	 * 
	 */
	public AbstractUIChecker() {
		// TODO Auto-generated constructor stub
	}
	
	

	public AbstractUIChecker(FBMManageUI ui) {
		super();
		this.ui = ui;
	}

	/**
	 * 校验类必须实现的方法
	 * @return String 错误信息/校验成功则返回null
	 */
	public abstract String check();
	
	protected FBMManageUI getUI(){
		return ui;
	}

	
	/**
	 * <p>
	 * 判断为空。
	 * <p>
	 * 作者：qbh <br>
	 * 日期：2005-12-8
	 * 
	 * @param o
	 * @return
	 */
	public static boolean isNULL(Object o) {
		if (o == null || o.toString().trim().length()==0) {
			return true;
		}
		return false;
	}
	
}
