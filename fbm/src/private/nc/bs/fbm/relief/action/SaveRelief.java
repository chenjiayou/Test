package nc.bs.fbm.relief.action;

import nc.vo.fbm.pub.BusiActionParamVO;
import nc.vo.fbm.pub.constant.FbmStatusConstant;
import nc.vo.fbm.relief.ReliefVO;
import nc.vo.pub.BusinessException;
import nc.vo.trade.pub.HYBillVO;

/**
 * 
 * 类功能说明： 调剂出库-保存 日期：2007-10-24 程序员： wues
 * 
 */
public class SaveRelief<K extends HYBillVO,T extends ReliefVO> extends ActionRelief<K,T> {
	@Override
	protected String doCheck(BusiActionParamVO<T> param) throws BusinessException {
		String commonError = commonCheck(param);
		if(commonError != null)return commonError;
		return null;
	}

	/**
	 * 票的前置状态为已调剂存放
	 */
	protected String getBeginStatus(BusiActionParamVO<T> param)
			throws BusinessException {
		return FbmStatusConstant.HAS_RELIEF_KEEP;
	}

	/**
	 * 票的截止状态为在调剂
	 */
	protected String getEndStatus(BusiActionParamVO<T> param) throws BusinessException {
		//根据调剂单位和收票登记单确定此收票登记单是否为自己的票据
//		if (ReliefUtil.isSelfBill(param)) {
//			//如果是自己的票仍为已调剂存
//			return FbmStatusConstant.HAS_INNER_KEEP;
//		} else 
			//否则票据状态就为在调剂==自己的票和他人的票，票据状态均为在调剂
			return FbmStatusConstant.ON_RELIEF;
	}

}
