/**
 *
 */
package nc.ui.fbm.returnbill;

import java.util.List;

import nc.ui.fac.account.pub.RefTakenQueryConditionClient;
import nc.ui.fac.account.pub.RefTakenQueryFilterEditorFactory;
import nc.ui.fbm.pub.FBMClientInfo;
import nc.ui.fbm.pub.FBManageEventHandler;
import nc.ui.fbm.pub.IFBMButton;
import nc.ui.fbm.returnbill.listener.ReturnTypePlanItemListener;
import nc.ui.pub.ButtonObject;
import nc.ui.pub.beans.UIDialog;
import nc.ui.pub.beans.constenum.DefaultConstEnum;
import nc.ui.pub.pf.PfUtilClient;
import nc.ui.tm.framework.util.ClientInfo;
import nc.ui.trade.controller.IControllerBase;
import nc.ui.trade.manage.BillManageUI;
import nc.vo.fbm.pub.constant.FbmBusConstant;
import nc.vo.fbm.returnbill.ReturnBVO;
import nc.vo.fbm.returnbill.ReturnVO;
import nc.vo.fbm.storage.StorageVO;
import nc.vo.fp.pub.IOBudgetQueryVO;
import nc.vo.pub.AggregatedValueObject;
import nc.vo.pub.CircularlyAccessibleValueObject;
import nc.vo.trade.button.ButtonVO;
import nc.vo.trade.pub.IExAggVO;

/**
 * <p>
 * 集中退票动作执行类
 * <p>创建人：lpf
 * <b>日期：2007-8-31
 *
 */
public class ReturnBillEventHandler extends FBManageEventHandler {

	/**
	 * @param billUI
	 * @param control
	 */
	public ReturnBillEventHandler(BillManageUI billUI, IControllerBase control) {
		super(billUI, control);
		// TODO Auto-generated constructor stub
	}
	
//	@Override
//	protected UIDialog createQueryUI() {
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
	protected void onBoElse(int intBtn) throws Exception {
		switch(intBtn){
		case IFBMButton.Gather_LQuerySFBill:
			//查出所有退票的转出生成的收付报单据
			String pk_busibill= getBufferData().getCurrentVO().getParentVO().getPrimaryKey();
			jumpToArapQuery(pk_busibill);
			break;
		case IFBMButton.BTN_QUERY_PLAN:
			onQueryPlan(createQueryPlanVO(ReturnVO.UNITPLANITEM));
			break;
		}
		super.onBoElse(intBtn);
	}
	
	
	public void onBoActionElse(ButtonObject bo) throws Exception {
		// 获得数据
		AggregatedValueObject modelVo = getBufferData().getCurrentVOClone();

		int intBtn = 0;
		if (bo.getData() != null)
			intBtn = ((ButtonVO) bo.getData()).getBtnNo();
		if(IFBMButton.FBM_VOUCHER == intBtn){//制证
			((ReturnVO) modelVo.getParentVO()).setDvoucherdate(ClientInfo.getCurrentDate());//制证日期
			((ReturnVO) modelVo.getParentVO()).setVvoucherid(ClientInfo.getUserPK());// 制证人
		}else if(IFBMButton.FBM_CANCELVOUCHER == intBtn){//取消制证
			((ReturnVO) modelVo.getParentVO()).setDvoucherdate(null);//制证日期
			((ReturnVO) modelVo.getParentVO()).setVvoucherid(null);// 制证人
		}
		beforeOnBoAction(intBtn, modelVo);
		// *******************
		Object retObj = getBusinessAction().processAction(bo.getTag(), modelVo,
				getUIController().getBillType(),
				getBillUI()._getDate().toString(), getBillUI().getUserObject());

		if(intBtn == IFBMButton.ReturnBill_TransToSFBill){//如果是转出
			Object retMessage = ((List)retObj).get(1);
			if(retMessage!=null){
				getBillUI().showWarningMessage(retMessage.toString());
			}
			retObj = ((List)retObj).get(0);
		}
		
		if (PfUtilClient.isSuccess()) {
			if (retObj instanceof AggregatedValueObject) {
				AggregatedValueObject retVo = (AggregatedValueObject) retObj;
				afterOnBoAction(intBtn, retVo);
				CircularlyAccessibleValueObject[] childVos = getChildVO(retVo);
				if (childVos == null)
					modelVo.setParentVO(retVo.getParentVO());
				else
					modelVo = retVo;
			}
			// 更新列表
			getBufferData().setVOAt(getBufferData().getCurrentRow(), modelVo);
			getBufferData().setCurrentRow(getBufferData().getCurrentRow());
		}
		CircularlyAccessibleValueObject vo = null;
		if (getBufferData().getCurrentVO() != null) {
			vo = getBufferData().getCurrentVO().getParentVO();
			((BillManageUI)getBillUI()).getBillListWrapper().updateListVo(vo,
					getBufferData().getCurrentRow());

			//执行公式
		}
	}
	
	
	private CircularlyAccessibleValueObject[] getChildVO(
			AggregatedValueObject retVo) {
		CircularlyAccessibleValueObject[] childVos = null;
		if (retVo instanceof IExAggVO)
			childVos = ((IExAggVO) retVo).getAllChildrenVO();
		else
			childVos = retVo.getChildrenVO();
		return childVos;
	}

	@Override
	public void onBoAdd(ButtonObject bo) throws Exception {
		// TODO Auto-generated method stub
		super.onBoAdd(bo);
		if(!FBMClientInfo.isSettleCenter()){
			DefaultConstEnum[] returntype = nc.ui.fbm.pub.ComBoxUtil.getReturnType(true);
			getUI().getBillCardWrapper().initHeadComboBox(ReturnVO.RETURNTYPE,returntype, false);
		}
		
		firePlanItemListener();
	}

	
	@Override
	protected void onBoEdit() throws Exception {
		// TODO Auto-generated method stub
		super.onBoEdit();
		firePlanItemListener();
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
		dialog.registerFilterEditorFactory(new RefTakenQueryFilterEditorFactory((ReturnBillUI)getBillUI(),((ReturnBillUI)getBillUI()).getRefTakenProccessor(),dialog));
		return dialog;
	}
	
	/**
	 * 触发退票动作
	 * 
	 */
	private void firePlanItemListener(){
		new ReturnTypePlanItemListener(getUI(),ReturnVO.RETURNTYPE).responseEditEvent(null);
	}
	
	protected IOBudgetQueryVO createQueryPlanVO(String planitemKey)
		throws Exception {
		IOBudgetQueryVO vo = new IOBudgetQueryVO();
		
		vo.setSyscode(FbmBusConstant.SYSCODE_FBM);
		vo.setPk_billtypecode(getUI().getUIControl().getBillType());
		ReturnVO returnVo = (ReturnVO) getBufferData().getCurrentVO().getParentVO();
		ReturnBVO[] bvo = (ReturnBVO[])getBufferData().getCurrentVO().getChildrenVO();
		vo.setPk_selfcorp(returnVo.getPk_corp());
		
		vo.setPk_planitem((String) returnVo.getAttributeValue(planitemKey));
		String pk_curr = null;
		if(bvo!= null && bvo.length > 0){
			pk_curr = bvo[0].getPk_curr();
		}
		vo.setPk_currtype(pk_curr);
		vo.setCheckplandate(returnVo.getDapprovedate());
		return vo;
	}
}
