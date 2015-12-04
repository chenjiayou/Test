package nc.ui.fbm.discount;

import java.io.Serializable;

/**
 * <p>
 *	贴现办理校验类
 * <p>创建人：bsrl
 * <b>日期：2007-09
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
        // TODO 自动生成构造函数存根
    }

    /* （非 Javadoc）
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
