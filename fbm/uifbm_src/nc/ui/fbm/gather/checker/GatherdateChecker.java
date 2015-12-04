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
 * ��Ʊ�Ǽǽ�������У��
 * <p>
 * �����ˣ�lpf <b>���ڣ�2007-8-16
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
		// * "��Ʊ����Ӧ��С�ڵ������ڴ��ڵ��ڳ�Ʊ����"
		// */
		// + "\n";
		// }
		if (gatherdate.before(invoicedate)) {
			return "��Ʊ����Ӧ�ô��ڵ��ڳ�Ʊ����\n";
		}
		if (enddate.before(invoicedate)) {
			return nc.ui.ml.NCLangRes.getInstance().getStrByID("36201005", "UPP36201005-000006")/*
																								 * @res
																								 * "��Ʊ����Ӧ��С�ڵ�������"
																								 */
					+ "\n";
		}
		// ��������-��Ʊ����<6����
		// UFDate maxDate = NatureDateTools.getDateAfterMonth(invoicedate, 6);
		// if(enddate.after(maxDate)){
		// return
		// nc.ui.ml.NCLangRes.getInstance().getStrByID("36201005","UPP36201005-000007")/*
		// @res"��������С�ڵ��ڣ���Ʊ���ڣ�6���£�"*/+ "\n";
		// }
		if (currentdate != null
				&& gatherdate != null
				&& currentdate.before(gatherdate)) {
			return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcode112", "UPPFBMCODE112-000008")/*
																											 * @res
																											 * "��Ʊ����ӦС�ڵ��ڵ�ǰ��½����"
																											 */;
		}
		return null;
	}
}