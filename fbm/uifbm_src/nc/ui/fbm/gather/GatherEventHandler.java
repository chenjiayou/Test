package nc.ui.fbm.gather;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import nc.bs.framework.common.NCLocator;
import nc.itf.cdm.util.CommonUtil;
import nc.itf.fbm.gather.IGatherBatchConsign;
import nc.itf.fbm.gather.IGatherService;
import nc.ui.fac.account.pub.RefTakenQueryConditionClient;
import nc.ui.fac.account.pub.RefTakenQueryFilterEditorFactory;
import nc.ui.fbm.pub.FBMClientInfo;
import nc.ui.fbm.pub.FBMManageUI;
import nc.ui.fbm.pub.FBManageEventHandler;
import nc.ui.fbm.pub.IFBMButton;
import nc.ui.fbm.pub.YFBEditListerner;
import nc.ui.pub.ButtonObject;
import nc.ui.pub.beans.MessageDialog;
import nc.ui.pub.beans.UIDialog;
import nc.ui.pub.bill.BillEditEvent;
import nc.ui.pub.bill.BillItem;
import nc.ui.pub.para.SysInitBO_Client;
import nc.ui.tm.framework.util.ClientInfo;
import nc.ui.trade.base.AbstractBillUI;
import nc.ui.trade.business.HYPubBO_Client;
import nc.ui.trade.button.IBillButton;
import nc.ui.trade.controller.IControllerBase;
import nc.ui.trade.manage.BillManageUI;
import nc.vo.fbm.consignbank.CollectionVO;
import nc.vo.fbm.discount.DiscountVO;
import nc.vo.fbm.impawn.ImpawnVO;
import nc.vo.fbm.pub.BaseinfoVO;
import nc.vo.fbm.pub.constant.FBMParamConstant;
import nc.vo.fbm.pub.constant.FbmBusConstant;
import nc.vo.fbm.pub.constant.FbmStatusConstant;
import nc.vo.fbm.register.RegisterVO;
import nc.vo.fp.pub.IOBudgetQueryVO;
import nc.vo.pub.AggregatedValueObject;
import nc.vo.pub.BusinessException;
import nc.vo.pub.SuperVO;
import nc.vo.pub.lang.UFBoolean;
import nc.vo.pub.lang.UFDate;
import nc.vo.pub.lang.UFDouble;
import nc.vo.trade.pub.HYBillVO;

/**
 * 
 * <p>
 * 收票登记动作类
 * <p>
 * 创建人：lpf <b>日期：2007-8-24
 * 
 */
public class GatherEventHandler extends FBManageEventHandler {

	GatherDiscountBatchDlg discountDlg = null;
	GatherConsignBatchDlg consignDlg = null;
	GatherImpawnBatchDlg impawnDlg = null;
	YFBEditListerner yfblisterner = new YFBEditListerner(getUI(),
			RegisterVO.PK_CURR, RegisterVO.PK_CURR, RegisterVO.MONEYY,
			RegisterVO.MONEYF, RegisterVO.MONEYB, RegisterVO.FRATE,
			RegisterVO.BRATE, null, null, "specialkey");
	String pk_cubasdoc = null;;

	public GatherEventHandler(BillManageUI billUI, IControllerBase control) {
		super(billUI, control);
	}

	@Override
	protected UIDialog createQueryUI() {
		RefTakenQueryConditionClient dialog = new RefTakenQueryConditionClient(
				getBillUI(), createTemplateInof(getBillUI()));
		// dialog.registerFieldValueEelementEditorFactory(new
		// ReleaseFieldValueElementEditorFactory(dialog.getQueryContext()));
		dialog.registerFilterEditorFactory(new RefTakenQueryFilterEditorFactory(
				(FBMManageUI) getBillUI(),
				((FBMManageUI) getBillUI()).getRefTakenProccessor(), dialog));
		return dialog;
	}

	/*
	 * （非 Javadoc）
	 * 
	 * @see nc.ui.trade.bill.BillEventHandler#onBoQuery()
	 */
	@Override
	protected void onBoQuery() throws Exception {
		// TODO 自动生成方法存根
		super.onBoQuery();
	}

