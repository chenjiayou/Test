/**
 *
 */
package nc.ui.fbm.pub.buttonvo;

import java.awt.Event;

import nc.ui.fbm.pub.IFBMButton;
import nc.ui.trade.base.IBillOperate;
import nc.ui.trade.button.IBillButtonVO;
import nc.vo.fbm.pub.constant.IFBMStatus;
import nc.vo.trade.button.ButtonVO;
import nc.vo.trade.pub.IBillStatus;

/**
 * <p>
 * ��Ʊ-ȡ������
 * <p>�����ˣ�lpf
 * <b>���ڣ�2007-9-20
 *
 */
public class InvoiceDelCancelBillBtnVO implements IBillButtonVO {

	/**
	 *
	 */
	public InvoiceDelCancelBillBtnVO() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see nc.ui.trade.button.IBillButtonVO#getButtonVO()
	 */
	public ButtonVO getButtonVO() {
		// TODO Auto-generated method stub
		ButtonVO btnVo=new ButtonVO();
		btnVo.setBtnChinaName("ȡ������");
		btnVo.setBtnName(nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm","UPPFBMComm-000024")/* @res"ȡ������"*/);
		btnVo.setBtnNo(IFBMButton.Invoice_DelCancelBill);
		btnVo.setHotKey("H");
		btnVo.setDisplayHotKey("(Shift+H)");
		btnVo.setModifiers(Event.SHIFT_MASK);
		btnVo.setBtnCode("CANCELDESTROY");
		btnVo.setOperateStatus(new int[]{IBillOperate.OP_NOTEDIT});
		btnVo.setBusinessStatus(new int[]{IFBMStatus.Create,IBillStatus.CHECKPASS});
		return btnVo;
	}

}