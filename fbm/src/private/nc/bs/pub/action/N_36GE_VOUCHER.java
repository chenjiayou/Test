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
 * ��ע���ڲ����õ���֤
���ݶ���ִ���еĶ�ִ̬����Ķ�ִ̬���ࡣ
 *
 * �������ڣ�(2007-10-10)
 * @author ƽ̨�ű�����
 */
public class N_36GE_VOUCHER extends AbstractCompiler2 {
private java.util.Hashtable m_methodReturnHas=new java.util.Hashtable();
private Hashtable m_keyHas=null;
/**
 * N_36GE_VOUCHER ������ע�⡣
 */
public N_36GE_VOUCHER() {
	super();
}
/*
* ��ע��ƽ̨��д������
* �ӿ�ִ����
*/
public Object runComClass(PfParameterVO vo) throws BusinessException {
try{
	super.m_tmpVo=vo;
	//####���ű����뺬�з���ֵ,����DLG��PNL������������з���ֵ####
	Object retObj = null;
	StorageBillService sbs = new StorageBillService();
	//�жϵ�ǰ��˾�Ƿ�Ϊ����,���Ϊ�����ж��Ƿ��Զ��Ƶ�λ��ƾ֤.
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
		//�жϵ�λ�Ƿ�����֤,�������֤,���Ĳ������ɵ�λ��ƾ֤.
		if(newstvo.getUnitvoucher().booleanValue()==false){
			retObj = new StorageBillService().makeVoucherStorageBillVO(vo.m_preValueVo,vo.m_currentDate,vo.m_operator);
		}
		//retObj = new StorageBillService().makeVoucherStorageBillVO(vo.m_preValueVo,vo.m_currentDate,vo.m_operator);

		//�浥λ��֤����뽫powerֵ�û���,������ת��ƾ֤����������
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
* ��ע��ƽ̨��дԭʼ�ű�
*/
public String getCodeRemark(){
	return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add","UPP3620ADD-000173")/*@res "	//####���ű����뺬�з���ֵ,����DLG��PNL������������з���ֵ####\n	Object retObj=null;\n	return retObj;\n"*/;}
/*
* ��ע�����ýű�������HAS
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