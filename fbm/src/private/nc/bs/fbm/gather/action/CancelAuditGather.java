package nc.bs.fbm.gather.action;

import nc.bs.fbm.pub.CommonDAO;
import nc.bs.fbm.pub.action.BusiMessageTranslator;
import nc.vo.fbm.pub.ActionVO;
import nc.vo.fbm.pub.BusiActionParamVO;
import nc.vo.fbm.pub.constant.FbmActionConstant;
import nc.vo.fbm.pub.constant.FbmBusConstant;
import nc.vo.fbm.pub.constant.FbmStatusConstant;
import nc.vo.fbm.register.RegisterVO;
import nc.vo.pub.BusinessException;

public class CancelAuditGather<K extends RegisterVO, T extends RegisterVO> extends ActionGather<K, T> {

	@Override
	protected String doCheck(BusiActionParamVO<T> param) throws BusinessException {
		String commonError = commonCheck(param);
		if(commonError != null)return commonError;

		ActionVO actionVO = param.getLastActionVO();
			//校验票据动作
		if(!(actionVO.getActioncode().equals(FbmActionConstant.AUDIT)
					&& actionVO.getBilltype().equals(FbmBusConstant.BILLTYPE_GATHER)
					&& actionVO.getPk_bill().equals(param.getPk_bill()))){
			return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000239")/* @res"票据"*/ + param.getBaseinfoVO().getFbmbillno() + nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000263")/* @res"的前一操作必须是审核收票登记单。"*/+ BusiMessageTranslator.translateAction(param);
		}
		return null;
	}


	@Override
	protected String getBeginStatus(BusiActionParamVO<T> param) throws BusinessException {
		RegisterVO vo = param.getSuperVO();
		if (FbmBusConstant.GATHER_TYPE_RELIEF.equals(vo.getGathertype())){//调剂存放过来的
			 return FbmStatusConstant.HAS_RELIEF_KEEP;//已内部存放
		} else {
			return  FbmStatusConstant.REGISTER;
		}
	}

	@Override
	protected String getEndStatus(BusiActionParamVO<T> param) throws BusinessException {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public void dealAccount(String pk_action, BusiActionParamVO<T> param) throws BusinessException {
		// TODO Auto-generated method stub
		CommonDAO dao = new CommonDAO();
		dao.delAccountDetailByActionPK(pk_action);
	}


}
