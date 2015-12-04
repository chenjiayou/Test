/**
 *
 */
package nc.ui.fbm.gather.checker;

import nc.itf.cdm.util.CommonUtil;
import nc.ui.fbm.pub.AbstractUIChecker;
import nc.ui.fbm.pub.FBMManageUI;
import nc.ui.pub.beans.UIRefPane;
import nc.vo.fbm.register.RegisterVO;

/**
 * <p>
 * 收付款单位校验
 * <p>创建人：lpf
 * <b>日期：2007-8-28
 *
 */
public class PayGetUnitNotEqualChecker extends AbstractUIChecker {

	/**
	 *
	 */
	public PayGetUnitNotEqualChecker() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ui
	 */
	public PayGetUnitNotEqualChecker(FBMManageUI ui) {
		super(ui);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see nc.ui.fbm.pub.AbstractUIChecker#check()
	 */
	@Override
	public String check() {
		StringBuffer errors=new StringBuffer();
		((UIRefPane)getUI().getBillCardPanel().getHeadItem(RegisterVO.PAYUNIT).getComponent()).getText();
		String payunit=(String) getUI().getBillCardPanel().getHeadItem(RegisterVO.PAYUNIT).getValueObject();
		if(payunit == null){
			payunit = ((UIRefPane)getUI().getBillCardPanel().getHeadItem(RegisterVO.PAYUNIT).getComponent()).getText();
		}
		String receiveunit=(String) getUI().getBillCardPanel().getHeadItem(RegisterVO.RECEIVEUNIT).getValueObject();
		if(receiveunit == null){
			receiveunit = ((UIRefPane)getUI().getBillCardPanel().getHeadItem(RegisterVO.RECEIVEUNIT).getComponent()).getText();
		}
		
		if(!CommonUtil.isNull(payunit)&&!CommonUtil.isNull(receiveunit)){
			if(payunit.equalsIgnoreCase(receiveunit)){
				errors.append(nc.ui.ml.NCLangRes.getInstance().getStrByID("36201005","UPP36201005-000009")/* @res"付款单位和收款单位不能选择同一客商"*/);
			}
		}else if(CommonUtil.isNull(payunit)&&CommonUtil.isNull(receiveunit)){
			String payunitname=(String) getUI().getBillCardPanel().getHeadItem(RegisterVO.PAYUNITNAME).getValueObject();
			String receiveunitname=(String) getUI().getBillCardPanel().getHeadItem(RegisterVO.RECEIVEUNITNAME).getValueObject();
			if(!CommonUtil.isNull(payunitname) && !CommonUtil.isNull(receiveunitname)&& payunitname.equalsIgnoreCase(receiveunitname)){
				errors.append(nc.ui.ml.NCLangRes.getInstance().getStrByID("36201005","UPP36201005-000009")/* @res"付款单位和收款单位不能选择同一客商"*/);
			}
		}
		return errors.toString();
	}
}