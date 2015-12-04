package nc.ui.fbm.reckonalarm;

import nc.ui.bd.ref.AbstractRefModel;
import nc.ui.fbm.pub.FBMClientInfo;
import nc.ui.pub.beans.UIRefPane;
import nc.ui.pub.beans.UITable;
import nc.ui.pub.bill.BillEditEvent;
import nc.ui.pub.bill.BillItem;
import nc.ui.pub.bill.BillModel;
import nc.ui.pub.bill.IBillItem;
import nc.ui.tm.framework.billtemplet.BillCardPanelTools;
import nc.ui.tm.framework.billtemplet.PkReturnableBillCellEditor;
import nc.ui.tm.framework.ref.AbstractTMRefModel;
import nc.ui.tm.framework.ref.filter.ObjectRefModelFilter;
import nc.ui.trade.bill.ICardController;
import nc.ui.trade.button.ButtonVOFactory;
import nc.ui.trade.button.IBillButton;
import nc.ui.trade.card.BillCardUI;
import nc.ui.trade.card.CardEventHandler;
import nc.vo.bd.settle.SettlecenterVO;
import nc.vo.fbm.proxy.OuterProxy;
import nc.vo.fbm.reckonalarm.ReckalarmVO;
import nc.vo.tam.account.IAccConst;
import nc.vo.tam.account.accid.AccidVO;

/**
 * <p>
 * 调剂清算设置界面UI类
 * <p>
 * 创建人：bsrl <b>日期：2007-10-30
 * 
 */
public class ReckonAlarmUI extends BillCardUI {

	@Override
	public void afterEdit(BillEditEvent e) {

		if (e.getKey().equalsIgnoreCase(ReckalarmVO.INACC)) {
			UIRefPane reckonacc = (UIRefPane) getBillCardPanel().getBodyItem(ReckalarmVO.INACC).getComponent();
			if (e.getValue() != null
					&& e.getValue().toString().trim().length() > 0) {
				String pk_curr = (String) reckonacc.getRefValue("bd_accid.pk_currtype");
				getBillCardPanel().getBillModel(ReckalarmVO.TABLENAME).setValueAt(pk_curr, e.getRow(), ReckalarmVO.PK_CURR);
				// ((UIRefPane)getBillCardPanel().getBodyItem(ReckalarmVO.PK_CURR).getComponent()).setPK(pk_curr);
			} else {
				getBillCardPanel().getBillModel(ReckalarmVO.TABLENAME).setValueAt(null, e.getRow(), ReckalarmVO.PK_CURR);
			}
		} else if (e.getKey().equalsIgnoreCase(ReckalarmVO.OUTACC)) {
			UIRefPane reckonacc = (UIRefPane) getBillCardPanel().getBodyItem(ReckalarmVO.OUTACC).getComponent();
			if (e.getValue() != null
					&& e.getValue().toString().trim().length() > 0) {
				String pk_curr = (String) reckonacc.getRefValue("bd_accid.pk_currtype");
				getBillCardPanel().getBillModel(ReckalarmVO.TABLENAME).setValueAt(pk_curr, e.getRow(), ReckalarmVO.PK_CURR);
				// ((UIRefPane)getBillCardPanel().getBodyItem(ReckalarmVO.PK_CURR).getComponent()).setPK(pk_curr);
			} else {
				getBillCardPanel().getBillModel(ReckalarmVO.TABLENAME).setValueAt(null, e.getRow(), ReckalarmVO.PK_CURR);
			}
		} else if (e.getKey().equalsIgnoreCase(ReckalarmVO.RECKONUNIT)) {
			getBillCardPanel().getBillModel(ReckalarmVO.TABLENAME).setValueAt(null, e.getRow(), ReckalarmVO.INACC);
			getBillCardPanel().getBillModel(ReckalarmVO.TABLENAME).setValueAt(null, e.getRow(), ReckalarmVO.OUTACC);
			getBillCardPanel().getBillModel(ReckalarmVO.TABLENAME).setValueAt(null, e.getRow(), ReckalarmVO.PK_CURR);
		} else if (e.getKey().equalsIgnoreCase(ReckalarmVO.PK_CURR)) {
			getBillCardPanel().getBillModel(ReckalarmVO.TABLENAME).setValueAt(null, e.getRow(), ReckalarmVO.INACC);
			getBillCardPanel().getBillModel(ReckalarmVO.TABLENAME).setValueAt(null, e.getRow(), ReckalarmVO.OUTACC);
		}
	}

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	public ReckonAlarmUI() {
		super();
		init();
	}

	private void init() {
		onButtonClicked(ButtonVOFactory.getInstance().build(IBillButton.Query).buildButton());
		initRef();
	}

