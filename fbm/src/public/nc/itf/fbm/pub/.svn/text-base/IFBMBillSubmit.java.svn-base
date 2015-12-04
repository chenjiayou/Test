package nc.itf.fbm.pub;

import nc.vo.pub.BusinessException;
import nc.vo.pub.lang.UFDate;
import nc.vo.trade.pub.HYBillVO;

/**
 * 资金票据单据提交接口
 * 
 * @author xwq
 *
 * 2008-7-26
 */
public interface IFBMBillSubmit {
	/**
	 * 提交单据
	 * @param billVO
	 * @param operatedate
	 * @param operator
	 * @throws BusinessException
	 */
	public void submit(HYBillVO billVO,UFDate operatedate,String operator) throws BusinessException;
	
	/**
	 * 取消提交单据
	 * @param billVO
	 * @param operatedate
	 * @param operator
	 * @throws BusinessException
	 */
	public void cancelSubmit(HYBillVO billVO,UFDate operatedate,String operator) throws BusinessException;
	
}
