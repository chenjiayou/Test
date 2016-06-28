package nc.bs.pub.action;

import java.util.Hashtable;

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
import nc.vo.trade.pub.HYBillVO;
import nc.vo.trade.pub.IBillStatus;
import nc.vo.uap.pf.PFBusinessException;
/**
 * ��ע�������йܵ�����
���ݶ���ִ���еĶ�ִ̬����Ķ�ִ̬���ࡣ
 *
 * �������ڣ�(2007-8-20)
 * @author ƽ̨�ű�����
 */
public class N_36GB_UNAPPROVE extends AbstractCompiler2 {
private java.util.Hashtable m_methodReturnHas=new java.util.Hashtable();
private Hashtable m_keyHas=null;
/**
 * N_36GB_UNAPPROVE ������ע�⡣
 */
public N_36GB_UNAPPROVE() {
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
	int vbillstatus = (Integer)vo.m_preValueVo.getParentVO().getAttributeValue("vbillstatus");
	//####���ű����뺬�з���ֵ,����DLG��PNL������������з���ֵ####
	procUnApproveFlow(vo);
	Object retObj = runClass("nc.bs.trade.comstatus.BillUnApprove",
			"unApproveBill", "nc.vo.pub.AggregatedValueObject:36GB",
			vo, m_keyHas, m_methodReturnHas);
	if(retObj instanceof HYBillVO){
		HYBillVO retBillVO = (HYBillVO)retObj;
		//���²�ѯ�ӱ����ݣ���ֹ�޸�ʱû���ӱ�����
		HYPubBO bo = new HYPubBO();
		retBillVO = (HYBillVO)bo.queryBillVOByPrimaryKey(new String[] { HYBillVO.class.getName(),
				StorageVO.class.getName(), StorageBVO.class.getName() }, retBillVO.getParentVO().getPrimaryKey());

//		ActionParamVO[] params = DefaultParamAdapter.changeBankKeep2Param((HYBillVO)retObj,FbmActionConstant.CANCELAUDIT);
//		FbmActionFactory.getInstance().createActionClass(FbmBusConstant.BILLTYPE_BANKKEEP,FbmActionConstant.CANCELAUDIT).doAction(params);
		if(vbillstatus == IBillStatus.CHECKPASS||vbillstatus==IFBMStatus.RETURNED){//ֻ�����ͨ��
			BusiActionFactory.getInstance().createActionClass(FbmBusConstant.BILLTYPE_BANKKEEP, FbmActionConstant.CANCELAUDIT).doAction(retBillVO, FbmActionConstant.CANCELAUDIT,false);
		}
		new StorageBillService().updateInputUnitfromBankKeepUnpprove((HYBillVO)retObj);
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