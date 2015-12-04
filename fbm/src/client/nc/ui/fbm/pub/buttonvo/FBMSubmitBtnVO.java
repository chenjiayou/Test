package nc.ui.fbm.pub.buttonvo;

import java.awt.Event;

import nc.ui.fbm.pub.IFBMButton;
import nc.ui.trade.base.IBillOperate;
import nc.ui.trade.button.IBillButtonVO;
import nc.vo.trade.button.ButtonVO;
import nc.vo.trade.pub.IBillStatus;

public class FBMSubmitBtnVO implements IBillButtonVO {

	public ButtonVO getButtonVO() {
		ButtonVO bvo = new ButtonVO();
		bvo.setBtnNo(IFBMButton.FBM_SUBMIT);
		bvo.setBtnChinaName("提交");
		bvo.setBtnName(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("common","UC001-0000029")/*@res "提交"*/);
		bvo.setBtnCode(IFBMButton.FBM_SUBMIT_STR);
		bvo.setHintStr(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("common","UC001-0000029")/*@res "提交"*/);
		bvo.setModifiers(Event.SHIFT_MASK);
		bvo.setHotKey("T");
		bvo.setDisplayHotKey("(Shift+T)");
		bvo.setOperateStatus(new int[] { IBillOperate.OP_NOTEDIT });
		bvo.setBusinessStatus(new int[]{IBillStatus.CHECKPASS});

		return bvo;
	}

}