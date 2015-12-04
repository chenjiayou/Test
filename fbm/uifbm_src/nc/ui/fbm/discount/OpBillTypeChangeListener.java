package nc.ui.fbm.discount;

import nc.ui.fbm.pub.AbstractItemEditListener;
import nc.ui.fbm.pub.FBMManageUI;
import nc.ui.pub.beans.UIRefPane;
import nc.ui.pub.bill.BillEditEvent;
import nc.ui.pub.bill.BillItem;
import nc.vo.fbm.consignbank.CollectionVO;
import nc.vo.fbm.discount.DiscountVO;
import nc.vo.fbm.endore.EndoreVO;
import nc.vo.fbm.pub.constant.FbmBusConstant;

/**
 * 票据类别监听器
 *
 * @author wues
 *
 */
public class OpBillTypeChangeListener extends AbstractItemEditListener {

	public OpBillTypeChangeListener() {
	}

	public OpBillTypeChangeListener(FBMManageUI ui, String itemKey) {
		super(ui, itemKey);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see nc.ui.fbm.pub.AbstractItemEditListener#responseEditEvent(nc.ui.pub.bill.BillEditEvent)
	 */
	@Override
	public void responseEditEvent(BillEditEvent editEvent) {
		BillItem item = getUI().getBillCardPanel().getHeadItem(
				DiscountVO.PK_SOURCE);
		item.setValue(null);
		BillEditEvent e = new BillEditEvent(item, nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("common","UC000-0003022")/*@res "票据编号"*/, DiscountVO.PK_SOURCE);
		getUI().afterEdit(e);
		if (FbmBusConstant.BILLTYPE_DISCOUNT_TRANSACT.equals( getUI().getBillCardPanel().getBillType())) {//贴现办理单
			BillItem item1 = getUI().getBillCardPanel().getHeadItem(DiscountVO.PK_DISCOUNT_APP);
			item1.setValue(null);
			//如果触发此方法则带出的opbilltype为空，这样每次改变后票据类别的值均为null
			//getUI().fireCardAfterEdit(DiscountVO.PK_DISCOUNT_APP);

			getUI().getBillCardPanel().getHeadItem(DiscountVO.FRATE).setValue(null);
			getUI().getBillCardPanel().getHeadItem(DiscountVO.BRATE).setValue(null);
			getUI().getBillCardPanel().getHeadItem(DiscountVO.MONEYB).setValue(null);
			getUI().getBillCardPanel().getHeadItem(DiscountVO.MONEYF).setValue(null);
			getUI().getBillCardPanel().getHeadItem(DiscountVO.DISCOUNT_ACCOUNT).setValue(null);
			getUI().getBillCardPanel().getHeadItem(DiscountVO.PK_DISCOUNT_BANK).setValue(null);
			getUI().getBillCardPanel().getHeadItem(DiscountVO.DISCOUNTCHARGE).setValue(null);
			getUI().getBillCardPanel().getHeadItem(DiscountVO.DISCOUNTDELAYDAYNUM).setValue(null);
			getUI().getBillCardPanel().getHeadItem(DiscountVO.DISCOUNTINTEREST).setValue(null);
			getUI().getBillCardPanel().getHeadItem(DiscountVO.CHARGEMONEYB).setValue(null);
			getUI().getBillCardPanel().getHeadItem(DiscountVO.CHARGEMONEYF).setValue(null);
			getUI().getBillCardPanel().getHeadItem(DiscountVO.INTERESTMONEYB).setValue(null);
			getUI().getBillCardPanel().getHeadItem(DiscountVO.INTERESTMONEYF).setValue(null);

			getUI().getBillCardPanel().getHeadItem(DiscountVO.DISCOUNTYRATESCHEME).setValue(null);
			getUI().getBillCardPanel().getHeadItem(DiscountVO.DDISCOUNTDATE).setValue(null);
			getUI().getBillCardPanel().getHeadItem(DiscountVO.DISCOUNTYRATE).setValue(null);
			getUI().getBillCardPanel().getHeadItem(DiscountVO.MONEYY).setValue(null);
			
			String opbilltype = getUI().getBillCardPanel().getHeadItem(getItemKey()).getValue();
			BillItem planitem = getUI().getBillCardPanel().getHeadItem(DiscountVO.FBMPLANITEM);
			if(planitem !=null){
				if(opbilltype.equals(FbmBusConstant.BILL_PRIVACY)){
					planitem.setEnabled(true);
				}else{
					planitem.setEnabled(false);
					planitem.setValue(null);
				}
			}
		}
		if (FbmBusConstant.BILLTYPE_COLLECTION_UNIT.equals( getUI().getBillCardPanel().getBillType())) {
			String opbilltype = getUI().getBillCardPanel().getHeadItem(getItemKey()).getValue();
			BillItem planitem = getUI().getBillCardPanel().getHeadItem(CollectionVO.FBMPLANITEM);
			if(planitem !=null){
				if(opbilltype.equals(FbmBusConstant.BILL_PRIVACY)){
					planitem.setEnabled(true);
				}else{
					planitem.setEnabled(false);
					planitem.setValue(null);
				}
			}
		}

		//添加了背书Listener 当选择票据类别时，清空被背书单位。
		if (FbmBusConstant.BILLTYPE_ENDORE.equals( getUI().getBillCardPanel().getBillType())) {//背书办理单
			Object pk_endorsee  = getUI().getBillCardPanel().getHeadItem(EndoreVO.ENDORSEE).getValueObject();
			((UIRefPane)getUI().getBillCardPanel().getHeadItem(EndoreVO.ENDORSEE).getComponent()).setPK(pk_endorsee);

		}

	}
}