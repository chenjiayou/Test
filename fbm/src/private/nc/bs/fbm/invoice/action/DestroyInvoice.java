package nc.bs.fbm.invoice.action;

import nc.bs.fbm.pub.CommonDAO;
import nc.bs.fbm.pub.action.BusiMessageTranslator;
import nc.itf.cdm.util.CommonUtil;
import nc.vo.fbm.pub.ActionVO;
import nc.vo.fbm.pub.BusiActionParamVO;
import nc.vo.fbm.pub.constant.FbmActionConstant;
import nc.vo.fbm.pub.constant.FbmBusConstant;
import nc.vo.fbm.pub.constant.FbmStatusConstant;
import nc.vo.fbm.register.RegisterVO;
import nc.vo.pub.BusinessException;


public class DestroyInvoice<K extends RegisterVO,T extends RegisterVO> extends ActionInvoice<K,T>{
	@Override
	protected String doCheck(BusiActionParamVO<T> param) throws BusinessException {
		ActionVO actionVO = param.getLastActionVO();
		String fbmbillno = param.getBaseinfoVO().getFbmbillno();
		if(actionVO == null){
			return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000238")/* @res"�Ҳ���Ʊ�ݶ���"*/;
		}
		//У��״̬
		if(!actionVO.getEndstatus().equals(FbmStatusConstant.REGISTER)&&!actionVO.getEndstatus().equals(FbmStatusConstant.HAS_RETURN)){
			return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000239")/* @res"Ʊ��"*/+ fbmbillno+ nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000276")/* @res"��״̬����"*/+ BusiMessageTranslator.retriveStatusName(FbmStatusConstant.REGISTER)
					+ nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000277")/* @res"����"*/+ BusiMessageTranslator.retriveStatusName(FbmStatusConstant.HAS_RETURN)+ nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000241")/* @res",����ʧ�ܡ�"*/ + BusiMessageTranslator.translateAction(param);
		}
		//У��ҵ������
		if(actionVO.getActiondate().after(param.getActiondate())){
			return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000239")/* @res"Ʊ��"*/+ fbmbillno + nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000243")/* @res"�ĵ�ǰҵ����������ǰһҵ�����ڡ�"*/+ BusiMessageTranslator.translateAction(param) ;
		}

			//У��Ʊ�ݶ���
		if((!(actionVO.getActioncode().equals(FbmActionConstant.AUDIT)
					&& actionVO.getBilltype().equals(FbmBusConstant.BILLTYPE_GATHER)))
			&&(!(actionVO.getActioncode().equals(FbmActionConstant.AUDIT)
					&& actionVO.getBilltype().equals(FbmBusConstant.BILLTYPE_RETURN)))){
			return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000239")/* @res"Ʊ��"*/ + param.getBaseinfoVO().getFbmbillno() + nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000278")/* @res"��ǰһ���������������Ʊ�Ǽǵ����������Ʊ��,�޷�������"*/+ BusiMessageTranslator.translateAction(param);
		}
		return null;
	}

	@Override
	protected String getBeginStatus(BusiActionParamVO<T>param)
			throws BusinessException {
		ActionVO lastaction = param.getLastActionVO();
		String endstatus = lastaction.getEndstatus();
		if(CommonUtil.isNull(endstatus)||!endstatus.equals(FbmStatusConstant.REGISTER)&&!endstatus.equals(FbmStatusConstant.HAS_RETURN)){
			throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000279")/* @res"Ʊ�ݵ�״̬����Ϊ����Ʊ�����ѵǼǣ�����ʧ��"*/);
		}
		return endstatus;
	}

	@Override
	protected String getEndStatus(BusiActionParamVO<T> param) throws BusinessException {
		return FbmStatusConstant.HAS_DESTROY;
	}

	/**
	 * �����Ʊ��ͷƱ��������Ʊ����Ҫ������Ʊ�Ǽǵ�״̬Ϊ�Ѻ���
	 */
	protected void afterAction(BusiActionParamVO<T>[] params) throws BusinessException {
		super.afterAction(params);

		if(CommonUtil.isNull(params)){
			return;
		}

		ActionVO lastaction = params[0].getLastActionVO();
		String endstatus = lastaction.getEndstatus();
		if(CommonUtil.isNull(endstatus)||endstatus.equals(FbmStatusConstant.HAS_RETURN)){
			return;
		}
		String pk_corp = params[0].getRegisterVO().getPk_corp();

		if(CommonUtil.isNull(pk_corp)){
			return;
		}
		RegisterVO registerVo = new CommonDAO().queryLastGather(params[0].getPk_baseinfo(), pk_corp);
		if(registerVo==null)
			throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000280")/* @res"û�ж�Ӧ����Ʊ�Ǽ���Ϣ���޷�������Ʊ"*/);


		nc.bs.fbm.gather.action.DestroyGather<RegisterVO,RegisterVO> gather = new nc.bs.fbm.gather.action.DestroyGather<RegisterVO,RegisterVO>();
		BusiActionParamVO<RegisterVO>[] newparams = gather.build4Destroy(registerVo,FbmActionConstant.DESTROY);
		
		gather.dealGather4Destroy(newparams[0]);
	}
}
