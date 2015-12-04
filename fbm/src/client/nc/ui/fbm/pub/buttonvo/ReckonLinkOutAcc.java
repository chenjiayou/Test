package nc.ui.fbm.pub.buttonvo;

import java.awt.Event;

import nc.ui.fbm.pub.IFBMButton;
import nc.ui.trade.button.IBillButtonVO;
import nc.vo.trade.button.ButtonVO;

public class ReckonLinkOutAcc implements IBillButtonVO {

	public ButtonVO getButtonVO() {
		ButtonVO bvo = new ButtonVO();
		bvo.setBtnNo(IFBMButton.Reckon_LinkOutAccountBalance);
		bvo.setBtnChinaName("联查转出账户余额");
		bvo.setBtnCode(IFBMButton.Reckon_LinkOutAccountBalance_STR);
		bvo.setBtnName(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add","UPP3620ADD-000053")/*@res "联查转出账户余额"*/);
		bvo.setHintStr(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add","UPP3620ADD-000053")/*@res "联查转出账户余额"*/);
        bvo.setHotKey("B");
        bvo.setDisplayHotKey("(Shift+B)");
        bvo.setModifiers(Event.SHIFT_MASK);

        return bvo;
	}

}