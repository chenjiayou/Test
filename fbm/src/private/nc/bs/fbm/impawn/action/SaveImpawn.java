package nc.bs.fbm.impawn.action;

import nc.vo.fbm.impawn.ImpawnVO;
import nc.vo.fbm.pub.BusiActionParamVO;
import nc.vo.fbm.pub.constant.FbmStatusConstant;
import nc.vo.pub.BusinessException;

/**
 * 
 * ���ܣ� Ʊ����Ѻ����action ���ڣ�2007-10-10 ����Ա��wues
 */
public class SaveImpawn<K extends ImpawnVO, T extends ImpawnVO> extends ActionImpawn<K, T> {
	
	@Override
	protected String doCheck(BusiActionParamVO<T> param) throws BusinessException {
		String commonError = commonCheck(param);
		if(commonError != null)return commonError;
		return null;
	}
	
	@Override
	protected String getBeginStatus(BusiActionParamVO<T> param)
			throws BusinessException {
		return FbmStatusConstant.REGISTER;
	}

	@Override
	protected String getEndStatus(BusiActionParamVO<T> param) throws BusinessException {
		return FbmStatusConstant.ON_IMPAWN;
	}

}
