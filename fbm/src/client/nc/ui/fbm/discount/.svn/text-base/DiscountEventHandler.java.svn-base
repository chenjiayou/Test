package nc.ui.fbm.discount;

import nc.itf.cdm.util.CommonUtil;
import nc.ui.cdm.pub.print.CardPanelPRTS;
import nc.ui.cdm.pub.print.SingleListHeadPRTS;
import nc.ui.fac.account.pub.IRefTakenProccessor;
import nc.ui.fac.account.pub.RefTakenQueryConditionClient;
import nc.ui.fac.account.pub.RefTakenQueryFilterEditorFactory;
import nc.ui.fbm.pub.FBMManageUI;
import nc.ui.fbm.pub.FBManageEventHandler;
import nc.ui.fbm.pub.IFBMButton;
import nc.ui.fbm.pub.YFBEditListerner;
import nc.ui.pub.ButtonObject;
import nc.ui.pub.beans.UIDialog;
import nc.ui.pub.bill.BillEditEvent;
import nc.ui.pub.bill.BillItem;
import nc.ui.tm.framework.util.ClientInfo;
import nc.ui.trade.base.AbstractBillUI;
import nc.ui.trade.button.IBillButton;
import nc.ui.trade.controller.IControllerBase;
import nc.ui.trade.manage.BillManageUI;
import nc.vo.fbm.consignbank.CollectionVO;
import nc.vo.fbm.discount.DiscountVO;
import nc.vo.fbm.pub.constant.FbmBusConstant;
import nc.vo.fbm.register.RegisterVO;
import nc.vo.fp.pub.IOBudgetQueryVO;
import nc.vo.pub.AggregatedValueObject;
import nc.vo.pub.BusinessException;

/**
 * <p>
 *	帖现办理EventHandler类
 * <p>创建人：bsrl
 * <b>日期：2007-09
 *
 */
public class DiscountEventHandler extends FBManageEventHandler {

	@Override
	protected UIDialog createQueryUI() {
		RefTakenQueryConditionClient dialog = new RefTakenQueryConditionClient(getBillUI(),createTemplateInof(getBillUI()));
//		dialog.registerFieldValueEelementEditorFactory(new ReleaseFieldValueElementEditorFactory(dialog.getQueryContext()));
		dialog.registerFilterEditorFactory(new RefTakenQueryFilterEditorFactory((FBMManageUI)getBillUI(),((FBMManageUI)getBillUI()).getRefTakenProccessor(),dialog));
		return dialog;
	}

	public DiscountEventHandler(BillManageUI billUI, IControllerBase control) {
		super(billUI, control);
	}

    @Override
	protected String getHeadCondition() {
    	String strwhere = super.getHeadCondition();
    	if(CommonUtil.isNull(strwhere)){
    		return  " (fbm_discount.pk_billtypecode = '36GG') ";
    	}else{
    		return strwhere + " and (fbm_discount.pk_billtypecode = '36GG') ";
    	}
	}

	@Override
	protected void onBoSave() throws Exception {
		getBillCardPanelWrapper().getBillCardPanel().getBillData().dataNotNullValidate();
		super.onBoSave();
	}

