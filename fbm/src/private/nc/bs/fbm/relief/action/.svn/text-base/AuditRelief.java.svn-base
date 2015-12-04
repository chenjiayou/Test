package nc.bs.fbm.relief.action;

import java.util.List;

import javax.naming.NamingException;

import nc.bs.fbm.gather.GatherBillService;
import nc.bs.fbm.pub.CommonDAO;
import nc.bs.fbm.pub.action.BusiMessageTranslator;
import nc.bs.fbm.relief.BusiReliefUtil;
import nc.bs.framework.common.InvocationInfoProxy;
import nc.bs.trade.business.HYPubBO;
import nc.vo.fbm.account.AccountDetailVO;
import nc.vo.fbm.pub.ActionVO;
import nc.vo.fbm.pub.BaseinfoVO;
import nc.vo.fbm.pub.BusiActionParamVO;
import nc.vo.fbm.pub.constant.FbmActionConstant;
import nc.vo.fbm.pub.constant.FbmBusConstant;
import nc.vo.fbm.pub.constant.FbmStatusConstant;
import nc.vo.fbm.register.RegisterVO;
import nc.vo.fbm.relief.ReliefVO;
import nc.vo.pub.BusinessException;
import nc.vo.pub.lang.UFBoolean;
import nc.vo.trade.pub.HYBillVO;

/**
 *
 * 类功能说明： 调剂出库-审核 日期：2007-10-24 程序员： wues
 *
 */
public class AuditRelief<K extends HYBillVO,T extends ReliefVO> extends ActionRelief<K,T> {

	@Override
	protected String doCheck(BusiActionParamVO<T> param) throws BusinessException {
		String commonError = commonCheck(param);
		if (commonError != null)
			return commonError;

		ActionVO actionVO = param.getLastActionVO();
		// 校验票据动作
		if (!(actionVO.getActioncode().equals(FbmActionConstant.SAVE)
				&& actionVO.getBilltype()
						.equals(FbmBusConstant.BILLTYPE_RELIEF) && actionVO
				.getPk_bill().equals(param.getPk_bill()))) {
			return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000239")/* @res"票据"*/ + param.getBaseinfoVO().getFbmbillno()
					+ nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000256")/* @res"的前一操作必须是保存调剂出库单。"*/
					+ BusiMessageTranslator.translateAction(param);
		}
		return null;
	}

	/**
	 * 票的前置状态为在调剂
	 */
	protected String getBeginStatus(BusiActionParamVO<T> param)
			throws BusinessException {
//		if (ReliefUtil.isSelfBill(param)) {
//			// 自己的票,开始的时候为已调剂存放
//			return FbmStatusConstant.HAS_INNER_KEEP;
//		} else
			// 他人调剂的票，开始的时候为在调剂
			return FbmStatusConstant.ON_RELIEF;
	}

	/**
	 * 票的截止状态为已调剂
	 */
	protected String getEndStatus(BusiActionParamVO<T> param) throws BusinessException {
		// 根据调剂单位和收票登记单确定此收票登记单是否为自己的票据
		if (BusiReliefUtil.isSelfBill(param)) {
			// 自己的票,结束的时候为已内部存放
			return FbmStatusConstant.HAS_INNER_KEEP;
		} else
			// 旧他人的票，结束时候为已调剂
			return FbmStatusConstant.HAS_RELIEF;
	}

	/**
	 * 完成基本处理后进行的额外处理: 生成存放地点为"中心"的收票登记单
	 *
	 * @throws NamingException
	 */
	protected void afterAction(BusiActionParamVO<T>[] params) throws BusinessException {
		List<BusiActionParamVO>[] splitArr = BusiReliefUtil.splitParams(params);

		List<BusiActionParamVO> selfList = null == splitArr ? null : splitArr[0];// 拆分出自己的票据
		List<BusiActionParamVO> otherList = null == splitArr ? null : splitArr[1];// 拆分出他人调剂过来的票据

		// 根据ParamMap得到并处理自己的票：更新收票登记单的状态
		dealSelfBill(selfList);// 处理自己的票
		// 处理其他单位的票：增加新收票登记单
		dealOtherBill(otherList);
	}

