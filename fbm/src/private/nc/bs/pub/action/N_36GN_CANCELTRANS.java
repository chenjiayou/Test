package nc.bs.pub.action;

import java.util.Hashtable;

import nc.bs.dao.BaseDAO;
import nc.bs.fbm.pub.OuterRelationDAO;
import nc.bs.fbm.pub.RegisterVO2BVO;
import nc.bs.pub.compiler.AbstractCompiler2;
import nc.bs.trade.business.HYPubBO;
import nc.vo.fbm.outer.OuterVO;
import nc.vo.fbm.returnbill.ReturnBVO;
import nc.vo.fbm.returnbill.ReturnVO;
import nc.vo.pub.BusinessException;
import nc.vo.pub.compiler.PfParameterVO;
import nc.vo.trade.pub.HYBillVO;
import nc.vo.trade.pub.IBillStatus;
import nc.vo.uap.pf.PFBusinessException;
/**
 * ��ע��������Ʊ��ȡ��ת��
���ݶ���ִ���еĶ�ִ̬����Ķ�ִ̬���ࡣ
 *
 * �������ڣ�(2007-10-10)
 * @author ƽ̨�ű�����
 */
public class N_36GN_CANCELTRANS extends AbstractCompiler2 {
private java.util.Hashtable m_methodReturnHas=new java.util.Hashtable();
private Hashtable m_keyHas=null;
/**
 * N_36GN_CANCELTRANS ������ע�⡣
 */
public N_36GN_CANCELTRANS() {
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
	ReturnVO parent = (ReturnVO)vo.m_preValueVo.getParentVO();
	//У���Ƿ�ת�����ɵ��ݾ���ɾ��
	OuterRelationDAO outerDao = new OuterRelationDAO();
	OuterVO[] outerVos = (OuterVO[])outerDao.queryByPkBusibill(parent.getPrimaryKey());
	if(outerVos!=null && outerVos.length > 0){
		throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000364")/* @res"ת�����ɵ��ո����δɾ�����޷�ȡ��ת��"*/);
	}
	//�޸ĵ���״̬Ϊ�����
	BaseDAO dao = new BaseDAO();
	ReturnVO returnVO = (ReturnVO)dao.retrieveByPK(ReturnVO.class, parent.getPrimaryKey());
	returnVO.setVbillstatus(IBillStatus.CHECKPASS);
	dao.updateVO(returnVO);

	HYPubBO bo = new HYPubBO();
	
	Object obj = bo.queryBillVOByPrimaryKey(new String[] {
					HYBillVO.class.getName(), ReturnVO.class.getName(),
					ReturnBVO.class.getName() }, parent.getPrimaryKey());
	// ִ �й�ʽ ���������ƣ����Ч�����⣬����ǰ̨���̨����������
			RegisterVO2BVO.actionOPExecFormula((HYBillVO) obj);
		return obj;
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