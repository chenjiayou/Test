package nc.ui.fbm.discountapply;

import nc.ui.fbm.pub.AbstractUIChecker;
import nc.ui.fbm.pub.FBMManageUI;
import nc.vo.fbm.discount.DiscountVO;
import nc.vo.pub.lang.UFDate;

/**
 *
 * <p>
 *	贴现申请界面前台UI校验类
 * <p>创建人：bsrl
 * <b>日期：2007-09
 *
 */
public class DiscountApplyUICheck extends AbstractUIChecker {

	public DiscountApplyUICheck() {
	}

	public DiscountApplyUICheck(FBMManageUI ui) {
		super(ui);
	}
//	public ICompareRule[] getHeadCompareRules() {
//		return new CompareRule[] {
//				new CompareRule("申请日期",DiscountVO.APPLY_DATE, ICompareRule.OPERATOR_MORE,"票据出票日期", DiscountVO.CPRQ,nc.vo.pf.pub.IDapType.UFDATE),
//				new CompareRule("票据到期日期",DiscountVO.DQRQ, ICompareRule.OPERATOR_MORE,"申请日期", DiscountVO.APPLY_DATE,nc.vo.pf.pub.IDapType.UFDATE),
//				new CompareRule("贴现日期",DiscountVO.DDISCOUNTDATE, ICompareRule.OPERATOR_NOTLESS,"申请日期", DiscountVO.APPLY_DATE,nc.vo.pf.pub.IDapType.UFDATE),
//				new CompareRule("贴现日期",DiscountVO.DDISCOUNTDATE, ICompareRule.OPERATOR_MORE,"票据出票日期", DiscountVO.CPRQ,nc.vo.pf.pub.IDapType.UFDATE),
//				new CompareRule("票据到期日期",DiscountVO.DQRQ, ICompareRule.OPERATOR_MORE,"贴现日期", DiscountVO.DDISCOUNTDATE,nc.vo.pf.pub.IDapType.UFDATE)};
//
//	}

	private UFDate createUFdate(Object obj) {
		UFDate date = null;
		if(obj != null && obj.toString().length() > 0) {
			date = new UFDate((String)obj);
		}
		return date;
	}

	@Override
	public String check() {
		StringBuffer warn = new StringBuffer();
		UFDate applydate = createUFdate(getUI().getBillCardPanel().getHeadItem(DiscountVO.APPLY_DATE).getValueObject());
		UFDate discountdate = createUFdate(getUI().getBillCardPanel().getHeadItem(DiscountVO.DDISCOUNTDATE).getValueObject());
		UFDate enddate = createUFdate(getUI().getBillCardPanel().getHeadItem(DiscountVO.DQRQ).getValueObject());
		UFDate invoicedate = createUFdate(getUI().getBillCardPanel().getHeadItem(DiscountVO.CPRQ).getValueObject());
		if(applydate != null && invoicedate != null && applydate.before(invoicedate)){
			warn.append(nc.ui.ml.NCLangRes.getInstance().getStrByID("36201025","UPP36201025-000004")/* @res"申请日期不能早于票据出票日期"*/+ "\n");
		}
		if(enddate != null && applydate != null && !enddate.after(applydate)){
			warn.append(nc.ui.ml.NCLangRes.getInstance().getStrByID("36201025","UPP36201025-000005")/* @res"票据到期日期必须晚于申请日期"*/+ "\n");
		}
		if(discountdate != null && applydate != null && discountdate.before(applydate)){
			warn.append(nc.ui.ml.NCLangRes.getInstance().getStrByID("36201025","UPP36201025-000006")/* @res"贴现日期不能早于申请日期"*/+ "\n");
		}
		if(discountdate != null && invoicedate != null && !discountdate.after(invoicedate)){
			warn.append(nc.ui.ml.NCLangRes.getInstance().getStrByID("36201025","UPP36201025-000007")/* @res"贴现日期必须晚于票据出票日期"*/+ "\n");
		}
		if(enddate != null && discountdate != null && !enddate.after(discountdate)){
			warn.append(nc.ui.ml.NCLangRes.getInstance().getStrByID("36201025","UPP36201025-000008")/* @res"票据到期日期必须晚于贴现日期"*/+ "\n");
		}
		return warn.toString();
	}
}