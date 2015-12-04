package nc.bs.fbm.out;

import nc.bs.fbm.pub.OuterRelationDAO;
import nc.vo.fbm.outer.ArapBillParamVO;
import nc.vo.pub.BusinessException;

public class ReturnBillDel implements IBillValidator {

	public void afterAction(ArapBillParamVO param) throws BusinessException {
		OuterRelationDAO relDao = new OuterRelationDAO();
//		if(param.getOuterVO() == null){
//			throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000388")/* @res"�Ҳ���Ʊ�ݺ��ո������ݵĹ�����ϵ"*/);
//		}
		if(param.getOuterVO()!=null){
			relDao.deleteRelation(param.getOuterVO().getPk_outerbill_b());
		}
	}

	public void beforeAction(ArapBillParamVO param) throws BusinessException {
		// TODO Auto-generated method stub

	}

	public void checkBaseinfo(ArapBillParamVO param) throws BusinessException {
		// TODO Auto-generated method stub

	}

	public void doCheck(ArapBillParamVO param) throws BusinessException {
		// TODO Auto-generated method stub

	}

	public boolean isFbmBill(String pk_jsfs) throws BusinessException {
		// TODO Auto-generated method stub
		return true;
	}

}
