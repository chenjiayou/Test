package nc.bs.fbm.relief.action;

import nc.vo.fbm.pub.BusiActionParamVO;
import nc.vo.fbm.pub.constant.FbmStatusConstant;
import nc.vo.fbm.relief.ReliefVO;
import nc.vo.pub.BusinessException;
import nc.vo.trade.pub.HYBillVO;

/**
 * �๦��˵����---���ϣ����಻�ã�����������ȥ����ȡ�����⹦�ܡ� 
 * 	��������-ȡ������ 
 * ���ڣ�2007-10-24 
 * ����Ա�� wues
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
	 * ���⴦�� �����ڲ����õ�
	 */
	protected void afterAction(BusiActionParamVO<T>[] params) throws BusinessException {
		if (null == params || params.length == 0) {
			return;
		}
	}
}
