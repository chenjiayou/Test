package nc.itf.fbm.pub;

import nc.vo.pub.BusinessException;
import nc.vo.pub.lang.UFDate;
import nc.vo.trade.pub.HYBillVO;

/**
 * 资金组织内公司维护银行账接口
 * 分结算中心和结算单位传现金平台的单据都要实现此接口
 * @author xwq
 *
 * 2008-12-20
 */
public interface IFbm2CmpAccSettleOrg {
	/**
	 * 单位记银行帐
	 * @param billvo
	 * @param tallycorp
	 * @param tallyman
	 * @param tallydate
	 * @throws BusinessException
	 */
	public abstract void insertBankAcc4Unit(HYBillVO billvo,String tallycorp,String tallyman,UFDate tallydate) throws BusinessException;
		
	/**
	 * 中心记银行账(一般是票据账)
	 * @param billvo
	 * @param tallycorp
	 * @param tallyman
	 * @param tallydate
	 * @throws BusinessException
	 */
	public abstract void insertBankAcc4Center(HYBillVO billvo,String tallycorp,String tallyman,UFDate tallydate) throws BusinessException;
	
}
