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
 * У���Ʊ���ڱ���С�ڵ�������
 * <p>�����ˣ�lpf
 * <b>���ڣ�2007-9-7
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
			return nc.ui.ml.NCLangRes.getInstance().getStrByID("36201005","UPP36201005-000006")/* @res"��Ʊ����Ӧ��С�ڵ�������"*/+ "\n";
		}

		//��������-��Ʊ����<6����
//		UFDate maxDate = NatureDateTools.getDateAfterMonth(invoicedate, 6);
//		if(enddate.after(maxDate)){
//			return nc.ui.ml.NCLangRes.getInstance().getStrByID("36201005","UPP36201005-000007")/* @res"��������С�ڵ��ڣ���Ʊ���ڣ�6���£�"*/+ "\n";
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
		// "��Ʊ���ڲ������ڸ��������˻��Ŀ�������"*/;
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
		// "��Ʊ���ڲ������ڱ�֤���˻��Ŀ�������"*/;
		// }
		// } catch (UifException e) {
		// Logger.error(e.getMessage());
		// }
		// }

		return null;
	}

}