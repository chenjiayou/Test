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
 * 功能： UI中按钮触发的事件在此类中描述 日期：2007-9-19 程序员：wues
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

	// 业务按钮需要在此方法捕获
	protected void beforeOnBoAction(int intBtn, AggregatedValueObject billVo)
			throws Exception {
		super.beforeOnBoAction(intBtn, billVo);
		// 设置质押回收日期和质押回收单位
		switch (intBtn) {
		case IFBMButton.BTN_IMPAWN_BACK:
			if (((ImpawnUI) getBillUI()).getImpawnDlg().showModal() == UIDialog.ID_OK) {
				String unit = ((ImpawnUI) getBillUI()).getImpawnDlg()
						.getPaneUnit().getRefPK();

				String date = ((ImpawnUI) getBillUI()).getImpawnDlg()
						.getPaneDate().getRefPK();

				if (null == date || "".equals(date.trim())) {
					throw new BusinessException(nc.ui.ml.NCLangRes.getInstance().getStrByID("36201040","UPP36201040-000011")/* @res"质押回收日期不允许为空"*/);
				}
				if (null == unit) {
					throw new BusinessException(nc.ui.ml.NCLangRes.getInstance().getStrByID("36201040","UPP36201040-000012")/* @res"质押回收人不允许为空"*/);
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
				// 点取消时退出
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
			throw new BusinessException(nc.ui.ml.NCLangRes.getInstance().getStrByID("36201040","UPP36201040-000013")/* @res"质押回收日期应大于质押日期"*/ + "\n");
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
	 * 校验评估价值和票面金额的大小
	 *
	 * @param evaluateValue
	 * @param monney
	 * @throws BusinessException
	 */
	private void validValue(String evaluateValue, String monney)
			throws BusinessException {
		if (null == evaluateValue || "".equals(evaluateValue.trim())) {
			throw new BusinessException(nc.ui.ml.NCLangRes.getInstance().getStrByID("36201040","UPP36201040-000014")/* @res"评估价值不能为空"*/);
		}
		if (null == monney || "".equals(monney.trim())) {
			throw new BusinessException(nc.ui.ml.NCLangRes.getInstance().getStrByID("36201040","UPP36201040-000015")/* @res"票面金额不能为空"*/);
		}
		// 评估价值大于票面金额
		if (new UFDouble(evaluateValue).getDouble() > new UFDouble(monney)
				.getDouble()) {
			throw new BusinessException(nc.ui.ml.NCLangRes.getInstance().getStrByID("36201040","UPP36201040-000016")/* @res"评估价值不能大于票面金额"*/);
		}
	}

	/**
	 * UAP中不把携带出来的值传递到后台 需要重新设置
	 *
	 * @param billVo
	 */
	private void resetRefValue(AggregatedValueObject billVo) {
		BillCardPanel billCardPanel = ((ImpawnUI) getBillUI())
				.getBillCardPanel();
		String pk_baseInfo = billCardPanel.getHeadItem(ImpawnVO.PK_BASEINFO)
				.getValueObject().toString();// 得到PK_BaseInfo
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
	 * 避免根据携带生成的要素查询时出错
	 */
//	@Override
//	protected UIDialog createQueryUI() {
//		return new RefTakenQueryConditionClient(getUI(), null, _getCorp()
//				.getPk_corp(), FbmBusConstant.FUNCODE_IMPAWN, _getOperator(),
//				FbmBusConstant.BILLTYPE_IMPAWN, getUI().getRefTakenProccessor());
//	}

}