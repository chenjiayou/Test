package nc.itf.fbm.arap;

import nc.vo.arap.outer.IArapGeneralObj;
import nc.vo.pub.BusinessException;
import nc.vo.pub.SuperVO;
import nc.vo.pub.lang.UFDate;

/**
 * ��ʽ�����ո�������
 * @author xwq
 *
 */
public interface IPushArapBill {
	/**
	 * �����ո������ݶ���
	 * @param vo
	 * @return
	 * @throws BusinessException
	 */
	public IArapGeneralObj buildArapObj(SuperVO vo , UFDate date) throws BusinessException;
}
