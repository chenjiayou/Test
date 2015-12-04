package nc.bs.fbm.pub.action;

import nc.bs.fbm.pub.ActionQueryDAO;
import nc.bs.fbm.pub.CommonDAO;
import nc.bs.fbm.pub.OuterRelationDAO;
import nc.bs.fbm.pub.SerialNumGenerator;
import nc.bs.fbm.pub.account.IBusiFbmAccount;
import nc.bs.framework.common.InvocationInfoProxy;
import nc.bs.uap.lock.PKLock;
import nc.itf.fbm.action.IBusiAction;
import nc.vo.fbm.acceptbill.AcceptVO;
import nc.vo.fbm.consignbank.CollectionVO;
import nc.vo.fbm.discount.DiscountVO;
import nc.vo.fbm.endore.EndoreVO;
import nc.vo.fbm.impawn.ImpawnVO;
import nc.vo.fbm.pub.ActionVO;
import nc.vo.fbm.pub.BaseinfoVO;
import nc.vo.fbm.pub.BusiActionParamVO;
import nc.vo.fbm.pub.constant.FbmActionConstant;
import nc.vo.fbm.pub.constant.FbmBusConstant;
import nc.vo.fbm.register.RegisterVO;
import nc.vo.fbm.relief.ReliefVO;
import nc.vo.fbm.returnbill.ReturnVO;
import nc.vo.fbm.storage.StorageVO;
import nc.vo.pub.BusinessException;
import nc.vo.pub.CircularlyAccessibleValueObject;
import nc.vo.pub.SuperVO;
import nc.vo.pub.lang.UFBoolean;
import nc.vo.pub.lang.UFDate;

public abstract class AbstractBusiAction<K, T extends SuperVO> implements IBusiAction <K>, IBusiFbmAccount<T>  {

	protected CommonDAO dao = new CommonDAO();
	protected ActionQueryDAO actionDao = new ActionQueryDAO();
	protected OuterRelationDAO relDao = new OuterRelationDAO();

	public AbstractBusiAction() {
		super();
	}

	 public abstract BusiActionParamVO<T>[] buildParam(K data, String actioncode) throws BusinessException;

	public void doAction(K inparams, String actioncode,boolean isNesting) throws BusinessException {
		BusiActionParamVO<T>[] params = buildParam(inparams, actioncode);

		if (params != null && params.length > 0) {
			int len = params.length;
			String[] pk_baseinfo = new String[len];
			for (int i = 0; i < len; i++) {
				pk_baseinfo[i] = params[i].getPk_baseinfo();
				// 以下查询防止pk_baseinfo为空的情况
				if (pk_baseinfo[i] == null) {
					RegisterVO registerVO = (RegisterVO) dao.getBaseDAO()
							.retrieveByPK(RegisterVO.class,
									params[i].getPk_source());

					if (null == registerVO) {
						throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000229")/* @res"根据收票登记单主键取到的收票登记单为空，请刷新重试。"*/);
					}
					pk_baseinfo[i] = registerVO.getPk_baseinfo();
				}
			}
			String userid = InvocationInfoProxy.getInstance().getUserCode();
			try {
				if(!isNesting)//如果为嵌套动作不执行锁操作。
				{
					// 锁定票据基本信息PK数组
					lock(pk_baseinfo, userid);
				}
				// 前置动作
				beforeAction(params);

				// 执行合法性校验
				doCheckArray(params);

				if (!params[0].getActioncode()
						.equals(FbmActionConstant.ONAUDIT)) {// 非审核中的情况则执行后续动作
					String pk_action = null;
					for (int i = 0; i < len; i++) {
						// 更新动作表数据
						pk_action = updateActionTable(params[i]);
						// 维护票据内部账户
						dealAccount(pk_action, params[i]);
					}

					// 维护各业务动作特殊操作
					afterAction(params);
				}
			} finally {
				if(!isNesting)//如果是嵌套动作就不执行锁操作。
				{
					// 释放锁
					unLock(pk_baseinfo, userid);
				}
			}
		}

	}


