package nc.ui.fbm.pub.buttonvo;

import java.awt.Event;

import nc.ui.fbm.pub.IFBMButton;
import nc.ui.trade.base.IBillOperate;
import nc.ui.trade.button.IBillButtonVO;
import nc.vo.fbm.pub.constant.IFBMStatus;
import nc.vo.trade.button.ButtonVO;
import nc.vo.trade.pub.IBillStatus;

public class FBMCancelSubmitBtnVO implements IBillButtonVO {

	public ButtonVO getButtonVO() {
		ButtonVO bvo = new ButtonVO();
		bvo.setBtnNo(IFBMButton.FBM_CANCELSUBMIT);
		bvo.setBtnChinaName("取消提交");
		bvo.setBtnName(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add","UPP3620ADD-000046")/*@res "取消提交"*/);
		bvo.setBtnCode(IFBMButton.FBM_CANCELSUBMIT_STR);
		bvo.setHintStr(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add","UPP3620ADD-000046")/*@res "取消提交"*/);
		bvo.setModifiers(Event.SHIFT_MASK);
		bvo.setHotKey("V");
		bvo.setDisplayHotKey("(Shift+V)");
		bvo.setOperateStatus(new int[] { IBillOperate.OP_NOTEDIT });
		bvo.setBusinessStatus(new int[]{IFBMStatus.SUBMIT});
		return bvo;
	}

}