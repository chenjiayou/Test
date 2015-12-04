package nc.itf.fbm.pub;

import nc.vo.pub.BusinessException;
import nc.vo.pub.lang.UFDate;
import nc.vo.trade.pub.HYBillVO;



/**
 * ���˽ӿ�
 * @author xwq
 *
 * 2008-7-29
 */
public interface IFBMBillTally {
	
	/**
	 * ����
	 * @param billvo
	 * @param operatedate
	 * @param operator
	 * @param pk_corp
	 * @throws BusinessException
	 */
	public void tally(HYBillVO billvo,UFDate operatedate,String operator,String pk_corp) throws BusinessException;
	
	/**
	 * ȡ������
	 * @param billvo
	 * @param operatedate
	 * @param operator
	 * @param pk_corp
	 * @throws BusinessException
	 */
	public void cancelTally(HYBillVO billvo,UFDate operatedate,String operator,String pk_corp) throws BusinessException;
	
}
