package nc.ui.fbm.pub.buttonvo;

import java.awt.Event;

import nc.ui.fbm.pub.IFBMButton;
import nc.ui.fbm.relief.status.IReliefStatus;
import nc.ui.trade.base.IBillOperate;
import nc.ui.trade.button.IBillButtonVO;
import nc.vo.trade.button.ButtonVO;
import nc.vo.trade.pub.IBillStatus;

/**
 *
 * ���ܣ�
       ��������-���ⰴť
 * ���ڣ�2007-10-20
 * ����Ա��wues
 */
public class ReliefOutputVO implements IBillButtonVO {

	public ReliefOutputVO() {
		super();

	}

	public ButtonVO getButtonVO() {
		ButtonVO btnVo=new ButtonVO();
		btnVo.setBtnChinaName("����");
		btnVo.setBtnName(nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm","UPPFBMComm-000003")/* @res"����"*/);
		btnVo.setBtnNo(IFBMButton.Relief_Output);
		btnVo.setBtnCode("OUTPUT");
		//���ð�ť��δ����ʱ����
		btnVo.setExtendStatus(new int[]{IReliefStatus.NOT_OUT});
		btnVo.setOperateStatus(new int[]{IBillOperate.OP_NOTEDIT});
		//btnVo.setBusinessStatus(new int[]{IBillStatus.CHECKPASS});

		//���ÿ�ݼ�Ctrl+O
		btnVo.setModifiers(Event.CTRL_MASK);
		btnVo.setHotKey("O");
		btnVo.setDisplayHotKey("(Ctrl+O)");

		return btnVo;
	}

}