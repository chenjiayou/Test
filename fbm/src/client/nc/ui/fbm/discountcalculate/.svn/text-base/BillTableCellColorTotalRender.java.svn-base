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
 * 创建日期：2006-9-14
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
	 * 作者：lisg <br>
	 * 日期：2006-9-14
	 * <p>默认最后一行为合计行<p>
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
