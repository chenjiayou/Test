package nc.bs.pub.action;

import java.util.Hashtable;

import nc.bs.fbm.relief.ReliefHelper;
import nc.bs.pub.compiler.AbstractCompiler2;
import nc.vo.pub.BusinessException;
import nc.vo.pub.compiler.PfParameterVO;
import nc.vo.trade.pub.HYBillVO;
import nc.vo.uap.pf.PFBusinessException;

/**
 * ��ע����������ĳ��� ���ݶ���ִ���еĶ�ִ̬����Ķ�ִ̬���ࡣ
 *
 * Ʊ�ݵ����ĳ�����Ϊһ����ݷ�ʽ�������ת���ڲ����õ����棬�������
 *
 * �������ڣ�(2007-10-22)
 *
 * @author ƽ̨�ű�����
 */
public class N_36GR_OUTPUT extends AbstractCompiler2 {
	private java.util.Hashtable m_methodReturnHas = new java.util.Hashtable();
	private Hashtable m_keyHas = null;

	/**
	 * N_36GR_OUTPUT ������ע�⡣
	 */
	public N_36GR_OUTPUT() {
		super();
	}

	/*
	 * ��ע��ƽ̨��д������ �ӿ�ִ����
	 */
	public Object runComClass(PfParameterVO vo) throws BusinessException {
		try {
			super.m_tmpVo = vo;
			ReliefHelper srv = new ReliefHelper();

			// ����Reilef�����ɳ����˺ͳ�������
			HYBillVO retBillVO = srv
					.updateReliefOut((HYBillVO) vo.m_preValueVo,
							vo.m_currentDate, vo.m_operator);

//			ActionParamVO[] params = DefaultParamAdapter.changeReliefParam(
//					retBillVO, FbmActionConstant.OUTPUT_SUCCESS);
			// ���⴦�������ڲ����õ�,
			//srv.makeStorageInnerBack(params);

			return retBillVO;
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