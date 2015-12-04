package nc.ui.fbm.reckon;

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
import nc.ui.fbm.pub.YFBEditListerner;
import nc.ui.fbm.pub.reffilter.InnerAccRuleFilter;
import nc.ui.fbm.reckon.listener.CurrTypeEditListener;
import nc.ui.fbm.reckon.listener.ReckonDateEditListener;
import nc.ui.fbm.reckon.listener.ReckonUnitEditListener;
import nc.ui.fbm.reckon.reffilter.CurrTypeRefFilter;
import nc.ui.fbm.reckon.reffilter.InnerAccCurrTypeRefFilter;
import nc.ui.fbm.reckon.reffilter.ReckonDateRefFilter;
import nc.ui.fbm.reckon.reffilter.ReckonGatherRefModelFilter;
import nc.ui.fbm.reckon.reffilter.ReckonUnitRefFilter;
import nc.ui.fbm.reckon.reffilter.RefUnitForSettleUnitFilter;
import nc.ui.fbm.reckon.status.ReckonStatusEngine;
import nc.ui.fbm.storage.checker.BodyItemUniqueChecker;
import nc.ui.fbm.storage.checker.BodyNotEmptyChecker;
import nc.ui.fbm.storage.listener.InnerKeepSourceEditListener;
import nc.ui.pub.beans.UIRefPane;
import nc.ui.pub.beans.constenum.DefaultConstEnum;
import nc.ui.pub.bill.BillItem;
import nc.ui.pub.bill.IBillItem;
import nc.ui.tm.framework.billtemplet.PkReturnableBillCellEditor;
import nc.ui.tm.framework.ref.AbstractTMRefModel;
import nc.ui.tm.framework.ref.filter.BillItemRefModelFilter;
import nc.ui.tm.framework.util.CurrencyClientUtil;
import nc.ui.trade.bill.AbstractManageController;
import nc.ui.trade.button.IBillButton;
import nc.ui.trade.manage.ManageEventHandler;
import nc.vo.bd.settle.SettlecenterQueryUtil;
import nc.vo.fbm.pub.FBMPublicQry;
import nc.vo.fbm.pub.constant.FbmBusConstant;
import nc.vo.fbm.reckon.ReckonBVO;
import nc.vo.fbm.reckon.ReckonVO;
import nc.vo.fbm.register.RegisterVO;
import nc.vo.fbm.storage.StoragePowerVO;
import nc.vo.pub.BusinessException;
import nc.vo.trade.pub.IBillStatus;

/**
 * <p>
 * 调剂清算单UI
 * <p>
 * 创建人：bsrl <b>日期：2007-10-19
 *
 */
public class ReckonUI extends FBMManageUI implements nc.ui.glpub.IUiPanel{

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private ReckonController m_ctrl = null;
	private ReckonEventHandler m_handler = null;

	private nc.ui.glpub.IParent m_parent = null;
	private Observer StatusEngine = getStatusEngine();

	public nc.ui.glpub.IParent getm_parent() {
		return m_parent;
	}

	public void showMe(nc.ui.glpub.IParent parent) {
		// TODO 自动生成方法存根
		parent.getUiManager().removeAll();
		parent.getUiManager().add(this,this.getName());
		m_parent = parent;
	}

	/**
	 *
	 */
	public ReckonUI() {
		super();
		initComBox();
		getBufferData().deleteObserver(this);
		getBufferData().addObserver(StatusEngine);
		getBufferData().addObserver(this);
	}

	/**
	 * @param useBillSource
	 */
	public ReckonUI(Boolean useBillSource) {
		super(useBillSource);
	}

	/**
	 * @param pk_corp
	 * @param pk_billType
	 * @param pk_busitype
	 * @param operater
	 * @param billId
	 */
	public ReckonUI(String pk_corp, String pk_billType, String pk_busitype,
			String operater, String billId) {
		super(pk_corp, pk_billType, pk_busitype, operater, billId);
	}

