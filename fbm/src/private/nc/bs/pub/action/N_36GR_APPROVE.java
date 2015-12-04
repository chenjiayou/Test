package nc.bs.pub.action;

import java.util.Hashtable;

import nc.bs.fbm.accdetail.ReliefVOToAccDetail;
import nc.bs.fbm.pub.RegisterVO2BVO;
import nc.bs.fbm.pub.action.BusiActionFactory;
import nc.bs.fbm.relief.ReliefService;
import nc.bs.framework.common.InvocationInfoProxy;
import nc.bs.framework.common.NCLocator;
import nc.bs.pub.compiler.AbstractCompiler2;
import nc.bs.trade.business.HYPubBO;
import nc.itf.fts.pub.ICommon;
import nc.vo.fbm.pub.constant.FbmActionConstant;
import nc.vo.fbm.pub.constant.FbmBusConstant;
import nc.vo.fbm.relief.ReliefBVO;
import nc.vo.fbm.relief.ReliefVO;
import nc.vo.pub.BusinessException;
import nc.vo.pub.CircularlyAccessibleValueObject;
import nc.vo.pub.compiler.PfParameterVO;
import nc.vo.pub.lang.UFDate;
import nc.vo.trade.pub.HYBillVO;
import nc.vo.trade.pub.IBillStatus;
import nc.vo.uap.pf.PFBusinessException;
/**
 * 备注：调剂出库的审核
单据动作执行中的动态执行类的动态执行类。
 *
 * 创建日期：(2007-10-22)
 * @author 平台脚本生成
 */
public class N_36GR_APPROVE extends AbstractCompiler2 {
private java.util.Hashtable m_methodReturnHas=new java.util.Hashtable();
private Hashtable m_keyHas=null;
/**
 * N_36GR_APPROVE 构造子注解。
 */
public N_36GR_APPROVE() {
	super();
}
/*
* 备注：平台编写规则类
* 接口执行类
*/
public Object runComClass(PfParameterVO vo) throws BusinessException {
try{
	super.m_tmpVo=vo;
	// ####本脚本必须含有返回值,返回DLG和PNL的组件不允许有返回值####
	procActionFlow(vo);
	Object retObj = runClass("nc.bs.trade.comstatus.BillApprove",
			"approveBill", "nc.vo.pub.AggregatedValueObject:36GR", vo,
			m_keyHas, m_methodReturnHas);

	if(retObj instanceof HYBillVO){
		HYBillVO retBillVO = (HYBillVO)retObj;
		//重新查询子表数据，防止修改时没有子表数据

		HYPubBO bo = new HYPubBO();
		retBillVO = (HYBillVO)bo.queryBillVOByPrimaryKey(new String[] { HYBillVO.class.getName(),
				ReliefVO.class.getName(), ReliefBVO.class.getName() }, retBillVO.getParentVO().getPrimaryKey());

		CircularlyAccessibleValueObject parentVO = retBillVO.getParentVO();
		Integer billstatus = (Integer)parentVO.getAttributeValue("vbillstatus");
		String actioncode = null;
		if(IBillStatus.CHECKPASS == billstatus.intValue()){//如果操作结果为审核通过
			actioncode = FbmActionConstant.AUDIT;
		}else{
			actioncode = FbmActionConstant.ONAUDIT;
		}
//		ActionParamVO[] params = DefaultParamAdapter.changeReliefParam(retBillVO,actioncode);
//		FbmActionFactory.getInstance().createActionClass(FbmBusConstant.BILLTYPE_RELIEF, actioncode).doAction(params);

		BusiActionFactory.getInstance().createActionClass(FbmBusConstant.BILLTYPE_RELIEF, actioncode).doAction((HYBillVO)retBillVO, actioncode,false);

		if( FbmActionConstant.AUDIT.equals(actioncode)){
			//检查受理日期
			ReliefVO reliefVO = (ReliefVO)((HYBillVO)retObj).getParentVO();
			 ICommon commonInterface = (ICommon)NCLocator.getInstance().lookup(ICommon.class);
			 UFDate dhandledate = commonInterface.getAutoProcessDate(reliefVO.getPk_corp());
			 if(dhandledate!=null){
				 if(reliefVO.getDapprovedate().after(dhandledate)){
					 throw new  BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add","UPP3620ADD-000175")/*@res "审核日期大于当前受理日期，无法写内部账户账"*/);
			 }
			 //更新受理日期字段
			 reliefVO.setDealdate(dhandledate);
			 String pk_corp = InvocationInfoProxy.getInstance().getCorpCode();
			 String businessno = commonInterface.getBusinessNo("36LW", pk_corp, null, null);
			 reliefVO.setBusinessno(businessno);
			 ((HYBillVO)retObj).setParentVO(reliefVO);
			 new HYPubBO().saveBill((HYBillVO)retObj);
			 }
			//NCdp200620425，20081209-更新持票单位为调剂单位
			 new ReliefService().updateHoldUnitForApprove((HYBillVO)retObj);

			//记内部账户账
			ReliefVOToAccDetail reliefAccdetailSrv = new ReliefVOToAccDetail();
			reliefAccdetailSrv.addAccDetail((HYBillVO)retObj,vo.m_operator,new UFDate(vo.m_currentDate));
		}

	}

	// 执 行公式 填充参照名称，解决效率问题，减少前台与后台交互次数。
			RegisterVO2BVO.actionOPExecFormula((HYBillVO) retObj);
	
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