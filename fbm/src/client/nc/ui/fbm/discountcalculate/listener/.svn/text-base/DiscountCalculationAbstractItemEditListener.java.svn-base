package nc.ui.fbm.discountcalculate.listener;

import nc.bs.logging.Logger;
import nc.ui.fbm.discountcalculate.DiscountCalculationUI;
import nc.ui.fbm.pub.IItemEditListener;
import nc.ui.ml.NCLangRes;
import nc.ui.pub.ClientEnvironment;
import nc.ui.pub.bill.BillEditEvent;
import nc.ui.pub.bill.IBillItem;
import nc.ui.tm.framework.util.ClientInfo;
import nc.vo.cf.exception.InterestAccrualException;
import nc.vo.fac.ia.enumeration.IAMethod;
import nc.vo.fbm.proxy.OuterProxy;
import nc.vo.pub.BusinessException;
import nc.vo.pub.lang.UFDate;
import nc.ui.fbm.discountcalculate.DiscountErrorFlag;

/**
 * 
 * <p>
 *	贴现试算界面编辑监听类抽象基类
 * <p>创建人：bsrl
 * <b>日期：2007-10-13
 *
 */
public abstract class DiscountCalculationAbstractItemEditListener implements IItemEditListener{
	private String itemKey = null;
	private int pos = IBillItem.HEAD;
	private nc.ui.fbm.discountcalculate.DiscountCalculationUI ui = null;
	
	public int getPos() {
		// TODO Auto-generated method stub
		return pos;
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
	
	protected Integer getInteger(Object integer){
		Integer ret = null;
		if(integer != null){
			if(integer instanceof Integer){
				ret = (Integer)integer;
			}else if(integer instanceof String){
				ret = new Integer((String)integer);
			}
		}
		
		return ret;
	}
	
	public DiscountCalculationUI getUI(){
		return ui;
	}
	
	/* (non-Javadoc)
	 * @see nc.ui.fbm.pub.IBillItemEditListener#getItemKey()
	 */
	public String getItemKey() {
		// TODO Auto-generated method stub
		return itemKey;
	}
	
	/**
	 * 
	 */
	public DiscountCalculationAbstractItemEditListener() {
	}

	/**
	 * @param ui
	 * @param itemKey
	 */
	public DiscountCalculationAbstractItemEditListener(nc.ui.fbm.discountcalculate.DiscountCalculationUI ui, String itemKey) {
		this.ui=ui;
		this.itemKey=itemKey;
	}

	/**
	 * @param ui
	 * @param itemKey
	 * @param pos
	 */
	public DiscountCalculationAbstractItemEditListener(nc.ui.fbm.discountcalculate.DiscountCalculationUI ui, String itemKey, int pos) {
		this.ui=ui;
		this.itemKey=itemKey;
		this.pos=pos;
	}
	
	/**
	 * <p>
	 *
	 * <p>
	 * 作者：lisg <br>
	 * 日期：2006-9-14
	 * @param rows
	 * @param errFlag
	 */
	protected void ShowMessageBox(int[] rows, int errFlag){
		StringBuffer str = new StringBuffer();
		for(int i : rows){
			BuildErrorMessage(str, errFlag, i);
		}
		
		getUI().showErrorMessage(str.toString());
	}
	
	protected void BuildErrorMessage(StringBuffer str, int errFlag, int i){
		str.append(BuildErrorMessage(new Integer(i).toString(),errFlag));
	}
	
	protected String BuildErrorMessage(int[] rows, int errFlag){
		if(rows == null || rows.length == 0) return "";
		
		StringBuffer str = new StringBuffer();
		for(int i : rows){
			str.append("," + i);
		}
		
		return BuildErrorMessage(str.substring(1),errFlag);
	}
	
	protected String BuildErrorMessage(String str_row, int errFlag){
		switch(errFlag){
			//"第" + str_row + "行: \"出票日期\"必须在\"贴现日期\"之前\n"
			case DiscountErrorFlag.ERROR_TXRQ_LT_CPRQ:	
				return NCLangRes.getInstance().getStrByID("362004", "UPP362004-000145")
				+str_row+NCLangRes.getInstance().getStrByID("362004", "UPP362004-000148")+"\n";
			//"第" + str_row + "行: \"起效日期\"必须在\"到期日期\"之前\n"
			case DiscountErrorFlag.ERROR_DQRQ_LT_QXRQ:
				return NCLangRes.getInstance().getStrByID("362004", "UPP362004-000145")
				+str_row+NCLangRes.getInstance().getStrByID("362004", "UPP362004-000149")+"\n";
			//"第" + str_row + "行: \"起效日期\"必须在\"贴现日期\"之前\n";
			case DiscountErrorFlag.ERROR_TXRQ_LT_QXRQ:
				return NCLangRes.getInstance().getStrByID("362004", "UPP362004-000145")
				+str_row+NCLangRes.getInstance().getStrByID("362004", "UPP362004-000150")+"\n";
		}
		
		return "";
	}
	
	public abstract void responseEditEvent(BillEditEvent editEvent) ;
	
}
