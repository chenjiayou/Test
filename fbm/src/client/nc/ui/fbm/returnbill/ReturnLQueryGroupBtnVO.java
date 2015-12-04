package nc.ui.fbm.returnbill;

import java.awt.Event;

import nc.ui.fbm.pub.IFBMButton;
import nc.ui.trade.button.IBillButtonVO;
import nc.vo.trade.button.ButtonVO;

public class ReturnLQueryGroupBtnVO implements IBillButtonVO {

	public ButtonVO getButtonVO() {
		// TODO Auto-generated method stub
		ButtonVO btnVo=new ButtonVO();
		btnVo.setBtnChinaName("联查");
		btnVo.setBtnName(nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm","UPPFBMComm-000010")/* @res"联查"*/);
		btnVo.setBtnNo(IFBMButton.BTN_RETURN_QUERYGROUP);
		btnVo.setHintStr(nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm","UPPFBMComm-000010")/* @res"联查"*/);
		btnVo.setHotKey("L");
		btnVo.setDisplayHotKey("(Shift+L)");
		btnVo.setModifiers(Event.SHIFT_MASK);
		btnVo.setChildAry(new int[] { IFBMButton.Gather_LQueryPJBook,
				IFBMButton.Gather_LQuerySFBill ,IFBMButton.FBM_QUERYVOUCHER,IFBMButton.BTN_QUERY_PLAN});
//		btnVo.setBusinessStatus(new int[]{IBillStatus.CHECKPASS});
		return btnVo;
	}

}
