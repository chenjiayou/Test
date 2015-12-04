package nc.ui.fbm.reckon;

import nc.ui.fbm.pub.FBMClientInfo;
import nc.ui.fbm.pub.IFBMButton;
import nc.ui.trade.bill.AbstractManageController;
import nc.ui.trade.businessaction.IBusinessActionType;
import nc.ui.trade.button.IBillButton;
import nc.vo.fbm.pub.constant.FbmBusConstant;
import nc.vo.fbm.reckon.ReckonBVO;
import nc.vo.fbm.reckon.ReckonVO;
import nc.vo.trade.pub.HYBillVO;

/**
 * <p>
 * 调剂清算单Controller
 * <p>
 * 创建人：bsrl <b>日期：2007-10-19
 * 
 */
public class ReckonController extends AbstractManageController {

	public ReckonController() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nc.ui.trade.bill.ICardController#getCardBodyHideCol()
	 */
	public String[] getCardBodyHideCol() {
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
					IFBMButton.Reckon_LinkGroup, 
					IBillButton.Print,
					IBillButton.Return };
		} else {
			return new int[] { 
					IBillButton.Query,
					IBillButton.Brow, 
					IBillButton.Refresh,
					IFBMButton.Reckon_LinkGroup,
					IBillButton.Print,
					IBillButton.Return};
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nc.ui.trade.bill.ICardController#isShowCardRowNo()
	 */
	public boolean isShowCardRowNo() {
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nc.ui.trade.bill.ICardController#isShowCardTotal()
	 */
	public boolean isShowCardTotal() {
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nc.ui.trade.controller.IControllerBase#getBillType()
	 */
	public String getBillType() {
		return FbmBusConstant.BILLTYPE_LIQUIDATE;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nc.ui.trade.controller.IControllerBase#getBillVoName()
	 */
	public String[] getBillVoName() {
		return new String[] { HYBillVO.class.getName(),
				ReckonVO.class.getName(), ReckonBVO.class.getName() };
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nc.ui.trade.controller.IControllerBase#getBodyCondition()
	 */
	public String getBodyCondition() {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nc.ui.trade.controller.IControllerBase#getBodyZYXKey()
	 */
	public String getBodyZYXKey() {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nc.ui.trade.controller.IControllerBase#getBusinessActionType()
	 */
	public int getBusinessActionType() {
		return IBusinessActionType.PLATFORM;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nc.ui.trade.controller.IControllerBase#getChildPkField()
	 */
	public String getChildPkField() {
		return ReckonBVO.PK_RECKON_B;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nc.ui.trade.controller.IControllerBase#getHeadZYXKey()
	 */
	public String getHeadZYXKey() {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nc.ui.trade.controller.IControllerBase#getPkField()
	 */
	public String getPkField() {
		return ReckonVO.PK_RECKON;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nc.ui.trade.controller.IControllerBase#isEditInGoing()
	 */
	public Boolean isEditInGoing() throws Exception {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nc.ui.trade.controller.IControllerBase#isExistBillStatus()
	 */
	public boolean isExistBillStatus() {
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nc.ui.trade.controller.IControllerBase#isLoadCardFormula()
	 */
	public boolean isLoadCardFormula() {
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nc.ui.trade.bill.IListController#getListBodyHideCol()
	 */
	public String[] getListBodyHideCol() {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nc.ui.trade.bill.IListController#getListButtonAry()
	 */
	public int[] getListButtonAry() {
		if (FBMClientInfo.isStartProcess().booleanValue()) {
			return new int[] { 
					IBillButton.Query, 
					IBillButton.Refresh, 
					IBillButton.Add,
					IBillButton.Edit, 
					IBillButton.Delete, 
					IBillButton.Action,					
					IFBMButton.Reckon_LinkGroup,
					IBillButton.Card};
		} else {
			return new int[] { 
					IBillButton.Query, 
					IBillButton.Refresh,
					IFBMButton.Reckon_LinkGroup ,
					IBillButton.Card };
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nc.ui.trade.bill.IListController#getListHeadHideCol()
	 */
	public String[] getListHeadHideCol() {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nc.ui.trade.bill.IListController#isShowListRowNo()
	 */
	public boolean isShowListRowNo() {
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nc.ui.trade.bill.IListController#isShowListTotal()
	 */
	public boolean isShowListTotal() {
		return true;
	}
}
