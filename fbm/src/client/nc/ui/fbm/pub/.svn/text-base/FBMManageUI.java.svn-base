/**
 *
 */
package nc.ui.fbm.pub;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;

import nc.bs.logging.Logger;
import nc.itf.cdm.util.CommonUtil;
import nc.itf.cdm.util.CurrencyPublicUtil;
import nc.ui.bd.def.ManageDefShowUtil;
import nc.ui.bd.ref.AbstractRefModel;
import nc.ui.fac.account.pub.CDMUITableSearchDialog;
import nc.ui.fac.account.pub.IRefTakenProccessor;
import nc.ui.fac.account.pub.RefTakenProccessor;
import nc.ui.fac.account.pub.RenderInitTools;
import nc.ui.pub.beans.MessageDialog;
import nc.ui.pub.beans.UIRefPane;
import nc.ui.pub.beans.UITable;
import nc.ui.pub.bill.BillEditEvent;
import nc.ui.pub.bill.BillItem;
import nc.ui.pub.linkoperate.ILinkAdd;
import nc.ui.pub.linkoperate.ILinkAddData;
import nc.ui.pub.linkoperate.ILinkApprove;
import nc.ui.pub.linkoperate.ILinkApproveData;
import nc.ui.pub.linkoperate.ILinkQuery;
import nc.ui.pub.linkoperate.ILinkQueryData;
import nc.ui.tm.framework.ref.AbstractTMRefModel;
import nc.ui.tm.framework.ref.filter.BillItemRefModelFilter;
import nc.ui.tm.framework.util.BillCardPanelTools;
import nc.ui.tm.framework.util.BillListPanelTools;
import nc.ui.tm.framework.util.ClientInfo;
import nc.ui.tm.framework.util.CurrencyClientUtil;
import nc.ui.trade.base.IBillOperate;
import nc.ui.trade.bill.AbstractManageController;
import nc.ui.trade.bill.BillTemplateWrapper;
import nc.ui.trade.bill.DefaultDefShowStrategyByBillItem;
import nc.ui.trade.bsdelegate.BDBusinessDelegator;
import nc.ui.trade.bsdelegate.BusinessDelegator;
import nc.ui.trade.businessaction.IBusinessActionType;
import nc.ui.trade.button.IBillButton;
import nc.ui.trade.manage.BillManageUI;
import nc.ui.trade.manage.ManageEventHandler;
import nc.vo.fbm.consignbank.CollectionVO;
import nc.vo.fbm.discount.DiscountVO;
import nc.vo.fbm.impawn.ImpawnVO;
import nc.vo.fbm.outer.OuterVO;
import nc.vo.fbm.proxy.OuterProxy;
import nc.vo.fbm.pub.constant.FbmBusConstant;
import nc.vo.fbm.storage.StorageBVO;
import nc.vo.fbm.storage.StorageVO;
import nc.vo.pub.AggregatedValueObject;
import nc.vo.pub.BusinessException;
import nc.vo.pub.CircularlyAccessibleValueObject;
import nc.vo.pub.SuperVO;
import nc.vo.trade.button.ButtonVO;
import nc.vo.trade.pub.HYBillVO;

/**
 * 
 * <p>
 * FBM公用UI
 * <p>
 * 创建人：lpf <b>日期：2007-8-10
 * 
 */
