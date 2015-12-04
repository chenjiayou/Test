package nc.ui.fbm.dailyreport;


import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.KeyStroke;

import nc.cmp.utils.StringUtil;
import nc.itf.cm.prv.CmpConst;
import nc.ui.cmp.iufo.CmpFctRunTestDlg;
import nc.ui.cmp.report.reportmanager.ColumnManager;
import nc.ui.cmp.report.reportmanager.ConditionManager;
import nc.ui.cmp.report.reportmanager.DSProcessInfoManager;
import nc.ui.cmp.report.reportuicommon.CommonReportQueryPanel;
import nc.ui.cmp.report.reportuicommon.ReportFrameForReportTemplet;
import nc.ui.glpub.IParent;
import nc.ui.glpub.IUiPanel;
import nc.ui.pub.ButtonObject;
import nc.ui.pub.beans.UILabel;
import nc.ui.pub.beans.UIRefPane;
import nc.ui.pub.bill.BillEditEvent;
import nc.ui.pub.formulaparse.FormulaParse;
import nc.ui.querytemplate.operator.IOperatorConstants;
import nc.vo.bd.access.IBdinfoConst;
import nc.vo.bd.notetype.INotetypeConst;
import nc.vo.cmp.report.BalanceKey;
import nc.vo.cmp.report.BalanceQueryVO;
import nc.vo.cmp.report.column.ColumnBasicInfo;
import nc.vo.cmp.report.column.ColumnDataGetterInfo;
import nc.vo.cmp.report.column.ReportColumn;
import nc.vo.cmp.report.condition.SimpleConditionUnit;
import nc.vo.cmp.report.datasetprocess.DataSetProcessType;
import nc.vo.cmp.report.datasetprocess.info.AddBalanceColProcessInfo;
import nc.vo.cmp.report.datasetprocess.info.SubstituteProcessInfo;
import nc.vo.cmp.report.datasetprocess.info.SubtotalProcessInfo;
import nc.vo.cmp.report.logicdb.ReportDataSourceTableInfo;
import nc.vo.pub.CircularlyAccessibleValueObject;
import nc.vo.pub.ValueObject;
import nc.vo.pub.lang.UFDate;
import nc.vo.querytemplate.querytype.IQueryType;

/**
* 票据日报表
* @author jianghao
* @version V5.5
* @since V5.5
* 2008-8-12
*/
public class NoteDailyRptToftPanel extends ReportFrameForReportTemplet implements IUiPanel {

	private IParent m_parent = null;  //报表的uimanager对象
	private ButtonObject[] subQuerybuttons = null;  //联查按钮

//	private INotetypeQry noteSrv = NCLocator.getInstance().lookup(INotetypeQry.class);
//	private NotetypeVO[] noteTypes = null;  //所有票据类型

	private boolean isFirstTimeLinkedQuery = true;  //是否首次被联查

	private CmpFctRunTestDlg testIufoFuncsDlg = null; //方便调试取数函数
	/**
	 *
	 */
	public NoteDailyRptToftPanel() {
		super();
		InputMap map = getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
       ActionMap am = getActionMap();
       String key = "CmpTestIufoFuncs";
       KeyStroke ks = KeyStroke.getKeyStroke(KeyEvent.VK_F12, KeyEvent.CTRL_MASK | KeyEvent.SHIFT_MASK, false);
       map.put(ks, key);
       am.put(key, new AbstractAction(){
       	public void actionPerformed(ActionEvent e) {
       		getTestIufoFuncsDlg().showModal();
       	}
       });
	}


