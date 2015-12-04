package nc.ui.fbm.pub.buttonvo;

import java.awt.Event;

import nc.ui.fbm.pub.IFBMButton;
import nc.ui.trade.button.IBillButtonVO;
import nc.vo.fbm.pub.constant.IFBMStatus;
import nc.vo.trade.button.ButtonVO;

/**
 * 取消质押回收
 * @author xwq
 *
 * 2008-9-22
 */
public class ImpawnCancelBackBtnVO implements IBillButtonVO {

	public ButtonVO getButtonVO() {
		ButtonVO btnVo=new ButtonVO();
		btnVo.setBtnChinaName("取消回收");
		btnVo.setBtnName(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add","UPP3620ADD-000049")/*@res "取消回收"*/);
		btnVo.setHintStr(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add","UPP3620ADD-000049")/*@res "取消回收"*/);
		btnVo.setBtnNo(IFBMButton.BTN_CANCEL_IMPAWNBACK);
		btnVo.setBtnCode(IFBMButton.BTN_CANCEL_IMPAWNBACK_STR);
		//btnVo.setOperateStatus(new int[]{IBillOperate.OP_NOTEDIT});
		btnVo.setBusinessStatus(new int[]{IFBMStatus.IMPAWN_BACK});//质押单已审批通过
		//取消质押回收Ctrl+T
		btnVo.setModifiers(Event.CTRL_MASK);
		btnVo.setHotKey("T");
		btnVo.setDisplayHotKey("(Ctrl+T)");

		return btnVo;
	}

}