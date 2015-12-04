package nc.ui.fbm.storage.status;

import java.util.List;

import nc.ui.pub.ClientEnvironment;
import nc.ui.trade.base.AbstractBillUI;
import nc.vo.bd.b203.SettleunitHeaderVO;
import nc.vo.bd.settle.SettlecenterVO;
import nc.vo.engine.status.AbstractBillUIStatus;
import nc.vo.fbm.pub.FBMProxy;
import nc.vo.fts.pub.CenterUnitUtil;
import nc.vo.pub.SuperVO;
import nc.vo.pub.lang.UFBoolean;

/**
 * 结算中心专用按钮，判断单据是否属于下级成员单位业务单据
 * 
 * @author xwq
 * 
 *         2008-8-2
 */
public class UnitBillStatus extends AbstractBillUIStatus<Integer> {

	public static int BTN_DISABLE = 0;

	public static int UNIT_BILL = 1;

	private String billcorpKey;

	public UnitBillStatus(AbstractBillUI ui, String billcorpKey) {
		super(ui);
		this.billcorpKey = billcorpKey;
	}

	public Integer getStatus() throws Exception {
		// TODO Auto-generated method stub
		if (getUI().getBufferData().getCurrentVO() == null) {
			return BTN_DISABLE;
		}
		SuperVO superVO = (SuperVO) getUI().getBufferData().getCurrentVO().getParentVO();
		String loginCorp = ClientEnvironment.getInstance().getCorporation().getPk_corp();
		// ISettleCenter centerSrv =
		// (ISettleCenter)NCLocator.getInstance().lookup(ISettleCenter.class.getName());
		// SettlecenterVO centerVO =
		// centerSrv.getSettleCenterByCorpPk(loginCorp);
		SettlecenterVO centerVO = CenterUnitUtil.instance.getSettleCenterVOByCorpPK(loginCorp);
		if (centerVO == null) {
			return BTN_DISABLE;
		}

		// 如果有后续的制证和记账操作则不能使用
		UFBoolean unittally = (UFBoolean) superVO.getAttributeValue("unittally");
		UFBoolean unitvoucher = (UFBoolean) superVO.getAttributeValue("unitvoucher");
		UFBoolean centervoucher = (UFBoolean) superVO.getAttributeValue("centervoucher");
		if (unittally.booleanValue()
				|| unitvoucher.booleanValue()
				|| centervoucher.booleanValue()) {
			return BTN_DISABLE;
		}

		String strWhere = "select * from bd_settleunit where pk_settlecent='"
				+ centerVO.getPrimaryKey()
				+ "'";
		// IUifService srv = NCLocator.getInstance().lookup(IUifService.class);
		// SettleunitHeaderVO[] units = (SettleunitHeaderVO[])
		// srv.queryByCondition(SettleunitHeaderVO.class, strWhere);
		List unitslist = FBMProxy.queryListVO(strWhere, SettleunitHeaderVO.class);
		SettleunitHeaderVO[] units = (SettleunitHeaderVO[]) unitslist.toArray(new SettleunitHeaderVO[] {});
		if (units == null) {
			return BTN_DISABLE;
		}

		for (int i = 0; i < units.length; i++) {
			if (units[i].getPk_corp1().equals(superVO.getAttributeValue(billcorpKey))) {
				return UNIT_BILL;
			}
		}

		return BTN_DISABLE;
	}

	public String getStatusKindName() {
		// TODO Auto-generated method stub
		return UnitBillStatus.class.getName();
	}

}