	private void initRef() {
		AbstractRefModel reckonunit = ((UIRefPane) getBillCardPanel().getBodyItem(ReckalarmVO.RECKONUNIT).getComponent()).getRefModel();
		SettlecenterVO settleCenterByCorpPk = OuterProxy.getSettleCenter().getSettleCenterByCorpPk(_getCorp().getPk_corp());
		if (settleCenterByCorpPk != null)
			reckonunit.addWherePart(" and bd_settlecenter.pk_settlecenter = '"
					+ settleCenterByCorpPk.getPrimaryKey()
					+ "'");

		AbstractTMRefModel reckaccrefModel = (AbstractTMRefModel) ((UIRefPane) getBillCardPanel().getBodyItem(ReckalarmVO.INACC).getComponent()).getRefModel();
		BillModel billModel = getBillCardWrapper().getBillCardPanel().getBillModel(ReckalarmVO.TABLENAME);

		reckaccrefModel.addSqlFilter(new ObjectRefModelFilter<BillModel>(
				billModel) {
			protected String getSelfFilterString() {
				UITable ut = getBillCardWrapper().getBillCardPanel().getBillTable();
				Object reckunit = ut.getModel().getValueAt(ut.getSelectedRow(), getBillCardWrapper().getBillCardPanel().getBodyColByKey(ReckalarmVO.RECKONUNIT
						+ IBillItem.ID_SUFFIX));
				Object pk_curr = ut.getModel().getValueAt(ut.getSelectedRow(), getBillCardWrapper().getBillCardPanel().getBodyColByKey(ReckalarmVO.PK_CURR
						+ IBillItem.ID_SUFFIX));
				String filter = " "
						+ AccidVO.ACCTYPE
						+ "="
						+ IAccConst.ACCCL_CURRENT;
				if (pk_curr != null) {
					filter = filter
							+ " and bd_accid.pk_currtype ='"
							+ pk_curr
							+ "' ";
				}
				if (reckunit != null) {
					return filter
							+ " and ownercorp in (select pk_corp1 from bd_cubasdoc where pk_cubasdoc ='"
							+ reckunit.toString()
							+ "')";
				} else {
					return filter;
				}
			}

			protected boolean isEnabled() {
				return true;
			}
		});

		reckaccrefModel = (AbstractTMRefModel) ((UIRefPane) getBillCardPanel().getBodyItem(ReckalarmVO.OUTACC).getComponent()).getRefModel();
		reckaccrefModel.addSqlFilter(new ObjectRefModelFilter<BillModel>(
				billModel) {
			protected String getSelfFilterString() {
				UITable ut = getBillCardWrapper().getBillCardPanel().getBillTable();
				Object reckunit = ut.getModel().getValueAt(ut.getSelectedRow(), getBillCardWrapper().getBillCardPanel().getBodyColByKey(ReckalarmVO.RECKONUNIT));
				Object pk_curr = ut.getModel().getValueAt(ut.getSelectedRow(), getBillCardWrapper().getBillCardPanel().getBodyColByKey(ReckalarmVO.PK_CURR));
				String filter = " "
						+ AccidVO.ACCTYPE
						+ "="
						+ IAccConst.ACCL_BILL;
				if (pk_curr != null) {
					filter = filter
							+ " and  bd_accid.pk_currtype ='"
							+ pk_curr
							+ "'  ";
				}
				if (reckunit != null) {
					return filter
							+ " and   ownercorp in (select pk_corp1 from bd_cubasdoc where pk_cubasdoc ='"
							+ reckunit.toString()
							+ "')";
				} else {
					return filter;
				}
			}

			protected boolean isEnabled() {
				return true;
			}
		});

	}

	public ReckonAlarmUI(String pk_corp, String pk_billType,
			String pk_busitype, String operater, String billId) {
		super(pk_corp, pk_billType, pk_busitype, operater, billId);
	}

	@Override
	protected ICardController createController() {
		return new ReckonAlarmController();
	}

	protected CardEventHandler createEventHandler() {
		return new ReckonAlarmEventHandler(this, getUIControl());
	}

	@Override
	public String getRefBillType() {
		return null;
	}

	@Override
	protected void initSelfData() {
		BillCardPanelTools.resetTableCellRenderer(getBillCardPanel(), false);
		setBillEditor(ReckalarmVO.TABLENAME, new String[] {
				ReckalarmVO.PK_CURR, ReckalarmVO.INACC, ReckalarmVO.RECKONUNIT });
		setBillEditor(ReckalarmVO.TABLENAME, new String[] {
				ReckalarmVO.PK_CURR, ReckalarmVO.OUTACC, ReckalarmVO.RECKONUNIT });

	}

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
				cm.getColumn(col).setCellEditor(new PkReturnableBillCellEditor(
						(UIRefPane) item.getComponent()));
		}
	}

	@Override
	public void setDefaultData() throws Exception {
	}

	public boolean isSaveAndCommitTogether() {
		return false;
	}

	/**
	 * 作者：bsrl <br>
	 * 日期：2007-11-28
	 * 
	 * @see nc.ui.pub.ToftPanel#checkPrerequisite()
	 */
	protected String checkPrerequisite() {
		if (!FBMClientInfo.isSettleCenter())
			return nc.ui.ml.NCLangRes.getInstance().getStrByID("36201050", "UPP36201050-000001")/*
																								 * @res
																								 * "非票据管理中心不可使用该节点!"
																								 */;

		return null;
	}
}