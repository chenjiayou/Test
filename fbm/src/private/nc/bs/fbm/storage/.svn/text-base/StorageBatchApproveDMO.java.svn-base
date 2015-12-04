package nc.bs.fbm.storage;

import nc.bs.pub.pf.IQueryData;
import nc.bs.trade.business.HYPubBO;
import nc.bs.trade.business.HYSuperDMO;
import nc.vo.fbm.storage.StorageVO;
import nc.vo.pub.BusinessException;
import nc.vo.pub.CircularlyAccessibleValueObject;

public class StorageBatchApproveDMO extends HYSuperDMO implements IQueryData {

	public CircularlyAccessibleValueObject[] queryAllBodyData(String key)
			throws BusinessException {
		CircularlyAccessibleValueObject[] voResults = null;
		StringBuffer whereString = new StringBuffer();
		whereString.append(" pk_storage = '").append(key).append("' ").append(
				" and isnull(dr,0) = 0 ");
		try {
			voResults = new HYPubBO().queryByCondition(StorageVO.class,
					whereString.toString());
		} catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		return voResults;
	}

	public CircularlyAccessibleValueObject[] queryAllHeadData(String whereString)
			throws BusinessException {
		CircularlyAccessibleValueObject[] voResults = null;
		try {
			voResults = new HYPubBO().queryByCondition(StorageVO.class,
					whereString);
		} catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		return voResults;
	}

}
