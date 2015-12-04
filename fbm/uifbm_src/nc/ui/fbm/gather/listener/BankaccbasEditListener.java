package nc.ui.fbm.gather.listener;

import nc.ui.fbm.pub.AbstractItemEditListener;
import nc.ui.fbm.pub.FBMManageUI;
import nc.ui.fbm.pub.refmodel.BaseInfoAccBankRefModel;
import nc.ui.pub.beans.UIRefPane;
import nc.ui.pub.bill.BillEditEvent;

/**
 * 编辑银行账号，带出客商
 * @author xwq
 *
 * 2008-12-27
 */
public class BankaccbasEditListener extends AbstractItemEditListener{

	private String custKey;//客商字段
	private String bankdocKey;//银行字段
	
	public BankaccbasEditListener(FBMManageUI ui,String itemKey,String custKey,String bankdocKey){
		super(ui,itemKey);
		this.custKey = custKey;
		this.bankdocKey = bankdocKey;
	}
	@Override
	public void responseEditEvent(BillEditEvent editEvent) {
		//2009.7.28 xwq 通过账号带客商修改为通过携带处理，因为有可能需要携带的不是开户公司而是被授权公司客商
//		String pk_bankaccbas =   getUI().getBillCardPanel().getHeadItem(getItemKey()).getValue();
//		UIRefPane bankaccbasPane =  (UIRefPane)getUI().getBillCardPanel().getHeadItem(getItemKey()).getComponent();
//		if(pk_bankaccbas == null){
//			return;
//		}
//		try {
//			String pk_cubasdoc = FBMPublicQry.retrivePk_cubasdocByPk_bankaccbas(pk_bankaccbas);
//			if(pk_cubasdoc !=null){
//				BillItem custItem = getUI().getBillCardPanel().getHeadItem(custKey);
//				custItem.setValue(pk_cubasdoc);
//			}
//		} catch (BusinessException e) {
//			Logger.error(e.getMessage());
//		}
		
		UIRefPane accbankunit = (UIRefPane) getUI().getBillCardPanel().getHeadItem(getItemKey()).getComponent();
		//根据账号携带银行
		Object bankdoc = accbankunit.getRefModel().getValue(BaseInfoAccBankRefModel.BANKDOC_FIELD);
		if(bankdoc!=null){
			getUI().getBillCardPanel().getHeadItem(bankdocKey).setValue(bankdoc);
		}
		
	}

}
