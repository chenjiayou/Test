package nc.ui.fbm.consignbank;

import java.util.ArrayList;
import java.util.Observer;

import nc.bs.logging.Logger;
import nc.itf.cdm.util.CommonUtil;
import nc.ui.bd.ref.AbstractRefModel;
import nc.ui.fac.account.pub.IRefTakenProccessor;
import nc.ui.fbm.consignbank.check.SameBankChecker;
import nc.ui.fbm.consignbank.listener.ConsignBankPKSourceListener;
import nc.ui.fbm.consignbank.listener.ConsignBankSourceEditListener;
import nc.ui.fbm.consignbank.reffilter.BillSourceFilter;
import nc.ui.fbm.consignbank.status.ConsignStatusEngine;
import nc.ui.fbm.discount.OpBillTypeChangeListener;
import nc.ui.fbm.discountapply.reffilter.BankAccRefModelFilter;
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
import nc.ui.tm.framework.ref.AbstractTMRefModel;
import nc.ui.trade.bill.AbstractManageController;
import nc.ui.trade.button.IBillButton;
import nc.ui.trade.manage.ManageEventHandler;
import nc.vo.fbm.consignbank.CollectionVO;
import nc.vo.fbm.discount.DiscountVO;
import nc.vo.fbm.pub.constant.FbmBusConstant;
import nc.vo.fbm.storage.StoragePowerVO;
import nc.vo.pub.BusinessException;
import nc.vo.trade.pub.IBillStatus;

/**
 *
 * <p>
 *	银行托收UI类
 * <p>创建人：bsrl
 * <b>日期：2007-09
 *
 */
public class ConsignBankUI extends FBMManageUI {

	private ConsignBankDisableDlg disableDlg = new ConsignBankDisableDlg(this);
	private ConsignBankTransactDlg transactDlg = new ConsignBankTransactDlg(this);
	private Observer StatusEngine = getStatusEngine();

	public ConsignBankUI() {
		initComBox();
		getBufferData().deleteObserver(this);
		getBufferData().addObserver(StatusEngine);
		getBufferData().addObserver(this);
	}

	public ConsignBankUI(Boolean useBillSource) {
		super(useBillSource);
	}
	private Observer getStatusEngine() {
		return new ConsignStatusEngine(this);
	}

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	public ConsignBankUI(String pk_corp, String pk_billType, String pk_busitype,
			String operater, String billId) {
		super(pk_corp, pk_billType, pk_busitype, operater, billId);
	}

	public ConsignBankDisableDlg getDisableDlg() {
		return disableDlg;
	}

	public ConsignBankTransactDlg getTransactDlg() {
		return transactDlg;
	}



	@Override
	protected AbstractManageController createController() {
		return new ConsignBankController();
	}

	@Override
	protected void initSelfData() {
		super.initSelfData();
		initRefPaneFilter();
		initRefPane();
//		initComBox();
		initCardThMark();
	}

	/**
	 * 根据票据类型过滤选票
	 */
	private void initRefPaneFilter() {
		UIRefPane repane = (UIRefPane) getBillCardPanel().getHeadItem(
				CollectionVO.PK_SOURCE).getComponent();
		repane.setCacheEnabled(false);
		// 根据票据类型过滤票据
		AbstractRefModel refdes = repane.getRefModel();
		if (AbstractRefModel.class.isAssignableFrom(refdes.getClass())) {
			//过滤器。zhouwb 2008-10-10
			BillSourceFilter a_filter = new BillSourceFilter(
					this,getBillCardPanel().getHeadItem(CollectionVO.OPBILLTYPE),CollectionVO.HOLDERBANK);
			((AbstractTMRefModel) refdes).addSqlFilter(a_filter);
		}

		BankAccRefModelFilter bankdocFilter  = new BankAccRefModelFilter(this);
		bankdocFilter.setBankdocKey(CollectionVO.HOLDERBANK);
		addSqlFilter(true, CollectionVO.HOLDERACC, bankdocFilter);

//		GatherBankBusRefModelFilter gFilter = new GatherBankBusRefModelFilter(this);
//		gFilter.setBankdocKey(CollectionVO.HOLDERBANK);
//		addSqlFilter(true, CollectionVO.PK_SOURCE, gFilter);
	}
	private void initCardThMark() {
		BillItem[] decimalItems=new BillItem[]{
				getBillCardPanel().getHeadItem(CollectionVO.MONEYY),
				getBillCardPanel().getHeadItem(CollectionVO.MONEYB),
				getBillCardPanel().getHeadItem(CollectionVO.PMJE),
				getBillCardPanel().getHeadItem(CollectionVO.MONEYF),
				getBillCardPanel().getHeadItem(CollectionVO.BRATE),
				getBillCardPanel().getHeadItem(CollectionVO.FRATE)
		};
		getBillCardPanel().setHeadTailShowThMark(decimalItems, true);
		getBillListPanel().getParentListPanel().setShowThMark(true);
	}



