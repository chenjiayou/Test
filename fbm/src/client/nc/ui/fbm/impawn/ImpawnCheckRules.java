package nc.ui.fbm.impawn;

import nc.ui.fbm.pub.AbstractUIChecker;
import nc.ui.fbm.pub.FBMManageUI;
import nc.vo.fbm.impawn.ImpawnVO;
import nc.vo.pub.lang.UFDate;
/**
 *
 * 功能：
       票据质押的约束检查
 * 日期：2007-9-28
 * 程序员：wues
 */
public class ImpawnCheckRules  extends AbstractUIChecker {

	public ImpawnCheckRules() {
		super();

	}

	public ImpawnCheckRules(FBMManageUI ui) {
		super(ui);

	}
	@Override
	public String check() {
		String errMsg = validateDate();
		if (null != errMsg) {
			return  errMsg;
		}
		return null;
	}

	private String validateDate(){
		// 到期日期
		UFDate enddate = new UFDate((String) getUI().getBillCardPanel()
				.getHeadItem(ImpawnVO.ENDDATE).getValueObject());
		// 收到日期
		UFDate recdate = new UFDate((String) getUI().getBillCardPanel()
				.getHeadItem(ImpawnVO.GATHERDATE).getValueObject());
		// 出票日期
		UFDate opendate = new UFDate((String) getUI().getBillCardPanel()
				.getHeadItem(ImpawnVO.INVOICEDATE).getValueObject());
		// 质押日期
		UFDate impawndate = new UFDate((String) getUI().getBillCardPanel()
				.getHeadItem(ImpawnVO.IMPAWNDATE).getValueObject());

		if (enddate.before(impawndate) || impawndate.before(recdate)
				|| impawndate.before(opendate) || opendate.after(recdate)) {
			return nc.ui.ml.NCLangRes.getInstance().getStrByID("36201040","UPP36201040-000010")/* @res"质押日期应小于票据到期日且大于票据收到日期和出票日期\n"*/;
		}
		return null;
	}

}