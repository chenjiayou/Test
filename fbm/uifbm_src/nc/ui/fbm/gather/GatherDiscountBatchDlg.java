package nc.ui.fbm.gather;

import java.awt.Container;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nc.ui.fac.account.pub.AbstractOKCancleDlg;
import nc.ui.fbm.pub.FBMManageUI;
import nc.ui.pub.ClientEnvironment;
import nc.ui.pub.beans.MessageDialog;
import nc.ui.pub.beans.UILabel;
import nc.ui.pub.beans.UIRefPane;
import nc.ui.pub.bill.BillData;
import nc.ui.pub.bill.BillItem;
import nc.ui.pub.bill.BillUIUtil;
import nc.vo.fbm.discount.DiscountVO;
import nc.vo.fbm.pub.constant.FbmBusConstant;

public class GatherDiscountBatchDlg extends AbstractOKCancleDlg {

	private static final long serialVersionUID = -2457383360934095721L;

	private Map<String, BillItem> itemMap = new HashMap<String, BillItem>();

	private String itemStr = "discount_account,ddiscountdate,"
			+ "discountdelaydaynum,applydate,ratedaynum,"
			+ "discountyrate,interestplanitem,balanceplanitem,fbmplanitem";

	private String curr = "";

	public GatherDiscountBatchDlg(Container parent) {
		super(parent);
		initialize();
	}

	public GatherDiscountBatchDlg(FBMManageUI ui, String checkCurr) {
		super(ui);
		curr = checkCurr;
		initialize();

	}

	private void initialize() {
		setTitle(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add", "UPP3620ADD-000244")/*
																									 * @res
																									 * "批量贴现!"
																									 */);
		getNorthPanel().setLayout(null);

		BillData billData = new BillData(
				BillUIUtil.getDefaultTempletStatic(FbmBusConstant.BILLTYPE_DISCOUNT_TRANSACT, null, null, nc.ui.pub.ClientEnvironment.getInstance().getCorporation().getPrimaryKey(), null, null));

		BillItem[] billItems = billData.getHeadItems();
		BillItem[] tmpItems = null;
		BillItem itemValue = null;
		List<BillItem> itemList = new ArrayList<BillItem>();
		for (int i = 0; i < billItems.length; i++) {
			String key = billItems[i].getKey();
			itemMap.put(key, billItems[i]);
			if (itemStr.contains(billItems[i].getKey())
					&& !billItems[i].getKey().equals(DiscountVO.PK_DISCOUNT)) {
				itemValue = new BillItem();
				itemValue.setCaptionLabel(new UILabel(billItems[i].getName()));
				itemValue.setComponent(billItems[i].getComponent());
				itemValue.setKey(key);
				itemList.add(itemValue);
				if (billItems[i].getKey().equals("discount_account")) {
					UIRefPane refPane = null;
					BillItem item = billItems[i];
					refPane = (UIRefPane) item.getComponent();
					refPane.setWhereString(" authcorp='"
							+ ClientEnvironment.getInstance().getCorporation().getPrimaryKey()
							+ "' and pk_currtype='"
							+ curr
							+ "'"
							+ " and accstate<>3 and acctype in(0,1) and isinneracc = 'N' and accclass<>3");
				}
				if (billItems[i].getKey().equals("ratedaynum")) {
					// BillItem item = billItems[i];
					nc.ui.pub.beans.UIComboBox cmb = (nc.ui.pub.beans.UIComboBox) itemValue.getComponent();
					cmb.removeAllItems();
					Object values[] = new Integer[] { 360, 365 };
					for (int j = 0; j < values.length; j++) {
						cmb.addItem(values[j]);
					}
					itemValue.setWithIndex(false);
				}
			}
		}
		tmpItems = (BillItem[]) itemList.toArray(new BillItem[0]);
		for (int i = 0; i < tmpItems.length; i++) {
			getNorthPanel().add(tmpItems[i].getCaptionLabel());
			getNorthPanel().add(tmpItems[i].getComponent());
		}
		// 布局管理
		getNorthPanel().setLayout(new nc.ui.bill.tools.BillHeadSpringLayout(
				tmpItems));
	}

	public String getItemValue(String key) {
		BillItem item = (BillItem) itemMap.get(key);
		return item.getValue();
	}

	@Override
	protected boolean onBoOK() {
		if (getItemValue("discount_account") == null
				|| "".equals(getItemValue("discount_account"))) {
			MessageDialog.showWarningDlg(this, nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add", "UPP3620ADD-000246")/*
																																	 * @res
																																	 * "失败提示!"
																																	 */, nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add", "UPP3620ADD-000249")/*
																																																							 * @res
																																																							 * "贴现银行账号不能为空!"
																																																							 */);
			return false;
		}
		if (getItemValue("discountyrate") == null
				|| "".equals(getItemValue("discountyrate"))) {
			MessageDialog.showWarningDlg(this, nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add", "UPP3620ADD-000246")/*
																																	 * @res
																																	 * "失败提示!"
																																	 */, nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add", "UPP3620ADD-000250")/*
																																																							 * @res
																																																							 * "贴现年利率不能为空!"
																																																							 */);
			return false;
		}
		if (getItemValue("ddiscountdate") == null
				|| "".equals(getItemValue("ddiscountdate"))) {
			MessageDialog.showWarningDlg(this, nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add", "UPP3620ADD-000246")/*
																																	 * @res
																																	 * "失败提示!"
																																	 */, nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add", "UPP3620ADD-000251")/*
																																																							 * @res
																																																							 * "贴现日期不能为空!"
																																																							 */);
			return false;
		}
		return super.onBoOK();
	}

}