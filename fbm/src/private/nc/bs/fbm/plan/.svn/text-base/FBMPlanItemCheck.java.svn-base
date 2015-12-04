package nc.bs.fbm.plan;

import nc.vo.bd.service.IBDOperate;
import nc.vo.pub.BusinessException;

public class FBMPlanItemCheck implements IBDOperate {

	public void afterOperate(String fun_code, int opType, String pk1,
			String pk2, Object bd_docData) throws BusinessException {
		// TODO Auto-generated method stub

	}

	public void beforeOperate(String fun_code, int opType, String pk1,
			String pk2, Object bd_docData) throws BusinessException {
		switch(opType){
		case IBDOperate.BDOPERATION_DEL:
			FBMPlanItemAssist fbmPlanAssist = new FBMPlanItemAssist();
			String msg = fbmPlanAssist.getCheckMessage(pk1);
			if(msg!=null && !"".equals(msg)){
				throw new BusinessException(msg);
			}
			break;
		}
	}
	

}
