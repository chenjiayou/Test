package nc.ui.fbm.endore;

import java.util.ArrayList;
import java.util.Observer;

import nc.bs.logging.Logger;
import nc.itf.cdm.util.CommonUtil;
import nc.ui.bd.ref.AbstractRefModel;
import nc.ui.fac.account.pub.IRefTakenProccessor;
import nc.ui.fbm.discount.OpBillTypeChangeListener;
import nc.ui.fbm.endore.checker.CopyButtonChecker;
import nc.ui.fbm.endore.checker.EndoreMoneyChecker;
import nc.ui.fbm.endore.checker.EndoreSerNotEqSeeChecker;
import nc.ui.fbm.endore.listener.ConfigBZEditListener;
import nc.ui.fbm.endore.listener.EndorePKSourceListener;
import nc.ui.fbm.endore.status.EndoreStatusEngine;
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
import nc.ui.tm.framework.ref.AbstractTMRefModel;
import nc.ui.tm.framework.util.ClientInfo;
import nc.ui.trade.bill.AbstractManageController;
import nc.ui.trade.button.IBillButton;
import nc.ui.trade.manage.ManageEventHandler;
import nc.vo.fbm.discount.DiscountVO;
import nc.vo.fbm.endore.EndoreVO;
import nc.vo.fbm.pub.constant.FbmBusConstant;
import nc.vo.fbm.storage.StoragePowerVO;
import nc.vo.pub.BusinessException;
import nc.vo.trade.pub.IBillStatus;


public class EndoreUI extends FBMManageUI {

	private static final long serialVersionUID = 1L;

	private Observer StatusEngine = getStatusEngine();

	public EndoreUI()
	{
		super();
		initCombox();
		getBufferData().deleteObserver(this);
		getBufferData().addObserver(StatusEngine);
		getBufferData().addObserver(this);
	}


	public EndoreUI(Boolean useBillSource) {
		super(useBillSource);
	}

	public EndoreUI(String pk_corp, String pk_billType, String pk_busitype,
			String operater, String billId) {
		super(pk_corp, pk_billType, pk_busitype, operater, billId);
	}


	private Observer getStatusEngine() {
		return new EndoreStatusEngine(this);
	}

	@Override
	public String getTitle() {
		return nc.ui.ml.NCLangRes.getInstance().getStrByID("36201037","UPT36201037-000010")/*@res "背书办理"*/;
	}

	@Override
	protected AbstractManageController createController() {
		return new EndoreCTL();
	}

	@Override
	protected void initSelfData() {
		super.initSelfData();
		initRefPaneFilter();
		DefaultConstEnum[] syscode = nc.ui.fbm.pub.ComBoxUtil.getSyscode();
		getBillCardWrapper().initHeadComboBox(EndoreVO.SYSCODE,syscode, false);
		getBillListWrapper().initHeadComboBox(EndoreVO.SYSCODE,syscode, false);
		
		((UIRefPane)getBillCardPanel().getHeadItem(EndoreVO.PK_SOURCE).getComponent()).setCacheEnabled(false);
	}
	/**
	 * 根据票据类型过滤选票
	 */
	private void initRefPaneFilter() {
		UIRefPane repane = (UIRefPane) getBillCardPanel().getHeadItem(
				EndoreVO.PK_SOURCE).getComponent();
		// 过滤内部账户
		AbstractRefModel refdes = repane.getRefModel();
		if (AbstractRefModel.class.isAssignableFrom(refdes.getClass())) {
			BillSelectOpTypeFilterForEndore a_filter = new BillSelectOpTypeFilterForEndore(
					getBillCardPanel().getHeadItem(EndoreVO.OPBILLTYPE));// 根据票据类型进行过滤
			((AbstractTMRefModel) refdes).addSqlFilter(a_filter);

			//添加票据为'已收票'过滤
			EndoreFilter endoreFilter = new EndoreFilter(getBillCardPanel().getHeadItem(EndoreVO.PK_SOURCE));
			((AbstractTMRefModel) refdes).addSqlFilter(endoreFilter);
		}

		// 被背书单位过滤客商信息:判断背书办理的票据类别，如果为统管，则只显示外部客商信息，否则显示内部＋外部客商信息。
		repane = (UIRefPane) getBillCardPanel().getHeadItem(EndoreVO.ENDORSEE)
				.getComponent();

		refdes = repane.getRefModel();

		if (AbstractRefModel.class.isAssignableFrom(refdes.getClass())) {

			EndoreMemberFilter fit = new EndoreMemberFilter(getBillCardPanel()
					.getHeadItem(EndoreVO.OPBILLTYPE));

			((AbstractTMRefModel) refdes).addSqlFilter(fit);

		}


	}


	@Override
	public void setDefaultData() throws Exception {
		super.setDefaultData();
		((UIComboBox) getBillCardPanel().getHeadItem(EndoreVO.SYSCODE)
				.getComponent()).setSelectedIndex(0);
		getBillCardPanel().getHeadItem(EndoreVO.PK_CORP).setValue(
				_getCorp().getPrimaryKey());
		getBillCardPanel().getTailItem(EndoreVO.VOPERATORID).setValue(
				_getOperator());
		getBillCardPanel().getTailItem(EndoreVO.DOPERATEDATE).setValue(
				_getDate());

		getBillCardPanel().getHeadItem(EndoreVO.PK_BILLTYPECODE).setValue(
				FbmBusConstant.BILLTYPE_ENDORE);

		getBillCardPanel().getHeadItem(EndoreVO.VBILLSTATUS).setValue(
				IBillStatus.FREE);
		String pk_cubasdoc = ClientInfo.getCommonCurCorpCubasdoc();
		getBillCardPanel().getHeadItem(EndoreVO.ENDORSER).setValue(
				pk_cubasdoc);
		// 设置票据种类，私有/统管
		((UIComboBox) getBillCardPanel().getHeadItem(DiscountVO.OPBILLTYPE)
				.getComponent()).setSelectedIndex(-1);

		if (!FBMClientInfo.isSettleCenter()) {// 如果不为中心,票据种类选择私有并不可编辑
			((UIComboBox) getBillCardPanel().getHeadItem(DiscountVO.OPBILLTYPE)
					.getComponent()).setSelectedIndex(0);
			((UIComboBox) getBillCardPanel().getHeadItem(DiscountVO.OPBILLTYPE)
					.getComponent()).setEnabled(false);
		}

	}

