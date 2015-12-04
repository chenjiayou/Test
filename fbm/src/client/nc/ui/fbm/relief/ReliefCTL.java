package nc.ui.fbm.relief;

import nc.ui.fbm.pub.FBMClientInfo;
import nc.ui.fbm.pub.IFBMButton;
import nc.ui.trade.bill.AbstractManageController;
import nc.ui.trade.businessaction.IBusinessActionType;
import nc.ui.trade.button.IBillButton;
import nc.vo.fbm.pub.constant.FbmBusConstant;
import nc.vo.fbm.relief.ReliefBVO;
import nc.vo.fbm.relief.ReliefVO;
import nc.vo.trade.pub.HYBillVO;

/**
 * 功能： 调剂出库界面控制器 日期：2007-10-20 程序员：wues
 */
public class ReliefCTL extends AbstractManageController {

	public ReliefCTL() {
		super();

	}

	public int[] getCardButtonAry() {
		if (FBMClientInfo.isStartProcess().booleanValue()) {
			return new int[] { 
					IBillButton.Query,
					IBillButton.Brow, 
					IBillButton.Refresh,
					IBillButton.Add, 
					IBillButton.Edit, 
					IBillButton.Delete,
					IBillButton.Line, 
					IBillButton.Cancel, 
					IBillButton.Save,
					IBillButton.Action, 
					IFBMButton.Relief_LinkQueryGroup,
					IBillButton.Print,
					IBillButton.Return};
		} else {
			return new int[] { 
					IBillButton.Query,
					IBillButton.Brow, 
					IBillButton.Refresh, 
					IFBMButton.Relief_LinkQueryGroup, 
					IBillButton.Print,
					IBillButton.Return };
		}
	}

	public String getBillType() {
		return FbmBusConstant.BILLTYPE_RELIEF;
	}

	public String[] getBillVoName() {
		return new String[] { HYBillVO.class.getName(),
				ReliefVO.class.getName(), ReliefBVO.class.getName() };
	}

	public int getBusinessActionType() {
		return IBusinessActionType.PLATFORM;
	}

	public String getChildPkField() {
		return ReliefBVO.PK_RELIEF_B;
	}

	public String getPkField() {
		return ReliefVO.PK_RELIEF;
	}

	public boolean isExistBillStatus() {
		return true;
	}

	public int[] getListButtonAry() {
		if (FBMClientInfo.isStartProcess().booleanValue()) {
			return new int[] { 
					IBillButton.Query, 
					IBillButton.Refresh, 
					IBillButton.Add,
					IBillButton.Edit, 
					IBillButton.Delete, 
					IBillButton.Action, 
					IFBMButton.Relief_LinkQueryGroup,
					IBillButton.Print,
					IBillButton.Card};
		} else {
			return new int[] { 
					IBillButton.Query, 
					IBillButton.Refresh,
					IFBMButton.Relief_LinkQueryGroup,
					IBillButton.Print,
					IBillButton.Card};
		}
	}

	public String[] getListHeadHideCol() {
		return null;
	}

	public boolean isShowListRowNo() {
		return true;
	}

	public boolean isShowListTotal() {
		return true;
	}

	public String[] getCardBodyHideCol() {
		return null;
	}

	public boolean isShowCardRowNo() {
		return false;
	}

	public boolean isShowCardTotal() {
		return false;
	}

	public String getBodyCondition() {
		return null;
	}

	public String getBodyZYXKey() {
		return null;
	}

	public String getHeadZYXKey() {
		return null;
	}

	public Boolean isEditInGoing() throws Exception {
		return null;
	}

	public boolean isLoadCardFormula() {
		return false;
	}

	public String[] getListBodyHideCol() {
		return null;
	}
}
