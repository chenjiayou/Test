package nc.ui.fbm.pub.buttonvo;

import nc.ui.fbm.pub.IFBMButton;
import nc.ui.trade.base.IBillOperate;
import nc.ui.trade.button.IBillButtonVO;
import nc.vo.fbm.pub.constant.IFBMStatus;
import nc.vo.trade.button.ButtonVO;
/**
 *
 * ���ܣ�
       ��������-ȡ������
       �ѷ�������Ҫ�˰�ť
 * ���ڣ�2007-10-20
 * ����Ա��wues
 */
public class ReliefCancelOutputVO implements IBillButtonVO {

	public ButtonVO getButtonVO() {
		ButtonVO btnVo=new ButtonVO();
		btnVo.setBtnChinaName("ȡ������");
		btnVo.setBtnName(nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm","UPPFBMComm-000002")/* @res"ȡ������"*/);
		btnVo.setBtnNo(IFBMButton.Relief_CancelOutput);
		btnVo.setBtnCode("CANCELOUT");
		btnVo.setOperateStatus(new int[]{IBillOperate.OP_NOTEDIT});
		btnVo.setBusinessStatus(new int[]{IFBMStatus.HAS_OUTPUT});//�ѳ�����ȡ������
		return btnVo;
	}

}