	@Override
	protected void onBoElse(int intBtn) throws Exception {
		// TODO Auto-generated method stub

		switch (intBtn) {
		case IFBMButton.BTN_QUERY_PLAN:
			onQueryPlan(createQueryPlanVO(RegisterVO.GATHERPLANITEM));
			break;
		case IFBMButton.Gather_BankKeep:
			JumpToOtherUI(FbmBusConstant.BILLTYPE_BANKKEEP);
			break;
		case IFBMButton.Gather_BankBack:
			JumpToOtherUI(FbmBusConstant.BILLTYPE_BANKBACK);
			break;
		case IFBMButton.Gather_ReturnBill:
			String gatherType = (String) getBufferData().getCurrentVO().getParentVO().getAttributeValue(RegisterVO.GATHERTYPE);
			// 如果是统管的票，当用户点击退票时，应给出提示，并阻止此操作。
			if (FbmBusConstant.GATHER_TYPE_UNISTORAGE.equals(gatherType)) {
				throw new BusinessException(
						nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add", "UPP3620ADD-000035")/*
																											 * @res
																											 * "统管的票,不能进行退票!"
																											 */);
			}
			JumpToOtherUI(FbmBusConstant.BILLTYPE_RETURN);
			break;
		case IFBMButton.Gather_BankDiscount:

			JumpToOtherUI(FbmBusConstant.BILLTYPE_DISCOUNT_APP);
			break;
		case IFBMButton.Gather_Consign:
			JumpToOtherUI(FbmBusConstant.BILLTYPE_COLLECTION_UNIT);
			break;
		case IFBMButton.Gather_Impawn:
			JumpToOtherUI(FbmBusConstant.BILLTYPE_IMPAWN);
			break;
		case IFBMButton.Gather_LQuerySFBill:
			if (getBufferData().getCurrentVO() == null) {
				throw new BusinessException(
						nc.ui.ml.NCLangRes.getInstance().getStrByID("36201005", "UPP36201005-000000")/*
																									 * @res
																									 * "请选中收票登记单进行联查"
																									 */);
			}
			String pk_busibill = getBufferData().getCurrentVO().getParentVO().getPrimaryKey();
			jumpToArapQuery(pk_busibill);
			break;
		case IFBMButton.BTNVO_SPWC:
			onBoSpwc();
			break;
		case IFBMButton.BTNVO_QXSP:
			onBoQxsp();
			break;
		case IFBMButton.PRINT4NOTE:
			onPrint4Note();
			break;
		// case IFBMButton.BTN_QUERY_PLAN:
		// onQueryPlan(createQueryPlanVO(RegisterVO.INVOICEPLANITEM));
		// break;
		case IFBMButton.Gather_BatchConsign:
			getBillUI().showHintMessage(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add", "UPP3620ADD-000243")/*
																															 * @res
																															 * "批量托收!"
																															 */);
			String checkCurr = checkCurr();
			if (checkCurr == null || "".equals(checkCurr)) {
				return;
			}
			consignDlg = new GatherConsignBatchDlg(getUI(), checkCurr);
			if (consignDlg.showModal() == UIDialog.ID_OK) {
				gatherBatchConsign();
			}
			break;
		case IFBMButton.Gather_BatchDiscount:
			getBillUI().showHintMessage(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add", "UPP3620ADD-000244")/*
																															 * @res
																															 * "批量贴现!"
																															 */);
			String checkCurr1 = checkCurr();
			if (checkCurr1 == null || "".equals(checkCurr1)) {
				return;
			}
			discountDlg = new GatherDiscountBatchDlg(getUI(), checkCurr1);
			if (discountDlg.showModal() == UIDialog.ID_OK) {
				gatherBatchDiscount();
			}
			break;
		case IFBMButton.Gather_BatchImpawn:
			String checkCurrImpawn = checkCurr();
			if (checkCurrImpawn == null || "".equals(checkCurrImpawn)) {
				return;
			}
			getBillUI().showHintMessage(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add", "UPP3620ADD-000245")/*
																															 * @res
																															 * "批量质押!"
																															 */);
			impawnDlg = new GatherImpawnBatchDlg(getUI());
			if (impawnDlg.showModal() == UIDialog.ID_OK) {
				gatherBatchImpawn();
			}
			break;
		}
		super.onBoElse(intBtn);
	}

	/**
	 * TODO 收票完成 事件处理
	 * 
	 * @author zhouwb 2008-9-17
	 */
	protected void onBoSpwc() throws Exception {
		if (isGatherARAP()) {
			getBillUI().showErrorMessage(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcode112", "UPPFBMCODE112-000011")/*
																																	 * @res
																																	 * "备查簿业务与应收管理、应付管理、现金平台集成应用，请通过应收管理、现金平台进行该操作"
																																	 */);
			return;
		}

		RegisterVO regVo = (RegisterVO) getBufferData().getCurrentVO().getParentVO();

		// 提示用户录入计划项目
		// if(((GatherUI)getBillUI()).getPlanItemDlg().showModal() ==
		// UIDialog.ID_OK){
		// String pk_planitem =
		// ((GatherUI)getBillUI()).getPlanItemDlg().getPlanItemPK();
		// regVo.setInvoiceplanitem(pk_planitem);
		// }else{
		// return;
		// }

		// 执行收票完成
		IGatherService gatherItf = (IGatherService) NCLocator.getInstance().lookup(IGatherService.class.getName());
		gatherItf.executeSpwc(regVo);

		// 刷新界面
		getBufferData().refresh();

		getBillUI().showHintMessage(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcode112", "UPPFBMCODE112-000012")/*
																																 * @res
																																 * "收票完成！"
																																 */);
	}

	/**
	 * TODO 取消收票 事件处理
	 * 
	 * @author zhouwb 2008-9-17
	 */
	protected void onBoQxsp() throws Exception {
		if (isGatherARAP()) {
			getBillUI().showErrorMessage(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcode112", "UPPFBMCODE112-000013")/*
																																	 * @res
																																	 * "备查簿业务与应收管理、应付管理、现金平台集成应用，请使用收付报取消收票！"
																																	 */);
			return;
		}

		RegisterVO regVo = (RegisterVO) getBufferData().getCurrentVO().getParentVO();

		// 执行取消收票
		IGatherService gatherItf = (IGatherService) NCLocator.getInstance().lookup(IGatherService.class.getName());
		gatherItf.executeQxsp(regVo);

		// 刷新界面
		getBufferData().refresh();

		getBillUI().showHintMessage(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcode112", "UPPFBMCODE112-000014")/*
																																 * @res
																																 * "取消收票成功！"
																																 */);
	}

