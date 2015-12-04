/**
 *
 */
package nc.ui.fbm.storage.status;

import nc.itf.cdm.util.CommonUtil;
import nc.ui.fbm.storage.innerkeep.InnerKeepUI;
import nc.ui.trade.base.AbstractBillUI;
import nc.vo.engine.status.AbstractBillUIStatus;
import nc.vo.fbm.storage.StoragePowerVO;
import nc.vo.fbm.storage.StorageVO;
import nc.vo.pub.AggregatedValueObject;


/**
 * <p>
 * 结算中心需要判断是作为单位的单据还是结算中心的单据
 * <p>创建人：lpf
 * <b>日期：2007-10-11
 *
 */
public class SettleCenterStatus extends AbstractBillUIStatus<Integer> {

	/**
	 * @param ui
	 */
	public SettleCenterStatus(AbstractBillUI ui) {
		super(ui);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see nc.vo.engine.status.IStatus#getStatus()
	 */
	public Integer getStatus() throws Exception {
		Integer retStatus = ISettleCenterStatus.ISSETTLE_STATUS;
		AggregatedValueObject currVo = getUI().getBufferData().getCurrentVO();
		if(currVo!=null&&currVo.getParentVO()!=null){
			StorageVO storageVo = (StorageVO) currVo.getParentVO();
			String pk_corp = getUI()._getCorp().getPrimaryKey();
			if(CommonUtil.isNull(pk_corp)){
				return retStatus;
			}
			StoragePowerVO power = getUI().getPower();
			boolean isCenter = power.isSettleCenter();
			boolean isSettle = power.isSettleUnit();
			if(isCenter&&isSettle){
				String pk_cubasdoc = power.getPk_cubasdoc();
				String keepunit = storageVo.getKeepunit();
				if(!CommonUtil.isNull(keepunit)&&pk_cubasdoc.equals(keepunit)){
					retStatus = ISettleCenterStatus.ISSETTLE_STATUS;
				}else{
					retStatus = ISettleCenterStatus.ISCENTER_STATUS;
				}
			}else{
				if(isCenter){
					retStatus = ISettleCenterStatus.ISCENTER_STATUS;
				}
				else if(isSettle){
					retStatus = ISettleCenterStatus.ISSETTLE_STATUS;
				}
			}
		}
		return retStatus;
	}

	/* (non-Javadoc)
	 * @see nc.vo.engine.status.IStatus#getStatusKindName()
	 */
	public String getStatusKindName() {
		return ISettleCenterStatus.class.getName();
	}

	@Override
	public InnerKeepUI getUI() {
		// TODO Auto-generated method stub
		return (InnerKeepUI) super.getUI();
	}

	
}
