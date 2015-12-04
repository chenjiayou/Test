package nc.bs.fbm.accpet.action;

import nc.vo.fbm.acceptbill.AcceptVO;
import nc.vo.fbm.pub.ActionVO;
import nc.vo.fbm.pub.BusiActionParamVO;
import nc.vo.fbm.pub.constant.FbmStatusConstant;
import nc.vo.fbm.pub.constant.IFBMStatus;
import nc.vo.fbm.register.RegisterVO;
import nc.vo.pub.BusinessException;
import nc.vo.trade.pub.IBillStatus;

public class SaveAccept<K extends AcceptVO, T extends AcceptVO> extends ActionAccept<K, T> {

	@Override
	protected String doCheck(BusiActionParamVO<T> param) throws BusinessException {

		ActionVO actionVO = param.getLastActionVO();
		if(actionVO == null){
			return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000238")/* @res"�Ҳ���Ʊ�ݶ���"*/;
		}

		//2007.11.27 xwq�жҸ���������Ʊ�ݵ�����֮��ɲ�У��״̬
		if(actionVO.getEndstatus().equals(FbmStatusConstant.HAS_DESTROY)){
			return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000239")/* @res"Ʊ��"*/ + param.getBaseinfoVO().getFbmbillno() + nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000318")/* @res"�Ѻ������޷��ж�"*/;
		}

		if(actionVO.getEndstatus().equals(FbmStatusConstant.HAS_PAY) || actionVO.getEndstatus().equals(FbmStatusConstant.ON_PAY)){
			return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000239")/* @res"Ʊ��"*/+ param.getBaseinfoVO().getFbmbillno() + nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000319")/* @res"�ڸ�����Ѹ���޷��ж�"*/;
		}

		//У��ҵ������
		if(actionVO.getActiondate().after(param.getActiondate())){
			return param.getBaseinfoVO().getFbmbillno()+nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000320")/* @res"��ҵ����������ǰһ����ҵ������"*/;
		}

		//У�鸶Ʊ�Ƿ����
		RegisterVO regVO = param.getRegisterVO();
		if(regVO != null){
			if(regVO.getVbillstatus().intValue() != IBillStatus.CHECKPASS && regVO.getVbillstatus().intValue() !=  IFBMStatus.Create){
				throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000321")/* @res"��Ʊ�Ǽǵ�״̬����Ϊ���ͨ���������ɵ���"*/);
			}
		}

		//У���Ƿ������ɻ��˵�
//		ICreateCorpQueryService ProductService = (ICreateCorpQueryService) NCLocator.getInstance().lookup(ICreateCorpQueryService.class.getName());
//		Hashtable productEnabled = ProductService.queryProductEnabled(param.getRegisterVO().getPk_corp(),new String[]{ProductCode.PROD_EP});
//		if(((UFBoolean)productEnabled.get(ProductInfo.pro_EP)).booleanValue()){//�������ո�����У��
//			OuterRelationDAO relDao = new OuterRelationDAO();
//			if(!relDao.isHjEffect(param.getPk_source())){
//				throw  new BusinessException("��Ʊ�ǼǵĻ��˽��㵥δ���ɻ�δ��Ч");
//			}
//		}

		return null;
	}

	@Override
	protected String getBeginStatus(BusiActionParamVO<T> param)
			throws BusinessException {
		return FbmStatusConstant.HAS_PAYBILL;
	}

	@Override
	protected String getEndStatus(BusiActionParamVO<T> param) throws BusinessException {
		return FbmStatusConstant.ON_PAY;
	}

}