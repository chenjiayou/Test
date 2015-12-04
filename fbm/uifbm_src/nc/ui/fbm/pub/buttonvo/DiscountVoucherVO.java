package nc.ui.fbm.pub.buttonvo;

import java.awt.Event;

import nc.ui.fbm.pub.IFBMButton;
import nc.ui.trade.base.IBillOperate;
import nc.ui.trade.button.IBillButtonVO;
import nc.vo.trade.button.ButtonVO;
import nc.vo.trade.pub.IBillStatus;
/**
 *
 * ���ܣ�
       ���ְ���-��֤
 * ���ڣ�2008-3-20
 */
public class DiscountVoucherVO implements IBillButtonVO {

	public DiscountVoucherVO() {
		super();

	}

	public ButtonVO getButtonVO() {
		ButtonVO btnVo=new ButtonVO();
		btnVo.setBtnChinaName("��֤");
		btnVo.setBtnName(nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm","UPPFBMComm-000028")/* @res"��֤"*/);
		btnVo.setBtnNo(IFBMButton.Discount_Voucher);
		btnVo.setBtnCode("VOUCHER");
		btnVo.setOperateStatus(new int[]{IBillOperate.OP_NOTEDIT});
		btnVo.setBusinessStatus(new int[]{IBillStatus.CHECKPASS});//����ͨ�������֤
		//���ÿ�ݼ�Ctrl+U
		btnVo.setModifiers(Event.CTRL_MASK);
		btnVo.setHotKey("M");
		btnVo.setDisplayHotKey("(Ctrl+M)");

		return btnVo;
	}

}