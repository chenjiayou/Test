/**
 *
 */
package nc.ui.fbm.returnbill;

import java.util.ArrayList;
import java.util.Observer;

import nc.bs.logging.Logger;
import nc.itf.cdm.util.CommonUtil;
import nc.ui.fac.account.pub.IRefTakenProccessor;
import nc.ui.fbm.pub.FBMButtonFactory;
import nc.ui.fbm.pub.FBMClientInfo;
import nc.ui.fbm.pub.FBMManageUI;
import nc.ui.fbm.pub.IFBMButton;
import nc.ui.fbm.pub.IItemEditListener;
import nc.ui.fbm.pub.IUIChecker;
import nc.ui.fbm.pub.ItemNotNullChecker;
import nc.ui.fbm.returnbill.btnstatus.ReturnBtnStatusEngine;
import nc.ui.fbm.returnbill.checker.ReturnBillCurrChecker;
import nc.ui.fbm.returnbill.checker.ReturnDateValidChecker;
import nc.ui.fbm.returnbill.listener.ReturnTypePlanItemListener;
import nc.ui.fbm.returnbill.reffilter.ReturnTypeRefModelFilter;
import nc.ui.fbm.storage.checker.BodyItemUniqueChecker;
import nc.ui.fbm.storage.checker.BodyNotEmptyChecker;
import nc.ui.fbm.storage.listener.InnerKeepSourceEditListener;
import nc.ui.pub.beans.UIRefPane;
import nc.ui.pub.beans.constenum.DefaultConstEnum;
import nc.ui.pub.bill.IBillItem;
import nc.ui.pub.linkoperate.ILinkAddData;
import nc.ui.tm.framework.billtemplet.PkReturnableBillCellEditor;
import nc.ui.tm.framework.ref.AbstractTMRefModel;
import nc.ui.trade.bill.AbstractManageController;
import nc.ui.trade.button.IBillButton;
import nc.ui.trade.manage.ManageEventHandler;
import nc.vo.fbm.pub.constant.FbmBusConstant;
import nc.vo.fbm.returnbill.ReturnBVO;
import nc.vo.fbm.returnbill.ReturnVO;
import nc.vo.fbm.storage.StoragePowerVO;
import nc.vo.pub.BusinessException;
import nc.vo.trade.pub.IBillStatus;

/**
 * <p>
 * 退票界面UI
 * <p>
 * 创建人：lpf <b>日期：2007-8-30
 *
 */
public class ReturnBillUI extends FBMManageUI {
	private ReturnBillEventHandler m_hdl = null;
	private ReturnBillController m_ctrl = null;
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private Observer StatusEngine = getStatusEngine();

	/**
	 *
	 */
	public ReturnBillUI() {
		super();
		initCombox();
		getBufferData().deleteObserver(this);
		getBufferData().addObserver(StatusEngine);
		getBufferData().addObserver(this);
	}

	/**
	 * @param useBillSource
	 */
	public ReturnBillUI(Boolean useBillSource) {
		super(useBillSource);
		initCombox();
	}

	/**
	 * @param pk_corp
	 * @param pk_billType
	 * @param pk_busitype
	 * @param operater
	 * @param billId
	 */
	public ReturnBillUI(String pk_corp, String pk_billType, String pk_busitype,
			String operater, String billId) {
		super(pk_corp, pk_billType, pk_busitype, operater, billId);
		initCombox();
	}

	@Override
	protected AbstractManageController createController() {
		if (m_ctrl == null)
			m_ctrl = new ReturnBillController();
		return m_ctrl;
	}

	@Override
	protected IRefTakenProccessor createRefTakenProccessor() {
		return new ReturnRefTakenProcessor(getBillListPanel(),getBillCardPanel());
	}

	@Override
	protected ArrayList<IUIChecker> createValidator(int btnid) {
		ArrayList<IUIChecker> alChecker = null;
		switch (btnid) {
		case IBillButton.Save:
			alChecker = new ArrayList<IUIChecker>();
			alChecker.add(new ItemNotNullChecker(this));
			alChecker.add(new BodyNotEmptyChecker(this));
			alChecker.add(new BodyItemUniqueChecker(this,new String[] { ReturnBVO.PK_SOURCE },new String[] { nc.ui.ml.NCLangRes.getInstance().getStrByID("362025","UPT362025-000016")/* @res"票据编号"*/ }));
			alChecker.add(new ReturnDateValidChecker(this));
			alChecker.add(new ReturnBillCurrChecker(this));
		}
		return alChecker;
	}

	@Override
	protected IItemEditListener[] getBillItemEditListener() {
		return new IItemEditListener[]{
				new BodyItemDeleteEditListener(this,ReturnVO.RETURNTYPE),
				new InnerKeepSourceEditListener(this,ReturnBVO.PK_SOURCE,IBillItem.BODY),
				new ReturnTypePlanItemListener(this,ReturnVO.RETURNTYPE)
		};
	}

	@Override
	protected void initSelfData() {
		super.initSelfData();
		initButtonVisible();
		initRefPane();
		initBillCellEditor();
	}

	private void initBillCellEditor() {
		int colomn = getBillCardPanel().getBodyColByKey(ReturnBVO.PK_SOURCE);
		UIRefPane refpane = (UIRefPane) getBillCardPanel().getBodyItem(ReturnBVO.PK_SOURCE).getComponent();
		getBillCardPanel().getBillTable().getColumnModel().getColumn(colomn).setCellEditor(new PkReturnableBillCellEditor(refpane));
	}