	private void initComBox() {

		//初始票据类型：私有，统管
		DefaultConstEnum[] opBillType = nc.ui.fbm.pub.ComBoxUtil.getOpBillType();
		getBillCardWrapper().initHeadComboBox(DiscountVO.OPBILLTYPE, opBillType, false);
		getBillListWrapper().initHeadComboBox(DiscountVO.OPBILLTYPE, opBillType, false);
		try{
			DefaultConstEnum[] acceptancetype = nc.ui.fbm.pub.ComBoxUtil.getAcceptanceType();
			getBillCardWrapper().initHeadComboBox(CollectionVO.PJLX, acceptancetype, false);
			getBillListWrapper().initHeadComboBox(CollectionVO.PJLX, acceptancetype, false);
		} catch (BusinessException e) {
			showErrorMessage(e.getMessage());
		}


		//单据状态
		DefaultConstEnum[] billStatus = nc.ui.fbm.pub.ComBoxUtil.getBillStatus(getUIControl().getBillType());

		getBillCardWrapper().initHeadComboBox(CollectionVO.VBILLSTATUS,billStatus, false);
		getBillListWrapper().initHeadComboBox(CollectionVO.VBILLSTATUS,billStatus, false);


	}

	private void initRefPane() {
		AbstractRefModel holdaccref=((UIRefPane) getBillCardPanel().getHeadItem(CollectionVO.HOLDERACC).getComponent()).getRefModel();
		holdaccref.setMatchPkWithWherePart(true);
		if (holdaccref instanceof AbstractTMRefModel) {
//			PayAccbankRefModelFilter filter=new PayAccbankRefModelFilter(getBillCardPanel().getHeadItem(CollectionVO.HOLDERACC),this,CollectionVO.CPRMC);
//			((AbstractTMRefModel)holdaccref).addSqlFilter(filter);
//			CurrtypeBankaccFilter filter1 = new CurrtypeBankaccFilter(this,CollectionVO.YBBZ);
//			((AbstractTMRefModel)holdaccref).addSqlFilter(filter1);

			CurrCorpBankaccFilter filter = new CurrCorpBankaccFilter();
			((AbstractTMRefModel)holdaccref).addSqlFilter(filter);
			CurrtypeBankaccFilter filter1 = new CurrtypeBankaccFilter(this,CollectionVO.YBBZ);
			((AbstractTMRefModel)holdaccref).addSqlFilter(filter1);
		}
	}