	@Override
	protected AbstractManageController createController() {
		if (m_ctrl == null)
			m_ctrl = new ReckonController();
		return m_ctrl;
	}

	@Override
	protected ManageEventHandler createEventHandler() {
		if (m_handler == null)
			m_handler = new ReckonEventHandler(this, createController());
		return m_handler;
	}

	@Override
	protected void initSelfData() {
		super.initSelfData();
		initRefPane();
		initCardThMark();
		// initComBox();
		initBillCellEditor();
	}

	private void initBillCellEditor() {
		int colomn = getBillCardPanel().getBodyColByKey(ReckonBVO.PK_DETAIL);
		UIRefPane refpane = (UIRefPane) getBillCardPanel().getBodyItem(ReckonBVO.PK_DETAIL).getComponent();
		getBillCardPanel().getBillTable().getColumnModel().getColumn(colomn).setCellEditor(new PkReturnableBillCellEditor(refpane));
	}

	private void initComBox() {
		DefaultConstEnum[] statusList = nc.ui.fbm.pub.ComBoxUtil
				.getReliefStatus();
		getBillCardWrapper().initHeadComboBox(ReckonVO.VBILLSTATUS, statusList,
				false);
		getBillListWrapper().initHeadComboBox(ReckonVO.VBILLSTATUS, statusList,
				false);

		DefaultConstEnum[] reliefdirection = nc.ui.fbm.pub.ComBoxUtil
				.getReckonDirection();
		getBillCardWrapper().initBodyComboBox(ReckonBVO.DIRECTION,
				reliefdirection, false);
		getBillListWrapper().initBodyComboBox(ReckonBVO.DIRECTION,
				reliefdirection, false);

		DefaultConstEnum[] fbillstatus = nc.ui.fbm.pub.ComBoxUtil
				.getFBillStatus();
		getBillCardWrapper().initBodyComboBox(ReckonBVO.FBMBILLSTATUS,
				fbillstatus, false);
		getBillListWrapper().initBodyComboBox(ReckonBVO.FBMBILLSTATUS,
				fbillstatus, false);
	}

