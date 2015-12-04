package nc.ui.fbm.discount.listener;

import nc.bs.framework.common.NCLocator;
import nc.bs.logging.Logger;
import nc.itf.fbm.discountcalculate.IInterestAccural;
import nc.ui.pub.ClientEnvironment;
import nc.ui.tm.framework.util.ClientInfo;
import nc.vo.cf.exception.InterestAccrualException;
import nc.vo.fbm.proxy.OuterProxy;
import nc.vo.fbm.register.RegisterVO;
import nc.vo.pub.lang.UFDate;
import nc.vo.pub.lang.UFDouble;

/**
 * 
 * <p>
 *	贴现界面计算贴现利息
 * <p>创建人：bsrl
 * <b>日期：2007-12-17
 *
 */
public class DiscountCalculate {
	
	public static UFDouble calculateDiscountInterest(UFDouble hpje, UFDate txrq, UFDate enddate,String currtype, int delayday, UFDouble ratenvalue, int ratedaynum, String pk_corp) {
		RegisterVO[] vos = new RegisterVO[1];
		vos[0] = new RegisterVO();
		vos[0].setMoneyy(hpje);
		vos[0].setTxrq(txrq);
		vos[0].setEnddate(enddate);
		vos[0].setDiscountdelaydaynum(delayday);
		vos[0].setTxnlv(ratenvalue);
		vos[0].setLlts(ratedaynum);
		vos[0].setPk_curr(currtype);
		vos[0].setPk_corp(pk_corp);
		IInterestAccural IInterAccu = (IInterestAccural)NCLocator.getInstance().lookup(IInterestAccural.class.getName());
		RegisterVO[] ret = null;
		try {
			ret = IInterAccu.computetxjx(vos);
		} catch (InterestAccrualException e) {
			Logger.error(e.getMessage(),e);
		}
		return ret[0].getTxlx();
	}
}
