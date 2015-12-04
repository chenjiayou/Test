package nc.ui.fbm.pub.buttonvo;

import java.awt.Event;

import nc.ui.fbm.pub.IFBMButton;
import nc.ui.trade.button.IBillButtonVO;
import nc.vo.trade.button.ButtonVO;

public class EndoreLinkBillButtonVO implements IBillButtonVO {

	public EndoreLinkBillButtonVO() {
		// TODO Auto-generated constructor stub
	}

	public ButtonVO getButtonVO() {
		ButtonVO bvo = new ButtonVO();
		bvo.setBtnNo(IFBMButton.Endore_LinkBill);
		bvo.setBtnChinaName("联查收付报单据");
		bvo.setBtnCode("DISLINKBILL");
		bvo.setBtnName(nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm","UPPFBMComm-000008")/* @res"联查收付款单据"*/);
		bvo.setHintStr(nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm","UPPFBMComm-000008")/* @res"联查收付款单据"*/);
        bvo.setHotKey("B");
        bvo.setDisplayHotKey("(Shift+B)");
        bvo.setModifiers(Event.SHIFT_MASK);
//        bvo.setOperateStatus(new int[]{IBillOperate.OP_NOTEDIT,IBillOperate.OP_INIT});
//        bvo.setBusinessStatus(new int[]{IFBMStatus.Transact});

        return bvo;
	}
}
