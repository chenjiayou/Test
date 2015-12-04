/**
 *
 */
package nc.ui.fbm.accept.reffilter;

import nc.itf.cdm.util.CommonUtil;
import nc.ui.fbm.pub.FBMManageUI;
import nc.ui.pub.ClientEnvironment;
import nc.ui.pub.bill.BillItem;
import nc.ui.tm.framework.ref.filter.BillItemRefModelFilter;
import nc.ui.trade.base.IBillOperate;
import nc.vo.fbm.acceptbill.AcceptVO;
import nc.vo.fbm.pub.constant.FbmStatusConstant;
import nc.vo.pub.AggregatedValueObject;

/**
 * <p>
 * 票据承兑票据编号参照过滤器
 * <p>创建人：lpf
 * <b>日期：2007-9-20
 *
 */
public class AcceptSourcePKRefModelFilter extends BillItemRefModelFilter {
	private FBMManageUI ui;
	/**
	 * @param billitem
	 */
	public AcceptSourcePKRefModelFilter(BillItem billitem) {
		super(billitem);
		// TODO Auto-generated constructor stub
	}

	public AcceptSourcePKRefModelFilter( FBMManageUI ui,BillItem billitem) {
		super(billitem);
		this.ui = ui;
	}

	/* (non-Javadoc)
	 * @see nc.ui.tm.framework.ref.filter.AbstractRefModelFilter#getSelfFilterString()
	 */
	@Override
	protected String getSelfFilterString() {
		String sql = " registerstatus='"+FbmStatusConstant.HAS_PAYBILL+"' and pk_corp = '"+ClientEnvironment.getInstance().getCorporation().getPk_corp()+"' ";
		AggregatedValueObject vo = ui.getBufferData().getCurrentVO();
		if(vo!=null&&vo.getParentVO()!=null&&IBillOperate.OP_EDIT==ui.getBillOperate()){
			String pk_source = (String) vo.getParentVO().getAttributeValue(AcceptVO.PK_SOURCE);
			if (!CommonUtil.isNull(pk_source)) {
				sql = sql + " or (pk_register='" + pk_source + "')";
			}
		}
		return "("+sql+")";
	}

}
