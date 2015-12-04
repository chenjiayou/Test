package nc.ui.fbm.illegal;

import nc.ui.trade.bill.ICardController;
import nc.ui.trade.bill.ISingleController;
import nc.ui.trade.businessaction.IBusinessActionType;
import nc.ui.trade.button.IBillButton;
import nc.vo.fbm.illegal.IllegalVO;
import nc.vo.fbm.pub.constant.FbmBusConstant;
import nc.vo.trade.pub.HYBillVO;

/**
 * �Ƿ�Ʊ�ݴ���Ľ��������Controller
 * 
 * @author wues
 * 
 */
public class IllegalBillCTL implements ICardController, ISingleController {
	/**
	 * ����ͷ��false��������:true
	 */
	public boolean isSingleDetail() {
		return true;
	}

	/**
	 * ���ؾ��忨Ƭ���ӱ�����,���������� ע��: �����ڸ÷����ڽ���ʵ����,�÷����ڹ����е��ã�
	 */
	public String[] getCardBodyHideCol() {
		return null;
	}

	/**
	 * ��Ƭ��ʾʱ��Ҫ��ʾ�İ�ť����
	 */
	public int[] getCardButtonAry() {
		return new int[] { IBillButton.Query, IBillButton.Edit,
				IBillButton.Line, IBillButton.Save, IBillButton.Cancel,
				IBillButton.Refresh, IBillButton.Print };
	}

	/**
	 * ��Ƭ�Ƿ���ʾ�к�,Ĭ�ϲ�������ʾ
	 */
	public boolean isShowCardRowNo() {
		return false;
	}

	/**
	 * ��Ƭ�Ƿ���ʾ�ϼ��У�Ĭ�ϲ���ʾ
	 */
	public boolean isShowCardTotal() {
		return false;
	}

	/**
	 * ����ע��Ĵ˵�������
	 */
	public String getBillType() {
		return FbmBusConstant.BILLTYPE_ILLEGAL;
	}

	/**
	 * ������Ӧ��VO����
	 */
	public String[] getBillVoName() {
		return new String[] { HYBillVO.class.getName(),
				IllegalVO.class.getName(), IllegalVO.class.getName() };
	}

	/**
	 * �ӱ��Ӧ�Ĳ�ѯ����
	 */
	public String getBodyCondition() {
		return null;
	}

	/**
	 * ��ñ����Զ�����(������)�ؼ���
	 */
	public String getBodyZYXKey() {
		return null;
	}

	/**
	 * ���BusinessAction����(BD\PF) ����Ϊ����ƽ̨
	 */
	public int getBusinessActionType() {
		return IBusinessActionType.BD;
	}

	/**
	 * ���ظõ����ӱ������
	 */
	public String getChildPkField() {
		return IllegalVO.PK_ILLEGAL;
	}

	/**
	 * ��ñ�ͷ�Զ�����ؼ���
	 */
	public String getHeadZYXKey() {
		return null;
	}

	/**
	 * ���ظõ��������������
	 */
	public String getPkField() {
		return IllegalVO.PK_ILLEGAL;
	}

	/**
	 * �����������Ƿ���޸ġ� ϵͳĬ�ϲ����޸ģ�������޸������ظ÷���
	 */
	public Boolean isEditInGoing() throws Exception {
		return new Boolean(false);
	}

	/**
	 * �Ƿ���ڵ���״̬��
	 */
	public boolean isExistBillStatus() {
		return false;
	}

	/**
	 * �Ƿ���ؿ�Ƭ��ʽ��ȱʡfalse
	 */
	public boolean isLoadCardFormula() {
		return false;
	}

	/**
	 * �����б���ʾʱ����������
	 */
	public String[] getListBodyHideCol() {
		return null;
	}

	/**
	 * �����б���ʾʱ��ͷ������
	 */
	public String[] getListHeadHideCol() {
		return null;
	}

	/**
	 * �б���ʾʱ�Ƿ���ʾ�к�
	 */
	public boolean isShowListRowNo() {
		return true;
	}

	/**
	 * �б���ʵʱ�Ƿ���ʾ�ϼ���
	 */
	public boolean isShowListTotal() {
		return true;
	}

}
