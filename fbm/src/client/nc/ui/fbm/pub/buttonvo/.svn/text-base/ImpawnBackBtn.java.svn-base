package nc.ui.fbm.pub.buttonvo;

import java.awt.Event;

import nc.ui.fbm.pub.IFBMButton;
import nc.ui.trade.button.IBillButtonVO;
import nc.vo.trade.button.ButtonVO;
import nc.vo.trade.pub.IBillStatus;
/**
 * 功能：
       质押回收按钮VO
 * 日期：2007-9-27
 * 程序员：wues
 */
public class ImpawnBackBtn implements IBillButtonVO {

	public ImpawnBackBtn() {
		super();

	}

	public ButtonVO getButtonVO() {
		ButtonVO btnVo=new ButtonVO();
		btnVo.setBtnChinaName("质押回收");
		btnVo.setBtnName(nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm","UPPFBMComm-000022")/* @res"质押回收"*/);
		btnVo.setHintStr(nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm","UPPFBMComm-000022")/* @res"质押回收"*/);
		btnVo.setBtnNo(IFBMButton.BTN_IMPAWN_BACK);
		btnVo.setBtnCode("IMPAWNBACK");
		//btnVo.setOperateStatus(new int[]{IBillOperate.OP_NOTEDIT});
		btnVo.setBusinessStatus(new int[]{IBillStatus.CHECKPASS});//质押单已审批通过
		//质押回收Ctrl+B
		btnVo.setModifiers(Event.CTRL_MASK);
		btnVo.setHotKey("B");
		btnVo.setDisplayHotKey("(Ctrl+B)");

		return btnVo;
	}

}