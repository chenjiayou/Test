package nc.impl.fbm.cmp;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import nc.bs.dao.BaseDAO;
import nc.bs.fbm.out.ArapBillDataAdapter;
import nc.bs.fbm.out.IBillValidator;
import nc.bs.fbm.pub.BaseInfoBO;
import nc.bs.fbm.pub.CommonDAO;
import nc.bs.fbm.pub.OuterRelationDAO;
import nc.bs.framework.common.InvocationInfoProxy;
import nc.bs.framework.common.NCLocator;
import nc.bs.uap.lock.PKLock;
import nc.itf.cdm.util.CommonUtil;
import nc.itf.uap.bd.notetype.INotetypeQry;
import nc.vo.bd.notetype.INotetypeConst;
import nc.vo.bd.notetype.NotetypeVO;
import nc.vo.cmp.settlement.SettlementAggVO;
import nc.vo.cmp.settlement.SettlementBodyVO;
import nc.vo.cmp.settlement.SettlementHeadVO;
import nc.vo.fbm.outer.ArapBillParamVO;
import nc.vo.fbm.outer.OuterVO;
import nc.vo.fbm.proxy.OuterProxy;
import nc.vo.fbm.pub.BaseinfoVO;
import nc.vo.fbm.pub.constant.FbmBusConstant;
import nc.vo.pub.BusinessException;
import nc.vo.pub.billtype.BilltypeVO;

/**
 * 主要为现金平台提供的辅助类
 *
 * 提供票据中的统一入口
 *
 * @author wues
 */
public abstract class AbstractCommandProcess {

	private CommonDAO dao = null;

	private OuterRelationDAO relaDAO = null;

	/**
	 * notetype查询类
	 */
	private INotetypeQry noteTypeServ = null;

