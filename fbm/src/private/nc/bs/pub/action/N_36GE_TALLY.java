package nc.bs.pub.action;

import java.util.Hashtable;

import nc.bs.fbm.plan.PlanFacade;
import nc.bs.fbm.pub.RegisterVO2BVO;
import nc.bs.framework.common.InvocationInfoProxy;
import nc.bs.pub.compiler.AbstractCompiler2;
import nc.bs.trade.business.HYPubBO;
import nc.impl.fbm.pub.FBMBillTallyImpl;
import nc.vo.fbm.storage.StorageBVO;
import nc.vo.fbm.storage.StorageVO;
import nc.vo.pub.BusinessException;
import nc.vo.pub.compiler.PfParameterVO;
import nc.vo.pub.lang.UFDate;
import nc.vo.trade.pub.HYBillVO;
import nc.vo.uap.pf.PFBusinessException;

/**
 * ��ע���ڲ����õļ��� ���ݶ���ִ���еĶ�ִ̬����Ķ�ִ̬���ࡣ
 * 
 * �������ڣ�(2008-8-2)
 * 
 * @author ƽ̨�ű�����
 */
public class N_36GE_TALLY extends AbstractCompiler2 {
	private java.util.Hashtable m_methodReturnHas = new java.util.Hashtable();
	private Hashtable m_keyHas = null;

	/**
	 * N_36GE_TALLY ������ע�⡣
	 */
	public N_36GE_TALLY() {
		super();
	}

	/*
	 * ��ע��ƽ̨��д������ �ӿ�ִ����
	 */
	public Object runComClass(PfParameterVO vo) throws BusinessException {
		try {
			super.m_tmpVo = vo;
			HYBillVO billVo = (HYBillVO) vo.m_preValueVo;
			StorageVO storageVo = (StorageVO) billVo.getParentVO();
			String corpPk = InvocationInfoProxy.getInstance().getCorpCode();
			String userId = storageVo.getTallyman();
			// String currdate = storageVo.getTallydate().toString();
			String tallydate = storageVo.getOutputdate().toString();

			// д�ʽ�ƻ�
			PlanFacade facade = new PlanFacade();
			facade.insertPlanExec(storageVo, storageVo.getKeepcorp());

			// new FBMBillTallyImpl().tally(billVo, UFDate.getDate(currdate),
			// userId, corpPk);

			new FBMBillTallyImpl().tally(billVo, UFDate.getDate(tallydate), userId, corpPk);

			String pk_storage = (String) storageVo.getAttributeValue(StorageVO.PK_STORAGE);
			HYPubBO bo = new HYPubBO();
			Object retObj = bo.queryBillVOByPrimaryKey(new String[] {
					HYBillVO.class.getName(), StorageVO.class.getName(),
					StorageBVO.class.getName() }, pk_storage);
			
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
		return "	Object retObj=null;\n	\n	return retObj;\n";
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