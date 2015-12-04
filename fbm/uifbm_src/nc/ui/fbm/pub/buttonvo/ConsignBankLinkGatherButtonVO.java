package nc.ui.fbm.pub.buttonvo;

import java.awt.Event;

import nc.ui.fbm.pub.IFBMButton;
import nc.ui.trade.button.IBillButtonVO;
import nc.vo.trade.button.ButtonVO;
//import nc.vo.trade.pub.IBillStatus;

//������������Ʊ�ݰ�ťVO��
//auther bsrl
public class ConsignBankLinkGatherButtonVO implements IBillButtonVO {

	public ConsignBankLinkGatherButtonVO() {
	}

	public ButtonVO getButtonVO() {

		ButtonVO bvo = new ButtonVO();
		bvo.setBtnNo(IFBMButton.ConsignBank_LinkGather);
		bvo.setBtnChinaName("����Ʊ�ݱ��鲾");
		bvo.setBtnCode("CONLINKGATHER");
		bvo.setBtnName(nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm","UPPFBMComm-000015")/* @res"����Ʊ�ݱ��鲾"*/);
		bvo.setHintStr(nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm","UPPFBMComm-000015")/* @res"����Ʊ�ݱ��鲾"*/);
        bvo.setHotKey("P");
        bvo.setDisplayHotKey("(Ctrl+Alt+P)");
        bvo.setModifiers(Event.CTRL_MASK);
//        bvo.setOperateStatus(new int[]{IBillOperate.OP_NOTEDIT});
//        bvo.setBusinessStatus(new int[]{IBillStatus.CHECKPASS});

        return bvo;
	}
}