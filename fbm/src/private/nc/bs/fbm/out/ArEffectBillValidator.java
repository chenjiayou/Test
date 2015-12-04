package nc.bs.fbm.out;

import nc.bs.fbm.gather.GatherBillService;
import nc.vo.fbm.outer.ArapBillParamVO;
import nc.vo.fbm.pub.constant.FbmStatusConstant;
import nc.vo.fbm.register.RegisterVO;
import nc.vo.pub.BusinessException;

public class ArEffectBillValidator extends AbstractBillValidator {

	public void checkBaseinfo(ArapBillParamVO param) throws BusinessException {

	}

	public void doCheck(ArapBillParamVO param)
			throws BusinessException {
		if(billnoSet.contains(param.getFbmbillno())){
			return;
		}
		if(param.getNewActionVO() == null){
			throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000389")/* @res"�Ҳ���Ʊ��ҵ�����"*/);
		}
		String curStatus = param.getNewActionVO().getEndstatus();
		if (!curStatus.equals(FbmStatusConstant.REGISTER)) {
			throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000225")/* @res"Ʊ��״̬�����ѵǼǣ��޷���Ч"*/);
		}
	}

	public void afterAction(ArapBillParamVO param)
			throws BusinessException {
		RegisterVO regVO = (RegisterVO) baseDao.retrieveByPK(RegisterVO.class,
				param.getNewActionVO().getPk_source());
		// ά���ⲿ����
		outRelDao.effectRelation(param.getOuterVO().getPk_outerbill_b());

		// ���õǼǱ��ո����־
		GatherBillService service = new GatherBillService();
		service.updateSfflagAndPlan(regVO.getPrimaryKey(), true,param.getPk_plansubj());



		super.afterAction(param);
	}
}