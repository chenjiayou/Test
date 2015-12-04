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
			throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000389")/* @res"找不到票据业务操作"*/);
		}
		String curStatus = param.getNewActionVO().getEndstatus();
		if (!curStatus.equals(FbmStatusConstant.REGISTER)) {
			throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000225")/* @res"票据状态不是已登记，无法生效"*/);
		}
	}

	public void afterAction(ArapBillParamVO param)
			throws BusinessException {
		RegisterVO regVO = (RegisterVO) baseDao.retrieveByPK(RegisterVO.class,
				param.getNewActionVO().getPk_source());
		// 维护外部关联
		outRelDao.effectRelation(param.getOuterVO().getPk_outerbill_b());

		// 设置登记表收付款标志
		GatherBillService service = new GatherBillService();
		service.updateSfflagAndPlan(regVO.getPrimaryKey(), true,param.getPk_plansubj());



		super.afterAction(param);
	}
}