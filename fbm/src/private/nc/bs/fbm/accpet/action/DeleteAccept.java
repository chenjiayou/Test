package nc.bs.fbm.accpet.action;

import nc.vo.fbm.acceptbill.AcceptVO;
import nc.vo.fbm.pub.BusiActionParamVO;
import nc.vo.fbm.pub.constant.FbmStatusConstant;
import nc.vo.pub.BusinessException;

public class DeleteAccept<K extends AcceptVO, T extends AcceptVO> extends ActionAccept<K, T> {

	@Override
	protected String doCheck(BusiActionParamVO<T> param) throws BusinessException {
//		String commonError = commonCheck(param);
//		if(commonError != null)return commonError;
//		
//		ActionVO actionVO = param.getLastActionVO();
//		//校验票据动作
//		if(!(actionVO.getActioncode().equals(FbmActionConstant.SAVE)
//				&& actionVO.getBilltype().equals(FbmBusConstant.BILLTYPE_BILLPAY)
//				&& actionVO.getPk_bill().equals(param.getPk_bill()))){
//			return "票据" + param.getBaseinfoVO().getFbmbillno() + "的前一操作必须是保存票据付款单。"+ MessageTranslator.translateAction(param);
//		}
		return null;
	}

	@Override
	protected String getBeginStatus(BusiActionParamVO<T> param)
			throws BusinessException {
		return FbmStatusConstant.ON_PAY;
	}

	@Override
	protected String getEndStatus(BusiActionParamVO<T> param) throws BusinessException {
		return null;
	}

}
