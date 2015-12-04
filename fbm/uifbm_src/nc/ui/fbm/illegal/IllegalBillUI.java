package nc.ui.fbm.illegal;

import nc.ui.pub.beans.UIRefPane;
import nc.ui.pub.beans.UITable;
import nc.ui.pub.bill.BillItem;
import nc.ui.pub.bill.BillModel;
import nc.ui.tm.framework.billtemplet.BillCardPanelTools;
import nc.ui.tm.framework.billtemplet.PkReturnableBillCellEditor;
import nc.ui.trade.bill.ICardController;
import nc.ui.trade.card.BillCardUI;
import nc.ui.trade.card.CardEventHandler;
import nc.vo.fbm.illegal.IllegalVO;

/**
 * �Ƿ�Ʊ�ݹ���UI
 * 
 * @author wues
 */
public class IllegalBillUI extends BillCardUI {
	//����ҳǩ����
	private static String bodyTabName = "fbm_illegal";
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public IllegalBillUI() {
		super();
	}

	public IllegalBillUI(String pk_corp, String pk_billType,
			String pk_busitype, String operater, String billId) {
		super(pk_corp, pk_billType, pk_busitype, operater, billId);
	}

	
	/**
	 * ���ش˽ڵ�Ľ��������
	 */
	protected ICardController createController() {
		return new IllegalBillCTL();
	}

	protected CardEventHandler createEventHandler() {
		return new IllegalBillHandler(this, getUIControl());
	}

	/**
	 * ���ó�ʼ������
	 */
	protected void initSelfData() {
		//UAP �ṩ��editor�����⣬��Ҫ���裬Ĭ�Ϸ���code
		BillCardPanelTools.resetTableCellRenderer(getBillCardPanel(), false);
		// ΪEditor��ֵ
		setBillEditor(bodyTabName, new String[] { IllegalVO.PK_CURR });
	}

	/**
	 * UAP��editor�����⣬��Ҫ����д��editor��ֵ��ʹ�䷵��pk
	 * 
	 * @param tableTabName
	 * @param colNames
	 */
	private void setBillEditor(String tableTabName, String[] colNames) {
		UITable table = getBillCardPanel().getBillTable(tableTabName);
		for (int i = 0; colNames != null && i < colNames.length; i++) {
			BillItem item = getBillCardPanel().getBodyItem(colNames[i]);
			if (item == null || item.isShow() == false)
				return;
			String itemkey = item.getKey();
			javax.swing.table.TableColumnModel cm = table.getColumnModel();
			BillModel bm = (BillModel) table.getModel();
			int col = bm.getBodyColByKey(itemkey);
			col = table.convertColumnIndexToView(col);
			if (col > -1 && col < bm.getColumnCount())
				cm.getColumn(col).setCellEditor(
						new PkReturnableBillCellEditor((UIRefPane) item
								.getComponent()));
		}
	}
	
	public boolean isSaveAndCommitTogether() {
		return false;
	}

	@Override
	/**
	 * ���븲��
	 */
	public String getRefBillType() {
		return null;
	}

	@Override
	/**
	 * ���븲��
	 */
	public void setDefaultData() throws Exception {
	}
}
