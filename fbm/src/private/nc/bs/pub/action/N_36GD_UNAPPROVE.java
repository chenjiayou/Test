package nc.bs.pub.action;

import java.util.Hashtable;

import nc.bs.fbm.plan.PlanFacade;
import nc.bs.fbm.pub.action.BusiActionFactory;
import nc.bs.fbm.storage.StorageBillService;
import nc.bs.pub.compiler.AbstractCompiler2;
import nc.bs.trade.business.HYPubBO;
import nc.vo.fbm.pub.constant.FbmActionConstant;
import nc.vo.fbm.pub.constant.FbmBusConstant;
import nc.vo.fbm.pub.constant.IFBMStatus;
import nc.vo.fbm.storage.StorageBVO;
import nc.vo.fbm.storage.StorageVO;
import nc.vo.pub.BusinessException;
import nc.vo.pub.compiler.PfParameterVO;
import nc.vo.pub.lang.UFDate;
import nc.vo.trade.pub.HYBillVO;
import nc.vo.trade.pub.IBillStatus;
import nc.vo.uap.pf.PFBusinessException;
/**
 * ��ע���ڲ���ŵ�����
���ݶ���ִ���еĶ�ִ̬����Ķ�ִ̬���ࡣ
 *
 * �������ڣ�(2007-10-10)
 * @author ƽ̨�ű�����
 */
public class N_36GD_UNAPPROVE extends AbstractCompiler2 {
private java.util.Hashtable m_methodReturnHas=new java.util.Hashtable();
private Hashtable m_keyHas=null;
/**
 * N_36GD_UNAPPROVE ������ע�⡣
 */
public N_36GD_UNAPPROVE() {
	super();
}
/*
* ��ע��ƽ̨��д������
* �ӿ�ִ����
*/
public Object runComClass(PfParameterVO vo) throws BusinessException {
try{
	super.m_tmpVo=vo;
	//��鵥��״̬�Ƿ����ͨ��
	StorageVO storageVO = (StorageVO)vo.m_preValueVo.getParentVO();
	int vbillstatus = storageVO.getVbillstatus();

	//####���ű����뺬�з���ֵ,����DLG��PNL������������з���ֵ####
	procUnApproveFlow(vo);

	
	Object retObj = runClass("nc.bs.trade.comstatus.BillUnApprove",
			"unApproveBill", "nc.vo.pub.AggregatedValueObject:36GD",
			vo, m_keyHas, m_methodReturnHas);
	if(retObj instanceof HYBillVO){
		HYBillVO retBillVO = (HYBillVO)retObj;
		//���²�ѯ�ӱ����ݣ���ֹ�޸�ʱû���ӱ�����
		HYPubBO bo = new HYPubBO();
		retBillVO = (HYBillVO)bo.queryBillVOByPrimaryKey(new String[] { HYBillVO.class.getName(),
				StorageVO.class.getName(), StorageBVO.class.getName() }, retBillVO.getParentVO().getPrimaryKey());

//		ActionParamVO[] params = DefaultParamAdapter.changeInnerKeep2Param(retBillVO,FbmActionConstant.CANCELAUDIT);
//		FbmActionFactory.getInstance().createActionClass(FbmBusConstant.BILLTYPE_INNERKEEP,FbmActionConstant.CANCELAUDIT).doAction(params);
		if(vbillstatus == IBillStatus.CHECKPASS || vbillstatus == IFBMStatus.RETURNED){//ֻ�����ͨ�������˻�
			BusiActionFactory.getInstance().createActionClass(FbmBusConstant.BILLTYPE_INNERKEEP, FbmActionConstant.CANCELAUDIT).doAction(retBillVO, FbmActionConstant.CANCELAUDIT,false);
		}
		StorageVO storagevo = ((StorageVO)((HYBillVO)retObj).getParentVO());
		storagevo.setDealdate(null);
		retObj = new StorageBillService().updateReturnInfoForUnapprove((HYBillVO)retObj);
	

	}


	
	if (retObj != null) {
		m_methodReturnHas.put("unApproveBill", retObj);
	}
	return retObj;
} catch (Exception ex) {
	if (ex instanceof BusinessException)
		throw (BusinessException) ex;
	else
    throw new PFBusinessException(ex.getMessage(), ex);
}
}
/*
* ��ע��ƽ̨��дԭʼ�ű�
*/
public String getCodeRemark(){
	return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add","UPP3620ADD-000173")/*@res "	//####���ű����뺬�з���ֵ,����DLG��PNL������������з���ֵ####\n	Object retObj=null;\n	return retObj;\n"*/;}
/*
* ��ע�����ýű�������HAS
*/
private void setParameter(String key,Object val)	{
	if (m_keyHas==null){
		m_keyHas=new Hashtable();
	}
	if (val!=null)	{
		m_keyHas.put(key,val);
	}
}
}