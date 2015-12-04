package nc.ui.fbm.discount.btnvo;

import java.awt.Event;

import nc.ui.fbm.pub.IFBMButton;
import nc.ui.trade.base.IBillOperate;
import nc.ui.trade.button.IBillButtonVO;
import nc.vo.trade.button.ButtonVO;

public class QryBalancePlanBtnVO implements IBillButtonVO{

	public ButtonVO getButtonVO() {
		ButtonVO btnVo=new ButtonVO();
		btnVo.setBtnChinaName("余额计划");
		btnVo.setBtnName(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add","UPP3620ADD-000007")/*@res "余额计划"*/);
		btnVo.setBtnNo(IFBMButton.BTN_QUERY_BALANCE_PLAN);
		btnVo.setBtnCode(IFBMButton.BTN_QUERY_BALANCE_PLAN_STR);
		btnVo.setHotKey("O");
		btnVo.setDisplayHotKey("(Shift+O)");
		btnVo.setModifiers(Event.SHIFT_MASK);
		btnVo.setOperateStatus(new int[]{IBillOperate.OP_NOTEDIT});
		return btnVo;
	}
}
