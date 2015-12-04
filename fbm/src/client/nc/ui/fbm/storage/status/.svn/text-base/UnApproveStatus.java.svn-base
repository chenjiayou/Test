/**
 * 
 */
package nc.ui.fbm.storage.status;

import nc.ui.trade.base.AbstractBillUI;
import nc.ui.trade.buffer.BillUIBuffer;
import nc.vo.engine.status.AbstractBillUIStatus;
import nc.vo.pub.CircularlyAccessibleValueObject;

/**
 * 
 * <p>
 * 
 * <p>
 * 创建人：lpf <b>日期：2007-10-11
 * 
 */
public class UnApproveStatus extends AbstractBillUIStatus<Integer> {

	public UnApproveStatus(AbstractBillUI ui) {
		super(ui);
		// TODO Auto-generated constructor stub
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nc.vo.engine.status.IStatus#getStatus()
	 */
	public Integer getStatus() throws Exception {

		BillUIBuffer buffer = getUI().getBufferData();
		Integer ret = IUnApproveStatus.ISNOT_APPROVE_MAN;

		if (buffer.getCurrentVO() != null) {
			CircularlyAccessibleValueObject dataVo = buffer.getCurrentVO().getParentVO();
			if (dataVo != null) {
				// String approveMan = (String)
				// dataVo.getAttributeValue(getUI().getBillField().getField_CheckMan());
				// if (!CommonUtil.isNull(approveMan) &&
				// ClientInfo.getUserPK().equals(approveMan)) {
				ret = IUnApproveStatus.IS_APPROVE_MAN;
				// }
			}
		}

		return ret;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nc.vo.engine.status.IStatus#getStatusKindName()
	 */
	public String getStatusKindName() {
		return IUnApproveStatus.class.getName();
	}

}
