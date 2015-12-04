package nc.bs.fbm.out;

import nc.vo.fbm.outer.ArapBillParamVO;
import nc.vo.fbm.pub.constant.FbmStatusConstant;
import nc.vo.pub.BusinessException;

public class ArDelBillValidator extends AbstractBillValidator {

	public void doCheck(ArapBillParamVO param)
			throws BusinessException {
		//如果是反向操作如果没有找到关联关系则直接返回
		if(param.getOuterVO() == null){
			return;
		}
		
		if(billnoSet.contains(param.getFbmbillno())){
			return;
		}
		// 检查票据状态是否已登记
		if(param.getNewActionVO() == null){
			throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000389")/* @res"找不到票据业务操作"*/);
		}
		if (!param.getNewActionVO().getEndstatus().equals(FbmStatusConstant.REGISTER)) {
			throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000222")/* @res"票据状态不是已登记，无法保存"*/);
		}
	}

	public void afterAction(ArapBillParamVO param)
			throws BusinessException {
		if (param.getOuterVO() != null && param.getOuterVO().getPk_outerbill_b() != null) {
			outRelDao.deleteRelation(param.getOuterVO().getPk_outerbill_b());	
		}
		super.afterAction(param);
	}

}