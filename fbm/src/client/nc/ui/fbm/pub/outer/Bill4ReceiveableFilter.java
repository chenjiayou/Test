package nc.ui.fbm.pub.outer;

import nc.ui.pub.ClientEnvironment;
import nc.ui.pub.bill.BillItem;
import nc.ui.tm.framework.ref.filter.BillItemRefModelFilter;
import nc.vo.fbm.pub.constant.FbmBusConstant;
import nc.vo.fbm.pub.constant.FbmStatusConstant;

public class Bill4ReceiveableFilter extends BillItemRefModelFilter {

	public Bill4ReceiveableFilter(BillItem billitem) {
		super(billitem);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected String getSelfFilterString() {
		// TODO Auto-generated method stub
		String pk_corp = ClientEnvironment.getInstance().getCorporation().getPk_corp();
		StringBuffer sb = new StringBuffer();
		sb.append(" exists(select pk_register from fbm_register ");
		sb.append(" where fbm_register.pk_baseinfo = fbm_baseinfo.pk_baseinfo ");
		sb.append(" and isnull(fbm_register.dr,0)=0 ");
		sb.append(" and fbm_register.pk_billtypecode = '" + FbmBusConstant.BILLTYPE_GATHER +"'");
		sb.append(" and fbm_register.pk_corp = '" + pk_corp + "'");
		sb.append(" and registerstatus = '" + FbmStatusConstant.REGISTER +"'");
		sb.append(" )");
		return sb.toString();
	}

}
