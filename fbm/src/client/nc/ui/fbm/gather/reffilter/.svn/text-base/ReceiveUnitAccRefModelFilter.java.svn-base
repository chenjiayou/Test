/**
 *
 */
package nc.ui.fbm.gather.reffilter;

import nc.itf.cdm.util.CommonUtil;
import nc.ui.fbm.pub.FBMClientInfo;
import nc.ui.fbm.pub.FBMManageUI;
import nc.ui.pub.beans.UIRefPane;
import nc.ui.pub.bill.BillItem;
import nc.ui.tm.framework.ref.filter.BillItemRefModelFilter;
import nc.vo.fbm.register.RegisterVO;
import nc.vo.pub.lang.UFBoolean;

/**
 * <p>
 * 客支持商手工录入，银行帐号支持手工录入
 * <p>创建人：lpf
 * <b>日期：2007-11-2
 *
 */
public class ReceiveUnitAccRefModelFilter extends BillItemRefModelFilter {
	private FBMManageUI ui;
	/**
	 * 客商
	 */
	private String souceKey;
	/**
	 * 币种
	 */
	private String currency;
	/**
	 * @param billitem
	 */
	public ReceiveUnitAccRefModelFilter(BillItem billitem) {
		super(billitem);
		// TODO Auto-generated constructor stub
	}
	
	public ReceiveUnitAccRefModelFilter(BillItem billitem, FBMManageUI ui) {
		super(billitem);
		this.ui = ui;
	}

	public ReceiveUnitAccRefModelFilter(BillItem billitem, FBMManageUI ui,
			String souceKey) {
		super(billitem);
		this.ui = ui;
		this.souceKey = souceKey;
	}

	
	public ReceiveUnitAccRefModelFilter(BillItem billitem, FBMManageUI ui,
			String souceKey, String currency) {
		super(billitem);
		this.ui = ui;
		this.souceKey = souceKey;
		this.currency = currency;
	}

	/* (non-Javadoc)
	 * @see nc.ui.tm.framework.ref.filter.AbstractRefModelFilter#getSelfFilterString()
	 */
	@Override
	protected String getSelfFilterString() {

		return null;
	}

	/**
	 * 
	 * <p>
	 * 判断客商是否是手工录入的,基本档案则返回false，手工录入返回true
	 * <p>
	 * 作者：lpf
	 * 日期：2007-12-25
	 * @param refpane
	 * @return
	 */
	private boolean isCustDocHandin(BillItem custItem) {
		UIRefPane refpane = (UIRefPane) custItem.getComponent();
		String pk_cubasdoc = (String) custItem.getValueObject();
		
		if(!CommonUtil.isNull(pk_cubasdoc)){
			String isCust = (String) refpane.getRefValue("isCust");
			if(isCust!=null&&!(UFBoolean.valueOf(isCust).booleanValue())){
				return true;
			}
		}
		return false;
	}

}
