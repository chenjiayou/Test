/**
 *
 */
package nc.ui.fbm.accept;

import java.util.ArrayList;
import java.util.Observer;

import nc.itf.cdm.util.CommonUtil;
import nc.ui.bd.ref.AbstractRefModel;
import nc.ui.fac.account.pub.IRefTakenProccessor;
import nc.ui.fbm.accept.checker.AcceptDateChecker;
import nc.ui.fbm.accept.checker.SecurityMoneyChecker;
import nc.ui.fbm.accept.listener.AcceptSourcePKEditListener;
import nc.ui.fbm.accept.listener.BackSecMoneyEditListener;
import nc.ui.fbm.accept.reffilter.AcceptSourcePKRefModelFilter;
import nc.ui.fbm.accept.reffilter.HoldUnitRefFilter;
import nc.ui.fbm.gather.listener.UnitEditListener;
import nc.ui.fbm.gather.reffilter.CusbasDocWithCorpRefFilter;
import nc.ui.fbm.gather.reffilter.HandInRefModelFilter;
import nc.ui.fbm.invoice.reffilter.CurrCorpBankaccFilter;
import nc.ui.fbm.invoice.reffilter.CurrtypeBankaccFilter;
import nc.ui.fbm.invoice.reffilter.PayAccbankRefModelFilter;
import nc.ui.fbm.pub.FBMButtonFactory;
import nc.ui.fbm.pub.FBMManageUI;
import nc.ui.fbm.pub.IFBMButton;
import nc.ui.fbm.pub.IItemEditListener;
import nc.ui.fbm.pub.IUIChecker;
import nc.ui.fbm.pub.ItemNotNullChecker;
import nc.ui.fbm.pub.YFBEditListerner;
import nc.ui.fbm.storage.listener.ClearItemValueEditListener;
import nc.ui.pub.beans.UIRefPane;
import nc.ui.pub.beans.constenum.DefaultConstEnum;
import nc.ui.pub.bill.BillCardPanel;
import nc.ui.pub.bill.BillEditEvent;
import nc.ui.pub.bill.BillItem;
import nc.ui.pub.linkoperate.ILinkAddData;
import nc.ui.tm.framework.ref.AbstractTMRefModel;
import nc.ui.trade.bill.AbstractManageController;
import nc.ui.trade.button.IBillButton;
import nc.ui.trade.manage.ManageEventHandler;
import nc.vo.fbm.acceptbill.AcceptVO;
import nc.vo.fbm.pub.constant.FbmBusConstant;
import nc.vo.fbm.register.RegisterVO;
import nc.vo.pub.BusinessException;
import nc.vo.trade.pub.IBillStatus;

/**
 * <p>
 * 票据付款UI
 * <p>创建人：lpf
 * <b>日期：2007-9-3
 *
 */
public class AcceptBillUI extends FBMManageUI {
	private AcceptBillController m_ctrl;
	private AcceptBillEventHandler m_handler;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Observer StatusEngine = getStatusEngine();
	/**
	 * 
	 */
	public AcceptBillUI() {
		// TODO Auto-generated constructor stub
		super();
		initComBox();
		getBufferData().deleteObserver(this);
		getBufferData().addObserver(StatusEngine);
		getBufferData().addObserver(this);
	}