	private void initRefPane() {
		UIRefPane returnTypePane=(UIRefPane) getBillCardPanel().getBodyItem(ReturnBVO.PK_SOURCE).getComponent();
		returnTypePane.setMultiSelectedEnabled(true);
		returnTypePane.setCacheEnabled(false);//设置缓存为false
		if(returnTypePane.getRefModel() instanceof AbstractTMRefModel){
			AbstractTMRefModel rettypeRefModel=(AbstractTMRefModel) returnTypePane.getRefModel();
			rettypeRefModel.addSqlFilter(new ReturnTypeRefModelFilter(this,getBillCardPanel().getHeadItem(ReturnVO.RETURNTYPE)));
		}
	}

	private void initCombox() {
		DefaultConstEnum[] returntype ;
		if(FBMClientInfo.isSettleCenter()){
			returntype = nc.ui.fbm.pub.ComBoxUtil.getReturnType(false);
		}else{
			returntype = nc.ui.fbm.pub.ComBoxUtil.getReturnType(true);
		}
		getBillCardWrapper().initHeadComboBox(ReturnVO.RETURNTYPE,
				returntype, false);
		getBillListWrapper().initHeadComboBox(ReturnVO.RETURNTYPE,
				returntype, false);

		try{
			DefaultConstEnum[] acceptancetype = nc.ui.fbm.pub.ComBoxUtil
					.getAcceptanceType();
			getBillCardWrapper().initBodyComboBox(ReturnBVO.FBMBILLTYPE,
					acceptancetype, false);
			getBillListWrapper().initBodyComboBox(ReturnBVO.FBMBILLTYPE,
					acceptancetype, false);
		} catch (BusinessException e) {
			showErrorMessage(e.getMessage());
		}
		DefaultConstEnum[] billStatus = nc.ui.fbm.pub.ComBoxUtil.getBillStatus(getUIControl().getBillType());
		getBillCardWrapper().initHeadComboBox(ReturnVO.VBILLSTATUS,
				billStatus, false);
		getBillListWrapper().initHeadComboBox(ReturnVO.VBILLSTATUS,
				billStatus, false);

		DefaultConstEnum[] fBillStatus = nc.ui.fbm.pub.ComBoxUtil
				.getFBillStatus();
		getBillCardWrapper().initBodyComboBox(ReturnBVO.ENDSTATUS,
				fBillStatus, false);
		getBillListWrapper().initBodyComboBox(ReturnBVO.ENDSTATUS,
				fBillStatus, false);
	}

	@Override
	public void setDefaultData() throws Exception {
		getBillCardPanel().getHeadItem(ReturnVO.VBILLSTATUS).setValue(
				IBillStatus.FREE);
		getBillCardPanel().getHeadItem(ReturnVO.PK_BILLTYPECODE).setValue(
				FbmBusConstant.BILLTYPE_RETURN);
		getBillCardPanel().getTailItem(ReturnVO.VOPERATORID).setValue(
				_getOperator());
		getBillCardPanel().getTailItem(ReturnVO.DOPERATEDATE).setValue(
				_getDate());
		getBillCardPanel().getHeadItem(ReturnVO.DRETURNDATE).setValue(
				_getDate());
		getBillCardPanel().getHeadItem(ReturnVO.RETURNPERSON).setValue(
				_getOperator());
		getBillCardPanel().getHeadItem(ReturnVO.PK_CORP).setValue(
				_getCorp().getPk_corp());
	}

	@Override
	protected ManageEventHandler createEventHandler() {
		if (m_hdl == null)
			m_hdl = new ReturnBillEventHandler(this, createController());
		return m_hdl;
	}

	@Override
	public void doAddAction(ILinkAddData adddata) {
		super.doAddAction(adddata);
		getBillCardPanel().getHeadItem(ReturnVO.RETURNTYPE).setEnabled(false);
		getBillCardPanel().getBodyItem(ReturnBVO.PK_SOURCE).setEnabled(false);
		getBillCardPanel().getHeadItem(ReturnVO.UNITPLANITEM).setEnabled(false);
	}

	@Override
	protected void initBusinessButton() {
		addPrivateBusinessButton(FBMButtonFactory.getButtonVO(IFBMButton.ReturnBill_TransToSFBill));
		addPrivateBusinessButton(FBMButtonFactory.getButtonVO(IFBMButton.ReturnBill_CancelTrans));
        addPrivateBusinessButton(FBMButtonFactory.getButtonVO(IFBMButton.FBM_VOUCHER));
        addPrivateBusinessButton(FBMButtonFactory.getButtonVO(IFBMButton.FBM_CANCELVOUCHER));
		addPrivateBusinessButton(FBMButtonFactory.getButtonVO(IFBMButton.FBM_TALLY));
		addPrivateBusinessButton(FBMButtonFactory.getButtonVO(IFBMButton.FBM_CANCELTALLY));
	}


	private Observer getStatusEngine() {
		return new ReturnBtnStatusEngine(this);
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
			return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add","UPP3620ADD-000116")/*@res "当前公司未设置对应客商,无法进行集中退票业务"*/;
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
				Logger.debug(e.getMessage());
			}

		}

	}


	 private StoragePowerVO power;

}