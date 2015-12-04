/**
 *
 */
package nc.ui.fbm.invoice;

import nc.ui.fbm.pub.IFBMButton;
import nc.ui.trade.bill.AbstractManageController;
import nc.ui.trade.bill.ISingleController;
import nc.ui.trade.businessaction.IBusinessActionType;
import nc.ui.trade.button.IBillButton;
import nc.vo.fbm.pub.constant.FbmBusConstant;
import nc.vo.fbm.register.RegisterVO;
import nc.vo.trade.pub.HYBillVO;

/**
 * <p>
 * 
 * <p>
 * 创建人：lpf <b>日期：2007-9-4
 * 
 */
public class InvoiceController extends AbstractManageController implements
		ISingleController {

	/**
	 * 
	 */
	public InvoiceController() {
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
		// TODO Auto-generated method stub
		return new int[] { 
				IBillButton.Query,
				IBillButton.Brow, 
				 IBillButton.Refresh,
				IBillButton.Add, 
				IBillButton.Edit, 
				IBillButton.Delete,
				IBillButton.Copy, 
				IBillButton.Cancel, 
				IBillButton.Save,
				IBillButton.Action, 
				IFBMButton.Attach,
				IFBMButton.Invoice_BusGroup, 
				IFBMButton.Invoice_LinkQueryGroup,
				IBillButton.Print,
				IFBMButton.PRINT4NOTE,
				IBillButton.Return};
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nc.ui.trade.bill.ICardController#isShowCardRowNo()
	 */
	public boolean isShowCardRowNo() {
		// TODO Auto-generated method stub
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nc.ui.trade.bill.ICardController#isShowCardTotal()
	 */
	public boolean isShowCardTotal() {
		// TODO Auto-generated method stub
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nc.ui.trade.controller.IControllerBase#getBillType()
	 */
	public String getBillType() {
		// TODO Auto-generated method stub
		return FbmBusConstant.BILLTYPE_INVOICE;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nc.ui.trade.controller.IControllerBase#getBillVoName()
	 */
	public String[] getBillVoName() {
		// TODO Auto-generated method stub
		return new String[] { HYBillVO.class.getName(),
				RegisterVO.class.getName(), RegisterVO.class.getName() };
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
		return null;
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
		return RegisterVO.PK_REGISTER;
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
		return true;
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
		return new int[] { 
				IBillButton.Query, 
				IBillButton.Refresh,
				IBillButton.Add,
				IBillButton.Edit, 
				IBillButton.Delete, 
				IBillButton.Action,
				IFBMButton.Attach,  
				IFBMButton.Invoice_BusGroup,
				IFBMButton.Invoice_LinkQueryGroup,
				IBillButton.Print,
				IBillButton.Card};
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

	public boolean isSingleDetail() {
		// TODO Auto-generated method stub
		return false;
	}

}
