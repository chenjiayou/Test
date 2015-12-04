package nc.bs.fbm.discountapply.action;

import nc.vo.fbm.discount.DiscountVO;
import nc.vo.fbm.pub.BusiActionParamVO;
import nc.vo.fbm.pub.constant.FbmStatusConstant;
import nc.vo.pub.BusinessException;

public class SaveDiscountApp<K extends DiscountVO, T extends DiscountVO> extends ActionDiscountApp<K, T> {

	@Override
	protected String doCheck(BusiActionParamVO<T> param) throws BusinessException {
		String commonError = commonCheck(param);
		if(commonError != null)return commonError;
		return null;
	}
	

	@Override
	protected String getBeginStatus(BusiActionParamVO<T> param) throws BusinessException {
		String endstatus = param.getLastActionVO().getEndstatus();
		if(endstatus.equals(FbmStatusConstant.REGISTER) || endstatus.equals(FbmStatusConstant.HAS_BANK_KEEP)){
			return endstatus;
		}
		return null;
	}

	@Override
	protected String getEndStatus(BusiActionParamVO<T> param) throws BusinessException {
		return FbmStatusConstant.ON_DISCOUNT;
	}

	public void dealAccount(String pk_action, BusiActionParamVO<T> param) throws BusinessException {
	}
}
