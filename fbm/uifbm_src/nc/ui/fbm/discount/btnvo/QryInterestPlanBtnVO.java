package nc.ui.fbm.discount.btnvo;

import java.awt.Event;

import nc.ui.fbm.pub.IFBMButton;
import nc.ui.trade.base.IBillOperate;
import nc.ui.trade.button.IBillButtonVO;
import nc.vo.trade.button.ButtonVO;

/**
 * 联查利息计划
 * @author xwq
 *
 * 2008-10-22
 */
public class QryInterestPlanBtnVO implements IBillButtonVO{

	public ButtonVO getButtonVO() {
		ButtonVO btnVo=new ButtonVO();
		btnVo.setBtnChinaName("利息计划");
		btnVo.setBtnName(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add","UPP3620ADD-000008")/*@res "利息计划"*/);
		btnVo.setBtnNo(IFBMButton.BTN_QUERY_INTEREST_PLAN);
		btnVo.setBtnCode(IFBMButton.BTN_QUERY_INTEREST_PLAN_STR);
		btnVo.setHotKey("R");
		btnVo.setDisplayHotKey("(Shift+R)");
		btnVo.setModifiers(Event.SHIFT_MASK);
		btnVo.setOperateStatus(new int[]{IBillOperate.OP_NOTEDIT});
		return btnVo;
	}
}