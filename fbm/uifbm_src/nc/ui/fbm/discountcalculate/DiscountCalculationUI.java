/**
 *
 */
package nc.ui.fbm.discountcalculate;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

import javax.swing.table.TableColumnModel;

import nc.bs.logging.Logger;
import nc.itf.cdm.util.CommonUtil;
import nc.ui.fac.account.pub.IRefTakenProccessor;
import nc.ui.fac.account.pub.RefTakenProccessor;
import nc.ui.fbm.discountcalculate.listener.BodyTxrllEditListener;
import nc.ui.fbm.discountcalculate.listener.BodyTxyllEditListener;
import nc.ui.fbm.discountcalculate.listener.DiscountCalculateBodyChoiceEditListener;
import nc.ui.fbm.discountcalculate.listener.DiscountCalculateBodyDiscountDelayDayNumEditListener;
import nc.ui.fbm.discountcalculate.listener.DiscountCalculateBodyLltsEditListener;
import nc.ui.fbm.discountcalculate.listener.DiscountCalculateBodyTxnllEditListener;
import nc.ui.fbm.discountcalculate.listener.DiscountCalculateBodyTxnllfaEditListener;
import nc.ui.fbm.discountcalculate.listener.DiscountCalculateBodyTxrqEditListener;
import nc.ui.fbm.discountcalculate.listener.DiscountCalculateHeadAllChoiceEditListener;
import nc.ui.fbm.discountcalculate.listener.DiscountCalculateHeadDiscountDelayDayNumEditListener;
import nc.ui.fbm.discountcalculate.listener.DiscountCalculateHeadLltsEditListener;
import nc.ui.fbm.discountcalculate.listener.DiscountCalculateHeadTxnllEditListener;
import nc.ui.fbm.discountcalculate.listener.DiscountCalculateHeadTxnllfaEditListener;
import nc.ui.fbm.discountcalculate.listener.DiscountCalculateHeadTxrqEditListener;
import nc.ui.fbm.pub.BillEditEventDispatcher;
import nc.ui.fbm.pub.FBMButtonFactory;
import nc.ui.fbm.pub.FBMClientInfo;
import nc.ui.fbm.pub.IFBMButton;
import nc.ui.fbm.pub.IItemEditListener;
import nc.ui.pub.beans.UICheckBox;
import nc.ui.pub.beans.UIComboBox;
import nc.ui.pub.beans.UIRefPane;
import nc.ui.pub.beans.UITable;
import nc.ui.pub.beans.constenum.DefaultConstEnum;
import nc.ui.pub.bill.BillEditEvent;
import nc.ui.pub.bill.BillItem;
import nc.ui.pub.bill.BillModel;
import nc.ui.pub.bill.IBillItem;
import nc.ui.trade.base.IBillOperate;
import nc.ui.trade.bill.ICardController;
import nc.ui.trade.bsdelegate.BusinessDelegator;
import nc.ui.trade.button.IBillButton;
import nc.ui.trade.card.CardEventHandler;
import nc.vo.fbm.proxy.OuterProxy;
import nc.vo.fbm.register.RegisterVO;
import nc.vo.fbm.storage.StoragePowerVO;
import nc.vo.pub.BusinessException;
import nc.vo.pub.CircularlyAccessibleValueObject;
import nc.vo.pub.bill.BillRendererVO;
import nc.vo.tm.enumeration.IParamEnum;
import nc.vo.trade.button.ButtonVO;
import nc.vo.trade.pub.IBillStatus;

/**
 * <p>
 *
 * <p>
 * 创建日期：2006-9-6
 *
 * @author lisg
 * @since v5.0
 */
