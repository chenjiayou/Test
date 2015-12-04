package nc.itf.fbm.impawn;

import nc.vo.pub.BusinessException;

/**
 * 
 * 功能：
       提供对外的接口,其他系统调用
 * 日期：2007-10-15
 * 程序员：wues
 */
public interface IImpawnService {
	/**
	 * 质押回收
	 * 参数：物权担保VO。
	 * 质押回收时需要用到回收人，回收日期，以及需要根据票据质押单PK回收质押单
	 * @throws BusinessException
	 */
	public void impawnBack(nc.vo.fi.impawn.ImpawnVO fiImpawnVO) throws BusinessException;
}
