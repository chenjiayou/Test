package nc.bs.pub.action;

import java.util.ArrayList;
import java.util.Hashtable;

import nc.bs.fbm.invoice.InvoiceBillService;
import nc.bs.fbm.pub.action.BusiActionFactory;
import nc.bs.framework.common.InvocationInfoProxy;
import nc.bs.pub.compiler.AbstractCompiler2;
import nc.bs.trade.business.HYPubBO;
import nc.vo.fbm.pub.constant.FbmActionConstant;
import nc.vo.fbm.pub.constant.FbmBusConstant;
import nc.vo.fbm.register.RegisterVO;
import nc.vo.pub.BusinessException;
import nc.vo.pub.compiler.PfParameterVO;
import nc.vo.pub.lang.UFDate;
import nc.vo.trade.pub.HYBillVO;
import nc.vo.uap.pf.PFBusinessException;
import nc.vo.uapbd.bankaccount.BankaccbasVO;
/**
 * ��ע��Ʊ�ݿ�Ʊ�ı���
���ݶ���ִ���еĶ�ִ̬����Ķ�ִ̬���ࡣ
 *
 * �������ڣ�(2007-9-4)
 * @author ƽ̨�ű�����
 */
public class N_36GL_SAVE extends AbstractCompiler2 {
private java.util.Hashtable m_methodReturnHas=new java.util.Hashtable();
private Hashtable m_keyHas=null;
/**
 * N_36GL_SAVE ������ע�⡣
 */
public N_36GL_SAVE() {
	super();
}
/*
* ��ע��ƽ̨��д������
* �ӿ�ִ����
*/
public Object runComClass(PfParameterVO vo) throws BusinessException {
try{
	super.m_tmpVo=vo;
	boolean isnew = false;
	RegisterVO headVo = (RegisterVO) vo.m_preValueVo.getParentVO();
	if (headVo.getPrimaryKey() == null
			|| headVo.getPrimaryKey().trim().length() == 0) {
		isnew = true;
	}
	//####���ű����뺬�з���ֵ,����DLG��PNL������������з���ֵ####
	Object retObj=null;
	checkSave(headVo); // ǰ̨У����ĵ���̨����ʱУ�顣
	String key=new InvoiceBillService().saveBaseinfo(headVo);
	headVo.setPk_baseinfo(key);
	m_tmpVo.m_preValueVo.setParentVO(headVo);
	UFDate invoicedate = headVo.getInvoicedate();

	retObj = runClass("nc.bs.trade.comsave.BillSave", "saveBill",
			"nc.vo.pub.AggregatedValueObject:36GL", vo, m_keyHas,
			m_methodReturnHas);

	HYBillVO retBillVO = (HYBillVO)((ArrayList)retObj).get(1);
	String actioncode = null;
	if(isnew){
		actioncode = FbmActionConstant.SAVE;
	}else{
		actioncode = FbmActionConstant.EDITSAVE;
	}

	if(retBillVO!=null){
		RegisterVO newVo = (RegisterVO) retBillVO.getParentVO();
		newVo.setInvoicedate(invoicedate);
	}
//	ActionParamVO[] params = DefaultParamAdapter.changeInvoice2Param(retBillVO,actioncode);
//	FbmActionFactory.getInstance().createActionClass(FbmBusConstant.BILLTYPE_INVOICE, actioncode).doAction(params);

	BusiActionFactory.getInstance().createActionClass(FbmBusConstant.BILLTYPE_INVOICE, actioncode).doAction((RegisterVO)retBillVO.getParentVO(), actioncode,false);
	
	
	//���ڸ���״̬����˱������²�ѯһ��
	RegisterVO regvo = (RegisterVO)new HYPubBO().queryByPrimaryKey(RegisterVO.class,(String) ((ArrayList)retObj).get(0));
	retBillVO.setParentVO(regvo);
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

	private void checkSave(RegisterVO regvo) throws BusinessException {
		UFDate currentOperDate = new UFDate(
				Long.parseLong(InvocationInfoProxy.getInstance().getDate()));
		String receivebank = regvo.getReceivebankacc();
		String paybank = regvo.getPaybankacc();
		HYPubBO pubbo = new HYPubBO();
		BankaccbasVO bankvo[] = (BankaccbasVO[]) pubbo.queryByCondition(BankaccbasVO.class, "pk_bankaccbas = '"
				+ receivebank
				+ "' or pk_bankaccbas = '"
				+ paybank
				+ "'");
		if (bankvo != null && bankvo.length > 0) {
			for (int i = 0; i < bankvo.length; i++) {
				BankaccbasVO bankBasVO = bankvo[i];
				UFDate opendate = bankBasVO.getAccopendate();
				if (currentOperDate != null
						&& opendate != null
						&& currentOperDate.before(opendate)) {
					throw new BusinessException(
							nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("36201505", "UPP36201505-000008")/*
																													 * @res
																													 * "�տ������˻����߸��������˻�������������ҵ�����ڣ��������档"
																													 */
					);
				}
			}
		}
		
		String paybankacc = regvo.getPaybankacc();
		UFDate invoicedate = regvo.getInvoicedate();
		BankaccbasVO bankaccbasVO = (BankaccbasVO) pubbo.queryByPrimaryKey(BankaccbasVO.class, paybankacc);
		UFDate opendate = (UFDate) bankaccbasVO.getAccopendate();
		if (invoicedate != null
				&& opendate != null
				&& invoicedate.before(opendate)) {
			throw new BusinessException(
					nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcode112", "UPPFBMCODE112-000009")/*
																												 * @res
																												 * "��Ʊ���ڲ������ڸ��������˻��Ŀ�������"
																												 */);
		}
		String impawnmode = regvo.getImpawnmode();
		if (impawnmode != null && impawnmode.equals("BAIL")) {
			
		String securityacc = regvo.getSecurityaccount();
			BankaccbasVO bankaccVO = (BankaccbasVO) pubbo.queryByPrimaryKey(BankaccbasVO.class, securityacc);
			UFDate opendate_acc = (UFDate) bankaccVO.getAccopendate();
			if (invoicedate != null
					&& opendate_acc != null
					&& invoicedate.before(opendate_acc)) {
				throw new BusinessException(
						nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcode112", "UPPFBMCODE112-000010")/*
																													 * @res
																													 * "��Ʊ���ڲ������ڱ�֤���˻��Ŀ�������"
																													 */);
			}
		}
		
		
	}

}