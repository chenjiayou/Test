package nc.ui.fbm.pub.buttonvo;

import nc.ui.fbm.pub.IFBMButton;
import nc.ui.trade.base.IBillOperate;
import nc.ui.trade.button.IBillButtonVO;
import nc.vo.fbm.pub.constant.IFBMStatus;
import nc.vo.trade.button.ButtonVO;
/**
 *
 * 功能：
       调剂出库-取消出库
       已废弃，不要此按钮
 * 日期：2007-10-20
 * 程序员：wues
 */
public class ReliefCancelOutputVO implements IBillButtonVO {

	public ButtonVO getButtonVO() {
		ButtonVO btnVo=new ButtonVO();
		btnVo.setBtnChinaName("取消出库");
		btnVo.setBtnName(nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm","UPPFBMComm-000002")/* @res"取消出库"*/);
		btnVo.setBtnNo(IFBMButton.Relief_CancelOutput);
		btnVo.setBtnCode("CANCELOUT");
		btnVo.setOperateStatus(new int[]{IBillOperate.OP_NOTEDIT});
		btnVo.setBusinessStatus(new int[]{IFBMStatus.HAS_OUTPUT});//已出库后可取消出库
		return btnVo;
	}

}