/**
 *
 */
package nc.ui.fbm.pub.buttonvo;

import java.awt.Event;

import nc.ui.fbm.pub.IFBMButton;
import nc.ui.trade.base.IBillOperate;
import nc.ui.trade.button.IBillButtonVO;
import nc.vo.trade.button.ButtonVO;

/**
 * @author bsrl
 *
 */
public class AttachButtonVO implements IBillButtonVO {

	/*
	 * (non-Javadoc)
	 *
	 * @see nc.ui.trade.button.IBillButtonVO#getButtonVO()
	 */
	public ButtonVO getButtonVO() {
		ButtonVO bvo = new ButtonVO();
		bvo.setBtnNo(IFBMButton.Attach);
		bvo.setBtnChinaName("¸½¼þ");
		bvo.setBtnName(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("common","UC000-0004141")/*@res "¸½¼þ"*/);
		bvo.setBtnCode("ATTACH");
		bvo.setHintStr(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("common","UC000-0004141")/*@res "¸½¼þ"*/);
		bvo.setModifiers(Event.CTRL_MASK);
		bvo.setOperateStatus(new int[] { IBillOperate.OP_NOTEDIT });

		return bvo;

	}

}