package nc.ui.fbm.endore;

import nc.ui.fbm.pub.IFBMButton;
import nc.ui.trade.bill.AbstractManageController;
import nc.ui.trade.bill.ISingleController;
import nc.ui.trade.businessaction.IBusinessActionType;
import nc.ui.trade.button.IBillButton;
import nc.vo.fbm.endore.EndoreVO;
import nc.vo.trade.pub.HYBillVO;

public class EndoreCTL extends AbstractManageController implements
		ISingleController {

	/**
	 * �˵�����Ҫ��VO����
	 */
	private final String[] VO_ARRAY = new String[] { HYBillVO.class.getName(),
			EndoreVO.class.getName(), EndoreVO.class.getName() };
	
	public EndoreCTL()
	{
		super();
	}
	
	public boolean isSingleDetail() {
		return false;
	}

	public String[] getCardBodyHideCol() {
		return null;
	}
	
	/**
	 * ��Ƭ��ʾʱ��Ҫ�İ�ť
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
				IFBMButton.Endore_LinkGroup,
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
		return "36GQ";
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
		return IBusinessActionType.PLATFORM;
	}

	public String getChildPkField() {
		
		return null;
	}

	public String getHeadZYXKey() {
		return null;
	}

	public String getPkField() {
		return EndoreVO.PK_ENDORE;
	}

	public Boolean isEditInGoing() throws Exception {
		return null;
	}
	
	//���������������״̬��
	public boolean isExistBillStatus() {
		return true;
	}
 
	
	
	public boolean isLoadCardFormula() {
		return false;
	}
	
	/**
	 * �б�ʱ���صİ�ť����
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
				IFBMButton.Endore_LinkGroup,
				IBillButton.Print,
				IBillButton.Card};
	}
	public String[] getListBodyHideCol() {
		return null;
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


}
