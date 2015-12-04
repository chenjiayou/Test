/**
 *
 */
package nc.ui.fbm.invoice.status;

import nc.itf.cdm.util.CommonUtil;
import nc.ui.fac.account.pub.IRefTakenProccessor;
import nc.ui.fbm.gather.status.IFBMBillTypeStatus;
import nc.ui.fbm.pub.FBMManageUI;
import nc.ui.trade.base.AbstractBillUI;
import nc.vo.engine.status.AbstractBillUIStatus;
import nc.vo.fbm.pub.constant.FbmStatusConstant;
import nc.vo.fbm.register.RegisterVO;

/**
 * <p>
 *
 * <p>创建人：lpf
 * <b>日期：2007-9-20
 *
 */
public class InvoiceFbillTypeStatus extends AbstractBillUIStatus<Integer> {

	/**
	 * @param ui
	 */
	public InvoiceFbillTypeStatus(AbstractBillUI ui) {
		super(ui);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see nc.vo.engine.status.IStatus#getStatus()
	 */
	public Integer getStatus() throws Exception {
		String registerstatus = null;
		IRefTakenProccessor refProcessor = getManageUI().getRefTakenProccessor();
		boolean isInvoiced = false;
		boolean isPaybill = false;
		boolean isDestroyed = false;
		boolean isReturned = false;
		if(getManageUI().isListPanelSelected()){
			int selRows[] = getManageUI().getBillListPanel().getParentListPanel().getTable().getSelectedRows();
			for (int i = 0; i < selRows.length; i++) {
				registerstatus = (String) refProcessor.getValueByTakenInList(RegisterVO.TABLENAME,RegisterVO.REGISTERSTATUS,
						selRows[i]);	
				if(!CommonUtil.isNull(registerstatus)){
					if(registerstatus.equals(FbmStatusConstant.HAS_PAYBILL)){
						isInvoiced = true;
					}else if(registerstatus.equalsIgnoreCase(FbmStatusConstant.HAS_DESTROY)){
						isDestroyed = true;
					}else if(registerstatus.equalsIgnoreCase(FbmStatusConstant.HAS_RETURN)){
						isReturned = true;
					}
				}
			}
		}else{
			registerstatus=(String) refProcessor.getValueByTakenInCard(RegisterVO.REGISTERSTATUS);
			if(!CommonUtil.isNull(registerstatus)){
				if( registerstatus.equals(FbmStatusConstant.HAS_INVOICE)){
					isInvoiced = true;
				}else if(registerstatus.equals(FbmStatusConstant.HAS_PAYBILL)){
					isPaybill = true;
				}else if(registerstatus.equalsIgnoreCase(FbmStatusConstant.HAS_DESTROY)){
					isDestroyed = true;
				}else if(registerstatus.equalsIgnoreCase(FbmStatusConstant.HAS_RETURN)){
					isReturned = true;
				}
			}
		}
		
		if(isInvoiced){
			return IFBMBillTypeStatus.STATUS_INVOICED;
		}else if(isPaybill){
			return IFBMBillTypeStatus.STATUS_PAYBILL;
		}else if(isDestroyed){
			return IFBMBillTypeStatus.STATUS_DESTROYED;
		}else if(isReturned){
			return IFBMBillTypeStatus.STATUS_RETURNED;
		}
		
		return IFBMBillTypeStatus.STATUS_NULL;
	}

	private FBMManageUI getManageUI() {
		// TODO Auto-generated method stub
		return (FBMManageUI) getUI();
	}

	/* (non-Javadoc)
	 * @see nc.vo.engine.status.IStatus#getStatusKindName()
	 */
	public String getStatusKindName() {
		// TODO Auto-generated method stub
		return IFBMBillTypeStatus.class.getName();
	}

}
