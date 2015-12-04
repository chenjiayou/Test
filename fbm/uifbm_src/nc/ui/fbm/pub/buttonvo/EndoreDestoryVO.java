package nc.ui.fbm.pub.buttonvo;

import java.awt.Event;

import nc.ui.fbm.pub.IFBMButton;
import nc.ui.trade.base.IBillOperate;
import nc.ui.trade.button.IBillButtonVO;
import nc.vo.fbm.pub.constant.IFBMStatus;
import nc.vo.trade.button.ButtonVO;

public class EndoreDestoryVO implements IBillButtonVO {

	public EndoreDestoryVO()
	{
		super();
	}
	public ButtonVO getButtonVO() {
		ButtonVO btnVo=new ButtonVO();
		btnVo.setBtnChinaName("����");
		btnVo.setBtnName(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("common","UC001-0000034")/*@res "����"*/);
		btnVo.setBtnCode("DESTROY");
		btnVo.setBtnNo(IFBMButton.Endore_Destroy);

		btnVo.setOperateStatus(new int[]{IBillOperate.OP_NOTEDIT});
		btnVo.setBusinessStatus(new int[]{IFBMStatus.HAS_VOUCHER_VAR});//����֤��ɳ���
		//���ÿ�ݼ�Ctrl+D
		btnVo.setModifiers(Event.CTRL_MASK);
		btnVo.setHotKey("D");
		btnVo.setDisplayHotKey("(Ctrl+D)");

		return btnVo;
	}

}