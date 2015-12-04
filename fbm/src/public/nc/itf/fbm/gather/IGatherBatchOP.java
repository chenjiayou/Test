package nc.itf.fbm.gather;

import nc.vo.trade.pub.HYBillVO;

public interface IGatherBatchOP {

	/**
	 * 收票--批量银行托收
	 * 
	 * @param hybo
	 * @param currentdate
	 * @param operator
	 * @return
	 * @throws Exception
	 */
	public void gatherOp_RequiresNew(HYBillVO hybo, String currentdate,
			String operator) throws Exception;

	/**
	 * 收票--批量质量
	 * 
	 * @param hybo
	 * @param currentdate
	 * @param operator
	 * @return
	 * @throws Exception
	 */
	public void gatherImpawn_RequiresNew(HYBillVO hybo, String currentdate,
			String operator) throws Exception;

	/**
	 * 收票--批量贴现
	 * 
	 * @param hybo
	 * @param currentdate
	 * @param operator
	 * @return
	 * @throws Exception
	 */
	public void gatherDiscount_RequiresNew(HYBillVO hybo, String currentdate,
			String operator) throws Exception;
}
