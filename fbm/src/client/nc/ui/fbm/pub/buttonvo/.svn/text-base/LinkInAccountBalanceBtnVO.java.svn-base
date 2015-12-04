package nc.ui.fbm.pub.buttonvo;

import java.awt.Event;

import nc.ui.fbm.pub.IFBMButton;
import nc.ui.trade.base.IBillOperate;
import nc.ui.trade.button.IBillButtonVO;
import nc.vo.trade.button.ButtonVO;

public class LinkInAccountBalanceBtnVO implements IBillButtonVO {

	public ButtonVO getButtonVO() {
		ButtonVO bvo = new ButtonVO();
		bvo.setBtnNo(IFBMButton.LINK_ACCOUNTBANLANCE);
		bvo.setBtnChinaName("联查内部账户余额");
		bvo.setBtnCode(IFBMButton.LINK_ACCOUNTBANLANCE_STR);
		bvo.setBtnName(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add","UPP3620ADD-000050")/*@res "联查内部账户余额"*/);
		bvo.setHintStr(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add","UPP3620ADD-000050")/*@res "联查内部账户余额"*/);
        bvo.setHotKey("A");
        bvo.setDisplayHotKey("(Shift+A)");
        bvo.setModifiers(Event.SHIFT_MASK);
        bvo.setOperateStatus(new int[]{IBillOperate.OP_NOTEDIT});

        return bvo;
	}

}