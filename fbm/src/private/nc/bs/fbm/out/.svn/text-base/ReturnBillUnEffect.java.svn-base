package nc.bs.fbm.out;

import nc.bs.fbm.pub.OuterRelationDAO;
import nc.vo.fbm.outer.ArapBillParamVO;
import nc.vo.pub.BusinessException;

public class ReturnBillUnEffect implements IBillValidator {

	public void afterAction(ArapBillParamVO param) throws BusinessException {
		// 维护外部关联
		OuterRelationDAO relDao = new OuterRelationDAO();
//		if(param.getOuterVO() == null){
//			throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000388")/* @res"找不到票据和收付报单据的关联关系"*/);
//		}
		if(param.getOuterVO()!= null){
			relDao.uneffectRelation(param.getOuterVO().getPk_outerbill_b());
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
