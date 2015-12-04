package nc.bs.pub.action;

import java.util.Hashtable;

import nc.bs.fbm.pub.RegisterVO2BVO;
import nc.bs.framework.common.InvocationInfoProxy;
import nc.bs.pub.compiler.AbstractCompiler2;
import nc.bs.trade.business.HYPubBO;
import nc.impl.fbm.pub.FBMBillTallyImpl;
import nc.vo.fbm.reckon.ReckonBVO;
import nc.vo.fbm.reckon.ReckonVO;
import nc.vo.pub.BusinessException;
import nc.vo.pub.compiler.PfParameterVO;
import nc.vo.pub.lang.UFDate;
import nc.vo.trade.pub.HYBillVO;
import nc.vo.uap.pf.PFBusinessException;
/**
 * ��ע����������ص���ȡ������
���ݶ���ִ���еĶ�ִ̬����Ķ�ִ̬���ࡣ
 *
 * �������ڣ�(2008-8-21)
 * @author ƽ̨�ű�����
 */
public class N_36GT_CANCELTALLY extends AbstractCompiler2 {
private java.util.Hashtable m_methodReturnHas=new java.util.Hashtable();
private Hashtable m_keyHas=null;
/**
 * N_36GT_CANCELTALLY ������ע�⡣
 */
public N_36GT_CANCELTALLY() {
	super();
}
/*
* ��ע��ƽ̨��д������
* �ӿ�ִ����
*/
public Object runComClass(PfParameterVO vo) throws BusinessException {
try{
	super.m_tmpVo=vo;
	HYBillVO billVo = (HYBillVO) vo.m_preValueVo;
	ReckonVO reckonVo = (ReckonVO) billVo.getParentVO();
	String corpPk = InvocationInfoProxy.getInstance().getCorpCode();
	String userId = reckonVo.getTallyman();
	String currdate = reckonVo.getTallydate().toString();
	new FBMBillTallyImpl().cancelTally(billVo, UFDate.getDate(currdate), userId, corpPk);
	
	String pk_reckon = (String)reckonVo.getPrimaryKey();
	HYPubBO bo = new HYPubBO();
	Object retObj = bo.queryBillVOByPrimaryKey(new String[] { HYBillVO.class.getName(),
				ReckonVO.class.getName(), ReckonBVO.class.getName() }, pk_reckon);
	// ִ �й�ʽ ���������ƣ����Ч�����⣬����ǰ̨���̨����������
	RegisterVO2BVO.actionOPExecFormula((HYBillVO) retObj);
	
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
	return "	Object retObj=null;\n	return retObj;\n";}
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
