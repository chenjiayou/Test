package nc.bs.pub.action;

import java.util.Hashtable;

import nc.bs.fbm.accept.AcceptBillService;
import nc.bs.fbm.accept.AcceptUtil;
import nc.bs.fbm.plan.PlanFacade;
import nc.bs.fbm.pub.action.BusiActionFactory;
import nc.bs.framework.common.InvocationInfoProxy;
import nc.bs.pub.compiler.AbstractCompiler2;
import nc.vo.fbm.acceptbill.AcceptVO;
import nc.vo.fbm.pub.constant.FbmActionConstant;
import nc.vo.fbm.pub.constant.FbmBusConstant;
import nc.vo.pub.BusinessException;
import nc.vo.pub.compiler.PfParameterVO;
import nc.vo.pub.lang.UFDate;
import nc.vo.trade.pub.HYBillVO;
import nc.vo.trade.pub.IBillStatus;
import nc.vo.uap.pf.PFBusinessException;
/**
 * ��ע��Ʊ�ݸ��������
���ݶ���ִ���еĶ�ִ̬����Ķ�ִ̬���ࡣ
 *
 * �������ڣ�(2007-9-4)
 * @author ƽ̨�ű�����
 */
public class N_36GM_UNAPPROVE extends AbstractCompiler2 {
private java.util.Hashtable m_methodReturnHas=new java.util.Hashtable();
private Hashtable m_keyHas=null;
/**
 * N_36GM_UNAPPROVE ������ע�⡣
 */
public N_36GM_UNAPPROVE() {
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

	
	AcceptVO acceptvo = (AcceptVO)vo.m_preValueVo.getParentVO();
	String pk_corp = InvocationInfoProxy.getInstance().getCorpCode();
	//ά���ʽ�ƻ�
	PlanFacade facade = new PlanFacade();
	facade.delPlanExec(acceptvo, acceptvo.getPk_corp());

	//####���ű����뺬�з���ֵ,����DLG��PNL������������з���ֵ####
	String ccReturnMsg = new AcceptBillService().applyRationBeforeGMUnApprove((AcceptVO)vo.m_preValueVo.getParentVO());

	procUnApproveFlow(vo);
	Object retObj = runClass("nc.bs.trade.comstatus.BillUnApprove",
			"unApproveBill", "nc.vo.pub.AggregatedValueObject:36GM",
			vo, m_keyHas, m_methodReturnHas);
	if(retObj instanceof HYBillVO){
		if(vbillstatus == IBillStatus.CHECKPASS){//ֻ�����ͨ��
			BusiActionFactory.getInstance().createActionClass(FbmBusConstant.BILLTYPE_BILLPAY, FbmActionConstant.CANCELAUDIT).doAction((AcceptVO)((HYBillVO)retObj).getParentVO(), FbmActionConstant.CANCELAUDIT,false);
		

				//ɾ�������˻���.
				//����ʱ��Я���ı�֤���˻���Ϣ��ʧ����˴���ԭ����acceptVo �ŵ�HYBillVO�н���ɾ�ˡ�
				HYBillVO retObj1 = new HYBillVO();
				retObj1.setParentVO(acceptvo);
				AcceptUtil cbs = new AcceptUtil();
				String loginCorp = InvocationInfoProxy.getInstance().getCorpCode();
				cbs.delCMPacc(retObj1, loginCorp, vo.m_operator, new UFDate(vo.m_currentDate));
		}
	}
	retObj = new Object[] { ccReturnMsg,retObj };
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