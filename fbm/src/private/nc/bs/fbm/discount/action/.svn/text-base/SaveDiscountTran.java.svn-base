package nc.bs.fbm.discount.action;

import nc.vo.fbm.discount.DiscountVO;
import nc.vo.fbm.pub.ActionVO;
import nc.vo.fbm.pub.BusiActionParamVO;
import nc.vo.fbm.pub.constant.FbmStatusConstant;
import nc.vo.pub.BusinessException;

public class SaveDiscountTran<K extends DiscountVO, T extends DiscountVO> extends ActionDiscountTran<K,T> {

	@Override
	protected String doCheck(BusiActionParamVO<T> param) throws BusinessException {
		String commonError = commonCheck(param);
		if(commonError != null)return commonError;
		return null;
	}

	@Override
	protected String getBeginStatus(BusiActionParamVO<T> param) throws BusinessException {
		DiscountVO vo = param.getSuperVO();
		String pk_app = vo.getPk_discount_app();

		ActionVO actionVO = param.getLastActionVO();
		if(actionVO == null){
			return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000238")/* @res"找不到票据动作"*/;
		}
		String endstatus = actionVO.getEndstatus();
		if(pk_app != null){
			return FbmStatusConstant.ON_DISCOUNT;
		}else{
			if(endstatus.equals(FbmStatusConstant.REGISTER) || endstatus.equals(FbmStatusConstant.HAS_BANK_KEEP)){
				return endstatus;
			}
		}
		return null;

	}

	@Override
	protected String getEndStatus(BusiActionParamVO<T> param) throws BusinessException {
		return FbmStatusConstant.ON_DISCOUNT;
	}

}