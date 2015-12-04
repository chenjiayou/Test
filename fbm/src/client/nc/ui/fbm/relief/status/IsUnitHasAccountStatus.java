/**
 * 
 */
package nc.ui.fbm.relief.status;

import nc.vo.trade.pub.IExtendStatus;

/**
 * 单位是否制证状态
 * @author lpf
 * 
 */
public interface IsUnitHasAccountStatus extends IExtendStatus {
	//未记账
	public final static int NOT_SIGH = 1;
	//已记账
	public final static int HAS_SIGN = 2;
}
