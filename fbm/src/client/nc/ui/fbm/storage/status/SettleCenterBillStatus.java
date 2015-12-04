/**
 *
 */
package nc.ui.fbm.storage.status;

import nc.itf.cdm.util.CommonUtil;
import nc.ui.fbm.storage.innerkeep.InnerKeepUI;
import nc.ui.trade.base.AbstractBillUI;
import nc.vo.engine.status.AbstractBillUIStatus;
import nc.vo.fbm.pub.constant.FbmBusConstant;
import nc.vo.fbm.storage.StoragePowerVO;
import nc.vo.fbm.storage.StorageVO;
import nc.vo.pub.AggregatedValueObject;

/**
 * <p>
 * 结算中心是否可以业务操作
 * <p>创建人：lpf
 * <b>日期：2007-11-29
 *
 */
public class SettleCenterBillStatus extends AbstractBillUIStatus<Integer> {

	/**
	 * @param ui
	 */
	public SettleCenterBillStatus(AbstractBillUI ui) {
		super(ui);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see nc.vo.engine.status.IStatus#getStatus()
	 */
	public Integer getStatus() throws Exception {
		int ret = ISettleCenterBillStatus.IS_SETTLEUNIT_BILL;
		StoragePowerVO power = getUI().getPower();
		AggregatedValueObject vo = getUI().getBufferData().getCurrentVO();
		if(vo!=null&&vo.getParentVO()!=null){
			if(power.isSettleCenter()&&power.isSettleUnit()){
				String itemkey = null;
				if(getUI().getUIControl().getBillType().equals(FbmBusConstant.BILLTYPE_INNERBACK)){
					itemkey = StorageVO.OUTPUTUNIT;
				}else{
					itemkey = StorageVO.KEEPUNIT;
				}
				String pk_dealunit = (String) vo.getParentVO().getAttributeValue(itemkey);
				String pk_centercust = power.getPk_cubasdoc();
				if(!CommonUtil.isNull(pk_dealunit)&&!CommonUtil.isNull(pk_centercust)&&pk_dealunit.equals(pk_centercust)){
					ret = ISettleCenterBillStatus.IS_OWN_BILL;
				}								
			}
		}
		
		return ret;
	}

	/* (non-Javadoc)
	 * @see nc.vo.engine.status.IStatus#getStatusKindName()
	 */
	public String getStatusKindName() {
		return ISettleCenterBillStatus.class.getName();
	}

	public InnerKeepUI getUI() {
		return (InnerKeepUI) super.getUI();
	}
}
