/**
 *
 */
package nc.ui.fbm.gather.listener;

import nc.ui.fbm.pub.AbstractItemEditListener;
import nc.ui.fbm.pub.FBMManageUI;
import nc.ui.pub.bill.BillEditEvent;
import nc.vo.fbm.pub.constant.FbmBusConstant;
import nc.vo.fbm.register.RegisterVO;

/**
 * <p>
 * 币种编辑，需要过滤付款银行账号
 * <p>
 * 创建人：lpf <b>日期：2007-11-2
 * 
 */
public class CurrencyEditListener extends AbstractItemEditListener {

	public CurrencyEditListener() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CurrencyEditListener(FBMManageUI ui, String itemKey, int pos) {
		super(ui, itemKey, pos);
		// TODO Auto-generated constructor stub
	}

	public CurrencyEditListener(FBMManageUI ui, String itemKey) {
		super(ui, itemKey);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void responseEditEvent(BillEditEvent editEvent) {
		String pk_curr = (String) getUI().getBillCardPanel().getHeadItem(RegisterVO.PK_CURR).getValueObject();

		if (getUI().getUIControl().getBillType().equals(FbmBusConstant.BILLTYPE_INVOICE)) {
			if (getUI().getBillCardPanel().getHeadItem(RegisterVO.PAYBANKACC) != null) {
				getUI().getBillCardPanel().getHeadItem(RegisterVO.PAYBANKACC).setValue(getUI().getBillCardPanel().getHeadItem(RegisterVO.PAYBANKACC).getValue());
			}
			if (getUI().getBillCardPanel().getHeadItem(RegisterVO.PAYACCNAME) != null) {
				getUI().getBillCardPanel().getHeadItem(RegisterVO.PAYACCNAME).setValue(getUI().getBillCardPanel().getHeadItem(RegisterVO.PAYACCNAME).getValue());
			}
			if (getUI().getBillCardPanel().getHeadItem(RegisterVO.RECEIVEBANKACC) != null) {
				getUI().getBillCardPanel().getHeadItem(RegisterVO.RECEIVEBANKACC).setValue(getUI().getBillCardPanel().getHeadItem(RegisterVO.RECEIVEBANKACC).getValue());
			}
			if (getUI().getBillCardPanel().getHeadItem(RegisterVO.RECEIVEACCNAME) != null) {
				getUI().getBillCardPanel().getHeadItem(RegisterVO.RECEIVEACCNAME).setValue(getUI().getBillCardPanel().getHeadItem(RegisterVO.RECEIVEACCNAME).getValue());
			}
			if (getUI().getBillCardPanel().getHeadItem(RegisterVO.SECURITYACCOUNT) != null) {
				getUI().getBillCardPanel().getHeadItem(RegisterVO.SECURITYACCOUNT).setValue(getUI().getBillCardPanel().getHeadItem(RegisterVO.SECURITYACCOUNT).getValue());
			}
			return;
		} else {
			// UIRefPane refpane = (UIRefPane)
			// getUI().getBillCardPanel().getHeadItem(RegisterVO.RECEIVEBANKACC).getComponent();
			// String isCust = (String) refpane.getRefValue("isCust");
			// if (!CommonUtil.isNull(pk_curr)) {
			// if (!CommonUtil.isNull(isCust)
			// && new UFBoolean(isCust).booleanValue()) {
			if (getUI().getBillCardPanel().getHeadItem(RegisterVO.RECEIVEBANKACC) != null) {
				getUI().getBillCardPanel().getHeadItem(RegisterVO.RECEIVEBANKACC).setValue(getUI().getBillCardPanel().getHeadItem(RegisterVO.RECEIVEBANKACC).getValue());
			}
			if (getUI().getBillCardPanel().getHeadItem(RegisterVO.RECEIVEACCNAME) != null) {
				if(getUI().getBillCardPanel().getHeadItem(RegisterVO.RECEIVEBANKACC).getValue() == null){
					getUI().getBillCardPanel().getHeadItem(RegisterVO.RECEIVEACCNAME).setValue(null);
				}
			}
			// }
			// }

			// UIRefPane refpane1 = (UIRefPane)
			// getUI().getBillCardPanel().getHeadItem(RegisterVO.PAYBANKACC).getComponent();
			// String isCust1 = (String) refpane1.getRefValue("isCust");
			// if (!CommonUtil.isNull(pk_curr)) {
			// if (!CommonUtil.isNull(isCust1)
			// && new UFBoolean(isCust1).booleanValue()) {
			if (getUI().getBillCardPanel().getHeadItem(RegisterVO.PAYBANKACC) != null) {
				getUI().getBillCardPanel().getHeadItem(RegisterVO.PAYBANKACC).setValue(getUI().getBillCardPanel().getHeadItem(RegisterVO.PAYBANKACC).getValue());
			}
			if (getUI().getBillCardPanel().getHeadItem(RegisterVO.PAYACCNAME) != null) {
				if(getUI().getBillCardPanel().getHeadItem(RegisterVO.PAYBANKACC).getValue() == null){
					getUI().getBillCardPanel().getHeadItem(RegisterVO.PAYACCNAME).setValue(null);
				}
			}
			// }
			// }
		}
	}
}
