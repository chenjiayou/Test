package nc.ui.fbm.pub.buttonvo;

import nc.ui.fbm.pub.IFBMButton;
import nc.ui.trade.base.IBillOperate;
import nc.ui.trade.button.IBillButtonVO;
import nc.vo.trade.button.ButtonVO;

/**
 *
 * 功能： 调剂出库-联查组 日期：2007-10-20 程序员：wues
 */
public class ReliefLQGroupVO implements IBillButtonVO {

	public ReliefLQGroupVO() {
		super();

	}

	public ButtonVO getButtonVO() {
		ButtonVO btnVo = new ButtonVO();
		btnVo.setBtnChinaName("联查");
		btnVo.setBtnName(nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm","UPPFBMComm-000010")/* @res"联查"*/);
		btnVo.setBtnCode("RELIEF_LINKQUERYGROUP");
		btnVo.setBtnNo(IFBMButton.Relief_LinkQueryGroup);
		btnVo
				.setChildAry(new int[] { IFBMButton.Discount_LinkGather,
						IFBMButton.Relief_LQAccoutBalance,
						IFBMButton.Relief_LQVoucher });
		// btnVo.setBusinessStatus(new int[]{IFBMStatus.HAS_VOUCHER});//制证后可联查凭证
		btnVo.setOperateStatus(new int[] { IBillOperate.OP_NOTEDIT });

		btnVo.setHintStr(nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm","UPPFBMComm-000010")/* @res"联查"*/);

		return btnVo;
	}

}