	/**
	 * 处理自己的 票据
	 *
	 * @param params
	 * @throws BusinessException
	 */
	private void dealSelfBill(List<BusiActionParamVO> list)
			throws BusinessException {
		// 不需要处理，调剂回自己的票只将状态改为已内部存放即可
	}

	/**
	 * 处理从其他单位调剂过来的票据 生成新的收票登记单
	 *
	 * @param list
	 * @throws BusinessException
	 */
	private void dealOtherBill(List<BusiActionParamVO> list)
			throws BusinessException {
		if (null == list || list.size() == 0) {
			return;
		}
		GatherBillService gatherSrv = new GatherBillService();
		gatherSrv.saveRegisterVos(getRegisterVOS((BusiActionParamVO<T>[]) list
				.toArray(new BusiActionParamVO[0])));
	}

	/**
	 * 得到RegisterVO数组
	 *
	 * @param params
	 * @return
	 * @throws BusinessException
	 */
	private HYBillVO[] getRegisterVOS(BusiActionParamVO<T>[] params)
			throws BusinessException {
		if (null == params || params.length == 0) {
			return new HYBillVO[0];
		}
		HYBillVO[] vos = new HYBillVO[params.length];
		for (int i = 0; i < vos.length; i++) {
			vos[i] = new HYBillVO();
			// 根据收票登记的主键和票据主键得到收票登记的VO进行保存
			vos[i].setParentVO(getNewVO(params[i]));
		}
		return vos;
	}

