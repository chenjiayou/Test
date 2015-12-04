package nc.bs.fbm.accpet.action;

import nc.bs.pub.pf.IQueryData;
import nc.bs.trade.business.HYPubBO;
import nc.bs.trade.business.HYSuperDMO;
import nc.vo.fbm.acceptbill.AcceptVO;
import nc.vo.pub.BusinessException;
import nc.vo.pub.CircularlyAccessibleValueObject;

public class AcceptBatchApproveDMO extends HYSuperDMO implements IQueryData {

	public CircularlyAccessibleValueObject[] queryAllBodyData(String key)
			throws BusinessException {
		return null;
	}

	public CircularlyAccessibleValueObject[] queryAllHeadData(String whereString)
			throws BusinessException {
		CircularlyAccessibleValueObject[] voResults = null;
		HYPubBO pubbo = new HYPubBO();
		try {
			voResults = pubbo.queryByCondition(AcceptVO.class, whereString);
		} catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		return voResults;
	}
}
