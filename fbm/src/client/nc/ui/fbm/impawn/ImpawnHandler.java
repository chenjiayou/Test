package nc.ui.fbm.impawn;

import nc.ui.fac.account.pub.RefTakenQueryConditionClient;
import nc.ui.fac.account.pub.RefTakenQueryFilterEditorFactory;
import nc.ui.fbm.pub.FBMManageUI;
import nc.ui.fbm.pub.FBManageEventHandler;
import nc.ui.fbm.pub.IFBMButton;
import nc.ui.pub.beans.UIDialog;
import nc.ui.pub.bill.BillCardPanel;
import nc.ui.trade.base.AbstractBillUI;
import nc.ui.trade.button.IBillButton;
import nc.ui.trade.controller.IControllerBase;
import nc.ui.trade.manage.BillManageUI;
import nc.vo.fbm.consignbank.CollectionVO;
import nc.vo.fbm.impawn.ImpawnVO;
import nc.vo.fbm.pub.constant.FbmBusConstant;
import nc.vo.pub.AggregatedValueObject;
import nc.vo.pub.BusinessException;
import nc.vo.pub.lang.UFDate;
import nc.vo.pub.lang.UFDouble;

/**
 * ���ܣ� UI�а�ť�������¼��ڴ��������� ���ڣ�2007-9-19 ����Ա��wues
 */
public class ImpawnHandler extends FBManageEventHandler {

	public ImpawnHandler(BillManageUI billUI, IControllerBase control) {
		super(billUI, control);
	}

	@Override
	protected UIDialog createQueryUI() {
		RefTakenQueryConditionClient dialog = new RefTakenQueryConditionClient(getBillUI(),createTemplateInof(getBillUI()));
//		dialog.registerFieldValueEelementEditorFactory(new ReleaseFieldValueElementEditorFactory(dialog.getQueryContext()));
		dialog.registerFilterEditorFactory(new RefTakenQueryFilterEditorFactory((FBMManageUI)getBillUI(),((FBMManageUI)getBillUI()).getRefTakenProccessor(),dialog));
		return dialog;	
	}

	@Override
	protected void onBoSave() throws Exception {
		getBillCardPanelWrapper().getBillCardPanel().getBillData()
				.dataNotNullValidate();

		String evaluateValue = (String) getBillCardPanelWrapper()
				.getBillCardPanel().getHeadItem(ImpawnVO.EVALUATEVALUE)
				.getValueObject();
		String monney = (String) getBillCardPanelWrapper().getBillCardPanel()
				.getHeadItem(ImpawnVO.MONEYY).getValueObject();
		validValue(evaluateValue, monney);

		super.onBoSave();
	}

	protected void onBoEdit() throws Exception {
		super.onBoEdit();
		this.getUI().getBillCardPanel().getHeadItem(ImpawnVO.VBILLNO).setEnabled(
				false);
	}

	// ҵ��ť��Ҫ�ڴ˷�������
	protected void beforeOnBoAction(int intBtn, AggregatedValueObject billVo)
			throws Exception {
		super.beforeOnBoAction(intBtn, billVo);
		// ������Ѻ�������ں���Ѻ���յ�λ
		switch (intBtn) {
		case IFBMButton.BTN_IMPAWN_BACK:
			if (((ImpawnUI) getBillUI()).getImpawnDlg().showModal() == UIDialog.ID_OK) {
				String unit = ((ImpawnUI) getBillUI()).getImpawnDlg()
						.getPaneUnit().getRefPK();

				String date = ((ImpawnUI) getBillUI()).getImpawnDlg()
						.getPaneDate().getRefPK();

				if (null == date || "".equals(date.trim())) {
					throw new BusinessException(nc.ui.ml.NCLangRes.getInstance().getStrByID("36201040","UPP36201040-000011")/* @res"��Ѻ�������ڲ�����Ϊ��"*/);
				}
				if (null == unit) {
					throw new BusinessException(nc.ui.ml.NCLangRes.getInstance().getStrByID("36201040","UPP36201040-000012")/* @res"��Ѻ�����˲�����Ϊ��"*/);
				}
				UFDate impawnDate = ((ImpawnVO) billVo.getParentVO())
						.getImpawndate();

				// (String)((ImpawnUI)
				// getBillUI()).getBillCardPanel().getHeadItem(ImpawnVO.IMPAWNDATE).getValueObject();

				validateDate(new UFDate(date), impawnDate);

				((ImpawnVO) billVo.getParentVO()).setImpawnbackunit(unit);
				((ImpawnVO) billVo.getParentVO()).setImpawnbackdate(new UFDate(
						(date)));

			} else {
				// ��ȡ��ʱ�˳�
				throw new BusinessException(NOTSHOW);
			}
			break;
		case IBillButton.Save:
			resetRefValue(billVo);
			break;
		case IBillButton.Edit:
			resetRefValue(billVo);
			break;
		}
	}