	protected void initRefPane() {
		((UIRefPane) getBillCardPanel().getBodyItem(ReckonBVO.PAYUNIT)
				.getComponent()).setAutoCheck(false);
		UIRefPane gatherPane = (UIRefPane) getBillCardPanel().getBodyItem(
				ReckonBVO.PK_DETAIL).getComponent();
		AbstractRefModel gathermdl = gatherPane.getRefModel();
		gatherPane.setMultiSelectedEnabled(true);

		// 根据清算单位过滤待清算票据
		if (AbstractTMRefModel.class.isAssignableFrom(gathermdl.getClass())) {
			CurrTypeRefFilter currtypefilter1 = new CurrTypeRefFilter(
					getBillCardPanel().getHeadItem(ReckonVO.PK_CURR));
			ReckonGatherRefModelFilter gatherfilter = new ReckonGatherRefModelFilter(
					getBillCardPanel().getHeadItem(ReckonVO.RECKONUNIT));
			// 根据清算日期过滤清算票据
			ReckonDateRefFilter reckondatefilter = new ReckonDateRefFilter(
					getBillCardPanel().getHeadItem(ReckonVO.DRECKONDATE));
			((AbstractTMRefModel) gathermdl).addSqlFilter(gatherfilter);
			((AbstractTMRefModel) gathermdl).addSqlFilter(reckondatefilter);
			((AbstractTMRefModel) gathermdl).addSqlFilter(currtypefilter1);
		}
		// 根据清算单位过滤清算账户
		AbstractRefModel reckoninacc = ((UIRefPane) getBillCardPanel()
				.getHeadItem(ReckonVO.INACC).getComponent())
				.getRefModel();
		if (reckoninacc instanceof AbstractTMRefModel) {
			ReckonUnitRefFilter reckonunitfilter = new ReckonUnitRefFilter(
					getBillCardPanel().getHeadItem(ReckonVO.RECKONUNIT));
			InnerAccCurrTypeRefFilter currtypefilter2 = new InnerAccCurrTypeRefFilter(
					getBillCardPanel().getHeadItem(ReckonVO.PK_CURR));
			((AbstractTMRefModel) reckoninacc).addSqlFilter(reckonunitfilter);
			((AbstractTMRefModel) reckoninacc).addSqlFilter(currtypefilter2);
		}
		AbstractRefModel reckonoutacc = ((UIRefPane) getBillCardPanel()
				.getHeadItem(ReckonVO.OUTACC).getComponent())
				.getRefModel();
		if (reckonoutacc instanceof AbstractTMRefModel) {
			ReckonUnitRefFilter reckonunitfilter = new ReckonUnitRefFilter(
					getBillCardPanel().getHeadItem(ReckonVO.RECKONUNIT));
			InnerAccCurrTypeRefFilter currtypefilter2 = new InnerAccCurrTypeRefFilter(
					getBillCardPanel().getHeadItem(ReckonVO.PK_CURR));
			((AbstractTMRefModel) reckonoutacc).addSqlFilter(reckonunitfilter);
			((AbstractTMRefModel) reckonoutacc).addSqlFilter(currtypefilter2);
		}

		AbstractRefModel reckonunit = ((UIRefPane) getBillCardPanel()
				.getHeadItem(ReckonVO.RECKONUNIT).getComponent()).getRefModel();
		if(reckonunit instanceof AbstractTMRefModel){
			RefUnitForSettleUnitFilter filter = new RefUnitForSettleUnitFilter();
			((AbstractTMRefModel)reckonunit).addSqlFilter(filter);
		}

		BillItemRefModelFilter filter = new BillItemRefModelFilter(
				getBillCardPanel().getHeadItem(ReckonVO.PK_RECKON)) {
			protected String getSelfFilterString() {
				if (CommonUtil.isNull((String) getBillItem().getValueObject())) {
					return " not exists (select 1 from fbm_reckon_b where fbm_reckon_b.pk_detail = gathertable.pk_detail and isnull(fbm_reckon_b.dr,0)=0) ";
				} else {
					String pk_reckon = (String) getBillItem().getValueObject();
					return " not exists (select 1 from fbm_reckon_b right outer join fbm_reckon"
							+ " on fbm_reckon_b.pk_reckon = fbm_reckon.pk_reckon "
							+ " where fbm_reckon_b.pk_detail = gathertable.pk_detail and isnull(fbm_reckon_b.dr,0)=0 "
							+ " and fbm_reckon.pk_reckon <> '"
							+ pk_reckon
							+ "')";
				}
			}
		};

		if (AbstractTMRefModel.class.isAssignableFrom(gathermdl.getClass())) {
			((AbstractTMRefModel) gathermdl).addSqlFilter(filter);
		}

		InnerAccRuleFilter inaccRuleFilter = new InnerAccRuleFilter(FbmBusConstant.BILLTYPE_LIQUIDATE,FbmBusConstant.ACC_IN);
		addSqlFilter(true, ReckonVO.INACC, inaccRuleFilter);
		InnerAccRuleFilter outaccRuleFilter = new InnerAccRuleFilter(FbmBusConstant.BILLTYPE_LIQUIDATE,FbmBusConstant.ACC_OUT);
		addSqlFilter(true, ReckonVO.OUTACC, outaccRuleFilter);
		
		UIRefPane srcPane = (UIRefPane) getBillCardPanel().getBodyItem(
				ReckonBVO.PK_SOURCE).getComponent();
		srcPane.setCacheEnabled(false);
	}

