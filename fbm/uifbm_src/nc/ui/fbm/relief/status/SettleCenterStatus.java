/**
 *
 */
package nc.ui.fbm.relief.status;

import nc.itf.cdm.util.CommonUtil;
import nc.ui.fbm.pub.FBMClientInfo;
import nc.ui.fbm.relief.ReliefUI;
import nc.ui.fbm.storage.status.ISettleCenterStatus;
import nc.ui.trade.base.AbstractBillUI;
import nc.vo.engine.status.AbstractBillUIStatus;

/**
 * 
 * 类功能说明： 中心判断状态机,判断是为中心还是结算单位 日期：2007-11-27 程序员： wues
 * 
 */
public class SettleCenterStatus extends AbstractBillUIStatus<Integer> {

	private static boolean isCenter = false;
	
	/**
	 * @param ui
	 */
	public SettleCenterStatus(AbstractBillUI ui) {
		super(ui);
		setOwnValue();
	}

	private void setOwnValue() {
		String pk_corp = getUI()._getCorp().getPrimaryKey();
		if (CommonUtil.isNull(pk_corp)) {
			isCenter = false;
		}
		isCenter = FBMClientInfo.isSettleCenter(pk_corp);
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see nc.vo.engine.status.IStatus#getStatus()
	 */
	public Integer getStatus() throws Exception {
		Integer retStatus = ISettleCenterStatus.ISSETTLE_STATUS;
		if (isCenter) {
			retStatus = ISettleCenterStatus.ISCENTER_STATUS;
		}
		return retStatus;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nc.vo.engine.status.IStatus#getStatusKindName()
	 */
	public String getStatusKindName() {
		return ISettleCenterStatus.class.getName();
	}

	@Override
	public ReliefUI getUI() {
		// TODO Auto-generated method stub
		return (ReliefUI) super.getUI();
	}

}
