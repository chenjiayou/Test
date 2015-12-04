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
 * <p>创建人：lpf
 * <b>日期：2007-10-10
 *
 */
public class CenterKeepInputBtnVO implements IBillButtonVO {


	public ButtonVO getButtonVO() {
		ButtonVO btnVo=new ButtonVO();
		btnVo.setBtnChinaName("确认");
		btnVo.setBtnName(nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm","UPPFBMComm-000005")/* @res"确认"*/);
		btnVo.setBtnNo(IFBMButton.CenterKeep_INPUT);
		btnVo.setBtnCode("INPUT");
		btnVo.setHotKey("R");
		btnVo.setDisplayHotKey("(Shift+R)");
		btnVo.setModifiers(Event.SHIFT_MASK);
		btnVo.setOperateStatus(new int[]{IBillOperate.OP_NOTEDIT});
		btnVo.setBusinessStatus(new int[]{IBillStatus.CHECKPASS,IFBMStatus.SUBMIT});
		return btnVo;
	}

}