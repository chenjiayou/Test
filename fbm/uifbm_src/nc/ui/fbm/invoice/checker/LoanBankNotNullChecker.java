/**
 *
 */
package nc.ui.fbm.invoice.checker;

import nc.itf.cdm.util.CommonUtil;
import nc.ui.fbm.pub.AbstractUIChecker;
import nc.ui.fbm.pub.FBMManageUI;
import nc.vo.fbm.pub.constant.FbmBusConstant;
import nc.vo.fbm.register.RegisterVO;

/**
 * <p>
 *
 * <p>创建人：lpf
 * <b>日期：2007-11-7
 *
 */
public class LoanBankNotNullChecker extends AbstractUIChecker {

	/**
	 *
	 */
	public LoanBankNotNullChecker() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ui
	 */
	public LoanBankNotNullChecker(FBMManageUI ui) {
		super(ui);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see nc.ui.fbm.pub.AbstractUIChecker#check()
	 */
	@Override
	public String check() {
		String cctype = (String) getUI().getBillCardPanel().getHeadItem(RegisterVO.CCTYPE).getValueObject();
		String pk_loanbank = (String) getUI().getBillCardPanel().getHeadItem(RegisterVO.PK_LOANBANK).getValueObject();
		if(!cctype.equals(FbmBusConstant.CCTYPE_NONE)){
			if(CommonUtil.isNull(pk_loanbank)){
				return nc.ui.ml.NCLangRes.getInstance().getStrByID("36201505","UPP36201505-000002")/* @res" 使用授信额度，贷款银行不能为空"*/;
			}
		}
		return null;
	}

}