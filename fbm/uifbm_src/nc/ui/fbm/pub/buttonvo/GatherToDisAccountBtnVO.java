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
 * <p>�����ˣ�lpf
 * <b>���ڣ�2007-9-6
 *
 */
public class GatherToDisAccountBtnVO implements IBillButtonVO {

	/**
	 *
	 */
	public GatherToDisAccountBtnVO() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see nc.ui.trade.button.IBillButtonVO#getButtonVO()
	 */
	public ButtonVO getButtonVO() {
		ButtonVO btnVo=new ButtonVO();
		btnVo.setBtnChinaName("��������");
		btnVo.setBtnName(nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm","UPPFBMComm-000019")/* @res"��������"*/);
		btnVo.setBtnNo(IFBMButton.Gather_BankDiscount);
		btnVo.setHotKey("G");
		btnVo.setDisplayHotKey("(Shift+G)");
		btnVo.setModifiers(Event.SHIFT_MASK);
		btnVo.setOperateStatus(new int[]{IBillOperate.OP_NOTEDIT});
		btnVo.setBusinessStatus(new int[]{IBillStatus.CHECKPASS});
		btnVo.setExtendStatus(new int[]{IFBMExtendStatus.GATHERED});
		return btnVo;
	}

}