package nc.ui.fbm.returnbill.btnstatus;

import nc.ui.trade.base.AbstractBillUI;
import nc.vo.engine.status.AbstractBillUIStatus;
import nc.vo.fbm.pub.constant.FbmBusConstant;
import nc.vo.fbm.pub.constant.IFBMStatus;
import nc.vo.fbm.returnbill.ReturnVO;
import nc.vo.trade.pub.IBillStatus;

/**
 * 转出按钮控制
 * @author xwq
 *
 * 2008-10-23
 */
public class TransformBtnStatus extends AbstractBillUIStatus<Integer>{
	public static int BTN_DISABLE = 0;
	public static int BTN_CAN_TRANSFORM = 1;
	public static int BTN_CAN_UNTRANSFORM = 2;
	
	public TransformBtnStatus(AbstractBillUI ui) {
		super(ui);
	}
	
	public Integer getStatus() throws Exception {
		if(getUI().getBufferData().getCurrentVO()==null || getUI().getBufferData().getCurrentVO().getParentVO() == null){
			return BTN_DISABLE;
		}
		
		ReturnVO returnVO = (ReturnVO)getUI().getBufferData().getCurrentVO().getParentVO();
		//如果是审核通过并且不是统管退票，可转出
		if(returnVO.getVbillstatus().equals(IBillStatus.CHECKPASS) 
				&& !returnVO.getReturntype().equals(FbmBusConstant.BACK_TYPE_UNISTORAGE)
				&& !returnVO.getReturntype().equals(FbmBusConstant.BACK_TYPE_UNISTORAGE_UNIT)
				){
			return BTN_CAN_TRANSFORM;
		}
		if(returnVO.getVbillstatus().equals(IFBMStatus.TRANSFORM) 
				&& !returnVO.getReturntype().equals(FbmBusConstant.BACK_TYPE_UNISTORAGE)
				&& !returnVO.getReturntype().equals(FbmBusConstant.BACK_TYPE_UNISTORAGE_UNIT)
				){
			return BTN_CAN_UNTRANSFORM;
		}
		return BTN_DISABLE;
	}

	public String getStatusKindName() {
		return TransformBtnStatus.class.getName();
	}
}
