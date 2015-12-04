/**
 * 
 */
package nc.ui.fbm.discountcalculate;

import nc.ui.pub.bill.BillItem;
import nc.vo.pub.bill.BillRendererVO;

/**
 * <p>
 * 
 * <p>
 * �������ڣ�2006-9-14
 * 
 * @author lisg
 * @since v5.0
 */
public class BillTableCellColorTotalRender extends AbstractBillTableCellColorRender {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * ���ߣ�lisg <br>
	 * ���ڣ�2006-9-14
	 * <p>Ĭ�����һ��Ϊ�ϼ���<p>
	 * @see nc.ui.fbm.m21.AbstractBillTableCellColorRender#isTotalRow(int, int)
	 */
	@Override
	protected boolean isTotalRow(int row, int col) {
		if(getTable() != null){
			if(getTable().getRowCount() == row+1){
				return true;
			}
		}
		
		return false;
	}

	/**
	 * 
	 */
	public BillTableCellColorTotalRender() {
		super();
	}

	/**
	 * @param item
	 * @param newParameterVO
	 */
	public BillTableCellColorTotalRender(BillItem item, BillRendererVO newParameterVO) {
		super(item, newParameterVO);
	}


	
	
}
