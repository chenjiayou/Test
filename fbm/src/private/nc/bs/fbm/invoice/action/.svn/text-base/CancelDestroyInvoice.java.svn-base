package nc.bs.fbm.invoice.action;

import nc.bs.fbm.pub.CommonDAO;
import nc.bs.fbm.pub.action.BusiMessageTranslator;
import nc.itf.cdm.util.CommonUtil;
import nc.vo.fbm.pub.ActionVO;
import nc.vo.fbm.pub.BusiActionParamVO;
import nc.vo.fbm.pub.constant.FbmActionConstant;
import nc.vo.fbm.pub.constant.FbmBusConstant;
import nc.vo.fbm.pub.constant.FbmStatusConstant;
import nc.vo.fbm.register.RegisterVO;
import nc.vo.pub.BusinessException;

public class CancelDestroyInvoice<K extends RegisterVO,T extends RegisterVO> extends ActionInvoice<K,T> {
	@Override
	protected String doCheck(BusiActionParamVO<T> param) throws BusinessException {
		String commonError = commonCheck(param);
		if(commonError != null)return commonError;

		ActionVO actionVO = param.getLastActionVO();
			//校验票据动作
		if((!(actionVO.getActioncode().equals(FbmActionConstant.DESTROY)
					&& actionVO.getBilltype().equals(FbmBusConstant.BILLTYPE_GATHER)))
		 &&(!(actionVO.getActioncode().equals(FbmActionConstant.DESTROY)
				&& actionVO.getBilltype().equals(FbmBusConstant.BILLTYPE_INVOICE)))){
			return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000239")/* @res"票据"*/ + param.getBaseinfoVO().getFbmbillno() + nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000271")/* @res"的前一操作必须是核销收票登记单或者核销已经退票的开票单。"*/+ BusiMessageTranslator.translateAction(param);
		}
		return null;
	}

	@Override
	protected String getBeginStatus(BusiActionParamVO<T> param)
			throws BusinessException {
		return FbmStatusConstant.HAS_DESTROY;
	}

	@Override
	protected String getEndStatus(BusiActionParamVO<T> param) throws BusinessException {
		return null;
	}

	@Override
	protected void afterAction(BusiActionParamVO<T>[] params) throws BusinessException {
		super.afterAction(params);

		ActionVO lastaction = params[0].getLastActionVO();
		String beginstatus = lastaction.getBeginstatus();
		//退票核销不需要删除收票信息
		if(CommonUtil.isNull(beginstatus)||beginstatus.equals(FbmStatusConstant.HAS_RETURN)){
			return;
		}
		String pk_corp = params[0].getRegisterVO().getPk_corp();

		if(CommonUtil.isNull(pk_corp)){
			return;
		}
		RegisterVO registerVo = new CommonDAO().queryLastGather(params[0].getPk_baseinfo(), pk_corp);
		if(registerVo!=null){
			nc.bs.fbm.gather.action.DestroyGather<RegisterVO,RegisterVO> gather = new nc.bs.fbm.gather.action.DestroyGather<RegisterVO,RegisterVO>();
			BusiActionParamVO<RegisterVO>[] newparams = gather.build4Destroy(registerVo,FbmActionConstant.CANCELDESTROY);
			
			gather.dealGather4Destroy(newparams[0]);
		}
	}

}
