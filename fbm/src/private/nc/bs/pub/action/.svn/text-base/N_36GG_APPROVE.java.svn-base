package nc.bs.pub.action;

import java.util.Hashtable;

import nc.bs.fbm.discount.DiscountUtil;
import nc.bs.fbm.plan.PlanFacade;
import nc.bs.fbm.pub.CommonDAO;
import nc.bs.fbm.pub.action.BusiActionFactory;
import nc.bs.framework.common.InvocationInfoProxy;
import nc.bs.framework.common.NCLocator;
import nc.bs.pub.compiler.AbstractCompiler2;
import nc.bs.trade.business.HYPubBO;
import nc.itf.fbm.illegal.IIllegalBillService;
import nc.vo.fbm.discount.DiscountVO;
import nc.vo.fbm.illegal.IllegalVO;
import nc.vo.fbm.pub.BaseinfoVO;
import nc.vo.fbm.pub.constant.FbmActionConstant;
import nc.vo.fbm.pub.constant.FbmBusConstant;
import nc.vo.pub.BusinessException;
import nc.vo.pub.CircularlyAccessibleValueObject;
import nc.vo.pub.compiler.PfParameterVO;
import nc.vo.pub.lang.UFBoolean;
import nc.vo.pub.lang.UFDate;
import nc.vo.trade.pub.HYBillVO;
import nc.vo.trade.pub.IBillStatus;
import nc.vo.uap.pf.PFBusinessException;
/**
 * 备注：贴现办理单的审核
单据动作执行中的动态执行类的动态执行类。
 *
 * 创建日期：(2007-8-25)
 * @author 平台脚本生成
 */
