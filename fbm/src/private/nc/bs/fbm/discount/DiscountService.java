package nc.bs.fbm.discount;

import nc.bs.trade.business.HYPubBO;
import nc.vo.fbm.discount.DiscountVO;
import nc.vo.pub.BusinessException;

public class DiscountService {
	/**
	 * <p>
	 * 制证时改变贴现办理状态，写入制证人，制证日期。
	 * <p>
	 * 作者：wangyg 日期：2008-3-20
	 * 
	 * @param obj
	 * @param statusType
	 */
	public DiscountVO updateUnitVoucher(DiscountVO discountvo)
			throws BusinessException {
		HYPubBO bo = new HYPubBO();
		bo.update(discountvo);
		return (DiscountVO)bo.queryByPrimaryKey(DiscountVO.class, discountvo.getPrimaryKey());
	}

 
	
}
