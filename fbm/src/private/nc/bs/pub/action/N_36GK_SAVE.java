package nc.bs.pub.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import nc.bs.fbm.pub.CommonDAO;
import nc.bs.fbm.pub.RegisterVO2BVO;
import nc.bs.pub.compiler.AbstractCompiler2;
import nc.bs.trade.business.HYPubBO;
import nc.bs.uap.lock.PKLock;
import nc.vo.fbm.account.AccountDetailVO;
import nc.vo.fbm.reckon.ReckonBVO;
import nc.vo.fbm.reckon.ReckonVO;
import nc.vo.fbm.register.RegisterVO;
import nc.vo.pub.BusinessException;
import nc.vo.pub.compiler.PfParameterVO;
import nc.vo.trade.pub.HYBillVO;
import nc.vo.uap.pf.PFBusinessException;

/**
 * ��ע���������㵥�ı��� ���ݶ���ִ���еĶ�ִ̬����Ķ�ִ̬���ࡣ
 *
 * �������ڣ�(2007-11-1)
 *
 * @author ƽ̨�ű�����
 */
public class N_36GK_SAVE extends AbstractCompiler2 {
	private java.util.Hashtable m_methodReturnHas = new java.util.Hashtable();
	private Hashtable m_keyHas = null;

	/**
	 * N_36GK_SAVE ������ע�⡣
	 */
	public N_36GK_SAVE() {
		super();
	}

	/*
	 * ��ע��ƽ̨��д������ �ӿ�ִ����
	 */
	public Object runComClass(PfParameterVO vo) throws BusinessException {
		CommonDAO dao = new CommonDAO();
		ReckonBVO[] reckonBVOs = (ReckonBVO[])vo.m_preValueVo.getChildrenVO();
		String[] pk_baseinfos = new String[reckonBVOs.length];
		for (int i = 0; i < reckonBVOs.length; i++) {
			pk_baseinfos[i] = reckonBVOs[i].getPk_baseinfo();
			// ���²�ѯ��ֹpk_baseinfoΪ�յ����
			if (pk_baseinfos[i] == null) {
				RegisterVO registerVO = (RegisterVO) dao.getBaseDAO()
						.retrieveByPK(RegisterVO.class,
								reckonBVOs[i].getPk_source());

				if (null == registerVO) {
					throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000229")/* @res"������Ʊ�Ǽǵ�����ȡ������Ʊ�Ǽǵ�Ϊ�գ���ˢ�����ԡ�"*/);
				}
				pk_baseinfos[i] = registerVO.getPk_baseinfo();
			}
		}

		try {
			super.m_tmpVo = vo;
			Object retObj = null;
			//������޸�ʱ,δ�ı��κ�����,��ִ��������.
			if(pk_baseinfos.length>0){
				lock(pk_baseinfos, vo.m_operator);
			}

			//����ж�,���Ϊ�޸ı�����ִ�д���֤.
			ReckonVO reckonvo = (ReckonVO)vo.m_preValueVo.getParentVO();
			String pk_value = reckonvo.getPk_reckon();
			if(pk_value == null||"".equals(pk_value)){
				validateUniqueBill(vo.m_preValueVo);
			}

			validateAccountDetail(vo.m_preValueVo);
			retObj = runClass("nc.bs.trade.comsave.BillSave", "saveBill",
					"nc.vo.pub.AggregatedValueObject:36GK", vo, m_keyHas,
					m_methodReturnHas);

			// ������ֵ����Я����ֵ���¸�ֵ
//			if (vo.m_preValueVo.getChildrenVO() != null
//					&& vo.m_preValueVo.getChildrenVO().length != 0) {// �õ����ֱ����ݲ�Ϊ0���
//				ResetRefValues.setReckonBodyRefValues(vo.m_preValueVo);
//			}

			HYBillVO retBillVO = (HYBillVO) ((ArrayList) retObj).get(1);

			if (retObj != null) {
				m_methodReturnHas.put("saveBill", retObj);
			}
			// ִ �й�ʽ ���������ƣ����Ч�����⣬����ǰ̨���̨����������
			RegisterVO2BVO.actionOPExecFormula((HYBillVO) ((ArrayList) retObj).get(1));
			return retObj;
		} catch (Exception ex) {
			if (ex instanceof BusinessException)
				throw (BusinessException) ex;
			else
				throw new PFBusinessException(ex.getMessage(), ex);
		} finally {
			//���pk_baseinfos��ֵ,��ִ�м������������.
			if(pk_baseinfos.length>0){
				unLock(pk_baseinfos, vo.m_operator);
			}
		}
	}

