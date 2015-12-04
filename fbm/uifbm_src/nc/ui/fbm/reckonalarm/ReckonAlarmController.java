package nc.ui.fbm.reckonalarm;

import nc.ui.trade.bill.ICardController;
import nc.ui.trade.bill.ISingleController;
import nc.ui.trade.businessaction.IBusinessActionType;
import nc.ui.trade.button.IBillButton;
import nc.vo.fbm.pub.constant.FbmBusConstant;
import nc.vo.fbm.reckonalarm.ReckalarmVO;
import nc.vo.trade.pub.HYBillVO;

/**
 * <p>
 * 调剂清算设置Controller
 * <p>创建人：bsrl
 * <b>日期：2007-10-30
 *
 */
public class ReckonAlarmController  implements ICardController, ISingleController {

	public String[] getCardBodyHideCol() {
		return null;
	}

	public int[] getCardButtonAry() {
		return new int[] { IBillButton.Edit,
				IBillButton.Line, IBillButton.Save, IBillButton.Cancel,
				IBillButton.Refresh };
	}

	public boolean isShowCardRowNo() {
		return false;
	}

	public boolean isShowCardTotal() {
		return false;
	}

	public String getBillType() {
		return FbmBusConstant.BILLTYPE_RECKON_ALARM;
	}

	public String[] getBillVoName() {
		return new String[] { HYBillVO.class.getName(),
				ReckalarmVO.class.getName(), ReckalarmVO.class.getName() };
	}

	public String getBodyCondition() {
		return null;
	}

	public String getBodyZYXKey() {
		return null;
	}

	public int getBusinessActionType() {
		return IBusinessActionType.BD;
	}

	public String getChildPkField() {
		return ReckalarmVO.PK_RECKALARM;
	}

	public String getHeadZYXKey() {
		return null;
	}

	public String getPkField() {
		return ReckalarmVO.PK_RECKALARM;
	}

	public Boolean isEditInGoing() throws Exception {
		return null;
	}

	public boolean isExistBillStatus() {
		return false;
	}

	public boolean isLoadCardFormula() {
		return false;
	}

	public boolean isSingleDetail() {
		return true;
	}

}
