package nc.bs.pub.action;

import java.util.Hashtable;

import nc.bs.dao.BaseDAO;
import nc.bs.fbm.pub.OuterRelationDAO;
import nc.bs.fbm.pub.RegisterVO2BVO;
import nc.bs.pub.compiler.AbstractCompiler2;
import nc.bs.trade.business.HYPubBO;
import nc.vo.fbm.outer.OuterVO;
import nc.vo.fbm.returnbill.ReturnBVO;
import nc.vo.fbm.returnbill.ReturnVO;
import nc.vo.pub.BusinessException;
import nc.vo.pub.compiler.PfParameterVO;
import nc.vo.trade.pub.HYBillVO;
import nc.vo.trade.pub.IBillStatus;
import nc.vo.uap.pf.PFBusinessException;
/**
 * 备注：集中退票的取消转出
单据动作执行中的动态执行类的动态执行类。
 *
 * 创建日期：(2007-10-10)
 * @author 平台脚本生成
 */
public class N_36GN_CANCELTRANS extends AbstractCompiler2 {
private java.util.Hashtable m_methodReturnHas=new java.util.Hashtable();
private Hashtable m_keyHas=null;
/**
 * N_36GN_CANCELTRANS 构造子注解。
 */
public N_36GN_CANCELTRANS() {
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
	Object retObj=null;
	ReturnVO parent = (ReturnVO)vo.m_preValueVo.getParentVO();
	//校验是否转出生成单据均被删除
	OuterRelationDAO outerDao = new OuterRelationDAO();
	OuterVO[] outerVos = (OuterVO[])outerDao.queryByPkBusibill(parent.getPrimaryKey());
	if(outerVos!=null && outerVos.length > 0){
		throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000364")/* @res"转出生成的收付款单据未删除，无法取消转出"*/);
	}
	//修改单据状态为已审核
	BaseDAO dao = new BaseDAO();
	ReturnVO returnVO = (ReturnVO)dao.retrieveByPK(ReturnVO.class, parent.getPrimaryKey());
	returnVO.setVbillstatus(IBillStatus.CHECKPASS);
	dao.updateVO(returnVO);

	HYPubBO bo = new HYPubBO();
	
	Object obj = bo.queryBillVOByPrimaryKey(new String[] {
					HYBillVO.class.getName(), ReturnVO.class.getName(),
					ReturnBVO.class.getName() }, parent.getPrimaryKey());
	// 执 行公式 填充参照名称，解决效率问题，减少前台与后台交互次数。
			RegisterVO2BVO.actionOPExecFormula((HYBillVO) obj);
		return obj;
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