package nc.bs.fbm.gather.action;

import nc.bs.pub.pf.IQueryData;
import nc.bs.trade.business.HYPubBO;
import nc.bs.trade.business.HYSuperDMO;
import nc.vo.fbm.pub.BaseinfoVO;
import nc.vo.fbm.register.RegisterVO;
import nc.vo.pub.BusinessException;
import nc.vo.pub.CircularlyAccessibleValueObject;

public class GatherBatchApproveDMO extends HYSuperDMO implements IQueryData {

	public CircularlyAccessibleValueObject[] queryAllBodyData(String key)
			throws BusinessException {
//		CircularlyAccessibleValueObject[] voResults = null;
//		StringBuffer whereString = new StringBuffer();
//		whereString.append(" pk_register = '")
//				   .append(key)
//				   .append("' ")
//				   .append(" and isnull(dr,0) = 0 ");
//		try {
//			voResults = new HYPubBO().queryByCondition(RegisterVO.class,whereString.toString());
//		} catch(Exception e) {
//			throw new BusinessException(e.getMessage());
//		}
//		return voResults;
		return null;
	}

	public CircularlyAccessibleValueObject[] queryAllHeadData(String whereString)
			throws BusinessException {
		CircularlyAccessibleValueObject[] voResults = null;
		HYPubBO pubbo = new HYPubBO();
		try {
			voResults = pubbo.queryByCondition(RegisterVO.class,whereString);
			if(voResults!=null&&voResults.length>0){
				for(int i=0;i<voResults.length;i++){
					String pk_baseinfo = (String)voResults[0].getAttributeValue("pk_baseinfo");
					BaseinfoVO basevo = (BaseinfoVO)pubbo.queryByPrimaryKey(BaseinfoVO.class, pk_baseinfo);
					voResults[0].setAttributeValue("invoicedate", basevo.getInvoicedate());
				}
			}
		} catch(Exception e) {
			throw new BusinessException(e.getMessage());
		}
		return voResults;
	}

}
