/**
 *
 */
package nc.ui.fbm.discountcalculate;

import java.util.Vector;

import nc.bs.framework.common.NCLocator;
import nc.bs.logging.Logger;
import nc.itf.cdm.util.CommonUtil;
import nc.itf.fbm.discountcalculate.IInterestAccural;
import nc.ui.cdm.pub.print.CardPanelPRTS;
import nc.ui.fac.account.pub.RefTakenQueryConditionClient;
import nc.ui.fac.account.pub.RefTakenQueryFilterEditorFactory;
import nc.ui.fbm.pub.IFBMButton;
import nc.ui.ml.NCLangRes;
import nc.ui.pub.beans.UIDialog;
import nc.ui.querytemplate.QueryConditionDLG;
import nc.ui.tm.framework.util.ClientInfo;
import nc.ui.trade.base.AbstractBillUI;
import nc.ui.trade.base.IBillOperate;
import nc.ui.trade.bill.ICardController;
import nc.ui.trade.button.IBillButton;
import nc.ui.trade.query.INormalQuery;
import nc.vo.bd.b08.CustBasVO;
import nc.vo.fbm.proxy.OuterProxy;
import nc.vo.fbm.pub.constant.FbmBusConstant;
import nc.vo.fbm.pub.constant.FbmStatusConstant;
import nc.vo.fbm.register.RegisterVO;
import nc.vo.pub.BusinessException;
import nc.vo.pub.lang.UFDate;
import nc.vo.pub.query.QueryConditionVO;
import nc.vo.querytemplate.TemplateInfo;

/**
 * <p>
 *
 * <p>
 * 创建日期：2006-9-6
 *
 * @author lisg
 * @since v5.0
 */
public class DiscountCalculationEventHandler extends TotalCardEventHandler {

	/**
	 * 作者：lisg <br>
	 * 日期：2006-9-11
	 *
	 * @see nc.ui.trade.handler.EventHandler#createQueryUI()
	 */
	// protected UIDialog createQueryUI()
	// {
	// // TemplateInfo tempinfo = new TemplateInfo();
	// // tempinfo.setPk_Org(_getCorp().getPrimaryKey());
	// // tempinfo.setCurrentCorpPk(_getCorp().getPrimaryKey());
	// // tempinfo.setFunNode(getBillUI()._getModuleCode());
	// // tempinfo.setUserid(_getOperator());
	// // tempinfo.setBusiType(getBillUI().getBusinessType());
	// // tempinfo.setNodekey(getBillUI().getNodeKey());
	// //
	// // return new HYQueryConditionDLG(getBillUI(), null, tempinfo);
	// return new HYQueryDLG(
	// getBillUI(),
	// null,
	// _getCorp().getPrimaryKey(),
	// getBillUI()._getModuleCode(),
	// _getOperator(),
	// getBillUI().getBusinessType(),
	// getBillUI().getNodeKey());
	// }
	@Override
	protected UIDialog createQueryUI() {
		RefTakenQueryConditionClient dialog = new RefTakenQueryConditionClient(getBillUI(),createTemplateInof(getBillUI()));
		dialog.registerFieldValueEelementEditorFactory(new DisCalFieldValueElementEditorFactory(dialog.getQueryContext()));
		dialog.registerFilterEditorFactory(new RefTakenQueryFilterEditorFactory((DiscountCalculationUI)getBillUI(),((DiscountCalculationUI)getBillUI()).getRefTakenProccessor(),dialog));
		dialog.registerQueryTemplateTotalVOProceeor(new DiscountCalculateTemplateTotalVOProcessor());
		return dialog;
//		UIRefPane pane = new UIRefPane();
//		pane.setRefModel(new GatherBusRefModel());
//
//		QueryConditionVO[] conditionVOs = dialog.getQryCondEditor().getTotalVO().getConditionVOs();
//
//		for (int i = 0; i < conditionVOs.length; i++) {
//			 conditionVOs[i].getFieldCode().replaceFirst("fbm_register", "v_fbm_regbase");
//		}
//		dialog.initTempletDatas(conditionVOs);
//		dialog.setValueRef("v_fbm_regbase.pk_register", pane);

	}

	public DiscountCalculationEventHandler(TotalRowBillCardUI billUI,
			ICardController control) {
		super(billUI, control);
	}

