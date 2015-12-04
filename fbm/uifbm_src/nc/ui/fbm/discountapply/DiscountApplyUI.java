package nc.ui.fbm.discountapply;

import java.util.ArrayList;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import nc.bs.logging.Logger;
import nc.itf.cdm.util.CommonUtil;
import nc.ui.bd.ref.AbstractRefModel;
import nc.ui.fac.account.pub.IRefTakenProccessor;
import nc.ui.fbm.discount.OpBillTypeChangeListener;
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
import nc.ui.fbm.discount.reffilter.DiscountBillSourceFilter;
import nc.ui.fbm.discountapply.listener.BankDocEditlistener;
import nc.ui.fbm.discountapply.listener.DiscountApplySourceEditListener;
import nc.ui.fbm.discountapply.reffilter.BankAccRefModelFilter;
import nc.ui.fbm.discountapply.reffilter.DiscountApplyHoldUnitRefModelFilter;
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
import nc.ui.pub.ButtonObject;
import nc.ui.pub.beans.UIComboBox;
import nc.ui.pub.beans.UIRefPane;
import nc.ui.pub.beans.constenum.DefaultConstEnum;
import nc.ui.pub.bill.BillItem;
import nc.ui.pub.para.SysInitBO_Client;
import nc.ui.tm.framework.ref.AbstractTMRefModel;
import nc.ui.trade.bill.AbstractManageController;
import nc.ui.trade.button.IBillButton;
import nc.ui.trade.manage.ManageEventHandler;
import nc.vo.fbm.discount.DiscountVO;
import nc.vo.fbm.pub.constant.FbmBusConstant;
import nc.vo.fbm.storage.StoragePowerVO;
import nc.vo.pub.BusinessException;
import nc.vo.tm.enumeration.IParamEnum;
import nc.vo.trade.pub.IBillStatus;

/**
 *
 * <p>
 * 贴现申请界面UI类
 * <p>
 * 创建人：bsrl <b>日期：2007-09
 *
 */
