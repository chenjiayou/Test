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

/**
 * <p>
 *
 * <p>创建人：lpf
 * <b>日期：2007-10-10
 *
 */
public class CenterKeepCancelInputBtnVO implements IBillButtonVO {

	/* (non-Javadoc)
	 * @see nc.ui.trade.button.IBillButtonVO#getButtonVO()
	 */
	public ButtonVO getButtonVO() {
		ButtonVO btnVo=new ButtonVO();
		btnVo.setBtnChinaName("取消确认");
		btnVo.setBtnName(nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm","UPPFBMComm-000004")/* @res"取消确认"*/);
		btnVo.setBtnNo(IFBMButton.CenterKeep_CANCELINPUT); 
		btnVo.setBtnCode("CANCELIN");
		btnVo.setHotKey("U");
		btnVo.setDisplayHotKey("(Shift+U)");
		btnVo.setModifiers(Event.SHIFT_MASK);
		btnVo.setOperateStatus(new int[]{IBillOperate.OP_NOTEDIT});
		btnVo.setBusinessStatus(new int[]{IFBMStatus.INPUT_SUCCESS});
		return btnVo;
	}

}