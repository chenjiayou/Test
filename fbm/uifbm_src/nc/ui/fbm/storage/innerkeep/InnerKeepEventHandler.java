/**
 *
 */
package nc.ui.fbm.storage.innerkeep;

import nc.ui.dap.service.DapBillQueryVoucher;
import nc.ui.fac.account.pub.RefTakenQueryFilterEditorFactory;
import nc.ui.fbm.pub.FBMManageUI;
import nc.ui.fbm.pub.FBManageEventHandler;
import nc.ui.fbm.pub.IFBMButton;
import nc.ui.fbm.storage.listener.InnerKeepSourceEditListener;
import nc.ui.pub.ButtonObject;
import nc.ui.pub.beans.UIDialog;
import nc.ui.pub.beans.UIRefPane;
import nc.ui.pub.bill.BillModel;
import nc.ui.tm.framework.util.ClientInfo;
import nc.ui.trade.controller.IControllerBase;
import nc.ui.trade.manage.BillManageUI;
import nc.vo.dap.inteface.IAccountPlat;
import nc.vo.dap.out.BillQueryVoucherVO;
import nc.vo.fbm.pub.constant.FbmBusConstant;
import nc.vo.fbm.pub.constant.IFBMStatus;
import nc.vo.fbm.storage.StorageBVO;
import nc.vo.fbm.storage.StoragePowerVO;
import nc.vo.fbm.storage.StorageVO;
import nc.vo.fp.pub.IOBudgetQueryVO;
import nc.vo.pub.AggregatedValueObject;
import nc.vo.pub.BusinessException;
import nc.vo.pub.lang.UFDouble;

/**
 * <p>
 * 
 * <p>
 * 创建人：lpf <b>日期：2007-10-9
 * 
 */
public class InnerKeepEventHandler extends FBManageEventHandler {

