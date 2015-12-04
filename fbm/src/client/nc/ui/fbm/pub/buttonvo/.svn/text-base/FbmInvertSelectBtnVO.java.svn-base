package nc.ui.fbm.pub.buttonvo;

import java.awt.Event;

import nc.ui.fbm.pub.IFBMButton;
import nc.ui.trade.base.IBillOperate;
import nc.ui.trade.button.IBillButtonVO;
import nc.vo.trade.button.ButtonVO;

public class FbmInvertSelectBtnVO implements IBillButtonVO {

	public FbmInvertSelectBtnVO() {
		super();
	}

	public ButtonVO getButtonVO() {
		ButtonVO btnVo = new ButtonVO();
		btnVo.setBtnChinaName("反选");
		btnVo.setBtnName(nc.ui.ml.NCLangRes.getInstance().getStrByID("36201005", "UPP36201005-000031")/*
																									 * @res
																									 * "反选"
																									 */);
		btnVo.setHintStr(nc.ui.ml.NCLangRes.getInstance().getStrByID("36201005", "UPP36201005-000031")/*
																									 * @res
																									 * "反选"
																									 */);
		btnVo.setBtnCode("INVERTSELECT");
		btnVo.setBtnNo(IFBMButton.FBM_INVERTSELECT);
		btnVo.setOperateStatus(new int[] { IBillOperate.OP_NOTEDIT });
		btnVo.setModifiers(Event.CTRL_MASK);
		btnVo.setHotKey("I");
		btnVo.setDisplayHotKey("(Ctrl+I)");
		return btnVo;
	}

}
