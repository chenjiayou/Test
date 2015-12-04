package nc.ui.fbm.gather;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import nc.bs.logging.Logger;
import nc.itf.cdm.util.CommonUtil;
import nc.ui.bd.ref.AbstractRefModel;
import nc.ui.fac.account.pub.IRefTakenProccessor;
import nc.ui.fbm.gather.checker.BaseinfoAccountChecker;
import nc.ui.fbm.gather.checker.GatherdateChecker;
import nc.ui.fbm.gather.checker.HoldPayUnitNotEqChecker;
import nc.ui.fbm.gather.checker.PayGetUnitNotEqualChecker;
import nc.ui.fbm.gather.listener.BankEditListener;
import nc.ui.fbm.gather.listener.BaseInfoEditListener;
import nc.ui.fbm.gather.listener.CurrencyEditListener;
import nc.ui.fbm.gather.listener.InvoiceDateEditListener;
import nc.ui.fbm.gather.listener.InvoiceUnitEditListener;
import nc.ui.fbm.gather.listener.ReceiveAccbankEditListener;
import nc.ui.fbm.gather.listener.ReceiveaccnameEditListener;
import nc.ui.fbm.gather.listener.SfflagEditListener;
import nc.ui.fbm.gather.listener.UnitEditListener;
import nc.ui.fbm.gather.reffilter.BankaccFieldFilter;
import nc.ui.fbm.gather.reffilter.BankdocFieldFilter;
import nc.ui.fbm.gather.reffilter.BankdocRefModelFilter;
import nc.ui.fbm.gather.reffilter.BaseInfoRefModelFilter;
import nc.ui.fbm.gather.reffilter.CusbasDocWithCorpRefFilter;
import nc.ui.fbm.gather.reffilter.HandInRefModelFilter;
import nc.ui.fbm.gather.reffilter.PaybillUnitRefModelFilter;
import nc.ui.fbm.gather.status.GatherStatusEngine;
import nc.ui.fbm.invoice.reffilter.CurrtypeBankaccFilter;
import nc.ui.fbm.pub.FBMClientInfo;
import nc.ui.fbm.pub.FBMManageUI;
import nc.ui.fbm.pub.IFBMButton;
import nc.ui.fbm.pub.IItemEditListener;
import nc.ui.fbm.pub.IUIChecker;
import nc.ui.fbm.pub.ItemNotNullChecker;
import nc.ui.fbm.pub.YFBEditListerner;
import nc.ui.fbm.pub.reffilter.BankAccUsePowerFilter;
import nc.ui.pub.beans.UIComboBox;
import nc.ui.pub.beans.UIRefPane;
import nc.ui.pub.beans.constenum.DefaultConstEnum;
import nc.ui.pub.bill.BillEditEvent;
import nc.ui.pub.bill.BillItem;
import nc.ui.pub.bill.BillScrollPane;
import nc.ui.pub.bill.BillTableSelectionEvent;
import nc.ui.tm.framework.ref.AbstractTMRefModel;
import nc.ui.tm.framework.util.CurrencyClientUtil;
import nc.ui.trade.base.IBillOperate;
import nc.ui.trade.bill.AbstractManageController;
import nc.ui.trade.bill.ITableTreeController;
import nc.ui.trade.button.IBillButton;
import nc.ui.trade.manage.ManageEventHandler;
import nc.vo.fbm.pub.constant.FbmBusConstant;
import nc.vo.fbm.register.RegisterVO;
import nc.vo.fbm.storage.StoragePowerVO;
import nc.vo.pub.BusinessException;
import nc.vo.pub.lang.UFBoolean;
import nc.vo.trade.pub.IBillStatus;

/**
 * 
 * <p>
 * 收票登记UI
 * <p>
 * 创建人：lpf <b>日期：2007-8-10
 * 
 */
public class GatherUI extends FBMManageUI {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private Observer StatusEngine = getStatusEngine();

	private BillScrollPane panel = null;

