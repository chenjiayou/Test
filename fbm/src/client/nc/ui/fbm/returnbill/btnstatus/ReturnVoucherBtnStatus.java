package nc.ui.fbm.returnbill.btnstatus;

import nc.ui.trade.base.AbstractBillUI;
import nc.vo.engine.status.AbstractBillUIStatus;
import nc.vo.fbm.pub.constant.FbmBusConstant;
import nc.vo.fbm.returnbill.ReturnVO;
import nc.vo.pub.CircularlyAccessibleValueObject;
import nc.vo.pub.lang.UFBoolean;


/**
 * 票据公用制证和取消制证按钮的判断类
 * @author xwq
 *
 * 2008-8-21
 */
public class ReturnVoucherBtnStatus extends AbstractBillUIStatus<Integer>{
	public static int BTN_DISABLE = 0;
	public static int CAN_VOUCHER = 1;
	public static int CAN_CANCELVOUCHER = 2;
	
	private String voucherKey;//制证字段编码
	
	private ReturnVoucherBtnStatus(AbstractBillUI ui) {
		super(ui);
	}
	
	public ReturnVoucherBtnStatus(AbstractBillUI ui,String voucherKey){
		super(ui);
		this.voucherKey = voucherKey;
	}
	
	public Integer getStatus() throws Exception {
		if(getUI().getBufferData().getCurrentVO()==null || getUI().getBufferData().getCurrentVO().getParentVO() == null){
			return BTN_DISABLE;
		}
		CircularlyAccessibleValueObject returnVo = getUI().getBufferData().getCurrentVO().getParentVO();
		if(getUI().getBufferData().getCurrentVO()==null || returnVo == null){
			return BTN_DISABLE;
		}
		Object o = returnVo.getAttributeValue(voucherKey);
		if(o == null){
			return BTN_DISABLE;
		}
		UFBoolean b = (UFBoolean)o;
		
		
		String returnType = (String) returnVo.getAttributeValue(ReturnVO.RETURNTYPE);
		//如果不是中心退出，也不是中心退入，则制证按钮置灰
		if(!returnType.equals(FbmBusConstant.BACK_TYPE_UNISTORAGE)&&!returnType.equals(FbmBusConstant.BACK_TYPE_UNISTORAGE_UNIT)){
			return BTN_DISABLE;
		}
		
		if(b.booleanValue()){
			return CAN_CANCELVOUCHER;
		}
		return CAN_VOUCHER;
	}

	public String getStatusKindName() {
		return ReturnVoucherBtnStatus.class.getName();
	}

}
