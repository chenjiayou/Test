/**
 *
 */
package nc.ui.fbm.storage.innerkeep;

import java.util.ArrayList;
import java.util.Observer;

import nc.bs.logging.Logger;
import nc.itf.cdm.util.CommonUtil;
import nc.ui.bd.ref.AbstractRefModel;
import nc.ui.fac.account.pub.IRefTakenProccessor;
import nc.ui.fbm.planref.PlanItem4InnerKeepModel;
import nc.ui.fbm.pub.ComBoxUtil;
import nc.ui.fbm.pub.FBMButtonFactory;
import nc.ui.fbm.pub.FBMClientInfo;
import nc.ui.fbm.pub.FBMManageUI;
import nc.ui.fbm.pub.IFBMButton;
import nc.ui.fbm.pub.IItemEditListener;
import nc.ui.fbm.pub.IUIChecker;
import nc.ui.fbm.pub.ItemNotNullChecker;
import nc.ui.fbm.pub.reffilter.InnerAccRuleFilter;
import nc.ui.fbm.returnbill.BodyItemDeleteEditListener;
import nc.ui.fbm.storage.InnerAccRefModelFilter;
import nc.ui.fbm.storage.InnerKeepRefTakenProcessor;
import nc.ui.fbm.storage.checker.BodyCurrencyChecker;
import nc.ui.fbm.storage.checker.BodyItemUniqueChecker;
import nc.ui.fbm.storage.checker.BodyNotEmptyChecker;
import nc.ui.fbm.storage.innerback.InnerKeepDateChecker;
import nc.ui.fbm.storage.listener.ClearItemValueEditListener;
import nc.ui.fbm.storage.listener.CurrtypeEditListener;
import nc.ui.fbm.storage.listener.InnerKeepSourceEditListener;
import nc.ui.fbm.storage.listener.InputTypeListener;
import nc.ui.fbm.storage.status.InnerKeepStatusEngine;
import nc.ui.pub.beans.MessageDialog;
import nc.ui.pub.beans.UIRefPane;
import nc.ui.pub.beans.constenum.DefaultConstEnum;
import nc.ui.pub.bill.IBillItem;
import nc.ui.pub.linkoperate.ILinkAddData;
import nc.ui.pub.linkoperate.ILinkType;
import nc.ui.tm.framework.billtemplet.PkReturnableBillCellEditor;
import nc.ui.tm.framework.ref.AbstractTMRefModel;
import nc.ui.tm.framework.util.CurrencyClientUtil;
import nc.ui.trade.bill.AbstractManageController;
import nc.ui.trade.button.IBillButton;
import nc.ui.trade.manage.ManageEventHandler;
import nc.vo.fbm.pub.FBMProxy;
import nc.vo.fbm.pub.FBMPublicQry;
import nc.vo.fbm.pub.constant.FbmBusConstant;
import nc.vo.fbm.storage.StorageBVO;
import nc.vo.fbm.storage.StoragePowerVO;
import nc.vo.fbm.storage.StorageVO;
import nc.vo.pub.BusinessException;
import nc.vo.trade.pub.IBillStatus;
import nc.vo.uapbd.bankdoc.BankdocVO;

/**
 * <p>
 * 内部存放UI
 * <p>
 * 创建人：lpf <b>日期：2007-10-9
 * 
 */
public class InnerKeepUI extends FBMManageUI {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private Observer StatusEngine = getStatusEngine();
	private InnerKeepEventHandler m_handler = null;
	private InnerKeepController m_ctrl = null;
	private StoragePowerVO power;

	/**
	 *
	 */
	public InnerKeepUI() {
		super();
		/*
		 * 增加对bufferData的观察者 注意因为观察者的顺序是后注册的先执行 因此需要重新调整观察者的注册顺序
		 */
		getBufferData().deleteObserver(this);
		getBufferData().addObserver(StatusEngine);
		getBufferData().addObserver(this);
		initComBox();
	}

	/**
	 * @param useBillSource
	 */
	public InnerKeepUI(Boolean useBillSource) {
		super(useBillSource);
		// TODO Auto-generated constructor stub

	}

