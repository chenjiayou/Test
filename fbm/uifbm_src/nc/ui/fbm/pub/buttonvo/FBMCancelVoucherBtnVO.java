package nc.ui.fbm.pub.buttonvo;

import java.awt.Event;

import nc.ui.fbm.pub.IFBMButton;
import nc.ui.trade.base.IBillOperate;
import nc.ui.trade.button.IBillButtonVO;
import nc.vo.fbm.pub.constant.IFBMStatus;
import nc.vo.trade.button.ButtonVO;

public class FBMCancelVoucherBtnVO implements IBillButtonVO {

	public ButtonVO getButtonVO() {
		ButtonVO btnVo=new ButtonVO();
		btnVo.setBtnChinaName("取消制证");
		btnVo.setBtnName(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000027")/* @res"取消制证"*/);
		btnVo.setHintStr(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000027")/* @res"取消制证"*/);
		btnVo.setBtnNo(IFBMButton.FBM_CANCELVOUCHER);
		btnVo.setBtnCode(IFBMButton.FBM_CANCELVOUCHER_STR);
		btnVo.setOperateStatus(new int[]{IBillOperate.OP_NOTEDIT});
		btnVo.setBusinessStatus(new int[]{IFBMStatus.INPUT_SUCCESS});
		//取消制证
		btnVo.setModifiers(Event.ALT_MASK);
		btnVo.setHotKey("M");
		btnVo.setDisplayHotKey("(Alt+M)");

		return btnVo;
	}

}
