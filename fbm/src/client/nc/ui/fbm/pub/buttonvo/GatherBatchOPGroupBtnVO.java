/**
 *
 */
package nc.ui.fbm.pub.buttonvo;

import java.awt.Event;

import nc.ui.fbm.pub.IFBMButton;
import nc.ui.trade.button.IBillButtonVO;
import nc.vo.trade.button.ButtonVO;
import nc.vo.trade.pub.IBillStatus;

/**
 * <p>
 * ��Ʊ�Ǽ�-ҵ�����ť����
 * <p>
 * �����ˣ�lpf <b>���ڣ�2007-8-24
 * 
 */
public class GatherBatchOPGroupBtnVO implements IBillButtonVO {

	/**
	 *
	 */
	public GatherBatchOPGroupBtnVO() {
		// TODO Auto-generated constructor stub
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nc.ui.trade.button.IBillButtonVO#getButtonVO()
	 */
	public ButtonVO getButtonVO() {
		ButtonVO btnVo = new ButtonVO();
		btnVo.setBtnChinaName("��������");
		btnVo.setBtnName(nc.ui.ml.NCLangRes.getInstance().getStrByID("36201005", "UPP36201005-000033")/*
																									 * @res
																									 * "��������"
																									 */);
		btnVo.setHintStr(nc.ui.ml.NCLangRes.getInstance().getStrByID("36201005", "UPP36201005-000033")/*
																									 * @res
																									 * "��������"
																									 */);
		btnVo.setHotKey("P");
		btnVo.setDisplayHotKey("(Shift+P)");
		btnVo.setModifiers(Event.SHIFT_MASK);
		btnVo.setBtnNo(IFBMButton.Gather_Batch);
		btnVo.setChildAry(new int[] { IFBMButton.Gather_BatchConsign,
				IFBMButton.Gather_BatchDiscount, IFBMButton.Gather_BatchImpawn });
		btnVo.setBusinessStatus(new int[] { IBillStatus.CHECKPASS });
		return btnVo;
	}

}