	/**
	 * @param useBillSource
	 */
	public AcceptBillUI(Boolean useBillSource) {
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
	public AcceptBillUI(String pk_corp, String pk_billType, String pk_busitype,
			String operater, String billId) {
		super(pk_corp, pk_billType, pk_busitype, operater, billId);
		initComBox();
	}

	@Override
	protected IRefTakenProccessor createRefTakenProccessor() {
		return new AcceptBillRefTakenProcessor(getBillListPanel(),getBillCardPanel());
	}

	@Override
	protected ArrayList<IUIChecker> createValidator(int btnid) {
		ArrayList<IUIChecker> alChecker = null;
		switch (btnid) {
		case IBillButton.Save:
			alChecker = new ArrayList<IUIChecker>();
			alChecker.add(new ItemNotNullChecker(this));
			alChecker.add(new AcceptDateChecker(this));
			//2008-12-08：NCdp200619361需要取消返回保证金要小于保证金的校验
			alChecker.add(new SecurityMoneyChecker(this));
		}
		return alChecker;
	}

	@Override
	protected IItemEditListener[] getBillItemEditListener() {
		return new IItemEditListener[] { 
				new AcceptSourcePKEditListener(this, AcceptVO.PK_SOURCE),
				new BackSecMoneyEditListener(this,AcceptVO.BACKSECMONEY),
				new ClearItemValueEditListener(this,AcceptVO.HOLDUNIT,AcceptVO.HOLDERACC),
				new UnitEditListener(this,AcceptVO.HOLDUNIT,AcceptVO.HOLDERACCNAME,AcceptVO.HOLDERACC,AcceptVO.HOLDUNITNAME),
				//new ReceiveAccbankEditListener(this,AcceptVO.HOLDERACC,AcceptVO.HOLDERACCNAME,AcceptVO.HOLDERACCODE),
				new YFBEditListerner(this, AcceptVO.PK_CURR,AcceptVO.PK_CURR, AcceptVO.MONEYY, AcceptVO.MONEYF ,AcceptVO.MONEYB,AcceptVO.FRATE,AcceptVO.BRATE),
				new YFBEditListerner(this, AcceptVO.PK_CURR,AcceptVO.PK_CURR, AcceptVO.SECURITYMONEY),
				new YFBEditListerner(this, AcceptVO.PK_CURR,AcceptVO.PK_CURR, AcceptVO.BACKSECMONEY),
				new YFBEditListerner(this, AcceptVO.PK_CURR,AcceptVO.PK_CURR, AcceptVO.BILLMONEYY),
				new YFBEditListerner(this, AcceptVO.MONEYY,AcceptVO.PK_CURR, AcceptVO.MONEYY, AcceptVO.MONEYF ,AcceptVO.MONEYB,AcceptVO.FRATE,AcceptVO.BRATE),
				new YFBEditListerner(this, AcceptVO.BRATE,AcceptVO.PK_CURR, AcceptVO.MONEYY, AcceptVO.MONEYF ,AcceptVO.MONEYB,AcceptVO.FRATE,AcceptVO.BRATE),
				new YFBEditListerner(this, AcceptVO.FRATE,AcceptVO.PK_CURR, AcceptVO.MONEYY, AcceptVO.MONEYF ,AcceptVO.MONEYB,AcceptVO.FRATE,AcceptVO.BRATE),
				
				new YFBEditListerner(this, AcceptVO.PK_CURR,AcceptVO.PK_CURR, AcceptVO.SECURITYMONEY, AcceptVO.SECURITYMONEYF ,AcceptVO.SECURITYMONEYB,AcceptVO.FRATE,AcceptVO.BRATE),
				new YFBEditListerner(this, AcceptVO.SECURITYMONEY,AcceptVO.PK_CURR, AcceptVO.SECURITYMONEY, AcceptVO.SECURITYMONEYF ,AcceptVO.SECURITYMONEYB,AcceptVO.FRATE,AcceptVO.BRATE),
				new YFBEditListerner(this, AcceptVO.SECURITYMONEYB,AcceptVO.PK_CURR, AcceptVO.SECURITYMONEY, AcceptVO.SECURITYMONEYF ,AcceptVO.SECURITYMONEYB,AcceptVO.FRATE,AcceptVO.BRATE),
				new YFBEditListerner(this, AcceptVO.SECURITYMONEYF,AcceptVO.PK_CURR, AcceptVO.SECURITYMONEY, AcceptVO.SECURITYMONEYF ,AcceptVO.SECURITYMONEYB,AcceptVO.FRATE,AcceptVO.BRATE),
				
				new YFBEditListerner(this, AcceptVO.PK_CURR,AcceptVO.PK_CURR, AcceptVO.BACKSECMONEY, AcceptVO.BACKSECMONEYF ,AcceptVO.BACKSECMONEYB,AcceptVO.FRATE,AcceptVO.BRATE),
				new YFBEditListerner(this, AcceptVO.BACKSECMONEY,AcceptVO.PK_CURR, AcceptVO.BACKSECMONEY, AcceptVO.BACKSECMONEYF ,AcceptVO.BACKSECMONEYB,AcceptVO.FRATE,AcceptVO.BRATE),
				new YFBEditListerner(this, AcceptVO.BACKSECMONEYB,AcceptVO.PK_CURR, AcceptVO.BACKSECMONEY, AcceptVO.BACKSECMONEYF ,AcceptVO.BACKSECMONEYB,AcceptVO.FRATE,AcceptVO.BRATE),
				new YFBEditListerner(this, AcceptVO.BACKSECMONEYF,AcceptVO.PK_CURR, AcceptVO.BACKSECMONEY, AcceptVO.BACKSECMONEYF ,AcceptVO.BACKSECMONEYB,AcceptVO.FRATE,AcceptVO.BRATE)
				
				
		};
	}

	@Override
	protected void initSelfData() {
		super.initSelfData();
		initRefPane();
		initCardThMark();
	}

	private void initComBox() {
		DefaultConstEnum[] vbillstatus = nc.ui.fbm.pub.ComBoxUtil.getBillStatus(getUIControl().getBillType());
		getBillCardWrapper().initHeadComboBox(AcceptVO.VBILLSTATUS,vbillstatus, false);
		getBillListWrapper().initHeadComboBox(AcceptVO.VBILLSTATUS,vbillstatus, false);
		
		DefaultConstEnum[] acceptancetype;
		try {
			acceptancetype = nc.ui.fbm.pub.ComBoxUtil.getAcceptanceType();
			getBillCardWrapper().initHeadComboBox(AcceptVO.FBMBILLTYPE, acceptancetype, false);
			getBillListWrapper().initHeadComboBox(AcceptVO.FBMBILLTYPE, acceptancetype, false);
		} catch (BusinessException e) {
			showErrorMessage(e.getMessage());
		}
		
		DefaultConstEnum[] ccType = nc.ui.fbm.pub.ComBoxUtil.getCCType();
		getBillCardWrapper().initHeadComboBox(RegisterVO.CCTYPE, ccType, false);
		getBillListWrapper().initHeadComboBox(RegisterVO.CCTYPE, ccType, false);

	}

	private void initRefPane() {
		UIRefPane sourcePKRef = (UIRefPane) getBillCardPanel().getHeadItem(AcceptVO.PK_SOURCE).getComponent();
		if(sourcePKRef.getRefModel() instanceof AbstractTMRefModel){
			AcceptSourcePKRefModelFilter filter = new AcceptSourcePKRefModelFilter(this,getBillCardPanel().getHeadItem(AcceptVO.PK_SOURCE));
			((AbstractTMRefModel)sourcePKRef.getRefModel()).addSqlFilter(filter);
		}
		
		BillItem holderaccItem = getBillCardPanel().getHeadItem(AcceptVO.HOLDERACC);
		UIRefPane holderaccRefPane = (UIRefPane) holderaccItem.getComponent();
//		holderaccRefPane.setAutoCheck(false);
		AbstractRefModel holdaccModel=(holderaccRefPane).getRefModel();
		if (holdaccModel instanceof AbstractTMRefModel) {
//			HolderaccRefModelFilter filter=new HolderaccRefModelFilter(holderaccItem,this,AcceptVO.HOLDUNIT,AcceptVO.PK_CURR);
//			((AbstractTMRefModel)holdaccModel).addSqlFilter(filter);
			
			PayAccbankRefModelFilter filter=new PayAccbankRefModelFilter(holderaccItem,this,AcceptVO.HOLDUNIT);
			((AbstractTMRefModel)holdaccModel).addSqlFilter(filter);
			CurrtypeBankaccFilter filter1 = new CurrtypeBankaccFilter(this,RegisterVO.PK_CURR);
			((AbstractTMRefModel)holdaccModel).addSqlFilter(filter1);
		}
		
		BillItem holderunitItem = getBillCardPanel().getHeadItem(AcceptVO.HOLDUNIT);
		UIRefPane holderunitRefPane = (UIRefPane) holderunitItem.getComponent();
//		holderunitRefPane.setAutoCheck(false);
		AbstractRefModel holderunitModel=(holderunitRefPane).getRefModel();
		if(holderunitModel instanceof AbstractTMRefModel){
			HandInRefModelFilter filter = new HandInRefModelFilter(holderunitItem);
			CusbasDocWithCorpRefFilter filter1 = new CusbasDocWithCorpRefFilter(this,AcceptVO.HOLDUNIT);
			((AbstractTMRefModel) holderunitModel).addSqlFilter(filter);
			((AbstractTMRefModel) holderunitModel).addSqlFilter(filter1);
			HoldUnitRefFilter filter2 = new HoldUnitRefFilter(this);
			((AbstractTMRefModel) holderunitModel).addSqlFilter(filter2);
			
		}
		
		AbstractRefModel backaccModel=((UIRefPane) getBillCardPanel().getHeadItem(AcceptVO.BACKSECACCOUNT).getComponent()).getRefModel();
		backaccModel.setMatchPkWithWherePart(true);
		if (backaccModel instanceof AbstractTMRefModel) {
			CurrCorpBankaccFilter filter = new CurrCorpBankaccFilter();
			((AbstractTMRefModel)backaccModel).addSqlFilter(filter);
			CurrtypeBankaccFilter filter1 = new CurrtypeBankaccFilter(this,RegisterVO.PK_CURR);
			((AbstractTMRefModel)backaccModel).addSqlFilter(filter1);
		}
		
		((UIRefPane)getBillCardPanel().getHeadItem(AcceptVO.PK_SOURCE).getComponent()).setCacheEnabled(false);
	}

	@Override
	public void setDefaultData() throws Exception {
		getBillCardPanel().getHeadItem(AcceptVO.VBILLSTATUS).setValue(
				IBillStatus.FREE);
		getBillCardPanel().getHeadItem(AcceptVO.PK_BILLTYPECODE).setValue(
				FbmBusConstant.BILLTYPE_BILLPAY);
		getBillCardPanel().getTailItem(AcceptVO.VOPERATORID).setValue(
				_getOperator());
		getBillCardPanel().getTailItem(AcceptVO.DOPERATEDATE).setValue(
				_getDate());
		getBillCardPanel().getHeadItem(AcceptVO.PK_CORP).setValue(
				_getCorp().getPrimaryKey());
	}

	@Override
	protected AbstractManageController createController() {
		if (m_ctrl == null)
			m_ctrl = new AcceptBillController();
		return m_ctrl;
	}

	@Override
	protected ManageEventHandler createEventHandler() {
		if (m_handler == null)
			m_handler = new AcceptBillEventHandler(this, createController());
		return m_handler;
	}

	@Override
	public void doAddAction(ILinkAddData adddata) {
		super.doAddAction(adddata);
		BillEditEvent e = new BillEditEvent(getBillCardPanel().getHeadItem(AcceptVO.PK_SOURCE),-1,-1);
		new AcceptSourcePKEditListener(this,AcceptVO.PK_SOURCE).responseEditEvent(e);
	}

	protected void initCardThMark() {
			BillItem[] decimalItems = new BillItem[] {
					getBillCardPanel().getHeadItem(AcceptVO.MONEYY),
					getBillCardPanel().getHeadItem(AcceptVO.MONEYB),
					getBillCardPanel().getHeadItem(AcceptVO.MONEYF),
					getBillCardPanel().getHeadItem(AcceptVO.BRATE),
					getBillCardPanel().getHeadItem(AcceptVO.FRATE),
					getBillCardPanel().getHeadItem(AcceptVO.SECURITYMONEY),
					getBillCardPanel().getHeadItem(AcceptVO.BACKSECMONEY),
					getBillCardPanel().getHeadItem(AcceptVO.BILLMONEYY)
					};
			getBillCardPanel().setHeadTailShowThMark(decimalItems, true);
			getBillListPanel().getParentListPanel().setShowThMark(true);
	}
	
	/**
	 * <p>
	 * 设置返回保证金是否可以编辑
	 * <p>
	 * 作者：lpf 
	 * 日期：2007-9-7
	 * 
	 * @param panel
	 * @param pk_source
	 */
	public void filterbacksecAccount(String pk_source) {
		BillCardPanel panel = getBillCardPanel();
		String impawnmode = (String) panel.getHeadItem(AcceptVO.IMPAWNMODE).getValueObject();
		if (CommonUtil.isNull(impawnmode)) {
			UIRefPane refpane = (UIRefPane) panel.getHeadItem(AcceptVO.PK_SOURCE).getComponent();
			refpane.setPK(pk_source);
			impawnmode = (String) refpane.getRefValue(AcceptVO.IMPAWNMODE);
		}
		if (impawnmode.equalsIgnoreCase(FbmBusConstant.ASSURETYPE_BAIL)) {
			setHeadItemEditable(new String[] { AcceptVO.BACKSECACCOUNT,	AcceptVO.BACKSECMONEY }, true);
		} else {
			setHeadItemEditable(new String[] { AcceptVO.BACKSECACCOUNT,AcceptVO.BACKSECMONEY }, false);
			panel.getHeadItem(AcceptVO.BACKSECACCOUNT).setValue(null);
			panel.getHeadItem(AcceptVO.BACKSECMONEY).setValue(null);
		}
	}
	
	protected void initBusinessButton() {
        addPrivateBusinessButton(FBMButtonFactory.getButtonVO(IFBMButton.FBM_VOUCHER));
        addPrivateBusinessButton(FBMButtonFactory.getButtonVO(IFBMButton.FBM_CANCELVOUCHER));
        addPrivateBusinessButton(FBMButtonFactory.getButtonVO(IFBMButton.FBM_QUERYVOUCHER));
    }
	
	private Observer getStatusEngine() {
		return new nc.ui.fbm.accept.status.AcceptStatusEngine(this);
	}
	
	/*
	 * 在更新表头单条数据的时候将reftakenprocessor中的缓存清空 (non-Javadoc)
	 * 
	 * @see nc.ui.trade.manage.BillManageUI#updateListVo()
	 */
	protected void updateListVo() throws java.lang.Exception {
		if (getRefTakenProccessor() != null
				&& getBufferData().getCurrentVO() != null) {
			getRefTakenProccessor().cleanCache();
		}
		super.updateListVo();
	}

}
