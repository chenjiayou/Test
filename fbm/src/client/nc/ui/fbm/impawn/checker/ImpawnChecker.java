package nc.ui.fbm.impawn.checker;

import nc.vo.trade.checkrule.CheckRule;
import nc.vo.trade.checkrule.ICheckRule;
import nc.vo.trade.checkrule.ICheckRules;
import nc.vo.trade.checkrule.ICheckRules2;
import nc.vo.trade.checkrule.ICompareRule;
import nc.vo.trade.checkrule.ISpecialChecker;
import nc.vo.trade.checkrule.IUniqueRule;
import nc.vo.trade.checkrule.IUniqueRules;

/**
 * 功能： VO校验类 日期：2007-9-28 程序员：wues
 */
public class ImpawnChecker implements ICheckRules, ICheckRules2, IUniqueRules {

	public ImpawnChecker() {
		super();
	}

	// /////////////ICheckRules
	public ICheckRule[] getHeadCheckRules() {
		return new CheckRule[] { new CheckRule(nc.ui.ml.NCLangRes.getInstance().getStrByID("36201040","UPP36201040-000002")/* @res"单据编号不能为空"*/, "vbillno", false,
				null, null),new CheckRule(nc.ui.ml.NCLangRes.getInstance().getStrByID("36201040","UPP36201040-000003")/* @res"票据号不能为空"*/, "pk_source", false,
						null, null)  };
	}

	public ICompareRule[] getHeadCompareRules() {
		return null;
//		return new CompareRule[] {
//				new CompareRule("质押日期", ImpawnVO.IMPAWNDATE,
//						ICompareRule.OPERATOR_LESS, "到期日期", ImpawnVO.ENDDATE,
//						IDapType.UFDATE),
//				new CompareRule("质押日期", ImpawnVO.IMPAWNDATE,
//						ICompareRule.OPERATOR_MORE, "收到日期", ImpawnVO.GATHERDATE,
//						IDapType.UFDATE),
//				new CompareRule("质押日期", ImpawnVO.IMPAWNDATE,
//						ICompareRule.OPERATOR_MORE, "到期日期", ImpawnVO.INVOICEDATE,
//						IDapType.UFDATE)};
	}

	public String[] getHeadIntegerField() {
		// TODO Auto-generated method stub
		return null;
	}

	public String[] getHeadUFDoubleField() {
		// TODO Auto-generated method stub
		return null;
	}

	// return body items check rules
	public ICheckRule[] getItemCheckRules(String tablecode) {
		return null;
	}

	public ICompareRule[] getItemCompareRules(String tablecode) {
		// TODO Auto-generated method stub
		return null;
	}

	public String[] getItemIntegerField(String tablecode) {
		// TODO Auto-generated method stub
		return null;
	}

	public String[] getItemUFDoubleField(String tablecode) {
		// TODO Auto-generated method stub
		return null;
	}

	// /////ICheckRules2
	public ISpecialChecker getSpecialChecker() {
		// TODO Auto-generated method stub
		return null;
	}

	// return true empty body
	public boolean isAllowEmptyBody(String tablecode) {
		return true;
	}

	// /////IUniqueRules,但表头不执行此段，唯一性检查只简单的检查UI列表中的是否重复
	public IUniqueRule[] getHeadUniqueRules() {
//		return new IUniqueRule[] { new UniqueRule("此票据已经质押，不允许重复质押",
//				new String[] { "pk_source","dr" }) };
		return null;
	}

	// to check whether the billNo is repeated
	public IUniqueRule[] getItemUniqueRules(String tablecode) {
		return null;
	}

}