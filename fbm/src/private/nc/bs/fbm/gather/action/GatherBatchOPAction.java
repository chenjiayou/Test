package nc.bs.fbm.gather.action;

import nc.bs.pub.action.N_36GG_APPROVE;
import nc.bs.pub.action.N_36GG_SAVE;
import nc.bs.pub.action.N_36GI_APPROVE;
import nc.bs.pub.action.N_36GI_SAVE;
import nc.bs.pub.action.N_36GI_TRANSACT;
import nc.bs.pub.action.N_36GO_APPROVE;
import nc.bs.pub.action.N_36GO_SAVE;
import nc.bs.trade.business.HYPubBO;
import nc.vo.fbm.consignbank.CollectionVO;
import nc.vo.fbm.discount.DiscountVO;
import nc.vo.fbm.impawn.ImpawnVO;
import nc.vo.fbm.proxy.OuterProxy;
import nc.vo.fbm.pub.constant.FbmBusConstant;
import nc.vo.pub.BusinessException;
import nc.vo.pub.compiler.PfParameterVO;
import nc.vo.pub.pf.workflow.IPFActionName;
import nc.vo.trade.pub.HYBillVO;

public class GatherBatchOPAction {

	/**
	 * 银行托收--批量保存
	 * 
	 * @param hybo
	 * @return
	 */
	public void batchSaveBankConsign(HYBillVO hybo) throws Exception {
		CollectionVO collection = (CollectionVO) hybo.getParentVO();
		String billNo = null;
		try {
			billNo = new HYPubBO().getBillNo(FbmBusConstant.BILLTYPE_COLLECTION_UNIT, collection.getPk_corp(), null, null);
			collection.setVbillno(billNo);

			N_36GI_SAVE consignSave = new N_36GI_SAVE();
			PfParameterVO pfvo = new PfParameterVO();
			pfvo.m_preValueVo = hybo;
			pfvo.m_billType = "36GI";
			pfvo.m_preValueVo.setParentVO(collection);

			consignSave.runComClass(pfvo);
		} catch (BusinessException e) {
			try {
				OuterProxy.getBillCodeRuleService().returnBillCodeOnDelete(collection.getPk_corp(), FbmBusConstant.BILLTYPE_COLLECTION_UNIT, billNo, null);
			} catch (BusinessException e1) {
				throw e1;
			}
			throw e;
		}
	}

	/**
	 * 银行托收 批量审核
	 */
	public void consignBankBatchApprove(HYBillVO hybo, String currentdate,
			String operator) throws Exception {
		CollectionVO collection = (CollectionVO) hybo.getParentVO();
		N_36GI_APPROVE consignApprove = new N_36GI_APPROVE();
		PfParameterVO pfvo = new PfParameterVO();
		pfvo.m_preValueVo = hybo;
		pfvo.m_billType = "36GI";
		pfvo.m_billId = collection.getPrimaryKey();
		pfvo.m_operator = collection.getVoperatorid();
		pfvo.m_actionName = IPFActionName.APPROVE;

		pfvo.m_currentDate = currentdate;
		pfvo.m_operator = operator;
		hybo.setParentVO(collection);
		pfvo.m_preValueVo.setParentVO(hybo.getParentVO());
		try {
			consignApprove.runComClass(pfvo);
		} catch (BusinessException e) {
			e.printStackTrace();
			throw e;
		}
	}

	/**
	 * 银行托收--批量办理
	 * 
	 * @param vec
	 * @param currentdate
	 * @param operator
	 * @return
	 */
	public void consignBankBatchTransact(HYBillVO hybo, String currentdate,
			String operator) throws Exception {
		CollectionVO collection = (CollectionVO) hybo.getParentVO();
		N_36GI_TRANSACT consignTransact = new N_36GI_TRANSACT();
		PfParameterVO pfvo = new PfParameterVO();
		pfvo.m_preValueVo = hybo;
		pfvo.m_billType = "36GI";
		pfvo.m_billId = collection.getPrimaryKey();
		pfvo.m_operator = collection.getVoperatorid();
		pfvo.m_actionName = "TRANSACT";
		hybo.setParentVO(collection);
		pfvo.m_currentDate = currentdate;
		pfvo.m_operator = operator;
		pfvo.m_preValueVo.setParentVO(hybo.getParentVO());
		try {
			consignTransact.runComClass(pfvo);
		} catch (BusinessException e) {
			throw e;
		}
	}