	/**
	 * 判断表体记录是否需要走票据接口
	 *
	 * @param vo
	 * @return
	 * @throws BusinessException
	 */
	protected boolean isNeedFbmDeal(SettlementBodyVO vo, SettlementHeadVO headVO,String command)
			throws BusinessException {

		String pk_notetype = vo.getPk_notetype();// 取得票据类型

		if (null == pk_notetype || "".equals(pk_notetype.trim())) {
			return false;
		}

		NotetypeVO noteTypeVO = getNoteTypeService().queryNotetypeVOByPk(
				pk_notetype);
		if (null != noteTypeVO) {
			//不为商业汇票直接返回false
			if (noteTypeVO.getNoteclass() != INotetypeConst.NOTECLASS_COMMERCIALDRAFT) {
				return false;
			}
			if (CommonUtil.isNull(vo.getNotenumber())) {
				return false;
			}
			boolean isExistNoteNo = true;
			BaseInfoBO bo = new BaseInfoBO();
			BaseinfoVO tempVO = bo.queryBaseInfoByFbmbillno(vo.getNotenumber());
			if (tempVO == null || tempVO.getFbmbillno() == null) {
				isExistNoteNo = false;
			}
			//xwq 2009.6.19 如果动作为删除或者弃审时不检查集成应用参数
			if(ArapBillDataAdapter.ACTION_DEL.equals(command) || ArapBillDataAdapter.ACTION_UNEFFECT.equals(command)){
				if (!isExistNoteNo ) {// 商业汇票且票据编号不存在
					throw new BusinessException("当前票据类型为商业汇票，且票据编号在资金票据系统中不存在");
				}
			}else {
				if (!isExistNoteNo || "N".equals(getFbmInitParm())) {// 商业汇票且票据编号不存在或非集成应用抛异常
					throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl
							.getNCLangRes().getStrByID("3620add",
									"UPP3620ADD-000195")/*
														 * @res
														 * "当前票据类型为商业汇票，且票据编号在资金票据系统中不存在或者票据业务集成应用没有启用。"
														 */);
				}
			}
			//判断单据大类是否合法(收款单、付款单、收款结算单、付款结算单)
			validBillType(vo, headVO);

			if (isExistNoteNo) {// 当前票据类型大类为商业汇票且票据编号存在
				return true;
			}
		}
		return false;

	}

	/**
	 * 判断单据类型是否合法，如果类型非sk，fk，sj，fj直接抛异常
	 * @param vo
	 * @param headVO
	 * @return
	 * @throws BusinessException
	 */
	private void validBillType(SettlementBodyVO vo, SettlementHeadVO headVO) throws BusinessException {
		String djdl = getDjdl(headVO);
		if (!CMPConstant.getDealBillType().contains(djdl)) {//不在需要处理的单据类型之内
			throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcode112","UPPFBMCODE112-000032")/*@res "当前引用了资金票据，而单据非(收款结算单、付款结算单、收款单、付款单)，请检查。"*/);
		}
	}


	/**
	 * 取收付报和票据是否集成应用参数
	 *
	 * @return
	 * @throws BusinessException
	 */
	private String getFbmInitParm() throws BusinessException {
		String pk_corp = InvocationInfoProxy.getInstance().getCorpCode();

		return getISysInitQry().queryByParaCode(pk_corp, "FBM005").getValue();
	}

	private nc.itf.uap.busibean.ISysInitQry getISysInitQry() {
		return (nc.itf.uap.busibean.ISysInitQry) NCLocator.getInstance()
				.lookup(nc.itf.uap.busibean.ISysInitQry.class);
	}

	/**
	 * 非新增操作，查询此单据，判断如果为退票生成打上退票生成标志
	 *
	 * @param vo
	 * @throws BusinessException
	 */
	protected void noAddProcess(SettlementAggVO vo,String command) throws BusinessException {
		
		//首先校验，有一条不需要fbm处理则直接返回，不进行处理
		SettlementBodyVO[] vos = (SettlementBodyVO[])vo.getChildrenVO();
		for (int i = 0; !CommonUtil.isNull(vos) && i < vos.length; i++) {
			if (!isNeedFbmDeal(vos[i], (SettlementHeadVO)vo.getParentVO(),command)) {
				return;
			}
		}
		//否则判断是否为退票生成单据
		OuterVO[] outerVos = getOutersByBusiPk(vo);
		if (!CommonUtil.isNull(outerVos)) {
			if (outerVos[0].getPk_billtypecode().equals(
					FbmBusConstant.BILLTYPE_RETURN)) {
				vo.setReturnBill(true);
			}
		}
	}

	/**
	 * 升级处理，编辑/审核时根据单据pk到fbm_outer表中查询相应关联关系，无记录直接抛异常
	 *
	 * @param vo
	 * @throws BusinessException
	 */
	protected void updateDataDealer(SettlementAggVO vo,String command)
			throws BusinessException {
		SettlementBodyVO[] vos = (SettlementBodyVO[])vo.getChildrenVO();
		OuterVO[] outerVos = null;
		for (int i = 0; !CommonUtil.isNull(vos) && i < vos.length; i++) {
			if (isNeedFbmDeal(vos[i], (SettlementHeadVO)vo.getParentVO(),command)) {// 当前vo需要fbm处理
				outerVos = getOutersByBusiPk(vo);
				boolean isUpdate = CommonUtil.isNull(outerVos) && !vo.isReturnBill();
				if (isUpdate) {
					throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcode112","UPPFBMCODE112-000033")/*@res "当前操作的单据为升级数据，不允许进行编辑和审核操作。"*/);
				}
			}
		}
	}

	private OuterVO[] getOutersByBusiPk(SettlementAggVO vo)
			throws BusinessException {
		if (null == vo) {
			CmpHelper.debug(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes()
					.getStrByID("3620add", "UPP3620ADD-000196")/*
																 * @res
																 * "===========================vo为空==========="
																 */);
			return null;
		}
		SettlementHeadVO headVO = (SettlementHeadVO) vo.getParentVO();
		OuterRelationDAO relDao = new OuterRelationDAO();
		OuterVO[] outerVos = relDao.queryByPk_h(headVO.getPrimaryKey());
		return outerVos;
	}

	/**
	 * 采用命令方式进行抽象处理
	 *
	 * @param vo
	 * @param command
	 *            validation处理类
	 * @throws BusinessException
	 */
	protected void doProcess(SettlementAggVO info, String validclass,String command)
			throws BusinessException {
		CmpHelper.debug(info, nc.vo.ml.NCLangRes4VoTransl.getNCLangRes()
				.getStrByID("3620add", "UPP3620ADD-000197")/*
															 * @res
															 * "调用doProcess,command命令为"
															 */
				+ validclass + "\n", "");
		if (null == info) {
			CmpHelper.debug(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes()
					.getStrByID("3620add", "UPP3620ADD-000198")/*
																 * @res
																 * "========info为空============"
																 */);
			return;
		}
		CmpHelper.debug(info, nc.vo.ml.NCLangRes4VoTransl.getNCLangRes()
				.getStrByID("3620add", "UPP3620ADD-000197")/*
															 * @res
															 * "调用doProcess,command命令为"
															 */
				+ validclass + "\n", "");

		SettlementHeadVO vo = (SettlementHeadVO) info.getParentVO();

		if (null == validclass || "".equals(validclass.trim())) {// validation校验类为空直接返回
			return;
		}

		// 实例化类
		IBillValidator val = getValidator(validclass);

		if (!isFBMEnabled(vo.getPk_corp())) {// 当前公司票据未启用
			return;
		}

		// 进行校验处理
		doProcess(info, val,command);

		CmpHelper.debug(info, nc.vo.ml.NCLangRes4VoTransl.getNCLangRes()
				.getStrByID("3620add", "UPP3620ADD-000199")/*
															 * @res
															 * "调用doProcess方法后\n"
															 */, "");

	}

	/**
	 * 根据validator和结算vo进行相关处理
	 *
	 * @param vo
	 * @param val
	 * @throws BusinessException
	 */

	private void doProcess(SettlementAggVO vo, IBillValidator val,String command)
			throws BusinessException {
		if (null == vo) {
			return;
		}
		SettlementBodyVO[] vos = (SettlementBodyVO[]) vo.getChildrenVO();
		if (CommonUtil.isNull(vos)) {
			return;
		}
		for (int i = 0; i < vos.length; i++) {
			CmpHelper.debug(vos[i], nc.vo.ml.NCLangRes4VoTransl.getNCLangRes()
					.getStrByID("3620add", "UPP3620ADD-000200")/*
																 * @res
																 * "循环表体每一行，进行票据校验。\n"
																 */, "");
			if (!isNeedFbmDeal(vos[i], (SettlementHeadVO)vo.getParentVO(),command)) {
				continue;
			}
			CmpHelper.debug(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes()
					.getStrByID("3620add", "UPP3620ADD-000201")/*
																 * @res
																 * "++++++++++++++++进行参数转换SettlementBodyVO到ArapBillParamVO+++++++++++++"
																 */);
			ArapBillParamVO arapParam = CMPBillDataAdapter.buildBillParam(vo,
					vos[i], i);
			CmpHelper.debug(arapParam, nc.vo.ml.NCLangRes4VoTransl
					.getNCLangRes().getStrByID("3620add", "UPP3620ADD-000202")/*
																				 * @res
																				 * "：：转换后参数为：："
																				 */, nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID(
					"3620add", "UPP3620ADD-000203")/*
													 * @res "稍后进行相关业务校验：："
													 */);
			PKLock.getInstance().acquireBatchLock(new String[]{arapParam.getFbmbillno()}, "", "");
			val.checkBaseinfo(arapParam);
			val.beforeAction(arapParam);
			val.doCheck(arapParam);
			val.afterAction(arapParam);
			PKLock.getInstance().releaseBatchLock(new String[]{arapParam.getFbmbillno()}, "", "");
		}

	}

	/**
	 * 根据票据类型pk判断是商业汇票还是银行汇票
	 *
	 * @param pk_notetype
	 * @return
	 */
	// private boolean isBusiNoteType(String pk_notetype) throws
	// BusinessException {
	// if (CommonUtil.isNull(pk_notetype)) {
	// return false;
	// }
	// INotetypeQry serv = getNoteTypeQryService();
	// NotetypeVO qryvo = serv.queryNotetypeVOByPk(pk_notetype);
	//
	// // 表示当前票据类型为商业汇票大类
	// if (INotetypeConst.NOTECLASS_COMMERCIALDRAFT == qryvo.getNoteclass()) {
	// return true;
	// }
	//
	// return false;
	// }
	/**
	 * 票据类型查询服务类
	 *
	 * @return
	 */
	// private INotetypeQry getNoteTypeQryService() {
	// INotetypeQry qry = NCLocator.getInstance().lookup(INotetypeQry.class);
	// return qry;
	// }
	/**
	 * 实例化相应的Validator类
	 *
	 * @param cls
	 * @return
	 * @throws BusinessException
	 */
	private IBillValidator getValidator(String cls) throws BusinessException {
		IBillValidator validator = null;
		try {
			validator = (IBillValidator) Class.forName(cls).newInstance();
		} catch (Exception e) {
			CmpHelper.debug(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes()
					.getStrByID("3620add", "UPP3620ADD-000204")/*
																 * @res
																 * "实例化具体Validator类【"
																 */
					+ cls
					+ nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID(
							"3620add", "UPP3620ADD-000205")/*
															 * @res "】时异常，请校验。"
															 */);
		}
		return validator;
	}

	/**
	 * 设置应收应付属性 里面赋值应收应付返回
	 *
	 * @param param
	 * @return
	 */
	protected void addOrient(SettlementAggVO vo,String command) throws BusinessException {
		if (vo == null) {
			return;
		}

		SettlementHeadVO headVO = (SettlementHeadVO) vo.getParentVO();
		SettlementBodyVO[] items = (SettlementBodyVO[]) vo.getChildrenVO();

		CmpHelper.debug(vo, nc.vo.ml.NCLangRes4VoTransl.getNCLangRes()
				.getStrByID("3620add", "UPP3620ADD-000206")/*
															 * @res
															 * "调用方法addOrient\n"
															 */, "");

		String billtype = headVO.getPk_tradetype();// 单据类型
		// 排除应收单和应付单
		if (CMPConstant.BILLTYPE_YS.equals(billtype)
				|| CMPConstant.BILLTYPE_YF.equals(billtype)) {
			return;
		}
		if (CommonUtil.isNull(items)) {
			return;
		}

		List<String> fbmbillnoList = new ArrayList<String>();
		for (int i = 0; i < items.length; i++) {
			if (isNeedFbmDeal(items[i], (SettlementHeadVO)vo.getParentVO(),command)) {
				fbmbillnoList.add(items[i].getNotenumber());
			}
		}

		BaseInfoBO bo = new BaseInfoBO();
		if (fbmbillnoList != null && fbmbillnoList.size() > 0) {
			Map<String, String> map = bo.queryOrientByBillno(fbmbillnoList
					.toArray(new String[0]));
			for (int i = 0; i < items.length; i++) {
				if (isNeedFbmDeal(items[i],(SettlementHeadVO)vo.getParentVO(),command)) {
					String rp = map.get(items[i].getNotenumber());
					items[i].setNotedirection(getRPInt(rp));
				}
			}
		}
		CmpHelper.debug(vo, nc.vo.ml.NCLangRes4VoTransl.getNCLangRes()
				.getStrByID("3620add", "UPP3620ADD-000207")/*
															 * @res
															 * "调用方法addOrient结束后结果VO\n"
															 */, "");
	}

	private int getRPInt(String rp) throws BusinessException {
		if ("ar".equals(rp)) {
			return 0;
		}
		if ("ap".equals(rp)) {
			return 1;
		}
		throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes()
				.getStrByID("3620add", "UPP3620ADD-000208")/*
															 * @res
															 * "未识别的票据收付方向："
															 */
				+ rp);
	}

	/**
	 * 判断相应公司是否启用了票据产品
	 *
	 * @param pk_corp
	 * @return
	 * @throws BusinessException
	 */
	protected boolean isFBMEnabled(String pk_corp) throws BusinessException {
		CommonDAO comDao = new CommonDAO();
		boolean fbmEnable = comDao.productEnableByCorp(pk_corp,
				FbmBusConstant.SYSCODE_FBM);
		if (fbmEnable) {
			return true;
		}
		return false;
	}

	protected CommonDAO getDAO() {
		if (null == dao) {
			dao = new CommonDAO();
		}
		return dao;
	}

	protected OuterRelationDAO getRelaDAO() {
		if (null == relaDAO) {
			relaDAO = new OuterRelationDAO();
		}
		return relaDAO;
	}

	/**
	 * 取得票据类型服务类
	 *
	 * @return
	 */
	protected INotetypeQry getNoteTypeService() {
		if (noteTypeServ == null) {
			noteTypeServ = OuterProxy.getNoteTypeService();
		}
		return noteTypeServ;
	}

	/**
	 * 判断是否为红冲单据，如果为红冲单据且有一行引用了资金票据直接抛异常
	 *
	 * @param vo
	 * @throws BusinessException
	 */
	protected void dealRedBill(SettlementAggVO vo,String command) throws BusinessException {
		if (vo.isReturnBill()) {//退票标志优先，退票标志未打上说明是直接做红冲处理
			return;
		}

		SettlementBodyVO[] vos = (SettlementBodyVO[]) vo.getChildrenVO();
		for (int i = 0; null != vos && i < vos.length; i++) {
			if (isNeedFbmDeal(vos[i], (SettlementHeadVO)vo.getParentVO(), command) && vo.isReset()) {
				throw new BusinessException(
						nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcode112","UPPFBMCODE112-000034")/*@res "当前单据引用了资金票据不允许直接做红冲处理，需要在资金票据的集中退票节点做退票转出。"*/);
			}
		}
	}

	/**
	 * 根据指定的billtype取相应的parentBillType
	 *
	 * @param billtype
	 * @return
	 */
	protected BilltypeVO getBillTypeVO(String billtype)
			throws BusinessException {

		ArrayList vo = (ArrayList) new BaseDAO().retrieveByClause(
				BilltypeVO.class, " pk_billtypecode='" + billtype + "' ");

		if (vo == null || vo.size() == 0) {
			throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcode112","UPPFBMCODE112-000035")/*@res "根据单据类型编码："*/ + billtype + nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcode112","UPPFBMCODE112-000036")/*@res "，无法查询到相应的单据类型信息(BilltypeVO)"*/);
		}

		return (BilltypeVO) vo.get(0);

	}

	/**
	 * 取指定单据大类
	 * @param vo
	 * @return
	 * @throws BusinessException
	 */
	protected String getDjdl(SettlementHeadVO vo) throws BusinessException {
		BilltypeVO billTypeVo = getBillTypeVO(vo.getPk_tradetype());

		String parentBillType = billTypeVo.getParentbilltype();// 得到单据大类
		return parentBillType;
	}

}