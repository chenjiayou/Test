/**
 * 
 */
package nc.ui.fbm.storage.status;

import nc.ui.trade.base.AbstractBillUI;
import nc.ui.trade.buffer.BillUIBuffer;
import nc.vo.engine.status.AbstractBillUIStatus;
import nc.vo.fbm.storage.StorageVO;
import nc.vo.pub.CircularlyAccessibleValueObject;

/**
 * 
 * <p>
 * 
 * <p>
 * 创建人：lpf <b>日期：2007-10-11
 * 
 */
public class ApproveStatus extends AbstractBillUIStatus<Integer> {

	public ApproveStatus(AbstractBillUI ui) {
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
		Integer ret = IApproveStatus.ISNOT_APPROVE_MAN;

		if (buffer.getCurrentVO() != null) {
			CircularlyAccessibleValueObject dataVo = buffer.getCurrentVO().getParentVO();
			if (dataVo != null) {
				String pk_id = (String) dataVo.getAttributeValue(StorageVO.PK_STORAGE);
				String billtype = (String) dataVo.getAttributeValue(StorageVO.PK_BILLTYPECODE);
				// 解决性能问题，减少调用次数
				// if (OuterProxy.getPFWorkflowQry().isCheckman(pk_id,
				// billtype,ClientInfo.getUserPK()))
				// {
				ret = IApproveStatus.IS_APPROVE_MAN;
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
		return IApproveStatus.class.getName();
	}
}
