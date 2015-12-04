package nc.bs.fbm.out;

import nc.bs.fbm.gather.GatherBillService;
import nc.vo.fbm.outer.ArapBillParamVO;
import nc.vo.fbm.pub.ActionVO;
import nc.vo.fbm.pub.constant.FbmStatusConstant;
import nc.vo.fbm.register.RegisterVO;
import nc.vo.pub.BusinessException;

public class ArUneffectBillValidator extends AbstractBillValidator {

	public void checkBaseinfo(ArapBillParamVO param) throws BusinessException {

	}

	public void doCheck(ArapBillParamVO param)
			throws BusinessException {
		//如果是反向操作如果没有找到关联关系则直接返回
		if(param.getOuterVO() == null){
			return;
		}
		
		if(billnoSet.contains(param.getFbmbillno())){
			return;
		}
		if(param.getNewActionVO() == null){
			throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000389")/* @res"找不到票据业务操作"*/);
		}
		String curStatus = param.getNewActionVO().getEndstatus();
		if (!curStatus.equals(FbmStatusConstant.REGISTER)) {
			throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000226")/* @res"票据状态不是已登记，无法取消生效"*/);
		}
		//校验动作
		if(param.getOuterVO() == null){
			throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000388")/* @res"找不到票据和收付报单据的关联关系"*/);
		}
		ActionVO curAction = actionDao.queryNewestActionByPk_bill(param.getOuterVO().getPk_busibill());
		if(curAction == null || curAction.getSerialnum().intValue()<param.getNewActionVO().getSerialnum().intValue()){
			throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000215")/* @res"票据已进行后续业务操作"*/);
		}
	}

	public void afterAction(ArapBillParamVO param)
			throws BusinessException {
		
		//如果是反向操作如果没有找到关联关系则直接返回
		if(param.getOuterVO() == null){
			return;
		}

		RegisterVO regVO = (RegisterVO) baseDao.retrieveByPK(RegisterVO.class,
				param.getNewActionVO().getPk_source());

		// 维护外部关联
		outRelDao.uneffectRelation(param.getOuterVO().getPk_outerbill_b());

		// 设置登记表收付款标志
		GatherBillService service = new GatherBillService();
		service.updateSfflagAndPlan(regVO.getPrimaryKey(), false,param.getPk_plansubj());


		super.afterAction(param);
	}

}