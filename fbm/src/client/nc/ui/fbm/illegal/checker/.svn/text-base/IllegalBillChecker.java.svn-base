package nc.ui.fbm.illegal.checker;

import nc.vo.fbm.illegal.IllegalVO;
import nc.vo.trade.checkrule.CheckRule;
import nc.vo.trade.checkrule.ICheckRule;
import nc.vo.trade.checkrule.ICheckRules;
import nc.vo.trade.checkrule.ICheckRules2;
import nc.vo.trade.checkrule.ICompareRule;
import nc.vo.trade.checkrule.ISpecialChecker;
import nc.vo.trade.checkrule.IUniqueRule;
import nc.vo.trade.checkrule.IUniqueRules;
import nc.vo.trade.checkrule.UniqueRule;

/**
 * illegal bill checker to check whether the billNo is null or whether the
 * billNo is repeated.
 *
 * @author wues
 *
 */
public class IllegalBillChecker implements ICheckRules, ICheckRules2,
		IUniqueRules {

	// /////////////ICheckRules
	public ICheckRule[] getHeadCheckRules() {
		// TODO Auto-generated method stub
		return null;
	}

	public ICompareRule[] getHeadCompareRules() {
		// TODO Auto-generated method stub
		return null;
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
		return new CheckRule[] {
		// check whether the billNo is null
		new CheckRule(nc.ui.ml.NCLangRes.getInstance().getStrByID("36200835","UPP36200835-000000")/* @res"非法票据编号不能为空"*/, IllegalVO.FBMBILLNO, false, null,
				null) };// return some check rules
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
	/**
	 * 是否允许表体为空
	 */
	public boolean isAllowEmptyBody(String tablecode) {
		return true;
	}

	///////IUniqueRules
	public IUniqueRule[] getHeadUniqueRules() {
		// TODO Auto-generated method stub
		return null;
	}

	// to check whether the billNo is repeated
	public IUniqueRule[] getItemUniqueRules(String tablecode) {
		return new IUniqueRule[] { new UniqueRule(nc.ui.ml.NCLangRes.getInstance().getStrByID("36200835","UPP36200835-000001")/* @res"此票据编号已存在"*/,
				new String[] { IllegalVO.FBMBILLNO }) };
	}

}