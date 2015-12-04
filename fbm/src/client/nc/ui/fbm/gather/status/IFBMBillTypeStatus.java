/**
 *
 */
package nc.ui.fbm.gather.status;

/**
 * <p>
 * 扩展状态
 * <p>创建人：lpf
 * <b>日期：2007-9-17
 *
 */
public interface IFBMBillTypeStatus {
	public static int STATUS_ALL = 0;//所有
	public static int STATUS_NULL = 1;
	public static int STATUS_REGISTERD = 2;//已登记
	public static int STATUS_BANKKEEPED = 3;//银行存放
	public static int STATUS_INVOICED = 4;//已开票
	public static int STATUS_DESTROYED = 5;//已核销
	public static int STATUS_RETURNED=6;//已退票
	public static int STATUA_DISABLE=7;//已作废
	public static int STATUA_ENDORE=8;//已背书
	public static int STATUS_PAYBILL = 9;//已付票
}
