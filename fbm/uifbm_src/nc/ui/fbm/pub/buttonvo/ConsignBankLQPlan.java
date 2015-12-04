package nc.ui.fbm.pub.buttonvo;

import java.awt.Event;

import nc.ui.fbm.pub.IFBMButton;
import nc.ui.trade.base.IBillOperate;
import nc.ui.trade.button.IBillButtonVO;
import nc.vo.trade.button.ButtonVO;

public class ConsignBankLQPlan implements IBillButtonVO {

	public ConsignBankLQPlan() {
		super();
	}
	
	public ButtonVO getButtonVO() {
		ButtonVO btnVo=new ButtonVO();
		btnVo.setBtnChinaName("联查托收计划");
		btnVo.setBtnName(nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm","UPPFBMComm-000393")/* @res"联查托收计划"*/);
		btnVo.setHintStr(nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm","UPPFBMComm-000393")/* @res"联查托收计划"*/);
		btnVo.setBtnCode("ConsignBank_LinkPlan");
		btnVo.setBtnNo(IFBMButton.ConsignBank_LinkPlan);

		btnVo.setOperateStatus(new int[]{IBillOperate.OP_NOTEDIT});
		//设置快捷键Ctrl+L
		btnVo.setModifiers(Event.CTRL_MASK);
		btnVo.setHotKey("L");
		btnVo.setDisplayHotKey("(Ctrl+L)");

		return btnVo;
	}

}
