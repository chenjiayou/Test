/**
 *
 */
package nc.ui.fbm.pub.buttonvo;

import java.awt.Event;

import nc.ui.fbm.pub.IFBMButton;
import nc.ui.trade.base.IBillOperate;
import nc.ui.trade.button.IBillButtonVO;
import nc.vo.fbm.pub.constant.IFBMExtendStatus;
import nc.vo.trade.button.ButtonVO;
import nc.vo.trade.pub.IBillStatus;

/**
 * <p>
 *
 * <p>创建人：lpf
 * <b>日期：2007-8-31
 *
 */
public class GatherToReturnBtnVO implements IBillButtonVO {

	/**
	 *
	 */
	public GatherToReturnBtnVO() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see nc.ui.trade.button.IBillButtonVO#getButtonVO()
	 */
	public ButtonVO getButtonVO() {
		// TODO Auto-generated method stub
		ButtonVO btnVo=new ButtonVO();
		btnVo.setBtnChinaName("退票");
		btnVo.setBtnName(nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm","UPPFBMComm-000021")/* @res"退票"*/);
		btnVo.setBtnNo(IFBMButton.Gather_ReturnBill);
		btnVo.setHotKey("J");
		btnVo.setDisplayHotKey("(Shift+J)");
		btnVo.setModifiers(Event.SHIFT_MASK);
		btnVo.setOperateStatus(new int[]{IBillOperate.OP_NOTEDIT});
		btnVo.setBusinessStatus(new int[]{IBillStatus.CHECKPASS});
		btnVo.setExtendStatus(new int[] { IFBMExtendStatus.GATHERED,
				IFBMExtendStatus.DISCOUNT, IFBMExtendStatus.ENDORE });
		return btnVo;
	}

}