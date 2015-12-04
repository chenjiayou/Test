/**
 *
 */
package nc.vo.fbm.ccinterface;

import nc.itf.cdm.util.CommonUtil;
import nc.vo.cc.control.DefaultCcControlVO;
import nc.vo.cc.control.ICcBusType;
import nc.vo.cc.control.ICcControl;
import nc.vo.cc.control.ICcType;
import nc.vo.fbm.acceptbill.AcceptVO;
import nc.vo.fbm.pub.constant.FbmBusConstant;
import nc.vo.fbm.register.RegisterVO;
import nc.vo.pub.lang.UFDouble;

/**
 * <p>
 * 
 * <p>
 * 创建人：lpf 
 * <b>日期：2007-9-18
 * 
 */
public class CCDataAdapter {
	
	private static CCDataAdapter instance = null;

	/**
	 * 
	 */
	public CCDataAdapter() {
		// TODO Auto-generated constructor stub
	}

	public static CCDataAdapter getInstance() {
		if (instance == null)
			instance = new CCDataAdapter();
		return instance;
	}
	
	/**
	 * <p>
	 * 开票-授信控制参数
	 * <p>
	 * 作者：lpf
	 * 日期：2007-9-28
	 * @param invoiceVo
	 * @return
	 */
	private ICcControl invoiceToCControl(RegisterVO invoiceVo){
		ICcControl ccontrol = new DefaultCcControlVO();
		
		ccontrol.setBillcode(invoiceVo.getVbillno());
		ccontrol.setBusDate(invoiceVo.getInvoicedate());
		ccontrol.setBusDetailType("confirmed_cp");
		ccontrol.setBusPK(invoiceVo.getPrimaryKey());
		
		
		ccontrol.setPk_curr(invoiceVo.getPk_curr());
		UFDouble moneyy = new UFDouble(0.0);
		if(invoiceVo.getMoneyy()!=null){
			moneyy = invoiceVo.getMoneyy();
		}
		
		if (!CommonUtil.isNull(invoiceVo.getImpawnmode())
				&& invoiceVo.getImpawnmode().equals(
						FbmBusConstant.ASSURETYPE_BAIL)) {
			if(invoiceVo.getSecuritymoney()!=null){
				moneyy = moneyy.sub(invoiceVo.getSecuritymoney());
			}
		}
		
		ccontrol.setCcAmount(moneyy);
		ccontrol.setCcType(ICcType.CC_OUTER);
		ccontrol.setCcSubType(invoiceVo.getCctype());
		
		ccontrol.setLoanBank(invoiceVo.getPk_loanbank());
		ccontrol.setDebitUnit(invoiceVo.getPayunit());
		ccontrol.setDebitUnitFlag(ICcControl.CC_CubasDoc);
		
		ccontrol.setStartDate(invoiceVo.getInvoicedate());
		ccontrol.setEndDate(invoiceVo.getEnddate());
		
		ccontrol.setBusSys(ICcType.SYS_FBM);
		
		ccontrol.setBusType(ICcBusType.FBM_INVOICE);
		ccontrol.setBusSubType(ICcBusType.FBM_BILLTYPE_INVOICE);
		
		ccontrol.setActionType(ICcType.CC_ACTION_TYPE_USED);
		
		ccontrol.setAction(ICcType.CC_ACTION_ADD);
		return ccontrol;
	}

