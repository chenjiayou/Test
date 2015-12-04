package nc.bs.pub.action;

import java.util.Hashtable;

import nc.bs.fbm.discount.DiscountUtil;
import nc.bs.fbm.plan.PlanFacade;
import nc.bs.fbm.pub.CommonDAO;
import nc.bs.fbm.pub.action.BusiActionFactory;
import nc.bs.framework.common.InvocationInfoProxy;
import nc.bs.framework.common.NCLocator;
import nc.bs.pub.compiler.AbstractCompiler2;
import nc.itf.fbm.illegal.IIllegalBillService;
import nc.vo.fbm.discount.DiscountVO;
import nc.vo.fbm.pub.BaseinfoVO;
import nc.vo.fbm.pub.constant.FbmActionConstant;
import nc.vo.fbm.pub.constant.FbmBusConstant;
import nc.vo.pub.BusinessException;
import nc.vo.pub.compiler.PfParameterVO;
import nc.vo.pub.lang.UFBoolean;
import nc.vo.pub.lang.UFDate;
import nc.vo.trade.pub.HYBillVO;
import nc.vo.trade.pub.IBillStatus;
import nc.vo.uap.pf.PFBusinessException;
/**
 * 备注：贴现办理单的弃审
单据动作执行中的动态执行类的动态执行类。
 *
 * 创建日期：(2007-8-25)
 * @author 平台脚本生成
 */
public class N_36GG_UNAPPROVE extends AbstractCompiler2 {
private java.util.Hashtable m_methodReturnHas=new java.util.Hashtable();
private Hashtable m_keyHas=null;
/**
 * N_36GG_UNAPPROVE 构造子注解。
 */
public N_36GG_UNAPPROVE() {
	super();
}
/*
* 备注：平台编写规则类
* 接口执行类
*/
public Object runComClass(PfParameterVO vo) throws BusinessException {
try{
	super.m_tmpVo = vo;
	//检查单据状态是否审核通过
	int vbillstatus = (Integer)vo.m_preValueVo.getParentVO().getAttributeValue("vbillstatus");

	
	procUnApproveFlow(vo);
	DiscountVO disvo = (DiscountVO)vo.m_preValueVo.getParentVO();
	String pk_corp = InvocationInfoProxy.getInstance().getCorpCode();
	Object retObj =runClass( "nc.bs.trade.comstatus.BillUnApprove","unApproveBill","nc.vo.pub.AggregatedValueObject:36GG",vo,m_keyHas,m_methodReturnHas);

	if(disvo.getResult().equalsIgnoreCase(FbmBusConstant.DISCOUNT_RESULT_DISABLE)) {
		deleteIllegalVO(disvo);
	    CommonDAO commondao = new CommonDAO();
	    BaseinfoVO baseinfovo = new BaseinfoVO();
	    baseinfovo.setPk_baseinfo(disvo.getPk_baseinfo());
	    baseinfovo.setDisable(new UFBoolean(false));
	    commondao.updateBaseinfoDisableStatusbyPk(baseinfovo);
	}else{
		//删除银行账户账.
		DiscountUtil cbs = new DiscountUtil();
		cbs.delCMPacc((HYBillVO)retObj, pk_corp, vo.m_operator,new UFDate( vo.m_currentDate));
	}

	if(retObj instanceof HYBillVO){
//		ActionParamVO[] params = DefaultParamAdapter.changeDiscount2Param((HYBillVO)retObj,FbmActionConstant.CANCELAUDIT);
//		FbmActionFactory.getInstance().createActionClass(FbmBusConstant.BILLTYPE_DISCOUNT_TRANSACT,FbmActionConstant.CANCELAUDIT).doAction(params);
		if(vbillstatus == IBillStatus.CHECKPASS){//只有审核通过
			BusiActionFactory.getInstance().createActionClass(FbmBusConstant.BILLTYPE_DISCOUNT_TRANSACT, FbmActionConstant.CANCELAUDIT).doAction((DiscountVO)((HYBillVO)retObj).getParentVO(), FbmActionConstant.CANCELAUDIT,false);
		}
		//维护资金计划
		DiscountVO discountvo = (DiscountVO) ((HYBillVO)retObj).getParentVO();
		PlanFacade facade = new PlanFacade();
		facade.delPlanExec(discountvo, discountvo.getPk_corp());
	}
	if (retObj != null) {
		m_methodReturnHas.put("unApproveBill",retObj);
	}


	return retObj;
} catch (Exception ex) {
	if (ex instanceof BusinessException)
		throw (BusinessException) ex;
	else
    throw new PFBusinessException(ex.getMessage(), ex);
}
}

	private void deleteIllegalVO(DiscountVO disvo) throws BusinessException {
		IIllegalBillService iIllegalBillService = (IIllegalBillService)NCLocator.getInstance().lookup(IIllegalBillService.class.getName());
		iIllegalBillService.deleteIllegalBill(iIllegalBillService.queryFbmBillNoByPk_Source(disvo.getPk_baseinfo()));

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