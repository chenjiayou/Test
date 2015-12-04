package nc.ui.fbm.relief.status;

import nc.ui.fbm.pub.FBMManageUI;
import nc.ui.trade.base.AbstractBillUI;
import nc.vo.engine.status.AbstractBillUIStatus;
/**
 * 
 * 类功能说明：
     //去掉取消出库按钮，不用加此状态进行控制
 * 日期：2007-11-27
 * 程序员： wues
 *
 */
public class ReliefStatus extends AbstractBillUIStatus<Integer> {

	/**
	 * @param ui
	 */
	public ReliefStatus(AbstractBillUI ui) {
		super(ui);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see nc.vo.engine.status.IStatus#getStatus()
	 */
	public Integer getStatus() throws Exception {
//		Integer retStatus = IReliefStatus.NOT_OUT;
//		AggregatedValueObject currVo = getUI().getBufferData().getCurrentVO();
//		if (null != currVo && null != currVo.getParentVO()) {
//			UFBoolean isout = ((ReliefVO)currVo.getParentVO()).getIsout();//得到已出库标示
//			if (UFBoolean.TRUE.equals(isout)) {//说明已出库
//				return -1;
//			}
//		}
//		return retStatus;
		return  IReliefStatus.NOT_OUT;
	}

	/* (non-Javadoc)
	 * @see nc.vo.engine.status.IStatus#getStatusKindName()
	 */
	public String getStatusKindName() {
		return IReliefStatus.class.getName();
	}

	@Override
	public FBMManageUI getUI() {
		// TODO Auto-generated method stub
		return (FBMManageUI) super.getUI();
	}
}