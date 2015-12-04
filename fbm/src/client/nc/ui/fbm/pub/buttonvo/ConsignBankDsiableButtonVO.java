package nc.ui.fbm.pub.buttonvo;

import java.awt.Event;

import nc.ui.fbm.pub.IFBMButton;
import nc.ui.trade.button.IBillButtonVO;
import nc.vo.trade.button.ButtonVO;
import nc.vo.trade.pub.IBillStatus;

//银行托收作废按钮VO类
//auther bsrl
public class ConsignBankDsiableButtonVO implements IBillButtonVO {

	public ConsignBankDsiableButtonVO() {
	}

	public ButtonVO getButtonVO() {

		ButtonVO bvo = new ButtonVO();
		bvo.setBtnNo(IFBMButton.ConsignBank_Disable);
		bvo.setBtnChinaName("作废");
		bvo.setBtnCode("DISABLE");
		bvo.setBtnName(nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm","UPPFBMComm-000007")/* @res"作废"*/);
		bvo.setHintStr(nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm","UPPFBMComm-000007")/* @res"作废"*/);
        bvo.setHotKey("D");
        bvo.setDisplayHotKey("(Shift+D)");
        bvo.setModifiers(Event.SHIFT_MASK);
//        bvo.setOperateStatus(new int[]{IBillOperate.OP_NOTEDIT});
        bvo.setBusinessStatus(new int[]{IBillStatus.CHECKPASS});

        return bvo;
	}

}