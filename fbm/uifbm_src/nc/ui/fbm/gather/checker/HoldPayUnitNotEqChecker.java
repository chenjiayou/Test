/**
 *
 */
package nc.ui.fbm.gather.checker;

import nc.ui.fbm.pub.AbstractUIChecker;
import nc.ui.fbm.pub.FBMManageUI;
import nc.vo.fbm.register.RegisterVO;

/**
 * <p>
 * ��Ʊ��λ�ͳ�Ʊ��λУ��
 * <p>�����ˣ�lpf
 * <b>���ڣ�2007-10-8
 *
 */
public class HoldPayUnitNotEqChecker extends AbstractUIChecker {

	/**
	 *
	 */
	public HoldPayUnitNotEqChecker() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ui
	 */
	public HoldPayUnitNotEqChecker(FBMManageUI ui) {
		super(ui);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see nc.ui.fbm.pub.AbstractUIChecker#check()
	 */
	@Override
	public String check() {
		StringBuffer errors=new StringBuffer();
		String holdUnit=(String) getUI().getBillCardPanel().getHeadItem(RegisterVO.HOLDUNIT).getValueObject();
		String paybillUnit=(String) getUI().getBillCardPanel().getHeadItem(RegisterVO.PAYBILLUNIT).getValueObject();
		if(holdUnit.equalsIgnoreCase(paybillUnit)){
			errors.append(nc.ui.ml.NCLangRes.getInstance().getStrByID("36201005","UPP36201005-000008")/* @res"��Ʊ��λ�ͳ�Ʊ��λ����ѡ��ͬһ����"*/);
		}
		return errors.toString();
	}

}