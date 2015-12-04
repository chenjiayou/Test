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
		//����Ƿ���������û���ҵ�������ϵ��ֱ�ӷ���
		if(param.getOuterVO() == null){
			return;
		}
		
		if(billnoSet.contains(param.getFbmbillno())){
			return;
		}
		if(param.getNewActionVO() == null){
			throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000389")/* @res"�Ҳ���Ʊ��ҵ�����"*/);
		}
		String curStatus = param.getNewActionVO().getEndstatus();
		if (!curStatus.equals(FbmStatusConstant.REGISTER)) {
			throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000226")/* @res"Ʊ��״̬�����ѵǼǣ��޷�ȡ����Ч"*/);
		}
		//У�鶯��
		if(param.getOuterVO() == null){
			throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000388")/* @res"�Ҳ���Ʊ�ݺ��ո������ݵĹ�����ϵ"*/);
		}
		ActionVO curAction = actionDao.queryNewestActionByPk_bill(param.getOuterVO().getPk_busibill());
		if(curAction == null || curAction.getSerialnum().intValue()<param.getNewActionVO().getSerialnum().intValue()){
			throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000215")/* @res"Ʊ���ѽ��к���ҵ�����"*/);
		}
	}

	public void afterAction(ArapBillParamVO param)
			throws BusinessException {
		
		//����Ƿ���������û���ҵ�������ϵ��ֱ�ӷ���
		if(param.getOuterVO() == null){
			return;
		}

		RegisterVO regVO = (RegisterVO) baseDao.retrieveByPK(RegisterVO.class,
				param.getNewActionVO().getPk_source());

		// ά���ⲿ����
		outRelDao.uneffectRelation(param.getOuterVO().getPk_outerbill_b());

		// ���õǼǱ��ո����־
		GatherBillService service = new GatherBillService();
		service.updateSfflagAndPlan(regVO.getPrimaryKey(), false,param.getPk_plansubj());


		super.afterAction(param);
	}

}