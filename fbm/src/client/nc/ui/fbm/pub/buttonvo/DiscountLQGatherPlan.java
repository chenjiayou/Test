package nc.ui.fbm.pub.buttonvo;

import java.awt.Event;

import nc.ui.fbm.pub.IFBMButton;
import nc.ui.trade.base.IBillOperate;
import nc.ui.trade.button.IBillButtonVO;
import nc.vo.trade.button.ButtonVO;

public class DiscountLQGatherPlan implements IBillButtonVO {

	public DiscountLQGatherPlan() {
		super();
	}
	
	public ButtonVO getButtonVO() {
		ButtonVO btnVo=new ButtonVO();
		btnVo.setBtnChinaName("联查收票计划");
		btnVo.setBtnName(nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm","UPPFBMComm-000392")/* @res"联查收票计划"*/);
		btnVo.setHintStr(nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm","UPPFBMComm-000392")/* @res"联查收票计划"*/);
		btnVo.setBtnCode("Discount_LinkGatherPlan");
		btnVo.setBtnNo(IFBMButton.Discount_LinkGatherPlan);

		btnVo.setOperateStatus(new int[]{IBillOperate.OP_NOTEDIT});
		//设置快捷键Ctrl+G
		btnVo.setModifiers(Event.CTRL_MASK);
		btnVo.setHotKey("G");
		btnVo.setDisplayHotKey("(Ctrl+G)");

		return btnVo;
	}

}
