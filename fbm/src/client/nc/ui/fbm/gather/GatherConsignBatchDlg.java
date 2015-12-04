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
import nc.vo.fbm.pub.constant.FbmBusConstant;

public class GatherConsignBatchDlg extends AbstractOKCancleDlg {

	private static final long serialVersionUID = -2457383360934095721L;

	private Map<String, BillItem> itemMap = new HashMap<String, BillItem>();

	private String itemStr = "holderacc,dcollectiondate,dconsigndate,collectionplanitem,"
			+ "fbmplanitem";

	private String curr = null;

	public GatherConsignBatchDlg(Container parent) {
		super(parent);
		initialize();
	}

	public GatherConsignBatchDlg(FBMManageUI ui, String pk_curr) {
		super(ui);
		curr = pk_curr;
		initialize();
	}

	@SuppressWarnings("unchecked")
	private void initialize() {
		setTitle(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add", "UPP3620ADD-000243")/*
																									 * @res
																									 * "批量托收!"
																									 */);
		getNorthPanel().setLayout(null);

		List itemList = new ArrayList();

		BillData billData = new BillData(
				BillUIUtil.getDefaultTempletStatic(FbmBusConstant.BILLTYPE_COLLECTION_UNIT, null, null, nc.ui.pub.ClientEnvironment.getInstance().getCorporation().getPrimaryKey(), null, null));

		BillItem[] billItems = billData.getHeadItems();
		BillItem[] tmpItems = null;
		BillItem itemValue = null;
		for (int i = 0; i < billItems.length; i++) {
			String key = billItems[i].getKey();
			itemMap.put(key, billItems[i]);
			if (itemStr.contains(billItems[i].getKey())) {
				itemValue = new BillItem();
				itemValue.setCaptionLabel(new UILabel(billItems[i].getName()));
				itemValue.setComponent(billItems[i].getComponent());
				itemValue.setKey(key);
				itemList.add(itemValue);

				if (billItems[i].getKey().equals("holderacc")) {
					UIRefPane refPane = null;
					BillItem item = billItems[i];
					refPane = (UIRefPane) item.getComponent();
					// CurrCorpBankaccFilter filter = new
					// CurrCorpBankaccFilter();
					refPane.setWhereString("authcorp='"
							+ ClientEnvironment.getInstance().getCorporation().getPrimaryKey()
							+ "' and pk_currtype='"
							+ curr
							+ "'"
							+ " and accstate<>3  and acctype in(0,1) and isinneracc = 'N' and accclass<>3");
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
		if (getItemValue("holderacc") == null
				|| "".equals(getItemValue("holderacc"))) {
			MessageDialog.showWarningDlg(this, nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add", "UPP3620ADD-000246")/*
																																	 * @res
																																	 * "失败提示!"
																																	 */, nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add", "UPP3620ADD-000247")/*
																																																							 * @res
																																																							 * "托收银行不能为空!"
																																																							 */);
			return false;
		}
		if (getItemValue("dcollectiondate") == null
				|| "".equals(getItemValue("dcollectiondate"))) {
			MessageDialog.showWarningDlg(this, nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add", "UPP3620ADD-000246")/*
																																	 * @res
																																	 * "失败提示!"
																																	 */, nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add", "UPP3620ADD-000248")/*
																																																							 * @res
																																																							 * "款项收妥日期不能为空!"
																																																							 */);
			return false;
		}
		return super.onBoOK();
	}

}