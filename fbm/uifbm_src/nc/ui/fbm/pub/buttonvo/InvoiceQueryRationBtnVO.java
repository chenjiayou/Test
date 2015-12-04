/**
 *
 */
package nc.ui.fbm.pub.buttonvo;

import java.awt.Event;

import nc.ui.fbm.pub.IFBMButton;
import nc.ui.trade.base.IBillOperate;
import nc.ui.trade.button.IBillButtonVO;
import nc.vo.trade.button.ButtonVO;
import nc.vo.trade.pub.IBillStatus;

/**
 * <p>
 * Ʊ�ݿ�Ʊ�������Ŷ�Ȱ�ť
 * <p>�����ˣ�lpf
 * <b>���ڣ�2007-9-18
 *
 */
public class InvoiceQueryRationBtnVO implements IBillButtonVO {

	/**
	 *
	 */
	public InvoiceQueryRationBtnVO() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see nc.ui.trade.button.IBillButtonVO#getButtonVO()
	 */
	public ButtonVO getButtonVO() {
		ButtonVO btnVo=new ButtonVO();
		btnVo.setBtnChinaName("�������Ŷ��");
		btnVo.setBtnName(nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm","UPPFBMComm-000025")/* @res"�������Ŷ��"*/);
		btnVo.setBtnNo(IFBMButton.QUERYRATION);
		btnVo.setHotKey("Q");
		btnVo.setDisplayHotKey("(Shift+Q)");
		btnVo.setModifiers(Event.SHIFT_MASK);
		btnVo.setOperateStatus(new int[]{IBillOperate.OP_NOTEDIT});
		btnVo.setBusinessStatus(new int[]{IBillStatus.ALL});
		return btnVo;
	}

}