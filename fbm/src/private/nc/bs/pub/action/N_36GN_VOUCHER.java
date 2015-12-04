package nc.bs.pub.action;

import java.util.Hashtable;

import nc.bs.fbm.returnbill.ReturnBillVoucher;
import nc.bs.pub.compiler.AbstractCompiler2;
import nc.vo.fbm.pub.FBMProxy;
import nc.vo.fbm.pub.constant.FbmBusConstant;
import nc.vo.fbm.returnbill.ReturnVO;
import nc.vo.pub.AggregatedValueObject;
import nc.vo.pub.BusinessException;
import nc.vo.pub.compiler.PfParameterVO;
import nc.vo.pub.lang.UFBoolean;
import nc.vo.uap.pf.PFBusinessException;
/**
 * ��ע��������Ʊ����֤
���ݶ���ִ���еĶ�ִ̬����Ķ�ִ̬���ࡣ
 *
 * �������ڣ�(2008-10-10)
 * @author ƽ̨�ű�����
 */
public class N_36GN_VOUCHER extends AbstractCompiler2 {
private java.util.Hashtable m_methodReturnHas=new java.util.Hashtable();
private Hashtable m_keyHas=null;
/**
 * N_36GN_VOUCHER ������ע�⡣
 */
public N_36GN_VOUCHER() {
	super();
}
/*
* ��ע��ƽ̨��д������
* �ӿ�ִ����
*/
public Object runComClass(PfParameterVO vo) throws BusinessException {
try{
	super.m_tmpVo=vo;
	//####���ű����뺬�з���ֵ,����DLG��PNL������������з���ֵ####
	Object retObj=null;
	new ReturnBillVoucher().voucher(vo.m_preValueVo, FbmBusConstant.OP_VOUCHER);

	ReturnVO returnVO = (ReturnVO)vo.m_preValueVo.getParentVO();
	returnVO.setVoucher(UFBoolean.TRUE);
	ReturnVO newReturnVO = (ReturnVO)FBMProxy.updateSuperVO(returnVO);

	AggregatedValueObject avo = (AggregatedValueObject)vo.m_preValueVo;
	avo.setParentVO(newReturnVO);

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