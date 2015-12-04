/**
 *
 */
package nc.ui.fbm.invoice.btnvo;

import java.awt.Event;

import nc.ui.fbm.pub.IFBMButton;
import nc.ui.trade.button.IBillButtonVO;
import nc.vo.trade.button.ButtonVO;

/**
 * <p>
 * 开票联查组按钮
 * <p>创建人：lpf
 * <b>日期：2007-9-20
 *
 */
public class InvoiceLinkQueryGroupBtnVO implements IBillButtonVO {

	/**
	 *
	 */
	public InvoiceLinkQueryGroupBtnVO() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see nc.ui.trade.button.IBillButtonVO#getButtonVO()
	 */
	public ButtonVO getButtonVO() {
		// TODO Auto-generated method stub
		ButtonVO btnVo=new ButtonVO();
		btnVo.setBtnChinaName("联查");
		btnVo.setBtnName(nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm","UPPFBMComm-000010")/* @res"联查"*/);
		btnVo.setHintStr(nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm","UPPFBMComm-000010")/* @res"联查"*/);
		btnVo.setHotKey("K");
		btnVo.setDisplayHotKey("(Shift+K)");
		btnVo.setModifiers(Event.SHIFT_MASK);
		btnVo.setBtnNo(IFBMButton.Invoice_LinkQueryGroup);
		btnVo.setChildAry(new int[] { IFBMButton.Gather_LQueryPJBook,IFBMButton.FBM_QUERYVOUCHER,IFBMButton.Gather_LQuerySFBill,IFBMButton.QUERYRATION,IFBMButton.BTN_QUERY_CHARGE_PLAN,IFBMButton.BTN_QUERY_INVOICE_PAY_PLAN});
		return btnVo;
	}

}