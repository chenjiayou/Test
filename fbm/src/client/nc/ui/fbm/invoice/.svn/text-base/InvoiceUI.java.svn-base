/**
 *
 */
package nc.ui.fbm.invoice;

import java.util.ArrayList;
import java.util.Observer;

import javax.swing.ListSelectionModel;

import nc.bs.logging.Logger;
import nc.itf.cdm.util.CommonUtil;
import nc.ui.bd.ref.AbstractRefModel;
import nc.ui.fac.account.pub.IRefTakenProccessor;
import nc.ui.fbm.gather.checker.InvoiceEndDateChecker;
import nc.ui.fbm.gather.checker.PayGetUnitNotEqualChecker;
import nc.ui.fbm.gather.listener.BankaccbasEditListener;
import nc.ui.fbm.gather.listener.CurrencyEditListener;
import nc.ui.fbm.gather.listener.InvoiceDateEditListener;
import nc.ui.fbm.gather.listener.SfflagEditListener;
import nc.ui.fbm.gather.reffilter.BankaccFieldFilter;
import nc.ui.fbm.gather.reffilter.BankdocFieldFilter;
import nc.ui.fbm.invoice.checker.AssureTypeSaveChecker;
import nc.ui.fbm.invoice.checker.LoanBankNotNullChecker;
import nc.ui.fbm.invoice.listener.AssureMoneyCalListener;
import nc.ui.fbm.invoice.listener.AssureTypeEditListener;
import nc.ui.fbm.invoice.listener.CctypeEditListener;
import nc.ui.fbm.invoice.listener.InvoiceBillTypeListener;
import nc.ui.fbm.invoice.listener.InvoiceMoneyyEditListener;
import nc.ui.fbm.invoice.listener.InvoicePayunitListener;
import nc.ui.fbm.invoice.reffilter.CurrCorpBankaccFilter;
import nc.ui.fbm.invoice.reffilter.CurrtypeBankaccFilter;
import nc.ui.fbm.invoice.reffilter.PayAccbankRefModelFilter;
import nc.ui.fbm.invoice.reffilter.ReceiveUnitRefModelFilter;
import nc.ui.fbm.invoice.status.InvoiceStatusEngine;
import nc.ui.fbm.pub.FBMButtonFactory;
import nc.ui.fbm.pub.FBMClientInfo;
import nc.ui.fbm.pub.FBMManageUI;
import nc.ui.fbm.pub.IFBMButton;
import nc.ui.fbm.pub.IItemEditListener;
import nc.ui.fbm.pub.IUIChecker;
import nc.ui.fbm.pub.ItemNotNullChecker;
import nc.ui.fbm.pub.YFBEditListerner;
import nc.ui.pub.beans.UIComboBox;
import nc.ui.pub.beans.UIRefPane;
import nc.ui.pub.beans.constenum.DefaultConstEnum;
import nc.ui.pub.bill.BillItem;
import nc.ui.tm.framework.ref.AbstractTMRefModel;
import nc.ui.tm.framework.util.ClientInfo;
import nc.ui.tm.framework.util.CurrencyClientUtil;
import nc.ui.trade.bill.AbstractManageController;
import nc.ui.trade.button.IBillButton;
import nc.ui.trade.manage.ManageEventHandler;
import nc.vo.fbm.pub.ActionVO;
import nc.vo.fbm.pub.constant.FbmBusConstant;
import nc.vo.fbm.register.RegisterVO;
import nc.vo.fbm.storage.StoragePowerVO;
import nc.vo.pub.BusinessException;
import nc.vo.pub.CircularlyAccessibleValueObject;
import nc.vo.pub.SuperVO;
import nc.vo.pub.lang.UFBoolean;
import nc.vo.trade.pub.IBillStatus;

/**
 * <p>
 * 票据开票UI
 * <p>创建人：lpf
 * <b>日期：2007-9-3
 *
 */
public class InvoiceUI extends FBMManageUI {
	private InvoiceController m_ctrl;
	private InvoiceEventHandler m_handler;
	private Observer StatusEngine = getStatusEngine();
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	/**
	 *
	 */
	public InvoiceUI() {
		super();
		initComBox();
		/*
		 * 增加对bufferData的观察者
		 * 注意因为观察者的顺序是后注册的先执行
		 * 因此需要重新调整观察者的注册顺序
		 */
		getBufferData().deleteObserver(this);
		getBufferData().addObserver(StatusEngine);
		getBufferData().addObserver(this);
	}