	//查询条件
	@Override
	public CommonReportQueryPanel createCommonReportQueryPanel() {
		return new CommonReportQueryPanel()
		{
			@Override
			public void addControlsToUI() {
				addToRegularControlPanel(getLabelUnit(), getRefPanelUnit());
				addToRegularControlPanel(getLabelNoteType(), getRefPanelNoteType());
				addToRegularControlPanel(getLabelCurr(), getRefPanelCurrency());
				addToRegularControlPanel(getLabelBillStatus(),getCbBoxSignStatus());
				addToRegularControlPanel(getLabelDateType(),getCbBoxDateType());
				//just for layout
				addToRegularControlPanel(new UILabel(""), new UILabel(""));
				addToRegularControlPanel(getLabelDate(),getRefPanelBeginDate());
				addToRegularControlPanel(getLabelDateTo(),getRefPanelEndDate());
				addToRegularControlPanel(getLabelSystem(), getRefPanelSystem());
				addToRegularControlPanel(getLabelBillType(), getRefPanelBillType());
				addToChkBoxPanel(getChkBoxNotShowNoHappen(), null);
				addToRdButtonPanel(getLabelSort(), getRdButtonSortByCorp(),
						getRdButtonSortByCurr());

				//赋默认值
				/*----------------------- add lsl 2015-08-27 ---------------------------*/
				if(ce.getCorporation().getPrimaryKey().equals("0001")){		//集团下特殊处理
					Vector v =getRefPanelUnit().getRefModel().getData();
					List<String> lists = new ArrayList<String>();
					if(v!=null && v.size()>0){
						Vector a = null;
						for(int i=0;i<v.size();i++){
							a = (Vector)v.get(i);
							for(int j=0;j<a.size();j++){	//数组的长度都为5,但是数组有些位置是空值，其中第三位存放的是每个公司的PK值
								if(a.get(2)!=null && !lists.contains(a.get(2))){
									System.out.println(a.get(3));	//第4位是公司代码
									lists.add(a.get(2).toString());
								}
								
							}
							
						}
						
						if(lists.size()>0){
							getRefPanelUnit().setPKs(lists.toArray(new String [lists.size()]));
						}
					}
					
					
				}else{	//公司下
					getRefPanelUnit().setPK(ce.getCorporation().getPrimaryKey());
					System.out.println("------->"+ce.getCorporation().getPrimaryKey());
				}
				/*----------------------- add lsl 2015-08-27 ---------------------------*/
				
				
				
				UFDate date = ce.getDate();
				getRefPanelBeginDate().setText(date.toString());
				getRefPanelEndDate().setText(date.toString());
				getRdButtonSortByCorp().setSelected(true);
			}

			@Override
			public String check() {
				String superChk = super.check();
				if(superChk != null)
					return superChk;
				if(getRefPanelBeginDate().getText() == null
						|| getRefPanelBeginDate().getText().trim().equals(""))
					return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("report","UPPreport-000091")/*@res "起始日期不能为空！"*/;
				String endDate = getRefPanelEndDate().getText();
				if(StringUtil.isEmptyWithTrim(endDate)){
					return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("report","UPPreport-000013")/*@res "结束日期不能为空！"*/;
				}
				if(!(endDate == null || endDate.trim().equals("")) && UFDate.getDate(endDate).before(UFDate.getDate(getRefPanelBeginDate().getText())))
					return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("report","UPPreport-000092")/*@res "起始日期不能在结束日期之后！"*/;
				return null;
			}
		};
	}


	@Override
	public String getFunNode() {
		return "20040708";//20040708
	}


	@Override
	public String[] getHideCurrFormatColCodes() {
		return new String[]{"startamount", "inamount",
				"outamount", "endamount"};
	}


	@Override
	public String[] getQryObjectCodeColCodes() {
		return null;
	}


	@Override
	public String[] getQryObjectKeyColCodes() {
		return null;
	}


	@Override
	public String[] getQryObjectNameColCodes() {
		return null;
	}




	/* (non-Javadoc)
	 * @see nc.ui.cmp.report.reportuicommon.ReportFrameForReportTemplet#getExtraReportColumns()
	 */
	@Override
	protected ReportColumn[] getExtraReportColumns() {
		ReportColumn[] rcs = new ReportColumn[4];
		rcs[0] = new ReportColumn();
		rcs[0].setColBasicInfo(new ColumnBasicInfo("corpcode", true));		//change lsl
		rcs[1] = new ReportColumn();
		rcs[1].setColBasicInfo(new ColumnBasicInfo("currtypecode", true));
		rcs[2] = new ReportColumn();
		rcs[2].setColBasicInfo(new ColumnBasicInfo("notetypecode", true));
		rcs[3] = new ReportColumn();
		rcs[3].setColBasicInfo(new ColumnBasicInfo("memo", true)); //为了处理小计行显示
		return rcs;
	}


