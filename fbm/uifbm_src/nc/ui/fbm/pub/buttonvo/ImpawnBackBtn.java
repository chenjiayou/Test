package nc.ui.fbm.pub.buttonvo;

import java.awt.Event;

import nc.ui.fbm.pub.IFBMButton;
import nc.ui.trade.button.IBillButtonVO;
import nc.vo.trade.button.ButtonVO;
import nc.vo.trade.pub.IBillStatus;
/**
 * ���ܣ�
       ��Ѻ���հ�ťVO
 * ���ڣ�2007-9-27
 * ����Ա��wues
 */
public class ImpawnBackBtn implements IBillButtonVO {

	public ImpawnBackBtn() {
		super();

	}

	public ButtonVO getButtonVO() {
		ButtonVO btnVo=new ButtonVO();
		btnVo.setBtnChinaName("��Ѻ����");
		btnVo.setBtnName(nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm","UPPFBMComm-000022")/* @res"��Ѻ����"*/);
		btnVo.setHintStr(nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm","UPPFBMComm-000022")/* @res"��Ѻ����"*/);
		btnVo.setBtnNo(IFBMButton.BTN_IMPAWN_BACK);
		btnVo.setBtnCode("IMPAWNBACK");
		//btnVo.setOperateStatus(new int[]{IBillOperate.OP_NOTEDIT});
		btnVo.setBusinessStatus(new int[]{IBillStatus.CHECKPASS});//��Ѻ��������ͨ��
		//��Ѻ����Ctrl+B
		btnVo.setModifiers(Event.CTRL_MASK);
		btnVo.setHotKey("B");
		btnVo.setDisplayHotKey("(Ctrl+B)");

		return btnVo;
	}

}