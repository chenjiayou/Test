/**
 *
 */
package nc.ui.fbm.accept;

import nc.bs.logging.Logger;
import nc.itf.cdm.util.CommonUtil;
import nc.ui.fac.account.pub.RefTakenQueryConditionClient;
import nc.ui.fac.account.pub.RefTakenQueryFilterEditorFactory;
import nc.ui.fbm.pub.DefaultLinkQueryParam;
import nc.ui.fbm.pub.FBMManageUI;
import nc.ui.fbm.pub.FBManageEventHandler;
import nc.ui.fbm.pub.IFBMButton;
import nc.ui.pub.beans.MessageDialog;
import nc.ui.pub.beans.UIDialog;
import nc.ui.pub.pf.PfUtilClient;
import nc.ui.tm.cmpbankacc.TMAccExceptionHandler;
import nc.ui.trade.base.AbstractBillUI;
import nc.ui.trade.businessaction.IPFACTION;
import nc.ui.trade.button.IBillButton;
import nc.ui.trade.controller.IControllerBase;
import nc.ui.trade.manage.BillManageUI;
import nc.ui.uap.sf.SFClientUtil;
import nc.vo.cc.control.ICcControl;
import nc.vo.cc.pub.billenum.CCBillEnum;
import nc.vo.cdm.exception.InnerAccountException;
import nc.vo.cmp.exception.CmpAuthorizationException;
import nc.vo.fbm.acceptbill.AcceptVO;
import nc.vo.fbm.ccinterface.CCDataAdapter;
import nc.vo.fbm.register.RegisterVO;
import nc.vo.fbm.storage.StorageVO;
import nc.vo.fp.pub.IOBudgetQueryVO;
import nc.vo.fts.account.IAccountConst;
import nc.vo.pub.AggregatedValueObject;
import nc.vo.pub.CircularlyAccessibleValueObject;
import nc.vo.pub.SuperVO;
import nc.vo.pub.lang.UFBoolean;
import nc.vo.pub.lang.UFDouble;
import nc.vo.trade.pub.HYBillVO;
import nc.vo.trade.pub.IBillStatus;
import nc.vo.trade.pub.IExAggVO;

/**
 * <p> 
 *
 * <p>创建人：lpf
 * <b>日期：2007-9-4
 *
 */
public class AcceptBillEventHandler extends FBManageEventHandler {

	/**
	 * @param billUI
	 * @param control
	 */
	public AcceptBillEventHandler(BillManageUI billUI, IControllerBase control) {
		super(billUI, control);
		// TODO Auto-generated constructor stub
	}


	@Override
	protected void onBoElse(int intBtn) throws Exception {
		// TODO Auto-generated method stub
		switch(intBtn){
		case IFBMButton.Gather_LQuerySFBill:
			String pk_busibill= getBufferData().getCurrentVO().getParentVO().getPrimaryKey();
			jumpToArapQuery(pk_busibill);
			break;
		case IFBMButton.QUERYRATION:
			onLinkQueryRation();
			break;
		case IFBMButton.BTN_QUERY_PLAN:
			onQueryPlan(createQueryPlanVO(AcceptVO.ACCEPTPLANITEM));
			break;
		}
		super.onBoElse(intBtn);
	}

	/**
	 *
	 * <p>
	 * 联查授信额度
	 * <p>
	 * 作者：lpf
	 * 日期：2007-9-19
	 */
	private void onLinkQueryRation() {
		fillAcceptBufferVo();
		AggregatedValueObject vo = getBufferData().getCurrentVO();
		if (vo == null || vo.getParentVO() == null)
			return;
		AcceptVO invoiceVo = (AcceptVO) vo.getParentVO();
		DefaultLinkQueryParam queryParam = new DefaultLinkQueryParam();
		queryParam.setBillPK(invoiceVo.getPrimaryKey());

		ICcControl cccontrol = CCDataAdapter.getInstance().acceptToCControlForApprove(invoiceVo);
		queryParam.setUserObject(cccontrol);

		SFClientUtil.openLinkedQueryDialog(CCBillEnum.BankRationNokeKey, getUI(), queryParam);
	}


