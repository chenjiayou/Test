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
 * 内部托管-取消制证
 * <p>创建人：lpf
 * <b>日期：2007-10-10
 *
 */
public class StorageDelVoucherBtnVO implements IBillButtonVO {

	/**
	 *
	 */
	public StorageDelVoucherBtnVO() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see nc.ui.trade.button.IBillButtonVO#getButtonVO()
	 */
	public ButtonVO getButtonVO() {
		ButtonVO btnVo=new ButtonVO();
		btnVo.setBtnChinaName("取消制证");
		btnVo.setBtnName(nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm","UPPFBMComm-000027")/* @res"取消制证"*/);
		//btnVo.setBtnNo(IFBMButton.Center_Storage_DelVoucher);
		btnVo.setBtnCode("DELGL");
		btnVo.setHotKey("Q");
		btnVo.setDisplayHotKey("(Shift+Q)");
		btnVo.setModifiers(Event.SHIFT_MASK);
		btnVo.setOperateStatus(new int[]{IBillOperate.OP_NOTEDIT});
		//btnVo.setBusinessStatus(new int[]{IFBMStatus.MADEVOUCHER});
		return btnVo;
	}

}