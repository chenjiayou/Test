package nc.ui.fbm.reckon.listener;

import java.util.ArrayList;

import nc.ui.fbm.pub.AbstractItemEditListener;
import nc.ui.fbm.pub.FBMManageUI;
import nc.ui.pub.bill.BillEditEvent;
import nc.ui.pub.bill.BillModel;
import nc.vo.fbm.reckon.ReckonBVO;
import nc.vo.fbm.reckon.ReckonVO;
import nc.vo.pub.lang.UFDate;
import nc.vo.pub.lang.UFDouble;

/**
 * <p>
 * 调剂清算-清算日期编辑监听器类
 * <p>创建人：bsrl
 * <b>日期：2007-11-01
 *
 */
public class ReckonDateEditListener extends AbstractItemEditListener{
	/**
	 * 
	 */
	public ReckonDateEditListener() {
	}

	/**
	 * @param ui
	 * @param itemKey
	 */
	public ReckonDateEditListener(FBMManageUI ui, String itemKey) {
		super(ui, itemKey);
	}

	/**
	 * @param ui
	 * @param itemKey
	 * @param pos
	 */
	public ReckonDateEditListener(FBMManageUI ui, String itemKey, int pos) {
		super(ui, itemKey, pos);
	}
	
	protected UFDate getUfDate(Object date){
		UFDate ret = null;
		if(date != null){
			if(date instanceof UFDate){
				ret = (UFDate)date;
			}else if(date instanceof String){
				ret = new UFDate((String)date);
			}
		}
		
		return ret;
	}

	/* (non-Javadoc)
	 * @see nc.ui.fbm.pub.AbstractItemEditListener#responseEditEvent(nc.ui.pub.bill.BillEditEvent)
	 */
	@Override
	public void responseEditEvent(BillEditEvent editEvent) {
		if(editEvent.getValue() != null && editEvent.getValue().toString().trim().length() > 0) {
			BillModel billmodel = getUI().getBillCardPanel().getBillModel(ReckonBVO.TABLENAME);
			int rowcount = billmodel.getRowCount();
			ArrayList arrdelline = new ArrayList();
			for(int i = 0; i < rowcount; i ++) {
				UFDate enddate = getUfDate(getUI().getBillCardPanel().getBillModel(ReckonBVO.TABLENAME).getValueAt(i, ReckonBVO.ENDDATE));
				if(enddate != null && enddate.after(new UFDate(editEvent.getValue().toString()))) {
					arrdelline.add(new Integer(i));
				}
			}
			if(arrdelline.size() > 0) {
			    int[] delline = new int[arrdelline.size()];
				for(int i = 0 ; i < arrdelline.size(); i ++) {
					delline[i] = ((Integer)arrdelline.toArray()[i]).intValue();
				}
				billmodel.delLine(delline);
				rowcount = billmodel.getRowCount();
				UFDouble summoney = new UFDouble(0);
				UFDouble reckonmoney = null;
		        for (int i = 0; i < rowcount; i++) {
		        	reckonmoney = (UFDouble)getUI().getRefTakenProccessor().getValueByTakenInList(ReckonBVO.TABLENAME, ReckonBVO.MONEYY, i);
		        	summoney = summoney.add(reckonmoney);
		        }
		        getUI().getBillCardPanel().getHeadItem(ReckonVO.RECKONMONEYSUM).setValue(summoney);
			}
	    }
	}
}