	/**
	 * У���±����Ʊ���������ֱ����Ƿ���ڣ���������׳��쳣
	 * @throws BusinessException
	 */
	private void validateUniqueBill(nc.vo.pub.AggregatedValueObject vo) throws BusinessException {
		ReckonBVO[] vos = getReckonVOS((ReckonBVO[])vo.getChildrenVO());
		if (vos != null && vos.length != 0 ) {
			throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add","UPP3620ADD-000176")/*@res "Ҫ����Ĳ���Ʊ���Ѿ�������������ٴ����㣬��ȷ�ϣ�"*/);
		}
	}

	private void validateAccountDetail(nc.vo.pub.AggregatedValueObject vo) throws BusinessException {
		String pks = getPKS((ReckonBVO[])vo.getChildrenVO());
		if (pks == null || "".equals(pks.trim())) {
			return ;
		}
		String condition = " pk_detail in (" + pks + ") and isliquid = 'N'";
		AccountDetailVO[] accountDetailVOs = (AccountDetailVO[])new HYPubBO().queryByCondition(AccountDetailVO.class, condition);
        // ���㵥������޸ı����¼���Ա���ԭ���ı����¼������ʱ��ʾ�����ּ�¼���Ǵ�����״̬ NCdp201016877
		// ������ӹ�ʽ�󣬴�������ɾ�к�BUFFer ����ʵ�ʵı������ݶ��ڱ�������ʾ����������
		// Ϊ�˴���buffer ����ظ����� ����������Ĵ���
		Map<String, String> childrenSize = new HashMap<String, String>();
		ReckonBVO bvo[] = (ReckonBVO[]) vo.getChildrenVO();
		for (int i = 0; i < bvo.length; i++) {
			childrenSize.put(bvo[i].getPk_source(), "");
		}
		if (accountDetailVOs.length != childrenSize.size()) {
        	throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add","UPP3620ADD-000177")/*@res "Ҫ����Ĳ���Ʊ�ݲ��Ǵ�����״̬����ȷ�ϣ�"*/);
        }
	}

	private ReckonBVO[] getReckonVOS(ReckonBVO[] vos) throws BusinessException {
		String pks = getPKS(vos);
		if (pks == null || "".equals(pks.trim())) {
			return null;
		}
		String condition = " pk_detail in (" + pks + ")";
		return (ReckonBVO[])new HYPubBO().queryByCondition(ReckonBVO.class, condition);
	}

	/**
	 * �������ֱ������voƴ�ӳ�in�ַ���
	 * @param vos
	 */
	private String getPKS(ReckonBVO[] vos) {
		StringBuffer pks = null;
		for (int i = 0; null != vos && i < vos.length; i++) {
			if (null == pks) {
				pks = new StringBuffer();
			}
			pks.append("'").append(vos[i].getPk_detail()).append("'");
			if (i != vos.length-1) {
				pks.append(",");
			}
		}
		return null == pks ? null : pks.toString();
	}

	private void lock(String[] pk_baseinfo, String userid) throws BusinessException {
		if(null == pk_baseinfo || pk_baseinfo.length == 0) {
			throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000230")/* @res"�޷�������ǰƱ�ݣ�ȡ�õ�Ʊ�ݻ�����ϢΪ�ա�"*/);
		}
		if(null == userid || "".equals(userid.trim())) {
			throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000231")/* @res"�޷�������ǰƱ�ݣ� ȡ�õĵ�ǰ�û�Ϊ�ա�"*/);
		}


		PKLock.getInstance().acquireBatchLock(pk_baseinfo, userid, "");
	}

	private void unLock(String[] pk_baseinfo, String userid) throws BusinessException {
		if(null == pk_baseinfo || pk_baseinfo.length == 0) {
			throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000232")/* @res"�޷������ǰƱ�ݵ�������ȡ�õ�Ʊ�ݻ�����ϢΪ�ա�"*/);
		}
		if(null == userid || "".equals(userid.trim())) {
			throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcomm","UPPFBMComm-000233")/* @res"�޷������ǰƱ�ݵ�������ȡ�õĵ�ǰ�û�Ϊ�ա�"*/);
		}
		PKLock.getInstance().releaseBatchLock(pk_baseinfo, userid, "");
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