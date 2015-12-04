package nc.ui.fbm.pub;

import javax.swing.table.TableModel;

import nc.ui.fac.account.pub.IRefTakenProccessor;
import nc.ui.pub.bill.BillItem;
import nc.ui.pub.bill.BillListPanel;
import nc.ui.pub.bill.BillModel;
import nc.ui.pub.bill.BillTableSelectionChangeListener;
import nc.ui.pub.bill.BillTableSelectionEvent;
import nc.vo.pub.lang.UFDate;

public class FBMListHeadDataRefTakenListener implements BillTableSelectionChangeListener {
	private BillListPanel listpanel;
	private IRefTakenProccessor process;
	
	public FBMListHeadDataRefTakenListener(BillListPanel listpanel,IRefTakenProccessor processor){
		super();
		this.listpanel = listpanel;
		this.process = processor;
	}
	
	public void selectionChanged(BillTableSelectionEvent e) {
		// TODO Auto-generated method stub
		
		int row = listpanel.getParentListPanel().getTable().getSelectedRow();
		if(row != -1){
			BillItem[] items = listpanel.getBillListData().getHeadItems();
			int len = items.length;
			TableModel model = listpanel.getParentListPanel().getTable().getModel();
			for(BillItem item:items){
				Object obj = ((BillModel)model).getValueAt(row, item.getKey());
				if(obj != null){
					continue;
				}
				obj = process.renderCell(null, row, item.getShowOrder(), item, (BillModel)model);
				if(obj instanceof UFDate){
					UFDate date = (UFDate)obj;
					if(date !=null){
						((BillModel)model).setValueAt(date.toString(), row, item.getKey());
					}
				}else{
					((BillModel)model).setValueAt(obj, row, item.getKey());
				}
			}
		}
	}

}
