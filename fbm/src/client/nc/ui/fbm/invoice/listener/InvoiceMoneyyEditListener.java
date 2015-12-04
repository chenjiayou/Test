/**
 *
 */
package nc.ui.fbm.invoice.listener;

import nc.itf.cdm.util.CommonUtil;
import nc.ui.fbm.pub.AbstractItemEditListener;
import nc.ui.fbm.pub.FBMManageUI;
import nc.ui.pub.bill.BillEditEvent;
import nc.vo.fbm.pub.constant.FbmBusConstant;
import nc.vo.fbm.register.RegisterVO;
import nc.vo.pub.lang.UFDouble;

/**
 * <p>
 * 保证金类型根据金额计算保证金比例、金额
 * <p>创建人：lpf
 * <b>日期：2007-11-19
 *
 */
public class InvoiceMoneyyEditListener extends AbstractItemEditListener {

	/**
	 * 
	 */
	public InvoiceMoneyyEditListener() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ui
	 * @param itemKey
	 */
	public InvoiceMoneyyEditListener(FBMManageUI ui, String itemKey) {
		super(ui, itemKey);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ui
	 * @param itemKey
	 * @param pos
	 */
	public InvoiceMoneyyEditListener(FBMManageUI ui, String itemKey, int pos) {
		super(ui, itemKey, pos);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see nc.ui.fbm.pub.AbstractItemEditListener#responseEditEvent(nc.ui.pub.bill.BillEditEvent)
	 */
	@Override
	public void responseEditEvent(BillEditEvent editEvent) {
		String strMoney=(String) getUI().getBillCardPanel().getHeadItem(RegisterVO.MONEYY).getValueObject();
		if(CommonUtil.isNull(strMoney)){
			return;
		}

		UFDouble moneyy=new UFDouble(strMoney);
		String impawnmode = (String) getUI().getBillCardPanel().getHeadItem(RegisterVO.IMPAWNMODE).getValueObject();

		if(!CommonUtil.isNull(impawnmode)&&impawnmode.equals(FbmBusConstant.ASSURETYPE_BAIL)){
			String strassMoney=(String) getUI().getBillCardPanel().getHeadItem(RegisterVO.SECURITYMONEY).getValueObject();
			String strassRate=(String) getUI().getBillCardPanel().getHeadItem(RegisterVO.SECURITYRATE).getValueObject();

			if(!CommonUtil.isNull(strassMoney)){
				UFDouble assureMoneyy=new UFDouble(strassMoney);
				UFDouble assureRate=assureMoneyy.div(moneyy);
				getUI().getBillCardPanel().getHeadItem(RegisterVO.SECURITYRATE).setValue(assureRate.multiply(100));
			}else {
				if (!CommonUtil.isNull(strassRate)){
					UFDouble assureRate=new UFDouble(strassRate);
					UFDouble assureMoneyy=moneyy.multiply(assureRate);
					getUI().getBillCardPanel().getHeadItem(RegisterVO.SECURITYMONEY).setValue(assureMoneyy.div(100));
				}
			}
		}

	}

}
