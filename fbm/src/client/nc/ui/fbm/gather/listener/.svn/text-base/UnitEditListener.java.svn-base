/**
 *
 */
package nc.ui.fbm.gather.listener;

import nc.itf.cdm.util.CommonUtil;
import nc.ui.fbm.pub.AbstractItemEditListener;
import nc.ui.fbm.pub.FBMManageUI;
import nc.ui.pub.beans.UIRefPane;
import nc.ui.pub.bill.BillEditEvent;
import nc.vo.fbm.register.RegisterVO;

/**
 * <p>
 * 收票单位编辑监听 选择单位，如果是基本档案则需要清空银行账号；手工录入则不需要 否则不需要
 * 
 * <p>
 * 创建人：lpf <b>日期：2007-9-13
 * 
 */
public class UnitEditListener extends AbstractItemEditListener {
	private String accName;// 开户行
	private String bankAccKey;// 账号
	private String unitName;// 单位名称

	/**
	 * 
	 */
	public UnitEditListener() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ui
	 * @param itemKey
	 */
	public UnitEditListener(FBMManageUI ui, String itemKey) {
		super(ui, itemKey);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 * @param ui
	 * @param itemKey
	 * @param accName
	 *            开户行
	 * @param bankAcc
	 *            账号
	 * @param unitName
	 *            单位名称
	 */
	public UnitEditListener(FBMManageUI ui, String itemKey, String accName,
			String bankAcc, String unitName) {
		super(ui, itemKey);
		this.accName = accName;
		this.bankAccKey = bankAcc;
		this.unitName = unitName;
	}

	/**
	 * @param ui
	 * @param itemKey
	 * @param pos
	 */
	public UnitEditListener(FBMManageUI ui, String itemKey, int pos) {
		super(ui, itemKey, pos);

	}

	@Override
	public void responseEditEvent(BillEditEvent editEvent) {
		UIRefPane cubasunit = (UIRefPane) getUI().getBillCardPanel().getHeadItem(getItemKey()).getComponent();
		String cubasTxt = cubasunit.getText().trim();
		String pk_cubasdoc = cubasunit.getRefPK();

		if (CommonUtil.isNull(pk_cubasdoc)) {
			getUI().getBillCardPanel().getHeadItem(unitName).setValue(cubasTxt);
		} else {
			if (cubasunit.getRefValue("isCust").equals("Y")) {
				getUI().getBillCardPanel().getHeadItem(unitName).setValue(cubasunit.getRefValue("custname"));
				getUI().getBillCardPanel().getHeadItem(bankAccKey).setValue(null);
				if (getUI().getBillCardPanel().getHeadItem(accName) != null) {
					getUI().getBillCardPanel().getHeadItem(accName).setValue(null);
				}

			} else {
				// 如果不是基本档案的客商，则没有关联的开户银行，开户银行、名称字段需要设置为空。
				getUI().getBillCardPanel().getHeadItem(RegisterVO.RECEIVEBANKACC).setValue(null);
				getUI().getBillCardPanel().getHeadItem(RegisterVO.RECEIVEACCNAME).setValue(null);
			}
		}
	}
}
