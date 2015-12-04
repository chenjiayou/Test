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
 *
 * <p>�����ˣ�lpf
 * <b>���ڣ�2007-10-9
 *
 */
public class ReturnCancelTransBtnVO implements IBillButtonVO {

	/**
	 *
	 */
	public ReturnCancelTransBtnVO() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see nc.ui.trade.button.IBillButtonVO#getButtonVO()
	 */
	public ButtonVO getButtonVO() {
		// TODO Auto-generated method stub
		ButtonVO btnVo=new ButtonVO();
		btnVo.setBtnChinaName("ȡ������");
		btnVo.setBtnName(nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm","UPPFBMComm-000033")/* @res"ȡ��ת��"*/);
		btnVo.setBtnNo(IFBMButton.ReturnBill_CancelTrans);
		btnVo.setBtnCode("CANCELTRANS");
		btnVo.setHotKey("X");
		btnVo.setDisplayHotKey("(Shift+X)");
		btnVo.setModifiers(Event.SHIFT_MASK);
		btnVo.setOperateStatus(new int[]{IBillOperate.OP_NOTEDIT});
		btnVo.setBusinessStatus(new int[]{IFBMStatus.TRANSFORM});
		return btnVo;
	}

}