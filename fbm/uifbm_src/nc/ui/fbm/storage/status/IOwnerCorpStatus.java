/**
 *
 */
package nc.ui.fbm.storage.status;

/**
 * <p>
 * 判断数据是否是当前公司数据
 * <p>创建人：lpf
 * <b>日期：2007-10-11
 *
 */
public interface IOwnerCorpStatus {
	/**
	 * 制单公司是当前公司
	 */
	public static final int IS_OWNER_CORP = 1;
	
	/**
	 * 制单公司不是当前公司
	 */
	public static final int ISNOT_OWNER_CORP = 2;
}
