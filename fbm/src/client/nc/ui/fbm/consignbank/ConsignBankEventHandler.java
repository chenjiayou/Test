package nc.ui.fbm.consignbank;

import nc.ui.fac.account.pub.RefTakenQueryConditionClient;
import nc.ui.fac.account.pub.RefTakenQueryFilterEditorFactory;
import nc.ui.fbm.pub.FBMManageUI;
import nc.ui.fbm.pub.FBManageEventHandler;
import nc.ui.fbm.pub.IFBMButton;
import nc.ui.fbm.pub.YFBEditListerner;
import nc.ui.pub.ButtonObject;
import nc.ui.pub.ClientEnvironment;
import nc.ui.pub.beans.UIDialog;
import nc.ui.pub.beans.UIRefPane;
import nc.ui.pub.bill.BillEditEvent;
import nc.ui.pub.bill.BillItem;
import nc.ui.tm.framework.util.ClientInfo;
import nc.ui.trade.base.AbstractBillUI;
import nc.ui.trade.button.IBillButton;
import nc.ui.trade.controller.IControllerBase;
import nc.ui.trade.manage.BillManageUI;
import nc.vo.fbm.consignbank.CollectionVO;
import nc.vo.fbm.pub.constant.FbmBusConstant;
import nc.vo.fbm.pub.constant.FbmStatusConstant;
import nc.vo.fbm.register.RegisterVO;
import nc.vo.fp.pub.IOBudgetQueryVO;
import nc.vo.pub.AggregatedValueObject;
import nc.vo.pub.BusinessException;
import nc.vo.pub.lang.UFDate;

/**
 * <p>
 * ��������EventHandler��
 * <p>
 * �����ˣ�bsrl <b>���ڣ�2007-09
 * 
 */
public class ConsignBankEventHandler extends FBManageEventHandler {

	public ConsignBankEventHandler(BillManageUI billUI, IControllerBase control) {
		super(billUI, control);
	}

	@Override
	protected void beforeOnBoAction(int intBtn, AggregatedValueObject billVo)
			throws Exception {
		super.beforeOnBoAction(intBtn, billVo);
		switch (intBtn) {
		case IFBMButton.ConsignBank_Disable:
			if (((ConsignBankUI) getBillUI()).getDisableDlg().showModal() == UIDialog.ID_OK) {
				((CollectionVO) billVo.getParentVO()).setDisablenote(((ConsignBankUI) getBillUI()).getDisableDlg().getTxtNote().getText());
				((CollectionVO) billVo.getParentVO()).setVdisablemanid(_getOperator());
				// NCdp201015741 Ŀǰ�жϡ��������������Ʊ�ݵ������ڡ���Ӧȥ����У�飬������������һҵ������֮��Ϳ�����
				// ���հ���36GI0909210004����½����2009-9-21���ϼ��ɸ���
				// String enddatestr = (String)
				// getBillCardPanelWrapper().getBillCardPanel().getHeadItem(CollectionVO.DQRQ).getValueObject();
				// if (enddatestr.trim().length() == 0) {
				// throw new BusinessException("Ʊ�ݵ������ڲ���Ϊ��!");
				// }
				// UFDate enddate = new UFDate(enddatestr);
				// UFDate disabledate = ClientInfo.getCurrentDate();
				// if (enddate.after(disabledate)) {
				// throw new BusinessException(
				// nc.ui.ml.NCLangRes.getInstance().getStrByID("36201020",
				// "UPP36201020-000014")/*
				// * @res
				// * "�������ڱ������Ʊ�ݵ������ڣ�"
				// */);
				// }
			} else {
				throw new BusinessException(NOTSHOW);
			}
			break;
		case IFBMButton.ConsignBank_Transact:
			if (((ConsignBankUI) getBillUI()).getTransactDlg().showModal() == UIDialog.ID_OK) {
				String enddatestr = (String) getBillCardPanelWrapper().getBillCardPanel().getHeadItem(CollectionVO.DQRQ).getValueObject();
				if (enddatestr.trim().length() == 0) {
					throw new BusinessException("Ʊ�ݵ������ڲ���Ϊ��!");
				}
				UFDate enddate = new UFDate(enddatestr);
				UFDate collectiondate = new UFDate(
						((ConsignBankUI) getBillUI()).getTransactDlg().getCollectionDate().getValueObject().toString());
				UFDate currentdate = ClientEnvironment.getInstance().getDate();
				if (collectiondate.compareTo(enddate) < 0)
					throw new BusinessException(
							nc.ui.ml.NCLangRes.getInstance().getStrByID("36201020", "UPP36201020-000005")/*
																										 * @res
																										 * "�����������ڱ������Ʊ�ݵ������ڣ�"
																										 */);
				if (currentdate.before(collectiondate)) {
					throw new BusinessException(
							nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add", "UPP3620ADD-000002")/*
																												 * @res
																												 * "��½���ڲ��������ڿ�����������"
																												 */);
				}

				((CollectionVO) billVo.getParentVO()).setDcollectiondate(collectiondate);
				((CollectionVO) billVo.getParentVO()).setVtransactorid(_getOperator());
			} else {
				throw new BusinessException(NOTSHOW);
			}
			break;
		default:
			break;

		}
	}

