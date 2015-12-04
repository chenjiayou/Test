package nc.bs.fbm.plan;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import nc.bs.dao.BaseDAO;
import nc.bs.fbm.pub.ActionQueryDAO;
import nc.bs.trade.business.HYPubBO;
import nc.jdbc.framework.processor.ArrayProcessor;
import nc.vo.fbm.acceptbill.AcceptVO;
import nc.vo.fbm.consignbank.CollectionVO;
import nc.vo.fbm.discount.DiscountVO;
import nc.vo.fbm.outer.OuterVO;
import nc.vo.fbm.pub.ActionVO;
import nc.vo.fbm.pub.BaseinfoVO;
import nc.vo.fbm.pub.FBMProxy;
import nc.vo.fbm.pub.constant.FbmActionConstant;
import nc.vo.fbm.pub.constant.FbmBusConstant;
import nc.vo.fbm.register.RegisterVO;
import nc.vo.fbm.returnbill.ReturnBVO;
import nc.vo.fbm.returnbill.ReturnVO;
import nc.vo.fbm.storage.StorageVO;
import nc.vo.fp.pub.IOBudgetQueryVO;
import nc.vo.fp.pub.IPlanConst;
import nc.vo.pub.BusinessException;
import nc.vo.pub.SuperVO;
import nc.vo.pub.lang.UFBoolean;
import nc.vo.pub.lang.UFDouble;

/**
 * 单据写计划执行数
 * 
 * @author xwq
 * 
 *         2008-9-17
 */
public class FbmBill2PlanAdapter {

	public static IOBudgetQueryVO[] bill2PlanVO(SuperVO vo, String actioncode)
			throws BusinessException {
		if (vo instanceof AcceptVO) {
			return accept2PlanVO((AcceptVO) vo);
		} else if (vo instanceof RegisterVO) {
			if (((RegisterVO) vo).getPk_billtypecode().equals(FbmBusConstant.BILLTYPE_GATHER)) {
				return gather2PlanVO((RegisterVO) vo);
			}
			if ("DESTROY".equals(actioncode)
					|| "CANCELDESTROY".equals(actioncode)) {
				return invoice2Destory((RegisterVO) vo, actioncode);
			}
			return invoice2PlanVO((RegisterVO) vo, actioncode);
		} else if (vo instanceof DiscountVO) {
			return discount2PlanVO((DiscountVO) vo);
		} else if (vo instanceof CollectionVO) {
			return collection2PlanVO((CollectionVO) vo);
		} else if (vo instanceof StorageVO) {
			return storage2PlanVO((StorageVO) vo);
		} else if (vo instanceof ReturnVO) {
			if (FbmBusConstant.BACK_TYPE_UNISTORAGE_UNIT.equals(vo.getAttributeValue(ReturnVO.RETURNTYPE))) {
				return returnBill2PlanVO((ReturnVO) vo);
			} else if (vo.getAttributeValue(ReturnVO.RETURNTYPE).equals(FbmBusConstant.BACK_TYPE_GATHER)
					|| vo.getAttributeValue(ReturnVO.RETURNTYPE).equals(FbmBusConstant.BACK_TYPE_DISABLE)) {
				return gather2PlanVO((ReturnVO) vo);
			} else if (vo.getAttributeValue(ReturnVO.RETURNTYPE).equals(FbmBusConstant.BACK_TYPE_INVOICE)) {
				return invoice2PlanVO((ReturnVO) vo);
			} else { // 临时解决决 背书退票，中心退出类型写计划报错
				return null;
			}
		}

		throw new BusinessException(
				nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add", "UPP3620ADD-000148")/*
																									 * @res
																									 * "单据类型错误，无法写资金计划"
																									 */);

	}

