/**
 *
 */
package nc.ui.fbm.storage.bankback;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nc.bs.framework.common.NCLocator;
import nc.bs.logging.Logger;
import nc.itf.cdm.util.CommonUtil;
import nc.itf.cdm.util.SqlUtil;
import nc.itf.uap.IUAPQueryBS;
import nc.jdbc.framework.processor.ColumnListProcessor;
import nc.jdbc.framework.processor.MapListProcessor;
import nc.ui.fbm.pub.FBMClientInfo;
import nc.ui.fbm.pub.IItemEditListener;
import nc.ui.fbm.pub.IUIChecker;
import nc.ui.fbm.pub.ItemNotNullChecker;
import nc.ui.fbm.pub.reffilter.BankWithoutSettleCenterFilter;
import nc.ui.fbm.storage.bankback.reffilter.GatherRefModelFilter;
import nc.ui.fbm.storage.bankkeep.BankKeepUI;
import nc.ui.fbm.storage.checker.BodyItemUniqueChecker;
import nc.ui.fbm.storage.checker.BodyNotEmptyChecker;
import nc.ui.fbm.storage.checker.KeepInviceDateChecker;
import nc.ui.fbm.storage.listener.InnerKeepSourceEditListener;
import nc.ui.pub.beans.MessageDialog;
import nc.ui.pub.beans.UIRefPane;
import nc.ui.pub.bill.IBillItem;
import nc.ui.pub.linkoperate.ILinkAddData;
import nc.ui.trade.bill.AbstractManageController;
import nc.ui.trade.button.IBillButton;
import nc.ui.trade.manage.ManageEventHandler;
import nc.vo.fbm.pub.constant.FbmBusConstant;
import nc.vo.fbm.storage.StorageBVO;
import nc.vo.fbm.storage.StoragePowerVO;
import nc.vo.fbm.storage.StorageVO;
import nc.vo.pub.AggregatedValueObject;
import nc.vo.pub.BusinessException;
import nc.vo.pub.SuperVO;
import nc.vo.trade.pub.IBillStatus;

/**
 * <p>
 * 银行领用单UI
 * <p>
 * 创建人：lpf <b>日期：2007-8-21
 *
 */
public class BankBackUI extends BankKeepUI {
	private BankBackController m_ctrl;
	private BankBackEventHandler m_handler;

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/**
	 *
	 */
	public BankBackUI() {
		// TODO Auto-generated constructor stub
		super();
	}

