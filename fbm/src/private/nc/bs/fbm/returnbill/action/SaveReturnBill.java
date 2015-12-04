package nc.bs.fbm.returnbill.action;

import nc.bs.fbm.pub.action.BusiMessageTranslator;
import nc.vo.fbm.pub.ActionVO;
import nc.vo.fbm.pub.BusiActionParamVO;
import nc.vo.fbm.pub.constant.FbmBusConstant;
import nc.vo.fbm.pub.constant.FbmStatusConstant;
import nc.vo.fbm.returnbill.ReturnVO;
import nc.vo.pub.BusinessException;
import nc.vo.trade.pub.HYBillVO;

public class SaveReturnBill<K extends HYBillVO,T extends ReturnVO> extends ActionReturnBill<K,T> {

	@Override
	protected String doCheck(BusiActionParamVO<T> param) throws BusinessException {
		ActionVO actionVO = param.getLastActionVO();
		String fbmbillno = param.getBaseinfoVO().getFbmbillno();
		if (actionVO == null) {
			return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID(
					"fbmcomm", "UPPFBMComm-000238")/* @res"找不到票据动作" */;
		}

		boolean isvalid = false;
		ReturnVO returnVO = param.getSuperVO();

		//当前的操作公司是否和上一动作的公司一致
		if (null == param.getPk_corp() || "".equals(param.getPk_corp().trim())) {
			throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add","UPP3620ADD-000166")/*@res "当前操作的公司为空，请确认。"*/);
		}
		//false：不一致；true：一致
		boolean isSameCorp = param.getPk_corp().equals(actionVO.getPk_corp());

		boolean isSameReg = param.getPk_source().equals(actionVO.getPk_source());

		//校验，如果是已退票状态则还需要校验是否为同一个公司做了两次相同业务，如果否才置true
		if (returnVO.getReturntype().equals(FbmBusConstant.BACK_TYPE_GATHER)
				&& (actionVO.getEndstatus().equals(FbmStatusConstant.REGISTER) )
				&& isSameReg) {
			isvalid = true;
		}
		if (returnVO.getReturntype().equals(FbmBusConstant.BACK_TYPE_ENDORE)
				&& ((actionVO.getEndstatus()
						.equals(FbmStatusConstant.HAS_ENDORE) && isSameReg)|| (actionVO
						.getEndstatus().equals(FbmStatusConstant.HAS_RETURN) && !isSameCorp))) {
			isvalid = true;
		}
		if (returnVO.getReturntype().equals(FbmBusConstant.BACK_TYPE_INVOICE)
				&& ((actionVO.getEndstatus().equals(
						FbmStatusConstant.HAS_PAYBILL)&& isSameReg) || (actionVO
						.getEndstatus().equals(FbmStatusConstant.HAS_RETURN) && !isSameCorp))) {
			isvalid = true;
		}
		if ((returnVO.getReturntype().equals(FbmBusConstant.BACK_TYPE_DISABLE))
				&& ((actionVO.getEndstatus().equals(
						FbmStatusConstant.HAS_DISABLE)&& isSameReg) || (actionVO
						.getEndstatus().equals(FbmStatusConstant.REGISTER)  && isSameCorp ))) {
			isvalid = true;
		}

		if (returnVO.getReturntype().equals(FbmBusConstant.BACK_TYPE_UNISTORAGE)
				&& ((actionVO.getEndstatus().equals(
						FbmStatusConstant.HAS_DISABLE)&& isSameReg) || (actionVO
						.getEndstatus().equals(FbmStatusConstant.REGISTER) ))) {
			isvalid = true;
		}

		if (returnVO.getReturntype().equals(FbmBusConstant.BACK_TYPE_UNISTORAGE_UNIT)
				&& actionVO.getEndstatus().equals(
						FbmStatusConstant.HAS_CENTER_RETURN)&& !isSameReg) {
			isvalid = true;
		}

		// 校验状态
		if (!isvalid) {
			return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID(
					"fbmcomm", "UPPFBMComm-000239")/* @res"票据" */
					+ fbmbillno
					+ nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID(
							"fbmcomm", "UPPFBMComm-000240")/* @res"的状态是" */
					+ BusiMessageTranslator.retriveStatusName(actionVO
							.getEndstatus())
					+ nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID(
							"fbmcomm", "UPPFBMComm-000241")/* @res",操作失败。" */
					+ BusiMessageTranslator.translateAction(param);
		}

		// 校验业务日期
		if (actionVO.getActiondate().after(param.getActiondate())) {
			return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID(
					"fbmcomm", "UPPFBMComm-000239")/* @res"票据" */
					+ fbmbillno
					+ nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID(
							"fbmcomm", "UPPFBMComm-000243")/* @res"的当前业务日期早于前一业务日期。" */
					+ BusiMessageTranslator.translateAction(param);
		}

		return null;
	}

	@Override
	protected String getBeginStatus(BusiActionParamVO<T> param)
			throws BusinessException {
		ReturnVO returnVO = param.getSuperVO();
		ActionVO actionVO = param.getLastActionVO();
		if (returnVO.getReturntype().equals(FbmBusConstant.BACK_TYPE_GATHER)) {
			return FbmStatusConstant.REGISTER;
		}
		if (returnVO.getReturntype().equals(FbmBusConstant.BACK_TYPE_ENDORE)) {
			return FbmStatusConstant.HAS_ENDORE;
		}
		if (returnVO.getReturntype().equals(FbmBusConstant.BACK_TYPE_INVOICE)) {
			return FbmStatusConstant.HAS_PAYBILL;
		}
		if (returnVO.getReturntype().equals(FbmBusConstant.BACK_TYPE_DISABLE)) {
			return actionVO.getEndstatus();
			//return FbmStatusConstant.HAS_DISABLE;
		}
		if(returnVO.getReturntype().equals(FbmBusConstant.BACK_TYPE_UNISTORAGE)){
			if(param.getBaseinfoVO().getDisable().booleanValue()){
				return FbmStatusConstant.HAS_DISABLE;
			}
			return FbmStatusConstant.HAS_ENDORE;
		}
		if(returnVO.getReturntype().equals(FbmBusConstant.BACK_TYPE_UNISTORAGE_UNIT)){
			return FbmStatusConstant.HAS_CENTER_USE;
		}
		return null;
	}

	@Override
	protected String getEndStatus(BusiActionParamVO<T> param) throws BusinessException {
		ReturnVO returnVO = param.getSuperVO();
		if(returnVO.getReturntype().equals(FbmBusConstant.BACK_TYPE_UNISTORAGE)){
			return FbmStatusConstant.ON_CENTER_RETURN;
		}
		if(returnVO.getReturntype().equals(FbmBusConstant.BACK_TYPE_UNISTORAGE_UNIT)){
			return FbmStatusConstant.ON_UNIT_RETURN;
		}
		return FbmStatusConstant.ON_RETURN;
	}

}