public class FBMManageUI extends BillManageUI implements ILinkAdd, ILinkQuery,
		ILinkApprove, nc.ui.glpub.IUiPanel {
	private IRefTakenProccessor refTakenProccessor = null;
	private HashMap<String, ArrayList<IUIChecker>> validatorMap = null;
	private CurrencyClientUtil currencyutil = null;
	private String TZ_flag = "*";// 台帐联查传送的PK标志

	private nc.ui.glpub.IParent m_parent = null;
	private FBMManageUI parentUI = null;// 跳转到新增界面的初始界面 UI

	private FbmBatchQueryDlg batchQuery = new FbmBatchQueryDlg(this);

	public nc.ui.glpub.IParent getm_parent() {
		return m_parent;
	}

	public void showMe(nc.ui.glpub.IParent parent) {
		// TODO 自动生成方法存根
		parent.getUiManager().removeAll();
		parent.getUiManager().add(this, this.getName());
		m_parent = parent;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private BillEditEventDispatcher billEditEventDispatcher = new BillEditEventDispatcher();

	/**
	 * 
	 */
	public FBMManageUI() {
		// TODO Auto-generated constructor stub
		super();
		initialize();
	}

	/**
	 * @param useBillSource
	 */
	public FBMManageUI(Boolean useBillSource) {
		super(useBillSource);
		// TODO Auto-generated constructor stub
		initialize();
	}

	/**
	 * @param pk_corp
	 * @param pk_billType
	 * @param pk_busitype
	 * @param operater
	 * @param billId
	 */
	public FBMManageUI(String pk_corp, String pk_billType, String pk_busitype,
			String operater, String billId) {
		super(pk_corp, pk_billType, pk_busitype, operater, billId);
		// TODO Auto-generated constructor stub
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nc.ui.trade.manage.BillManageUI#createController()
	 */
	@Override
	protected AbstractManageController createController() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * <p>
	 * 初始化界面公用信息
	 * <p>
	 * 作者：lisg <br>
	 * 日期：2006-11-24
	 */
	protected void initialize() {
		// 初始化listerner
		addItemEditListener();

		// 初始化tableCellRender
		initTableCellRender();

		initListRefPane();

		addValidator();

		initCurrencyutil();

		initUITableSearchDialog();

		initMouseListener();
		
		getBillListPanel().getParentListPanel().addRowSelectionChangeListener(new FBMListHeadDataRefTakenListener(getBillListPanel(),getRefTakenProccessor()));
	}

	/**
	 * 初始化列表界面监听
	 */
	private void initMouseListener() {
		MouseListener[] listeners = getBillListPanel().getParentListPanel().getTable().getTableHeader().getMouseListeners();

		for (MouseListener listener : listeners) {
			getBillListPanel().getParentListPanel().getTable().getTableHeader().removeMouseListener(listener);
		}

		getBillListPanel().getParentListPanel().getTable().getTableHeader().addMouseListener(new FBMColumnRenderListener(
				getBillListPanel(), getBillCardPanel(), getRefTakenProccessor()));
		// getBillListPanel().getParentListPanel().getTable().getTableHeader().addMouseListener(new
		// FBMLineRenderListener(
		// getBillListPanel(), getBillCardPanel(), getRefTakenProccessor()));
		for (MouseListener listener : listeners) {
			getBillListPanel().getParentListPanel().getTable().getTableHeader().addMouseListener(listener);
		}
	}

	private void initListRefPane() {
		BillCardPanelTools.initBillCardPanelRef(getBillCardPanel());
		BillListPanelTools.initBillListPanelRef(getBillListPanel());
	}

	private void addValidator() {
		// TODO Auto-generated method stub
		int[] btns = getUIControl().getCardButtonAry();
		for (int i = 0; i < btns.length; i++) {
			if (createValidator(btns[i]) != null)
				addValidators(btns[i], createValidator(btns[i]));
		}
	}

	private void addValidators(int buttonid, ArrayList<IUIChecker> alValidator) {
		// TODO Auto-generated method stub
		if (validatorMap == null)
			validatorMap = new HashMap<String, ArrayList<IUIChecker>>();
		validatorMap.put(new Integer(buttonid).toString(), alValidator);
	}

	protected ArrayList<IUIChecker> createValidator(int btnid) {
		// TODO Auto-generated method stub
		return null;
	}

	public HashMap<String, ArrayList<IUIChecker>> getValidatorMap() {
		return validatorMap;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seenc.ui.trade.manage.BillManageUI#setBodySpecialData(nc.vo.pub.
	 * CircularlyAccessibleValueObject[])
	 */
	@Override
	public void setBodySpecialData(CircularlyAccessibleValueObject[] vos)
			throws Exception {
		// ((RefTakenProccessor)getRefTakenProccessor()).getCacheMap().clear();
		// TODO Auto-generated method stub
		if (getRefTakenProccessor() == null) {
			return;
		}
		if (!isListPanelSelected()) {// 只有选择卡片界面时才调用携带
			getRefTakenProccessor().takenRefValueForQuery();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seenc.ui.trade.manage.BillManageUI#setHeadSpecialData(nc.vo.pub.
	 * CircularlyAccessibleValueObject, int)
	 */
	@Override
	protected void setHeadSpecialData(CircularlyAccessibleValueObject vo,
			int intRow) throws Exception {
		// TODO Auto-generated method stub
		if (getRefTakenProccessor() == null) {
			return;
		}
		getRefTakenProccessor().takenRefValueForQuery();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nc.ui.trade.base.AbstractBillUI#initSelfData()
	 */
	@Override
	protected void initSelfData() {
		// 设置默认执行编辑公式
		getBillCardPanel().setAutoExecHeadEditFormula(true);
		initButtonVisible();
		initDefItem();
		// 屏蔽表体右键菜单。
		getBillCardPanel().getBodyPanel().getPmBody().setEnabled(false);
		getBillCardPanel().getBodyPanel().getPmBody().remove(getBillCardPanel().getBodyPanel().getMiCopyLine());
		getBillCardPanel().getBodyPanel().getPmBody().remove(getBillCardPanel().getBodyPanel().getMiAddLine());
		getBillCardPanel().getBodyPanel().getPmBody().remove(getBillCardPanel().getBodyPanel().getMiInsertLine());
		getBillCardPanel().getBodyPanel().getPmBody().remove(getBillCardPanel().getBodyPanel().getMiPasteLine());
		getBillCardPanel().getBodyPanel().getPmBody().remove(getBillCardPanel().getBodyPanel().getMiPasteLineToTail());
		getBillCardPanel().getBodyPanel().getPmBody().remove(getBillCardPanel().getBodyPanel().getMiDelLine());
	}

	protected void initButtonVisible() {
		if (getButtonManager().getButton(IBillButton.CopyLine) != null) {
			getButtonManager().getButton(IBillButton.CopyLine).setVisible(false);
		}
		if (getButtonManager().getButton(IBillButton.PasteLine) != null) {
			getButtonManager().getButton(IBillButton.PasteLine).setVisible(false);
		}
		if (getButtonManager().getButton(IBillButton.InsLine) != null) {
			getButtonManager().getButton(IBillButton.InsLine).setVisible(false);
		}
		if (getButtonManager().getButton(IBillButton.PasteLinetoTail) != null) {
			getButtonManager().getButton(IBillButton.PasteLinetoTail).setVisible(false);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nc.ui.trade.base.AbstractBillUI#setDefaultData()
	 */
	@Override
	public void setDefaultData() throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void afterEdit(BillEditEvent e) {
		if (getRefTakenProccessor() != null) {
			if (e.getRow() < 0) {
				getRefTakenProccessor().takenCardRefValue(e.getKey(), true);
			} else {
				getRefTakenProccessor().takenListRefValue(e.getKey(), e.getRow());
			}
		}
		billEditEventDispatcher.responseEditEvent(e);

		// 处理中心贴现和托收，如果采用了GatherBusRefModel作为参照的话设置票据类型
		// if ((e.getSource() instanceof UIRefPane)) {
		// // UIRefPanel，并且为贴现或背书的参照
		// if (((UIRefPane) e.getSource()).getRefModel() instanceof
		// GatherBusRefModel
		// /*|| ((UIRefPane) e.getSource()).getRefModel() instanceof
		// EndoreRefModel*/) {
		// if ((FBMClientInfo.isSettleCenter())
		// && DiscountVO.PK_SOURCE.equals(e.getKey())) {
		// String pk_corp = (String) ((UIRefPane) e.getSource())
		// .getRefModel().getValue("pk_corp");
		// String cur_pk_corp = FBMClientInfo.getCorpPK();
		// if (cur_pk_corp.equals(pk_corp)) {// 设置为私有
		// ((UIComboBox) getBillCardPanel().getHeadItem(
		// DiscountVO.OPBILLTYPE).getComponent())
		// .setSelectedItem(FbmBusConstant.BILL_PRIVACY);
		// } else {
		// ((UIComboBox) getBillCardPanel().getHeadItem(
		// DiscountVO.OPBILLTYPE).getComponent())
		// .setSelectedItem(FbmBusConstant.BILL_UNISTORAGE);
		// }
		// }
		// }
		// }

	}

	protected IItemEditListener[] getBillItemEditListener() {
		return null;
	}

	private void addItemEditListener() {
		IItemEditListener[] editlisteners = getBillItemEditListener();
		billEditEventDispatcher.addEditListeners(editlisteners);
	}

	public boolean onClosing() {
		boolean b = super.onClosing();
		if (b) {
			if (getBillOperate() == IBillOperate.OP_ADD) {
				String billno = null;
				BillItem bi = getBillCardPanel().getHeadItem("vbillno");
				if (bi != null) {
					billno = (String) bi.getValueObject();
				}
				if (billno != null) {
					try {
						OuterProxy.getBillCodeRuleService().returnBillCodeOnDelete(ClientInfo.getCorpPK(), getUIControl().getBillType(), billno, null);
					} catch (Exception e) {
						Logger.error(e.getMessage(), e);
					}
				}
			}
		}
		if (parentUI != null) {
			ManageEventHandler handler = parentUI.getManageEventHandler();
			if (handler instanceof FBManageEventHandler) {
				try {
					((FBManageEventHandler) handler).onBoRefresh();
				} catch (Exception e) {
					Logger.error(e.getMessage(), e);
				}
			}
		}
		return b;

	}

	public final IRefTakenProccessor getRefTakenProccessor() {
		if (refTakenProccessor == null) {
			refTakenProccessor = createRefTakenProccessor();
		}
		return refTakenProccessor;
	}

	protected IRefTakenProccessor createRefTakenProccessor() {
		return null;
	}

	/**
	 * <p>
	 * 初始化列表状态下的TableCellRender
	 * <p>
	 * 作者：lisg <br>
	 * 日期：2006-11-24
	 */
	protected void initTableCellRender() {
		RenderInitTools renderInitTools = new RenderInitTools(
				getBillListPanel(), getBillCardPanel());
		renderInitTools.setRefTakenProccessor(getRefTakenProccessor());
		renderInitTools.setShowCode(false);
		// 给列表头加render
		renderInitTools.resetListHeadCellRenderer();
		// 给列表体加render
		renderInitTools.resetListBodyCellRenderer();
		// 给卡片界面的list加render
		renderInitTools.resetCardBodyCellRenderer();
	}

	@Override
	protected String getBillNo() throws Exception {
		return OuterProxy.getBillCodeRuleService().getBillCode_RequiresNew(createController().getBillType(), _getCorp().getPrimaryKey(), null, null);
	}

	private CurrencyClientUtil initCurrencyutil() {
		if (getCurrencyItemKeys() == null || getCurrencyItemKeys().length == 0)
			return null;

		if (currencyutil == null)
			currencyutil = new CurrencyClientUtil(getCurrencyItemKeys());

		return currencyutil;
	}

	protected String[] getCurrencyItemKeys() {
		return null;
	}

	public boolean isSaveAndCommitTogether() {
		return true;
	}

	@Override
	protected void initPrivateButton() {
		addPrivateBtn(getUIControl().getCardButtonAry());
		addPrivateBtn(getUIControl().getListButtonAry());
		initBusinessButton();
	}

	protected void initBusinessButton() {
		// TODO Auto-generated method stub

	}

	private void addPrivateBtn(int[] btnIDs) {
		if (btnIDs != null) {
			for (int i = 0; i < btnIDs.length; i++) {
				ButtonVO buttonVO = FBMButtonFactory.getButtonVO(btnIDs[i]);
				if (buttonVO != null) {
					addPrivateButton(buttonVO);

					int[] chldBtnIDs = buttonVO.getChildAry();
					if (chldBtnIDs != null) {
						addPrivateBtn(chldBtnIDs);
					}
				}
			}
		}
	}

	/**
	 * UI关联操作-新增调用方法
	 */
	public void doAddAction(ILinkAddData adddata) {
		// TODO Auto-generated method stub
		if (!(adddata.getUserObject() instanceof AggregatedValueObject)) {
			return;
		}

		if (adddata instanceof DefaultLinkAddParam) {
			parentUI = ((DefaultLinkAddParam) adddata).getUi();
		}
		AggregatedValueObject vo = (AggregatedValueObject) adddata.getUserObject();
		try {
			setButtonVisible(createController().getCardButtonAry(), false);
			int[] btns = createController().getCardButtonAry();

			for (int i = 0; i < btns.length; i++) {
				if (btns[i] == IBillButton.Save
						|| btns[i] == IBillButton.Edit
						|| btns[i] == IBillButton.Delete
						|| btns[i] == IBillButton.Cancel
						|| btns[i] == IBillButton.Action
						|| btns[i] == IBillButton.Audit
						|| btns[i] == IBillButton.CancelAudit
						|| btns[i] == IBillButton.Refresh) {
					getButtonManager().getButton(btns[i]).setVisible(true);
				}
				if (btns[i] == IBillButton.Line) {
					if (FbmBusConstant.BILLTYPE_INNERBACK.equals(getUIControl().getBillType())) {
						getButtonManager().getButton(btns[i]).setVisible(true);
					}
				}

			}
			setCurrentPanel(BillTemplateWrapper.CARDPANEL);
			setBillOperate(IBillOperate.OP_ADD);
			String billcode = (String) getBillCardPanel().getHeadItem(getBillField().getField_BillNo()).getValueObject();
			setCardUIData(vo);
			getBillCardPanel().getHeadItem(getBillField().getField_BillNo()).setValue(billcode);
			if (getUIControl().getBillType().equals(FbmBusConstant.BILLTYPE_DISCOUNT_APP)) {
				fireCardAfterEdit(DiscountVO.PK_SOURCE);
			}
			if (getUIControl().getBillType().equals(FbmBusConstant.BILLTYPE_COLLECTION_UNIT)) {
				fireCardAfterEdit(CollectionVO.PK_SOURCE);
			}

			if (getUIControl().getBillType().equals(FbmBusConstant.BILLTYPE_IMPAWN)) {
				fireCardAfterEdit(ImpawnVO.PK_SOURCE);
			}

			if(getUIControl().getBillType().equals(FbmBusConstant.BILLTYPE_BANKKEEP) 
					||getUIControl().getBillType().equals(FbmBusConstant.BILLTYPE_BANKBACK) 
					|| getUIControl().getBillType().equals(FbmBusConstant.BILLTYPE_INNERKEEP)
					||getUIControl().getBillType().equals(FbmBusConstant.BILLTYPE_INNERBACK)){
				
				UIRefPane gatherPane = (UIRefPane) getBillCardPanel().getBodyItem(
						StorageBVO.PK_SOURCE).getComponent();
				CircularlyAccessibleValueObject[] bodys = vo.getChildrenVO();
				if(bodys!=null&&bodys.length > 0){
					String[] pks = new String[bodys.length];
					for(int i =0; i < bodys.length;i++ ){
						pks[i] = (String)bodys[i].getAttributeValue(StorageBVO.PK_SOURCE);
					}
					if(getUIControl().getBillType().equals(FbmBusConstant.BILLTYPE_INNERBACK)){
						BillItem inputtype =  getBillCardPanel().getHeadItem(StorageVO.INPUTTYPE);
						inputtype.setValue(FbmBusConstant.KEEP_TYPE_RELIEF);

					}
					int currentrowcount = getBillCardPanel().getRowCount();
					for (int j = 0; j < currentrowcount; j++) {
						getBillCardPanel().delLine();
					}
					getBillCardPanel().addLine();
					gatherPane.setAutoCheck(false);
					gatherPane.getRefModel().setMatchPkWithWherePart(false);
					gatherPane.setPKs(pks);
					//getBillCardPanel().getBillModel(getBillCardPanel().getCurrentBodyTableCode()).setRowCount(pks.length);
					fireCardBodyAfterEdit(StorageBVO.PK_SOURCE);
				}
				
			
			}
			if (getUIControl().getBillType().equals(FbmBusConstant.BILLTYPE_INNERBACK)) {// 内部领用
				this.getBillCardPanel().getHeadItem(StorageVO.OUTPUTUNIT).setEnabled(false);
			}

		} catch (Exception e) {
			Logger.error(e.getMessage(), e);
		}
	}

	public void setButtonVisible(int[] btnno, boolean isvisible) {
		if (btnno == null || btnno.length == 0)
			return;
		for (int i = 0; i < btnno.length; i++) {
			getButtonManager().getButton(btnno[i]).setVisible(isvisible);
		}
	}

	public void setHeadItemEditable(String[] itemKeys, boolean isEditable) {
		// TODO Auto-generated method stub
		if (itemKeys == null || itemKeys.length == 0)
			return;
		for (int i = 0; i < itemKeys.length; i++) {
			getBillCardPanel().getHeadItem(itemKeys[i]).setEnabled(isEditable);
		}
	}

	public void setBodyItemEditable(String tableCode, String[] itemKeys,
			boolean isEditable) {
		if (CommonUtil.isNull(tableCode) || CommonUtil.isNull(itemKeys))
			return;
		for (int i = 0; i < itemKeys.length; i++) {
			getBillCardPanel().getBodyItem(tableCode, itemKeys[i]).setEnabled(isEditable);
		}
	}

	public BillEditEventDispatcher getBillEditEventDispatcher() {
		return billEditEventDispatcher;
	}

	public CurrencyClientUtil getCurrencyutil() {
		return currencyutil;
	}

	/**
	 * 联查票据系统单据:传入billid,userobject=null userobject：new String[2] OtherPK：单据PK
	 * 增加规则台帐只能穿透联查自己公司的单据（除内部存放领用外） CC联查，会计平台联查，台帐联查
	 */
	public void doQueryAction(ILinkQueryData querydata) {
		String sourcePk = "";
		String sourceSys = "";
		boolean bolIsTZ = false;

		if (querydata.getUserObject() != null
				&& querydata.getUserObject() instanceof String[]) {
			String[] sourcePks = (String[]) querydata.getUserObject();
			if (sourcePks != null && sourcePks.length > 1) {
				sourcePk = sourcePks[1];
			}
		} else {
			sourcePk = (String) querydata.getBillID();
			if (sourcePk.length() > 20) {
				if (sourcePk.indexOf(TZ_flag) > 0) {
					sourcePk = calculatePk(sourcePk, TZ_flag);
					String billtype = getUIControl().getBillType();
					if (!billtype.equals(FbmBusConstant.BILLTYPE_INNERBACK)
							&& !billtype.equals(FbmBusConstant.BILLTYPE_INNERKEEP)) {
						bolIsTZ = true;
					}
				} else
					sourcePk = calculatePk(sourcePk, StorageVO.Procmsg_flag);
			}
			sourceSys = FbmBusConstant.FBM_SYSCODE;
		}
		setCurrentPanel(BillTemplateWrapper.CARDPANEL);
		if (!CommonUtil.isNull(sourcePk)) {
			getBufferData().clear();
			try {
				if (sourceSys.equals(FbmBusConstant.FBM_SYSCODE)) {
					AggregatedValueObject loadHeadData = loadHeadData(sourcePk);
					if (loadHeadData == null
							|| loadHeadData.getParentVO() == null) {
						MessageDialog.showErrorDlg(this, nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm", "UPPFBMComm-000157")/*
																																	 * @res
																																	 * "错误"
																																	 */, nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm", "UPPFBMComm-000158")/*
																																																					 * @res
																																																					 * "找不到相应的业务单据！"
																																																					 */);
						loadHeadData = null;
					} else {
						if (bolIsTZ) {
							String corpPk = (String) loadHeadData.getParentVO().getAttributeValue("pk_corp");
							if (CommonUtil.isNull(corpPk)
									|| !corpPk.equals(_getCorp().getPk_corp())) {
								MessageDialog.showHintDlg(this, nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm", "UPPFBMComm-000390")/*
																																			 * @res
																																			 * "警告"
																																			 */, nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm", "UPPFBMComm-000391")/*
																																																							 * @res
																																																							 * "票据业务单据不支持跨公司查询！"
																																																							 */);
								loadHeadData = null;
							}
						}
					}
					getBufferData().addVOToBuffer(loadHeadData);
					getBufferData().setCurrentRow(getBufferData().getCurrentRow());
					setButtonVisible(getUIControl().getCardButtonAry(), false);
					// AccPubTool.setButtonVisible(new ButtonObject[] {
					// getButtonManager().getButton(IBillButton.Edit),
					// getButtonManager().getButton(IBillButton.Delete)}, true);
				} else {
					SFqueryfunnode(sourcePk);
					setButtonVisible(getUIControl().getCardButtonAry(), false);
					setButtonVisible(getUIControl().getListButtonAry(), false);
					getButtonManager().getButton(IBillButton.Brow).setVisible(true);
					getButtonManager().getButton(IBillButton.Card).setVisible(true);
					getButtonManager().getButton(IBillButton.Return).setVisible(true);
					getButtonManager().getButton(IBillButton.Edit).setVisible(true);
					getButtonManager().getButton(IBillButton.Delete).setVisible(true);
				}

				updateButtonUI();
			} catch (Exception e) {
				Logger.error(e.getMessage(), e);
			}
		}
	}

	private String calculatePk(String sourcePk, String splitstr) {
		int index = sourcePk.indexOf(splitstr);
		sourcePk = sourcePk.substring(0, index);

		// String[] splits = sourcePk.split(splitstr);
		// if(splits==null||splits.length==0){
		return sourcePk;
		// }
		// return splits[0];
	}

	/**
	 * <p>
	 * SF报的联查方法
	 * <p>
	 * 作者：lpf 日期：2007-11-16
	 * 
	 * @param sourcePk
	 * @throws Exception
	 */
	private void SFqueryfunnode(String sourcePk) throws Exception {
		String sourceClassname = getUIControl().getBillVoName()[1];
		String subClassname = getUIControl().getBillVoName()[2];
		String pkname = ((SuperVO) Class.forName(sourceClassname).newInstance()).getPKFieldName();
		String sql = createSFBillQuerySql(pkname, sourcePk);
		SuperVO[] headVos = null;
		AggregatedValueObject[] queryVos = null;
		String subPkname = ((SuperVO) Class.forName(subClassname).newInstance()).getParentPKFieldName();
		if (sql.length() > 0) {
			if (CommonUtil.isNull(subClassname) || subPkname == null) {
				headVos = getBusiDelegator().queryByCondition(Class.forName(sourceClassname), " ("
						+ sql.toString()
						+ ") ");
				if (headVos != null && headVos.length > 0) {
					queryVos = new HYBillVO[headVos.length];
					for (int i = 0; i < queryVos.length; i++) {
						queryVos[i] = new HYBillVO();
						queryVos[i].setParentVO(headVos[i]);
					}
				}
			} else {
				queryVos = getBusiDelegator().queryBillVOByCondition(getUIControl().getBillVoName(), " ("
						+ sql.toString()
						+ ") ");
				if (queryVos != null && queryVos.length > 0) {
					headVos = new SuperVO[queryVos.length];
					for (int i = 0; i < queryVos.length; i++) {
						headVos[i] = (SuperVO) queryVos[i].getParentVO();
					}
				}
			}

			if (headVos != null && headVos.length > 0) {
				setListHeadData(headVos);
				getBufferData().addVOsToBuffer(queryVos);
				setBillOperate(IBillOperate.OP_NOTEDIT);
				getBufferData().setCurrentRow(0);
			}
		}
	}

	protected void queryFBMnode(String sourcePk) throws Exception {
		if (loadHeadData(sourcePk) != null) {
			MessageDialog.showErrorDlg(this, nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm", "UPPFBMComm-000157")/*
																														 * @res
																														 * "错误"
																														 */, nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm", "UPPFBMComm-000158")/*
																																																		 * @res
																																																		 * "找不到相应的业务单据！"
																																																		 */);
			return;
		}
		getBufferData().addVOToBuffer(loadHeadData(sourcePk));
		getBufferData().setCurrentRow(getBufferData().getCurrentRow());
	}

	/**
	 * <p>
	 * 收付报系统联查sql
	 * <p>
	 * 作者：lpf 日期：2007-10-26
	 * 
	 * @param sql
	 * @param sourceVos
	 * @param pkname
	 * @throws Exception
	 */
	private String createSFBillQuerySql(String pkname, String sourcePk)
			throws Exception {
		StringBuffer sql = new StringBuffer();
		SuperVO[] sourceVos = getBusiDelegator().queryByCondition(OuterVO.class, OuterVO.PK_OUTERBILL_B
				+ " = '"
				+ sourcePk
				+ "'");

		for (int i = 0; i < sourceVos.length; i++) {
			if (sourceVos[i].getAttributeValue(OuterVO.PK_BUSIBILL) != null) {
				if (sql.length() > 0) {
					sql.append(" or ");
				}
				String fieldName = OuterVO.PK_BUSIBILL;
				// 背书PK转换成登记表PK进行查询
				if (FbmBusConstant.BILLTYPE_ENDORE.equals(String.valueOf(sourceVos[i].getAttributeValue(OuterVO.PK_BILLTYPECODE)))) {
					fieldName = OuterVO.PK_REGISTER;
				}
				sql.append(pkname
						+ "='"
						+ sourceVos[i].getAttributeValue(fieldName)
						+ "' ");
			}
		}
		return sql.toString();
	}

	/**
	 * 审批流设置其他按钮不显示
	 */
	public void doApproveAction(ILinkApproveData approvedata) {
		super.doApproveAction(approvedata);
		try {
			setButtonVisible(createController().getCardButtonAry(), false);
			getButtonManager().getButton(IBillButton.Action).setVisible(true);
			updateButtonUI();
		} catch (Exception e) {
			Logger.error(e.getMessage(), e);
		}
	}

	/*
	 * 在查询后重新设置列表表头数据的时候，将当前列表中reftakenprocessor的缓存清空 (non-Javadoc)
	 * 
	 * @seenc.ui.trade.manage.BillManageUI#setTotalHeadSpecialData(nc.vo.pub.
	 * CircularlyAccessibleValueObject[])
	 */
	protected void setTotalHeadSpecialData(CircularlyAccessibleValueObject[] vos)
			throws Exception {
		if (getRefTakenProccessor() != null) {
			 getRefTakenProccessor().cleanCache();
		}
	}

	/*
	 * 在更新表头单条数据的时候将reftakenprocessor中的缓存清空 (non-Javadoc)
	 * 
	 * @see nc.ui.trade.manage.BillManageUI#updateListVo()
	 */
	protected void updateListVo() throws java.lang.Exception {
		if (getRefTakenProccessor() != null
				&& getBufferData().getCurrentVO() != null) {
			 getRefTakenProccessor().cleanCache();
		}
		super.updateListVo();
	}

	@Override
	protected String checkPrerequisite() {
		String pk_currtype = null;
		try {
			pk_currtype = new CurrencyPublicUtil(_getCorp().getPk_corp()).getLocalCurrPK();
		} catch (BusinessException e) {
			Logger.error(e.getMessage(), e);
		}
		if (CommonUtil.isNull(pk_currtype)) {
			return nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm", "UPPFBMComm-000159")/*
																							 * @res
																							 * "当前公司未设置本位币!"
																							 */;
		}
		return null;
	}

	// 表头编辑
	public void fireCardAfterEdit(String ItemKey) {
		BillItem bi = getBillCardPanel().getHeadItem(ItemKey);
		if (bi != null) {
			afterEdit(new BillEditEvent(bi.getComponent(), bi.getValueObject(),
					ItemKey, -1, BillItem.HEAD));
		}
	}
	
	public void fireCardBodyAfterEdit(String ItemKey) {
		BillItem bi = getBillCardPanel().getBodyItem(ItemKey);
		if (bi != null) {
			afterEdit(new BillEditEvent(bi.getComponent(), bi.getValueObject(),
					ItemKey, 0, BillItem.BODY));
		}
	}

	public void setCardUIData(AggregatedValueObject vo) throws Exception {
		if (getRefTakenProccessor() != null) {
			((RefTakenProccessor) getRefTakenProccessor()).resetDecimalDigits(vo);
			((RefTakenProccessor) getRefTakenProccessor()).setCardVO(vo);
		}
		super.setCardUIData(vo);
		if (getRefTakenProccessor() != null) {
			((RefTakenProccessor) getRefTakenProccessor()).setCardVO(null);
		}
	}

	@Override
	protected BusinessDelegator createBusinessDelegator() {
		if (getUIControl().getBusinessActionType() == IBusinessActionType.BD)
			return new BDBusinessDelegator();
		else
			return new FBMBusinessDelegator();
	}

	/**
	 * 为参照设置filter
	 * 
	 * @param isHeadItem
	 * @param itemKey
	 * @param filter
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 * @throws ClassNotFoundException
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 * @throws IllegalArgumentException
	 */
	protected void addSqlFilter(boolean isHeadItem, String itemKey,
			BillItemRefModelFilter filter) {
		UIRefPane refPane = null;
		BillItem item = null;

		if (isHeadItem) {
			item = getBillCardPanel().getHeadItem(itemKey);
		} else {
			item = getBillCardPanel().getBodyItem(itemKey);
		}
		refPane = (UIRefPane) item.getComponent();

		AbstractRefModel refModel = refPane.getRefModel();
		if (AbstractTMRefModel.class.isAssignableFrom(refModel.getClass())) {
			((AbstractTMRefModel) refModel).addSqlFilter(filter);
		}
	}

	@Override
	public void bodyRowChange(BillEditEvent e) {
		super.bodyRowChange(e);
		// if (e.getSource() ==
		// getBillListPanel().getParentListPanel().getTable()) {//列表界面行变换
		// IRefTakenProccessor processor = getRefTakenProccessor();
		// if(processor!=null){
		// ITakenColUtil util = processor.getTakenColUtil();
		// RefTakenVOMap map = util.getRefTakenVOMap();
		// //获得表头的tablecode
		// try {
		// Field f_templatevo =
		// getBillListPanel().getBillListData().getClass().getDeclaredField("billTempletVO");
		// f_templatevo.setAccessible(true);
		// BillTempletVO templatevo =
		// (BillTempletVO)f_templatevo.get(getBillListPanel().getBillListData());
		// for(BillTabVO tabvo :
		// templatevo.getHeadVO().getStructvo().getBillTabVOs()){
		// if(tabvo.getPos() == IBillItem.HEAD){
		// String tablecode = tabvo.getTabcode();
		// String[] sourceKeys = map.getAllRefTakenKeys(tablecode);
		// for (int j = 0; j < sourceKeys.length; j++) {
		// processor.takenListRefValue(sourceKeys[j], e.getRow());
		// }
		// }
		// }
		// } catch (Exception e1) {
		// Logger.error(e1.getMessage(),e1);
		// }
		// }
		// }
	}

	protected void initUITableSearchDialog() {
		UITable table = getBillListPanel().getParentListPanel().getTable();
		ActionListener listen = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CDMUITableSearchDialog searchDialog = new CDMUITableSearchDialog(
						FBMManageUI.this,
						getBillListPanel().getParentListPanel().getTable());
				searchDialog.showModal();
			}
		};
		table.getLocateMenu().removeActionListener(table.getLocateMenu().getActionListeners()[0]);
		table.getLocateMenu().addActionListener(listen);

		UITable cardbody = getBillCardPanel().getBodyPanel().getTable();

		ActionListener cardBodyListen = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CDMUITableSearchDialog searchDialog = new CDMUITableSearchDialog(
						FBMManageUI.this,
						getBillCardPanel().getBodyPanel().getTable());
				searchDialog.showModal();
			}
		};
		cardbody.getLocateMenu().removeActionListener(cardbody.getLocateMenu().getActionListeners()[0]);
		cardbody.getLocateMenu().addActionListener(cardBodyListen);

		UITable listbody = getBillListPanel().getChildListPanel().getTable();
		ActionListener listBodyListen = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CDMUITableSearchDialog searchDialog = new CDMUITableSearchDialog(
						FBMManageUI.this,
						getBillListPanel().getChildListPanel().getTable());
				searchDialog.showModal();
			}
		};
		listbody.getLocateMenu().removeActionListener(listbody.getLocateMenu().getActionListeners()[0]);
		listbody.getLocateMenu().addActionListener(listBodyListen);

	}

	private void initDefItem() {
		String[] strDefObjs = null;
		String billtype = getUIControl().getBillType();
		if (FbmBusConstant.BILLTYPE_INVOICE.equals(billtype)) {
			strDefObjs = new String[] { "付票登记单表头" };
		} else if (FbmBusConstant.BILLTYPE_GATHER.equals(billtype)) {
			strDefObjs = new String[] { "收票登记单表头" };
		} else if (FbmBusConstant.BILLTYPE_BILLPAY.equals(billtype)) {
			strDefObjs = new String[] { "票据付款单表头" };
		} else if (FbmBusConstant.BILLTYPE_COLLECTION_UNIT.equals(billtype)) {
			strDefObjs = new String[] { "托收办理单表头" };
		} else if (FbmBusConstant.BILLTYPE_DISCOUNT_TRANSACT.equals(billtype)) {
			strDefObjs = new String[] { "贴现办理单表头" };
		} else if (FbmBusConstant.BILLTYPE_ENDORE.equals(billtype)) {
			strDefObjs = new String[] { "背书办理单表头" };
		} else if (FbmBusConstant.BILLTYPE_IMPAWN.equals(billtype)) {
			strDefObjs = new String[] { "质押单表头" };
		} else if (FbmBusConstant.BILLTYPE_LIQUIDATE.equals(billtype)) {
			strDefObjs = new String[] { "调剂清算单表头" };
		} else if (FbmBusConstant.BILLTYPE_RELIEF.equals(billtype)) {
			strDefObjs = new String[] { "票据调剂单表头" };
		} else if (FbmBusConstant.BILLTYPE_RETURN.equals(billtype)) {
			strDefObjs = new String[] { "退票单表头" };
		} else if (FbmBusConstant.BILLTYPE_INNERKEEP.equals(billtype)) {
			strDefObjs = new String[] { "内部托管单表头" };
		} else if (FbmBusConstant.BILLTYPE_INNERBACK.equals(billtype)) {
			strDefObjs = new String[] { "内部领用单表头" };
		} else if (FbmBusConstant.BILLTYPE_BANKKEEP.equals(billtype)) {
			strDefObjs = new String[] { "银行托管单表头" };
		} else if ((FbmBusConstant.BILLTYPE_BANKBACK).equals(billtype)) {
			strDefObjs = new String[] { "银行领用单表头" };
		}

		String[] strPrefix = new String[] { "def" };

		try {
			new ManageDefShowUtil(getBillCardPanel(), getBillListPanel(),
					new DefaultDefShowStrategyByBillItem()).showDefWhenRef(strDefObjs, strPrefix, true);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public FbmBatchQueryDlg getBatchQueryDlg() {
		return batchQuery;
	}

 

}