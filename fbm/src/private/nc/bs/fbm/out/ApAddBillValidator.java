package nc.bs.fbm.out;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import nc.bs.fbm.pub.action.BusiActionFactory;
import nc.bs.framework.common.NCLocator;
import nc.bs.pub.action.N_36GQ_SAVE;
import nc.impl.fbm.cmp.CMPConstant;
import nc.itf.uap.sfapp.IBillcodeRuleService;
import nc.vo.fbm.endore.EndoreVO;
import nc.vo.fbm.outer.ArapBillParamVO;
import nc.vo.fbm.outer.OuterVO;
import nc.vo.fbm.pub.BaseinfoVO;
import nc.vo.fbm.pub.constant.FbmActionConstant;
import nc.vo.fbm.pub.constant.FbmBusConstant;
import nc.vo.fbm.pub.constant.FbmStatusConstant;
import nc.vo.fbm.register.RegisterVO;
import nc.vo.pub.BusinessException;
import nc.vo.pub.compiler.PfParameterVO;
import nc.vo.trade.pub.HYBillVO;
import nc.vo.trade.pub.IBillStatus;

public class ApAddBillValidator extends AbstractBillValidator {

	/**
	 * 存放票据号和客商的对应关系，处理一张票拆成多张票同时背书给多个客商的情况
	 */
	private Map<String, String> noteCustMap = new HashMap<String, String>();

	/**
	 * 判断同一张票据是否付给不同客商
	 * 
	 * @param noteNo
	 * @param custPk
	 * @throws BusinessException
	 */
	private void dealSameNote(String noteNo, String custPk)
			throws BusinessException {
		String oldCust = noteCustMap.get(noteNo);
		if (oldCust == null) {
			noteCustMap.put(noteNo, custPk);
		} else {// 当前的票据号在map中已存在，比较custPk与以前的客商pk是否一致，不一致报错
			if (!oldCust.equals(custPk)) {
				throw new BusinessException(
						nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcode112", "UPPFBMCODE112-000026")/*
																													 * @res
																													 * "同一张票据不能付给不同客商"
																													 */);
			}
		}
	}

	public void checkBaseinfo(ArapBillParamVO param) throws BusinessException {

		BaseinfoVO baseinfoVO = param.getBaseinfoVO();
		if (param.getFbmbillno() == null) {
			throw new BusinessException(
					nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm", "UPPFBMComm-000184")/*
																										 * @res
																										 * "第"
																										 */
							+ param.getRow()
							+ nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm", "UPPFBMComm-000185")/*
																													 * @res
																													 * "行票据编号不可为空"
																													 */);
		}

		if (baseinfoVO == null) {
			throw new BusinessException(
					nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm", "UPPFBMComm-000184")/*
																										 * @res
																										 * "第"
																										 */
							+ param.getRow()
							+ nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm", "UPPFBMComm-000186")/*
																													 * @res
																													 * "行票据编号不存在"
																													 */);
		}

		if (!param.getPk_curr().equals(baseinfoVO.getPk_curr())) {
			throw new BusinessException(
					nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm", "UPPFBMComm-000184")/*
																										 * @res
																										 * "第"
																										 */
							+ param.getRow()
							+ nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm", "UPPFBMComm-000187")/*
																													 * @res
																													 * "行票据币种不一致"
																													 */);
		}

		if (param.getMoneyy().doubleValue() != baseinfoVO.getMoneyy().doubleValue()) {
			throw new BusinessException(
					nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm", "UPPFBMComm-000184")/*
																										 * @res
																										 * "第"
																										 */
							+ param.getRow()
							+ nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm", "UPPFBMComm-000203")/*
																													 * @res
																													 * "行票据金额不一致"
																													 */);
		}
	}

