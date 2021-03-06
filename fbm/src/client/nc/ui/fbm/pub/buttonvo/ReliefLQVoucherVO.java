package nc.ui.fbm.pub.buttonvo;

import java.awt.Event;

import nc.ui.fbm.pub.IFBMButton;
import nc.ui.trade.base.IBillOperate;
import nc.ui.trade.button.IBillButtonVO;
import nc.vo.trade.button.ButtonVO;

/**
 *
 * 功能：
       调剂出库-联查凭证
 * 日期：2007-10-20
 * 程序员：wues
 */
public class ReliefLQVoucherVO implements IBillButtonVO {

	public ButtonVO getButtonVO() {
		ButtonVO btnVo=new ButtonVO();
		btnVo.setBtnChinaName("联查凭证");
		btnVo.setBtnName(nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm","UPPFBMComm-000030")/* @res"联查凭证"*/);
		btnVo.setBtnCode("RELIEF_LQVOUCHER");
		btnVo.setBtnNo(IFBMButton.Relief_LQVoucher);

		btnVo.setOperateStatus(new int[]{IBillOperate.OP_NOTEDIT});
		//设置快捷键Ctrl+V
		btnVo.setModifiers(Event.CTRL_MASK);
		btnVo.setHotKey("V");
		btnVo.setDisplayHotKey("(Ctrl+V)");

		return btnVo;
	}

}