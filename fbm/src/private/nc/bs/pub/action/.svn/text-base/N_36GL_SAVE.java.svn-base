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
 * 备注：票据开票的保存
单据动作执行中的动态执行类的动态执行类。
 *
 * 创建日期：(2007-9-4)
 * @author 平台脚本生成
 */
public class N_36GL_SAVE extends AbstractCompiler2 {
private java.util.Hashtable m_methodReturnHas=new java.util.Hashtable();
private Hashtable m_keyHas=null;
/**
 * N_36GL_SAVE 构造子注解。
 */
public N_36GL_SAVE() {
	super();
}
/*
* 备注：平台编写规则类
* 接口执行类
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
	//####本脚本必须含有返回值,返回DLG和PNL的组件不允许有返回值####
	Object retObj=null;
	checkSave(headVo); // 前台校验类改到后台保存时校验。
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
	
	
	//由于更新状态，因此必须重新查询一次
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
																													 * "收款银行账户或者付款银行账户开户日期晚于业务日期，不允许保存。"
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
																												 * "出票日期不能早于付款银行账户的开户日期"
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
																													 * "出票日期不能早于保证金账户的开户日期"
																													 */);
			}
		}
		
		
	}

}