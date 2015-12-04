package nc.ui.fbm.accrule;

import nc.ui.fbm.pub.ComBoxUtil;
import nc.ui.fbm.pub.FBManageEventHandler;
import nc.ui.pub.beans.constenum.DefaultConstEnum;
import nc.ui.trade.controller.IControllerBase;
import nc.ui.trade.manage.BillManageUI;
import nc.vo.fbm.accrule.AccRuleVO;
import nc.vo.fbm.pub.constant.FbmBusConstant;

public class AccRuleHandler extends FBManageEventHandler {

	public AccRuleHandler(BillManageUI billUI, IControllerBase control) {
		super(billUI, control);
	}

	
	@Override
	protected void onBoEdit() throws Exception {
		//如果是清算单，也允许修改，由应用控制规则
		AccRuleVO accrulevo = (AccRuleVO)getBufferData().getCurrentVO().getParentVO();
//		if(accrulevo !=null){
//			if(FbmBusConstant.LIQUIDATE.equals(accrulevo.getAccrulename())){
//				throw new BusinessException("调剂清算设置账户不可修改");
//			}
//		}

		super.onBoEdit();
		if(FbmBusConstant.ACC_IN.equals(accrulevo.getAccname())){
			DefaultConstEnum[] accrulename = ComBoxUtil.getReckonInAccRef();
			((AccRuleUI)getUI()).getBillCardWrapper().initHeadComboBox(AccRuleVO.ACCREF,
					accrulename, false);
			((AccRuleUI)getUI()).getBillCardPanel().getHeadItem(AccRuleVO.ACCREF).setValue(accrulevo.getAccref());
		}else if(FbmBusConstant.ACC_OUT.equals(accrulevo.getAccname())){
			DefaultConstEnum[] accrulename = ComBoxUtil.getReckonOutAccRef();
			((AccRuleUI)getUI()).getBillCardWrapper().initHeadComboBox(AccRuleVO.ACCREF,
					accrulename, false);
			((AccRuleUI)getUI()).getBillCardPanel().getHeadItem(AccRuleVO.ACCREF).setValue(accrulevo.getAccref());
		}
		((AccRuleUI) getBillUI()).getBillCardPanel().getHeadItem("accrulecode").setEnabled(false);
		((AccRuleUI) getBillUI()).getBillCardPanel().getHeadItem("accrulename").setEnabled(false);
		((AccRuleUI) getBillUI()).getBillCardPanel().getHeadItem("accname").setEnabled(false);
	}


	@Override
	protected void onBoSave() throws Exception {
		// TODO Auto-generated method stub
		super.onBoSave();
		//恢复参照
		DefaultConstEnum[] accref = ComBoxUtil.getAccRef();
		// 初始化入账账户参照下拉框
		((AccRuleUI)getUI()).getBillCardWrapper().initHeadComboBox(AccRuleVO.ACCREF,
				accref, false);
	}


	@Override
	protected void onBoCancel() throws Exception {
		// TODO Auto-generated method stub
		super.onBoCancel();
		//恢复参照
		DefaultConstEnum[] accref = ComBoxUtil.getAccRef();
		// 初始化入账账户参照下拉框
		((AccRuleUI)getUI()).getBillCardWrapper().initHeadComboBox(AccRuleVO.ACCREF,
				accref, false);
	}
	
	
	
	

}