	@Override
	public void setDefaultData() throws Exception {
		getBillCardPanel().getHeadItem(ReckonVO.VBILLSTATUS).setValue(
				IBillStatus.FREE);
		getBillCardPanel().getHeadItem(ReckonVO.PK_BILLTYPECODE).setValue(
				FbmBusConstant.BILLTYPE_LIQUIDATE);
		getBillCardPanel().getTailItem(ReckonVO.VOPERATORID).setValue(
				_getOperator());
		getBillCardPanel().getTailItem(ReckonVO.DOPERATEDATE).setValue(
				_getDate());
		getBillCardPanel().getHeadItem(ReckonVO.PK_CORP).setValue(
				_getCorp().getPk_corp());
		getBillCardPanel().getHeadItem(ReckonVO.RECKONCENTER).setValue(
				SettlecenterQueryUtil.getInstance().getCenterVOByCorpID(_getCorp().getPk_corp()).getPrimaryKey());
		//NCdp201040610 新增收票及开票，默认币种应取本位币，当前为空
		CurrencyClientUtil currencyClientUtil = new CurrencyClientUtil();
		getBillCardPanel().getHeadItem(ReckonVO.PK_CURR).setValue(currencyClientUtil.getLocalCurrPK());
	}

	@Override
	protected IRefTakenProccessor createRefTakenProccessor() {
		return new ReckonRefTakenProcessor(getBillListPanel(),
				getBillCardPanel());
	}

	/**
	 * 注册需要格式化的字段
	 */
	protected void initCardThMark() {
		BillItem[] decimalItems = new BillItem[] { getBillCardPanel()
				.getHeadItem(ReckonVO.RECKONMONEYSUM) };// 清算金额汇总

		getBillCardPanel().setHeadTailShowThMark(decimalItems, true);
		getBillListPanel().getParentListPanel().setShowThMark(true);
	}

	protected IItemEditListener[] getBillItemEditListener() {
		return new IItemEditListener[] {
				new InnerKeepSourceEditListener(this, ReckonBVO.PK_DETAIL,
						IBillItem.BODY),
				new ReckonUnitEditListener(this, ReckonVO.RECKONUNIT,
						IBillItem.HEAD),
				new ReckonDateEditListener(this, ReckonVO.DRECKONDATE,
						IBillItem.HEAD),
				new CurrTypeEditListener(this, ReckonVO.PK_CURR, IBillItem.HEAD),

				new YFBEditListerner(this, ReckonVO.RECKONMONEYSUM,
						ReckonVO.PK_CURR, ReckonVO.RECKONMONEYSUM) };
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
					new String[] { ReckonBVO.PK_DETAIL },
					new String[] { nc.ui.ml.NCLangRes.getInstance().getStrByID("36201050","UPT36201050-000032")/* @res"票据编号"*/ }));
		}
		return alChecker;
	}

 

	protected void initBusinessButton() {
		addPrivateBusinessButton(FBMButtonFactory.getButtonVO(IFBMButton.FBM_VOUCHER));
		addPrivateBusinessButton(FBMButtonFactory.getButtonVO(IFBMButton.FBM_CANCELVOUCHER));
		addPrivateBusinessButton(FBMButtonFactory.getButtonVO(IFBMButton.Reckon_LinkInAccountBalance));
		addPrivateBusinessButton(FBMButtonFactory.getButtonVO(IFBMButton.Reckon_LinkOutAccountBalance));
	}

	private Observer getStatusEngine() {
		return new ReckonStatusEngine(this);
	}

	public StoragePowerVO getPower() {
		if(power==null){
			initPower();
		}
		return power;
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

	protected String checkPrerequisite() {
		try {
			String pk_settlecenter = FBMPublicQry.retrivePk_settlecentByPkCorp(_getCorp().getPrimaryKey());
			if(pk_settlecenter == null){
				return nc.ui.ml.NCLangRes.getInstance().getStrByID("3620add","UPP3620ADD-000242")/* @res"非资金组织体系下,当前节点不能使用"*/ ;
			}
		} catch (BusinessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
		}
		return super.checkPrerequisite();
	}
		
}