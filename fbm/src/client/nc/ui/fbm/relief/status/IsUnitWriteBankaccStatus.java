/**
 * 
 */
package nc.ui.fbm.relief.status;

import nc.ui.trade.base.AbstractBillUI;
import nc.vo.engine.status.AbstractBillUIStatus;
import nc.vo.fbm.relief.ReliefVO;
import nc.vo.pub.AggregatedValueObject;
import nc.vo.pub.lang.UFBoolean;

/**
 * @author lpf
 *
 */
public class IsUnitWriteBankaccStatus extends AbstractBillUIStatus<Integer> {
	//н╢╪гук
	public final static int NOT_SIGH = 1;
	//ря╪гук
	public final static int HAS_SIGN = 2;
	/**
	 * @param ui
	 */
	public IsUnitWriteBankaccStatus(AbstractBillUI ui) {
		super(ui);
	}

	/* (non-Javadoc)
	 * @see nc.vo.engine.status.IStatus#getStatus()
	 */
	public Integer getStatus() throws Exception {
		AggregatedValueObject billVo = getUI().getBufferData().getCurrentVO();
		if(billVo == null){
			return NOT_SIGH;
		}
		
		UFBoolean isTally = (UFBoolean)billVo.getParentVO().getAttributeValue(ReliefVO.UNITTALLY);
		if(isTally.booleanValue()){
			return HAS_SIGN;
		}
		return NOT_SIGH;
	}

	/* (non-Javadoc)
	 * @see nc.vo.engine.status.IStatus#getStatusKindName()
	 */
	public String getStatusKindName() {
		return IsUnitWriteBankaccStatus.class.getName();
	}

}
