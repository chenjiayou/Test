/**
 *
 */
package nc.ui.fbm.accept.checker;

import nc.ui.fbm.pub.AbstractUIChecker;
import nc.ui.fbm.pub.FBMManageUI;
import nc.vo.fbm.acceptbill.AcceptVO;
import nc.vo.pub.lang.UFDouble;

/**
 * <p>
 *
 * <p>创建人：lpf
 * <b>日期：2007-11-7
 *
 */
public class SecurityMoneyChecker extends AbstractUIChecker {

	/**
	 *
	 */
	public SecurityMoneyChecker() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ui
	 */
	public SecurityMoneyChecker(FBMManageUI ui) {
		super(ui);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see nc.ui.fbm.pub.AbstractUIChecker#check()
	 */
	@Override
	public String check() {
//		String pledge = (String) getUI().getBillCardPanel().getHeadItem(AcceptVO.IMPAWNMODE).getValueObject();
		String billmoneyy = (String) getUI().getBillCardPanel().getHeadItem(AcceptVO.BILLMONEYY).getValueObject();
		String moneyy = (String) getUI().getBillCardPanel().getHeadItem(AcceptVO.MONEYY).getValueObject();

//		if(pledge.equals(FbmBusConstant.ASSURETYPE_BAIL)){
//			String secmoneyy = (String) getUI().getBillCardPanel().getHeadItem(AcceptVO.SECURITYMONEY).getValueObject();
//			String backmoney = (String) getUI().getBillCardPanel().getHeadItem(AcceptVO.BACKSECMONEY).getValueObject();
//
//			if(!CommonUtil.isNull(backmoney)){
//				if(new UFDouble(secmoneyy).sub(new UFDouble(backmoney)).doubleValue()<0){
//					return nc.ui.ml.NCLangRes.getInstance().getStrByID("36201510","UPP36201510-000003")/* @res"返回保证金金额应该小于保证金金额"*/;
//				}
//			}
//		}
		if(new UFDouble(billmoneyy).sub(new UFDouble(moneyy)).doubleValue()<0){
			return nc.ui.ml.NCLangRes.getInstance().getStrByID("36201510","UPP36201510-000004")/* @res"实际结算付款金额应该小于票面金额"*/;
		}

		return null;
	}

}