	/**
	 * 付票登记核销计划VO
	 * 
	 * @param regvo
	 * @return
	 * @throws BusinessException
	 */
	private static IOBudgetQueryVO[] invoice2Destory(RegisterVO regvo,
			String actioncode) throws BusinessException {
		IOBudgetQueryVO[] rvo = new IOBudgetQueryVO[1];
		initIOBugetVO(rvo, regvo);
		BaseDAO dao = new BaseDAO();
		BaseinfoVO baseinfovo = (BaseinfoVO) dao.retrieveByPK(BaseinfoVO.class, regvo.getPk_baseinfo());

		ActionQueryDAO actionDAO = new ActionQueryDAO();
		ActionVO newActionVO = actionDAO.queryNewestByPk_baseinfo(regvo.getPk_baseinfo(), regvo.getPk_corp());

		// 只有付票退票的审核才要维护计划
		if (actioncode.equals("DESTROY")) {
			if (newActionVO.getActioncode().equals(FbmActionConstant.AUDIT)
					&& newActionVO.getBilltype().equals(FbmBusConstant.BILLTYPE_RETURN)) {
				// 如果没有在fbm_outer表里，说明付票没有走收付报，而是直接已付票打勾，返回
				// SuperVO[] outers =
				// FBMProxy.getUifService().queryByCondition(OuterVO.class,
				// "isnull(dr,0)=0 and pk_billtypecode='36GL' and pk_busibill='"
				// + regvo.getPrimaryKey()
				// + "'");
				// if (outers != null && outers.length > 0) {
				// return null;
				// }

				// 开票收入项目
				rvo[0].setPk_selfcorp(regvo.getPk_corp());
				rvo[0].setCheckplandate(regvo.getVerifydate());
				rvo[0].setAmount(baseinfovo.getMoneyy().multiply(-1));// 票据金额取负值
				rvo[0].setPk_currtype(baseinfovo.getPk_curr());
				rvo[0].setPk_costsubj(regvo.getInvoiceitem());
				rvo[0].setPk_planitem(regvo.getInvoiceplanitem());
				rvo[0].setIoflag(IPlanConst.IOFLAG_INCOME);// 收入
				rvo[0].setMemo(regvo.getNote());
				return rvo;
			} else if (newActionVO.getActioncode().equals(FbmActionConstant.DESTROY)
					&& newActionVO.getBilltype().equals(FbmBusConstant.BILLTYPE_INVOICE)) {
				// 如果没有在fbm_outer表里，说明付票没有走收付报，而是直接已付票打勾，返回
				SuperVO[] outers = FBMProxy.getUifService().queryByCondition(OuterVO.class, "isnull(dr,0)=0 and pk_billtypecode='36GL' and pk_busibill='"
						+ regvo.getPrimaryKey()
						+ "'");
				if (outers != null && outers.length > 0) {
					return null;
				}

				// 开票收入项目
				rvo[0].setPk_selfcorp(regvo.getPk_corp());
				rvo[0].setCheckplandate(regvo.getVerifydate());
				rvo[0].setAmount(baseinfovo.getMoneyy().multiply(-1));// 票据金额取负值
				rvo[0].setPk_currtype(baseinfovo.getPk_curr());
				rvo[0].setPk_costsubj(regvo.getInvoiceitem());
				rvo[0].setPk_planitem(regvo.getInvoiceplanitem());
				rvo[0].setIoflag(IPlanConst.IOFLAG_INCOME);// 收入
				rvo[0].setMemo(regvo.getNote());
				return rvo;
				
			} else if (newActionVO.getActioncode().equals(FbmActionConstant.DESTROY)
					&& newActionVO.getBilltype().equals(FbmBusConstant.BILLTYPE_GATHER)) {
				// 如果没有在fbm_outer表里，说明付票没有走收付报，而是直接已付票打勾，返回
				SuperVO[] outers = FBMProxy.getUifService().queryByCondition(OuterVO.class, "isnull(dr,0)=0 and pk_billtypecode='36GL' and pk_busibill='"
						+ regvo.getPrimaryKey()
						+ "'");
				if (outers != null && outers.length > 0) {
					return null;
				}

				// 开票收入项目
				rvo[0].setPk_selfcorp(regvo.getPk_corp());
				rvo[0].setCheckplandate(regvo.getVerifydate());
				rvo[0].setAmount(baseinfovo.getMoneyy().multiply(-1));// 票据金额取负值
				rvo[0].setPk_currtype(baseinfovo.getPk_curr());
				rvo[0].setPk_costsubj(regvo.getInvoiceitem());
				rvo[0].setPk_planitem(regvo.getInvoiceplanitem());
				rvo[0].setIoflag(IPlanConst.IOFLAG_INCOME);// 收入
				rvo[0].setMemo(regvo.getNote());
				return rvo;

			} else if (newActionVO.getActioncode().equals(FbmActionConstant.AUDIT)
					&& newActionVO.getBilltype().equals(FbmBusConstant.BILLTYPE_GATHER)) {
				// 收票收入项目
				rvo[0].setPk_selfcorp(regvo.getPk_corp());
				rvo[0].setCheckplandate(regvo.getVerifydate());
				rvo[0].setAmount(baseinfovo.getMoneyy().multiply(-1));// 票据金额取负值
				rvo[0].setPk_currtype(baseinfovo.getPk_curr());
				rvo[0].setIoflag(IPlanConst.IOFLAG_INCOME);// 收入
				// 查找收票登记单上的计划项目
				RegisterVO gathervo = (RegisterVO) FBMProxy.getUifService().queryByPrimaryKey(RegisterVO.class, newActionVO.getPk_source());
				rvo[0].setPk_planitem(gathervo.getGatherplanitem());

				return rvo;
			}
		}
		// 去掉了取消核销功能
		// else if(actioncode.endsWith("CANCELDESTROY")){
		// if(newActionVO.getActioncode().equals(FbmActionConstant.AUDIT)&&
		// newActionVO.getBilltype().equals(FbmBusConstant.BILLTYPE_RETURN)){
		// //开票收入项目
		// rvo[0].setPk_selfcorp(regvo.getPk_corp());
		// rvo[0].setCheckplandate(baseinfovo.getInvoicedate());
		// rvo[0].setAmount(baseinfovo.getMoneyy().multiply(-1));//票据金额取负值
		// rvo[0].setPk_currtype(baseinfovo.getPk_curr());
		// rvo[0].setPk_costsubj(regvo.getInvoiceitem());
		// rvo[0].setPk_planitem(regvo.getInvoiceplanitem());
		// rvo[0].setIoflag(IPlanConst.IOFLAG_INCOME);// 收入
		// rvo[0].setMemo(regvo.getNote());
		// return rvo;
		// }
		// }
		return null;
	}

