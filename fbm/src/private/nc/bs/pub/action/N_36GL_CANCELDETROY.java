package nc.bs.pub.action;

import java.util.Hashtable;

import nc.bs.fbm.invoice.InvoiceBillService;
import nc.bs.fbm.plan.PlanFacade;
import nc.bs.fbm.pub.action.BusiActionFactory;
import nc.bs.pub.compiler.AbstractCompiler2;
import nc.vo.fbm.pub.constant.FbmActionConstant;
import nc.vo.fbm.pub.constant.FbmBusConstant;
import nc.vo.fbm.register.RegisterVO;
import nc.vo.pub.BusinessException;
import nc.vo.pub.compiler.PfParameterVO;
import nc.vo.trade.pub.HYBillVO;
import nc.vo.uap.pf.PFBusinessException;
/**
 * ��ע��Ʊ�ݿ�Ʊ��ȡ������
���ݶ���ִ���еĶ�ִ̬����Ķ�ִ̬���ࡣ
 *
 * �������ڣ�(2007-9-20)
 * @author ƽ̨�ű�����
 */
public class N_36GL_CANCELDETROY extends AbstractCompiler2 {
private java.util.Hashtable m_methodReturnHas=new java.util.Hashtable();
private Hashtable m_keyHas=null;
/**
 * N_36GL_DELCANCELBILL ������ע�⡣
 */
public N_36GL_CANCELDETROY() {
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
	if(vo.m_preValueVo instanceof HYBillVO){
		RegisterVO regVO = (RegisterVO)vo.m_preValueVo.getParentVO();
		BusiActionFactory.getInstance().createActionClass(FbmBusConstant.BILLTYPE_INVOICE, FbmActionConstant.CANCELDESTROY).doAction(regVO, FbmActionConstant.CANCELDESTROY,false);
		PlanFacade facade = new PlanFacade();
		facade.delPlanExec(regVO, regVO.getPk_corp(),"CANCELDESTROY");
	}
	Object retObj = new InvoiceBillService().CancelDestroyBill(vo.m_preValueVo);
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