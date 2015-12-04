package nc.ui.fbm.consignbank;

import nc.ui.fbm.pub.AbstractUIChecker;
import nc.ui.fbm.pub.FBMManageUI;
import nc.ui.pub.ClientEnvironment;
import nc.vo.fbm.consignbank.CollectionVO;
import nc.vo.pub.lang.UFDate;

/**
 * <p>
 * 银行托收前台校验类
 * <p>
 * 创建人：bsrl <b>日期：2007-09
 * 
 */
public class ConsignBankUICheck extends AbstractUIChecker {

	public ConsignBankUICheck() {
	}

	public ConsignBankUICheck(FBMManageUI ui) {
		super(ui);
	}

	// public ICompareRule[] getHeadCompareRules() {
	// return new CompareRule[] {
	// new CompareRule("款项收妥日期",CollectionVO.DCOLLECTIONDATE,
	// ICompareRule.OPERATOR_NOTLESS,"票据到期日期",
	// CollectionVO.DQRQ,nc.vo.pf.pub.IDapType.UFDATE),
	// new CompareRule("委托日期",CollectionVO.DCONSIGNDATE,
	// ICompareRule.OPERATOR_MORE,"票据出票日期",
	// CollectionVO.CPRQ,nc.vo.pf.pub.IDapType.UFDATE),
	// new CompareRule("票据到期日期",CollectionVO.DQRQ,
	// ICompareRule.OPERATOR_MORE,"委托日期",
	// CollectionVO.DCONSIGNDATE,nc.vo.pf.pub.IDapType.UFDATE)};
	//
	// }

	private UFDate createUFdate(Object obj) {
		UFDate date = null;
		if (obj != null && obj.toString().length() > 0) {
			date = new UFDate((String) obj);
		}
		return date;
	}

	@Override
	public String check() {
		StringBuffer warn = new StringBuffer();
		UFDate collectiondate = createUFdate(getUI().getBillCardPanel().getHeadItem(CollectionVO.DCOLLECTIONDATE).getValueObject());
		UFDate consigndate = createUFdate(getUI().getBillCardPanel().getHeadItem(CollectionVO.DCONSIGNDATE).getValueObject());
		UFDate enddate = createUFdate(getUI().getBillCardPanel().getHeadItem(CollectionVO.DQRQ).getValueObject());
		UFDate invoicedate = createUFdate(getUI().getBillCardPanel().getHeadItem(CollectionVO.CPRQ).getValueObject());
		UFDate currentDate = ClientEnvironment.getInstance().getDate();

		if (collectiondate != null
				&& currentDate != null
				&& collectiondate.after(currentDate)) {
			warn.append(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add", "UPP3620ADD-000006")/*
																											 * @res
																											 * "款项收妥日期应小于等于当前登陆日期"
																											 */);
		}

		if (collectiondate != null
				&& enddate != null
				&& collectiondate.before(enddate)) {
			warn.append(nc.ui.ml.NCLangRes.getInstance().getStrByID("36201020", "UPP36201020-000009")/*
																									 * @res
																									 * "款项收妥日期不能早于票据到期日期"
																									 */
					+ "\n");
		}
		if (consigndate != null
				&& invoicedate != null
				&& invoicedate.after(consigndate)) {
			warn.append(nc.ui.ml.NCLangRes.getInstance().getStrByID("36201020", "UPP36201020-000010")/*
																									 * @res
																									 * "委托日期必须晚于或等于票据出票日期"
																									 */
					+ "\n");
		}
		if (enddate != null
				&& consigndate != null
				&& !enddate.after(consigndate)) {
			// warn.append(nc.ui.ml.NCLangRes.getInstance().getStrByID("36201020","UPP36201020-000011")/*
			// @res"票据到期日期必须晚于委托日期"*/+ "\n");
		}
		if (collectiondate != null
				&& consigndate != null
				&& consigndate.after(collectiondate)) {
			warn.append("托收办理单款项收妥日期应该大于等于委托日期");
		}
		return warn.toString();
	}

}