public class DiscountCalculationUI
extends TotalRowBillCardUI {

	private int oldrow = -1;
	private String oldkey = "";

	private boolean needfire = true;
	private Integer decimalpoint = 2;

	private Hashtable<BillItem, Object> oldstatues = new Hashtable<BillItem, Object>();
	private BillEditEventDispatcher billEditEventDispatcher = new BillEditEventDispatcher();
	private IRefTakenProccessor refTakenProccessor = null;

	/**
	 * 作者：lisg <br>
	 * 日期：2006-9-12
	 *
	 * @see nc.ui.trade.card.BillCardUI#getCardHeadShowNum()
	 */
	@Override
	public String[][] getCardHeadShowNum() {
		return new String[][]{
				{"txnlv", RegisterVO.TXYLL, RegisterVO.TXRLL},
				{getDecimalPoint().toString(), getDecimalPoint().toString(),getDecimalPoint().toString()}
		};
	}

	public BillEditEventDispatcher getBillEditEventDispatcher() {
		return billEditEventDispatcher;
	}

	public final IRefTakenProccessor getRefTakenProccessor(){
		if(refTakenProccessor == null){
			refTakenProccessor = createRefTakenProccessor();
		}
		return refTakenProccessor;
	}

	protected IRefTakenProccessor createRefTakenProccessor() {
		RefTakenProccessor p = new DiscountCalculateRefTakenProccessor(null, getBillCardPanel());
		p.setCacheType(RefTakenProccessor.CACHE_ROW);
		return p;
	}


	/**
	 * 作者：lisg <br>
	 * 日期：2006-9-12
	 *
	 * @see nc.ui.trade.card.BillCardUI#getCardItemShowNum()
	 */
	@Override
	public String[][] getCardItemShowNum() {
		return new String[][]{
				{"txnlv",RegisterVO.TXYLL, RegisterVO.TXRLL},
				{getDecimalPoint().toString(),getDecimalPoint().toString(),getDecimalPoint().toString()}
		};
	}


	public DiscountCalculationUI(){
		super();
		initRender();
		addItemEditListener();
		reSetButtonStatues();
	}

	/**
	 * 作者：lisg <br>
	 * 日期：2006-9-6
	 *
	 * @see nc.ui.trade.base.AbstractBillUI#onClosing()
	 */
	@Override
	public boolean onClosing() {
		return true;
	}

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 作者：lisg <br>
	 * 日期：2006-9-6
	 *
	 * @see nc.ui.trade.card.BillCardUI#createController()
	 */
	@Override
	protected ICardController createController() {
		return new DiscountCalculationController();
	}

	/**
	 * 作者：lisg <br>
	 * 日期：2006-9-6
	 *
	 * @see nc.ui.trade.base.AbstractBillUI#getRefBillType()
	 */
	@Override
	public String getRefBillType() {
		return null;
	}

	/**
	 * 作者：lisg <br>
	 * 日期：2006-9-6
	 *
	 * @see nc.ui.trade.base.AbstractBillUI#initSelfData()
	 */
	@Override
	protected void initSelfData() {
		//初始化下拉框
		initComBox();
		//初始化参照的响应机制
		initRefPane();
	}

	protected void initRender(){
		UITable t = getBillCardPanel().getBillTable();
		BillModel bm = getBillCardPanel().getBillModel();
		TableColumnModel tm = t.getColumnModel();
		BillItem[] bis = getBillCardPanel().getBodyItems();
		BillRendererVO rendervo = new BillRendererVO();
		rendervo.setShowThMark(true);
		for(BillItem bi : bis){
			if(bi.isShow() == false || bi.getKey().equals("choice")){
				continue;
			}else{
				int col_index = t.convertColumnIndexToView(bm.getBodyColByKey(bi.getKey()));
				if(!TotalRowUITools.isLegalTotalController(getUIControl())){
					tm.getColumn(col_index).setCellRenderer(new TotalRowTableCellColorRender(bm,null,getDistinctColIndex(),bi,rendervo));
				}else{
					String classname = getBodyVOName();
					ITotalSpecify ts = getTotalSpecifyController();
					TotalRowPara trp = ts.getTotalRowPara(classname);

					tm.getColumn(col_index).setCellRenderer(new DiscountCalculateTableCellRender(bm,bi,getRefTakenProccessor(),rendervo,false,trp,getDistinctColIndex()));
				}
			}
		}
	}


	/**
	 * 作者：lisg <br>
	 * 日期：2006-9-6
	 *
	 * @see nc.ui.trade.base.AbstractBillUI#initPrivateButton()
	 */
	@Override
	protected void initPrivateButton() {
		addPrivateButton(FBMButtonFactory.getButtonVO(IFBMButton.Discount_Calculate));
	}

	/**
	 * 作者：lisg <br>
	 * 日期：2006-9-6
	 *
	 * @see nc.ui.trade.card.BillCardUI#createEventHandler()
	 */
	@Override
	protected CardEventHandler createEventHandler() {
		return new DiscountCalculationEventHandler(this, getUIControl());
	}

	private void reSetButtonStatues(){
		//修改查询按钮状态
		((ButtonVO)getButtonManager().getButton(IBillButton.Query).getData()).setOperateStatus(new int[]{
				IBillOperate.OP_EDIT,
				IBillOperate.OP_NOTEDIT,
				IBillOperate.OP_NOADD_NOTEDIT,
				IBillOperate.OP_INIT
		});
		//修改打印按钮状态
		((ButtonVO)getButtonManager().getButton(IBillButton.Print).getData()).setOperateStatus(new int[]{
				IBillOperate.OP_EDIT
		});

		try {
			setBillOperate(IBillOperate.OP_INIT);
		} catch (Exception e) {
			Logger.error(e.getMessage(),e);
		}
	}

	/**
	 * 作者：lisg <br>
	 * 日期：2006-9-7
	 *初始化界面下拉框
	 * @see nc.ui.trade.card.BillCardUI#beforeEdit(nc.ui.pub.bill.BillEditEvent)
	 */
	protected void initComBox(){
		DefaultConstEnum[] comBoxLlts = new DefaultConstEnum[]{
				new DefaultConstEnum(360,"360"),
				new DefaultConstEnum(365,"365")};

		getBillCardWrapper().initHeadComboBox(RegisterVO.LLTS, comBoxLlts, false);
		getBillCardWrapper().initBodyComboBox(RegisterVO.LLTS, comBoxLlts, false);

//		getBillCardWrapper().initBodyComboBox(RegisterVO.VBILLSTATUS,new BillStatus().strStateRemark, true);
		getBillCardWrapper().initBodyComboBox(RegisterVO.VBILLSTATUS,
				getBillStatus(),
				false);

		DefaultConstEnum[] gathertype = nc.ui.fbm.pub.ComBoxUtil.getGathertype();
		getBillCardWrapper().initBodyComboBox(RegisterVO.GATHERTYPE, gathertype, false);

		DefaultConstEnum[] registerstatus = nc.ui.fbm.pub.ComBoxUtil.getFBillStatus();
		getBillCardWrapper().initBodyComboBox(RegisterVO.REGISTERSTATUS, registerstatus, false);

		try{
			DefaultConstEnum[] acceptancetype = nc.ui.fbm.pub.ComBoxUtil.getAcceptanceType();
			getBillCardWrapper().initBodyComboBox(RegisterVO.FBMBILLTYPE, acceptancetype, false);
		} catch (BusinessException e) {
			showErrorMessage(e.getMessage());
		}
	}

	protected void initRefPane(){
		((UIRefPane)getBillCardPanel().getHeadItem(RegisterVO.TXNLLFA).getComponent()).setButtonFireEvent(true);
//		((UIRefPane)getBillCardPanel().getHeadItem("txnllfa_c").getComponent()).setButtonFireEvent(true);
		nc.ui.fbm.pub.refmodel.OutCustDocAndBankDocRefModel refModel = new nc.ui.fbm.pub.refmodel.OutCustDocAndBankDocRefModel();
		((UIRefPane)getBillCardPanel().getBodyItem(RegisterVO.KEEPUNIT).getComponent()).setRefModel(refModel);
	}

	/**
	 * 作者：lisg <br>
	 * 日期：2006-9-7
	 *
	 * @see nc.ui.trade.card.BillCardUI#beforeEdit(nc.ui.pub.bill.BillEditEvent)
	 */
	@Override
	public boolean beforeEdit(BillEditEvent e) {
		if(!super.beforeEdit(e)){
			return false;
		}

		UITable ut = getBillCardPanel().getBillTable();

		//校验行利率天数设置
		if(e.getPos() ==IBillItem.BODY && e.getKey().equals(RegisterVO.LLTS)){

			if(e.getRow() == oldrow && oldkey.equals("txnllfa_c")){
				oldkey = e.getKey();
				return false;
			}

			Object o = ut.getModel().getValueAt(e.getRow(), getBillCardPanel().getBodyColByKey(RegisterVO.TXNLLFA));

			oldkey = e.getKey();
			oldrow = e.getRow();

			if(o == null || ((String)o).trim().equals("")){
				return true;
			}else{
				return false;
			}
		}

		if(e.getPos() ==IBillItem.BODY && (e.getKey().equals("txnllfa_c"))){
			oldkey = e.getKey();
			oldrow = e.getRow();

			UIRefPane content = (UIRefPane)((BillItem)e.getSource()).getComponent();
			content.setButtonFireEvent(true);

			return true;
		}

		oldkey = e.getKey();
		oldrow = e.getRow();
		return true;
	}

	protected IItemEditListener[] getBillItemEditListener(){
		return new IItemEditListener[]{new DiscountCalculateBodyLltsEditListener(this,RegisterVO.LLTS, IBillItem.BODY),
                new DiscountCalculateBodyTxnllEditListener(this,RegisterVO.TXNLL, IBillItem.BODY),
                new DiscountCalculateBodyTxnllfaEditListener(this,"txnllfa_c", IBillItem.BODY),
                new DiscountCalculateBodyTxrqEditListener(this,RegisterVO.TXRQ, IBillItem.BODY),
                new DiscountCalculateBodyDiscountDelayDayNumEditListener(this,RegisterVO.DISCOUNTDELAYDAYNUM, IBillItem.BODY),
                new DiscountCalculateHeadLltsEditListener(this,RegisterVO.LLTS),
                new DiscountCalculateHeadTxnllEditListener(this,RegisterVO.TXNLL),
                new DiscountCalculateHeadTxnllfaEditListener(this,RegisterVO.TXNLLFA),
                new DiscountCalculateHeadTxrqEditListener(this,RegisterVO.TXRQ),
                new DiscountCalculateHeadDiscountDelayDayNumEditListener(this,RegisterVO.DISCOUNTDELAYDAYNUM),
                new BodyTxrllEditListener(this,RegisterVO.TXRLL, IBillItem.BODY),
                new BodyTxyllEditListener(this,RegisterVO.TXYLL, IBillItem.BODY),
                new DiscountCalculateHeadAllChoiceEditListener(this),
                new DiscountCalculateBodyChoiceEditListener(this)};

	}

	private void addItemEditListener(){
		IItemEditListener[] editlisteners=getBillItemEditListener();
		billEditEventDispatcher.addEditListeners(editlisteners);
	}

	/**
	 * 作者：lisg <br>
	 * 日期：2006-9-14
	 *编辑后处理方法
	 * @see nc.ui.trade.card.BillCardUI#afterEdit(nc.ui.pub.bill.BillEditEvent)
	 */
	@Override
	public void afterEdit(BillEditEvent e) {
		if(!needfire){
			return;
		}

		if(getRefTakenProccessor()!=null){
			if(e.getRow() < 0){
				getRefTakenProccessor().takenCardRefValue(e.getKey(), true);
			}else{
				getRefTakenProccessor().takenListRefValue(e.getKey(), e.getRow());
			}
		}
		billEditEventDispatcher.responseEditEvent(e);

		UICheckBox m_check = (UICheckBox)getBillCardPanel().getHeadItem("autocal").getComponent();
		boolean b_auto = m_check.isSelected();

		storeCurrentHeadData();
		getBillCardPanel().stopEditing();

		//是否进行自动运算
		try {
			if(b_auto){
				if(e.getRow() < 0)
					((DiscountCalculationEventHandler)getCardEventHandler()).calculationDiscount(null, false);
				else
					((DiscountCalculationEventHandler)getCardEventHandler()).calculationDiscount(new Integer[]{e.getRow()} , false);
			}else{
				((DiscountCalculationEventHandler)getCardEventHandler()).CalculateForeGround(null);
			}
		} catch (Exception e1) {
			Logger.error(e1.getMessage(),e1);
		}
		reCoverCurrentHeadData();

		//恢复焦点
		requestFocus(e);
	}

	/**
	 * 作者：lisg <br>
	 * +
	 * 日期：2006-9-7
	 *
	 * @see nc.ui.trade.card.BillCardUI#initEventListener()
	 */
	@Override
	protected void initEventListener() {
		super.initEventListener();
		new CurrTypeDecimalAdapterWithNULL(getBillCardPanel().getBillModel(), RegisterVO.PK_CURR, new String[]{RegisterVO.MONEYY,RegisterVO.TXJZ,RegisterVO.TXLX});
	}

	private void storeCurrentHeadData(){
		BillItem[] bis = getBillCardPanel().getHeadItems();

		for(BillItem bi : bis){
			if(bi.getValueObject() == null){
				oldstatues.remove(bi);
				continue;
			}
			oldstatues.put(bi, CommonUtil.cloneObject(bi.getValueObject()));
		}

		needfire = false;
	}

	private void reCoverCurrentHeadData(){
		Enumeration<BillItem> keys = oldstatues.keys();
		while(keys.hasMoreElements()){
			BillItem bi = keys.nextElement();
			bi.setValue(oldstatues.get(bi));
		}

		UIRefPane txnllfa = (UIRefPane)getBillCardPanel().getHeadItem(RegisterVO.TXNLLFA).getComponent();
		UIComboBox llts = (UIComboBox)getBillCardPanel().getHeadItem(RegisterVO.LLTS).getComponent();
		if(!txnllfa.getText().equals("")){
			llts.setEnabled(false);
		}

		needfire = true;
	}

	private Integer getDecimalPoint(){
		if(decimalpoint == null){
			initDecimalPoint();
		}

		return decimalpoint;
	}

	private Integer initDecimalPoint(){
		try {
			decimalpoint = OuterProxy.getSysInitQry().getParaInt("0001", IParamEnum.FI068);
		} catch (BusinessException e) {
			decimalpoint = 2;
			Logger.error(e.getMessage(),e);
		}

		return decimalpoint;
	}

	private void requestFocus(BillEditEvent e){
		switch(e.getPos()){
			case HEAD:
				getBillCardPanel().getHeadItem(e.getKey()).getComponent().requestFocus();
				break;
			case BODY:
				getBillCardPanel().getBillTable().requestFocus();
				int row = e.getRow();
				int col = getBillCardPanel().getBodyColByKey(e.getKey());
				col = getBillCardPanel().getBillTable().convertColumnIndexToView(col);
				getBillCardPanel().getBillTable().setRowSelectionInterval(row,row);
				getBillCardPanel().getBillTable().setColumnSelectionInterval(col,col);
				break;
		}
	}

	@Override
	public void setDefaultData() throws Exception {

	}

	@Override
	protected void setBodySpecialData(CircularlyAccessibleValueObject[] vos)
			throws Exception {
		if(getRefTakenProccessor() != null){
			 getRefTakenProccessor().cleanCache();
		}
		super.setBodySpecialData(vos);
	}

	protected BusinessDelegator createBusinessDelegator() {
		if(getUIControl().getBusinessActionType() == nc.ui.trade.businessaction.IBusinessActionType.BD){
			return new DiscountTotalRowBusinessDelegator(getUIControl(), getRefTakenProccessor());
		}else{
			return new BusinessDelegator();
		}
	}

	protected Object getValueByKey(Vector v, String key){
		return getRefTakenProccessor().getValueByTakenInVector(RegisterVO.TABLENAME, v, key);
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
			return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add","UPP3620ADD-000020")/*@res "当前公司未设置对应客商,无法进行贴现试算业务"*/;
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

	/**
	 * 单据类型下拉框定义
	 * 
	 * @param billtype
	 * @return
	 */
	public static DefaultConstEnum[] getBillStatus() {
		DefaultConstEnum[] retenum = null;
		Vector<DefaultConstEnum> v = new Vector<DefaultConstEnum>();
		v
				.add(new DefaultConstEnum(new Integer(IBillStatus.NOPASS),
						nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID(
								"uifactory", "UPPuifactory-000096")/*
																	 * @res
																	 * "审批不通过"
																	 */));
		v.add(new DefaultConstEnum(new Integer(IBillStatus.CHECKPASS),
				nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID(
						"uifactory", "UPPuifactory-000097")/* @res "审批通过" */));
		v
				.add(new DefaultConstEnum(new Integer(IBillStatus.CHECKGOING),
						nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID(
								"uifactory", "UPPuifactory-000098")/*
																	 * @res
																	 * "审批进行中"
																	 */));
		v.add(new DefaultConstEnum(new Integer(IBillStatus.FREE),
				nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID(
						"uifactory", "UPPuifactory-000101")/* @res "自由态" */));
		retenum = v.toArray(new DefaultConstEnum[0]);
		return retenum;
	}
}