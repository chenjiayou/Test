package nc.bs.fbm.out;

import nc.vo.fbm.outer.ArapBillParamVO;
import nc.vo.fbm.pub.ActionVO;
import nc.vo.pub.BusinessException;

public class UnEffectPushBillValidator extends AbstractBillValidator {

	@Override
	public void checkBaseinfo(ArapBillParamVO param)throws BusinessException {
		// TODO Auto-generated method stub
		if(billnoSet.contains(param.getFbmbillno())){
			return;
		}

	}

	public void doCheck(ArapBillParamVO param)
			throws BusinessException {
		//У�鶯��
		if(param.getOuterVO() == null){
			throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000388")/* @res"�Ҳ���Ʊ�ݺ��ո������ݵĹ�����ϵ"*/);
		}
		if(param.getOuterdjdl().equals("hj")){
			if(!param.getNewActionVO().getPk_bill().equals(param.getOuterVO().getPk_busibill())){
				throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000215")/* @res"Ʊ���ѽ��к���ҵ�����"*/);
			}
		}
	}

	public void afterAction(ArapBillParamVO param)
			throws BusinessException {
		outRelDao.uneffectRelation(param.getPk_bill_b());
		super.afterAction(param);
	}

}