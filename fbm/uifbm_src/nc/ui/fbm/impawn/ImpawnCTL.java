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
 * ���ܣ� ��Ѻ������������ ���ڣ�2007-9-19 ����Ա��wues
 */
public class ImpawnCTL extends AbstractManageController implements
		ISingleController {
	/**
	 * �˵�����Ҫ��VO����
	 */
	private final String[] VO_ARRAY = new String[] { HYBillVO.class.getName(),
			ImpawnVO.class.getName(), ImpawnVO.class.getName() };

	public ImpawnCTL() {
		super();
	}

	/**
	 * ȡ����������
	 */
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
	 * ���ص�������
	 */
	public String getBillType() {
		return FbmBusConstant.BILLTYPE_IMPAWN;
	}

	/**
	 * ����VO����
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
	 * ��ƽ̨
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
	 * �������ĵ��ݣ���Ҫ���ó�true
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