	private AcceptVO fillAcceptBufferVo() {
		AggregatedValueObject bufferVo = getBufferData().getCurrentVO();
		AcceptVO acceptVo = (AcceptVO) bufferVo.getParentVO();
		try {
			AcceptVO uiVo = (AcceptVO) getUI().getVOFromUI().getParentVO();

			if (bufferVo != null && bufferVo.getParentVO() != null) {
				acceptVo.setEnddate(uiVo.getEnddate());
				acceptVo.setInvoicedate(uiVo.getInvoicedate());
				acceptVo.setInvoiceunit(uiVo.getInvoiceunit());
				acceptVo.setPayunit(uiVo.getPayunit());
				acceptVo.setReceiveunit(uiVo.getReceiveunit());
				acceptVo.setPk_curr(uiVo.getPk_curr());
				acceptVo.setPk_loanbank(uiVo.getPk_loanbank());
			}
		} catch (Exception e) {
			Logger.error(e.getMessage(),e);
		}
		return acceptVo;
	}

	/**
	 * 票据编号参照的特殊性，可以直接在参照上新增票据编号，所以在参照上设置了filter后，在保存时调用
	 * setAddNewOperate(isAdding(),billVO)方法setCurrentRow，此时单据状态仍然是编辑态，从参照中取值加入了过滤，从而取不出数据来
	 * 现在暂时改动了isAdding方法，提前把界面设置为disable，从而不走参照过滤
	 */
	public boolean isAdding() {
		// TODO Auto-generated method stub
		boolean ret =  super.isAdding();

		if(ret){
			getUI().getBillCardPanel().setEnabled(false);
			getUI().getBillListPanel().setEnabled(false);
		}

		return ret;
	}

	@Override
	protected void buttonActionAfter(AbstractBillUI billUI, int intBtn)
			throws Exception {
		super.buttonActionAfter(billUI, intBtn);
		switch(intBtn){
		case IBillButton.Edit:
			String pk_source = (String) getUI().getBillCardPanel().getHeadItem(AcceptVO.PK_SOURCE).getValueObject();
			if (CommonUtil.isNull(pk_source)) {
				return;
			}
			((AcceptBillUI)getUI()).filterbacksecAccount(pk_source);
			break;
		case IBillButton.Copy:
			getUI().fireCardAfterEdit(AcceptVO.PK_SOURCE);
			getUI().getBillCardPanel().getHeadItem(AcceptVO.HOLDERACC).setValue(null);
			getUI().getBillCardPanel().getHeadItem(AcceptVO.BACKSECACCOUNT).setValue(null);
			getUI().getBillCardPanel().getHeadItem(AcceptVO.BACKSECMONEY).setValue(null);
			break;
		}
	}

//	@Override
//	protected UIDialog createQueryUI() {
//		// TODO Auto-generated method stub
//		return new RefTakenQueryConditionClient(
//				getBillUI(),
//				null,
//				_getCorp().getPrimaryKey(),
//				getBillUI()._getModuleCode(),
//				_getOperator(),
//				getBillUI().getBusinessType(),
//				getBillUI().getNodeKey(),
//				((FBMManageUI)getBillUI()).getRefTakenProccessor());
//	}

