package nc.ui.fbm.discountapply;

import nc.itf.cdm.util.CommonUtil;
import nc.ui.fac.account.pub.RefTakenQueryConditionClient;
import nc.ui.fac.account.pub.RefTakenQueryFilterEditorFactory;
import nc.ui.fbm.pub.FBMManageUI;
import nc.ui.fbm.pub.FBManageEventHandler;
import nc.ui.fbm.pub.IFBMButton;
import nc.ui.fbm.pub.YFBEditListerner;
import nc.ui.pub.beans.MessageDialog;
import nc.ui.pub.beans.UIDialog;
import nc.ui.pub.bill.BillEditEvent;
import nc.ui.pub.bill.BillItem;
import nc.ui.tm.linkoperation.DefaultLinkEditParam;
import nc.ui.trade.base.AbstractBillUI;
import nc.ui.trade.business.HYPubBO_Client;
import nc.ui.trade.button.IBillButton;
import nc.ui.trade.controller.IControllerBase;
import nc.ui.trade.manage.BillManageUI;
import nc.ui.uap.sf.SFClientUtil;
import nc.vo.fbm.discount.DiscountVO;
import nc.vo.fbm.pub.constant.FbmBusConstant;
import nc.vo.pub.AggregatedValueObject;
import nc.vo.pub.BusinessException;
import nc.vo.pub.SuperVO;

/**
 *
 * <p>
 *	贴现申请界面EventHandler类
 * <p>创建人：bsrl
 * <b>日期：2007-09
 *
 */
public class DiscountApplyEventHandler extends FBManageEventHandler {

	public DiscountApplyEventHandler(BillManageUI billUI,
			IControllerBase control) {
		super(billUI, control);
	}

    @Override
	protected void onBoSave() throws Exception {
		getBillCardPanelWrapper().getBillCardPanel().getBillData().dataNotNullValidate();
		super.onBoSave();
	}

	@Override
	protected void onBoElse(int intBtn) throws Exception {
		super.onBoElse(intBtn);
		switch(intBtn){
		case IFBMButton.DiscountApply_Transact:
			DiscountApplyToDiscount();
			break;
		}
	}

	@Override
	protected void beforeOnBoAction(int intBtn, AggregatedValueObject billVo) throws java.lang.Exception {
		super.beforeOnBoAction(intBtn, billVo);
		switch(intBtn){
		case IFBMButton.DiscountApply_Transact:
			DiscountApplyToDiscount();
			break;
		}
    }

	/**
	 * <p>
	 * 跳转到贴现办理单界面
	 * <p>
	 * 作者：bsrl
	 * @throws BusinessException
	 */
	private void DiscountApplyToDiscount() throws BusinessException {
		AggregatedValueObject vo = getBufferData().getCurrentVO();
		if (vo == null || vo.getParentVO() == null)
			return;
		DiscountVO curvo = (DiscountVO)vo.getParentVO();

		SuperVO[] disvos = HYPubBO_Client.queryByCondition(DiscountVO.class, " isnull(dr,0) = 0 and pk_discount_app = '" +curvo.getPk_discount()+"'");
		if(disvos != null && disvos.length != 0) {
			MessageDialog.showHintDlg(getUI(),nc.ui.ml.NCLangRes.getInstance().getStrByID("36201025","UPP36201025-000000")/* @res"警告"*/,nc.ui.ml.NCLangRes.getInstance().getStrByID("36201025","UPP36201025-000001")/* @res"该申请单对应的办理单已经存在！"*/);
			return;
		}

		curvo.setVbillstatus(nc.vo.trade.pub.IBillStatus.FREE);
		curvo.setVapproveid(null);
		curvo.setDapprovedate(null);
		curvo.setVoperatorid(_getOperator());
		curvo.setDoperatedate(_getDate());
		curvo.setPk_billtypecode(FbmBusConstant.BILLTYPE_DISCOUNT_TRANSACT);
		curvo.setResult(FbmBusConstant.DISCOUNT_RESULT_SUCCESS);
		DefaultLinkEditParam link = new DefaultLinkEditParam(vo);
		link.setBillPK(vo.getParentVO().getPrimaryKey());
		link.setBillType(FbmBusConstant.BILLTYPE_DISCOUNT_APP);
		if(hasOpen(FbmBusConstant.FUNCODE_DISCOUNT_TRANSACT)) {
			MessageDialog.showHintDlg(getUI(),nc.ui.ml.NCLangRes.getInstance().getStrByID("36201025","UPP36201025-000002")/* @res"快捷错误"*/,nc.ui.ml.NCLangRes.getInstance().getStrByID("36201025","UPP36201025-000003")/* @res"调用的界面已经打开，请直接在该界面上操作，或者保存该界面上的数据后关闭界面重试！"*/);
			return;
		}
		SFClientUtil.openLinkedADDDialog(FbmBusConstant.FUNCODE_DISCOUNT_TRANSACT,
				getBillUI(), link, null);
	}

    @Override
	protected String getHeadCondition() {
    	String strwhere = super.getHeadCondition();
    	if(CommonUtil.isNull(strwhere)){
    		return  " (fbm_discount.pk_billtypecode = '" + FbmBusConstant.BILLTYPE_DISCOUNT_APP + "') ";
    	}else{
    		return strwhere + " and (fbm_discount.pk_billtypecode = '" + FbmBusConstant.BILLTYPE_DISCOUNT_APP + "') ";
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
		//String billcode =((IUifService) NCLocator.getInstance().lookup(IUifService.class.getName())).getBillNo(getUIController().getBillType(), ClientEnvironment.getInstance().getCorporation().getPrimaryKey(), null, null);
		//((BillManageUI)getBillUI()).getBillCardPanel().getHeadItem(DiscountVO.VBILLNO).setValue(billcode);
	}

	@Override
	protected UIDialog createQueryUI() {
//		return new RefTakenQueryConditionClient(getUI(), null, _getCorp()
//				.getPk_corp(), FbmBusConstant.FUNCODE_DISCOUNT_APP,
//				_getOperator(), FbmBusConstant.BILLTYPE_DISCOUNT_APP,getUI().getRefTakenProccessor());
		RefTakenQueryConditionClient dialog = new RefTakenQueryConditionClient(getBillUI(),createTemplateInof(getBillUI()));
//		dialog.registerFieldValueEelementEditorFactory(new ReleaseFieldValueElementEditorFactory(dialog.getQueryContext()));
		dialog.registerFilterEditorFactory(new RefTakenQueryFilterEditorFactory((FBMManageUI)getBillUI(),((FBMManageUI)getBillUI()).getRefTakenProccessor(),dialog));
		return dialog;
	}
	
	@Override
	protected void buttonActionAfter(AbstractBillUI billUI, int intBtn)
			throws Exception {
		super.buttonActionAfter(billUI, intBtn);
		if(intBtn == IBillButton.Edit) {
			BillItem bi = getUI().getBillCardPanel().getHeadItem(DiscountVO.YBBZ);
			BillEditEvent e = new BillEditEvent(bi.getComponent(), bi.getValueObject(),
					"specialkey", -1, BillItem.HEAD);
			new YFBEditListerner((FBMManageUI) getBillUI(), DiscountVO.YBBZ, DiscountVO.YBBZ,DiscountVO.MONEYY, DiscountVO.MONEYF,DiscountVO.MONEYB, DiscountVO.FRATE, DiscountVO.BRATE,null,null,"specialkey").responseEditEvent(e);
		
		}
	}
}