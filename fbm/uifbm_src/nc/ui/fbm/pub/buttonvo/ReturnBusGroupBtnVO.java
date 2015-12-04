/**
 *
 */
package nc.ui.fbm.pub.buttonvo;

import java.awt.Event;

import nc.ui.fbm.pub.IFBMButton;
import nc.ui.trade.button.IBillButtonVO;
import nc.vo.trade.button.ButtonVO;

/**
 * <p>
 * ��Ʊ-ҵ����
 * <p>�����ˣ�lpf
 * <b>���ڣ�2007-10-9
 *
 */
public class ReturnBusGroupBtnVO implements IBillButtonVO {

	/**
	 *
	 */
	public ReturnBusGroupBtnVO() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see nc.ui.trade.button.IBillButtonVO#getButtonVO()
	 */
	public ButtonVO getButtonVO() {
		// TODO Auto-generated method stub
		ButtonVO btnVo=new ButtonVO();
		btnVo.setBtnChinaName("ҵ�����");
		btnVo.setBtnName(nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm","UPPFBMComm-000014")/* @res"ҵ�����"*/);
		btnVo.setBtnNo(IFBMButton.ReturnBill_BusGroup);
		btnVo.setHotKey("B");
		btnVo.setDisplayHotKey("(Shift+B)");
		btnVo.setModifiers(Event.SHIFT_MASK);
		btnVo.setChildAry(new int[] { IFBMButton.ReturnBill_TransToSFBill,IFBMButton.ReturnBill_CancelTrans });
//		btnVo.setBusinessStatus(new int[]{IBillStatus.CHECKPASS});
		return btnVo;
	}

}