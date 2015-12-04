package nc.bs.pub.action;

import java.util.Hashtable;

import nc.bs.fbm.endore.EndoreService;
import nc.bs.fbm.endore.EndoreVoucher;
import nc.bs.pub.compiler.AbstractCompiler2;
import nc.bs.trade.business.HYPubBO;
import nc.vo.fbm.endore.EndoreVO;
import nc.vo.fbm.pub.constant.FbmBusConstant;
import nc.vo.pub.AggregatedValueObject;
import nc.vo.pub.BusinessException;
import nc.vo.pub.compiler.PfParameterVO;
import nc.vo.pub.lang.UFBoolean;
import nc.vo.uap.pf.PFBusinessException;
/**
 * ��ע������������֤
���ݶ���ִ���еĶ�ִ̬����Ķ�ִ̬���ࡣ
 *
 * �������ڣ�(2008-3-19)
 * @author ƽ̨�ű�����
 */
public class N_36GQ_VOUCHER extends AbstractCompiler2 {
private java.util.Hashtable m_methodReturnHas=new java.util.Hashtable();
private Hashtable m_keyHas=null;
/**
 * N_36GQ_VOUCHER ������ע�⡣
 */
public N_36GQ_VOUCHER() {
	super();
}
/*
* ��ע��ƽ̨��д������
* �ӿ�ִ����
*/
public Object runComClass(PfParameterVO vo) throws BusinessException {
try{
	super.m_tmpVo=vo;
	
	Object retObj = new EndoreVoucher().voucher(vo.m_preValueVo, FbmBusConstant.OP_VOUCHER);
	
	EndoreVO endorevo = (EndoreVO)vo.m_preValueVo.getParentVO();
	
	endorevo.setUnitvoucher(new UFBoolean(true));
	new EndoreService().updateUnitVoucher(endorevo);
	
	AggregatedValueObject avo = (AggregatedValueObject)retObj;
	EndoreVO oldvo = (EndoreVO)avo.getParentVO();
	String pk_key = oldvo.getPrimaryKey();
	
	EndoreVO newvo =  (EndoreVO)new HYPubBO().queryByPrimaryKey(EndoreVO.class,pk_key);
	
	avo.setParentVO(newvo);
	
	return avo;
	
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
	return "	Object retObj =null;\n	return retObj;\n";}
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
