package nc.bs.fbm.consignbank.action;

import nc.bs.dao.BaseDAO;
import nc.bs.fbm.impawn.ImpawnDAO;
import nc.bs.fbm.pub.ActionQueryDAO;
import nc.bs.fbm.pub.action.BusiMessageTranslator;
import nc.jdbc.framework.processor.ColumnProcessor;
import nc.vo.fbm.consignbank.CollectionVO;
import nc.vo.fbm.pub.ActionVO;
import nc.vo.fbm.pub.BusiActionParamVO;
import nc.vo.fbm.pub.constant.FbmActionConstant;
import nc.vo.fbm.pub.constant.FbmBusConstant;
import nc.vo.fbm.pub.constant.FbmStatusConstant;
import nc.vo.fbm.pub.constant.IFBMStatus;
import nc.vo.pub.BusinessException;

/**
 * <p>
 *��������ȡ������-Ʊ��action��
 * <p>�����ˣ�bsrl
 * <b>���ڣ�2007-11-20
 *
 */
public class CancelTransactCollection<K extends CollectionVO, T extends CollectionVO>  extends ActionCollection<K, T> {

	@Override
	protected String doCheck(BusiActionParamVO<T> param) throws BusinessException {
		String commonError = commonCheck(param);
		if(commonError != null)return commonError;

		ActionVO actionVO = param.getLastActionVO();
			//У��Ʊ�ݶ���
		if(!(actionVO.getActioncode().equals(FbmActionConstant.TRANSACT)
					&& actionVO.getBilltype().equals(FbmBusConstant.BILLTYPE_COLLECTION_UNIT)
					&& actionVO.getPk_bill().equals(param.getPk_bill()))){
			return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000239")/* @res"Ʊ��"*/ + param.getBaseinfoVO().getFbmbillno() + nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000274")/* @res"��ǰһ���������ǰ������յ���"*/+ BusiMessageTranslator.translateAction(param);
		}
		return null;
	}

	@Override
	protected String getBeginStatus(BusiActionParamVO<T> param)
			throws BusinessException {
		return FbmStatusConstant.HAS_COLLECTION;
	}

	@Override
	protected String getEndStatus(BusiActionParamVO<T> param) throws BusinessException {
		return null;
	}
	
	@Override
	protected void afterAction(BusiActionParamVO<T>[] params) throws BusinessException {
		super.afterAction(params);
		
		//ȡ����Ѻ���ա�zhouwb
		cancelImpawnBack(params[0].getPk_source(), params[0].getPk_bill());
	}
	
	/**
	 * TODO ȡ����Ѻ����
	 * 
	 * @param pk_source ��Ʊ�Ǽǵ�ID
	 * @param pk_colbill �������յ�ID
	 * 
	 * @author zhouwb 2008-10-09
	 */
	private void cancelImpawnBack(String pk_source, String pk_colbill) throws BusinessException
	{
		//��ѯ�������յ������[��ʼ״̬],�ǣ�����Ѻ��ʱ����ִ��ȡ����Ѻ����
		String beginStatus = new ActionQueryDAO().getBeginstatusByBillaction(pk_colbill, FbmActionConstant.SAVE);
		if(!FbmStatusConstant.HAS_IMPAWN.equals(beginStatus))
			return;
		
		String pk_impawn = queryPk_impawn(pk_source);
		if(pk_impawn==null || pk_impawn.length()==0)
			return;
		
		//ȡ����Ѻ����
		String sSql = "UPDATE fbm_impawn SET impawnbackunit=null, impawnbackdate=null, vbillstatus=" + IFBMStatus.CHECKPASS + 
			" WHERE pk_impawn='" + pk_impawn + "' and isnull(dr,0)=0";		
		new BaseDAO().executeUpdate(sSql);
		
		cancelUneffectFiImpawn(pk_impawn);
	}
	
	/**
	 * TODO ͨ������Ʊ�Ǽǵ�PK�ݲ�ѯ���ѻ��յ���Ѻ��PK��
	 * 
	 * @param pk_source ��Ʊ�Ǽǵ�PK
	 * @return ��Ѻ��PK
	 * 
	 * @author zhouwb 2008-10-08
	 */
	private String queryPk_impawn(String pk_source) throws BusinessException 
	{
		String sSql = "SELECT pk_impawn FROM fbm_impawn " +
			"WHERE pk_source='" + pk_source + "' and vbillstatus=" + IFBMStatus.IMPAWN_BACK + " and isnull(dr,0)=0";
		
		return (String)new BaseDAO().executeQuery(sSql, new ColumnProcessor());
	}
	
	/**
	 * TODO ͨ����Ѻ��ID��ȡ��������Ȩ������
	 * 
	 * @param pk_fbmimpawn ��Ѻ��ID
	 * 		
	 * @author zhouwb 2008-10-09
	 */
	private void cancelUneffectFiImpawn(String pk_fbmimpawn) throws BusinessException
	{
		String pk_fiimpawn = new ImpawnDAO().queryPk_fiimpawn(pk_fbmimpawn);
		if(pk_fiimpawn==null || pk_fiimpawn.length()==0)
			return;
		
		String sql = " UPDATE fi_impawn SET impawnstatus = 'Y' WHERE pk_id= '" + pk_fiimpawn + "' and impawnstatus = 'N' and isnull(dr,0)=0";
		new BaseDAO().executeUpdate(sql);
	}

}