	@Override
	public void onBoAudit() throws Exception {
		AggregatedValueObject modelVo = getBufferData().getCurrentVOClone();
		setCheckManAndDate(modelVo);
		// 如果状态一致则退出
		if (checkVOStatus(modelVo, new int[] { IBillStatus.CHECKPASS })) {
			Logger.debug("无效的鼠标处理机制");
			return;
		}
		fixAcceptVo(modelVo);

		beforeOnBoAction(IBillButton.Audit, modelVo);
		// *******************
//		Object returnVo = PfUtilClient.processActionFlow(getUI(),IPFACTION.APPROVE,getUIController().getBillType(),
//						getBillUI()._getDate().toString(),modelVo,getBillUI().getUserObject());

		Object returnVo = null;
		try {
			returnVo = PfUtilClient.runAction(getUI(), IPFACTION.APPROVE,
					getUIController().getBillType(), getBillUI()._getDate()
							.toString(), modelVo, getBillUI().getUserObject(),
					null, null, null);

		} catch (Exception e) {
			String mess = e.getMessage();
			if (null != mess && mess.indexOf("10003") != -1) {// 里面包含10003，说明是提示信息
				int ret = MessageDialog.showYesNoDlg(this.getUI(), null, mess+ nc.vo.ml.NCLangRes4VoTransl
						.getNCLangRes().getStrByID(
								"faccdmcode",
								"UPPFACCDMCODE-000034")/*
														 * @res "
														 * 是否继续？"
														 */);
				if (4 == ret) {// 用户点击yes
					modelVo.getParentVO().setAttributeValue("writeplan",
							UFBoolean.TRUE);
					returnVo = PfUtilClient.runAction(getUI(), IPFACTION.APPROVE,
							getUIController().getBillType(), getBillUI()._getDate()
									.toString(), modelVo, getBillUI().getUserObject(),
							null, null, null);
				}
			} else if (e instanceof InnerAccountException) {
				InnerAccountException ex = (InnerAccountException) e;

				if (IAccountConst.HINT.equals(ex.getErrType())) {// 提示
					int ret = getUI().showYesNoMessage(
							ex.getErrInfo()
									+ nc.vo.ml.NCLangRes4VoTransl
											.getNCLangRes().getStrByID(
													"faccdmcode",
													"UPPFACCDMCODE-000034")/*
																			 * @res "
																			 * 是否继续？"
																			 */);
					if (4 == ret) {// 用户未点击yes
						SuperVO superVO = (SuperVO) modelVo.getParentVO();
						if (superVO instanceof StorageVO) {
							superVO.setAttributeValue("writeInnerAcc",
									UFBoolean.TRUE);
						}
						returnVo = PfUtilClient.runAction(getUI(),
								IPFACTION.APPROVE, getUIController()
										.getBillType(), getBillUI()._getDate()
										.toString(), modelVo, getBillUI()
										.getUserObject(), null, null, null);
					}
				}
			} else if (e instanceof CmpAuthorizationException) {
				CmpAuthorizationException ex = (CmpAuthorizationException) e;

				boolean result = new TMAccExceptionHandler(getUI()).handleException(ex);
				if (result) {// 用户未点击yes
					SuperVO superVO = (SuperVO) modelVo.getParentVO();
					if (superVO instanceof RegisterVO) {
						superVO.setAttributeValue("writebankacc", UFBoolean.TRUE);
					} else if (superVO instanceof AcceptVO) {
						superVO.setAttributeValue("writebankacc", UFBoolean.TRUE);
					}
					modelVo.setParentVO(superVO);
					returnVo = PfUtilClient.runAction(getUI(), IPFACTION.APPROVE, getUIController().getBillType(), getBillUI()._getDate().toString(), modelVo, getBillUI().getUserObject(), null, null, null);
				}
				// }
			} else
				throw e;
		}

		AggregatedValueObject retVo = null;

		if (returnVo != null && returnVo instanceof Object[]) {
			Object[] returnMsg = (Object[]) returnVo;
			String ccReturnMsg = (String) returnMsg[0];
			retVo = (AggregatedValueObject) returnMsg[1];
			if (!CommonUtil.isNull(ccReturnMsg)) {
				MessageDialog.showHintDlg(getUI(), nc.ui.ml.NCLangRes
						.getInstance().getStrByID("36201505",
								"UPP36201505-000003")/* @res"提示" */,
						ccReturnMsg);
			}
		} else if (returnVo instanceof AggregatedValueObject
				|| returnVo instanceof HYBillVO) {
			retVo = (AggregatedValueObject) returnVo;
		}

		if (PfUtilClient.isSuccess()) {

			afterOnBoAction(IBillButton.Audit, retVo);
			CircularlyAccessibleValueObject[] childVos = getChildVO(retVo);
			if (childVos == null)
				modelVo.setParentVO(retVo.getParentVO());
			else
				modelVo = retVo;
			// 更新列表
			getBufferData().setVOAt(getBufferData().getCurrentRow(), modelVo);
			getBufferData().setCurrentRow(getBufferData().getCurrentRow());
		}
		((AcceptBillUI) getUI()).updateListVo();
	}

	private void setCheckManAndDate(AggregatedValueObject vo) throws Exception {
	// 放入审批日期、审批人
		vo.getParentVO().setAttributeValue(getBillField().getField_CheckDate(),
				getBillUI()._getDate());
		vo.getParentVO().setAttributeValue(getBillField().getField_CheckMan(),
				getBillUI()._getOperator());
	}

	/**
	 * 获得子表数据。 创建日期：(2004-3-11 17:44:14)
	 *
	 * @return nc.vo.pub.CircularlyAccessibleValueObject[]
	 */
	private CircularlyAccessibleValueObject[] getChildVO(
			AggregatedValueObject retVo) {
		CircularlyAccessibleValueObject[] childVos = null;
		if (retVo instanceof IExAggVO)
			childVos = ((IExAggVO) retVo).getAllChildrenVO();
		else
			childVos = retVo.getChildrenVO();
		return childVos;
	}

