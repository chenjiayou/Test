package nc.ui.fbm.pub.buttonvo;

import java.awt.Event;

import nc.ui.fbm.pub.IFBMButton;
import nc.ui.trade.base.IBillOperate;
import nc.ui.trade.button.IBillButtonVO;
import nc.vo.trade.button.ButtonVO;

public class FbmCancelSelectBtnVO implements IBillButtonVO {

	public FbmCancelSelectBtnVO() {
		super();
	}

	public ButtonVO getButtonVO() {
		ButtonVO btnVo = new ButtonVO();
		btnVo.setBtnChinaName("全消");
		btnVo.setBtnName(nc.ui.ml.NCLangRes.getInstance().getStrByID("36201005", "UPP36201005-000030")/*
																									 * @res
																									 * "全消"
																									 */);
		btnVo.setHintStr(nc.ui.ml.NCLangRes.getInstance().getStrByID("36201005", "UPP36201005-000030")/*
																									 * @res
																									 * "全消"
																									 */);
		btnVo.setBtnCode("CANCELSELECT");
		btnVo.setBtnNo(IFBMButton.FBM_CANCELSELECT);
		btnVo.setOperateStatus(new int[] { IBillOperate.OP_NOTEDIT });
		btnVo.setModifiers(Event.CTRL_MASK);
		btnVo.setHotKey("N");
		btnVo.setDisplayHotKey("(Ctrl+N)");
		return btnVo;
	}

}
