/**
 *
 */
package nc.ui.fbm.invoice.status;

/**
 * <p>
 * 开票是否核销状态定义
 * <p>创建人：lpf
 * <b>日期：2007-11-8
 *
 */
public interface IsBillDestroyed {
	public static int STATUS_DESTROYED = 1; //核销
	public static int STATUS_NOT_DESTROYED = 2; //没有核销
}
