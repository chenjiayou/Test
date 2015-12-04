package nc.ui.fbm.pub.buttonvo;

import java.awt.Event;

import nc.ui.fbm.pub.IFBMButton;
import nc.ui.trade.button.IBillButtonVO;
import nc.vo.trade.button.ButtonVO;

//Ã˘œ÷ ‘À„∞¥≈•VO¿‡
//auther bsrl
public class DiscountCalculateButtonVO  implements IBillButtonVO{

	public DiscountCalculateButtonVO() {
	}

	public ButtonVO getButtonVO() {
		ButtonVO bvo = new ButtonVO();
		bvo.setBtnNo(IFBMButton.Discount_Calculate);
		bvo.setBtnChinaName("Ã˘œ÷ ‘À„");
		bvo.setBtnCode("DISCOUNT");
		bvo.setBtnName(nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm","UPPFBMComm-000012")/* @res"Ã˘œ÷ ‘À„"*/);
		bvo.setHintStr(nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm","UPPFBMComm-000012")/* @res"Ã˘œ÷ ‘À„"*/);
        bvo.setHotKey("D");
        bvo.setDisplayHotKey("(Shift+D)");
        bvo.setModifiers(Event.SHIFT_MASK);
//        bvo.setOperateStatus(new int[]{IBillOperate.OP_NOTEDIT,IBillOperate.OP_INIT});
//        bvo.setBusinessStatus(new int[]{IFBMStatus.Transact});

        return bvo;
	}

}