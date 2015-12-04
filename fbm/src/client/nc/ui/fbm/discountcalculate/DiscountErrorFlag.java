/**
 * 
 */
package nc.ui.fbm.discountcalculate;

/**
 * <p>
 * 
 * <p>
 * 创建日期：2006-9-8
 * 
 * @author lisg
 * @since v5.0
 */
public interface DiscountErrorFlag {
	/**
	 * 贴现日期小于出票日期
	 */
	public static final int ERROR_TXRQ_LT_CPRQ = 0; 
	/**
	 * 到期日期小于起效日期
	 */
	public static final int ERROR_DQRQ_LT_QXRQ = 1;
	/**
	 * 贴现日期小于起效日期
	 */
	public static final int ERROR_TXRQ_LT_QXRQ = 2;
	/**
	 * 起效日期不存在
	 */
	public static final int ERROR_QXRQ_NOT_EXIST = 3;
}