	PlanItemPopDlg planDlg = new PlanItemPopDlg(this);// 计划项目对话框

	private GatherConsignBatchDlg batchDlg = new GatherConsignBatchDlg(this);

	private GatherImpawnBatchDlg batchImpawnDlg = new GatherImpawnBatchDlg(this);

	private GatherDiscountBatchDlg batchDiscountDlg = new GatherDiscountBatchDlg(
			this);

	public GatherUI() {
		super();
		/*
		 * 增加对bufferData的观察者 注意因为观察者的顺序是后注册的先执行 因此需要重新调整观察者的注册顺序
		 */
		getBufferData().deleteObserver(this);
		getBufferData().addObserver(StatusEngine);
		getBufferData().addObserver(this);

		// panel = getBillListPanel().getParentListPanel();
		// panel.getTable().getSelectionModel().addListSelectionListener(new
		// RowSelectListener());
		initComBox();
	}

	protected Observer getStatusEngine() {
		// TODO Auto-generated method stub
		return new GatherStatusEngine(this);
	}

	/**
	 * <p>
	 * UAP行选择监听判断minrow最小行是否发生行变化
	 * <p>
	 * 创建人：lpf <b>日期：2007-9-19
	 * 
	 */
	public class RowSelectListener implements ListSelectionListener {
		public void valueChanged(ListSelectionEvent e) {
			if (!e.getValueIsAdjusting()) {
				if (panel.getRowSelectionChangeListener() != null) {
					BillTableSelectionEvent be = new BillTableSelectionEvent(
							panel);
					be.setSelectIndex(((ListSelectionModel) e.getSource()).getAnchorSelectionIndex());
					panel.getRowSelectionChangeListener().selectionChanged(be);
				}
			}
			if (panel.getEditListener() != null) {
				int row = panel.getTable().getSelectedRow();
				int oldviewrow = -1;
				int oldrow = -1;
				try {
					Field f = BillScrollPane.class.getDeclaredField("oldrow");
					f.setAccessible(true);
					oldrow = f.getInt(panel);
				} catch (Exception e1) {
					Logger.error(e1.getMessage(), e1);
				}

				if (oldrow != -1)
					oldviewrow = oldrow;
				BillEditEvent ev = new BillEditEvent(panel.getTable(),
						oldviewrow, row);

				int column = panel.getTable().getSelectedColumn();
				if (column >= 0) {
					BillItem item = panel.getTableModel().getBodyItems()[column];
					ev.setKey(item.getKey());
				}
				panel.getEditListener().bodyRowChange(ev);
				oldrow = row;
				// }
			}
		}
	}

	public GatherUI(Boolean useBillSource) {
		super(useBillSource);
	}

	public GatherUI(String pk_corp, String pk_billType, String pk_busitype,
			String operater, String billId) {
		super(pk_corp, pk_billType, pk_busitype, operater, billId);
	}

	@Override
	protected AbstractManageController createController() {
		return new GatherController();
	}

	@Override
	protected void initSelfData() {
		super.initSelfData();
		initRefPane();
		initCardThMark();
		getBillListPanel().getParentListPanel().getTable().setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		getBillListPanel().getHeadBillModel().setCellEditableController(new BatchSelectController());
	}

	protected void initCardThMark() {
		BillItem[] decimalItems = new BillItem[] {
				getBillCardPanel().getHeadItem(RegisterVO.MONEYY),
				getBillCardPanel().getHeadItem(RegisterVO.MONEYB),
				getBillCardPanel().getHeadItem(RegisterVO.MONEYF),
				getBillCardPanel().getHeadItem(RegisterVO.BRATE),
				getBillCardPanel().getHeadItem(RegisterVO.FRATE) };
		getBillCardPanel().setHeadTailShowThMark(decimalItems, true);
		getBillListPanel().getParentListPanel().setShowThMark(true);
	}

