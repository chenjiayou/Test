package nc.ui.fbm.discount;

import java.util.ArrayList;
import java.util.Observer;

import nc.bs.logging.Logger;
import nc.itf.cdm.util.CommonUtil;
import nc.ui.bd.ref.AbstractRefModel;
import nc.ui.fac.account.pub.IRefTakenProccessor;
import nc.ui.fbm.discount.listener.DiscountApplyEditListener;
import nc.ui.fbm.discount.listener.DiscountChargeEditListener;
import nc.ui.fbm.discount.listener.DiscountDateEditListener;
import nc.ui.fbm.discount.listener.DiscountDelayDayNumEditListener;
import nc.ui.fbm.discount.listener.DiscountInterestEditListener;
import nc.ui.fbm.discount.listener.DiscountMoneyyEditListener;
import nc.ui.fbm.discount.listener.DiscountPKSourceListener;
import nc.ui.fbm.discount.listener.DiscountRateEditListener;
import nc.ui.fbm.discount.listener.DiscountResultEditListener;
import nc.ui.fbm.discount.listener.DiscountYearDayEditListener;
import nc.ui.fbm.discount.listener.GatherEditListener;
import nc.ui.fbm.discount.reffilter.DiscountAppRefModelFilter;
import nc.ui.fbm.discount.reffilter.DiscountBillSourceFilter;
import nc.ui.fbm.discount.reffilter.DiscountHoldUnitRefModelFilter;
import nc.ui.fbm.discount.status.DiscountStatusEngine;
import nc.ui.fbm.discountapply.listener.BankDocEditlistener;
import nc.ui.fbm.discountapply.reffilter.BankAccRefModelFilter;
import nc.ui.fbm.discountapply.reffilter.GatherBankBusRefModelFilter;
import nc.ui.fbm.invoice.reffilter.CurrCorpBankaccFilter;
import nc.ui.fbm.invoice.reffilter.CurrtypeBankaccFilter;
import nc.ui.fbm.pub.FBMButtonFactory;
import nc.ui.fbm.pub.FBMClientInfo;
import nc.ui.fbm.pub.FBMManageUI;
import nc.ui.fbm.pub.IFBMButton;
import nc.ui.fbm.pub.IItemEditListener;
import nc.ui.fbm.pub.IUIChecker;
import nc.ui.fbm.pub.YFBEditListerner;
import nc.ui.pub.beans.UIComboBox;
import nc.ui.pub.beans.UIRefPane;
import nc.ui.pub.beans.constenum.DefaultConstEnum;
import nc.ui.pub.bill.BillItem;
import nc.ui.pub.linkoperate.ILinkAdd;
import nc.ui.pub.linkoperate.ILinkAddData;
import nc.ui.pub.para.SysInitBO_Client;
import nc.ui.tm.framework.ref.AbstractTMRefModel;
import nc.ui.trade.base.IBillOperate;
import nc.ui.trade.bill.AbstractManageController;
import nc.ui.trade.bill.BillTemplateWrapper;
import nc.ui.trade.button.IBillButton;
import nc.ui.trade.manage.ManageEventHandler;
import nc.uif.pub.exception.UifException;
import nc.vo.fbm.discount.DiscountVO;
import nc.vo.fbm.pub.constant.FbmBusConstant;
import nc.vo.fbm.storage.StoragePowerVO;
import nc.vo.pub.AggregatedValueObject;
import nc.vo.pub.BusinessException;
import nc.vo.tm.enumeration.IParamEnum;
import nc.vo.trade.pub.IBillStatus;

/**
 *
 * <p>
 * 贴现办理UI类
 * <p>
 * 创建人：bsrl <b>日期：2007-09
 *
 */
public class DiscountUI extends FBMManageUI implements ILinkAdd {

	private Integer decimalpoint = 2;
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private Observer StatusEngine = getStatusEngine();

	public DiscountUI() {
		initComBox();
		getBufferData().deleteObserver(this);
		getBufferData().addObserver(StatusEngine);
		getBufferData().addObserver(this);
	}