	@Override
	protected void afterOnBoAction(int intBtn, AggregatedValueObject billVo)
			throws Exception {
		super.afterOnBoAction(intBtn, billVo);
		switch (intBtn) {
		case IFBMButton.ConsignBank_CancelTransact:
			// getBufferData().setCurrentRow(getBufferData().getCurrentRow());
			// int currentRow = getBufferData().getCurrentRow();
			// onBoRefresh();
			// batchCacheQuery();
			// getBufferData().setCurrentRow(currentRow);
			break;
		}
	}

	// @Override�����󣬲����״˴��Ĵ����кκ��壬��̨�ű��᷵������ts���ý���
	// protected void afterOnBoAction(int intBtn, AggregatedValueObject billVo)
	// throws Exception {
	// super.afterOnBoAction(intBtn, billVo);
	// switch (intBtn) {
	// case IFBMButton.ConsignBank_Disable:
	// case IFBMButton.ConsignBank_Transact:
	// case IFBMButton.ConsignBank_CancelTransact:
	// getUI().getBillListWrapper().setListHeadData(
	// getBufferData().getAllHeadVOsFromBuffer());
	// getBillCardPanelWrapper().setCardData(
	// getBufferData().getCurrentVO());
	// getUI().updateUI();
	// onBoRefresh();
	// break;
	// default:
	// break;
	// }
	// }

	/**
	 * @return Ʊ������״̬�Ƿ�Ϊ������Ѻ��
	 * @author zhouwb 2008-10-10
	 */
	protected boolean isHasImpawn() {
		// [Ʊ�ݱ���]����
		UIRefPane repane = (UIRefPane) getBillCardPanelWrapper().getBillCardPanel().getHeadItem(CollectionVO.PK_SOURCE).getComponent();
		// Ʊ������״̬
		String newStatus = (String) repane.getRefModel().getValue(RegisterVO.REGISTERSTATUS);

		return newStatus != null
				&& newStatus.equals(FbmStatusConstant.HAS_IMPAWN);
	}

	@Override
	protected void onBoSave() throws Exception {
		getBillCardPanelWrapper().getBillCardPanel().getBillData().dataNotNullValidate();

		if (isHasImpawn()) {
			if (getBillUI().showYesNoMessage(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add", "UPP3620ADD-000003")/*
																																 * @res
																																 * "Ʊ��Ϊ����Ѻ״̬���Ƿ񱣴棿"
																																 */) != UIDialog.ID_YES)
				return;
		}

		super.onBoSave();
	}

