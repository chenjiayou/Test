package nc.ui.fbm.discountapply.reffilter;

import nc.itf.fbm.cust.CombineCust;
import nc.ui.fbm.pub.FBMManageUI;
import nc.ui.tm.framework.ref.filter.BillItemRefModelFilter;
import nc.ui.trade.base.AbstractBillUI;

public class GatherBankBusRefModelFilter extends BillItemRefModelFilter {


	private String bankdocKey = null;//银行档案字段名称
	
	public GatherBankBusRefModelFilter(AbstractBillUI ui) {
		super(ui);
	}


	public String getBankdocKey() {
		return bankdocKey;
	}

	public void setBankdocKey(String bankdocKey) {
		this.bankdocKey = bankdocKey;
	}

	@Override
	protected String getSelfFilterString() {
		Object pk_bankdoc = ((FBMManageUI)ui).getBillCardPanel().getHeadItem(bankdocKey).getValueObject();
		//存放地点为当前银行或内部客商PK
		if(pk_bankdoc!=null){
			return " ( keepunit = '"+pk_bankdoc+"' or exists (select 1 from "+CombineCust.getCombineCustSQL()+" where pk_cubasdoc = gathertable.keepunit and type=0))";
		}
		return null;
	}

}
