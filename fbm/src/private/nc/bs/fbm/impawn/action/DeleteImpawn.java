package nc.bs.fbm.impawn.action;

import nc.bs.fbm.pub.action.BusiMessageTranslator;
import nc.vo.fbm.impawn.ImpawnVO;
import nc.vo.fbm.pub.ActionVO;
import nc.vo.fbm.pub.BusiActionParamVO;
import nc.vo.fbm.pub.constant.FbmActionConstant;
import nc.vo.fbm.pub.constant.FbmBusConstant;
import nc.vo.fbm.pub.constant.FbmStatusConstant;
import nc.vo.pub.BusinessException;
/**
 *
 * 功能：
       删除质押记录
 * 日期：2007-10-10
 * 程序员：wues
 */
public class DeleteImpawn<K extends ImpawnVO, T extends ImpawnVO> extends ActionImpawn<K, T> {
	@Override
	protected String doCheck(BusiActionParamVO<T> param) throws BusinessException {
		String commonError = commonCheck(param);
		if(commonError != null)return commonError;

		ActionVO actionVO = param.getLastActionVO();
		//校验票据动作
		if(!(actionVO.getActioncode().equals(FbmActionConstant.SAVE)
				&& actionVO.getBilltype().equals(FbmBusConstant.BILLTYPE_IMPAWN)
				&& actionVO.getPk_bill().equals(param.getPk_bill()))){
			return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000239")/* @res"票据"*/ + param.getBaseinfoVO().getFbmbillno() + nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000252")/* @res"的前一操作必须是保存质押单。"*/+ BusiMessageTranslator.translateAction(param);
		}
		return null;
	}

	//前置状态为在质押
	protected String getBeginStatus(BusiActionParamVO<T> param) throws BusinessException {
		return  FbmStatusConstant.ON_IMPAWN;
	}
	//结束状态
	protected String getEndStatus(BusiActionParamVO<T> param) throws BusinessException {
		// TODO Auto-generated method stub
		return null;
	}

}