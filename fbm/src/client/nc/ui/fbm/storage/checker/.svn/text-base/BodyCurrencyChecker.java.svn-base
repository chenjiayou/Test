/**
 *
 */
package nc.ui.fbm.storage.checker;

import java.util.ArrayList;

import nc.itf.cdm.util.CommonUtil;
import nc.ui.fbm.pub.AbstractUIChecker;
import nc.ui.fbm.pub.FBMManageUI;
import nc.ui.pub.beans.UIRefPane;
import nc.ui.pub.bill.BillItem;
import nc.vo.fbm.storage.StorageBVO;

/**
 * <p>
 * 表体币种校验
 * <p>创建人：lpf
 * <b>日期：2007-11-29
 *
 */
public class BodyCurrencyChecker extends AbstractUIChecker {
	private String accItemKey;
	/**
	 *
	 */
	public BodyCurrencyChecker() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ui
	 */
	public BodyCurrencyChecker(FBMManageUI ui) {
		super(ui);
		// TODO Auto-generated constructor stub
	}

	public BodyCurrencyChecker(FBMManageUI ui, String accItemKey) {
		super(ui);
		this.accItemKey = accItemKey;
	}

	/* (non-Javadoc)
	 * @see nc.ui.fbm.pub.AbstractUIChecker#check()
	 */
	@Override
	public String check() {
		int rowcount = getUI().getBillCardPanel().getBillData().getBillModel().getRowCount();
		java.util.List<String> list = new ArrayList<String>();
		String currencyPk = null;
		for (int i = 0; i < rowcount; i++) {
			currencyPk = (String) getUI().getBillCardPanel().getBillData().getBillModel().getValueAt(i, StorageBVO.PK_CURR);
			if(i==0)
				list.add(currencyPk);
			else{
				if(!list.contains(currencyPk)){
					return nc.ui.ml.NCLangRes.getInstance().getStrByID("36201010","UPP36201010-000000")/* @res"第"*/+(i+1)+nc.ui.ml.NCLangRes.getInstance().getStrByID("36201010","UPP36201010-000001")/* @res"行票据币种不一致,无法保存"*/;
				}
			}
		}

		if(!CommonUtil.isNull(accItemKey)){
			BillItem accountItem = getUI().getBillCardPanel().getHeadItem(accItemKey);
			UIRefPane refpane = (UIRefPane) accountItem.getComponent();
			String pk_currtype = (String) refpane.getRefValue("bd_accid.pk_currtype");
			if(!CommonUtil.isNull(pk_currtype)){
				if(!pk_currtype.equals(currencyPk)){
					return nc.ui.ml.NCLangRes.getInstance().getStrByID("36201010","UPP36201010-000002")/* @res"内部账户币种和表体票据币种不一致，无法保存"*/;
				}
			}
		}
		return null;
	}

}