	/**
	 *
	 * 填充业务字段 如操作人、操作日期和是否仅检查 xwq 2007-9-3
	 *
	 * @param
	 * @return
	 * @throws
	 * @since NC5.0
	 */
	protected void fillBusiField(CircularlyAccessibleValueObject vo,
			BusiActionParamVO<T> param, String actioncode) throws BusinessException {
		param.setActioncode(actioncode);
		if (actioncode.equals(FbmActionConstant.EDITSAVE)
				|| actioncode.equals(FbmActionConstant.SAVE)) {
			param.setActionperson((String) vo.getAttributeValue("voperatorid"));
			param.setActiondate((UFDate) vo.getAttributeValue("doperatedate"));
		}

		if (actioncode.equals(FbmActionConstant.ONAUDIT)
				|| actioncode.equals(FbmActionConstant.AUDIT)
				|| actioncode.equals(FbmActionConstant.DESTROY)
				|| actioncode.equals(FbmActionConstant.CANCELDESTROY)) {
			param.setActionperson((String) vo.getAttributeValue("vapproveid"));
			param.setActiondate((UFDate) vo.getAttributeValue("dapprovedate"));
		}
	}


//	/**
//	 * 执行票据动作 xwq 2007-8-14
//	 *
//	 * @param params
//	 *            动作参数VO数组
//	 * @return
//	 * @throws
//	 * @since NC5.0
//	 */
//	public void doAction(BusiActionParamVO[] params) throws BusinessException {
//		if (params != null && params.length > 0) {
//			int len = params.length;
//			String[] pk_baseinfo = new String[len];
//			for (int i = 0; i < len; i++) {
//				pk_baseinfo[i] = params[i].getPk_baseinfo();
//				// 以下查询防止pk_baseinfo为空的情况
//				if (pk_baseinfo[i] == null) {
//					RegisterVO registerVO = (RegisterVO) dao.getBaseDAO()
//							.retrieveByPK(RegisterVO.class,
//									params[i].getPk_source());
//
//					if (null == registerVO) {
//						throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000229")/* @res"根据收票登记单主键取到的收票登记单为空，请刷新重试。"*/);
//					}
//					pk_baseinfo[i] = registerVO.getPk_baseinfo();
//				}
//			}
//			String userid = InvocationInfoProxy.getInstance().getUserCode();
//			try {
//				// 锁定票据基本信息PK数组
//				lock(pk_baseinfo, userid);
//
//				// 前置动作
//				beforeAction(params);
//
//				// 执行合法性校验
//				doCheckArray(params);
//
//				if (!params[0].getActioncode()
//						.equals(FbmActionConstant.ONAUDIT)) {// 非审核中的情况则执行后续动作
//					String pk_action = null;
//					for (int i = 0; i < len; i++) {
//						// 更新动作表数据
//						pk_action = updateActionTable(params[i]);
//						// 维护票据内部账户
//						dealAccount(pk_action, params[i]);
//					}
//
//					// 维护各业务动作特殊操作
//					afterAction(params);
//				}
//			} finally {
//				// 释放锁
//				unLock(pk_baseinfo, userid);
//			}
//		}
//	}

