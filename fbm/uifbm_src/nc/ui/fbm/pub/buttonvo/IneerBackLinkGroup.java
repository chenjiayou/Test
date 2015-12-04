package nc.ui.fbm.pub.buttonvo;

import nc.ui.fbm.pub.IFBMButton;
import nc.ui.trade.button.IBillButtonVO;
import nc.vo.trade.button.ButtonVO;

public class IneerBackLinkGroup implements IBillButtonVO {

	public IneerBackLinkGroup() {
		super();
	}
	public ButtonVO getButtonVO() {
		ButtonVO btnVo=new ButtonVO();
		btnVo.setBtnChinaName("����");
		btnVo.setBtnName(nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm","UPPFBMComm-000010")/* @res"����"*/);
		btnVo.setBtnNo(IFBMButton.InnerBack_LinkGroup);
		btnVo.setChildAry(new int[] { IFBMButton.Center_Storage_QueryGL,
				IFBMButton.Gather_LQueryPJBook,
				IFBMButton.LINK_ACCOUNTBANLANCE,
				IFBMButton.BTN_QUERY_PLAN});
		btnVo.setHintStr(nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm","UPPFBMComm-000010")/* @res"����"*/);
		return btnVo;
	}

}
