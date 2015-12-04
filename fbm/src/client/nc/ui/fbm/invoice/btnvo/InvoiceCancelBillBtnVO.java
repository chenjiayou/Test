/**
 *
 */
package nc.ui.fbm.invoice.btnvo;

import java.awt.Event;

import nc.ui.fbm.pub.IFBMButton;
import nc.ui.trade.base.IBillOperate;
import nc.ui.trade.button.IBillButtonVO;
import nc.vo.fbm.pub.constant.IFBMStatus;
import nc.vo.trade.button.ButtonVO;
import nc.vo.trade.pub.IBillStatus;

/**
 * <p>
 * 开票登记-核销
 * <p>创建人：lpf
 * <b>日期：2007-9-20
 *
 */
public class InvoiceCancelBillBtnVO implements IBillButtonVO {

	/**
	 *
	 */
	public InvoiceCancelBillBtnVO() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see nc.ui.trade.button.IBillButtonVO#getButtonVO()
	 */
	public ButtonVO getButtonVO() {
		ButtonVO btnVo=new ButtonVO();
		btnVo.setBtnChinaName("核销");
		btnVo.setBtnName(nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm","UPPFBMComm-000023")/* @res"核销"*/);
		btnVo.setBtnNo(IFBMButton.Invoice_CancelBill);
		btnVo.setHotKey("G");
		btnVo.setDisplayHotKey("(Shift+G)");
		btnVo.setModifiers(Event.SHIFT_MASK);
		btnVo.setBtnCode(IFBMButton.Invoice_CancelBill_STR);
		btnVo.setOperateStatus(new int[]{IBillOperate.OP_NOTEDIT});
		btnVo.setBusinessStatus(new int[]{IFBMStatus.Create,IBillStatus.CHECKPASS});
		return btnVo;
	}

}