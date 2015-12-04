package nc.bs.fbm.pub.account;

import nc.vo.fbm.pub.BusiActionParamVO;
import nc.vo.pub.BusinessException;

public interface IBusiFbmAccount<T> {
	
	/**
	 * 
	 * 维护账户变更
	 * xwq    2007-8-28
	 * @param	pk_action 对应动作表PK
	 * @param   param 动作参数VO
	 * @return	
	 * @throws	BusinessException
	 * @since	NC5.0
	 */
	public void  dealAccount(String pk_action,BusiActionParamVO<T> param) throws BusinessException;

}
