package nc.ui.fbm.gather.listener;

import nc.ui.fbm.pub.AbstractItemEditListener;
import nc.ui.fbm.pub.FBMManageUI;
import nc.ui.pub.bill.BillEditEvent;
import nc.ui.pub.bill.BillItem;
import nc.vo.fbm.register.RegisterVO;

/**
 * 已收票标志 监听
 * 如果打勾，则允许录入计划项目
 * 否则不允许
 * @author xwq
 *
 * 2009-1-12
 */
public class SfflagEditListener extends AbstractItemEditListener {

	/**
	 * @param ui
	 * @param itemKey
	 */
	public SfflagEditListener(FBMManageUI ui, String itemKey) {
		super(ui, itemKey);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void responseEditEvent(BillEditEvent editEvent) {
		// TODO Auto-generated method stub
		Boolean sfflag = new Boolean(getUI().getBillCardPanel().getHeadItem(RegisterVO.SFFLAG).getValue());
//		BillItem planitem = getUI().getBillCardPanel().getHeadItem(RegisterVO.INVOICEPLANITEM);
		BillItem gatherPlanItem = getUI().getBillCardPanel().getHeadItem(RegisterVO.GATHERPLANITEM);
		BillItem invoiceOutPlanItem = getUI().getBillCardPanel().getHeadItem(RegisterVO.INVOICEOUTPLANITEM);
		
//		if(planitem!=null){
//			if(sfflag.equals(Boolean.TRUE)){
//				planitem.setEnabled(true);
//			}else{
//				planitem.setValue(null);
//				planitem.setEnabled(false);
//			}
//		}
		
		if(gatherPlanItem!=null){
			if(sfflag.equals(Boolean.TRUE)){
				gatherPlanItem.setEnabled(true);
			}else{
				gatherPlanItem.setValue(null);
				gatherPlanItem.setEnabled(false);
			}
		}
		
		if(invoiceOutPlanItem !=null){
			if(sfflag.equals(Boolean.TRUE)){
				invoiceOutPlanItem.setEnabled(true);
			}else
			{
				invoiceOutPlanItem.setValue(null);
				invoiceOutPlanItem.setEnabled(false);
			}
		}
		
		
	}

}
