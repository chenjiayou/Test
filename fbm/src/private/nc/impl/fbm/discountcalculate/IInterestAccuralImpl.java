package nc.impl.fbm.discountcalculate;

import java.util.HashMap;

import nc.bs.framework.common.NCLocator;
import nc.bs.logging.Logger;
import nc.bs.ml.NCLangResOnserver;
import nc.itf.cdm.util.CurrencyPublicUtil;
import nc.itf.fac.rateset.IRateSetAccess;
import nc.vo.cf.exception.InterestAccrualException;
import nc.vo.fac.ia.ArgsVO;
import nc.vo.fac.ia.InterestVO;
import nc.vo.fac.ia.PrincipalParamVO;
import nc.vo.fac.ia.enumeration.IAEnum;
import nc.vo.fac.ia.enumeration.IAMethod;
import nc.vo.fac.ia.util.InterestCalculator;
import nc.vo.fbm.proxy.OuterProxy;
import nc.vo.fbm.pub.constant.FBMParamConstant;
import nc.vo.fbm.register.RegisterVO;
import nc.vo.pub.BusinessException;
import nc.vo.pub.lang.UFDate;
import nc.vo.pub.lang.UFDouble;
import nc.vo.tm.intrate.DefaultIntRateItemVO;
import nc.vo.tm.intrate.DefaultIntRateVO;
import nc.vo.tm.intrate.IInterestRate;
import nc.vo.tm.intrate.IInterestRateItem;

/**
 * <p>
 * 贴现利率计息计算处理类
 * <p>
 * 创建日期：(2006-9-7)
 *
 * @author bsrl
 * @since v5.0
 */
public class IInterestAccuralImpl implements nc.itf.fbm.discountcalculate.IInterestAccural{