	/**
	 * 托收转计划VO
	 * 
	 * @param collectionvo
	 * @return
	 * @throws BusinessException
	 */
	private static IOBudgetQueryVO[] collection2PlanVO(CollectionVO collectionvo)
			throws BusinessException {

		String opBillType = collectionvo.getOpbilltype();

		if (opBillType.equals(FbmBusConstant.BILL_UNISTORAGE)) {// 统管只维护托收计划项目
			IOBudgetQueryVO[] rvo = new IOBudgetQueryVO[1];
			initIOBugetVO(rvo, collectionvo);
			BaseDAO dao = new BaseDAO();
			BaseinfoVO baseinfovo = (BaseinfoVO) dao.retrieveByPK(BaseinfoVO.class, collectionvo.getPk_baseinfo());

			// 托收计划项目
			rvo[0].setPk_selfcorp(collectionvo.getPk_corp());
			rvo[0].setCheckplandate(collectionvo.getDcollectiondate());
			rvo[0].setAmount(baseinfovo.getMoneyy());
			rvo[0].setPk_currtype(baseinfovo.getPk_curr());
			rvo[0].setPk_costsubj(collectionvo.getCollectionitem());
			rvo[0].setPk_planitem(collectionvo.getCollectionplanitem());
			rvo[0].setIoflag(IPlanConst.IOFLAG_INCOME);// 收入
			rvo[0].setMemo(collectionvo.getNote());

			return rvo;
		} else if (opBillType.equals(FbmBusConstant.BILL_PRIVACY)) {
			IOBudgetQueryVO[] rvo = new IOBudgetQueryVO[2];
			initIOBugetVO(rvo, collectionvo);
			BaseDAO dao = new BaseDAO();
			BaseinfoVO baseinfovo = (BaseinfoVO) dao.retrieveByPK(BaseinfoVO.class, collectionvo.getPk_baseinfo());

			// 托收计划项目
			rvo[0].setPk_selfcorp(collectionvo.getPk_corp());
			rvo[0].setCheckplandate(collectionvo.getDcollectiondate());
			rvo[0].setAmount(baseinfovo.getMoneyy());
			rvo[0].setPk_currtype(baseinfovo.getPk_curr());
			rvo[0].setPk_costsubj(collectionvo.getCollectionitem());
			rvo[0].setPk_planitem(collectionvo.getCollectionplanitem());
			rvo[0].setIoflag(IPlanConst.IOFLAG_INCOME);
			rvo[0].setMemo(collectionvo.getNote());

			// 收票计划项目
			rvo[1].setPk_selfcorp(collectionvo.getPk_corp());
			rvo[1].setCheckplandate(collectionvo.getDcollectiondate());
			rvo[1].setAmount(baseinfovo.getMoneyy());
			rvo[1].setPk_currtype(baseinfovo.getPk_curr());
			rvo[1].setPk_costsubj(collectionvo.getFbmitem());
			rvo[1].setPk_planitem(collectionvo.getFbmplanitem());
			rvo[1].setIoflag(IPlanConst.IOFLAG_PAYOUT);
			rvo[1].setMemo(collectionvo.getNote());

			return rvo;
		}

		return null;
	}

