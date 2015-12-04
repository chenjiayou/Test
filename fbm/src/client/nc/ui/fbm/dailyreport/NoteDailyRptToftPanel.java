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
* Ʊ���ձ���
* @author jianghao
* @version V5.5
* @since V5.5
* 2008-8-12
*/
public class NoteDailyRptToftPanel extends ReportFrameForReportTemplet implements IUiPanel {

	private IParent m_parent = null;  //�����uimanager����
	private ButtonObject[] subQuerybuttons = null;  //���鰴ť

//	private INotetypeQry noteSrv = NCLocator.getInstance().lookup(INotetypeQry.class);
//	private NotetypeVO[] noteTypes = null;  //����Ʊ������

	private boolean isFirstTimeLinkedQuery = true;  //�Ƿ��״α�����

	private CmpFctRunTestDlg testIufoFuncsDlg = null; //�������ȡ������
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


	//��ѯ����
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

				//��Ĭ��ֵ
				/*----------------------- add lsl 2015-08-27 ---------------------------*/
				if(ce.getCorporation().getPrimaryKey().equals("0001")){		//���������⴦��
					Vector v =getRefPanelUnit().getRefModel().getData();
					List<String> lists = new ArrayList<String>();
					if(v!=null && v.size()>0){
						Vector a = null;
						for(int i=0;i<v.size();i++){
							a = (Vector)v.get(i);
							for(int j=0;j<a.size();j++){	//����ĳ��ȶ�Ϊ5,����������Щλ���ǿ�ֵ�����е���λ��ŵ���ÿ����˾��PKֵ
								if(a.get(2)!=null && !lists.contains(a.get(2))){
									System.out.println(a.get(3));	//��4λ�ǹ�˾����
									lists.add(a.get(2).toString());
								}
								
							}
							
						}
						
						if(lists.size()>0){
							getRefPanelUnit().setPKs(lists.toArray(new String [lists.size()]));
						}
					}
					
					
				}else{	//��˾��
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
					return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("report","UPPreport-000091")/*@res "��ʼ���ڲ���Ϊ�գ�"*/;
				String endDate = getRefPanelEndDate().getText();
				if(StringUtil.isEmptyWithTrim(endDate)){
					return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("report","UPPreport-000013")/*@res "�������ڲ���Ϊ�գ�"*/;
				}
				if(!(endDate == null || endDate.trim().equals("")) && UFDate.getDate(endDate).before(UFDate.getDate(getRefPanelBeginDate().getText())))
					return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("report","UPPreport-000092")/*@res "��ʼ���ڲ����ڽ�������֮��"*/;
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
		rcs[3].setColBasicInfo(new ColumnBasicInfo("memo", true)); //Ϊ�˴���С������ʾ
		return rcs;
	}


