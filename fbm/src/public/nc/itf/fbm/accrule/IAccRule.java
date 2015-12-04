package nc.itf.fbm.accrule;

import nc.vo.fbm.accrule.AccRuleVO;
import nc.vo.pub.BusinessException;

/**
 * 内部帐户入账规则设置接口
 * @author xwq
 *
 * 2008-8-28
 */
public interface IAccRule {

	/**
	 * 获得帐户参照范围
	 * @param billtype
	 * @param accname
	 * @return
	 * @throws BusinessException
	 */
	public AccRuleVO retriveAccRef(String billtype,String accname)throws BusinessException;
	
}
