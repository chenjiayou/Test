package nc.ui.fbm.pub.buttonvo;

import java.awt.Event;

import nc.ui.fbm.pub.IFBMButton;
import nc.ui.trade.base.IBillOperate;
import nc.ui.trade.button.IBillButtonVO;
import nc.vo.trade.button.ButtonVO;

/**
*
* 功能：
      背书办理-联查凭证
* 日期：2008-3-21
*/
public class EndoreLQVoucherVO implements IBillButtonVO { 

	public EndoreLQVoucherVO()
	{
		super();
	}
	
	public ButtonVO getButtonVO() {
		ButtonVO btnVo=new ButtonVO();
		btnVo.setBtnChinaName("联查凭证");
		btnVo.setBtnName(nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm","UPPFBMComm-000030")/* @res"联查凭证"*/);
		btnVo.setHintStr(nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm","UPPFBMComm-000030")/* @res"联查凭证"*/);
		btnVo.setBtnCode("Endore_LinkVoucher");
		btnVo.setBtnNo(IFBMButton.Endore_LinkVoucher);

		btnVo.setOperateStatus(new int[]{IBillOperate.OP_NOTEDIT});
		//设置快捷键Ctrl+V
		btnVo.setModifiers(Event.CTRL_MASK);
		btnVo.setHotKey("V");
		btnVo.setDisplayHotKey("(Ctrl+V)");

		return btnVo;
	}
}

