/**
 *
 */
package nc.ui.fbm.storage.status;

/**
 * <p>
 * 结算中心是否可以业务操作
 * <p>创建人：lpf
 * <b>日期：2007-11-29
 *
 */
public interface ISettleCenterBillStatus {
	/**
	 * 录入单据是下级单位的
	 */
	public static final int IS_SETTLEUNIT_BILL = 1;
	
	/**
	 * 录入单据是自己提交的
	 */
	public static final int IS_OWN_BILL = 2;
}
