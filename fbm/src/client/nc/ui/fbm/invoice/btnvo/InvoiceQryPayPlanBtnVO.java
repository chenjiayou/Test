package nc.ui.fbm.invoice.btnvo;

import java.awt.Event;

import nc.ui.fbm.pub.IFBMButton;
import nc.ui.trade.base.IBillOperate;
import nc.ui.trade.button.IBillButtonVO;
import nc.vo.trade.button.ButtonVO;

/**
 * ��Ʊ���鸶Ʊ�ƻ���Ŀ��Ӧ���ʽ�ƻ�
 * @author xwq
 *
 * 2008-10-22
 */
public class InvoiceQryPayPlanBtnVO implements IBillButtonVO{

	public ButtonVO getButtonVO() {
		ButtonVO btnVo=new ButtonVO();
		btnVo.setBtnChinaName("��Ʊ����ƻ�");
		btnVo.setBtnName(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add","UPP3620ADD-000039")/*@res "��Ʊ�ƻ�"*/);
		btnVo.setBtnNo(IFBMButton.BTN_QUERY_INVOICE_PAY_PLAN);
		btnVo.setBtnCode(IFBMButton.BTN_QUERY_INVOICE_PAY_PLAN_STR);
		btnVo.setHotKey("H");
		btnVo.setDisplayHotKey("(Shift+H)");
		btnVo.setModifiers(Event.SHIFT_MASK);
		btnVo.setOperateStatus(new int[]{IBillOperate.OP_NOTEDIT});
		return btnVo;
	}
}
