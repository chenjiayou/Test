package nc.bs.pub.action;

import nc.vo.fbm.impawn.ImpawnVO;
import nc.vo.fbm.pub.constant.FbmActionConstant;
import nc.vo.fbm.pub.constant.FbmBusConstant;
import nc.vo.pub.pf.PfUtilActionVO;
import nc.bs.fbm.pub.action.BusiActionFactory;
import nc.bs.pub.compiler.*;
import nc.vo.pub.compiler.PfParameterVO;
import java.math.*;
import java.util.*;
import nc.vo.pub.lang.*;
import nc.bs.pub.pf.PfUtilTools;
import nc.bs.trade.business.HYPubBO;
import nc.vo.pub.AggregatedValueObject;
import nc.vo.pub.BusinessException;
import nc.vo.trade.pub.HYBillVO;
import nc.vo.uap.pf.PFBusinessException;
/**
 * 备注：票据质押的取消质押回收
单据动作执行中的动态执行类的动态执行类。
 *
 * 创建日期：(2008-9-22)
 * @author 平台脚本生成
 */
public class N_36GO_CANCELBACK extends AbstractCompiler2 {
private java.util.Hashtable m_methodReturnHas=new java.util.Hashtable();
private Hashtable m_keyHas=null;
/**
 * N_36GO_CANCELBACK 构造子注解。
 */
public N_36GO_CANCELBACK() {
	super();
}
/*
* 备注：平台编写规则类
* 接口执行类
*/
public Object runComClass(PfParameterVO vo) throws BusinessException {
try{
	super.m_tmpVo = vo;
	HYBillVO retBillVO = (HYBillVO)vo.m_preValueVo;
	BusiActionFactory.getInstance().createActionClass(FbmBusConstant.BILLTYPE_IMPAWN, FbmActionConstant.CANCELIMPAWNBACK).doAction((ImpawnVO)retBillVO.getParentVO(), FbmActionConstant.CANCELIMPAWNBACK,false);


	retBillVO.setParentVO(new HYPubBO().queryByPrimaryKey(ImpawnVO.class, retBillVO.getParentVO().getPrimaryKey()));

return retBillVO;
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
	return "	Object ret = null;\nreturn ret;\n";}
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
