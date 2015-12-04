package nc.bs.fbm.consignbank.action;

import nc.bs.dao.BaseDAO;
import nc.bs.fbm.impawn.ImpawnDAO;
import nc.bs.fbm.pub.CommonDAO;
import nc.bs.fbm.pub.action.BusiMessageTranslator;
import nc.jdbc.framework.processor.ColumnProcessor;
import nc.vo.fbm.account.AccountDetailVO;
import nc.vo.fbm.consignbank.CollectionVO;
import nc.vo.fbm.pub.ActionVO;
import nc.vo.fbm.pub.BaseinfoVO;
import nc.vo.fbm.pub.BusiActionParamVO;
import nc.vo.fbm.pub.constant.FbmActionConstant;
import nc.vo.fbm.pub.constant.FbmBusConstant;
import nc.vo.fbm.pub.constant.FbmStatusConstant;
import nc.vo.fbm.pub.constant.IFBMStatus;
import nc.vo.pub.BusinessException;

public class TransactCollection<K extends CollectionVO, T extends CollectionVO> extends ActionCollection<K, T> {

	@Override
	protected String doCheck(BusiActionParamVO<T> param) throws BusinessException {
		String commonError = commonCheck(param);
		if(commonError != null)return commonError;

		ActionVO actionVO = param.getLastActionVO();
			//校验票据动作
		if(!(actionVO.getActioncode().equals(FbmActionConstant.AUDIT)
					&& actionVO.getBilltype().equals(FbmBusConstant.BILLTYPE_COLLECTION_UNIT)
					&& actionVO.getPk_bill().equals(param.getPk_bill()))){
			return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000239")/* @res"票据"*/ + param.getBaseinfoVO().getFbmbillno() + nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000260")/* @res"的前一操作必须是审核银行托收单。"*/+ BusiMessageTranslator.translateAction(param);
		}
		return null;
	}

	@Override
	protected String getBeginStatus(BusiActionParamVO<T> param)
			throws BusinessException {
		// TODO Auto-generated method stub
		return FbmStatusConstant.ON_COLLECTION;
	}

	@Override
	protected String getEndStatus(BusiActionParamVO<T> param) throws BusinessException {
		// TODO Auto-generated method stub
		return FbmStatusConstant.HAS_COLLECTION;
	}

	public void dealAccount(String pk_action, BusiActionParamVO<T> param) throws BusinessException {
		// TODO Auto-generated method stub
		CommonDAO dao = new CommonDAO();
		BaseinfoVO baseinfoVO = dao.queryBaseinfoByPK(param.getPk_baseinfo());

		//本地存放户减少
		AccountDetailVO[] vos = new AccountDetailVO[1];
		vos[0] = new AccountDetailVO();
		vos[0].setPk_org(param.getPk_org());
		vos[0].setMoneyy(baseinfoVO.getMoneyy().multiply(-1));
		vos[0].setPk_baseinfo(param.getPk_baseinfo());
		vos[0].setPk_action(pk_action);
		vos[0].setPk_type(FbmBusConstant.ACCOUNT_TYPE_LOCALKEEP);
		vos[0].setReason(FbmBusConstant.ACCOUNT_REASON_COLLECTION);
		dao.getBaseDAO().insertVOArray(vos);
	}
	
	@Override
	protected void afterAction(BusiActionParamVO<T>[] params) throws BusinessException {
		super.afterAction(params);
		
		//质押回收。zhouwb
		impawnBack(params[0]);
	}
	
	/**
	 * TODO 质押回收
	 * 
	 * @param paramvo
	 * 
	 * @author zhouwb 2008-10-10
	 */
	private void impawnBack(BusiActionParamVO<T> paramvo) throws BusinessException 
	{
		//质押单PK
		String pk_impawn = queryPk_impawn(paramvo.getPk_source());
		if(pk_impawn == null || pk_impawn.length()==0)
			return;
		
		//银行托收单VO
		CollectionVO colVO = (CollectionVO)paramvo.getSuperVO();
		
		//质押回收
		new ImpawnDAO().impawnBack(pk_impawn, colVO.getVtransactorid(), colVO.getDcollectiondate());
		
		uneffectFiImpawn(pk_impawn);
	}
	
	/**
	 * TODO 通过［收票登记单PK］查询［未回收的质押单PK］
	 * 
	 * @param pk_source 收票登记单PK
	 * @return 质押单PK
	 * 
	 * @author zhouwb 2008-10-10
	 */
	private String queryPk_impawn(String pk_source) throws BusinessException 
	{
		String sSql = "SELECT pk_impawn FROM fbm_impawn " +
			"WHERE pk_source='" + pk_source + "' and vbillstatus=" + IFBMStatus.CHECKPASS + " and isnull(dr,0)=0";
		
		return (String)new BaseDAO().executeQuery(sSql, new ColumnProcessor());
	}
	
	/**
	 * TODO 通过质押单ID，作废物权担保单
	 * 
	 * @param pk_fbmimpawn 质押单ID
	 * 		
	 * @author zhouwb 2008-10-10
	 */
	private void uneffectFiImpawn(String pk_fbmimpawn) throws BusinessException
	{
		String pk_fiimpawn = new ImpawnDAO().queryPk_fiimpawn(pk_fbmimpawn);
		if(pk_fiimpawn == null || pk_fiimpawn.length()==0)
			return;
		
		String sql = " update fi_impawn set impawnstatus = 'N' where pk_id= '" + pk_fiimpawn + "' and impawnstatus = 'Y'";
		new BaseDAO().executeUpdate(sql);
	}
}