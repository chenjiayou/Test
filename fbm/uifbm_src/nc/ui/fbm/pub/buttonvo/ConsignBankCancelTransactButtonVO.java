package nc.ui.fbm.pub.buttonvo;

import java.awt.Event;

import nc.ui.fbm.pub.IFBMButton;
import nc.ui.trade.base.IBillOperate;
import nc.ui.trade.button.IBillButtonVO;
import nc.vo.fbm.pub.constant.IFBMStatus;
import nc.vo.trade.button.ButtonVO;

/**
 * <p>
 *银行托收取消办理按钮VO类
 * <p>创建人：bsrl
 * <b>日期：2007-11-20
 *
 */
public class ConsignBankCancelTransactButtonVO implements IBillButtonVO {

	public ConsignBankCancelTransactButtonVO() {
	}

	public ButtonVO getButtonVO() {
		ButtonVO bvo = new ButtonVO();
		bvo.setBtnNo(IFBMButton.ConsignBank_CancelTransact);
		bvo.setBtnChinaName("取消确认");
		bvo.setBtnCode("CANCELTRANSACT");
		bvo.setBtnName(nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm","UPPFBMComm-000006")/* @res"取消确认"*/);
		bvo.setHintStr(nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm","UPPFBMComm-000006")/* @res"取消确认"*/);
        bvo.setHotKey("C");
        bvo.setDisplayHotKey("(Shift+C)");
        bvo.setModifiers(Event.SHIFT_MASK);
        bvo.setOperateStatus(new int[]{IBillOperate.OP_NOTEDIT,IBillOperate.OP_INIT});
        bvo.setBusinessStatus(new int[]{IFBMStatus.Transact});

        return bvo;
	}
}