	@Override
	public void setDefaultData() throws Exception {
		getBillCardPanel().getHeadItem(CollectionVO.PK_CORP).setValue(_getCorp().getPrimaryKey());
		getBillCardPanel().getHeadItem(CollectionVO.VBILLSTATUS).setValue(IBillStatus.FREE);
		getBillCardPanel().getHeadItem(DiscountVO.PK_BILLTYPECODE).setValue(FbmBusConstant.BILLTYPE_COLLECTION_UNIT);
		getBillCardPanel().getTailItem(CollectionVO.VOPERATORID).setValue(_getOperator());
		getBillCardPanel().getTailItem(CollectionVO.DOPERATEDATE).setValue(_getDate());

		// 设置票据种类，私有/统管
		((UIComboBox) getBillCardPanel().getHeadItem(DiscountVO.OPBILLTYPE)
				.getComponent()).setSelectedIndex(-1);

		if (!FBMClientInfo.isSettleCenter()) {// 如果不为中心
			((UIComboBox) getBillCardPanel().getHeadItem(DiscountVO.OPBILLTYPE)
					.getComponent()).setSelectedIndex(0);
			((UIComboBox) getBillCardPanel().getHeadItem(DiscountVO.OPBILLTYPE)
					.getComponent()).setEnabled(false);
		}
	}

	@Override
	protected ManageEventHandler createEventHandler() {
		return new ConsignBankEventHandler(this,getUIControl());
	}

	@Override
	public IItemEditListener[] getBillItemEditListener() {
		return new IItemEditListener[]{new ConsignBankSourceEditListener(this,CollectionVO.PK_SOURCE),
				new OpBillTypeChangeListener(this,DiscountVO.OPBILLTYPE),//票据类别下拉框监听
				new ConsignBankPKSourceListener(this,CollectionVO.PK_SOURCE),//票据编号改变时，修改票据类别。
			   new YFBEditListerner(this, CollectionVO.YBBZ,CollectionVO.YBBZ,CollectionVO.MONEYY, CollectionVO.MONEYF,CollectionVO.MONEYB, CollectionVO.FRATE, CollectionVO.BRATE),
			   new YFBEditListerner(this, CollectionVO.BRATE,CollectionVO.YBBZ,CollectionVO.MONEYY, CollectionVO.MONEYF,CollectionVO.MONEYB, CollectionVO.FRATE, CollectionVO.BRATE),
			   new YFBEditListerner(this, CollectionVO.FRATE,CollectionVO.YBBZ,CollectionVO.MONEYY, CollectionVO.MONEYF,CollectionVO.MONEYB, CollectionVO.FRATE, CollectionVO.BRATE)
               };
	}

	@Override
	protected IRefTakenProccessor createRefTakenProccessor() {
		return new ConsignBankRefTakenProcessor(getBillListPanel(), getBillCardPanel());
	}

	protected void initBusinessButton() {
        addPrivateBusinessButton(FBMButtonFactory.getButtonVO(IFBMButton.ConsignBank_Disable));
        addPrivateBusinessButton(FBMButtonFactory.getButtonVO(IFBMButton.ConsignBank_Transact));
        addPrivateBusinessButton(FBMButtonFactory.getButtonVO(IFBMButton.ConsignBank_CancelTransact));
      //add by wangyg 添加了制证与取消制证按钮的状态控制
        addPrivateBusinessButton(FBMButtonFactory
				.getButtonVO(IFBMButton.FBM_VOUCHER));
		addPrivateBusinessButton(FBMButtonFactory
				.getButtonVO(IFBMButton.FBM_CANCELVOUCHER));
    }

	protected ArrayList<IUIChecker> createValidator(int btnid) {
		ArrayList<IUIChecker> alChecker = null;
		switch (btnid) {
		case IBillButton.Save:
			alChecker = new ArrayList<IUIChecker>();
			alChecker.add(new ConsignBankUICheck(this));
			alChecker.add(new SameBankChecker(this));
		}
		return alChecker;
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
			return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add","UPP3620ADD-000005")/*@res "当前公司未设置对应客商,无法进行银行托收业务"*/;
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



//	@Override
//	public Object getUserObject() {
//		return new ConsignBankBackCheck();
//	}

	//currencyItemKeys 分别为：原币币种、是否固定汇率、辅币汇率、本币汇率、利率日期的ItemKey
//	protected String[] getCurrencyItemKeys() {
//		return new String[]{GatherVO.PK_CURR,"fixflag",GatherVO.FRATE,GatherVO.BRATE,"begindate"};
//	}

}