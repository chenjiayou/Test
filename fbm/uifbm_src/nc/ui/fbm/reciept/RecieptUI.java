package nc.ui.fbm.reciept;

import java.util.Observer;

import nc.bs.logging.Logger;
import nc.itf.cdm.util.CommonUtil;
import nc.ui.fac.account.pub.IRefTakenProccessor;
import nc.ui.fbm.pub.FBMButtonFactory;
import nc.ui.fbm.pub.FBMClientInfo;
import nc.ui.fbm.pub.FBMManageUI;
import nc.ui.fbm.pub.IFBMButton;
import nc.ui.fbm.reckon.ReckonRefTakenProcessor;
import nc.ui.pub.beans.UIDialog;
import nc.ui.pub.beans.UIRefPane;
import nc.ui.pub.beans.constenum.DefaultConstEnum;
import nc.ui.pub.query.QueryConditionClient;
import nc.ui.trade.bill.AbstractManageController;
import nc.ui.trade.manage.ManageEventHandler;
import nc.vo.fbm.reckon.ReckonBVO;
import nc.vo.fbm.reckon.ReckonVO;
import nc.vo.fbm.storage.StoragePowerVO;
import nc.vo.pub.BusinessException;
import nc.vo.pub.query.QueryConditionVO;
import nc.ui.fbm.reciept.status.RecieptStatusEngine;

/**
 * 类功能说明： 调剂清算回单(成员单位) 日期：2007-10-31 程序员： wues
 */
public class RecieptUI extends FBMManageUI {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private RecieptController m_ctrl = null;
	private RecieptHandler m_handler = null;
	private Observer StatusEngine = getStatusEngine();
	/**
	 *
	 */
	public RecieptUI() {
		super();
		initComBox();
		getBufferData().deleteObserver(this);
		getBufferData().addObserver(StatusEngine);
		getBufferData().addObserver(this);
	}

	/**
	 * @param useBillSource
	 */
	public RecieptUI(Boolean useBillSource) {
		super(useBillSource);
	}

	/**
	 * @param pk_corp
	 * @param pk_billType
	 * @param pk_busitype
	 * @param operater
	 * @param billId
	 */
	public RecieptUI(String pk_corp, String pk_billType, String pk_busitype,
			String operater, String billId) {
		super(pk_corp, pk_billType, pk_busitype, operater, billId);
	}

	@Override
	protected AbstractManageController createController() {
		if (m_ctrl == null)
			m_ctrl = new RecieptController();
		return m_ctrl;
	}

	@Override
	protected ManageEventHandler createEventHandler() {
		if (m_handler == null)
			m_handler = new RecieptHandler(this, createController());
		return m_handler;
	}

	@Override
	protected void initSelfData() {
		super.initSelfData();
		initComBox();
		initRefPane();
	}

	protected void initRefPane() {
		((UIRefPane) getBillCardPanel().getBodyItem(ReckonBVO.PAYUNIT)
				.getComponent()).setAutoCheck(false);
	}

	private void initComBox() {
		// 初始化单据状态下拉框，只要已审核、已制证即可
		DefaultConstEnum[] statusList = nc.ui.fbm.pub.ComBoxUtil
				.getReliefStatus();
		getBillCardWrapper().initHeadComboBox(ReckonVO.VBILLSTATUS, statusList,
				false);
		getBillListWrapper().initHeadComboBox(ReckonVO.VBILLSTATUS, statusList,
				false);

		DefaultConstEnum[] reliefdirection = nc.ui.fbm.pub.ComBoxUtil
				.getReckonDirection();
		getBillCardWrapper().initBodyComboBox(ReckonBVO.DIRECTION,
				reliefdirection, false);
		getBillListWrapper().initBodyComboBox(ReckonBVO.DIRECTION,
				reliefdirection, false);
	}

	@Override
	public void setDefaultData() throws Exception {
		getBillCardPanel().getTailItem(ReckonVO.VOPERATORID).setValue(
				_getOperator());
		getBillCardPanel().getTailItem(ReckonVO.DOPERATEDATE).setValue(
				_getDate());
	}

	@Override
	protected IRefTakenProccessor createRefTakenProccessor() {
		return new ReckonRefTakenProcessor(getBillListPanel(),
				getBillCardPanel());
	}

	@Override
	protected void initBusinessButton() {
		addPrivateBusinessButton(FBMButtonFactory.getButtonVO(IFBMButton.FBM_VOUCHER));
		addPrivateBusinessButton(FBMButtonFactory.getButtonVO(IFBMButton.FBM_CANCELVOUCHER));
		addPrivateBusinessButton(FBMButtonFactory.getButtonVO(IFBMButton.FBM_TALLY));
		addPrivateBusinessButton(FBMButtonFactory.getButtonVO(IFBMButton.FBM_CANCELTALLY));
	}

	/**
	 * 设置清算单位查询条件默认为自己,且不允许修改
	 */
//	public void initQueryCondition(UIDialog qryClient)
//			throws java.lang.Exception {
//		super.initQueryCondition(qryClient);
//		QueryConditionClient queryDlg = (QueryConditionClient) qryClient;
//		QueryConditionVO[] conditons = queryDlg.getConditionDatas();
//
//		if (!CommonUtil.isNull(conditons)) {
//			for (int i = 0; i < conditons.length; i++) {
//				if (conditons[i].getFieldCode().equals("fbm_reckon.reckonunit")) {
//					String pk_corp = _getCorp().getPrimaryKey();
//					if (CommonUtil.isNull(pk_corp)) {
//						continue;
//					}
//					queryDlg.setDefaultValue("fbm_reckon.reckonunit",
//							FBMClientInfo.getCorpCubasdoc(pk_corp), null);
//					queryDlg.setConditionEditable("fbm_reckon.reckonunit",
//							false);
//				}
//			}
//		}
//	}

	private Observer getStatusEngine() {
		return new RecieptStatusEngine(this);
	}

	public StoragePowerVO getPower() {
		if(power==null){
			initPower();
		}
		return power;
	}
	 @Override
	protected String checkPrerequisite() {
		initPower();
		String pk_cubasdoc = getPower().getPk_cubasdoc();
		if(CommonUtil.isNull(pk_cubasdoc)){
			return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add","UPP3620ADD-000112")/*@res "当前公司未设置对应客商,无法进行调剂清算回单业务"*/;
		}
  	  return super.checkPrerequisite();
	}

		/**
	 *
	 * <p>
	 * 初始化权限
	 * <p>
	 * 作者：lpf
	 * 日期：2007-11-28
	 */
	private void initPower() {
		if(power==null){
			try {
				power = new StoragePowerVO();
				String corpPk = _getCorp().getPk_corp();
				power.setPk_corp(corpPk);
				power.setCurrDate(_getDate());
				power.setCurrUserPK(_getOperator());
				power.setSettleCenter(FBMClientInfo.isSettleCenter());
				power.setPk_cubasdoc(FBMClientInfo.getCommonCurCorpCubasdoc());
				power.setSettleUnit(FBMClientInfo.isSettleUnit(corpPk));
				power.setPk_settlecenter(FBMClientInfo.getSuperSettleCenter(corpPk));
			} catch (BusinessException e) {
				Logger.error(e.getMessage(),e);
			}

		}

	}


	 private StoragePowerVO power;

}