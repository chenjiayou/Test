package nc.itf.fbm.outer;

import nc.vo.fbm.outer.OuterVO;
import nc.vo.pub.BusinessException;

/**
 * �ⲿ��������ӿ�
 * @author xwq
 *
 */
public interface IOuterService {
	/**
	 * ����ҵ�񵥾�PK����ѯ�ո������PK����
	 * @param pk_busibill
	 * @return
	 * @throws BusinessException
	 */
	public OuterVO[] queryOuterVOSByBusiBill(String pk_busibill) throws BusinessException;
}