public class DiscountApplyUI extends FBMManageUI implements
		ListSelectionListener {

	private Integer decimalpoint = 2;
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	public DiscountApplyUI() {
		// UFDate.getDaysBetween(new UFDate("2007-09-14"), new
		// UFDate("2008-03-07"));
	}

	public DiscountApplyUI(Boolean useBillSource) {
		super(useBillSource);
	}

	public DiscountApplyUI(String pk_corp, String pk_billType,
			String pk_busitype, String operater, String billId) {
		super(pk_corp, pk_billType, pk_busitype, operater, billId);
	}

	@Override
	protected AbstractManageController createController() {
		return new DiscountApplyController();
	}

	@Override
	protected void initSelfData() {
		super.initSelfData();
		initRefPaneFilter();
		initRefPane();
		initComBox();
		initCardThMark();
		getBillListPanel().getHeadTable().getSelectionModel()
				.addListSelectionListener(this);
		setButtonVisible();
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
					this,getBillCardPanel().getHeadItem(DiscountVO.OPBILLTYPE),DiscountVO.PK_DISCOUNT_BANK);
			((AbstractTMRefModel) refdes).addSqlFilter(a_filter);
		}
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
	public String[][] getHeadShowNum() {
		return new String[][] { { DiscountVO.DISCOUNTYRATE },
				{ getDecimalPoint().toString() } };
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

		getBillCardWrapper().initHeadComboBox(DiscountVO.RATEDAYNUM,
				new Integer[] { 360, 365 }, false);// .initHeadComboBox(DiscountVO.RATEDAYNUM,
													// acceptancetype, false);
		getBillListWrapper().initHeadComboBox(DiscountVO.RATEDAYNUM,
				new Integer[] { 360, 365 }, false);

		DefaultConstEnum[] billStatus = nc.ui.fbm.pub.ComBoxUtil
				.getBillStatus(getUIControl().getBillType());
		getBillCardWrapper().initHeadComboBox(DiscountVO.VBILLSTATUS,
				billStatus, false);
		getBillListWrapper().initHeadComboBox(DiscountVO.VBILLSTATUS,
				billStatus, false);
	}

	private void initRefPane() {
		AbstractRefModel holdaccref = ((UIRefPane) getBillCardPanel()
				.getHeadItem(DiscountVO.HOLDERACC).getComponent())
				.getRefModel();
		AbstractRefModel discountaccref = ((UIRefPane) getBillCardPanel()
				.getHeadItem(DiscountVO.DISCOUNT_ACCOUNT).getComponent())
				.getRefModel();
		discountaccref.setMatchPkWithWherePart(true);

		if (holdaccref instanceof AbstractTMRefModel) {
			DiscountApplyHoldUnitRefModelFilter holdunitfilter = new DiscountApplyHoldUnitRefModelFilter(
					getBillCardPanel().getHeadItem(DiscountVO.CPRMC));
			((AbstractTMRefModel) holdaccref).addSqlFilter(holdunitfilter);
		}
		if (discountaccref instanceof AbstractTMRefModel) {
//			PayAccbankRefModelFilter filter=new PayAccbankRefModelFilter(getBillCardPanel().getHeadItem(DiscountVO.HOLDERACC),this,DiscountVO.CPRMC);
//			((AbstractTMRefModel)discountaccref).addSqlFilter(filter);
//			CurrtypeBankaccFilter filter1 = new CurrtypeBankaccFilter(this,DiscountVO.YBBZ);
//			((AbstractTMRefModel)discountaccref).addSqlFilter(filter1);

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

	}

	@Override
	public void setDefaultData() throws Exception {
		getBillCardPanel().getHeadItem(DiscountVO.PK_CORP).setValue(
				_getCorp().getPrimaryKey());
		getBillCardPanel().getHeadItem(DiscountVO.VBILLSTATUS).setValue(
				IBillStatus.FREE);
		getBillCardPanel().getHeadItem(DiscountVO.PK_BILLTYPECODE).setValue(
				FbmBusConstant.BILLTYPE_DISCOUNT_APP);
		getBillCardPanel().getHeadItem(DiscountVO.APPLY_DATE).setValue(
				_getDate());
		getBillCardPanel().getTailItem(DiscountVO.VOPERATORID).setValue(
				_getOperator());
		getBillCardPanel().getTailItem(DiscountVO.DOPERATEDATE).setValue(
				_getDate());
		((UIComboBox) getBillCardPanel().getHeadItem(DiscountVO.RATEDAYNUM)
				.getComponent()).setSelectedIndex(0);
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
		return new DiscountApplyEventHandler(this, getUIControl());
	}

	@Override
	public IItemEditListener[] getBillItemEditListener() {
		return new IItemEditListener[] {
				new GatherEditListener(this, DiscountVO.PK_SOURCE),
				new DiscountRateEditListener(this, DiscountVO.DISCOUNTYRATE),
				new DiscountDateEditListener(this, DiscountVO.DDISCOUNTDATE),
				new DiscountApplySourceEditListener(this, DiscountVO.PK_SOURCE),
				new DiscountResultEditListener(this, DiscountVO.RESULT),

				new OpBillTypeChangeListener(this, DiscountVO.OPBILLTYPE),// 票据类别下拉框监听
				new DiscountPKSourceListener(this,DiscountVO.PK_SOURCE), //票编编号改变时修改票据类别.

				new DiscountChargeEditListener(this, DiscountVO.DISCOUNTCHARGE),
				new DiscountInterestEditListener(this,
						DiscountVO.DISCOUNTINTEREST),
				new DiscountMoneyyEditListener(this, DiscountVO.MONEYY),
				new DiscountYearDayEditListener(this, DiscountVO.RATEDAYNUM),
				new DiscountDelayDayNumEditListener(this,
						DiscountVO.DISCOUNTDELAYDAYNUM),
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
						DiscountVO.FRATE, DiscountVO.BRATE) ,
				new BankDocEditlistener(this,DiscountVO.PK_DISCOUNT_BANK,DiscountVO.DISCOUNT_ACCOUNT)};

	}

	@Override
	protected IRefTakenProccessor createRefTakenProccessor() {
		return new DiscountApplyRefTakenProccessor(getBillListPanel(),
				getBillCardPanel());
	}

	protected void initBusinessButton() {
		addPrivateBusinessButton(FBMButtonFactory
				.getButtonVO(IFBMButton.DiscountApply_Transact));
	}

	// @Override
	// public Object getUserObject() {
	// return new DiscountApplyBackCheck();
	// }

	protected ArrayList<IUIChecker> createValidator(int btnid) {
		ArrayList<IUIChecker> alChecker = null;
		switch (btnid) {
		case IBillButton.Save:
			alChecker = new ArrayList<IUIChecker>();
			alChecker.add(new DiscountApplyUICheck(this));
		}
		return alChecker;
	}

	// 这个方法 是控制办理按钮 可用还是不可用，但在点击办理时，又进行了查询，为修改效率此处不做查询
	// public void valueChanged(ListSelectionEvent e) {
	//
	// try {
	// if(getBufferData().getCurrentVO()!=null && (DiscountVO)
	// getBufferData().getCurrentVO().getParentVO()!=null ){
	// DiscountVO curvo = (DiscountVO) getBufferData().getCurrentVO()
	// .getParentVO();
	// SuperVO[] disvos = HYPubBO_Client.queryByCondition(
	// DiscountVO.class,
	// " isnull(dr,0) = 0 and pk_discount_app = '"
	// + curvo.getPk_discount() + "'");
	// if (disvos != null && disvos.length != 0) {
	// // Object[] butvos =
	// //
	// (ButtonObject[])getButtonManager().getButton(IBillButton.Action).getChildren().toArray();
	// // for(int i = 0 ; i < butvos.length; i ++) {
	// //
	// if(((ButtonObject)butvos[i]).getCode().equals(IFBMButton.DiscountApply_Transact))
	// // {
	// getButtonManager().getButton("TRANSACT").setEnabled(false);
	// // }
	// // }
	// } else {
	// // Object[] butvos =
	// //
	// getButtonManager().getButton(IBillButton.Action).getChildren().toArray();
	// // for(int i = 0 ; i < butvos.length; i ++) {
	// //
	// if(((ButtonObject)butvos[i]).getCode().equals(IFBMButton.DiscountApply_Transact))
	// // {
	// getButtonManager().getButton("TRANSACT").setEnabled(true);
	// // }
	// // }
	// // IFBMButton.DiscountApply_Transact).setEnabled(true);
	// }
	// }
	// updateButtons();
	// } catch (UifException e1) {
	// Logger.error(e1.getMessage(),e1);
	// }
	// }


	// currencyItemKeys 分别为：原币币种、是否固定汇率、辅币汇率、本币汇率、利率日期的ItemKey
	// protected String[] getCurrencyItemKeys() {
	// // TODO Auto-generated method stub
	// return new
	// String[]{GatherVO.PK_CURR,"fixflag",GatherVO.FRATE,GatherVO.BRATE,"begindate"};
	// }

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
			return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add","UPP3620ADD-000011")/*@res "当前公司未设置对应客商,无法进行贴现申请业务"*/;
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

	public void valueChanged(ListSelectionEvent e) {
		// TODO Auto-generated method stub
		
	}
	 private StoragePowerVO power;

	 private void setButtonVisible(){
		 ButtonObject[] btnobj = getButtonManager().getButton(IFBMButton.Discount_LinkGroup).getChildButtonGroup();
			if(btnobj!=null&& btnobj.length>0){
				for(int i=0;i<btnobj.length;i++){
					if(!btnobj[i].getTag().equals("135")){
						btnobj[i].setVisible(false);
					}
				}
			}
	 }

	
	 
	 
}