package nc.bs.pub.action;

import java.util.ArrayList;
import java.util.Hashtable;

import nc.bs.fbm.consignbank.ConsignBankUtil;
import nc.bs.fbm.plan.PlanFacade;
import nc.bs.fbm.pub.action.BusiActionFactory;
import nc.bs.framework.common.InvocationInfoProxy;
import nc.bs.pub.compiler.AbstractCompiler2;
import nc.vo.fbm.consignbank.CollectionVO;
import nc.vo.fbm.pub.constant.FbmActionConstant;
import nc.vo.fbm.pub.constant.FbmBusConstant;
import nc.vo.fbm.pub.constant.IFBMStatus;
import nc.vo.pub.AggregatedValueObject;
import nc.vo.pub.BusinessException;
import nc.vo.pub.compiler.PfParameterVO;
import nc.vo.pub.lang.UFDate;
import nc.vo.trade.pub.HYBillVO;
import nc.vo.uap.pf.PFBusinessException;
/**
 * 备注：银行托收转账单的取消办理
单据动作执行中的动态执行类的动态执行类。
 *
 * 创建日期：(2007-11-20)
 * @author 平台脚本生成
 */
public class N_36GI_CANCELTRANSACT extends AbstractCompiler2 {
private java.util.Hashtable m_methodReturnHas=new java.util.Hashtable();
private Hashtable m_keyHas=null;
/**
 * N_36GI_CANCELTRANSACT 构造子注解。
 */
public N_36GI_CANCELTRANSACT() {
	super();
}
/*
* 备注：平台编写规则类
* 接口执行类
*/
public Object runComClass(PfParameterVO vo) throws BusinessException {
try{
	super.m_tmpVo = vo;
    Object retObj=null;
    String pk_corp = InvocationInfoProxy.getInstance().getCorpCode();
    setParameter("billVo", retObj);
	setParameter("userObj", vo.m_userObj);
	CollectionVO clvo = (CollectionVO)vo.m_preValueVo.getParentVO();
	
	//删除计划\
	PlanFacade facade = new PlanFacade();
	facade.delPlanExec(clvo, clvo.getPk_corp());
	
	clvo.setVtransactorid(null);
	clvo.setDtransactdate(null);
	clvo.setVbillstatus(IFBMStatus.CHECKPASS);
	clvo.setDcollectiondate(null);

    retObj = runClass("nc.bs.trade.comsave.BillSave","saveBill","nc.vo.pub.AggregatedValueObject:36GI",vo,m_keyHas,m_methodReturnHas);
    HYBillVO retBillVO = (HYBillVO)((ArrayList)retObj).get(1);
	if(retBillVO instanceof HYBillVO){
//		ActionParamVO[] params = DefaultParamAdapter.changeCollection2Param((HYBillVO)retBillVO,FbmActionConstant.CANCELTRANSACT);
//		FbmActionFactory.getInstance().createActionClass(FbmBusConstant.BILLTYPE_COLLECTION_UNIT, FbmActionConstant.CANCELTRANSACT).doAction(params);
		BusiActionFactory.getInstance().createActionClass(FbmBusConstant.BILLTYPE_COLLECTION_UNIT, FbmActionConstant.CANCELTRANSACT).doAction((CollectionVO)retBillVO.getParentVO(), FbmActionConstant.CANCELTRANSACT,false);

	}

    if (retObj != null) {
		m_methodReturnHas.put("saveBill",retObj);
	}

	try
	{
		//删除银行账户账.
		ConsignBankUtil cbs = new ConsignBankUtil();
		cbs.delCMPacc((HYBillVO)retBillVO, pk_corp, vo.m_operator, new UFDate(vo.m_currentDate));

	} catch (Exception e) {
		throw new BusinessException(e.getMessage());
	}
    return (AggregatedValueObject)retBillVO;
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