	/**
	 * @param useBillSource
	 */
	public InvoiceUI(Boolean useBillSource) {
		super(useBillSource);
		initComBox();
	}

	/**
	 * @param pk_corp
	 * @param pk_billType
	 * @param pk_busitype
	 * @param operater
	 * @param billId
	 */
	public InvoiceUI(String pk_corp, String pk_billType, String pk_busitype,
			String operater, String billId) {
		super(pk_corp, pk_billType, pk_busitype, operater, billId);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected AbstractManageController createController() {
		if (m_ctrl == null)
			m_ctrl = new InvoiceController();
		return m_ctrl;
	}

	@Override
	protected ManageEventHandler createEventHandler() {
		if (m_handler == null)
			m_handler = new InvoiceEventHandler(this, createController());
		return m_handler;
	}

	@Override
	protected void initSelfData() {
		super.initSelfData();
		initRefPane();
		initCardThMark();
		getBillListPanel().getParentListPanel().getTable().setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
	}

	private void initComBox() {
		try{
			DefaultConstEnum[] acceptanceType = nc.ui.fbm.pub.ComBoxUtil.getAcceptanceType();
			getBillCardWrapper().initHeadComboBox(RegisterVO.FBMBILLTYPE, acceptanceType, false);
			getBillListWrapper().initHeadComboBox(RegisterVO.FBMBILLTYPE, acceptanceType, false);
		} catch (BusinessException e) {
			showErrorMessage(e.getMessage());
		}

		DefaultConstEnum[] assureType = nc.ui.fbm.pub.ComBoxUtil.getAssureType();
		getBillCardWrapper().initHeadComboBox(RegisterVO.IMPAWNMODE, assureType, false);
		getBillListWrapper().initHeadComboBox(RegisterVO.IMPAWNMODE, assureType, false);

		DefaultConstEnum[] ccType = nc.ui.fbm.pub.ComBoxUtil.getCCType();
		getBillCardWrapper().initHeadComboBox(RegisterVO.CCTYPE, ccType, false);
		getBillListWrapper().initHeadComboBox(RegisterVO.CCTYPE, ccType, false);

		DefaultConstEnum[] vbillstatus = nc.ui.fbm.pub.ComBoxUtil.getBillStatus(FbmBusConstant.BILLTYPE_INVOICE);
		getBillCardWrapper().initHeadComboBox(RegisterVO.VBILLSTATUS,vbillstatus, false);
		getBillListWrapper().initHeadComboBox(RegisterVO.VBILLSTATUS,vbillstatus, false);

		DefaultConstEnum[] fbillstatus = nc.ui.fbm.pub.ComBoxUtil.getFBillStatus();
		getBillCardWrapper().initHeadComboBox(RegisterVO.REGISTERSTATUS, fbillstatus, false);
		getBillListWrapper().initHeadComboBox(RegisterVO.REGISTERSTATUS, fbillstatus, false);

	}

	private void initRefPane() {
		UIRefPane baseinfoPane=(UIRefPane) getBillCardPanel().getHeadItem(RegisterVO.PK_BASEINFO).getComponent();
		baseinfoPane.setAutoCheck(false);

		AbstractRefModel accbankModel=((UIRefPane) getBillCardPanel().getHeadItem(RegisterVO.PAYBANKACC).getComponent()).getRefModel();
		accbankModel.setMatchPkWithWherePart(true);
		if (accbankModel instanceof AbstractTMRefModel) {
			CurrCorpBankaccFilter filter = new CurrCorpBankaccFilter();
			((AbstractTMRefModel)accbankModel).addSqlFilter(filter);
			CurrtypeBankaccFilter filter1 = new CurrtypeBankaccFilter(this,RegisterVO.PK_CURR);
			((AbstractTMRefModel)accbankModel).addSqlFilter(filter1);
			PayAccbankRefModelFilter filter2=new PayAccbankRefModelFilter(getBillCardPanel().getHeadItem(RegisterVO.PAYBANKACC),this,RegisterVO.PAYUNIT);
			((AbstractTMRefModel)accbankModel).addSqlFilter(filter2);
			
			BankaccFieldFilter filter3 = new BankaccFieldFilter(getBillCardPanel().getHeadItem(RegisterVO.PAYBANK));
			((AbstractTMRefModel) accbankModel).addSqlFilter(filter3);
		}
		AbstractRefModel receiveaccModel=((UIRefPane) getBillCardPanel().getHeadItem(RegisterVO.RECEIVEBANKACC).getComponent()).getRefModel();
		receiveaccModel.setMatchPkWithWherePart(true);
		if (receiveaccModel instanceof AbstractTMRefModel) {
			PayAccbankRefModelFilter filter=new PayAccbankRefModelFilter(getBillCardPanel().getHeadItem(RegisterVO.RECEIVEBANKACC),this,RegisterVO.RECEIVEUNIT);
			((AbstractTMRefModel)receiveaccModel).addSqlFilter(filter);
			CurrtypeBankaccFilter filter1 = new CurrtypeBankaccFilter(this,RegisterVO.PK_CURR);
			((AbstractTMRefModel)receiveaccModel).addSqlFilter(filter1);
			BankaccFieldFilter filter2 = new BankaccFieldFilter(getBillCardPanel().getHeadItem(RegisterVO.RECEIVEBANK));
			((AbstractTMRefModel) receiveaccModel).addSqlFilter(filter2);
		}

		AbstractRefModel secaccModel=((UIRefPane) getBillCardPanel().getHeadItem(RegisterVO.SECURITYACCOUNT).getComponent()).getRefModel();
		secaccModel.setMatchPkWithWherePart(true);
		if (secaccModel instanceof AbstractTMRefModel) {
			CurrCorpBankaccFilter filter = new CurrCorpBankaccFilter();
			((AbstractTMRefModel)secaccModel).addSqlFilter(filter);
			CurrtypeBankaccFilter filter1 = new CurrtypeBankaccFilter(this,RegisterVO.PK_CURR);
			((AbstractTMRefModel)secaccModel).addSqlFilter(filter1);
		}

		AbstractRefModel receiveunitModel=((UIRefPane) getBillCardPanel().getHeadItem(RegisterVO.RECEIVEUNIT).getComponent()).getRefModel();
		receiveunitModel.setMatchPkWithWherePart(true);
		if (receiveunitModel instanceof AbstractTMRefModel) {
			ReceiveUnitRefModelFilter filter=new ReceiveUnitRefModelFilter(getBillCardPanel().getHeadItem(RegisterVO.RECEIVEUNIT),this);
			((AbstractTMRefModel)receiveunitModel).addSqlFilter(filter);
		}
		
		BillItem paybankItem = getBillCardPanel().getHeadItem(RegisterVO.PAYBANK);
		UIRefPane paybankRef = (UIRefPane) paybankItem.getComponent();
		paybankRef.setAutoCheck(false);
		AbstractRefModel paybank1ItemunitMode =  paybankRef.getRefModel();
		if (paybank1ItemunitMode instanceof AbstractTMRefModel) {
			BankdocFieldFilter filter = new BankdocFieldFilter(paybankItem,getBillCardPanel().getHeadItem(RegisterVO.PAYBANKACC));
			((AbstractTMRefModel) paybank1ItemunitMode).addSqlFilter(filter);
			}

		BillItem receivebankItem = getBillCardPanel().getHeadItem(RegisterVO.RECEIVEBANK);
		UIRefPane receivebankRef = (UIRefPane) receivebankItem.getComponent();
		paybankRef.setAutoCheck(false);
		AbstractRefModel receivebankItemunitMode =  receivebankRef.getRefModel();
		if (paybank1ItemunitMode instanceof AbstractTMRefModel) {
			BankdocFieldFilter filter = new BankdocFieldFilter(receivebankItem,getBillCardPanel().getHeadItem(RegisterVO.RECEIVEBANKACC));
			((AbstractTMRefModel) receivebankItemunitMode).addSqlFilter(filter);
			}
	}

	@Override
	public void setDefaultData() throws Exception {
		getBillCardPanel().getHeadItem(RegisterVO.VBILLSTATUS).setValue(IBillStatus.FREE);
		getBillCardPanel().getHeadItem(RegisterVO.PK_BILLTYPECODE).setValue(FbmBusConstant.BILLTYPE_INVOICE);
		getBillCardPanel().getTailItem(RegisterVO.VOPERATORID).setValue(_getOperator());
		getBillCardPanel().getTailItem(RegisterVO.DOPERATEDATE).setValue(_getDate());
		getBillCardPanel().getHeadItem(RegisterVO.PK_CORP).setValue(_getCorp().getPrimaryKey());

		String custPK = ClientInfo.getCommonCurCorpCubasdoc();
		getBillCardPanel().getHeadItem(RegisterVO.INVOICEUNIT).setValue(custPK);
		getBillCardPanel().getHeadItem(RegisterVO.PAYUNIT).setValue(custPK);
		getBillCardPanel().getHeadItem(RegisterVO.HOLDUNIT).setValue(custPK);

		getBillCardPanel().getHeadItem(RegisterVO.INVOICEDATE).setValue(_getDate());
		fireCardAfterEdit(RegisterVO.INVOICEDATE);

		((UIComboBox) (getBillCardPanel().getHeadItem(RegisterVO.FBMBILLTYPE)
				.getComponent())).setSelectedIndex(0);

		getBillCardPanel().getHeadItem(RegisterVO.ORIENTATION).setValue(FbmBusConstant.ORIEINTATION_AP);
		getBillCardPanel().getHeadItem(RegisterVO.CCTYPE).setValue(FbmBusConstant.CCTYPE_NONE);
		getBillCardPanel().getHeadItem(RegisterVO.ISVERIFY).setValue(new UFBoolean(false));
//		getBillCardPanel().getHeadItem(RegisterVO.SFFLAG).setValue(false);
		//NCdp201040610 新增收票及开票，默认币种应取本位币，当前为空
		CurrencyClientUtil currencyClientUtil = new CurrencyClientUtil();
		getBillCardPanel().getHeadItem(RegisterVO.PK_CURR).setValue(currencyClientUtil.getLocalCurrPK());
	}

	@Override
	protected IRefTakenProccessor createRefTakenProccessor() {
		return new InvoiceRefTakenProcessor(getBillListPanel(),getBillCardPanel());
	}

	@Override
	protected ArrayList<IUIChecker> createValidator(int btnid) {
		ArrayList<IUIChecker> alChecker=null;
		switch(btnid){
		case IBillButton.Save:
			alChecker=new ArrayList<IUIChecker>();
			alChecker.add(new ItemNotNullChecker(this));
			alChecker.add(new InvoiceEndDateChecker(this));
			alChecker.add(new PayGetUnitNotEqualChecker(this));
			alChecker.add(new LoanBankNotNullChecker(this));
			alChecker.add(new AssureTypeSaveChecker(this));
		}
		return alChecker;
	}

	public IItemEditListener[] getBillItemEditListener() {
		return new IItemEditListener[] {
				new InvoiceBillTypeListener(this,RegisterVO.FBMBILLTYPE),
//				new InvoicePayunitListener(this, RegisterVO.PAYUNIT),
				new InvoicePayunitListener(this, RegisterVO.RECEIVEUNIT),
				new CurrencyEditListener(this,RegisterVO.PK_CURR),
				new AssureTypeEditListener(this, RegisterVO.IMPAWNMODE),
				new AssureMoneyCalListener(this, RegisterVO.SECURITYRATE),
				new AssureMoneyCalListener(this, RegisterVO.SECURITYMONEY),
				new InvoiceMoneyyEditListener(this, RegisterVO.MONEYY),
				new CctypeEditListener(this,RegisterVO.CCTYPE),
				new InvoiceDateEditListener(this,RegisterVO.INVOICEDATE),
				new YFBEditListerner(this, RegisterVO.PK_CURR,
						RegisterVO.PK_CURR, RegisterVO.MONEYY,
						RegisterVO.MONEYF, RegisterVO.MONEYB, RegisterVO.FRATE,
						RegisterVO.BRATE),
				new YFBEditListerner(this, RegisterVO.PK_CURR,RegisterVO.PK_CURR, RegisterVO.SECURITYMONEY,
						RegisterVO.SECURITYMONEYF, RegisterVO.SECURITYMONEYB, RegisterVO.FRATE,
						RegisterVO.BRATE),
				new YFBEditListerner(this, RegisterVO.PK_CURR,
						RegisterVO.PK_CURR, RegisterVO.POUNDAGEMONEY,
						RegisterVO.POUNDAGEMONEYF, RegisterVO.POUNDAGEMONEYB, RegisterVO.FRATE,
						RegisterVO.BRATE),

				new YFBEditListerner(this, RegisterVO.MONEYY,
						RegisterVO.PK_CURR, RegisterVO.MONEYY,
						RegisterVO.MONEYF, RegisterVO.MONEYB, RegisterVO.FRATE,
						RegisterVO.BRATE),
				new YFBEditListerner(this, RegisterVO.SECURITYMONEY,
						RegisterVO.PK_CURR, RegisterVO.SECURITYMONEY,
						RegisterVO.SECURITYMONEYF, RegisterVO.SECURITYMONEYB, RegisterVO.FRATE,
						RegisterVO.BRATE),
				new YFBEditListerner(this, RegisterVO.POUNDAGEMONEY,
						RegisterVO.PK_CURR, RegisterVO.POUNDAGEMONEY,
						RegisterVO.POUNDAGEMONEYF, RegisterVO.POUNDAGEMONEYB, RegisterVO.FRATE,
						RegisterVO.BRATE),
				new YFBEditListerner(this, RegisterVO.BRATE,
						RegisterVO.PK_CURR, RegisterVO.POUNDAGEMONEY,
						RegisterVO.POUNDAGEMONEYF, RegisterVO.POUNDAGEMONEYB, RegisterVO.FRATE,
						RegisterVO.BRATE),
				new YFBEditListerner(this, RegisterVO.FRATE,
						RegisterVO.PK_CURR, RegisterVO.POUNDAGEMONEY,
						RegisterVO.POUNDAGEMONEYF, RegisterVO.POUNDAGEMONEYB, RegisterVO.FRATE,
						RegisterVO.BRATE),
				new YFBEditListerner(this, RegisterVO.BRATE,
						RegisterVO.PK_CURR, RegisterVO.SECURITYMONEY,
						RegisterVO.SECURITYMONEYF, RegisterVO.SECURITYMONEYB, RegisterVO.FRATE,
						RegisterVO.BRATE),
				new YFBEditListerner(this, RegisterVO.FRATE,
						RegisterVO.PK_CURR, RegisterVO.SECURITYMONEY,
						RegisterVO.SECURITYMONEYF, RegisterVO.SECURITYMONEYB, RegisterVO.FRATE,
						RegisterVO.BRATE),
				new YFBEditListerner(this, RegisterVO.BRATE,
						RegisterVO.PK_CURR, RegisterVO.MONEYY,
						RegisterVO.MONEYF, RegisterVO.MONEYB, RegisterVO.FRATE,
						RegisterVO.BRATE),
				new YFBEditListerner(this, RegisterVO.FRATE,
						RegisterVO.PK_CURR, RegisterVO.MONEYY,
						RegisterVO.MONEYF, RegisterVO.MONEYB, RegisterVO.FRATE,
						RegisterVO.BRATE),
				new BankaccbasEditListener(this,RegisterVO.RECEIVEBANKACC,RegisterVO.RECEIVEUNIT,RegisterVO.RECEIVEBANK),
				new BankaccbasEditListener(this,RegisterVO.PAYBANKACC,RegisterVO.PAYUNIT,RegisterVO.PAYBANK),
				new SfflagEditListener(this,RegisterVO.SFFLAG)
				};
	}

	private Observer getStatusEngine() {
		return new InvoiceStatusEngine(this);
	}

	protected void initBusinessButton() {
        addPrivateBusinessButton(FBMButtonFactory.getButtonVO(IFBMButton.Invoice_CancelBill));
        addPrivateBusinessButton(FBMButtonFactory.getButtonVO(IFBMButton.Invoice_DelCancelBill));
        addPrivateBusinessButton(FBMButtonFactory.getButtonVO(IFBMButton.FBM_VOUCHER));
        addPrivateBusinessButton(FBMButtonFactory.getButtonVO(IFBMButton.FBM_CANCELVOUCHER));
        addPrivateBusinessButton(FBMButtonFactory.getButtonVO(IFBMButton.FBM_QUERYVOUCHER));
    }

//	@Override
//	public void initQueryCondition(UIDialog qryClient) throws Exception {
//		super.initQueryCondition(qryClient);
//
//		QueryConditionClient queryDlg = (QueryConditionClient)qryClient;
//
//		QueryConditionVO[] conditons = queryDlg.getConditionDatas();
//		if(!CommonUtil.isNull(conditons)){
//			for (int i = 0; i < conditons.length; i++) {
//				if(conditons[i].getFieldCode().equals("fbm_register.fbillstatus")){
//					DefaultConstEnum[] enums = nc.ui.fbm.pub.ComBoxUtil.getInvoiceFBillstatus();
//					conditons[i].setEnums(enums);
//				}
//			}
//		}
//
//		UIRefPane refPane = new UIRefPane();
//		AbstractTMRefModel refModel = new InvoiceRefModel();
//		refPane.setRefModel(refModel);
//		refModel.setSqlFilter(new SQLRefModelFilter(){
//			protected String getSelfFilterString() {
//				return " pk_billtypecode='"+FbmBusConstant.BILLTYPE_INVOICE+"' and pk_corp='"+_getCorp().getPk_corp()+"' ";
//			}
//
//			protected boolean isEnabled() {
//				return true;
//			}
//		});
//		queryDlg.setValueRef("fbm_register.pk_register",refPane);
//	}

	protected void initCardThMark() {
		BillItem[] decimalItems = new BillItem[] {
				getBillCardPanel().getHeadItem(RegisterVO.MONEYY),
				getBillCardPanel().getHeadItem(RegisterVO.MONEYB),
				getBillCardPanel().getHeadItem(RegisterVO.MONEYF),
				getBillCardPanel().getHeadItem(RegisterVO.BRATE),
				getBillCardPanel().getHeadItem(RegisterVO.FRATE),
				getBillCardPanel().getHeadItem(RegisterVO.SECURITYMONEY),
				getBillCardPanel().getHeadItem(RegisterVO.POUNDAGEMONEY)
				};
		getBillCardPanel().setHeadTailShowThMark(decimalItems, true);
		getBillListPanel().getParentListPanel().setShowThMark(true);
	}

	/**
	 * 更新指定行数据。 创建日期：(2004-1-11 11:23:25)
	 *
	 * @exception java.lang.Exception
	 *                异常说明。
	 */
	public void updateListVo() throws java.lang.Exception {
		CircularlyAccessibleValueObject vo = null;
		if (getBufferData().getCurrentVO() != null) {
			vo = getBufferData().getCurrentVO().getParentVO();
			getBillListWrapper().updateListVo(vo,
					getBufferData().getCurrentRow());

			//执行公式
		}
	}

	@Override
	protected void queryFBMnode(String sourcePk) throws Exception {
		getBufferData().addVOToBuffer(loadHeadData(sourcePk));
		getBufferData().setCurrentRow(getBufferData().getCurrentRow());

		//解决授信的跨公司联查状态无法显示的问题
		String fbillstatus = (String) getBillCardPanel().getHeadItem(RegisterVO.REGISTERSTATUS).getValueObject();
		if(CommonUtil.isNull(fbillstatus)){
			String corpPk = (String) getBillCardPanel().getHeadItem(RegisterVO.PK_CORP).getValueObject();
			SuperVO[] queryVos = getBusiDelegator().queryByCondition(ActionVO.class, " pk_source='"+sourcePk+"' and isnew = 'Y' and pk_corp='"+corpPk+"' and isnull(dr,0)=0 order by ts desc ");
			if(!CommonUtil.isNull(queryVos)){
				String registerstatus = (String) queryVos[0].getAttributeValue(RegisterVO.REGISTERSTATUS);
				getBillCardPanel().getHeadItem(RegisterVO.REGISTERSTATUS).setValue(registerstatus);
			}
		}
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
			return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add","UPP3620ADD-000041")/*@res "当前公司未设置对应客商,无法进行付票登记业务"*/;
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