	/**
	 * @return 背书业务是否与收付报单据集成应用
	 * 
	 * @author zhouwb 2008-9-18
	 */
	private boolean isGatherARAP() throws Exception {
		UFBoolean value = SysInitBO_Client.getParaBoolean(_getCorp().getPk_corp(), FBMParamConstant.FBM005);
		return value != null && value.booleanValue();
	}

	@Override
	protected void buttonActionAfter(AbstractBillUI billUI, int intBtn)
			throws Exception {
		super.buttonActionAfter(billUI, intBtn);
		switch (intBtn) {
		case IBillButton.Edit:
			String isnewBill = (String) getBillCardPanelWrapper().getBillCardPanel().getHeadItem(RegisterVO.ISNEWBILL).getValueObject();
			setBaseinfoEditable(new Boolean(isnewBill).booleanValue());
			String flag = (String) getUI().getBillCardPanel().getHeadItem(RegisterVO.SFFLAG).getValueObject();
			if ("true".equals(flag)) {
				getBillCardPanelWrapper().getBillCardPanel().getHeadItem(RegisterVO.GATHERPLANITEM).setEnabled(true);
			}
			BillItem bi = getUI().getBillCardPanel().getHeadItem(RegisterVO.PK_CURR);
			BillEditEvent e = new BillEditEvent(bi.getComponent(),
					bi.getValueObject(), "specialkey", -1, BillItem.HEAD);
			yfblisterner.responseEditEvent(e);
			break;
		case IBillButton.Copy:
			getBillCardPanelWrapper().getBillCardPanel().getHeadItem(RegisterVO.GATHERTYPE).setValue(FbmBusConstant.GATHER_TYPE_INPUT);
			String sfflag = (String) getUI().getBillCardPanel().getHeadItem(RegisterVO.SFFLAG).getValueObject();
			if ("true".equals(sfflag)) {
				getBillCardPanelWrapper().getBillCardPanel().getHeadItem(RegisterVO.GATHERPLANITEM).setEnabled(true);
			}
			// String pk_cubasdoc = FBMClientInfo.getCommonCurCorpCubasdoc();
			if (pk_cubasdoc == null || !(pk_cubasdoc.trim().length() > 0)) {
				pk_cubasdoc = FBMClientInfo.getCommonCurCorpCubasdoc();
			}
			getBillCardPanelWrapper().getBillCardPanel().getHeadItem(RegisterVO.KEEPUNIT).setValue(pk_cubasdoc);
			getBillCardPanelWrapper().getBillCardPanel().getHeadItem(RegisterVO.HOLDUNIT).setValue(pk_cubasdoc);
			// getUI().fireCardAfterEdit(RegisterVO.PK_CURR);
			bi = getUI().getBillCardPanel().getHeadItem(RegisterVO.PK_CURR);
			e = new BillEditEvent(bi.getComponent(), bi.getValueObject(),
					"specialkey", -1, BillItem.HEAD);
			yfblisterner.responseEditEvent(e);
			break;
		}
	}

