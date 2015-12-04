package nc.bs.fbm.consignbank.action;

import nc.vo.fbm.consignbank.CollectionVO;
import nc.vo.fbm.pub.BusiActionParamVO;
import nc.vo.fbm.pub.constant.FbmStatusConstant;
import nc.vo.pub.BusinessException;

public class SaveCollection<K extends CollectionVO, T extends CollectionVO> extends ActionCollection<K, T> {

	@Override
	protected String doCheck(BusiActionParamVO<T> param) throws BusinessException {
		String commonError = commonCheck(param);
		if(commonError != null)return commonError;
		return null;
	}
	

	//zhouwb 2008-10-10 增加返回值：HAS_IMPAWN
	@Override
	protected String getBeginStatus(BusiActionParamVO<T> param) throws BusinessException {
		 
		String endstatus = param.getLastActionVO().getEndstatus();
		if(endstatus.equals(FbmStatusConstant.REGISTER) || endstatus.equals(FbmStatusConstant.HAS_BANK_KEEP)
				|| endstatus.equals(FbmStatusConstant.HAS_IMPAWN)){	
			return endstatus;
		}
		
		return null;
	}

	@Override
	protected String getEndStatus(BusiActionParamVO<T> param) throws BusinessException {
		return FbmStatusConstant.ON_COLLECTION;
	}

}
