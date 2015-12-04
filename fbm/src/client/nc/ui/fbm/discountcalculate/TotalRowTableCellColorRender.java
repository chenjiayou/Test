/**
 * 
 */
package nc.ui.fbm.discountcalculate;

import java.util.Vector;

import nc.ui.pub.bill.BillItem;
import nc.ui.pub.bill.BillModel;
import nc.vo.pub.bill.BillRendererVO;

/**
 * <p>
 * 
 * <p>
 * 创建日期：2006-9-20
 * 
 * @author lisg
 * @since v5.0
 */
public class TotalRowTableCellColorRender extends AbstractBillTableCellColorRender {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	BillModel m_bm = null;
	TotalRowPara m_trp = null;
	int m_distinctindex = -1;
	


	/**
	 * @param item
	 * @param newParameterVO
	 */
	public TotalRowTableCellColorRender(BillModel bm, TotalRowPara trp, int distinctindex, BillItem item, BillRendererVO newParameterVO) {
		super(item, newParameterVO);
		m_bm = bm;
		this.m_trp = trp;
		m_distinctindex = distinctindex;
	}

	/**
	 * 作者：lisg <br>
	 * 日期：2006-9-20
	 *
	 * @see nc.ui.fbm.m23.AbstractBillTableCellColorRender#isTotalRow(int, int)
	 */
	@Override
	protected boolean isTotalRow(int row, int col) {
		if(row < 0 || getDistinctIndex() == -1 || getTotalRowPara() == null) return false;
		
		Vector v = (Vector)getBm().getDataVector().get(row);
		if(getDistinctIndex() > v.size() || getDistinctIndex() == v.size()) return false;
		
		Object Flag = (v).get(getDistinctIndex());
		
		if(Flag == null || !(Flag instanceof String)) return false;
		
		if(getTotalRowPara().getKeyWithFlag().containsKey((String)Flag)){
			return true;
		}
		return false;
	}

	/**
	 * @return the m_bm
	 */
	public BillModel getBm() {
		return m_bm;
	}

	/**
	 * @param m_bm the m_bm to set
	 */
	public void setBm(BillModel m_bm) {
		this.m_bm = m_bm;
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
	
	

}
