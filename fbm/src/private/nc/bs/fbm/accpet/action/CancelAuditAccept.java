package nc.bs.fbm.accpet.action;

import nc.vo.fbm.acceptbill.AcceptVO;
import nc.vo.fbm.pub.BusiActionParamVO;
import nc.vo.fbm.pub.constant.FbmStatusConstant;
import nc.vo.pub.BusinessException;

public class CancelAuditAccept<K extends AcceptVO, T extends AcceptVO> extends ActionAccept<K, T> {

	@Override
	protected String doCheck(BusiActionParamVO<T> param) throws BusinessException {
//		String commonError = commonCheck(param);
//		if(commonError != null)return commonError;
//			
//		ActionVO actionVO = param.getLastActionVO();
//			//校验票据动作
//		if(!(actionVO.getActioncode().equals(FbmActionConstant.AUDIT)
//					&& actionVO.getBilltype().equals(FbmBusConstant.BILLTYPE_BILLPAY)
//					&& actionVO.getPk_bill().equals(param.getPk_bill()))){
//			return "票据" + param.getBaseinfoVO().getFbmbillno() + "的前一操作必须是审核票据付款单。"+ MessageTranslator.translateAction(param);
//		}
		return null;
	}


	@Override
	protected String getBeginStatus(BusiActionParamVO<T> param)
			throws BusinessException {
		// TODO Auto-generated method stub
		return FbmStatusConstant.HAS_PAY;
	}

	@Override
	protected String getEndStatus(BusiActionParamVO<T> param) throws BusinessException {
		// TODO Auto-generated method stub
		return null;
	}

}
