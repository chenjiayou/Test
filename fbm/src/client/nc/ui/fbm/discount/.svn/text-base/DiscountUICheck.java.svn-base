package nc.ui.fbm.discount;

import nc.ui.fbm.pub.AbstractUIChecker;
import nc.ui.fbm.pub.FBMManageUI;
import nc.ui.pub.ClientEnvironment;
import nc.vo.fbm.discount.DiscountVO;
import nc.vo.fbm.pub.constant.FbmBusConstant;
import nc.vo.pub.lang.UFDate;
import nc.vo.pub.lang.UFDouble;

/**
 *
 * <p>
 *	贴现办理前台校验类
 * <p>创建人：bsrl
 * <b>日期：2007-09
 *
 */
public class DiscountUICheck extends AbstractUIChecker {

	public DiscountUICheck() {
	}

	public DiscountUICheck(FBMManageUI ui) {
		super(ui);
	}

//	public ICompareRule[] getHeadCompareRules() {
//		return new CompareRule[] {
//			new CompareRule("实际贴现日期",DiscountVO.DDISCOUNTDATE, ICompareRule.OPERATOR_MORE,"票据出票日期", DiscountVO.CPRQ,nc.vo.pf.pub.IDapType.UFDATE),
//			new CompareRule("票据到期日期",DiscountVO.DQRQ, ICompareRule.OPERATOR_MORE,"实际贴现日期", DiscountVO.DDISCOUNTDATE,nc.vo.pf.pub.IDapType.UFDATE)};
//
//	}

	private UFDate createUFdate(Object obj) {
		UFDate date = null;
		if(obj != null && obj.toString().length() > 0) {
			date = new UFDate((String)obj);
		}
		return date;
	}

	private String checkNotNull() {
		StringBuffer warn = new StringBuffer();
		Object discountinterest = getUI().getBillCardPanel().getHeadItem(DiscountVO.DISCOUNTINTEREST).getValueObject();
		Object discountmoney = getUI().getBillCardPanel().getHeadItem(DiscountVO.MONEYY).getValueObject();
		if(discountinterest == null || discountinterest.toString().trim().length() == 0) {
			warn.append(nc.ui.ml.NCLangRes.getInstance().getStrByID("36201030","UPP36201030-000002")/* @res"贴现利息不能为空！"*/+ "\n");
		}
		if(discountmoney == null || discountmoney.toString().trim().length() == 0) {
			warn.append(nc.ui.ml.NCLangRes.getInstance().getStrByID("36201030","UPP36201030-000003")/* @res"贴现余额不能为空！"*/+ "\n");
		}
		return warn.toString();
	}


	@Override
	public String check() {
		StringBuffer warn = new StringBuffer();
		UFDate discountdate = createUFdate(getUI().getBillCardPanel().getHeadItem(DiscountVO.DDISCOUNTDATE).getValueObject());
		UFDate enddate = createUFdate(getUI().getBillCardPanel().getHeadItem(DiscountVO.DQRQ).getValueObject());
		UFDate invoicedate = createUFdate(getUI().getBillCardPanel().getHeadItem(DiscountVO.CPRQ).getValueObject());
		UFDate transactdate = createUFdate(getUI().getBillCardPanel().getHeadItem(DiscountVO.DTRANSACTDATE).getValueObject());

		String result = getUI().getBillCardPanel().getHeadItem(DiscountVO.RESULT).getValueObject().toString();

		UFDate currentDate = ClientEnvironment.getInstance().getDate();

		if (discountdate != null && currentDate != null
				&& discountdate.after(currentDate)) {
			warn.append(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add","UPP3620ADD-000010")/*@res " 实际贴现日期不能晚于当前登陆日期."*/);
		}


		if(discountdate != null && invoicedate != null && !discountdate.after(invoicedate)){
			warn.append(nc.ui.ml.NCLangRes.getInstance().getStrByID("36201030","UPP36201030-000004")/* @res"实际贴现日期必须晚于票据出票日期！"*/+ "\n");
		}
		if(enddate != null && discountdate != null && !enddate.after(discountdate)){
			warn.append(nc.ui.ml.NCLangRes.getInstance().getStrByID("36201030","UPP36201030-000005")/* @res"票据到期日期必须晚于实际贴现日期！"*/+ "\n");
		}
		if(transactdate != null && discountdate != null && transactdate.after(discountdate)){
			warn.append(nc.ui.ml.NCLangRes.getInstance().getStrByID("36201030","UPP36201030-000006")/* @res"办理日期不能晚于实际贴现日期！"*/+ "\n");
		}
		if(result.equalsIgnoreCase(FbmBusConstant.DISCOUNT_RESULT_SUCCESS)) {
			warn.append(checkNotNull());
			// 已转到后台进行校验
			// try {
			// String discount_account = (String)
			// getUI().getBillCardPanel().getHeadItem(DiscountVO.DISCOUNT_ACCOUNT).getValueObject();
			// if(discount_account!=null){
			// BankaccbasVO bankaccbasVO =
			// (BankaccbasVO)FBMProxy.getUifService().queryByPrimaryKey(BankaccbasVO.class,
			// discount_account);
			// UFDate opendate = (UFDate)bankaccbasVO.getAccopendate();
			// if(discountdate!=null && opendate!=null &&
			// discountdate.before(opendate)){
			// warn.append(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcode112","UPPFBMCODE112-000002")/*@res
			// "实现贴现日期不能早于开户日期"*/);
			// }
			// }
			// else {
			// return
			// nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcode112","UPPFBMCODE112-000003")/*@res
			// "贴现银行账号不能为空"*/;
			// }
			// } catch (UifException e) {
			// Logger.error(e.getMessage());
			// }
			//检查贴现利息，贴现余额和贴现余额必须大于等于0
			UFDouble discountcharge = new UFDouble (getUI().getBillCardPanel().getHeadItem(DiscountVO.DISCOUNTCHARGE).getValue());
			UFDouble discountinterest = new UFDouble (getUI().getBillCardPanel().getHeadItem(DiscountVO.DISCOUNTINTEREST).getValue());
			UFDouble moneyy = new UFDouble(getUI().getBillCardPanel().getHeadItem(DiscountVO.MONEYY).getValue());

			if(discountcharge !=null && discountcharge.doubleValue() <0){
				warn.append(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcode112","UPPFBMCODE112-000004")/*@res "贴现手续费不能小于0"*/);
			}
			if(discountinterest!=null && discountinterest.doubleValue()<0){
				warn.append(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcode112","UPPFBMCODE112-000005")/*@res "贴现利息不能小于0"*/);
			}
			if(moneyy !=null && moneyy.doubleValue() < 0){
				warn.append(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcode112","UPPFBMCODE112-000006")/*@res "贴现余额不能小于0"*/);
			}

		}
		return warn.toString();
	}

}