	public IInterestAccuralImpl()
	{
		super();
	}
	/**
	 * <p>
	 * 为一条票据主表vo构造PrincipalParamVO
	 * <p>
	 * 作者：bsrl <br>
	 * 日期：2006-9-7
	 *
	 * @param pjzbvo
	 * @return PrincipalParamVO
	 * @throws InterestAccrualException
	 */
	private PrincipalParamVO getPrincipalVo(RegisterVO registervo,HashMap<String, IInterestRate> rates) throws InterestAccrualException {
		IInterestRate rate = null;
		IInterestRate[] tempRate = null;
		UFDate beginDate = null;
		UFDate endDate = null;
		PrincipalParamVO ppvo = null;
		String intcode = "";

		if (registervo != null) {
			beginDate = registervo.getTxrq();
			endDate = registervo.getEnddate();
			if(endDate != null && !endDate.equals(beginDate))
				endDate = endDate.getDateBefore(1);  //贴现记息开始日期和结束日期不算头尾,按开区间算

			String intRatePk_id = registervo.getTxnllfa();
			UFDouble txnll = registervo.getTxnlv();
			Integer llts = registervo.getLlts();

			ppvo = new PrincipalParamVO();
			if (beginDate != null && endDate != null) {
				if (beginDate.compareTo(endDate) <= 0) { // 记息开始日期必须小于等于到期(结束)日期
					if (intRatePk_id == null) { // 如果利率方案为空,则按输入的年利率计算
						if (txnll != null) {
							intcode = "discount";
//							rate = new DefaultIntRateVO(txnll, llts.intValue(), intcode);
							rate = new DefaultIntRateVO(txnll, llts.intValue(),intcode);
//							((DefaultIntRateVO)rate).setCode(intcode);
//							((DefaultIntRateVO)rate).setDayOfYear(registervo.getLlts());
							ppvo.setUseIRate(DefaultIntRateItemVO.ATTR_NORMAL);
						}
					} else { // 若利率方案不为空则按利率方案计算利息
						try {
							// 如果已经前面已经有vo记息用了一样的利率方案,则直接从HashMap中获取,不查询数据库
							if (rates.containsKey(intRatePk_id)) {
								tempRate = (IInterestRate[])new DefaultIntRateVO[1];
								tempRate[0] = (IInterestRate) rates.get(intRatePk_id);
							}
							else {
								// 获取CDM的利率方案查找工具接口,通过利率方案的pk_id获取利率方案实体数组,贴现记息方法没有版本变化,返回长度为一
								IRateSetAccess iRateAcs = (IRateSetAccess) NCLocator.getInstance().lookup(IRateSetAccess.class.getName());
								tempRate = iRateAcs.queryRateset(intRatePk_id, beginDate, endDate);
							}
							if(tempRate != null && tempRate.length != 0) {
							     rate = tempRate[0];
							     IInterestRateItem[] rateitems = rate.getItems();
							     if (rateitems[0].getAttribute() == IInterestRateItem.ATTR_PERIOND || rateitems[0].getAttribute() == IInterestRateItem.ATTR_NORMAL)
								     ppvo.setUseIRate(IInterestRateItem.ATTR_NORMAL);
							     if (rateitems[0].getAttribute() == IInterestRateItem.ATTR_RATION)
								     ppvo.setUseIRate(IInterestRateItem.ATTR_DISCOUNT);
							}
						} catch (BusinessException e) {
							String errMsg = NCLangResOnserver.getInstance().getStrByID("cfcode", "UPPcfcode-000223")/* @res"查询利率设置时出现错误！" */;
							throw new InterestAccrualException(errMsg, e);
						}
					}
				}
			}

			if(registervo.getDiscountdelaydaynum() != null && registervo.getDiscountdelaydaynum().toString().trim().length() > 0) {
				ppvo.setBegDate(beginDate.getDateBefore(registervo.getDiscountdelaydaynum()));
			}else {
				ppvo.setBegDate(beginDate);
			}
			ppvo.setEndDate(endDate);
			ppvo.setMoney(registervo.getMoneyy());
			ppvo.setIntRate(rate);
			ppvo.setPeriodAttr(ArgsVO.PERIODATTR_NATURE);

			String iMethodType = null;
			try {
				iMethodType = OuterProxy.getSysInitQry().getParaString(registervo.getPk_corp(), FBMParamConstant.FBM001);
			} catch (BusinessException e) {
				Logger.error(e.getMessage(), e);
				throw new InterestAccrualException(e.getMessage(), e);
			}

			if(iMethodType.equals("按年利率计算"))
				ppvo.setIAMethod(IAMethod.USE_YEAR_RATE);
			else
				ppvo.setIAMethod(IAMethod.USE_DAY_RATE);
		}
        return ppvo;
    }


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
	public RegisterVO[] computetxjx(RegisterVO[] registervos) throws InterestAccrualException{
		PrincipalParamVO[] ppvos = new PrincipalParamVO[registervos.length];
		InterestVO[] intevos = null;
		InterestCalculator ical = new InterestCalculator();

		HashMap<String, IInterestRate> rates = new HashMap<String, IInterestRate>();

		for(int i = 0; i < registervos.length; i++) {
			ppvos[i] = getPrincipalVo(registervos[i],rates);

			DefaultIntRateVO rate = null;
			if (ppvos[i] != null)
				rate = (DefaultIntRateVO) ppvos[i].getIntRate();
			if (registervos[i] != null) {
				// 币种折算工具类,用来获取辅本币金额和原币的精度
				String pk_corp = registervos[i].getPk_corp();
				CurrencyPublicUtil curPubUtil = new CurrencyPublicUtil(pk_corp, registervos[i].getPk_curr());
				try {
					UFDouble[] yfbMoney = curPubUtil.getYfbMoney(registervos[i].getMoneyy(), registervos[i].getTxrq().toString());
					registervos[i].setMoneyf(yfbMoney[1]);
					registervos[i].setMoneyb(yfbMoney[2]);
				} catch (BusinessException e1) {
					Logger.error(e1, e1);
					throw new InterestAccrualException(e1.getMessage(), e1);
				}
				try {
					ppvos[i].setCurrencyDigit(curPubUtil.getYDecimalDigit()); // 设置显示原币金额和利息的精度
				} catch (BusinessException e) {
					Logger.error(e.getMessage(), e);
					throw new InterestAccrualException(e.getMessage(), e);
				}

				DefaultIntRateItemVO intRateitem = null;
				if (rate != null) {
					// 计算贴现利息
					intevos = ical.calculate(new PrincipalParamVO[] { ppvos[i] }, rate);

					intRateitem = (DefaultIntRateItemVO) intevos[0].getIntRateItem();

					// 把每个pjzbvo用到的利率方案记录到HashMap,重复利用, 减少查询数据库的次数

					if (registervos[i].getTxnllfa() != null && !rates.containsKey(registervos[i].getTxnllfa()))
						rates.put(registervos[i].getTxnllfa(), rate);

					// 计算结果赋给pjzbvo的贴现利息值和贴现净值,即抛去利息剩下的贴现金额
					if (registervos[i].getTxrq().equals(registervos[i].getEnddate())) {
						registervos[i].setTxlx(IAEnum.UFD0);
						registervos[i].setTxjz(registervos[i].getMoneyy());
					} else {
						registervos[i].setTxlx(intevos[0].getInterest());
						registervos[i].setTxjz(registervos[i].getMoneyy().sub(intevos[0].getInterest()));
					}

					// 用户选择利率方案自动时填充年,月,日利率的数值
					if (intRateitem != null) {
						registervos[i].setTxnlv(intRateitem.getIntRateY());
						registervos[i].setTxyll(intRateitem.getIntRateM());
						registervos[i].setTxrll(intRateitem.getIntRateD());
					}
					else {
//						 如果利率方案为空
						registervos[i].setTxjz(registervos[i].getMoneyy());
						registervos[i].setTxlx(IAEnum.UFD0);// 贴现利息为零
					}
				} else {
					// 如果利率方案为空
					registervos[i].setTxjz(registervos[i].getMoneyy());
					registervos[i].setTxlx(IAEnum.UFD0);// 贴现利息为零
				}
			}
		}

		return registervos;
	}

}