package nc.bs.pub.action;

import java.util.Hashtable;

import nc.bs.fbm.consignbank.ConsignBankService;
import nc.bs.fbm.consignbank.ConsignBankVoucher;
import nc.bs.pub.compiler.AbstractCompiler2;
import nc.bs.trade.business.HYPubBO;
import nc.vo.fbm.consignbank.CollectionVO;
import nc.vo.fbm.pub.constant.FbmBusConstant;
import nc.vo.fbm.pub.constant.IFBMStatus;
import nc.vo.pub.AggregatedValueObject;
import nc.vo.pub.BusinessException;
import nc.vo.pub.compiler.PfParameterVO;
import nc.vo.pub.lang.UFBoolean;
import nc.vo.uap.pf.PFBusinessException;
/**
 * ��ע����������ת�˵���ȡ����֤
���ݶ���ִ���еĶ�ִ̬����Ķ�ִ̬���ࡣ
 *
 * �������ڣ�(2008-3-20)
 * @author ƽ̨�ű�����
 */
public class N_36GI_CANCELVOUCHER extends AbstractCompiler2 {
private java.util.Hashtable m_methodReturnHas=new java.util.Hashtable();
private Hashtable m_keyHas=null;
/**
 * N_36GI_CANCELVOUCHER ������ע�⡣
 */
public N_36GI_CANCELVOUCHER() {
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
	Object retObj = new ConsignBankVoucher().voucher(vo.m_preValueVo, FbmBusConstant.OP_CANCELVOUCHER);

	CollectionVO consignbankvo = (CollectionVO)vo.m_preValueVo.getParentVO();
	consignbankvo.setUnitvoucher(new UFBoolean(false));
	new ConsignBankService().updateUnitVoucher(consignbankvo);

	AggregatedValueObject avo = (AggregatedValueObject)retObj;
	CollectionVO oldvo = (CollectionVO)avo.getParentVO();
	String pk_key = oldvo.getPrimaryKey();
	CollectionVO newvo =  (CollectionVO)new HYPubBO().queryByPrimaryKey(CollectionVO.class,pk_key);
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