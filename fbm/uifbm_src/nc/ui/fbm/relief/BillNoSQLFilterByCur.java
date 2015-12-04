package nc.ui.fbm.relief;

import nc.ui.fbm.pub.FBMManageUI;
import nc.ui.pub.beans.UIRefPane;
import nc.ui.pub.bill.BillItem;
import nc.ui.tm.framework.ref.filter.BillItemRefModelFilter;

/**
 * 
 * 类功能说明：
     根据币种过滤掉非同币种的待调剂的票据
 * 日期：2007-12-13
 * 程序员： wues
 *
 */
public class BillNoSQLFilterByCur extends BillItemRefModelFilter {
	private FBMManageUI ui;
	//内部账户key
	private String innerAccItemKey; 
	/**
	 * @param billitem
	 */
	public BillNoSQLFilterByCur(BillItem billitem) {
		super(billitem);
	}

	/* (non-Javadoc)
	 * @see nc.ui.tm.framework.ref.filter.AbstractRefModelFilter#getSelfFilterString()
	 */
	public BillNoSQLFilterByCur(BillItem billitem,FBMManageUI ui, String innerAccItemKey) {
		super(billitem);
		this.ui=ui;
		this.innerAccItemKey = innerAccItemKey;
	}

	@Override
	protected String getSelfFilterString() {
		String curtype = getAccCurrType();
		
		if (null == curtype || "".equals(curtype)) {
			return null;
		}
		return "  pk_curr='" + curtype + "' ";
	}
	
	/**
	 * 取账户对应币种pk
	 * @return
	 */
	private String getAccCurrType() {
		UIRefPane refpane = (UIRefPane) ui.getBillCardPanel().getHeadItem(innerAccItemKey).getComponent();
		String pk_currtype = (String) refpane.getRefValue("pk_currtype");
		return pk_currtype;
	}
}