	protected void initComBox() {
		DefaultConstEnum[] gathertype = nc.ui.fbm.pub.ComBoxUtil.getGathertype();
		getBillCardWrapper().initHeadComboBox(RegisterVO.GATHERTYPE, gathertype, false);
		getBillListWrapper().initHeadComboBox(RegisterVO.GATHERTYPE, gathertype, false);

		try {
			DefaultConstEnum[] acceptancetype = nc.ui.fbm.pub.ComBoxUtil.getAcceptanceType();

			getBillCardWrapper().initHeadComboBox(RegisterVO.FBMBILLTYPE, acceptancetype, false);
			getBillListWrapper().initHeadComboBox(RegisterVO.FBMBILLTYPE, acceptancetype, false);
		} catch (BusinessException e) {
			showErrorMessage(e.getMessage());
		}

		DefaultConstEnum[] registerstatus = nc.ui.fbm.pub.ComBoxUtil.getFBillStatus();
		getBillCardWrapper().initHeadComboBox(RegisterVO.REGISTERSTATUS, registerstatus, false);
		getBillListWrapper().initHeadComboBox(RegisterVO.REGISTERSTATUS, registerstatus, false);

		DefaultConstEnum[] vbillstatus = nc.ui.fbm.pub.ComBoxUtil.getBillStatus(FbmBusConstant.BILLTYPE_GATHER);
		getBillCardWrapper().initHeadComboBox(RegisterVO.VBILLSTATUS, vbillstatus, false);
		getBillListWrapper().initHeadComboBox(RegisterVO.VBILLSTATUS, vbillstatus, false);

		// getBillCardPanel().getHeadItem(RegisterVO.GATHERPLANITEM).setEnabled(false);

	}

