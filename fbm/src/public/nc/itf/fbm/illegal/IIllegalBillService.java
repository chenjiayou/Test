package nc.itf.fbm.illegal;

import nc.vo.fbm.illegal.IllegalVO;
import nc.vo.pub.BusinessException;

/**
 * �����Ƿ�Ʊ�ݵķ����ӿ�
 * @author wues
 */
public interface IIllegalBillService {
	/**
	 * ���ӷǷ�Ʊ��
	 * ����Ʊ�ǼǵȲ���ʱ����Ƿ�Ʊ
	 * ����ô˷�����ӵ��Ƿ�Ʊ�ݵ���
	 * @param vo
	 * @return
	 */
	public String addIllegalBill(IllegalVO vo) throws BusinessException; 
	
	/**
	 * ɾ���Ƿ�Ʊ��
	 */
	public void deleteIllegalBill(String fbmbillno) throws BusinessException ;
	
	/**
	 * ���ݻ�����Ϣpk���Ʊ�ݱ�ţ��Ա��Ҷ�Ӧ�ķǷ�Ʊ��
	 */
	public String queryFbmBillNoByPk_Source(String pk_baseinfo) throws BusinessException ;
	/**
	 * ���ݱ��У���Ʊ���Ƿ�Ϊ�Ƿ�Ʊ��
	 * @param vo
	 * @return
	 * @throws BusinessException
	 */
	public String validateBeforeInsert(IllegalVO vo) throws BusinessException;
	
}
