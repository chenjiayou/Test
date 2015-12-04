/**
 *
 */
package nc.ui.fbm.invoice.checker;

import nc.itf.cdm.util.CommonUtil;
import nc.ui.fbm.pub.AbstractUIChecker;
import nc.ui.fbm.pub.FBMManageUI;
import nc.ui.pub.beans.UIComboBox;
import nc.vo.fbm.pub.constant.FbmBusConstant;
import nc.vo.fbm.register.RegisterVO;
import nc.vo.pub.lang.UFDouble;

/**
 * <p>
 *
 * <p>创建人：lpf
 * <b>日期：2007-11-29
 *
 */
public class AssureTypeSaveChecker extends AbstractUIChecker {

	/**
	 *
	 */
	public AssureTypeSaveChecker() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ui
	 */
	public AssureTypeSaveChecker(FBMManageUI ui) {
		super(ui);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see nc.ui.fbm.pub.AbstractUIChecker#check()
	 */
	@Override
	public String check() {
		UIComboBox combox = (UIComboBox) getUI().getBillCardPanel().getHeadItem(RegisterVO.IMPAWNMODE).getComponent();
		String assuretype = (String) combox.getSelectdItemValue();
		String billmoneyy = getUI().getBillCardPanel().getHeadItem(RegisterVO.MONEYY).getValueObject().toString();
		Double dbillmoney = new UFDouble(billmoneyy).doubleValue();

		if(!CommonUtil.isNull(assuretype)){
			if(assuretype.equals(FbmBusConstant.ASSURETYPE_BAIL)){
				String securityMoney = (String) getUI().getBillCardPanel().getHeadItem(RegisterVO.SECURITYMONEY).getValueObject();
				String securityaccount = (String) getUI().getBillCardPanel().getHeadItem(RegisterVO.SECURITYACCOUNT).getValueObject();
				Double dsecuritymoney = new UFDouble(securityMoney).doubleValue();

				if(CommonUtil.isNull(securityMoney)||new UFDouble(securityMoney).doubleValue()<=0){
					return nc.ui.ml.NCLangRes.getInstance().getStrByID("36201505","UPP36201505-000000")/* @res"担保方式为保证金，保证金额必须大于0"*/;
				}
				if(CommonUtil.isNull(securityaccount)){
					return nc.ui.ml.NCLangRes.getInstance().getStrByID("36201505","UPP36201505-000001")/* @res"担保方式为保证金，保证金账号不能为空"*/;
				}
				if(!CommonUtil.isNull(billmoneyy) && !CommonUtil.isNull(securityMoney) && dsecuritymoney>dbillmoney){
					return nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("fbmcode112","UPPFBMCODE112-000016")/*@res "保证金金额不允许大于票面金额"*/;
				}

			}
		}
		return null;
	}

}