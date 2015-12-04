package nc.ui.fbm.pub.buttonvo;

import java.awt.Event;

import nc.ui.fbm.pub.IFBMButton;
import nc.ui.trade.base.IBillOperate;
import nc.ui.trade.button.IBillButtonVO;
import nc.vo.trade.button.ButtonVO;
/**
 *
 * 功能：
       调剂出库-联查账户余额
 * 日期：2007-10-20
 * 程序员：wues
 */
public class ReliefLQAccountBalanceVO implements IBillButtonVO {

	public ReliefLQAccountBalanceVO() {
		super();

	}

	public ButtonVO getButtonVO() {
		ButtonVO btnVo=new ButtonVO();
		btnVo.setBtnChinaName("联查账户余额");
		btnVo.setBtnName(nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm","UPPFBMComm-000031")/* @res"联查账户余额"*/);
		btnVo.setBtnCode("RELIEF_LQACCOUNTBALANCE");
		btnVo.setBtnNo(IFBMButton.Relief_LQAccoutBalance);
		btnVo.setOperateStatus(new int[]{IBillOperate.OP_NOTEDIT});

		//设置快捷键Ctrl+A
		btnVo.setModifiers(Event.CTRL_MASK);
		btnVo.setHotKey("A");
		btnVo.setDisplayHotKey("(Ctrl+A)");

		return btnVo;
	}

}