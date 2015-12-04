package nc.ui.fbm.pub.buttonvo;

import java.awt.Event;

import nc.ui.fbm.pub.IFBMButton;
import nc.ui.trade.button.IBillButtonVO;
import nc.vo.trade.button.ButtonVO;
import nc.vo.trade.pub.IBillStatus;

//贴现申请办理按钮VO类
//auther bsrl
public class DiscountApplyTransactButtonVO implements IBillButtonVO {

	public DiscountApplyTransactButtonVO() {
	}

	public ButtonVO getButtonVO() {
		ButtonVO bvo = new ButtonVO();
		bvo.setBtnNo(IFBMButton.DiscountApply_Transact);
		bvo.setBtnChinaName("办理");
		bvo.setBtnCode("TRANSACT");
		bvo.setBtnName(nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm","UPPFBMComm-000011")/* @res"办理"*/);
		bvo.setHintStr(nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm","UPPFBMComm-000011")/* @res"办理"*/);
        bvo.setHotKey("T");
        bvo.setDisplayHotKey("(Shift+T)");
        bvo.setModifiers(Event.SHIFT_MASK);
//        bvo.setOperateStatus(new int[]{nc.ui.trade.base.IBillOperate.OP_NOTEDIT});
        bvo.setBusinessStatus(new int[]{IBillStatus.CHECKPASS});

        return bvo;
	}

}