package nc.bs.pub.action;

import java.util.Hashtable;

import nc.bs.fbm.endore.EndoreClearVoucher;
import nc.bs.fbm.endore.EndoreService;
import nc.bs.fbm.pub.action.BusiActionFactory;
import nc.bs.pub.compiler.AbstractCompiler2;
import nc.bs.trade.business.HYPubBO;
import nc.vo.fbm.endore.EndoreVO;
import nc.vo.fbm.pub.constant.FbmActionConstant;
import nc.vo.fbm.pub.constant.FbmBusConstant;
import nc.vo.fbm.pub.constant.IFBMStatus;
import nc.vo.pub.BusinessException;
import nc.vo.pub.compiler.PfParameterVO;
import nc.vo.trade.pub.HYBillVO;
import nc.vo.uap.pf.PFBusinessException;
/**
 * ��ע���������ĳ���
���ݶ���ִ���еĶ�ִ̬����Ķ�ִ̬���ࡣ
 *
 * �������ڣ�(2008-3-21)
 * @author ƽ̨�ű�����
 */
public class N_36GQ_DESTROY extends AbstractCompiler2 {
private java.util.Hashtable m_methodReturnHas=new java.util.Hashtable();
private Hashtable m_keyHas=null;
/**
 * N_36GQ_DESTROY ������ע�⡣
 */
public N_36GQ_DESTROY() {
	super();
}
/*
* ��ע��ƽ̨��д������
* �ӿ�ִ����
*/
@SuppressWarnings("unchecked")
public Object runComClass(PfParameterVO vo) throws BusinessException {
	try{
		super.m_tmpVo=vo;
		//####���ű����뺬�з���ֵ,����DLG��PNL������������з���ֵ####

		HYBillVO hbvo = new HYBillVO();

		EndoreVO endorevo = (EndoreVO)vo.m_preValueVo.getParentVO();

		//�ı�Ʊ��״̬
		BusiActionFactory.getInstance().createActionClass(
						FbmBusConstant.BILLTYPE_ENDORE, FbmActionConstant.DESTROY)
						.doAction(endorevo, FbmActionConstant.DESTROY, false);

		//�ı䱳�����״̬Ϊ�ѳ���
		new EndoreService().updateDestroyStatus(endorevo, IFBMStatus.HAS_CLEAR);

		//�����������ƾ֤
		new EndoreClearVoucher().voucher(vo.m_preValueVo, FbmBusConstant.OP_CLEAR);

		String pk_key = endorevo.getPrimaryKey();

		EndoreVO newvo =  (EndoreVO)new HYPubBO().queryByPrimaryKey(EndoreVO.class,pk_key);

		hbvo.setParentVO(newvo);

		return hbvo;

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