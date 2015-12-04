package nc.ui.fbm.relief;

import java.util.ArrayList;
import java.util.Observer;

import nc.bs.logging.Logger;
import nc.itf.cdm.util.CommonUtil;
import nc.ui.bd.ref.AbstractRefModel;
import nc.ui.fac.account.pub.IRefTakenProccessor;
import nc.ui.fbm.pub.FBMButtonFactory;
import nc.ui.fbm.pub.FBMClientInfo;
import nc.ui.fbm.pub.FBMManageUI;
import nc.ui.fbm.pub.IFBMButton;
import nc.ui.fbm.pub.IItemEditListener;
import nc.ui.fbm.pub.IUIChecker;
import nc.ui.fbm.pub.ItemNotNullChecker;
import nc.ui.fbm.pub.reffilter.InnerAccRuleFilter;
import nc.ui.fbm.pub.refmodel.InnerAccountRefModelFilter;
import nc.ui.fbm.relief.listener.InnerAccListener;
import nc.ui.fbm.relief.listener.ReliefCurrTypeEditListener;
import nc.ui.fbm.relief.status.ReliefStatusEngine;
import nc.ui.fbm.storage.checker.BodyCurrencyChecker;
import nc.ui.fbm.storage.checker.BodyItemUniqueChecker;
import nc.ui.fbm.storage.checker.BodyNotEmptyChecker;
import nc.ui.fbm.storage.listener.InnerKeepSourceEditListener;
import nc.ui.pub.beans.MessageDialog;
import nc.ui.pub.beans.UIRefPane;
import nc.ui.pub.beans.constenum.DefaultConstEnum;
import nc.ui.pub.bill.IBillItem;
import nc.ui.pub.linkoperate.ILinkType;
import nc.ui.tm.framework.billtemplet.PkReturnableBillCellEditor;
import nc.ui.tm.framework.ref.AbstractTMRefModel;
import nc.ui.tm.framework.util.CurrencyClientUtil;
import nc.ui.trade.bill.AbstractManageController;
import nc.ui.trade.button.IBillButton;
import nc.ui.trade.manage.ManageEventHandler;
import nc.vo.fbm.pub.FBMPublicQry;
import nc.vo.fbm.pub.constant.FbmBusConstant;
import nc.vo.fbm.relief.ReliefBVO;
import nc.vo.fbm.relief.ReliefVO;
import nc.vo.fbm.storage.StoragePowerVO;
import nc.vo.pub.BusinessException;
import nc.vo.pub.lang.UFBoolean;
import nc.vo.trade.pub.IBillStatus;

/**
 * 功能： 票据调剂出库 日期：2007-10-20 程序员：wues
 */
public class ReliefUI extends FBMManageUI {

	private static final long serialVersionUID = 1L;

	private ManageEventHandler handler = null;
	private ReliefCTL controller = null;
	private Observer StatusEngine = getStatusEngine();

	public ReliefUI() {
		super();
		initComBox();
		getBufferData().deleteObserver(this);
		getBufferData().addObserver(StatusEngine);
		getBufferData().addObserver(this);
		setBtnDisabled();
	}

	public ReliefUI(Boolean useBillSource) {
		super(useBillSource);
	}

	public ReliefUI(String pk_corp, String pk_billType, String pk_busitype,
			String operater, String billId) {
		super(pk_corp, pk_billType, pk_busitype, operater, billId);

	}

	protected ManageEventHandler createEventHandler() {
		if (handler == null)
			handler = new ReliefHandler(this, createController());
		return handler;
	}

	protected AbstractManageController createController() {
		if (controller == null)
			controller = new ReliefCTL();
		return controller;
	}

	@Override
	protected void initSelfData() {
		super.initSelfData();
		initRefPaneFilter();
		initRefPane();
		initBillCellEditor();
	}

	private void initBillCellEditor() {
		int colomn = getBillCardPanel().getBodyColByKey(ReliefBVO.PK_SOURCE);
		UIRefPane refpane = (UIRefPane) getBillCardPanel().getBodyItem(ReliefBVO.PK_SOURCE).getComponent();
		getBillCardPanel().getBillTable().getColumnModel().getColumn(colomn).setCellEditor(new PkReturnableBillCellEditor(
				refpane));
	}

