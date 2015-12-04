package nc.ui.fbm.pub.buttonvo;

import java.awt.Event;

import nc.ui.fbm.pub.IFBMButton;
import nc.ui.trade.button.IBillButtonVO;
import nc.vo.trade.button.ButtonVO;

/**
 * <p>
 *调剂清算单联查账户余额按钮VO
 * <p>创建人：bsrl
 * <b>日期：2007-11-05
 */
public class ReckonLinkAccountBalance implements IBillButtonVO {

	public ReckonLinkAccountBalance() {
	}

	public ButtonVO getButtonVO() {

		ButtonVO bvo = new ButtonVO();
		bvo.setBtnNo(IFBMButton.Reckon_LinkAccoutBalance);
		bvo.setBtnChinaName("联查账户余额"); 
		bvo.setBtnCode("RECKLINKACC");
		bvo.setBtnName(nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm","UPPFBMComm-000031")/* @res"联查账户余额"*/);
		bvo.setHintStr(nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm","UPPFBMComm-000031")/* @res"联查账户余额"*/);
        bvo.setHotKey("A");
        bvo.setDisplayHotKey("(Shift+A)");
        bvo.setModifiers(Event.SHIFT_MASK);
//        bvo.setOperateStatus(new int[]{IBillOperate.OP_NOTEDIT});
//        bvo.setBusinessStatus(new int[]{IBillStatus.CHECKPASS});

        return bvo;
	}
}