	public void doCheck(ArapBillParamVO param) throws BusinessException {
		RegisterVO regVO = (RegisterVO) baseDao.retrieveByPK(RegisterVO.class, param.getNewActionVO().getPk_source());

		// 判断不同的票据是否付给了不同的客商
		dealSameNote(param.getFbmbillno(), param.getOtherunit());

		if (param.getNewActionVO().getEndstatus().equals(FbmStatusConstant.REGISTER)
				|| param.getNewActionVO().getEndstatus().equals(FbmStatusConstant.ON_ENDORE)) {// 使用收到票据付款

			if (!param.getCurrentunit().equals(regVO.getHoldunit())) {
				throw new BusinessException(
						nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm", "UPPFBMComm-000204")/*
																											 * @res
																											 * "付款单上的付款单位和票据上的持票单位不一致"
																											 */);
			}
		} else if (param.getNewActionVO().getEndstatus().equals(FbmStatusConstant.HAS_INVOICE)
				|| param.getNewActionVO().getEndstatus().equals(FbmStatusConstant.ON_PAYBILL)) {// 使用开出票据付款
			if (!param.getCurrentunit().equals(param.getBaseinfoVO().getPayunit())) {
				throw new BusinessException(
						nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm", "UPPFBMComm-000205")/*
																											 * @res
																											 * "付款单上的付款单位和票据上的付款单位不一致"
																											 */);
			}
			boolean oneCustNull = (param.getBaseinfoVO().getReceiveunit() == null && param.getOtherunit() != null)
					|| (param.getBaseinfoVO().getReceiveunit() != null && param.getOtherunit() == null);
			boolean twoCustNull = (param.getBaseinfoVO().getReceiveunit() == null && param.getOtherunit() == null);
			if (oneCustNull
					|| (!twoCustNull && !param.getBaseinfoVO().getReceiveunit().equals(param.getOtherunit()))) {
				throw new BusinessException(
						nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm", "UPPFBMComm-000206")/*
																											 * @res
																											 * "付款单上的收款单位和票据上的收款单位不一致"
																											 */);
			}
		}

		if (billnoSet.contains(param.getFbmbillno())) {
			return;
		}

		if (param.getNewActionVO().getEndstatus().equals(FbmStatusConstant.REGISTER)
				|| param.getNewActionVO().getEndstatus().equals(FbmStatusConstant.HAS_RELIEF_KEEP)) {// 使用收到票据付款

			if (!regVO.getSfflag().booleanValue()) {
				throw new BusinessException(
						nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm", "UPPFBMComm-000207")/*
																											 * @res
																											 * "此票据没有收票收款记录"
																											 */);
			}
		} else if (param.getNewActionVO().getEndstatus().equals(FbmStatusConstant.HAS_INVOICE)) {// 使用开出票据付款

			if (regVO.getSfflag().booleanValue()) {
				throw new BusinessException(
						nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm", "UPPFBMComm-000208")/*
																											 * @res
																											 * "票据收款标志为已付款,无法录入付款单"
																											 */);
			}

			// 如果是应付票据，则检查划账结算单是否生效
			// if (!outRelDao.isHjEffect(regVO.getPrimaryKey())) {
			// throw new BusinessException("背书单位付票登记的划账结算单未生成或未生效");
			// }

			if (regVO.getSfflag().booleanValue()) {
				throw new BusinessException(
						nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm", "UPPFBMComm-000209")/*
																											 * @res
																											 * "应付票据已进行付票付款"
																											 */);
			}
		} else {
			throw new BusinessException(
					nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm", "UPPFBMComm-000210")/*
																										 * @res
																										 * "票据状态不是已登记或已开票"
																										 */);
		}
		// 重新查询外部关联
		OuterVO[] outers = outRelDao.queryByPkBusibill(regVO.getPrimaryKey());
		// 校验外部关联
		boolean isvalid = false;
		if (outers == null) {
			isvalid = true;
		} else if ((outers[0].getOuterdjdl().equals(CMPConstant.BILLTYPE_SK)
				|| outers[0].getOuterdjdl().equals(CMPConstant.BILLTYPE_SJ)
				|| outers[0].getOuterdjdl().equals(CMPConstant.BILLTYPE_SJ_OLD) || outers[0].getOuterdjdl().equals(CMPConstant.BILLTYPE_SK_OLD))
				&& outers[0].getOuterstatus().equals(FbmBusConstant.OUTERBILL_OVER)
				&& outers[0].getPk_billtypecode().equals(FbmBusConstant.BILLTYPE_GATHER)) {// 情况二：有外部关联记录,即回头票
			isvalid = true;
		}

		if (!isvalid) {
			throw new BusinessException(
					nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm", "UPPFBMComm-000223")/*
																										 * @res
																										 * "票据已经被其他单据使用"
																										 */);
		}

	}

