package nc.ui.fbm.pub.buttonvo;

import java.awt.Event;

import nc.ui.fbm.pub.IFBMButton;
import nc.ui.trade.base.IBillOperate;
import nc.ui.trade.button.IBillButtonVO;
import nc.vo.trade.button.ButtonVO;

/**
 * ����ƻ�
 * @author xwq
 *
 * 2008-10-21
 */
public class Query2PlanBtnVO implements IBillButtonVO{

	public ButtonVO getButtonVO() {
		ButtonVO btnVo=new ButtonVO();
		btnVo.setBtnChinaName("�����ʽ�ƻ�");
		btnVo.setBtnName(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add","UPP3620ADD-000051")/*@res "�����ʽ�ƻ�"*/);
		btnVo.setBtnNo(IFBMButton.BTN_QUERY_PLAN);
		btnVo.setBtnCode(IFBMButton.BTN_QUERY_PLAN_STR);
		btnVo.setHotKey("Q");
		btnVo.setDisplayHotKey("(Shift+Q)");
		btnVo.setModifiers(Event.SHIFT_MASK);
		btnVo.setOperateStatus(new int[]{IBillOperate.OP_NOTEDIT});
		return btnVo;
	}

}