	//��ʼ�������ѯ����������
	@Override
	public void initConditionManager(ConditionManager manager) {
		//����򵥲�ѯ����������λ�����硰�ֶ� ���������� ֵ��
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
	 * ��ɱ������ݼ�������Ϣ��ʼ������������Ԥ������ǰȷ�������ݼ�������Ϣ����
	 * ��Ϊ�ӹ���Ϣ�ܿ���ȡ�����û��ڲ�ѯ�Ի����ϵ�ѡ�����Բ�����Ϣ��Ҫ��resetManagerByDlg������
	 * @param processInfoManager
	 */
	@Override
	public void initDSProcessInfoManager(DSProcessInfoManager processInfoManager) {
		//��һ������ձ����ڳ���--����������
		AddBalanceColProcessInfo addBcPi = new AddBalanceColProcessInfo();
		addBcPi.setJoinLeftCol(new String[]{"corp", "pk_notetype", "currtype"});
		addBcPi.setJoinRightCol(new String[]{BalanceKey.PK_CORP, BalanceKey.PK_NOTETYPE,BalanceKey.PK_CURRTYPE});
		addBcPi.setAppendLeftCol(new String[]{"corp", "pk_notetype", "currtype","startamount", "startlocalamount"});
		addBcPi.setAppendRightCol(new String[]{BalanceKey.PK_CORP, BalanceKey.PK_NOTETYPE,
									BalanceKey.PK_CURRTYPE, BalanceKey.PRIMAL_AMOUNT, BalanceKey.LOCAL_AMOUNT});
		addBcPi.setArgValues(new Object[]{getColumnManager().getAttributeNames()});

		BalanceQueryVO bqvo = new BalanceQueryVO();
		//����״̬��Ҫreset
		bqvo.setInit(true);
		bqvo.setGroupByKey(new String[]{BalanceKey.PK_CORP, BalanceKey.PK_CURRTYPE,
				BalanceKey.PK_NOTETYPE});
		bqvo.setFundForm(CmpConst.BILL_DEPOSIT);
		addBcPi.setBqvo(bqvo);
		processInfoManager.addProcessInfo(DataSetProcessType.ADD_BALANCE_COL, addBcPi);

		//�ڶ����滻��˾�ͱ���
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


		//������С��
		SubtotalProcessInfo stPi = new SubtotalProcessInfo();
		stPi.setSubtotalByCol(new String[]{"corp"});
		stPi.setLimitSumGen(0);
		stPi.setSubtotalObject(new String[]{"startamount", "startlocalamount",
				"inamount", "inlocalamount",
				"outamount", "outlocalamount"});
		stPi.setSubtotalShowCol(new String[]{"corpname"});
		stPi.setShowText(new String[]{nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("report","UPPreport-000009")/*@res "��˾С��"*/});
		stPi.setRequireOutputDetail(true);
		stPi.setTotalSum(true);
		processInfoManager.addProcessInfo(DataSetProcessType.SUBTOTAL, stPi);
	}


	/**
	 * ��ɱ�������Դ��ʼ��������ȷ���������Щ���ݿ���в�ѯ
	 * @param dataSourceTableInfo
	 */
	@Override
	public void initDataSourceTableInfo(
			ReportDataSourceTableInfo dataSourceTableInfo) {
		//Ԥ�����շ�����������ĳЩ�������������״̬+�������ڣ����ý����
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
		if(dateType.equals(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("report","UC000-0000799")/*@res "��������"*/))
			scu = (SimpleConditionUnit)condManager.getSimpleConditionUnit(billDateField,
					IOperatorConstants.GE, false);
		else if(dateType.equals(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("report","UC000-0003103")/*@res "ǩ������"*/))
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

		if(dateType.equals(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("report","UC000-0000799")/*@res "��������"*/))
			scu = (SimpleConditionUnit)condManager.getSimpleConditionUnit(billDateField,
					IOperatorConstants.LE, false);
		else if(dateType.equals(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("report","UC000-0003103")/*@res "ǩ������"*/))
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
			bqvo.setDate(null);  //����ѯdao��������

		//����˾�򵥱��ֲ�С��
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
//			stPi.setShowText(new String[]{nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("report","UPPreport-000009")/*@res "��˾С��"*/});
//		}
//		else
//		{
//			stPi.setSubtotalByCol(new String[]{"currtypecode"});
//			stPi.setSubtotalShowCol(new String[]{"currtypename"});
//			stPi.setShowText(new String[]{nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("report","UPPreport-000010")/*@res "����С��"*/});
//		}
	}


	public ValueObject[] processResultVOs(ValueObject[] resultVos) {
		if(resultVos == null || resultVos.length == 0)
			return null;
		resultVos = super.processResultVOs(resultVos);
		CircularlyAccessibleValueObject[] vos = (CircularlyAccessibleValueObject[])resultVos;
		//CIntelVO�ӹ����ߴ������
		Object expla = null;
		for (int i = 0; i < vos.length; i++) {
			expla = vos[i].getAttributeValue("corpname");
			if(expla != null && (expla.toString().trim().indexOf(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("report","UPPreport-000006")/*@res "С��"*/) > -1
					|| expla.toString().trim().indexOf(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("report","UPPreport-000007")/*@res "�ܼ�"*/) > -1))
			{
				vos[i].setAttributeValue("pk_notetype", null);
				vos[i].setAttributeValue("currtypename", null);
			}
			expla = vos[i].getAttributeValue("currtypename");
			if(expla != null && (expla.toString().trim().indexOf(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("report","UPPreport-000006")/*@res "С��"*/) > -1
					|| expla.toString().trim().indexOf(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("report","UPPreport-000007")/*@res "�ܼ�"*/) > -1))
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
						noteClass = nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("report","UPPreport-000078")/*@res "֧Ʊ"*/;
						break;
					case INotetypeConst.NOTECLASS_COMMERCIALDRAFT:
						noteClass = nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("report","UPPreport-000079")/*@res "��ҵ��Ʊ"*/;
						break;
					case INotetypeConst.NOTECLASS_BANKDRAFT:
						noteClass = nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("report","UPPreport-000080")/*@res "���л�Ʊ"*/;
						break;
					case INotetypeConst.NOTECLASS_CASHIERCHEQUE:
						noteClass = nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("report","UPPreport-000081")/*@res "��Ʊ"*/;
						break;
					case INotetypeConst.NOTECLASS_OTHER:
						noteClass = nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("report","UPPreport-000082")/*@res "����"*/;
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
		if("corp".equals(key))  //��˾
		{
			getterInfo.setFieldUnit(pk_corpField);
			getterInfo.setGroupByField(true);
		}
		else if("currtype".equals(key))  //����
		{
			getterInfo.setFieldUnit(pk_currtypeField);
			getterInfo.setGroupByField(true);
		}
		else if("pk_notetype".equals(key))  //Ʊ������pk
		{
			getterInfo.setFieldUnit(pk_notetypeField);
			getterInfo.setGroupByField(true);
		}
		else if("inamount".equals(key))  //����ԭ��
		{
			getterInfo.setFieldUnit(receiveField);
			getterInfo.setSumField(true);
		}
		else if("inlocalamount".equals(key))  //���뱾��
		{
			getterInfo.setFieldUnit(receiveLField);
			getterInfo.setSumField(true);
		}
		else if("outamount".equals(key))  //֧��ԭ��
		{
			getterInfo.setFieldUnit(payField);
			getterInfo.setSumField(true);
		}
		else if("outlocalamount".equals(key))  //֧������
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
		ButtonObject subQueryBtn = new ButtonObject(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("report","UPPreport-000095")/*@res "�����ռ���"*/, nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("report","UPPreport-000095")/*@res "�����ռ���"*/, 2, "�����ռ���");	/*-=notranslate=-*/
		subQueryBtn.setTag("�����ռ���");
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
		return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("report","UPPreport-000101")/*@res "Ʊ���ձ���"*/;
	}


	@Override
	public void onButtonClicked(ButtonObject bo) {
		if(bo == getQueryBtn())
		{
			onQuery();
		}
		else if(bo.getTag().equals("ˢ��"))
			refresh();
		else if(bo.getTag().equals("ֱ�Ӵ�ӡ"))
			directPrint();
		else if(bo.getTag().equals("ģ���ӡ"))
			templetPrint();
		else if(bo.getTag().equals("�����ռ���"))
			queryDailyBook();
		else if(bo.getTag().equals("����"))
			m_parent.closeMe();
	}


	private void queryDailyBook()
	{
		int i = getReportTemplet().getBillTable().getSelectedRow();
		CircularlyAccessibleValueObject[] vos = getReportData();
		CommonReportQueryPanel panel = getCommonReportQueryPanel();
		Object[] condValues = new Object[9];
		condValues[0] = vos[i].getAttributeValue("corp"); //��˾pk
		condValues[1] = vos[i].getAttributeValue("pk_notetype"); //Ʊ������pk
		condValues[2] = vos[i].getAttributeValue("currtype"); //����pk
		condValues[3] = panel.getCbBoxSignStatus().getSelectdItemValue(); //����״̬
		condValues[4] = panel.getCbBoxDateType().getSelectedItem(); //��������
		condValues[5] = panel.getRefPanelBeginDate().getText(); //��ʼ����
		condValues[6] = panel.getRefPanelEndDate().getText(); //��ֹ����
		condValues[7] = panel.getRefPanelSystem().getRefPKs(); //ҵ��ϵͳpk
		condValues[8] = panel.getRefPanelBillType().getRefPKs(); //�������ͣ��������ͣ�
		IUiPanel uiPanel = (IUiPanel) m_parent.showNext("nc.ui.cmp.report.dailybook.NoteDailyBookToftPanel");
		uiPanel.invoke(condValues, "queryFromDailyReport");
	}


	public void bodyRowChange(BillEditEvent e) {
		int selected = e.getRow();
		String corp = (String)getReportData()[selected].getAttributeValue("corpname");
		String currtype = (String)getReportData()[selected].getAttributeValue("currtypename");
		ButtonObject subQryBtn = null;
		for (int i = 0; i < getButtons().length; i++) {
			if(getButtons()[i].getTag().equals("�����ռ���"))
			{
				subQryBtn = getButtons()[i];
				break;
			}
		}
		if((corp != null && (corp.indexOf(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("report","UPPreport-000006")/*@res "С��"*/) > -1 || corp.indexOf(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("report","UPPreport-000007")/*@res "�ܼ�"*/) > -1))
				|| (currtype != null && (currtype.indexOf(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("report","UPPreport-000006")/*@res "С��"*/) > -1 || currtype.indexOf(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("report","UPPreport-000007")/*@res "�ܼ�"*/) > -1)))
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
			//Ϊ����������谴ť
			if(subQuerybuttons == null)
			{
				ButtonObject refreshBtn = getRefreshBtn();
				ButtonObject printBtn = getPrintBtn();
				ButtonObject subQueryBtn = new ButtonObject(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("report","UPPreport-000095")/*@res "�����ռ���"*/, nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("report","UPPreport-000095")/*@res "�����ռ���"*/, 2, "�����ռ���");	/*-=notranslate=-*/
				subQueryBtn.setTag("�����ռ���");
				subQueryBtn.setPowerContrl(false);
				ButtonObject returnBtn = new ButtonObject(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("report","UC001-0000038")/*@res "����"*/, nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("report","UC001-0000038")/*@res "����"*/, 2, "����");	/*-=notranslate=-*/
				returnBtn.setTag("����");
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
			panel.getRefPanelUnit().setPK(condValues[0]);  //��˾pk
		else if(condValues[0] instanceof String[])
			panel.getRefPanelUnit().setPKs((String[])condValues[0]);  //��˾pk
		if("fromFundDailyReport".equals(queryName))  //�����ʽ��ձ���
		{
			panel.getRefPanelCurrency().setPK(condValues[1]); //����pk
			panel.getCbBoxSignStatus().setSelectedIndex(((Integer)condValues[2]).intValue());//����״̬
			panel.getCbBoxDateType().setSelectedItem(condValues[3]);  //��������
			panel.getRefPanelBeginDate().setText((String)condValues[4]); //��ʼ����
			panel.getRefPanelEndDate().setText((String)condValues[5]); //��ֹ����
			panel.getRefPanelSystem().setPKs((String[])condValues[6]); //ҵ��ϵͳpk
			panel.getRefPanelBillType().setPKs((String[])condValues[7]); //�������ͣ��������ͣ�
		}
		else  //�����ʽ������ܱ�
		{
			panel.getRefPanelCurrency().setPK(condValues[1]); //����pk
			panel.getCbBoxSignStatus().setSelectedIndex(2);  //�ѽ���
			panel.getCbBoxDateType().setSelectedIndex(2);  //��������
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
	 * �����첽��ѯ���ͣ����ز�ѯ��ʽ
	 */
	public int getQueryType() {
		return IQueryType.ASYN_ONLY;
	}
}
