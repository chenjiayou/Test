/**
 *
 */
package nc.ui.fbm.pub;

import java.awt.BorderLayout;
import java.awt.Container;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import nc.bs.framework.common.NCLocator;
import nc.bs.logging.Logger;
import nc.itf.cdm.util.CommonUtil;
import nc.itf.fbm.accrule.IAccRule;
import nc.itf.fbm.arap.IPushArapBill;
import nc.itf.fbm.dapmsg.IDapMsgService;
import nc.itf.fbm.outer.IOuterService;
import nc.itf.fbm.pub.IFBMBillSubmit;
import nc.itf.fbm.relief.IReliefService;
import nc.itf.uap.sf.ICreateCorpQueryService;
import nc.jdbc.framework.processor.ArrayListProcessor;
import nc.jdbc.framework.processor.ColumnListProcessor;
import nc.jdbc.framework.processor.ColumnProcessor;
import nc.ui.cdm.pub.print.CardPanelPRTS;
import nc.ui.cdm.pub.print.SingleListHeadPRTS;
import nc.ui.dap.service.DapBillQueryVoucher;
import nc.ui.dap.voucher.BatchGenerator;
import nc.ui.fac.account.pub.RefTakenQueryConditionClient;
import nc.ui.fac.account.pub.RefTakenQueryFilterEditorFactory;
import nc.ui.fbm.gather.RegisterVOConverter;
import nc.ui.fbm.pub.outer.LinkSFQueryData;
import nc.ui.pub.ButtonObject;
import nc.ui.pub.ClientEnvironment;
import nc.ui.pub.IFuncWindow;
import nc.ui.pub.beans.MessageDialog;
import nc.ui.pub.beans.UIDialog;
import nc.ui.pub.bill.BillCardPanel;
import nc.ui.pub.bill.BillItem;
import nc.ui.pub.bill.BillListPanel;
import nc.ui.pub.filesystem.FileManageUI;
import nc.ui.pub.linkoperate.ILinkQueryData;
import nc.ui.pub.pf.PfUtilClient;
import nc.ui.pub.querymodel.QELinkQueryData;
import nc.ui.querytemplate.QueryConditionDLG;
import nc.ui.tm.framework.util.ClientInfo;
import nc.ui.tm.framework.util.hint.HintBusiActionResult;
import nc.ui.tm.framework.util.hint.HintBusiActionWorker;
import nc.ui.tm.framework.util.hint.IHintBusiAction;
import nc.ui.tm.framework.util.hint.manage.ManageHintBusiActionContext;
import nc.ui.trade.base.AbstractBillUI;
import nc.ui.trade.base.IBillOperate;
import nc.ui.trade.business.HYPubBO_Client;
import nc.ui.trade.businessaction.IPFACTION;
import nc.ui.trade.button.IBillButton;
import nc.ui.trade.controller.IControllerBase;
import nc.ui.trade.manage.BillManageUI;
import nc.ui.trade.manage.ManageEventHandler;
import nc.ui.uap.sf.SFClientUtil;
import nc.vo.arap.outer.IArapGeneralObj;
import nc.vo.bd.basedata.CurrtypeVO;
import nc.vo.dap.inteface.IAccountPlat;
import nc.vo.dap.out.BillQueryVoucherVO;
import nc.vo.dap.out.DapMsgVO;
import nc.vo.dap.pub.OperationParameter;
import nc.vo.fbm.acceptbill.AcceptVO;
import nc.vo.fbm.accrule.AccRuleVO;
import nc.vo.fbm.consignbank.CollectionVO;
import nc.vo.fbm.discount.DiscountVO;
import nc.vo.fbm.outer.OuterVO;
import nc.vo.fbm.proxy.OuterProxy;
import nc.vo.fbm.pub.FBMProxy;
import nc.vo.fbm.pub.FunCodeUtil;
import nc.vo.fbm.pub.TransferBill4NoteVO;
import nc.vo.fbm.pub.constant.FBMParamConstant;
import nc.vo.fbm.pub.constant.FbmBusConstant;
import nc.vo.fbm.pub.constant.IFBMStatus;
import nc.vo.fbm.reckon.ReckonVO;
import nc.vo.fbm.register.RegisterVO;
import nc.vo.fbm.relief.ReliefBVO;
import nc.vo.fbm.relief.ReliefVO;
import nc.vo.fbm.returnbill.ReturnBVO;
import nc.vo.fbm.storage.StorageBVO;
import nc.vo.fbm.storage.StorageVO;
import nc.vo.fp.fp611.PlanVO;
import nc.vo.fp.pub.IOBudgetQueryVO;
import nc.vo.fp.pub.proxy.FpProxy;
import nc.vo.fts.account.AccBalanceVO;
import nc.vo.fts.account.AccDetailQueryVO;
import nc.vo.fts.pub.proxy.FTSProxy;
import nc.vo.jcom.xml.XMLUtil;
import nc.vo.pub.AggregatedValueObject;
import nc.vo.pub.BusinessException;
import nc.vo.pub.BusinessRuntimeException;
import nc.vo.pub.CircularlyAccessibleValueObject;
import nc.vo.pub.SuperVO;
import nc.vo.pub.ValidationException;
import nc.vo.pub.lang.UFBoolean;
import nc.vo.pub.lang.UFDouble;
import nc.vo.pub.querymodel.ParamConst;
import nc.vo.pub.querymodel.QEPenetrateObject;
import nc.vo.pub.querymodel.QueryParamVO;
import nc.vo.querytemplate.TemplateInfo;
import nc.vo.tam.account.IAccConst;
import nc.vo.tam.account.accid.AccidVO;
import nc.vo.trade.button.ButtonVO;
import nc.vo.trade.pub.HYBillVO;
import nc.vo.trade.pub.IBillStatus;
import nc.vo.trade.pub.IExAggVO;
import nc.vo.uapbd.bankdoc.BankdocVO;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * <p>
 * 资金票据产品共用动作基类
 * <p>
 * 创建人：lpf <b>日期：2007-8-14
 * 
 */
public class FBManageEventHandler extends ManageEventHandler {

	protected static String NOTSHOW = "$NOTSHOW$";

