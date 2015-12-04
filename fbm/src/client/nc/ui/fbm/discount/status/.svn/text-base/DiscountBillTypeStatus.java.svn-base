
package nc.ui.fbm.discount.status;

import nc.ui.fbm.pub.FBMManageUI;
import nc.ui.trade.base.AbstractBillUI;
import nc.vo.engine.status.AbstractBillUIStatus;
import nc.vo.fbm.discount.DiscountVO;
import nc.vo.fbm.pub.constant.FbmBusConstant;
import nc.vo.pub.AggregatedValueObject;
import nc.vo.pub.SuperVO;
/**
 * 
 ***********************************************************
 * 日期：2008-3-28							   
 * 程序员:吴二山 							   
 * 功能：根据票据类别控制制证按钮是否可用
 ***********************************************************
 */
public class DiscountBillTypeStatus  extends AbstractBillUIStatus<Integer> {

	/**
	 * @param ui
	 */
	public DiscountBillTypeStatus(AbstractBillUI ui) {
		super(ui);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nc.vo.engine.status.IStatus#getStatus()
	 */
	public Integer getStatus() throws Exception {
		int unistorage = IOpBillTypeStatus.UNISTORAGE;// 默认统管
		AggregatedValueObject currVo = getUI().getBufferData().getCurrentVO();
		if (null != currVo && null != currVo.getParentVO()) {
			String opBillType = (String)((SuperVO) currVo.getParentVO())
					.getAttributeValue(DiscountVO.OPBILLTYPE);// 得到票据类别
			if (FbmBusConstant.BILL_PRIVACY.equals(opBillType)) {
				return new Integer(IOpBillTypeStatus.PRIVATEBILL);//自有
			}
		}
		return new Integer(unistorage);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nc.vo.engine.status.IStatus#getStatusKindName()
	 */
	public String getStatusKindName() {
		return IOpBillTypeStatus.class.getName();
	}

	@Override
	public FBMManageUI getUI() {
		// TODO Auto-generated method stub
		return (FBMManageUI) super.getUI();
	}

}
