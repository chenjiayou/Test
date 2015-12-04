package nc.ui.fbm.pub.buttonvo;

import java.awt.Event;

import nc.ui.fbm.pub.IFBMButton;
import nc.ui.trade.base.IBillOperate;
import nc.ui.trade.button.IBillButtonVO;
import nc.vo.fbm.pub.constant.IFBMStatus;
import nc.vo.trade.button.ButtonVO;

public class EndoreDestoryVO implements IBillButtonVO {

	public EndoreDestoryVO()
	{
		super();
	}
	public ButtonVO getButtonVO() {
		ButtonVO btnVo=new ButtonVO();
		btnVo.setBtnChinaName("冲销");
		btnVo.setBtnName(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("common","UC001-0000034")/*@res "冲销"*/);
		btnVo.setBtnCode("DESTROY");
		btnVo.setBtnNo(IFBMButton.Endore_Destroy);

		btnVo.setOperateStatus(new int[]{IBillOperate.OP_NOTEDIT});
		btnVo.setBusinessStatus(new int[]{IFBMStatus.HAS_VOUCHER_VAR});//已制证后可冲销
		//设置快捷键Ctrl+D
		btnVo.setModifiers(Event.CTRL_MASK);
		btnVo.setHotKey("D");
		btnVo.setDisplayHotKey("(Ctrl+D)");

		return btnVo;
	}

}