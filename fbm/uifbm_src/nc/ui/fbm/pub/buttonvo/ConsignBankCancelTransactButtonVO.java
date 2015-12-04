package nc.ui.fbm.pub.buttonvo;

import java.awt.Event;

import nc.ui.fbm.pub.IFBMButton;
import nc.ui.trade.base.IBillOperate;
import nc.ui.trade.button.IBillButtonVO;
import nc.vo.fbm.pub.constant.IFBMStatus;
import nc.vo.trade.button.ButtonVO;

/**
 * <p>
 *��������ȡ������ťVO��
 * <p>�����ˣ�bsrl
 * <b>���ڣ�2007-11-20
 *
 */
public class ConsignBankCancelTransactButtonVO implements IBillButtonVO {

	public ConsignBankCancelTransactButtonVO() {
	}

	public ButtonVO getButtonVO() {
		ButtonVO bvo = new ButtonVO();
		bvo.setBtnNo(IFBMButton.ConsignBank_CancelTransact);
		bvo.setBtnChinaName("ȡ��ȷ��");
		bvo.setBtnCode("CANCELTRANSACT");
		bvo.setBtnName(nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm","UPPFBMComm-000006")/* @res"ȡ��ȷ��"*/);
		bvo.setHintStr(nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm","UPPFBMComm-000006")/* @res"ȡ��ȷ��"*/);
        bvo.setHotKey("C");
        bvo.setDisplayHotKey("(Shift+C)");
        bvo.setModifiers(Event.SHIFT_MASK);
        bvo.setOperateStatus(new int[]{IBillOperate.OP_NOTEDIT,IBillOperate.OP_INIT});
        bvo.setBusinessStatus(new int[]{IFBMStatus.Transact});

        return bvo;
	}
}