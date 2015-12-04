/**
 *
 */
package nc.ui.fbm.gather.checker;


import nc.ui.fbm.pub.AbstractUIChecker;
import nc.ui.fbm.pub.FBMManageUI;
import nc.vo.fbm.register.RegisterVO;
import nc.vo.pub.lang.UFDate;

/**
 * <p>
 * 校验出票日期必须小于到期日期
 * <p>创建人：lpf
 * <b>日期：2007-9-7
 *
 */
public class InvoiceEndDateChecker extends AbstractUIChecker {

	/**
	 *
	 */
	public InvoiceEndDateChecker() {
		// TODO Auto-generated constructor stub
	}


	public InvoiceEndDateChecker(FBMManageUI ui) {
		super(ui);
		// TODO Auto-generated constructor stub
	}


	@Override
	public String check() {
		UFDate enddate=new UFDate((String)getUI().getBillCardPanel().getHeadItem(RegisterVO.ENDDATE).getValueObject());
		UFDate invoicedate=new UFDate((String)getUI().getBillCardPanel().getHeadItem(RegisterVO.INVOICEDATE).getValueObject());
		if(enddate.before(invoicedate)){
			return nc.ui.ml.NCLangRes.getInstance().getStrByID("36201005","UPP36201005-000006")/* @res"出票日期应该小于到期日期"*/+ "\n";
		}

		//到期日期-出票日期<6个月
//		UFDate maxDate = NatureDateTools.getDateAfterMonth(invoicedate, 6);
//		if(enddate.after(maxDate)){
//			return nc.ui.ml.NCLangRes.getInstance().getStrByID("36201005","UPP36201005-000007")/* @res"到期日期小于等于（出票日期＋6个月）"*/+ "\n";
//		}

		// try {
		// String paybankacc =
		// getUI().getBillCardPanel().getHeadItem(RegisterVO.PAYBANKACC).getValueObject().toString();
		// BankaccbasVO bankaccbasVO =
		// (BankaccbasVO)FBMProxy.getUifService().queryByPrimaryKey(BankaccbasVO.class,
		// paybankacc);
		// UFDate opendate = (UFDate)bankaccbasVO.getAccopendate();
		// if(invoicedate!=null && opendate!=null &&
		// invoicedate.before(opendate)){
		// return
		// nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcode112","UPPFBMCODE112-000009")/*@res
		// "出票日期不能早于付款银行账户的开户日期"*/;
		// }
		// } catch (UifException e) {
		// Logger.error(e.getMessage());
		// }
		// String impawnmode =
		// getUI().getBillCardPanel().getHeadItem(RegisterVO.IMPAWNMODE).getValueObject().toString();
		// if(impawnmode!=null && impawnmode.equals("BAIL")){
		// try {
		// String securityacc =
		// getUI().getBillCardPanel().getHeadItem(RegisterVO.SECURITYACCOUNT).getValueObject().toString();
		// BankaccbasVO bankaccVO =
		// (BankaccbasVO)FBMProxy.getUifService().queryByPrimaryKey(BankaccbasVO.class,
		// securityacc);
		// UFDate opendate = (UFDate)bankaccVO.getAccopendate();
		// if(invoicedate!=null && opendate!=null &&
		// invoicedate.before(opendate)){
		// return
		// nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcode112","UPPFBMCODE112-000010")/*@res
		// "出票日期不能早于保证金账户的开户日期"*/;
		// }
		// } catch (UifException e) {
		// Logger.error(e.getMessage());
		// }
		// }

		return null;
	}

}