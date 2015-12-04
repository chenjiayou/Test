package nc.bs.pub.action;

import java.util.Hashtable;

import nc.bs.fbm.pub.FbmCommonCheck;
import nc.bs.fbm.pub.action.BusiActionFactory;
import nc.bs.framework.common.InvocationInfoProxy;
import nc.bs.pub.compiler.AbstractCompiler2;
import nc.vo.fbm.endore.EndoreVO;
import nc.vo.fbm.pub.constant.FbmActionConstant;
import nc.vo.fbm.pub.constant.FbmBusConstant;
import nc.vo.pub.BusinessException;
import nc.vo.pub.CircularlyAccessibleValueObject;
import nc.vo.pub.compiler.PfParameterVO;
import nc.vo.trade.pub.HYBillVO;
import nc.vo.trade.pub.IBillStatus;
import nc.vo.uap.pf.PFBusinessException;
	/**
 * ��ע�����ı������� ���ݶ���ִ���еĶ�ִ̬����Ķ�ִ̬���ࡣ
 * 
 * �������ڣ�(2008-2-27)
 * 
 * @author ƽ̨�ű�����
 */
public class N_36GQ_APPROVE extends AbstractCompiler2 {
	private java.util.Hashtable m_methodReturnHas = new java.util.Hashtable();
	private Hashtable m_keyHas = null;

	/**
	 * N_36GW_APPROVE ������ע�⡣
	 */
	public N_36GQ_APPROVE() {
		super();
	}

	/*
	 * ��ע��ƽ̨��д������ �ӿ�ִ����
	 */
	public Object runComClass(PfParameterVO vo) throws BusinessException {
		try {
			super.m_tmpVo = vo;
			// ####���ű����뺬�з���ֵ,����DLG��PNL������������з���ֵ####
			
			EndoreVO checkvo = (EndoreVO) vo.m_preValueVo.getParentVO();
			checkAudit(checkvo); // ǰ̨���鵥У�飬����Ч�ʸĵ���̨����У��.
			
			procActionFlow(vo);
			Object retObj = runClass("nc.bs.trade.comstatus.BillApprove", "approveBill", "nc.vo.pub.AggregatedValueObject:36GQ", vo, m_keyHas, m_methodReturnHas);

			if (retObj instanceof HYBillVO) {
				CircularlyAccessibleValueObject parentVO = ((HYBillVO) retObj).getParentVO();
				Integer billstatus = (Integer) parentVO.getAttributeValue("vbillstatus");

				String actioncode = null;
				if (billstatus.intValue() == IBillStatus.CHECKPASS) {// ����������Ϊ���ͨ��
					actioncode = FbmActionConstant.AUDIT;
				} else {
					actioncode = FbmActionConstant.ONAUDIT;
				}
				EndoreVO endorevo = (EndoreVO) ((HYBillVO) retObj).getParentVO();
				BusiActionFactory.getInstance().createActionClass(FbmBusConstant.BILLTYPE_ENDORE, actioncode).doAction(endorevo, actioncode, false);
			}
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
		return "	Object retObj =null;\n	return retObj;\n";
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

	/**
	 * ���鵥���ʱ����У�顣
	 * 
	 * @param endorevo
	 * @throws Exception
	 */
	private void checkAudit(EndoreVO endorevo) throws Exception {
		String pk_corp = InvocationInfoProxy.getInstance().getCorpCode();
		String pk_endore = endorevo.getPrimaryKey();
		boolean arapFlag = FbmCommonCheck.isStartARAP(pk_corp);
		boolean paramFlag = "Y".equals(FbmCommonCheck.getParamValue(pk_corp));

		if (!arapFlag && paramFlag) {
			throw new BusinessException(
					nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add", "UPP3620ADD-000030")/*
																										 * @res
																										 * "��������Ƿ����ո������ݼ���Ӧ�ò���ֵ ��\n�������ո�����ʶ��һ�£�"
																										 */);
		}
		if (FbmCommonCheck.isCreatedByARAP(pk_endore)) {
			throw new BusinessException(
					nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add", "UPP3620ADD-000029")/*
																										 * @res
																										 * "�˼�¼���ո�����ʽ���ɣ�������ִ�д˲�����"
																										 */);
		}
		
	}
}
