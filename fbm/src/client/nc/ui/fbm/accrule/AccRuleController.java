package nc.ui.fbm.accrule;

import nc.ui.trade.bill.AbstractManageController;
import nc.ui.trade.bill.ISingleController;
import nc.ui.trade.businessaction.IBusinessActionType;
import nc.ui.trade.button.IBillButton;
import nc.vo.fbm.accrule.AccRuleVO;
import nc.vo.fbm.pub.constant.FbmBusConstant;
import nc.vo.trade.pub.HYBillVO;

public class AccRuleController extends AbstractManageController implements
		ISingleController {

	private final String[] VO_ARRAY = new String[] { HYBillVO.class.getName(),
			AccRuleVO.class.getName(), AccRuleVO.class.getName() };
	
	public boolean isSingleDetail() {
		return false;
	}

	public String[] getCardBodyHideCol() {
		return null;
	}

	/**
	 * 卡片显示时需要的按钮
	 */
	public int[] getCardButtonAry() {
		return new int[] { IBillButton.Brow,
				 IBillButton.Edit, 
				IBillButton.Save,  IBillButton.Cancel,
				IBillButton.Return, IBillButton.Refresh,IBillButton.Print
				};
	}

	public boolean isShowCardRowNo() {
		return false;
	}

	public boolean isShowCardTotal() {
		return false;
	}

	public String getBillType() {
		return FbmBusConstant.BILLTYPE_ACCRULE;
	}

	public String[] getBillVoName() {
		return VO_ARRAY;
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
		return null;
	}

	public String getHeadZYXKey() {
		return null;
	}

	public String getPkField() {
		return AccRuleVO.PK_ACCRULE;
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

	public String[] getListBodyHideCol() {
		return null;
	}

	public int[] getListButtonAry() {
		return new int[] { IBillButton.Card,
				IBillButton.Edit,
				IBillButton.Save, IBillButton.Cancel,
				IBillButton.Refresh,IBillButton.Print
				};
	}

	public String[] getListHeadHideCol() {
		return null;
	}

	public boolean isShowListRowNo() {
		return false;
	}

	public boolean isShowListTotal() {
		return false;
	}

}