	private void initRefPane() {
		UIRefPane srcPane = (UIRefPane) getBillCardPanel().getBodyItem(ReliefBVO.PK_SOURCE).getComponent();
		srcPane.setMultiSelectedEnabled(true);
		srcPane.setCacheEnabled(false);
		AbstractRefModel srcModel = srcPane.getRefModel();
		if (AbstractTMRefModel.class.isAssignableFrom(srcModel.getClass())) {
			BillNoSQLFilterByCur filter = new BillNoSQLFilterByCur(
					getBillCardPanel().getBodyItem(ReliefBVO.PK_SOURCE), this,
					ReliefVO.PK_CURRTYPE);
			((AbstractTMRefModel) srcModel).addSqlFilter(filter);
		}
	}

	/**
	 * 
	 * 初始化过滤器
	 */
	private void initRefPaneFilter() {
		UIRefPane settleacc = (UIRefPane) getBillCardPanel().getHeadItem(ReliefVO.INNERACC).getComponent();
		// 过滤内部账户
		AbstractRefModel refdes = settleacc.getRefModel();
		if (AbstractRefModel.class.isAssignableFrom(refdes.getClass())) {
			InnerAccountRefModelFilter a_filter = new InnerAccountRefModelFilter(
					getBillCardPanel().getHeadItem(ReliefVO.RELIEFUNIT), this);
			((AbstractTMRefModel) refdes).addSqlFilter(a_filter);
		}
		InnerAccRuleFilter accRuleFilter = new InnerAccRuleFilter(
				FbmBusConstant.BILLTYPE_RELIEF, FbmBusConstant.ACC_INNER);
		addSqlFilter(true, ReliefVO.INNERACC, accRuleFilter);

		AbstractRefModel custfmdl = ((UIRefPane) getBillCardPanel().getHeadItem(ReliefVO.RELIEFUNIT).getComponent()).getRefModel();
		if (custfmdl instanceof AbstractTMRefModel) {
			ReliefUnitRefModelFilter filter = new ReliefUnitRefModelFilter(
					getBillCardPanel().getHeadItem(ReliefVO.RELIEFUNIT), this);
			((AbstractTMRefModel) custfmdl).addSqlFilter(filter);
		}
	}

	private void initComBox() {
		DefaultConstEnum[] statusList = nc.ui.fbm.pub.ComBoxUtil.getReliefStatus();
		getBillCardWrapper().initHeadComboBox(ReliefVO.VBILLSTATUS, statusList, false);
		getBillListWrapper().initHeadComboBox(ReliefVO.VBILLSTATUS, statusList, false);

		try {
			DefaultConstEnum[] acceptancetype = nc.ui.fbm.pub.ComBoxUtil.getAcceptanceType();
			getBillCardWrapper().initBodyComboBox(ReliefBVO.FBMBILLTYPE, acceptancetype, false);
			getBillListWrapper().initBodyComboBox(ReliefBVO.FBMBILLTYPE, acceptancetype, false);
		} catch (BusinessException e) {
			showErrorMessage(e.getMessage());
		}

	}

