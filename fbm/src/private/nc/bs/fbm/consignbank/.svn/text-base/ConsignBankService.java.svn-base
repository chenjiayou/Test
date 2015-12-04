package nc.bs.fbm.consignbank;

import nc.bs.trade.business.HYPubBO;
import nc.vo.fbm.consignbank.CollectionVO;
import nc.vo.pub.BusinessException;

public class ConsignBankService {
	
	/**
	 * <p>
	 * 　制证时改变银行托收状态，写入制证人，制证日期。
	 * <p>
	 * 作者：wangyg
	 * 日期：2008-3-20
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
