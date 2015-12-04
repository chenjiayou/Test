/**
 *
 */
package nc.ui.fbm.gather.reffilter;

import nc.ui.pub.bill.BillItem;
import nc.ui.tm.framework.ref.filter.BillItemRefModelFilter;
import nc.vo.fbm.register.RegisterVO;

/**
 * <p>
 * 过滤掉参照中手工录入的数据
 * <p>创建人：lpf
 * <b>日期：2007-12-11
 *
 */
public class BankdocFieldFilter extends BillItemRefModelFilter {

	/**
	 * @param billitem
	 */
	private BillItem accItem;
 
	public BankdocFieldFilter(BillItem billitem,BillItem accItem) {
		super(billitem);
		
		this.accItem = accItem;
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see nc.ui.tm.framework.ref.filter.AbstractRefModelFilter#getSelfFilterString()
	 */
	@Override
	protected String getSelfFilterString() {

		if(accItem.getValueObject()!=null ){
			
			return "pk_bankdoc in (select pk_bankdoc from bd_bankaccbas where  pk_bankaccbas ='"+ accItem.getValueObject() +"' and pk_banktype is not null)";
		}
		else{
			
			return null;	
		}
		
	}

}
