package nc.ui.fbm.pub.buttonvo;

import java.awt.Event;

import nc.ui.fbm.pub.IFBMButton;
import nc.ui.trade.base.IBillOperate;
import nc.ui.trade.button.IBillButtonVO;
import nc.vo.fbm.pub.constant.IFBMStatus;
import nc.vo.trade.button.ButtonVO;

/**
 *
 * 类功能说明： 调剂清算回单-制证按钮 日期：2007-11-1 程序员： wues
 *
 */
public class RecieptVoucherVO implements IBillButtonVO {

	public RecieptVoucherVO() {
		super();
	}

	public ButtonVO getButtonVO() {
		ButtonVO btnVO = new ButtonVO();
		btnVO.setBtnChinaName("制证");
		btnVO.setBtnName(nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm","UPPFBMComm-000028")/* @res"制证"*/);
		btnVO.setBtnCode("VOUCHER");
		btnVO.setBtnNo(IFBMButton.Reciept_Voucher);
		btnVO.setOperateStatus(new int[]{IBillOperate.OP_NOTEDIT});
		//清算回单已中心制证后单位可制单位的证
		btnVO.setBusinessStatus(new int[]{IFBMStatus.HAS_VOUCHER});

		//设置快捷键Ctrl+U
		btnVO.setModifiers(Event.CTRL_MASK);
		btnVO.setHotKey("U");
		btnVO.setDisplayHotKey("(Ctrl+U)");

		return btnVO;
	}
}