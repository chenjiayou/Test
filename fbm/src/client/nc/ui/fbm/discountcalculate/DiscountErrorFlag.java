/**
 * 
 */
package nc.ui.fbm.discountcalculate;

/**
 * <p>
 * 
 * <p>
 * �������ڣ�2006-9-8
 * 
 * @author lisg
 * @since v5.0
 */
public interface DiscountErrorFlag {
	/**
	 * ��������С�ڳ�Ʊ����
	 */
	public static final int ERROR_TXRQ_LT_CPRQ = 0; 
	/**
	 * ��������С����Ч����
	 */
	public static final int ERROR_DQRQ_LT_QXRQ = 1;
	/**
	 * ��������С����Ч����
	 */
	public static final int ERROR_TXRQ_LT_QXRQ = 2;
	/**
	 * ��Ч���ڲ�����
	 */
	public static final int ERROR_QXRQ_NOT_EXIST = 3;
}
