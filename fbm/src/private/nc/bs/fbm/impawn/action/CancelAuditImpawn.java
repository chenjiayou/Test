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
 * ���ܣ� ��Ѻ���� ���ڣ�2007-10-10 ����Ա��wues
 */
public class CancelAuditImpawn<K extends ImpawnVO, T extends ImpawnVO> extends ActionImpawn<K, T> {
	@Override
	protected String doCheck(BusiActionParamVO<T> param) throws BusinessException {
		String commonError = commonCheck(param);
		if (commonError != null)
			return commonError;

		ActionVO actionVO = param.getLastActionVO();
		// У��Ʊ�ݶ���
		if (!(actionVO.getActioncode().equals(FbmActionConstant.AUDIT)
				&& actionVO.getBilltype()
						.equals(FbmBusConstant.BILLTYPE_IMPAWN) && actionVO
				.getPk_bill().equals(param.getPk_bill()))) {
			return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000239")/* @res"Ʊ��"*/ + param.getBaseinfoVO().getFbmbillno()
					+ nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000264")/* @res"��ǰһ���������������Ѻ����"*/
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
	 * ���⴦�� ɾ����Ȩ������¼ ������Ѻ��
	 */
	protected void afterAction(BusiActionParamVO<T>[] param) throws BusinessException {
		ImpawnVO impawnVO = param[0].getSuperVO();
		//ɾ����Ȩ������¼
		deleteFiImpawn(impawnVO);
	}

	/**
	 *
	 * ������Ѻ��VOɾ����Ӧ����Ȩ������Ϣ,ɾ��ʱֻҪ����pk_othersys�жϼ���s
	 *
	 * @param fiImpawnPK
	 * @param fbmImpawnVO
	 * @throws BusinessException
	 */
	private void deleteFiImpawn(ImpawnVO impawnVO) throws BusinessException {
		// �Ŵ�ϵͳ������ɾ������Ȩ����
		if (isEnabled(impawnVO.getPk_corp(),ProductInfo.pro_CDM)) {
			OuterProxy.getImpawnService().deleteImpawn(changeVO(impawnVO));
		}
	}

	/**
	 *
	 * ����ʱ����˲�����˻�ɾ��
	 */
	public void dealAccount(String pk_action, BusiActionParamVO<T> param)
			throws BusinessException {
		CommonDAO dao = new CommonDAO();
		dao.delAccountDetailByActionPK(pk_action);
	}
}