	protected void onBoCopy() throws Exception {
		// TODO Auto-generated method stub
		super.onBoCopy();
		if (getBillCardPanelWrapper().getBillCardPanel().getTailItem(RegisterVO.APPROVETIME)!=null){
			getBillCardPanelWrapper().getBillCardPanel().getTailItem(RegisterVO.APPROVETIME).setValue(null);
		}
		// if
		// (getBillCardPanelWrapper().getBillCardPanel().getHeadItem(RegisterVO.ORIENTATION)
		// != null) {
		// getBillCardPanelWrapper().getBillCardPanel().getHeadItem(RegisterVO.ORIENTATION).setValue(FbmBusConstant.ORIEINTATION_AR);
			getBillCardPanelWrapper().getBillCardPanel().setHeadItem(RegisterVO.ORIENTATION, FbmBusConstant.ORIEINTATION_AR);
			BillItem billItem = getBillCardPanelWrapper().getBillCardPanel().getHeadItem(RegisterVO.ORIENTATION);
		billItem.setValue("ar");
		getBillCardPanelWrapper().getBillCardPanel().setHeadItem(RegisterVO.SFFLAG, new UFBoolean(false));
		getBillCardPanelWrapper().getBillCardPanel().setHeadItem(RegisterVO.GATHERPLANITEM, null);
		// }
		//复制时为防用户修改票据基本信息，让票据基本信息除票据编号外其它字段不可编辑。
		getBillCardPanelWrapper().getBillCardPanel().getHeadItem(RegisterVO.PK_CURR).setEnabled(false);
		getBillCardPanelWrapper().getBillCardPanel().getHeadItem(RegisterVO.FBMBILLTYPE).setEnabled(false);
		getBillCardPanelWrapper().getBillCardPanel().getHeadItem(RegisterVO.MONEYY).setEnabled(false);
		getBillCardPanelWrapper().getBillCardPanel().getHeadItem(RegisterVO.PAYUNIT).setEnabled(false);
		getBillCardPanelWrapper().getBillCardPanel().getHeadItem(RegisterVO.PAYBANKACC).setEnabled(false);
		getBillCardPanelWrapper().getBillCardPanel().getHeadItem(RegisterVO.PAYBANK).setEnabled(false);
		getBillCardPanelWrapper().getBillCardPanel().getHeadItem(RegisterVO.RECEIVEUNIT).setEnabled(false);
		getBillCardPanelWrapper().getBillCardPanel().getHeadItem(RegisterVO.RECEIVEBANKACC).setEnabled(false);
		getBillCardPanelWrapper().getBillCardPanel().getHeadItem(RegisterVO.RECEIVEBANK).setEnabled(false);
		getBillCardPanelWrapper().getBillCardPanel().getHeadItem(RegisterVO.INVOICEUNIT).setEnabled(false);
		getBillCardPanelWrapper().getBillCardPanel().getHeadItem(RegisterVO.INVOICEDATE).setEnabled(false);
		getBillCardPanelWrapper().getBillCardPanel().getHeadItem(RegisterVO.ENDDATE).setEnabled(false);
		getBillCardPanelWrapper().getBillCardPanel().getHeadItem(RegisterVO.ACCEPTANCENO).setEnabled(false);
		getBillCardPanelWrapper().getBillCardPanel().getHeadItem(RegisterVO.CONTRACTNO).setEnabled(false);
			
	}
	@Override
	public void onBoAdd(ButtonObject bo) throws Exception {
		// TODO Auto-generated method stub
		super.onBoAdd(bo);
		getBillCardPanelWrapper().getBillCardPanel().getHeadItem(RegisterVO.PK_CURR).setEnabled(true);
		getBillCardPanelWrapper().getBillCardPanel().getHeadItem(RegisterVO.FBMBILLTYPE).setEnabled(true);
		getBillCardPanelWrapper().getBillCardPanel().getHeadItem(RegisterVO.MONEYY).setEnabled(true);
		getBillCardPanelWrapper().getBillCardPanel().getHeadItem(RegisterVO.PAYUNIT).setEnabled(true);
		getBillCardPanelWrapper().getBillCardPanel().getHeadItem(RegisterVO.PAYBANKACC).setEnabled(true);
		getBillCardPanelWrapper().getBillCardPanel().getHeadItem(RegisterVO.PAYBANK).setEnabled(true);
		getBillCardPanelWrapper().getBillCardPanel().getHeadItem(RegisterVO.RECEIVEUNIT).setEnabled(true);
		getBillCardPanelWrapper().getBillCardPanel().getHeadItem(RegisterVO.RECEIVEBANKACC).setEnabled(true);
		getBillCardPanelWrapper().getBillCardPanel().getHeadItem(RegisterVO.RECEIVEBANK).setEnabled(true);
		getBillCardPanelWrapper().getBillCardPanel().getHeadItem(RegisterVO.INVOICEUNIT).setEnabled(true);
		getBillCardPanelWrapper().getBillCardPanel().getHeadItem(RegisterVO.INVOICEDATE).setEnabled(true);
		getBillCardPanelWrapper().getBillCardPanel().getHeadItem(RegisterVO.ENDDATE).setEnabled(true);
		getBillCardPanelWrapper().getBillCardPanel().getHeadItem(RegisterVO.ACCEPTANCENO).setEnabled(true);
		getBillCardPanelWrapper().getBillCardPanel().getHeadItem(RegisterVO.CONTRACTNO).setEnabled(true);
	}
	// @Override
	// protected UIDialog createQueryUI() {
	// RefTakenQueryConditionClient Querydlg = new RefTakenQueryConditionClient(
	// getBillUI(),
	// null,
	// _getCorp().getPrimaryKey(),
	// getBillUI()._getModuleCode(),
	// _getOperator(),
	// getBillUI().getBusinessType(),
	// getBillUI().getNodeKey(),
	// ((FBMManageUI)getBillUI()).getRefTakenProccessor());
	// QueryConditionVO[] conditionVOs = Querydlg.getAllTempletDatas();
	// Vector<QueryConditionVO> vector = new Vector<QueryConditionVO>();
	// for (int i = 0; i < conditionVOs.length; i++) {
	// QueryConditionVO conditionVO = conditionVOs[i];
	// if(!conditionVO.getFieldCode().equalsIgnoreCase(DiscountVO.OPBILLTYPE)) {
	// vector.add(conditionVO);
	// }
	// }
	// conditionVOs = vector.toArray(new QueryConditionVO[0]);
	// Querydlg.initTempletDatas(conditionVOs);
	// return Querydlg;
	// }

	@Override
	protected String getHeadCondition() {
		String sql = " "
				+ RegisterVO.TABLENAME
				+ ".pk_billtypecode='"
				+ FbmBusConstant.BILLTYPE_GATHER
				+ "' ";
		return super.getHeadCondition() + " and " + sql;
	}

	private void setBaseinfoEditable(boolean editable) {
		getUI().setHeadItemEditable(new String[] { RegisterVO.PAYBANKACC,
				RegisterVO.PAYUNIT, RegisterVO.RECEIVEBANKACC,
				RegisterVO.PAYUNIT, RegisterVO.FBMBILLTYPE, RegisterVO.MONEYY,
				RegisterVO.PK_CURR, RegisterVO.INVOICEDATE,
				RegisterVO.INVOICEUNIT, RegisterVO.ENDDATE,
				RegisterVO.ACCEPTANCENO, RegisterVO.CONTRACTNO,
				RegisterVO.RECEIVEUNIT ,RegisterVO.PAYBANK,RegisterVO.RECEIVEBANK}, editable);
	}

