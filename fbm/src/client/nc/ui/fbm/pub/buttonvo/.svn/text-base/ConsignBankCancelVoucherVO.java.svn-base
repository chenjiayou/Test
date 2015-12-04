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
       银行托收-取消制证
 * 日期：2008-3-20
 * 
 */
public class ConsignBankCancelVoucherVO implements IBillButtonVO {

	public ConsignBankCancelVoucherVO() {
		super();

	}

	public ButtonVO getButtonVO() {
		ButtonVO btnVo=new ButtonVO();
		btnVo.setBtnChinaName("取消制证");
		btnVo.setBtnName(nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm","UPPFBMComm-000027")/* @res"取消制证"*/);
		btnVo.setBtnNo(IFBMButton.ConsignBank_CancelVoucher);
		btnVo.setBtnCode("CANCELVOUCHER");
		btnVo.setOperateStatus(new int[]{IBillOperate.OP_NOTEDIT});
		btnVo.setBusinessStatus(new int[]{IFBMStatus.HAS_VOUCHER_VAR});//已制证后可取消制证
		//取消制证
		btnVo.setModifiers(Event.ALT_MASK);
		btnVo.setHotKey("M");
		btnVo.setDisplayHotKey("(Alt+M)");

		return btnVo;
	}

}