	public void afterAction(ArapBillParamVO param) throws BusinessException {
		String curStatus = param.getNewActionVO().getEndstatus();
		RegisterVO regVO = (RegisterVO) baseDao.retrieveByPK(RegisterVO.class, param.getNewActionVO().getPk_source());

		String pk_endore;
		if (!billnoSet.contains(param.getFbmbillno())) {
			// 增加背书办理单记录
			EndoreVO endoreVO = new EndoreVO();
			if (curStatus.equals(FbmStatusConstant.REGISTER)) {
				endoreVO.setPk_billtypecode(FbmBusConstant.BILLTYPE_ENDORE);
				endoreVO.setOpbilltype(FbmBusConstant.BILL_PRIVACY);// 设置为私有
				endoreVO.setPk_corp(regVO.getPk_corp());
			} else if (curStatus.equals(FbmStatusConstant.HAS_RELIEF_KEEP)) {
				endoreVO.setPk_billtypecode(FbmBusConstant.BILLTYPE_ENDORE);
				endoreVO.setOpbilltype(FbmBusConstant.BILL_UNISTORAGE);// 设置为统管
				endoreVO.setPk_corp(param.getPk_corp());
			} else {
				endoreVO.setPk_billtypecode(FbmBusConstant.BILLTYPE_INVOICE);
				endoreVO.setOpbilltype(FbmBusConstant.BILL_PRIVACY);// 设置为私有
				endoreVO.setPk_corp(regVO.getPk_corp());
			}
			// 增加单据编号
			IBillcodeRuleService codeSrv = (IBillcodeRuleService) NCLocator.getInstance().lookup(IBillcodeRuleService.class.getName());
			String vbillno = codeSrv.getBillCode_RequiresNew(endoreVO.getPk_billtypecode(), regVO.getPk_corp(), null, null);
			endoreVO.setVbillno(vbillno);
			endoreVO.setPk_source(regVO.getPrimaryKey());
			endoreVO.setPk_baseinfo(param.getBaseinfoVO().getPrimaryKey());
			endoreVO.setEndorser(param.getCurrentunit());// 背书人
			endoreVO.setEndorsee(param.getOtherunit());// 被背书人
			endoreVO.setVbillstatus(IBillStatus.FREE);

			endoreVO.setVoperatorid(param.getVoperator());
			endoreVO.setDoperatedate(param.getDoperatdate());
			endoreVO.setBusdate(param.getDjrq());

			endoreVO.setSyscode(FbmBusConstant.ENDORE_SYS_CMP);// 设置来源系统为收付报
			// 添加本币汇率，本币金额，辅币汇率和辅币金额
			endoreVO.setBrate(param.getBrate());
			endoreVO.setMoneyb(param.getMoneyb());
			endoreVO.setFrate(param.getFrate());
			endoreVO.setMoneyf(param.getMoneyf());
			endoreVO.setMoneyy(param.getMoneyy());

			// pk_endore = baseDao.insertVO(endoreVO);
			//
			// EndoreVO newEndoreVO = (EndoreVO)
			// baseDao.retrieveByPK(EndoreVO.class,pk_endore);
			// BusiActionFactory.getInstance().createActionClass(FbmBusConstant.BILLTYPE_ENDORE,
			// FbmActionConstant.SAVE).doAction(newEndoreVO,
			// FbmActionConstant.SAVE, false);

			if (regVO.getPk_billtypecode().equals(FbmBusConstant.BILLTYPE_GATHER)) {
				N_36GQ_SAVE endoreSave = new N_36GQ_SAVE();
				PfParameterVO vo = new PfParameterVO();
				vo.m_preValueVo = new HYBillVO();
				vo.m_preValueVo.setParentVO(endoreVO);
				pk_endore = (String) ((ArrayList) (endoreSave.runComClass(vo))).get(0);
			} else {
				pk_endore = baseDao.insertVO(endoreVO);
				EndoreVO newEndoreVO = (EndoreVO) baseDao.retrieveByPK(EndoreVO.class, pk_endore);
				BusiActionFactory.getInstance().createActionClass(FbmBusConstant.BILLTYPE_ENDORE, FbmActionConstant.SAVE).doAction(newEndoreVO, FbmActionConstant.SAVE, false);
			}

			lastEndorePK.put(param.getFbmbillno(), pk_endore);
		} else {
			pk_endore = lastEndorePK.get(param.getFbmbillno());
		}

		// 插入外部单据关联关系
		OuterVO outer = new OuterVO();
		outer.setPk_outerbill_h(param.getPk_bill_h());
		outer.setPk_outerbill_b(param.getPk_bill_b());
		outer.setOuterdjdl(param.getOuterdjdl());
		outer.setOuterstatus(FbmBusConstant.OUTERBILL_USE);
		outer.setOuterbilltype(param.getOuterbilltype());
		outer.setPk_corp(param.getPk_corp());
		// outer.setPk_busibill(regVO.getPrimaryKey());
		// outer.setPk_billtypecode(regVO.getPk_billtypecode());
		// 特别注意：由于可能存在一张票拆多条的情况，因此可能第二张票过来时状态已经修改为在背书状态
		if (curStatus.equals(FbmStatusConstant.REGISTER)
				|| curStatus.equals(FbmStatusConstant.HAS_RELIEF_KEEP)
				|| curStatus.equals(FbmStatusConstant.ON_ENDORE)) {// 应收票据取背书办理单界面
			outer.setPk_busibill(pk_endore);
			outer.setPk_billtypecode(FbmBusConstant.BILLTYPE_ENDORE);
		} else {// 应付票据仍然取付票登记
			outer.setPk_busibill(regVO.getPrimaryKey());
			outer.setPk_billtypecode(FbmBusConstant.BILLTYPE_INVOICE);
		}
		outer.setPk_register(param.getNewActionVO().getPk_source());
		baseDao.insertVO(outer);

		super.afterAction(param);
	}

}