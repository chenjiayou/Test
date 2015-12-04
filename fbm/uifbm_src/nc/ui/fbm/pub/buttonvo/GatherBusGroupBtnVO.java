/**
 *
 */
package nc.ui.fbm.pub.buttonvo;

import java.awt.Event;

import nc.ui.fbm.pub.IFBMButton;
import nc.ui.trade.button.IBillButtonVO;
import nc.vo.trade.button.ButtonVO;
import nc.vo.trade.pub.IBillStatus;
//import nc.vo.trade.pub.IBillStatus;

/**
 * <p>
 * ��Ʊ�Ǽ�-ҵ�����ť����
 * <p>�����ˣ�lpf
 * <b>���ڣ�2007-8-24
 *
 */
public class GatherBusGroupBtnVO implements IBillButtonVO {

	/**
	 *
	 */
	public GatherBusGroupBtnVO() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see nc.ui.trade.button.IBillButtonVO#getButtonVO()
	 */
	public ButtonVO getButtonVO() {
		ButtonVO btnVo=new ButtonVO();
		btnVo.setBtnChinaName("ҵ�����");
		btnVo.setBtnName(nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm","UPPFBMComm-000014")/* @res"ҵ�����"*/);
		btnVo.setHintStr(nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm","UPPFBMComm-000014")/* @res"ҵ�����"*/);
		btnVo.setHotKey("B");
		btnVo.setDisplayHotKey("(Shift+B)");
		btnVo.setModifiers(Event.SHIFT_MASK);
		btnVo.setBtnNo(IFBMButton.Gather_BusGroup);
		btnVo.setChildAry(new int[] { IFBMButton.Gather_BankBack,
				IFBMButton.Gather_BankKeep, IFBMButton.Gather_BankDiscount,
				IFBMButton.Gather_Consign,IFBMButton.Gather_Impawn,IFBMButton.Gather_ReturnBill });
		btnVo.setBusinessStatus(new int[]{IBillStatus.CHECKPASS});
		return btnVo;
	}

}