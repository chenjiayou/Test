package nc.bs.fbm.endore.action;

import nc.bs.fbm.pub.FbmCommonCheck;
import nc.vo.fbm.endore.EndoreVO;
import nc.vo.fbm.pub.constant.FbmBusConstant;
import nc.vo.pub.AggregatedValueObject;
import nc.vo.pub.BusinessException;

public class EndoreBSCheck implements nc.vo.trade.pub.IBDACTION,
		nc.bs.trade.business.IBDBusiCheck {

	public void check(int intBdAction, AggregatedValueObject vo, Object userObj)
			throws Exception {
		if (intBdAction == SAVE)
			onCheckSave(vo);
	}

	private void onCheckSave(AggregatedValueObject vo) throws Exception {
		EndoreVO headVo = (EndoreVO) vo.getParentVO();
		String pk_corp = headVo.getPk_corp();
		String opbilltype = headVo.getOpbilltype();
		boolean arapFlag = FbmCommonCheck.isStartARAP(pk_corp);
		boolean paramFlag = "Y".equals(FbmCommonCheck.getParamValue(pk_corp));

		if (!arapFlag && paramFlag) {
			throw new BusinessException(
					nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add", "UPP3620ADD-000030")/*
																										 * @res
																										 * "背书办理单是否与收付报单据集成应用参数值 与\n　启用收付报标识不一致！"
																										 */);
		}
		if (FbmBusConstant.BILL_PRIVACY.equals(opbilltype)
				&& "Y".equals(FbmCommonCheck.getParamValue(pk_corp))) {
			throw new BusinessException(
					nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add", "UPP3620ADD-000028")/*
																										 * @res
																										 * "已启用收付报不能对私有票据进行保存。"
																										 */);
		}
	}
	public void dealAfter(int intBdAction, AggregatedValueObject billVo,
			Object userObj) throws Exception {
		// TODO Auto-generated method stub

	}

}