	//初始化报表查询条件管理器
	@Override
	public void initConditionManager(ConditionManager manager) {
		//报表简单查询条件基本单位，形如“字段 条件操作符 值”
		SimpleConditionUnit condUnit = new SimpleConditionUnit(fundFormField,IOperatorConstants.EQ, true);
		condUnit.setValueList(new Integer[]{new Integer(CmpConst.BILL_DEPOSIT)});
		manager.addCondition(condUnit);
		manager.addCondition(isBillRecordCU);
		manager.addCondition(drConditionUnit);
		manager.addCondition(new SimpleConditionUnit(pk_corpField,IOperatorConstants.IN, false));
		manager.addCondition(new SimpleConditionUnit(pk_currtypeField,IOperatorConstants.IN, false));
		manager.addCondition(new SimpleConditionUnit(pk_notetypeField,IOperatorConstants.IN, false));
		manager.addCondition(new SimpleConditionUnit(billDateField,IOperatorConstants.GE, false));
		manager.addCondition(new SimpleConditionUnit(billDateField,IOperatorConstants.LE, false));
		manager.addCondition(new SimpleConditionUnit(signDateField,IOperatorConstants.GE, false));
		manager.addCondition(new SimpleConditionUnit(signDateField,IOperatorConstants.LE, false));
		manager.addCondition(new SimpleConditionUnit(payDateField,IOperatorConstants.GE, false));
		manager.addCondition(new SimpleConditionUnit(payDateField,IOperatorConstants.LE, false));
		manager.addCondition(new SimpleConditionUnit(systemCodeField, IOperatorConstants.IN, false));
		manager.addCondition(new SimpleConditionUnit(billTypeField, IOperatorConstants.IN, false));
	}


	/**
	 * 完成报表数据集处理信息初始化工作，例如预置能提前确定的数据集处理信息对象。
	 * 因为加工信息很可能取决于用户在查询对话框上的选择，所以部分信息需要在resetManagerByDlg中设置
	 * @param processInfoManager
	 */
	@Override
	public void initDSProcessInfoManager(DSProcessInfoManager processInfoManager) {
		//第一步添加日报表期初列--“昨日余额”列
		AddBalanceColProcessInfo addBcPi = new AddBalanceColProcessInfo();
		addBcPi.setJoinLeftCol(new String[]{"corp", "pk_notetype", "currtype"});
		addBcPi.setJoinRightCol(new String[]{BalanceKey.PK_CORP, BalanceKey.PK_NOTETYPE,BalanceKey.PK_CURRTYPE});
		addBcPi.setAppendLeftCol(new String[]{"corp", "pk_notetype", "currtype","startamount", "startlocalamount"});
		addBcPi.setAppendRightCol(new String[]{BalanceKey.PK_CORP, BalanceKey.PK_NOTETYPE,
									BalanceKey.PK_CURRTYPE, BalanceKey.PRIMAL_AMOUNT, BalanceKey.LOCAL_AMOUNT});
		addBcPi.setArgValues(new Object[]{getColumnManager().getAttributeNames()});

		BalanceQueryVO bqvo = new BalanceQueryVO();
		//单据状态需要reset
		bqvo.setInit(true);
		bqvo.setGroupByKey(new String[]{BalanceKey.PK_CORP, BalanceKey.PK_CURRTYPE,
				BalanceKey.PK_NOTETYPE});
		bqvo.setFundForm(CmpConst.BILL_DEPOSIT);
		addBcPi.setBqvo(bqvo);
		processInfoManager.addProcessInfo(DataSetProcessType.ADD_BALANCE_COL, addBcPi);

		//第二步替换公司和币种
		SubstituteProcessInfo sttPi = new SubstituteProcessInfo();
		sttPi.setCorpCol("corp");
		ArrayList<String[]> substituteCol = new ArrayList<String[]>();
		ArrayList<String[]> joinCol = new ArrayList<String[]>();
		ArrayList extraInfo = new ArrayList();
		substituteCol.add(new String[]{"corpname","corpcode"});
		substituteCol.add(new String[]{"currtypename","currtypecode"});
		joinCol.add(new String[]{"corp"});
		joinCol.add(new String[]{"currtype"});
		extraInfo.add(IBdinfoConst.CORP);
		extraInfo.add(IBdinfoConst.CURRTYPE);
		sttPi.setSubstituteCol(substituteCol);
		sttPi.setJoinCol(joinCol);
		sttPi.setExtraInfo(extraInfo);
		processInfoManager.addProcessInfo(DataSetProcessType.SUBSTITUTE, sttPi);


		//第三步小计
		SubtotalProcessInfo stPi = new SubtotalProcessInfo();
		stPi.setSubtotalByCol(new String[]{"corp"});
		stPi.setLimitSumGen(0);
		stPi.setSubtotalObject(new String[]{"startamount", "startlocalamount",
				"inamount", "inlocalamount",
				"outamount", "outlocalamount"});
		stPi.setSubtotalShowCol(new String[]{"corpname"});
		stPi.setShowText(new String[]{nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("report","UPPreport-000009")/*@res "公司小计"*/});
		stPi.setRequireOutputDetail(true);
		stPi.setTotalSum(true);
		processInfoManager.addProcessInfo(DataSetProcessType.SUBTOTAL, stPi);
	}


