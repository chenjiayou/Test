package nc.ui.fbm.gather;

import nc.ui.pub.bill.BillModelCellEditableController;

public class BatchSelectController implements BillModelCellEditableController {

	public boolean isCellEditable(boolean value, int row, String itemkey) {
		// TODO Auto-generated method stub
		if(itemkey.equals("batchop")){
			return true;
		}
		return false;
	}
	

}
