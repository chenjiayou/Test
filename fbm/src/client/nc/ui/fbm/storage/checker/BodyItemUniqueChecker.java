/**
 *
 */
package nc.ui.fbm.storage.checker;

import nc.ui.fbm.pub.AbstractUIChecker;
import nc.ui.fbm.pub.FBMManageUI;
import nc.ui.pub.bill.BillCardPanel;
import nc.ui.pub.bill.BillData;
import nc.vo.trade.checkrule.ICompareRule;
import nc.vo.trade.checkrule.VOChecker;

/**
 * <p>
 * ��������Ψһ��У��
 * <p>
 * �����ˣ�lpf <b>���ڣ�2007-8-28
 * uniqueKeys:��ҪУ��ı���Key
 * itemNames����ҪУ���item����
 */
public class BodyItemUniqueChecker extends AbstractUIChecker {
	private String[] uniqueKeys;
	private String[] itemNames;

	/**
	 *
	 */
	public BodyItemUniqueChecker() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ui
	 */
	public BodyItemUniqueChecker(FBMManageUI ui) {
		super(ui);
		// TODO Auto-generated constructor stub
	}

	public BodyItemUniqueChecker(FBMManageUI ui, String[] uniqueKeys, String[] itemNames) {
		super(ui);
		this.uniqueKeys = uniqueKeys;
		this.itemNames = itemNames;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see nc.ui.fbm.pub.AbstractUIChecker#check()
	 */
	@Override
	public String check() {
		// TODO Auto-generated method stub
		// ��֤����
		BillCardPanel panel=getUI().getBillCardPanel();
		String[] tableCodes = panel.getBillData().getTableCodes(BillData.BODY);
		if (tableCodes != null) {
			for (int t = 0; t < tableCodes.length; t++) {
				String tablecode = tableCodes[t];
				int row=panel.getBillData().getBillModel(tablecode).getRowCount();
				for (int i = 0; i < row-1; i++) {
					for (int j = i + 1; j < row; j++) {
						boolean same = true;
						for (int keys = 0; keys < uniqueKeys.length; keys++) {
							Object o1 = panel.getBillData().getBillModel(tablecode).getValueAt(i,uniqueKeys[keys]);
							Object o2 = panel.getBillData().getBillModel(tablecode).getValueAt(j,uniqueKeys[keys]);
							same = same
									&& VOChecker.voassert(o1,
											ICompareRule.OPERATOR_EQUAL, o2);
							if (same)
								return nc.ui.ml.NCLangRes.getInstance().getStrByID("36201010","UPP36201010-000000")/* @res"��"*/+(i+1)+nc.ui.ml.NCLangRes.getInstance().getStrByID("36201010","UPP36201010-000003")/* @res"�к͵�"*/+(j+1)+nc.ui.ml.NCLangRes.getInstance().getStrByID("36201010","UPP36201010-000004")/* @res"��"*/+itemNames[keys]+nc.ui.ml.NCLangRes.getInstance().getStrByID("36201010","UPP36201010-000005")/* @res"�ظ�"*/ + "\n";

						}
					}
				}
			}
		}
		return null;
	}

}