package nc.ui.fbm.pub.buttonvo;

import java.awt.Event;

import nc.ui.fbm.pub.IFBMButton;
import nc.ui.trade.base.IBillOperate;
import nc.ui.trade.button.IBillButtonVO;
import nc.vo.fbm.pub.constant.IFBMStatus;
import nc.vo.trade.button.ButtonVO;

public class EndoreLQClearVoucherVO implements IBillButtonVO {

	public EndoreLQClearVoucherVO() {
		super();
	}

	public ButtonVO getButtonVO() {
		ButtonVO btnVo=new ButtonVO();
		btnVo.setBtnChinaName("联查冲销凭证");
		btnVo.setBtnName(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add","UPP3620ADD-000045")/*@res "联查冲销凭证"*/);
		btnVo.setHintStr(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add","UPP3620ADD-000045")/*@res "联查冲销凭证"*/);
		btnVo.setBtnCode("Endore_LinkClearVoucher");
		btnVo.setBtnNo(IFBMButton.Endore_LinkClearVoucher);

		btnVo.setOperateStatus(new int[]{IBillOperate.OP_NOTEDIT});
		btnVo.setBusinessStatus(new int[]{IFBMStatus.HAS_CLEAR});//已冲销后可联查冲销凭证
		//设置快捷键Ctrl+V
		btnVo.setModifiers(Event.CTRL_MASK);
		btnVo.setHotKey("V");
		btnVo.setDisplayHotKey("(Alt+V)");

		return btnVo;
	}

}