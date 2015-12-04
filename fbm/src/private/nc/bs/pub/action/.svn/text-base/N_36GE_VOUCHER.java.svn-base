package nc.bs.pub.action;

import nc.vo.fbm.storage.StoragePowerVO;
import nc.vo.fbm.storage.StorageVO;
import nc.vo.pub.pf.PfUtilActionVO;
import nc.bs.fbm.storage.StorageBillService;
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
 * 备注：内部领用的制证
单据动作执行中的动态执行类的动态执行类。
 *
 * 创建日期：(2007-10-10)
 * @author 平台脚本生成
 */
public class N_36GE_VOUCHER extends AbstractCompiler2 {
private java.util.Hashtable m_methodReturnHas=new java.util.Hashtable();
private Hashtable m_keyHas=null;
/**
 * N_36GE_VOUCHER 构造子注解。
 */
public N_36GE_VOUCHER() {
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
	//判断当前公司是否为中心,如果为中心判断是否自动制单位的凭证.
	boolean autovouchflag = sbs.queryVoucherParam();
	
	StorageVO storageVo = (StorageVO) vo.m_preValueVo.getParentVO();
	StoragePowerVO power = storageVo.getPowerVo();
	
	boolean centerflag = power.isSettleCenter();
	if(centerflag && autovouchflag){
		retObj = new StorageBillService().makeVoucherStorageBillVO(vo.m_preValueVo,vo.m_currentDate,vo.m_operator);
		power.setUnitBill(true);
		power.setSettleCenter(false);
		StorageVO stvo = (StorageVO)vo.m_preValueVo.getParentVO();
		stvo.setUnitvoucherdate(power.getCurrDate());
		stvo.setUnitvouchermanid(power.getCurrUserPK());
		stvo.setUnitvoucher(new UFBoolean(true));
		vo.m_preValueVo.setParentVO(stvo);
		((StorageVO) vo.m_preValueVo.getParentVO()).setPowerVo(power);
		HYPubBO pubbo = new HYPubBO();
		StorageVO newstvo = (StorageVO)pubbo.queryByPrimaryKey(StorageVO.class,stvo.getPrimaryKey());
		//判断单位是否已制证,如果已制证,中心不再生成单位的凭证.
		if(newstvo.getUnitvoucher().booleanValue()==false){
			retObj = new StorageBillService().makeVoucherStorageBillVO(vo.m_preValueVo,vo.m_currentDate,vo.m_operator);
		}
		//retObj = new StorageBillService().makeVoucherStorageBillVO(vo.m_preValueVo,vo.m_currentDate,vo.m_operator);

		//替单位制证后必须将power值置回来,否则跳转到凭证界面有问题
		power.setUnitBill(false);
		power.setSettleCenter(true);
	}else
	{
		retObj = new StorageBillService().makeVoucherStorageBillVO(vo.m_preValueVo,vo.m_currentDate,vo.m_operator);
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