	/**
	 * 贴现转计划VO
	 * 
	 * @param discountVO
	 * @return
	 * @throws BusinessException
	 */
	private static IOBudgetQueryVO[] discount2PlanVO(DiscountVO discountVO)
			throws BusinessException {

		String opBillType = discountVO.getOpbilltype();
		ArrayList<IOBudgetQueryVO> list = new ArrayList<IOBudgetQueryVO>();

		if (opBillType.equals(FbmBusConstant.BILL_UNISTORAGE)) {// 统管只维护贴现计划项目
			BaseDAO dao = new BaseDAO();
			BaseinfoVO baseinfovo = (BaseinfoVO) dao.retrieveByPK(BaseinfoVO.class, discountVO.getPk_baseinfo());

			// 贴现手续费
			if (discountVO.getDiscountcharge() != null
					&& discountVO.getDiscountcharge().doubleValue() > 0) {
				UFDouble discountcharge = discountVO.getDiscountcharge();
				IOBudgetQueryVO rVo = createDefaultVO(discountVO);
				rVo.setPk_selfcorp(discountVO.getPk_corp());
				rVo.setCheckplandate(discountVO.getDdiscountdate());
				rVo.setAmount(discountcharge);
				rVo.setPk_currtype(baseinfovo.getPk_curr());
				rVo.setPk_costsubj(discountVO.getChargeitem());
				rVo.setPk_planitem(discountVO.getChargeplanitem());
				rVo.setIoflag(IPlanConst.IOFLAG_PAYOUT);
				rVo.setMemo(discountVO.getNote());
				list.add(rVo);
			}

			// 贴现利息
			UFDouble discountinterest = discountVO.getDiscountinterest() == null ? new UFDouble(
					0)
					: discountVO.getDiscountinterest();
			if (discountinterest.doubleValue() > 0) {
				IOBudgetQueryVO rVolx = createDefaultVO(discountVO);
				rVolx.setPk_selfcorp(discountVO.getPk_corp());
				rVolx.setCheckplandate(discountVO.getDdiscountdate());
				rVolx.setAmount(discountinterest);
				rVolx.setPk_currtype(baseinfovo.getPk_curr());
				rVolx.setPk_costsubj(discountVO.getInterestitem());
				rVolx.setPk_planitem(discountVO.getInterestplanitem());
				rVolx.setIoflag(IPlanConst.IOFLAG_PAYOUT);
				rVolx.setMemo(discountVO.getNote());
				list.add(rVolx);
			}

			// 贴现余额
			UFDouble balancemoney = discountVO.getMoneyy() == null ? new UFDouble(
					0)
					: discountVO.getMoneyy();
			if (balancemoney.doubleValue() > 0) {
				IOBudgetQueryVO rVoye = createDefaultVO(discountVO);
				rVoye.setPk_selfcorp(discountVO.getPk_corp());
				rVoye.setCheckplandate(discountVO.getDdiscountdate());
				rVoye.setAmount(balancemoney);
				rVoye.setPk_currtype(baseinfovo.getPk_curr());
				rVoye.setPk_costsubj(discountVO.getBalanceitem());
				rVoye.setPk_planitem(discountVO.getBalanceplanitem());
				rVoye.setIoflag(IPlanConst.IOFLAG_INCOME);
				rVoye.setMemo(discountVO.getNote());
				list.add(rVoye);
			}

			IOBudgetQueryVO[] rvo = list.toArray(new IOBudgetQueryVO[0]);
			return rvo;
		} else if (opBillType.equals(FbmBusConstant.BILL_PRIVACY)) {

			BaseDAO dao = new BaseDAO();
			BaseinfoVO baseinfovo = (BaseinfoVO) dao.retrieveByPK(BaseinfoVO.class, discountVO.getPk_baseinfo());

			// 贴现手续费
			if (discountVO.getDiscountcharge() != null
					&& discountVO.getDiscountcharge().doubleValue() > 0) {
				IOBudgetQueryVO rVosxf = createDefaultVO(discountVO);
				rVosxf.setPk_selfcorp(discountVO.getPk_corp());
				rVosxf.setCheckplandate(discountVO.getDdiscountdate());
				UFDouble discountcharge = discountVO.getDiscountcharge() == null ? new UFDouble(
						0)
						: discountVO.getDiscountcharge();
				rVosxf.setAmount(discountcharge);
				rVosxf.setPk_currtype(baseinfovo.getPk_curr());
				rVosxf.setPk_costsubj(discountVO.getChargeitem());
				rVosxf.setPk_planitem(discountVO.getChargeplanitem());
				rVosxf.setIoflag(IPlanConst.IOFLAG_PAYOUT);
				rVosxf.setMemo(discountVO.getNote());
				list.add(rVosxf);
			}

			// 贴现利息
			UFDouble discountinterest = discountVO.getDiscountinterest() == null ? new UFDouble(
					0)
					: discountVO.getDiscountinterest();
			if (discountinterest.doubleValue() > 0) {
				IOBudgetQueryVO rVolx = createDefaultVO(discountVO);
				rVolx.setPk_selfcorp(discountVO.getPk_corp());
				rVolx.setCheckplandate(discountVO.getDdiscountdate());
				rVolx.setAmount(discountinterest);
				rVolx.setPk_currtype(baseinfovo.getPk_curr());
				rVolx.setPk_costsubj(discountVO.getInterestitem());
				rVolx.setPk_planitem(discountVO.getInterestplanitem());
				rVolx.setIoflag(IPlanConst.IOFLAG_PAYOUT);
				rVolx.setMemo(discountVO.getNote());
				list.add(rVolx);
			}

			// 贴现余额
			UFDouble balancemoney = discountVO.getMoneyy() == null ? new UFDouble(
					0)
					: discountVO.getMoneyy();
			if (balancemoney.doubleValue() > 0) {
				IOBudgetQueryVO rVoye = createDefaultVO(discountVO);
				rVoye.setPk_selfcorp(discountVO.getPk_corp());
				rVoye.setCheckplandate(discountVO.getDdiscountdate());
				rVoye.setAmount(balancemoney);
				rVoye.setPk_currtype(baseinfovo.getPk_curr());
				rVoye.setPk_costsubj(discountVO.getBalanceitem());
				rVoye.setPk_planitem(discountVO.getBalanceplanitem());
				rVoye.setIoflag(IPlanConst.IOFLAG_INCOME);
				rVoye.setMemo(discountVO.getNote());
				list.add(rVoye);
			}

			// 票据计划
			IOBudgetQueryVO rVojh = createDefaultVO(discountVO);
			rVojh.setPk_selfcorp(discountVO.getPk_corp());
			rVojh.setCheckplandate(discountVO.getDdiscountdate());
			rVojh.setAmount(baseinfovo.getMoneyy());
			rVojh.setPk_currtype(baseinfovo.getPk_curr());
			rVojh.setPk_costsubj(discountVO.getFbmitem());
			rVojh.setPk_planitem(discountVO.getFbmplanitem());
			rVojh.setIoflag(IPlanConst.IOFLAG_PAYOUT);
			rVojh.setMemo(discountVO.getNote());
			list.add(rVojh);

			IOBudgetQueryVO[] rvo = list.toArray(new IOBudgetQueryVO[0]);
			return rvo;
		}

		return null;

	}

