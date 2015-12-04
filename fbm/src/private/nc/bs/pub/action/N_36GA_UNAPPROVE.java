package nc.bs.pub.action;

import java.util.Hashtable;

import nc.bs.dao.BaseDAO;
import nc.bs.fbm.gather.GatherCmpAcc;
import nc.bs.fbm.plan.PlanFacade;
import nc.bs.fbm.pub.action.BusiActionFactory;
import nc.bs.framework.common.InvocationInfoProxy;
import nc.bs.pub.compiler.AbstractCompiler2;
import nc.bs.trade.business.HYPubBO;
import nc.vo.fbm.pub.constant.FbmActionConstant;
import nc.vo.fbm.pub.constant.FbmBusConstant;
import nc.vo.fbm.register.RegisterVO;
import nc.vo.pub.BusinessException;
import nc.vo.pub.compiler.PfParameterVO;
import nc.vo.pub.lang.UFBoolean;
import nc.vo.pub.lang.UFDate;
import nc.vo.trade.pub.HYBillVO;
import nc.vo.trade.pub.IBillStatus;
import nc.vo.uap.pf.PFBusinessException;
/**
 * 备注：新收票登记的弃审
单据动作执行中的动态执行类的动态执行类。
 *
 * 创建日期：(2007-8-9)
 * @author 平台脚本生成
 */
public class N_36GA_UNAPPROVE extends AbstractCompiler2 {
private java.util.Hashtable m_methodReturnHas=new java.util.Hashtable();
private Hashtable m_keyHas=null;
/**
 * N_36GA_UNAPPROVE 构造子注解。
 */
public N_36GA_UNAPPROVE() {
	super();
}
/*
* 备注：平台编写规则类
* 接口执行类
*/
public Object runComClass(PfParameterVO vo) throws BusinessException {
try{
	super.m_tmpVo=vo;
	//####本脚本必须含有返回值,返回DLG和PNL的组件不允许有返回值####
	//检查单据状态是否审核通过
	int vbillstatus = (Integer)vo.m_preValueVo.getParentVO().getAttributeValue("vbillstatus");
	
	procUnApproveFlow(vo);
	Object retObj = runClass("nc.bs.trade.comstatus.BillUnApprove",
			"unApproveBill", "nc.vo.pub.AggregatedValueObject:36GA",
			vo, m_keyHas, m_methodReturnHas);
	if(retObj instanceof HYBillVO){
		RegisterVO regVO = (RegisterVO)((HYBillVO)retObj).getParentVO();
		regVO.setApprovetime(null);
		new BaseDAO().updateVO(regVO);
		
		if(vbillstatus == IBillStatus.CHECKPASS){//只有审核通过
			BusiActionFactory.getInstance().createActionClass(FbmBusConstant.BILLTYPE_GATHER,FbmActionConstant.CANCELAUDIT).doAction(regVO,FbmActionConstant.CANCELAUDIT,false);
		}

//		ActionParamVO[] params = DefaultParamAdapter.changeGather2Param((HYBillVO)retObj,FbmActionConstant.CANCELAUDIT);
//		FbmActionFactory.getInstance().createActionClass(FbmBusConstant.BILLTYPE_GATHER,FbmActionConstant.CANCELAUDIT).doAction(params);
		if(UFBoolean.TRUE.equals(regVO.getSfflag())){
			//维护资金计划
			PlanFacade facade = new  PlanFacade();
			facade.delPlanExec(regVO, regVO.getPk_corp());
		}
		
		if(vbillstatus == IBillStatus.CHECKPASS &&  regVO.getSfflag().booleanValue()){
			String loginCorp = InvocationInfoProxy.getInstance().getCorpCode();
			GatherCmpAcc srv = new GatherCmpAcc();
			srv.delCMPacc((HYBillVO)retObj, loginCorp, vo.m_operator,new UFDate(vo.m_currentDate));
		}
		regVO= (RegisterVO)new HYPubBO().queryByPrimaryKey(RegisterVO.class,regVO.getPrimaryKey());
		((HYBillVO) retObj).setParentVO(regVO);
	}
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
* 备注：平台编写原始脚本
*/
public String getCodeRemark(){
	return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add","UPP3620ADD-000173")/*@res "	//####本脚本必须含有返回值,返回DLG和PNL的组件不允许有返回值####\n	Object retObj=null;\n	return retObj;\n"*/;}
/*
* 备注：设置脚本变量的HAS
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