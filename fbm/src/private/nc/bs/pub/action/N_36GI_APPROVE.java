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
import nc.vo.pub.BusinessException;
import nc.vo.pub.CircularlyAccessibleValueObject;
import nc.vo.pub.compiler.PfParameterVO;
import nc.vo.pub.lang.UFDate;
import nc.vo.trade.pub.HYBillVO;
import nc.vo.trade.pub.IBillStatus;
import nc.vo.uap.pf.PFBusinessException;
/**
 * 备注：银行托收转账单的审核
单据动作执行中的动态执行类的动态执行类。
 *
 * 创建日期：(2007-8-25)
 * @author 平台脚本生成
 */
public class N_36GI_APPROVE extends AbstractCompiler2 {
private java.util.Hashtable m_methodReturnHas=new java.util.Hashtable();
private Hashtable m_keyHas=null;
/**
 * N_36GI_APPROVE 构造子注解。
 */
public N_36GI_APPROVE() {
	super();
}
/*
* 备注：平台编写规则类
* 接口执行类
*/
public Object runComClass(PfParameterVO vo) throws BusinessException {
try{
	super.m_tmpVo=vo;
	CollectionVO clvoold = (CollectionVO)m_tmpVo.m_preValueVo.getParentVO().clone();

	procActionFlow(vo);
	Object retObj=runClass("nc.bs.trade.comstatus.BillApprove","approveBill","nc.vo.pub.AggregatedValueObject:36GI",vo,m_keyHas,m_methodReturnHas);

	String actioncode  = createBillAction(retObj);

	if (retObj != null) {
		m_methodReturnHas.put("approveBill",retObj);
	}
	//如果款项受托日期不为空，则直接办理之
	 if (retObj instanceof HYBillVO  ) {
		 HYBillVO billVO = (HYBillVO) retObj;

		 CollectionVO clvo = (CollectionVO)billVO.getParentVO();
		 if(clvo.getDcollectiondate()!=null){
			 clvo.setDtransactdate(clvo.getDapprovedate());
			 clvo.setVtransactorid(clvo.getVapproveid());
			 clvo.setVbillstatus(IFBMStatus.Transact);
			 Object  tmp = runClass("nc.bs.trade.comsave.BillSave","saveBill","nc.vo.pub.AggregatedValueObject:36GI",vo,m_keyHas,m_methodReturnHas);
			    HYBillVO retBillVO = (HYBillVO)((ArrayList)tmp).get(1);
				if(retBillVO instanceof HYBillVO){
					BusiActionFactory.getInstance().createActionClass(FbmBusConstant.BILLTYPE_COLLECTION_UNIT, FbmActionConstant.TRANSACT).doAction((CollectionVO)retBillVO.getParentVO(), FbmActionConstant.TRANSACT,false);
				}
			    retObj = retBillVO;

					if(FbmActionConstant.AUDIT.equals(actioncode)){
						//写银行账户账.
						ConsignBankUtil cbs = new ConsignBankUtil();
						String loginCorp = InvocationInfoProxy.getInstance().getCorpCode();
						cbs.addCMPBank((HYBillVO)retBillVO, loginCorp, vo.m_operator,new UFDate(vo.m_currentDate));
						cbs.addCMPBill((HYBillVO)retBillVO, loginCorp, vo.m_operator,new UFDate(vo.m_currentDate));
					}

				//写资金计划
				CollectionVO collectionVO = (CollectionVO) ((HYBillVO)retBillVO).getParentVO();
				collectionVO.setWriteplan(clvoold.getWriteplan());
				PlanFacade facade = new PlanFacade();
				facade.insertPlanExec(collectionVO, collectionVO.getPk_corp());
		 }
	}

	return retObj;

} catch (Exception ex) {
	if (ex instanceof BusinessException)
		throw (BusinessException) ex;
	else
    throw new PFBusinessException(ex.getMessage(), ex);
}
}
private String createBillAction(Object retObj) throws BusinessException {
	if(retObj instanceof HYBillVO){
		CircularlyAccessibleValueObject parentVO = ((HYBillVO)retObj).getParentVO();
		Integer billstatus = (Integer)parentVO.getAttributeValue("vbillstatus");

		String actioncode = null;
		if(billstatus.intValue() == IBillStatus.CHECKPASS){//如果操作结果为审核通过
			actioncode = FbmActionConstant.AUDIT;
		}else{
			actioncode = FbmActionConstant.ONAUDIT;
		}
//		ActionParamVO[] params = DefaultParamAdapter.changeCollection2Param((HYBillVO)retObj,actioncode);
//		FbmActionFactory.getInstance().createActionClass(FbmBusConstant.BILLTYPE_COLLECTION_UNIT, actioncode).doAction(params);
		BusiActionFactory.getInstance().createActionClass(FbmBusConstant.BILLTYPE_COLLECTION_UNIT, actioncode).doAction((CollectionVO)parentVO, actioncode,false);

		return actioncode;
	}
	return null;
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