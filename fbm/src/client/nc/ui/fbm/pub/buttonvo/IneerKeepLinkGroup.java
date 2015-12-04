package nc.ui.fbm.pub.buttonvo;

import nc.ui.fbm.pub.IFBMButton;
import nc.ui.trade.button.IBillButtonVO;
import nc.vo.trade.button.ButtonVO;

public class IneerKeepLinkGroup implements IBillButtonVO {

	public IneerKeepLinkGroup() {
		super();
	}
	
	public ButtonVO getButtonVO() {
		ButtonVO btnVo=new ButtonVO();
		btnVo.setBtnChinaName("联查");
		btnVo.setBtnName(nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm","UPPFBMComm-000010")/* @res"联查"*/);
		btnVo.setBtnNo(IFBMButton.InnerKeep_LinkGroup);
		btnVo.setChildAry(new int[] { IFBMButton.Center_Storage_QueryGL,
				IFBMButton.Gather_LQueryPJBook,
				IFBMButton.LINK_ACCOUNTBANLANCE,
				IFBMButton.BTN_QUERY_PLAN});
		btnVo.setHintStr(nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm","UPPFBMComm-000010")/* @res"联查"*/);
		return btnVo;
	}

}
