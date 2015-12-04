package nc.ui.fbm.pub.buttonvo;

import java.awt.Event;

import nc.ui.fbm.pub.IFBMButton;
import nc.ui.trade.base.IBillOperate;
import nc.ui.trade.button.IBillButtonVO;
import nc.vo.fbm.pub.constant.IFBMStatus;
import nc.vo.trade.button.ButtonVO;

/**
 * <p>
 *调剂清算单取消制证按钮VO
 * <p>创建人：bsrl
 * <b>日期：2007-11-05
 */
public class ReckonCancelVoucherButtonVO implements IBillButtonVO {
	/**
	 *
	 */
	public ReckonCancelVoucherButtonVO() {
	}

	/* (non-Javadoc)
	 * @see nc.ui.trade.button.IBillButtonVO#getButtonVO()
	 */
	public ButtonVO getButtonVO() {
		ButtonVO btnVo=new ButtonVO();
		btnVo.setBtnChinaName("取消制证");
		btnVo.setBtnName(nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm","UPPFBMComm-000027")/* @res"取消制证"*/);
		btnVo.setBtnNo(IFBMButton.Reckon_CancelVoucher);
		btnVo.setBtnCode("UNVOUCHER");
		btnVo.setHotKey("U");
		btnVo.setDisplayHotKey("(Shift+U)");
		btnVo.setModifiers(Event.SHIFT_MASK);
		btnVo.setOperateStatus(new int[]{IBillOperate.OP_NOTEDIT});
		btnVo.setBusinessStatus(new int[]{IFBMStatus.HAS_VOUCHER});
		return btnVo;
	}

}