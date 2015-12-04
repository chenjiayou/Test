package nc.ui.fbm.upgrade;

import java.awt.Event;

import nc.ui.trade.button.IBillButtonVO;
import nc.vo.trade.button.ButtonVO;

public class ClearBtnVO implements IBillButtonVO {

	public ButtonVO getButtonVO() {
		ButtonVO bvo = new ButtonVO();
		bvo.setBtnNo(IUpgradeBtn.UPGRADE_CLEAR);
		bvo.setBtnChinaName("�����������");
		bvo.setBtnCode("UPGRADE_CLEAR");
		bvo.setBtnName(nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm","UPPFBMComm-000161")/* @res"�����������"*/);
		bvo.setHintStr(nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm","UPPFBMComm-000161")/* @res"�����������"*/);
		
        bvo.setHotKey("C");
        bvo.setDisplayHotKey("(Shift+C)");
        bvo.setModifiers(Event.SHIFT_MASK);
		return bvo;
	}

}