	/**
	 * <p>
	 * 票据付款-授信控制参数
	 * 如果担保方式为保证金，则释放的额度为：票面金额-保证金 20090305
	 * <p>
	 * 作者：lpf
	 * 日期：2007-9-28
	 * @param acceptVo
	 * @return
	 */
	private ICcControl acceptBillToCControl(AcceptVO acceptVo) {
		ICcControl ccontrol = new DefaultCcControlVO();
		
		ccontrol.setBillcode(acceptVo.getVbillno());
		ccontrol.setBusDate(acceptVo.getDacceptdate());
		ccontrol.setBusDetailType("confirmed_cp");
		ccontrol.setBusPK(acceptVo.getPrimaryKey());
		
		ccontrol.setPk_curr(acceptVo.getPk_curr());
		UFDouble money = new UFDouble(0.0);
		if(acceptVo.getBillmoneyy()!=null){
			money = acceptVo.getBillmoneyy();
		}
		if(acceptVo.getSecuritymoney()!=null){
			money = money.sub(acceptVo.getSecuritymoney());
		}
		
		ccontrol.setCcAmount(money);
		ccontrol.setBusRefPK(acceptVo.getPk_source());
		
		ccontrol.setCcType(ICcType.CC_OUTER);
		ccontrol.setCcSubType(acceptVo.getCctype());
		
		ccontrol.setLoanBank(acceptVo.getPk_loanbank());
		ccontrol.setDebitUnit(acceptVo.getPayunit());
		ccontrol.setDebitUnitFlag(ICcControl.CC_CubasDoc);
		
		ccontrol.setStartDate(acceptVo.getInvoicedate());
		ccontrol.setEndDate(acceptVo.getEnddate());
		
		ccontrol.setBusSys(ICcType.SYS_FBM);
		
		ccontrol.setBusType(ICcBusType.FBM_INVOICE);
		ccontrol.setBusSubType(ICcBusType.FBM_BILLTYPE_ACCEPT);
		
		ccontrol.setActionType(ICcType.CC_ACTION_TYPE_RELEASE);
		
		ccontrol.setAction(ICcType.CC_ACTION_ADD);
		
		ccontrol.setStartDate(acceptVo.getInvoicedate());
		
		ccontrol.setEndDate(acceptVo.getEnddate());
		
		return ccontrol;
	}
	
	/**
	 * <p>
	 * 开票审核-占用额度
	 * <p>
	 * 作者：lpf
	 * 日期：2007-9-28
	 * @param invoiceVo
	 * @return
	 */
	public ICcControl invoiceToCControlForApprove(RegisterVO invoiceVo){
		return invoiceToCControl(invoiceVo);
	}
	
	/**
	 * <p>
	 * 开票弃审-删除占用额度
	 * <p>
	 * 作者：lpf
	 * 日期：2007-9-28
	 * @param invoiceVo
	 * @return
	 */
	public ICcControl invoiceToCControlForUnApprove(RegisterVO invoiceVo) {
		ICcControl ccontrol = invoiceToCControl(invoiceVo);
		ccontrol.setAction(ICcType.CC_ACTION_DEL);
		return ccontrol;
	}
	
	/**
	 * <p>
	 * 承兑审核-释放额度
	 * <p>
	 * 作者：lpf
	 * 日期：2007-9-28
	 * @param acceptVo
	 * @return
	 */
	public ICcControl acceptToCControlForApprove(AcceptVO acceptVo) {
		return acceptBillToCControl(acceptVo);
	}

	/**
	 * <p>
	 * 承兑弃审-删除释放额度
	 * <p>
	 * 作者：lpf
	 * 日期：2007-9-28
	 * @param acceptVo
	 * @return
	 */
	public ICcControl acceptToCControlForUnApprove(AcceptVO acceptVo) {
		ICcControl ccontrol = acceptBillToCControl(acceptVo);
		ccontrol.setAction(ICcType.CC_ACTION_DEL);
		return ccontrol;
	}
	
	/**
	 * 
	 * <p>
	 * 开票核销-释放额度
	 * <p>
	 * 作者：lpf
	 * 日期：2007-10-22
	 * @param returnVo
	 * @return
	 */
	public ICcControl InvoiceToCControlForCancelBill(RegisterVO registerVo){
		ICcControl ccontrol = invoiceToCControl(registerVo);
		ccontrol.setActionType(ICcType.CC_ACTION_TYPE_RELEASE);
		ccontrol.setBusDate(registerVo.getVerifydate());
		return ccontrol;
	}
	
	/**
	 * 
	 * <p>
	 * 开票取消核销-反释放额度
	 * <p>
	 * 作者：lpf
	 * 日期：2007-10-22
	 * @param returnVo
	 * @return
	 */
	public ICcControl InvoiceToCControlForDelCancel(RegisterVO registerVo){
		ICcControl ccontrol = InvoiceToCControlForCancelBill(registerVo);
		ccontrol.setAction(ICcType.CC_ACTION_DEL);
		ccontrol.setBusDate(registerVo.getInvoicedate());
		return ccontrol;
	}
}
