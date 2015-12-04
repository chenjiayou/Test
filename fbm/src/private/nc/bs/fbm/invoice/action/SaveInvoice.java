package nc.bs.fbm.invoice.action;

import nc.vo.fbm.pub.BusiActionParamVO;
import nc.vo.fbm.pub.constant.FbmStatusConstant;
import nc.vo.fbm.register.RegisterVO;
import nc.vo.pub.BusinessException;

public class SaveInvoice<K extends RegisterVO,T extends RegisterVO> extends ActionInvoice<K,T> {

	@Override
	protected String doCheck(BusiActionParamVO<T> param) throws BusinessException {
		return null;
	}

	@Override
	protected String getBeginStatus(BusiActionParamVO<T> param)
			throws BusinessException {
		// TODO Auto-generated method stub
		return FbmStatusConstant.NONE;
	}

	@Override
	protected String getEndStatus(BusiActionParamVO<T> param) throws BusinessException {
		// TODO Auto-generated method stub
		RegisterVO vo = param.getSuperVO();
		if(vo.getSfflag().booleanValue()){
			return FbmStatusConstant.ON_PAYBILL;
		}
		return FbmStatusConstant.ON_INVOICE;
	}

}