	/**
	 * 作者：lisg <br>
	 * 日期：2006-9-8
	 *
	 * @see nc.ui.trade.bill.BillEventHandler#onBoElse(int)
	 */
	@Override
	protected void onBoElse(int intBtn) throws Exception {
		switch (intBtn) {
		case IFBMButton.Discount_Calculate:
			calculationDiscount(null, true);
			break;
		}
	}

	/**
	 * <p>
	 * 根据索引进行批量计算
	 * <p>
	 * 作者：lisg <br>
	 * 日期：2006-9-8
	 *
	 * @param rowList
	 * @param withMessage
	 * @throws Exception
	 */
	public void calculationDiscount(Integer[] rowList, boolean withMessage)
			throws Exception {

		getBillCardPanelWrapper().getBillCardPanel().stopEditing();

		// 1.组织VOs
		RegisterVO[] vos = getVOsByIndex(rowList);

		vos = filterVoByChoice(vos);

		if(CommonUtil.isNull(vos)) {
			RegisterVO[] vos2 = getAllRowVOs();
			vos2 = getCommonRowVOs(vos2);

			reSetRowVOs(vos2);
			return;
		}
//		if (CommonUtil.isNull(vos))
//			return;

		Vector[] checkv = checkVOs(vos);

		RegisterVO[] corvos = (RegisterVO[]) checkv[0].get(1);

		if (CommonUtil.isNull(corvos)) {
			if (withMessage) {
				getBillUI().showErrorMessage(
						NCLangRes.getInstance().getStrByID("362004",
								"UPP362004-000144")/* "表体数据不完整不能进行批算!" */);
			}
		} else if (corvos.length != vos.length && withMessage) {
			String errstr = (String) checkv[1].get(0);
			// "第 " + errstr + " 行, 数据不完整,是否进行批算?
			if (getBillUI().showYesNoMessage(
					NCLangRes.getInstance().getStrByID("362004",
							"UPP362004-000145")
							+ errstr
							+ NCLangRes.getInstance().getStrByID("362004",
									"UPP362004-000146")) == UIDialog.ID_YES) {
				// 3.后台去计算
				try {
					vos = callRemoteService(corvos);
				} catch (Exception ex) {
					AfterCalculate(false, vos);
					Logger.error(ex.getMessage(),ex);
					getBillUI().showErrorMessage(
							NCLangRes.getInstance().getStrByID("362004",
									"UPP362004-000147")/* 远程服务调用出错 */
									+ "\n" + ex.getMessage());
				}
			}
		} else {
			// 3.后台去计算
			try {
				vos = callRemoteService(corvos);
			} catch (Exception ex) {
				AfterCalculate(false, vos);
				Logger.error(ex.getMessage(),ex);
				getBillUI().showErrorMessage(
						NCLangRes.getInstance().getStrByID("362004",
								"UPP362004-000147")/* 远程服务调用出错 */
								+ "\n" + ex.getMessage());
			}
		}

		AfterCalculate(false, vos);
	}

	/**
	 * <p>
	 *
	 * <p>
	 * 作者：lisg <br>
	 * 日期：2006-9-18
	 *
	 * @param rowList
	 * @param vos
	 * @throws Exception
	 * @throws NumberFormatException
	 */
	private void AfterCalculate(boolean isDirect, RegisterVO[] vos)
			throws Exception, NumberFormatException {
		// 4.后续处理
		if (isDirect) {
			reSetRowVOs(vos);
		} else {

			boolean isLegal = true;
			if (TotalRowUITools.isLegalTotalController(getUIController())) {
				isLegal = true;
			} else {
				isLegal = false;
			}

			ITotalSpecify ts = getTotalSpecifyController();
			TotalRowPara trp = (ts == null) ? null : ts
					.getTotalRowPara(RegisterVO.class.getName());

			RegisterVO[] vos2 = getAllRowVOs();
			for (int i = 0; i < vos.length; i++) {
				if (isLegal && trp != null) {
					vos2[new Integer((String) vos[i].getAttributeValue(trp
							.getDistinctColName()))] = vos[i];
				} else {
					vos2[new Integer(vos[i].getDef5())] = vos[i];
				}
			}

			vos2 = getCommonRowVOs(vos2);

			reSetRowVOs(vos2);
		}
		return;
	}

