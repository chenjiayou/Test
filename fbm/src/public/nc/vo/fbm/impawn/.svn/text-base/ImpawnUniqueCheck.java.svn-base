package nc.vo.fbm.impawn;

import nc.vo.pub.AggregatedValueObject;

/**
 * 
 * 功能： 票据质押的唯一性校验 日期：2007-10-8 程序员：wues
 */
public class ImpawnUniqueCheck implements nc.vo.trade.pub.IBDACTION,
		nc.bs.trade.business.IBDBusiCheck {

	public void check(int intBdAction, AggregatedValueObject vo, Object userObj)
			throws Exception {

		if (intBdAction == SAVE) {
			checkOnSave(vo);
		}
	}

	private void checkOnSave(AggregatedValueObject vo) throws Exception {
		// 唯一性校验的工具类
//		BillIsUnique check = new BillIsUnique();
//		java.util.ArrayList unilist = new java.util.ArrayList();
//		unilist.add(new String[] { "pk_source"});
//		try {
//			check.checkBillIsUnique(vo, unilist);
//		} catch (BusinessException e) {
//			throw new BusinessException("此票据已经质押，不能重复质押");
//		}
	}

	public void dealAfter(int intBdAction, AggregatedValueObject billVo,
			Object userObj) throws Exception {
	}

}
