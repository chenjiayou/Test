package nc.bs.fbm.relief.action;

import nc.bs.fbm.pub.action.BusiMessageTranslator;
import nc.vo.fbm.pub.ActionVO;
import nc.vo.fbm.pub.BusiActionParamVO;
import nc.vo.fbm.pub.constant.FbmActionConstant;
import nc.vo.fbm.pub.constant.FbmBusConstant;
import nc.vo.fbm.pub.constant.FbmStatusConstant;
import nc.vo.fbm.relief.ReliefVO;
import nc.vo.pub.BusinessException;
import nc.vo.trade.pub.HYBillVO;

/**
 * 
 * 类功能说明： 托收出库删除 日期：2007-10-24 程序员： wues
 * 
 */
public class DeleteRelief<K extends HYBillVO,T extends ReliefVO> extends ActionRelief<K,T>{
	@Override
	protected String getBeginStatus(BusiActionParamVO<T> param)
			throws BusinessException {
		// 根据调剂单位和收票登记单确定此收票登记单是否为自己的票据
		// if (ReliefUtil.isSelfBill(param)) {
		// //如果是自己的票仍为已调剂存
		// return FbmStatusConstant.HAS_RELIEF_KEEP;
		// } else
		// 否则票据状态就为在调剂
		return FbmStatusConstant.ON_RELIEF;
	}

	@Override
	protected String getEndStatus(BusiActionParamVO<T> param) throws BusinessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected String doCheck(BusiActionParamVO<T> param) throws BusinessException {
		String commonError = commonCheck(param);
		if (commonError != null)
			return commonError;

		ActionVO actionVO = param.getLastActionVO();
		// 校验票据动作
		if (!(actionVO.getActioncode().equals(FbmActionConstant.SAVE)
				&& actionVO.getBilltype()
						.equals(FbmBusConstant.BILLTYPE_RELIEF) && actionVO
				.getPk_bill().equals(param.getPk_bill()))) {
			return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID(
					"fbmcomm", "UPPFBMComm-000239")/* @res"票据" */
					+ param.getBaseinfoVO().getFbmbillno()
					+ nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID(
							"fbmcomm", "UPPFBMComm-000275")/* @res"的前一操作必须是保存调剂单。" */
					+ BusiMessageTranslator.translateAction(param);
		}
		return null;
	}

}