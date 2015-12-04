package nc.bs.pub.action;

import nc.vo.fbm.discount.DiscountVO;
import nc.vo.fbm.endore.EndoreVO;
import nc.vo.fbm.pub.constant.FbmBusConstant;
import nc.vo.fbm.pub.constant.IFBMStatus;
import nc.vo.pub.pf.PfUtilActionVO;
import nc.bs.fbm.discount.DiscountService;
import nc.bs.fbm.discount.DiscountVoucher;
import nc.bs.pub.compiler.*;
import nc.vo.pub.compiler.PfParameterVO;
import java.math.*;
import java.util.*;
import nc.vo.pub.lang.*;
import nc.bs.pub.pf.PfUtilTools;
import nc.bs.trade.business.HYPubBO;
import nc.vo.pub.AggregatedValueObject;
import nc.vo.pub.BusinessException;
import nc.vo.uap.pf.PFBusinessException;
/**
 * ��ע�����ְ�����ȡ����֤
���ݶ���ִ���еĶ�ִ̬����Ķ�ִ̬���ࡣ
 *
 * �������ڣ�(2008-3-20)
 * @author ƽ̨�ű�����
 */
public class N_36GG_CANCELVOUCHER extends AbstractCompiler2 {
private java.util.Hashtable m_methodReturnHas=new java.util.Hashtable();
private Hashtable m_keyHas=null;
/**
 * N_36GG_CANCELVOUCHER ������ע�⡣
 */
public N_36GG_CANCELVOUCHER() {
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
	Object retObj = new DiscountVoucher().voucher(vo.m_preValueVo, FbmBusConstant.OP_CANCELVOUCHER);

	DiscountVO discountvo = (DiscountVO)vo.m_preValueVo.getParentVO();

	discountvo.setUnitvoucher(new UFBoolean(false));
	new DiscountService().updateUnitVoucher(discountvo);

	AggregatedValueObject avo = (AggregatedValueObject)retObj;
	DiscountVO oldvo = (DiscountVO)avo.getParentVO();
	String pk_key = oldvo.getPrimaryKey();

	DiscountVO newvo =  (DiscountVO)new HYPubBO().queryByPrimaryKey(DiscountVO.class,pk_key);

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