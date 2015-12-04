package nc.ui.fbm.pub.buttonvo;

import java.awt.Event;

import nc.ui.fbm.pub.IFBMButton;
import nc.ui.trade.base.IBillOperate;
import nc.ui.trade.button.IBillButtonVO;
import nc.vo.trade.button.ButtonVO;

public class FbmSelectAllBtnVO implements IBillButtonVO {

	public FbmSelectAllBtnVO() {
		super();
	}

	public ButtonVO getButtonVO() {
		ButtonVO btnVo = new ButtonVO();
		btnVo.setBtnChinaName("ȫѡ");
		btnVo.setBtnName(nc.ui.ml.NCLangRes.getInstance().getStrByID("36201005", "UPP36201005-000032")/*
																									 * @res
																									 * "ȫѡ"
																									 */);
		btnVo.setHintStr(nc.ui.ml.NCLangRes.getInstance().getStrByID("36201005", "UPP36201005-000032")/*
																									 * @res
																									 * "ȫѡ"
																									 */);
		btnVo.setBtnCode("SELECTALL");
		btnVo.setBtnNo(IFBMButton.FBM_SELECTALL);
		btnVo.setOperateStatus(new int[] { IBillOperate.OP_NOTEDIT });
		btnVo.setModifiers(Event.CTRL_MASK);
		btnVo.setHotKey("A");
		btnVo.setDisplayHotKey("(Ctrl+A)");
		return btnVo;
	}

}
