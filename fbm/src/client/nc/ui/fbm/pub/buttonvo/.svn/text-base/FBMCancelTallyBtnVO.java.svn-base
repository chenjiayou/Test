package nc.ui.fbm.pub.buttonvo;

import java.awt.Event;

import nc.ui.fbm.pub.IFBMButton;
import nc.ui.trade.base.IBillOperate;
import nc.ui.trade.button.IBillButtonVO;
import nc.vo.fbm.pub.constant.IFBMStatus;
import nc.vo.trade.button.ButtonVO;

public class FBMCancelTallyBtnVO implements IBillButtonVO {

	public ButtonVO getButtonVO() {
		ButtonVO bvo = new ButtonVO();
		bvo.setBtnNo(IFBMButton.FBM_CANCELTALLY);
		bvo.setBtnChinaName("取消记账");
		bvo.setBtnName(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add","UPP3620ADD-000047")/*@res "取消记账"*/);
		bvo.setBtnCode(IFBMButton.FBM_CANCELTALLY_STR);
		bvo.setHintStr(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add","UPP3620ADD-000047")/*@res "取消记账"*/);
		bvo.setModifiers(Event.SHIFT_MASK);
		bvo.setHotKey("Z");
		bvo.setDisplayHotKey("(Shift+Z)");
		bvo.setOperateStatus(new int[] { IBillOperate.OP_NOTEDIT });
		bvo.setBusinessStatus(new int[]{IFBMStatus.INPUT_SUCCESS,IFBMStatus.OUTPUT_SUCCESS});
		return bvo;
	}

}