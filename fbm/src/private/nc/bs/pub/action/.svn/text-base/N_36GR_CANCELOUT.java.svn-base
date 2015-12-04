package nc.bs.pub.action;

import java.util.ArrayList;
import java.util.Hashtable;

import nc.bs.fbm.pub.action.BusiActionFactory;
import nc.bs.pub.compiler.AbstractCompiler2;
import nc.vo.fbm.pub.constant.FbmActionConstant;
import nc.vo.fbm.pub.constant.FbmBusConstant;
import nc.vo.fbm.pub.constant.IFBMStatus;
import nc.vo.fbm.relief.ReliefVO;
import nc.vo.pub.BusinessException;
import nc.vo.pub.compiler.PfParameterVO;
import nc.vo.trade.pub.HYBillVO;
import nc.vo.uap.pf.PFBusinessException;
/**
 * 备注：调剂出库的制证
单据动作执行中的动态执行类的动态执行类。
 *
 * 创建日期：(2007-10-22)
 * @author 平台脚本生成
 */
public class N_36GR_CANCELOUT extends AbstractCompiler2 {
private java.util.Hashtable m_methodReturnHas=new java.util.Hashtable();
private Hashtable m_keyHas=null;
/**
 * N_36GR_VOUCHER 构造子注解。
 */
public N_36GR_CANCELOUT() {
	super();
}
/*
* 备注：平台编写规则类
* 接口执行类
*/
public Object runComClass(PfParameterVO vo) throws BusinessException {
try{
	super.m_tmpVo = vo;
	Object retObj = null;

	ReliefVO reliefVO = (ReliefVO) vo.m_preValueVo.getParentVO();
	reliefVO.setVbillstatus(new Integer(IFBMStatus.CHECKPASS));// 将单据状态改为审批通过
	retObj = runClass("nc.bs.trade.comsave.BillSave", "saveBill", //更新单据状态
			"nc.vo.pub.AggregatedValueObject:36GR", vo, m_keyHas,
			m_methodReturnHas);

	HYBillVO retBillVO = (HYBillVO) ((ArrayList) retObj).get(1);
//	ActionParamVO[] params = DefaultParamAdapter.changeReliefParam(
//			retBillVO, FbmActionConstant.CANCELOUTPUT);
//	FbmActionFactory.getInstance().createActionClass(
//			FbmBusConstant.BILLTYPE_RELIEF,
//			FbmActionConstant.CANCELOUTPUT).doAction(params);
	BusiActionFactory.getInstance().createActionClass(FbmBusConstant.BILLTYPE_RELIEF, FbmActionConstant.CANCELOUTPUT).doAction(retBillVO, FbmActionConstant.CANCELOUTPUT,false);

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