	private static IOBudgetQueryVO createDefaultVO(SuperVO busvo) {
		IOBudgetQueryVO vo = new IOBudgetQueryVO();
		vo.setSyscode(FbmBusConstant.SYSCODE_FBM);
		vo.setPk_billtypecode((String) busvo.getAttributeValue("pk_billtypecode"));
		vo.setVbillno((String) busvo.getAttributeValue("vbillno"));
		vo.setPk_busitypecode((String) busvo.getAttributeValue("pk_billtypecode"));
		vo.setPk_lastbillid(busvo.getPrimaryKey());
		vo.setPk_lastbillrowid(busvo.getPrimaryKey());
		vo.setIswritepreperformmny(UFBoolean.TRUE);
		return vo;
	}

	/**
	 * 票据承兑转计划VO ,保证金不维护计划
	 * 
	 * @param acceptvo
	 * @return
	 * @throws BusinessException
	 */
	private static IOBudgetQueryVO[] accept2PlanVO(AcceptVO acceptvo)
			throws BusinessException {
		IOBudgetQueryVO[] rvo = new IOBudgetQueryVO[1];
		initIOBugetVO(rvo, acceptvo);

		BaseDAO dao = new BaseDAO();
		BaseinfoVO baseinfovo = (BaseinfoVO) dao.retrieveByPK(BaseinfoVO.class, acceptvo.getPk_baseinfo());

		// 承兑支出计划项目
		rvo[0].setPk_selfcorp(acceptvo.getPk_corp());
		rvo[0].setCheckplandate(acceptvo.getDacceptdate());
		rvo[0].setAmount(baseinfovo.getMoneyy());
		rvo[0].setPk_currtype(baseinfovo.getPk_curr());
		rvo[0].setPk_costsubj(acceptvo.getAcceptitem());
		rvo[0].setPk_planitem(acceptvo.getAcceptplanitem());
		rvo[0].setIoflag(IPlanConst.IOFLAG_PAYOUT);// 支出
		rvo[0].setMemo(acceptvo.getNote());

		return rvo;
	}

