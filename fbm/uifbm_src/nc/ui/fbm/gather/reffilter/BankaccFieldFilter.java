/**
 *
 */
package nc.ui.fbm.gather.reffilter;

import nc.ui.pub.bill.BillItem;
import nc.ui.tm.framework.ref.filter.BillItemRefModelFilter;
import nc.vo.fbm.register.RegisterVO;

/**
 * <p>
 * ���˵��������ֹ�¼�������
 * <p>�����ˣ�lpf
 * <b>���ڣ�2007-12-11
 *
 */
public class BankaccFieldFilter extends BillItemRefModelFilter {

	/**
	 * @param billitem
	 */
	private BillItem bankdocItem;
	public BankaccFieldFilter(BillItem bankdocItem) {
		super(bankdocItem);
		this.bankdocItem=bankdocItem;
		
		// TODO Auto-generated constructor stub
	}
		/* (non-Javadoc)
	 * @see nc.ui.tm.framework.ref.filter.AbstractRefModelFilter#getSelfFilterString()
	 */
	@Override
	protected String getSelfFilterString() {

		if(billitem.getValueObject()!=null){
			
			return " pk_bankdoc ='"+ bankdocItem.getValueObject() +"' and pk_banktype is not null";
		}
		else{
			
			return null;	
		}
		
	}

}
