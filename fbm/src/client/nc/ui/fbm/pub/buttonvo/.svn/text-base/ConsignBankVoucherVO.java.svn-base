package nc.ui.fbm.pub.buttonvo;

import java.awt.Event;

import nc.ui.fbm.pub.IFBMButton;
import nc.ui.trade.base.IBillOperate;
import nc.ui.trade.button.IBillButtonVO;
import nc.vo.fbm.pub.constant.IFBMStatus;
import nc.vo.trade.button.ButtonVO;
/**
 *
 * 功能：
       银行托收-制证
 * 日期：2008-3-20
 */
public class ConsignBankVoucherVO implements IBillButtonVO {

	public ConsignBankVoucherVO() {
		super();

	}

	public ButtonVO getButtonVO() {
		ButtonVO btnVo=new ButtonVO();
		btnVo.setBtnChinaName("制证");
		btnVo.setBtnName(nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm","UPPFBMComm-000028")/* @res"制证"*/);
		btnVo.setBtnNo(IFBMButton.ConsignBank_Voucher);
		btnVo.setBtnCode("VOUCHER");
		btnVo.setOperateStatus(new int[]{IBillOperate.OP_NOTEDIT});
		btnVo.setBusinessStatus(new int[]{IFBMStatus.Transact});//办理过后方可显示制证
		//设置快捷键Ctrl+U
		btnVo.setModifiers(Event.CTRL_MASK);
		btnVo.setHotKey("M");
		btnVo.setDisplayHotKey("(Ctrl+M)");

		return btnVo;
	}

}