/**
 *
 */
package nc.bs.fbm.innerback.action;

import nc.vo.fbm.pub.BusiActionParamVO;
import nc.vo.fbm.pub.constant.FbmStatusConstant;
import nc.vo.fbm.storage.StorageVO;
import nc.vo.pub.BusinessException;
import nc.vo.trade.pub.HYBillVO;

/**
 * <p>
 *
 * <p>创建人：lpf
 * <b>日期：2007-10-16
 *
 */
public class SaveInnerBack<K extends HYBillVO,T extends StorageVO> extends ActionInnerBack<K,T> {

	@Override
	protected String doCheck(BusiActionParamVO<T> param) throws BusinessException {
		String commonError = commonCheck(param);
		if(commonError != null)return commonError;
		return null;
	}

	/* (non-Javadoc)
	 * @see nc.bs.fbm.pub.action.AbstractAction#getBeginStatus(nc.vo.fbm.pub.ActionParamVO)
	 */
	@Override
	protected String getBeginStatus(BusiActionParamVO<T> param)
			throws BusinessException {
		String endstatus = param.getLastActionVO().getEndstatus();
		if(endstatus.equals(FbmStatusConstant.HAS_CENTER_USE)||  endstatus.equals(FbmStatusConstant.HAS_CLEAR) || endstatus.equals(FbmStatusConstant.HAS_DISABLE) || endstatus.equals(FbmStatusConstant.HAS_INNER_KEEP) || endstatus.equals(FbmStatusConstant.HAS_RELIEF_KEEP) ){
			return endstatus;
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see nc.bs.fbm.pub.action.AbstractAction#getEndStatus(nc.vo.fbm.pub.ActionParamVO)
	 */
	@Override
	protected String getEndStatus(BusiActionParamVO<T> param) throws BusinessException {
		return FbmStatusConstant.ON_INNER_BACK;
	}

}
