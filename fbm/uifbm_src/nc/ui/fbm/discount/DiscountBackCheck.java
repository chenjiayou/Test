package nc.ui.fbm.discount;

import java.io.Serializable;

/**
 * <p>
 *	���ְ���У����
 * <p>�����ˣ�bsrl
 * <b>���ڣ�2007-09
 *
 */
public class DiscountBackCheck implements Serializable, nc.vo.trade.pub.IBDGetCheckClass2{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * 
     */
    public DiscountBackCheck() {
        super();
        // TODO �Զ����ɹ��캯�����
    }

    /* ���� Javadoc��
     * @see nc.vo.trade.pub.IBDGetCheckClass#getCheckClass()
     */
    public String getCheckClass() {
        return "nc.bs.fbm.discount.DiscountBusCheck";
    }

	public String getUICheckClass() {
		return null;
//		"nc.ui.fbm.discount.DiscountUICheck";
	}
}