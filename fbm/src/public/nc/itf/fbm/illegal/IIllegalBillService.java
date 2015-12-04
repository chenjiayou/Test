package nc.itf.fbm.illegal;

import nc.vo.fbm.illegal.IllegalVO;
import nc.vo.pub.BusinessException;

/**
 * 操作非法票据的服务层接口
 * @author wues
 */
public interface IIllegalBillService {
	/**
	 * 增加非法票据
	 * 在收票登记等操作时如果是废票
	 * 则调用此方法添加到非法票据当中
	 * @param vo
	 * @return
	 */
	public String addIllegalBill(IllegalVO vo) throws BusinessException; 
	
	/**
	 * 删除非法票据
	 */
	public void deleteIllegalBill(String fbmbillno) throws BusinessException ;
	
	/**
	 * 根据基本信息pk查出票据编号，以便找对应的非法票据
	 */
	public String queryFbmBillNoByPk_Source(String pk_baseinfo) throws BusinessException ;
	/**
	 * 根据编号校验此票据是否为非法票据
	 * @param vo
	 * @return
	 * @throws BusinessException
	 */
	public String validateBeforeInsert(IllegalVO vo) throws BusinessException;
	
}
