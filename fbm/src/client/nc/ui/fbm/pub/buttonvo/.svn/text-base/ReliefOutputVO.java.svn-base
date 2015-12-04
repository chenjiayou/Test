package nc.ui.fbm.pub.buttonvo;

import java.awt.Event;

import nc.ui.fbm.pub.IFBMButton;
import nc.ui.fbm.relief.status.IReliefStatus;
import nc.ui.trade.base.IBillOperate;
import nc.ui.trade.button.IBillButtonVO;
import nc.vo.trade.button.ButtonVO;
import nc.vo.trade.pub.IBillStatus;

/**
 *
 * 功能：
       调剂出库-出库按钮
 * 日期：2007-10-20
 * 程序员：wues
 */
public class ReliefOutputVO implements IBillButtonVO {

	public ReliefOutputVO() {
		super();

	}

	public ButtonVO getButtonVO() {
		ButtonVO btnVo=new ButtonVO();
		btnVo.setBtnChinaName("出库");
		btnVo.setBtnName(nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm","UPPFBMComm-000003")/* @res"出库"*/);
		btnVo.setBtnNo(IFBMButton.Relief_Output);
		btnVo.setBtnCode("OUTPUT");
		//设置按钮在未出库时可用
		btnVo.setExtendStatus(new int[]{IReliefStatus.NOT_OUT});
		btnVo.setOperateStatus(new int[]{IBillOperate.OP_NOTEDIT});
		//btnVo.setBusinessStatus(new int[]{IBillStatus.CHECKPASS});

		//设置快捷键Ctrl+O
		btnVo.setModifiers(Event.CTRL_MASK);
		btnVo.setHotKey("O");
		btnVo.setDisplayHotKey("(Ctrl+O)");

		return btnVo;
	}

}