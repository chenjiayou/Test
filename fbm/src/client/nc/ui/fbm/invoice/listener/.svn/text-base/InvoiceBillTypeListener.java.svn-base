/**
 *
 */
package nc.ui.fbm.invoice.listener;

import nc.itf.cdm.util.CommonUtil;
import nc.ui.fbm.pub.AbstractItemEditListener;
import nc.ui.fbm.pub.FBMManageUI;
import nc.ui.pub.bill.BillCardPanel;
import nc.ui.pub.bill.BillEditEvent;
import nc.vo.fbm.pub.constant.FbmBusConstant;
import nc.vo.fbm.register.RegisterVO;

/**
 * <p>
 * 票据类型编辑监听
 * <p>创建人：lpf
 * <b>日期：2007-9-29
 *
 */
public class InvoiceBillTypeListener extends AbstractItemEditListener {

	/**
	 * 
	 */
	public InvoiceBillTypeListener() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ui
	 * @param itemKey
	 */
	public InvoiceBillTypeListener(FBMManageUI ui, String itemKey) {
		super(ui, itemKey);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ui
	 * @param itemKey
	 * @param pos
	 */
	public InvoiceBillTypeListener(FBMManageUI ui, String itemKey, int pos) {
		super(ui, itemKey, pos);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see nc.ui.fbm.pub.AbstractItemEditListener#responseEditEvent(nc.ui.pub.bill.BillEditEvent)
	 */
	@Override
	public void responseEditEvent(BillEditEvent editEvent) {
		// TODO Auto-generated method stub
		BillCardPanel billCardPanel = getUI().getBillCardPanel();
		String fbmBillType = (String) billCardPanel.getHeadItem(RegisterVO.FBMBILLTYPE).getValueObject();
		if(CommonUtil.isNull(fbmBillType)){
			return;
		}
		//if(fbmBillType.equals(FbmBusConstant.ACCEPTANCE_BANK)){
			billCardPanel.getHeadItem(RegisterVO.CCTYPE).setEnabled(true);
			String cctype = (String) billCardPanel.getHeadItem(RegisterVO.CCTYPE).getValueObject();
			if(CommonUtil.isNull(cctype)||cctype.equals(FbmBusConstant.CCTYPE_NONE)){
				billCardPanel.getHeadItem(RegisterVO.PK_LOANBANK).setEnabled(false);
			}else{
				billCardPanel.getHeadItem(RegisterVO.PK_LOANBANK).setEnabled(true);
			}

	}

}