	protected void initRefPane() {
		BillItem baseinfoItem = getBillCardPanel().getHeadItem(RegisterVO.PK_BASEINFO);
		UIRefPane baseinfoPane = (UIRefPane) baseinfoItem.getComponent();
		baseinfoPane.setAutoCheck(false);
		baseinfoPane.setButtonFireEvent(true);
		AbstractRefModel baseRefmdl = baseinfoPane.getRefModel();
		if (baseRefmdl instanceof AbstractTMRefModel) {
			BaseInfoRefModelFilter filter = new BaseInfoRefModelFilter(
					baseinfoItem);
			((AbstractTMRefModel) baseRefmdl).addSqlFilter(filter);
		}
		// 收款银行，付款银行
		BillItem paybank = getBillCardPanel().getHeadItem(RegisterVO.PAYBANK);
		UIRefPane paybankPane = (UIRefPane) paybank.getComponent();
		paybankPane.setAutoCheck(false);
		baseinfoPane.setButtonFireEvent(true);
		AbstractRefModel paybankRefmdl = paybankPane.getRefModel();
		if (paybankRefmdl instanceof AbstractTMRefModel) {
			BankdocRefModelFilter bankfilter = new BankdocRefModelFilter(
					paybank);
			((AbstractTMRefModel) paybankRefmdl).addSqlFilter(bankfilter);
		}
		BillItem receivebank = getBillCardPanel().getHeadItem(RegisterVO.RECEIVEBANK);
		UIRefPane receivebankPane = (UIRefPane) receivebank.getComponent();
		receivebankPane.setAutoCheck(false);
		receivebankPane.setButtonFireEvent(true);
		AbstractRefModel receivebankRefmdl = receivebankPane.getRefModel();
		if (receivebankRefmdl instanceof AbstractTMRefModel) {
			BankdocRefModelFilter receivebankfilter = new BankdocRefModelFilter(
					receivebank);
			((AbstractTMRefModel) receivebankRefmdl).addSqlFilter(receivebankfilter);
		}
		BillItem invoiceItem = getBillCardPanel().getHeadItem(RegisterVO.INVOICEUNIT);
		UIRefPane invoiceRef = (UIRefPane) invoiceItem.getComponent();
		invoiceRef.setAutoCheck(false);
		AbstractRefModel invoiceunitModel = invoiceRef.getRefModel();
		if (invoiceunitModel instanceof AbstractTMRefModel) {
			HandInRefModelFilter filter = new HandInRefModelFilter(invoiceItem);
			CusbasDocWithCorpRefFilter filter1 = new CusbasDocWithCorpRefFilter(
					this, RegisterVO.INVOICEUNIT);
			((AbstractTMRefModel) invoiceunitModel).addSqlFilter(filter);
			((AbstractTMRefModel) invoiceunitModel).addSqlFilter(filter1);
		}

		BillItem receiveaccItem = getBillCardPanel().getHeadItem(RegisterVO.RECEIVEBANKACC);
		UIRefPane receivebankRef = (UIRefPane) receiveaccItem.getComponent();
		receivebankRef.setAutoCheck(false);
		AbstractRefModel receiveaccModel = receivebankRef.getRefModel();
		if (receiveaccModel instanceof AbstractTMRefModel) {
			// ReceiveUnitAccRefModelFilter filter = new
			// ReceiveUnitAccRefModelFilter(receiveaccItem,this,RegisterVO.RECEIVEUNIT,RegisterVO.PK_CURR);
			BankAccUsePowerFilter filter = new BankAccUsePowerFilter(this,
					RegisterVO.RECEIVEUNIT);
			((AbstractTMRefModel) receiveaccModel).addSqlFilter(filter);
			CurrtypeBankaccFilter filter1 = new CurrtypeBankaccFilter(this,
					RegisterVO.PK_CURR);
			((AbstractTMRefModel) receiveaccModel).addSqlFilter(filter1);
			
			BankaccFieldFilter filter2 = new BankaccFieldFilter(getBillCardPanel().getHeadItem(RegisterVO.RECEIVEBANK));
			((AbstractTMRefModel) receiveaccModel).addSqlFilter(filter2);
		}

		BillItem receiveunitItem = getBillCardPanel().getHeadItem(RegisterVO.RECEIVEUNIT);
		UIRefPane unitRefPane = (UIRefPane) receiveunitItem.getComponent();
		unitRefPane.setAutoCheck(false);
		AbstractRefModel receiveunitModel = unitRefPane.getRefModel();
		if (receiveunitModel instanceof AbstractTMRefModel) {
			HandInRefModelFilter filter = new HandInRefModelFilter(
					receiveunitItem);
			CusbasDocWithCorpRefFilter filter1 = new CusbasDocWithCorpRefFilter(
					this, RegisterVO.RECEIVEUNIT);
			// ((AbstractTMRefModel) receiveunitModel).addSqlFilter(filter);
			((AbstractTMRefModel) receiveunitModel).addSqlFilter(filter1);
		}

		BillItem payaccItem = getBillCardPanel().getHeadItem(RegisterVO.PAYBANKACC);
		UIRefPane paybankRef = (UIRefPane) payaccItem.getComponent();
		paybankRef.setAutoCheck(false);
		AbstractRefModel accbankModel = paybankRef.getRefModel();
		if (accbankModel instanceof AbstractTMRefModel) {
			// ReceiveUnitAccRefModelFilter filter = new
			// ReceiveUnitAccRefModelFilter(payaccItem,
			// this,RegisterVO.PAYUNIT,RegisterVO.PK_CURR);
			BankAccUsePowerFilter filter = new BankAccUsePowerFilter(this,
					RegisterVO.PAYUNIT);
			((AbstractTMRefModel) accbankModel).addSqlFilter(filter);
			CurrtypeBankaccFilter filter1 = new CurrtypeBankaccFilter(this,
					RegisterVO.PK_CURR);
			((AbstractTMRefModel) accbankModel).addSqlFilter(filter1);
			
			BankaccFieldFilter filter2 = new BankaccFieldFilter(getBillCardPanel().getHeadItem(RegisterVO.PAYBANK));
			((AbstractTMRefModel) accbankModel).addSqlFilter(filter2);
		}

		BillItem payunitItem = getBillCardPanel().getHeadItem(RegisterVO.PAYUNIT);
		UIRefPane payunitRefPane = (UIRefPane) payunitItem.getComponent();
		payunitRefPane.setAutoCheck(false);
		AbstractRefModel payunitModel = payunitRefPane.getRefModel();
		if (payunitModel instanceof AbstractTMRefModel) {
			HandInRefModelFilter filter = new HandInRefModelFilter(payunitItem);
			CusbasDocWithCorpRefFilter filter1 = new CusbasDocWithCorpRefFilter(
					this, RegisterVO.PAYUNIT);
			((AbstractTMRefModel) payunitModel).addSqlFilter(filter);
			((AbstractTMRefModel) payunitModel).addSqlFilter(filter1);
		}

		BillItem paybillItem = getBillCardPanel().getHeadItem(RegisterVO.PAYBILLUNIT);
		AbstractRefModel paybillunitModel = ((UIRefPane) paybillItem.getComponent()).getRefModel();
		if (paybillunitModel instanceof AbstractTMRefModel) {
			PaybillUnitRefModelFilter filter = new PaybillUnitRefModelFilter(
					paybillItem);
			((AbstractTMRefModel) paybillunitModel).addSqlFilter(filter);
		}

		UIRefPane paybankRefPane = (UIRefPane) getBillCardPanel().getHeadItem(RegisterVO.PAYBANK).getComponent();
		paybankRefPane.setAutoCheck(false);

		UIRefPane receivebankRefPane = (UIRefPane) getBillCardPanel().getHeadItem(RegisterVO.RECEIVEBANK).getComponent();
		receivebankRefPane.setAutoCheck(false);
		
		
		BillItem receivebankItem = getBillCardPanel().getHeadItem(RegisterVO.RECEIVEBANK);
		BillItem receivebankaccItem = getBillCardPanel().getHeadItem(RegisterVO.RECEIVEBANKACC);
		UIRefPane receivebank1Ref = (UIRefPane) receivebankItem.getComponent();
		receivebank1Ref.setAutoCheck(false);
		AbstractRefModel receivebank1ItemunitModel =  receivebank1Ref.getRefModel();
		if (receivebank1ItemunitModel instanceof AbstractTMRefModel) {
			BankdocFieldFilter filter = new BankdocFieldFilter(receivebankItem,receivebankaccItem);
			((AbstractTMRefModel) receivebank1ItemunitModel).addSqlFilter(filter);
			}
		
		BillItem paybankItem = getBillCardPanel().getHeadItem(RegisterVO.PAYBANK);
		BillItem paybankaccItem = getBillCardPanel().getHeadItem(RegisterVO.PAYBANKACC);
		UIRefPane paybank1Ref = (UIRefPane) paybankItem.getComponent();
		paybank1Ref.setAutoCheck(false);
		AbstractRefModel paybank1ItemunitModel =  paybank1Ref.getRefModel();
		if (paybank1ItemunitModel instanceof AbstractTMRefModel) {
			BankdocFieldFilter filter = new BankdocFieldFilter(paybankItem,paybankaccItem);
			((AbstractTMRefModel) paybank1ItemunitModel).addSqlFilter(filter);
			}
	}

