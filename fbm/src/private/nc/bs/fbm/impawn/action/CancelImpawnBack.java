package nc.bs.fbm.impawn.action;

import nc.bs.fbm.impawn.ImpawnDAO;
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
 * ȡ����Ѻ����
 * @author xwq
 *
 * 2008-9-22
 */
public class CancelImpawnBack <K extends ImpawnVO, T extends ImpawnVO> extends ActionImpawn<K, T> {


	@Override
	protected String doCheck(BusiActionParamVO<T> param) throws BusinessException {
		String commonError = commonCheck(param);
		if (commonError != null)
			return commonError;

		ActionVO actionVO = param.getLastActionVO();
		// У��Ʊ�ݶ���
		if (!(actionVO.getActioncode().equals(FbmActionConstant.IMPAWNBACK)
				&& actionVO.getBilltype()
						.equals(FbmBusConstant.BILLTYPE_IMPAWN) && actionVO
				.getPk_bill().equals(param.getPk_bill()))) {
			return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000239")/* @res"Ʊ��"*/ + param.getBaseinfoVO().getFbmbillno()
					+ nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add","UPP3620ADD-000142")/*@res "ǰһ������������Ѻ����"*/
					+ BusiMessageTranslator.translateAction(param);
		}
		return null;
	}
	/**
	 * ���⴦�� ȡ��������Ӧ��Ȩ������¼
	 */
	protected void afterAction(BusiActionParamVO<T>[] param) throws BusinessException {
		ImpawnVO impawnVO = (ImpawnVO) param[0].getViewVO();
		//ȡ����Ѻ����
		cancelimpawnBack(impawnVO);
		//ȡ��������Ȩ����
		cancelUneffectFiImpawn(impawnVO);
	}

	private void cancelimpawnBack(ImpawnVO vo) throws BusinessException {
		ImpawnDAO dao = new ImpawnDAO();
		dao.cancelImpawnBack(vo.getPk_impawn(), vo.getImpawnbackunit(), vo.getImpawnbackdate());//����Ѻ����
	}

	@Override
	protected String getBeginStatus(BusiActionParamVO param)
			throws BusinessException {
		// TODO Auto-generated method stub
		return FbmStatusConstant.REGISTER;
	}

	@Override
	protected String getEndStatus(BusiActionParamVO param)
			throws BusinessException {
		// TODO Auto-generated method stub
		return FbmStatusConstant.HAS_IMPAWN;
	}


	/**
	 * ������Ȩ����
	 *
	 * @param fiImpawnPK
	 * @throws BusinessException
	 */
	private void cancelUneffectFiImpawn(ImpawnVO impawnVO) throws BusinessException {
		if (null == impawnVO || impawnVO.getPk_impawn() == null
				|| "".equals(impawnVO.getPk_impawn().trim())) {
			throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000281")/* @res"Ҫȡ�����ϵ���Ѻ��Ϊ��"*/);
		}
		if (isEnabled(impawnVO.getPk_corp(),ProductInfo.pro_CDM)) {
			OuterProxy.getImpawnService().cancelUnEffectImpawn(changeVO(impawnVO));
		}
	}

}