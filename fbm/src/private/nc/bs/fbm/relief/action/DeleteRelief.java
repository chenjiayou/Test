package nc.bs.fbm.relief.action;

import nc.bs.fbm.pub.action.BusiMessageTranslator;
import nc.vo.fbm.pub.ActionVO;
import nc.vo.fbm.pub.BusiActionParamVO;
import nc.vo.fbm.pub.constant.FbmActionConstant;
import nc.vo.fbm.pub.constant.FbmBusConstant;
import nc.vo.fbm.pub.constant.FbmStatusConstant;
import nc.vo.fbm.relief.ReliefVO;
import nc.vo.pub.BusinessException;
import nc.vo.trade.pub.HYBillVO;

/**
 * 
 * �๦��˵���� ���ճ���ɾ�� ���ڣ�2007-10-24 ����Ա�� wues
 * 
 */
public class DeleteRelief<K extends HYBillVO,T extends ReliefVO> extends ActionRelief<K,T>{
	@Override
	protected String getBeginStatus(BusiActionParamVO<T> param)
			throws BusinessException {
		// ���ݵ�����λ����Ʊ�Ǽǵ�ȷ������Ʊ�Ǽǵ��Ƿ�Ϊ�Լ���Ʊ��
		// if (ReliefUtil.isSelfBill(param)) {
		// //������Լ���Ʊ��Ϊ�ѵ�����
		// return FbmStatusConstant.HAS_RELIEF_KEEP;
		// } else
		// ����Ʊ��״̬��Ϊ�ڵ���
		return FbmStatusConstant.ON_RELIEF;
	}

	@Override
	protected String getEndStatus(BusiActionParamVO<T> param) throws BusinessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected String doCheck(BusiActionParamVO<T> param) throws BusinessException {
		String commonError = commonCheck(param);
		if (commonError != null)
			return commonError;

		ActionVO actionVO = param.getLastActionVO();
		// У��Ʊ�ݶ���
		if (!(actionVO.getActioncode().equals(FbmActionConstant.SAVE)
				&& actionVO.getBilltype()
						.equals(FbmBusConstant.BILLTYPE_RELIEF) && actionVO
				.getPk_bill().equals(param.getPk_bill()))) {
			return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID(
					"fbmcomm", "UPPFBMComm-000239")/* @res"Ʊ��" */
					+ param.getBaseinfoVO().getFbmbillno()
					+ nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID(
							"fbmcomm", "UPPFBMComm-000275")/* @res"��ǰһ���������Ǳ����������" */
					+ BusiMessageTranslator.translateAction(param);
		}
		return null;
	}

}