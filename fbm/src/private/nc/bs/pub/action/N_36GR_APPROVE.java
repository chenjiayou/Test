package nc.bs.pub.action;

import java.util.Hashtable;

import nc.bs.fbm.accdetail.ReliefVOToAccDetail;
import nc.bs.fbm.pub.RegisterVO2BVO;
import nc.bs.fbm.pub.action.BusiActionFactory;
import nc.bs.fbm.relief.ReliefService;
import nc.bs.framework.common.InvocationInfoProxy;
import nc.bs.framework.common.NCLocator;
import nc.bs.pub.compiler.AbstractCompiler2;
import nc.bs.trade.business.HYPubBO;
import nc.itf.fts.pub.ICommon;
import nc.vo.fbm.pub.constant.FbmActionConstant;
import nc.vo.fbm.pub.constant.FbmBusConstant;
import nc.vo.fbm.relief.ReliefBVO;
import nc.vo.fbm.relief.ReliefVO;
import nc.vo.pub.BusinessException;
import nc.vo.pub.CircularlyAccessibleValueObject;
import nc.vo.pub.compiler.PfParameterVO;
import nc.vo.pub.lang.UFDate;
import nc.vo.trade.pub.HYBillVO;
import nc.vo.trade.pub.IBillStatus;
import nc.vo.uap.pf.PFBusinessException;
/**
 * ��ע��������������
���ݶ���ִ���еĶ�ִ̬����Ķ�ִ̬���ࡣ
 *
 * �������ڣ�(2007-10-22)
 * @author ƽ̨�ű�����
 */
public class N_36GR_APPROVE extends AbstractCompiler2 {
private java.util.Hashtable m_methodReturnHas=new java.util.Hashtable();
private Hashtable m_keyHas=null;
/**
 * N_36GR_APPROVE ������ע�⡣
 */
public N_36GR_APPROVE() {
	super();
}
/*
* ��ע��ƽ̨��д������
* �ӿ�ִ����
*/
public Object runComClass(PfParameterVO vo) throws BusinessException {
try{
	super.m_tmpVo=vo;
	// ####���ű����뺬�з���ֵ,����DLG��PNL������������з���ֵ####
	procActionFlow(vo);
	Object retObj = runClass("nc.bs.trade.comstatus.BillApprove",
			"approveBill", "nc.vo.pub.AggregatedValueObject:36GR", vo,
			m_keyHas, m_methodReturnHas);

	if(retObj instanceof HYBillVO){
		HYBillVO retBillVO = (HYBillVO)retObj;
		//���²�ѯ�ӱ����ݣ���ֹ�޸�ʱû���ӱ�����

		HYPubBO bo = new HYPubBO();
		retBillVO = (HYBillVO)bo.queryBillVOByPrimaryKey(new String[] { HYBillVO.class.getName(),
				ReliefVO.class.getName(), ReliefBVO.class.getName() }, retBillVO.getParentVO().getPrimaryKey());

		CircularlyAccessibleValueObject parentVO = retBillVO.getParentVO();
		Integer billstatus = (Integer)parentVO.getAttributeValue("vbillstatus");
		String actioncode = null;
		if(IBillStatus.CHECKPASS == billstatus.intValue()){//����������Ϊ���ͨ��
			actioncode = FbmActionConstant.AUDIT;
		}else{
			actioncode = FbmActionConstant.ONAUDIT;
		}
//		ActionParamVO[] params = DefaultParamAdapter.changeReliefParam(retBillVO,actioncode);
//		FbmActionFactory.getInstance().createActionClass(FbmBusConstant.BILLTYPE_RELIEF, actioncode).doAction(params);

		BusiActionFactory.getInstance().createActionClass(FbmBusConstant.BILLTYPE_RELIEF, actioncode).doAction((HYBillVO)retBillVO, actioncode,false);

		if( FbmActionConstant.AUDIT.equals(actioncode)){
			//�����������
			ReliefVO reliefVO = (ReliefVO)((HYBillVO)retObj).getParentVO();
			 ICommon commonInterface = (ICommon)NCLocator.getInstance().lookup(ICommon.class);
			 UFDate dhandledate = commonInterface.getAutoProcessDate(reliefVO.getPk_corp());
			 if(dhandledate!=null){
				 if(reliefVO.getDapprovedate().after(dhandledate)){
					 throw new  BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add","UPP3620ADD-000175")/*@res "������ڴ��ڵ�ǰ�������ڣ��޷�д�ڲ��˻���"*/);
			 }
			 //�������������ֶ�
			 reliefVO.setDealdate(dhandledate);
			 String pk_corp = InvocationInfoProxy.getInstance().getCorpCode();
			 String businessno = commonInterface.getBusinessNo("36LW", pk_corp, null, null);
			 reliefVO.setBusinessno(businessno);
			 ((HYBillVO)retObj).setParentVO(reliefVO);
			 new HYPubBO().saveBill((HYBillVO)retObj);
			 }
			//NCdp200620425��20081209-���³�Ʊ��λΪ������λ
			 new ReliefService().updateHoldUnitForApprove((HYBillVO)retObj);

			//���ڲ��˻���
			ReliefVOToAccDetail reliefAccdetailSrv = new ReliefVOToAccDetail();
			reliefAccdetailSrv.addAccDetail((HYBillVO)retObj,vo.m_operator,new UFDate(vo.m_currentDate));
		}

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