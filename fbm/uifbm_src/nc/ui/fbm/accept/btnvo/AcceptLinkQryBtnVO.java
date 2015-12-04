package nc.ui.fbm.accept.btnvo;

import java.awt.Event;

import nc.ui.fbm.pub.IFBMButton;
import nc.ui.trade.button.IBillButtonVO;
import nc.vo.trade.button.ButtonVO;

/**
 * 承兑联查按钮组
 * @author xwq
 *
 * 2008-10-22
 */
public class AcceptLinkQryBtnVO implements IBillButtonVO {

	public ButtonVO getButtonVO() {
		// TODO Auto-generated method stub
		ButtonVO btnVo=new ButtonVO();
		btnVo.setBtnChinaName("联查");
		btnVo.setBtnName(nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm","UPPFBMComm-000010")/* @res"联查"*/);
		btnVo.setHintStr(nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm","UPPFBMComm-000010")/* @res"联查"*/);
		btnVo.setHotKey("K");
		btnVo.setDisplayHotKey("(Shift+K)");
		btnVo.setModifiers(Event.SHIFT_MASK);
		btnVo.setBtnNo(IFBMButton.BTN_ACCEPT_QUERY_GROUP);
		btnVo.setChildAry(new int[] { IFBMButton.Gather_LQueryPJBook,IFBMButton.FBM_QUERYVOUCHER,IFBMButton.QUERYRATION,IFBMButton.BTN_QUERY_PLAN});
		return btnVo;
	}

}