	public DiscountUI(Boolean useBillSource) {
		super(useBillSource);
	}

	public DiscountUI(String pk_corp, String pk_billType, String pk_busitype,
			String operater, String billId) {
		super(pk_corp, pk_billType, pk_busitype, operater, billId);
	}

	private Observer getStatusEngine() {
		return new DiscountStatusEngine(this);
	}

	@Override
	protected AbstractManageController createController() {
		return new DiscountController();
	}

	@Override
	protected void initSelfData() {
		super.initSelfData();
		initRefPaneFilter();
		initRefPane();
		// initComBox();
		initCardThMark();
	}

	private void initCardThMark() {
		BillItem[] decimalItems = new BillItem[] {
				getBillCardPanel().getHeadItem(DiscountVO.MONEYY),
				getBillCardPanel().getHeadItem(DiscountVO.HPJE),
				getBillCardPanel().getHeadItem(DiscountVO.MONEYB),
				getBillCardPanel().getHeadItem(DiscountVO.MONEYF),
				getBillCardPanel().getHeadItem(DiscountVO.BRATE),
				getBillCardPanel().getHeadItem(DiscountVO.FRATE) };
		getBillCardPanel().setHeadTailShowThMark(decimalItems, true);
		getBillListPanel().getParentListPanel().setShowThMark(true);
	}

	/**
	 * 根据票据类型过滤选票
	 */
	private void initRefPaneFilter() {
		UIRefPane repane = (UIRefPane) getBillCardPanel().getHeadItem(
				DiscountVO.PK_SOURCE).getComponent();
		repane.setCacheEnabled(false);
		// 过滤内部账户
		AbstractRefModel refdes = repane.getRefModel();
		if (AbstractRefModel.class.isAssignableFrom(refdes.getClass())) {
			DiscountBillSourceFilter a_filter = new DiscountBillSourceFilter(
					this,getBillCardPanel().getHeadItem(DiscountVO.OPBILLTYPE),DiscountVO.HOLDERBANK);
			((AbstractTMRefModel) refdes).addSqlFilter(a_filter);
		}
	}

	private void initComBox() {
		// 初始票据类型：私有，统管
		DefaultConstEnum[] opBillType = nc.ui.fbm.pub.ComBoxUtil
				.getOpBillType();
		getBillCardWrapper().initHeadComboBox(DiscountVO.OPBILLTYPE,
				opBillType, false);
		getBillListWrapper().initHeadComboBox(DiscountVO.OPBILLTYPE,
				opBillType, false);

		try{
			DefaultConstEnum[] acceptancetype = nc.ui.fbm.pub.ComBoxUtil
					.getAcceptanceType();
			getBillCardWrapper().initHeadComboBox(DiscountVO.PJLX, acceptancetype,
					false);
			getBillListWrapper().initHeadComboBox(DiscountVO.PJLX, acceptancetype,
					false);
		} catch (BusinessException e) {
			showErrorMessage(e.getMessage());
		}


		//单据状态
		DefaultConstEnum[] billStatus = nc.ui.fbm.pub.ComBoxUtil
				.getBillStatus(getUIControl().getBillType());
		getBillCardWrapper().initHeadComboBox(DiscountVO.VBILLSTATUS,
				billStatus, false);
		getBillListWrapper().initHeadComboBox(DiscountVO.VBILLSTATUS,
				billStatus, false);

		DefaultConstEnum[] discountresult = nc.ui.fbm.pub.ComBoxUtil
				.getDiscountResult();
		getBillCardWrapper().initHeadComboBox(DiscountVO.RESULT,
				discountresult, false);
		getBillListWrapper().initHeadComboBox(DiscountVO.RESULT,
				discountresult, false);

		getBillCardWrapper().initHeadComboBox(DiscountVO.RATEDAYNUM,
				new Integer[] { 360, 365 }, false);// .initHeadComboBox(DiscountVO.RATEDAYNUM,
													// acceptancetype, false);
		getBillListWrapper().initHeadComboBox(DiscountVO.RATEDAYNUM,
				new Integer[] { 360, 365 }, false);


	}