	/**
	 * 完成报表数据源初始化工作，确定报表从哪些数据库表中查询
	 * @param dataSourceTableInfo
	 */
	@Override
	public void initDataSourceTableInfo(
			ReportDataSourceTableInfo dataSourceTableInfo) {
		//预处理日发生表不能满足某些情况，例如查结算状态+单据日期，仍用结算表
//		dataSourceTableInfo.setMainTable("cmp_occurdaily");
		dataSourceTableInfo.setMainTable("cmp_detail");
	}


	@Override
	public boolean isComplexConditionDlgUsed() {
		return false;
	}


	@Override
	public void resetManagerByDlg(ColumnManager colManager,
			ConditionManager condManager, DSProcessInfoManager piManager) {
		CommonReportQueryPanel panel = getCommonReportQueryPanel();
		resetConditionManager(condManager, panel);
		resetDSProcessInfoManager(piManager, panel);
	}


	private void resetConditionManager(ConditionManager condManager,
			CommonReportQueryPanel panel) {
		SimpleConditionUnit scu = (SimpleConditionUnit)condManager.getSimpleConditionUnit
		(pk_corpField, IOperatorConstants.IN, false);
		scu.setValueList(panel.getRefPanelUnit().getRefPKs());
		scu = (SimpleConditionUnit)condManager.getSimpleConditionUnit(pk_notetypeField,
				IOperatorConstants.IN, false);
		if(null == panel.getRefPanelNoteType().getRefPK()){
			panel.getRefPanelNoteType().getRefModel().setWherePart("pk_corp ='0001'");
			panel.getRefPanelNoteType().getRefModel().reloadData();
			Vector vec = panel.getRefPanelNoteType().getRefModel().getVecData();
			List<String> pks = new ArrayList<String>(vec.size());
			for(int i=0;null != vec && i<vec.size();i++){
				Vector vo = (Vector) vec.get(i);
				if(null != vo){
					pks.add(vo.get(vo.size() - 1).toString());
				}
			}
			scu.setValueList(pks.toArray(new String[0]));
		}else{
			scu.setValueList(panel.getRefPanelNoteType().getRefPKs());
		}
		scu = (SimpleConditionUnit)condManager.getSimpleConditionUnit(pk_currtypeField,
				IOperatorConstants.IN, false);
		scu.setValueList(panel.getRefPanelCurrency().getRefPKs());
		resetBillStatusCondition(condManager, ((Integer)panel.getCbBoxSignStatus().getSelectdItemValue()).intValue());

		clearDateCondition(condManager);
		Object dateType = panel.getCbBoxDateType().getSelectedItem();
		if(dateType.equals(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("report","UC000-0000799")/*@res "单据日期"*/))
			scu = (SimpleConditionUnit)condManager.getSimpleConditionUnit(billDateField,
					IOperatorConstants.GE, false);
		else if(dateType.equals(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("report","UC000-0003103")/*@res "签字日期"*/))
			scu = (SimpleConditionUnit)condManager.getSimpleConditionUnit(signDateField,
					IOperatorConstants.GE, false);
		else
			scu = (SimpleConditionUnit)condManager.getSimpleConditionUnit(payDateField,
					IOperatorConstants.GE, false);
		if(panel.getRefPanelBeginDate().getText() != null &&
				!panel.getRefPanelBeginDate().getText().trim().equals(""))
		{
			scu.setValueList(new String[]{panel.getRefPanelBeginDate().getText()});
		}
		else
			scu.setValueList(null);

		if(dateType.equals(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("report","UC000-0000799")/*@res "单据日期"*/))
			scu = (SimpleConditionUnit)condManager.getSimpleConditionUnit(billDateField,
					IOperatorConstants.LE, false);
		else if(dateType.equals(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("report","UC000-0003103")/*@res "签字日期"*/))
			scu = (SimpleConditionUnit)condManager.getSimpleConditionUnit(signDateField,
					IOperatorConstants.LE, false);
		else
			scu = (SimpleConditionUnit)condManager.getSimpleConditionUnit(payDateField,
					IOperatorConstants.LE, false);
		if(panel.getRefPanelEndDate().getText() != null &&
				!panel.getRefPanelEndDate().getText().trim().equals(""))
			scu.setValueList(new String[]{panel.getRefPanelEndDate().getText()});
		else
			scu.setValueList(null);

		scu = (SimpleConditionUnit)condManager.getSimpleConditionUnit(systemCodeField, IOperatorConstants.IN, false);
		scu.setValueList(panel.getRefPanelSystem().getRefCodes());
		scu = (SimpleConditionUnit)condManager.getSimpleConditionUnit(billTypeField, IOperatorConstants.IN, false);
		scu.setValueList(panel.getRefPanelBillType().getRefPKs());
	}

