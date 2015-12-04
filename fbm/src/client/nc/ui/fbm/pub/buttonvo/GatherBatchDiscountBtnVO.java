/**
 *
 */
package nc.ui.fbm.pub.buttonvo;

import java.awt.Event;

import nc.ui.fbm.pub.IFBMButton;
import nc.ui.trade.base.IBillOperate;
import nc.ui.trade.button.IBillButtonVO;
import nc.vo.fbm.pub.constant.IFBMExtendStatus;
import nc.vo.trade.button.ButtonVO;
import nc.vo.trade.pub.IBillStatus;

public class GatherBatchDiscountBtnVO implements IBillButtonVO {

	public GatherBatchDiscountBtnVO() {
		// TODO Auto-generated constructor stub
	}

	public ButtonVO getButtonVO() {
		ButtonVO btnVo = new ButtonVO();
		btnVo.setBtnChinaName("批量贴现");
		btnVo.setBtnName(nc.ui.ml.NCLangRes.getInstance().getStrByID("36201005", "UPP36201005-000035")/*
																									 * @res
																									 * "批量贴现"
																									 */);
		btnVo.setHintStr(nc.ui.ml.NCLangRes.getInstance().getStrByID("36201005", "UPP36201005-000035")/*
																									 * @res
																									 * "批量贴现"
																									 */);
		btnVo.setBtnNo(IFBMButton.Gather_BatchDiscount);
		btnVo.setHotKey("O");
		btnVo.setDisplayHotKey("(Shift+O)");
		btnVo.setModifiers(Event.SHIFT_MASK);
		btnVo.setOperateStatus(new int[] { IBillOperate.OP_NOTEDIT });
		btnVo.setBusinessStatus(new int[] { IBillStatus.CHECKPASS });
		btnVo.setExtendStatus(new int[] { IFBMExtendStatus.GATHERED,
				IFBMExtendStatus.BANKKEEPED });
		return btnVo;
	}

}