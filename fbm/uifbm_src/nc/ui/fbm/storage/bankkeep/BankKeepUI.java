package nc.ui.fbm.storage.bankkeep;

import java.util.ArrayList;

import nc.bs.logging.Logger;
import nc.itf.cdm.util.CommonUtil;
import nc.ui.fac.account.pub.IRefTakenProccessor;
import nc.ui.fbm.pub.FBMClientInfo;
import nc.ui.fbm.pub.FBMManageUI;
import nc.ui.fbm.pub.IItemEditListener;
import nc.ui.fbm.pub.IUIChecker;
import nc.ui.fbm.pub.ItemNotNullChecker;
import nc.ui.fbm.pub.reffilter.BankWithoutSettleCenterFilter;
import nc.ui.fbm.storage.StorageRefTakenProcessor;
import nc.ui.fbm.storage.bankkeep.reffilter.GatherRefModelFilter;
import nc.ui.fbm.storage.checker.BodyItemUniqueChecker;
import nc.ui.fbm.storage.checker.BodyNotEmptyChecker;
import nc.ui.fbm.storage.checker.KeepInviceDateChecker;
import nc.ui.fbm.storage.listener.InnerKeepSourceEditListener;
import nc.ui.pub.beans.UIRefPane;
import nc.ui.pub.beans.constenum.DefaultConstEnum;
import nc.ui.pub.bill.IBillItem;
import nc.ui.pub.linkoperate.ILinkAddData;
import nc.ui.tm.framework.billtemplet.PkReturnableBillCellEditor;
import nc.ui.trade.bill.AbstractManageController;
import nc.ui.trade.button.IBillButton;
import nc.ui.trade.manage.ManageEventHandler;
import nc.vo.fbm.pub.constant.FbmBusConstant;
import nc.vo.fbm.storage.StorageBVO;
import nc.vo.fbm.storage.StoragePowerVO;
import nc.vo.fbm.storage.StorageVO;
import nc.vo.pub.BusinessException;
import nc.vo.trade.pub.BillStatus;
import nc.vo.trade.pub.IBillStatus;

/**
 * <p>
 * 银行存放单UI
 * <p>
 * 创建人：lpf <b>日期：2007-8-20
 *
 */
public class BankKeepUI extends FBMManageUI {
	private BankKeepController m_ctrl = null;
	private BankKeepEventHandler m_handler = null;

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/**
	 *
	 */
	public BankKeepUI() {
		// TODO Auto-generated constructor stub
		super();
	}

	/**
	 * @param useBillSource
	 */
	public BankKeepUI(Boolean useBillSource) {
		super(useBillSource);
	}

	/**
	 * @param pk_corp
	 * @param pk_billType
	 * @param pk_busitype
	 * @param operater
	 * @param billId
	 */
	public BankKeepUI(String pk_corp, String pk_billType, String pk_busitype,
			String operater, String billId) {
		super(pk_corp, pk_billType, pk_busitype, operater, billId);
	}

	@Override
	protected AbstractManageController createController() {
		// TODO Auto-generated method stub
		if (m_ctrl == null)
			m_ctrl = new BankKeepController();
		return m_ctrl;
	}

	@Override
	protected ManageEventHandler createEventHandler() {
		// TODO Auto-generated method stub
		if (m_handler == null)
			m_handler = new BankKeepEventHandler(this, createController());
		return m_handler;
	}

	@Override
	protected void initSelfData() {
		super.initSelfData();
		initRefPane();
		initCardThMark();
		initComBox();
		initButtonVisible();
		initBillCellEditor();
	}

	private void initBillCellEditor() {
		int colomn = getBillCardPanel().getBodyColByKey(StorageBVO.PK_SOURCE);
		UIRefPane refpane = (UIRefPane) getBillCardPanel().getBodyItem(StorageBVO.PK_SOURCE).getComponent();
		refpane.setCacheEnabled(false);
		getBillCardPanel().getBillTable().getColumnModel().getColumn(colomn).setCellEditor(new PkReturnableBillCellEditor(refpane));
	}


