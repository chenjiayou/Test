package nc.ui.fbm.upgrade;

import java.awt.Event;

import nc.ui.trade.button.IBillButtonVO;
import nc.vo.trade.button.ButtonVO;

public class UpgradeFbmBtnVO implements IBillButtonVO {

	public ButtonVO getButtonVO() {
		ButtonVO bvo = new ButtonVO();
		bvo.setBtnNo(IUpgradeBtn.UPGRADE_FBM);
		bvo.setBtnChinaName("升级资金票据");
		bvo.setBtnCode("UPGRADE_FBM");
		bvo.setBtnName(nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm","UPPFBMComm-000163")/* @res"升级资金票据"*/);
		bvo.setHintStr(nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm","UPPFBMComm-000163")/* @res"升级资金票据"*/);
		
        bvo.setHotKey("F");
        bvo.setDisplayHotKey("(Shift+F)");
        bvo.setModifiers(Event.SHIFT_MASK);
        
		return bvo;
	}

}