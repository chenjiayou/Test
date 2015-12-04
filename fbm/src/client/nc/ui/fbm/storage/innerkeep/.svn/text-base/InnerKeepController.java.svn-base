/**
 *
 */
package nc.ui.fbm.storage.innerkeep;

import nc.ui.fbm.pub.FBMClientInfo;
import nc.ui.fbm.pub.IFBMButton;
import nc.ui.trade.bill.AbstractManageController;
import nc.ui.trade.businessaction.IBusinessActionType;
import nc.ui.trade.button.IBillButton;
import nc.vo.fbm.pub.constant.FbmBusConstant;
import nc.vo.fbm.storage.StorageBVO;
import nc.vo.fbm.storage.StorageVO;
import nc.vo.trade.pub.HYBillVO;

/**
 * <p>
 * 
 * <p>
 * 创建人：lpf <b>日期：2007-10-9
 * 
 */
public class InnerKeepController extends AbstractManageController {

	public InnerKeepController() {
		// TODO Auto-generated constructor stub
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nc.ui.trade.bill.ICardController#getCardBodyHideCol()
	 */
	public String[] getCardBodyHideCol() {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nc.ui.trade.bill.ICardController#getCardButtonAry()
	 */
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
					IFBMButton.FBM_SUBMIT,
					IFBMButton.FBM_CANCELSUBMIT, 
					IFBMButton.InnerKeep_LinkGroup, 
					IBillButton.Print,
					IBillButton.Return};
		} else {
			return new int[] { 
					IBillButton.Query,
					IBillButton.Brow, 
					IBillButton.Refresh, 
					IFBMButton.InnerKeep_LinkGroup, 
					IBillButton.Print,
					IBillButton.Return
					};
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nc.ui.trade.bill.ICardController#isShowCardRowNo()
	 */
	public boolean isShowCardRowNo() {
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nc.ui.trade.bill.ICardController#isShowCardTotal()
	 */
	public boolean isShowCardTotal() {
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nc.ui.trade.controller.IControllerBase#getBillType()
	 */
	public String getBillType() {
		return FbmBusConstant.BILLTYPE_INNERKEEP;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nc.ui.trade.controller.IControllerBase#getBillVoName()
	 */
	public String[] getBillVoName() {
		return new String[] { HYBillVO.class.getName(),
				StorageVO.class.getName(), StorageBVO.class.getName() };
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nc.ui.trade.controller.IControllerBase#getBodyCondition()
	 */
	public String getBodyCondition() {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nc.ui.trade.controller.IControllerBase#getBodyZYXKey()
	 */
	public String getBodyZYXKey() {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nc.ui.trade.controller.IControllerBase#getBusinessActionType()
	 */
	public int getBusinessActionType() {
		// TODO Auto-generated method stub
		return IBusinessActionType.PLATFORM;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nc.ui.trade.controller.IControllerBase#getChildPkField()
	 */
	public String getChildPkField() {
		// TODO Auto-generated method stub
		return StorageBVO.PK_STORAGE_B;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nc.ui.trade.controller.IControllerBase#getHeadZYXKey()
	 */
	public String getHeadZYXKey() {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nc.ui.trade.controller.IControllerBase#getPkField()
	 */
	public String getPkField() {
		// TODO Auto-generated method stub
		return StorageVO.PK_STORAGE;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nc.ui.trade.controller.IControllerBase#isEditInGoing()
	 */
	public Boolean isEditInGoing() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nc.ui.trade.controller.IControllerBase#isExistBillStatus()
	 */
	public boolean isExistBillStatus() {
		// TODO Auto-generated method stub
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nc.ui.trade.controller.IControllerBase#isLoadCardFormula()
	 */
	public boolean isLoadCardFormula() {
		// TODO Auto-generated method stub
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nc.ui.trade.bill.IListController#getListBodyHideCol()
	 */
	public String[] getListBodyHideCol() {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nc.ui.trade.bill.IListController#getListButtonAry()
	 */
	public int[] getListButtonAry() {
		// TODO Auto-generated method stub
		if (FBMClientInfo.isStartProcess().booleanValue()) {
			return new int[] { 
					IBillButton.Query, 
					IBillButton.Refresh,
					IBillButton.Add,
					IBillButton.Edit, 
					IBillButton.Delete, 
					IBillButton.Action,
					IFBMButton.FBM_SUBMIT,
					IFBMButton.FBM_CANCELSUBMIT, 
					IFBMButton.InnerKeep_LinkGroup,
					IBillButton.Card };
		} else {
			return new int[] { 
					IBillButton.Query, 
					IBillButton.Refresh,
					IFBMButton.InnerKeep_LinkGroup,
					IBillButton.Card };
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nc.ui.trade.bill.IListController#getListHeadHideCol()
	 */
	public String[] getListHeadHideCol() {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nc.ui.trade.bill.IListController#isShowListRowNo()
	 */
	public boolean isShowListRowNo() {
		// TODO Auto-generated method stub
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nc.ui.trade.bill.IListController#isShowListTotal()
	 */
	public boolean isShowListTotal() {
		// TODO Auto-generated method stub
		return true;
	}

}