	@Override
	public void setDefaultData() throws Exception {
		getBillCardPanel().getHeadItem(RegisterVO.PK_CORP).setValue(_getCorp().getPrimaryKey());
		getBillCardPanel().getHeadItem(RegisterVO.VBILLSTATUS).setValue(IBillStatus.FREE);
		getBillCardPanel().getTailItem(RegisterVO.VOPERATORID).setValue(_getOperator());
		getBillCardPanel().getTailItem(RegisterVO.DOPERATEDATE).setValue(_getDate());
		String pk_cubasdoc = FBMClientInfo.getCommonCurCorpCubasdoc();
		getBillCardPanel().getHeadItem(RegisterVO.HOLDUNIT).setValue(pk_cubasdoc);
		getBillCardPanel().getHeadItem(RegisterVO.KEEPUNIT).setValue(pk_cubasdoc);
		//收款单位默认为当前公司对应的客商。
		getBillCardPanel().getHeadItem(RegisterVO.RECEIVEUNIT).setValue(pk_cubasdoc);
		getBillCardPanel().getHeadItem(RegisterVO.PK_BILLTYPECODE).setValue(FbmBusConstant.BILLTYPE_GATHER);
		getBillCardPanel().getHeadItem(RegisterVO.GATHERDATE).setValue(_getDate());
		getBillCardPanel().getHeadItem(RegisterVO.ORIENTATION).setValue(FbmBusConstant.ORIEINTATION_AR);
		((UIComboBox) getBillCardPanel().getHeadItem(RegisterVO.GATHERTYPE).getComponent()).setSelectedIndex(0);
		
		if(getBillCardPanel().getHeadTailItem(RegisterVO.FIRSTMAN)!=null){
			getBillCardPanel().getHeadTailItem(RegisterVO.FIRSTMAN).setValue(_getOperator());
		}
		//NCdp201040610 新增收票及开票，默认币种应取本位币，当前为空
		CurrencyClientUtil currencyClientUtil = new CurrencyClientUtil();
		getBillCardPanel().getHeadItem(RegisterVO.PK_CURR).setValue(currencyClientUtil.getLocalCurrPK());
		// getBillCardPanel().getHeadItem(RegisterVO.SFFLAG).setValue(
		// false);
	}

