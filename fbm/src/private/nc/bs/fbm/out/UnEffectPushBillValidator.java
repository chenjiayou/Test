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
		//校验动作
		if(param.getOuterVO() == null){
			throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000388")/* @res"找不到票据和收付报单据的关联关系"*/);
		}
		if(param.getOuterdjdl().equals("hj")){
			if(!param.getNewActionVO().getPk_bill().equals(param.getOuterVO().getPk_busibill())){
				throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000215")/* @res"票据已进行后续业务操作"*/);
			}
		}
	}

	public void afterAction(ArapBillParamVO param)
			throws BusinessException {
		outRelDao.uneffectRelation(param.getPk_bill_b());
		super.afterAction(param);
	}

}