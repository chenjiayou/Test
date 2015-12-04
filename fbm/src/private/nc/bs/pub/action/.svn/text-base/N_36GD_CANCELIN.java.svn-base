package nc.bs.pub.action;

import java.util.Hashtable;

import nc.bs.fbm.accdetail.StorageVOToAccDetail;
import nc.bs.fbm.pub.RegisterVO2BVO;
import nc.bs.fbm.pub.action.BusiActionFactory;
import nc.bs.fbm.storage.StorageBillService;
import nc.bs.fbm.storage.StorageUtil;
import nc.bs.framework.common.InvocationInfoProxy;
import nc.bs.pub.compiler.AbstractCompiler2;
import nc.bs.trade.business.HYPubBO;
import nc.vo.fbm.pub.constant.FbmActionConstant;
import nc.vo.fbm.pub.constant.FbmBusConstant;
import nc.vo.fbm.storage.StorageBVO;
import nc.vo.fbm.storage.StorageVO;
import nc.vo.pub.BusinessException;
import nc.vo.pub.compiler.PfParameterVO;
import nc.vo.pub.lang.UFDate;
import nc.vo.trade.pub.HYBillVO;
import nc.vo.uap.pf.PFBusinessException;
/**
 * 备注：内部存放的取消入库
单据动作执行中的动态执行类的动态执行类。
 *
 * 创建日期：(2007-10-10)
 * @author 平台脚本生成
 */
public class N_36GD_CANCELIN extends AbstractCompiler2 {
private java.util.Hashtable m_methodReturnHas=new java.util.Hashtable();
private Hashtable m_keyHas=null;
/**
 * N_36GD_CANCELIN 构造子注解。
 */
public N_36GD_CANCELIN() {
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
	HYBillVO tmpVO = (HYBillVO)vo.m_preValueVo;
	if(tmpVO instanceof HYBillVO){
		//重新查询子表数据，防止修改时没有子表数据
		HYPubBO bo = new HYPubBO();
		tmpVO = (HYBillVO)bo.queryBillVOByPrimaryKey(new String[] { HYBillVO.class.getName(),
				StorageVO.class.getName(), StorageBVO.class.getName() }, tmpVO.getParentVO().getPrimaryKey());

//		ActionParamVO[] params = DefaultParamAdapter.changeInnerKeep2Param(tmpVO,FbmActionConstant.CANCELINPUT);
//		FbmActionFactory.getInstance().createActionClass(FbmBusConstant.BILLTYPE_INNERKEEP,FbmActionConstant.CANCELINPUT).doAction(params);
		BusiActionFactory.getInstance().createActionClass(FbmBusConstant.BILLTYPE_INNERKEEP, FbmActionConstant.CANCELINPUT).doAction(tmpVO, FbmActionConstant.CANCELINPUT,false);

		retObj = new StorageBillService().cancelInStorageBillVO(tmpVO);

		//维护内部账户账
		StorageVOToAccDetail storageSrv = new StorageVOToAccDetail();
		InvocationInfoProxy info = InvocationInfoProxy.getInstance();
		storageSrv.delAccDetail( (HYBillVO)retObj,info.getCorpCode(),vo.m_operator,new UFDate(vo.m_currentDate));

		//中心票据账
		StorageUtil storageUtil = new StorageUtil();
		storageUtil.delCMPacc((HYBillVO)retObj,info.getCorpCode(),vo.m_operator,new UFDate(vo.m_currentDate));


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