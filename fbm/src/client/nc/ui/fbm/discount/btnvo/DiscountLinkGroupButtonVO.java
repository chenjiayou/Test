package nc.ui.fbm.discount.btnvo;

import nc.ui.fbm.pub.IFBMButton;
import nc.ui.trade.button.IBillButtonVO;
import nc.vo.trade.button.ButtonVO;

//���ְ��������鰴ťVO��
//auther bsrl
public class DiscountLinkGroupButtonVO implements IBillButtonVO{
	/**
	 *
	 */
	public DiscountLinkGroupButtonVO() {
	}

	/* (non-Javadoc)
	 * @see nc.ui.trade.button.IBillButtonVO#getButtonVO()
	 */
	public ButtonVO getButtonVO() {
		ButtonVO btnVo=new ButtonVO();
		btnVo.setBtnChinaName("����");
		btnVo.setBtnName(nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm","UPPFBMComm-000010")/* @res"����"*/);
		btnVo.setBtnNo(IFBMButton.Discount_LinkGroup);
		btnVo.setChildAry(new int[] { IFBMButton.Discount_LinkGather,
				IFBMButton.Discount_LinkVoucher,
				IFBMButton.BTN_QUERY_CHARGE_PLAN,
				IFBMButton.BTN_QUERY_INTEREST_PLAN,
				IFBMButton.BTN_QUERY_BALANCE_PLAN,
				IFBMButton.Discount_LinkGatherPlan});
		btnVo.setHintStr(nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm","UPPFBMComm-000010")/* @res"����"*/);
//		btnVo.setBusinessStatus(new int[]{IBillStatus.CHECKPASS});
		
		return btnVo;
	}
}