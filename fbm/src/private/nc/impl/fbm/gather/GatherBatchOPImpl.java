package nc.impl.fbm.gather;

import nc.bs.fbm.gather.action.GatherBatchOPAction;
import nc.itf.fbm.gather.IGatherBatchOP;
import nc.vo.fbm.consignbank.CollectionVO;
import nc.vo.fbm.discount.DiscountVO;
import nc.vo.pub.lang.UFBoolean;
import nc.vo.trade.pub.HYBillVO;

public class GatherBatchOPImpl implements IGatherBatchOP {

	/**
	 * 批量托收
	 */
	public void gatherOp_RequiresNew(HYBillVO hybo, String currentdate,
			String operator) throws Exception {
		GatherBatchOPAction gatherAction = new GatherBatchOPAction();
		UFBoolean writeplan = ((CollectionVO) hybo.getParentVO()).getWriteplan();
		gatherAction.batchSaveBankConsign(hybo);
		CollectionVO collectionvo = (CollectionVO) hybo.getParentVO();
		collectionvo.setWriteplan(writeplan);
		hybo.setParentVO(collectionvo);
		gatherAction.consignBankBatchApprove(hybo, currentdate, operator);
	}

	/**
	 * 批量贴现
	 */
	public void gatherDiscount_RequiresNew(HYBillVO hybo, String currentdate,
			String operator) throws Exception {
		GatherBatchOPAction gatherAction = new GatherBatchOPAction();
		DiscountVO collection = (DiscountVO) hybo.getParentVO();
		UFBoolean writeplan = collection.getWritePlan();
		gatherAction.DiscountbatchSave(hybo);
		DiscountVO collectionvo = (DiscountVO) hybo.getParentVO();
		collectionvo.setWritePlan(writeplan);
		hybo.setParentVO(collectionvo);
		gatherAction.DiscountBatchApprove(hybo, currentdate, operator);

	}

	/**
	 * 批量质押
	 */
	public void gatherImpawn_RequiresNew(HYBillVO hybo, String currentdate,
			String operator) throws Exception {
		GatherBatchOPAction gatherAction = new GatherBatchOPAction();
		gatherAction.ImpawnbatchSave(hybo);
		gatherAction.ImpawnBatchApprove(hybo, currentdate, operator);

	}

}
