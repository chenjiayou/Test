package nc.bs.pub.action;

import java.util.Hashtable;

import nc.bs.fbm.pub.FbmCommonCheck;
import nc.bs.framework.common.InvocationInfoProxy;
import nc.bs.pub.compiler.AbstractCompiler2;
import nc.vo.fbm.endore.EndoreVO;
import nc.vo.pub.BusinessException;
import nc.vo.pub.compiler.PfParameterVO;
import nc.vo.uap.pf.PFBusinessException;
	/**
 * ��ע�����ı���ı༭ ���ݶ���ִ���еĶ�ִ̬����Ķ�ִ̬���ࡣ
 * 
 * �������ڣ�(2008-2-27)
 * 
 * @author ƽ̨�ű�����
 */
public class N_36GQ_EDIT extends AbstractCompiler2 {
	private java.util.Hashtable m_methodReturnHas = new java.util.Hashtable();
	private Hashtable m_keyHas = null;

	/**
	 * N_36GW_EDIT ������ע�⡣
	 */
	public N_36GQ_EDIT() {
		super();
	}

	/*
	 * ��ע��ƽ̨��д������ �ӿ�ִ����
	 */
	public Object runComClass(PfParameterVO vo) throws BusinessException {
		try {
			super.m_tmpVo = vo;
			
			// ǰ̨У��ת����̨��
			EndoreVO headVo = (EndoreVO) vo.m_preValueVo.getParentVO();
			checkEdit(headVo);
			Object retObj = null;
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
		return "	Object retObj=null;\n	return retObj;\n";
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

	private void checkEdit(EndoreVO endorevo) throws Exception {
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
