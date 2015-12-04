/**
 *
 */
package nc.ui.fbm.invoice.reffilter;

import nc.itf.cdm.util.CommonUtil;
import nc.ui.fbm.pub.FBMManageUI;
import nc.ui.fbm.pub.refmodel.FBMBankAccBasRefModel;
import nc.ui.pub.bill.BillItem;
import nc.ui.tm.framework.ref.filter.BillItemRefModelFilter;

/**
 * <p>
 * 客商是基本档案，开户银行是支持手工录入的.
 * <p>
 * 创建人：lpf <b>日期：2007-12-24
 * 
 */
public class PayAccbankRefModelFilter extends BillItemRefModelFilter {
	private FBMManageUI ui;
	private String sourceKey;

	/**
	 * @param billitem
	 */
	public PayAccbankRefModelFilter(BillItem billitem) {
		super(billitem);
		// TODO Auto-generated constructor stub
	}

	public PayAccbankRefModelFilter(BillItem billitem, FBMManageUI ui,
			String sourceKey) {
		super(billitem);
		this.ui = ui;
		this.sourceKey = sourceKey;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nc.ui.tm.framework.ref.filter.AbstractRefModelFilter#getSelfFilterString
	 * ()
	 */
	@Override
	protected String getSelfFilterString() {
		String sqlfilter = "";
		String custPk = (String) ui.getBillCardPanel().getHeadItem(sourceKey).getValueObject();
		if (CommonUtil.isNull(custPk)) {
			custPk = (String) ui.getRefTakenProccessor().getValueByTakenInCard(sourceKey);
		}

		// 选择了客商或者手工录入客商
		if (!CommonUtil.isNull(custPk)) {

			// sqlfilter =
			// " ( pk_cubasdoc ='"+custPk+"' or ("+FBMBankAccBasRefModel.AUTHCORP+" = (select pk_corp1 from bd_cubasdoc where pk_cubasdoc ='"+
			// custPk + "') and pk_cubasdoc='"+custPk+"')  )  ";
			sqlfilter = " (  pk_cubasdoc ='"
					+ custPk
					+ "' or ("
					+ FBMBankAccBasRefModel.AUTHCORP
					+ " = (select pk_corp1 from bd_cubasdoc where pk_cubasdoc ='"
					+ custPk
					+ "') )  )  ";
		}
		return sqlfilter;
	}

}
