package nc.itf.fbm.accrule;

import nc.vo.fbm.accrule.AccRuleVO;
import nc.vo.pub.BusinessException;

/**
 * �ڲ��ʻ����˹������ýӿ�
 * @author xwq
 *
 * 2008-8-28
 */
public interface IAccRule {

	/**
	 * ����ʻ����շ�Χ
	 * @param billtype
	 * @param accname
	 * @return
	 * @throws BusinessException
	 */
	public AccRuleVO retriveAccRef(String billtype,String accname)throws BusinessException;
	
}
