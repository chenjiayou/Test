/**
 *
 */
package nc.ui.fbm.storage.status;

import nc.itf.cdm.util.CommonUtil;
import nc.ui.fbm.pub.FBMManageUI;
import nc.ui.trade.base.AbstractBillUI;
import nc.vo.engine.status.AbstractBillUIStatus;
import nc.vo.pub.AggregatedValueObject;

/**
 * <p>
 *
 * <p>创建人：lpf
 * <b>日期：2007-10-11
 *
 */
public class OwnerCorpStatus extends AbstractBillUIStatus<Integer> {

	/**
	 * @param ui
	 */
	public OwnerCorpStatus(AbstractBillUI ui) {
		super(ui);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see nc.vo.engine.status.IStatus#getStatus()
	 */
	public Integer getStatus() throws Exception {
		Integer retStatus = IOwnerCorpStatus.ISNOT_OWNER_CORP;
		AggregatedValueObject currVo = getUI().getBufferData().getCurrentVO();
		if(currVo!=null&&currVo.getParentVO()!=null){
			String pk_corp = (String) currVo.getParentVO().getAttributeValue(getUI().getBillField().getField_Corp());
			if(!CommonUtil.isNull(pk_corp)&&pk_corp.equals(getUI()._getCorp().getPk_corp())){
				retStatus = IOwnerCorpStatus.IS_OWNER_CORP;
			}
		}
		return retStatus;
	}

	/* (non-Javadoc)
	 * @see nc.vo.engine.status.IStatus#getStatusKindName()
	 */
	public String getStatusKindName() {
		return IOwnerCorpStatus.class.getName();
	}

	@Override
	public FBMManageUI getUI() {
		// TODO Auto-generated method stub
		return (FBMManageUI) super.getUI();
	}
}
