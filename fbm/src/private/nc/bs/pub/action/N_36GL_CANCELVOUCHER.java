package nc.bs.pub.action;

import nc.vo.fbm.proxy.OuterProxy;
import nc.vo.fbm.pub.constant.FbmBusConstant;
import nc.vo.fbm.register.RegisterVO;
import nc.vo.pub.pf.PfUtilActionVO;
import nc.bs.dao.BaseDAO;
import nc.bs.fbm.invoice.InvoiceVoucher;
import nc.bs.pub.compiler.*;
import nc.vo.pub.compiler.PfParameterVO;
import java.math.*;
import java.util.*;
import nc.vo.pub.lang.*;
import nc.bs.pub.pf.PfUtilTools;
import nc.itf.dap.pub.IDapSendMessage;
import nc.vo.pub.AggregatedValueObject;
import nc.vo.pub.BusinessException;
import nc.vo.trade.pub.HYBillVO;
import nc.vo.uap.pf.PFBusinessException;
/**
 * 备注：票据开票的取消制证
单据动作执行中的动态执行类的动态执行类。
 *
 * 创建日期：(2008-8-22)
 * @author 平台脚本生成
 */
public class N_36GL_CANCELVOUCHER extends AbstractCompiler2 {
private java.util.Hashtable m_methodReturnHas=new java.util.Hashtable();
private Hashtable m_keyHas=null;
/**
 * N_36GL_CANCELVOUCHER 构造子注解。
 */
public N_36GL_CANCELVOUCHER() {
	super();
}
/*
* 备注：平台编写规则类
* 接口执行类
*/
public Object runComClass(PfParameterVO vo) throws BusinessException {
try{
	super.m_tmpVo=vo;
	
	IDapSendMessage dapservice = OuterProxy.getDapSendMessageService();
	HYBillVO billvo = (HYBillVO)vo.m_preValueVo;
	dapservice.sendMessage(new InvoiceVoucher().getDapMsgVo(billvo,FbmBusConstant.OP_CANCELVOUCHER), billvo);
	
	RegisterVO regVO = (RegisterVO)vo.m_preValueVo.getParentVO();
	regVO.setVoucher(new UFBoolean(false));
	BaseDAO dao = new BaseDAO();
	dao.updateVO(regVO);
	
	regVO = (RegisterVO)dao.retrieveByPK(RegisterVO.class, regVO.getPrimaryKey());
	HYBillVO retObj= new HYBillVO();
	retObj.setParentVO(regVO);
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
	return "	Object retObj=null;\n	return retObj;\n";}
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