	/**
	 * 开票转资金计划接口参数
	 * 
	 * @param regvo
	 * @return
	 * @throws BusinessException
	 */
	private static IOBudgetQueryVO[] invoice2PlanVO(RegisterVO regvo,
			String actioncode) throws BusinessException {

		IOBudgetQueryVO[] rvo = new IOBudgetQueryVO[3];
		initIOBugetVO(rvo, regvo);

		BaseDAO dao = new BaseDAO();
		BaseinfoVO baseinfovo = (BaseinfoVO) dao.retrieveByPK(BaseinfoVO.class, regvo.getPk_baseinfo());

		// 手续费支出项目
		rvo[0].setPk_selfcorp(regvo.getPk_corp());
		rvo[0].setCheckplandate(baseinfovo.getInvoicedate());
		rvo[0].setAmount(regvo.getPoundagemoney());
		rvo[0].setPk_currtype(baseinfovo.getPk_curr());
		rvo[0].setPk_costsubj(regvo.getChargeitem());
		rvo[0].setPk_planitem(regvo.getChargeplanitem());
		rvo[0].setIoflag(IPlanConst.IOFLAG_PAYOUT);// 支出
		rvo[0].setMemo(regvo.getNote());

		// 开票收入项目
		rvo[1].setPk_selfcorp(regvo.getPk_corp());
		rvo[1].setCheckplandate(baseinfovo.getInvoicedate());
		rvo[1].setAmount(baseinfovo.getMoneyy());// 票据金额
		rvo[1].setPk_currtype(baseinfovo.getPk_curr());
		rvo[1].setPk_costsubj(regvo.getInvoiceitem());
		rvo[1].setPk_planitem(regvo.getInvoiceplanitem());
		rvo[1].setIoflag(IPlanConst.IOFLAG_INCOME);// 收入
		rvo[1].setMemo(regvo.getNote());

		// 付票计划项目
		rvo[2].setPk_selfcorp(regvo.getPk_corp());
		rvo[2].setCheckplandate(baseinfovo.getInvoicedate());
		rvo[2].setAmount(baseinfovo.getMoneyy());
		rvo[2].setPk_currtype(baseinfovo.getPk_curr());
		rvo[2].setPk_costsubj(regvo.getInvoiceoutitem());
		rvo[2].setPk_planitem(regvo.getInvoiceoutplanitem());
		rvo[2].setIoflag(IPlanConst.IOFLAG_PAYOUT);// 支出
		rvo[2].setMemo(regvo.getNote());

		// 判断是否被付款单引用
		SuperVO[] outers = FBMProxy.getUifService().queryByCondition(OuterVO.class, "isnull(dr,0)=0 and pk_billtypecode='36GL' and pk_busibill='"
				+ regvo.getPrimaryKey()
				+ "'");
		boolean hasCmpBill = false;
		if (outers != null && outers.length > 0) {
			hasCmpBill = true;
		}
		if (regvo.getSfflag().booleanValue() && !hasCmpBill) {// 如果付票登记打勾了则只维护手续费
			return new IOBudgetQueryVO[] { rvo[0], rvo[1], rvo[2] };
		}

		return new IOBudgetQueryVO[] { rvo[0], rvo[1] };
	}

	/**
	 * 内部托管和内部领用
	 * 
	 * @param storageVO
	 * @return
	 * @throws BusinessException
	 */
	private static IOBudgetQueryVO[] storage2PlanVO(StorageVO storageVO)
			throws BusinessException {
		IOBudgetQueryVO[] rvo = new IOBudgetQueryVO[1];
		initIOBugetVO(rvo, storageVO);

		rvo[0].setPk_selfcorp(storageVO.getKeepcorp());
		rvo[0].setCheckplandate(storageVO.getDapprovedate());
		rvo[0].setAmount(storageVO.getSummoneyy());
		rvo[0].setPk_currtype(storageVO.getPk_currtype());
		rvo[0].setPk_costsubj(storageVO.getUnititem());
		rvo[0].setPk_planitem(storageVO.getUnitplanitem());
		rvo[0].setMemo(storageVO.getMemo());
		if (storageVO.getPk_billtypecode().equals(FbmBusConstant.BILLTYPE_INNERKEEP)) {
			rvo[0].setIoflag(IPlanConst.IOFLAG_PAYOUT);
		} else if (storageVO.getPk_billtypecode().equals(FbmBusConstant.BILLTYPE_INNERBACK)) {
			rvo[0].setIoflag(IPlanConst.IOFLAG_INCOME);
		}

		return rvo;
	}