	@Override
	protected ManageEventHandler createEventHandler() {
		return new GatherEventHandler(this, getUIControl());
	}

	@Override
	public IItemEditListener[] getBillItemEditListener() {
		return new IItemEditListener[] {
				new BaseInfoEditListener(this, RegisterVO.PK_BASEINFO),
				new UnitEditListener(this, RegisterVO.PAYUNIT,
						RegisterVO.PAYACCNAME, RegisterVO.PAYBANKACC,
						RegisterVO.PAYUNITNAME),
				new UnitEditListener(this, RegisterVO.RECEIVEUNIT,
						RegisterVO.RECEIVEACCNAME, RegisterVO.RECEIVEBANKACC,
						RegisterVO.RECEIVEUNITNAME),
				new CurrencyEditListener(this, RegisterVO.PK_CURR),
				new ReceiveAccbankEditListener(this, RegisterVO.RECEIVEBANKACC,
						RegisterVO.RECEIVEACCNAME, RegisterVO.RECEIVEBANKACCODE,RegisterVO.RECEIVEUNIT,RegisterVO.RECEIVEBANK),
				new ReceiveAccbankEditListener(this, RegisterVO.PAYBANKACC,
						RegisterVO.PAYACCNAME, RegisterVO.PAYBANKACCODE,RegisterVO.PAYUNIT,RegisterVO.PAYBANK),
				new ReceiveaccnameEditListener(this, RegisterVO.PAYACCNAME,
						RegisterVO.ISACCCUST),
				new ReceiveaccnameEditListener(this, RegisterVO.RECEIVEACCNAME,
						RegisterVO.ISRECACCCUST),
				new InvoiceUnitEditListener(this, RegisterVO.INVOICEUNIT),
				new InvoiceDateEditListener(this, RegisterVO.INVOICEDATE),
				new YFBEditListerner(this, RegisterVO.PK_CURR,
						RegisterVO.PK_CURR, RegisterVO.MONEYY,
						RegisterVO.MONEYF, RegisterVO.MONEYB, RegisterVO.FRATE,
						RegisterVO.BRATE),
				new YFBEditListerner(this, RegisterVO.MONEYY,
						RegisterVO.PK_CURR, RegisterVO.MONEYY,
						RegisterVO.MONEYF, RegisterVO.MONEYB, RegisterVO.FRATE,
						RegisterVO.BRATE),
				new YFBEditListerner(this, RegisterVO.FRATE,
						RegisterVO.PK_CURR, RegisterVO.MONEYY,
						RegisterVO.MONEYF, RegisterVO.MONEYB, RegisterVO.FRATE,
						RegisterVO.BRATE),
				new YFBEditListerner(this, RegisterVO.BRATE,
						RegisterVO.PK_CURR, RegisterVO.MONEYY,
						RegisterVO.MONEYF, RegisterVO.MONEYB, RegisterVO.FRATE,
						RegisterVO.BRATE),
				new BankEditListener(this, RegisterVO.RECEIVEBANK,
						RegisterVO.RECEIVEBANKNAME),
				new BankEditListener(this, RegisterVO.PAYBANK,
						RegisterVO.PAYBANKNAME),
				new SfflagEditListener(this, RegisterVO.SFFLAG) };
	}