	private void validateDate(UFDate impawnDate, UFDate impawnBackDate)
			throws BusinessException {
		if (impawnDate.before(impawnBackDate)) {
			throw new BusinessException(nc.ui.ml.NCLangRes.getInstance().getStrByID("36201040","UPP36201040-000013")/* @res"��Ѻ��������Ӧ������Ѻ����"*/ + "\n");
		}
	}

	@Override
	protected void buttonActionAfter(AbstractBillUI billUI, int intBtn)
			throws Exception {
		super.buttonActionAfter(billUI, intBtn);
		switch (intBtn) {
		case IBillButton.Copy:
			getBillCardPanelWrapper().getBillCardPanel().getHeadItem(
					ImpawnVO.IMPAWNBACKUNIT).setValue("");
			getBillCardPanelWrapper().getBillCardPanel().getHeadTailItem(
					ImpawnVO.IMPAWNBACKDATE).setValue("");
			break;
		}
	}

	/**
	 * У��������ֵ��Ʊ����Ĵ�С
	 *
	 * @param evaluateValue
	 * @param monney
	 * @throws BusinessException
	 */
	private void validValue(String evaluateValue, String monney)
			throws BusinessException {
		if (null == evaluateValue || "".equals(evaluateValue.trim())) {
			throw new BusinessException(nc.ui.ml.NCLangRes.getInstance().getStrByID("36201040","UPP36201040-000014")/* @res"������ֵ����Ϊ��"*/);
		}
		if (null == monney || "".equals(monney.trim())) {
			throw new BusinessException(nc.ui.ml.NCLangRes.getInstance().getStrByID("36201040","UPP36201040-000015")/* @res"Ʊ�����Ϊ��"*/);
		}
		// ������ֵ����Ʊ����
		if (new UFDouble(evaluateValue).getDouble() > new UFDouble(monney)
				.getDouble()) {
			throw new BusinessException(nc.ui.ml.NCLangRes.getInstance().getStrByID("36201040","UPP36201040-000016")/* @res"������ֵ���ܴ���Ʊ����"*/);
		}
	}

	/**
	 * UAP�в���Я��������ֵ���ݵ���̨ ��Ҫ��������
	 *
	 * @param billVo
	 */
	private void resetRefValue(AggregatedValueObject billVo) {
		BillCardPanel billCardPanel = ((ImpawnUI) getBillUI())
				.getBillCardPanel();
		String pk_baseInfo = billCardPanel.getHeadItem(ImpawnVO.PK_BASEINFO)
				.getValueObject().toString();// �õ�PK_BaseInfo
		((ImpawnVO) billVo.getParentVO()).setPk_baseinfo(pk_baseInfo);
	}

	protected void onBusinessException(BusinessException e) {
		if (!NOTSHOW.equals(e.getMessage())) {
			super.onBusinessException(e);
		}
	}

	@Override
	protected void onBoCopy() throws Exception {
		// TODO Auto-generated method stub
		super.onBoCopy();
		getUI().getBillCardPanel().getHeadItem(ImpawnVO.PK_SOURCE).setValue(null);
		getUI().getBillCardPanel().getHeadItem(ImpawnVO.FBMBILLTYPE).setValue(null);
		getUI().getBillCardPanel().getHeadItem(ImpawnVO.GATHERDATE).setValue(null);
		getUI().getBillCardPanel().getHeadItem(ImpawnVO.INVOICEDATE).setValue(null);
		getUI().getBillCardPanel().getHeadItem(ImpawnVO.ENDDATE).setValue(null);
		getUI().getBillCardPanel().getHeadItem(ImpawnVO.INVOICEUNIT).setValue(null);
		getUI().getBillCardPanel().getHeadItem(ImpawnVO.BRATE).setValue(null);
		getUI().getBillCardPanel().getHeadItem(ImpawnVO.HOLDUNIT).setValue(null);
		getUI().getBillCardPanel().getHeadItem(ImpawnVO.FRATE).setValue(null);
		getUI().getBillCardPanel().getHeadItem(ImpawnVO.PAYUNIT).setValue(null);
		getUI().getBillCardPanel().getHeadItem(ImpawnVO.PAYBANK).setValue(null);
		getUI().getBillCardPanel().getHeadItem(ImpawnVO.MONEYB).setValue(null);
		getUI().getBillCardPanel().getHeadItem(ImpawnVO.MONEYF).setValue(null);
		getUI().getBillCardPanel().getHeadItem(ImpawnVO.PAYBANKACC).setValue(null);
		getUI().getBillCardPanel().getHeadItem(ImpawnVO.MONEYY).setValue(null);
		getUI().getBillCardPanel().getHeadItem(ImpawnVO.PK_CURR).setValue(null);
		
	}
	/**
	 * �������Я�����ɵ�Ҫ�ز�ѯʱ����
	 */
//	@Override
//	protected UIDialog createQueryUI() {
//		return new RefTakenQueryConditionClient(getUI(), null, _getCorp()
//				.getPk_corp(), FbmBusConstant.FUNCODE_IMPAWN, _getOperator(),
//				FbmBusConstant.BILLTYPE_IMPAWN, getUI().getRefTakenProccessor());
//	}

}