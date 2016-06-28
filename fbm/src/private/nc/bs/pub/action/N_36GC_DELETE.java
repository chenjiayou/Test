package nc.bs.pub.action;

import java.util.Hashtable;

import nc.bs.fbm.pub.action.BusiActionFactory;
import nc.bs.pub.compiler.AbstractCompiler2;
import nc.bs.trade.business.HYPubBO;
import nc.vo.fbm.pub.constant.FbmActionConstant;
import nc.vo.fbm.pub.constant.FbmBusConstant;
import nc.vo.fbm.storage.StorageBVO;
import nc.vo.fbm.storage.StorageVO;
import nc.vo.pub.BusinessException;
import nc.vo.pub.compiler.PfParameterVO;
import nc.vo.trade.pub.HYBillVO;
import nc.vo.uap.pf.PFBusinessException;
/**
 * ��ע���������õ�ɾ��
���ݶ���ִ���еĶ�ִ̬����Ķ�ִ̬���ࡣ
 *
 * �������ڣ�(2007-8-21)
 * @author ƽ̨�ű�����
 */
public class N_36GC_DELETE extends AbstractCompiler2 {
private java.util.Hashtable m_methodReturnHas=new java.util.Hashtable();
private Hashtable m_keyHas=null;
/**
 * N_36GC_DELETE ������ע�⡣
 */
public N_36GC_DELETE() {
	super();
}
/*
* ��ע��ƽ̨��д������
* �ӿ�ִ����
*/
public Object runComClass(PfParameterVO vo) throws BusinessException {
try{
	super.m_tmpVo=vo;
	HYBillVO tmpVO = (HYBillVO)vo.m_preValueVo;
	//���²�ѯ�ӱ����ݣ���ֹ�޸�ʱû���ӱ�����
	HYPubBO bo = new HYPubBO();
	tmpVO = (HYBillVO)bo.queryBillVOByPrimaryKey(new String[] { HYBillVO.class.getName(),
			StorageVO.class.getName(), StorageBVO.class.getName() }, tmpVO.getParentVO().getPrimaryKey());


//	ActionParamVO[] params = DefaultParamAdapter.changeBankBack2Param(tmpVO,FbmActionConstant.DELETE);
//	FbmActionFactory.getInstance().createActionClass(FbmBusConstant.BILLTYPE_BANKBACK, FbmActionConstant.DELETE).doAction(params);
	BusiActionFactory.getInstance().createActionClass(FbmBusConstant.BILLTYPE_BANKBACK, FbmActionConstant.DELETE).doAction(tmpVO, FbmActionConstant.DELETE,false);

	//####���ű����뺬�з���ֵ,����DLG��PNL������������з���ֵ####
	Object retObj = null;

	retObj = runClass("nc.bs.trade.comdelete.BillDelete", "deleteBill",
			"nc.vo.pub.AggregatedValueObject:36GC", vo, m_keyHas,
			m_methodReturnHas);
	if (retObj != null) {
		m_methodReturnHas.put("deleteBill", retObj);
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