	private void clearDateCondition(ConditionManager condManager) {
		((SimpleConditionUnit)condManager.getSimpleConditionUnit(billDateField,
				IOperatorConstants.GE, false)).setValueList(null);
		((SimpleConditionUnit)condManager.getSimpleConditionUnit(signDateField,
				IOperatorConstants.GE, false)).setValueList(null);
		((SimpleConditionUnit)condManager.getSimpleConditionUnit(payDateField,
				IOperatorConstants.GE, false)).setValueList(null);
		((SimpleConditionUnit)condManager.getSimpleConditionUnit(billDateField,
				IOperatorConstants.LE, false)).setValueList(null);
		((SimpleConditionUnit)condManager.getSimpleConditionUnit(signDateField,
				IOperatorConstants.LE, false)).setValueList(null);
		((SimpleConditionUnit)condManager.getSimpleConditionUnit(payDateField,
				IOperatorConstants.LE, false)).setValueList(null);
	}


	private void resetDSProcessInfoManager(DSProcessInfoManager piManager,
			CommonReportQueryPanel panel) {
		AddBalanceColProcessInfo abcPi = (AddBalanceColProcessInfo)piManager.getProcessInfo(
				DataSetProcessType.ADD_BALANCE_COL);
		abcPi.setJoinAll(!panel.getChkBoxNotShowNoHappen().isSelected());
		BalanceQueryVO bqvo = abcPi.getBqvo();
		if(bqvo == null)
		{
			bqvo = new BalanceQueryVO();
			abcPi.setBqvo(bqvo);
		}
		String[] pks_corp = panel.getRefPanelUnit().getRefPKs();
		String[] pks_currtype = panel.getRefPanelCurrency().getRefPKs();
		bqvo.setPks_corp(pks_corp);
		if(null == panel.getRefPanelNoteType().getRefPK()){
			Vector vec = panel.getRefPanelNoteType().getRefModel().getVecData();
			List<String> pks = new ArrayList<String>(vec.size());
			for(int i=0;null != vec && i<vec.size();i++){
				Vector vo = (Vector) vec.get(i);
				if(null != vo){
					pks.add(vo.get(vo.size() - 1).toString());
				}
			}
			bqvo.setPks_notetype(pks.toArray(new String[0]));
		}else{
			bqvo.setPks_notetype(panel.getRefPanelNoteType().getRefPKs());
		}
//		bqvo.setPks_notetype(panel.getRefPanelNoteType().getRefPKs());
		bqvo.setPks_currtype(pks_currtype);
		bqvo.setBillStatus(((Integer)panel.getCbBoxSignStatus().getSelectdItemValue())
				.intValue());
		bqvo.setDateType(panel.getCbBoxDateType().getSelectedItem().toString());
		if(panel.getRefPanelBeginDate().getText() != null &&
				!panel.getRefPanelBeginDate().getText().trim().equals(""))
		{
			bqvo.setDate(new UFDate(panel.getRefPanelBeginDate().getText()));
		}
		else
			bqvo.setDate(null);  //余额查询dao会有问题

		//单公司或单币种不小计
		if((pks_corp.length == 1 && panel.getRdButtonSortByCorp().isSelected())
				|| (pks_currtype != null && pks_currtype.length == 1
						&& panel.getRdButtonSortByCurr().isSelected()))
		{
			piManager.removeProcessInfo(DataSetProcessType.SUBTOTAL);
			return;
		}
		else{
			piManager.removeProcessInfo(DataSetProcessType.SUBTOTAL);
			return;
		}
//		piManager.unRemovedProcessInfo(DataSetProcessType.SUBTOTAL);
//		SubtotalProcessInfo stPi = (SubtotalProcessInfo)piManager.getProcessInfo(DataSetProcessType.SUBTOTAL);
//		if(panel.getRdButtonSortByCorp().isSelected())
//		{
//			stPi.setSubtotalByCol(new String[]{"corpcode"});
//			stPi.setSubtotalShowCol(new String[]{"corpname"});
//			stPi.setShowText(new String[]{nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("report","UPPreport-000009")/*@res "公司小计"*/});
//		}
//		else
//		{
//			stPi.setSubtotalByCol(new String[]{"currtypecode"});
//			stPi.setSubtotalShowCol(new String[]{"currtypename"});
//			stPi.setShowText(new String[]{nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("report","UPPreport-000010")/*@res "币种小计"*/});
//		}
	}