	private void initRefPane() {
		AbstractRefModel source = ((UIRefPane) getBillCardPanel()
				.getHeadItem(DiscountVO.PK_SOURCE).getComponent())
				.getRefModel();

		source.setMatchPkWithWherePart(false);


		AbstractRefModel holdaccref = ((UIRefPane) getBillCardPanel()
				.getHeadItem(DiscountVO.HOLDERACC).getComponent())
				.getRefModel();
		AbstractRefModel discountaccref = ((UIRefPane) getBillCardPanel()
				.getHeadItem(DiscountVO.DISCOUNT_ACCOUNT).getComponent())
				.getRefModel();
		discountaccref.setMatchPkWithWherePart(true);

		if (holdaccref instanceof AbstractTMRefModel) {
			DiscountHoldUnitRefModelFilter holdunitfilter = new DiscountHoldUnitRefModelFilter(
					getBillCardPanel().getHeadItem(DiscountVO.CPRMC));
			((AbstractTMRefModel) holdaccref).addSqlFilter(holdunitfilter);
		}
		if (discountaccref instanceof AbstractTMRefModel) {
			CurrCorpBankaccFilter filter = new CurrCorpBankaccFilter();
			((AbstractTMRefModel)discountaccref).addSqlFilter(filter);
			CurrtypeBankaccFilter filter1 = new CurrtypeBankaccFilter(this,DiscountVO.YBBZ);
			((AbstractTMRefModel)discountaccref).addSqlFilter(filter1);
		}

		BankAccRefModelFilter bankdocFilter  = new BankAccRefModelFilter(this);
		bankdocFilter.setBankdocKey(DiscountVO.PK_DISCOUNT_BANK);
		addSqlFilter(true, DiscountVO.DISCOUNT_ACCOUNT, bankdocFilter);

		GatherBankBusRefModelFilter gFilter = new GatherBankBusRefModelFilter(this);
		gFilter.setBankdocKey(DiscountVO.PK_DISCOUNT_BANK);
		addSqlFilter(true, DiscountVO.PK_SOURCE, gFilter);
		
		AbstractRefModel discountappref = ((UIRefPane) getBillCardPanel()
				.getHeadItem(DiscountVO.PK_DISCOUNT_APP).getComponent())
				.getRefModel();
		if (discountappref instanceof AbstractTMRefModel) {
			DiscountAppRefModelFilter discountappfilter = new DiscountAppRefModelFilter(this);
			((AbstractTMRefModel) discountappref).addSqlFilter(discountappfilter);
		}

	}

	@Override
	public void setDefaultData() throws Exception {
		getBillCardPanel().getHeadItem(DiscountVO.PK_CORP).setValue(
				_getCorp().getPrimaryKey());
		getBillCardPanel().getHeadItem(DiscountVO.VBILLSTATUS).setValue(
				IBillStatus.FREE);
		getBillCardPanel().getHeadItem(DiscountVO.PK_BILLTYPECODE).setValue(
				FbmBusConstant.BILLTYPE_DISCOUNT_TRANSACT);
		getBillCardPanel().getTailItem(DiscountVO.VOPERATORID).setValue(
				_getOperator());
		getBillCardPanel().getTailItem(DiscountVO.DOPERATEDATE).setValue(
				_getDate());
		((UIComboBox) getBillCardPanel().getHeadItem(DiscountVO.RATEDAYNUM)
				.getComponent()).setSelectedIndex(0);
		((UIComboBox) getBillCardPanel().getHeadItem(DiscountVO.RESULT)
				.getComponent()).setSelectedIndex(0);
		//设置票据种类，私有/统管
		((UIComboBox)getBillCardPanel().getHeadItem(DiscountVO.OPBILLTYPE).getComponent()).setSelectedIndex(-1);

		if (!FBMClientInfo.isSettleCenter()) {//如果不为中心
			((UIComboBox)getBillCardPanel().getHeadItem(DiscountVO.OPBILLTYPE).getComponent()).setSelectedIndex(0);
			((UIComboBox)getBillCardPanel().getHeadItem(DiscountVO.OPBILLTYPE).getComponent()).setEnabled(false);
		}
		getBillCardPanel().getHeadItem(DiscountVO.FAILREASON).setEnabled(false);

	}