	/**
	 * 退票
	 * 
	 * @param returnVO
	 * @return
	 * @throws BusinessException
	 */
	private static IOBudgetQueryVO[] returnBill2PlanVO(ReturnVO returnVO)
			throws BusinessException {
		IOBudgetQueryVO[] rvo = new IOBudgetQueryVO[1];
		initIOBugetVO(rvo, returnVO);

		// 计算退票子表票据金额合计
		String sql = "select pk_curr,sum(moneyy) from fbm_return_b inner join fbm_baseinfo on fbm_return_b.pk_baseinfo=fbm_baseinfo.pk_baseinfo where pk_return='"
				+ returnVO.getPrimaryKey()
				+ "' group by pk_curr";
		BaseDAO dao = new BaseDAO();
		Object[] obj = (Object[]) dao.executeQuery(sql, new ArrayProcessor());

		// 获得币种
		UFDouble summoney = new UFDouble(0);
		String pk_curr = null;
		if (obj != null) {
			pk_curr = obj[0].toString();
			summoney = new UFDouble(obj[1].toString());
		}
		rvo[0].setPk_selfcorp(returnVO.getPk_corp());
		rvo[0].setCheckplandate(returnVO.getDreturndate());
		rvo[0].setAmount(summoney);
		rvo[0].setPk_currtype(pk_curr);
		rvo[0].setPk_costsubj(returnVO.getUnititem());
		rvo[0].setPk_planitem(returnVO.getUnitplanitem());
		rvo[0].setMemo(returnVO.getReturnnote());
		rvo[0].setIoflag(IPlanConst.IOFLAG_INCOME);

		return rvo;
	}

	/**
	 * 初始化计划VO对象
	 * 
	 * @param vos
	 */
	private static void initIOBugetVO(IOBudgetQueryVO[] vos, SuperVO busvo) {
		int len = vos.length;
		for (int i = 0; i < len; i++) {
			vos[i] = new IOBudgetQueryVO();
			vos[i].setSyscode(FbmBusConstant.SYSCODE_FBM);
			vos[i].setPk_billtypecode((String) busvo.getAttributeValue("pk_billtypecode"));
			vos[i].setVbillno((String) busvo.getAttributeValue("vbillno"));
			vos[i].setPk_busitypecode((String) busvo.getAttributeValue("pk_billtypecode"));
			vos[i].setPk_lastbillid(busvo.getPrimaryKey());
			vos[i].setPk_lastbillrowid(busvo.getPrimaryKey());
			vos[i].setIswritepreperformmny(UFBoolean.TRUE);
		}
	}

	/**
	 * 收票计划项目
	 * 
	 * @param regvo
	 * @return
	 * @throws BusinessException
	 */
	private static IOBudgetQueryVO[] gather2PlanVO(RegisterVO regvo)
			throws BusinessException {
		IOBudgetQueryVO[] rvo = new IOBudgetQueryVO[1];
		initIOBugetVO(rvo, regvo);
		BaseDAO dao = new BaseDAO();
		BaseinfoVO baseinfovo = (BaseinfoVO) dao.retrieveByPK(BaseinfoVO.class, regvo.getPk_baseinfo());

		// 托收计划项目
		rvo[0].setPk_selfcorp(regvo.getPk_corp());
		// UFDate vcheckplandate = new UFDate();//收票成功日期也就是当前登陆日期
		// 收票登记单写计划的业务日期应该是收票日期

		rvo[0].setCheckplandate(regvo.getGatherdate());
		rvo[0].setAmount(baseinfovo.getMoneyy());
		rvo[0].setPk_currtype(baseinfovo.getPk_curr());
		rvo[0].setPk_costsubj(regvo.getGatheritem());
		rvo[0].setPk_planitem(regvo.getGatherplanitem());
		rvo[0].setIoflag(IPlanConst.IOFLAG_INCOME);// 收入
		rvo[0].setMemo(regvo.getNote());

		return rvo;
	}

