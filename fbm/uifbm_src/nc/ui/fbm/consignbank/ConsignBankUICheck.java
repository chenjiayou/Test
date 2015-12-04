package nc.ui.fbm.consignbank;

import nc.ui.fbm.pub.AbstractUIChecker;
import nc.ui.fbm.pub.FBMManageUI;
import nc.ui.pub.ClientEnvironment;
import nc.vo.fbm.consignbank.CollectionVO;
import nc.vo.pub.lang.UFDate;

/**
 * <p>
 * ��������ǰ̨У����
 * <p>
 * �����ˣ�bsrl <b>���ڣ�2007-09
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
	// new CompareRule("������������",CollectionVO.DCOLLECTIONDATE,
	// ICompareRule.OPERATOR_NOTLESS,"Ʊ�ݵ�������",
	// CollectionVO.DQRQ,nc.vo.pf.pub.IDapType.UFDATE),
	// new CompareRule("ί������",CollectionVO.DCONSIGNDATE,
	// ICompareRule.OPERATOR_MORE,"Ʊ�ݳ�Ʊ����",
	// CollectionVO.CPRQ,nc.vo.pf.pub.IDapType.UFDATE),
	// new CompareRule("Ʊ�ݵ�������",CollectionVO.DQRQ,
	// ICompareRule.OPERATOR_MORE,"ί������",
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
																											 * "������������ӦС�ڵ��ڵ�ǰ��½����"
																											 */);
		}

		if (collectiondate != null
				&& enddate != null
				&& collectiondate.before(enddate)) {
			warn.append(nc.ui.ml.NCLangRes.getInstance().getStrByID("36201020", "UPP36201020-000009")/*
																									 * @res
																									 * "�����������ڲ�������Ʊ�ݵ�������"
																									 */
					+ "\n");
		}
		if (consigndate != null
				&& invoicedate != null
				&& invoicedate.after(consigndate)) {
			warn.append(nc.ui.ml.NCLangRes.getInstance().getStrByID("36201020", "UPP36201020-000010")/*
																									 * @res
																									 * "ί�����ڱ������ڻ����Ʊ�ݳ�Ʊ����"
																									 */
					+ "\n");
		}
		if (enddate != null
				&& consigndate != null
				&& !enddate.after(consigndate)) {
			// warn.append(nc.ui.ml.NCLangRes.getInstance().getStrByID("36201020","UPP36201020-000011")/*
			// @res"Ʊ�ݵ������ڱ�������ί������"*/+ "\n");
		}
		if (collectiondate != null
				&& consigndate != null
				&& consigndate.after(collectiondate)) {
			warn.append("���հ���������������Ӧ�ô��ڵ���ί������");
		}
		return warn.toString();
	}

}