	/**
	 * <p>
	 * 根据索引数组返回正确的VO数组,这些VO不包括索引行 如果传入参数为null,则返回所有VO对象
	 * <p>
	 * 作者：lisg <br>
	 * 日期：2006-9-8
	 *
	 * @param rowList
	 * @param v
	 * @param vos
	 * @return
	 */
	private RegisterVO[] getVOsByIndex(Integer[] rowList) throws Exception {
		Vector<RegisterVO> v = new Vector<RegisterVO>();
		RegisterVO[] vos = getAllRowVOs();

		if (CommonUtil.isNull(rowList)) {
			vos = getCommonRowVOs(vos);
			return vos;
		}

		// 1.组织VO
		for (int i = 0; i < rowList.length; i++) {
			v.add(vos[rowList[i]]);
		}

		// 2.得到重新组织的VOs
		vos = v.toArray(new RegisterVO[] {});

		vos = getCommonRowVOs(vos);
		return vos;
	}

	/**
	 * <p>
	 * 回调服务，得到计算结果
	 * <p>
	 * 作者：lisg <br>
	 * 日期：2006-9-8
	 *
	 * @param vos
	 * @return
	 */
	private RegisterVO[] callRemoteService(RegisterVO[] vos) throws Exception {
		vos = setVos(vos);
		IInterestAccural IInterAccu = (IInterestAccural) NCLocator
				.getInstance().lookup(IInterestAccural.class.getName());
		return IInterAccu.computetxjx(vos);
	}

	/**
	 * <p>
	 * 获得所有界面VO包括合计行在内
	 * <p>
	 * 作者：lisg <br>
	 * 日期：2006-9-20
	 *
	 * @return
	 * @throws Exception
	 */
	public RegisterVO[] getAllRowVOs() throws Exception {
		if (getBufferData() == null || getBufferData().getCurrentVO() == null)
			return null;

		RegisterVO[] vos2 = (RegisterVO[]) getBillCardPanelWrapper()
				.getBillVOFromUI().getChildrenVO();

		if (vos2 == null)
			return vos2;

		ITotalSpecify ts = getTotalSpecifyController();
		TotalRowPara trp = (ts == null) ? null : ts
				.getTotalRowPara(RegisterVO.class.getName());
		for (int index = 0; index < vos2.length; index++) {
			if (!isCountVO(vos2[index])) {
				if (TotalRowUITools.isLegalTotalController(getUIController())
						&& trp != null) {
					// 如果不是合计行则设置VO所在的序号
					vos2[index].setAttributeValue(trp.getDistinctColName(),
							(new Integer(index)).toString());
				} else {
					vos2[index].setDef5((new Integer(index)).toString());
				}
			}
		}

		return vos2;
	}

	private RegisterVO[] setVos(RegisterVO[] vos) {
		for (int i = 0; i < vos.length; i++) {
			UFDate enddate = getUfDate((((DiscountCalculationUI) getTotalRowBillCardUI())
					.getRefTakenProccessor().getValueByTakenInVO(
					RegisterVO.TABLENAME, vos[i], RegisterVO.ENDDATE)));
			String pk_curr = (String) (((DiscountCalculationUI) getTotalRowBillCardUI())
					.getRefTakenProccessor().getValueByTakenInVO(
					RegisterVO.TABLENAME, vos[i], RegisterVO.PK_CURR));
			vos[i].setEnddate(enddate);
			vos[i].setPk_curr(pk_curr);
		}
		return vos;
	}

	protected UFDate getUfDate(Object date) {
		UFDate ret = null;
		if (date != null) {
			if (date instanceof UFDate) {
				ret = (UFDate) date;
			} else if (date instanceof String) {
				ret = new UFDate((String) date);
			}
		}

		return ret;
	}

	/**
	 * <p>
	 * 获得VOS中非合计行的所有VOS,如果传入参数为空则返回当前所有的非合计VOs
	 * <p>
	 * 作者：lisg <br>
	 * 日期：2006-9-20
	 *
	 * @param vos
	 * @return
	 * @throws Exception
	 */
	public RegisterVO[] getCommonRowVOs(RegisterVO[] vos) throws Exception {

		if (vos == null) {
			vos = getAllRowVOs();
		}

		if (vos == null)
			return vos;

		Vector<RegisterVO> ret = new Vector<RegisterVO>();

		for (int index = 0; index < vos.length; index++) {
			if (isCountVO(vos[index]))
				continue;
			ret.add(vos[index]);

		}

		return ret.toArray(new RegisterVO[] {});

	}