	/**
	 * @param useBillSource
	 */
	public BankBackUI(Boolean useBillSource) {
		super(useBillSource);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param pk_corp
	 * @param pk_billType
	 * @param pk_busitype
	 * @param operater
	 * @param billId
	 */
	public BankBackUI(String pk_corp, String pk_billType, String pk_busitype,
			String operater, String billId) {
		super(pk_corp, pk_billType, pk_busitype, operater, billId);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected AbstractManageController createController() {
		if (m_ctrl == null)
			m_ctrl = new BankBackController();
		return m_ctrl;
	}

	protected void initRefPane() {
		((UIRefPane) getBillCardPanel().getBodyItem(StorageBVO.PK_SOURCE).getComponent()).setMultiSelectedEnabled(true);
		((UIRefPane) getBillCardPanel().getBodyItem(StorageBVO.PK_SOURCE).getComponent()).setCacheEnabled(false);
		GatherRefModelFilter gatherFilter = new GatherRefModelFilter(this);
		addSqlFilter(false, StorageBVO.PK_SOURCE, gatherFilter);
		BankWithoutSettleCenterFilter bFilter = new BankWithoutSettleCenterFilter(this);
		addSqlFilter(true, StorageVO.KEEPUNIT, bFilter);

	}

	@Override
	public void setDefaultData() throws Exception {
		getBillCardPanel().getHeadItem(StorageVO.VBILLSTATUS).setValue(
				IBillStatus.FREE);
		getBillCardPanel().getHeadItem(StorageVO.PK_BILLTYPECODE).setValue(
				FbmBusConstant.BILLTYPE_BANKBACK);

		getBillCardPanel().getTailItem(StorageVO.VOPERATORID).setValue(
				_getOperator());
		getBillCardPanel().getTailItem(StorageVO.DOPERATEDATE).setValue(
				_getDate());
		getBillCardPanel().getHeadItem(StorageVO.PK_CORP).setValue(
				_getCorp().getPk_corp());
	}

	@Override
	protected ManageEventHandler createEventHandler() {
		if (m_handler == null)
			m_handler = new BankBackEventHandler(this, createController());
		return m_handler;
	}

	@Override
	protected ArrayList<IUIChecker> createValidator(int btnid) {
		ArrayList<IUIChecker> alChecker = null;
		switch (btnid) {
		case IBillButton.Save:
			alChecker = new ArrayList<IUIChecker>();
			alChecker.add(new ItemNotNullChecker(this));
			alChecker.add(new BodyNotEmptyChecker(this));
			alChecker.add(new BodyItemUniqueChecker(this,
					new String[] { StorageBVO.PK_SOURCE },
					new String[] { nc.ui.ml.NCLangRes.getInstance().getStrByID("36201015","UPT36201015-000021")/* @res"票据编号"*/ }));
			alChecker.add(new KeepInviceDateChecker(this,StorageVO.OUTPUTDATE));
			alChecker.add(new KeepBankChecker(this,StorageVO.KEEPUNIT));
		}
		return alChecker;
	}

	@Override
	protected IItemEditListener[] getBillItemEditListener() {
		return new IItemEditListener[]{
			new InnerKeepSourceEditListener(this,StorageBVO.PK_SOURCE,IBillItem.BODY),
			//new BankBackSourceListener(this,StorageBVO.PK_SOURCE,IBillItem.BODY)
		};
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
			return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add","UPP3620ADD-000117")/*@res "当前公司未设置对应客商,无法进行银行领用业务"*/;
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

	@Override
	public void doAddAction(ILinkAddData adddata) {
		// 填充托管银行字段
		AggregatedValueObject vo = (AggregatedValueObject) adddata
				.getUserObject();
		if (vo != null && !CommonUtil.isNull(vo.getChildrenVO())) {
			StorageBVO[] queryVos = (StorageBVO[]) vo.getChildrenVO();
			String[] sourcePks = new String[queryVos.length];
			for (int i = 0; i < queryVos.length; i++) {
				String sourcePk = queryVos[i].getPk_source();
				if (!CommonUtil.isNull(sourcePk)) {
					sourcePks[i] = sourcePk;
				}
			}
			// 不同存放地点
			String keepunit = null;
			String sqlin = SqlUtil.getInClause(sourcePks);
			String sql = "select keepunit,pk_source from fbm_storage s left join fbm_action a on s.pk_storage=a.pk_bill where pk_source "
					+ sqlin + " order by serialnum desc ";
			IUAPQueryBS query = NCLocator.getInstance().lookup(
					IUAPQueryBS.class);
			HashMap<String, String> map = new HashMap<String, String>();
			try {
				ArrayList ret = (ArrayList) query.executeQuery(sql,
						new MapListProcessor());
				if (ret != null && ret.size() > 0) {
					for (int i = 0; i < ret.size(); i++) {
						Map querymap = (Map) ret.get(i);
						if (querymap != null) {
							keepunit = (String) querymap.get("keepunit");
							String sourcePk = (String) querymap.get("pk_source");
							if(map.get(sourcePk)==null)
								map.put(sourcePk, keepunit);
						}
					}
				}
			} catch (BusinessException e) {
				Logger.error(e.getMessage(),e);
			}
			
			for (int i = 0; i < queryVos.length; i++) {
				String sourcePk = queryVos[i].getPk_source();
				String value = map.get(sourcePk);
				if(value!=null){
					queryVos[i].setKeepbank(value);
				}
			}
		}

		super.doAddAction(adddata);
	}
}