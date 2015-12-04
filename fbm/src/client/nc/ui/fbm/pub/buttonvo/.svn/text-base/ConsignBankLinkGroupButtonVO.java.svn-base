package nc.ui.fbm.pub.buttonvo;

import nc.ui.fbm.pub.IFBMButton;
import nc.ui.trade.button.IBillButtonVO;
import nc.vo.trade.button.ButtonVO;

//银行托收联查组按钮VO类
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
		btnVo.setBtnChinaName("联查");
		btnVo.setBtnName(nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm","UPPFBMComm-000010")/* @res"联查"*/);
		btnVo.setBtnNo(IFBMButton.ConsignBank_LinkGroup);
		btnVo.setChildAry(new int[] { IFBMButton.ConsignBank_LinkGather,
				IFBMButton.ConsignBank_LinkVoucher,
				IFBMButton.ConsignBank_LinkPlan,
				IFBMButton.ConsignBank_LinkGatherPlan});
		btnVo.setHintStr(nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm","UPPFBMComm-000010")/* @res"联查"*/);
//		btnVo.setBusinessStatus(new int[]{IBillStatus.CHECKPASS});
		return btnVo;
	}
}