	@Override
	protected void beforeOnBoAction(int intBtn, AggregatedValueObject billVo)
			throws Exception {
		super.beforeOnBoAction(intBtn, billVo);
		switch (intBtn) {
		case IBillButton.CancelAudit:
			if(getBufferData().getCurrentVO() !=null){
			RegisterVO regVO = (RegisterVO)getBufferData().getCurrentVO().getParentVO();
			String gatherType = regVO.getGathertype();
			if (!CommonUtil.isNull(gatherType)
					&& !gatherType.equals(FbmBusConstant.GATHER_TYPE_INPUT)) {
				throw new BusinessException(
						nc.ui.ml.NCLangRes.getInstance().getStrByID("36201005", "UPP36201005-000001")/*
																									 * @res
																									 * "非收票录入的收票登记单无法弃审"
																									 */);
			}
			}
		}
	}

	// @Override
	// protected void onBoSave() throws Exception {
	// // TODO Auto-generated method stub
	// UFDate currentOperDate = ClientEnvironment.getInstance().getDate();
	// String receivebank = (String)
	// getUI().getBillCardPanel().getHeadItem(RegisterVO.RECEIVEBANKACC).getValueObject();
	// String paybank = (String)
	// getUI().getBillCardPanel().getHeadItem(RegisterVO.PAYBANKACC).getValueObject();
	// HYPubBO_Client pub_clientbo = new HYPubBO_Client();
	// BankaccbasVO bankvo[] = (BankaccbasVO[])
	// pub_clientbo.queryByCondition(BankaccbasVO.class, "pk_bankaccbas = '"
	// + receivebank
	// + "' or pk_bankaccbas = '"
	// + paybank
	// + "'");
	// if (bankvo != null && bankvo.length > 0) {
	// for (int i = 0; i < bankvo.length; i++) {
	// BankaccbasVO bankBasVO = bankvo[i];
	// UFDate opendate = bankBasVO.getAccopendate();
	// if (currentOperDate.before(opendate)) {
	// throw new BusinessException(
	// nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("36201005",
	// "UPP36201005-000020")/*
	// * @res
	// * "收款银行账户或者付款银行账户开户日期晚于业务日期，不允许保存。"
	// */);
	// }
	// }
	// }
	// super.onBoSave();
	//
	// }

	@Override
	protected IOBudgetQueryVO createQueryPlanVO(String planitemKey)
			throws Exception {
		IOBudgetQueryVO vo = super.createQueryPlanVO(planitemKey);
		vo.setPk_selfcorp(ClientInfo.getCorpPK());
		AggregatedValueObject billVo = getBufferData().getCurrentVO();
		RegisterVO registerVo = (RegisterVO) billVo.getParentVO();
		vo.setPk_planitem((String) registerVo.getAttributeValue(planitemKey));
		vo.setPk_currtype(registerVo.getPk_curr());
		vo.setCheckplandate(registerVo.getGatherdate());
		return vo;
	}

