package nc.ui.fbm.gather;

import java.awt.Container;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nc.ui.fac.account.pub.AbstractOKCancleDlg;
import nc.ui.pub.beans.MessageDialog;
import nc.ui.pub.beans.UILabel;
import nc.ui.pub.bill.BillData;
import nc.ui.pub.bill.BillItem;
import nc.ui.pub.bill.BillUIUtil;
import nc.vo.fbm.pub.constant.FbmBusConstant;

public class GatherImpawnBatchDlg extends AbstractOKCancleDlg {

	private static final long serialVersionUID = -2457383360934095721L;

	private Map<String, BillItem> itemMap = new HashMap<String, BillItem>();

	String itemStr = "debitunit,impawnunit,creditunit,impawndate,impawnrate,impawnbank";

	public GatherImpawnBatchDlg(Container parent) {
		super(parent);
		initialize();
	}

	private void initialize() {
		setTitle(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add", "UPP3620ADD-000245")/*
																									 * @res
																									 * "������Ѻ!"
																									 */);
		getNorthPanel().setLayout(null);

		List<BillItem> itemList = new ArrayList<BillItem>();
		BillData billData = new BillData(
				BillUIUtil.getDefaultTempletStatic(FbmBusConstant.BILLTYPE_IMPAWN, null, null, nc.ui.pub.ClientEnvironment.getInstance().getCorporation().getPrimaryKey(), null, null));

		BillItem[] billItems = billData.getHeadItems();
		BillItem[] tmpItems = new BillItem[4];
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
			}
		}
		tmpItems = (BillItem[]) itemList.toArray(new BillItem[0]);

		for (int i = 0; i < tmpItems.length; i++) {
			getNorthPanel().add(tmpItems[i].getCaptionLabel());
			getNorthPanel().add(tmpItems[i].getComponent());
		}
		// ���ֹ���
		getNorthPanel().setLayout(new nc.ui.bill.tools.BillHeadSpringLayout(
				tmpItems));
	}

	public String getItemValue(String key) {
		BillItem item = (BillItem) itemMap.get(key);
		return item.getValue();
	}

	@Override
	protected boolean onBoOK() {
		if (getItemValue("impawnrate") == null
				|| "".equals(getItemValue("impawnrate"))) {
			MessageDialog.showWarningDlg(this, nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add", "UPP3620ADD-000246")/*
																																	 * @res
																																	 * "ʧ����ʾ!"
																																	 */, nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add", "UPP3620ADD-000252")/*
																																																							 * @res
																																																							 * "��Ѻ�ʲ���Ϊ��!"
																																																							 */);
			return false;
		}
		if (getItemValue("debitunit") == null
				|| "".equals(getItemValue("debitunit"))) {
			MessageDialog.showWarningDlg(this, nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add", "UPP3620ADD-000246")/*
																																	 * @res
																																	 * "ʧ����ʾ!"
																																	 */, nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add", "UPP3620ADD-000253")/*
																																																							 * @res
																																																							 * "��λ����Ϊ��!"
																																																							 */);
			return false;
		}
		if (getItemValue("impawnunit") == null
				|| "".equals(getItemValue("impawnunit"))) {
			MessageDialog.showWarningDlg(this, nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add", "UPP3620ADD-000246")/*
																																	 * @res
																																	 * "ʧ����ʾ!"
																																	 */, nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add", "UPP3620ADD-000254")/*
																																																							 * @res
																																																							 * "��Ѻ�˲���Ϊ��!"
																																																							 */);
			return false;
		}
		if (getItemValue("creditunit") == null
				|| "".equals(getItemValue("creditunit"))) {
			MessageDialog.showWarningDlg(this, nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add", "UPP3620ADD-000246")/*
																																	 * @res
																																	 * "ʧ����ʾ!"
																																	 */, nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add", "UPP3620ADD-000255")/*
																																																							 * @res
																																																							 * "���λ����Ϊ��!"
																																																							 */);
			return false;
		}
		if (getItemValue("impawndate") == null
				|| "".equals(getItemValue("impawndate"))) {
			MessageDialog.showWarningDlg(this, nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add", "UPP3620ADD-000246")/*
																																	 * @res
																																	 * "ʧ����ʾ!"
																																	 */, nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add", "UPP3620ADD-000256")/*
																																																							 * @res
																																																							 * "��Ѻ���ڲ���Ϊ��!"
																																																							 */);
			return false;
		}

		return super.onBoOK();
	}

}