	public ValueObject[] processResultVOs(ValueObject[] resultVos) {
		if(resultVos == null || resultVos.length == 0)
			return null;
		resultVos = super.processResultVOs(resultVos);
		CircularlyAccessibleValueObject[] vos = (CircularlyAccessibleValueObject[])resultVos;
		//CIntelVO加工工具处理局限
		Object expla = null;
		for (int i = 0; i < vos.length; i++) {
			expla = vos[i].getAttributeValue("corpname");
			if(expla != null && (expla.toString().trim().indexOf(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("report","UPPreport-000006")/*@res "小计"*/) > -1
					|| expla.toString().trim().indexOf(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("report","UPPreport-000007")/*@res "总计"*/) > -1))
			{
				vos[i].setAttributeValue("pk_notetype", null);
				vos[i].setAttributeValue("currtypename", null);
			}
			expla = vos[i].getAttributeValue("currtypename");
			if(expla != null && (expla.toString().trim().indexOf(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("report","UPPreport-000006")/*@res "小计"*/) > -1
					|| expla.toString().trim().indexOf(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("report","UPPreport-000007")/*@res "总计"*/) > -1))
			{
				vos[i].setAttributeValue("pk_notetype", null);
				vos[i].setAttributeValue("corpname", null);
			}
		}
		addNoteClass(vos);
		return resultVos;
	}



	private void addNoteClass(CircularlyAccessibleValueObject[] vos) {
		FormulaParse parser = new FormulaParse();
		parser.setExpress("notetype->getColvalue(bd_notetype,noteclass,pk_notetype,pk)");
		String noteClassInt = "";
		String noteClass = "";
		for (int i = 0; i < vos.length; i++) {
			if(vos[i].getAttributeValue("pk_notetype") != null)
			{
				parser.addVariable("pk", vos[i].getAttributeValue("pk_notetype"));
				noteClassInt = parser.getValue();
				if(noteClassInt != null && !noteClassInt.trim().equals(""))
					switch (Integer.valueOf(noteClassInt).intValue()) {
					case INotetypeConst.NOTECLASS_CHEQUE:
						noteClass = nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("report","UPPreport-000078")/*@res "支票"*/;
						break;
					case INotetypeConst.NOTECLASS_COMMERCIALDRAFT:
						noteClass = nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("report","UPPreport-000079")/*@res "商业汇票"*/;
						break;
					case INotetypeConst.NOTECLASS_BANKDRAFT:
						noteClass = nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("report","UPPreport-000080")/*@res "银行汇票"*/;
						break;
					case INotetypeConst.NOTECLASS_CASHIERCHEQUE:
						noteClass = nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("report","UPPreport-000081")/*@res "本票"*/;
						break;
					case INotetypeConst.NOTECLASS_OTHER:
						noteClass = nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("report","UPPreport-000082")/*@res "其他"*/;
						break;
					default:
						break;
					}
				vos[i].setAttributeValue("noteclass", noteClass);
			}
		}
	}


	@Override
	public void setDataGetterInfo(ColumnDataGetterInfo getterInfo, String key) {
		if("corp".equals(key))  //公司
		{
			getterInfo.setFieldUnit(pk_corpField);
			getterInfo.setGroupByField(true);
		}
		else if("currtype".equals(key))  //币种
		{
			getterInfo.setFieldUnit(pk_currtypeField);
			getterInfo.setGroupByField(true);
		}
		else if("pk_notetype".equals(key))  //票据类型pk
		{
			getterInfo.setFieldUnit(pk_notetypeField);
			getterInfo.setGroupByField(true);
		}
		else if("inamount".equals(key))  //收入原币
		{
			getterInfo.setFieldUnit(receiveField);
			getterInfo.setSumField(true);
		}
		else if("inlocalamount".equals(key))  //收入本币
		{
			getterInfo.setFieldUnit(receiveLField);
			getterInfo.setSumField(true);
		}
		else if("outamount".equals(key))  //支出原币
		{
			getterInfo.setFieldUnit(payField);
			getterInfo.setSumField(true);
		}
		else if("outlocalamount".equals(key))  //支出本币
		{
			getterInfo.setFieldUnit(payLField);
			getterInfo.setSumField(true);
		}
	}