	/**
	 * 付票退票占计划执行数，构造付票退票计划VO
	 * 
	 * @param regvo
	 * @param returnvo
	 * @return
	 * @throws BusinessException
	 */
	public static IOBudgetQueryVO[] invoice2PlanVO(ReturnVO returnvo)
			throws BusinessException {
		HYPubBO pubbo = new HYPubBO();
		List<IOBudgetQueryVO> returnlist = new ArrayList<IOBudgetQueryVO>();
		ReturnBVO[] childrenVO = (ReturnBVO[]) pubbo.queryByCondition(ReturnBVO.class, "pk_return = '"
				+ returnvo.getPrimaryKey()
				+ "'");
		if (childrenVO != null && childrenVO.length > 0) {
			IOBudgetQueryVO[] rvo = new IOBudgetQueryVO[childrenVO.length];
			BaseDAO dao = new BaseDAO();

			initIOBugetVO(rvo, returnvo);

			for (int i = 0; i < childrenVO.length; i++) {

				BaseinfoVO baseinfovo = (BaseinfoVO) dao.retrieveByPK(BaseinfoVO.class, childrenVO[i].getPk_baseinfo());
				RegisterVO regvo = (RegisterVO) dao.retrieveByPK(RegisterVO.class, childrenVO[i].getPk_source());
				// 判断是否有接口单据引用，如果有接口单据引用就不再冲收支计划了
				Collection col = dao.retrieveByClause(OuterVO.class, "pk_register='"
						+ childrenVO[i].getPk_source()
						+ "'");
				if (col != null && col.size() > 0) {
					continue;
				}
				// 付票计划项目
				rvo[i].setPk_selfcorp(returnvo.getPk_corp());
				rvo[i].setCheckplandate(returnvo.getDreturndate());
				rvo[i].setAmount(baseinfovo.getMoneyy().multiply(-1));
				rvo[i].setPk_currtype(baseinfovo.getPk_curr());
				rvo[i].setPk_costsubj(regvo.getInvoiceoutitem());
				rvo[i].setPk_planitem(regvo.getInvoiceoutplanitem());
				rvo[i].setIoflag(IPlanConst.IOFLAG_PAYOUT);// 支出
				rvo[i].setMemo(regvo.getNote());
				returnlist.add(rvo[i]);
			}
		}
		return returnlist.toArray(new IOBudgetQueryVO[0]);
	}

	/**
	 * 开票退票占计划执行数，构造付票退票计划VO
	 * 
	 * @param regvo
	 * @param returnvo
	 * @return
	 * @throws BusinessException
	 */
	public static IOBudgetQueryVO[] gather2PlanVO(ReturnVO returnvo)
			throws BusinessException {

		HYPubBO pubbo = new HYPubBO();
		List<IOBudgetQueryVO> returnlist = new ArrayList<IOBudgetQueryVO>();
		ReturnBVO[] childrenVO = (ReturnBVO[]) pubbo.queryByCondition(ReturnBVO.class, "pk_return = '"
				+ returnvo.getPrimaryKey()
				+ "'");
		if (childrenVO != null && childrenVO.length > 0) {
			IOBudgetQueryVO[] rvo = new IOBudgetQueryVO[childrenVO.length];
			BaseDAO dao = new BaseDAO();

			initIOBugetVO(rvo, returnvo);

			for (int i = 0; i < childrenVO.length; i++) {

				BaseinfoVO baseinfovo = (BaseinfoVO) dao.retrieveByPK(BaseinfoVO.class, childrenVO[i].getPk_baseinfo());
				RegisterVO regvo = (RegisterVO) dao.retrieveByPK(RegisterVO.class, childrenVO[i].getPk_source());
				// 判断是否有接口单据引用，如果有接口单据引用就不再冲收支计划了
				Collection col = dao.retrieveByClause(OuterVO.class, "pk_register='"
						+ childrenVO[i].getPk_source()
						+ "'");
				if (col != null && col.size() > 0) {
					continue;
				}

				// 开票收入项目
				rvo[i].setPk_selfcorp(returnvo.getPk_corp());
				rvo[i].setCheckplandate(returnvo.getDreturndate());
				rvo[i].setAmount(baseinfovo.getMoneyy().multiply(-1));// 票据金额
				rvo[i].setPk_currtype(baseinfovo.getPk_curr());
				rvo[i].setPk_costsubj(regvo.getGatheritem());
				rvo[i].setPk_planitem(regvo.getGatherplanitem());
				rvo[i].setIoflag(IPlanConst.IOFLAG_INCOME);// 收入
				rvo[i].setMemo(regvo.getNote());
				returnlist.add(rvo[i]);

			}
		}
		return returnlist.toArray(new IOBudgetQueryVO[0]);
	}

}