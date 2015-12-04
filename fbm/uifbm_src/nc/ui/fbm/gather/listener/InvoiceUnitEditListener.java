/**
 *
 */
package nc.ui.fbm.gather.listener;

import nc.itf.cdm.util.CommonUtil;
import nc.ui.fbm.pub.AbstractItemEditListener;
import nc.ui.fbm.pub.FBMManageUI;
import nc.ui.pub.beans.UIRefPane;
import nc.ui.pub.bill.BillEditEvent;
import nc.vo.fbm.register.RegisterVO;

/**
 * <p>
 * 出票单位编辑监听器
 * <p>创建人：lpf
 * <b>日期：2007-9-13
 *
 */
public class InvoiceUnitEditListener extends AbstractItemEditListener {

	/**
	 * 
	 */
	public InvoiceUnitEditListener() {
		// TODO Auto-generated constructor stub
	}


	public InvoiceUnitEditListener(FBMManageUI ui, String itemKey, int pos) {
		super(ui, itemKey, pos);
		// TODO Auto-generated constructor stub
	}


	public InvoiceUnitEditListener(FBMManageUI ui, String itemKey) {
		super(ui, itemKey);
		// TODO Auto-generated constructor stub
	}


	@Override
	public void responseEditEvent(BillEditEvent editEvent) {
		UIRefPane cubasunit = (UIRefPane) getUI().getBillCardPanel().getHeadItem(getItemKey()).getComponent();
		String cubasTxt = cubasunit.getText().trim();
		String pk_cubasdoc = cubasunit.getRefPK();
		
		if(CommonUtil.isNull(pk_cubasdoc)){
			getUI().getBillCardPanel().getHeadItem(RegisterVO.INVOICEUNITNAME).setValue(cubasTxt);
		}else{
			getUI().getBillCardPanel().getHeadItem(RegisterVO.INVOICEUNITNAME).setValue(cubasunit.getRefValue("custname"));
		}
	}
	
}