	@Override
	protected void onBoCopy() throws Exception {
		super.onBoCopy();
		// String billcode =((IUifService)
		// NCLocator.getInstance().lookup(IUifService.class.getName())).getBillNo(getUIController().getBillType(),
		// ClientEnvironment.getInstance().getCorporation().getPrimaryKey(),
		// null, null);
		// ((BillManageUI)getBillUI()).getBillCardPanel().getHeadItem(CollectionVO.VBILLNO).setValue(billcode);
		// ��ղ����ֶ�ֵ
		((BillManageUI) getBillUI()).getBillCardPanel().getHeadItem(CollectionVO.DCOLLECTIONDATE).setValue(null);
		((BillManageUI) getBillUI()).getBillCardPanel().getHeadItem(CollectionVO.DCONSIGNDATE).setValue(null);
		((BillManageUI) getBillUI()).getBillCardPanel().getHeadItem(CollectionVO.DDISABLEDATE).setValue(null);
		((BillManageUI) getBillUI()).getBillCardPanel().getHeadItem(CollectionVO.DTRANSACTDATE).setValue(null);
		((BillManageUI) getBillUI()).getBillCardPanel().getHeadItem(CollectionVO.VDISABLEMANID).setValue(null);
		((BillManageUI) getBillUI()).getBillCardPanel().getHeadItem(CollectionVO.VTRANSACTORID).setValue(null);
		((BillManageUI) getBillUI()).getBillCardPanel().getHeadItem(CollectionVO.DISABLENOTE).setValue(null);
		getUI().getBillCardPanel().getHeadItem(CollectionVO.PJLX).setValue(null);
		getUI().getBillCardPanel().getHeadItem(CollectionVO.FKDW).setValue(null);
		getUI().getBillCardPanel().getHeadItem(CollectionVO.FKDWYHZH).setValue(null);
		getUI().getBillCardPanel().getHeadItem(CollectionVO.FKKHH).setValue(null);
		getUI().getBillCardPanel().getHeadItem(CollectionVO.SPRQ).setValue(null);
		getUI().getBillCardPanel().getHeadItem(CollectionVO.CPRQ).setValue(null);
		getUI().getBillCardPanel().getHeadItem(CollectionVO.DQRQ).setValue(null);
		getUI().getBillCardPanel().getHeadItem(CollectionVO.PMJE).setValue(null);
		getUI().getBillCardPanel().getHeadItem(CollectionVO.YBBZ).setValue(null);
		getUI().getBillCardPanel().getHeadItem(CollectionVO.PK_SOURCE).setValue(null);
		getUI().getBillCardPanel().getHeadItem("pk_holderbank").setValue(null);
	}

	// @Override
	// protected UIDialog createQueryUI() {
	// return new RefTakenQueryConditionClient(getUI(), null, _getCorp()
	// .getPk_corp(), FbmBusConstant.FUNCODE_CONSIGN, _getOperator(),
	// FbmBusConstant.BILLTYPE_COLLECTION_UNIT, getUI()
	// .getRefTakenProccessor());
	// }

