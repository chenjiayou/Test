package nc.itf.fbm.pub;

import nc.vo.pub.BusinessException;
import nc.vo.pub.lang.UFDate;
import nc.vo.trade.pub.HYBillVO;

/**
 * �ʽ���֯�ڹ�˾ά�������˽ӿ�
 * �ֽ������ĺͽ��㵥λ���ֽ�ƽ̨�ĵ��ݶ�Ҫʵ�ִ˽ӿ�
 * @author xwq
 *
 * 2008-12-20
 */
public interface IFbm2CmpAccSettleOrg {
	/**
	 * ��λ��������
	 * @param billvo
	 * @param tallycorp
	 * @param tallyman
	 * @param tallydate
	 * @throws BusinessException
	 */
	public abstract void insertBankAcc4Unit(HYBillVO billvo,String tallycorp,String tallyman,UFDate tallydate) throws BusinessException;
		
	/**
	 * ���ļ�������(һ����Ʊ����)
	 * @param billvo
	 * @param tallycorp
	 * @param tallyman
	 * @param tallydate
	 * @throws BusinessException
	 */
	public abstract void insertBankAcc4Center(HYBillVO billvo,String tallycorp,String tallyman,UFDate tallydate) throws BusinessException;
	
}
