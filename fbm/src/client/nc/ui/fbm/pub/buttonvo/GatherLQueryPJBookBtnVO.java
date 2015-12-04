/**
 *
 */
package nc.ui.fbm.pub.buttonvo;

import java.awt.Event;

import nc.ui.fbm.pub.IFBMButton;
import nc.ui.trade.base.IBillOperate;
import nc.ui.trade.button.IBillButtonVO;
import nc.vo.trade.button.ButtonVO;

/**
 * <p>
 * ����Ʊ�ݱ��鲾
 * <p>�����ˣ�lpf
 * <b>���ڣ�2007-10-9
 *
 */
public class GatherLQueryPJBookBtnVO implements IBillButtonVO {

	/**
	 *
	 */
	public GatherLQueryPJBookBtnVO() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see nc.ui.trade.button.IBillButtonVO#getButtonVO()
	 */
	public ButtonVO getButtonVO() {
		// TODO Auto-generated method stub
		ButtonVO btnVo=new ButtonVO();
		btnVo.setBtnChinaName("����Ʊ�ݱ��鲾");
		btnVo.setBtnName(nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm","UPPFBMComm-000015")/* @res"����Ʊ�ݱ��鲾"*/);
		btnVo.setHintStr(nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm","UPPFBMComm-000015")/* @res"����Ʊ�ݱ��鲾"*/);
		btnVo.setBtnNo(IFBMButton.Gather_LQueryPJBook);
		btnVo.setHotKey("P");
		btnVo.setDisplayHotKey("(Shift+P)");
		btnVo.setModifiers(Event.SHIFT_MASK);
		btnVo.setOperateStatus(new int[]{IBillOperate.OP_NOTEDIT});
//		btnVo.setBusinessStatus(new int[]{IBillStatus.CHECKPASS});
		return btnVo;
	}

}