package nc.bs.fbm.gather.action;

import nc.bs.fbm.pub.action.BusiMessageTranslator;
import nc.vo.fbm.pub.ActionVO;
import nc.vo.fbm.pub.BusiActionParamVO;
import nc.vo.fbm.pub.constant.FbmBusConstant;
import nc.vo.fbm.pub.constant.FbmStatusConstant;
import nc.vo.fbm.pub.constant.IFBMStatus;
import nc.vo.fbm.register.RegisterVO;
import nc.vo.fbm.returnbill.ReturnVO;
import nc.vo.pub.BusinessException;

public class SaveGather<K extends RegisterVO, T extends RegisterVO> extends ActionGather<K, T> {

	@Override
	protected String doCheck(BusiActionParamVO<T> param) throws BusinessException {

		ActionVO actionVO = param.getLastActionVO();

		if (actionVO == null) {
			return null;
		}
//		else{
//			/* У��ҵ������
//			 * ͳ��������Ʊʱ����Ӧȡ����Ʊ����С�ڵ�λ������ŵ����ĵ�����ʱ��Ӧ���Ʋ���������Ʊ�Ǽǵ���
//			 * ����ֹ����ͳ�ܵ��ݵĲ�����(���֡����ա�����)
//			 * 	   
//			*/
//			if (actionVO.getActiondate().after(param.getActiondate())) {
//				return "Ʊ�ݵ�ǰҵ����������ǰһҵ�����ڡ�"	+ BusiMessageTranslator.translateAction(param);
//			}
//		}
		

		if (actionVO.getEndstatus().equals(FbmStatusConstant.HAS_DISCOUNT)) {
			return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000322")/* @res"Ʊ�������֣��޷�����"*/ + BusiMessageTranslator.translateAction(param);
		} else if (actionVO.getEndstatus().equals(FbmStatusConstant.HAS_IMPAWN)) {
			return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000323")/* @res"Ʊ������Ѻ���޷���Ʊ"*/ + BusiMessageTranslator.translateAction(param);
		} else if (actionVO.getEndstatus().equals(FbmStatusConstant.HAS_ENDORE)
				|| actionVO.getEndstatus()
						.equals(FbmStatusConstant.HAS_PAYBILL)
				|| actionVO.getEndstatus().equals(FbmStatusConstant.HAS_RELIEF)
				|| actionVO.getEndstatus().equals(FbmStatusConstant.HAS_RETURN)
				|| actionVO.getEndstatus().equals(FbmStatusConstant.HAS_CENTER_USE)) {
			// ��ȷ�����
		} else {
			return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000324")/* @res"Ʊ��ʹ���У��޷���Ʊ��"*/ + BusiMessageTranslator.translateAction(param);
		}

		if (actionVO != null) {
			if (actionVO.getEndstatus().equals(FbmStatusConstant.HAS_PAYBILL)) {// �Ѹ�Ʊ
				// ����Ƿ���������Ч�Ļ��˽��㵥
				// ICreateCorpQueryService ProductService =
				// (ICreateCorpQueryService)
				// NCLocator.getInstance().lookup(ICreateCorpQueryService.class.getName());
				// Hashtable productEnabled =
				// ProductService.queryProductEnabled(param.getRegisterVO().getPk_corp(),new
				// String[]{ProductCode.PROD_EP});
				// if(((UFBoolean)productEnabled.get(ProductInfo.pro_EP)).booleanValue()){//�������ո�����У��
				// OuterRelationDAO relDao = new OuterRelationDAO();
				// if(!relDao.isHjEffect(actionVO.getPk_source())){
				// throw new BusinessException("���鵥λ��Ʊ�ǼǵĻ��˽��㵥δ���ɻ�δ��Ч");
				// }
				// }
			} else if (actionVO.getEndstatus().equals(
					FbmStatusConstant.HAS_RETURN)) {
				// ��Ʊ����Ʊ
				ReturnVO returnVO = (ReturnVO) dao.getBaseDAO().retrieveByPK(
						ReturnVO.class, actionVO.getPk_bill());
				if (returnVO == null) {
					throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000325")/* @res"�Ҳ�����Ӧ����Ʊ��"*/);
				}
				if (returnVO.getVbillstatus().intValue() != IFBMStatus.TRANSFORM) {
					//throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000326")/* @res"��Ʊ��δִ��ת�������޷���Ʊ"*/);
					throw new BusinessException("��Ʊ��ǰ��������Ʊ������Ʊ��δ���������ȵ���Ʊ�ڵ�������ٽ�����Ʊ��");
				}

				if (actionVO.getActiondate().after(param.getActiondate())) {
					throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000327")/* @res"Ʊ��:"*/
							+ param.getBaseinfoVO().getFbmbillno()
							+ nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000243")/* @res"�ĵ�ǰҵ����������ǰһҵ�����ڡ�"*/
							+ BusiMessageTranslator.translateAction(param));
				}

				if (!returnVO.getReturntype().equals(
						FbmBusConstant.BACK_TYPE_GATHER)) {
					throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000328")/* @res"ֻ����Ʊ����Ϊ��Ʊ��Ʊ��Ʊ�ݲ���������Ʊ"*/);
				}
//				RegisterVO newRegisterVO = (RegisterVO) dao
//						.getBaseDAO()
//						.retrieveByPK(RegisterVO.class, actionVO.getPk_source());
//				if (!newRegisterVO.getPk_corp().equals(
//						param.getRegisterVO().getPk_corp())) {
//					throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000329")/* @res"Ʊ����ص�����ҵ��������ڱ���˾"*/);
//				}
			}
		}
		return null;
	}

	@Override
	protected String getBeginStatus(BusiActionParamVO<T> param)
			throws BusinessException {
		// TODO Auto-generated method stub
		return FbmStatusConstant.NONE;
	}

	@Override
	protected String getEndStatus(BusiActionParamVO<T> param) throws BusinessException {
		// TODO Auto-generated method stub
		return FbmStatusConstant.ON_GATHER;
	}

}
