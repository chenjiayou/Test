package nc.ui.fbm.pub.buttonvo;

import java.awt.Event;

import nc.ui.fbm.pub.IFBMButton;
import nc.ui.trade.button.IBillButtonVO;
import nc.vo.trade.button.ButtonVO;
import nc.vo.trade.pub.IBillStatus;

//�������հ���ťVO��
//auther bsrl
public class ConsignBankTransactButtonVO implements IBillButtonVO {

	public ConsignBankTransactButtonVO() {
	}

	public ButtonVO getButtonVO() {

		ButtonVO bvo = new ButtonVO();
		bvo.setBtnNo(IFBMButton.ConsignBank_Transact);
		bvo.setBtnChinaName("ȷ��");
		bvo.setBtnCode("TRANSACT");
		bvo.setBtnName(nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm","UPPFBMComm-000011")/* @res"ȷ��"*/);
		bvo.setHintStr(nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm","UPPFBMComm-000011")/* @res"ȷ��"*/);
        bvo.setHotKey("T");
        bvo.setDisplayHotKey("(Shift+T)");
        bvo.setModifiers(Event.SHIFT_MASK);
//        bvo.setOperateStatus(new int[]{IBillOperate.OP_NOTEDIT});
        bvo.setBusinessStatus(new int[]{IBillStatus.CHECKPASS});

        return bvo;


	}

}