	/**
	 * @param pk_corp
	 * @param pk_billType
	 * @param pk_busitype
	 * @param operater
	 * @param billId
	 */
	public InnerKeepUI(String pk_corp, String pk_billType, String pk_busitype,
			String operater, String billId) {
		super(pk_corp, pk_billType, pk_busitype, operater, billId);
		// TODO Auto-generated constructor stub
	}

	protected ManageEventHandler createEventHandler() {
		if (m_handler == null)
			m_handler = new InnerKeepEventHandler(this, createController());
		return m_handler;
	}

	protected AbstractManageController createController() {
		if (m_ctrl == null)
			m_ctrl = new InnerKeepController();
		return m_ctrl;
	}

	@Override
	protected void initSelfData() {
		super.initSelfData();
		initPower();
		initRefPane();
		initButtonVisible();
		initItemEditablebyPower();
		initBillCellEditor();
		// getBillCardPanel().getBodyPanel(StorageBVO.tablecode).setTotalRowShow(true);
	}

	private void initBillCellEditor() {
		int colomn = getBillCardPanel().getBodyColByKey(StorageBVO.PK_SOURCE);
		UIRefPane refpane = (UIRefPane) getBillCardPanel().getBodyItem(StorageBVO.PK_SOURCE).getComponent();
		getBillCardPanel().getBillTable().getColumnModel().getColumn(colomn).setCellEditor(new PkReturnableBillCellEditor(
				refpane));
	}

