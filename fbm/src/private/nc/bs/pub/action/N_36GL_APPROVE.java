package nc.bs.pub.action;

import java.util.ArrayList;
import java.util.Hashtable;

import nc.bs.fbm.invoice.InvoiceBillService;
import nc.bs.fbm.invoice.InvoiceUtil;
import nc.bs.fbm.plan.PlanFacade;
import nc.bs.fbm.pub.action.BusiActionFactory;
import nc.bs.framework.common.InvocationInfoProxy;
import nc.bs.pub.compiler.AbstractCompiler2;
import nc.bs.trade.business.HYPubBO;
import nc.vo.fbm.pub.constant.FbmActionConstant;
import nc.vo.fbm.pub.constant.FbmBusConstant;
import nc.vo.fbm.register.RegisterVO;
import nc.vo.pub.BusinessException;
import nc.vo.pub.CircularlyAccessibleValueObject;
import nc.vo.pub.compiler.PfParameterVO;
import nc.vo.pub.lang.UFBoolean;
import nc.vo.pub.lang.UFDate;
import nc.vo.trade.pub.HYBillVO;
import nc.vo.trade.pub.IBillStatus;
import nc.vo.uap.pf.PFBusinessException;

/**
 * ��ע��Ʊ�ݿ�Ʊ����� ���ݶ���ִ���еĶ�ִ̬����Ķ�ִ̬���ࡣ
 * 
 * �������ڣ�(2007-9-4)
 * 
 * @author ƽ̨�ű�����
 */
public class N_36GL_APPROVE extends AbstractCompiler2 {
	private java.util.Hashtable m_methodReturnHas = new java.util.Hashtable();
	private Hashtable m_keyHas = null;

	/**
	 * N_36GL_APPROVE ������ע�⡣
	 */
	public N_36GL_APPROVE() {
		super();
	}

	/*
	 * ��ע��ƽ̨��д������ �ӿ�ִ����
	 */
	public Object runComClass(PfParameterVO vo) throws BusinessException {
		try {
			super.m_tmpVo = vo;
			// ####���ű����뺬�з���ֵ,����DLG��PNL������������з���ֵ####
			String ccReturnMsg = null;
			RegisterVO registerVo = null;
			UFBoolean writebankacc = null;
			if (vo.m_preValueVo != null
					&& vo.m_preValueVo.getParentVO() != null) {
				registerVo = (RegisterVO) vo.m_preValueVo.getParentVO().clone();
			}
			if (vo.m_preValueVo.getParentVO() != null) {
				writebankacc = ((RegisterVO) vo.m_preValueVo.getParentVO()).getWritebankacc();
			}
			procActionFlow(vo);

			Object retObj = runClass("nc.bs.trade.comstatus.BillApprove", "approveBill", "nc.vo.pub.AggregatedValueObject:36GL", vo, m_keyHas, m_methodReturnHas);

			String actioncode = null;
			if (retObj instanceof HYBillVO) {
				CircularlyAccessibleValueObject parentVO = ((HYBillVO) retObj).getParentVO();
				Integer billstatus = (Integer) parentVO.getAttributeValue("vbillstatus");

				if (billstatus.intValue() == IBillStatus.CHECKPASS) {// ����������Ϊ���ͨ��
					actioncode = FbmActionConstant.AUDIT;
				} else {
					actioncode = FbmActionConstant.ONAUDIT;
				}
				((RegisterVO) parentVO).setInvoicedate(registerVo.getInvoicedate());
				// ActionParamVO[] params =
				// DefaultParamAdapter.changeInvoice2Param((HYBillVO)retObj,actioncode);
				// FbmActionFactory.getInstance().createActionClass(FbmBusConstant.BILLTYPE_INVOICE,
				// actioncode).doAction(params);
				BusiActionFactory.getInstance().createActionClass(FbmBusConstant.BILLTYPE_INVOICE, actioncode).doAction((RegisterVO) parentVO, actioncode, false);
			}

			if (actioncode != null
					&& actioncode.equals(FbmActionConstant.AUDIT)) {
				if (registerVo != null) {
					ccReturnMsg = new InvoiceBillService().applyRationBeforeGLApprove(registerVo);
				}

				// if(!registerVo.getSfflag().booleanValue()){//����Ѹ�Ʊ���򹴣���д�����˺ͼƻ�
				// ά���ʽ�ƻ�
				PlanFacade facade = new PlanFacade();
				facade.insertPlanExec(registerVo, registerVo.getPk_corp(), "AUDIT");

				// try
				// {
				// д�����˻���.
				RegisterVO tmpvo = (RegisterVO) ((HYBillVO) retObj).getParentVO();
				tmpvo.setWritebankacc(writebankacc);
				((HYBillVO) retObj).setParentVO(tmpvo);
				InvoiceUtil cbs = new InvoiceUtil();
				String loginCorp = InvocationInfoProxy.getInstance().getCorpCode();
				cbs.addCMPBank((HYBillVO) retObj, loginCorp, vo.m_operator, new UFDate(
						vo.m_currentDate));
				cbs.addCMPBill((HYBillVO) retObj, loginCorp, vo.m_operator, new UFDate(
						vo.m_currentDate));
				// } catch (Exception e) {
				// throw new BusinessException(e.getMessage());
				// }
				// }
			}
			
			//���ڸ���״̬����˱������²�ѯһ��
			RegisterVO regvo = (RegisterVO)new HYPubBO().queryByPrimaryKey(RegisterVO.class,registerVo.getPrimaryKey());
			((HYBillVO) retObj).setParentVO(regvo);
			
			retObj = new Object[] { ccReturnMsg, retObj };

			if (retObj != null) {
				m_methodReturnHas.put("approveBill", retObj);
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
	public String getCodeRemark() {
		return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add", "UPP3620ADD-000173")/*
																									 * @res
																									 * "	//####���ű����뺬�з���ֵ,����DLG��PNL������������з���ֵ####\n	Object retObj=null;\n	return retObj;\n"
																									 */;
	}

	/*
	 * ��ע�����ýű�������HAS
	 */
	private void setParameter(String key, Object val) {
		if (m_keyHas == null) {
			m_keyHas = new Hashtable();
		}
		if (val != null) {
			m_keyHas.put(key, val);
		}
	}
}