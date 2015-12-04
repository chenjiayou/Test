package nc.ui.fbm.impawn.checker;

import nc.ui.fbm.pub.AbstractUIChecker;
import nc.ui.fbm.pub.FBMManageUI;
import nc.ui.pub.ClientEnvironment;
import nc.vo.fbm.impawn.ImpawnVO;
import nc.vo.pub.lang.UFDate;

/**
 *
 * ���ܣ� Ʊ����Ѻ����У���� ���ڣ�2007-10-8 ����Ա��wues
 */
public class DateChecker extends AbstractUIChecker {

	public DateChecker() {
		super();

	}

	public DateChecker(FBMManageUI ui) {
		super(ui);

	}

	public String check() {
		// TODO Auto-generated method stub

		String enddate_str = (String) getUI().getBillCardPanel().getHeadItem(
				ImpawnVO.ENDDATE).getValueObject();
		UFDate enddate = null;
		UFDate currentdate = null;
		currentdate = ClientEnvironment.getInstance().getDate();
		if (null != enddate_str && !"".equals(enddate_str.trim())) {
			enddate = new UFDate(enddate_str);
		}
		String recdate_str = (String) getUI().getBillCardPanel().getHeadItem(
				ImpawnVO.GATHERDATE).getValueObject();
		UFDate recdate = null;
		if (null != recdate_str && !"".equals(recdate_str.trim())) {
			recdate = new UFDate(recdate_str);
		}
		String impawndate_str = (String) getUI().getBillCardPanel().getHeadItem(
				ImpawnVO.IMPAWNDATE).getValueObject();
		UFDate impawndate = null;
		if (null != impawndate_str && !"".equals(impawndate_str.trim())) {
			impawndate = new UFDate(impawndate_str);
		}

		if (null != enddate && null != impawndate && enddate.before(impawndate)) {
			return nc.ui.ml.NCLangRes.getInstance().getStrByID("36201040","UPP36201040-000000")/* @res"��Ѻ����ӦС��Ʊ�ݵ�����"*/ + "\n";
		}
		if (null != recdate && null != impawndate && impawndate.before(recdate)) {
			return nc.ui.ml.NCLangRes.getInstance().getStrByID("36201040","UPP36201040-000001")/* @res"��Ѻ����Ӧ�����յ�����"*/ + "\n";
		}
		if(currentdate!=null && impawndate!=null && currentdate.before(impawndate)){
			return (nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3620add","UPP3620ADD-000037")/*@res "��½���ڲ���������Ѻ����"*/);
		}

		return null;
	}

}