	protected void initItemEditablebyPower() {
		if (!getPower().isSettleCenter() && getPower().isSettleUnit()) {
			getBillCardPanel().getHeadItem(StorageVO.KEEPUNIT).setEnabled(false);
		}
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

	protected void initRefPane() {
		UIRefPane gatherPane = (UIRefPane) getBillCardPanel().getBodyItem(StorageBVO.PK_SOURCE).getComponent();
		gatherPane.setMultiSelectedEnabled(true);
		gatherPane.setCacheEnabled(false);
		AbstractRefModel gathermdl = gatherPane.getRefModel();
		if (AbstractTMRefModel.class.isAssignableFrom(gathermdl.getClass())) {
			InnerKeepSourceRefModelFilter filter = new InnerKeepSourceRefModelFilter(
					getBillCardPanel().getBodyItem(StorageBVO.PK_SOURCE), this);
			((AbstractTMRefModel) gathermdl).addSqlFilter(filter);
		}

		InnerAccRefModelFilter accFilter = new InnerAccRefModelFilter(
				getBillCardPanel().getHeadItem(StorageVO.KEEPACCOUNT), this,
				StorageVO.KEEPUNIT);
		addSqlFilter(true, StorageVO.KEEPACCOUNT, accFilter);
		InnerAccRuleFilter accRuleFilter = new InnerAccRuleFilter(
				FbmBusConstant.BILLTYPE_INNERKEEP, FbmBusConstant.ACC_INNER);
		addSqlFilter(true, StorageVO.KEEPACCOUNT, accRuleFilter);

		KeepUnitRefModelFilter unitFilter = new KeepUnitRefModelFilter(
				getBillCardPanel().getHeadItem(StorageVO.KEEPUNIT), this);
		addSqlFilter(true, StorageVO.KEEPUNIT, unitFilter);

		PlanItem4InnerKeepModel planModel = new PlanItem4InnerKeepModel(
				getBillCardPanel().getHeadItem(StorageVO.KEEPCORP));
		((UIRefPane) getBillCardPanel().getHeadItem(StorageVO.UNITPLANITEM).getComponent()).setRefModel(planModel);

	}

	private void initComBox() {
		DefaultConstEnum[] keepType = ComBoxUtil.getStorageKeepType();
		getBillCardWrapper().initHeadComboBox(StorageVO.INPUTTYPE, keepType, false);
		getBillListWrapper().initHeadComboBox(StorageVO.INPUTTYPE, keepType, false);

		DefaultConstEnum[] billstatus = ComBoxUtil.getBillStatus(getUIControl().getBillType());
		getBillCardWrapper().initHeadComboBox(StorageVO.VBILLSTATUS, billstatus, false);
		getBillListWrapper().initHeadComboBox(StorageVO.VBILLSTATUS, billstatus, false);

		try {
			DefaultConstEnum[] acceptancetype = nc.ui.fbm.pub.ComBoxUtil.getAcceptanceType();
			getBillCardWrapper().initBodyComboBox(StorageBVO.ACCEPTANCETYPE, acceptancetype, false);
			getBillListWrapper().initBodyComboBox(StorageBVO.ACCEPTANCETYPE, acceptancetype, false);
		} catch (BusinessException e) {
			showErrorMessage(e.getMessage());
		}

		DefaultConstEnum[] fBillStatus = nc.ui.fbm.pub.ComBoxUtil.getFBillStatus();
		getBillCardWrapper().initBodyComboBox(StorageBVO.ENDSTATUS, fBillStatus, false);
		getBillListWrapper().initBodyComboBox(StorageBVO.ENDSTATUS, fBillStatus, false);
	}

	@Override
	public void setDefaultData() throws Exception {
		if (!getPower().isSettleCenter()) {
			String pk_currCust = getPower().getPk_cubasdoc();
			getBillCardPanel().getHeadItem(StorageVO.KEEPUNIT).setValue(pk_currCust);
			fireCardAfterEdit(StorageVO.KEEPUNIT);
		}
		getBillCardPanel().getHeadItem(StorageVO.VBILLSTATUS).setValue(IBillStatus.FREE);
		getBillCardPanel().getHeadItem(StorageVO.PK_BILLTYPECODE).setValue(getUIControl().getBillType());

		getBillCardPanel().getTailItem(StorageVO.VOPERATORID).setValue(_getOperator());
		getBillCardPanel().getTailItem(StorageVO.DOPERATEDATE).setValue(_getDate());
		getBillCardPanel().getHeadItem(StorageVO.PK_CORP).setValue(_getCorp().getPk_corp());
		// getBillCardPanel().getHeadItem(StorageVO.INPUTTYPE).setValue(
		// FbmBusConstant.KEEP_TYPE_STORE);

		CurrencyClientUtil currencyClientUtil = new CurrencyClientUtil();
		getBillCardPanel().getHeadItem(StorageVO.PK_CURRTYPE).setValue(currencyClientUtil.getLocalCurrPK());

		// 设置受托银行
		String pk_settlecenter = FBMPublicQry.retrivePk_settlecentByPkCorp(_getCorp().getPrimaryKey());
		BankdocVO bankdocVO = FBMProxy.getBankDocQueryService().queryBankDocByCenterPk(pk_settlecenter);
		if (bankdocVO != null)
			getBillCardPanel().getHeadItem(StorageVO.PK_BANKDOC).setValue(bankdocVO.getPrimaryKey());
	}

	@Override
	protected ArrayList<IUIChecker> createValidator(int btnid) {
		ArrayList<IUIChecker> alChecker = null;
		switch (btnid) {
		case IBillButton.Save:
			alChecker = new ArrayList<IUIChecker>();
			alChecker.add(new ItemNotNullChecker(this));
			alChecker.add(new BodyNotEmptyChecker(this));
			alChecker.add(new BodyItemUniqueChecker(
					this,
					new String[] { StorageBVO.PK_SOURCE },
					new String[] { nc.ui.ml.NCLangRes.getInstance().getStrByID("36201017", "UPT36201017-000020") /*
																												 * @res
																												 * "票据编号"
																												 */}));
			alChecker.add(new InnerKeepDateChecker(this, StorageVO.DOPERATEDATE));
			alChecker.add(new BodyCurrencyChecker(this, StorageVO.KEEPACCOUNT));
			break;
		case IBillButton.Audit:
			alChecker = new ArrayList<IUIChecker>();
			alChecker.add(new InnerKeepDateChecker(this, StorageVO.DAPPROVEDATE));
			break;
		case IFBMButton.CenterKeep_INPUT:
			alChecker = new ArrayList<IUIChecker>();
			alChecker.add(new InnerKeepDateChecker(this, StorageVO.INPUTDATE));
			break;
		}

		return alChecker;
	}

	@Override
	protected IRefTakenProccessor createRefTakenProccessor() {
		return new InnerKeepRefTakenProcessor(getBillListPanel(),
				getBillCardPanel());
	}

	protected Observer getStatusEngine() {
		return new InnerKeepStatusEngine(this);
	}

	@Override
	protected void initBusinessButton() {
		addPrivateBusinessButton(FBMButtonFactory.getButtonVO(IFBMButton.CenterKeep_INPUT));
		addPrivateBusinessButton(FBMButtonFactory.getButtonVO(IFBMButton.CenterKeep_CANCELINPUT));
		addPrivateBusinessButton(FBMButtonFactory.getButtonVO(IFBMButton.CenterKeep_Return));
		addPrivateBusinessButton(FBMButtonFactory.getButtonVO(IFBMButton.FBM_VOUCHER));
		addPrivateBusinessButton(FBMButtonFactory.getButtonVO(IFBMButton.FBM_CANCELVOUCHER));
		addPrivateBusinessButton(FBMButtonFactory.getButtonVO(IFBMButton.FBM_TALLY));
		addPrivateBusinessButton(FBMButtonFactory.getButtonVO(IFBMButton.FBM_CANCELTALLY));
		addPrivateButton(FBMButtonFactory.getButtonVO(IFBMButton.FBM_SUBMIT));
		addPrivateButton(FBMButtonFactory.getButtonVO(IFBMButton.FBM_CANCELSUBMIT));
		addPrivateButton(FBMButtonFactory.getButtonVO(IFBMButton.LINK_ACCOUNTBANLANCE));
	}

	@Override
	protected IItemEditListener[] getBillItemEditListener() {
		return new IItemEditListener[] {
				new InnerKeepSourceEditListener(this, StorageBVO.PK_SOURCE,
						IBillItem.BODY),
				new BodyItemDeleteEditListener(this, StorageVO.KEEPUNIT),
				new ClearItemValueEditListener(this, StorageVO.KEEPUNIT,
						StorageVO.KEEPACCOUNT),
				new CurrtypeEditListener(this, StorageVO.PK_CURRTYPE),
				new InputTypeListener(this, StorageVO.INPUTTYPE) };
	}

	public StoragePowerVO getPower() {
		if (power == null) {
			initPower();
		}
		return power;
	}

	@Override
	protected String checkPrerequisite() {
		try {
			String pk_settlecenter = FBMPublicQry.retrivePk_settlecentByPkCorp(_getCorp().getPrimaryKey());
			if (pk_settlecenter == null) {
				return nc.ui.ml.NCLangRes.getInstance().getStrByID("3620add", "UPP3620ADD-000242")/*
																								 * @res
																								 * "非资金组织体系下,当前节点不能使用"
																								 */;
			}
		} catch (BusinessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		initPower();
		String pk_cubasdoc = getPower().getPk_cubasdoc();
		if (CommonUtil.isNull(pk_cubasdoc)) {
			return nc.ui.ml.NCLangRes.getInstance().getStrByID("36201017", "UPP36201017-000002")/*
																								 * @res
																								 * "当前公司未设置对应客商,无法进行内部存放业务"
																								 */;
		}
		if (ILinkType.LINK_TYPE_QUERY == getLinkedType()) {
			return super.checkPrerequisite();
		}

		if (!FBMClientInfo.isStartProcess().booleanValue()) {
			MessageDialog.showWarningDlg(this, nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("361608", "UPP361608-000015")/*
																																 * @res
																																 * "提示"
																																 */, nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("361608", "UPP361608-000263")/*
																																																						 * @res
																																																						 * "登录日期没有开始受理过，只有查询，打印，刷新功能！"
																																																						 */);
		}
		return super.checkPrerequisite();
	}

	@Override
	public void doAddAction(ILinkAddData adddata) {
		// TODO Auto-generated method stub
		super.doAddAction(adddata);
		// 设置类型为调剂托管
		getBillCardPanel().getHeadItem(StorageVO.INPUTTYPE).setValue(FbmBusConstant.KEEP_TYPE_RELIEF);

	}
}