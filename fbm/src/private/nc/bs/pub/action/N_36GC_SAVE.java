package nc.bs.pub.action;

import java.util.ArrayList;
import java.util.Hashtable;

import nc.bs.fbm.pub.RegisterVO2BVO;
import nc.bs.fbm.pub.action.BusiActionFactory;
import nc.bs.fbm.storage.StorageBillService;
import nc.bs.pub.compiler.AbstractCompiler2;
import nc.bs.trade.business.HYPubBO;
import nc.vo.fbm.pub.constant.FbmActionConstant;
import nc.vo.fbm.pub.constant.FbmBusConstant;
import nc.vo.fbm.storage.StorageBVO;
import nc.vo.fbm.storage.StorageVO;
import nc.vo.pub.BusinessException;
import nc.vo.pub.compiler.PfParameterVO;
import nc.vo.trade.pub.HYBillVO;
import nc.vo.uap.pf.PFBusinessException;
/**
 * 备注：银行领用的保存
单据动作执行中的动态执行类的动态执行类。
 *
 * 创建日期：(2007-8-21)
 * @author 平台脚本生成
 */
public class N_36GC_SAVE extends AbstractCompiler2 {
private java.util.Hashtable m_methodReturnHas=new java.util.Hashtable();
private Hashtable m_keyHas=null;
/**
 * N_36GC_SAVE 构造子注解。
 */
public N_36GC_SAVE() {
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
	Object retObj = null;
	String pk = vo.m_preValueVo.getParentVO().getPrimaryKey();
	boolean isNew = false;
	if(pk == null || pk.length() == 0){
		isNew = true;
	}
	HYPubBO bo = new HYPubBO();
	if(!isNew){
		//重新查询子表数据，防止修改时没有子表数据
		HYBillVO tmpVO = (HYBillVO)vo.m_preValueVo;

		tmpVO = (HYBillVO)bo.queryBillVOByPrimaryKey(new String[] { HYBillVO.class.getName(),
				StorageVO.class.getName(), StorageBVO.class.getName() }, tmpVO.getParentVO().getPrimaryKey());


//		ActionParamVO[] params = DefaultParamAdapter.changeBankBack2Param(tmpVO,FbmActionConstant.DELETE);
//		FbmActionFactory.getInstance().createActionClass(FbmBusConstant.BILLTYPE_BANKBACK, FbmActionConstant.DELETE).doAction(params);
		BusiActionFactory.getInstance().createActionClass(FbmBusConstant.BILLTYPE_BANKBACK, FbmActionConstant.DELETE).doAction(tmpVO, FbmActionConstant.DELETE,false);

	}

	retObj = runClass("nc.bs.trade.comsave.BillSave", "saveBill",
			"nc.vo.pub.AggregatedValueObject:36GC", vo, m_keyHas,
			m_methodReturnHas);// 36GC是单据类型

	new StorageBillService().filterVOForApproveFlow(vo, pk);

	HYBillVO retBillVO = (HYBillVO)((ArrayList)retObj).get(1);
	//重新查询子表数据，防止修改时没有子表数据
	retBillVO = (HYBillVO)bo.queryBillVOByPrimaryKey(new String[] { HYBillVO.class.getName(),
			StorageVO.class.getName(), StorageBVO.class.getName() }, retBillVO.getParentVO().getPrimaryKey());

	String actioncode = null;

	if(isNew){
		actioncode = FbmActionConstant.SAVE;
	}else{
		actioncode = FbmActionConstant.EDITSAVE;
	}

//	ActionParamVO[] params = DefaultParamAdapter.changeBankBack2Param(retBillVO,actioncode);
//	FbmActionFactory.getInstance().createActionClass(FbmBusConstant.BILLTYPE_BANKBACK, actioncode).doAction(params);
	BusiActionFactory.getInstance().createActionClass(FbmBusConstant.BILLTYPE_BANKBACK, actioncode).doAction(retBillVO, actioncode,false);

	// 执 行公式 填充参照名称，解决效率问题，减少前台与后台交互次数。
			RegisterVO2BVO.actionOPExecFormula((HYBillVO) ((ArrayList) retObj).get(1));
			
	if (retObj != null) {
		m_methodReturnHas.put("saveBill", retObj);
	}
	
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