	/**
	 * ���ܣ���֤ʱ������֤�ˣ���֤���ڣ�ȡ����֤ʱ�������֤�ˣ���֤���� add by wangyg Date:2008-03-26
	 */
	@Override
	public void onBoActionElse(ButtonObject bo) throws Exception {
		String btncode = bo.getCode();
		AggregatedValueObject vo = getBufferData().getCurrentVO();
		if (btncode.equals(IFBMButton.Endore_CANCELVOUCHER_STR)) {// ȡ����֤
			((CollectionVO) vo.getParentVO()).setDvoucherdate(null);// ��֤����
			((CollectionVO) vo.getParentVO()).setVvoucherid(null);// ��֤��
		} else if (btncode.equals(IFBMButton.Endore_VOUCHER_STR)) {// ��֤

			((CollectionVO) vo.getParentVO()).setDvoucherdate(ClientInfo.getCurrentDate());// ��֤����
			((CollectionVO) vo.getParentVO()).setVvoucherid(ClientInfo.getUserPK());// ��֤��
		} else if (btncode.equals(IFBMButton.ConsignBank_Disable)) {
			Object obj = getUI().getBillCardWrapper().getBillCardPanel().getHeadItem(CollectionVO.DCOLLECTIONDATE);
			if (obj != null) {
				throw new BusinessException(
						nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add", "UPP3620ADD-000004")/*
																											 * @res
																											 * "�����������ڲ�Ϊ�գ���������"
																											 */);
			}
		}

		// ��Э����ֵ����д��CollectionVO��(Ʊ�ݱ��)
		if (vo != null && vo.getParentVO() != null) {
			CollectionVO collectionvo = (CollectionVO) vo.getParentVO();
			String fbmbillno = (String) getUI().getRefTakenProccessor().getValueByTakenInCard(CollectionVO.FBMBILLNO);
			collectionvo.setFbmbillno(fbmbillno);
		}

		super.onBoActionElse(bo);
	}

	/**
	 * <p>
	 * �����ж��������հ�����ͳ�ܻ���˽�еġ������ͳ�ܵķ����棬˽�еķ��ؼ�
	 * <p>
	 * ���ߣ�wangyg ���ڣ�2008-4-14
	 * 
	 * @return
	 */
	public boolean checkCreatBill() {
		boolean flag = false;
		AggregatedValueObject vo = getBufferData().getCurrentVO();
		CollectionVO collectionvo = (CollectionVO) vo.getParentVO();
		String opbilltype = collectionvo.getOpbilltype();
		if (FbmBusConstant.BILL_UNISTORAGE.equals(opbilltype)) {
			return true;
		}
		return flag;
	}

	@Override
	protected void buttonActionAfter(AbstractBillUI billUI, int intBtn)
			throws Exception {
		super.buttonActionAfter(billUI, intBtn);
		switch (intBtn) {
		case IBillButton.Edit:
			BillItem bi = getUI().getBillCardPanel().getHeadItem(CollectionVO.YBBZ);
			BillEditEvent e = new BillEditEvent(bi.getComponent(),
					bi.getValueObject(), "specialkey", -1, BillItem.HEAD);
			new YFBEditListerner(getUI(), CollectionVO.YBBZ, CollectionVO.YBBZ,
					CollectionVO.MONEYY, CollectionVO.MONEYF,
					CollectionVO.MONEYB, CollectionVO.FRATE,
					CollectionVO.BRATE, null, null, "specialkey").responseEditEvent(e);
			break;
		}
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

	@Override
	protected void onBoElse(int intBtn) throws Exception {
		switch (intBtn) {
		case IFBMButton.ConsignBank_LinkPlan:
			onQueryPlan(createQueryPlanVO(CollectionVO.COLLECTIONPLANITEM));
			break;
		case IFBMButton.ConsignBank_LinkGatherPlan:
			onQueryPlan(createQueryPlanVO(CollectionVO.FBMPLANITEM));
			break;
		}
		super.onBoElse(intBtn);
	}

	@Override
	protected IOBudgetQueryVO createQueryPlanVO(String planitemKey)
			throws Exception {
		IOBudgetQueryVO vo = super.createQueryPlanVO(planitemKey);
		CollectionVO collectionVO = (CollectionVO) getBufferData().getCurrentVO().getParentVO();
		vo.setPk_planitem((String) collectionVO.getAttributeValue(planitemKey));
		String pk_curr = (String) getUI().getBillCardWrapper().getBillCardPanel().getHeadItem(CollectionVO.YBBZ).getValueObject();
		vo.setPk_currtype(pk_curr);
		UFDate checkDate = collectionVO.getDcollectiondate();
		if (checkDate == null) {
			checkDate = _getDate();
		}
		vo.setCheckplandate(checkDate);
		return vo;
	}

}