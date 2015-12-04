package nc.ui.fbm.pub.buttonvo;

import java.awt.Event;

import nc.ui.fbm.pub.IFBMButton;
import nc.ui.trade.base.IBillOperate;
import nc.ui.trade.button.IBillButtonVO;
import nc.vo.fbm.pub.constant.IFBMStatus;
import nc.vo.trade.button.ButtonVO;

public class FBMTallyBtnVO implements IBillButtonVO {

	public ButtonVO getButtonVO() {
		ButtonVO bvo = new ButtonVO();
		bvo.setBtnNo(IFBMButton.FBM_TALLY);
		bvo.setBtnChinaName("╪гук");
		bvo.setBtnName(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add","UPP3620ADD-000048")/*@res "╪гук"*/);
		bvo.setBtnCode(IFBMButton.FBM_TALLY_STR);
		bvo.setHintStr(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add","UPP3620ADD-000048")/*@res "╪гук"*/);
		bvo.setModifiers(Event.SHIFT_MASK);
		bvo.setHotKey("Y");
		bvo.setDisplayHotKey("(Shift+Y)");
		bvo.setOperateStatus(new int[] { IBillOperate.OP_NOTEDIT });
		bvo.setBusinessStatus(new int[]{IFBMStatus.INPUT_SUCCESS,IFBMStatus.OUTPUT_SUCCESS});

		return bvo;
	}

}