package nc.ui.fbm.pub.buttonvo;

import java.awt.Event;

import nc.ui.fbm.pub.IFBMButton;
import nc.ui.trade.base.IBillOperate;
import nc.ui.trade.button.IBillButtonVO;
import nc.vo.trade.button.ButtonVO;
import nc.vo.trade.pub.IBillStatus;
/**
 *
 * 功能：
       背书办理-制证
 * 日期：2008-3-20
 */
public class EndoreVoucherVO implements IBillButtonVO {

	public EndoreVoucherVO() {
		super();

	}

	public ButtonVO getButtonVO() {
		ButtonVO btnVo=new ButtonVO();
		btnVo.setBtnChinaName("制证");
		btnVo.setBtnName(nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm","UPPFBMComm-000028")/* @res"制证"*/);
		btnVo.setBtnNo(IFBMButton.Endore_Voucher);
		btnVo.setBtnCode("VOUCHER");
		btnVo.setOperateStatus(new int[]{IBillOperate.OP_NOTEDIT});
		btnVo.setBusinessStatus(new int[]{IBillStatus.CHECKPASS});//审批通过后可制证
		//设置快捷键Ctrl+U
		btnVo.setModifiers(Event.CTRL_MASK);
		btnVo.setHotKey("M");
		btnVo.setDisplayHotKey("(Ctrl+M)");

		return btnVo;
	}

}