package nc.bs.fbm.impawn.action;

import nc.bs.fbm.pub.CommonDAO;
import nc.bs.fbm.pub.action.BusiMessageTranslator;
import nc.vo.fbm.impawn.ImpawnVO;
import nc.vo.fbm.proxy.OuterProxy;
import nc.vo.fbm.pub.ActionVO;
import nc.vo.fbm.pub.BusiActionParamVO;
import nc.vo.fbm.pub.constant.FbmActionConstant;
import nc.vo.fbm.pub.constant.FbmBusConstant;
import nc.vo.fbm.pub.constant.FbmStatusConstant;
import nc.vo.fi.pub.ProductInfo;
import nc.vo.pub.BusinessException;

/**
 *
 * 功能： 质押弃审 日期：2007-10-10 程序员：wues
 */
public class CancelAuditImpawn<K extends ImpawnVO, T extends ImpawnVO> extends ActionImpawn<K, T> {
	@Override
	protected String doCheck(BusiActionParamVO<T> param) throws BusinessException {
		String commonError = commonCheck(param);
		if (commonError != null)
			return commonError;

		ActionVO actionVO = param.getLastActionVO();
		// 校验票据动作
		if (!(actionVO.getActioncode().equals(FbmActionConstant.AUDIT)
				&& actionVO.getBilltype()
						.equals(FbmBusConstant.BILLTYPE_IMPAWN) && actionVO
				.getPk_bill().equals(param.getPk_bill()))) {
			return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000239")/* @res"票据"*/ + param.getBaseinfoVO().getFbmbillno()
					+ nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000264")/* @res"的前一操作必须是审核质押单。"*/
					+ BusiMessageTranslator.translateAction(param);
		}
		return null;
	}

	@Override
	protected String getBeginStatus(BusiActionParamVO<T> param)
			throws BusinessException {
		return FbmStatusConstant.HAS_IMPAWN;
	}

	@Override
	protected String getEndStatus(BusiActionParamVO<T> param) throws BusinessException {
		return FbmStatusConstant.ON_IMPAWN;
	}

	/**
	 * 额外处理： 删除物权担保记录 弃审质押单
	 */
	protected void afterAction(BusiActionParamVO<T>[] param) throws BusinessException {
		ImpawnVO impawnVO = param[0].getSuperVO();
		//删除物权担保记录
		deleteFiImpawn(impawnVO);
	}

	/**
	 *
	 * 根据质押单VO删除相应的物权担保信息,删除时只要根据pk_othersys判断即可s
	 *
	 * @param fiImpawnPK
	 * @param fbmImpawnVO
	 * @throws BusinessException
	 */
	private void deleteFiImpawn(ImpawnVO impawnVO) throws BusinessException {
		// 信贷系统启用则删除此物权担保
		if (isEnabled(impawnVO.getPk_corp(),ProductInfo.pro_CDM)) {
			OuterProxy.getImpawnService().deleteImpawn(changeVO(impawnVO));
		}
	}

	/**
	 *
	 * 弃审时将审核插入的账户删除
	 */
	public void dealAccount(String pk_action, BusiActionParamVO<T> param)
			throws BusinessException {
		CommonDAO dao = new CommonDAO();
		dao.delAccountDetailByActionPK(pk_action);
	}
}