	@Override
	public void setBodyCondition(String strBodyCond) throws Exception {
		super.setBodyCondition(strBodyCond);
	}

	private Integer getDecimalPoint() {
		if (decimalpoint == null) {
			initDecimalPoint();
		}

		return decimalpoint;
	}

	private Integer initDecimalPoint() {
		try {
			decimalpoint = SysInitBO_Client.getParaInt("0001",
					IParamEnum.FI068);
		} catch (BusinessException e) {
			decimalpoint = 2;
			Logger.error(e.getMessage(),e);
		}

		return decimalpoint;
	}

	@Override
	protected ManageEventHandler createEventHandler() {
		return new DiscountEventHandler(this, getUIControl());
	}

	@Override
	public IItemEditListener[] getBillItemEditListener() {
		return new IItemEditListener[] {
				new GatherEditListener(this, DiscountVO.PK_SOURCE),
				new DiscountRateEditListener(this, DiscountVO.DISCOUNTYRATE),
				new DiscountDateEditListener(this, DiscountVO.DDISCOUNTDATE),
				new DiscountApplyEditListener(this, DiscountVO.PK_DISCOUNT_APP),
				new DiscountResultEditListener(this, DiscountVO.RESULT),
				new DiscountChargeEditListener(this, DiscountVO.DISCOUNTCHARGE),
				new DiscountMoneyyEditListener(this, DiscountVO.MONEYY),
				new DiscountInterestEditListener(this,
						DiscountVO.DISCOUNTINTEREST),
				new DiscountDelayDayNumEditListener(this,
						DiscountVO.DISCOUNTDELAYDAYNUM),

				new OpBillTypeChangeListener(this, DiscountVO.OPBILLTYPE),// 票据类别下拉框监听
				new DiscountPKSourceListener(this,DiscountVO.PK_SOURCE), //票编编号改变时修改票据类别.

				new DiscountYearDayEditListener(this, DiscountVO.RATEDAYNUM),
				new YFBEditListerner(this, DiscountVO.YBBZ, DiscountVO.YBBZ,
						DiscountVO.MONEYY, DiscountVO.MONEYF,
						DiscountVO.MONEYB, DiscountVO.FRATE, DiscountVO.BRATE),
				new YFBEditListerner(this, DiscountVO.MONEYY, DiscountVO.YBBZ,
						DiscountVO.MONEYY, DiscountVO.MONEYF,
						DiscountVO.MONEYB, DiscountVO.FRATE, DiscountVO.BRATE),
				new YFBEditListerner(this, DiscountVO.MONEYY, DiscountVO.YBBZ,
						DiscountVO.DISCOUNTINTEREST, DiscountVO.INTERESTMONEYF,
						DiscountVO.INTERESTMONEYB, DiscountVO.FRATE,
						DiscountVO.BRATE),
				new YFBEditListerner(this, DiscountVO.DISCOUNTCHARGE,
						DiscountVO.YBBZ, DiscountVO.MONEYY, DiscountVO.MONEYF,
						DiscountVO.MONEYB, DiscountVO.FRATE, DiscountVO.BRATE),
				new YFBEditListerner(this, DiscountVO.DISCOUNTINTEREST,
						DiscountVO.YBBZ, DiscountVO.MONEYY, DiscountVO.MONEYF,
						DiscountVO.MONEYB, DiscountVO.FRATE, DiscountVO.BRATE),
				new YFBEditListerner(this, DiscountVO.RATEDAYNUM,
						DiscountVO.YBBZ, DiscountVO.MONEYY, DiscountVO.MONEYF,
						DiscountVO.MONEYB, DiscountVO.FRATE, DiscountVO.BRATE),
				new YFBEditListerner(this, DiscountVO.DISCOUNTDELAYDAYNUM,
						DiscountVO.YBBZ, DiscountVO.MONEYY, DiscountVO.MONEYF,
						DiscountVO.MONEYB, DiscountVO.FRATE, DiscountVO.BRATE),
				new YFBEditListerner(this, DiscountVO.DISCOUNTYRATE,
						DiscountVO.YBBZ, DiscountVO.MONEYY, DiscountVO.MONEYF,
						DiscountVO.MONEYB, DiscountVO.FRATE, DiscountVO.BRATE),
				new YFBEditListerner(this, DiscountVO.DDISCOUNTDATE,
						DiscountVO.YBBZ, DiscountVO.MONEYY, DiscountVO.MONEYF,
						DiscountVO.MONEYB, DiscountVO.FRATE, DiscountVO.BRATE),
				new YFBEditListerner(this, DiscountVO.YBBZ, DiscountVO.YBBZ,
						DiscountVO.DISCOUNTINTEREST, DiscountVO.INTERESTMONEYF,
						DiscountVO.INTERESTMONEYB, DiscountVO.FRATE,
						DiscountVO.BRATE),
				new YFBEditListerner(this, DiscountVO.DISCOUNTINTEREST,
						DiscountVO.YBBZ, DiscountVO.DISCOUNTINTEREST,
						DiscountVO.INTERESTMONEYF, DiscountVO.INTERESTMONEYB,
						DiscountVO.FRATE, DiscountVO.BRATE),
				new YFBEditListerner(this, DiscountVO.RATEDAYNUM,
						DiscountVO.YBBZ, DiscountVO.DISCOUNTINTEREST,
						DiscountVO.INTERESTMONEYF, DiscountVO.INTERESTMONEYB,
						DiscountVO.FRATE, DiscountVO.BRATE),
				new YFBEditListerner(this, DiscountVO.DISCOUNTDELAYDAYNUM,
						DiscountVO.YBBZ, DiscountVO.DISCOUNTINTEREST,
						DiscountVO.INTERESTMONEYF, DiscountVO.INTERESTMONEYB,
						DiscountVO.FRATE, DiscountVO.BRATE),
				new YFBEditListerner(this, DiscountVO.DISCOUNTYRATE,
						DiscountVO.YBBZ, DiscountVO.DISCOUNTINTEREST,
						DiscountVO.INTERESTMONEYF, DiscountVO.INTERESTMONEYB,
						DiscountVO.FRATE, DiscountVO.BRATE),
				new YFBEditListerner(this, DiscountVO.DDISCOUNTDATE,
						DiscountVO.YBBZ, DiscountVO.DISCOUNTINTEREST,
						DiscountVO.INTERESTMONEYF, DiscountVO.INTERESTMONEYB,
						DiscountVO.FRATE, DiscountVO.BRATE),
				new YFBEditListerner(this, DiscountVO.YBBZ, DiscountVO.YBBZ,
						DiscountVO.DISCOUNTCHARGE, DiscountVO.CHARGEMONEYF,
						DiscountVO.CHARGEMONEYB, DiscountVO.FRATE,
						DiscountVO.BRATE),
				new YFBEditListerner(this, DiscountVO.DISCOUNTCHARGE,
						DiscountVO.YBBZ, DiscountVO.DISCOUNTCHARGE,
						DiscountVO.CHARGEMONEYF, DiscountVO.CHARGEMONEYB,
						DiscountVO.FRATE, DiscountVO.BRATE),
				new YFBEditListerner(this, DiscountVO.FRATE, DiscountVO.YBBZ,
						DiscountVO.MONEYY, DiscountVO.MONEYF,
						DiscountVO.MONEYB, DiscountVO.FRATE, DiscountVO.BRATE),
				new YFBEditListerner(this, DiscountVO.BRATE, DiscountVO.YBBZ,
						DiscountVO.MONEYY, DiscountVO.MONEYF,
						DiscountVO.MONEYB, DiscountVO.FRATE, DiscountVO.BRATE),
				new YFBEditListerner(this, DiscountVO.FRATE, DiscountVO.YBBZ,
						DiscountVO.DISCOUNTINTEREST, DiscountVO.INTERESTMONEYF,
						DiscountVO.INTERESTMONEYB, DiscountVO.FRATE,
						DiscountVO.BRATE),
				new YFBEditListerner(this, DiscountVO.BRATE, DiscountVO.YBBZ,
						DiscountVO.DISCOUNTINTEREST, DiscountVO.INTERESTMONEYF,
						DiscountVO.INTERESTMONEYB, DiscountVO.FRATE,
						DiscountVO.BRATE),
				new YFBEditListerner(this, DiscountVO.FRATE, DiscountVO.YBBZ,
						DiscountVO.DISCOUNTCHARGE, DiscountVO.CHARGEMONEYF,
						DiscountVO.CHARGEMONEYB, DiscountVO.FRATE,
						DiscountVO.BRATE),
				new YFBEditListerner(this, DiscountVO.BRATE, DiscountVO.YBBZ,
						DiscountVO.DISCOUNTCHARGE, DiscountVO.CHARGEMONEYF,
						DiscountVO.CHARGEMONEYB, DiscountVO.FRATE,
						DiscountVO.BRATE) ,
			new BankDocEditlistener(this,DiscountVO.PK_DISCOUNT_BANK,DiscountVO.DISCOUNT_ACCOUNT)};

	}

