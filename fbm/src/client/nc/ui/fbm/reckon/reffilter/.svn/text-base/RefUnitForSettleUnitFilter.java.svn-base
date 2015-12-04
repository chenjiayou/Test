/**
 * 
 */
package nc.ui.fbm.reckon.reffilter;

import nc.ui.pub.bill.BillItem;
import nc.ui.tm.framework.ref.filter.BillItemRefModelFilter;
import nc.ui.tm.framework.util.ClientInfo;
import nc.ui.trade.base.AbstractBillUI;
import nc.vo.bd.settle.SettlecenterVO;
import nc.vo.fbm.proxy.OuterProxy;

/**
 * @author lpf
 *
 */
public class RefUnitForSettleUnitFilter extends BillItemRefModelFilter {

	/**
	 * 
	 */
	public RefUnitForSettleUnitFilter() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param billitem
	 */
	public RefUnitForSettleUnitFilter(BillItem billitem) {
		super(billitem);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ui
	 */
	public RefUnitForSettleUnitFilter(AbstractBillUI ui) {
		super(ui);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see nc.ui.tm.framework.ref.filter.AbstractRefModelFilter#getSelfFilterString()
	 */
	@Override
	protected String getSelfFilterString() {
		SettlecenterVO settleCenterByCorpPk = OuterProxy.getSettleCenter()
				.getSettleCenterByCorpPk(ClientInfo.getCorpPK());
		return " pk_corp1 in(select pk_corp1 from bd_settleunit where pk_settlecent = '"+settleCenterByCorpPk.getPrimaryKey()+"') ";
	}

}
