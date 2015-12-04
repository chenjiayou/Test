package nc.ui.fbm.pub.buttonvo;

import java.awt.Event;

import nc.ui.fbm.pub.IFBMButton;
import nc.ui.trade.button.IBillButtonVO;
import nc.vo.trade.button.ButtonVO;

/**
 * <p>
 *�������㵥�����˻���ťVO
 * <p>�����ˣ�bsrl
 * <b>���ڣ�2007-11-05
 */
public class ReckonLinkAccountBalance implements IBillButtonVO {

	public ReckonLinkAccountBalance() {
	}

	public ButtonVO getButtonVO() {

		ButtonVO bvo = new ButtonVO();
		bvo.setBtnNo(IFBMButton.Reckon_LinkAccoutBalance);
		bvo.setBtnChinaName("�����˻����"); 
		bvo.setBtnCode("RECKLINKACC");
		bvo.setBtnName(nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm","UPPFBMComm-000031")/* @res"�����˻����"*/);
		bvo.setHintStr(nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm","UPPFBMComm-000031")/* @res"�����˻����"*/);
        bvo.setHotKey("A");
        bvo.setDisplayHotKey("(Shift+A)");
        bvo.setModifiers(Event.SHIFT_MASK);
//        bvo.setOperateStatus(new int[]{IBillOperate.OP_NOTEDIT});
//        bvo.setBusinessStatus(new int[]{IBillStatus.CHECKPASS});

        return bvo;
	}
}