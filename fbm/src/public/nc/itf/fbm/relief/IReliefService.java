package nc.itf.fbm.relief;

import nc.vo.fbm.register.RegisterVO;
import nc.vo.fbm.relief.ReliefVO;
import nc.vo.pub.BusinessException;
import nc.vo.pub.lang.UFDate;

/**
 * 类功能说明：
     票据调剂接口
 * 日期：2007-12-1
 * 程序员： wues
 */
public interface IReliefService {
	/**
	 * 根据pk_relief取到所有未曾出库的调剂票据返回
	 * 自己的票：返回调剂票据的RegisterVO
	 * 他人的票：返回新生成的收票登记单VO
	 * @param pk_relief
	 * @return
	 * @throws BusinessException
	 */
	public RegisterVO[] getRegisterVOByRelief(String pk_relief) throws BusinessException;
	
	/**
	 * 自动内部领用
	 * @param vo
	 * @throws BusinessException
	 */
	public String autoInnerBack(ReliefVO vo,UFDate outputDate,String tallyman,UFDate tallydate) throws BusinessException;
	
}