	@Override
	protected IRefTakenProccessor createRefTakenProccessor() {
		return new GatherRefTakenProcessor(getBillListPanel(),
				getBillCardPanel());
	}

	/**
	 * 收票登记校验类注册：非空项校验、收票日期校验、表体不能为空
	 */
	protected ArrayList<IUIChecker> createValidator(int btnid) {
		ArrayList<IUIChecker> alChecker = null;
		switch (btnid) {
		case IBillButton.Save:
			alChecker = new ArrayList<IUIChecker>();
			alChecker.add(new ItemNotNullChecker(this));
			alChecker.add(new GatherdateChecker(this));
			alChecker.add(new PayGetUnitNotEqualChecker(this));
			alChecker.add(new HoldPayUnitNotEqChecker(this));
			alChecker.add(new BaseinfoAccountChecker(this));
		}
		return alChecker;
	}

	@Override
	public void afterEdit(BillEditEvent e) {
		if (getRefTakenProccessor() != null) {
			if (e.getRow() < 0) {
				boolean need = isNeedRefTaken(e);
				getRefTakenProccessor().takenCardRefValue(e.getKey(), need);
			} else {
				getRefTakenProccessor().takenListRefValue(e.getKey(), e.getRow());
			}
		}
		getBillEditEventDispatcher().responseEditEvent(e);
	}

	/**
	 * <p>
	 * 没办法的办法：解决票据编号是手工输入并且参照中不存在（已经被过滤或者是新增票据） 的情况下，如果修改票据编号，携带会清空所有界面数据的问题
	 * <p>
	 * 作者：lpf 日期：2007-9-12
	 * 
	 * @param e
	 * @return false 不需要携带
	 */
	private boolean isNeedRefTaken(BillEditEvent e) {
		if (e.getKey().equalsIgnoreCase(RegisterVO.PK_BASEINFO)) {
			String pk_baseinfo = (String) getBillCardPanel().getHeadItem(RegisterVO.PK_BASEINFO).getValueObject();
			if (getBillOperate() == IBillOperate.OP_ADD) {
				if (CommonUtil.isNull(pk_baseinfo)) {
					return false;
				}
			} else if (getBillOperate() == IBillOperate.OP_EDIT) {
				String isnewstr = (String) getBillCardPanel().getHeadItem(RegisterVO.ISNEWBILL).getValueObject();
				if (new Boolean(isnewstr).booleanValue()
						&& CommonUtil.isNull(pk_baseinfo)) {
					return false;
				}
			}
		}
		return true;
	}

