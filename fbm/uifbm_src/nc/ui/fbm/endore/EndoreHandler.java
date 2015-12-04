package nc.ui.fbm.endore;

import nc.ui.fac.account.pub.RefTakenQueryConditionClient;
import nc.ui.fac.account.pub.RefTakenQueryFilterEditorFactory;
import nc.ui.fbm.endore.checker.OperateEndoreCheck;
import nc.ui.fbm.pub.FBMManageUI;
import nc.ui.fbm.pub.FBManageEventHandler;
import nc.ui.fbm.pub.IFBMButton;
import nc.ui.pub.ButtonObject;
import nc.ui.pub.beans.UIComboBox;
import nc.ui.pub.beans.UIDialog;
import nc.ui.trade.controller.IControllerBase;
import nc.ui.trade.manage.BillManageUI;
import nc.vo.fbm.discount.DiscountVO;
import nc.vo.fbm.endore.EndoreVO;
import nc.vo.fbm.pub.constant.FbmBusConstant;
import nc.vo.fts.pub.CenterUnitUtil;
import nc.vo.pub.AggregatedValueObject;
import nc.vo.pub.BusinessException;
import nc.vo.pub.lang.UFDouble;

public class EndoreHandler extends FBManageEventHandler 
{

	public EndoreHandler(BillManageUI billUI, IControllerBase control) {
		super(billUI, control);
		
	}
	
	public void onBoAdd(ButtonObject bo) throws Exception {
		
		String pk_corp = _getCorp().getPk_corp();
		OperateEndoreCheck.checkAdd(pk_corp);
		boolean product_flag = "Y".equals(OperateEndoreCheck.getParamValue(pk_corp));
		if (CenterUnitUtil.instance.isCorpCenter(pk_corp)) {
			super.onBoAdd(bo);
			if(product_flag)
			{
				((UIComboBox) getUI().getBillCardPanel().getHeadItem(DiscountVO.OPBILLTYPE)
						.getComponent()).setSelectedIndex(1);
				((UIComboBox) getUI().getBillCardPanel().getHeadItem(DiscountVO.OPBILLTYPE)
						.getComponent()).setEnabled(false);
			}
		}else
		{
			super.onBoAdd(bo);
			
		}
		
	}

	protected void onBoEdit() throws Exception {
		
		String pk_corp = _getCorp().getPk_corp();
		// checkOther();
		boolean product_flag = "Y".equals(OperateEndoreCheck.getParamValue(pk_corp));
		if (CenterUnitUtil.instance.isCorpCenter(pk_corp)) {
			super.onBoEdit();
			if (product_flag) {
				((UIComboBox) getUI().getBillCardPanel().getHeadItem(DiscountVO.OPBILLTYPE)
						.getComponent()).setSelectedIndex(1);
			}
		}else
		{
			super.onBoEdit();
			((UIComboBox) getUI().getBillCardPanel().getHeadItem(DiscountVO.OPBILLTYPE)
					.getComponent()).setSelectedIndex(0);
			((UIComboBox) getUI().getBillCardPanel().getHeadItem(DiscountVO.OPBILLTYPE)
					.getComponent()).setEnabled(false);
		}
		this.getUI().getBillCardPanel().getHeadItem(EndoreVO.VBILLNO).setEnabled(false);
	}
	
	
	//点击执行下按钮时执行。
	@Override
	public void onBoActionElse(ButtonObject bo) throws Exception {
		AggregatedValueObject vo = getBufferData().getCurrentVO();
		//对协带的值重新写到EndoreVO中(币种，金额)
		if (vo != null && vo.getParentVO() != null) {
			EndoreVO endorevo = (EndoreVO)vo.getParentVO();
			UFDouble moneyy = null;
			Object moneyyobj = getUI().getRefTakenProccessor().getValueByTakenInCard(EndoreVO.MONEYY);
			if (moneyyobj != null){
				moneyy = new UFDouble(moneyyobj.toString());
			}
			String pk_curr = "";
			Object pk_curr_obj = getUI().getRefTakenProccessor().getValueByTakenInCard(EndoreVO.PK_CURR);
			if(pk_curr_obj!=null){
				pk_curr = pk_curr_obj.toString();
			}
			String fbmbillno = (String)getUI().getRefTakenProccessor().getValueByTakenInCard(EndoreVO.FBMBILLNO);
			endorevo.setMoneyy(moneyy);
			endorevo.setPk_curr(pk_curr);
			endorevo.setFbmbillno(fbmbillno);
		}
		super.onBoActionElse(bo);
	}

	@Override
	protected void onBoElse(int intBtn) throws Exception {
		switch (intBtn) {
		case IFBMButton.Endore_LinkBill:
			if(getBufferData().getCurrentVO() == null){
				throw new BusinessException(nc.ui.ml.NCLangRes.getInstance().getStrByID("36201030","UPP36201030-000000")/* @res"请选择一条记录"*/);
			}
			String pk_busibill= getBufferData().getCurrentVO().getParentVO().getPrimaryKey();
			jumpToArapQuery(pk_busibill);
			break;
		case IFBMButton.Endore_Destroy:
		
			break;
	}
		super.onBoElse(intBtn);
	}
	
	
	@Override
	protected void onBoDelete() throws Exception {
		checkOther();
		super.onBoDelete();
	}

	@Override
	protected String getHeadCondition() {
		return super.getHeadCondition() + " and fbm_endore.pk_billtypecode='" + FbmBusConstant.BILLTYPE_ENDORE +"'";
	}
	
	
	//判断当前记录是否为收付报推式生成。如果为收付报生成会抛出异常。
	public void checkOther() throws Exception{
		String pk_corp = _getCorp().getPk_corp();
		AggregatedValueObject vo = getBufferData().getCurrentVO();
		EndoreVO endorevo = (EndoreVO)vo.getParentVO();
		String pk_endore = endorevo.getPrimaryKey();
		
		OperateEndoreCheck.checkOtherOP(pk_corp, pk_endore);
	}
	
	

	@Override
	protected UIDialog createQueryUI() {
		RefTakenQueryConditionClient dialog = new RefTakenQueryConditionClient(getBillUI(),createTemplateInof(getBillUI()));
		// dialog.registerFieldValueEelementEditorFactory(new
		// ReleaseFieldValueElementEditorFactory(dialog.getQueryContext()));
		dialog.registerFilterEditorFactory(new RefTakenQueryFilterEditorFactory((FBMManageUI)getBillUI(),((FBMManageUI)getBillUI()).getRefTakenProccessor(),dialog));
		return dialog;
	}
	
}
