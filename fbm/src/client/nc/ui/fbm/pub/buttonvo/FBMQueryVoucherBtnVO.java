package nc.ui.fbm.pub.buttonvo;

import java.awt.Event;

import nc.ui.fbm.pub.IFBMButton;
import nc.ui.trade.base.IBillOperate;
import nc.ui.trade.button.IBillButtonVO;
import nc.vo.trade.button.ButtonVO;

public class FBMQueryVoucherBtnVO implements IBillButtonVO {

	public ButtonVO getButtonVO() {
		ButtonVO btnVo=new ButtonVO();
		btnVo.setBtnChinaName("����ƾ֤");
		btnVo.setBtnName(nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm","UPPFBMComm-000030")/* @res"����ƾ֤"*/);
		btnVo.setHintStr(nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm","UPPFBMComm-000030")/* @res"����ƾ֤"*/);
		btnVo.setBtnCode(IFBMButton.FBM_QUERYVOUCHER_STR);
		btnVo.setBtnNo(IFBMButton.FBM_QUERYVOUCHER);

		btnVo.setOperateStatus(new int[]{IBillOperate.OP_NOTEDIT});
		//���ÿ�ݼ�Ctrl+V
		btnVo.setModifiers(Event.CTRL_MASK);
		btnVo.setHotKey("V");
		btnVo.setDisplayHotKey("(Ctrl+V)");

		return btnVo;
	}

}
