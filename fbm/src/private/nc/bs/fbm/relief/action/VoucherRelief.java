package nc.bs.fbm.relief.action;

import nc.vo.fbm.pub.BusiActionParamVO;
import nc.vo.fbm.relief.ReliefVO;
import nc.vo.pub.BusinessException;
import nc.vo.trade.pub.HYBillVO;
/**
 * 
 * 类功能说明：
     调剂出库-制证
 * 日期：2007-10-24
 * 程序员： wues
 *
 */
public class VoucherRelief<K extends HYBillVO,T extends ReliefVO> extends ActionRelief<K,T>{

	@Override
	protected String doCheck(BusiActionParamVO<T> param) throws BusinessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected String getBeginStatus(BusiActionParamVO<T> param)
			throws BusinessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected String getEndStatus(BusiActionParamVO<T> param) throws BusinessException {
		// TODO Auto-generated method stub
		return null;
	}

}
