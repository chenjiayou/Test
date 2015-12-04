/**
 *
 */
package nc.ui.fbm.gather.checker;

import nc.ui.fbm.pub.AbstractUIChecker;
import nc.ui.fbm.pub.FBMManageUI;
import nc.ui.pub.ClientEnvironment;
import nc.vo.fbm.register.RegisterVO;
import nc.vo.pub.lang.UFDate;

/**
 * <p>
 * 收票登记界面日期校验
 * <p>
 * 创建人：lpf <b>日期：2007-8-16
 * 
 */
public class GatherdateChecker extends AbstractUIChecker {

	/**
	 *
	 */
	public GatherdateChecker() {
		// TODO Auto-generated constructor stub
	}

	public GatherdateChecker(FBMManageUI ui) {
		super(ui);
		// TODO Auto-generated constructor stub
	}

	public String check() {
		UFDate enddate = new UFDate(
				(String) getUI().getBillCardPanel().getHeadItem(RegisterVO.ENDDATE).getValueObject());
		UFDate invoicedate = new UFDate(
				(String) getUI().getBillCardPanel().getHeadItem(RegisterVO.INVOICEDATE).getValueObject());
		UFDate gatherdate = new UFDate(
				(String) getUI().getBillCardPanel().getHeadItem(RegisterVO.GATHERDATE).getValueObject());
		UFDate currentdate = ClientEnvironment.getInstance().getDate();
		// if (gatherdate.before(invoicedate) || gatherdate.after(enddate)) {
		// return nc.ui.ml.NCLangRes.getInstance().getStrByID("36201005",
		// "UPP36201005-000005")/*
		// * @res
		// * "收票日期应该小于到期日期大于等于出票日期"
		// */
		// + "\n";
		// }
		if (gatherdate.before(invoicedate)) {
			return "收票日期应该大于等于出票日期\n";
		}
		if (enddate.before(invoicedate)) {
			return nc.ui.ml.NCLangRes.getInstance().getStrByID("36201005", "UPP36201005-000006")/*
																								 * @res
																								 * "出票日期应该小于到期日期"
																								 */
					+ "\n";
		}
		// 到期日期-出票日期<6个月
		// UFDate maxDate = NatureDateTools.getDateAfterMonth(invoicedate, 6);
		// if(enddate.after(maxDate)){
		// return
		// nc.ui.ml.NCLangRes.getInstance().getStrByID("36201005","UPP36201005-000007")/*
		// @res"到期日期小于等于（出票日期＋6个月）"*/+ "\n";
		// }
		if (currentdate != null
				&& gatherdate != null
				&& currentdate.before(gatherdate)) {
			return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcode112", "UPPFBMCODE112-000008")/*
																											 * @res
																											 * "收票日期应小于等于当前登陆日期"
																											 */;
		}
		return null;
	}
}