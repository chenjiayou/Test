package nc.ui.fbm.pub.buttonvo;

import nc.ui.fbm.pub.IFBMButton;
import nc.ui.trade.button.IBillButtonVO;
import nc.vo.trade.button.ButtonVO;

//�������������鰴ťVO��
//auther bsrl
public class ConsignBankLinkGroupButtonVO implements IBillButtonVO{
	/**
	 *
	 */
	public ConsignBankLinkGroupButtonVO() {
	}

	/* (non-Javadoc)
	 * @see nc.ui.trade.button.IBillButtonVO#getButtonVO()
	 */
	public ButtonVO getButtonVO() {
		ButtonVO btnVo=new ButtonVO();
		btnVo.setBtnChinaName("����");
		btnVo.setBtnName(nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm","UPPFBMComm-000010")/* @res"����"*/);
		btnVo.setBtnNo(IFBMButton.ConsignBank_LinkGroup);
		btnVo.setChildAry(new int[] { IFBMButton.ConsignBank_LinkGather,
				IFBMButton.ConsignBank_LinkVoucher,
				IFBMButton.ConsignBank_LinkPlan,
				IFBMButton.ConsignBank_LinkGatherPlan});
		btnVo.setHintStr(nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm","UPPFBMComm-000010")/* @res"����"*/);
//		btnVo.setBusinessStatus(new int[]{IBillStatus.CHECKPASS});
		return btnVo;
	}
}