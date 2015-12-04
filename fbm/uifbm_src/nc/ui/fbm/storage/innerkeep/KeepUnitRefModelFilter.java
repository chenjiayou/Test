/**
 *
 */
package nc.ui.fbm.storage.innerkeep;

import nc.ui.fbm.pub.FBMManageUI;
import nc.ui.pub.ClientEnvironment;
import nc.ui.pub.bill.BillItem;
import nc.vo.fbm.storage.StoragePowerVO;

/**
 * <p>
 * 
 * <p>
 * 创建人：lpf <b>日期：2007-11-28
 * 
 */
public class KeepUnitRefModelFilter extends
		nc.ui.tm.framework.ref.filter.BillItemRefModelFilter {
	private FBMManageUI ui;

	public KeepUnitRefModelFilter(BillItem billitem) {
		super(billitem);
		// TODO Auto-generated constructor stub
	}

	public KeepUnitRefModelFilter(BillItem billitem, FBMManageUI ui) {
		super(billitem);
		this.ui = ui;
	}

	@Override
	protected String getSelfFilterString() {
		String sqlfilter = null;
		StoragePowerVO power = ((InnerKeepUI) ui).getPower();
		String pk_cubasdoc = power.getPk_cubasdoc();
		if (power.isSettleCenter()) {
			sqlfilter = SubCustDocCondition.getCusDocFilterContidtion();
			if (power.isSettleUnit()) {
				sqlfilter = sqlfilter
						+ " or bd_cubasdoc.pk_cubasdoc='"
						+ pk_cubasdoc
						+ "'";
				// 内部托管单，托管单位应该把结算中心对应的客商过滤掉 NCdp201012051
				sqlfilter = sqlfilter
						+ " and bd_cubasdoc.pk_corp1 <> '"
						+ ClientEnvironment.getInstance().getCorporation().getPk_corp()
						+ "'";
			}
			
		} else {
			sqlfilter = " bd_cubasdoc.pk_cubasdoc='" + pk_cubasdoc + "' ";
		}
		return "(" + sqlfilter + ") and custprop<>0 ";
	}
}
