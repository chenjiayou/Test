/**
 *
 */
package nc.ui.fbm.storage.checker;

import nc.ui.fbm.pub.AbstractUIChecker;
import nc.ui.fbm.pub.FBMManageUI;
import nc.vo.pub.CircularlyAccessibleValueObject;

/**
 * <p>
 * 表体不允许为空的界面校验
 * <p>
 * 创建人：lpf <b>日期：2007-8-25
 *
 */
public class BodyNotEmptyChecker extends AbstractUIChecker {

	/**
	 *
	 */
	public BodyNotEmptyChecker() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ui
	 */
	public BodyNotEmptyChecker(FBMManageUI ui) {
		super(ui);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String check() {
		// TODO Auto-generated method stub
		CircularlyAccessibleValueObject[] bodyVos = getUI().getBillCardPanel()
				.getBillModel().getBodyValueVOs(
						getUI().getUIControl().getBillVoName()[2]);
		if (bodyVos == null || bodyVos.length == 0)
			return nc.ui.ml.NCLangRes.getInstance().getStrByID("36201010","UPP36201010-000006")/* @res"表体为空"*/ + "\n";
		return null;
	}

}