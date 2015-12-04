package nc.ui.fbm.pub.buttonvo;

import java.awt.Event;

import nc.ui.fbm.pub.IFBMButton;
import nc.ui.trade.base.IBillOperate;
import nc.ui.trade.button.IBillButtonVO;
import nc.vo.fbm.pub.constant.IFBMStatus;
import nc.vo.trade.button.ButtonVO;
/**
 *
 * 类功能说明：
     调剂清算回单-取消制证
 * 日期：2007-11-1
 * 程序员： wues
 *
 */
public class RecieptCancelVoucherVO implements IBillButtonVO{

	public RecieptCancelVoucherVO() {
		super();
	}

	public ButtonVO getButtonVO() {
		ButtonVO btnVO = new ButtonVO();
		btnVO.setBtnChinaName("取消制证");
		btnVO.setBtnName(nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm","UPPFBMComm-000027")/* @res"取消制证"*/);
		btnVO.setBtnCode("CANCELVOUCHER");
		btnVO.setBtnNo(IFBMButton.Reciept_CancelVoucher);
		btnVO.setOperateStatus(new int[]{IBillOperate.OP_NOTEDIT});
		//调剂清算回单单位制证后可取消制证
		btnVO.setBusinessStatus(new int[]{IFBMStatus.HAS_UNIT_VOUCHER});

		//取消制证
		btnVO.setModifiers(Event.ALT_MASK);
		btnVO.setHotKey("U");
		btnVO.setDisplayHotKey("(Alt+U)");

		return btnVO;
	}

}