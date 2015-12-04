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
 * ���ܣ�
       ɾ����Ѻ��¼
 * ���ڣ�2007-10-10
 * ����Ա��wues
 */
public class DeleteImpawn<K extends ImpawnVO, T extends ImpawnVO> extends ActionImpawn<K, T> {
	@Override
	protected String doCheck(BusiActionParamVO<T> param) throws BusinessException {
		String commonError = commonCheck(param);
		if(commonError != null)return commonError;

		ActionVO actionVO = param.getLastActionVO();
		//У��Ʊ�ݶ���
		if(!(actionVO.getActioncode().equals(FbmActionConstant.SAVE)
				&& actionVO.getBilltype().equals(FbmBusConstant.BILLTYPE_IMPAWN)
				&& actionVO.getPk_bill().equals(param.getPk_bill()))){
			return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000239")/* @res"Ʊ��"*/ + param.getBaseinfoVO().getFbmbillno() + nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000252")/* @res"��ǰһ���������Ǳ�����Ѻ����"*/+ BusiMessageTranslator.translateAction(param);
		}
		return null;
	}

	//ǰ��״̬Ϊ����Ѻ
	protected String getBeginStatus(BusiActionParamVO<T> param) throws BusinessException {
		return  FbmStatusConstant.ON_IMPAWN;
	}
	//����״̬
	protected String getEndStatus(BusiActionParamVO<T> param) throws BusinessException {
		// TODO Auto-generated method stub
		return null;
	}

}