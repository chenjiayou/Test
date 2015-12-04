package nc.impl.fbm.outer;

import nc.bs.fbm.pub.OuterRelationDAO;
import nc.itf.fbm.outer.IOuterService;
import nc.vo.fbm.outer.OuterVO;
import nc.vo.pub.BusinessException;

/**
 * 根据业务单据pk(收票登记单、质押单...)
 * @author wues
 *
 */
public class OuterServiceImpl implements IOuterService {

	public OuterVO[] queryOuterVOSByBusiBill(String pk_busibill)
			throws BusinessException {
		OuterRelationDAO dao = new OuterRelationDAO();
		OuterVO[] ret = dao.queryByPkBusibill(pk_busibill);
		return ret;
	}

}