package nc.ui.fbm.relief.status;
/**
 * 
 * 类功能说明：
     扩展状态
 * 日期：2007-10-29
 * 程序员： wues
 *
 */
public interface IReliefStatus extends nc.vo.trade.pub.IExtendStatus{
	//未出库
	public final static int NOT_OUT = 1;
	//已出库
	public final static int HAS_OUT = 2;
}
