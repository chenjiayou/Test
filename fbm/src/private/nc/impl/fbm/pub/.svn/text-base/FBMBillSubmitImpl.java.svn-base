package nc.impl.fbm.pub;

import nc.bs.dao.BaseDAO;
import nc.bs.framework.common.InvocationInfoProxy;
import nc.bs.framework.common.NCLocator;
import nc.bs.logging.Logger;
import nc.bs.trade.business.HYPubBO;
import nc.bs.uap.lock.PKLock;
import nc.itf.fbm.pub.IFBMBillSubmit;
import nc.itf.fts.pub.ICommon;
import nc.uif.pub.exception.UifException;
import nc.vo.fbm.proxy.OuterProxy;
import nc.vo.fbm.pub.FBMPublicQry;
import nc.vo.fbm.pub.constant.IFBMStatus;
import nc.vo.pub.BusinessException;
import nc.vo.pub.SuperVO;
import nc.vo.pub.lang.UFDate;
import nc.vo.trade.pub.HYBillVO;


public class FBMBillSubmitImpl implements IFBMBillSubmit{
	private final String VBILLSTATUS = "vbillstatus";
	private final String SUBMITMANID = "submitmanid";
	private final String SUBMITDATE = "submitdate";
	private final String DEALDATE = "dealdate";
	private final String AUDITDATE = "dapprovedate";
	private final String BUSINESSNO="businessno";


	public void cancelSubmit(HYBillVO billVO, UFDate operatedate,
			String operator) throws BusinessException {
		SuperVO parentVO = (SuperVO)billVO.getParentVO();
		checkTs(parentVO);
		String[] lockPks = lockBill(parentVO);

		try {
			parentVO.setAttributeValue(VBILLSTATUS, IFBMStatus.CHECKPASS);
			parentVO.setAttributeValue(SUBMITMANID, null);
			parentVO.setAttributeValue(SUBMITDATE, null);
			parentVO.setAttributeValue(DEALDATE, null);
			BaseDAO dao = new BaseDAO();
			dao.updateVO(parentVO);
		} catch (BusinessException e) {
			Logger.error(e.getMessage(), e);
			throw e;
		}finally{
			//单据解锁
			UnLockBill(lockPks);
		}
	}

	public void submit(HYBillVO billVO, UFDate operatedate, String operator)
			throws BusinessException {
		SuperVO parentVO = (SuperVO)billVO.getParentVO();

		//提交日期必须大于审核日期
		UFDate approveDate = (UFDate)parentVO.getAttributeValue(AUDITDATE);
		if(approveDate !=null && approveDate.after(operatedate)){
			throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcode112","UPPFBMCODE112-000044")/*@res "提交日期不能小于审核日期"*/);
		}
		checkTs(parentVO);
		//单据加锁
		String[] lockPks= lockBill(parentVO);
		try {
			parentVO.setAttributeValue(VBILLSTATUS, IFBMStatus.SUBMIT);
			parentVO.setAttributeValue(SUBMITMANID, operator);
			parentVO.setAttributeValue(SUBMITDATE, operatedate);
			UFDate dealdate = getDealDate((String) parentVO
					.getAttributeValue("pk_corp"));
			parentVO.setAttributeValue(DEALDATE, dealdate);
			if (dealdate != null) {
				String corpPk = InvocationInfoProxy.getInstance().getCorpCode();
				String businessno = OuterProxy.getFTSCommonService()
						.getBusinessNo("36LW", corpPk, null, null);
				parentVO.setAttributeValue(BUSINESSNO, businessno);
			}
			BaseDAO dao = new BaseDAO();
			dao.updateVO(parentVO);
		} catch (BusinessException e) {
			Logger.error(e.getMessage(), e);
			throw e;
		}finally{
			// 单据解锁
			UnLockBill(lockPks);		
		}
	}

	private void checkTs(SuperVO parentVO) throws UifException,
			BusinessException {
		HYPubBO bo = new HYPubBO();
		SuperVO newVo = bo.queryByPrimaryKey(parentVO.getClass(), parentVO.getPrimaryKey());
		if(newVo==null){
			throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcode112","UPPFBMCODE112-000045")/*@res "单据已经被删除，请刷新后再试！"*/);
		}

		if(!newVo.getAttributeValue("ts").equals(parentVO.getAttributeValue("ts"))){
			throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcode112","UPPFBMCODE112-000046")/*@res "单据已经被他人修改，请刷新后再试！"*/);
		}
	}

	private void UnLockBill(String[] lockPks) {
		PKLock.getInstance().releaseBatchLock(lockPks, "FBM", "BusLock");
	}

	private String[] lockBill(SuperVO parentVO) throws BusinessException {
		String[] lockPks = new String[]{parentVO.getPrimaryKey()};
		boolean bLock = OuterProxy.getPKLockBS().acquireBatchLock_RequiresNew(lockPks, "FBM");
		if (!bLock) {
			throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcode112","UPPFBMCODE112-000047")/*@res "其他用户正在操作单据，请稍候再试！"*/);
		}
		return lockPks;
	}

	/**
	 *
	 * @param pk_corp
	 * @return
	 * @throws BusinessException
	 */
	private UFDate getDealDate(String pk_corp) throws BusinessException{
		ICommon commonInterface = (ICommon)NCLocator.getInstance().lookup(ICommon.class);
		return commonInterface.getAutoProcessDate(FBMPublicQry.retrivePk_centcorpByPkCorp(pk_corp));
	}

}