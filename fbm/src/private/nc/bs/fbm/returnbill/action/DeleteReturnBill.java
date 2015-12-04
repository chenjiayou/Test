package nc.bs.fbm.returnbill.action;

import nc.bs.fbm.pub.action.BusiMessageTranslator;
import nc.vo.fbm.pub.ActionVO;
import nc.vo.fbm.pub.BusiActionParamVO;
import nc.vo.fbm.pub.constant.FbmActionConstant;
import nc.vo.fbm.pub.constant.FbmBusConstant;
import nc.vo.fbm.pub.constant.FbmStatusConstant;
import nc.vo.fbm.returnbill.ReturnVO;
import nc.vo.pub.BusinessException;
import nc.vo.trade.pub.HYBillVO;

public class DeleteReturnBill<K extends HYBillVO,T extends ReturnVO> extends ActionReturnBill<K,T> {
	@Override
	protected String doCheck(BusiActionParamVO<T> param) throws BusinessException {
		String commonError = commonCheck(param);
		if(commonError != null)return commonError;

		ActionVO actionVO = param.getLastActionVO();
			//校验票据动作
		if(!(actionVO.getActioncode().equals(FbmActionConstant.SAVE)
					&& actionVO.getBilltype().equals(FbmBusConstant.BILLTYPE_RETURN)
					&& actionVO.getPk_bill().equals(param.getPk_bill()))){
			return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000239")/* @res"票据"*/ + param.getBaseinfoVO().getFbmbillno() + nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000257")/* @res"的前一操作必须是保存退票单。"*/+ BusiMessageTranslator.translateAction(param);
		}
		return null;
	}

	@Override
	protected String getBeginStatus(BusiActionParamVO<T> param)
			throws BusinessException {
		ReturnVO returnVO = param.getSuperVO();
		if(returnVO.getReturntype().equals(FbmBusConstant.BACK_TYPE_UNISTORAGE)){
			return FbmStatusConstant.ON_CENTER_RETURN;
		}
		if(returnVO.getReturntype().equals(FbmBusConstant.BACK_TYPE_UNISTORAGE_UNIT)){
			return FbmStatusConstant.ON_UNIT_RETURN;
		}
		return FbmStatusConstant.ON_RETURN;
	}

	@Override
	protected String getEndStatus(BusiActionParamVO<T> param) throws BusinessException {
		// TODO Auto-generated method stub
		return null;
	}

}