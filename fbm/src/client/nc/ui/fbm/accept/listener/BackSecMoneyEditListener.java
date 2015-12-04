/**
 *
 */
package nc.ui.fbm.accept.listener;

import nc.itf.cdm.util.CommonUtil;
import nc.ui.fbm.pub.AbstractItemEditListener;
import nc.ui.fbm.pub.FBMManageUI;
import nc.ui.pub.bill.BillCardPanel;
import nc.ui.pub.bill.BillEditEvent;
import nc.vo.fbm.acceptbill.AcceptVO;
import nc.vo.pub.lang.UFDouble;

/**
 * <p>
 * 返回保证金监听器
 * <p>创建人：lpf
 * <b>日期：2007-9-7
 *
 */
public class BackSecMoneyEditListener extends AbstractItemEditListener {

	/**
	 * 
	 */
	public BackSecMoneyEditListener() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ui
	 * @param itemKey
	 */
	public BackSecMoneyEditListener(FBMManageUI ui, String itemKey) {
		super(ui, itemKey);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ui
	 * @param itemKey
	 * @param pos
	 */
	public BackSecMoneyEditListener(FBMManageUI ui, String itemKey, int pos) {
		super(ui, itemKey, pos);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see nc.ui.fbm.pub.AbstractItemEditListener#responseEditEvent(nc.ui.pub.bill.BillEditEvent)
	 */
	@Override
	public void responseEditEvent(BillEditEvent editEvent) {
		BillCardPanel panel=getUI().getBillCardPanel();
		String backsrcmoney=(String) panel.getHeadItem(getItemKey()).getValueObject();
		if(CommonUtil.isNull(backsrcmoney)){
			backsrcmoney = "0";//如果清空则为0
		}
		String billmoneyy=(String) panel.getHeadItem(AcceptVO.BILLMONEYY).getValueObject();
		String secMoney = (String) panel.getHeadItem(AcceptVO.SECURITYMONEY).getValueObject();
		//NCdp200615046
		//票据付款时：当返回保证金>开票上的保证金金额时，实际结算金额应取票面金额，就不应单纯按公式计算了
		if(new UFDouble(backsrcmoney).sub(new UFDouble(secMoney)).doubleValue()>0){
			panel.getHeadItem(AcceptVO.MONEYY).setValue(billmoneyy);
		}else
		{
			UFDouble moneyy = new UFDouble(billmoneyy).sub(new UFDouble(secMoney))
					.add(new UFDouble(backsrcmoney));
			panel.getHeadItem(AcceptVO.MONEYY).setValue(moneyy);
		}
		getUI().fireCardAfterEdit(AcceptVO.MONEYY);
	}

}
