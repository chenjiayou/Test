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
 * FBM����UI
 * <p>
 * �����ˣ�lpf <b>���ڣ�2007-8-10
 * 
 */
public class FBMManageUI extends BillManageUI implements ILinkAdd, ILinkQuery,
		ILinkApprove, nc.ui.glpub.IUiPanel {
	private IRefTakenProccessor refTakenProccessor = null;
	private HashMap<String, ArrayList<IUIChecker>> validatorMap = null;
	private CurrencyClientUtil currencyutil = null;
	private String TZ_flag = "*";// ̨�����鴫�͵�PK��־

	private nc.ui.glpub.IParent m_parent = null;
	private FBMManageUI parentUI = null;// ��ת����������ĳ�ʼ���� UI

	private FbmBatchQueryDlg batchQuery = new FbmBatchQueryDlg(this);

	public nc.ui.glpub.IParent getm_parent() {
		return m_parent;
	}

	public void showMe(nc.ui.glpub.IParent parent) {
		// TODO �Զ����ɷ������
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
	 * ��ʼ�����湫����Ϣ
	 * <p>
	 * ���ߣ�lisg <br>
	 * ���ڣ�2006-11-24
	 */
	protected void initialize() {
		// ��ʼ��listerner
		addItemEditListener();

		// ��ʼ��tableCellRender
		initTableCellRender();

		initListRefPane();

		addValidator();

		initCurrencyutil();

		initUITableSearchDialog();

		initMouseListener();
		
		getBillListPanel().getParentListPanel().addRowSelectionChangeListener(new FBMListHeadDataRefTakenListener(getBillListPanel(),getRefTakenProccessor()));
	}

	/**
	 * ��ʼ���б�������
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
		if (!isListPanelSelected()) {// ֻ��ѡ��Ƭ����ʱ�ŵ���Я��
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
		// ����Ĭ��ִ�б༭��ʽ
		getBillCardPanel().setAutoExecHeadEditFormula(true);
		initButtonVisible();
		initDefItem();
		// ���α����Ҽ��˵���
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

		// �����������ֺ����գ����������GatherBusRefModel��Ϊ���յĻ�����Ʊ������
		// if ((e.getSource() instanceof UIRefPane)) {
		// // UIRefPanel������Ϊ���ֻ���Ĳ���
		// if (((UIRefPane) e.getSource()).getRefModel() instanceof
		// GatherBusRefModel
		// /*|| ((UIRefPane) e.getSource()).getRefModel() instanceof
		// EndoreRefModel*/) {
		// if ((FBMClientInfo.isSettleCenter())
		// && DiscountVO.PK_SOURCE.equals(e.getKey())) {
		// String pk_corp = (String) ((UIRefPane) e.getSource())
		// .getRefModel().getValue("pk_corp");
		// String cur_pk_corp = FBMClientInfo.getCorpPK();
		// if (cur_pk_corp.equals(pk_corp)) {// ����Ϊ˽��
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
	 * ��ʼ���б�״̬�µ�TableCellRender
	 * <p>
	 * ���ߣ�lisg <br>
	 * ���ڣ�2006-11-24
	 */
	protected void initTableCellRender() {
		RenderInitTools renderInitTools = new RenderInitTools(
				getBillListPanel(), getBillCardPanel());
		renderInitTools.setRefTakenProccessor(getRefTakenProccessor());
		renderInitTools.setShowCode(false);
		// ���б�ͷ��render
		renderInitTools.resetListHeadCellRenderer();
		// ���б����render
		renderInitTools.resetListBodyCellRenderer();
		// ����Ƭ�����list��render
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
	 * UI��������-�������÷���
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
			if (getUIControl().getBillType().equals(FbmBusConstant.BILLTYPE_INNERBACK)) {// �ڲ�����
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
	 * ����Ʊ��ϵͳ����:����billid,userobject=null userobject��new String[2] OtherPK������PK
	 * ���ӹ���̨��ֻ�ܴ�͸�����Լ���˾�ĵ��ݣ����ڲ���������⣩ CC���飬���ƽ̨���飬̨������
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
																																	 * "����"
																																	 */, nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm", "UPPFBMComm-000158")/*
																																																					 * @res
																																																					 * "�Ҳ�����Ӧ��ҵ�񵥾ݣ�"
																																																					 */);
						loadHeadData = null;
					} else {
						if (bolIsTZ) {
							String corpPk = (String) loadHeadData.getParentVO().getAttributeValue("pk_corp");
							if (CommonUtil.isNull(corpPk)
									|| !corpPk.equals(_getCorp().getPk_corp())) {
								MessageDialog.showHintDlg(this, nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm", "UPPFBMComm-000390")/*
																																			 * @res
																																			 * "����"
																																			 */, nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm", "UPPFBMComm-000391")/*
																																																							 * @res
																																																							 * "Ʊ��ҵ�񵥾ݲ�֧�ֿ繫˾��ѯ��"
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
	 * SF�������鷽��
	 * <p>
	 * ���ߣ�lpf ���ڣ�2007-11-16
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
																														 * "����"
																														 */, nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm", "UPPFBMComm-000158")/*
																																																		 * @res
																																																		 * "�Ҳ�����Ӧ��ҵ�񵥾ݣ�"
																																																		 */);
			return;
		}
		getBufferData().addVOToBuffer(loadHeadData(sourcePk));
		getBufferData().setCurrentRow(getBufferData().getCurrentRow());
	}

	/**
	 * <p>
	 * �ո���ϵͳ����sql
	 * <p>
	 * ���ߣ�lpf ���ڣ�2007-10-26
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
				// ����PKת���ɵǼǱ�PK���в�ѯ
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
	 * ����������������ť����ʾ
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
	 * �ڲ�ѯ�����������б��ͷ���ݵ�ʱ�򣬽���ǰ�б���reftakenprocessor�Ļ������ (non-Javadoc)
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
	 * �ڸ��±�ͷ�������ݵ�ʱ��reftakenprocessor�еĻ������ (non-Javadoc)
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
																							 * "��ǰ��˾δ���ñ�λ��!"
																							 */;
		}
		return null;
	}

	// ��ͷ�༭
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
	 * Ϊ��������filter
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
		// getBillListPanel().getParentListPanel().getTable()) {//�б�����б任
		// IRefTakenProccessor processor = getRefTakenProccessor();
		// if(processor!=null){
		// ITakenColUtil util = processor.getTakenColUtil();
		// RefTakenVOMap map = util.getRefTakenVOMap();
		// //��ñ�ͷ��tablecode
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
			strDefObjs = new String[] { "��Ʊ�Ǽǵ���ͷ" };
		} else if (FbmBusConstant.BILLTYPE_GATHER.equals(billtype)) {
			strDefObjs = new String[] { "��Ʊ�Ǽǵ���ͷ" };
		} else if (FbmBusConstant.BILLTYPE_BILLPAY.equals(billtype)) {
			strDefObjs = new String[] { "Ʊ�ݸ����ͷ" };
		} else if (FbmBusConstant.BILLTYPE_COLLECTION_UNIT.equals(billtype)) {
			strDefObjs = new String[] { "���հ�����ͷ" };
		} else if (FbmBusConstant.BILLTYPE_DISCOUNT_TRANSACT.equals(billtype)) {
			strDefObjs = new String[] { "���ְ�����ͷ" };
		} else if (FbmBusConstant.BILLTYPE_ENDORE.equals(billtype)) {
			strDefObjs = new String[] { "���������ͷ" };
		} else if (FbmBusConstant.BILLTYPE_IMPAWN.equals(billtype)) {
			strDefObjs = new String[] { "��Ѻ����ͷ" };
		} else if (FbmBusConstant.BILLTYPE_LIQUIDATE.equals(billtype)) {
			strDefObjs = new String[] { "�������㵥��ͷ" };
		} else if (FbmBusConstant.BILLTYPE_RELIEF.equals(billtype)) {
			strDefObjs = new String[] { "Ʊ�ݵ�������ͷ" };
		} else if (FbmBusConstant.BILLTYPE_RETURN.equals(billtype)) {
			strDefObjs = new String[] { "��Ʊ����ͷ" };
		} else if (FbmBusConstant.BILLTYPE_INNERKEEP.equals(billtype)) {
			strDefObjs = new String[] { "�ڲ��йܵ���ͷ" };
		} else if (FbmBusConstant.BILLTYPE_INNERBACK.equals(billtype)) {
			strDefObjs = new String[] { "�ڲ����õ���ͷ" };
		} else if (FbmBusConstant.BILLTYPE_BANKKEEP.equals(billtype)) {
			strDefObjs = new String[] { "�����йܵ���ͷ" };
		} else if ((FbmBusConstant.BILLTYPE_BANKBACK).equals(billtype)) {
			strDefObjs = new String[] { "�������õ���ͷ" };
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