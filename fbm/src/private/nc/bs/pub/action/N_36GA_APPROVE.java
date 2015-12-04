package nc.bs.pub.action;

import java.util.Hashtable;

import nc.bs.dao.BaseDAO;
import nc.bs.fbm.gather.GatherCmpAcc;
import nc.bs.fbm.plan.PlanFacade;
import nc.bs.fbm.pub.action.BusiActionFactory;
import nc.bs.framework.common.InvocationInfoProxy;
import nc.bs.framework.core.service.TimeService;
import nc.bs.pub.compiler.AbstractCompiler2;
import nc.bs.trade.business.HYPubBO;
import nc.vo.fbm.pub.constant.FbmActionConstant;
import nc.vo.fbm.pub.constant.FbmBusConstant;
import nc.vo.fbm.register.RegisterVO;
import nc.vo.pub.BusinessException;
import nc.vo.pub.CircularlyAccessibleValueObject;
import nc.vo.pub.compiler.PfParameterVO;
import nc.vo.pub.lang.UFBoolean;
import nc.vo.pub.lang.UFDate;
import nc.vo.pub.lang.UFDateTime;
import nc.vo.trade.pub.HYBillVO;
import nc.vo.trade.pub.IBillStatus;
import nc.vo.uap.pf.PFBusinessException;
/**
 * ��ע������Ʊ�Ǽǵ����
���ݶ���ִ���еĶ�ִ̬����Ķ�ִ̬���ࡣ
 *
 * �������ڣ�(2007-8-9)
 * @author ƽ̨�ű�����
 */
public class N_36GA_APPROVE extends AbstractCompiler2 {
private java.util.Hashtable m_methodReturnHas=new java.util.Hashtable();
private Hashtable m_keyHas=null;
/**
 * N_36GA_APPROVE ������ע�⡣
 */
public N_36GA_APPROVE() {
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
	procActionFlow(vo);
	Object retObj=runClass("nc.bs.trade.comstatus.BillApprove","approveBill","nc.vo.pub.AggregatedValueObject:36GA",vo,m_keyHas,m_methodReturnHas);

	if(retObj instanceof HYBillVO){
		CircularlyAccessibleValueObject parentVO = ((HYBillVO)retObj).getParentVO();
		Integer billstatus = (Integer)parentVO.getAttributeValue("vbillstatus");

		String actioncode = null;
		if(billstatus.intValue() == IBillStatus.CHECKPASS){//����������Ϊ���ͨ��
			actioncode = FbmActionConstant.AUDIT;
		}else{
			actioncode = FbmActionConstant.ONAUDIT;
		}
		
//		ActionParamVO[] params = DefaultParamAdapter.changeGather2Param((HYBillVO)retObj,actioncode);
//		FbmActionFactory.getInstance().createActionClass(FbmBusConstant.BILLTYPE_GATHER, actioncode).doAction(params);
		RegisterVO regVO = (RegisterVO)parentVO;
		regVO.setApprovetime(new UFDateTime(TimeService.getInstance().getTime()));
		new BaseDAO().updateVO(regVO);
		BusiActionFactory.getInstance().createActionClass(FbmBusConstant.BILLTYPE_GATHER, actioncode).doAction(regVO, actioncode,false);
		
		if(UFBoolean.TRUE.equals(regVO.getSfflag())){
			//ά���ʽ�ƻ�
			PlanFacade facade = new  PlanFacade();
			facade.insertPlanExec(regVO, regVO.getPk_corp());
		}
		if(billstatus.intValue() == IBillStatus.CHECKPASS && regVO.getSfflag().booleanValue()){
			String loginCorp = InvocationInfoProxy.getInstance().getCorpCode();
			GatherCmpAcc srv = new GatherCmpAcc();
			srv.addCMPBill((HYBillVO)retObj, loginCorp, vo.m_operator,new UFDate(vo.m_currentDate));
		}
		regVO= (RegisterVO)new HYPubBO().queryByPrimaryKey(RegisterVO.class,regVO.getPrimaryKey());
		((HYBillVO) retObj).setParentVO(regVO);
	}
	if (retObj != null) {
		m_methodReturnHas.put("approveBill",retObj);
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