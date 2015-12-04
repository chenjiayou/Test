package nc.bs.pub.action;

import java.util.Hashtable;

import nc.bs.fbm.pub.UnVoucherCheckService;
import nc.bs.fbm.storage.StorageBillService;
import nc.bs.pub.compiler.AbstractCompiler2;
import nc.vo.fbm.storage.StoragePowerVO;
import nc.vo.fbm.storage.StorageVO;
import nc.vo.pub.BusinessException;
import nc.vo.pub.compiler.PfParameterVO;
import nc.vo.uap.pf.PFBusinessException;
/**
 * ��ע���ڲ���ŵ�ȡ����֤
���ݶ���ִ���еĶ�ִ̬����Ķ�ִ̬���ࡣ
 *
 * �������ڣ�(2007-10-10)
 * @author ƽ̨�ű�����
 */
public class N_36GD_CANCELVOUCHER extends AbstractCompiler2 {
private java.util.Hashtable m_methodReturnHas=new java.util.Hashtable();
private Hashtable m_keyHas=null;
/**
 * N_36GD_DELGL ������ע�⡣
 */
public N_36GD_CANCELVOUCHER() {
	super();
}
/*
* ��ע��ƽ̨��д������
* �ӿ�ִ����
*/
public Object runComClass(PfParameterVO vo) throws BusinessException {
try{
	super.m_tmpVo=vo;
	UnVoucherCheckService srv = new UnVoucherCheckService();
	if (null != vo.m_preValueVo && vo.m_preValueVo.getParentVO() != null && vo.m_preValueVo.getParentVO().getPrimaryKey() !=null){
		srv.checkUnVoucher(vo.m_preValueVo.getParentVO().getPrimaryKey());
	}
	//####���ű����뺬�з���ֵ,����DLG��PNL������������з���ֵ####
	Object retObj=null;
	StorageBillService sbs = new StorageBillService();
	//�ж�������֤ʱ�Ƿ��Զ��Ƶ�λ��ƾ֤.
	boolean autovouchflag = sbs.queryVoucherParam();
	StorageVO storageVo = (StorageVO) vo.m_preValueVo.getParentVO();
	StoragePowerVO power = storageVo.getPowerVo();
	boolean centerflag = power.isSettleCenter();
	if(centerflag && autovouchflag){
		retObj = sbs.cancelVoucherStorageBillVO(vo.m_preValueVo,vo.m_currentDate,vo.m_operator);
		power.setUnitBill(true);
		power.setSettleCenter(false);
		//ȡ����֤ʱ,Ҫ�����֤��,��֤ʱ��,���ж�pk_corp��rela_corp�Ƿ���ͬ.
		//����Ϊ���������Զ��Ƶ�λ��ƾ֤,��ȡ��ʱ,Ҫ��յ�λ����֤��,��֤ʱ��,����ǿ�ư��������ó�һ����˾.
		power.setPk_corp(power.getRela_corp()); 
		((StorageVO) vo.m_preValueVo.getParentVO()).setPowerVo(power);
		retObj = sbs.cancelVoucherStorageBillVO(vo.m_preValueVo,vo.m_currentDate,vo.m_operator);
		
		//�浥λ��֤����뽫powerֵ�û���,������ת��ƾ֤����������
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