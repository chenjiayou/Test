package nc.ui.fbm.impawn;

import java.util.ArrayList;

import nc.bs.logging.Logger;
import nc.itf.cdm.util.CommonUtil;
import nc.ui.bd.ref.AbstractRefModel;
import nc.ui.fac.account.pub.IRefTakenProccessor;
import nc.ui.fbm.impawn.checker.DateChecker;
import nc.ui.fbm.impawn.listener.BillNoChangedListener;
import nc.ui.fbm.impawn.listener.ImpawnValueListener;
import nc.ui.fbm.impawn.reffilter.ImpawnFilter;
import nc.ui.fbm.pub.FBMButtonFactory;
import nc.ui.fbm.pub.FBMClientInfo;
import nc.ui.fbm.pub.FBMManageUI;
import nc.ui.fbm.pub.IFBMButton;
import nc.ui.fbm.pub.IItemEditListener;
import nc.ui.fbm.pub.IUIChecker;
import nc.ui.fbm.pub.YFBEditListerner;
import nc.ui.fbm.pub.reffilter.BankWithoutSettleCenterFilter;
import nc.ui.pub.beans.UIRefPane;
import nc.ui.pub.beans.constenum.DefaultConstEnum;
import nc.ui.pub.bill.BillItem;
import nc.ui.tm.framework.ref.AbstractTMRefModel;
import nc.ui.trade.bill.AbstractManageController;
import nc.ui.trade.button.IBillButton;
import nc.ui.trade.manage.ManageEventHandler;
import nc.vo.fbm.impawn.ImpawnVO;
import nc.vo.fbm.pub.constant.FbmBusConstant;
import nc.vo.fbm.register.RegisterVO;
import nc.vo.fbm.storage.StoragePowerVO;
import nc.vo.pub.BusinessException;
import nc.vo.pub.lang.UFDouble;
import nc.vo.trade.pub.IBillStatus;

/**
 * 类功能说明： 票据质押 日期：2007-11-6 程序员： wues
 */
public class ImpawnUI extends FBMManageUI {
	private ImpawnBackInfoDialog dilog = null;
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	public ImpawnUI() {
		super();
		// 在此初始化combox，避免平台覆盖单据状态
		initComBox();
	}

	public ImpawnUI(Boolean useBillSource) {
		super(useBillSource);
	}

	public ImpawnUI(String pk_corp, String pk_billType, String pk_busitype,
			String operater, String billId) {
		super(pk_corp, pk_billType, pk_busitype, operater, billId);
	}

	@Override
	protected AbstractManageController createController() {
		return new ImpawnCTL();
	}

	@Override
	/**
	 * 一般用来初始化comboBox之类的东西
	 */
	protected void initSelfData() {
		super.initSelfData();
		initRefPane();
		initComBox();
		initCardThMark();

	}

	/**
	 * 注册需要格式化的字段
	 */
	protected void initCardThMark() {
		BillItem[] decimalItems = new BillItem[] {
				getBillCardPanel().getHeadItem(ImpawnVO.MONEYY),// 票面金额
				getBillCardPanel().getHeadItem(ImpawnVO.EVALUATEVALUE),// 评估价值
				getBillCardPanel().getHeadItem(ImpawnVO.IMPAWNABLE),// 可质押价值
				getBillCardPanel().getHeadItem(ImpawnVO.IMPAWNRATE) };// 质押率

		getBillCardPanel().setHeadTailShowThMark(decimalItems, true);
		getBillListPanel().getParentListPanel().setShowThMark(true);
	}

	private void initComBox() {
		// 票据类型
		try{
			DefaultConstEnum[] acceptancetype = nc.ui.fbm.pub.ComBoxUtil
					.getAcceptanceType();
			// 初始化票据类型下拉框
			getBillCardWrapper().initHeadComboBox(ImpawnVO.FBMBILLTYPE,
					acceptancetype, false);
			getBillListWrapper().initHeadComboBox(ImpawnVO.FBMBILLTYPE,
					acceptancetype, false);
		} catch (BusinessException e) {
			showErrorMessage(e.getMessage());
		}
		// 单据状态
		DefaultConstEnum[] statusType = nc.ui.fbm.pub.ComBoxUtil
				.getImpawnStatus();


		// 初始化单据状态下拉框
		getBillCardWrapper().initHeadComboBox(ImpawnVO.VBILLSTATUS, statusType,
				false);
		getBillListWrapper().initHeadComboBox(ImpawnVO.VBILLSTATUS, statusType,
				false);
	}

	@Override
	/**
	 * 设置界面上一些元素的默认值
	 */
	public void setDefaultData() throws Exception {
		getBillCardPanel().getHeadItem(ImpawnVO.PK_BILLTYPECODE).setValue(
				FbmBusConstant.BILLTYPE_IMPAWN);
		getBillCardPanel().getHeadItem(ImpawnVO.VBILLSTATUS).setValue(
				IBillStatus.FREE);

		// getBillCardPanel().getHeadItem(ImpawnVO.IMPAWNRATE).setValue(
		// new UFDouble(100));

		getBillCardPanel().getHeadItem(ImpawnVO.IMPAWNABLE).setValue(
				new UFDouble(0));

		getBillCardPanel().getTailItem(ImpawnVO.VOPERATORID).setValue(
				_getOperator());
		getBillCardPanel().getTailItem(ImpawnVO.DOPERATEDATE).setValue(
				_getDate());
		getBillCardPanel().getHeadItem(ImpawnVO.PK_CORP).setValue(
				_getCorp().getPrimaryKey());
		getBillCardPanel().getHeadItem(ImpawnVO.IMPAWNUNIT).setValue(
				_getOperator());
	}

