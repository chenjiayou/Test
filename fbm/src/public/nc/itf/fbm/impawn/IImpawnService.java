package nc.itf.fbm.impawn;

import nc.vo.pub.BusinessException;

/**
 * 
 * ���ܣ�
       �ṩ����Ľӿ�,����ϵͳ����
 * ���ڣ�2007-10-15
 * ����Ա��wues
 */
public interface IImpawnService {
	/**
	 * ��Ѻ����
	 * ��������Ȩ����VO��
	 * ��Ѻ����ʱ��Ҫ�õ������ˣ��������ڣ��Լ���Ҫ����Ʊ����Ѻ��PK������Ѻ��
	 * @throws BusinessException
	 */
	public void impawnBack(nc.vo.fi.impawn.ImpawnVO fiImpawnVO) throws BusinessException;
}
