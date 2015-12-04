package nc.bs.pub.action;

import java.util.Hashtable;

import nc.bs.fbm.accdetail.ReliefVOToAccDetail;
import nc.bs.fbm.pub.RegisterVO2BVO;
import nc.bs.fbm.pub.action.BusiActionFactory;
import nc.bs.fbm.relief.ReliefService;
import nc.bs.pub.compiler.AbstractCompiler2;
import nc.bs.trade.business.HYPubBO;
import nc.vo.fbm.pub.constant.FbmActionConstant;
import nc.vo.fbm.pub.constant.FbmBusConstant;
import nc.vo.fbm.relief.ReliefBVO;
import nc.vo.fbm.relief.ReliefVO;
import nc.vo.pub.AggregatedValueObject;
import nc.vo.pub.BusinessException;
import nc.vo.pub.compiler.PfParameterVO;
import nc.vo.trade.pub.HYBillVO;
import nc.vo.trade.pub.IBillStatus;
import nc.vo.uap.pf.PFBusinessException;

/**
 * ��ע��������������� ���ݶ���ִ���еĶ�ִ̬����Ķ�ִ̬���ࡣ
 *
 * �������ڣ�(2007-10-22)
 *
 * @author ƽ̨�ű�����
 */
public class N_36GR_UNAPPROVE extends AbstractCompiler2 {
	private java.util.Hashtable m_methodReturnHas = new java.util.Hashtable();
	private Hashtable m_keyHas = null;

	/**
	 * N_36GR_UNAPPROVE ������ע�⡣
	 */
	public N_36GR_UNAPPROVE() {
		super();
	}

	/*
	 * ��ע��ƽ̨��д������ �ӿ�ִ����
	 */
	public Object runComClass(PfParameterVO vo) throws BusinessException {
		try {
			super.m_tmpVo = vo;
			//��鵥��״̬�Ƿ����ͨ��
			int vbillstatus = (Integer)vo.m_preValueVo.getParentVO().getAttributeValue("vbillstatus");

			procUnApproveFlow(vo);
			Object retObj = runClass("nc.bs.trade.comstatus.BillUnApprove",
					"unApproveBill", "nc.vo.pub.AggregatedValueObject:36GR",
					vo, m_keyHas, m_methodReturnHas);
			if (retObj instanceof HYBillVO && vbillstatus == IBillStatus.CHECKPASS) {
				HYBillVO retBillVO = (HYBillVO) retObj;
				// ���²�ѯ�ӱ����ݣ���ֹ�޸�ʱû���ӱ�����
				HYPubBO bo = new HYPubBO();
				retBillVO = (HYBillVO) bo.queryBillVOByPrimaryKey(new String[] {
						HYBillVO.class.getName(), ReliefVO.class.getName(),
						ReliefBVO.class.getName() }, retBillVO.getParentVO()
						.getPrimaryKey());

				// ActionParamVO[] params =
				// DefaultParamAdapter.changeReliefParam(retBillVO,FbmActionConstant.CANCELAUDIT);
				// FbmActionFactory.getInstance().createActionClass(FbmBusConstant.BILLTYPE_RELIEF,FbmActionConstant.CANCELAUDIT).doAction(params);
				BusiActionFactory.getInstance().createActionClass(
						FbmBusConstant.BILLTYPE_RELIEF,
						FbmActionConstant.CANCELAUDIT).doAction(retBillVO,
						FbmActionConstant.CANCELAUDIT, false);

				// ������������ֶ�
				ReliefVO reliefVO = (ReliefVO) retBillVO.getParentVO();
				reliefVO.setDealdate(null);
				((HYBillVO) retBillVO).setParentVO(reliefVO);
				new HYPubBO().saveBill((HYBillVO) retBillVO);
				retObj = retBillVO;
				//NCdp200620425��20081209-���³�Ʊ��λΪԭ��Ʊ��λ
				new ReliefService().updateHoldUnitForUnApprove(retBillVO);
				// ά���ڲ��˻���
				ReliefVOToAccDetail reliefAccdetailSrv = new ReliefVOToAccDetail();
				reliefAccdetailSrv
						.delAccDetail((ReliefVO) ((AggregatedValueObject) retObj)
								.getParentVO());
			}
			if (retObj != null) {
				m_methodReturnHas.put("unApproveBill", retObj);
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
	public String getCodeRemark() {
		return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add","UPP3620ADD-000173")/*@res "	//####���ű����뺬�з���ֵ,����DLG��PNL������������з���ֵ####\n	Object retObj=null;\n	return retObj;\n"*/;
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