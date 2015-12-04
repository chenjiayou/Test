/**
 * 
 */
package nc.itf.fbm.discountcalculate;

import nc.vo.cf.exception.InterestAccrualException;
import nc.vo.fbm.register.RegisterVO;

/**
 * <p>
 * 贴现利率计息计算处理类接口
 * <p>
 * 创建日期：(2006-9-7)
 * 
 * @author bsrl
 * @since v5.0
 */
public interface IInterestAccural {
	
	/**
	 * <p>
	 * 计算多条PjzbVO的批量贴现利息,给PjzbVO[]把贴现金额填充好返回
	 * <p>
	 * 作者：bsrl <br>
	 * 日期：2006-9-7
	 * 
	 * @param pjzbvos
	 * @return
	 * @throws InterestAccrualException
	 */
	public RegisterVO[] computetxjx(RegisterVO[] registervos) throws InterestAccrualException;

}
