package nc.bs.fbm.relief.action;

import nc.vo.fbm.pub.BusiActionParamVO;
import nc.vo.fbm.pub.constant.FbmStatusConstant;
import nc.vo.fbm.relief.ReliefVO;
import nc.vo.pub.BusinessException;
import nc.vo.trade.pub.HYBillVO;

/**
 * 类功能说明：---作废，此类不用，调剂出库中去掉“取消出库功能” 
 * 	调剂出库-取消出库 
 * 日期：2007-10-24 
 * 程序员： wues
 */
public class CancelOutputRelief<K extends HYBillVO,T extends ReliefVO> extends ActionRelief<K,T> {

	@Override
	protected String doCheck(BusiActionParamVO<T> param) throws BusinessException {
		String commonError = commonCheck(param);
		if(commonError != null)return commonError;
		return null;
	}


	@Override
	protected String getBeginStatus(BusiActionParamVO<T> param)
			throws BusinessException {
		// TODO Auto-generated method stub
		return FbmStatusConstant.HAS_RELIEF;
	}

	@Override
	protected String getEndStatus(BusiActionParamVO<T> param) throws BusinessException {
		// TODO Auto-generated method stub
		return FbmStatusConstant.HAS_RELIEF;
	}

	/**
	 * 额外处理 生成内部领用单
	 */
	protected void afterAction(BusiActionParamVO<T>[] params) throws BusinessException {
		if (null == params || params.length == 0) {
			return;
		}
	}
}
