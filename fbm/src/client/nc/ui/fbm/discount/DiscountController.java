package nc.ui.fbm.discount;

import nc.ui.fbm.pub.IFBMButton;
import nc.ui.trade.bill.AbstractManageController;
import nc.ui.trade.bill.ISingleController;
import nc.ui.trade.businessaction.IBusinessActionType;
import nc.ui.trade.button.IBillButton;
import nc.vo.fbm.discount.DiscountVO;
import nc.vo.fbm.pub.constant.FbmBusConstant;

/**
 * <p>
 *	贴现办理Controller类
 * <p>创建人：bsrl
 * <b>日期：2007-09
 *
 */
public class DiscountController extends AbstractManageController implements ISingleController{

	public static String BILLTYPE = FbmBusConstant.BILLTYPE_DISCOUNT_TRANSACT;
	public static String PK_FIELD = DiscountVO.PK_DISCOUNT;
	public static String[] VO_NAMES = {
			nc.vo.trade.pub.HYBillVO.class.getName(),
			nc.vo.fbm.discount.DiscountVO.class.getName(),
			nc.vo.fbm.discount.DiscountVO.class.getName() 
			};
	
	public String[] getCardBodyHideCol() {
		return null;
	}

	public int[] getCardButtonAry() {
		return new int[]{
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
				IFBMButton.Discount_LinkGroup,
				IBillButton.Print,
				IBillButton.Return
		};
	}

	public boolean isShowCardRowNo() {
		return false;
	}

	public boolean isShowCardTotal() {
		return false;
	}

	public String getBillType() {
		return BILLTYPE;
	}

	public String[] getBillVoName() {
		return VO_NAMES;
	}

	public String getBodyCondition() {
		return null;
	}

	public String getBodyZYXKey() {
		return null;
	}

	public int getBusinessActionType() {
		return IBusinessActionType.PLATFORM;
	}

	public String getChildPkField() {
		return null;
	}

	public String getHeadZYXKey() {
		return null;
	}

	public String getPkField() {
		return PK_FIELD;
	}

	public Boolean isEditInGoing() throws Exception {
		return null;
	}

	public boolean isExistBillStatus() {
		return true;
	}

	public boolean isLoadCardFormula() {
		return false;
	}

	public String[] getListBodyHideCol() {
		return null;
	}

	public int[] getListButtonAry() {
		return new int[]{
				IBillButton.Query,
				IBillButton.Refresh,
				IBillButton.Add,
				IBillButton.Edit,
				IBillButton.Delete,
				IBillButton.Copy,
				IBillButton.Action,
				IFBMButton.Discount_LinkGroup,
				IBillButton.Print,
				IBillButton.Card
		};
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

	public boolean isSingleDetail() {
		return false;
	}

}