	/**
	 * 批量处理银行托收.
	 */
	public void gatherBatchConsign() throws Exception {
		StringBuffer errorMsg = new StringBuffer();
		boolean writeplan = false;
		String holderacc = consignDlg.getItemValue("holderacc");
		String collectiondate = consignDlg.getItemValue("dcollectiondate");
		String dconsigndate = consignDlg.getItemValue("dconsigndate");
		String collectionplanitem = consignDlg.getItemValue("collectionplanitem");
		String fbmplanitem = consignDlg.getItemValue("fbmplanitem");
		if ((collectionplanitem != null && collectionplanitem.trim().length() > 0)
				|| (fbmplanitem != null && fbmplanitem.trim().length() > 0)) {
			String paramValue = SysInitBO_Client.queryByParaCode(ClientInfo.getCorpPK(), FBMParamConstant.FBM005).getValue();
			if ("Y".equals(paramValue)) {
				int message = MessageDialog.showYesNoCancelDlg(getUI(), nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("36201005", "UPP36201005-000022")/*
																																								 * @res
																																								 * "写计划提示"
																																								 */, nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("36201005", "UPP36201005-000021")/*
																																																															 * @res
																																																															 * "当写计划超出计划执行数时是否强制写计划？"
																																																															 */, MessageDialog.ID_CANCEL);
				if (message == UIDialog.ID_CANCEL) {
					return;
				}
				if (message == UIDialog.ID_YES) {
					writeplan = true;
				} else {
					writeplan = false;
				}
			}
		}
		Object obj = null;
		Vector<HYBillVO> vec = new Vector<HYBillVO>();
		int size = getBufferData().getVOBufferSize();
		for (int i = 0; i < size; i++) {

			obj = getUI().getBillListPanel().getParentListPanel().getTable().getValueAt(i, 0);

			if (obj != null
					&& (new UFBoolean(obj.toString())).booleanValue() == true) {
				AggregatedValueObject selVo = getBufferData().getVOByRowNo(i);
				RegisterVO resvo = (RegisterVO) selVo.getParentVO();
				String registerstatus = (String) getUI().getRefTakenProccessor().getValueByTakenInList(RegisterVO.TABLENAME, RegisterVO.REGISTERSTATUS, i);
				resvo.setRegisterstatus(registerstatus);
				try {
					if ((resvo.getRegisterstatus().equals(FbmStatusConstant.REGISTER)
							|| resvo.getRegisterstatus().equals(FbmStatusConstant.HAS_BANK_KEEP) || resvo.getRegisterstatus().equals(FbmStatusConstant.HAS_IMPAWN))
							&& resvo.getSfflag().booleanValue()) {
						HYBillVO convertVo = new RegisterVOConverter().convertTool(new RegisterVO[] { resvo }, FbmBusConstant.BILLTYPE_COLLECTION_UNIT);
						CollectionVO collectionvo = (CollectionVO) convertVo.getParentVO();
						collectionvo.setHolderacc(holderacc);
						if (collectiondate != null
								&& !"".equals(collectiondate)) {
							collectionvo.setDcollectiondate(new UFDate(
									collectiondate));
						}
						if (dconsigndate != null && !"".equals(dconsigndate)) {
							collectionvo.setDconsigndate((new UFDate(
									dconsigndate)));
						}
						collectionvo.setCollectionplanitem(collectionplanitem);
						collectionvo.setFbmplanitem(fbmplanitem);

						collectionvo.setYbbz(resvo.getPk_curr());
						collectionvo.setFbmbillno(resvo.getFbmbillno());
						collectionvo.setPmje(resvo.getMoneyy());
						collectionvo.setMoneyy(resvo.getMoneyy());
						collectionvo.setMoneyf(resvo.getMoneyf());
						collectionvo.setMoneyb(resvo.getMoneyb());
						collectionvo.setBrate(resvo.getBrate());
						collectionvo.setFrate(resvo.getFrate());
						collectionvo.setWriteplan(new UFBoolean(writeplan));
						collectionvo.setUnitvoucher(new UFBoolean(false));
						convertVo.setParentVO(collectionvo);
						vec.add(convertVo);
					} else {
						errorMsg.append(resvo.getVbillno() + "\n");
					}
				} catch (BusinessException e) {
					e.printStackTrace();
				}
			}
		}
		if (vec.size() > 0) {
			IGatherBatchConsign ibatchconsign = (IGatherBatchConsign) NCLocator.getInstance().lookup(IGatherBatchConsign.class.getName());
			errorMsg.append(ibatchconsign.batchBankConsignOP(vec, ClientInfo.getCurrentDate().toString(), ClientInfo.getUserPK()));

		}

		batchCacheQuery();
		showErrorMsg(errorMsg.toString());
		getBillUI().showHintMessage(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("36201005", "UPP36201005-000023")/*
																															 * @res
																															 * "批量托收处理完毕"
																															 */);
	}