	/**
	 * 组装后台需要的RegisterVO
	 *
	 * @param param
	 * @param oldVO
	 * @return
	 * @throws BusinessException
	 */
	private RegisterVO getNewVO(BusiActionParamVO<T> param) throws BusinessException {
		RegisterVO vo = new RegisterVO();

		RegisterVO oldVO = param.getRegisterVO();
		ReliefVO reliefVO = param.getSuperVO();

		vo.setPk_baseinfo(param.getPk_baseinfo());
		vo.setPk_billtypecode(FbmBusConstant.BILLTYPE_GATHER);
		vo.setGatherdate(param.getActiondate());
		vo.setGathertype(FbmBusConstant.GATHER_TYPE_RELIEF);// 调剂来源
		vo.setPk_source(param.getPk_bill());// 来源单据：调剂出库单
		vo.setPaybillunit(dao.queryCustByCorp(InvocationInfoProxy.getInstance()
				.getCorpCode()));// 付票单位为当前登陆的公司
		vo.setHoldunit(reliefVO.getReliefunit());// 持票单位
		vo.setMoneyy(oldVO.getMoneyy());
		vo.setFrate(oldVO.getFrate());
		vo.setBrate(oldVO.getBrate());
		vo.setMoneyb(oldVO.getMoneyb());
		vo.setMoneyf(oldVO.getMoneyf());
		vo.setKeepunit(dao.queryCustByCorp(InvocationInfoProxy.getInstance()
				.getCorpCode()));// 持票单位同付票单位，取中心
		vo.setIsnewbill(UFBoolean.FALSE);
		vo.setSfflag(UFBoolean.FALSE);
		vo.setIsverify(UFBoolean.FALSE);
		vo.setNote("");
		vo.setPk_corp(dao.queryCorpByCust(reliefVO.getReliefunit()));// 根据客商取公司
		vo.setVbillno(new HYPubBO().getBillNo(FbmBusConstant.BILLTYPE_GATHER,
				vo.getPk_corp(), null, null));
		/** ************************************************ */
		vo.setSfflag(UFBoolean.TRUE);// 收付款标志，默认打上true
		/** ************************************************ */
		vo.setVoperatorid(param.getActionperson());
		vo.setDoperatedate(param.getActiondate());
		vo.setVapproveid(param.getActionperson());
		vo.setDapprovedate(param.getActiondate());
		vo.setVapprovenote("");

		return vo;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see nc.bs.fbm.pub.action.AbstractAction#dealAccount(java.lang.String,
	 *      nc.vo.fbm.pub.ActionParamVO)
	 */
	@Override
	public void dealAccount(String pk_action, BusiActionParamVO<T> param)
			throws BusinessException {
		CommonDAO dao = new CommonDAO();
		BaseinfoVO baseinfoVO = dao.queryBaseinfoByPK(param.getPk_baseinfo());
		/**
		 * 将A单位票据调剂给C单位 生成三条虚拟账户记录： A单位调剂存放户减少 C单位内部存放户增加 A和C的清算户
		 */
		boolean isSelf = false;
		AccountDetailVO[] vos = null;
		if (BusiReliefUtil.isSelfBill(param)) {
			vos = new AccountDetailVO[2];
			isSelf = true;
		} else {
			vos = new AccountDetailVO[4];
		}

		// A调剂存放户减少
		vos[0] = new AccountDetailVO();
		vos[0].setPk_org(param.getUnit_a());// 调出的单位
		vos[0].setMoneyy(baseinfoVO.getMoneyy().multiply(-1));
		vos[0].setPk_baseinfo(param.getPk_baseinfo());
		vos[0].setPk_action(pk_action);
		vos[0].setPk_type(FbmBusConstant.ACCOUNT_TYPE_RELIEF);// 中心调剂户减少
		vos[0].setReason(FbmBusConstant.ACCOUNT_REASON_RELIEF_USE);
		vos[0].setIsliquid(UFBoolean.FALSE);// 是否清算，默认false
		vos[0].setLiquidationdate(baseinfoVO.getEnddate());// 清算日期
		vos[0].setPk_register(param.getRegisterVO().getPrimaryKey());// 设置pk_register

		// C内部存放户增加
		vos[1] = new AccountDetailVO();
		vos[1].setPk_org(param.getUnit_b());// 调入的单位
		vos[1].setMoneyy(baseinfoVO.getMoneyy());
		vos[1].setPk_baseinfo(param.getPk_baseinfo());
		vos[1].setPk_action(pk_action);
		vos[1].setPk_type(FbmBusConstant.ACCOUNT_TYPE_CENTER);// 中心存放户增加
		vos[1].setReason(FbmBusConstant.ACCOUNT_REASON_RELIEF_USE);
		vos[1].setIsliquid(UFBoolean.FALSE);// 是否清算，默认false
		vos[1].setLiquidationdate(baseinfoVO.getEnddate());// 清算日期
		vos[1].setPk_register(param.getRegisterVO().getPrimaryKey());// 设置pk_register

		if (!isSelf) {
			// A的清算户减少
			vos[2] = new AccountDetailVO();
			vos[2].setPk_org(param.getRegisterVO().getHoldunit());// 调出的单位减少
			vos[2].setMoneyy(baseinfoVO.getMoneyy());
			vos[2].setPk_baseinfo(param.getPk_baseinfo());
			vos[2].setPk_action(pk_action);
			vos[2].setPk_type(FbmBusConstant.ACCOUNT_TYPE_LIQUID);// A单位清算户减少
			vos[2].setReason(FbmBusConstant.ACCOUNT_REASON_RELIEF_USE);
			vos[2].setIsliquid(UFBoolean.FALSE);// 是否清算，默认false
			vos[2].setLiquidationdate(baseinfoVO.getEnddate());// 清算日期
			vos[2].setPk_register(param.getRegisterVO().getPrimaryKey());// 设置pk_register

			// C的清算户增加
			vos[3] = new AccountDetailVO();
			vos[3].setPk_org(param.getUnit_b());// 调到的单位增加
			vos[3].setMoneyy(baseinfoVO.getMoneyy().multiply(-1));
			vos[3].setPk_baseinfo(param.getPk_baseinfo());
			vos[3].setPk_action(pk_action);
			vos[3].setPk_type(FbmBusConstant.ACCOUNT_TYPE_LIQUID);// C单位清算户增加
			vos[3].setReason(FbmBusConstant.ACCOUNT_REASON_RELIEF_USE);
			vos[3].setIsliquid(UFBoolean.FALSE);// 是否清算，默认false
			vos[3].setLiquidationdate(baseinfoVO.getEnddate());// 清算日期
			vos[3].setPk_register(param.getRegisterVO().getPrimaryKey());// 设置pk_register
		}

		dao.getBaseDAO().insertVOArray(vos);
	}
}