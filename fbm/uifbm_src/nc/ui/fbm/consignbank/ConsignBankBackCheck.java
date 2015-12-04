package nc.ui.fbm.consignbank;

import java.io.Serializable;

/**
 * <p>
 *	银行托收校验类
 * <p>创建人：bsrl
 * <b>日期：2007-09
 *
 */
public class ConsignBankBackCheck implements Serializable, nc.vo.trade.pub.IBDGetCheckClass2{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * 
     */
    public ConsignBankBackCheck() {
        super();
        // TODO 自动生成构造函数存根
    }

    /* （非 Javadoc）
     * @see nc.vo.trade.pub.IBDGetCheckClass#getCheckClass()
     */
    public String getCheckClass() {
        return "nc.bs.fbm.consignbank.ConsignBankBusCheck";
    }

	public String getUICheckClass() {
		return null;
		//"nc.ui.fbm.consignbank.ConsignBankUICheck";
	}
}
