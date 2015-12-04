/**
 *
 */
package nc.ui.fbm.storage.innerback;

import java.util.ArrayList;

import nc.ui.bd.ref.AbstractRefModel;
import nc.ui.fac.account.pub.IRefTakenProccessor;
import nc.ui.fbm.planref.PlanItem4InnerBackModel;
import nc.ui.fbm.pub.FBMButtonFactory;
import nc.ui.fbm.pub.IFBMButton;
import nc.ui.fbm.pub.IItemEditListener;
import nc.ui.fbm.pub.IUIChecker;
import nc.ui.fbm.pub.ItemNotNullChecker;
import nc.ui.fbm.pub.reffilter.InnerAccIncludePartFrozenFilter;
import nc.ui.fbm.pub.reffilter.InnerAccRuleFilter;
import nc.ui.fbm.returnbill.BodyItemDeleteEditListener;
import nc.ui.fbm.storage.InnerAccRefModelFilter;
import nc.ui.fbm.storage.InnerBackRefTakenProcessor;
import nc.ui.fbm.storage.checker.BodyCurrencyChecker;
import nc.ui.fbm.storage.checker.BodyItemUniqueChecker;
import nc.ui.fbm.storage.checker.BodyNotEmptyChecker;
import nc.ui.fbm.storage.innerkeep.InnerKeepUI;
import nc.ui.fbm.storage.innerkeep.KeepUnitRefModelFilter;
import nc.ui.fbm.storage.listener.ClearItemValueEditListener;
import nc.ui.fbm.storage.listener.CurrtypeEditListener;
import nc.ui.fbm.storage.listener.InnerKeepSourceEditListener;
import nc.ui.fbm.storage.listener.InputTypeListener;
import nc.ui.pub.beans.UIRefPane;
import nc.ui.pub.bill.IBillItem;
import nc.ui.tm.framework.ref.AbstractTMRefModel;
import nc.ui.tm.framework.util.CurrencyClientUtil;
import nc.ui.trade.bill.AbstractManageController;
import nc.ui.trade.button.IBillButton;
import nc.ui.trade.manage.ManageEventHandler;
import nc.vo.fbm.pub.FBMProxy;
import nc.vo.fbm.pub.FBMPublicQry;
import nc.vo.fbm.pub.constant.FbmBusConstant;
import nc.vo.fbm.storage.StorageBVO;
import nc.vo.fbm.storage.StorageVO;
import nc.vo.trade.pub.IBillStatus;
import nc.vo.uapbd.bankdoc.BankdocVO;

/**
 * <p>
 * 内部领用UI
 * <p>创建人：lpf
 * <b>日期：2007-10-9
 *
 */
public class InnerBackUI extends InnerKeepUI {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private InnerBackEventHandler m_handler;
	private InnerBackController m_ctrl;

	/**
	 *
	 */
	public InnerBackUI() {
		super();
	}