	private void initComBox() {
		try{
			DefaultConstEnum[] acceptancetype = nc.ui.fbm.pub.ComBoxUtil
					.getAcceptanceType();
			getBillCardWrapper().initBodyComboBox(StorageBVO.ACCEPTANCETYPE,
					acceptancetype, false);
			getBillListWrapper().initBodyComboBox(StorageBVO.ACCEPTANCETYPE,
					acceptancetype, false);
		} catch (BusinessException e) {
			showErrorMessage(e.getMessage());
		}
		DefaultConstEnum[] keeptype = nc.ui.fbm.pub.ComBoxUtil
				.getStorageKeepType();
		getBillCardWrapper().initHeadComboBox(StorageVO.INPUTTYPE, keeptype,
				false);
		getBillListWrapper().initHeadComboBox(StorageVO.INPUTTYPE, keeptype,
				false);

		getBillCardWrapper().initHeadComboBox(StorageVO.VBILLSTATUS,
				new BillStatus().strStateRemark, true);
		getBillListWrapper().initHeadComboBox(StorageVO.VBILLSTATUS,
				new BillStatus().strStateRemark, true);

		DefaultConstEnum[] fBillStatus = nc.ui.fbm.pub.ComBoxUtil
				.getFBillStatus();
		getBillCardWrapper().initBodyComboBox(StorageBVO.ENDSTATUS,
				fBillStatus, false);
		getBillListWrapper().initBodyComboBox(StorageBVO.ENDSTATUS,
				fBillStatus, false);
	}

	protected void initRefPane(){
		((UIRefPane) getBillCardPanel().getBodyItem(StorageBVO.PK_SOURCE).getComponent()).setMultiSelectedEnabled(true);

		GatherRefModelFilter gFilter = new GatherRefModelFilter(this);
		addSqlFilter(false, StorageBVO.PK_SOURCE, gFilter);
		BankWithoutSettleCenterFilter bFilter = new BankWithoutSettleCenterFilter(this);
		addSqlFilter(true, StorageVO.KEEPUNIT, bFilter);

	}

	@Override
	public void setDefaultData() throws Exception {
		getBillCardPanel().getHeadItem(StorageVO.VBILLSTATUS).setValue(
				IBillStatus.FREE);
		getBillCardPanel().getHeadItem(StorageVO.PK_BILLTYPECODE).setValue(
				FbmBusConstant.BILLTYPE_BANKKEEP);
		getBillCardPanel().getTailItem(StorageVO.VOPERATORID).setValue(
				_getOperator());
		getBillCardPanel().getTailItem(StorageVO.DOPERATEDATE).setValue(
				_getDate());
		getBillCardPanel().getHeadItem(StorageVO.PK_CORP).setValue(
				_getCorp().getPk_corp());
	}

	@Override
	protected IRefTakenProccessor createRefTakenProccessor() {
		return new StorageRefTakenProcessor(getBillListPanel(),
				getBillCardPanel());
	}

	@Override
	protected ArrayList<IUIChecker> createValidator(int btnid) {
		ArrayList<IUIChecker> alChecker = null;
		switch (btnid) {
		case IBillButton.Save:
			alChecker = new ArrayList<IUIChecker>();
			alChecker.add(new ItemNotNullChecker(this));
			alChecker.add(new BodyNotEmptyChecker(this));
			alChecker.add(new BodyItemUniqueChecker(this,
					new String[] { StorageBVO.PK_SOURCE },
					new String[] { nc.ui.ml.NCLangRes.getInstance().getStrByID("36201010","UPT36201010-000014")/* @res"票据编号"*/ }));
			alChecker.add(new KeepInviceDateChecker(this,StorageVO.INPUTDATE));
		}
		return alChecker;
	}

	@Override
	public void doAddAction(ILinkAddData adddata) {
		super.doAddAction(adddata);
		getBillCardPanel().getBodyItem(StorageBVO.PK_SOURCE).setEnabled(false);
	}

	protected void initCardThMark() {
//		BillItem[] decimalItems = new BillItem[] {getBillCardPanel().getBodyItem(StorageBVO.MONEYY)};
		getBillCardPanel().setBodyShowThMark(true);
		getBillListPanel().getParentListPanel().setShowThMark(true);
	}

	@Override
	protected IItemEditListener[] getBillItemEditListener() {
		return new IItemEditListener[]{
			new InnerKeepSourceEditListener(this,StorageBVO.PK_SOURCE,IBillItem.BODY)
		};
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
			return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add","UPP3620ADD-000120")/*@res "当前公司未设置对应客商,无法进行银行存放业务"*/;
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