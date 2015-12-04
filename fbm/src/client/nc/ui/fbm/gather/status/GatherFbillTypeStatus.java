/**
 *
 */
package nc.ui.fbm.gather.status;

import nc.itf.cdm.util.CommonUtil;
import nc.ui.fac.account.pub.IRefTakenProccessor;
import nc.ui.fbm.pub.FBMManageUI;
import nc.ui.trade.base.AbstractBillUI;
import nc.vo.engine.status.AbstractBillUIStatus;
import nc.vo.fbm.pub.constant.FbmStatusConstant;
import nc.vo.fbm.register.RegisterVO;
import nc.vo.pub.AggregatedValueObject;
import nc.vo.pub.lang.UFBoolean;

/**
 * <p>
 * 票据状态机
 * <p>创建人：lpf
 * <b>日期：2007-9-17
 *
 */
public class GatherFbillTypeStatus extends AbstractBillUIStatus<Integer> {

	/**
	 * @param ui
	 */
	public GatherFbillTypeStatus(AbstractBillUI ui) {
		super(ui);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see nc.vo.engine.status.IStatus#getStatus()
	 */
	public Integer getStatus() throws Exception {
		String registerstatus = null;
		IRefTakenProccessor refProcessor = getManageUI().getRefTakenProccessor();
		boolean isRegisted = false;
		boolean isSfflag = false;
		boolean isBankKeeped = false;
		boolean isdisable = false;
		boolean isendore = false;
		if(getManageUI().isListPanelSelected()){
			int selRows[] = getManageUI().getBillListPanel().getParentListPanel().getTable().getSelectedRows();
			for (int i = 0; i < selRows.length; i++) {
				registerstatus = /*FbmStatusConstant.REGISTER*/(String) refProcessor.getValueByTakenInList(RegisterVO.TABLENAME,RegisterVO.REGISTERSTATUS,
						selRows[i]);
				Object sfflag = getManageUI().getBillListPanel().getHeadBillModel().getValueAt(selRows[i], RegisterVO.SFFLAG);
				if(sfflag!=null&&((Boolean) sfflag).booleanValue()){
					isSfflag = true;
				}
				if(!CommonUtil.isNull(registerstatus)){
					if(registerstatus.equals(FbmStatusConstant.REGISTER)){
						isRegisted = true;
					}else if(registerstatus.equalsIgnoreCase(FbmStatusConstant.HAS_BANK_KEEP)){
						isBankKeeped = true;
					}else if(registerstatus.equalsIgnoreCase(FbmStatusConstant.HAS_DISABLE)){
						isdisable = true;
					}else if(registerstatus.equalsIgnoreCase(FbmStatusConstant.HAS_ENDORE)){
						isendore = true;
					}
				}
			}
		}else{
			registerstatus=(String) refProcessor.getValueByTakenInCard(RegisterVO.REGISTERSTATUS);
			AggregatedValueObject vo = getManageUI().getBufferData().getCurrentVO();
			if(vo!=null&&vo.getParentVO()!=null){
				Object sfflag = vo.getParentVO().getAttributeValue(RegisterVO.SFFLAG);
				if(sfflag==null||!((UFBoolean) sfflag).booleanValue()){
					return IFBMBillTypeStatus.STATUS_NULL;
				}
			}
			
			if(!CommonUtil.isNull(registerstatus)){
				if(registerstatus.equals(FbmStatusConstant.REGISTER)){
					isRegisted = true;
					return IFBMBillTypeStatus.STATUS_REGISTERD;
				}else if(registerstatus.equalsIgnoreCase(FbmStatusConstant.HAS_BANK_KEEP)){
					isBankKeeped = true;
					return IFBMBillTypeStatus.STATUS_BANKKEEPED;
				}else if(registerstatus.equalsIgnoreCase(FbmStatusConstant.HAS_DISABLE)){
					return IFBMBillTypeStatus.STATUA_DISABLE;
				}else if(registerstatus.equalsIgnoreCase(FbmStatusConstant.HAS_ENDORE)){
					return IFBMBillTypeStatus.STATUA_ENDORE;
				}
			}
		}
		
		if(!isSfflag){
			return IFBMBillTypeStatus.STATUS_NULL;
		}
		if(isRegisted&&isBankKeeped){
			return IFBMBillTypeStatus.STATUS_ALL;
		}else if(isRegisted){
			return IFBMBillTypeStatus.STATUS_REGISTERD;
		}else if(isBankKeeped){
			return IFBMBillTypeStatus.STATUS_BANKKEEPED;
		}else if(isdisable){
			return IFBMBillTypeStatus.STATUA_DISABLE;
		}else if(isendore){
			return IFBMBillTypeStatus.STATUA_ENDORE;
		}
		
		return IFBMBillTypeStatus.STATUS_NULL;
	}

	/* (non-Javadoc)
	 * @see nc.vo.engine.status.IStatus#getStatusKindName()
	 */
	public String getStatusKindName() {
		// TODO Auto-generated method stub
		return IFBMBillTypeStatus.class.getName();
	}

	private FBMManageUI getManageUI() {
		// TODO Auto-generated method stub
		return (FBMManageUI) getUI();
	}
}
