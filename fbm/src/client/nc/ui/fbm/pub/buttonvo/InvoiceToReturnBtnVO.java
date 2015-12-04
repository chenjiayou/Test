/**
 *
 */
package nc.ui.fbm.pub.buttonvo;

import java.awt.Event;

import nc.ui.fbm.pub.IFBMButton;
import nc.ui.trade.base.IBillOperate;
import nc.ui.trade.button.IBillButtonVO;
import nc.vo.fbm.pub.constant.IFBMStatus;
import nc.vo.trade.button.ButtonVO;
import nc.vo.trade.pub.IBillStatus;

/**
 * <p>
 * ��Ʊ�Ǽ�-��Ʊ
 * <p>�����ˣ�lpf
 * <b>���ڣ�2007-9-19
 *
 */
public class InvoiceToReturnBtnVO implements IBillButtonVO {

	/**
	 *
	 */
	public InvoiceToReturnBtnVO() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see nc.ui.trade.button.IBillButtonVO#getButtonVO()
	 */
	public ButtonVO getButtonVO() {
		ButtonVO btnVo=new ButtonVO();
		btnVo.setBtnChinaName("��Ʊ");
		btnVo.setBtnName(nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm","UPPFBMComm-000021")/* @res"��Ʊ"*/);
		btnVo.setBtnNo(IFBMButton.Invoice_Return);
		btnVo.setHotKey("E");
		btnVo.setDisplayHotKey("(Shift+E)");
		btnVo.setModifiers(Event.SHIFT_MASK);
		btnVo.setOperateStatus(new int[]{IBillOperate.OP_NOTEDIT});
		btnVo.setBusinessStatus(new int[]{IBillStatus.CHECKPASS,IFBMStatus.Create});
		return btnVo;
	}

}