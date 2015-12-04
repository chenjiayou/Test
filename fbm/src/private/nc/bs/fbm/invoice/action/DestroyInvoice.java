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


public class DestroyInvoice<K extends RegisterVO,T extends RegisterVO> extends ActionInvoice<K,T>{
	@Override
	protected String doCheck(BusiActionParamVO<T> param) throws BusinessException {
		ActionVO actionVO = param.getLastActionVO();
		String fbmbillno = param.getBaseinfoVO().getFbmbillno();
		if(actionVO == null){
			return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000238")/* @res"找不到票据动作"*/;
		}
		//校验状态
		if(!actionVO.getEndstatus().equals(FbmStatusConstant.REGISTER)&&!actionVO.getEndstatus().equals(FbmStatusConstant.HAS_RETURN)){
			return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000239")/* @res"票据"*/+ fbmbillno+ nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000276")/* @res"的状态不是"*/+ BusiMessageTranslator.retriveStatusName(FbmStatusConstant.REGISTER)
					+ nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000277")/* @res"或者"*/+ BusiMessageTranslator.retriveStatusName(FbmStatusConstant.HAS_RETURN)+ nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000241")/* @res",操作失败。"*/ + BusiMessageTranslator.translateAction(param);
		}
		//校验业务日期
		if(actionVO.getActiondate().after(param.getActiondate())){
			return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000239")/* @res"票据"*/+ fbmbillno + nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000243")/* @res"的当前业务日期早于前一业务日期。"*/+ BusiMessageTranslator.translateAction(param) ;
		}

			//校验票据动作
		if((!(actionVO.getActioncode().equals(FbmActionConstant.AUDIT)
					&& actionVO.getBilltype().equals(FbmBusConstant.BILLTYPE_GATHER)))
			&&(!(actionVO.getActioncode().equals(FbmActionConstant.AUDIT)
					&& actionVO.getBilltype().equals(FbmBusConstant.BILLTYPE_RETURN)))){
			return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000239")/* @res"票据"*/ + param.getBaseinfoVO().getFbmbillno() + nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000278")/* @res"的前一操作必须是审核收票登记单或者审核退票单,无法核销。"*/+ BusiMessageTranslator.translateAction(param);
		}
		return null;
	}

	@Override
	protected String getBeginStatus(BusiActionParamVO<T>param)
			throws BusinessException {
		ActionVO lastaction = param.getLastActionVO();
		String endstatus = lastaction.getEndstatus();
		if(CommonUtil.isNull(endstatus)||!endstatus.equals(FbmStatusConstant.REGISTER)&&!endstatus.equals(FbmStatusConstant.HAS_RETURN)){
			throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000279")/* @res"票据的状态必须为已退票或者已登记，操作失败"*/);
		}
		return endstatus;
	}

	@Override
	protected String getEndStatus(BusiActionParamVO<T> param) throws BusinessException {
		return FbmStatusConstant.HAS_DESTROY;
	}

	/**
	 * 如果开票回头票，核销开票后需要更新收票登记单状态为已核销
	 */
	protected void afterAction(BusiActionParamVO<T>[] params) throws BusinessException {
		super.afterAction(params);

		if(CommonUtil.isNull(params)){
			return;
		}

		ActionVO lastaction = params[0].getLastActionVO();
		String endstatus = lastaction.getEndstatus();
		if(CommonUtil.isNull(endstatus)||endstatus.equals(FbmStatusConstant.HAS_RETURN)){
			return;
		}
		String pk_corp = params[0].getRegisterVO().getPk_corp();

		if(CommonUtil.isNull(pk_corp)){
			return;
		}
		RegisterVO registerVo = new CommonDAO().queryLastGather(params[0].getPk_baseinfo(), pk_corp);
		if(registerVo==null)
			throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000280")/* @res"没有对应的收票登记信息，无法核销开票"*/);


		nc.bs.fbm.gather.action.DestroyGather<RegisterVO,RegisterVO> gather = new nc.bs.fbm.gather.action.DestroyGather<RegisterVO,RegisterVO>();
		BusiActionParamVO<RegisterVO>[] newparams = gather.build4Destroy(registerVo,FbmActionConstant.DESTROY);
		
		gather.dealGather4Destroy(newparams[0]);
	}
}
