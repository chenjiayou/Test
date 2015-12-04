package nc.ui.fbm.storage.status;

public interface IApproveStatus {

	/**
	 *当前登录人员有审核的权限 
	 */
	public static int IS_APPROVE_MAN = 0;
	
	/**
	 * 当前登陆人员没有审批权限
	 */
	public static int ISNOT_APPROVE_MAN = 1;
	
}