	@Override
	protected void buttonActionAfter(AbstractBillUI billUI, int intBtn)
			throws Exception {
		super.buttonActionAfter(billUI, intBtn);
		if(intBtn == IBillButton.Edit) {
			if(getUI().getBillCardPanel().getHeadItem(DiscountVO.RESULT).getValueObject().toString().equalsIgnoreCase(FbmBusConstant.DISCOUNT_RESULT_DISABLE)){
				getUI().getBillCardPanel().getHeadItem(DiscountVO.MONEYY).setEnabled(false);
				getUI().getBillCardPanel().getHeadItem(DiscountVO.DISCOUNTCHARGE).setEnabled(false);
				getUI().getBillCardPanel().getHeadItem(DiscountVO.DISCOUNTINTEREST).setEnabled(false);
				getUI().getBillCardPanel().getHeadItem(DiscountVO.DISCOUNTYRATE).setEnabled(false);
				getUI().getBillCardPanel().getHeadItem(DiscountVO.RATEDAYNUM).setEnabled(false);
				getUI().getBillCardPanel().getHeadItem(DiscountVO.DISCOUNTDELAYDAYNUM).setEnabled(false);
			} else {
				getUI().getBillCardPanel().getHeadItem(DiscountVO.FAILREASON).setEnabled(false);
			}
			BillItem bi = getUI().getBillCardPanel().getHeadItem(CollectionVO.YBBZ);
			BillEditEvent e = new BillEditEvent(bi.getComponent(), bi.getValueObject(),
					"specialkey", -1, BillItem.HEAD);
			new YFBEditListerner((FBMManageUI) getBillUI(), DiscountVO.YBBZ, DiscountVO.YBBZ,DiscountVO.MONEYY, DiscountVO.MONEYF,DiscountVO.MONEYB, DiscountVO.FRATE, DiscountVO.BRATE,null,null,"specialkey").responseEditEvent(e);
		}
	}

	@Override
	protected void onBoCopy() throws Exception {
		super.onBoCopy();
		getUI().getBillCardPanel().getHeadItem(DiscountVO.PK_SOURCE).setValue(null);
		getUI().getBillCardPanel().getHeadItem(DiscountVO.PJLX).setValue(null);
		getUI().getBillCardPanel().getHeadItem(DiscountVO.SPRQ).setValue(null);
		getUI().getBillCardPanel().getHeadItem(DiscountVO.CPRQ).setValue(null);
		getUI().getBillCardPanel().getHeadItem(DiscountVO.DQRQ).setValue(null);
		getUI().getBillCardPanel().getHeadItem(DiscountVO.HPJE).setValue(null);
		getUI().getBillCardPanel().getHeadItem(DiscountVO.YBBZ).setValue(null);
		getUI().getBillCardPanel().getHeadItem(DiscountVO.HOLDERACC).setValue(null);
		getUI().getBillCardPanel().getHeadItem(DiscountVO.HOLDERBANK).setValue(null);
		getUI().getBillCardPanel().getHeadItem(DiscountVO.HPCDRMC).setValue(null);
		getUI().getBillCardPanel().getHeadItem(DiscountVO.HPCDRYHZH).setValue(null);
		getUI().getBillCardPanel().getHeadItem(DiscountVO.HPCDRKHH).setValue(null);
	}



//	@Override
//	protected UIDialog createQueryUI() {
//		return new RefTakenQueryConditionClient(getUI(), null, _getCorp()
//				.getPk_corp(), FbmBusConstant.FUNCODE_DISCOUNT_TRANSACT,
//				_getOperator(), FbmBusConstant.BILLTYPE_DISCOUNT_TRANSACT,getUI().getRefTakenProccessor());
//	}

	/**
	 * 功能：制证时生成制证人，制证日期，取消制证时，清空制证人，制证日期
	 * add by wangyg
	 * Date:2008-03-26
	 */
	@Override
	public void onBoActionElse(ButtonObject bo) throws Exception {
		String btncode = bo.getCode();
		AggregatedValueObject vo = getBufferData().getCurrentVO();
		if (btncode.equals(IFBMButton.Endore_CANCELVOUCHER_STR)) {// 取消制证
			((DiscountVO) vo.getParentVO()).setDvoucherdate(null);//制证日期
			((DiscountVO) vo.getParentVO()).setVvoucherid(null);//制证人
		}
		if (btncode.equals(IFBMButton.Endore_VOUCHER_STR)) {//制证
			
				((DiscountVO) vo.getParentVO()).setDvoucherdate(ClientInfo.getCurrentDate());//制证日期
				((DiscountVO) vo.getParentVO()).setVvoucherid(ClientInfo.getUserPK());// 制证人
		}
		
		//对协带的值重新写到EndoreVO中(票据编号)
		if (vo != null && vo.getParentVO() != null) {
			DiscountVO discountvo = (DiscountVO)vo.getParentVO();
			String fbmbillno = (String)getUI().getRefTakenProccessor().getValueByTakenInCard(DiscountVO.FBMBILLNO);
			discountvo.setFbmbillno(fbmbillno);
		}
		
		super.onBoActionElse(bo);
	}
	
