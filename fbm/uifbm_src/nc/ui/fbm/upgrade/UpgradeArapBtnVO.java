package nc.ui.fbm.upgrade;

import java.awt.Event;

import nc.ui.trade.button.IBillButtonVO;
import nc.vo.trade.button.ButtonVO;

public class UpgradeArapBtnVO implements IBillButtonVO {

	public ButtonVO getButtonVO() {
		// TODO Auto-generated method stub
		ButtonVO bvo = new ButtonVO();
		bvo.setBtnNo(IUpgradeBtn.UPGRADE_ARAP);
		bvo.setBtnChinaName("升级财务票据");
		bvo.setBtnCode("UPGRADE_ARAP");
		bvo.setBtnName(nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm","UPPFBMComm-000162")/* @res"升级财务票据"*/);
		bvo.setHintStr(nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm","UPPFBMComm-000162")/* @res"升级财务票据"*/);
		
        bvo.setHotKey("A");
        bvo.setDisplayHotKey("(Shift+A)");
        bvo.setModifiers(Event.SHIFT_MASK);
		return bvo;
	}

}