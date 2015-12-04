package nc.ui.fbm.pub.buttonvo;

import java.awt.Event;

import nc.ui.fbm.pub.IFBMButton;
import nc.ui.trade.base.IBillOperate;
import nc.ui.trade.button.IBillButtonVO;
import nc.vo.trade.button.ButtonVO;
//import nc.vo.trade.pub.IBillStatus;

//贴现办理联查票据按钮VO类
//auther bsrl
public class DiscountLinkGatherButtonVO implements IBillButtonVO {

	public DiscountLinkGatherButtonVO() {
	}

	public ButtonVO getButtonVO() {

		ButtonVO bvo = new ButtonVO();
		bvo.setBtnNo(IFBMButton.Discount_LinkGather);
		bvo.setBtnChinaName("联查票据备查簿");
		bvo.setBtnCode("DISLINKGATHER");
		bvo.setBtnName(nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm","UPPFBMComm-000015")/* @res"联查票据备查簿"*/);
		bvo.setHintStr(nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm","UPPFBMComm-000015")/* @res"联查票据备查簿"*/);
		bvo.setOperateStatus(new int[]{IBillOperate.OP_NOTEDIT});


        bvo.setHotKey("P");
        bvo.setDisplayHotKey("(Ctrl+Alt+P)");
        bvo.setModifiers(Event.CTRL_MASK);
//        bvo.setOperateStatus(new int[]{IBillOperate.OP_NOTEDIT});
//        bvo.setBusinessStatus(new int[]{IBillStatus.CHECKPASS});

        return bvo;
	}
}