	/**
	 * @param billUI
	 * @param control
	 */
	public InnerKeepEventHandler(BillManageUI billUI, IControllerBase control) {
		super(billUI, control);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected String getHeadCondition() {
		StringBuffer whereSql = new StringBuffer();
		StoragePowerVO power = ((InnerKeepUI) getUI()).getPower();

		String statusfilter = "(fbm_storage.vbillstatus="
				+ String.valueOf(IFBMStatus.INPUT_SUCCESS)
				+ " or fbm_storage.vbillstatus="
				+ String.valueOf(IFBMStatus.OUTPUT_SUCCESS)
				+ " or fbm_storage.vbillstatus="
				+ String.valueOf(IFBMStatus.SUBMIT)
				+ " or fbm_storage.vbillstatus="
				+ String.valueOf(IFBMStatus.RETURNED)
				+ " )";

		String centerstatus = "( fbm_storage.vbillstatus="
				+ String.valueOf(IFBMStatus.INPUT_SUCCESS)
				+ " or fbm_storage.vbillstatus="
				+ String.valueOf(IFBMStatus.OUTPUT_SUCCESS)
				+ " or fbm_storage.vbillstatus="
				+ String.valueOf(IFBMStatus.SUBMIT)
				+ " or fbm_storage.vbillstatus="
				+ String.valueOf(IFBMStatus.RETURNED)
				+ " )";

		if (power.isSettleCenter()) {
			whereSql.append(" (fbm_storage.pk_corp ='"
					+ ClientInfo.getCorpPK()
					+ "' or ((fbm_storage.keepunit in ("
					+ getsubCorpCustPK(ClientInfo.getCorpPK())
					+ ")) and "
					+ centerstatus
					+ ")) ");
		} else {
			whereSql.append(" (fbm_storage.pk_corp ='"
					+ ClientInfo.getCorpPK()
					+ "' or (fbm_storage.keepunit='"
					+ power.getPk_cubasdoc()
					+ "' and "
					+ statusfilter
					+ ")) ");
		}
		whereSql.append(" and fbm_storage.pk_billtypecode = '"
				+ getUIController().getBillType()
				+ "' ");
		return whereSql.toString();
	}

	// 得到当前公司的下属所有结算单位对应的客商
	protected String getsubCorpCustPK(String pk_corp) {
		return " select pk_cubasdoc from bd_cubasdoc where "
				+ SubCustDocCondition.getCusDocFilterContidtion();
	}

	@Override
	protected void beforeOnBoAction(int intBtn, AggregatedValueObject billVo)
			throws Exception {
		super.beforeOnBoAction(intBtn, billVo);
		if (billVo == null
				|| billVo.getParentVO() == null
				|| billVo.getChildrenVO() == null)
			return;
		switch (intBtn) {
		case IFBMButton.CenterKeep_Return:
			ReturnReatonInputDlg dlg = new ReturnReatonInputDlg(getUI());
			if (dlg.showModal() == UIDialog.ID_OK) {
				String reason = dlg.getTxtInput().getText();
				billVo.getParentVO().setAttributeValue(StorageVO.RETURNREASON, reason);
			} else {
				throw new BusinessException(NOTSHOW);
			}
			break;
		}

	}

	@Override
	public void onBoActionElse(ButtonObject bo) throws Exception {
		String btncode = bo.getCode();
		AggregatedValueObject vo = getBufferData().getCurrentVO();
		if (vo != null && vo.getParentVO() != null) {
			StorageVO storageVo = (StorageVO) vo.getParentVO();
			StorageBVO[] storageVos = (StorageBVO[]) vo.getChildrenVO();
			if (btncode.equals(IFBMButton.FBM_VOUCHER_STR)
					|| btncode.equals(IFBMButton.FBM_CANCELVOUCHER_STR)
					|| btncode.equals(IFBMButton.INNERKEEP_INPUT)
					|| btncode.equals(IFBMButton.INNERKEEP_CANCELINPUT)
					|| btncode.equals(IFBMButton.INNERBACK_OUTPUT)
					|| btncode.equals(IFBMButton.INNERBACK_CANCELOUT)) {
				StoragePowerVO power = ((InnerKeepUI) getUI()).getPower();

				String billtype = getUIController().getBillType();
				String itemkey = StorageVO.KEEPUNIT;

				if (billtype.equals(FbmBusConstant.BILLTYPE_INNERBACK)) {
					itemkey = StorageVO.OUTPUTUNIT;
				}
				UIRefPane pane = (UIRefPane) getUI().getBillCardPanel().getHeadItem(itemkey).getComponent();
				pane.setPK(storageVo.getAttributeValue(itemkey));
				String rela_corp = (String) pane.getRefValue("bd_cubasdoc.pk_corp1");
				power.setRela_corp(rela_corp);
				storageVo.setPowerVo(power);
			}

			if (btncode.equals(IFBMButton.FBM_VOUCHER_STR)
					|| btncode.equals(IFBMButton.FBM_CANCELVOUCHER_STR)) {
				for (int i = 0; i < storageVos.length; i++) {
					UFDouble moneyy = storageVos[i].getMoneyy();
					Object moneyyobj = getUI().getRefTakenProccessor().getValueByTakenInList(StorageBVO.tablecode, StorageBVO.MONEYY, i);
					if (moneyyobj != null && moneyy == null) {
						moneyy = new UFDouble(moneyyobj.toString());
					}

					UFDouble moneyb = storageVos[i].getMoneyb();
					Object moneybobj = getUI().getRefTakenProccessor().getValueByTakenInList(StorageBVO.tablecode, StorageBVO.MONEYB, i);
					if (moneybobj != null && moneyb == null)
						moneyb = new UFDouble(moneybobj.toString());

					UFDouble moneybrate = storageVos[i].getMoneybrate();
					Object brateobj = getUI().getRefTakenProccessor().getValueByTakenInList(StorageBVO.tablecode, StorageBVO.MONEYBRATE, i);
					if (brateobj != null && moneybrate == null)
						moneybrate = new UFDouble(brateobj.toString());

					String fbmbillno = (String) getUI().getRefTakenProccessor().getValueByTakenInList(StorageBVO.tablecode, StorageBVO.FBMBILLNO, i);

					storageVos[i].setMoneyy(moneyy);
					storageVos[i].setMoneybrate(moneybrate);
					storageVos[i].setMoneyb(moneyb);
					storageVos[i].setFbmbillno(fbmbillno);
				}
			}
		}

		super.onBoActionElse(bo);
		if (btncode.equals(IFBMButton.FBM_TALLY_STR)) {
			String pk_accid = ((StorageVO) vo.getParentVO()).getKeepaccount();
			boolean isValid = showNoTallyInfo(pk_accid);
			if (!isValid) {
				return;
			}
		}
		if (btncode.equals(IFBMButton.INNERKEEP_CANCELINPUT)) {
			// if (getUI().isListPanelSelected()) {
			getUI().getBillListPanel().getHeadBillModel().setValueAt(null, getBufferData().getCurrentRow(), StorageVO.INPUTPERSON);
			// }
		}

		if (btncode.equals(IFBMButton.INNERBACK_CANCELOUT)) {
			// if (getUI().isListPanelSelected()) {
			getUI().getBillListPanel().getHeadBillModel().setValueAt(null, getBufferData().getCurrentRow(), StorageVO.OUTPUTPERSON);
			// }
		}
	}

	// @Override
	// protected UIDialog createQueryUI() {
	// return new RefTakenQueryConditionClient(
	// getBillUI(),
	// null,
	// _getCorp().getPrimaryKey(),
	// getBillUI()._getModuleCode(),
	// _getOperator(),
	// getBillUI().getBusinessType(),
	// getBillUI().getNodeKey(),
	// ((FBMManageUI)getBillUI()).getRefTakenProccessor());
	// }

	@Override
	protected void onBoSave() throws Exception {
		getBillCardPanelWrapper().getBillCardPanel().getBillData().dataNotNullValidate();
		super.onBoSave();
	}

	@Override
	protected void onBoEdit() throws Exception {

		super.onBoEdit();
		String inputtype = getBillUI().getBufferData().getCurrentVO().getParentVO().getAttributeValue(StorageVO.INPUTTYPE).toString();

		if (inputtype.equals("relief")) {
			((InnerKeepUI) getBillUI()).getBillCardPanel().getHeadItem(StorageVO.KEEPACCOUNT).setEnabled(true);
			((InnerKeepUI) getBillUI()).getBillCardPanel().getHeadItem(StorageVO.UNITPLANITEM).setEnabled(true);
		} else {
			((InnerKeepUI) getBillUI()).getBillCardPanel().getHeadItem(StorageVO.KEEPACCOUNT).setEnabled(false);
			((InnerKeepUI) getBillUI()).getBillCardPanel().getHeadItem(StorageVO.UNITPLANITEM).setEnabled(false);
		}
	}

	@Override
	protected void onBoElse(int intBtn) throws Exception {
		switch (intBtn) {
		case IFBMButton.Center_Storage_QueryGL:
			onBoQueryVoucher();
			break;
		case IFBMButton.BTN_QUERY_PLAN:
			onQueryPlan(createQueryPlanVO(StorageVO.UNITPLANITEM));
			break;
		}
		super.onBoElse(intBtn);
	}

	protected IOBudgetQueryVO createQueryPlanVO(String planitemKey)
			throws Exception {
		IOBudgetQueryVO vo = new IOBudgetQueryVO();

		vo.setSyscode(FbmBusConstant.SYSCODE_FBM);
		vo.setPk_billtypecode(getUI().getUIControl().getBillType());
		StorageVO storageVo = (StorageVO) getBufferData().getCurrentVO().getParentVO();
		vo.setPk_selfcorp(storageVo.getKeepcorp());

		vo.setPk_planitem((String) storageVo.getAttributeValue(planitemKey));
		String pk_curr = (String) getUI().getBillCardWrapper().getBillCardPanel().getHeadItem(StorageVO.PK_CURRTYPE).getValueObject();
		vo.setPk_currtype(pk_curr);
		vo.setCheckplandate(storageVo.getDapprovedate());
		return vo;
	}

	/**
	 * 
	 * <p>
	 * 联查凭证
	 * <p>
	 * 作者：lpf 日期：2007-11-23
	 */
	private void onBoQueryVoucher() {
		StoragePowerVO power = ((InnerKeepUI) getUI()).getPower();
		AggregatedValueObject vo = getBufferData().getCurrentVO();
		int destSystem = IAccountPlat.DESTSYS_GL;
		if (null == vo) {
			getBillUI().showWarningMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID("36201017", "UPP36201017-000000")/*
																														 * @res
																														 * "请选择要联查的单据。"
																														 */);
			return;
		}

		String pk_cubasdoc = power.getPk_cubasdoc();
		String corpPk = _getCorp().getPk_corp();
		String pk;
		try {
			StorageVO storageVo = (StorageVO) vo.getParentVO();
			String keepunit = null;
			if (getUIController().getBillType().equals(FbmBusConstant.BILLTYPE_INNERKEEP)) {
				keepunit = storageVo.getKeepunit();
			} else {
				keepunit = storageVo.getOutputunit();
			}

			// if(!keepunit.equals(pk_cubasdoc)){
			// destSystem = IAccountPlat.DESTSYS_SETTLE;
			// }
			pk = storageVo.getPrimaryKey();
			String procmsg = pk + StorageVO.Procmsg_flag + corpPk;

			BillQueryVoucherVO queryvo = new BillQueryVoucherVO();

			queryvo.setDestSystem(IAccountPlat.DESTSYS_GL);
			procmsg = findProcmsg4UpgradeData(procmsg);
			queryvo.setPk_bill(procmsg);

			DapBillQueryVoucher.showVoucher(queryvo, getBillUI());
		} catch (Exception ex) {
			getBillUI().showErrorMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID("36201017", "UPP36201017-000001")/*
																													 * @res
																													 * "联查凭证出错，详情请见错误日志。"
																													 */);
			return;
		}
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

	/**
	 * 删行时候重新计算金额汇总
	 */
	protected void onBoLineDel() throws Exception {
		super.onBoLineDel();
		BillModel billmodel = getUI().getBillCardPanel().getBillModel(StorageBVO.tablecode);
		InnerKeepSourceEditListener listen = new InnerKeepSourceEditListener(
				getUI(), null);
		listen.dealSumMoneyy(billmodel, StorageBVO.tablecode, StorageBVO.MONEYY, StorageVO.SUMMONEYY);
	}

	@Override
	protected UIDialog createQueryUI() {
		InnerKeepQueryDialog dialog = new InnerKeepQueryDialog(
				(InnerKeepUI) getBillUI(), createTemplateInof(getBillUI()));
		dialog.registerFilterEditorFactory(new RefTakenQueryFilterEditorFactory(
				(FBMManageUI) getBillUI(),
				((FBMManageUI) getBillUI()).getRefTakenProccessor(), dialog));
		return dialog;
	}
}