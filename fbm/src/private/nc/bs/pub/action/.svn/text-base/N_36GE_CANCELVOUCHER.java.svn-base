package nc.bs.pub.action;

import java.util.Hashtable;

import nc.bs.fbm.storage.StorageBillService;
import nc.bs.pub.compiler.AbstractCompiler2;
import nc.vo.fbm.storage.StoragePowerVO;
import nc.vo.fbm.storage.StorageVO;
import nc.vo.pub.BusinessException;
import nc.vo.pub.compiler.PfParameterVO;
import nc.vo.uap.pf.PFBusinessException;
/**
 * 备注：内部领用的取消制证
单据动作执行中的动态执行类的动态执行类。
 *
 * 创建日期：(2007-10-10)
 * @author 平台脚本生成
 */
public class N_36GE_CANCELVOUCHER extends AbstractCompiler2 {
private java.util.Hashtable m_methodReturnHas=new java.util.Hashtable();
private Hashtable m_keyHas=null;
/**
 * N_36GE_DELGL 构造子注解。
 */
public N_36GE_CANCELVOUCHER() {
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
	StorageBillService sbs = new StorageBillService();
	//判断中心制证时是否自动制单位的凭证.
	boolean autovouchflag = sbs.queryVoucherParam();
	StorageVO storageVo = (StorageVO) vo.m_preValueVo.getParentVO();
	StoragePowerVO power = storageVo.getPowerVo();
	boolean centerflag = power.isSettleCenter();
	if(centerflag && autovouchflag){
		retObj = sbs.cancelVoucherStorageBillVO(vo.m_preValueVo,vo.m_currentDate,vo.m_operator);
		power.setUnitBill(true);
		power.setSettleCenter(false);
		//取消制证时,要清空制证人,制证时间,会判断pk_corp与rela_corp是否相同.
		//所以为了在中心自动制单位的凭证,在取消时,要清空单位的制证人,制证时间,这里强制把它们设置成一个公司.
		power.setPk_corp(power.getRela_corp()); 
		((StorageVO) vo.m_preValueVo.getParentVO()).setPowerVo(power);
		retObj = sbs.cancelVoucherStorageBillVO(vo.m_preValueVo,vo.m_currentDate,vo.m_operator);
		
		//替单位制证后必须将power值置回来,否则跳转到凭证界面有问题
		power.setUnitBill(false);
		power.setSettleCenter(true);
	}else
	{
		retObj = sbs.cancelVoucherStorageBillVO(vo.m_preValueVo,vo.m_currentDate,vo.m_operator);
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