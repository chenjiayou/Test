package nc.ui.fbm.pub.buttonvo;

import java.awt.Event;

import nc.ui.fbm.pub.IFBMButton;
import nc.ui.trade.base.IBillOperate;
import nc.ui.trade.button.IBillButtonVO;
import nc.vo.fbm.pub.constant.IFBMStatus;
import nc.vo.trade.button.ButtonVO;

public class FBMVoucherBtnVO implements IBillButtonVO {

	public ButtonVO getButtonVO() {
		ButtonVO btnVo=new ButtonVO();
		btnVo.setBtnChinaName("制证");
		btnVo.setBtnName(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000028")/* @res"制证"*/);
		btnVo.setHintStr(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000028")/* @res"制证"*/);
		btnVo.setBtnNo(IFBMButton.FBM_VOUCHER);
		btnVo.setBtnCode(IFBMButton.FBM_VOUCHER_STR);
		btnVo.setOperateStatus(new int[]{IBillOperate.OP_NOTEDIT});
		btnVo.setBusinessStatus(new int[]{IFBMStatus.INPUT_SUCCESS});
		//取消制证
		btnVo.setModifiers(Event.CTRL_MASK);
		btnVo.setHotKey("M");
		btnVo.setDisplayHotKey("(Ctrl+M)");

		return btnVo;
	}

	
}
