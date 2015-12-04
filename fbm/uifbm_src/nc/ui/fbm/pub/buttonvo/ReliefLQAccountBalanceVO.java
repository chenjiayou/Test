package nc.ui.fbm.pub.buttonvo;

import java.awt.Event;

import nc.ui.fbm.pub.IFBMButton;
import nc.ui.trade.base.IBillOperate;
import nc.ui.trade.button.IBillButtonVO;
import nc.vo.trade.button.ButtonVO;
/**
 *
 * ���ܣ�
       ��������-�����˻����
 * ���ڣ�2007-10-20
 * ����Ա��wues
 */
public class ReliefLQAccountBalanceVO implements IBillButtonVO {

	public ReliefLQAccountBalanceVO() {
		super();

	}

	public ButtonVO getButtonVO() {
		ButtonVO btnVo=new ButtonVO();
		btnVo.setBtnChinaName("�����˻����");
		btnVo.setBtnName(nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm","UPPFBMComm-000031")/* @res"�����˻����"*/);
		btnVo.setBtnCode("RELIEF_LQACCOUNTBALANCE");
		btnVo.setBtnNo(IFBMButton.Relief_LQAccoutBalance);
		btnVo.setOperateStatus(new int[]{IBillOperate.OP_NOTEDIT});

		//���ÿ�ݼ�Ctrl+A
		btnVo.setModifiers(Event.CTRL_MASK);
		btnVo.setHotKey("A");
		btnVo.setDisplayHotKey("(Ctrl+A)");

		return btnVo;
	}

}