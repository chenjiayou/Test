package nc.bs.fbm.relief.action;

import nc.vo.fbm.pub.BusiActionParamVO;
import nc.vo.fbm.relief.ReliefVO;
import nc.vo.pub.BusinessException;
import nc.vo.trade.pub.HYBillVO;
/**
 * 
 * 类功能说明：
     调剂出库-出库, 出库动作看成一个快捷方式，不做任何处理
 * 日期：2007-10-24
 * 程序员： wues
 *
 */
public class OutputRelief<K extends HYBillVO,T extends ReliefVO> extends ActionRelief<K,T>{

	@Override
	protected String getBeginStatus(BusiActionParamVO<T> param)
			throws BusinessException {
		return null;
	}

	@Override
	protected String getEndStatus(BusiActionParamVO<T> param) throws BusinessException {
		return null;
	}

	@Override
	protected String doCheck(BusiActionParamVO<T> param) throws BusinessException {
		// TODO Auto-generated method stub
		return null;
	}

}
