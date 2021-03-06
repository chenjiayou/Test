package nc.bs.pub.action;

import java.util.Hashtable;

import nc.bs.fbm.pub.RegisterVO2BVO;
import nc.bs.fbm.pub.action.BusiActionFactory;
import nc.bs.fbm.storage.StorageBillService;
import nc.bs.pub.compiler.AbstractCompiler2;
import nc.bs.trade.business.HYPubBO;
import nc.vo.fbm.pub.constant.FbmActionConstant;
import nc.vo.fbm.pub.constant.FbmBusConstant;
import nc.vo.fbm.pub.constant.IFBMStatus;
import nc.vo.fbm.storage.StorageBVO;
import nc.vo.fbm.storage.StorageVO;
import nc.vo.pub.BusinessException;
import nc.vo.pub.compiler.PfParameterVO;
import nc.vo.trade.pub.HYBillVO;
import nc.vo.trade.pub.IBillStatus;
import nc.vo.uap.pf.PFBusinessException;
/**
 * 备注：内部领用的弃审
单据动作执行中的动态执行类的动态执行类。
 *
 * 创建日期：(2007-10-10)
 * @author 平台脚本生成
 */
public class N_36GE_UNAPPROVE extends AbstractCompiler2 {
private java.util.Hashtable m_methodReturnHas=new java.util.Hashtable();
private Hashtable m_keyHas=null;
/**
 * N_36GE_UNAPPROVE 构造子注解。
 */
public N_36GE_UNAPPROVE() {
	super();
}
/*
* 备注：平台编写规则类
* 接口执行类
*/
public Object runComClass(PfParameterVO vo) throws BusinessException {
try{
	super.m_tmpVo=vo;
	//检查单据状态是否审核通过
	StorageVO storageVO = (StorageVO)vo.m_preValueVo.getParentVO();
	int vbillstatus = storageVO.getVbillstatus();

	//####本脚本必须含有返回值,返回DLG和PNL的组件不允许有返回值####
	procUnApproveFlow(vo);

	
	Object retObj = runClass("nc.bs.trade.comstatus.BillUnApprove",
			"unApproveBill", "nc.vo.pub.AggregatedValueObject:36GE",
			vo, m_keyHas, m_methodReturnHas);
	if(retObj instanceof HYBillVO){
		HYBillVO retBillVO = (HYBillVO)retObj;
		//重新查询子表数据，防止修改时没有子表数据
		HYPubBO bo = new HYPubBO();
		retBillVO = (HYBillVO)bo.queryBillVOByPrimaryKey(new String[] { HYBillVO.class.getName(),
				StorageVO.class.getName(), StorageBVO.class.getName() }, retBillVO.getParentVO().getPrimaryKey());

//		ActionParamVO[] params = DefaultParamAdapter.changeInnerBack2Param(retBillVO,FbmActionConstant.CANCELAUDIT);
//		FbmActionFactory.getInstance().createActionClass(FbmBusConstant.BILLTYPE_INNERBACK,FbmActionConstant.CANCELAUDIT).doAction(params);
		if(vbillstatus == IBillStatus.CHECKPASS||vbillstatus==IFBMStatus.RETURNED){//只有审核通过
			BusiActionFactory.getInstance().createActionClass(FbmBusConstant.BILLTYPE_INNERBACK, FbmActionConstant.CANCELAUDIT).doAction(retBillVO, FbmActionConstant.CANCELAUDIT,false);
		}
		StorageVO storagevo = ((StorageVO)((HYBillVO)retObj).getParentVO());
		
		storagevo.setDealdate(null);
		retObj = new StorageBillService().updateReturnInfoForUnapprove((HYBillVO)retObj);
		
	}
	if (retObj != null) {
		m_methodReturnHas.put("unApproveBill", retObj);
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