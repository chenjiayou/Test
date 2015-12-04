package nc.ui.fbm.upgrade;

import nc.ui.trade.bill.IListController;
import nc.ui.trade.bill.ISingleController;
import nc.vo.fbm.upgrade.NoteTypeVO;
import nc.vo.trade.pub.HYBillVO;

public class UpdateCTL implements IListController ,ISingleController{

	public String[] getListBodyHideCol() {
		// TODO Auto-generated method stub
		return null;
	}

	public int[] getListButtonAry() {
		// TODO Auto-generated method stub
		return new int[]{IUpgradeBtn.UPGRADE_ARAP,IUpgradeBtn.UPGRADE_FBM,IUpgradeBtn.UPGRADE_CLEAR};
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
		return false;
	}

	public String getBillType() {
		// TODO Auto-generated method stub
		return "36GZ";
	}

	public String[] getBillVoName() {
		// TODO Auto-generated method stub
		return new String[]{HYBillVO.class.getName(),NoteTypeVO.class.getName(),NoteTypeVO.class.getName()};
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
		return 0;
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
		return "notetype_oid";
	}

	public Boolean isEditInGoing() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean isExistBillStatus() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isLoadCardFormula() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isSingleDetail() {
		// TODO Auto-generated method stub
		return false;
	}

}
