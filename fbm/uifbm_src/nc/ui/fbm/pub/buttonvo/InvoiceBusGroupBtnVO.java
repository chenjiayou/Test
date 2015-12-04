/**
 *
 */
package nc.ui.fbm.pub.buttonvo;

import java.awt.Event;

import nc.ui.fbm.pub.IFBMButton;
import nc.ui.trade.button.IBillButtonVO;
import nc.vo.fbm.pub.constant.IFBMStatus;
import nc.vo.trade.button.ButtonVO;
import nc.vo.trade.pub.IBillStatus;

/**
 * <p>
 *
 * <p>�����ˣ�lpf
 * <b>���ڣ�2007-9-19
 *
 */
public class InvoiceBusGroupBtnVO implements IBillButtonVO {

	/**
	 *
	 */
	public InvoiceBusGroupBtnVO() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see nc.ui.trade.button.IBillButtonVO#getButtonVO()
	 */
	public ButtonVO getButtonVO() {
		ButtonVO btnVo=new ButtonVO();
		btnVo.setBtnChinaName("ҵ�����");
		btnVo.setBtnName(nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm","UPPFBMComm-000014")/* @res"ҵ�����"*/);
		btnVo.setBtnNo(IFBMButton.Invoice_BusGroup);
		btnVo.setHintStr(nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm","UPPFBMComm-000014")/* @res"ҵ�����"*/);
		btnVo.setHotKey("B");
		btnVo.setDisplayHotKey("(Shift+B)");
		btnVo.setModifiers(Event.SHIFT_MASK);
		btnVo.setBusinessStatus(new int[]{IBillStatus.CHECKPASS,IFBMStatus.Create});
		btnVo.setChildAry(new int[] { IFBMButton.Invoice_BillPay,IFBMButton.Invoice_Return });
		return btnVo;
	}

}