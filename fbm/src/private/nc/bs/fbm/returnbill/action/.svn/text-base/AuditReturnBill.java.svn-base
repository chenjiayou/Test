package nc.bs.fbm.returnbill.action;

import nc.bs.fbm.pub.action.BusiMessageTranslator;
import nc.vo.fbm.pub.ActionVO;
import nc.vo.fbm.pub.BusiActionParamVO;
import nc.vo.fbm.pub.constant.FbmActionConstant;
import nc.vo.fbm.pub.constant.FbmBusConstant;
import nc.vo.fbm.pub.constant.FbmStatusConstant;
import nc.vo.fbm.register.RegisterVO;
import nc.vo.fbm.returnbill.ReturnVO;
import nc.vo.pub.BusinessException;
import nc.vo.trade.pub.HYBillVO;

public class AuditReturnBill<K extends HYBillVO,T extends ReturnVO>  extends ActionReturnBill<K,T> {

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
	protected String getBeginStatus(BusiActionParamVO<T> param) throws BusinessException {
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
		ReturnVO returnVO = param.getSuperVO();

		if(returnVO.getReturntype().equals(FbmBusConstant.BACK_TYPE_UNISTORAGE)){
			//中心退出退票审核
			return FbmStatusConstant.HAS_CENTER_RETURN;
		}
		if(returnVO.getReturntype().equals(FbmBusConstant.BACK_TYPE_UNISTORAGE_UNIT)){
			//单位退入退票审核
//			if(param.getBaseinfoVO().getDisable().booleanValue()){
//				return FbmStatusConstant.HAS_RETURN;
//			}else{
//				return FbmStatusConstant.REGISTER;
//			}
			//NC5.6均改成已登记
			return FbmStatusConstant.REGISTER;
			
		}
		if(returnVO.getReturntype().equals(FbmBusConstant.BACK_TYPE_ENDORE)){
			return FbmStatusConstant.REGISTER;
		}
		return FbmStatusConstant.HAS_RETURN;
	}

	@Override
	protected void afterAction(BusiActionParamVO<T>[] params) throws BusinessException {
		
		if(params!=null && params.length > 0){
			ReturnVO returnVO;
			RegisterVO regVO ;
			
			for(int i = 0; i < params.length ; i++){
				returnVO = params[i].getSuperVO();
				//单位退入时要修改存放地点
				if(returnVO.getReturntype().equals(FbmBusConstant.BACK_TYPE_UNISTORAGE_UNIT)){
					regVO = params[i].getRegisterVO();
					// regVO.setKeepunit(regVO.getHoldunit());
					// String holdunit = regVO.getHoldunit();
					String sql = "update fbm_register set keepunit = '"
							+ regVO.getHoldunit()
							+ "' where pk_register = '"
							+ regVO.getPk_register()
							+ "'";

					dao.getBaseDAO().executeUpdate(sql);
				}
			}
		}
		
	}

}