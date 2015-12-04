/**
 *
 */
package nc.ui.fbm.storage.innerback;

import java.util.ArrayList;

import nc.itf.cdm.util.CommonUtil;
import nc.ui.fbm.pub.FBMManageUI;
import nc.ui.pub.ClientEnvironment;
import nc.ui.pub.beans.UIRefPane;
import nc.ui.pub.bill.BillItem;
import nc.ui.tm.framework.ref.filter.BillItemRefModelFilter;
import nc.ui.trade.base.IBillOperate;
import nc.vo.fbm.pub.constant.FbmBusConstant;
import nc.vo.fbm.pub.constant.FbmStatusConstant;
import nc.vo.fbm.storage.StorageBVO;
import nc.vo.fbm.storage.StorageVO;
import nc.vo.pub.AggregatedValueObject;

/**
 * <p>
 * 
 * <p>
 * 创建人：lpf <b>日期：2007-10-11
 * 
 */
public class InnerBackSourceRefModelFilter extends BillItemRefModelFilter {
	private FBMManageUI ui;

	/**
	 * @param billitem
	 */
	public InnerBackSourceRefModelFilter(BillItem billitem) {
		super(billitem);
		// TODO Auto-generated constructor stub
	}

	public InnerBackSourceRefModelFilter(BillItem billitem, FBMManageUI ui) {
		super(billitem);
		this.ui = ui;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nc.ui.tm.framework.ref.filter.AbstractRefModelFilter#getSelfFilterString
	 * ()
	 */
	@Override
	protected String getSelfFilterString() {
		String pk_corp1 = null;

		String pk_cubasdoc = (String) ui.getBillCardPanel().getHeadItem(StorageVO.OUTPUTUNIT).getValueObject();
		String pk_curr = (String) ui.getBillCardPanel().getHeadItem(StorageVO.PK_CURRTYPE).getValueObject();

		String inputtype = (String) ui.getBillCardPanel().getHeadItem(StorageVO.INPUTTYPE).getValueObject();
		// String pk_account = (String)
		// ui.getBillCardPanel().getHeadItem(StorageVO.KEEPACCOUNT).getValueObject();

		if (!CommonUtil.isNull(pk_cubasdoc)) {
			UIRefPane refpane = (UIRefPane) ui.getBillCardPanel().getHeadItem(StorageVO.OUTPUTUNIT).getComponent();
			pk_corp1 = (String) refpane.getRefValue("bd_cubasdoc.pk_corp1");

			if (CommonUtil.isNull(pk_corp1)) {
				pk_corp1 = ClientEnvironment.getInstance().getCorporation().getPk_corp();
			}
		}

		String statussql = "(registerstatus='"
				+ FbmStatusConstant.HAS_INNER_KEEP
				+ "' or registerstatus='"
				+ FbmStatusConstant.HAS_RELIEF_KEEP
				+ "')";
		if (FbmBusConstant.KEEP_TYPE_STORE.equals(inputtype)) {
			statussql = "(registerstatus='"
					+ FbmStatusConstant.HAS_INNER_KEEP
					+ "')";
		} else if (FbmBusConstant.KEEP_TYPE_RELIEF.equals(inputtype)) {
			statussql = "(registerstatus='"
					+ FbmStatusConstant.HAS_RELIEF_KEEP
					+ "')";
		}
		// String pk_settlecenter =
		// FBMClientInfo.getSuperSettleCenter(pk_corp1);
		// String pk_corpsettlecenter =
		// OuterProxy.getSettleCenter().getSettleCenterByPk(pk_settlecenter).getPk_corp();

		String sql = " (pk_corp='"
				+ pk_corp1
				+ "' and "
				+ statussql
				+ " and sfflag='Y'  and sfflag='Y' and disable='N')";

		String pk_currtype = null;
		if (!CommonUtil.isNull(pk_curr)) {
			UIRefPane refpane = (UIRefPane) ui.getBillCardPanel().getHeadItem(StorageVO.PK_CURRTYPE).getComponent();
			pk_currtype = (String) refpane.getRefValue("pk_currtype");
			sql = sql + " and pk_curr='" + pk_currtype + "'";
		}

		AggregatedValueObject vo = ui.getBufferData().getCurrentVO();
		if (vo != null
				&& vo.getChildrenVO() != null
				&& IBillOperate.OP_EDIT == ui.getBillOperate()) {
			StorageBVO[] vos = (StorageBVO[]) vo.getChildrenVO();
			ArrayList<String> pks = new ArrayList<String>();
			if (!CommonUtil.isNull(vos)) {
				String pkfilter = " or ( pk_register in(";
				for (int i = 0; i < vos.length; i++) {
					String key = (String) vos[i].getAttributeValue(StorageBVO.PK_SOURCE);
					if (!CommonUtil.isNull(key) && !pks.contains(key)) {
						if (i > 0) {
							pkfilter = pkfilter + ",";
						}
						pks.add(key);
						pkfilter = pkfilter + "'" + key + "'";
					}
				}
				if (pks.size() > 0) {
					if (pk_currtype != null)
						sql = "("
								+ sql
								+ ")"
								+ pkfilter
								+ ") and pk_curr = '"
								+ pk_currtype
								+ "')";
					else
						sql = "(" + sql + ")" + pkfilter + ")";
				}
			}
		}
		return "(" + sql + ")";
	}

}
