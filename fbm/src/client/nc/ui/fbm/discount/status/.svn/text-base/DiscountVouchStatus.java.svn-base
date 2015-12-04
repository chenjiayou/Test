package nc.ui.fbm.discount.status;

import nc.ui.fbm.pub.FBMManageUI;
import nc.ui.trade.base.AbstractBillUI;
import nc.vo.engine.status.AbstractBillUIStatus;
import nc.vo.fbm.discount.DiscountVO;
import nc.vo.fbm.pub.constant.FbmBusConstant;
import nc.vo.pub.AggregatedValueObject;

/**
 * 
 ***********************************************************
 * 日期：2008-3-28							   
 * 程序员:吴二山 							   
 * 功能：通过办理成功与否控制制证按钮是否可用						   
 ***********************************************************
 */
public class DiscountVouchStatus extends AbstractBillUIStatus<Integer> {

	/**
	 * @param ui
	 */
	public DiscountVouchStatus(AbstractBillUI ui) {
		super(ui);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nc.vo.engine.status.IStatus#getStatus()
	 */
	public Integer getStatus() throws Exception {
		int failed = IDiscountStatus.FAILD;// 办理成功
		AggregatedValueObject currVo = getUI().getBufferData().getCurrentVO();
		if (null != currVo && null != currVo.getParentVO()) {
			String transResult = ((DiscountVO) currVo.getParentVO())
					.getResult();// 得到办理结果
			if (FbmBusConstant.DISCOUNT_RESULT_SUCCESS.equals(transResult)) {
				return new Integer(IDiscountStatus.SUCCESS);
			}
		}
		return new Integer(failed);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nc.vo.engine.status.IStatus#getStatusKindName()
	 */
	public String getStatusKindName() {
		return IDiscountStatus.class.getName();
	}

	@Override
	public FBMManageUI getUI() {
		// TODO Auto-generated method stub
		return (FBMManageUI) super.getUI();
	}
}