/**
 *
 */
package nc.ui.fbm.gather;

import java.awt.Container;

import nc.ui.trade.businessaction.IPFACTION;
import nc.ui.trade.check.BeforeActionCHK;
import nc.vo.fbm.register.RegisterVO;
import nc.vo.pub.AggregatedValueObject;
import nc.vo.pub.BusinessException;
import nc.vo.trade.checkrule.CheckRule;
import nc.vo.trade.checkrule.CompareRule;
import nc.vo.trade.checkrule.ICheckRule;
import nc.vo.trade.checkrule.ICheckRules;
import nc.vo.trade.checkrule.ICompareRule;
import nc.vo.trade.checkrule.VOChecker;

/**
 * <p>
 *
 * <p>�����ˣ�lpf
 * <b>���ڣ�2007-8-15
 *
 */
public class GatherUIChecker extends BeforeActionCHK implements ICheckRules {

	/**
	 *
	 */
	public GatherUIChecker() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see nc.vo.trade.checkrule.ICheckRules#getHeadCheckRules()
	 */
	public ICheckRule[] getHeadCheckRules() {
		// TODO Auto-generated method stub
		return new CheckRule[]{
			new CheckRule(nc.ui.ml.NCLangRes.getInstance().getStrByID("36201005","UPT36201005-000010")/* @res"����"*/,RegisterVO.PK_CURR,false,null,null),
			new CheckRule(nc.ui.ml.NCLangRes.getInstance().getStrByID("36201005","UPT36201005-000005")/* @res"Ʊ�ݱ��"*/,RegisterVO.PK_BASEINFO,false,null,null),
			new CheckRule(nc.ui.ml.NCLangRes.getInstance().getStrByID("36201005","UPT36201005-000028")/* @res"Ʊ������"*/,RegisterVO.FBMBILLTYPE,false,null,null),
			new CheckRule(nc.ui.ml.NCLangRes.getInstance().getStrByID("36201005","UPT36201005-000004")/* @res"�տλ"*/,RegisterVO.RECEIVEUNIT,false,null,null),
			new CheckRule(nc.ui.ml.NCLangRes.getInstance().getStrByID("36201005","UPT36201005-000042")/* @res"���λ"*/,RegisterVO.PAYUNIT,false,null,null),
			new CheckRule(nc.ui.ml.NCLangRes.getInstance().getStrByID("36201005","UPP36201005-000003")/* @res"���������˺�"*/,RegisterVO.PAYBANKACC,false,null,null),
			new CheckRule(nc.ui.ml.NCLangRes.getInstance().getStrByID("36201005","UPP36201005-000004")/* @res"�ո��������˺�"*/,RegisterVO.RECEIVEBANKACC,false,null,null),
			new CheckRule(nc.ui.ml.NCLangRes.getInstance().getStrByID("36201005","UPT36201005-000028")/* @res"Ʊ������"*/,RegisterVO.FBMBILLTYPE,false,null,null),
			new CheckRule(nc.ui.ml.NCLangRes.getInstance().getStrByID("36201005","UPT36201005-000029")/* @res"��Ʊ���"*/,RegisterVO.MONEYY,false,null,null),
			new CheckRule(nc.ui.ml.NCLangRes.getInstance().getStrByID("36201005","UPT36201005-000035")/* @res"��Ʊ����"*/,RegisterVO.INVOICEDATE,false,null,null),
			new CheckRule(nc.ui.ml.NCLangRes.getInstance().getStrByID("36201005","UPT36201005-000037")/* @res"��������"*/,RegisterVO.ENDDATE,false,null,null),
			new CheckRule(nc.ui.ml.NCLangRes.getInstance().getStrByID("36201005","UPT36201005-000007")/* @res"��Ʊ����"*/,RegisterVO.GATHERDATE,false,null,null),
			new CheckRule(nc.ui.ml.NCLangRes.getInstance().getStrByID("36201005","UPT36201005-000006")/* @res"��Ʊ��Դ����"*/,RegisterVO.GATHERTYPE,false,null,null)
		};
	}

	/* (non-Javadoc)
	 * @see nc.vo.trade.checkrule.ICheckRules#getHeadCompareRules()
	 */
	public ICompareRule[] getHeadCompareRules() {
		// TODO Auto-generated method stub
		return new CompareRule[] {
				new CompareRule(RegisterVO.ENDDATE,RegisterVO.ENDDATE, ICompareRule.OPERATOR_MORE,RegisterVO.INVOICEDATE, RegisterVO.INVOICEDATE,nc.vo.pf.pub.IDapType.UFDATE),
				new CompareRule(RegisterVO.GATHERDATE,RegisterVO.GATHERDATE, ICompareRule.OPERATOR_MORE,RegisterVO.INVOICEDATE, RegisterVO.INVOICEDATE,nc.vo.pf.pub.IDapType.UFDATE),
				new CompareRule(RegisterVO.ENDDATE,RegisterVO.ENDDATE, ICompareRule.OPERATOR_MORE,RegisterVO.GATHERDATE, RegisterVO.GATHERDATE,nc.vo.pf.pub.IDapType.UFDATE)};
	}

	/* (non-Javadoc)
	 * @see nc.vo.trade.checkrule.ICheckRules#getHeadIntegerField()
	 */
	public String[] getHeadIntegerField() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see nc.vo.trade.checkrule.ICheckRules#getHeadUFDoubleField()
	 */
	public String[] getHeadUFDoubleField() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see nc.vo.trade.checkrule.ICheckRules#getItemCheckRules(java.lang.String)
	 */
	public ICheckRule[] getItemCheckRules(String tablecode) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see nc.vo.trade.checkrule.ICheckRules#getItemCompareRules(java.lang.String)
	 */
	public ICompareRule[] getItemCompareRules(String tablecode) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see nc.vo.trade.checkrule.ICheckRules#getItemIntegerField(java.lang.String)
	 */
	public String[] getItemIntegerField(String tablecode) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see nc.vo.trade.checkrule.ICheckRules#getItemUFDoubleField(java.lang.String)
	 */
	public String[] getItemUFDoubleField(String tablecode) {
		// TODO Auto-generated method stub
		return null;
	}

	public void runClass(Container parent, String billType, String actionName,
			AggregatedValueObject vo, Object obj) throws Exception {
		// TODO Auto-generated method stub
		if (actionName.equals(IPFACTION.COMMIT)) {
			if (!VOChecker.check(vo, new GatherUIChecker())) {
				throw new BusinessException(VOChecker.getErrorMessage());
			}
		}
	}

	public void runBatchClass(Container parent, String billType,
			String actionName, AggregatedValueObject[] vos, Object[] obj)
			throws Exception {
		// TODO Auto-generated method stub

	}

}