package nc.bs.fbm.invoice.action;

import nc.bs.fbm.pub.action.BusiMessageTranslator;
import nc.vo.fbm.pub.ActionVO;
import nc.vo.fbm.pub.BusiActionParamVO;
import nc.vo.fbm.pub.constant.FbmActionConstant;
import nc.vo.fbm.pub.constant.FbmBusConstant;
import nc.vo.fbm.pub.constant.FbmStatusConstant;
import nc.vo.fbm.register.RegisterVO;
import nc.vo.pub.BusinessException;

public class DeleteInvoice<K extends RegisterVO,T extends RegisterVO> extends ActionInvoice<K,T> {
	@Override
	protected String doCheck(BusiActionParamVO<T> param) throws BusinessException {
		String commonError = commonCheck(param);
		if(commonError != null)return commonError;

		ActionVO actionVO = param.getLastActionVO();
			//校验票据动作
		if(!(actionVO.getActioncode().equals(FbmActionConstant.SAVE)
					&& actionVO.getBilltype().equals(FbmBusConstant.BILLTYPE_INVOICE)
					&& actionVO.getPk_bill().equals(param.getPk_bill()))){
			return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000239")/* @res"票据"*/ + param.getBaseinfoVO().getFbmbillno() + nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000255")/* @res"的前一操作必须是保存付票登记单。"*/+ BusiMessageTranslator.translateAction(param);
		}
		return null;
	}
	@Override
	protected String getBeginStatus(BusiActionParamVO<T> param)
			throws BusinessException {
		RegisterVO vo = param.getSuperVO();
		if(vo.getSfflag().booleanValue()){
			return FbmStatusConstant.ON_PAYBILL;
		}
		return FbmStatusConstant.ON_INVOICE;
	}

	@Override
	protected String getEndStatus(BusiActionParamVO<T> param) throws BusinessException {
		// TODO Auto-generated method stub
		return null;
	}


}
