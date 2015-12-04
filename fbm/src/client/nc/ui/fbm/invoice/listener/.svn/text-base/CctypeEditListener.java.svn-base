/**
 *
 */
package nc.ui.fbm.invoice.listener;

import nc.itf.cdm.util.CommonUtil;
import nc.ui.fbm.pub.AbstractItemEditListener;
import nc.ui.fbm.pub.FBMManageUI;
import nc.ui.pub.bill.BillEditEvent;
import nc.ui.pub.bill.BillItem;
import nc.vo.fbm.pub.constant.FbmBusConstant;
import nc.vo.fbm.register.RegisterVO;

/**
 * <p>
 * 授信类型编辑判断贷款银行是否可以编辑，是否可空
 * <p>创建人：lpf
 * <b>日期：2007-10-18
 *
 */
public class CctypeEditListener extends AbstractItemEditListener {

	private String itemKey;

	/**
	 * 
	 */
	public CctypeEditListener() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ui
	 * @param itemKey
	 */
	public CctypeEditListener(FBMManageUI ui, String itemKey) {
		super(ui, itemKey);
		this.itemKey = itemKey;
	}

	/**
	 * @param ui
	 * @param itemKey
	 * @param pos
	 */
	public CctypeEditListener(FBMManageUI ui, String itemKey, int pos) {
		super(ui, itemKey, pos);
	}

	/* (non-Javadoc)
	 * @see nc.ui.fbm.pub.AbstractItemEditListener#responseEditEvent(nc.ui.pub.bill.BillEditEvent)
	 */
	@Override
	public void responseEditEvent(BillEditEvent editEvent) {
		String cctype = (String) getUI().getBillCardPanel().getHeadItem(itemKey).getValueObject();
		BillItem loanBankItem = getUI().getBillCardPanel().getHeadItem(RegisterVO.PK_LOANBANK);
		if(!CommonUtil.isNull(cctype)&&!cctype.equals(FbmBusConstant.CCTYPE_NONE)){
			loanBankItem.setEnabled(true);
//			loanBankItem.setNull(false);
		}else{
			loanBankItem.setEnabled(false);
//			loanBankItem.setNull(true);
			loanBankItem.setValue(null);
		}
	}

}
