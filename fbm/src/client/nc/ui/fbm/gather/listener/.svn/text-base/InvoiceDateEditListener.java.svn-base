/**
 *
 */
package nc.ui.fbm.gather.listener;

import nc.ui.fbm.pub.AbstractItemEditListener;
import nc.ui.fbm.pub.FBMManageUI;
import nc.ui.pub.bill.BillEditEvent;
import nc.vo.fac.ia.util.NatureDateTools;
import nc.vo.fbm.register.RegisterVO;
import nc.vo.pub.lang.UFDate;

/**
 * <p>
 * 默认到期日期为出票日期+6个月。  
 * 例如：出票日期为3月3日；到期日期为9月3日。
 * 2007.11.22 张俊 <zhangjun@ufida.com.cn> 收票登记和付票登记  易用性需求
 * 
 * <p>创建人：lpf
 * <b>日期：2007-11-22
 *
 */
public class InvoiceDateEditListener extends AbstractItemEditListener {

	/**
	 * 
	 */
	public InvoiceDateEditListener() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ui
	 * @param itemKey
	 */
	public InvoiceDateEditListener(FBMManageUI ui, String itemKey) {
		super(ui, itemKey);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ui
	 * @param itemKey
	 * @param pos
	 */
	public InvoiceDateEditListener(FBMManageUI ui, String itemKey, int pos) {
		super(ui, itemKey, pos);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see nc.ui.fbm.pub.AbstractItemEditListener#responseEditEvent(nc.ui.pub.bill.BillEditEvent)
	 */
	@Override
	public void responseEditEvent(BillEditEvent editEvent) {
		UFDate invoiceDate = new UFDate((String)getUI().getBillCardPanel().getHeadItem(getItemKey()).getValueObject());
		UFDate enddate = NatureDateTools.getDateAfterMonth(invoiceDate, 6);
		
		getUI().getBillCardPanel().getHeadItem(RegisterVO.ENDDATE).setValue(enddate);
	}

}
