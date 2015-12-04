package nc.ui.fbm.pub.buttonvo;

import nc.ui.fbm.pub.IFBMButton;
import nc.ui.trade.button.IBillButtonVO;
import nc.vo.trade.button.ButtonVO;

/**
 * <p>
 *�������㵥�����鰴ťVO
 * <p>�����ˣ�bsrl
 * <b>���ڣ�2007-11-05
 *
 */
public class ReckonLinkGroupButtonVO implements IBillButtonVO{
	/**
	 *
	 */
	public ReckonLinkGroupButtonVO() {
	}

	/* (non-Javadoc)
	 * @see nc.ui.trade.button.IBillButtonVO#getButtonVO()
	 */
	public ButtonVO getButtonVO() {
		ButtonVO btnVo=new ButtonVO();
		btnVo.setBtnChinaName("����");
		btnVo.setBtnName(nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm","UPPFBMComm-000010")/* @res"����"*/);
		btnVo.setHintStr(nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm","UPPFBMComm-000010")/* @res"����"*/);
		btnVo.setBtnNo(IFBMButton.Reckon_LinkGroup);
		btnVo.setChildAry(new int[] { IFBMButton.Reckon_LinkInAccountBalance,IFBMButton.Reckon_LinkOutAccountBalance,
				IFBMButton.Reckon_LinkVoucher });
		return btnVo;
	}
}