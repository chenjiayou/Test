/**
 *
 */
package nc.ui.fbm.invoice.listener;

import nc.itf.cdm.util.CommonUtil;
import nc.ui.fbm.pub.AbstractItemEditListener;
import nc.ui.fbm.pub.FBMManageUI;
import nc.ui.pub.bill.BillEditEvent;
import nc.vo.fbm.register.RegisterVO;
import nc.vo.pub.lang.UFDouble;

/**
 * <p>
 * 保证金额计算
 * <p>创建人：lpf
 * <b>日期：2007-9-4
 *
 */
public class AssureMoneyCalListener extends AbstractItemEditListener {

	/**
	 * 
	 */
	public AssureMoneyCalListener() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ui
	 * @param itemKey
	 */
	public AssureMoneyCalListener(FBMManageUI ui, String itemKey) {
		super(ui, itemKey);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ui
	 * @param itemKey
	 * @param pos
	 */
	public AssureMoneyCalListener(FBMManageUI ui, String itemKey, int pos) {
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
		//编辑比例调整金额，编辑金额调整比例
		UFDouble moneyy=new UFDouble(strMoney);
		String strassMoney=(String) getUI().getBillCardPanel().getHeadItem(RegisterVO.SECURITYMONEY).getValueObject();
		String strassRate=(String) getUI().getBillCardPanel().getHeadItem(RegisterVO.SECURITYRATE).getValueObject();
		
		if(getItemKey().equalsIgnoreCase(RegisterVO.SECURITYRATE)){
			if(!CommonUtil.isNull(strassRate)){
				UFDouble assureRate=new UFDouble(strassRate);
				UFDouble assureMoneyy=moneyy.multiply(assureRate);
				getUI().getBillCardPanel().getHeadItem(RegisterVO.SECURITYMONEY).setValue(assureMoneyy.div(100));
			}else{
				getUI().getBillCardPanel().getHeadItem(RegisterVO.SECURITYMONEY).setValue(null);
			}
			getUI().fireCardAfterEdit(RegisterVO.SECURITYMONEY);
		}else if(getItemKey().equalsIgnoreCase(RegisterVO.SECURITYMONEY)){
			if(!CommonUtil.isNull(strassMoney)){
				UFDouble assureMoneyy=new UFDouble(strassMoney);
				UFDouble assureRate=assureMoneyy.div(moneyy);
				getUI().getBillCardPanel().getHeadItem(RegisterVO.SECURITYRATE).setValue(assureRate.multiply(100));
			}else{
				getUI().getBillCardPanel().getHeadItem(RegisterVO.SECURITYRATE).setValue(null);
			}
		}
	}

}