	protected void lock(String[] pk_baseinfo, String userid) throws BusinessException {
		if(null == pk_baseinfo || pk_baseinfo.length == 0) {
			throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000230")/* @res"无法锁定当前票据，取得的票据基本信息为空。"*/);
		}
		if(null == userid || "".equals(userid.trim())) {
			throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000231")/* @res"无法锁定当前票据， 取得的当前用户为空。"*/);
		}


		boolean isLock = PKLock.getInstance().acquireBatchLock(pk_baseinfo, userid, "");
		if(!isLock){
			throw new BusinessException(new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000396")/* @res"加锁失败，票据正在被使用，请稍候再试。"*/));
		}
	}

	protected void unLock(String[] pk_baseinfo, String userid) throws BusinessException {
		if(null == pk_baseinfo || pk_baseinfo.length == 0) {
			throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000232")/* @res"无法解除当前票据的锁定，取得的票据基本信息为空。"*/);
		}
		if(null == userid || "".equals(userid.trim())) {
			throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000233")/* @res"无法解除当前票据的锁定，取得的当前用户为空。"*/);
		}
		PKLock.getInstance().releaseBatchLock(pk_baseinfo, userid, "");
	}

	/**
	 *
	 * 更新动作表数据 xwq 2007-8-14
	 *
	 * @param
	 * @return
	 * @throws
	 * @since NC5.0
	 */
	protected String updateActionTable(BusiActionParamVO<T> param)
			throws BusinessException {
		String actionCode = param.getActioncode();
		if (actionCode.equals(FbmActionConstant.AUDIT)
				|| actionCode.equals(FbmActionConstant.SAVE)
				|| actionCode.equals(FbmActionConstant.TRANSACT)
				|| actionCode.equals(FbmActionConstant.DISABLE)
				|| actionCode.equals(FbmActionConstant.IMPAWNBACK)
				|| actionCode.equals(FbmActionConstant.INPUT_SUCCESS)
				|| actionCode.equals(FbmActionConstant.OUTPUT_SUCCESS)
				|| actionCode.equals(FbmActionConstant.DESTROY)
				|| actionCode.equals(FbmActionConstant.CENTERUSE)) {// 单据保存和审核,办理和作废出库入库核销
			// 插入新动作
			ActionVO action = new ActionVO();
			action.setBeginstatus(getBeginStatus(param));
			action.setEndstatus(getEndStatus(param));
			action.setActioncode(param.getActioncode());
			action.setBilltype(param.getBilltype());
			action.setPk_bill(param.getPk_bill());// 设置业务单据PK
			action.setPk_source(param.getPk_source());
			action.setActionperson(param.getActionperson());
			action.setActiondate(param.getActiondate());
			action.setIsnew(new UFBoolean(true));// 设置当前动作为最新动作
			action.setPk_org(param.getPk_org());

			SerialNumGenerator genDMO = new SerialNumGenerator();
			action.setSerialnum(genDMO.getNextID(param.getPk_baseinfo()));
			action.setPk_baseinfo(param.getPk_baseinfo());
			action.setPk_corp(param.getPk_corp());
			// 所有动作设置为历史动作
			actionDao.disableHistoryAction(param.getPk_source());
			// 插入新动作
			String pk_action =  dao.getBaseDAO().insertVO(action);
			resetEndStatus(action,param.getPk_baseinfo());
			return pk_action;

		} else if (actionCode.equals(FbmActionConstant.DELETE)
				|| actionCode.equals(FbmActionConstant.CANCELAUDIT)
				|| actionCode.equals(FbmActionConstant.CANCELINPUT)
				|| actionCode.equals(FbmActionConstant.CANCELOUTPUT)
				|| actionCode.equals(FbmActionConstant.CANCELDESTROY)
				|| actionCode.equals(FbmActionConstant.CANCELTRANSACT)
				|| actionCode.equals(FbmActionConstant.CANCELCENTERUSER)
				|| actionCode.equals(FbmActionConstant.CANCELIMPAWNBACK)) {// 逆向操作
																			// ，如弃审、删除、取消出库、取消入库
			ActionVO lastAction = actionDao.queryNewestByPk_register(param
					.getPk_source());

			dao.getBaseDAO().deleteVO(lastAction);
			// 查询最后动作，设置为新动作
			ActionVO newAction = actionDao.queryNewestByPk_register(param
					.getPk_source());
			if (newAction != null) {
				newAction.setIsnew(new UFBoolean(true));
				dao.getBaseDAO().updateVO(newAction);
			}
			
			resetEndStatus(newAction,param.getPk_baseinfo());
			return lastAction.getPrimaryKey();
		}

		return null;
	}

	/**
	 *
	 * 更新账户明细数据
	 *
	 * 默认不操作账户数据，只在有账户变更的动作内覆盖该方法 xwq 2007-8-21
	 *
	 * @param
	 * @return
	 * @throws
	 * @since NC5.0
	 */
	public void dealAccount(String pk_action, BusiActionParamVO<T> param)
			throws BusinessException {
		// TODO Auto-generated method stub

	}

	/**
	 *
	 * 业务操作前有效性校验(数组) xwq 2007-9-3
	 *
	 * @param
	 * @return
	 * @throws
	 * @since NC5.0
	 */
	protected void doCheckArray(BusiActionParamVO<T> params[])
			throws BusinessException {
		int len = params.length;
		StringBuffer sb = new StringBuffer();
		boolean error = false;
		String tmp = null;
		String outTmp = null;

		for (int i = 0; i < len; i++) {
			if (!params[i].isForUpgrade()) {// 非升级使用时要校验
				tmp = doCheck(params[i]);
				outTmp = checkOuter(params[i]);
			}
			if (tmp != null) {
				error = true;
				sb.append(tmp);
				sb.append("\n");
			}
			if (outTmp != null) {
				error = true;
				sb.append(outTmp);
				sb.append("\n");
			}

		}

		if (error) {
			throw new BusinessException(sb.toString());
		}
	}

	/**
	 *
	 * 业务操作前有效性校验(单条) xwq 2007-8-14
	 *
	 * @param
	 * @return
	 * @throws
	 * @since NC5.0
	 */
	protected abstract String doCheck(BusiActionParamVO<T> param)
			throws BusinessException;

	/**
	 *
	 * 返回动作开始前的收票状态 xwq 2007-8-28
	 *
	 * @param param
	 *            TODO
	 * @param
	 * @return
	 * @throws BusinessException
	 *             TODO
	 * @throws
	 * @since NC5.0
	 */
	protected abstract String getBeginStatus(BusiActionParamVO<T> param)
			throws BusinessException;

	/**
	 *
	 * 返回动作结束后的收票状态 xwq 2007-8-28
	 *
	 * @param param
	 *            TODO
	 * @param
	 * @return
	 * @throws BusinessException
	 *             TODO
	 * @throws
	 * @since NC5.0
	 */
	protected abstract String getEndStatus(BusiActionParamVO<T> param)
			throws BusinessException;

	/**
	 *
	 * 入口操作 xwq 2007-9-4
	 *
	 * @param
	 * @return
	 * @throws
	 * @since NC5.0
	 */
	protected void  beforeAction(BusiActionParamVO<T>[] params)
			throws BusinessException {
		int len = params.length;
		for (int i = 0; i < len; i++) {

			// 查询业务单据VO
			T superVO = (T) dao.getBaseDAO().retrieveByPK(
					getClassByBillType(params[i].getBilltype()),
					params[i].getPk_bill());

			RegisterVO registerVO = (RegisterVO) dao.getBaseDAO().retrieveByPK(
					RegisterVO.class, params[i].getPk_source());

			if(null==registerVO){
				throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add","UPP3620ADD-000149")/*@res "无法得到票据信息，请确定票据是否存在！"*/);
			}

			BaseinfoVO baseinfoVO = (BaseinfoVO) dao.getBaseDAO().retrieveByPK(
					BaseinfoVO.class, registerVO.getPk_baseinfo());

			if (null == superVO) {
				throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000234")/* @res"取得的当前单据为空，请执行票据选择的刷新操作。"*/);
			}
			if (null == registerVO) {
				throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000235")/* @res"取得的当前的收票登记单为空，请执行票据选择的刷新操作。"*/);
			}
			if (null == baseinfoVO) {
				throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000236")/* @res"取得的当前票据基本信息为空，请执行票据选择的刷新操作。"*/);
			}

			params[i].setSuperVO(superVO);


			params[i].setRegisterVO(registerVO);
			params[i].setBaseinfoVO(baseinfoVO);

			if (params[i].getActioncode().equals(FbmActionConstant.EDITSAVE)) {
				// 删除原action
				ActionVO actionVO = actionDao
						.queryNewestActionByPk_bill(params[i].getPk_bill());
				if (actionVO != null) {
					dao.getBaseDAO().deleteVO(actionVO);
					// 删除原账户明细数据
					dao.delAccountDetailByActionPK(actionVO.getPk_action());
					// 查询最后动作，设置为新动作
					ActionVO newAction = actionDao
							.queryNewestByPk_register(actionVO.getPk_source());
					if (newAction != null) {
						newAction.setIsnew(new UFBoolean(true));
						dao.getBaseDAO().updateVO(newAction);
					}
					resetEndStatus(newAction,actionVO.getPk_baseinfo());
				}

				// 动作编码修改为新增保存
				params[i].setActioncode(FbmActionConstant.SAVE);
			}

			// 如果没有传入基本信息主键，那么查询得到
			if (params[i].getPk_baseinfo() == null) {
				params[i].setPk_baseinfo(baseinfoVO.getPrimaryKey());
				params[i].setMoneyy(baseinfoVO.getMoneyy());
			}

			ActionVO lastActionVO = actionDao.queryNewestByPk_baseinfo(
					registerVO.getPk_baseinfo(), params[i].getPk_corp());
			//ActionVO lastActionVO = actionDao.queryNewestByPk_register(params[i].getPk_source());

			params[i].setLastActionVO(lastActionVO);
		}
	}

	/**
	 * 检查外部单据关联合法性 默认实现为收付款标志为真
	 *
	 * @param param
	 * @return
	 * @throws BusinessException
	 */
	protected String checkOuter(BusiActionParamVO<T> param) throws BusinessException {
		RegisterVO registerVO = param.getRegisterVO();
		if (!registerVO.getSfflag().booleanValue()) {
			return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000237")/* @res"票据的收款或者付款没有完成，无法进行后续业务"*/;
		}
		return null;
	}

	/**
	 * 动作执行后方法
	 *
	 * @param param
	 * @throws BusinessException
	 */
	protected void afterAction(BusiActionParamVO<T>[] params) throws BusinessException {

	}

	/**
	 * 根据单据类型编码返回VO类名
	 *
	 * @param pk_billtypecode
	 * @return
	 */
	private Class getClassByBillType(String pk_billtypecode) {
		if (pk_billtypecode.equals(FbmBusConstant.BILLTYPE_GATHER)
				|| pk_billtypecode.equals(FbmBusConstant.BILLTYPE_INVOICE)) {
			return RegisterVO.class;
		} else if (pk_billtypecode.equals(FbmBusConstant.BILLTYPE_BANKKEEP)
				|| pk_billtypecode.equals(FbmBusConstant.BILLTYPE_BANKBACK)
				|| pk_billtypecode.equals(FbmBusConstant.BILLTYPE_INNERKEEP)
				|| pk_billtypecode.equals(FbmBusConstant.BILLTYPE_INNERBACK)) {
			return StorageVO.class;
		} else if (pk_billtypecode
				.equals(FbmBusConstant.BILLTYPE_COLLECTION_UNIT)) {
			return CollectionVO.class;
		} else if (pk_billtypecode.equals(FbmBusConstant.BILLTYPE_DISCOUNT_APP)
				|| pk_billtypecode
						.equals(FbmBusConstant.BILLTYPE_DISCOUNT_TRANSACT)) {
			return DiscountVO.class;
		} else if (pk_billtypecode.equals(FbmBusConstant.BILLTYPE_BILLPAY)) {
			return AcceptVO.class;
		} else if (pk_billtypecode.equals(FbmBusConstant.BILLTYPE_ENDORE)) {
			return EndoreVO.class;
		} else if (pk_billtypecode.equals(FbmBusConstant.BILLTYPE_IMPAWN)) {
			return ImpawnVO.class;
		} else if (pk_billtypecode.equals(FbmBusConstant.BILLTYPE_RETURN)) {
			return ReturnVO.class;
		} else if (FbmBusConstant.BILLTYPE_RELIEF.equals(pk_billtypecode)) {
			return ReliefVO.class;
		}
		return null;
	}

	/**
	 * 公用校验
	 *
	 * @param fbmbillno
	 * @param actionVO
	 * @return
	 * @throws BusinessException
	 */
	protected String commonCheck(BusiActionParamVO<T> param) throws BusinessException {
		ActionVO actionVO = param.getLastActionVO();
		String fbmbillno = param.getBaseinfoVO().getFbmbillno();
		if (actionVO == null) {
			return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000238")/* @res"找不到票据动作"*/;
		}
		// 校验状态
		if (!actionVO.getEndstatus().equals(getBeginStatus(param))) {
			return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000239")/* @res"票据"*/
					+ fbmbillno
					+ nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000240")/* @res"的状态是"*/
					+ BusiMessageTranslator.retriveStatusName(actionVO
							.getEndstatus()) + nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000241")/* @res",操作失败。"*/
					+ BusiMessageTranslator.translateAction(param);
		}
		String actionCode = param.getActioncode();// 插入新记录才需要校验时间先后
		if (actionCode.equals(FbmActionConstant.AUDIT)
				|| actionCode.equals(FbmActionConstant.SAVE)
				|| actionCode.equals(FbmActionConstant.TRANSACT)
				|| actionCode.equals(FbmActionConstant.DISABLE)
				|| actionCode.equals(FbmActionConstant.IMPAWNBACK)
				|| actionCode.equals(FbmActionConstant.INPUT_SUCCESS)
				|| actionCode.equals(FbmActionConstant.OUTPUT_SUCCESS)
				|| actionCode.equals(FbmActionConstant.DESTROY)
				|| actionCode.equals(FbmActionConstant.CENTERUSE)) {
			// added by wes show a message dialog when actiondate is null
			if (null == actionVO.getActiondate()) {
				return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000242")/* @res"前一动作日期为空，请确认。"*/;
			}
			// 校验业务日期
			if (actionVO.getActiondate().after(param.getActiondate())) {
				return  nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add","UPP3620ADD-000150")/*@res "当前业务日期:"*/+ param.getActiondate() +"," + nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000239")/* @res"票据"*/ + fbmbillno + nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000243")/* @res"的当前业务日期早于前一业务日期。"*/
						+ BusiMessageTranslator.translateAction(param);
			}
		}
		return null;
	}