public class N_36GG_APPROVE extends AbstractCompiler2 {
private java.util.Hashtable m_methodReturnHas=new java.util.Hashtable();
private Hashtable m_keyHas=null;
/**
 * N_36GG_APPROVE 构造子注解。
 */
public N_36GG_APPROVE() {
	super();
}
/*
* 备注：平台编写规则类
* 接口执行类
*/
public Object runComClass(PfParameterVO vo) throws BusinessException {
try{
	super.m_tmpVo=vo;

	DiscountVO disvo = (DiscountVO)vo.m_preValueVo.getParentVO();
	String pk_corp = InvocationInfoProxy.getInstance().getCorpCode();
	procActionFlow(vo);
	Object retObj=runClass("nc.bs.trade.comstatus.BillApprove","approveBill","nc.vo.pub.AggregatedValueObject:36GG",vo,m_keyHas,m_methodReturnHas);

	if(disvo.getResult().equalsIgnoreCase(FbmBusConstant.DISCOUNT_RESULT_DISABLE)) {
		createIllegalVO(disvo);
	    CommonDAO commondao = new CommonDAO();
	    BaseinfoVO baseinfovo = new BaseinfoVO();
	    baseinfovo.setPk_baseinfo(disvo.getPk_baseinfo());
	    baseinfovo.setDisable(new UFBoolean(true));
	    commondao.updateBaseinfoDisableStatusbyPk(baseinfovo);
	}
	//维护资金计划,只有贴现成功才维护计划
	if(disvo.getResult().equalsIgnoreCase(FbmBusConstant.DISCOUNT_RESULT_SUCCESS)) {
		DiscountVO discountvo = (DiscountVO) ((HYBillVO)retObj).getParentVO();
		discountvo.setWritePlan(disvo.getWritePlan());
		PlanFacade facade = new PlanFacade();
		facade.insertPlanExec(discountvo, discountvo.getPk_corp());
	}

	String actioncode = createBillAction(retObj);
	if (retObj != null) {
		m_methodReturnHas.put("approveBill",retObj);
	}

	if(FbmActionConstant.AUDIT.equals(actioncode)){//
		if(FbmBusConstant.DISCOUNT_RESULT_SUCCESS.equals(disvo.getResult())){
			//写银行账户账.
			DiscountUtil cbs = new DiscountUtil();
			String loginCorp = InvocationInfoProxy.getInstance().getCorpCode();
			cbs.addCMPBank((HYBillVO)retObj, loginCorp, vo.m_operator, new UFDate(vo.m_currentDate));
			cbs.addCMPBill((HYBillVO)retObj, loginCorp, vo.m_operator, new UFDate(vo.m_currentDate));
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
//		ActionParamVO[] params = DefaultParamAdapter.changeDiscount2Param((HYBillVO)retObj,actioncode);
//		FbmActionFactory.getInstance().createActionClass(FbmBusConstant.BILLTYPE_DISCOUNT_TRANSACT, actioncode).doAction(params);
		BusiActionFactory.getInstance().createActionClass(FbmBusConstant.BILLTYPE_DISCOUNT_TRANSACT, actioncode).doAction((DiscountVO)parentVO, actioncode,false);

		return actioncode;
	}
	return null;
}

	private void createIllegalVO(DiscountVO disvo) throws BusinessException {
		IIllegalBillService iIllegalBillService = (IIllegalBillService)NCLocator.getInstance().lookup(IIllegalBillService.class.getName());
		IllegalVO illegalVO = new IllegalVO();
		illegalVO.setEnddate(disvo.getDqrq());
		illegalVO.setPk_curr(disvo.getYbbz());
		illegalVO.setPayunitname(disvo.getHpcdrmc());
		illegalVO.setMoneyy(disvo.getHpje());
		illegalVO.setFbmbillno(disvo.getPk_baseinfo());
		illegalVO.setInvoicedate(disvo.getCprq());
		HYPubBO hypubbo = new HYPubBO();
		BaseinfoVO baseinfo = (BaseinfoVO)hypubbo.queryByPrimaryKey(BaseinfoVO.class, disvo.getPk_baseinfo());
		illegalVO.setInvoiceunitname(baseinfo.getInvoiceunit());
		illegalVO.setReceiveunitname(baseinfo.getReceiveunit());
		nc.vo.pub.SuperVOUtil.execFormulaWithVOs(new IllegalVO[] {illegalVO},
				new String[]{
			 		"payunitname->getColValue(v_fbm_cubasdoc,custname,pk_cubasdoc,payunitname)",
			 		"moneyy->getColValue(fbm_baseinfo,moneyy,pk_baseinfo,fbmbillno)",
					"fbmbillno->getColValue(fbm_baseinfo,fbmbillno,pk_baseinfo,fbmbillno)",
					"invoiceunitname->getColValue(v_fbm_cubasdoc,custname,pk_cubasdoc,invoiceunitname)",
					"receiveunitname->iif(getColValue(v_fbm_cubasdoc,custname,pk_cubasdoc,receiveunitname) != null,getColValue(v_fbm_cubasdoc,custname,pk_cubasdoc,receiveunitname), receiveunitname)"
					});
	    if(illegalVO != null) {
	    	iIllegalBillService.addIllegalBill(illegalVO);
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

//	private IllegalVO convertCollectionVOtoIllegalVO(DiscountVO discountVO)  throws BusinessException {
//		if(discountVO == null)
//			return null;
//		IIllegalBillService iIllegalBillService = (IIllegalBillService)NCLocator.getInstance().lookup(IIllegalBillService.class.getName());
//		IllegalVO illegalVO = new IllegalVO();
//		illegalVO.setEnddate(discountVO.getDqrq());
//		illegalVO.setPk_curr(discountVO.getYbbz());
//		illegalVO.setPayunitname(discountVO.getHpcdrmc());
//		illegalVO.setMoneyy(discountVO.getHpje());
//		illegalVO.setFbmbillno(iIllegalBillService.queryFbmBillNoByPk_Source(discountVO.getPk_baseinfo()));
//		illegalVO.setInvoicedate(discountVO.getCprq());
//		return illegalVO;
//	}
}