	/**
	 * <p>
	 *  UI　与 Handler类的关联
	 * <p>
	 */
	@Override
	protected ManageEventHandler createEventHandler() {
		return new EndoreHandler(this, getUIControl());
	}
	/**
	 * <p
	 * 初始化票据状态下拉框的值。
	 * <p>
	 * @author wangyg
	 */
	public void initCombox()
	{
		// 初始票据类型：私有，统管
		DefaultConstEnum[] opBillType = nc.ui.fbm.pub.ComBoxUtil
				.getOpBillType();
		getBillCardWrapper().initHeadComboBox(DiscountVO.OPBILLTYPE,
				opBillType, false);
		getBillListWrapper().initHeadComboBox(DiscountVO.OPBILLTYPE,
				opBillType, false);
		try{
		// 票据类型
			DefaultConstEnum[] acceptancetype = nc.ui.fbm.pub.ComBoxUtil
					.getAcceptanceType();
			// 初始化票据类型下拉框
			getBillCardWrapper().initHeadComboBox(EndoreVO.FBMBILLTYPE,
					acceptancetype, false);
			getBillListWrapper().initHeadComboBox(EndoreVO.FBMBILLTYPE,
					acceptancetype, false);
		} catch (BusinessException e) {
			showErrorMessage(e.getMessage());
		}
		// 单据状态
		DefaultConstEnum[] statusType = nc.ui.fbm.pub.ComBoxUtil.getEndoreStatus();



		// 初始化单据状态下拉框
		getBillCardWrapper().initHeadComboBox(EndoreVO.VBILLSTATUS, statusType,
				false);
		getBillListWrapper().initHeadComboBox(EndoreVO.VBILLSTATUS, statusType,
				false);
	}

	/**
	 * 背书金额前台校验
	 */
	protected ArrayList<IUIChecker> createValidator(int btnid) {
		ArrayList<IUIChecker> alChecker = null;
		switch (btnid) {
		case IBillButton.Save:
			alChecker = new ArrayList<IUIChecker>();
			alChecker.add(new EndoreMoneyChecker(this));
			alChecker.add(new EndoreSerNotEqSeeChecker(this));
			break;
		case IBillButton.Copy:
			alChecker = new ArrayList<IUIChecker>();
			alChecker.add(new CopyButtonChecker(this));
		}
		return alChecker;
	}
	@Override
	protected IRefTakenProccessor createRefTakenProccessor() {
		// TODO Auto-generated method stub
		return new EndoreRefTakenProccessor(getBillListPanel(),
				getBillCardPanel());
	}

	//Listener，监听票据类别，票据编号，被背书单位。
	@Override
	public IItemEditListener[] getBillItemEditListener() {
		return new IItemEditListener[] {

				//票据类别下拉框监听,清空票据编号与被背书单位
				new OpBillTypeChangeListener(this, EndoreVO.OPBILLTYPE),

				//票据编号变化清空被背书单位
				new EndorePKSourceListener(this, EndoreVO.PK_SOURCE),

				new ConfigBZEditListener(this, EndoreVO.PK_SOURCE),

				new YFBEditListerner(this, EndoreVO.PK_CURR, EndoreVO.PK_CURR,
						EndoreVO.MONEYY, EndoreVO.MONEYF, EndoreVO.MONEYB,
						EndoreVO.FRATE, EndoreVO.BRATE),
				new YFBEditListerner(this, EndoreVO.BRATE, EndoreVO.PK_CURR,
						EndoreVO.MONEYY, EndoreVO.MONEYF, EndoreVO.MONEYB,
						EndoreVO.FRATE, EndoreVO.BRATE),
				new YFBEditListerner(this, EndoreVO.FRATE, EndoreVO.PK_CURR,
						EndoreVO.MONEYY, EndoreVO.MONEYF, EndoreVO.MONEYB,
						EndoreVO.FRATE, EndoreVO.BRATE)
						};
	}


//	@Override
//	public void initQueryCondition(UIDialog qryClient) throws Exception {
//		// TODO Auto-generated method stub
//		super.initQueryCondition(qryClient);
//		AbstractTMRefModel refModel = new nc.ui.fbm.pub.refmodel.QueryUniStorBillRefModel();
//		UIRefPane refPane = new UIRefPane();
//		refPane.setRefModel(refModel);
//		QueryConditionClient queryDlg = (QueryConditionClient)qryClient;
//		queryDlg.setValueRef("fbm_endore.pk_source",refPane);
//	}

	@Override
	protected void initBusinessButton() {
		addPrivateBusinessButton(FBMButtonFactory
				.getButtonVO(IFBMButton.FBM_VOUCHER));
		addPrivateBusinessButton(FBMButtonFactory
				.getButtonVO(IFBMButton.FBM_CANCELVOUCHER));

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
			return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add","UPP3620ADD-000031")/*@res "当前公司未设置对应客商,无法进行背书办理业务"*/;
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

	
	public Object getUserObject() {
		// TODO Auto-generated method stub
		return super.getUserObject();
	}

	 private StoragePowerVO power;

}