package nc.ui.fbm.pub.buttonvo;

import java.awt.Event;

import nc.ui.fbm.pub.IFBMButton;
import nc.ui.trade.button.IBillButtonVO;
import nc.vo.trade.button.ButtonVO;

/**
 * <p>
 * 收票登记-联查
 * <p>
 * 创建人：lpf <b>日期：2007-10-9
 * 
 */
public class GatherLQueryGroupBtnVO implements IBillButtonVO {

	/**
	 *
	 */
	public GatherLQueryGroupBtnVO() {
		// TODO Auto-generated constructor stub
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nc.ui.trade.button.IBillButtonVO#getButtonVO()
	 */
	public ButtonVO getButtonVO() {
		ButtonVO btnVo = new ButtonVO();
		btnVo.setBtnChinaName("联查");
		btnVo.setBtnName(nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm", "UPPFBMComm-000010")/*
																									 * @res
																									 * "联查"
																									 */);
		btnVo.setBtnNo(IFBMButton.Gather_LQueryGroup);
		btnVo.setHintStr(nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm", "UPPFBMComm-000010")/*
																									 * @res
																									 * "联查"
																									 */);
		btnVo.setHotKey("L");
		btnVo.setDisplayHotKey("(Shift+L)");
		btnVo.setModifiers(Event.SHIFT_MASK);
		btnVo.setChildAry(new int[] { IFBMButton.Gather_LQueryPJBook,
				IFBMButton.Gather_LQuerySFBill, IFBMButton.BTN_QUERY_PLAN });
		// btnVo.setBusinessStatus(new int[]{IBillStatus.CHECKPASS});
		return btnVo;
	}

}