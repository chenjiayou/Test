package nc.bs.pub.action;

import nc.vo.fbm.discount.DiscountVO;
import nc.vo.fbm.endore.EndoreVO;
import nc.vo.fbm.pub.constant.FbmBusConstant;
import nc.vo.fbm.pub.constant.IFBMStatus;
import nc.vo.pub.pf.PfUtilActionVO;
import nc.bs.fbm.discount.DiscountService;
import nc.bs.fbm.discount.DiscountVoucher;
import nc.bs.pub.compiler.*;
import nc.vo.pub.compiler.PfParameterVO;
import java.math.*;
import java.util.*;
import nc.vo.pub.lang.*;
import nc.bs.pub.pf.PfUtilTools;
import nc.bs.trade.business.HYPubBO;
import nc.vo.pub.AggregatedValueObject;
import nc.vo.pub.BusinessException;
import nc.vo.uap.pf.PFBusinessException;
/**
 * 备注：贴现办理单的取消制证
单据动作执行中的动态执行类的动态执行类。
 *
 * 创建日期：(2008-3-20)
 * @author 平台脚本生成
 */
public class N_36GG_CANCELVOUCHER extends AbstractCompiler2 {
private java.util.Hashtable m_methodReturnHas=new java.util.Hashtable();
private Hashtable m_keyHas=null;
/**
 * N_36GG_CANCELVOUCHER 构造子注解。
 */
public N_36GG_CANCELVOUCHER() {
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
	Object retObj = new DiscountVoucher().voucher(vo.m_preValueVo, FbmBusConstant.OP_CANCELVOUCHER);

	DiscountVO discountvo = (DiscountVO)vo.m_preValueVo.getParentVO();

	discountvo.setUnitvoucher(new UFBoolean(false));
	new DiscountService().updateUnitVoucher(discountvo);

	AggregatedValueObject avo = (AggregatedValueObject)retObj;
	DiscountVO oldvo = (DiscountVO)avo.getParentVO();
	String pk_key = oldvo.getPrimaryKey();

	DiscountVO newvo =  (DiscountVO)new HYPubBO().queryByPrimaryKey(DiscountVO.class,pk_key);

	avo.setParentVO(newvo);

	return avo;

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