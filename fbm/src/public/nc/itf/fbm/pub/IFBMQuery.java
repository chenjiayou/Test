package nc.itf.fbm.pub;

import nc.vo.pub.BusinessException;
import nc.vo.pub.CircularlyAccessibleValueObject;
import nc.vo.pub.SuperVO;

public interface IFBMQuery {

	/**
	 * ��ѯ����ӿڣ���ʵ�������ù�˾��ѯ������Ҫ��ʾ�����ƣ�ʡȥ�� ���յ������������Ч�ʡ�
	 * 
	 * @param c
	 * @param wherePart
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public CircularlyAccessibleValueObject[] queryBodyData(Class c,
			String wherePart) throws BusinessException;

	/**
	 * ǰ̨���� Listener ����Э��ʱ����̨�������࣬����ͨ��pks��ѯ�����VOһ�η������б����С�
	 * 
	 * @param c
	 * @param pks
	 * @return
	 * @throws BusinessException
	 */
	public CircularlyAccessibleValueObject[] queryBodyVOs(SuperVO supervo,
			String[] pks)
			throws BusinessException;
}
