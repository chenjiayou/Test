package nc.ui.fbm.pub.buttonvo;

import nc.ui.fbm.pub.IFBMButton;
import nc.ui.trade.button.IBillButtonVO;
import nc.vo.trade.button.ButtonVO;

public class EndoreLinkGroupButtonVO implements IBillButtonVO {

	public EndoreLinkGroupButtonVO() {
		// TODO Auto-generated constructor stub
	}

	public ButtonVO getButtonVO() {
		ButtonVO btnVo = new ButtonVO();
		btnVo.setBtnChinaName("联查");
		btnVo.setBtnName(nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm",
				"UPPFBMComm-000010")/* @res"联查" */);
		btnVo.setBtnNo(IFBMButton.Endore_LinkGroup);
		btnVo.setChildAry(new int[] {
				IFBMButton.Gather_LQueryPJBook, IFBMButton.Endore_LinkBill
				});
		btnVo.setHintStr(nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm",
				"UPPFBMComm-000010")/* @res"联查" */);
		// btnVo.setBusinessStatus(new int[]{IBillStatus.CHECKPASS});

		return btnVo;
	}

}
