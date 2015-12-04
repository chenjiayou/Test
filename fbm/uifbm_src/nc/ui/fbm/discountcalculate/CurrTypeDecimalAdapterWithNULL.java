/**
 * 
 */
package nc.ui.fbm.discountcalculate;

import nc.ui.pub.bill.BillModel;
import nc.ui.pub.bill.DefaultCurrTypeDecimalAdapter;

/**
 * <p>
 * 
 * <p>
 * 创建日期：2006-9-9
 * 
 * @author lisg
 * @since v5.0
 */
public class CurrTypeDecimalAdapterWithNULL extends DefaultCurrTypeDecimalAdapter {

	private int defaultDecimal = 2;
	
	public CurrTypeDecimalAdapterWithNULL(BillModel bm, String Source, String[] Target) {
		super(bm, Source, Target);
	}
	
	public CurrTypeDecimalAdapterWithNULL(BillModel bm, String Source, String[] Target, int defaultDecimal) {
		super(bm, Source, Target);
		this.defaultDecimal = defaultDecimal;
	}

	/**
	 * 作者：lisg <br>
	 * 日期：2006-9-9
	 *
	 * @see nc.ui.pub.bill.DefaultCurrTypeDecimalAdapter#getDecimalFromSource(int, java.lang.Object)
	 */
	@Override
	public int getDecimalFromSource(int row, Object pkValue) {
		if(pkValue == null) return getDefaultDecimal();
		return super.getDecimalFromSource(row, pkValue);
	}

	/**
	 * @return the defaultDecimal
	 */
	public int getDefaultDecimal() {
		return defaultDecimal;
	}

	/**
	 * @param defaultDecimal the defaultDecimal to set
	 */
	public void setDefaultDecimal(int defaultDecimal) {
		this.defaultDecimal = defaultDecimal;
	}
	
	
	
	
	

}
