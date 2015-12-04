/**
 *
 */
package nc.bs.fbm.innerback.action;

import nc.bs.fbm.pub.action.BusiMessageTranslator;
import nc.bs.trade.business.HYPubBO;
import nc.vo.fbm.pub.ActionVO;
import nc.vo.fbm.pub.BaseinfoVO;
import nc.vo.fbm.pub.BusiActionParamVO;
import nc.vo.fbm.pub.constant.FbmActionConstant;
import nc.vo.fbm.pub.constant.FbmBusConstant;
import nc.vo.fbm.pub.constant.FbmStatusConstant;
import nc.vo.fbm.storage.StorageVO;
import nc.vo.pub.BusinessException;
import nc.vo.trade.pub.HYBillVO;

/**
 * <p>
 *
 * <p>创建人：lpf
 * <b>日期：2007-10-16
 *
 */
public class CanceloutInnerBack<K extends HYBillVO,T extends StorageVO> extends ActionInnerBack<K,T> {

	@Override
	protected String doCheck(BusiActionParamVO<T> param) throws BusinessException {
		String commonError = commonCheck(param);
		if(commonError != null)return commonError;

		ActionVO actionVO = param.getLastActionVO();
			//校验票据动作
		if(!(actionVO.getActioncode().equals(FbmActionConstant.OUTPUT_SUCCESS)
					&& actionVO.getBilltype().equals(FbmBusConstant.BILLTYPE_INNERBACK)
					&& actionVO.getPk_bill().equals(param.getPk_bill()))){
			return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000239")/* @res"票据"*/ + param.getBaseinfoVO().getFbmbillno() + nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000273")/* @res"的前一操作必须是出库内部领用单。"*/+ BusiMessageTranslator.translateAction(param);
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see nc.bs.fbm.pub.action.AbstractAction#getBeginStatus(nc.vo.fbm.pub.ActionParamVO)
	 */
	@Override
	protected String getBeginStatus(BusiActionParamVO<T> param)
			throws BusinessException {
		HYPubBO hypubbo = new HYPubBO();
		BaseinfoVO baseinfovo = (BaseinfoVO)hypubbo.queryByPrimaryKey(BaseinfoVO.class, param.getPk_baseinfo());
		if(baseinfovo != null && baseinfovo.getDisable() != null) {
			if(baseinfovo.getDisable().booleanValue()) {
				return FbmStatusConstant.HAS_DISABLE;
			} else {
				return FbmStatusConstant.REGISTER;
			}
		} else {
			return null;
		}
	}

	/* (non-Javadoc)
	 * @see nc.bs.fbm.pub.action.AbstractAction#getEndStatus(nc.vo.fbm.pub.ActionParamVO)
	 */
	@Override
	protected String getEndStatus(BusiActionParamVO<T> param) throws BusinessException {
		return null;
	}

}