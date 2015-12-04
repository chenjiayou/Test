package nc.ui.fbm.pub.buttonvo;

import nc.ui.fbm.pub.IFBMButton;
import nc.ui.trade.base.IBillOperate;
import nc.ui.trade.button.IBillButtonVO;
import nc.vo.trade.button.ButtonVO;

/**
 * TODO ��Ʊ�Ǽǣ���Ʊ���
 * 
 * @author zhouwb 2008-9-17
 *
 */
public class SpwcBtnVO implements IBillButtonVO {

	public SpwcBtnVO() {
		super();
	}

	public ButtonVO getButtonVO() {
		ButtonVO btnVo=new ButtonVO();
		btnVo.setBtnChinaName("��Ʊ���");
		btnVo.setBtnName(nc.ui.ml.NCLangRes.getInstance().getStrByID("36201005","UPP36201005-000017")/* @res"��Ʊ���"*/);
		btnVo.setHintStr(nc.ui.ml.NCLangRes.getInstance().getStrByID("36201005","UPP36201005-000017")/* @res"��Ʊ���"*/);
		btnVo.setBtnNo(IFBMButton.BTNVO_SPWC);

		btnVo.setOperateStatus(new int[]{IBillOperate.OP_NOTEDIT});
//		btnVo.setBusinessStatus(new int[]{IBillStatus.CHECKPASS});
		//���ÿ�ݼ�Ctrl+V
//		btnVo.setModifiers(Event.CTRL_MASK);
//		btnVo.setHotKey("V");
//		btnVo.setDisplayHotKey("(Alt+V)");

		return btnVo;
	}

}