	/**
	 * 返回质押回收对话框
	 *
	 * @return
	 */
	public ImpawnBackInfoDialog getImpawnDlg() {
		if (null == dilog) {
			dilog = new ImpawnBackInfoDialog(this);
		}
		return dilog;
	}

	protected void initRefPane() {
		UIRefPane pane = (UIRefPane) getBillCardPanel().getHeadItem(
				ImpawnVO.PK_SOURCE).getComponent();
		pane.setCacheEnabled(false);
		AbstractRefModel refModel = pane.getRefModel();
		if (AbstractTMRefModel.class.isAssignableFrom(refModel.getClass())) {
			// 过滤器，票据选择时非已登记状态的票据过滤掉
			ImpawnFilter filter = new ImpawnFilter(getBillCardPanel()
					.getHeadItem(ImpawnVO.PK_SOURCE));
			((AbstractTMRefModel) refModel).addSqlFilter(filter);
		}
		// 付款单位
		UIRefPane payunitRefPane = (UIRefPane) getBillCardPanel().getHeadItem(
				ImpawnVO.PAYUNIT).getComponent();
		payunitRefPane.setAutoCheck(false);

		// 付款单位银行账号
		UIRefPane payunitAccRef = (UIRefPane) getBillCardPanel().getHeadItem(
				ImpawnVO.PAYBANKACC).getComponent();
		payunitAccRef.setAutoCheck(false);

		BankWithoutSettleCenterFilter bFilter = new BankWithoutSettleCenterFilter(this);
		addSqlFilter(true, ImpawnVO.IMPAWNBANK, bFilter);

	}

	@Override
	protected ManageEventHandler createEventHandler() {
		return new ImpawnHandler(this, getUIControl());
	}

	@Override
	public IItemEditListener[] getBillItemEditListener() {
		return new IItemEditListener[] {
				new ImpawnValueListener(this, ImpawnVO.IMPAWNRATE),
				new ImpawnValueListener(this, ImpawnVO.EVALUATEVALUE),
				new BillNoChangedListener(this, ImpawnVO.PK_SOURCE),

				new YFBEditListerner(this, ImpawnVO.EVALUATEVALUE,
						ImpawnVO.PK_CURR, ImpawnVO.EVALUATEVALUE),

				new YFBEditListerner(this, RegisterVO.PK_CURR,
						RegisterVO.PK_CURR, ImpawnVO.MONEYY,
						ImpawnVO.MONEYF, ImpawnVO.MONEYB, ImpawnVO.FRATE,
						ImpawnVO.BRATE),
				new YFBEditListerner(this, ImpawnVO.MONEYY,
						ImpawnVO.PK_CURR, ImpawnVO.MONEYY,
						ImpawnVO.MONEYF, ImpawnVO.MONEYB, ImpawnVO.FRATE,
						ImpawnVO.BRATE),
				new YFBEditListerner(this, ImpawnVO.FRATE,
						ImpawnVO.PK_CURR, ImpawnVO.MONEYY,
						ImpawnVO.MONEYF, ImpawnVO.MONEYB, ImpawnVO.FRATE,
						ImpawnVO.BRATE),

				new YFBEditListerner(this, ImpawnVO.BRATE,
						ImpawnVO.PK_CURR, ImpawnVO.MONEYY,
						ImpawnVO.MONEYF, ImpawnVO.MONEYB, ImpawnVO.FRATE,
						ImpawnVO.BRATE),

				new YFBEditListerner(this, ImpawnVO.IMPAWNABLE,
						ImpawnVO.PK_CURR, ImpawnVO.IMPAWNABLE) };

	}

	@Override
	protected IRefTakenProccessor createRefTakenProccessor() {
		return new ImpawnRefTakenProccessor(getBillListPanel(),
				getBillCardPanel());
	}

	/**
	 * 票据质押前台校验
	 */
	protected ArrayList<IUIChecker> createValidator(int btnid) {
		ArrayList<IUIChecker> alChecker = null;
		switch (btnid) {
		case IBillButton.Save:
			alChecker = new ArrayList<IUIChecker>();
			alChecker.add(new DateChecker(this));
		}
		return alChecker;
	}

	protected void initBusinessButton() {
		addPrivateBusinessButton(FBMButtonFactory
				.getButtonVO(IFBMButton.BTN_IMPAWN_BACK));
		addPrivateBusinessButton(FBMButtonFactory
				.getButtonVO(IFBMButton.BTN_CANCEL_IMPAWNBACK));
		addPrivateBusinessButton(FBMButtonFactory
				.getButtonVO(IFBMButton.Discount_LinkGather));
	}

	public StoragePowerVO getPower() {
		if(power==null){
			initPower();
		}
		return power;
	}
	 @Override
	protected String checkPrerequisite() {
		initPower();
		String pk_cubasdoc = getPower().getPk_cubasdoc();
		if(CommonUtil.isNull(pk_cubasdoc)){
			return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add","UPP3620ADD-000038")/*@res "当前公司未设置对应客商,无法进行票据质押业务"*/;
		}
  	  return super.checkPrerequisite();
	}

		/**
	 *
	 * <p>
	 * 初始化权限
	 * <p>
	 * 作者：lpf
	 * 日期：2007-11-28
	 */
	private void initPower() {
		if(power==null){
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
				Logger.error(e.getMessage(),e);
			}

		}

	}


	 private StoragePowerVO power;

}