	/**
	 * <p>
	 *  用于判断贴现办理单是统管还是私有的。如果是统管的返回真，私有的返回假
	 * <p>
	 * 作者：wangyg
	 * 日期：2008-4-14
	 * @return
	 */
	public boolean checkCreatBill(){
		boolean flag = false;
		AggregatedValueObject vo = getBufferData().getCurrentVO();
		DiscountVO discountvo = (DiscountVO)vo.getParentVO();
		String opbilltype = discountvo.getOpbilltype();
		if(FbmBusConstant.BILL_UNISTORAGE.equals(opbilltype)){
			return true;
		}
		return flag;
	}
	
//	@Override
//	protected void onBoPrint() throws Exception {
//		if (((BillManageUI) getBillUI()).isListPanelSelected()) {
//			IRefTakenProccessor listProc = new DiscountRefTakenProccessor_Print(((BillManageUI)getUI()).getBillListPanel(),((BillManageUI)getUI()).getBillCardPanel());
//			nc.ui.pub.print.IDataSource dataSource = new SingleListHeadPRTS(
//					getBillUI()._getModuleCode(), ((BillManageUI) getBillUI())
//							.getBillListPanel(), listProc);
//			nc.ui.pub.print.PrintEntry print = new nc.ui.pub.print.PrintEntry(
//					null, dataSource);
//			print.setTemplateID(getBillUI()._getCorp().getPrimaryKey(),
//					getBillUI()._getModuleCode(), getBillUI()._getOperator(),
//					getBillUI().getBusinessType(), getBillUI().getNodeKey());
//			if (print.selectTemplate() == 1)
//				print.preview();
//		} else {
//			nc.ui.pub.print.IDataSource dataSource = new CardPanelPRTS(
//					getBillUI()._getModuleCode(), getBillCardPanelWrapper()
//							.getBillCardPanel(), getUI()
//							.getRefTakenProccessor());
//			nc.ui.pub.print.PrintEntry print = new nc.ui.pub.print.PrintEntry(
//					null, dataSource);
//			print.setTemplateID(getBillUI()._getCorp().getPrimaryKey(),
//					getBillUI()._getModuleCode(), getBillUI()._getOperator(),
//					getBillUI().getBusinessType(), getBillUI().getNodeKey());
//			if (print.selectTemplate() == 1)
//				print.preview();
//			// super.onBoPrint();
//		}
//	}

	@Override
	protected void onBoElse(int intBtn) throws Exception {
		// TODO Auto-generated method stub
		switch(intBtn){
		case IFBMButton.BTN_QUERY_CHARGE_PLAN:
			onQueryPlan(createQueryPlanVO(DiscountVO.CHARGEPLANITEM));
			break;
		case IFBMButton.BTN_QUERY_INTEREST_PLAN:
			onQueryPlan(createQueryPlanVO(DiscountVO.INTERESTPLANITEM));
			break;
		case IFBMButton.BTN_QUERY_BALANCE_PLAN:
			onQueryPlan(createQueryPlanVO(DiscountVO.BALANCEPLANITEM));
			break;
		case IFBMButton.Discount_LinkGatherPlan:
			onQueryPlan(createQueryPlanVO(DiscountVO.FBMPLANITEM));
			break;
		}
		super.onBoElse(intBtn);
	}
	
	@Override
	protected IOBudgetQueryVO createQueryPlanVO(String planitemKey) throws Exception {
		// TODO Auto-generated method stub
		IOBudgetQueryVO vo= super.createQueryPlanVO(planitemKey);
		DiscountVO discountvo = (DiscountVO)getBufferData().getCurrentVO().getParentVO();
		vo.setPk_planitem((String)discountvo.getAttributeValue(planitemKey));
		String pk_curr = (String)getUI().getBillCardWrapper().getBillCardPanel().getHeadItem(DiscountVO.YBBZ).getValueObject();
		vo.setPk_currtype(pk_curr);
		vo.setCheckplandate(discountvo.getDdiscountdate());
		return vo;
	}

}