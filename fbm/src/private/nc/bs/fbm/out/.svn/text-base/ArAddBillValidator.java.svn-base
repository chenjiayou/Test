package nc.bs.fbm.out;

import nc.impl.fbm.cmp.CMPConstant;
import nc.itf.cm.prv.CmpConst;
import nc.vo.fbm.outer.ArapBillParamVO;
import nc.vo.fbm.outer.OuterVO;
import nc.vo.fbm.pub.BaseinfoVO;
import nc.vo.fbm.pub.constant.FbmBusConstant;
import nc.vo.fbm.pub.constant.FbmStatusConstant;
import nc.vo.fbm.register.RegisterVO;
import nc.vo.pub.BusinessException;

public class ArAddBillValidator extends AbstractBillValidator {

	public void checkBaseinfo(ArapBillParamVO param) throws BusinessException {

		BaseinfoVO baseinfoVO = param.getBaseinfoVO();
		if (param.getFbmbillno() == null) {
			throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl
					.getNCLangRes().getStrByID("fbmcomm", "UPPFBMComm-000184")/* @res"第" */
					+ param.getRow()
					+ nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID(
							"fbmcomm", "UPPFBMComm-000185")/* @res"行票据编号不可为空" */);
		}

		if (baseinfoVO == null) {
			throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl
					.getNCLangRes().getStrByID("fbmcomm", "UPPFBMComm-000184")/* @res"第" */
					+ param.getRow()
					+ nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID(
							"fbmcomm", "UPPFBMComm-000186")/* @res"行票据编号不存在" */);
		}

		if (!param.getPk_curr().equals(baseinfoVO.getPk_curr())) {
			throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl
					.getNCLangRes().getStrByID("fbmcomm", "UPPFBMComm-000184")/* @res"第" */
					+ param.getRow()
					+ nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID(
							"fbmcomm", "UPPFBMComm-000187")/* @res"行票据币种不一致" */);
		}

		if (param.getMoneyy().doubleValue() != baseinfoVO.getMoneyy()
				.doubleValue()) {
			throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl
					.getNCLangRes().getStrByID("fbmcomm", "UPPFBMComm-000184")/* @res"第" */
					+ param.getRow()
					+ nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID(
							"fbmcomm", "UPPFBMComm-000203")/* @res"行票据金额不一致" */);
		}
	}

	public void doCheck(ArapBillParamVO param) throws BusinessException {
		RegisterVO regVO = (RegisterVO) baseDao.retrieveByPK(RegisterVO.class,
				param.getNewActionVO().getPk_source());

		// 检查收款单位与持票单位是否一致
		if (!param.getCurrentunit().equals(regVO.getHoldunit())) {
			throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl
					.getNCLangRes().getStrByID("fbmcomm", "UPPFBMComm-000219")/*
																				 * @res"收款单上的收款单位和票据上的持票单位不一致"
																				 */);
		}
		// 检查付款单位与付票单位是否一致
		if (!regVO.getPaybillunit().equals(param.getOtherunit())) {
			throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl
					.getNCLangRes().getStrByID("fbmcomm", "UPPFBMComm-000220")/*
																				 * @res"收款单上的付款单位和票据上的付票单位不一致"
																				 */);
		}

		if (billnoSet.contains(param.getFbmbillno())) {
			return;
		}

		if (regVO.getSfflag().booleanValue()) {
			throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl
					.getNCLangRes().getStrByID("fbmcomm", "UPPFBMComm-000221")/* @res"票据收款标志为已收款,无法录入收款单" */);
		}
		// 检查票据状态是否已登记
		if (param.getNewActionVO() == null) {
			throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl
					.getNCLangRes().getStrByID("fbmcomm", "UPPFBMComm-000389")/* @res"找不到票据业务操作" */);
		}
		if (!param.getNewActionVO().getEndstatus().equals(
				FbmStatusConstant.REGISTER)) {
			throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl
					.getNCLangRes().getStrByID("fbmcomm", "UPPFBMComm-000222")/* @res"票据状态不是已登记，无法保存" */);
		}

		// 重新查询外部关联
		OuterVO[] outers = outRelDao.queryByPkBusibill(regVO.getPrimaryKey());
		// 校验外部关联
		boolean isvalid = false;
		if (outers == null) {
			isvalid = true;
		} else if ((outers[0].getOuterdjdl().equals(CMPConstant.BILLTYPE_FK)
				|| outers[0].getOuterdjdl().equals(CMPConstant.BILLTYPE_SJ)
				|| outers[0].getOuterdjdl().equals(CMPConstant.BILLTYPE_FJ)
				|| outers[0].getOuterdjdl().equals(CMPConstant.BILLTYPE_FJ_OLD) 
				|| outers[0].getOuterdjdl().equals(CMPConstant.BILLTYPE_FK_OLD))
				&& outers[0].getOuterstatus().equals(
						FbmBusConstant.OUTERBILL_OVER)
				&& outers[0].getPk_billtypecode().equals(
						FbmBusConstant.BILLTYPE_ENDORE)) {// 情况二：有外部关联记录,即回头票
			isvalid = true;
		}

		if (!isvalid) {
 			throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl
					.getNCLangRes().getStrByID("fbmcomm", "UPPFBMComm-000223")/* @res"票据已经被其他单据使用" */);
		}
	}

	public void afterAction(ArapBillParamVO param) throws BusinessException {
		RegisterVO regVO = (RegisterVO) baseDao.retrieveByPK(RegisterVO.class,
				param.getNewActionVO().getPk_source());
		// 插入外部单据关联关系
		OuterVO outer = new OuterVO();
		outer.setPk_outerbill_h(param.getPk_bill_h());
		outer.setPk_outerbill_b(param.getPk_bill_b());
		outer.setOuterdjdl(param.getOuterdjdl());
		outer.setOuterstatus(FbmBusConstant.OUTERBILL_USE);
		outer.setOuterbilltype(param.getOuterbilltype());
		outer.setPk_corp(param.getPk_corp());
		outer.setPk_busibill(regVO.getPrimaryKey());
		outer.setPk_billtypecode(FbmBusConstant.BILLTYPE_GATHER);
		outer.setPk_register(param.getNewActionVO().getPk_source());
		baseDao.insertVO(outer);

		super.afterAction(param);

	}

}