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
 * <b>���ڣ�2007-10-10
 *
 */
public class StorageQueryGLBtnVO implements IBillButtonVO {

	/**
	 *
	 */
	public StorageQueryGLBtnVO() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see nc.ui.trade.button.IBillButtonVO#getButtonVO()
	 */
	public ButtonVO getButtonVO() {
		ButtonVO btnVo=new ButtonVO();
		btnVo.setBtnChinaName("����ƾ֤");
		btnVo.setBtnName(nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm","UPPFBMComm-000030")/* @res"����ƾ֤"*/);
		btnVo.setBtnNo(IFBMButton.Center_Storage_QueryGL);
		btnVo.setHintStr(nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm","UPPFBMComm-000034")/* @res"��������ƾ֤"*/);
		btnVo.setHotKey("P");
		btnVo.setDisplayHotKey("(Shift+P)");
		btnVo.setModifiers(Event.SHIFT_MASK);
		btnVo.setOperateStatus(new int[]{IBillOperate.OP_NOTEDIT});
		//btnVo.setBusinessStatus(new int[]{IBillStatus.CHECKPASS,IFBMStatus.HAS_OUTPUT,IFBMStatus.MADEVOUCHER,IFBMStatus.RETURNED,IFBMStatus.INPUT_SUCCESS,IFBMStatus.HAS_UNIT_VOUCHER});
//		btnVo.setExtendStatus(new int[]{IFBMExtendStatus.GATHERED});
		return btnVo;
	}

}