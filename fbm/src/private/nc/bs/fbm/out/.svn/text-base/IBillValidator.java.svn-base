package nc.bs.fbm.out;

import nc.vo.fbm.outer.ArapBillParamVO;
import nc.vo.pub.BusinessException;

/**
 * 收付报校验票据接口
 * @author xwq
 *
 */
public interface IBillValidator {

	/**
	 * 检查票据基本信息
	 * @param whole
	 * @throws BusinessException
	 */
	public void checkBaseinfo(ArapBillParamVO param) throws BusinessException;

	/**
	 * 前置业务操作
	 * @param param
	 * @throws BusinessException
	 */
	public void beforeAction(ArapBillParamVO param) throws BusinessException;
	
	
	public void doCheck(ArapBillParamVO param) throws BusinessException;
	/**
	 * 后续业务操作
	 * @param param
	 * @param whole
	 * @throws BusinessException
	 */
	public void afterAction(ArapBillParamVO param) throws BusinessException;
}
