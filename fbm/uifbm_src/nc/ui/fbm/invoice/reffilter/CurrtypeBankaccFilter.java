package nc.ui.fbm.invoice.reffilter;

import nc.itf.cdm.util.CommonUtil;
import nc.ui.fbm.pub.FBMManageUI;
import nc.ui.pub.bill.BillItem;
import nc.ui.tm.framework.ref.filter.BillItemRefModelFilter;

/**
 * ±ÒÖÖ¹ýÂËÆ÷
 * @author xwq
 *
 * 2008-10-28
 */
public class CurrtypeBankaccFilter extends BillItemRefModelFilter {
	private FBMManageUI ui;
	private String currKey;
	
	public CurrtypeBankaccFilter(FBMManageUI ui,
			String currKey) {
		super();
		this.ui = ui;
		this.currKey = currKey;
	}
	@Override
	protected String getSelfFilterString() {
		String pk_curr = (String) ui.getBillCardPanel().getHeadItem(currKey).getValueObject();
		if(CommonUtil.isNull(pk_curr)){
			pk_curr = (String) ui.getRefTakenProccessor().getValueByTakenInCard(currKey);
		}
		if(!CommonUtil.isNull(pk_curr)){
			return "pk_currtype='" + pk_curr + "'";
		}
		return null;
	}
	
}
