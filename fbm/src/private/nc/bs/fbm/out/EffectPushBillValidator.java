package nc.bs.fbm.out;

import nc.vo.fbm.outer.ArapBillParamVO;
import nc.vo.pub.BusinessException;

public class EffectPushBillValidator extends AbstractBillValidator {
	public void doCheck(ArapBillParamVO param)throws BusinessException {
		// TODO Auto-generated method stub
		if(billnoSet.contains(param.getFbmbillno())){
			return;
		}
	}

	@Override
	public void checkBaseinfo(ArapBillParamVO param)throws BusinessException {
		// �շ���������ɾ��

	}

	public void afterAction(ArapBillParamVO param)
			throws BusinessException {
		
		outRelDao.effectRelation(param.getPk_bill_b());
		super.afterAction(param);
	}

}
