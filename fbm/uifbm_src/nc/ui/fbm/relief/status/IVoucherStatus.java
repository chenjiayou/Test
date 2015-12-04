package nc.ui.fbm.relief.status;

/**
 * 
 * 类功能说明：
     判断结算单位按钮是否亮，如果系统参数(FBM002-中心是否替单位制证)为是则不亮
     否则亮即单位自己制证。
 * 日期：2007-11-29
 * 程序员： wues
 *
 */

public interface IVoucherStatus {

	//系统参数设置为是，即单位制证按钮不亮
	public static int SYS_PARAM_YES = 1;
	//系统参数设置为否，即单位制证按钮亮
	public static int SYS_PARAM_NO = 2;
	
}
