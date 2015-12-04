package nc.ui.fbm.invoice.btnvo;

import java.awt.Event;

import nc.ui.fbm.pub.IFBMButton;
import nc.ui.trade.base.IBillOperate;
import nc.ui.trade.button.IBillButtonVO;
import nc.vo.trade.button.ButtonVO;

/**
 * 开票联查手续费支出项目的资金计划
 * @author xwq
 *
 * 2008-10-22
 */
public class QryChargePlanBtnVO implements IBillButtonVO{

	public ButtonVO getButtonVO() {
		ButtonVO btnVo=new ButtonVO();
		btnVo.setBtnChinaName("手续费计划");
		btnVo.setBtnName(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add","UPP3620ADD-000040")/*@res "手续费计划"*/);
		btnVo.setBtnNo(IFBMButton.BTN_QUERY_CHARGE_PLAN);
		btnVo.setBtnCode(IFBMButton.BTN_QUERY_CHARGE_PLAN_STR);
		btnVo.setHotKey("R");
		btnVo.setDisplayHotKey("(Shift+R)");
		btnVo.setModifiers(Event.SHIFT_MASK);
		btnVo.setOperateStatus(new int[]{IBillOperate.OP_NOTEDIT});
		return btnVo;
	}
}