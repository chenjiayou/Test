package nc.ui.fbm.impawn;

import nc.ui.fbm.pub.IFBMButton;
import nc.ui.trade.bill.AbstractManageController;
import nc.ui.trade.bill.ISingleController;
import nc.ui.trade.businessaction.IBusinessActionType;
import nc.ui.trade.button.IBillButton;
import nc.vo.fbm.impawn.ImpawnVO;
import nc.vo.fbm.pub.constant.FbmBusConstant;
import nc.vo.trade.pub.HYBillVO;

/**
 * 功能： 质押管理界面控制器 日期：2007-9-19 程序员：wues
 */
public class ImpawnCTL extends AbstractManageController implements
		ISingleController {
	/**
	 * 此单据需要的VO序列
	 */
	private final String[] VO_ARRAY = new String[] { HYBillVO.class.getName(),
			ImpawnVO.class.getName(), ImpawnVO.class.getName() };

	public ImpawnCTL() {
		super();
	}

	/**
	 * 取表体隐藏列
	 */
	public String[] getCardBodyHideCol() {
		return null;
	}

	/**
	 * 卡片显示时需要的按钮
	 */
	public int[] getCardButtonAry() {
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
				IFBMButton.Discount_LinkGather,
				IBillButton.Print,
				IBillButton.Return};
	}

	public boolean isShowCardRowNo() {
		return false;
	}

	public boolean isShowCardTotal() {
		return false;
	}

	/**
	 * 返回单据类型
	 */
	public String getBillType() {
		return FbmBusConstant.BILLTYPE_IMPAWN;
	}

	/**
	 * 返回VO序列
	 */
	public String[] getBillVoName() {
		return VO_ARRAY;
	}

	public String getBodyCondition() {
		return null;
	}

	public String getBodyZYXKey() {
		return null;
	}

	/**
	 * 走平台
	 */
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
		return ImpawnVO.PK_IMPAWN;
	}

	public Boolean isEditInGoing() throws Exception {
		return null;
	}

	/**
	 * 走审批的单据，需要设置成true
	 */
	public boolean isExistBillStatus() {
		return true;
	}

	public boolean isLoadCardFormula() {
		return false;
	}

	public String[] getListBodyHideCol() {
		return null;
	}

	/**
	 * 列表时返回的按钮序列
	 */
	public int[] getListButtonAry() {
		return  new int[] { 
				IBillButton.Query,
				IBillButton.Refresh,
				IBillButton.Add, 
				IBillButton.Edit, 
				IBillButton.Delete,
				IBillButton.Copy, 
				IBillButton.Action, 
				IFBMButton.Discount_LinkGather,
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