	/**
	 * 更新收票登记单最新票据状态
	 * 更新票据基本信息最新票据状态
	 * @param actionvo
	 * @throws BusinessException
	 */
	private void resetEndStatus(ActionVO actionvo,String pk_baseinfo) throws BusinessException{
		if(actionvo == null){//出现收票登记或者付票登记被删除的情况
			if(pk_baseinfo!=null){
				ActionVO newAction = actionDao.queryNewestByPk_baseinfo(pk_baseinfo,null);
				if(newAction!=null){
					String sql = "update fbm_baseinfo set baseinfostatus = '"+newAction.getEndstatus()+"' where pk_baseinfo='"+pk_baseinfo+"'";
					dao.getBaseDAO().executeUpdate(sql);
				}
			}
			return;
		}
		String pk_register = actionvo.getPk_source();
		String sql = "update fbm_register set registerstatus = '"+actionvo.getEndstatus()+"' where pk_register = '"+pk_register+"'";
		dao.getBaseDAO().executeUpdate(sql);
		//String pk_baseinfo = actionvo.getPk_baseinfo();
		sql = "update fbm_baseinfo set baseinfostatus = '"+actionvo.getEndstatus()+"' where pk_baseinfo='"+pk_baseinfo+"'";
		dao.getBaseDAO().executeUpdate(sql);
	}

}