	@Override
	public void showHeaderData() {
		CommonReportQueryPanel panel = getCommonReportQueryPanel();
		UIRefPane corpPanel = (UIRefPane)getReportTemplet().
		getHeadItem("corpname").getComponent();
		corpPanel.setText(panel.getRefPanelUnit().getText());
		UIRefPane datePanel = (UIRefPane)getReportTemplet().
		getHeadItem("date").getComponent();
		String dateRange = "";
		if(panel.getRefPanelBeginDate().getText() != null
				&& !panel.getRefPanelBeginDate().getText().trim().equals(""))
			dateRange += panel.getRefPanelBeginDate().getText() + " ---- ";
		if(panel.getRefPanelEndDate().getText() != null
				&& !panel.getRefPanelEndDate().getText().trim().equals(""))
			dateRange += panel.getRefPanelEndDate().getText();
		datePanel.setText(dateRange);
	}


	@Override
	public ButtonObject[] createMainFrameButtons() {
		ButtonObject subQueryBtn = new ButtonObject(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("report","UPPreport-000095")/*@res "联查日记账"*/, nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("report","UPPreport-000095")/*@res "联查日记账"*/, 2, "联查日记账");	/*-=notranslate=-*/
		subQueryBtn.setTag("联查日记账");
		subQueryBtn.setEnabled(false);
		ButtonObject refreshBtn = getRefreshBtn();
		ButtonObject printBtn = getPrintBtn();
		ButtonObject[] buttons = new ButtonObject[4];
		buttons[0] = getQueryBtn();
		buttons[1] = subQueryBtn;
		buttons[2] = printBtn;
		buttons[3] = refreshBtn;
		return buttons;
	}



	@Override
	public String getTitle() {
		return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("report","UPPreport-000101")/*@res "票据日报表"*/;
	}


	@Override
	public void onButtonClicked(ButtonObject bo) {
		if(bo == getQueryBtn())
		{
			onQuery();
		}
		else if(bo.getTag().equals("刷新"))
			refresh();
		else if(bo.getTag().equals("直接打印"))
			directPrint();
		else if(bo.getTag().equals("模板打印"))
			templetPrint();
		else if(bo.getTag().equals("联查日记账"))
			queryDailyBook();
		else if(bo.getTag().equals("返回"))
			m_parent.closeMe();
	}


	private void queryDailyBook()
	{
		int i = getReportTemplet().getBillTable().getSelectedRow();
		CircularlyAccessibleValueObject[] vos = getReportData();
		CommonReportQueryPanel panel = getCommonReportQueryPanel();
		Object[] condValues = new Object[9];
		condValues[0] = vos[i].getAttributeValue("corp"); //公司pk
		condValues[1] = vos[i].getAttributeValue("pk_notetype"); //票据类型pk
		condValues[2] = vos[i].getAttributeValue("currtype"); //币种pk
		condValues[3] = panel.getCbBoxSignStatus().getSelectdItemValue(); //单据状态
		condValues[4] = panel.getCbBoxDateType().getSelectedItem(); //日期类型
		condValues[5] = panel.getRefPanelBeginDate().getText(); //起始日期
		condValues[6] = panel.getRefPanelEndDate().getText(); //终止日期
		condValues[7] = panel.getRefPanelSystem().getRefPKs(); //业务系统pk
		condValues[8] = panel.getRefPanelBillType().getRefPKs(); //单据类型（交易类型）
		IUiPanel uiPanel = (IUiPanel) m_parent.showNext("nc.ui.cmp.report.dailybook.NoteDailyBookToftPanel");
		uiPanel.invoke(condValues, "queryFromDailyReport");
	}


	public void bodyRowChange(BillEditEvent e) {
		int selected = e.getRow();
		String corp = (String)getReportData()[selected].getAttributeValue("corpname");
		String currtype = (String)getReportData()[selected].getAttributeValue("currtypename");
		ButtonObject subQryBtn = null;
		for (int i = 0; i < getButtons().length; i++) {
			if(getButtons()[i].getTag().equals("联查日记账"))
			{
				subQryBtn = getButtons()[i];
				break;
			}
		}
		if((corp != null && (corp.indexOf(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("report","UPPreport-000006")/*@res "小计"*/) > -1 || corp.indexOf(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("report","UPPreport-000007")/*@res "总计"*/) > -1))
				|| (currtype != null && (currtype.indexOf(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("report","UPPreport-000006")/*@res "小计"*/) > -1 || currtype.indexOf(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("report","UPPreport-000007")/*@res "总计"*/) > -1)))
			subQryBtn.setEnabled(false);
		else
			subQryBtn.setEnabled(true);
		updateButtons();
	}


	public void addListener(Object objListener, Object objUserdata) {

	}