	/**
	 * 批量贴现.
	 */
	public void gatherBatchDiscount() throws Exception {
		boolean writeplan = false;
		StringBuffer errorMsg = new StringBuffer();
		StringBuffer reMsg = new StringBuffer();
		HYPubBO_Client pubclientbo = new HYPubBO_Client();
		BaseinfoVO baseinfovo = null;
		String discountdate = discountDlg.getItemValue("ddiscountdate");
		String ratedaynum = discountDlg.getItemValue("ratedaynum");
		String discountdelaydaynum = discountDlg.getItemValue("discountdelaydaynum");
		String discountyrate = discountDlg.getItemValue("discountyrate");
		String interestplanitem = discountDlg.getItemValue("interestplanitem");
		String balanceplanitem = discountDlg.getItemValue("balanceplanitem");
		String fbmplanitem = discountDlg.getItemValue("fbmplanitem");
		if ((interestplanitem != null && interestplanitem.trim().length() > 0)
				|| (fbmplanitem != null && fbmplanitem.trim().length() > 0)) {
			String paramValue = SysInitBO_Client.queryByParaCode(ClientInfo.getCorpPK(), FBMParamConstant.FBM005).getValue();
			if ("Y".equals(paramValue)) {
				int message = MessageDialog.showYesNoCancelDlg(getUI(), nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("36201005", "UPP36201005-000022")/*
																																								 * @res
																																								 * "写计划提示"
																																								 */, nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("36201005", "UPP36201005-000021")/*
																																																															 * @res
																																																															 * "当写计划超出计划执行数时是否强制写计划？"
																																																															 */, MessageDialog.ID_CANCEL);
				if (message == UIDialog.ID_CANCEL) {
					return;
				}
				if (message == UIDialog.ID_YES) {
					writeplan = true;
				} else {
					writeplan = false;
				}
			}
		}
		Object obj = null;
		Vector<HYBillVO> vec = new Vector<HYBillVO>();
		int size = getBufferData().getVOBufferSize();
		for (int i = 0; i < size; i++) {

			obj = getUI().getBillListPanel().getParentListPanel().getTable().getValueAt(i, 0);

			if (obj != null
					&& (new UFBoolean(obj.toString())).booleanValue() == true) {
				AggregatedValueObject selVo = getBufferData().getVOByRowNo(i);
				RegisterVO resvo = (RegisterVO) selVo.getParentVO();
				String registerstatus = (String) getUI().getRefTakenProccessor().getValueByTakenInList(RegisterVO.TABLENAME, RegisterVO.REGISTERSTATUS, i);
				resvo.setRegisterstatus(registerstatus);
				try {
					if ((resvo.getRegisterstatus().equals(FbmStatusConstant.REGISTER))
							&& resvo.getSfflag().booleanValue()) {
						HYBillVO convertVo = new RegisterVOConverter().convertTool(new RegisterVO[] { resvo }, FbmBusConstant.BILLTYPE_DISCOUNT_TRANSACT);
						DiscountVO discountvo = (DiscountVO) convertVo.getParentVO();
						discountvo.setDiscount_account(discountDlg.getItemValue("discount_account"));
						discountvo.setPk_discount_bank(discountDlg.getItemValue("pk_discount_bank"));

						if (interestplanitem != null
								&& !"".equals(interestplanitem)) {
							discountvo.setInterestplanitem(interestplanitem);
						}
						if (balanceplanitem != null
								&& !"".equals(balanceplanitem)) {
							discountvo.setBalanceplanitem(balanceplanitem);
						}
						if (fbmplanitem != null && !"".equals(fbmplanitem)) {
							discountvo.setFbmplanitem(fbmplanitem);
						}
						if (discountdate != null && !"".equals(discountdate)) {
							discountvo.setDdiscountdate(new UFDate(discountdate));
						}
						if (discountyrate != null && !"".equals(discountyrate)) {
							discountvo.setDiscountyrate(new UFDouble(
									discountyrate));
						}
						if (ratedaynum != null && !"".equals(ratedaynum)) {
							discountvo.setRatedaynum(new Integer(ratedaynum));
						}
						if (discountdelaydaynum != null
								&& !"".equals(discountdelaydaynum)) {
							discountvo.setDiscountdelaydaynum(new Integer(
									discountdelaydaynum));
						} else {
							// 如果没有输入延迟天数的，默认为0
							discountvo.setDiscountdelaydaynum(new Integer(0));
							discountdelaydaynum = "0";
						}
						baseinfovo = (BaseinfoVO) pubclientbo.queryByPrimaryKey(BaseinfoVO.class, resvo.getPk_baseinfo());
						discountvo.setWritePlan(new UFBoolean(writeplan));
						discountvo.setYbbz(resvo.getPk_curr());
						discountvo.setFbmbillno(resvo.getFbmbillno());
						discountvo.setMoneyf(resvo.getMoneyf());
						discountvo.setMoneyb(resvo.getMoneyb());
						discountvo.setResult(FbmBusConstant.DISCOUNT_RESULT_SUCCESS);
						// 调用方法计算贴现利息.
						// UFDouble interestValue =
						// DiscountCalculate.calculateDiscountInterest(resvo.getMoneyy(),
						// new UFDate(
						// discountdate), baseinfovo.getEnddate(),
						// resvo.getPk_curr(), new Integer(
						// discountdelaydaynum).intValue(), new UFDouble(
						// discountyrate), new Integer(ratedaynum).intValue(),
						// discountvo.getPk_corp());
						// discountvo.setDiscountinterest(interestValue);
						// discountvo.setMoneyy(resvo.getMoneyy().sub(interestValue));
						discountvo.setDiscountcharge(new UFDouble(0));
						discountvo.setUnitvoucher(new UFBoolean(false));
						convertVo.setParentVO(discountvo);
						vec.add(convertVo);
					} else {
						errorMsg.append(resvo.getVbillno() + "\n");
					}
				} catch (BusinessException e) {
					e.printStackTrace();
				}
			}
		}
		if (vec.size() > 0) {
			IGatherBatchConsign ibatchconsign = (IGatherBatchConsign) NCLocator.getInstance().lookup(IGatherBatchConsign.class.getName());
			errorMsg.append(ibatchconsign.batchDiscountOP(vec, ClientInfo.getCurrentDate().toString(), ClientInfo.getUserPK()));

		}

		batchCacheQuery();
		showErrorMsg(errorMsg.toString());
		getBillUI().showHintMessage(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("36201005", "UPP36201005-000024")/*
																															 * @res
																															 * "批量贴现处理完毕"
																															 */);
	}

