/**
 *
 */
package nc.ui.fbm.pub.buttonvo;

import java.awt.Event;

import nc.ui.fbm.pub.IFBMButton;
import nc.ui.trade.base.IBillOperate;
import nc.ui.trade.button.IBillButtonVO;
import nc.vo.trade.button.ButtonVO;

/**
 * <p>
 * 联查收付款单据
 * <p>创建人：lpf
 * <b>日期：2007-10-9
 *
 */
public class GatherLQuerySFBillBtnVO implements IBillButtonVO {

	/**
	 *
	 */
	public GatherLQuerySFBillBtnVO() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see nc.ui.trade.button.IBillButtonVO#getButtonVO()
	 */
	public ButtonVO getButtonVO() {
		ButtonVO btnVo=new ButtonVO();
		btnVo.setBtnChinaName("联查收付款单据");
		btnVo.setBtnName(nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm","UPPFBMComm-000008")/* @res"联查收付款单据"*/);
		btnVo.setBtnNo(IFBMButton.Gather_LQuerySFBill);
		btnVo.setHotKey("M");
		btnVo.setDisplayHotKey("(Shift+M)");
		btnVo.setModifiers(Event.SHIFT_MASK);
		btnVo.setOperateStatus(new int[]{IBillOperate.OP_NOTEDIT});
//		btnVo.setBusinessStatus(new int[]{IBillStatus.CHECKPASS});
		return btnVo;
	}

}