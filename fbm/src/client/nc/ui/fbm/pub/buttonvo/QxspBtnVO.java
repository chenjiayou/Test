package nc.ui.fbm.pub.buttonvo;

import nc.ui.fbm.pub.IFBMButton;
import nc.ui.trade.base.IBillOperate;
import nc.ui.trade.button.IBillButtonVO;
import nc.vo.trade.button.ButtonVO;

/**
 * TODO 收票登记－取消收票
 * @author zhouwb 2008-9-17
 *
 */
public class QxspBtnVO implements IBillButtonVO {

	public QxspBtnVO() {
		super();
	}

	public ButtonVO getButtonVO() {
		ButtonVO btnVo=new ButtonVO();
		btnVo.setBtnChinaName("取消收票");
		btnVo.setBtnName(nc.ui.ml.NCLangRes.getInstance().getStrByID("36201005","UPP36201005-000018")/* @res"取消收票"*/);
		btnVo.setHintStr(nc.ui.ml.NCLangRes.getInstance().getStrByID("36201005","UPP36201005-000018")/* @res"取消收票"*/);
		btnVo.setBtnNo(IFBMButton.BTNVO_QXSP);

		btnVo.setOperateStatus(new int[]{IBillOperate.OP_NOTEDIT});
//		btnVo.setBusinessStatus(new int[]{IBillStatus.CHECKPASS});
		//设置快捷键Ctrl+V
//		btnVo.setModifiers(Event.CTRL_MASK);
//		btnVo.setHotKey("V");
//		btnVo.setDisplayHotKey("(Alt+V)");

		return btnVo;
	}

}