	/**
	 * 批量质押.
	 */
	public void gatherBatchImpawn() throws Exception {
		StringBuffer errorMsg = new StringBuffer();
		Object obj = null;
		Vector<HYBillVO> vec = new Vector<HYBillVO>();
		int size = getBufferData().getVOBufferSize();
		for (int i = 0; i < size; i++) {

			obj = getUI().getBillListPanel().getParentListPanel().getTable().getValueAt(i, 0);

			if (obj != null
					&& (new UFBoolean(obj.toString())).booleanValue() == true) {
				AggregatedValueObject selVo = getBufferData().getVOByRowNo(i);
				RegisterVO resvo = (RegisterVO) selVo.getParentVO();
				String registerstatus = (String) getUI().getRefTakenProccessor().getValueByTakenInList(RegisterVO.TABLENAME, RegisterVO.REGISTERSTATUS, i);
				resvo.setRegisterstatus(registerstatus);
				try {
					if ((resvo.getRegisterstatus().equals(FbmStatusConstant.REGISTER))
							&& resvo.getSfflag().booleanValue()) {
						HYBillVO convertVo = new RegisterVOConverter().convertTool(new RegisterVO[] { resvo }, FbmBusConstant.BILLTYPE_IMPAWN);
						ImpawnVO impawnvo = (ImpawnVO) convertVo.getParentVO();
						impawnvo.setDebitunit(impawnDlg.getItemValue("debitunit"));
						impawnvo.setImpawnunit(impawnDlg.getItemValue("impawnunit"));
						impawnvo.setCreditunit(impawnDlg.getItemValue("creditunit"));
						impawnvo.setImpawnrate(new UFDouble(
								impawnDlg.getItemValue("impawnrate")));
						impawnvo.setImpawnbank(impawnDlg.getItemValue("impawnbank"));
						if (impawnDlg.getItemValue("impawndate") != null
								&& !"".equals(impawnDlg.getItemValue("impawndate"))) {
							impawnvo.setImpawndate(new UFDate(
									impawnDlg.getItemValue("impawndate")));
						}
						impawnvo.setPk_curr(resvo.getPk_curr());
						impawnvo.setFbmbillno(resvo.getFbmbillno());
						impawnvo.setMoneyy(resvo.getMoneyy());
						impawnvo.setMoneyf(resvo.getMoneyf());
						impawnvo.setMoneyb(resvo.getMoneyb());
						impawnvo.setImpawnable(resvo.getMoneyy().multiply(new UFDouble(
								impawnDlg.getItemValue("impawnrate"))).div(100));
						impawnvo.setEvaluatevalue(resvo.getMoneyy());
						convertVo.setParentVO(impawnvo);
						vec.add(convertVo);
					} else {
						errorMsg.append(resvo.getVbillno() + "\n");
					}
				} catch (BusinessException e) {
					e.printStackTrace();
				}
			}
		}
		if (vec.size() > 0) {
			IGatherBatchConsign ibatchconsign = (IGatherBatchConsign) NCLocator.getInstance().lookup(IGatherBatchConsign.class.getName());
			errorMsg.append(ibatchconsign.batchImpawnOP(vec, ClientInfo.getCurrentDate().toString(), ClientInfo.getUserPK()));

		}

		batchCacheQuery();
		showErrorMsg(errorMsg.toString());
		getBillUI().showHintMessage(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("36201005", "UPP36201005-000025")/*
																															 * @res
																															 * "批量质押理完毕"
																															 */);
	}

	@SuppressWarnings("unchecked")
	public String checkCurr() {
		Object obj = null;
		String pk_curr_r = "";
		Map currMap = new HashMap();
		int size = getBufferData().getVOBufferSize();
		for (int i = 0; i < size; i++) {

			obj = getUI().getBillListPanel().getParentListPanel().getTable().getValueAt(i, 0);

			if (obj != null
					&& (new UFBoolean(obj.toString())).booleanValue() == true) {
				AggregatedValueObject selVo = getBufferData().getVOByRowNo(i);
				String pk_curr = (String) getUI().getRefTakenProccessor().getValueByTakenInList(RegisterVO.TABLENAME, RegisterVO.PK_CURR, i);
				RegisterVO resvo = (RegisterVO) selVo.getParentVO();

				currMap.put(pk_curr, pk_curr);
				pk_curr_r = pk_curr;
			}
		}
		if (currMap.size() == 0) {
			MessageDialog.showWarningDlg(getUI(), nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("36201005", "UPP36201005-000026")/*
																																		 * @res
																																		 * "失败提示"
																																		 */, nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("36201005", "UPP36201005-000027")/*
																																																									 * @res
																																																									 * "至少要选择一张票！"
																																																									 */);
			return null;
		}
		if (currMap.size() > 1) {
			MessageDialog.showWarningDlg(getUI(), nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("36201005", "UPP36201005-000026")/*
																																		 * @res
																																		 * "失败提示"
																																		 */, nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("36201005", "UPP36201005-000028")/*
																																																									 * @res
																																																									 * "币种不一致不允许做批量操作！"
																																																									 */);

			return null;
		}
		return pk_curr_r;
	}

	public void showErrorMsg(String errorMsg) throws BusinessException {
		if (errorMsg != null && !"".equals(errorMsg)) {
			// StringBuffer reMsg = new StringBuffer();
			// errorMsg = errorMsg.substring(0,errorMsg.length()-1);
			// RegisterVO regvo[] =
			// (RegisterVO[])HYPubBO_Client.queryByCondition(RegisterVO.class,
			// " pk_register in("+errorMsg+")");
			// for(int i=0;i<regvo.length;i++){
			// reMsg.append(regvo[i].getVbillno()+"\n");
			// }
			MessageDialog.showWarningDlg(getUI(), nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("36201005", "UPP36201005-000026")/*
																																		 * @res
																																		 * "失败提示"
																																		 */, errorMsg
					+ nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("36201005", "UPP36201005-000029")/*
																											 * @res
																											 * "\n 没有操作成功！"
																											 */);
		}
	}
	
	@Override
	protected void onBoSave() throws Exception {
		String fbmbillno = (String)getBillCardPanelWrapper().getBillCardPanel().getHeadItem(RegisterVO.FBMBILLNO).getValueObject();
		String pk_baseinfo = (String)getBillCardPanelWrapper().getBillCardPanel().getHeadItem(RegisterVO.PK_BASEINFO).getValueObject();
		if(pk_baseinfo == null){
			HYPubBO_Client pub_client = new HYPubBO_Client();
			SuperVO supervo[] = pub_client.queryByCondition(BaseinfoVO.class, "fbmbillno = '"+fbmbillno+"'");
			if(supervo!=null&&supervo.length>0){
				getBillCardPanelWrapper().getBillCardPanel().setHeadItem(RegisterVO.ISNEWBILL, new UFBoolean(false));
			}
		}
		super.onBoSave();
	}
}
