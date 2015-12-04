package nc.bs.pub.action;

import java.util.Hashtable;

import nc.bs.fbm.accdetail.StorageVOToAccDetail;
import nc.bs.fbm.pub.RegisterVO2BVO;
import nc.bs.fbm.pub.action.BusiActionFactory;
import nc.bs.fbm.storage.StorageBillService;
import nc.bs.fbm.storage.StorageUtil;
import nc.bs.framework.common.InvocationInfoProxy;
import nc.bs.framework.common.NCLocator;
import nc.bs.pub.compiler.AbstractCompiler2;
import nc.bs.trade.business.HYPubBO;
import nc.itf.fts.pub.ICommon;
import nc.vo.fbm.pub.FBMPublicQry;
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
 * 备注：内部存放的入库
单据动作执行中的动态执行类的动态执行类。
 *
 * 创建日期：(2007-10-10)
 * @author 平台脚本生成
 */
public class N_36GD_INPUT extends AbstractCompiler2 {
private java.util.Hashtable m_methodReturnHas=new java.util.Hashtable();
private Hashtable m_keyHas=null;
/**
 * N_36GD_INPUT 构造子注解。
 */
public N_36GD_INPUT() {
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

		((StorageVO)tmpVO.getParentVO()).setInputdate(new UFDate(vo.m_currentDate));
		tmpVO.getParentVO().setAttributeValue(StorageVO.INPUTPERSON,vo.m_operator);
//		ActionParamVO[] params = DefaultParamAdapter.changeInnerKeep2Param(tmpVO,FbmActionConstant.INPUT_SUCCESS);
//		FbmActionFactory.getInstance().createActionClass(FbmBusConstant.BILLTYPE_INNERKEEP,FbmActionConstant.INPUT_SUCCESS).doAction(params);
		BusiActionFactory.getInstance().createActionClass(FbmBusConstant.BILLTYPE_INNERKEEP, FbmActionConstant.INPUT_SUCCESS).doAction(tmpVO, FbmActionConstant.INPUT_SUCCESS,false);

		retObj = new StorageBillService().inputStorageBillVO((HYBillVO)vo.m_preValueVo,vo.m_currentDate);

		// NCdp200603114
		// 登陆日期小于受理日期时，内部领用单及内部托管单应可以确认，即视同倒起息，按确认日期即登陆日期维护内部账户账。目前不可以

		//如果确认日期小于受理日期，则报错
		StorageVO storageVO = (StorageVO)((HYBillVO)retObj).getParentVO();
		UFDate inputdate = storageVO.getInputdate();//确认日期,即登录日期
		UFDate submitdate = storageVO.getSubmitdate();//提交日期
		ICommon commonInterface = (ICommon)NCLocator.getInstance().lookup(ICommon.class);
		UFDate dealdate = commonInterface.getAutoProcessDate(FBMPublicQry.retrivePk_centcorpByPkCorp(storageVO.getPk_corp()));

		if(dealdate!=null){//说明安装了结算产品
			if(inputdate!=null&&inputdate.after(dealdate)){
				throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcode112","UPPFBMCODE112-000030")/*@res "确认日期不能大于当前受理日期"*/);
			}
		}

		if(submitdate !=null && submitdate.after(inputdate)){
			throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcode112","UPPFBMCODE112-000031")/*@res "确认日期必须大于提交日期"*/);
		}
		//		if(storageVO.getDealdate().after(storageVO.getInputdate())){
//			throw new  BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add","UPP3620ADD-000174")/*@res "确认日期小于受理日期，无法写内部账户账"*/);
//		}

		//记内部账户账
		StorageVOToAccDetail storageSrv = new StorageVOToAccDetail();
		InvocationInfoProxy info = InvocationInfoProxy.getInstance();

		storageSrv.addAccDetail((HYBillVO)retObj ,info.getCorpCode(),vo.m_operator,new UFDate(vo.m_currentDate));


		//记中心票据账
		StorageUtil storageUtil = new StorageUtil();
		storageUtil.insertBankAcc4Center((HYBillVO)retObj,info.getCorpCode(),vo.m_operator,new UFDate(vo.m_currentDate));

	}
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