package nc.bs.fbm.consignbank;

import nc.bs.trade.business.HYPubBO;
import nc.vo.fbm.consignbank.CollectionVO;
import nc.vo.pub.BusinessException;

public class ConsignBankService {
	
	/**
	 * <p>
	 * ����֤ʱ�ı���������״̬��д����֤�ˣ���֤���ڡ�
	 * <p>
	 * ���ߣ�wangyg
	 * ���ڣ�2008-3-20
	 * @param obj
	 * @param statusType
	 */
	public CollectionVO updateUnitVoucher(CollectionVO vo)throws BusinessException
	{
		HYPubBO bo = new HYPubBO();
		bo.update(vo);
		return (CollectionVO)bo.queryByPrimaryKey(CollectionVO.class, vo.getPrimaryKey());
	}

}
