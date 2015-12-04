package nc.ui.fbm.discount.listener;

import nc.itf.cdm.util.CommonUtil;
import nc.ui.fbm.pub.AbstractItemEditListener;
import nc.ui.fbm.pub.FBMClientInfo;
import nc.ui.fbm.pub.FBMManageUI;
import nc.ui.pub.beans.UIComboBox;
import nc.ui.pub.beans.UIRefPane;
import nc.ui.pub.bill.BillEditEvent;
import nc.ui.pub.bill.BillItem;
import nc.vo.fbm.discount.DiscountVO;
import nc.vo.fbm.pub.constant.FbmBusConstant;
import nc.vo.fbm.pub.constant.FbmStatusConstant;
import nc.vo.fbm.register.RegisterVO;

public class DiscountPKSourceListener extends AbstractItemEditListener {

	
	public DiscountPKSourceListener() {
	}

	public DiscountPKSourceListener(FBMManageUI ui, String itemKey) {
		super(ui, itemKey);
	}
	
	
	@Override
	public void responseEditEvent(BillEditEvent editEvent) {
		//点击票据编号时，票据类别的判断。私有与统管。	
		if ((FBMClientInfo.isSettleCenter())) {
			String pk_corp = (String) ((UIRefPane) editEvent.getSource())
					.getRefModel().getValue("pk_corp");
			String cur_pk_corp = FBMClientInfo.getCorpPK();
			if(pk_corp != null){
				if (cur_pk_corp.equals(pk_corp)) {// 设置为私有
					((UIComboBox) getUI().getBillCardPanel().getHeadItem(
							DiscountVO.OPBILLTYPE).getComponent())
							.setSelectedItem(FbmBusConstant.BILL_PRIVACY);
					if (FbmBusConstant.BILLTYPE_DISCOUNT_TRANSACT.equals( getUI().getBillCardPanel().getBillType())) {
						BillItem planitem = getUI().getBillCardPanel().getHeadItem(DiscountVO.FBMPLANITEM);
						planitem.setEnabled(true);
					}
				} else {
					((UIComboBox) getUI().getBillCardPanel().getHeadItem(
							DiscountVO.OPBILLTYPE).getComponent())
							.setSelectedItem(FbmBusConstant.BILL_UNISTORAGE);
					if (FbmBusConstant.BILLTYPE_DISCOUNT_TRANSACT.equals( getUI().getBillCardPanel().getBillType())) {
						BillItem planitem = getUI().getBillCardPanel().getHeadItem(DiscountVO.FBMPLANITEM);
						planitem.setEnabled(false);
						planitem.setValue(null);
					}
				}
			}
		}
		UIRefPane pane = (UIRefPane) getUI().getBillCardPanel().getHeadItem(
				DiscountVO.PK_SOURCE).getComponent();
		String registerstatus = (String) pane.getRefValue(RegisterVO.REGISTERSTATUS);
		String currencyPk = (String) pane.getRefValue("pk_curr");
		if (FbmStatusConstant.HAS_BANK_KEEP.equals(registerstatus)) {// 已银行托管
			String keepunit = (String) pane.getRefValue("keepunit");
			
			//getUI().getBillCardPanel().getHeadItem(DiscountVO.PK_DISCOUNT_BANK).setEnabled(false);
			
			getUI().getBillCardPanel().getHeadItem(DiscountVO.PK_DISCOUNT_BANK)
					.setValue(keepunit);
		} else {
//			getUI().getBillCardPanel().getHeadItem(DiscountVO.PK_DISCOUNT_BANK).setEnabled(true);
//			getUI().getBillCardPanel().getHeadItem(DiscountVO.PK_DISCOUNT_BANK)
//					.setValue(null);
		}
		UIRefPane bankaccpane = (UIRefPane) getUI().getBillCardPanel().getHeadItem(
				DiscountVO.DISCOUNT_ACCOUNT).getComponent();
		String oldPk = (String) bankaccpane.getRefValue("pk_currtype");
		if(!CommonUtil.isNull(currencyPk)&&!CommonUtil.isNull(oldPk)){
			if(!currencyPk.equals(oldPk)){
				getUI().getBillCardPanel().getHeadItem(DiscountVO.DISCOUNT_ACCOUNT).setValue(null);
				getUI().getBillCardPanel().getHeadItem(DiscountVO.PK_DISCOUNT_BANK).setValue(null);
			}
		}
	}
}
			
	


