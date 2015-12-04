package nc.ui.fbm.gather;

import nc.ui.fbm.pub.IFBMButton;
import nc.ui.trade.bill.AbstractManageController;
import nc.ui.trade.bill.ISingleController;
import nc.ui.trade.businessaction.IBusinessActionType;
import nc.ui.trade.button.IBillButton;

public class GatherController extends AbstractManageController implements
		ISingleController {

	public static String BILLTYPE = "36GA";
	public static String PK_FIELD = "pk_gather";

	public static String[] VO_NAMES = {
			nc.vo.trade.pub.HYBillVO.class.getName(),
			nc.vo.fbm.register.RegisterVO.class.getName(),
			nc.vo.fbm.register.RegisterVO.class.getName() };

	public GatherController() {
		// TODO Auto-generated constructor stub
	}

	public String[] getCardBodyHideCol() {
		// TODO Auto-generated method stub
		return null;
	}

	// 修改人：zhouwb 2008-9-17 添加［收票完成、取消收票］按钮
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
				IFBMButton.BTNVO_SPWC,
				IFBMButton.BTNVO_QXSP,
				IFBMButton.Gather_BusGroup,
				IFBMButton.Attach, 							
				IFBMButton.Gather_LQueryGroup,
				IBillButton.Print,
				IBillButton.Return
		};
	}

	public boolean isShowCardRowNo() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isShowCardTotal() {
		// TODO Auto-generated method stub
		return false;
	}

	public String getBillType() {
		// TODO Auto-generated method stub
		return BILLTYPE;
	}

	public String[] getBillVoName() {
		// TODO Auto-generated method stub
		return VO_NAMES;
	}

	public String getBodyCondition() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getBodyZYXKey() {
		// TODO Auto-generated method stub
		return null;
	}

	public int getBusinessActionType() {
		// TODO Auto-generated method stub
		return IBusinessActionType.PLATFORM;
	}

	public String getChildPkField() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getHeadZYXKey() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getPkField() {
		// TODO Auto-generated method stub
		return PK_FIELD;
	}

	public Boolean isEditInGoing() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean isExistBillStatus() {
		// TODO Auto-generated method stub
		return true;
	}

	public boolean isLoadCardFormula() {
		// TODO Auto-generated method stub
		return false;
	}

	public String[] getListBodyHideCol() {
		// TODO Auto-generated method stub
		return null;
	}

	public int[] getListButtonAry() {
		// TODO Auto-generated method stub
		return new int[] { 
				IBillButton.Query, 
				IBillButton.Refresh,
				IBillButton.Add,
				IBillButton.Edit, 
				IBillButton.Delete, 
				IBillButton.Action,
				IFBMButton.Gather_BusGroup,
				IFBMButton.FBM_SELECTALL,
				IFBMButton.FBM_CANCELSELECT,
				IFBMButton.FBM_INVERTSELECT,
				IFBMButton.Gather_Batch,
				IFBMButton.Attach,
				IFBMButton.Gather_LQueryGroup,
				IBillButton.Print,
				IBillButton.Card
		};
	}

	public String[] getListHeadHideCol() {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean isShowListRowNo() {
		// TODO Auto-generated method stub
		return true;
	}

	public boolean isShowListTotal() {
		// TODO Auto-generated method stub
		return true;
	}

	public boolean isSingleDetail() {
		// TODO Auto-generated method stub
		return false;
	}

}