	/**
	 * 贴现申请 --批量保存
	 * 
	 * @param hybo
	 * @return
	 */
	public void DiscountbatchSave(HYBillVO hybo) throws Exception {
		DiscountVO collection = (DiscountVO) hybo.getParentVO();
		String billNo = null;
		try {
			billNo = new HYPubBO().getBillNo(FbmBusConstant.BILLTYPE_DISCOUNT_TRANSACT, collection.getPk_corp(), null, null);
			collection.setVbillno(billNo);
			N_36GG_SAVE discountSave = new N_36GG_SAVE();
			PfParameterVO pfvo = new PfParameterVO();
			pfvo.m_preValueVo = hybo;
			pfvo.m_billType = "36GG";
			pfvo.m_preValueVo.setParentVO(collection);
			discountSave.runComClass(pfvo);
		} catch (BusinessException e) {
			try {
				OuterProxy.getBillCodeRuleService().returnBillCodeOnDelete(collection.getPk_corp(), FbmBusConstant.BILLTYPE_DISCOUNT_TRANSACT, billNo, null);
			} catch (BusinessException e1) {
				throw e1;
			}
			e.printStackTrace();
			throw e;
		}
	}

	/**
	 * 贴现申请 批量审核
	 */
	public void DiscountBatchApprove(HYBillVO hybo, String currentdate,
			String operator) throws Exception {
		DiscountVO discountvo = (DiscountVO) hybo.getParentVO();
		N_36GG_APPROVE consignApprove = new N_36GG_APPROVE();
		PfParameterVO pfvo = new PfParameterVO();
		pfvo.m_preValueVo = hybo;
		pfvo.m_billType = "36GG";
		pfvo.m_billId = discountvo.getPrimaryKey();
		pfvo.m_operator = discountvo.getVoperatorid();
		pfvo.m_actionName = IPFActionName.APPROVE;

		pfvo.m_currentDate = currentdate;
		pfvo.m_operator = operator;
		pfvo.m_preValueVo.setParentVO(hybo.getParentVO());
		try {
			consignApprove.runComClass(pfvo);
		} catch (BusinessException e) {
			throw e;
		}
	}

	/**
	 * 质押 --批量保存
	 * 
	 * @param hybo
	 * @return
	 */
	public void ImpawnbatchSave(HYBillVO hybo) throws Exception {
		ImpawnVO impawnvo = (ImpawnVO) hybo.getParentVO();
		String billNo = null;
		try {
			billNo = new HYPubBO().getBillNo(FbmBusConstant.BILLTYPE_IMPAWN, impawnvo.getPk_corp(), null, null);
			impawnvo.setVbillno(billNo);
			N_36GO_SAVE discountSave = new N_36GO_SAVE();
			PfParameterVO pfvo = new PfParameterVO();
			pfvo.m_preValueVo = hybo;
			pfvo.m_billType = "36GO";
			pfvo.m_preValueVo.setParentVO(impawnvo);

			discountSave.runComClass(pfvo);
		} catch (BusinessException e) {
			try {
				OuterProxy.getBillCodeRuleService().returnBillCodeOnDelete(impawnvo.getPk_corp(), FbmBusConstant.BILLTYPE_IMPAWN, billNo, null);
			} catch (BusinessException e1) {
				throw e1;
			}
			throw e;
		}
	}

	/**
	 * 质押 批量审核
	 */
	public void ImpawnBatchApprove(HYBillVO hybo, String currentdate,
			String operator) throws Exception {
		ImpawnVO impawnvo = (ImpawnVO) hybo.getParentVO();
		N_36GO_APPROVE consignApprove = new N_36GO_APPROVE();
		PfParameterVO pfvo = new PfParameterVO();
		pfvo.m_preValueVo = hybo;
		pfvo.m_billType = "36GO";
		pfvo.m_billId = impawnvo.getPrimaryKey();
		pfvo.m_operator = impawnvo.getVoperatorid();
		pfvo.m_actionName = IPFActionName.APPROVE;

		pfvo.m_currentDate = currentdate;
		pfvo.m_operator = operator;
		pfvo.m_preValueVo.setParentVO(hybo.getParentVO());
		try {
			consignApprove.runComClass(pfvo);
		} catch (BusinessException e) {
			throw e;
		}
	}

}
