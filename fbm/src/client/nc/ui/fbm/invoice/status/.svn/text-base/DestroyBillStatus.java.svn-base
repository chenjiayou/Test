/**
 *
 */
package nc.ui.fbm.invoice.status;

import nc.ui.fbm.pub.FBMManageUI;
import nc.ui.trade.base.AbstractBillUI;
import nc.vo.engine.status.AbstractBillUIStatus;
import nc.vo.fbm.register.RegisterVO;
import nc.vo.pub.lang.UFBoolean;

/**
 * <p>
 * 是否核销
 * <p>创建人：lpf
 * <b>日期：2007-11-8
 *
 */
public class DestroyBillStatus extends AbstractBillUIStatus<Integer> {

	/**
	 * @param ui
	 */
	public DestroyBillStatus(AbstractBillUI ui) {
		super(ui);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see nc.vo.engine.status.IStatus#getStatus()
	 */
	public Integer getStatus() throws Exception {
		Object verifyobj = getManageUI().getBillCardPanel().getHeadItem(RegisterVO.ISVERIFY).getValueObject();
		if(verifyobj!=null&&!new UFBoolean((String)verifyobj).booleanValue())
			return IsBillDestroyed.STATUS_NOT_DESTROYED;
		
		return IsBillDestroyed.STATUS_DESTROYED;
	}

	/* (non-Javadoc)
	 * @see nc.vo.engine.status.IStatus#getStatusKindName()
	 */
	public String getStatusKindName() {
		return IsBillDestroyed.class.getName();
	}

	private FBMManageUI getManageUI() {
		return (FBMManageUI) getUI();
	}
}