	@Override
	public void update(Observable o, Object arg) {
		if (beforeUpdate()) {
			try {
				if (isListPanelSelected()) {
					// edit by stl2006-08-21 set the selected state of the
					// current row
					setListBodyData();
					int nrow = getBufferData().getCurrentRow();
					int maxSelIndex = getBillListPanel().getHeadTable().getSelectionModel().getMaxSelectionIndex();
					int minSelIndex = getBillListPanel().getHeadTable().getSelectionModel().getMinSelectionIndex();
					if (nrow >= 0 && maxSelIndex == minSelIndex) {
						getBillListPanel().addEditListener(null);
						getBillListPanel().getHeadTable().getSelectionModel().setSelectionInterval(nrow, nrow);
						getBillListPanel().addEditListener(this);
					}
				} else
					setCardUIData(getBufferData().getCurrentVO());

				this.getBillCardPanel().updateValue();
				// 设置单据状态
				// updateBtnStateByCurrentVO();
			} catch (Exception e) {
				Logger.error(e.getMessage(), e);
				showErrorMessage(e.getMessage());
			}
			if (getUIControl() instanceof ITableTreeController) {
				setTableToTreeTable();
			}
		}

		afterUpdate();

		// 根据表头[已收票]字段,控制[收票完成、取消收票]按钮的可用状态. zhouwb 2008-9-18
		boolean sfflag = new UFBoolean(
				getBillCardPanel().getHeadItem("sfflag").getValue()).booleanValue();
		Integer vbillstatus = getBillCardPanel().getHeadItem(RegisterVO.VBILLSTATUS).getValueObject() == null ? null
				: (Integer) getBillCardPanel().getHeadItem(RegisterVO.VBILLSTATUS).getValueObject();
		if (vbillstatus != null
				&& vbillstatus.intValue() == IBillStatus.CHECKPASS) {
			if (sfflag) {
				getButtonManager().getButton(IFBMButton.BTNVO_SPWC).setEnabled(false);
				getButtonManager().getButton(IFBMButton.BTNVO_QXSP).setEnabled(true);
			} else {
				getButtonManager().getButton(IFBMButton.BTNVO_SPWC).setEnabled(true);
				getButtonManager().getButton(IFBMButton.BTNVO_QXSP).setEnabled(false);
			}
		} else {
			getButtonManager().getButton(IFBMButton.BTNVO_SPWC).setEnabled(false);
			getButtonManager().getButton(IFBMButton.BTNVO_QXSP).setEnabled(false);
		}
		updateButtons();

	}

	@Override
	public String getTitle() {
		return nc.ui.ml.NCLangRes.getInstance().getStrByID("36201005", "UPP36201005-000002")/*
																							 * @res
																							 * "收票登记"
																							 */;
	}

	public StoragePowerVO getPower() {
		if (power == null) {
			initPower();
		}
		return power;
	}

	@Override
	protected String checkPrerequisite() {
		initPower();
		String pk_cubasdoc = getPower().getPk_cubasdoc();
		if (CommonUtil.isNull(pk_cubasdoc)) {
			return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("36201005", "UPP36201005-000037")/*
																										 * @res
																										 * "当前公司未设置对应客商,无法进行收票登记业务"
																										 */;
		}
		return super.checkPrerequisite();
	}

	/**
	 * 
	 * <p>
	 * 初始化权限
	 * <p>
	 * 作者：lpf 日期：2007-11-28
	 */
	private void initPower() {
		if (power == null) {
			try {
				power = new StoragePowerVO();
				String corpPk = _getCorp().getPk_corp();
				power.setPk_corp(corpPk);
				power.setCurrDate(_getDate());
				power.setCurrUserPK(_getOperator());
				power.setSettleCenter(FBMClientInfo.isSettleCenter());
				power.setPk_cubasdoc(FBMClientInfo.getCommonCurCorpCubasdoc());
				power.setSettleUnit(FBMClientInfo.isSettleUnit(corpPk));
				power.setPk_settlecenter(FBMClientInfo.getSuperSettleCenter(corpPk));
			} catch (BusinessException e) {
				Logger.error(e.getMessage(), e);
			}

		}

	}

	private StoragePowerVO power;

	protected PlanItemPopDlg getPlanItemDlg() {
		return planDlg;
	}

	public GatherConsignBatchDlg getBatchDlg() {
		return batchDlg;
	}

	public GatherImpawnBatchDlg getBatchImpawnDlg() {
		return batchImpawnDlg;
	}

	public GatherDiscountBatchDlg getBatchDiscountDlg() {
		return batchDiscountDlg;
	}

	protected void updateListVo() throws java.lang.Exception {
		if (getRefTakenProccessor() != null
				&& getBufferData().getCurrentVO() != null) {
			getRefTakenProccessor().cleanCache();
		}
		super.updateListVo();
	}
	
	
	
}