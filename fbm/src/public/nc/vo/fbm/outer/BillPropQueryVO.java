package nc.vo.fbm.outer;

/**
 * 
 * <p>
 *   收付款单查询票据属性的参数对象
 * </p>
 * @author xwq
 * @date 2007-9-12
 * @version V5.0
 */
public class BillPropQueryVO {
	
	private String billno;//票据编号
	
	private String pk_corp;//公司

	public String getBillno() {
		return billno;
	}

	public void setBillno(String billno) {
		this.billno = billno;
	}

	public String getPk_corp() {
		return pk_corp;
	}

	public void setPk_corp(String pk_corp) {
		this.pk_corp = pk_corp;
	}
	
	
}
