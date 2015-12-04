package nc.ui.fbm.pub.buttonvo;

import java.awt.Event;

import nc.ui.fbm.pub.IFBMButton;
import nc.ui.trade.button.IBillButtonVO;
import nc.vo.trade.button.ButtonVO;

/**
 * <p>
 *�������㵥����ƾ֤��ťVO
 * <p>�����ˣ�bsrl
 * <b>���ڣ�2007-11-05
 */
public class ReckonLinkVoucherButtonVO implements IBillButtonVO {

	public ReckonLinkVoucherButtonVO() {
	}

	public ButtonVO getButtonVO() {

		ButtonVO bvo = new ButtonVO();
		bvo.setBtnNo(IFBMButton.Reckon_LinkVoucher);
		bvo.setBtnChinaName("����ƾ֤");
		bvo.setBtnCode("RECKLINKVOUCH");
		bvo.setBtnName(nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm","UPPFBMComm-000030")/* @res"����ƾ֤"*/);
		bvo.setHintStr(nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm","UPPFBMComm-000030")/* @res"����ƾ֤"*/);
        bvo.setHotKey("L");
        bvo.setDisplayHotKey("(Shift+L)");
        bvo.setModifiers(Event.SHIFT_MASK);
//        bvo.setOperateStatus(new int[]{IBillOperate.OP_NOTEDIT});
//        bvo.setBusinessStatus(new int[]{IBillStatus.CHECKPASS});

        return bvo;
	}
}