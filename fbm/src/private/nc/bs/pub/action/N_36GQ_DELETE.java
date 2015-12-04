package nc.bs.pub.action;

import nc.vo.fbm.endore.EndoreVO;
import nc.vo.fbm.pub.constant.FbmBusConstant;
import nc.vo.pub.BusinessException;
import nc.vo.pub.compiler.PfParameterVO;
import nc.vo.uap.pf.PFBusinessException;

/**
 * ��ע�����ı����ɾ�� ���ݶ���ִ���еĶ�ִ̬����Ķ�ִ̬���ࡣ
 * 
 * �������ڣ�(2008-2-27)
 * 
 * @author ƽ̨�ű�����
 */
public class N_36GQ_DELETE extends AbstractCenterOperation {
	/**
	 * N_36GW_DELETE ������ע�⡣
	 */
	public N_36GQ_DELETE() {
		super();
	}

	/*
	 * ��ע��ƽ̨��д������ �ӿ�ִ����
	 */
	public Object runComClass(PfParameterVO vo) throws BusinessException {
		try {
			super.m_tmpVo = vo;

			EndoreVO checkvo = (EndoreVO) vo.m_preValueVo.getParentVO();
			// checkDelete(checkvo); // ǰ̨���鵥У�飬����Ч�ʸĵ���̨����У��.
			
			EndoreVO headVo = (EndoreVO) vo.m_preValueVo.getParentVO();
			// �ж���ͳ��ҵ���������Լ���ҵ�񣺸���Ʊ�����ͽ����ж�
			if (headVo.getOpbilltype().equals(FbmBusConstant.BILL_PRIVACY)) {// ˽�˵�ҵ��
				return delPrivacy(vo);
			} else {// ͳ��ҵ��
				return delUniStorage(vo);
			}
		} catch (Exception ex) {
			if (ex instanceof BusinessException)
				throw (BusinessException) ex;
			else
				throw new PFBusinessException(ex.getMessage(), ex);
		}
	}
	
}
