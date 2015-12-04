package nc.ui.fbm.storage.status;

import nc.bs.framework.common.NCLocator;
import nc.itf.uap.bd.cust.ICuBasDocQry;
import nc.ui.pub.ClientEnvironment;
import nc.ui.trade.base.AbstractBillUI;
import nc.vo.bd.b08.CubasdocVO;
import nc.vo.bd.b08.CustBasVO;
import nc.vo.engine.status.AbstractBillUIStatus;
import nc.vo.fbm.pub.constant.FbmBusConstant;
import nc.vo.fbm.pub.constant.IFBMStatus;
import nc.vo.pub.SuperVO;
import nc.vo.pub.lang.UFBoolean;

/**
 * ��֤��ť״̬����
 * 
 * @author xwq
 * 
 * 2008-7-30
 */
public class VoucherStatus extends AbstractBillUIStatus<Integer> {

	public static int BTN_DISABLE = 0;
	public static int UNIT_CAN_VOUCHER = 1;
	public static int UNIT_CAN_CANCELVOUCHER = 2;
	public static int CENTER_CAN_VOUCHER = 3;
	public static int CENTER_CAN_CANCELVOUCHER = 4;

	private String docorpKey;
	private String unitVoucherFlagKey;
	private String centerVoucherFlagKey;

	public VoucherStatus(AbstractBillUI ui, String docorpKey,
			String unitVoucherFlagKey, String centerVoucherFlagKey) {
		super(ui);
		this.docorpKey = docorpKey;
		this.unitVoucherFlagKey = unitVoucherFlagKey;
		this.centerVoucherFlagKey = centerVoucherFlagKey;
	}

	public Integer getStatus() throws Exception {
		if (getUI().getBufferData().getCurrentVO() == null) {
			return BTN_DISABLE;
		}
		SuperVO parentVO = (SuperVO) getUI().getBufferData().getCurrentVO()
				.getParentVO();
		UFBoolean unit = (UFBoolean) parentVO
				.getAttributeValue(unitVoucherFlagKey);// ��λ��֤��־
		UFBoolean center = (UFBoolean) parentVO
				.getAttributeValue(centerVoucherFlagKey);// ������֤��־
		Integer vbillstatus = (Integer) parentVO
				.getAttributeValue("vbillstatus");// ����״̬

		String docorp = (String) parentVO.getAttributeValue(docorpKey);

		String currcorp = ClientEnvironment.getInstance().getCorporation()
				.getPrimaryKey();
		boolean isUnit = currcorp.equals(docorp);
		String billtypecode = (String) parentVO
				.getAttributeValue("pk_billtypecode");

//		if (vbillstatus == IFBMStatus.INPUT_SUCCESS
//				|| vbillstatus == IFBMStatus.OUTPUT_SUCCESS
//				|| (vbillstatus == IFBMStatus.CHECKPASS && FbmBusConstant.BILLTYPE_RELIEF.equals(billtypecode))) {
			if (isUnit) {// ��λ��¼����λ����
				if (unit.booleanValue()) {// ��λ����֤
					return UNIT_CAN_CANCELVOUCHER;
				} else {// ��λδ��֤
					return UNIT_CAN_VOUCHER;
				}
			} else {
				if (center.booleanValue()) {
					return CENTER_CAN_CANCELVOUCHER;
				} else {
					return CENTER_CAN_VOUCHER;
				}
			}
//		}

		//return BTN_DISABLE;
	}

	public String getStatusKindName() {
		return VoucherStatus.class.getName();
	}
}
