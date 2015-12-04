package nc.ui.fbm.discountcalculate;

import java.awt.Color;
import java.awt.Component;
import java.util.Vector;

import javax.swing.JTable;

import nc.itf.cdm.util.CommonUtil;
import nc.ui.fac.account.pub.BillCellRenderWithTakenCol;
import nc.ui.fac.account.pub.IRefTakenProccessor;
import nc.ui.pub.bill.BillItem;
import nc.ui.pub.bill.BillModel;
import nc.vo.pub.bill.BillRendererVO;

public class DiscountCalculateTableCellRender extends BillCellRenderWithTakenCol 
	implements IBillTableCellColor {
	
	private JTable table;
	TotalRowPara m_trp = null;
	int m_distinctindex = -1;

	public DiscountCalculateTableCellRender(BillModel bm, BillItem item,
			IRefTakenProccessor refTakenProcessor,
			BillRendererVO newParameterVO, boolean isShowCode, TotalRowPara trp, int distinctindex) {
		super(bm, item, refTakenProcessor, newParameterVO, isShowCode);
		this.m_trp = trp;
		m_distinctindex = distinctindex;
	}

	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		this.table = table;
//		if(isTotalRow(row, column)){
//			if(!isTotalDefined(row, column)){
//				value = null;
//			}
//		}
		
		return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
	}
	
	private boolean isTotalDefined(int row, int column){
		if(row < 0 || getDistinctIndex() == -1 || getTotalRowPara() == null) return false;
		
		String itemKey = getBillItem().getKey();
		
		if(itemKey.equals(getTotalRowPara().getDistinctColName())
				|| itemKey.equals(getTotalRowPara().getSpecifyFeild())
				|| itemKey.equals(getTotalRowPara().getTotalDispName())){
			return true;
		}
		
		for(int i = 0; i < CommonUtil.getArrayLength(getTotalRowPara().getTotalColName()); i++){
			if(itemKey.equals(getTotalRowPara().getTotalColName()[i])){
				return true;
			}
		}
		
		return false;
	
	}
	
	/**
	 * @return the table
	 */
	public JTable getTable() {
		return table;
	}

	/**
	 * 作者：lisg <br>
	 * 日期：2006-9-14
	 * <p>根据行列信息判断当前背景颜色<p>
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
	 * 判断当前行是否是合计行
	 * 子类可以重载该方法,自定义合计行规则
	 * 默认没有启用合计行
	 * <p>
	 * 作者：lisg <br>
	 * 日期：2006-9-14
	 * @param row
	 * @param col
	 * @return
	 */
	protected boolean isTotalRow(int row, int col) {
		if(row < 0 || getDistinctIndex() == -1 || getTotalRowPara() == null) return false;
		
		Vector v = (Vector)getBillModel().getDataVector().get(row);
		if(getDistinctIndex() > v.size() || getDistinctIndex() == v.size()) return false;
		
		Object Flag = (v).get(getDistinctIndex());
		
		if(Flag == null || !(Flag instanceof String)) return false;
		
		if(getTotalRowPara().getKeyWithFlag().containsKey((String)Flag)){
			return true;
		}
		return false;
	}
	
	/**
	 * @return the m_trp
	 */
	public TotalRowPara getTotalRowPara() {
		return m_trp;
	}

	/**
	 * @param m_trp the m_trp to set
	 */
	public void setTotalRowPara(TotalRowPara trp) {
		this.m_trp = trp;
	}
	
	/**
	 * @return the m_distinctindex
	 */
	public int getDistinctIndex() {
		return m_distinctindex;
	}

	/**
	 * @param m_distinctindex the m_distinctindex to set
	 */
	public void setDistinctIndex(int m_distinctindex) {
		this.m_distinctindex = m_distinctindex;
	}
	
	protected Object convertValue(Object value, int row, int col) {
		if(!isTotalRow(row, col)){
			return super.convertValue(value, row, col);
		}else{
			return value;
		}
	}
}
