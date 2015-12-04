/**
 * 
 */
package nc.ui.fbm.discountcalculate;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JTable;

import nc.ui.pub.bill.BillItem;
import nc.ui.pub.bill.BillTableCellRenderer;
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
public abstract class AbstractBillTableCellColorRender extends BillTableCellRenderer implements IBillTableCellColor {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private JTable table;
	
	
	/**
	 * 
	 */
	public AbstractBillTableCellColorRender() {
		super();
	}

	/**
	 * @param item
	 * @param newParameterVO
	 */
	public AbstractBillTableCellColorRender(BillItem item, BillRendererVO newParameterVO) {
		super(item, newParameterVO);
	}

   

	/**
	 * ���ߣ�lisg <br>
	 * ���ڣ�2006-9-14
	 *
	 * @see nc.ui.pub.bill.BillTableCellRenderer#getTableCellRendererComponent(javax.swing.JTable, java.lang.Object, boolean, boolean, int, int)
	 */
	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		this.table = table;
		return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
	}

	/**
	 * @return the table
	 */
	public JTable getTable() {
		return table;
	}

	/**
	 * ���ߣ�lisg <br>
	 * ���ڣ�2006-9-14
	 * <p>����������Ϣ�жϵ�ǰ������ɫ<p>
	 * @see nc.ui.pub.bill.BillTableCellRenderer#getBackGround(int, int)
	 */
	@Override
	public Color getBackGround(int row, int col) {
		JTable t = getTable();
		if(t!=null){
			if(isTotalRow(row, col)){
				return TOTAL_ROW_COLOR;
			}
			
			if(t.isCellEditable(row, col)){
				return ENABLE_EDIT_COLOR;
			}
		}
		
		return row % 2 == 0 ?EVEN_ROW_COLOR :ODD_ROW_COLOR ;
	}
	
	/**
	 * <p>
	 * �жϵ�ǰ���Ƿ��Ǻϼ���
	 * ����������ظ÷���,�Զ���ϼ��й���
	 * Ĭ��û�����úϼ���
	 * <p>
	 * ���ߣ�lisg <br>
	 * ���ڣ�2006-9-14
	 * @param row
	 * @param col
	 * @return
	 */
	protected boolean isTotalRow(int row, int col){
		return false;
	}
	
	

}
