package nc.bs.fbm.pub.account;

import nc.vo.fbm.pub.BusiActionParamVO;
import nc.vo.pub.BusinessException;

public interface IBusiFbmAccount<T> {
	
	/**
	 * 
	 * ά���˻����
	 * xwq    2007-8-28
	 * @param	pk_action ��Ӧ������PK
	 * @param   param ��������VO
	 * @return	
	 * @throws	BusinessException
	 * @since	NC5.0
	 */
	public void  dealAccount(String pk_action,BusiActionParamVO<T> param) throws BusinessException;

}
