/**
 * 
 */
package nc.ui.fbm.discount.reffilter;

import nc.ui.fbm.pub.FBMClientInfo;
import nc.ui.fbm.pub.FBMManageUI;
import nc.ui.pub.ClientEnvironment;
import nc.ui.pub.beans.UIComboBox;
import nc.ui.pub.bill.BillItem;
import nc.ui.tm.framework.ref.filter.BillItemRefModelFilter;
import nc.ui.trade.base.AbstractBillUI;
import nc.vo.fbm.pub.constant.FbmBusConstant;
import nc.vo.fbm.pub.constant.FbmStatusConstant;

/**
 * @author lpf
 *
 */
public class DiscountBillSourceFilter extends BillItemRefModelFilter {
	private String bankKey;
	private FBMManageUI ui;
	/**
	 * 
	 */
	public DiscountBillSourceFilter() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param billitem
	 */
	public DiscountBillSourceFilter(BillItem billitem) {
		super(billitem);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ui
	 */
	public DiscountBillSourceFilter(AbstractBillUI ui) {
		super(ui);
		// TODO Auto-generated constructor stub
	}

	public DiscountBillSourceFilter(FBMManageUI ui, BillItem billitem,
			String bankKey) {
		super(billitem);
		this.ui = ui;
		this.bankKey = bankKey;
	}

	/* (non-Javadoc)
	 * @see nc.ui.tm.framework.ref.filter.AbstractRefModelFilter#getSelfFilterString()
	 */
	@Override
	protected String getSelfFilterString() {
		String ret = "1=1";
		if (getBillItem() != null) {
			UIComboBox opBillType = (UIComboBox) getBillItem().getComponent();
			String opType = (String) opBillType.getSelectdItemValue();
			if(opType !=null){
			//如果选择的是私有票据，则加入pk_corp的条件,只选本公司的
				if (FbmBusConstant.BILL_PRIVACY.equals(opType)) {
					Object pk_bank = ui.getBillCardPanel().getHeadItem(bankKey).getValueObject();
					String bankfilter = null;
					if(pk_bank!=null){
						bankfilter = " pk_register in(select pk_source from fbm_storage join fbm_storage_b on fbm_storage.pk_storage=fbm_storage_b.pk_storage where keepunit = '"+pk_bank+"')";
						ret = ret + " and (registerstatus='register' or (registerstatus='has_bank_keep' and "+bankfilter+") ) ";
					}else {
						ret = ret + " and (registerstatus='register' or registerstatus='has_bank_keep' ) ";
					}
						ret = ret + " and  pk_corp = '" + ClientEnvironment.getInstance().getCorporation().getPk_corp() + "'" ;	 
				} else {
					ret = ret + " and pk_corp != '" + ClientEnvironment.getInstance().getCorporation().getPk_corp() + "'";
				}
			}else{
				ret = ret +" and registerstatus<>'"+FbmStatusConstant.HAS_IMPAWN+"' ";
			}
		}
		
		ret = ret + " and disable = 'N' and pk_billtypecode='36GA' ";
		
		if (FBMClientInfo.isSettleCenter()) {
			ret = ret+" and registerstatus in('register','has_bank_keep','has_relief_keep','has_impawn')";
		}else{
			ret = ret+" and registerstatus in('register','has_bank_keep','has_impawn')";
		}
		return ret;
	}

}