	/**
	 * <p>
	 * 返回校验后的结果
	 * <p>
	 * 作者：lisg <br>
	 * 日期：2006-9-18
	 *
	 * @param vos
	 * @return
	 */
	public Vector[] checkVOs(RegisterVO[] vos) {
		if (CommonUtil.isNull(vos))
			return null;

		Vector[] retv = new Vector[2];
		retv[0] = new Vector();
		retv[1] = new Vector();

		StringBuffer corstr = new StringBuffer();
		StringBuffer errstr = new StringBuffer();

		Vector<RegisterVO> corvolist = new Vector<RegisterVO>();
		Vector<RegisterVO> errvolist = new Vector<RegisterVO>();

		boolean isLegal = true;
		if (TotalRowUITools.isLegalTotalController(getUIController())) {
			isLegal = true;
		} else {
			isLegal = false;
		}

		ITotalSpecify ts = getTotalSpecifyController();
		TotalRowPara trp = (ts == null) ? null : ts
				.getTotalRowPara(RegisterVO.class.getName());

		for (int index = 0; index < vos.length; index++) {
			if (!vos[index].customValidate(null)) {
				if (isLegal && trp != null) {
					errstr.append(","
							+ new Integer(
									new Integer((String) vos[index]
											.getAttributeValue(trp
													.getDistinctColName()))
											.intValue() + 1));
				} else {
					errstr.append(","
							+ new Integer(new Integer(vos[index].getDef5())
									.intValue() + 1));
				}
				errvolist.add(vos[index]);
			} else {
				if (isLegal) {
					corstr.append(","
							+ new Integer(
									new Integer((String) vos[index]
											.getAttributeValue(trp
													.getDistinctColName()))
											.intValue() + 1));
				} else {
					corstr.append(","
							+ new Integer(new Integer(vos[index].getDef5())
									.intValue() + 1));
				}
				corvolist.add(vos[index]);
			}
		}

		// 初始化正确列表
		if (corstr.length() != 0) {
			retv[0].add(corstr.substring(1));
		} else {
			retv[0].add("");
		}
		retv[0].add(corvolist.toArray(new RegisterVO[] {}));

		// 初始化错误列表
		if (errstr.length() != 0) {
			retv[1].add(errstr.substring(1));
		} else {
			retv[1].add("");
		}
		retv[1].add(errvolist.toArray(new RegisterVO[] {}));

		return retv;
	}

	/**
	 * 作者：lisg <br>
	 * 日期：2006-9-20
	 *
	 * @see nc.ui.fbm.m23.TotalCardEventHandlerWITHT#doUpdateBuffer()
	 */
	@Override
	protected void doUpdateBuffer(boolean isNull) throws Exception {
		/**
		 * 因为updateBuffer已经被final因此无法继承 只能拷贝函数内容劫持对于billUI状态的修改，
		 * 将该状态的附值推迟到函数体外，主要用来解决 事件完成响应以后，对按钮事件的继续激发
		 */
		if (getBufferData().getVOBufferSize() != 0) {

			getBillUI().setListHeadData(
					getBufferData().getAllHeadVOsFromBuffer());
			getBufferData().setCurrentRow(0);
			if (!isNull) {
				getBillUI().setBillOperate(IBillOperate.OP_EDIT);
			} else {
				getBillUI().setBillOperate(IBillOperate.OP_NOTEDIT);
			}

		} else {
			super.doUpdateBuffer(isNull);
			return;
		}
	}

	public void CalculateForeGround(RegisterVO[] vos) throws Exception {
		if (CommonUtil.isNull(vos)) {
			vos = getCommonRowVOs(null);
		}

		reSetRowVOs(vos);
	}

