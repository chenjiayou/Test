package nc.ui.fbm.accrule;

import nc.bs.logging.Logger;
import nc.ui.fbm.pub.ComBoxUtil;
import nc.ui.fbm.pub.FBMManageUI;
import nc.ui.pub.beans.constenum.DefaultConstEnum;
import nc.ui.trade.base.IBillOperate;
import nc.ui.trade.bill.AbstractManageController;
import nc.ui.trade.button.IBillButton;
import nc.ui.trade.manage.ManageEventHandler;
import nc.vo.fbm.accrule.AccRuleVO;
import nc.vo.trade.pub.HYBillVO;

public class AccRuleUI extends FBMManageUI {

	private static final long serialVersionUID = 1L;

	public AccRuleUI() {
		super();
		initCombox();
		initData();
	}

	public AccRuleUI(Boolean useBillSource) {
		super(useBillSource);
	}

	public AccRuleUI(String pk_corp, String pk_billType, String pk_busitype,
			String operater, String billId) {
		super(pk_corp, pk_billType, pk_busitype, operater, billId);
	}

	@Override
	public String getTitle() {
		return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add","UPT36200860-000004")/*@res "内部账户入账设置"*/;
	}

	@Override
	protected AbstractManageController createController() {
		return new AccRuleController();
	}

	/**
	 * 初始化下拉列表西框.
	 */
	public void initCombox(){
		DefaultConstEnum[] accref = ComBoxUtil.getAccRef();
		// 初始化入账账户参照下拉框
		getBillCardWrapper().initHeadComboBox(AccRuleVO.ACCREF,
				accref, false);
		getBillListWrapper().initHeadComboBox(AccRuleVO.ACCREF,
				accref, false);

		DefaultConstEnum[] accmny = ComBoxUtil.getAccMny();
		// 初始化入账金额下拉框
		getBillCardWrapper().initHeadComboBox(AccRuleVO.ACCMNY,
				accmny, false);
		getBillListWrapper().initHeadComboBox(AccRuleVO.ACCMNY,
				accmny, false);

		//单据名称下拉列表
		DefaultConstEnum[] accrulename = ComBoxUtil.getAccRuleName();
		getBillCardWrapper().initHeadComboBox(AccRuleVO.ACCRULENAME,
				accrulename, false);
		getBillListWrapper().initHeadComboBox(AccRuleVO.ACCRULENAME,
				accrulename, false);

		//入账账户名称
		DefaultConstEnum[] accname = ComBoxUtil.getAccName();
		getBillCardWrapper().initHeadComboBox(AccRuleVO.ACCNAME,
				accname, false);
		getBillListWrapper().initHeadComboBox(AccRuleVO.ACCNAME,
				accname, false);

	}


	private void initData() {
		HYBillVO[] retvos = null;
		AccRuleVO[] queryvos = null;
		try {
			queryvos = (AccRuleVO[]) getBusiDelegator().queryByCondition(
					AccRuleVO.class, "isnull(dr,0)=0 ");
			if (queryvos == null) {
				getBufferData().clear();
				return;
			}
			if (queryvos != null && queryvos.length > 0) {
				retvos = new HYBillVO[queryvos.length];
				for (int i = 0; i < queryvos.length; i++) {
					retvos[i] = new HYBillVO();
					retvos[i].setParentVO(queryvos[i]);
					retvos[i].setChildrenVO(null);
				}
			}
			getBufferData().clear();
			setListHeadData(queryvos);
			getBufferData().addVOsToBuffer(retvos);
			setBillOperate(IBillOperate.OP_NOTEDIT);
			getBufferData().setCurrentRow(0);
		} catch (Exception e) {
			Logger.error("参数控制查询数据出错：" + e.getMessage());
		}
	}

	@Override
	protected ManageEventHandler createEventHandler() {
		// TODO Auto-generated method stub
		return new AccRuleHandler(this, getUIControl());
	}

	@Override
	public boolean isSaveAndCommitTogether() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected void initSelfData() {
		// TODO Auto-generated method stub
		super.initSelfData();

	}

	@Override
	public void updateButtons() {
		// TODO Auto-generated method stub
		super.updateButtons();
//		getButtonManager().getButton(IBillButton.Add).setEnabled(false);
//		getButtonManager().getButton(IBillButton.Delete).setEnabled(false);

		//非集团不允许修改
		String pk_corp = _getCorp().getPrimaryKey();
        if(!pk_corp.equals("0001")){
        	getButtonManager().getButton(IBillButton.Edit).setEnabled(false);
        }
	}

}