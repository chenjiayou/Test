package nc.ui.fbm.discountapply;

import nc.ui.fbm.pub.AbstractUIChecker;
import nc.ui.fbm.pub.FBMManageUI;
import nc.vo.fbm.discount.DiscountVO;
import nc.vo.pub.lang.UFDate;

/**
 *
 * <p>
 *	�����������ǰ̨UIУ����
 * <p>�����ˣ�bsrl
 * <b>���ڣ�2007-09
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
//				new CompareRule("��������",DiscountVO.APPLY_DATE, ICompareRule.OPERATOR_MORE,"Ʊ�ݳ�Ʊ����", DiscountVO.CPRQ,nc.vo.pf.pub.IDapType.UFDATE),
//				new CompareRule("Ʊ�ݵ�������",DiscountVO.DQRQ, ICompareRule.OPERATOR_MORE,"��������", DiscountVO.APPLY_DATE,nc.vo.pf.pub.IDapType.UFDATE),
//				new CompareRule("��������",DiscountVO.DDISCOUNTDATE, ICompareRule.OPERATOR_NOTLESS,"��������", DiscountVO.APPLY_DATE,nc.vo.pf.pub.IDapType.UFDATE),
//				new CompareRule("��������",DiscountVO.DDISCOUNTDATE, ICompareRule.OPERATOR_MORE,"Ʊ�ݳ�Ʊ����", DiscountVO.CPRQ,nc.vo.pf.pub.IDapType.UFDATE),
//				new CompareRule("Ʊ�ݵ�������",DiscountVO.DQRQ, ICompareRule.OPERATOR_MORE,"��������", DiscountVO.DDISCOUNTDATE,nc.vo.pf.pub.IDapType.UFDATE)};
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
			warn.append(nc.ui.ml.NCLangRes.getInstance().getStrByID("36201025","UPP36201025-000004")/* @res"�������ڲ�������Ʊ�ݳ�Ʊ����"*/+ "\n");
		}
		if(enddate != null && applydate != null && !enddate.after(applydate)){
			warn.append(nc.ui.ml.NCLangRes.getInstance().getStrByID("36201025","UPP36201025-000005")/* @res"Ʊ�ݵ������ڱ���������������"*/+ "\n");
		}
		if(discountdate != null && applydate != null && discountdate.before(applydate)){
			warn.append(nc.ui.ml.NCLangRes.getInstance().getStrByID("36201025","UPP36201025-000006")/* @res"�������ڲ���������������"*/+ "\n");
		}
		if(discountdate != null && invoicedate != null && !discountdate.after(invoicedate)){
			warn.append(nc.ui.ml.NCLangRes.getInstance().getStrByID("36201025","UPP36201025-000007")/* @res"�������ڱ�������Ʊ�ݳ�Ʊ����"*/+ "\n");
		}
		if(enddate != null && discountdate != null && !enddate.after(discountdate)){
			warn.append(nc.ui.ml.NCLangRes.getInstance().getStrByID("36201025","UPP36201025-000008")/* @res"Ʊ�ݵ������ڱ���������������"*/+ "\n");
		}
		return warn.toString();
	}
}