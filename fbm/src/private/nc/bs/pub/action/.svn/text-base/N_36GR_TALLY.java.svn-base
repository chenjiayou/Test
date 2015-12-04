package nc.bs.pub.action;

import java.util.Hashtable;

import nc.bs.fbm.pub.RegisterVO2BVO;
import nc.bs.framework.common.InvocationInfoProxy;
import nc.bs.pub.compiler.AbstractCompiler2;
import nc.bs.trade.business.HYPubBO;
import nc.impl.fbm.pub.FBMBillTallyImpl;
import nc.vo.fbm.relief.ReliefBVO;
import nc.vo.fbm.relief.ReliefVO;
import nc.vo.pub.AggregatedValueObject;
import nc.vo.pub.BusinessException;
import nc.vo.pub.compiler.PfParameterVO;
import nc.vo.pub.lang.UFDate;
import nc.vo.trade.pub.HYBillVO;
import nc.vo.uap.pf.PFBusinessException;
/**
 * 备注：票据调剂的记账
单据动作执行中的动态执行类的动态执行类。
 *
 * 创建日期：(2008-8-12)
 * @author 平台脚本生成
 */
public class N_36GR_TALLY extends AbstractCompiler2 {
private java.util.Hashtable m_methodReturnHas=new java.util.Hashtable();
private Hashtable m_keyHas=null;
/**
 * N_36GR_TALLY 构造子注解。
 */
public N_36GR_TALLY() {
	super();
}
/*
* 备注：平台编写规则类
* 接口执行类
*/
public Object runComClass(PfParameterVO vo) throws BusinessException {
try{
	super.m_tmpVo=vo;
	HYBillVO billVo = (HYBillVO) vo.m_preValueVo;
	ReliefVO reliefVo = (ReliefVO) billVo.getParentVO();
	String pk_acc = reliefVo.getInneracc();
			if (pk_acc == null || "".equals(pk_acc)) {
				throw new BusinessException(" 调剂内部账户为空，不能记账！");
			}
	String corpPk = InvocationInfoProxy.getInstance().getCorpCode();
	String userId = reliefVo.getTallyman();
	String currdate = reliefVo.getTallydate().toString();
	new FBMBillTallyImpl().tally(billVo, UFDate.getDate(currdate), userId, corpPk);
	
	String pk_relief = reliefVo.getPrimaryKey();
	HYPubBO bo = new HYPubBO();
	AggregatedValueObject retObj = bo.queryBillVOByPrimaryKey(new String[] {
					HYBillVO.class.getName(),
				ReliefVO.class.getName(), ReliefBVO.class.getName()}, pk_relief);
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
