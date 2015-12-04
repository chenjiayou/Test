/**
 *
 */
package nc.ui.fbm.accept.listener;

import nc.itf.cdm.util.CommonUtil;
import nc.ui.fbm.accept.AcceptBillUI;
import nc.ui.fbm.pub.AbstractItemEditListener;
import nc.ui.fbm.pub.FBMManageUI;
import nc.ui.pub.bill.BillCardPanel;
import nc.ui.pub.bill.BillEditEvent;
import nc.vo.fbm.acceptbill.AcceptVO;
import nc.vo.pub.lang.UFDouble;

/**
 * <p>
 * Ʊ�ݸ����Ʊ�ݱ�ż�����
 * <p>
 * �����ˣ�lpf 
 * <b>���ڣ�2007-9-7
 * 
 */
public class AcceptSourcePKEditListener extends AbstractItemEditListener {

	/**
	 * 
	 */
	public AcceptSourcePKEditListener() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ui
	 * @param itemKey
	 */
	public AcceptSourcePKEditListener(FBMManageUI ui, String itemKey) {
		super(ui, itemKey);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ui
	 * @param itemKey
	 * @param pos
	 */
	public AcceptSourcePKEditListener(FBMManageUI ui, String itemKey, int pos) {
		super(ui, itemKey, pos);
		// TODO Auto-generated constructor stub
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nc.ui.fbm.pub.AbstractItemEditListener#responseEditEvent(nc.ui.pub.bill.BillEditEvent)
	 */
	@Override
	public void responseEditEvent(BillEditEvent editEvent) {
		BillCardPanel panel = getUI().getBillCardPanel();
		String pk_source = (String) panel.getHeadItem(AcceptVO.PK_SOURCE).getValueObject();
		if (CommonUtil.isNull(pk_source)) {
			return;
		}
		getUI().fireCardAfterEdit(AcceptVO.PK_CURR);
		
		((AcceptBillUI)getUI()).filterbacksecAccount(pk_source);
		
		calculateMoneyy(panel);
		
		// getUI().fireCardAfterEdit(AcceptVO.HOLDUNIT);
	}

	/**
	 * <p>
	 * ����ʵ�ʽ�����
	 * Ʊ����-���ر�֤����
	 * 
	 * 20081127-hzguo���޸�Ϊʵ�ʽ����Ʊ�����֤����+���ر�֤���
	 * <p>
	 * ���ߣ�lpf
	 * ���ڣ�2007-9-7
	 * @param panel
	 */
	private void calculateMoneyy(BillCardPanel panel) {
		String billmoneyy=(String) panel.getHeadItem(AcceptVO.BILLMONEYY).getValueObject();
		String returnMoneyy = (String) panel.getHeadItem(AcceptVO.BACKSECMONEY).getValueObject();
		String secMoney = (String) panel.getHeadItem(AcceptVO.SECURITYMONEY).getValueObject();
		
		UFDouble moneyy = new UFDouble(billmoneyy).sub(new UFDouble(secMoney))
				.add(new UFDouble(returnMoneyy));
		panel.getHeadItem(AcceptVO.MONEYY).setValue(moneyy);

		getUI().fireCardAfterEdit(AcceptVO.MONEYY);
	}

}
