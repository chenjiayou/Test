package nc.ui.fbm.pub.buttonvo;

import java.awt.Event;

import nc.ui.fbm.pub.IFBMButton;
import nc.ui.trade.base.IBillOperate;
import nc.ui.trade.button.IBillButtonVO;
import nc.vo.trade.button.ButtonVO;
import nc.vo.trade.pub.IBillStatus;

/**
 * <p>
 *�������㵥��֤��ťVO
 * <p>�����ˣ�bsrl
 * <b>���ڣ�2007-11-05
 */
public class ReckonVoucherButtonVO implements IBillButtonVO {
	/**
	 *
	 */
	public ReckonVoucherButtonVO() {
	}

	/* (non-Javadoc)
	 * @see nc.ui.trade.button.IBillButtonVO#getButtonVO()
	 */
	public ButtonVO getButtonVO() {
		ButtonVO btnVo=new ButtonVO();
		btnVo.setBtnChinaName("��֤");
		btnVo.setBtnName(nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm","UPPFBMComm-000028")/* @res"��֤"*/);
		btnVo.setBtnNo(IFBMButton.Reckon_Voucher);
		btnVo.setBtnCode("VOUCHER");
		btnVo.setHotKey("V");
		btnVo.setDisplayHotKey("(Shift+V)");
		btnVo.setModifiers(Event.SHIFT_MASK);
		btnVo.setOperateStatus(new int[]{IBillOperate.OP_NOTEDIT});
		btnVo.setBusinessStatus(new int[]{IBillStatus.CHECKPASS});
		return btnVo;
	}

}