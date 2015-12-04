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

/**
 * <p>
 * ��Ʊ�Ǽ�-��������
 * <p>
 * �����ˣ�lpf <b>���ڣ�2007-9-6
 * 
 */
public class GatherBatchConsignBtnVO implements IBillButtonVO {

	/**
	 *
	 */
	public GatherBatchConsignBtnVO() {
		// TODO Auto-generated constructor stub
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nc.ui.trade.button.IBillButtonVO#getButtonVO()
	 */
	public ButtonVO getButtonVO() {
		ButtonVO btnVo = new ButtonVO();
		btnVo.setBtnChinaName("������������");
		btnVo.setBtnName(nc.ui.ml.NCLangRes.getInstance().getStrByID("36201005", "UPP36201005-000034")/*
																									 * @res
																									 * "������������"
																									 */);
		btnVo.setHintStr(nc.ui.ml.NCLangRes.getInstance().getStrByID("36201005", "UPP36201005-000034")/*
																									 * @res
																									 * "������������"
																									 */);
		btnVo.setBtnNo(IFBMButton.Gather_BatchConsign);
		btnVo.setHotKey("B");
		btnVo.setDisplayHotKey("(Shift+B)");
		btnVo.setModifiers(Event.SHIFT_MASK);
		btnVo.setOperateStatus(new int[] { IBillOperate.OP_NOTEDIT });
		btnVo.setBusinessStatus(new int[] { IBillStatus.CHECKPASS });
		btnVo.setExtendStatus(new int[] { IFBMExtendStatus.GATHERED,
				IFBMExtendStatus.BANKKEEPED });
		return btnVo;

	}

}