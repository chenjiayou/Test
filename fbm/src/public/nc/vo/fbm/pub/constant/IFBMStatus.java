package nc.vo.fbm.pub.constant;

import nc.vo.trade.pub.IBillStatus;

/**
 * <p>
 * 扩展单据状态
 * <p>
 * 创建人：bsrl 
 * <b>日期：2007-8-30
 * 
 */
public interface IFBMStatus extends IBillStatus {
	/**
	 * 托收-已办理
	 */
	public static int Transact = 9;
	/**
	 * 托收-已作废
	 */
	public static int Disable = 10;
	
	/**
	 * 托收-已生成单据
	 */
	public static int Create = 12;
	
	/**
	 * 票据质押-质押回收
	 */
	public static int IMPAWN_BACK = 20;
	/**
	 * 内部存放-已入库
	 */
	public static final int INPUT_SUCCESS = 13;
	/**
	 * 内部领用-已出库
	 */
	public static final int OUTPUT_SUCCESS = 14;
	/**
	 * 内部托管-已退回
	 */
	public static final int  RETURNED = 15;

	/**
	 * 退票-已转出
	 */
	public static final int TRANSFORM = 17;
	
	/**
	 * 调剂出库-已出库
	 */
	public static final int HAS_OUTPUT = 18;
	/**
	 * 调剂出库-已中心制证
	 */
	public static final int HAS_VOUCHER = 19;   

	/**
	 * 收票-暂存
	 */
	public static final int INIT = 20;
	
	/**
	 * 贴现申请-在办理
	 */
	public static final int ON_TRANSACT = 21;   

	/**
	 * 贴现申请-已办理
	 */
	public static final int HAS_TRANSACT = 22;
	
	/**
	 * 已单位制证
	 */
	public static final int HAS_UNIT_VOUCHER = 23;
	
	/**
	 * 背书办理－已冲销
	 */
	public static final int HAS_CLEAR = 24;
	
	/**
	 * 背书办理－已制证
	 */
	public static final int HAS_VOUCHER_VAR = 25;
	
	public static final int SUBMIT = 40;
	

}