	@Override
	protected IRefTakenProccessor createRefTakenProccessor() {
		return new DiscountRefTakenProccessor(getBillListPanel(),
				getBillCardPanel());
	}

	protected ArrayList<IUIChecker> createValidator(int btnid) {
		ArrayList<IUIChecker> alChecker = null;
		switch (btnid) {
		case IBillButton.Save:
			alChecker = new ArrayList<IUIChecker>();
			alChecker.add(new DiscountUICheck(this));
		}
		return alChecker;
	}

	// currencyItemKeys 分别为：原币币种、是否固定汇率、辅币汇率、本币汇率、利率日期的ItemKey
	// protected String[] getCurrencyItemKeys() {
	// // TODO Auto-generated method stub
	// return new
	// String[]{GatherVO.PK_CURR,"fixflag",GatherVO.FRATE,GatherVO.BRATE,"begindate"};
	// }

	public void doAddAction(ILinkAddData adddata) {
		if (adddata.getSourceBillID() == null) {
			return;
		}
		try {

			setCurrentPanel(BillTemplateWrapper.CARDPANEL);
			setBillOperate(IBillOperate.OP_ADD);
			String billcode = (String) getBillCardPanel().getHeadItem(
					getBillField().getField_BillNo()).getValueObject();
			getBillCardPanel().getHeadItem(getBillField().getField_BillNo())
					.setValue(billcode);

			AggregatedValueObject aggvo = (AggregatedValueObject) adddata
					.getUserObject();
			DiscountVO discountvo = (DiscountVO) aggvo.getParentVO();

			discountvo
					.setPk_billtypecode(FbmBusConstant.BILLTYPE_DISCOUNT_TRANSACT);
			discountvo.setPk_discount_app(discountvo.pk_discount);
			discountvo.setVbillno(billcode);
			// 必须设为空，否则与申请单用同一PK
			discountvo.setPrimaryKey(null);
			getBillCardPanel().setVisible(true);
			setCardUIData(aggvo);

		} catch (UifException e) {
			Logger.error(e.getMessage(),e);
			showErrorMessage(e.getMessage());
		} catch (Exception e) {
			Logger.error(e.getMessage(),e);
			showErrorMessage(e.getMessage());
		}
	}


	protected void initBusinessButton() {
		//add by wangyg 添加了制证与取消制证按钮的状态控制
		addPrivateBusinessButton(FBMButtonFactory
				.getButtonVO(IFBMButton.FBM_VOUCHER));
		addPrivateBusinessButton(FBMButtonFactory
				.getButtonVO(IFBMButton.FBM_CANCELVOUCHER));

	}

	@Override
	public String[][] getHeadShowNum() {
		return new String[][] { { DiscountVO.DISCOUNTYRATE },
				{ getDecimalPoint().toString() } };
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
			return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add","UPP3620ADD-000009")/*@res "当前公司未设置对应客商,无法进行贴现办理业务"*/;
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