	@Override
	protected void doBodyQuery(String strWhere) throws Exception,
			ClassNotFoundException, InstantiationException,
			IllegalAccessException {
		String keepunit = null;
		if(OuterProxy.getSettleCenter().isSettleCenter(ClientInfo.getCorpPK())) {
			String sql=" bd_cubasdoc.pk_corp1 = '" + ClientInfo.getCorpPK() + "' ";
			CustBasVO[] custbasVos = null;
			try {
				custbasVos = OuterProxy.getCustManDocQuery().queryCustBasicInfo(sql, ClientInfo.getCorpPK());
			} catch (BusinessException e) {
				Logger.error(e.getMessage(),e);
			}
			if(custbasVos==null||custbasVos.length==0)
				throw new Exception(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add","UPP3620ADD-000019")/*@res "当前公司没有对应的客商信息，请检查！"*/);
			keepunit = custbasVos[0].getPk_cubasdoc();
		}
		strWhere += " and pk_billtypecode = '"
				+ FbmBusConstant.BILLTYPE_GATHER
				+ "' and ( ( exists (select 1 from fbm_action where pk_source =  v_fbm_regbase.pk_register and "
				+ "isnew = 'Y' and endstatus in ('"
				+ FbmStatusConstant.REGISTER + "','"
				+ FbmStatusConstant.HAS_BANK_KEEP + "','"
				+ FbmStatusConstant.HAS_INNER_KEEP + "','"
				+ FbmStatusConstant.HAS_RELIEF_KEEP + "', '"
				+ FbmStatusConstant.ON_INNER_KEEP + "', " + "'"
				+ FbmStatusConstant.ON_RELIEF_KEEP + "', '"
				+ FbmStatusConstant.ON_BANK_BACK + "','"
				+ FbmStatusConstant.ON_INNER_BACK + "','"
				+ FbmStatusConstant.ON_BANK_KEEP
				+ "') and isnull(fbm_action.dr,0) = 0)  and pk_corp = '"+ ClientInfo.getCorpPK() +"' ) " ;


		strWhere +=	"or ( exists (select 1 from fbm_action a where a.pk_source =  v_fbm_regbase.pk_register and "
				+ "a.isnew = 'Y' and a.endstatus = '" + FbmStatusConstant.HAS_RELIEF_KEEP + "' and " +
			      "isnull(a.dr,0) = 0)  and pk_corp != '"+ ClientInfo.getCorpPK() +"' and  v_fbm_regbase.keepunit = " +
			      " '"+ keepunit +"')";

		strWhere += " )";
		super.doBodyQuery(strWhere);
	}

	protected boolean askForBodyQueryCondition(StringBuffer sqlWhereBuf)
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

		if (getUIController().getBodyCondition() != null)
			strWhere = strWhere + " and "
					+ getUIController().getBodyCondition();
		// 现在我先直接把这个拼好的串放到StringBuffer中而不去优化拼串的过程
		sqlWhereBuf.append(strWhere);
		return true;
}

	@Override
	protected void onBoPrint() throws Exception {
		// nc.ui.pub.print.IDataSource dataSource = new
		// CardPanelPRTS(getBillUI()
		// ._getModuleCode(), getBillCardPanelWrapper().getBillCardPanel());
		// nc.ui.pub.print.PrintEntry print = new
		// nc.ui.pub.print.PrintEntry(null,
		// dataSource);
		// print.setTemplateID(getBillUI()._getCorp().getPrimaryKey(),
		// getBillUI()
		// ._getModuleCode(), getBillUI()._getOperator(), getBillUI()
		// .getBusinessType(), getBillUI().getNodeKey());
		// if (print.selectTemplate() == 1)
		// print.preview();

		nc.ui.pub.print.IDataSource dataSource = new CardPanelPRTS(getBillUI()
				._getModuleCode(),
				getBillCardPanelWrapper().getBillCardPanel(),
				((DiscountCalculationUI) getBillUI()).getRefTakenProccessor());
		nc.ui.pub.print.PrintEntry print = new nc.ui.pub.print.PrintEntry(null,
				dataSource);
		print.setTemplateID(getBillUI()._getCorp().getPrimaryKey(), getBillUI()
				._getModuleCode(), getBillUI()._getOperator(), getBillUI()
				.getBusinessType(), getBillUI().getNodeKey());
		if (print.selectTemplate() == 1)
			print.preview();

	}
	/**
	 * 过滤掉没有选中的数据
	 * @param vos
	 * @return
	 */
	private RegisterVO[] filterVoByChoice(RegisterVO[] vos) {
		if (vos == null){
			return vos;
		}
		Vector<RegisterVO> vec = new Vector<RegisterVO>();
		for (int i = 0; i < vos.length; i++) {
			RegisterVO pjzbVO = vos[i];
			if (pjzbVO.getChoice() != null && pjzbVO.getChoice().booleanValue()){
				vec.add(pjzbVO);
			}
		}
		RegisterVO[] retVos = new RegisterVO[vec.size()];
		retVos = vec.toArray(retVos);
		return retVos;
	}

	protected TemplateInfo createTemplateInof(AbstractBillUI ui){
		TemplateInfo info = new TemplateInfo();
		info.setPk_Org(ClientInfo.getCorpPK());
		info.setCurrentCorpPk(ClientInfo.getCorpPK());
		info.setFunNode(ui.getModuleCode());
		info.setUserid(ClientInfo.getUserPK());
		info.setBusiType(getBillUI().getBusinessType());
		return info;
	}
}