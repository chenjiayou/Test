package nc.bs.fbm.out;

import nc.vo.fbm.outer.ArapBillParamVO;
import nc.vo.pub.BusinessException;

/**
 * �ո���У��Ʊ�ݽӿ�
 * @author xwq
 *
 */
public interface IBillValidator {

	/**
	 * ���Ʊ�ݻ�����Ϣ
	 * @param whole
	 * @throws BusinessException
	 */
	public void checkBaseinfo(ArapBillParamVO param) throws BusinessException;

	/**
	 * ǰ��ҵ�����
	 * @param param
	 * @throws BusinessException
	 */
	public void beforeAction(ArapBillParamVO param) throws BusinessException;
	
	
	public void doCheck(ArapBillParamVO param) throws BusinessException;
	/**
	 * ����ҵ�����
	 * @param param
	 * @param whole
	 * @throws BusinessException
	 */
	public void afterAction(ArapBillParamVO param) throws BusinessException;
}