	public Object invoke(Object objData, Object objUserData) {
		Object[] condValues = null;
		if(objUserData != null)
		{
			//为联查界面重设按钮
			if(subQuerybuttons == null)
			{
				ButtonObject refreshBtn = getRefreshBtn();
				ButtonObject printBtn = getPrintBtn();
				ButtonObject subQueryBtn = new ButtonObject(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("report","UPPreport-000095")/*@res "联查日记账"*/, nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("report","UPPreport-000095")/*@res "联查日记账"*/, 2, "联查日记账");	/*-=notranslate=-*/
				subQueryBtn.setTag("联查日记账");
				subQueryBtn.setPowerContrl(false);
				ButtonObject returnBtn = new ButtonObject(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("report","UC001-0000038")/*@res "返回"*/, nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("report","UC001-0000038")/*@res "返回"*/, 2, "返回");	/*-=notranslate=-*/
				returnBtn.setTag("返回");
				returnBtn.setPowerContrl(false);
				subQuerybuttons = new ButtonObject[4];
				subQuerybuttons[0] = refreshBtn;
				subQuerybuttons[1] = printBtn;
				subQuerybuttons[2] = subQueryBtn;
				subQuerybuttons[3] = returnBtn;
				setButtons(subQuerybuttons);
			}
			condValues = (Object[])objData;
		}
		subQuery(condValues, objUserData.toString());

		return null;
	}


	public void nextClosed() {

	}


	public void removeListener(Object objListener, Object objUserdata) {

	}


	public void showMe(IParent parent) {
		parent.getUiManager().removeAll();
		parent.getUiManager().add(this,this.getName());
		m_parent = parent;
	}


	@Override
	public void resetManagerForSubQuery(ColumnManager colManager, ConditionManager condManager,
			DSProcessInfoManager piManager, Object[] condValues, String queryName) {
		if(isFirstTimeLinkedQuery )
		{
			isFirstTimeLinkedQuery = false;
			SimpleConditionUnit condUnit = new SimpleConditionUnit(fundFormField, IOperatorConstants.EQ, true);
			condUnit.setValueList(new Integer[]{new Integer(CmpConst.BILL_DEPOSIT)});
			condManager.addCondition(condUnit);
			condManager.addCondition(isBillRecordCU);
			condManager.addCondition(drConditionUnit);
		}
		CommonReportQueryPanel panel = getCommonReportQueryPanel();
		if(condValues[0] instanceof String)
			panel.getRefPanelUnit().setPK(condValues[0]);  //公司pk
		else if(condValues[0] instanceof String[])
			panel.getRefPanelUnit().setPKs((String[])condValues[0]);  //公司pk
		if("fromFundDailyReport".equals(queryName))  //来自资金日报表
		{
			panel.getRefPanelCurrency().setPK(condValues[1]); //币种pk
			panel.getCbBoxSignStatus().setSelectedIndex(((Integer)condValues[2]).intValue());//单据状态
			panel.getCbBoxDateType().setSelectedItem(condValues[3]);  //日期类型
			panel.getRefPanelBeginDate().setText((String)condValues[4]); //起始日期
			panel.getRefPanelEndDate().setText((String)condValues[5]); //终止日期
			panel.getRefPanelSystem().setPKs((String[])condValues[6]); //业务系统pk
			panel.getRefPanelBillType().setPKs((String[])condValues[7]); //单据类型（交易类型）
		}
		else  //来自资金余额汇总表
		{
			panel.getRefPanelCurrency().setPK(condValues[1]); //币种pk
			panel.getCbBoxSignStatus().setSelectedIndex(2);  //已结算
			panel.getCbBoxDateType().setSelectedIndex(2);  //结算日期
			panel.getRefPanelBeginDate().setText((String)condValues[2]);
			panel.getRefPanelEndDate().setText((String)condValues[2]);
		}
		resetManagerByDlg(colManager, condManager, piManager);
	}

	@Override
	public void showHeaderDataForSubQuery(Object[] condValues, String queryName) {
		showHeaderData();
		int len = condValues.length;
		if(condValues[len-1] != null && condValues[len-1].equals(CURR_FORMAT))
			getFormatComboInHeader().setSelectedItem(condValues[len-1]);

	}
	
	public CmpFctRunTestDlg getTestIufoFuncsDlg() {
		if(testIufoFuncsDlg == null)
			testIufoFuncsDlg = new CmpFctRunTestDlg(this);
		return testIufoFuncsDlg;
	}
	@Override
	/**
	 * 设置异步查询类型，加载查询方式
	 */
	public int getQueryType() {
		return IQueryType.ASYN_ONLY;
	}
}
