package nc.ui.fbm.invoice.listener;

import nc.itf.cdm.util.CommonUtil;
import nc.ui.fbm.pub.AbstractItemEditListener;
import nc.ui.fbm.pub.FBMManageUI;
import nc.ui.pub.bill.BillEditEvent;
import nc.vo.fbm.pub.constant.FbmBusConstant;
import nc.vo.fbm.register.RegisterVO;

/**
 * <p>
 * 开票登记的担保方式
 * <p>创建人：lpf
 * <b>日期：2007-9-7
 *
 */
public class AssureTypeEditListener extends AbstractItemEditListener {

	public AssureTypeEditListener() {
		// TODO Auto-generated constructor stub
	}

	public AssureTypeEditListener(FBMManageUI ui, String itemKey) {
		super(ui, itemKey);
		// TODO Auto-generated constructor stub
	}

	public AssureTypeEditListener(FBMManageUI ui, String itemKey, int pos) {
		super(ui, itemKey, pos);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void responseEditEvent(BillEditEvent editEvent) {
		String assureType=(String) (getUI().getBillCardPanel().getHeadItem(RegisterVO.IMPAWNMODE).getValueObject());
		if(CommonUtil.isNull(assureType))
			return;
		if(assureType.equalsIgnoreCase(FbmBusConstant.ASSURETYPE_BAIL)){
			getUI().setHeadItemEditable(new String[]{RegisterVO.SECURITYACCOUNT,RegisterVO.SECURITYMONEY,RegisterVO.SECURITYRATE}, true);
			getUI().getBillCardPanel().getHeadItem(RegisterVO.SECURITYMONEY).setNull(true);
			getUI().getBillCardPanel().getHeadItem(RegisterVO.SECURITYACCOUNT).setNull(true);
		}else{
			getUI().setHeadItemEditable(new String[]{RegisterVO.SECURITYACCOUNT,RegisterVO.SECURITYMONEY,RegisterVO.SECURITYRATE}, false);
			getUI().getBillCardPanel().getHeadItem(RegisterVO.SECURITYACCOUNT).setValue(null);
			getUI().getBillCardPanel().getHeadItem(RegisterVO.SECURITYMONEY).setValue(null);
			getUI().getBillCardPanel().getHeadItem(RegisterVO.SECURITYRATE).setValue(null);
			getUI().getBillCardPanel().getHeadItem(RegisterVO.SECURITYMONEYB).setValue(null);
			getUI().getBillCardPanel().getHeadItem(RegisterVO.SECURITYMONEYF).setValue(null);
			getUI().getBillCardPanel().getHeadItem(RegisterVO.SECURITYMONEY).setNull(false);
			getUI().getBillCardPanel().getHeadItem(RegisterVO.SECURITYACCOUNT).setNull(false);
		}

	}

}