	private Observer getStatusEngine() {
		return new ReliefStatusEngine(this);
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
					new String[] { ReliefBVO.PK_SOURCE },
					new String[] { nc.ui.ml.NCLangRes.getInstance().getStrByID("36201045", "UPT36201045-000011") /*
																												 * @res
																												 * "票据编号"
																												 */}));
			alChecker.add(new BodyCurrencyChecker(this, ReliefVO.INNERACC));
		}
		return alChecker;
	}

	@Override
	protected IRefTakenProccessor createRefTakenProccessor() {
		return new ReliefRefTakenProcessor(getBillListPanel(),
				getBillCardPanel());
	}

	@Override
	public void setDefaultData() throws Exception {
		getBillCardPanel().getHeadItem(ReliefVO.VBILLSTATUS).setValue(IBillStatus.FREE);
		getBillCardPanel().getHeadItem(ReliefVO.ISOUT).setValue(UFBoolean.FALSE);
		getBillCardPanel().getHeadItem(ReliefVO.PK_BILLTYPECODE).setValue(FbmBusConstant.BILLTYPE_RELIEF);
		getBillCardPanel().getTailItem(ReliefVO.VOPERATORID).setValue(_getOperator());
		getBillCardPanel().getTailItem(ReliefVO.DOPERATEDATE).setValue(_getDate());
		getBillCardPanel().getHeadItem(ReliefVO.PK_CORP).setValue(_getCorp().getPk_corp());
		CurrencyClientUtil currencyClientUtil = new CurrencyClientUtil();
		getBillCardPanel().getHeadItem(ReliefVO.PK_CURRTYPE).setValue(currencyClientUtil.getLocalCurrPK());
	}

	/**
	 * 进入时设置默认按钮不可用
	 */
	private void setBtnDisabled() {
		if (!FBMClientInfo.isSettleCenter(_getCorp().getPk_corp())) {
			getButtonManager().getButton(IBillButton.Add).setEnabled(false);
			getButtonManager().getButton(IBillButton.Delete).setEnabled(false);
			getButtonManager().getButton(IBillButton.Edit).setEnabled(false);
			getButtonManager().getButton(IBillButton.Save).setEnabled(false);
			getButtonManager().getButton(IFBMButton.Discount_LinkGather).setEnabled(false);

			updateButtons();
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nc.ui.fbm.pub.FBMManageUI#getBillItemEditListener()
	 */
	@Override
	protected IItemEditListener[] getBillItemEditListener() {
		return new IItemEditListener[] {
				new InnerAccListener(this, ReliefVO.RELIEFUNIT, IBillItem.HEAD),
				new ReliefCurrTypeEditListener(this, ReliefVO.PK_CURRTYPE,
						IBillItem.HEAD),
				new InnerKeepSourceEditListener(this, ReliefBVO.PK_SOURCE,
						IBillItem.BODY) };
	}

	@Override
	protected void initBusinessButton() {
		addPrivateBusinessButton(FBMButtonFactory.getButtonVO(IFBMButton.Relief_Output));
		addPrivateBusinessButton(FBMButtonFactory.getButtonVO(IFBMButton.Relief_CancelOutput));
		addPrivateBusinessButton(FBMButtonFactory.getButtonVO(IFBMButton.FBM_VOUCHER));
		addPrivateBusinessButton(FBMButtonFactory.getButtonVO(IFBMButton.FBM_CANCELVOUCHER));
		addPrivateBusinessButton(FBMButtonFactory.getButtonVO(IFBMButton.Relief_LinkQueryGroup));

		addPrivateBusinessButton(FBMButtonFactory.getButtonVO(IFBMButton.Discount_LinkGather));

		addPrivateBusinessButton(FBMButtonFactory.getButtonVO(IFBMButton.Relief_LQAccoutBalance));
		addPrivateBusinessButton(FBMButtonFactory.getButtonVO(IFBMButton.Relief_LQVoucher));

		addPrivateBusinessButton(FBMButtonFactory.getButtonVO(IFBMButton.FBM_TALLY));
		addPrivateBusinessButton(FBMButtonFactory.getButtonVO(IFBMButton.FBM_CANCELTALLY));
	}

	// public void initQueryCondition(UIDialog qryClient)
	// throws java.lang.Exception {
	// super.initQueryCondition(qryClient);
	// QueryConditionClient queryDlg = (QueryConditionClient) qryClient;
	// QueryConditionVO[] conditons = queryDlg.getConditionDatas();
	//
	// if (!CommonUtil.isNull(conditons)) {
	// for (int i = 0; i < conditons.length; i++) {
	// if (conditons[i].getFieldCode().equals("fbm_relief.reliefunit")) {
	// String pk_corp = _getCorp().getPrimaryKey();
	// if (CommonUtil.isNull(pk_corp)) {
	// continue;
	// }
	// boolean isCenter = FBMClientInfo.isSettleCenter(pk_corp);
	// if (!isCenter) {
	// queryDlg.setDefaultValue("fbm_relief.reliefunit",
	// FBMClientInfo.getCorpCubasdoc(pk_corp), null);
	// queryDlg.setConditionEditable("fbm_relief.reliefunit",
	// false);
	// }
	// }
	// }
	// }
	// }

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
			return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add", "UPP3620ADD-000114")/*
																										 * @res
																										 * "当前公司未设置对应客商,无法进行票据调剂业务"
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

}