	private BatchGenerator generator = null;
	/**
	 * @param billUI
	 * @param control
	 */
	public FBManageEventHandler(BillManageUI billUI, IControllerBase control) {
		super(billUI, control);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void buttonActionBefore(AbstractBillUI billUI, int intBtn)
			throws Exception {
		// 界面合法性数据校验
		String errorMsg = validateCheck(intBtn);
		if (!CommonUtil.isNull(errorMsg))
			throw new ValidationException(errorMsg);
		switch (intBtn) {
		case IBillButton.Cancel:
			if (getBillUI().getBillOperate() == IBillOperate.OP_ADD) {
				BillItem bi = getBillCardPanelWrapper().getBillCardPanel().getHeadItem(getBillNoKey());
				String billno = null;
				if (bi != null) {
					billno = (String) bi.getValueObject();
				}

				if (billno != null) {
					OuterProxy.getBillCodeRuleService().returnBillCodeOnDelete(ClientInfo.getCorpPK(), getUIController().getBillType(), billno, null);
				}
			}
			break;
		}

	}

	@Override
	protected void buttonActionAfter(AbstractBillUI billUI, int intBtn)
			throws Exception {
		switch (intBtn) {
		case IBillButton.Copy:
			getBillCardPanelWrapper().getBillCardPanel().getHeadItem(getBillField().getField_BillStatus()).setValue(IBillStatus.FREE);
			getBillCardPanelWrapper().getBillCardPanel().getHeadTailItem(getBillField().getField_Operator()).setValue(_getOperator());
			getBillCardPanelWrapper().getBillCardPanel().getHeadTailItem("doperatedate").setValue(_getDate());
			getBillCardPanelWrapper().getBillCardPanel().getHeadTailItem(getBillField().getField_CheckMan()).setValue(null);
			getBillCardPanelWrapper().getBillCardPanel().getHeadTailItem(getBillField().getField_CheckDate()).setValue(null);
			getBillCardPanelWrapper().getBillCardPanel().getHeadTailItem(getBillField().getField_CheckNote()).setValue(null);
			BillItem voucheritem = getBillCardPanelWrapper().getBillCardPanel().getHeadTailItem("unitvoucher");
			if (voucheritem != null) {
				voucheritem.setValue(UFBoolean.FALSE);
			}
			BillItem voucheriditem = getBillCardPanelWrapper().getBillCardPanel().getHeadTailItem("vvoucherid");
			if (voucheriditem != null) {
				voucheriditem.setValue(null);
			}
			BillItem voucherdateitem = getBillCardPanelWrapper().getBillCardPanel().getHeadTailItem("dvoucherdate");
			if (voucherdateitem != null) {
				voucherdateitem.setValue(null);
			}
			break;
		// 单据号处理 modified by wes, billno returned after action moved from before
		// action
		// case IBillButton.Delete:
		// AggregatedValueObject avo = getBufferData().getCurrentVO();
		// if (avo != null) {
		// String billno = null;
		// billno = (String) avo.getParentVO().getAttributeValue(
		// getBillNoKey());
		//
		// if (billno != null) {
		// OuterProxy.getBillCodeRuleService().returnBillCodeOnDelete(
		// ClientInfo.getCorpPK(),
		// getUIController().getBillType(), billno, null);
		// }
		// }
		// break;
		}
	}

	protected String validateCheck(int intBtn) {
		String errorMessage = null;
		if (getUI().getValidatorMap() == null
				|| getUI().getValidatorMap().get(new Integer(intBtn).toString()) == null) {
			return null;
		}
		ArrayList<IUIChecker> checkers = getUI().getValidatorMap().get(new Integer(
				intBtn).toString());
		for (int i = 0; i < checkers.size(); i++) {
			errorMessage = checkers.get(i).check();
			if (errorMessage != null && errorMessage.length() > 0)
				return nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm", "UPPFBMComm-000138")/*
																								 * @res
																								 * "界面非法数据校验："
																								 */
						+ "\n"
						+ errorMessage;
		}
		return null;
	}

	protected FBMManageUI getUI() {
		return (FBMManageUI) getBillUI();
	}

	/**
	 * <p>
	 * 获得用户制定的billno字段key
	 * <p>
	 * 作者：lisg <br>
	 * 日期：2007-3-14
	 * 
	 * @return
	 */
	protected String getBillNoKey() {
		return getBillUI().getBillField().getField_BillNo();
	}

	/**
	 * 检查该节点是否已经被打开了,如果已经打开了就将其显示并返回true,否则返回false;
	 * 
	 * @param funcCode
	 * @return
	 */
	protected boolean hasOpen(String funcCode) {
		List openModules = ClientEnvironment.getInstance().getOpenModules();
		Iterator it = openModules.iterator();
		boolean isOpen = false;
		while (it.hasNext()) {
			// MainFrame openModule = (MainFrame) it.next();
			IFuncWindow window = (IFuncWindow) it.next();
			if (window.getFuncPanel().getModuleCode().equals(funcCode)) {
				window.showWindow();
				isOpen = true;
			}
		}
		return isOpen;
	}

	/**
	 * <p>
	 * 开票/收票跳转到其他界面的公用方法，因为需要根据票据状态后台VO过滤，所以需要携带状态值
	 * <p>
	 * 作者：lpf 日期：2007-8-29
	 * 
	 * @param billtype
	 * @throws BusinessException
	 */
	public void JumpToOtherUI(String billtype) throws BusinessException {
		RegisterVO[] RegisterVOs = null;
		String registerstatus = "";
		if (getUI().isListPanelSelected()) {
			int selRows[] = getUI().getBillListPanel().getParentListPanel().getTable().getSelectedRows();
			RegisterVOs = new RegisterVO[selRows.length];
			for (int i = 0; i < selRows.length; i++) {
				int curRow = selRows[i];
				AggregatedValueObject selVo = getBufferData().getVOByRowNo(curRow);
				RegisterVOs[i] = (RegisterVO) selVo.getParentVO();
				registerstatus = (String) getUI().getRefTakenProccessor().getValueByTakenInList(RegisterVO.TABLENAME, RegisterVO.REGISTERSTATUS, curRow);
				RegisterVOs[i].setRegisterstatus(registerstatus);
			}
		} else {
			RegisterVOs = new RegisterVO[1];
			registerstatus = (String) getUI().getRefTakenProccessor().getValueByTakenInCard(RegisterVO.REGISTERSTATUS);
			RegisterVOs[0] = (RegisterVO) getBufferData().getCurrentVO().getParentVO();
			RegisterVOs[0].setRegisterstatus(registerstatus);
		}

		HYBillVO convertVo = new RegisterVOConverter().convertTool(RegisterVOs, billtype);

		if (hasOpen(FunCodeUtil.getFuncodeByBillType(billtype))) {
			MessageDialog.showHintDlg(getUI(), nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm", "UPPFBMComm-000139")/*
																														 * @res
																														 * "快捷错误"
																														 */, nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm", "UPPFBMComm-000140")/*
																																																		 * @res
																																																		 * "调用的界面已经打开，请直接在该界面上操作，或者保存该界面上的数据后关闭界面重试！"
																																																		 */);
			return;
		}

		DefaultLinkAddParam link = new DefaultLinkAddParam(convertVo);
		link.setBillPK(RegisterVOs[0].getPrimaryKey());
		link.setBillType(getUI().getUIControl().getBillType());
		link.setUi(getUI());

		SFClientUtil.openLinkedADDDialog(FunCodeUtil.getFuncodeByBillType(billtype), getBillUI(), link, null);
	}

	/**
	 * 从票据调剂单跳转到内部领用单
	 * 
	 * @param billType
	 */
	protected void JumpToInnerBack(String billType) throws BusinessException {
		RegisterVO[] list = null;
		IReliefService reliefServ = OuterProxy.getReliefService();
		AggregatedValueObject selVo = null;
		if (getUI().isListPanelSelected()) {// 列表界面
			int selRows[] = getUI().getBillListPanel().getParentListPanel().getTable().getSelectedRows();
			if (null == selRows || selRows.length == 0 || selRows.length > 1) {
				throw new BusinessException(
						nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm", "UPPFBMComm-000141")/*
																									 * @res
																									 * "请选择一张票据调剂单。"
																									 */);
			}
			int curRow = selRows[0];

			selVo = getBufferData().getVOByRowNo(curRow);
			list = reliefServ.getRegisterVOByRelief(selVo.getParentVO().getPrimaryKey());
		} else {// 卡片界面
			selVo = getBufferData().getCurrentVO();
			list = reliefServ.getRegisterVOByRelief(selVo.getParentVO().getPrimaryKey());
		}
		if (selVo == null || selVo.getParentVO() == null) {
			throw new BusinessException(
					nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm", "UPPFBMComm-000142")/*
																								 * @res
																								 * "请选择一张票据调剂单"
																								 */);
		}
		if (null == list || list.length == 0) {
			throw new BusinessException(
					nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm", "UPPFBMComm-000143")/*
																								 * @res
																								 * "此调剂单没有未曾出库的票据，请确认。"
																								 */);
		}

		ReliefVO vo = (ReliefVO) selVo.getParentVO();

		String isautoback = OuterProxy.getSysInitQry().getParaString("@@@@", FBMParamConstant.FBM004);
		if (isautoback != null
				&& UFBoolean.TRUE.equals(UFBoolean.valueOf(isautoback))) {
			String message = reliefServ.autoInnerBack(vo, getUI()._getDate(), getUI()._getOperator(), getUI()._getDate());
			MessageDialog.showHintDlg(getUI(), nc.ui.ml.NCLangRes.getInstance().getStrByID("36201510", "UPP36201510-000005")/*
																															 * @res
																															 * "提示"
																															 */, message);

		} else {
			openNewTab(list, billType, vo);
		}
	}

	/**
	 * 判断并打开新节点
	 * 
	 * @param RegisterVOs
	 * @param billtype
	 * @throws BusinessException
	 */
	private void openNewTab(RegisterVO[] RegisterVOs, String billtype,
			ReliefVO vo) throws BusinessException {
		HYBillVO convertVo = new RegisterVOConverter().convertTool(RegisterVOs, billtype);

		((StorageVO) convertVo.getParentVO()).setOutputunit(vo.getReliefunit());// 调剂单位为出库单位
		((StorageVO) convertVo.getParentVO()).setKeepaccount(vo.getInneracc());// 调剂单位的内部帐户
		((StorageVO) convertVo.getParentVO()).setPk_currtype(vo.getPk_currtype());

		if (hasOpen(FunCodeUtil.getFuncodeByBillType(billtype))) {
			MessageDialog.showHintDlg(getUI(), nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm", "UPPFBMComm-000139")/*
																														 * @res
																														 * "快捷错误"
																														 */, nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm", "UPPFBMComm-000140")/*
																																																		 * @res
																																																		 * "调用的界面已经打开，请直接在该界面上操作，或者保存该界面上的数据后关闭界面重试！"
																																																		 */);
			return;
		}

		DefaultLinkAddParam link = new DefaultLinkAddParam(convertVo);
		link.setBillPK(RegisterVOs[0].getPrimaryKey());
		link.setBillType(getUI().getUIControl().getBillType());

		SFClientUtil.openLinkedADDDialog(FunCodeUtil.getFuncodeByBillType(billtype), getBillUI(), link, null);
	}

	protected UIDialog createQueryUI() {
		// return new HYQueryDLG(getBillUI(), null, _getCorp().getPrimaryKey(),
		// getBillUI()._getModuleCode(), _getOperator(), getBillUI()
		// .getBusinessType(), getBillUI().getNodeKey());
		RefTakenQueryConditionClient dialog = new RefTakenQueryConditionClient(
				getBillUI(), createTemplateInof(getBillUI()));
		// dialog.registerFieldValueEelementEditorFactory(new
		// ReleaseFieldValueElementEditorFactory(dialog.getQueryContext()));
		dialog.registerFilterEditorFactory(new RefTakenQueryFilterEditorFactory(
				(FBMManageUI) getBillUI(),
				((FBMManageUI) getBillUI()).getRefTakenProccessor(), dialog));
		return dialog;
	}

	/**
	 * 联查收付款单据界面
	 * 
	 * @throws BusinessException
	 */
	protected void jumpToArapQuery(String pk_busibill) throws BusinessException {
		IOuterService service = (IOuterService) NCLocator.getInstance().lookup(IOuterService.class.getName());

		OuterVO[] vos = service.queryOuterVOSByBusiBill(pk_busibill);
		if (CommonUtil.isNull(vos)) {
			throw new BusinessException(
					nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add", "UPP3620ADD-000094")/*
																										 * @res
																										 * "没有与此相关连的单据"
																										 */);
		}
		String pk_register = null;
		if (vos.length > 1) {
			String billtype = getUIController().getBillType();
			// 其余都在表头
			if (billtype.equals(FbmBusConstant.BILLTYPE_RETURN)) {
				int selRow = ((FBMManageUI) getBillUI()).getBillCardPanel().getBillTable(ReturnBVO.tablecode).getSelectedRow();

				if (selRow < 0) {
					throw new BusinessException(
							nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add", "UPP3620ADD-000235")/*
																												 * @res
																												 * "请选择表体一张票据进行联查"
																												 */);
				}

				SuperVO[] bodyvos = (SuperVO[]) getBufferData().getCurrentVO().getChildrenVO();
				pk_register = (String) bodyvos[selRow].getAttributeValue("pk_source");
			}
		}

		String funcode = getFunCode(vos[0]);
		if (null == funcode) {
			throw new BusinessException(
					nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add", "UPP3620ADD-000095")/*
																										 * @res
																										 * "根据大类："
																										 */
							+ vos[0].getOuterdjdl()
							+ nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add", "UPP3620ADD-000096")/*
																													 * @res
																													 * "得到的节点号为空，请检查"
																													 */);
		}

		String[] pk_bill_h = getPks(vos, pk_register);

		ILinkQueryData data = new LinkSFQueryData(pk_bill_h);
		// 跳转到报账中心的单据管理界面
		SFClientUtil.openLinkedQueryDialog(funcode, getBillUI(), data);

	}

	private String[] getPks(OuterVO[] vos, String pk_register) throws BusinessException{

		
		
		List<String> list = new ArrayList<String>();

		if (null == pk_register) {// 表示非退票，不选表体
			return conver2ArapPKs(new String[]{vos[0].getPk_outerbill_h()}) ;
		}

		for (int i = 0; i < vos.length; i++) {
			if (pk_register.equals(vos[i].getPk_register())) {
				list.add(vos[i].getPk_outerbill_h());
			}
		}
		return conver2ArapPKs(list.toArray(new String[list.size()]));
	}
	
	private String[] conver2ArapPKs(String[] pks) throws BusinessException{
		List<String> list = new ArrayList<String>();
		for(String pk:pks){
			String sql_arap = "select vouchid from arap_djzb where vouchid =(select pk_busibill from cmp_settlement where pk_settlement = '"+pk+"')";
			Object obj = FBMProxy.getUAPQuery().executeQuery(sql_arap, new ColumnProcessor());
			if(obj == null){
					String sql_cmp = "select vouchid from  cmp_busibill where vouchid =(select pk_busibill from cmp_settlement where pk_settlement = '"+pk+"')";
					obj = FBMProxy.getUAPQuery().executeQuery(sql_cmp, new ColumnProcessor());
					
			}
			if(obj != null){
				list.add(obj.toString());
			}
		}
		return list.toArray(new String[0]);
	}

	private String getFunCode(OuterVO vo) throws BusinessException {

		String djdl = vo.getOuterdjdl();

		// if ("F2".equals(djdl) || "F3".equals(djdl) || "F4".equals(djdl)
		// || "F5".equals(djdl)) {// 收款结算单/付款结算单/收款单/付款单->现金平台
		// return "20040401";//结算处理节点号
		// }
		if("fj".equals(djdl)){
			return "20040321";
		}
		
		if("sj".equals(djdl)){
			return  "20040320";
		}
		Object obj = FBMProxy.getUAPQuery().executeQuery("select nodecode from bd_billtype where pk_billtypecode = '"+djdl+"' ", new ColumnProcessor());
		
		return obj.toString();
	}

	/**
	 * 联查票据备查簿
	 * 
	 * @throws BusinessException
	 */
	protected void LinkFBMBillBook(String pk_baseinfo) throws BusinessException {
		QELinkQueryData qeLinkQueryData = new QELinkQueryData();

		Hashtable<String, Object> hashPeneRow = new Hashtable<String, Object>();

		// 选中行的VO列名与对应列值
		// String[] attributes = supervo.getAttributeNames();
		// for (int i = 0; i < attributes.length; i++) {
		// if (supervo.getAttributeValue(attributes[i]) != null) {
		// hashPeneRow.put(attributes[i], supervo
		// .getAttributeValue(attributes[i]));
		// }
		// }
		QEPenetrateObject qePenetrateObject = new QEPenetrateObject(hashPeneRow);
		// 对应自定义查询(主查询)的标识
		qePenetrateObject.setId("newbcbhead");

		// 自定义查询的参数
		Hashtable<String, Object> hashParam = new Hashtable<String, Object>();
		// billtype billcode receiveunit payunit opendatebegin opendateend
		// arrivedatebegin arrivedateend
		// pk_curr billmoneybegin billmoneyend status
		// if(disvo.getPjlx() != null) {
		// hashParam.put("billtype", (disvo.getPjlx() == "bank")?
		// "银行承兑汇票":"商业承兑汇票");
		// }
		if (pk_baseinfo != null && pk_baseinfo.trim().length() > 0) {
			QueryParamVO qeParamVO = new QueryParamVO();
			qeParamVO.setConsultCode("nc.ui.fbm.pub.refmodel.QEBaseinfoRefModel");
			qeParamVO.setDsName("design");
			qeParamVO.setRefPk(pk_baseinfo);
			qeParamVO.setValue(pk_baseinfo);
			qeParamVO.setDataType(ParamConst.REF_ID);
			qeParamVO.setOperaCode("=");
			qeParamVO.setParamCode("pk_baseinfo");
			qeParamVO.setParamName(nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm", "UPPFBMComm-000036")/*
																											 * @res
																											 */);
			hashParam.put("pk_baseinfo", qeParamVO);
			
			
		} else {
			throw new BusinessException(
					nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm", "UPPFBMComm-000144")/*
																								 * @res
																								 * "票据号为空！"
																								 */);
		}
		// if(disvo.getCprq() != null) {
		// hashParam.put("opendatebegin", disvo.getCprq());
		// hashParam.put("opendateend", disvo.getCprq());
		// }
		// if(disvo.getDqrq() != null) {
		// hashParam.put("arrivedatebegin", disvo.getDqrq());
		// hashParam.put("arrivedateend", disvo.getDqrq());
		// }
		// if(disvo.getYbbz() != null) {
		// hashParam.put("pk_curr", disvo.getYbbz());
		// }
		// if(disvo.getHpje() != null) {
		// hashParam.put("billmoneybegin", disvo.getHpje());
		// hashParam.put("billmoneyend", disvo.getHpje());
		// }
		qePenetrateObject.setHashParam(hashParam);
		qeLinkQueryData.setPeneObj(qePenetrateObject);
		if (hasOpen(FbmBusConstant.FUNCODE_FBMBILL_BOOK)) {
			MessageDialog.showHintDlg(getUI(), nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm", "UPPFBMComm-000139")/*
																														 * @res
																														 * "快捷错误"
																														 */, nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm", "UPPFBMComm-000140")/*
																																																		 * @res
																																																		 * "调用的界面已经打开，请直接在该界面上操作，或者保存该界面上的数据后关闭界面重试！"
																																																		 */);
			return;
		}
		SFClientUtil.openLinkedQueryDialog(FbmBusConstant.FUNCODE_FBMBILL_BOOK, getBillUI(), qeLinkQueryData);
	}

	/**
	 * 推式生成收付报单据
	 * 
	 * @throws BusinessException
	 */
	protected void createArapBill(AggregatedValueObject billVo)
			throws BusinessException {
		try {

			// 检查公司是否启用了收付报产品
			// ICreateCorpQueryService ProductService =
			// (ICreateCorpQueryService) NCLocator
			// .getInstance().lookup(
			// ICreateCorpQueryService.class.getName());
			// Hashtable productEnabled = ProductService.queryProductEnabled(
			// _getCorp().getPrimaryKey(),
			// new String[] { ProductCode.PROD_EP });
			// if (!((UFBoolean) productEnabled.get(ProductInfo.pro_EP))
			// .booleanValue()) {
			// throw new BusinessException("报账中心未启用，无法生成单据");
			// }

			// 检查汇率
			SuperVO vo = (SuperVO) billVo.getParentVO();
			// UFDouble frate = (UFDouble) vo.getAttributeValue("frate");
			// UFDouble brate = (UFDouble) vo.getAttributeValue("brate");
			// String pk_corp = (String) vo.getAttributeValue("pk_corp");
			//
			// String curField = "pk_curr";
			//
			String pk_billtypecode = (String) vo.getAttributeValue("pk_billtypecode");
			// if
			// (pk_billtypecode.equals(FbmBusConstant.BILLTYPE_COLLECTION_UNIT)
			// || pk_billtypecode
			// .equals(FbmBusConstant.BILLTYPE_DISCOUNT_TRANSACT)) {
			// curField = "ybbz";
			// }
			// String pk_curr = (String) vo.getAttributeValue(curField);
			//
			// // 是否主辅币核算
			// boolean isLocalFractional = CurrParamQuery.getInstance()
			// .isBlnLocalFrac(pk_corp).booleanValue();
			// // 原币是否为主币
			// boolean isLocalCurrType = CurrParamQuery.getInstance()
			// .isLocalCurrType(pk_corp, pk_curr);
			// if (isLocalFractional && !isLocalCurrType &&
			// MathUtil.isZero(frate)) {
			// throw new BusinessException("未设置辅币汇率");
			// }
			//
			// if (brate == null || brate.doubleValue() == 0) {
			// throw new BusinessException("未设置本币汇率");
			// }

			Class cls = null;
			try {
				cls = Class.forName("nc.ui.arap.outer.GeneralARAPDialog");
			} catch (Exception e) {
				throw new BusinessException(
						nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add", "UPP3620ADD-000097")/*
																											 * @res
																											 * "未安装收付报产品!"
																											 */);
			}
			Constructor con = cls.getConstructor(java.awt.Container.class);
			Object dlg = con.newInstance(getBillUI());
			Method m1 = cls.getMethod("showDlg", IArapGeneralObj.class);

			if (vo == null) {
				throw new BusinessException(
						nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm", "UPPFBMComm-000145")/*
																									 * @res
																									 * "请选择一条记录"
																									 */);
			}

			IPushArapBill service = (IPushArapBill) NCLocator.getInstance().lookup(IPushArapBill.class.getName());
			IArapGeneralObj data = service.buildArapObj(vo, _getDate());

			Object ret = m1.invoke(dlg, data);
			int intRet = new Integer(ret.toString()).intValue();

			if (UIDialog.ID_OK != intRet) {
				throw new BusinessException(NOTSHOW);
			}

			// 重新查询当前行数据

			if (pk_billtypecode.equals(FbmBusConstant.BILLTYPE_COLLECTION_UNIT)) {
				vo = HYPubBO_Client.queryByPrimaryKey(CollectionVO.class, vo.getPrimaryKey());
			} else if (pk_billtypecode.equals(FbmBusConstant.BILLTYPE_DISCOUNT_TRANSACT)) {
				vo = HYPubBO_Client.queryByPrimaryKey(DiscountVO.class, vo.getPrimaryKey());
			} else if (pk_billtypecode.equals(FbmBusConstant.BILLTYPE_INVOICE)) {
				vo = HYPubBO_Client.queryByPrimaryKey(RegisterVO.class, vo.getPrimaryKey());
			} else if (pk_billtypecode.equals(FbmBusConstant.BILLTYPE_BILLPAY)) {
				vo = HYPubBO_Client.queryByPrimaryKey(AcceptVO.class, vo.getPrimaryKey());
			}
			billVo.setParentVO(vo);

		} catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
	}

	protected void onBusinessException(BusinessException e) {
		if (e.getMessage() == null || !e.getMessage().equals(NOTSHOW)) {
			super.onBusinessException(e);
		}
	}

	/**
	 * 按钮m_boPrint点击时执行的动作,如有必要，请覆盖. bsrl
	 */
	protected void onBoPrint() throws Exception {
		if (((BillManageUI) getBillUI()).isListPanelSelected()) {
			nc.ui.pub.print.IDataSource dataSource = new SingleListHeadPRTS(
					getBillUI()._getModuleCode(),
					((BillManageUI) getBillUI()).getBillListPanel(),
					getUI().getRefTakenProccessor());
			nc.ui.pub.print.PrintEntry print = new nc.ui.pub.print.PrintEntry(
					getBillUI(), dataSource);
			print.setTemplateID(getBillUI()._getCorp().getPrimaryKey(), getBillUI()._getModuleCode(), getBillUI()._getOperator(), getBillUI().getBusinessType(), getBillUI().getNodeKey());
			if (print.selectTemplate() == 1)
				print.preview();
		} else {
			nc.ui.pub.print.IDataSource dataSource = new CardPanelPRTS(
					getBillUI()._getModuleCode(),
					getBillCardPanelWrapper().getBillCardPanel(),
					getUI().getRefTakenProccessor());
			nc.ui.pub.print.PrintEntry print = new nc.ui.pub.print.PrintEntry(
					getBillUI(), dataSource);
			print.setTemplateID(getBillUI()._getCorp().getPrimaryKey(), getBillUI()._getModuleCode(), getBillUI()._getOperator(), getBillUI().getBusinessType(), getBillUI().getNodeKey());
			if (print.selectTemplate() == 1)
				print.preview();
			// super.onBoPrint();
		}
	}

	/**
	 * 票据通打印
	 * 
	 * @author wangxf 2009-3-3
	 * @since NC5.5
	 */
	protected void onPrint4Note() {

		nc.ui.pub.print.IDataSource dataSource = null;
		TransferBill4NoteVO[] vos = null;
		BillItem[] items = null;
		String print4NoteKey = "";
		HashMap<String, BillItem> itemMap = new HashMap<String, BillItem>();
		HashMap<String, BillItem> tailitemMap = new HashMap<String, BillItem>();
		if (((BillManageUI) getBillUI()).isListPanelSelected()) {
			dataSource = new SingleListHeadPRTS(getBillUI()._getModuleCode(),
					getUI().getBillListPanel());
			BillListPanel panel = getUI().getBillListPanel();
			int[] selRows = getUI().getBillListPanel().getParentListPanel().getTable().getSelectedRows();
			vos = new TransferBill4NoteVO[selRows.length];
			for (int i = 0; i < vos.length; i++) {
				vos[i] = new TransferBill4NoteVO();
			}
			// 只有表头
			items = panel.getHeadBillModel().getBodyItems();
			for (int i = 0; i < CommonUtil.getArrayLength(items); i++) {
				BillItem item = items[i];
				String[] value = dataSource.getItemValuesByExpress("h_"
						+ item.getKey());
				print4NoteKey = getPrint4NoteKey(item.getKey());
				itemMap.put(print4NoteKey, item);
				if (CommonUtil.getArrayLength(value) > 0) {
					for (int j = 0; j < vos.length; j++) {
						vos[j].setAttributeValue(print4NoteKey, value[j]);
					}
				}
			}
		} else {
			dataSource = new CardPanelPRTS(getBillUI()._getModuleCode(),
					getBillCardPanelWrapper().getBillCardPanel());
			BillCardPanel panel = getBillCardPanelWrapper().getBillCardPanel();

			// vos = new TransferBill4NoteVO[panel.getRowCount()];

			vos = new TransferBill4NoteVO[1];
			for (int i = 0; i < vos.length; i++) {
				vos[i] = new TransferBill4NoteVO();
			}
			// 表头
			items = panel.getHeadItems();
			for (int i = 0; i < CommonUtil.getArrayLength(items); i++) {
				BillItem item = items[i];
				String[] value = dataSource.getItemValuesByExpress("h_"
						+ item.getKey());
				print4NoteKey = getPrint4NoteKey(item.getKey());
				itemMap.put(print4NoteKey, item);
				if (CommonUtil.getArrayLength(value) > 0) {
					for (int j = 0; j < vos.length; j++) {
						if (value[j] != null && value[j].contains(",")) {
							value[j] = value[j].replaceAll(",", "");
						}
						vos[j].setAttributeValue(print4NoteKey, value[j]);
					}
				}
			}
			// 表尾
			items = panel.getTailItems();
			for (int i = 0; i < CommonUtil.getArrayLength(items); i++) {
				BillItem item = items[i];
				String[] value = dataSource.getItemValuesByExpress("t_"
						+ item.getKey());
				print4NoteKey = getPrint4NoteKey(item.getKey());
				tailitemMap.put(print4NoteKey, item);
				if (CommonUtil.getArrayLength(value) > 0) {
					for (int j = 0; j < vos.length; j++) {
						vos[j].setAttributeValue(print4NoteKey, value[0]);
					}
				}
			}
			// items = panel.getBillModel().getBodyItems();
			// 表体
			items = panel.getBodyShowItems();
			if (items != null && items.length > 0) {
				for (int i = 0; i < CommonUtil.getArrayLength(items); i++) {
					BillItem item = items[i];
					String[] value = dataSource.getItemValuesByExpress(item.getKey());
					print4NoteKey = getPrint4NoteKey(item.getKey());
					itemMap.put(print4NoteKey, item);
					if (CommonUtil.getArrayLength(value) > 0) {
						for (int j = 0; j < vos.length; j++) {
							vos[j].setAttributeValue(print4NoteKey, value[j]);
						}
					}
				}
			}
		}

		try {

			RegisterVO invoiceVo = (RegisterVO) getUI().getBufferData().getCurrentVO().getParentVO();
			BankdocVO bankvo = null;
			if(invoiceVo.getPaybank()!=null){
				bankvo = (BankdocVO) HYPubBO_Client.queryByPrimaryKey(BankdocVO.class, invoiceVo.getPaybank());
			}

			Document doc = XMLUtil.newDocument();
			String[] keys = vos[0].getAttributeNames();
			Element billsElement = XMLUtil.createLeafElement(doc, "bills", "");
			doc.appendChild(billsElement);
			for (int i = 0; i < vos.length; i++) {
				TransferBill4NoteVO vo = vos[i];

				Element billElement = XMLUtil.createLeafElement(doc, "bill", "");
				billsElement.appendChild(billElement);
				billElement.setAttribute("id", vo.getVbillno());

				// 表头
				Element headElement = XMLUtil.createLeafElement(doc, "head", "");
				for (int j = 0; j < keys.length; j++) {
					String key = keys[j];
					if (itemMap.containsKey(key)) {
						Element keyElement = XMLUtil.createLeafElement(doc, key, (String) vo.getAttributeValue(key));
						keyElement.setAttribute("name", itemMap.get(key).getName());
						headElement.appendChild(keyElement);
					}

				}
				Element externalElement = XMLUtil.createLeafElement(doc, "bankno",bankvo==null?"": bankvo.getDef1());
				externalElement.setAttribute("name", "行号");
				headElement.appendChild(externalElement);

				externalElement = XMLUtil.createLeafElement(doc, "bankaddress", bankvo==null?"":bankvo.getDef2());
				externalElement.setAttribute("name", "地址");
				headElement.appendChild(externalElement);

				billElement.appendChild(headElement);
				// 表体
				Element bodyElement = XMLUtil.createLeafElement(doc, "body", "");
				billElement.appendChild(bodyElement);
				// 表尾
				Element tailElement = XMLUtil.createLeafElement(doc, "tail", "");
				for (int j = 0; j < keys.length; j++) {
					String key = keys[j];
					if (tailitemMap.containsKey(key)) {
						Element keyElement = XMLUtil.createLeafElement(doc, key, (String) vo.getAttributeValue(key));
						keyElement.setAttribute("name", tailitemMap.get(key).getName());
						tailElement.appendChild(keyElement);
					}

				}
				billElement.appendChild(tailElement);
			}
			// NodeList list = doc.getChildNodes();
			// list.getLength();
			// list.item(0).getChildNodes().item(1);
			// FileWriter writer = new FileWriter(new File("c:\\a.xml"));
			// XMLUtil.printDOMTree(writer, doc, 0, "gb2312");
			TransformerFactory tFactory = TransformerFactory.newInstance();
			Transformer transformer = tFactory.newTransformer();

			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			// transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION,
			// "no");
			transformer.setOutputProperty(OutputKeys.METHOD, "xml");
			transformer.setOutputProperty(OutputKeys.VERSION, "1.0");
			transformer.setOutputProperty(OutputKeys.ENCODING, "GB2312");
			// transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			// transformer.setOutputProperty("{http://xml.apache.org/sxlt}indent-amount",
			// "4");
			String user_home = System.getProperties().getProperty("user.home");
			FileOutputStream fileOutputStream = new FileOutputStream(user_home
					+ "\\ncarapbill\\ftsbill.xml");
			transformer.transform(new DOMSource(doc), new StreamResult(
					fileOutputStream));
			fileOutputStream.close();

			// OutputFormat format = new OutputFormat(doc);
			// format.setEncoding("GB2312");
			// XMLWriter writer = new XMLWriter(new FileWriter(new
			// File(fileName)),format);

			File pathFile = new File(user_home
					+ "\\ncarapbill\\UfnotePath.dat ");
			Logger.error("file:"
					+ pathFile.getPath()
					+ "/"
					+ pathFile.getName());
			FileInputStream input = new FileInputStream(pathFile);
			byte[] pathByte = new byte[1024];
			input.read(pathByte);
			String path = new String(pathByte);
			path = path.trim();
			Logger.error("path:" + path);
			input.close();
			String accountCode = ClientEnvironment.getInstance().getConfigAccount().getAccountName();
			if (accountCode == null)
				accountCode = ClientEnvironment.getInstance().getConfigAccount().getAccountCode();
			Logger.error("account:" + accountCode);
			StringBuffer tmp = new StringBuffer(path);
			tmp.append("\\UFNote6.exe _bync");
			tmp.append(" ");
			tmp.append(accountCode + " ftsbill");
			// tmp.append("1111" +" ftsbill");
			Logger.error("程序:" + tmp.toString());
			Runtime runtime = Runtime.getRuntime();
			runtime.exec(tmp.toString());

		} catch (Exception e) {
			throw new BusinessRuntimeException(e.getMessage(), e);
			// e.printStackTrace();
		}

	}

	private String getPrint4NoteKey(String key) {
		if (key.indexOf(".") > 0) {
			return key.substring(key.indexOf(".") + 1);
		}
		return key;
	}

	/**
	 * 票据编号参照的特殊性，可以直接在参照上新增票据编号，所以在参照上设置了filter后，在保存时调用
	 * setAddNewOperate(isAdding
	 * (),billVO)方法setCurrentRow，此时单据状态仍然是编辑态，从参照中取值加入了过滤，从而取不出数据来
	 * 现在暂时改动了isAdding方法，提前把界面设置为disable，从而不走参照过滤
	 * 
	 * 所有保存新增只要加了sqlFilter就有问题，保存完后不能将值携带回来，原因是加上了过滤条件。
	 */
	public boolean isAdding() {
		boolean ret = super.isAdding();

		if (ret) {
			getUI().getBillCardPanel().setEnabled(false);
			getUI().getBillListPanel().setEnabled(false);
		}

		return ret;
	}

	@Override
	protected void onBoSave() throws Exception {
		String error = validateCheck(IBillButton.Save);
		if (!CommonUtil.isNull(error))
			throw new ValidationException(error);
		super.onBoSave();
		// super.onBoRefresh();
	}

	@Override
	protected SuperVO[] queryHeadVOs(String strWhere) throws Exception,
			ClassNotFoundException {
		String tablename = null;
		try {
			tablename = getUIController().getBillVoName()[1];
			SuperVO vo = (SuperVO) InitClass(Class.forName(tablename));
			tablename = vo.getTableName();
		} catch (Exception e) {
		}
		strWhere += " order by "
				+ (tablename == null ? "" : (tablename + "."))
				+ "vbillno ";
		return super.queryHeadVOs(strWhere);
	}

	@Override
	protected void onBoElse(int intBtn) throws Exception {
		super.onBoElse(intBtn);
		switch (intBtn) {
		case IFBMButton.Discount_LinkGather:// 联查票据备查簿
		case IFBMButton.Gather_LQueryPJBook:
		case IFBMButton.ConsignBank_LinkGather:
			String pk_source = beforeLinkFBMBillBook();
			LinkFBMBillBook(pk_source);
			break;
		case IFBMButton.LINK_ACCOUNTBANLANCE:
		case IFBMButton.Relief_LQAccoutBalance:// 调剂联查账户余额
		case IFBMButton.Reckon_LinkInAccountBalance:// 清算联查账户余额
			showAccBalance(ReckonVO.INACC);
			break;
		case IFBMButton.Reckon_LinkOutAccountBalance:// 清算联查账户余额
			showAccBalance(ReckonVO.OUTACC);
			break;
		case IFBMButton.Relief_LQVoucher:// 调剂联查凭证
		case IFBMButton.Reckon_LinkVoucher:// 清算联查凭证, 如果是中心则联查结算凭证，单位联查总账凭证
		case IFBMButton.Endore_LinkVoucher:// 背书办理单联查凭证.
		case IFBMButton.Discount_LinkVoucher:// 贴现办理联查凭证。
		case IFBMButton.ConsignBank_LinkVoucher:// 银行托收联查凭证.
		case IFBMButton.FBM_QUERYVOUCHER:
			queryVoucher();
			break;
		case IFBMButton.Attach:
			onBoAttach();
			break;
		case IFBMButton.FBM_SUBMIT:// 提交
			onBoSubmit();
			break;
		case IFBMButton.FBM_CANCELSUBMIT:// 取消提交
			onBoCancelSubmit();
			break;
		case IFBMButton.FBM_SELECTALL:
			selectAll();
			break;
		case IFBMButton.FBM_CANCELSELECT:
			cancelSelect();
			break;
		case IFBMButton.FBM_INVERTSELECT:
			invertSelect();
			break;

		default:
			break;
		}
	}

	protected void onBoAttach() throws Exception {
		getUI().showHintMessage(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add", "UPP3620ADD-000098")/*
																													 * @res
																													 * "开始上传附件...."
																													 */);
		if (((BillManageUI) getBillUI()).isListPanelSelected()) {
			int[] selrows = getUI().getBillListPanel().getHeadTable().getSelectedRows();
			if (selrows == null || selrows.length == 0) {
				MessageDialog.showWarningDlg(getUI(), nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add", "UPP3620ADD-000099")/*
																																			 * @res
																																			 * "提示"
																																			 */, nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add", "UPP3620ADD-000100")/*
																																																									 * @res
																																																									 * "请选择一条数据"
																																																									 */);
				return;
			}
			if (selrows.length > 1) {
				MessageDialog.showWarningDlg(getUI(), nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add", "UPP3620ADD-000099")/*
																																			 * @res
																																			 * "提示"
																																			 */, nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add", "UPP3620ADD-000101")/*
																																																									 * @res
																																																									 * "上传附件时，一次只能选择一条数据"
																																																									 */);
				return;
			}
		} else {
			if (getBufferData().getCurrentVO() == null) {
				MessageDialog.showWarningDlg(getUI(), nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add", "UPP3620ADD-000099")/*
																																			 * @res
																																			 * "提示"
																																			 */, nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add", "UPP3620ADD-000100")/*
																																																									 * @res
																																																									 * "请选择一条数据"
																																																									 */);
				return;
			}
		}

		// String funcode = getUI().getModuleCode();

		RegisterVO registerVO = null;
		registerVO = (RegisterVO) getBufferData().getCurrentVO().getParentVO();
		if (registerVO.getPrimaryKey() == null
				|| registerVO.getPrimaryKey().length() <= 0
				|| registerVO.getVbillno() == null
				|| registerVO.getVbillno().length() <= 0) {
			MessageDialog.showWarningDlg(getUI(), nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add", "UPP3620ADD-000099")/*
																																		 * @res
																																		 * "提示"
																																		 */, nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add", "UPP3620ADD-000102")/*
																																																								 * @res
																																																								 * "未保存不能上传附件"
																																																								 */);
		}
		String strPK = registerVO.getPrimaryKey();
		FileManageUI fileui = new FileManageUI();
		fileui.setRootDirStr(strPK);
		fileui.setTreeRootVisible(false);
		UIDialog dlg = new UIDialog(getBillUI());
		dlg.getContentPane().setLayout(new BorderLayout());
		dlg.getContentPane().add(fileui, "Center");
		dlg.setResizable(true);
		dlg.setSize(600, 400);
		dlg.setTitle(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add", "UPP3620ADD-000103")/*
																										 * @res
																										 * "单据文档管理"
																										 */);
		if (!registerVO.getVbillstatus().equals(new Integer(IBillStatus.FREE))) {
			fileui.setCreateNewFolderEnable(false);
			fileui.setDeleteNodeEnable(false);
			fileui.setUploadFileEnable(false);
			fileui.setDownloadFileEnable(false);
		}
		if (dlg.showModal() == UIDialog.ID_CANCEL)
			return;
	}

	/**
	 * 联查凭证
	 */
	private void queryVoucher() {
		AggregatedValueObject vo = getBufferData().getCurrentVO();
		if (null == vo) {
			getBillUI().showWarningMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm", "UPPFBMComm-000146")/*
																													 * @res
																													 * "请选择要联查的单据。"
																													 */);
			return;
		}
		String corpPk = _getCorp().getPk_corp();
		try {
			String pk = vo.getParentVO().getPrimaryKey();
			String procmsg = pk;

			String pk_billtypecode = getUIController().getBillType();
			if (pk_billtypecode.equals(FbmBusConstant.BILLTYPE_INNERKEEP)
					|| pk_billtypecode.equals(FbmBusConstant.BILLTYPE_INNERBACK)
					|| pk_billtypecode.equals(FbmBusConstant.BILLTYPE_RELIEF)
					|| pk_billtypecode.equals(FbmBusConstant.BILLTYPE_LIQUIDATE)
					|| pk_billtypecode.equals(FbmBusConstant.BILLTYPE_RECKON_RECIEPT)) {
				procmsg = pk + StorageVO.Procmsg_flag + corpPk;
			}

			BillQueryVoucherVO queryvo = new BillQueryVoucherVO();

			procmsg = findProcmsg4UpgradeData(procmsg);

			queryvo.setDestSystem(IAccountPlat.DESTSYS_GL);// 联查总账凭证

			queryvo.setPk_bill(procmsg);

			DapBillQueryVoucher.showVoucher(queryvo, getBillUI());
		} catch (Exception ex) {
			Logger.error(ex.getMessage());
			getBillUI().showErrorMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm", "UPPFBMComm-000147")/*
																													 * @res
																													 * "联查凭证出错，详情请见错误日志。"
																													 */);
			return;
		}

	}

	// 背书办理联查冲销凭证
	private void queryDestroyVoucher() {
		AggregatedValueObject vo = getBufferData().getCurrentVO();
		if (null == vo) {
			getBillUI().showWarningMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm", "UPPFBMComm-000146")/*
																													 * @res
																													 * "请选择要联查的单据。"
																													 */);
			return;
		}
		String corpPk = _getCorp().getPk_corp();
		try {
			String pk = vo.getParentVO().getPrimaryKey();
			String procmsg = pk
					+ StorageVO.Procmsg_flag
					+ corpPk
					+ StorageVO.Procmsg_flag
					+ FbmBusConstant.BILLTYPE_ENDORECLEAE;

			BillQueryVoucherVO queryvo = new BillQueryVoucherVO();

			if (FBMClientInfo.isSettleCenter()) {
				queryvo.setDestSystem(IAccountPlat.DESTSYS_SETTLE);// 如果是中心则联查结算凭证
			} else {
				queryvo.setDestSystem(IAccountPlat.DESTSYS_GL);// 其他则联查总账凭证
			}
			queryvo.setPk_bill(procmsg);

			DapBillQueryVoucher.showVoucher(queryvo, getBillUI());
		} catch (Exception ex) {
			Logger.error(ex.getMessage());
			getBillUI().showErrorMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm", "UPPFBMComm-000147")/*
																													 * @res
																													 * "联查凭证出错，详情请见错误日志。"
																													 */);
			return;
		}

	}

	private String beforeLinkFBMBillBook() throws BusinessException {
		int rowCount = 0;
		int currow = 0;
		AggregatedValueObject vo = getBufferData().getCurrentVO();
		if (vo == null) {
			throw new BusinessException(
					nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm", "UPPFBMComm-000145")/*
																								 * @res
																								 * "请选择一条记录"
																								 */);
		}

		SuperVO curvo = (SuperVO) vo.getParentVO();
		String billtype = (String) curvo.getAttributeValue("pk_billtypecode");
		String pk_baseinfo = null;

		if (billtype.equalsIgnoreCase(FbmBusConstant.BILLTYPE_GATHER)
				|| billtype.equalsIgnoreCase(FbmBusConstant.BILLTYPE_INVOICE)) {
			pk_baseinfo = (String) curvo.getAttributeValue("pk_baseinfo");
		} else if (billtype.equalsIgnoreCase(FbmBusConstant.BILLTYPE_COLLECTION_UNIT)
				|| billtype.equalsIgnoreCase(FbmBusConstant.BILLTYPE_DISCOUNT_TRANSACT)
				|| billtype.equalsIgnoreCase(FbmBusConstant.BILLTYPE_DISCOUNT_APP)
				|| billtype.equalsIgnoreCase(FbmBusConstant.BILLTYPE_IMPAWN)
				|| billtype.equalsIgnoreCase(FbmBusConstant.BILLTYPE_BILLPAY)
				|| billtype.equalsIgnoreCase(FbmBusConstant.BILLTYPE_ENDORE)) {
			pk_baseinfo = (String) curvo.getAttributeValue("pk_baseinfo");
		} else if (billtype.equalsIgnoreCase(FbmBusConstant.BILLTYPE_BANKBACK)
				|| billtype.equalsIgnoreCase(FbmBusConstant.BILLTYPE_BANKKEEP)
				|| billtype.equalsIgnoreCase(FbmBusConstant.BILLTYPE_INNERBACK)
				|| billtype.equalsIgnoreCase(FbmBusConstant.BILLTYPE_INNERKEEP)
				|| billtype.equalsIgnoreCase(FbmBusConstant.BILLTYPE_RETURN)
				|| billtype.equalsIgnoreCase(FbmBusConstant.BILLTYPE_RELIEF)) {
			if (!billtype.equalsIgnoreCase(FbmBusConstant.BILLTYPE_RETURN) && !billtype.equalsIgnoreCase(FbmBusConstant.BILLTYPE_RELIEF)) {
				if(getUI().isListPanelSelected()){
					rowCount = ((FBMManageUI) getBillUI()).getBillListPanel().getBodyTable(StorageBVO.tablecode).getSelectedRowCount();
					currow = ((FBMManageUI) getBillUI()).getBillListPanel().getBodyTable(StorageBVO.tablecode).getSelectedRow();
				}else
				{
					currow = ((FBMManageUI) getBillUI()).getBillCardPanel().getBillTable(StorageBVO.tablecode).getSelectedRow();
					rowCount = ((FBMManageUI) getBillUI()).getBillCardPanel().getBillTable(StorageBVO.tablecode).getSelectedRowCount();
				}
			}
			if (billtype.equalsIgnoreCase(FbmBusConstant.BILLTYPE_RETURN)) {
				if(getUI().isListPanelSelected()){
					rowCount = ((FBMManageUI) getBillUI()).getBillListPanel().getBodyTable(ReturnBVO.tablecode).getSelectedRowCount();
					currow = ((FBMManageUI) getBillUI()).getBillListPanel().getBodyTable(ReturnBVO.tablecode).getSelectedRow();
				}else
				{
					rowCount = ((FBMManageUI) getBillUI()).getBillCardPanel().getBillTable(ReturnBVO.tablecode).getSelectedRowCount();
					currow = ((FBMManageUI) getBillUI()).getBillCardPanel().getBillTable(ReturnBVO.tablecode).getSelectedRow();
				}
			} else if (billtype.equalsIgnoreCase(FbmBusConstant.BILLTYPE_RELIEF)) {
				if(getUI().isListPanelSelected()){
					rowCount = ((FBMManageUI) getBillUI()).getBillListPanel().getBodyTable(ReliefBVO.TABLECODE).getSelectedRowCount();
					currow = ((FBMManageUI) getBillUI()).getBillListPanel().getBodyTable(ReliefBVO.TABLECODE).getSelectedRow();
				}else
				{
					rowCount = ((FBMManageUI) getBillUI()).getBillCardPanel().getBillTable(ReliefBVO.TABLECODE).getSelectedRowCount();
					currow = ((FBMManageUI) getBillUI()).getBillCardPanel().getBillTable(ReliefBVO.TABLECODE).getSelectedRow();
				}
			}
			if (currow < 0 || rowCount>1) {
				throw new BusinessException(
						nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm", "UPPFBMComm-000148")/*
																									 * @res
																									 * "请选择一条子表记录"
																									 */);
			}
			SuperVO[] bodyvos = (SuperVO[]) vo.getChildrenVO();
			pk_baseinfo = (String) bodyvos[currow].getAttributeValue("pk_baseinfo");

		} 
		return pk_baseinfo;
	}

	/**
	 * 根据公司pk和帐号查询内部帐户的账面余额
	 * 
	 * @param pk_corp
	 * @param accNo
	 * @return
	 */
	private UFDouble getBalance(String pk_corp, String pk_accid)
			throws BusinessException {
		/* 计算账面余额 */
		UFDouble udBookBalance = null; // 账面余额
		// try {
		// udBookBalance = FTSProxy.getCommon().getBalanceBook(pk_corp,
		// pk_accid);
		// } catch (Exception ex) {
		// Debug.error(ex);
		// MessageDialog.showErrorDlg(getUI(),
		// nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm","UPPFBMComm-000149")/*
		// @res"提示："*/,
		// nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm","UPPFBMComm-000150")/*
		// @res"查询账面余额出错"*/);
		// }
		AccDetailQueryVO accdetailqueryvo = new AccDetailQueryVO();
		accdetailqueryvo.setPk_accid(pk_accid);
		AccBalanceVO accrealbalancevo = (AccBalanceVO) FTSProxy.getAccountService().queryAccRealBalance(accdetailqueryvo);
		return accrealbalancevo.getRealbalance();
	}

	/**
	 * 联查账户余额 账面(根据凭证联查)
	 * 
	 * @throws BusinessException
	 */
	private void showAccBalance(String accKey) throws BusinessException {
		String pk_cust = null;
		String pk_corp = null;
		String pk_accid = null;

		AggregatedValueObject vo = getBufferData().getCurrentVO();
		if (vo == null) {
			throw new BusinessException(
					nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm", "UPPFBMComm-000145")/*
																								 * @res
																								 * "请选择一条记录"
																								 */);
		}
		SuperVO curvo = (SuperVO) vo.getParentVO();

		String billtype = (String) curvo.getAttributeValue("pk_billtypecode");
		// 清算/清算回单，则将superVO转为ReckonVO
		if (FbmBusConstant.BILLTYPE_LIQUIDATE.equals(billtype)
				|| FbmBusConstant.BILLTYPE_RECKON_RECIEPT.equals(billtype)) {
			ReckonVO reckonVO = (ReckonVO) curvo;
			pk_cust = reckonVO.getReckonunit();// 清算单位
			pk_accid = (String) reckonVO.getAttributeValue(accKey);// 清算账户
		}
		// 调剂则将superVO转为ReliefVO
		if (FbmBusConstant.BILLTYPE_RELIEF.equals(billtype)) {
			ReliefVO reliefVO = (ReliefVO) curvo;
			pk_cust = reliefVO.getReliefunit();// 调剂单位
			pk_accid = reliefVO.getInneracc();// 调剂账户
		}

		if (FbmBusConstant.BILLTYPE_INNERKEEP.equals(billtype)) {
			StorageVO storageVO = (StorageVO) curvo;
			pk_cust = storageVO.getKeepunit();
			pk_accid = storageVO.getKeepaccount();
		}
		if (FbmBusConstant.BILLTYPE_INNERBACK.equals(billtype)) {
			StorageVO storageVO = (StorageVO) curvo;
			pk_cust = storageVO.getOutputunit();
			pk_accid = storageVO.getKeepaccount();
		}
		// 中心
		if (FBMClientInfo.isSettleCenter()) {
			pk_corp = FBMClientInfo.queryCorpByCust(pk_cust);// 调剂单位对应公司
		} else {
			pk_corp = FBMClientInfo.getCorpPK();// 取当前公司pk
		}

		if (pk_accid == null) {
			throw new BusinessException(
					nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add", "UPP3620ADD-000104")/*
																										 * @res
																										 * "账户字段为空，无法联查"
																										 */);
		}
		UFDouble balance = getBalance(pk_corp, pk_accid);// 得到账面余额

		CurrtypeVO currVO = FBMClientInfo.getCurrTypeVO(pk_accid);// 根据账户得到账户对应币种的VO

		balance = balance.setScale(0 - currVO.getCurrdigit(), UFDouble.ROUND_HALF_UP);// 设置精度

		BalanceQryDlg dlg = new BalanceQryDlg(this.getUI());

		dlg.getUITextFieldBookBalance_pub().setText(balance.toString());// 给对话框赋值

		dlg.showModal();
	}

	@Override
	protected void onBoDelete() throws Exception {

		boolean isListSelect = ((FBMManageUI) getBillUI()).isListPanelSelected();
		String billno = null;
		if (isListSelect) {
			billno = (String) getBufferData().getCurrentVO().getParentVO().getAttributeValue(getBillNoKey());
		} else {
			BillItem bi = getBillCardPanelWrapper().getBillCardPanel().getHeadItem(getBillNoKey());

			if (bi != null) {
				billno = (String) bi.getValueObject();
			}
		}
		super.onBoDelete();
		if (billno != null) {
			OuterProxy.getBillCodeRuleService().returnBillCodeOnDelete(ClientInfo.getCorpPK(), getUIController().getBillType(), billno, null);
		}
	}

	protected String getHeadCondition() {
		// 公司
		if (getBillCardPanelWrapper() != null)
			if (getBillCardPanelWrapper().getBillCardPanel().getHeadTailItem(getBillField().getField_Corp()) != null) {
				String tablename = null;
				try {
					tablename = getUIController().getBillVoName()[1];
					SuperVO vo = (SuperVO) InitClass(Class.forName(tablename));
					tablename = vo.getTableName();
				} catch (Exception e) {
				}

				return " "
						+ (tablename == null ? "" : (tablename + "."))
						+ getBillField().getField_Corp()
						+ "='"
						+ getBillUI()._getCorp().getPrimaryKey()
						+ "'";
			}
		return null;
	}

	protected Object InitClass(Class className) {
		try {
			return className.newInstance();
		} catch (InstantiationException e) {
			throw new IllegalArgumentException(
					nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm", "UPPFBMComm-000151")/*
																								 * @res
																								 * "传入的参数类无法进行实例化"
																								 */);
		} catch (IllegalAccessException e) {
			throw new IllegalArgumentException(
					nc.ui.ml.NCLangRes.getInstance().getStrByID("fbmcomm", "UPPFBMComm-000152")/*
																								 * @res
																								 * "传入不合法参数"
																								 */);
		}
	}

	/**
	 * 弹出查询对话框向用户询问查询条件。 如果用户在对话框点击了“确定”，那么返回true,否则返回false
	 * 查询条件通过传入的StringBuffer返回给调用者
	 * 
	 * @param sqlWhereBuf
	 *            保存查询条件的StringBuffer
	 * @return 用户选确定返回true否则返回false
	 */
	protected boolean askForQueryCondition(StringBuffer sqlWhereBuf)
			throws Exception {
		if (sqlWhereBuf == null)
			throw new IllegalArgumentException(
					"askForQueryCondition().sqlWhereBuf cann't be null");
		UIDialog querydialog = getQueryUI();

		if (querydialog.showModal() != UIDialog.ID_OK)
			return false;
		QueryConditionDLG query = (QueryConditionDLG) querydialog;

		String strWhere = query.getWhereSQL();
		if (strWhere == null)
			strWhere = "1=1";

		String tablename = null;
		try {
			tablename = getUIController().getBillVoName()[1];
			SuperVO vo = (SuperVO) InitClass(Class.forName(tablename));
			tablename = vo.getTableName();
		} catch (Exception e) {
		}
		tablename = (tablename == null ? "" : (tablename + "."));

		if (getButtonManager().getButton(IBillButton.Busitype) != null) {
			if (getBillIsUseBusiCode().booleanValue())
				// 业务类型编码
				strWhere = "("
						+ strWhere
						+ ") and "
						+ tablename
						+ getBillField().getField_BusiCode()
						+ "='"
						+ getBillUI().getBusicode()
						+ "'";

			else
				// 业务类型
				strWhere = "("
						+ strWhere
						+ ") and "
						+ tablename
						+ getBillField().getField_Busitype()
						+ "='"
						+ getBillUI().getBusinessType()
						+ "'";

		}

		strWhere = "(" + strWhere + ") and (isnull(" + tablename + "dr,0)=0)";

		if (getHeadCondition() != null)
			strWhere = strWhere + " and " + getHeadCondition();
		// 现在我先直接把这个拼好的串放到StringBuffer中而不去优化拼串的过程
		sqlWhereBuf.append(strWhere);
		return true;
	}

	@Override
	protected void afterOnBoAction(int intBtn, AggregatedValueObject billVo)
			throws Exception {
		super.afterOnBoAction(intBtn, billVo);
		switch (intBtn) {
		case IFBMButton.ConsignBank_Voucher:
		case IFBMButton.Discount_Voucher:
		case IFBMButton.Endore_Voucher:
		case IFBMButton.Reckon_Voucher:
		case IFBMButton.Relief_Voucher:
		case IFBMButton.Reciept_Voucher:
		case IFBMButton.FBM_VOUCHER:
			try {
				IDapMsgService Idapmsg = (IDapMsgService) NCLocator.getInstance().lookup(IDapMsgService.class.getName());

				// 由于多帐簿需要做的修改 20081216 xwq
				DapMsgVO msgVO = Idapmsg.getDapMsgVo(getUIController().getBillType(), billVo, FbmBusConstant.OP_VOUCHER);
				generator = getBatchGenerator();
				String[][] bill = { { msgVO.getProc(), msgVO.getProcMsg() } };
				BillQueryVoucherVO queryvo = new BillQueryVoucherVO();
				queryvo.setDestSystem(IAccountPlat.DESTSYS_GL);
				queryvo.setPk_bill(msgVO.getProcMsg());// 单据pk
				OperationParameter para = new OperationParameter();
				para.IsLeachInvalidBill = true;

				BillQueryVoucherVO[] queryvos = FTSProxy.getDapQueryMessage().queryAllVouchers(new BillQueryVoucherVO[] { queryvo });

				if (queryvos == null || queryvos.length == 0) {
					// 没有单据对应的实时凭证和总账凭证
					generator.generateBatch(bill, ((FBMManageUI) getBillUI()).getm_parent(), _getDate(), (FBMManageUI) getBillUI(), para);
				} else {
					// 所有单据对应的凭证中是否存在实时凭证（以为多账簿的存在，一张单据可能对应多个凭证）
					UFBoolean isExistRealVoucher = new UFBoolean(false);
					for (int j = 0; j < queryvos.length; j++) {
						if (queryvos[j].getPk_voucher() == null) {
							isExistRealVoucher = new UFBoolean(true);
						}
					}
					if (isExistRealVoucher.booleanValue()) {
						generator.generateBatch(bill, ((FBMManageUI) getBillUI()).getm_parent(), _getDate(), (FBMManageUI) getBillUI(), para);
					} else {
						MessageDialog.showHintDlg(getBillUI(), nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcode112", "UPPFBMCODE112-000018")/*
																																						 * @res
																																						 * "提示"
																																						 */, nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcode112", "UPPFBMCODE112-000019")/*
																																																														 * @res
																																																														 * "此单据已经生成总账凭证！"
																																																														 */);
					}
				}
			} catch (Exception e) {
				getBillUI().showErrorMessage(e.getMessage());
				Logger.error(e.getMessage());
			} finally {
				onBoRefresh();
			}
			break;
		default:
			break;
		}

	}

	@Override
	protected void onBoEdit() throws Exception {

		super.onBoEdit();
		// AggregatedValueObject vo = getBufferData().getCurrentVO();
		// if (vo.getChildrenVO() != null) {
		// getBillCardPanelWrapper().getBillCardPanel().getTailItem(StorageVO.VOPERATORID).setValue(_getOperator());
		// getBillCardPanelWrapper().getBillCardPanel().getTailItem(StorageVO.DOPERATEDATE).setValue(_getDate());
		// }
		BillItem operor = null;
		BillItem operdate = null;

		operor = getBillCardPanelWrapper().getBillCardPanel().getTailItem(StorageVO.VOPERATORID);
		operdate = getBillCardPanelWrapper().getBillCardPanel().getTailItem(StorageVO.DOPERATEDATE);

		if (operor != null && operdate != null) {
			operor.setValue(_getOperator());
			operdate.setValue(_getDate());
		}
	}

	/**
	 * 提交
	 * 
	 * @throws Exception
	 */
	protected void onBoSubmit() throws Exception {
		IFBMBillSubmit submit = (IFBMBillSubmit) NCLocator.getInstance().lookup(IFBMBillSubmit.class.getName());
		HYBillVO billvo = (HYBillVO) getBufferData().getCurrentVO();
		if (billvo != null) {
			int vbillstatus = (Integer) billvo.getParentVO().getAttributeValue("vbillstatus");
			if (vbillstatus != IFBMStatus.CHECKPASS) {
				throw new BusinessException(
						nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add", "UPP3620ADD-000105")/*
																											 * @res
																											 * "单据状态不是审核通过无法提交"
																											 */);
			}
			submit.submit(billvo, getUI()._getDate(), getUI()._getOperator());
			onBoRefresh();
		}
	}

	/**
	 * 取消提交
	 * 
	 * @throws Exception
	 */
	protected void onBoCancelSubmit() throws Exception {
		IFBMBillSubmit submit = (IFBMBillSubmit) NCLocator.getInstance().lookup(IFBMBillSubmit.class.getName());
		HYBillVO billvo = (HYBillVO) getBufferData().getCurrentVO();

		if (billvo != null) {
			int vbillstatus = (Integer) billvo.getParentVO().getAttributeValue("vbillstatus");
			if (vbillstatus != IFBMStatus.SUBMIT) {
				throw new BusinessException(
						nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add", "UPP3620ADD-000106")/*
																											 * @res
																											 * "单据状态不是已提交，无法取消提交"
																											 */);
			}
			submit.cancelSubmit(billvo, getUI()._getDate(), getUI()._getOperator());
			onBoRefresh();
		}
	}

	/**
	 * 创建联查资金计划VO
	 * 
	 * @return
	 * @throws Exception
	 */
	protected IOBudgetQueryVO createQueryPlanVO(String planitemKey)
			throws Exception {
		IOBudgetQueryVO ioBgtqvos = new IOBudgetQueryVO();
		ioBgtqvos.setPk_selfcorp(_getCorp().getPrimaryKey());

		ioBgtqvos.setSyscode(FbmBusConstant.SYSCODE_FBM);
		ioBgtqvos.setPk_billtypecode(getUI().getUIControl().getBillType());
		return ioBgtqvos;
	}

	/**
	 * 联查资金计划
	 * 
	 * @throws Exception
	 */
	protected void onQueryPlan(IOBudgetQueryVO ioBgtqvos) throws Exception {
		// 判断资金计划是否安装
		ICreateCorpQueryService productSrv = (ICreateCorpQueryService) NCLocator.getInstance().lookup(ICreateCorpQueryService.class.getName());

		if (!productSrv.isEnabled(getUI()._getCorp().getPrimaryKey(), "FP")) {
			MessageDialog.showHintDlg(getBillUI(), nc.ui.ml.NCLangRes.getInstance().getStrByID("361614", "UPP361614-000007")/*
																															 * @res
																															 * "提示"
																															 */, nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add", "UPP3620ADD-000107")/*
																																																					 * @res
																																																					 * "资金计划模块没有安装不支持联查计划"
																																																					 */);
			return;
		}

		try {
			// 如果设置计划日期则取当前日期
			if (ioBgtqvos != null && ioBgtqvos.getCheckplandate() == null) {
				ioBgtqvos.setCheckplandate(_getDate());
			}
			PlanVO[] planvos = FpProxy.getFtsPlan().getPlanVO(ioBgtqvos, "1=1"); // is_perform=1
			if (planvos == null || planvos.length == 0) {
				MessageDialog.showHintDlg(getBillUI(), nc.ui.ml.NCLangRes.getInstance().getStrByID("361614", "UPP361614-000007")/*
																																 * @res
																																 * "提示"
																																 */, nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add", "UPP3620ADD-000108")/*
																																																						 * @res
																																																						 * "没有对应的资金计划表!"
																																																						 */);
				return;
			}
			Class moduleClass = Class.forName("nc.ui.fp.fp611.PlanVersDlg");
			Constructor cs = null;
			Object[] objPara = new Object[3];
			objPara[0] = getBillUI();
			objPara[1] = nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add", "UPP3620ADD-000109")/*
																											 * @res
																											 * "联查计划表"
																											 */;
			objPara[2] = planvos;

			Class[] paraClass = new Class[3];
			paraClass[0] = Container.class;
			paraClass[1] = String.class;
			paraClass[2] = objPara[2].getClass();

			// 带参构造方法：
			cs = moduleClass.getConstructor(paraClass);
			UIDialog newDlg = (UIDialog) cs.newInstance(objPara);
			newDlg.showModal();
		} catch (Exception e) {
			Logger.error(e.getMessage(), e);
			MessageDialog.showErrorDlg(getBillUI(), nc.ui.ml.NCLangRes.getInstance().getStrByID("361614", "UPP361614-000007")/*
																															 * @res
																															 * "提示"
																															 */, nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add", "UPP3620ADD-000110")/*
																																																					 * @res
																																																					 * "联查计划表出错:"
																																																					 */
					+ e.getMessage());
		}
	}

	/**
	 * 如果当前单据使用的内部账户是票据户时，给出不记账提示
	 * 
	 * @return boolean =TRUE不符合条件 =FALSE符合条件可以记账
	 * @throws BusinessException
	 */
	protected boolean showNoTallyInfo(String pk_accid) throws BusinessException {
		if (pk_accid == null) {
			return true;
		}
		AccidVO currAcc = (AccidVO) FBMProxy.getUifService().queryByPrimaryKey(AccidVO.class, pk_accid);
		if (currAcc != null && IAccConst.ACCL_BILL == currAcc.getAcctype()) {
			MessageDialog.showHintDlg(getUI(), nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add", "UPP3620ADD-000099")/*
																																	 * @res
																																	 * "提示"
																																	 */, nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add", "UPP3620ADD-000111")/*
																																																							 * @res
																																																							 * "内部账户类型为票据户，不记账"
																																																							 */);
			return false;
		}
		return true;
	}

	@Override
	public void onBoAudit() throws Exception {
		// 检查入账规则
		checkNull4InnerAcc();
		// 获得数据
		AggregatedValueObject modelVo = getBufferData().getCurrentVOClone();
		setCheckManAndDate(modelVo);
		// 如果状态一致则退出
		if (checkVOStatus(modelVo, new int[] { IBillStatus.CHECKPASS })) {
			return;
		}
		beforeOnBoAction(IBillButton.Audit, modelVo);

		// *******************
		Object returnVo = null;
		ManageHintBusiActionContext context = new ManageHintBusiActionContext(
				IPFACTION.APPROVE, modelVo, getBusinessAction(),
				(BillManageUI) getBillUI());

		IHintBusiAction<ManageHintBusiActionContext, Object> action = new FBMHintBusiAction<Object>();

		HintBusiActionResult<Object> result = HintBusiActionWorker.instance.processAction(action, context);
		
		returnVo = HintBusiActionWorker.instance.getBSRetValue(result);
		// try {
		// returnVo = PfUtilClient.runAction(getUI(), IPFACTION.APPROVE,
		// getUIController().getBillType(), getBillUI()._getDate().toString(),
		// modelVo, getBillUI().getUserObject(), null, null, null);
		//
		// } catch (Exception e) {
		// String mess = e.getMessage();
		// if (null != mess && mess.indexOf("10003") != -1) {//
		// 里面包含10003，说明是提示信息
		// int ret = MessageDialog.showYesNoDlg(this.getUI(), null, mess
		// + nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("faccdmcode",
		// "UPPFACCDMCODE-000034")/*
		// * @res
		// * "
		// * 是否继续
		// * ？
		// * "
		// */);
		// if (4 == ret) {// 用户点击yes
		// modelVo.getParentVO().setAttributeValue("writeplan", UFBoolean.TRUE);
		// returnVo = PfUtilClient.runAction(getUI(), IPFACTION.APPROVE,
		// getUIController().getBillType(), getBillUI()._getDate().toString(),
		// modelVo, getBillUI().getUserObject(), null, null, null);
		// }
		// } else if (e instanceof InnerAccountException) {
		// InnerAccountException ex = (InnerAccountException) e;
		//
		// if (IAccountConst.HINT.equals(ex.getErrType())) {// 提示
		// int ret = getUI().showYesNoMessage(ex.getErrInfo()
		// + nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("faccdmcode",
		// "UPPFACCDMCODE-000034")/*
		// * @res
		// * "
		// * 是否继续
		// * ？
		// * "
		// */);
		// if (4 == ret) {// 用户未点击yes
		// SuperVO superVO = (SuperVO) modelVo.getParentVO();
		// if (superVO instanceof StorageVO) {
		// superVO.setAttributeValue("writeinneracc", UFBoolean.TRUE);
		// }
		// returnVo = PfUtilClient.runAction(getUI(), IPFACTION.APPROVE,
		// getUIController().getBillType(), getBillUI()._getDate().toString(),
		// modelVo, getBillUI().getUserObject(), null, null, null);
		// }
		// }
		// } else if (e instanceof CmpAuthorizationException) {
		// CmpAuthorizationException ex = (CmpAuthorizationException) e;
		//
		// boolean result = new
		// TMAccExceptionHandler(getUI()).handleException(ex);
		// if (result) {// 用户未点击yes
		// SuperVO superVO = (SuperVO) modelVo.getParentVO();
		// if (superVO instanceof RegisterVO) {
		// superVO.setAttributeValue("writebankacc", UFBoolean.TRUE);
		// } else if (superVO instanceof ReckonVO) {
		// superVO.setAttributeValue("writebankacc", UFBoolean.TRUE);
		// }
		// modelVo.setParentVO(superVO);
		// returnVo = PfUtilClient.runAction(getUI(), IPFACTION.APPROVE,
		// getUIController().getBillType(), getBillUI()._getDate().toString(),
		// modelVo, getBillUI().getUserObject(), null, null, null);
		// }
		// // }
		// } else
		// throw e;
		// }
		//
		// AggregatedValueObject retVo = null;
		//
		// if (returnVo != null && returnVo instanceof Object[]) {
		// Object[] returnMsg = (Object[]) returnVo;
		// String ccReturnMsg = (String) returnMsg[0];
		// retVo = (AggregatedValueObject) returnMsg[1];
		// if (!CommonUtil.isNull(ccReturnMsg)) {
		// MessageDialog.showHintDlg(getUI(),
		// nc.ui.ml.NCLangRes.getInstance().getStrByID("36201505",
		// "UPP36201505-000003")/*
		// * @res
		// * "提示"
		// */, ccReturnMsg);
		// }
		// } else if (returnVo instanceof AggregatedValueObject
		// || returnVo instanceof HYBillVO) {
		// retVo = (AggregatedValueObject) returnVo;
		// }

		if (PfUtilClient.isSuccess()) {
			if (returnVo instanceof AggregatedValueObject) {
				AggregatedValueObject retVo = (AggregatedValueObject) returnVo;
				afterOnBoAction(IBillButton.Audit, retVo);
				CircularlyAccessibleValueObject[] childVos = getChildVO(retVo);
				if (childVos == null)
					modelVo.setParentVO(retVo.getParentVO());
				else
					modelVo = retVo;

				// 更新列表
				getBufferData().setVOAt(getBufferData().getCurrentRow(), modelVo);
				getBufferData().setCurrentRow(getBufferData().getCurrentRow());
			}
		}
		onBoRefresh();
		((FBMManageUI) getUI()).updateListVo();
	}

	/**
	 * 按钮m_boCancelAudit点击时执行的动作,如有必要，请覆盖.
	 */
	protected void onBoCancelAudit() throws Exception {
		// 获得数据
		AggregatedValueObject modelVo = getBufferData().getCurrentVOClone();
		// 放入反审批日期、审批人
		// setCheckManAndDate(modelVo);
		// 如果状态一致则退出
		if (checkVOStatus(modelVo, new int[] { IBillStatus.FREE })) {
			return;
		}
		// RegisterVO fillVo = fillInvoiceBufferVo(modelVo);
		// modelVo.setParentVO(fillVo);

		beforeOnBoAction(IBillButton.CancelAudit, modelVo);
		// *******************

		Object returnVo = PfUtilClient.processActionFlow(getUI(), IPFACTION.UNAPPROVE, getUIController().getBillType(), getBillUI()._getDate().toString(), modelVo, getBillUI().getUserObject());

		String ccReturnMsg = null;
		AggregatedValueObject retVo = null;

		if (returnVo != null && returnVo instanceof Object[]) {
			Object[] returnMsg = (Object[]) returnVo;
			ccReturnMsg = (String) returnMsg[0];
			retVo = (AggregatedValueObject) returnMsg[1];
			if (!CommonUtil.isNull(ccReturnMsg)) {
				MessageDialog.showHintDlg(getUI(), nc.ui.ml.NCLangRes.getInstance().getStrByID("36201505", "UPP36201505-000003")/*
																																 * @res
																																 * "提示"
																																 */, ccReturnMsg);
			}
		} else if (returnVo instanceof AggregatedValueObject) {
			retVo = (AggregatedValueObject) returnVo;
		}

		if (PfUtilClient.isSuccess()) {
			afterOnBoAction(IBillButton.CancelAudit, modelVo);
			CircularlyAccessibleValueObject[] childVos = getChildVO(retVo);
			if (childVos == null)
				modelVo.setParentVO(retVo.getParentVO());
			else
				modelVo = retVo;

			Integer intState = (Integer) modelVo.getParentVO().getAttributeValue(getBillField().getField_BillStatus());
			if (intState.intValue() == IBillStatus.FREE) {
				modelVo.getParentVO().setAttributeValue(getBillField().getField_CheckMan(), null);
				modelVo.getParentVO().setAttributeValue(getBillField().getField_CheckDate(), null);
			}
			// 更新列表数据
			getBufferData().setVOAt(getBufferData().getCurrentRow(), modelVo);
			getBufferData().setCurrentRow(getBufferData().getCurrentRow());
			((FBMManageUI) getUI()).updateListVo();
			// int currentRow = getBufferData().getCurrentRow();
			// int buffersize = getBufferData().getVOBufferSize();
			// onBoRefresh();
			// if (buffersize > 1) {
			// batchCacheQuery();
			// }
			// getBufferData().setCurrentRow(currentRow);
		}
	}

	@Override
	public void onBoActionElse(ButtonObject bo) throws Exception {
		AggregatedValueObject modelVo = getBufferData().getCurrentVOClone();
		int intBtn = 0;
		if (bo.getData() != null)
			intBtn = ((ButtonVO) bo.getData()).getBtnNo();
		beforeOnBoAction(intBtn, modelVo);
		filterCurrDateandUser(bo, modelVo);
		// *******************
		Object retObj = null;
		
		ManageHintBusiActionContext context = new ManageHintBusiActionContext(
				bo.getTag(), modelVo, getBusinessAction(),
				(BillManageUI) getBillUI());

		IHintBusiAction<ManageHintBusiActionContext, Object> action = new FBMHintBusiAction<Object>();

		HintBusiActionResult<Object> result = HintBusiActionWorker.instance.processAction(action, context);

		retObj = HintBusiActionWorker.instance.getBSRetValue(result);
		// try {
		// retObj = getBusinessAction().processAction(bo.getTag(), modelVo,
		// getUIController().getBillType(), getBillUI()._getDate().toString(),
		// getBillUI().getUserObject());
		// } catch (Exception e) {
		// String mess = e.getMessage();
		// if (null != mess && mess.indexOf("10003") != -1) {//
		// 里面包含10003，说明是提示信息
		// int ret = MessageDialog.showYesNoDlg(this.getUI(), null, mess
		// + nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("faccdmcode",
		// "UPPFACCDMCODE-000034")/*
		// * @res
		// * "
		// * 是否继续
		// * ？
		// * "
		// */);
		// if (4 == ret) {// 用户点击yes
		// modelVo.getParentVO().setAttributeValue("writeplan", UFBoolean.TRUE);
		// retObj = getBusinessAction().processAction(bo.getTag(), modelVo,
		// getUIController().getBillType(), getBillUI()._getDate().toString(),
		// getBillUI().getUserObject());
		// }
		// } else if (e instanceof InnerAccountException) {
		// InnerAccountException ex = (InnerAccountException) e;
		//
		// if (IAccountConst.HINT.equals(ex.getErrType())) {// 提示
		// int ret = getUI().showYesNoMessage(ex.getErrInfo()
		// + nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("faccdmcode",
		// "UPPFACCDMCODE-000034")/*
		// * @res
		// * "
		// * 是否继续
		// * ？
		// * "
		// */);
		// if (MessageDialog.YES_YESTOALL_NO_CANCEL_OPTION == ret) {// 用户点击yes
		// SuperVO superVO = (SuperVO) modelVo.getParentVO();
		// if (superVO instanceof StorageVO) {
		// ((StorageVO) superVO).setWriteinneracc(UFBoolean.TRUE);
		//
		// }
		// retObj = getBusinessAction().processAction(bo.getTag(), modelVo,
		// getUIController().getBillType(), getBillUI()._getDate().toString(),
		// getBillUI().getUserObject());
		// }
		// }
		// } else if (e instanceof CmpAuthorizationException) {
		// CmpAuthorizationException ex = (CmpAuthorizationException) e;
		//
		// boolean result = new
		// TMAccExceptionHandler(getUI()).handleException(ex);
		// if (result) {// 用户未点击yes
		// SuperVO superVO = (SuperVO) modelVo.getParentVO();
		// if (superVO instanceof RegisterVO) {
		// superVO.setAttributeValue("writebankacc", UFBoolean.TRUE);
		// }
		// modelVo.setParentVO(superVO);
		// retObj = getBusinessAction().processAction(bo.getTag(), modelVo,
		// getUIController().getBillType(), getBillUI()._getDate().toString(),
		// getBillUI().getUserObject());
		// }
		// // }
		// } else
		// throw e;
		// }

		AggregatedValueObject retVo = null;
		String ccReturnMsg = null;

		if (PfUtilClient.isSuccess()) {
			if (retObj instanceof Object[]) {
				Object[] returnMsg = (Object[]) retObj;
				ccReturnMsg = (String) returnMsg[0];
				retVo = (AggregatedValueObject) returnMsg[1];
				if (!CommonUtil.isNull(ccReturnMsg)) {
					MessageDialog.showHintDlg(getUI(), nc.ui.ml.NCLangRes.getInstance().getStrByID("36201505", "UPP36201505-000003")/*
																																	 * @res
																																	 * "提示"
																																	 */, ccReturnMsg);
				}
			}
			if (retObj instanceof AggregatedValueObject) {
				retVo = (AggregatedValueObject) retObj;
			}
			if (retObj == null) {
				retVo = modelVo;
			}
			afterOnBoAction(intBtn, retVo);
			CircularlyAccessibleValueObject[] childVos = getChildVO(retVo);
			if (childVos == null)
				modelVo.setParentVO(retVo.getParentVO());
			else
				modelVo = retVo;
			// 更新列表
			getBufferData().setVOAt(getBufferData().getCurrentRow(), modelVo);
			getBufferData().setCurrentRow(getBufferData().getCurrentRow());
		}

		((FBMManageUI) getUI()).updateListVo();
	}

	private void filterCurrDateandUser(ButtonObject bo,
			AggregatedValueObject modelVo) {
		String btncode = bo.getCode();
		if (btncode.equals(IFBMButton.FBM_TALLY_STR)
				|| btncode.equals(IFBMButton.FBM_CANCELTALLY_STR)) {
			SuperVO headVo = (SuperVO) modelVo.getParentVO();
			headVo.setAttributeValue(StorageVO.TALLYDATE, _getDate());
			headVo.setAttributeValue(StorageVO.TALLYMAN, _getOperator());
		}
	}

	private void setCheckManAndDate(AggregatedValueObject vo) throws Exception {
		// 放入审批日期、审批人
		vo.getParentVO().setAttributeValue(getBillField().getField_CheckDate(), getBillUI()._getDate());
		vo.getParentVO().setAttributeValue(getBillField().getField_CheckMan(), getBillUI()._getOperator());
	}

	/**
	 * 获得子表数据。 创建日期：(2004-3-11 17:44:14)
	 * 
	 * @return nc.vo.pub.CircularlyAccessibleValueObject[]
	 */
	private CircularlyAccessibleValueObject[] getChildVO(
			AggregatedValueObject retVo) {
		CircularlyAccessibleValueObject[] childVos = null;
		if (retVo instanceof IExAggVO)
			childVos = ((IExAggVO) retVo).getAllChildrenVO();
		else
			childVos = retVo.getChildrenVO();
		return childVos;
	}

	/**
	 * 这个方法不要删除
	 */
	@Override
	protected void onBoRefresh() throws Exception {
		super.onBoRefresh();
	}

	protected TemplateInfo createTemplateInof(AbstractBillUI ui) {
		TemplateInfo info = new TemplateInfo();
		info.setPk_Org(ClientInfo.getCorpPK());
		info.setCurrentCorpPk(ClientInfo.getCorpPK());
		info.setFunNode(ui.getModuleCode());
		info.setUserid(ClientInfo.getUserPK());
		info.setBusiType(getBillUI().getBusinessType());
		return info;
	}

	/**
	 * 日后修改，原因是卡片更新单据后返回按钮并没有更新列表界面数据，但是buffer已经更新，暂时这样修改
	 */
	protected void onBoReturn() throws Exception {
		super.onBoReturn();
		super.onBoRefresh();
	}

	/**
	 * 检查内部帐户是否为空
	 */
	protected void checkNull4InnerAcc() throws BusinessException {
		IAccRule accrule = FBMProxy.getAccRuleService();
		String pk_billtypecode = (String) getBufferData().getCurrentVO().getParentVO().getAttributeValue("pk_billtypecode");
		if (pk_billtypecode.equals(FbmBusConstant.BILLTYPE_LIQUIDATE)) {
			AccRuleVO accruleVO = accrule.retriveAccRef(pk_billtypecode, FbmBusConstant.ACC_IN);
			String inacc = ((ReckonVO) getBufferData().getCurrentVO().getParentVO()).getInacc();
			// 如果账户为空，并且入账规则必须入账，则报错
			if (inacc == null
					&& !accruleVO.getAccref().equals(FbmBusConstant.ACCRULE_NONE)) {
				throw new BusinessException(
						nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcode112", "UPPFBMCODE112-000020")/*
																													 * @res
																													 * "内部账户入账规则为票据户或活期户，必须录入转入账户"
																													 */);
			}

			accruleVO = accrule.retriveAccRef(pk_billtypecode, FbmBusConstant.ACC_OUT);
			String outacc = ((ReckonVO) getBufferData().getCurrentVO().getParentVO()).getOutacc();
			// 如果账户为空，并且入账规则必须入账，则报错
			if (outacc == null
					&& !accruleVO.getAccref().equals(FbmBusConstant.ACCRULE_NONE)) {
				throw new BusinessException(
						nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcode112", "UPPFBMCODE112-000021")/*
																													 * @res
																													 * "内部账户入账规则为票据户或活期户，必须录入转出账户"
																													 */);
			}
		} else if (pk_billtypecode.equals(FbmBusConstant.BILLTYPE_INNERKEEP)
				|| pk_billtypecode.equals(FbmBusConstant.BILLTYPE_INNERBACK)) {

			AccRuleVO accruleVO = accrule.retriveAccRef(pk_billtypecode, FbmBusConstant.ACC_INNER);
			StorageVO storageVO = ((StorageVO) getBufferData().getCurrentVO().getParentVO());
			// 如果是保管，则直接返回
			if (FbmBusConstant.KEEP_TYPE_STORE.equals(storageVO.getInputtype())) {
				return;
			}
			String keepaccount = storageVO.getKeepaccount();
			if (keepaccount == null
					&& !accruleVO.getAccref().equals(FbmBusConstant.ACCRULE_NONE)) {
				throw new BusinessException(
						nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcode112", "UPPFBMCODE112-000022")/*
																													 * @res
																													 * "内部账户入账规则为票据户或活期户，必须录入内部账户"
																													 */);
			}
		} else if (pk_billtypecode.equals(FbmBusConstant.BILLTYPE_RELIEF)) {
			AccRuleVO accruleVO = accrule.retriveAccRef(pk_billtypecode, FbmBusConstant.ACC_INNER);
			String inneracc = ((ReliefVO) getBufferData().getCurrentVO().getParentVO()).getInneracc();
			if (inneracc == null
					&& !accruleVO.getAccref().equals(FbmBusConstant.ACCRULE_NONE)) {
				throw new BusinessException(
						nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcode112", "UPPFBMCODE112-000022")/*
																													 * @res
																													 * "内部账户入账规则为票据户或活期户，必须录入内部账户"
																													 */);
			}
		}
	}

	/**
	 * 查找升级数据的总帐凭证
	 */
	protected String findProcmsg4UpgradeData(String procmsg)
			throws BusinessException {
		String destSystem = null;

		String sql = "select auditdate,pk_vouchentry from dap_finindex join fts_voucher on dap_finindex.pk_vouchentry = fts_voucher.pk_voucher where procmsg='"
				+ procmsg
				+ "'";
		List<String[]> list = (List<String[]>) FBMProxy.getUAPQuery().executeQuery(sql, new ArrayListProcessor());
		if (list == null || list.size() == 0) {// 没有找到结算凭证
			return procmsg;
		} else {
			Object[] info = list.get(0);
			return info[0] + "Voch" + info[1];
		}
	}

	/**
	 * 全选 作者: wangyg 日期: 2008-12-18
	 */
	public void selectAll() {
		int size = getBufferData().getVOBufferSize();
		for (int i = 0; i < size; i++) {
			getUI().getBillListPanel().getParentListPanel().getTable().setValueAt(new UFBoolean(
					true), i, 0);
		}
	}

	/**
	 * 全消 作者: wangyg 日期: 2008-12-18
	 */
	public void cancelSelect() {
		int size = getBufferData().getVOBufferSize();
		for (int i = 0; i < size; i++) {
			getUI().getBillListPanel().getParentListPanel().getTable().setValueAt(new UFBoolean(
					false), i, 0);
		}
	}

	/**
	 * 反选 作者: wangyg 日期: 2008-12-18
	 */
	public void invertSelect() {
		int size = getBufferData().getVOBufferSize();
		for (int i = 0; i < size; i++) {
			Object obj = getUI().getBillListPanel().getParentListPanel().getTable().getValueAt(i, 0);
			if (obj != null
					&& (new UFBoolean(obj.toString())).booleanValue() == true) {
				getUI().getBillListPanel().getParentListPanel().getTable().setValueAt(new UFBoolean(
						false), i, 0);
			} else {
				getUI().getBillListPanel().getParentListPanel().getTable().setValueAt(new UFBoolean(
						true), i, 0);
			}
		}
	}

	public void batchCacheQuery() throws Exception {

		String tablename = null;
		String wheresql = "";
		try {
			tablename = getUIController().getBillVoName()[1];
			SuperVO vo = (SuperVO) InitClass(Class.forName(tablename));
			tablename = vo.getTableName();
		} catch (Exception e) {
			tablename = "";
		}
		tablename = (tablename == null ? "" : (tablename + "."));

		String refpk = ((FBMManageUI) getBillUI()).getBatchQueryDlg().getTxtBillNo().getText();
		String billno[] = null;
		if (refpk != null && !"".equals(refpk)) {
			billno = refpk.split("\n");
		}
		UIDialog querydialog = getQueryUI();
		QueryConditionDLG query = (QueryConditionDLG) querydialog;
		String strWhere = query.getWhereSQL();

		if (strWhere == null) {
			strWhere = "1=1 ";
		}
		if (billno != null && billno.length > 0) {
			for (int i = 0; i < billno.length; i++) {
				if (!"".equals(billno[i].trim())) {
					wheresql = wheresql + "'" + billno[i] + "',";
				}
			}
			if (!"".equals(wheresql)) {
				wheresql = wheresql.substring(0, wheresql.length() - 1);
				strWhere = strWhere
						+ " and "
						+ tablename
						+ "pk_baseinfo in(select pk_baseinfo from fbm_baseinfo where fbm_baseinfo.fbmbillno in("
						+ wheresql
						+ "))";

			}
		}

		if (getHeadCondition() != null)
			strWhere = strWhere + " and " + getHeadCondition();
		SuperVO[] queryVos = queryHeadVOs(strWhere.toString());

		getBufferData().clear();
		// 增加数据到Buffer
		addDataToBuffer(queryVos);
		updateBuffer();

	}
	
	/**
	 * NC56处理制证内存泄露
	 * xwq
	 * @return
	 */
	private BatchGenerator getBatchGenerator(){
		if(generator == null){
			generator = new BatchGenerator();
		}
		return generator;
	}

}