	/**
	 * @param useBillSource
	 */
	public InnerBackUI(Boolean useBillSource) {
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
	public InnerBackUI(String pk_corp, String pk_billType, String pk_busitype,
			String operater, String billId) {
		super(pk_corp, pk_billType, pk_busitype, operater, billId);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void setDefaultData() throws Exception {
		getBillCardPanel().getHeadItem(StorageVO.VBILLSTATUS).setValue(
				IBillStatus.FREE);
		getBillCardPanel().getHeadItem(StorageVO.PK_BILLTYPECODE).setValue(
				getUIControl().getBillType());

		getBillCardPanel().getTailItem(StorageVO.VOPERATORID).setValue(
				_getOperator());
		getBillCardPanel().getTailItem(StorageVO.DOPERATEDATE).setValue(
				_getDate());
		getBillCardPanel().getHeadItem(StorageVO.PK_CORP).setValue(
				_getCorp().getPk_corp());

		if(!getPower().isSettleCenter()){
			String pk_cubasdoc = getPower().getPk_cubasdoc();
			getBillCardPanel().getHeadItem(StorageVO.OUTPUTUNIT).setValue(pk_cubasdoc);
			fireCardAfterEdit(StorageVO.OUTPUTUNIT);
		}
		
		CurrencyClientUtil currencyClientUtil = new CurrencyClientUtil();
		getBillCardPanel().getHeadItem(StorageVO.PK_CURRTYPE).setValue(
				currencyClientUtil.getLocalCurrPK());
		
		//设置受托银行
		String pk_settlecenter = FBMPublicQry.retrivePk_settlecentByPkCorp(_getCorp().getPrimaryKey());
		BankdocVO bankdocVO = FBMProxy.getBankDocQueryService().queryBankDocByCenterPk(pk_settlecenter);
		if(bankdocVO!=null)
		getBillCardPanel().getHeadItem(StorageVO.PK_BANKDOC).setValue(bankdocVO.getPrimaryKey());
	
	}

	protected ManageEventHandler createEventHandler() {
		if (m_handler == null)
			m_handler = new InnerBackEventHandler(this, createController());
		return m_handler;
	}

	protected AbstractManageController createController() {
		if (m_ctrl == null)
			m_ctrl = new InnerBackController();
		return m_ctrl;
	}

	@Override
	protected void initRefPane() {
		UIRefPane gatherPane = (UIRefPane) getBillCardPanel().getBodyItem(
				StorageBVO.PK_SOURCE).getComponent();
		gatherPane.setMultiSelectedEnabled(true);
		gatherPane.setCacheEnabled(false);
		AbstractRefModel gathermdl = gatherPane.getRefModel();
		if (AbstractTMRefModel.class.isAssignableFrom(gathermdl.getClass())) {
			InnerBackSourceRefModelFilter filter = new InnerBackSourceRefModelFilter(
					getBillCardPanel().getBodyItem(StorageBVO.PK_SOURCE),this);
			((AbstractTMRefModel) gathermdl).addSqlFilter(filter);
		}

		AbstractRefModel baseRefmdl=((UIRefPane) getBillCardPanel().getHeadItem(StorageVO.KEEPACCOUNT).getComponent()).getRefModel();
		if (baseRefmdl instanceof AbstractTMRefModel) {
			InnerAccRefModelFilter filter=new InnerAccRefModelFilter(getBillCardPanel().getHeadItem(StorageVO.KEEPACCOUNT),this,StorageVO.OUTPUTUNIT);
			((AbstractTMRefModel)baseRefmdl).addSqlFilter(filter);
			InnerAccIncludePartFrozenFilter filter1= new InnerAccIncludePartFrozenFilter();
			((AbstractTMRefModel)baseRefmdl).addSqlFilter(filter1);
		}
		InnerAccRuleFilter accRuleFilter = new InnerAccRuleFilter(FbmBusConstant.BILLTYPE_INNERBACK,FbmBusConstant.ACC_INNER);
		addSqlFilter(true, StorageVO.KEEPACCOUNT, accRuleFilter);

		AbstractRefModel custfmdl = ((UIRefPane) getBillCardPanel()
				.getHeadItem(StorageVO.OUTPUTUNIT).getComponent())
				.getRefModel();
		if (custfmdl instanceof AbstractTMRefModel) {
			KeepUnitRefModelFilter filter = new KeepUnitRefModelFilter(
					getBillCardPanel().getHeadItem(StorageVO.OUTPUTUNIT),this);
			((AbstractTMRefModel) custfmdl).addSqlFilter(filter);
		}
		
		PlanItem4InnerBackModel planModel = new PlanItem4InnerBackModel(getBillCardPanel().getHeadItem(StorageVO.KEEPCORP));
		((UIRefPane)getBillCardPanel().getHeadItem(StorageVO.UNITPLANITEM).getComponent()).setRefModel(planModel);

	}

	@Override
	protected void initBusinessButton() {
		addPrivateBusinessButton(FBMButtonFactory.getButtonVO(IFBMButton.CenterBack_Output));
		addPrivateBusinessButton(FBMButtonFactory.getButtonVO(IFBMButton.CenterBack_CancelOut));
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
		return new IItemEditListener[]{
			new InnerKeepSourceEditListener(this,StorageBVO.PK_SOURCE,IBillItem.BODY),
			new BodyItemDeleteEditListener(this,StorageVO.OUTPUTUNIT),
			new ClearItemValueEditListener(this,StorageVO.OUTPUTUNIT,StorageVO.KEEPACCOUNT),
			new CurrtypeEditListener(this,StorageVO.PK_CURRTYPE),
			new InputTypeListener(this,StorageVO.INPUTTYPE)
		};
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
					new String[] { nc.ui.ml.NCLangRes.getInstance().getStrByID("36201019","UPT36201019-000040")/* @res"票据编号"*/ }));
			alChecker.add(new InnerKeepDateChecker(this,StorageVO.DOPERATEDATE));
			alChecker.add(new BodyCurrencyChecker(this,StorageVO.KEEPACCOUNT));
			break;
		case IBillButton.Audit:
			alChecker = new ArrayList<IUIChecker>();
			alChecker.add(new InnerKeepDateChecker(this,StorageVO.DAPPROVEDATE));
			break;
		case IFBMButton.CenterBack_Output:
			alChecker = new ArrayList<IUIChecker>();
			alChecker.add(new InnerKeepDateChecker(this,StorageVO.OUTPUTDATE));
			break;
		}

		return alChecker;
	}

	@Override
	protected void initItemEditablebyPower() {
		if(!getPower().isSettleCenter()&&getPower().isSettleUnit()){
			getBillCardPanel().getHeadItem(StorageVO.OUTPUTUNIT).setEnabled(false);
		}
	}
	
	@Override
	protected IRefTakenProccessor createRefTakenProccessor() {
		return new InnerBackRefTakenProcessor(getBillListPanel(),
				getBillCardPanel());
	}


}