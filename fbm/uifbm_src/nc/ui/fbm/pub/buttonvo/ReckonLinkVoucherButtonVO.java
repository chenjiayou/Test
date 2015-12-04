package nc.ui.fbm.pub.buttonvo;

import java.awt.Event;

import nc.ui.fbm.pub.IFBMButton;
import nc.ui.trade.button.IBillButtonVO;
import nc.vo.trade.button.ButtonVO;

/**
 * <p>
 *调剂清算单联查凭证按钮VO
 * <p>创建人：bsrl
 * <b>日期：2007-11-05
 */
public class ReckonLinkVoucherButtonVO implements IBillButtonVO {

	public ReckonLinkVoucherButtonVO() {
	}

	public ButtonVO getButtonVO() {

		ButtonVO bvo = new ButtonVO();
		bvo.setBtnNo(IFBMButton.Reckon_LinkVoucher);
		bvo.setBtnChinaName("联查凭证");
		bvo.setBtnCode("RECKLINKVOUCH");
		bvo.setBtnName(nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm","UPPFBMComm-000030")/* @res"联查凭证"*/);
		bvo.setHintStr(nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm","UPPFBMComm-000030")/* @res"联查凭证"*/);
        bvo.setHotKey("L");
        bvo.setDisplayHotKey("(Shift+L)");
        bvo.setModifiers(Event.SHIFT_MASK);
//        bvo.setOperateStatus(new int[]{IBillOperate.OP_NOTEDIT});
//        bvo.setBusinessStatus(new int[]{IBillStatus.CHECKPASS});

        return bvo;
	}
}