	/**
	 * 按钮m_boCancelAudit点击时执行的动作,如有必要，请覆盖.
	 */
	protected void onBoCancelAudit() throws Exception {
		// 获得数据
		AggregatedValueObject modelVo = getBufferData().getCurrentVOClone();
		// 放入反审批日期、审批人
		setCheckManAndDate(modelVo);
		// 如果状态一致则退出
		if (checkVOStatus(modelVo, new int[] { IBillStatus.FREE })) {
			Logger.debug("无效的鼠标处理机制");
			return;
		}

		fixAcceptVo(modelVo);

		beforeOnBoAction(IBillButton.CancelAudit, modelVo);
		// *******************

		Object returnVo = PfUtilClient.processActionFlow(getUI(),IPFACTION.UNAPPROVE,getUIController().getBillType(),
				getBillUI()._getDate().toString(),modelVo,getBillUI().getUserObject());

		String ccReturnMsg = null;
		AggregatedValueObject retVo = null;

		if(returnVo!=null&&returnVo instanceof Object[]){
			Object[] returnMsg = (Object[]) returnVo;
			ccReturnMsg = (String) returnMsg[0];
			retVo = (AggregatedValueObject) returnMsg[1];
			if(!CommonUtil.isNull(ccReturnMsg)){
				MessageDialog.showHintDlg(getUI(), nc.ui.ml.NCLangRes.getInstance().getStrByID("36201510","UPP36201510-000005")/* @res"提示"*/, ccReturnMsg);
			}
		}else if(returnVo instanceof AggregatedValueObject){
			retVo = (AggregatedValueObject) returnVo;
		}

		if (PfUtilClient.isSuccess()) {
			afterOnBoAction(IBillButton.CancelAudit, modelVo);
			CircularlyAccessibleValueObject[] childVos = getChildVO(retVo);
			if (childVos == null)
				modelVo.setParentVO(retVo.getParentVO());
			else
				modelVo = retVo;

			Integer intState = (Integer) modelVo.getParentVO()
					.getAttributeValue(getBillField().getField_BillStatus());
			if (intState.intValue() == IBillStatus.FREE) {
				modelVo.getParentVO().setAttributeValue(
						getBillField().getField_CheckMan(), null);
				modelVo.getParentVO().setAttributeValue(
						getBillField().getField_CheckDate(), null);
			}
			// 更新列表数据
			getBufferData().setVOAt(getBufferData().getCurrentRow(), modelVo);
			getBufferData().setCurrentRow(getBufferData().getCurrentRow());
		}
		((AcceptBillUI) getUI()).updateListVo();
	}


	/**
	 * <p>
	 *
	 * <p>
	 * 作者：lpf
	 * 日期：2007-11-5
	 * @param modelVo
	 */
	private void fixAcceptVo(AggregatedValueObject modelVo) {
		AcceptVO headVo = (AcceptVO) modelVo.getParentVO();
		headVo.setAttributeValue(AcceptVO.BILLMONEYY, new UFDouble((String)getUI().getBillCardPanel().getHeadItem(AcceptVO.BILLMONEYY).getValueObject()));
	}
	
	@Override
	protected IOBudgetQueryVO createQueryPlanVO(String planitemKey) throws Exception {
		IOBudgetQueryVO vo= super.createQueryPlanVO(planitemKey);
		AcceptVO  acceptVO = (AcceptVO)getBufferData().getCurrentVO().getParentVO();
		vo.setPk_planitem((String)acceptVO.getAttributeValue(planitemKey));
		vo.setPk_currtype(acceptVO.getPk_curr());
		
		vo.setCheckplandate(acceptVO.getDacceptdate());
		return vo;
	}


	/* （非 Javadoc）
	 * @see nc.ui.trade.bill.BillEventHandler#onBoQuery()
	 */
	@Override
	protected void onBoQuery() throws Exception {
		// TODO 自动生成方法存根
		super.onBoQuery();
	}


	@Override
	protected UIDialog createQueryUI() {
		RefTakenQueryConditionClient dialog = new RefTakenQueryConditionClient(getBillUI(),createTemplateInof(getBillUI()));
//		dialog.registerFieldValueEelementEditorFactory(new ReleaseFieldValueElementEditorFactory(dialog.getQueryContext()));
		dialog.registerFilterEditorFactory(new RefTakenQueryFilterEditorFactory((FBMManageUI)getBillUI(),((FBMManageUI)getBillUI()).getRefTakenProccessor(),dialog));
		return dialog;
	}

}