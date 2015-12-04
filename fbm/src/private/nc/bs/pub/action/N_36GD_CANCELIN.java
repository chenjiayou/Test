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
 * ��ע���ڲ���ŵ�ȡ�����
���ݶ���ִ���еĶ�ִ̬����Ķ�ִ̬���ࡣ
 *
 * �������ڣ�(2007-10-10)
 * @author ƽ̨�ű�����
 */
public class N_36GD_CANCELIN extends AbstractCompiler2 {
private java.util.Hashtable m_methodReturnHas=new java.util.Hashtable();
private Hashtable m_keyHas=null;
/**
 * N_36GD_CANCELIN ������ע�⡣
 */
public N_36GD_CANCELIN() {
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
	Object retObj=null;
	HYBillVO tmpVO = (HYBillVO)vo.m_preValueVo;
	if(tmpVO instanceof HYBillVO){
		//���²�ѯ�ӱ����ݣ���ֹ�޸�ʱû���ӱ�����
		HYPubBO bo = new HYPubBO();
		tmpVO = (HYBillVO)bo.queryBillVOByPrimaryKey(new String[] { HYBillVO.class.getName(),
				StorageVO.class.getName(), StorageBVO.class.getName() }, tmpVO.getParentVO().getPrimaryKey());

//		ActionParamVO[] params = DefaultParamAdapter.changeInnerKeep2Param(tmpVO,FbmActionConstant.CANCELINPUT);
//		FbmActionFactory.getInstance().createActionClass(FbmBusConstant.BILLTYPE_INNERKEEP,FbmActionConstant.CANCELINPUT).doAction(params);
		BusiActionFactory.getInstance().createActionClass(FbmBusConstant.BILLTYPE_INNERKEEP, FbmActionConstant.CANCELINPUT).doAction(tmpVO, FbmActionConstant.CANCELINPUT,false);

		retObj = new StorageBillService().cancelInStorageBillVO(tmpVO);

		//ά���ڲ��˻���
		StorageVOToAccDetail storageSrv = new StorageVOToAccDetail();
		InvocationInfoProxy info = InvocationInfoProxy.getInstance();
		storageSrv.delAccDetail( (HYBillVO)retObj,info.getCorpCode(),vo.m_operator,new UFDate(vo.m_currentDate));

		//����Ʊ����
		StorageUtil storageUtil = new StorageUtil();
		storageUtil.delCMPacc((HYBillVO)retObj,info.getCorpCode(),vo.m_operator,new UFDate(vo.m_currentDate));


	}
	// ִ �й�ʽ ���������ƣ����Ч�����⣬����ǰ̨���̨����������
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